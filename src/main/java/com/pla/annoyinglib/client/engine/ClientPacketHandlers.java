package com.pla.annoyinglib.client.engine;
import com.pla.annoyinglib.util.CommonUtil;
import com.pla.annoyinglib.util.AAAParticlesUtil;
import static com.pla.annoyinglib.rig.RigColliderPreset.BODY;
import static com.pla.annoyinglib.rig.RigColliderPreset.*;
import static com.pla.annoyinglib.rig.RigColliderAnchor.*;
import net.minecraft.core.particles.ParticleTypes;
import com.pla.annoyinglib.network.ClientboundDiamondAttractorFx;
import com.pla.annoyinglib.config.AnnoyingLibClientConfig.VfxEffect;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.MobSupremacy;
import com.pla.annoyinglib.compat.photon.PhotonClientFxUtil;
import com.pla.annoyinglib.client.animation.RigClientAnimationState;
import com.pla.annoyinglib.network.*;
import com.pla.annoyinglib.util.ExplosionFxMute;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandlers {
    public static final DustParticleOptions DIAMOND_GREEN_DUST =
            new DustParticleOptions(new Vector3f(0.0F, 1.0F, 0.3F), 1.15F);
    public static final DustParticleOptions DIAMOND_GLOW_DUST =
            new DustParticleOptions(new Vector3f(0.9F, 1.0F, 0.2F), 0.9F);

    private ClientPacketHandlers() {}

    public static Vec3 randomUnit(RandomSource rand) {
        double z = rand.nextDouble() * 2.0 - 1.0;
        double a = rand.nextDouble() * Math.PI * 2.0;
        double r = Math.sqrt(Math.max(0.0, 1.0 - z * z));
        return new Vec3(r * Math.cos(a), z, r * Math.sin(a));
    }

    public static void spawnParticle(Level level, ParticleOptions particle, Vec3 pos, Vec3 velocity) {
        level.addParticle(particle, true, pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z);
    }

    public static void spawnSpreadParticle(Level level, ParticleOptions particle, Vec3 center, RandomSource rand,
                                            double xOffset, double yOffset, double zOffset, double speed) {
        Vec3 pos = center.add(
                (rand.nextDouble() * 2.0D - 1.0D) * xOffset,
                (rand.nextDouble() * 2.0D - 1.0D) * yOffset,
                (rand.nextDouble() * 2.0D - 1.0D) * zOffset);
        Vec3 velocity = speed == 0.0D ? Vec3.ZERO : randomUnit(rand).scale(speed);
        spawnParticle(level, particle, pos, velocity);
    }

    public static String pulseKey(String prefix, int entityId, int tickCount, String suffix) {
        return prefix + ":" + entityId + ":" + tickCount + suffix;
    }

    public static boolean followPhotonEntity(Level level, String effectPath, String key, Entity entity,
                                              Vec3 offset, int lifetimeTicks) {
        if (entity == null || entity.isRemoved()) {
            return false;
        }

        Vec3 fixedOffset = offset == null ? Vec3.ZERO : offset;
        return PhotonClientFxUtil.followPosition(key, level, effectPath,
                () -> entity.isRemoved() ? null : entity.position().add(fixedOffset), lifetimeTicks);
    }

    public static void handleMuteExplosionAtPos(ClientboundMuteExplosionAtPos msg) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        ExplosionFxMute.mark(msg.pos().asLong(), level.getGameTime() + msg.lifetimeTicks());
    }

    public static void handleBlueDemonEffectFx(Object msg) {
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.handleBlueDemonEffectFx(msg);
    }

    public static void handleWoopieSwordWind(Object msg) {
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.handleWoopieSwordWind(msg);
    }

    public static void handleGroundFracture(ClientboundGroundFracture msg) {
        GroundFractureClient.handle(msg);
    }

    public static void handleRigAnimation(ClientboundRigAnimation msg) {
        if (Minecraft.getInstance().level == null) {
            return;
        }

        RigClientAnimationState.start(msg.entityId(), msg.animationId(), msg.durationTicks(),
                msg.trailStartTick(), msg.trailEndTickExclusive());
    }



public static void handleDiamondAttractor(ClientboundDiamondAttractorFx msg) {

                Level level = Minecraft.getInstance().level;

                if (level == null) {
                    return;
                }

                Entity entity = level.getEntity(msg.entityId());
                ClientVfxRouter.run(
                        VfxEffect.DIAMOND_ATTRACTOR,
                        () -> entity != null && PhotonClientFxUtil.spawnAt(level, "av_attractor", CommonUtil.getVanillaSwordOrBodyPosition(entity)),
                        () -> {
                            if (entity == null) {
                                return false;
                            }

                            AAAParticlesUtil.sendDiamondAttractor(level, entity);
                            return true;
                        },
                        () -> {
                            if (entity == null) {
                                return;
                            }

                            RandomSource rand = level.getRandom();
                            Vec3 center = CommonUtil.getVanillaSwordOrBodyPosition(entity);
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

                                com.pla.annoyinglib.client.engine.ClientPacketHandlers.spawnParticle(level, rand.nextBoolean() ? DIAMOND_GREEN_DUST : DIAMOND_GLOW_DUST, pos, velocity);

                                if ((i & 3) == 0) {
                                    com.pla.annoyinglib.client.engine.ClientPacketHandlers.spawnParticle(level, ParticleTypes.END_ROD, pos, velocity.scale(0.4D));
                                }

                                if (i % 6 == 0) {
                                    com.pla.annoyinglib.client.engine.ClientPacketHandlers.spawnParticle(level, ParticleTypes.ELECTRIC_SPARK, pos, velocity.scale(0.7D));
                                }
                            }

                            for (int i = 0; i < 32; i++) {
                                Vec3 offset = com.pla.annoyinglib.client.engine.ClientPacketHandlers.randomUnit(rand).scale(0.85D + rand.nextDouble() * 1.25D);
                                Vec3 pos = center.add(offset);
                                Vec3 velocity = center.subtract(pos).normalize().scale(0.07D + rand.nextDouble() * 0.05D);

                                com.pla.annoyinglib.client.engine.ClientPacketHandlers.spawnParticle(level, ParticleTypes.ENCHANT, pos, velocity);
                                if ((i & 1) == 0) {
                                    com.pla.annoyinglib.client.engine.ClientPacketHandlers.spawnParticle(level, DIAMOND_GREEN_DUST, pos, velocity);
                                }
                            }
                        });
    }

    public static void handlePlayerGroundTransitionPosition(ClientboundPlayerGroundTransitionPosition msg) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        minecraft.player.setDeltaMovement(Vec3.ZERO);
        minecraft.player.fallDistance = 0.0F;
        minecraft.player.setPos(msg.x(), msg.y(), msg.z());
    }



}
