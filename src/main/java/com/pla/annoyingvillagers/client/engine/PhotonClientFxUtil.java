package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class PhotonClientFxUtil {
    private static final String PHOTON_MOD_ID = "photon";
    private static final int STALE_TICKS = 5;
    private static final Map<String, ActiveEffect> ACTIVE_EFFECTS = new HashMap<>();

    private static Reflection reflection;
    private static boolean reflectionFailed;
    private static boolean warnedReflectionFailure;

    private PhotonClientFxUtil() {
    }

    @FunctionalInterface
    public interface BeamPositionProvider {
        Vec3 get(float partialTicks);
    }

    public enum BeamForwardAxis {
        POSITIVE_Z,
        POSITIVE_X
    }

    public static boolean isLoaded() {
        return ModList.get().isLoaded(PHOTON_MOD_ID) && reflection() != null;
    }

    public static boolean hasPhotonMod() {
        return ModList.get().isLoaded(PHOTON_MOD_ID);
    }

    public static ResourceLocation photon(String path) {
        return ResourceLocation.fromNamespaceAndPath(PHOTON_MOD_ID, path);
    }

    public static boolean spawnAt(Level level, String effectPath, Vec3 pos) {
        return spawnAt(level, effectPath, pos, new Quaternionf(), new Vector3f(1.0F, 1.0F, 1.0F));
    }

    public static boolean spawnDirectional(Level level, String effectPath, Vec3 from, Vec3 to, boolean flip) {
        if (from == null || to == null) {
            return false;
        }

        return spawnAt(level, effectPath, from, rotationFromTo(from, to, flip), new Vector3f(1.0F, 1.0F, 1.0F));
    }

    public static boolean spawnPortal(Level level, String effectPath, Vec3 pos, Vec3 normal) {
        return spawnAt(level, effectPath, pos, portalRotation(normal), unitScale());
    }

    public static boolean spawnAt(Level level, String effectPath, Vec3 pos, Quaternionf rotation, Vector3f scale) {
        if (!canUse(level) || pos == null) {
            return false;
        }

        try {
            Reflection r = reflection();
            if (r == null) {
                return false;
            }

            Object fx = r.getFx.invoke(null, photon(effectPath));
            if (fx == null) {
                return false;
            }

            BlockPos blockPos = BlockPos.containing(pos);
            Object effect = r.blockEffectConstructor.newInstance(fx, level, blockPos);
            r.setOffset.invoke(effect, offsetFromBlockCenter(pos, blockPos));
            r.setRotation.invoke(effect, rotation);
            r.setScale.invoke(effect, scale);
            r.setAllowMulti.invoke(effect, true);
            r.blockStart.invoke(effect);
            return true;
        } catch (ReflectiveOperationException | RuntimeException e) {
            warnReflectionFailure(e);
            return false;
        }
    }

    public static boolean followPortal(String key, Level level, String effectPath, Supplier<Vec3> positionSupplier,
                                       Supplier<Vec3> normalSupplier, int lifetimeTicks) {
        if (!canUse(level) || positionSupplier == null || normalSupplier == null) {
            return false;
        }

        try {
            Reflection r = reflection();
            if (r == null) {
                return false;
            }

            Vec3 pos = positionSupplier.get();
            if (pos == null) {
                return false;
            }

            Quaternionf rotation = portalRotation(normalSupplier.get());
            Vector3f scale = unitScale();
            long now = level.getGameTime();
            String normalizedKey = normalizeKey(key, effectPath);
            ActiveEffect active = ACTIVE_EFFECTS.get(normalizedKey);

            if (active == null || active.level != level || !active.isAlive(r)) {
                active = createRuntimeEffect(r, level, effectPath, pos, rotation, scale, now, lifetimeTicks);
                if (active == null) {
                    return false;
                }

                ACTIVE_EFFECTS.put(normalizedKey, active);
            }

            active.positionSupplier = positionSupplier;
            active.rotationSupplier = () -> portalRotation(normalSupplier.get());
            active.scaleSupplier = PhotonClientFxUtil::unitScale;
            active.updateTransform(r, pos, rotation, scale, now, lifetimeTicks);
            return true;
        } catch (ReflectiveOperationException | RuntimeException e) {
            warnReflectionFailure(e);
            return false;
        }
    }

    public static boolean followPosition(String key, Level level, String effectPath, Supplier<Vec3> positionSupplier, int lifetimeTicks) {
        if (!canUse(level) || positionSupplier == null) {
            return false;
        }

        try {
            Reflection r = reflection();
            if (r == null) {
                return false;
            }

            Vec3 pos = positionSupplier.get();
            if (pos == null) {
                return false;
            }

            long now = level.getGameTime();
            String normalizedKey = normalizeKey(key, effectPath);
            ActiveEffect active = ACTIVE_EFFECTS.get(normalizedKey);

            if (active == null || active.level != level || !active.isAlive(r)) {
                active = createRuntimeEffect(r, level, effectPath, pos, new Quaternionf(), unitScale(), now, lifetimeTicks);
                if (active == null) {
                    return false;
                }

                ACTIVE_EFFECTS.put(normalizedKey, active);
            }

            active.positionSupplier = positionSupplier;
            active.rotationSupplier = null;
            active.scaleSupplier = null;
            active.updatePosition(r, pos, now, lifetimeTicks);
            return true;
        } catch (ReflectiveOperationException | RuntimeException e) {
            warnReflectionFailure(e);
            return false;
        }
    }

    public static boolean followBeam(String key, Level level, String effectPath, Entity owner,
                                     BeamPositionProvider startProvider,
                                     BeamPositionProvider endProvider,
                                     BooleanSupplier aliveSupplier,
                                     int lifetimeTicks) {
        return followBeam(key, level, effectPath, owner, startProvider, endProvider, aliveSupplier, BeamForwardAxis.POSITIVE_Z, 0.0F, lifetimeTicks);
    }

    public static boolean followBeam(String key, Level level, String effectPath, Entity owner,
                                     BeamPositionProvider startProvider,
                                     BeamPositionProvider endProvider,
                                     BooleanSupplier aliveSupplier,
                                     BeamForwardAxis forwardAxis,
                                     float visualBaseLength,
                                     int lifetimeTicks) {
        if (!canUse(level) || owner == null || startProvider == null || endProvider == null || aliveSupplier == null) {
            return false;
        }

        try {
            return PhotonBeamEffect.startOrUpdate(
                    photon(effectPath),
                    level,
                    owner,
                    normalizeKey(key, effectPath),
                    startProvider,
                    endProvider,
                    aliveSupplier,
                    forwardAxis == null ? BeamForwardAxis.POSITIVE_Z : forwardAxis,
                    visualBaseLength,
                    lifetimeTicks);
        } catch (LinkageError | RuntimeException e) {
            warnReflectionFailure(e);
            return false;
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || ACTIVE_EFFECTS.isEmpty()) {
            return;
        }

        Level level = Minecraft.getInstance().level;
        if (level == null) {
            destroyAll();
            return;
        }

        Reflection r = reflection();
        if (r == null) {
            destroyAll();
            return;
        }

        long now = level.getGameTime();
        Iterator<Map.Entry<String, ActiveEffect>> iterator = ACTIVE_EFFECTS.entrySet().iterator();
        while (iterator.hasNext()) {
            ActiveEffect active = iterator.next().getValue();
            if (active.level == level && active.positionSupplier != null && !active.tickFollow(r, now)) {
                active.destroy(r);
                iterator.remove();
                continue;
            }

            if (active.level != level || now >= active.expireTick || !active.isAlive(r)) {
                active.destroy(r);
                iterator.remove();
            }
        }
    }

    private static ActiveEffect createRuntimeEffect(
            Reflection r,
            Level level,
            String effectPath,
            Vec3 pos,
            Quaternionf rotation,
            Vector3f scale,
            long now,
            int lifetimeTicks)
            throws ReflectiveOperationException {
        Object fx = r.getFx.invoke(null, photon(effectPath));
        if (fx == null) {
            return null;
        }

        BlockPos blockPos = BlockPos.containing(pos);
        Object effect = r.blockEffectConstructor.newInstance(fx, level, blockPos);
        r.setOffset.invoke(effect, offsetFromBlockCenter(pos, blockPos));
        r.setRotation.invoke(effect, rotation);
        r.setScale.invoke(effect, scale);
        r.setAllowMulti.invoke(effect, true);
        r.blockStart.invoke(effect);

        Object runtime = r.getRuntime.invoke(effect);
        if (runtime == null) {
            return null;
        }

        Object root = r.getRoot.invoke(runtime);
        if (root == null) {
            return null;
        }

        ActiveEffect active = new ActiveEffect(level, runtime, root, now);
        active.expireTick = now + Math.max(lifetimeTicks, STALE_TICKS);
        return active;
    }

    private static boolean canUse(Level level) {
        return level != null && level.isClientSide && ModList.get().isLoaded(PHOTON_MOD_ID);
    }

    private static String normalizeKey(String key, String effectPath) {
        return key + ":" + effectPath.toLowerCase(Locale.ROOT);
    }

    private static void destroyAll() {
        Reflection r = reflection();
        if (r != null) {
            ACTIVE_EFFECTS.values().forEach(active -> active.destroy(r));
        }

        ACTIVE_EFFECTS.clear();
    }

    private static Vector3f offsetFromBlockCenter(Vec3 pos, BlockPos blockPos) {
        return new Vector3f(
                (float) (pos.x - blockPos.getX() - 0.5D),
                (float) (pos.y - blockPos.getY() - 0.5D),
                (float) (pos.z - blockPos.getZ() - 0.5D));
    }

    private static Quaternionf rotationFromTo(Vec3 from, Vec3 to, boolean flip) {
        Vec3 delta = to.subtract(from);
        if (flip) {
            delta = delta.scale(-1.0D);
        }

        return rotationToward(delta);
    }

    private static Quaternionf portalRotation(Vec3 normal) {
        return rotationToward(normalizeOrDefault(normal));
    }

    private static Quaternionf rotationToward(Vec3 direction) {
        Vec3 delta = normalizeOrDefault(direction);
        double xz = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        float yaw = (float) Math.atan2(delta.x, delta.z);
        float pitch = (float) -Math.atan2(delta.y, xz);
        return new Quaternionf().rotateXYZ(pitch, yaw, 0.0F);
    }

    private static Vec3 normalizeOrDefault(Vec3 direction) {
        if (direction == null || direction.lengthSqr() < 1.0E-7D) {
            return new Vec3(0.0D, 0.0D, 1.0D);
        }
        return direction.normalize();
    }

    private static Vector3f unitScale() {
        return new Vector3f(1.0F, 1.0F, 1.0F);
    }

    private static Reflection reflection() {
        if (reflection != null) {
            return reflection;
        }

        if (reflectionFailed || !ModList.get().isLoaded(PHOTON_MOD_ID)) {
            return null;
        }

        try {
            Class<?> fxClass = Class.forName("com.lowdragmc.photon.client.fx.FX");
            Class<?> fxHelperClass = Class.forName("com.lowdragmc.photon.client.fx.FXHelper");
            Class<?> fxEffectClass = Class.forName("com.lowdragmc.photon.client.fx.FXEffect");
            Class<?> blockEffectClass = Class.forName("com.lowdragmc.photon.client.fx.BlockEffect");
            Class<?> fxRuntimeClass = Class.forName("com.lowdragmc.photon.client.fx.FXRuntime");
            Class<?> fxObjectClass = Class.forName("com.lowdragmc.photon.client.gameobject.IFXObject");

            reflection = new Reflection(
                    fxHelperClass.getMethod("getFX", ResourceLocation.class),
                    blockEffectClass.getConstructor(fxClass, Level.class, BlockPos.class),
                    fxEffectClass.getMethod("setOffset", Vector3f.class),
                    fxEffectClass.getMethod("setRotation", Quaternionf.class),
                    fxEffectClass.getMethod("setScale", Vector3f.class),
                    fxEffectClass.getMethod("setAllowMulti", boolean.class),
                    fxEffectClass.getMethod("getRuntime"),
                    blockEffectClass.getMethod("start"),
                    fxRuntimeClass.getMethod("getRoot"),
                    fxRuntimeClass.getMethod("isAlive"),
                    fxRuntimeClass.getMethod("destroy", boolean.class),
                    fxObjectClass.getMethod("updatePos", Vector3f.class),
                    fxObjectClass.getMethod("updateRotation", Quaternionf.class),
                    fxObjectClass.getMethod("updateScale", Vector3f.class));
            return reflection;
        } catch (ReflectiveOperationException | LinkageError e) {
            reflectionFailed = true;
            warnReflectionFailure(e);
            return null;
        }
    }

    private static void warnReflectionFailure(Throwable throwable) {
        if (warnedReflectionFailure) {
            return;
        }

        warnedReflectionFailure = true;
        AnnoyingVillagers.LOGGER.warn("Photon is loaded, but Annoying Villagers could not access Photon FX APIs.", throwable);
    }

    private record Reflection(
            Method getFx,
            Constructor<?> blockEffectConstructor,
            Method setOffset,
            Method setRotation,
            Method setScale,
            Method setAllowMulti,
            Method getRuntime,
            Method blockStart,
            Method getRoot,
            Method isAlive,
            Method destroy,
            Method updatePos,
            Method updateRotation,
            Method updateScale) {
    }

    private static final class ActiveEffect {
        private final Level level;
        private final Object runtime;
        private final Object root;
        private Supplier<Vec3> positionSupplier;
        private Supplier<Quaternionf> rotationSupplier;
        private Supplier<Vector3f> scaleSupplier;
        private long lastUpdateTick;
        private long expireTick;

        private ActiveEffect(Level level, Object runtime, Object root, long now) {
            this.level = level;
            this.runtime = runtime;
            this.root = root;
            this.lastUpdateTick = now;
            this.expireTick = now + STALE_TICKS;
        }

        private void updatePosition(Reflection r, Vec3 pos, long now, int lifetimeTicks) throws ReflectiveOperationException {
            updateTransform(r, pos, null, unitScale(), now, lifetimeTicks);
        }

        private void updateTransform(
                Reflection r,
                Vec3 pos,
                Quaternionf rotation,
                Vector3f scale,
                long now,
                int lifetimeTicks
        ) throws ReflectiveOperationException {
            r.updatePos.invoke(root, new Vector3f((float) pos.x, (float) pos.y, (float) pos.z));
            if (rotation != null) {
                r.updateRotation.invoke(root, rotation);
            }
            r.updateScale.invoke(root, scale == null ? unitScale() : scale);
            lastUpdateTick = now;
            expireTick = now + Math.max(lifetimeTicks, STALE_TICKS);
        }

        private boolean tickFollow(Reflection r, long now) {
            try {
                Vec3 pos = positionSupplier.get();
                if (pos == null) {
                    return false;
                }

                Quaternionf rotation = rotationSupplier == null ? null : rotationSupplier.get();
                Vector3f scale = scaleSupplier == null ? unitScale() : scaleSupplier.get();
                long previousExpireTick = expireTick;
                updateTransform(r, pos, rotation, scale, now, STALE_TICKS);
                expireTick = previousExpireTick;
                return true;
            } catch (ReflectiveOperationException | RuntimeException ignored) {
                return false;
            }
        }

        private boolean isAlive(Reflection r) {
            try {
                return Boolean.TRUE.equals(r.isAlive.invoke(runtime));
            } catch (ReflectiveOperationException | RuntimeException ignored) {
                return false;
            }
        }

        private void destroy(Reflection r) {
            try {
                r.destroy.invoke(runtime, false);
            } catch (ReflectiveOperationException | RuntimeException ignored) {
            }
        }
    }
}
