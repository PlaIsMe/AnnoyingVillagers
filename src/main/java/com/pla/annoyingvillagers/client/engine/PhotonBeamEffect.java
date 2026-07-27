package com.pla.annoyingvillagers.client.engine;

import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.lowdragmc.photon.client.gameobject.IFXObject;
import com.lowdragmc.photon.client.gameobject.emitter.beam.BeamConfig;
import com.lowdragmc.photon.client.gameobject.emitter.beam.BeamEmitter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;

@OnlyIn(Dist.CLIENT)
final class PhotonBeamEffect extends EntityEffect {
    private static final Map<String, PhotonBeamEffect> ACTIVE = new HashMap<>();
    private static final double FOLLOW_SMOOTHING = 0.55D;
    private static final double SNAP_DISTANCE_SQR = 64.0D;
    private static final float MIN_VISUAL_SCALE = 1.0E-4F;

    private final String key;
    private final Map<BeamEmitter, Vector3f> originalBeamEnds = new IdentityHashMap<>();
    private PhotonClientFxUtil.BeamPositionProvider startProvider;
    private PhotonClientFxUtil.BeamPositionProvider endProvider;
    private BooleanSupplier aliveSupplier;
    private PhotonClientFxUtil.BeamForwardAxis forwardAxis;
    private float visualBaseLength;
    private Vec3 lastGoodStart;
    private Vec3 lastGoodEnd;
    private Vec3 smoothedStart;
    private Vec3 smoothedEnd;
    private long expireTick;

    private PhotonBeamEffect(FX fx, Level level, Entity owner, String key,
                             PhotonClientFxUtil.BeamPositionProvider startProvider,
                             PhotonClientFxUtil.BeamPositionProvider endProvider,
                             BooleanSupplier aliveSupplier,
                             PhotonClientFxUtil.BeamForwardAxis forwardAxis,
                             float visualBaseLength,
                             int lifetimeTicks) {
        super(fx, level, owner, EntityEffect.AutoRotate.NONE);
        this.key = key;
        this.startProvider = startProvider;
        this.endProvider = endProvider;
        this.aliveSupplier = aliveSupplier;
        this.forwardAxis = forwardAxis;
        this.visualBaseLength = Math.max(visualBaseLength, 0.0F);
        this.expireTick = lifetimeTicks > 0 ? level.getGameTime() + lifetimeTicks : Long.MAX_VALUE;
    }

    static boolean startOrUpdate(ResourceLocation fxLocation, Level level, Entity owner, String key,
                                 PhotonClientFxUtil.BeamPositionProvider startProvider,
                                 PhotonClientFxUtil.BeamPositionProvider endProvider,
                                 BooleanSupplier aliveSupplier,
                                 PhotonClientFxUtil.BeamForwardAxis forwardAxis,
                                 float visualBaseLength,
                                 int lifetimeTicks) {
        FX fx = FXHelper.getFX(fxLocation);
        if (fx == null || level == null || owner == null || !owner.isAlive()
                || startProvider == null || endProvider == null || aliveSupplier == null) {
            return false;
        }

        cleanupDead();

        PhotonBeamEffect active = ACTIVE.get(key);
        if (active != null && active.level == level && active.entity == owner
                && active.runtime != null && active.runtime.isAlive()
                && Objects.equals(active.fx.getFxLocation(), fx.getFxLocation())) {
            active.startProvider = startProvider;
            active.endProvider = endProvider;
            active.aliveSupplier = aliveSupplier;
            active.forwardAxis = forwardAxis;
            active.visualBaseLength = Math.max(visualBaseLength, 0.0F);
            active.expireTick = lifetimeTicks > 0 ? level.getGameTime() + lifetimeTicks : Long.MAX_VALUE;
            return active.cacheBeamTarget(1.0F) != null;
        }

        PhotonBeamEffect effect = new PhotonBeamEffect(fx, level, owner, key, startProvider, endProvider, aliveSupplier, forwardAxis, visualBaseLength, lifetimeTicks);
        effect.setAllowMulti(true);
        effect.start();
        return effect.runtime != null && effect.runtime.isAlive();
    }

    @Override
    public void updateFXObjectTick(IFXObject object) {
        if (runtime == null || object != runtime.getRoot()) {
            return;
        }

        if (!entity.isAlive() || !isSourceAlive()) {
            stop();
        }
    }

    @Override
    public void updateFXObjectFrame(IFXObject object, float partialTicks) {
        if (runtime == null || object != runtime.getRoot()) {
            return;
        }

        if (!isSourceAlive() || !applyBeamTransform(partialTicks)) {
            stop();
        }
    }

    @Override
    public void start() {
        if (!entity.isAlive() || !isSourceAlive() || cacheBeamTarget(1.0F) == null) {
            return;
        }

        PhotonBeamEffect previous = ACTIVE.remove(key);
        if (previous != null) {
            previous.stop();
        }

        super.start();
        if (this.runtime == null) {
            return;
        }

        if (!applyBeamTransform(this.runtime.getRoot(), 1.0F)) {
            stop();
            return;
        }

        ACTIVE.put(key, this);
    }

    private boolean isSourceAlive() {
        return level != null
                && level.isClientSide
                && level.getGameTime() <= expireTick
                && aliveSupplier.getAsBoolean();
    }

    private boolean applyBeamTransform(float partialTicks) {
        return runtime != null && applyBeamTransform(runtime.getRoot(), partialTicks);
    }

