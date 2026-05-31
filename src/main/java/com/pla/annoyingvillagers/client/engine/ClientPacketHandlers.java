package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.event.NoVfxPortalEvent;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.*;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.ExplosionFxMute;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class ClientPacketHandlers {
    private static final int BLACK_FIRE_FALLBACK_LOOKUP_TICKS = 80;
    private static final Map<Integer, Long> ACTIVE_BLACK_FIRE_FALLBACKS = new HashMap<>();
    private static Level blackFireFallbackLevel;
    private static final DustParticleOptions BLACK_FIRE_DUST =
            new DustParticleOptions(new Vector3f(0.03F, 0.03F, 0.035F), 1.35F);
    private static final DustParticleOptions BLACK_FIRE_FLASH_DUST =
            new DustParticleOptions(new Vector3f(0.85F, 0.9F, 0.8F), 0.9F);
    private static final DustParticleOptions DIAMOND_GREEN_DUST =
            new DustParticleOptions(new Vector3f(0.0F, 1.0F, 0.3F), 1.15F);
    private static final DustParticleOptions DIAMOND_GLOW_DUST =
            new DustParticleOptions(new Vector3f(0.9F, 1.0F, 0.2F), 0.9F);

    private ClientPacketHandlers() {}

    private static void resetBlackFireFallbacks(Level level) {
        if (blackFireFallbackLevel != level) {
            ACTIVE_BLACK_FIRE_FALLBACKS.clear();
            blackFireFallbackLevel = level;
        }
    }

    private static void spawnOmniRings(Level level, RandomSource rand, Vec3 center) {
        int ringPlanes = 6;
        for (int p = 0; p < ringPlanes; p++) {
            Vec3 normal = randomUnit(rand);

            spawnRing3d(level, rand, center, normal, 52, 2.0, 0.10, 0.12, 0.035);
            spawnRing3d(level, rand, center, normal, 60, 2.8, 0.14, 0.11, 0.030);
        }
    }

    private static void spawnRing3d(Level level, RandomSource rand, Vec3 center, Vec3 n,
                                    int points, double radius, double thickness,
                                    double tangentialSpeed, double outwardSpeed) {

        Vec3 normal = n.normalize();
        Vec3 u = normal.cross(new Vec3(0, 1, 0));
        if (u.lengthSqr() < 1.0e-6) u = normal.cross(new Vec3(1, 0, 0));
        u = u.normalize();
        Vec3 v = normal.cross(u).normalize();

        for (int i = 0; i < points; i++) {
            double a = (i / (double) points) * (Math.PI * 2.0) + rand.nextDouble() * 0.10;
            double cos = Math.cos(a);
            double sin = Math.sin(a);

            Vec3 radialDir = u.scale(cos).add(v.scale(sin));
            Vec3 tangentDir = normal.cross(radialDir).normalize();

            double layer = (rand.nextDouble() - 0.5) * 2.0 * thickness;
            Vec3 pos = center.add(radialDir.scale(radius)).add(normal.scale(layer));

            Vec3 vel = tangentDir.scale(tangentialSpeed)
                    .add(radialDir.scale(outwardSpeed))
                    .add((rand.nextDouble() - 0.5) * 0.02, (rand.nextDouble() - 0.5) * 0.02, (rand.nextDouble() - 0.5) * 0.02);

            level.addParticle(AnnoyingVillagersModParticleTypes.ENDER.get(), true,
                    pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);

            if ((i & 3) == 0) {
                level.addParticle(ParticleTypes.REVERSE_PORTAL, true,
                        pos.x, pos.y, pos.z, vel.x * 0.35, vel.y * 0.2, vel.z * 0.35);
            }
        }
    }

    private static Vec3 randomUnit(RandomSource rand) {
        double z = rand.nextDouble() * 2.0 - 1.0;
        double a = rand.nextDouble() * Math.PI * 2.0;
        double r = Math.sqrt(Math.max(0.0, 1.0 - z * z));
        return new Vec3(r * Math.cos(a), z, r * Math.sin(a));
    }

    private static void spawnParticle(Level level, ParticleOptions particle, Vec3 pos, Vec3 velocity) {
        level.addParticle(particle, true, pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);
    }

    private static Vec3 getSwordOrBodyPosition(Entity entity) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    entity,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    Minecraft.getInstance().getFrameTime(),
                    0.0F
            );

            if (pos != null) {
                return pos;
            }
        } catch (Exception ignored) {
        }

        return entity.position().add(0.0D, entity.getBbHeight() * 0.65D, 0.0D);
    }

    private static Vec3 getBlackFireFallbackPosition(Entity entity) {
        if (entity instanceof BlackFireEntity blackFire) {
            if (blackFire.isFollowOwnerSwordMode()) {
                Entity owner = blackFire.getOwnerEntity();

                if (owner != null && owner.isAlive() && !owner.isRemoved()) {
                    return getSwordOrBodyPosition(owner);
                }
            }

            return blackFire.position();
        }

        return getSwordOrBodyPosition(entity);
    }

    private static void startBlackFireFallback(Level level, int entityId) {
        resetBlackFireFallbacks(level);
        ACTIVE_BLACK_FIRE_FALLBACKS.put(entityId, level.getGameTime() + BLACK_FIRE_FALLBACK_LOOKUP_TICKS);

        Entity entity = level.getEntity(entityId);
        if (entity != null && entity.isAlive() && !entity.isRemoved()) {
            spawnBlackFireFallback(level, entity, true);
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;

        if (level == null) {
            ACTIVE_BLACK_FIRE_FALLBACKS.clear();
            blackFireFallbackLevel = level;
            return;
        }

        resetBlackFireFallbacks(level);

        if (ACTIVE_BLACK_FIRE_FALLBACKS.isEmpty()) {
            return;
        }

        long now = level.getGameTime();
        Iterator<Map.Entry<Integer, Long>> iterator = ACTIVE_BLACK_FIRE_FALLBACKS.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Long> active = iterator.next();
            Entity entity = level.getEntity(active.getKey());

            if (entity == null) {
                if (now > active.getValue()) {
                    iterator.remove();
                }

                continue;
            }

            if (!entity.isAlive() || entity.isRemoved()) {
                iterator.remove();
                continue;
            }

            spawnBlackFireFallback(level, entity);
        }
    }

    private static void spawnBlackFireFallback(Level level, Entity entity) {
        spawnBlackFireFallback(level, entity, false);
    }

    private static void spawnBlackFireFallback(Level level, Entity entity, boolean burst) {
        if (entity == null) {
            return;
        }

        RandomSource rand = level.getRandom();
        Vec3 center = getBlackFireFallbackPosition(entity);
        double radius = Math.max(0.35D, entity.getBbWidth() * 0.85D);
        double height = Math.max(0.45D, entity.getBbHeight() * 0.75D);
        int ringParticles = burst ? 54 : 16;
        int coreParticles = burst ? 12 : 4;

        for (int i = 0; i < ringParticles; i++) {
            double angle = (i / (double) ringParticles) * Math.PI * 2.0D + rand.nextDouble() * 0.35D;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double ringRadius = radius * (0.55D + rand.nextDouble() * 0.75D);

            Vec3 outward = new Vec3(cos, 0.0D, sin);
            Vec3 tangent = new Vec3(-sin, 0.0D, cos);
            Vec3 pos = center
                    .add(outward.scale(ringRadius))
                    .add(0.0D, (rand.nextDouble() - 0.35D) * height, 0.0D);
            Vec3 velocity = tangent.scale(0.035D + rand.nextDouble() * 0.055D)
                    .add(outward.scale((rand.nextDouble() - 0.45D) * 0.035D))
                    .add(0.0D, 0.025D + rand.nextDouble() * 0.055D, 0.0D);

            spawnParticle(level, rand.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.LARGE_SMOKE, pos, velocity);

            if ((i & 3) == 0) {
                spawnParticle(level, BLACK_FIRE_DUST, pos, velocity.scale(0.35D));
            }

            if (i % 5 == 0) {
                spawnParticle(level, ParticleTypes.SOUL_FIRE_FLAME, pos, velocity.scale(0.45D));
            }
        }

        for (int i = 0; i < coreParticles; i++) {
            Vec3 offset = randomUnit(rand).scale(rand.nextDouble() * radius * 0.45D);
            Vec3 pos = center.add(offset);
            Vec3 velocity = offset.scale(0.03D).add(0.0D, 0.04D + rand.nextDouble() * 0.06D, 0.0D);

            spawnParticle(level, BLACK_FIRE_FLASH_DUST, pos, velocity);
            if ((i & 1) == 0) {
                spawnParticle(level, ParticleTypes.POOF, pos, velocity.scale(0.55D));
            }
        }
    }

    private static void spawnDiamondAttractorFallback(Level level, Entity entity) {
        if (entity == null) {
            return;
        }

        RandomSource rand = level.getRandom();
        Vec3 center = getSwordOrBodyPosition(entity);
        Vec3 forward = entity.getLookAngle();

        if (forward.lengthSqr() < 1.0e-6D) {
            forward = new Vec3(0.0D, 0.0D, 1.0D);
        }

        forward = forward.normalize();

        Vec3 side = new Vec3(-forward.z, 0.0D, forward.x);
        if (side.lengthSqr() < 1.0e-6D) {
            side = new Vec3(1.0D, 0.0D, 0.0D);
        }
        side = side.normalize();

        Vec3 up = side.cross(forward).normalize();

        for (int i = 0; i < 86; i++) {
            double progress = i / 85.0D;
            double angle = progress * Math.PI * 4.0D + rand.nextDouble() * 0.18D;
            double radius = 0.16D + Math.sin(progress * Math.PI) * 0.72D;

            Vec3 radial = side.scale(Math.cos(angle)).add(up.scale(Math.sin(angle)));
            Vec3 pos = center
                    .add(forward.scale((progress - 0.5D) * 1.25D))
                    .add(radial.scale(radius));

            Vec3 tangent = forward.cross(radial).normalize().scale(0.065D + rand.nextDouble() * 0.04D);
            Vec3 pull = center.subtract(pos).normalize().scale(0.025D);
            Vec3 velocity = tangent.add(pull).add(forward.scale(0.015D));

            spawnParticle(level, rand.nextBoolean() ? DIAMOND_GREEN_DUST : DIAMOND_GLOW_DUST, pos, velocity);

            if ((i & 3) == 0) {
                spawnParticle(level, ParticleTypes.END_ROD, pos, velocity.scale(0.4D));
            }

            if (i % 6 == 0) {
                spawnParticle(level, ParticleTypes.ELECTRIC_SPARK, pos, velocity.scale(0.7D));
            }
        }

        for (int i = 0; i < 32; i++) {
            Vec3 offset = randomUnit(rand).scale(0.85D + rand.nextDouble() * 1.25D);
            Vec3 pos = center.add(offset);
            Vec3 velocity = center.subtract(pos).normalize().scale(0.07D + rand.nextDouble() * 0.05D);

            spawnParticle(level, ParticleTypes.ENCHANT, pos, velocity);
            if ((i & 1) == 0) {
                spawnParticle(level, DIAMOND_GREEN_DUST, pos, velocity);
            }
        }
    }

    public static void handleGlaiveExplosion(ClientboundGlaiveExplosionFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendEnderGlaiveExplosion(msg.from(), msg.to(), level);
        } else {
            Vec3 center = msg.to();
            RandomSource rand = level.getRandom();

            level.addParticle(
                    AnnoyingVillagersModParticleTypes.FIREBALL.get(),
                    true,
                    center.x, center.y, center.z,
                    5.0, 1, 0.0
            );
            spawnOmniRings(level, rand, center);
        }

        level.playLocalSound(msg.from().x, msg.from().y, msg.from().z, AnnoyingVillagersModSounds.ENDER_SHOT.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }

    public static void handleMuteExplosionAtPos(ClientboundMuteExplosionAtPos msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        ExplosionFxMute.mark(msg.pos().asLong(), level.getGameTime() + msg.lifetimeTicks());
    }

    public static void handleHerobrinePortalFx(ClientboundHerobrinePortalFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendHerobrinePortal(level, msg.from().x, msg.from().y, msg.from().z);
        } else {
            NoVfxPortalEvent.spawn(msg.from(), 60);
        }
    }

    public static void handleLitePortalFx(ClientboundLitePortalFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendLitePortal(level, msg.from().x, msg.from().y, msg.from().z);
        } else {
            NoVfxPortalEvent.spawn(msg.from(), 0);
        }
    }

    public static void handleWoopieSwordWind(ClientboundWoopieSwordWindFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendWoopieWind(level, msg.from().x, msg.from().y, msg.from().z);
        } else {
            RandomSource rand = level.getRandom();

            int rings = 3;
            int pointsPerRing = 36;
            double baseRadius = 0.9;
            double radiusStep = 0.35;
            double baseY = 0.15;
            double yStep = 0.18;

            double tangentialSpeed = 0.14;
            double outwardSpeed = 0.03;

            for (int r = 0; r < rings; r++) {
                double radius = baseRadius + r * radiusStep;
                double yOff = baseY + r * yStep;

                for (int i = 0; i < pointsPerRing; i++) {
                    double a = (i / (double) pointsPerRing) * Math.PI * 2.0 + rand.nextDouble() * 0.12;
                    double cos = Math.cos(a);
                    double sin = Math.sin(a);

                    double px = msg.from().x + cos * radius;
                    double py = msg.from().y + yOff + (rand.nextDouble() - 0.5) * 0.06;
                    double pz = msg.from().z + sin * radius;
                    double vx = (-sin) * tangentialSpeed + cos * outwardSpeed;
                    double vy = 0.01 + rand.nextDouble() * 0.02;
                    double vz = ( cos) * tangentialSpeed + sin * outwardSpeed;

                    level.addParticle(ParticleTypes.CLOUD, true, px, py, pz, vx, vy, vz);
                    if ((i & 3) == 0) {
                        level.addParticle(ParticleTypes.SMOKE, true, px, py, pz, vx * 0.35, vy * 0.2, vz * 0.35);
                    }
                }
            }

            for (int i = 0; i < 14; i++) {
                double vx = (rand.nextDouble() - 0.5) * 0.25;
                double vy = 0.03 + rand.nextDouble() * 0.18;
                double vz = (rand.nextDouble() - 0.5) * 0.25;
                level.addParticle(ParticleTypes.POOF, true, msg.from().x, msg.from().y + 0.25, msg.from().z, vx, vy, vz);
            }

            level.addParticle(ParticleTypes.EXPLOSION, true, msg.from().x, msg.from().y + 0.35, msg.from().z, 0.0, 0.0, 0.0);
        }

        level.playLocalSound(msg.from().x, msg.from().y, msg.from().z, AnnoyingVillagersModSounds.WOOPIE_WIND.get(),
                SoundSource.NEUTRAL, 1.0F, 1.0F, false);
    }

    public static void handleBlackFire(ClientboundBlackFireFx msg) {
        Level level = Minecraft.getInstance().level;

        if (level == null) {
            return;
        }
        if (ModList.get().isLoaded("aaa_particles")) {
            Entity entity = level.getEntity(msg.entityId());
            AAAParticlesUtil.sendBlackFire(level, entity);
        } else {
            startBlackFireFallback(level, msg.entityId());
        }
    }

    public static void handleDiamondAttractor(ClientboundDiamondAttractorFx msg) {
        Level level = Minecraft.getInstance().level;

        if (level == null) {
            return;
        }

        if (ModList.get().isLoaded("aaa_particles")) {
            Entity entity = level.getEntity(msg.entityId());
            AAAParticlesUtil.sendDiamondAttractor(level, entity);
        } else {
            Entity entity = level.getEntity(msg.entityId());
            spawnDiamondAttractorFallback(level, entity);
        }
    }
}
