package com.pla.annoyinglib.util;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.compat.aaa_particles.emitterinfo.BlackFireParticleEmitterInfo;
import com.pla.annoyinglib.compat.aaa_particles.emitterinfo.BlueDemonThunderBeamParticleEmitterInfo;
import com.pla.annoyinglib.entity.BlueDemonThunderBeamEntity;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AAAParticlesUtil {
     // add this in herobrine_vessels
    // public static void sendDragonBeam(Vec3 from, Vec3 to, Level level, HerobrineDragonEntity caster, LivingEntity target) {
    //     new DragonBeamParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "dragon_beam"))
    //             .fromTo(from, to, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
    //             .follow(caster, target, 120, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
    //             .spawnInWorld(level, null);
    // }

    public static void sendDragonBeamHit(Level level, BlockPos hitBlock) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "dragon_beam_hit"))
                        .clone()
                        .position(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ()));
    }

    public static void sendBlueDemonThunderBeam(Level level, BlueDemonThunderBeamEntity blueDemonThunderBeamEntity) {
        new BlueDemonThunderBeamParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "blue_demon_thunder_beam"))
                .followBeam(blueDemonThunderBeamEntity, blueDemonThunderBeamEntity.getDuration(), BlueDemonThunderBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .spawnInWorld(level, null);
    }

    public static void sendHerobrinePortal(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "herobrine_portal"))
                        .clone()
                        .position(x, y, z));
    }

    public static void sendHerobrineAssistance(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "requesting_assistance"))
                        .clone()
                        .position(x, y, z));
    }

    public static void sendWoopieWind(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "woopie_sword_wind"))
                        .clone()
                        .position(x, y, z));
    }

    // add this in herobrine_vessels
    // public static boolean sendTeleportPortal(Level level, Vec3 pos, Vec3 normal) {
    //     if (level == null || pos == null || !level.isClientSide()) {
    //         return false;
    //     }

    //     new TeleportPortalParticleEmitterInfo(
    //             ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "teleport_portal"))
    //             .atPortal(pos, normal, TeleportPortalParticleEmitterInfo.ForwardAxis.PLUS_Z, 0.0F)
    //             .spawnInWorld(level, Minecraft.getInstance().player);
    //     return true;
    // }

    public static void sendBlackFire(Level level, Entity entity) {
        if (level == null || entity == null) {
            return;
        }

        if (!level.isClientSide()) {
            return;
        }

        new BlackFireParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "black_fire"))
                .followEntity(entity, 60, Vec3.ZERO)
                .smoothing(1.0D)
                .spawnInWorld(level, Minecraft.getInstance().player);
    }

    public static void sendDiamondAttractor(Level level, Entity entity) {
        if (level == null || entity == null) {
            return;
        }

        if (!level.isClientSide()) {
            return;
        }

        Vec3 pos = CommonUtil.getVanillaSwordOrBodyPosition(entity, Minecraft.getInstance().getFrameTime());

        new ParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "diamond_attractor"))
                .position(pos.x, pos.y, pos.z)
                .spawnInWorld(level, Minecraft.getInstance().player);
    }
}