    private boolean applyBeamTransform(IFXObject root, float partialTicks) {
        BeamEndpoints target = cacheBeamTarget(partialTicks);
        if (root == null || target == null) {
            return false;
        }

        smoothedStart = smooth(smoothedStart, target.start());
        smoothedEnd = smooth(smoothedEnd, target.end());

        BeamTransform transform = beamTransform(smoothedStart, smoothedEnd, forwardAxis);
        root.updatePos(new Vector3f((float) smoothedStart.x, (float) smoothedStart.y, (float) smoothedStart.z));
        root.updateRotation(transform.rotation);
        updateBeamLength(root, transform.length);
        return true;
    }

    private BeamEndpoints cacheBeamTarget(float partialTicks) {
        Vec3 from = startProvider.get(partialTicks);
        Vec3 to = endProvider.get(partialTicks);
        if (from != null && to != null) {
            lastGoodStart = from;
            lastGoodEnd = to;
        }

        if (lastGoodStart == null || lastGoodEnd == null) {
            return null;
        }

        return new BeamEndpoints(lastGoodStart, lastGoodEnd);
    }

    private static Vec3 smooth(Vec3 current, Vec3 target) {
        if (current == null || current.distanceToSqr(target) > SNAP_DISTANCE_SQR) {
            return target;
        }

        return current.lerp(target, FOLLOW_SMOOTHING);
    }

    private void updateBeamLength(IFXObject root, float length) {
        if (visualBaseLength > 0.0F) {
            float scale = Math.max(length / visualBaseLength, MIN_VISUAL_SCALE);
            root.updateScale(scaleVector(forwardAxis, scale));
            restoreNativeBeamEnds();
            return;
        }

        boolean usesNativeBeamEmitter = updateNativeBeamEnds(length);
        root.updateScale(usesNativeBeamEmitter
                ? new Vector3f(1.0F, 1.0F, 1.0F)
                : scaleVector(forwardAxis, length));
    }

    private boolean updateNativeBeamEnds(float length) {
        if (runtime == null) {
            return false;
        }

        boolean updated = false;
        for (Object object : runtime.getAllSceneObjects()) {
            if (!(object instanceof BeamEmitter beamEmitter)) {
                continue;
            }

            BeamConfig config = beamEmitter.getConfig();
            if (config != null) {
                rememberOriginalBeamEnd(beamEmitter, config);
                setAxisLength(config.getEnd(), forwardAxis, length);
                updated = true;
            }
        }

        return updated;
    }

    private boolean restoreNativeBeamEnds() {
        if (runtime == null) {
            return false;
        }

        boolean restored = false;
        for (Object object : runtime.getAllSceneObjects()) {
            if (!(object instanceof BeamEmitter beamEmitter)) {
                continue;
            }

            BeamConfig config = beamEmitter.getConfig();
            if (config != null) {
                Vector3f originalEnd = rememberOriginalBeamEnd(beamEmitter, config);
                config.getEnd().set(originalEnd);
                restored = true;
            }
        }

        return restored;
    }

    private Vector3f rememberOriginalBeamEnd(BeamEmitter beamEmitter, BeamConfig config) {
        return originalBeamEnds.computeIfAbsent(beamEmitter, ignored -> new Vector3f(config.getEnd()));
    }

    private static void setAxisLength(Vector3f vector, PhotonClientFxUtil.BeamForwardAxis axis, float length) {
        if (axis == PhotonClientFxUtil.BeamForwardAxis.POSITIVE_X) {
            vector.set(length, 0.0F, 0.0F);
        } else {
            vector.set(0.0F, 0.0F, length);
        }
    }

    private static Vector3f scaleVector(PhotonClientFxUtil.BeamForwardAxis axis, float length) {
        if (axis == PhotonClientFxUtil.BeamForwardAxis.POSITIVE_X) {
            return new Vector3f(length, 1.0F, 1.0F);
        }

        return new Vector3f(1.0F, 1.0F, length);
    }

    private void stop() {
        ACTIVE.remove(key);
        if (runtime != null) {
            runtime.destroy(forcedDeath);
            runtime = null;
        }
        removeFromEntityCache();
    }

    private void removeFromEntityCache() {
        List<EntityEffect> effects = EntityEffect.CACHE.get(entity);
        if (effects == null) {
            return;
        }

        effects.remove(this);
        if (effects.isEmpty()) {
            EntityEffect.CACHE.remove(entity);
        }
    }

    private static void cleanupDead() {
        Iterator<Map.Entry<String, PhotonBeamEffect>> iterator = ACTIVE.entrySet().iterator();
        while (iterator.hasNext()) {
            PhotonBeamEffect effect = iterator.next().getValue();
            if (effect.runtime == null || !effect.runtime.isAlive() || !effect.isSourceAlive()) {
                if (effect.runtime != null) {
                    effect.runtime.destroy(effect.forcedDeath);
                    effect.runtime = null;
                }
                effect.removeFromEntityCache();
                iterator.remove();
            }
        }
    }

    private static BeamTransform beamTransform(Vec3 from, Vec3 to, PhotonClientFxUtil.BeamForwardAxis forwardAxis) {
        Vec3 delta = to.subtract(from);
        double length = Math.max(delta.length(), 1.0e-4D);
        float toX = (float) (delta.x / length);
        float toY = (float) (delta.y / length);
        float toZ = (float) (delta.z / length);
        Quaternionf rotation = forwardAxis == PhotonClientFxUtil.BeamForwardAxis.POSITIVE_X
                ? new Quaternionf().rotationTo(1.0F, 0.0F, 0.0F, toX, toY, toZ)
                : new Quaternionf().rotationTo(0.0F, 0.0F, 1.0F, toX, toY, toZ);
        return new BeamTransform(rotation, (float) length);
    }

    private record BeamEndpoints(Vec3 start, Vec3 end) {
    }

    private record BeamTransform(Quaternionf rotation, float length) {
    }
}
