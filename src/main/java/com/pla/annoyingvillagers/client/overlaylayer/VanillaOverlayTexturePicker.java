package com.pla.annoyingvillagers.client.overlaylayer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.HerobrineEyesUtil;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.potion.ObedienceMobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.jetbrains.annotations.Nullable;

final class VanillaOverlayTexturePicker {
    private static final ResourceLocation DEFAULT_HEROBRINE_EYES = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/default/default.png");
    private static final ResourceLocation INFECTED_PLAYER_NPC = new ResourceLocation(AnnoyingVillagers.MODID, "infected_player_npc");
    private static final ResourceLocation INFECTED_PLAYER_BLOOD = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/player_mob_blood.png");
    private static final ResourceLocation ZOMBIE_VILLAGER_EYES = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/obedience/zombie_villager.png");
    private static final ResourceLocation ZOMBIE_EYES = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/obedience/zombie.png");
    private static final ResourceLocation SKELETON_EYES = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/obedience/skeleton.png");
    private static final ResourceLocation PIGLIN_EYES = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/obedience/piglin.png");
    private static final ResourceLocation ILLAGER_EYES = new ResourceLocation(AnnoyingVillagers.MODID, "textures/entities/obedience/illager.png");

    private VanillaOverlayTexturePicker() {
    }

    @Nullable
    static ResourceLocation pickHumanoidTexture(LivingEntity entity) {
        if (entity instanceof LowHerobrineCloneEntity) {
            String name = entity.hasCustomName() ? entity.getCustomName().getString() : entity.getName().getString();
            return HerobrineEyesUtil.getHerobrineEyesTexture(name);
        }
        if (INFECTED_PLAYER_NPC.equals(EntityType.getKey(entity.getType()))) {
            return INFECTED_PLAYER_BLOOD;
        }
        if (entity instanceof HerobrineMob
                || entity instanceof LowShadowHerobrineCloneEntity
                || entity instanceof HerobrineGregEntity herobrineGreg && herobrineGreg.isWhiteEye()) {
            return DEFAULT_HEROBRINE_EYES;
        }
        if (ObedienceMobEffect.canBeObedientMob(entity) && entity.hasEffect(AnnoyingVillagersModMobEffects.OBEDIENCE.get())) {
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
        return ObedienceMobEffect.canBeObedientMob(entity) && entity.hasEffect(AnnoyingVillagersModMobEffects.OBEDIENCE.get())
                ? ILLAGER_EYES
                : null;
    }

    static boolean isBloodTexture(ResourceLocation texture) {
        return INFECTED_PLAYER_BLOOD.equals(texture);
    }
}
