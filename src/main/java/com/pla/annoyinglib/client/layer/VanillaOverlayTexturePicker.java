package com.pla.annoyinglib.client.layer;

import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.init.AnnoyingLibModMobEffects;
import com.pla.annoyinglib.potion.ObedienceMobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.jetbrains.annotations.Nullable;

final class VanillaOverlayTexturePicker {
    private static final ResourceLocation ZOMBIE_VILLAGER_EYES = ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/obedience/zombie_villager.png");
    private static final ResourceLocation ZOMBIE_EYES = ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/obedience/zombie.png");
    private static final ResourceLocation SKELETON_EYES = ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/obedience/skeleton.png");
    private static final ResourceLocation PIGLIN_EYES = ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/obedience/piglin.png");
    private static final ResourceLocation ILLAGER_EYES = ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/obedience/illager.png");

    private VanillaOverlayTexturePicker() {
    }

    @Nullable
    static ResourceLocation pickHumanoidTexture(LivingEntity entity) {
        if (ModList.get().isLoaded("herobrine_vessels")) {
            ResourceLocation texture = HerobrineVessels.Client.lowCloneEyes(entity);
            if (texture != null) return texture;
            texture = HerobrineVessels.Client.infectedPlayerBlood(entity);
            if (texture != null) return texture;
        }
        if (ModList.get().isLoaded("herobrine_vessels")) {
            ResourceLocation texture = HerobrineVessels.Client.herobrineEyes(entity);
            if (texture != null) return texture;
        }
        if (ObedienceMobEffect.canBeObedientMob(entity) && entity.hasEffect(AnnoyingLibModMobEffects.OBEDIENCE.get())) {
            if (entity instanceof ZombieVillager) {
                return ZOMBIE_VILLAGER_EYES;
            }
            if (entity instanceof Zombie) {
                return ZOMBIE_EYES;
            }
            if (entity instanceof AbstractSkeleton) {
                return SKELETON_EYES;
            }
            if (entity instanceof AbstractPiglin) {
                return PIGLIN_EYES;
            }
        }
        return null;
    }

    @Nullable
    static ResourceLocation pickIllagerTexture(AbstractIllager entity) {
        return ObedienceMobEffect.canBeObedientMob(entity) && entity.hasEffect(AnnoyingLibModMobEffects.OBEDIENCE.get())
                ? ILLAGER_EYES
                : null;
    }

    static boolean isBloodTexture(ResourceLocation texture) {
        return ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.Client.isInfectedPlayerBloodTexture(texture);
    }
}
