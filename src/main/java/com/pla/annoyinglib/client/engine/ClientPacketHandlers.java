package com.pla.annoyinglib.client.engine;
import com.pla.annoyinglib.util.CommonUtil;
import com.pla.annoyinglib.util.AAAParticlesUtil;
import static com.pla.annoyinglib.rig.RigColliderPreset.BODY;
import static com.pla.annoyinglib.rig.RigColliderPreset.*;
import static com.pla.annoyinglib.rig.RigColliderAnchor.*;
import net.minecraft.core.particles.ParticleTypes;
import com.pla.annoyinglib.network.ClientboundDiamondAttractorFx;
import com.pla.annoyinglib.config.AnnoyingLibClientConfig.VfxEffect;
import com.pla.annoyinglib.config.AnnoyingLibClientConfig;
import com.pla.annoyinglib.compat.PlayerNpcFactions;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.MobSupremacy;
import com.pla.annoyinglib.compat.photon.PhotonClientFxUtil;
import com.pla.annoyinglib.client.animation.RigClientAnimationState;
import com.pla.annoyinglib.network.*;
import com.pla.annoyinglib.util.ExplosionFxMute;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandlers {
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
        if (ModList.get().isLoaded("player_npc_factions")) PlayerNpcFactions.handleWoopieSwordWind(msg);
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
        if (level == null) return;
        var mode = AnnoyingLibClientConfig.getMode(VfxEffect.DIAMOND_ATTRACTOR);
        if (mode == AnnoyingLibClientConfig.VfxMode.DEFAULT || mode == AnnoyingLibClientConfig.VfxMode.VANILLA) {
            DiamondAttractorVfx.start(level, msg.entityId());
            return;
        }
        Entity entity = level.getEntity(msg.entityId());
        ClientVfxRouter.run(VfxEffect.DIAMOND_ATTRACTOR,
                () -> entity != null && PhotonClientFxUtil.followPosition("attractor:" + msg.entityId(), level, "av_attractor",
                        () -> entity.isAlive() && !entity.isRemoved() ? CommonUtil.getVanillaSwordOrBodyPosition(entity) : null, 32),
                () -> {
                    if (entity == null) return false;
                    AAAParticlesUtil.sendDiamondAttractor(level, entity);
                    return true;
                },
                () -> DiamondAttractorVfx.start(level, msg.entityId()));
    }

    public static void handlePlayerGroundTransitionPosition(ClientboundPlayerGroundTransitionPosition msg) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        minecraft.player.setDeltaMovement(Vec3.ZERO);
        minecraft.player.fallDistance = 0.0F;
        minecraft.player.setPos(msg.x(), msg.y(), msg.z());
    }



}
