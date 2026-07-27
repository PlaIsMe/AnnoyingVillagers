package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo.BlackFireParticleEmitterInfo;
import com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo.BlueDemonThunderBeamParticleEmitterInfo;
import com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo.DragonBeamParticleEmitterInfo;
import com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo.EnderGlaiveExplosionParticleEmitterInfo;
import com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo.TeleportPortalParticleEmitterInfo;
import com.pla.annoyingvillagers.entity.BlueDemonThunderBeamEntity;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

public class AAAParticlesUtil {
    public static void sendEnderGlaiveExplosion(Vec3 from, Vec3 to, Level level) {
        new EnderGlaiveExplosionParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_glaive_explosion"))
                .fromTo(from, to,
                        EnderGlaiveExplosionParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f, true)
                .spawnInWorld(level, null);
    }

    public static void sendDragonBeam(Vec3 from, Vec3 to, Level level, HerobrineDragonEntity caster, LivingEntity target) {
        new DragonBeamParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dragon_beam"))
                .fromTo(from, to, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .follow(caster, target, 120, DragonBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .spawnInWorld(level, null);
    }

    public static void sendDragonBeamHit(Level level, BlockPos hitBlock) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dragon_beam_hit"))
                        .clone()
                        .position(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ()));
    }

    public static void sendBlueDemonThunderBeam(Level level, BlueDemonThunderBeamEntity blueDemonThunderBeamEntity) {
        new BlueDemonThunderBeamParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blue_demon_thunder_beam"))
                .followBeam(blueDemonThunderBeamEntity, blueDemonThunderBeamEntity.getDuration(), BlueDemonThunderBeamParticleEmitterInfo.ForwardAxis.PLUS_Z, 0f)
                .spawnInWorld(level, null);
    }

    public static void sendHerobrinePortal(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "herobrine_portal"))
                        .clone()
                        .position(x, y, z));
    }

    public static void sendHerobrineAssistance(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "requesting_assistance"))
                        .clone()
                        .position(x, y, z));
    }

    public static void sendWoopieWind(Level level, double x, double y, double z) {
        AAALevel.addParticle(level, false,
                new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "woopie_sword_wind"))
                        .clone()
                        .position(x, y, z));
    }

    public static boolean sendTeleportPortal(Level level, Vec3 pos, Vec3 normal) {
        if (level == null || pos == null || !level.isClientSide()) {
            return false;
        }

        new TeleportPortalParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "teleport_portal"))
                .atPortal(pos, normal, TeleportPortalParticleEmitterInfo.ForwardAxis.PLUS_Z, 0.0F)
                .spawnInWorld(level, Minecraft.getInstance().player);
        return true;
    }

    public static void sendBlackFire(Level level, Entity entity) {
        if (level == null || entity == null) {
            return;
        }

        if (!level.isClientSide()) {
            return;
        }

        new BlackFireParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "black_fire"))
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

        Vec3 pos;

        try {
            pos = EpicfightUtil.getJointWithTranslation(
                    entity,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    Minecraft.getInstance().getFrameTime(),
                    0.0F
            );
        } catch (Exception ignored) {
            pos = null;
        }

        if (pos == null) {
            pos = entity.position().add(0.0D, entity.getBbHeight() * 0.6D, 0.0D);
        }

        new ParticleEmitterInfo(
                ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_attractor"))
                .position(pos.x, pos.y, pos.z)
                .spawnInWorld(level, Minecraft.getInstance().player);
    }
}
