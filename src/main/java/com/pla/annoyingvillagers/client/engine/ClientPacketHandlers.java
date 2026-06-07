package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.event.NoVfxPortalEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlackFireSwordItem;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.network.*;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import com.pla.annoyingvillagers.util.ExplosionFxMute;
import com.pla.annoyingvillagers.util.HerobrineUtil;

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
import net.minecraftforge.fml.ModList;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandlers {
    private static final DustParticleOptions DIAMOND_GREEN_DUST =
            new DustParticleOptions(new Vector3f(0.0F, 1.0F, 0.3F), 1.15F);
    private static final DustParticleOptions DIAMOND_GLOW_DUST =
            new DustParticleOptions(new Vector3f(0.9F, 1.0F, 0.2F), 0.9F);

    private ClientPacketHandlers() {}

    private static Vec3 randomUnit(RandomSource rand) {
        double z = rand.nextDouble() * 2.0 - 1.0;
        double a = rand.nextDouble() * Math.PI * 2.0;
        double r = Math.sqrt(Math.max(0.0, 1.0 - z * z));
        return new Vec3(r * Math.cos(a), z, r * Math.sin(a));
    }

    private static void spawnParticle(Level level, ParticleOptions particle, Vec3 pos, Vec3 velocity) {
        level.addParticle(particle, true, pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);
    }

    public static void handleGlaiveExplosion(ClientboundGlaiveExplosionFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (PhotonClientFxUtil.spawnDirectional(level, "av_explodepurpur", msg.from(), msg.to(), true)) {
            // Photon handled this effect; sound still plays below.
        } else if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendEnderGlaiveExplosion(msg.from(), msg.to(), level);
        } else {
            EnderGlaiveItem.spawnExplosionFallback(level, msg.to());
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

    public static void handleHerobrineAssistanceFx(ClientboundHerobrineAssistancelFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendHerobrineAssistance(level, msg.from().x, msg.from().y, msg.from().z);
        } else {
            HerobrineUtil.startHerobrineAssistanceFallback(level, msg.from());
        }
    }

    public static void handleWoopieSwordWind(ClientboundWoopieSwordWindFx msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;

        if (PhotonClientFxUtil.spawnAt(level, "whoopiewind", msg.from())) {
            // Photon handled this effect; sound still plays below.
        } else if (ModList.get().isLoaded("aaa_particles")) {
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
        Entity entity = level.getEntity(msg.entityId());
        if (entity != null && PhotonClientFxUtil.followPosition(
                "blackfire:" + msg.entityId(),
                level,
                "blackfire",
                () -> {
                    Entity current = level.getEntity(msg.entityId());
                    return current == null || !current.isAlive() || current.isRemoved()
                            ? null
                            : BlackFireSwordItem.getBlackFireFallbackPosition(current);
                },
                60)) {
            return;
        }

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendBlackFire(level, entity);
        } else {
            BlackFireSwordItem.startBlackFireFallback(level, msg.entityId());
        }
    }

    public static void handleDiamondAttractor(ClientboundDiamondAttractorFx msg) {
        Level level = Minecraft.getInstance().level;

        if (level == null) {
            return;
        }

        Entity entity = level.getEntity(msg.entityId());
        if (entity != null && PhotonClientFxUtil.spawnAt(level, "av_attractor", BlackFireSwordItem.getSwordOrBodyPosition(entity))) {
            return;
        }

        if (ModList.get().isLoaded("aaa_particles")) {
            AAAParticlesUtil.sendDiamondAttractor(level, entity);
        } else {
            if (entity == null) {
                return;
            }

            RandomSource rand = level.getRandom();
            Vec3 center = BlackFireSwordItem.getSwordOrBodyPosition(entity);
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
    }

}
