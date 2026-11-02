package com.pla.annoyinglib.util;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AAAParticlesUtil {
    public static void sendDragonBeam(Vec3 from, Vec3 to, Level level, Entity caster, LivingEntity target) {
        if (ModList.get().isLoaded("herobrine_vessels")) HerobrineVessels.sendDragonBeam(from, to, level, caster, target);
    }

    public static void sendDragonBeamHit(Level level, BlockPos hitBlock) {
        if (ModList.get().isLoaded("herobrine_vessels")) HerobrineVessels.Client.sendDragonBeamHit(level, hitBlock);
    }

    public static void sendBlueDemonThunderBeam(Level level, Entity blueDemonThunderBeamEntity) {
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.sendBlueDemonThunderBeam(level, blueDemonThunderBeamEntity);
    }

    public static void sendHerobrinePortal(Level level, double x, double y, double z) {
        if (ModList.get().isLoaded("herobrine_vessels")) HerobrineVessels.Client.sendHerobrinePortal(level, x, y, z);
    }

    public static void sendHerobrineAssistance(Level level, double x, double y, double z) {
        if (ModList.get().isLoaded("herobrine_vessels")) HerobrineVessels.Client.sendHerobrineAssistance(level, x, y, z);
    }

    public static void sendWoopieWind(Level level, double x, double y, double z) {
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.sendWoopieWind(level, x, y, z);
    }

    public static boolean sendTeleportPortal(Level level, Vec3 pos, Vec3 normal) {
        return ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.Client.sendTeleportPortal(level, pos, normal);
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
                ResourceLocation.fromNamespaceAndPath("annoyinglib", "diamond_attractor"))
                .position(pos.x, pos.y, pos.z)
                .spawnInWorld(level, Minecraft.getInstance().player);
    }

    public static void sendBlackFire(Level level, Entity entity) {
        if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.sendBlackFire(level, entity);
    }
}
