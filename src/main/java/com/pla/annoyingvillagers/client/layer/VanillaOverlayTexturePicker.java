package com.pla.annoyingvillagers.client.layer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.util.HerobrineEyesUtil;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.potion.ObedienceMobEffect;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import org.jetbrains.annotations.Nullable;

public final class VanillaOverlayTexturePicker {
    private static final Identifier DEFAULT_HEROBRINE_EYES = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_eyes/default/default.png");
    private static final Identifier INFECTED_PLAYER_NPC = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "infected_player_npc");
    private static final Identifier INFECTED_PLAYER_BLOOD = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/player_mob_blood.png");
    private static final Identifier ZOMBIE_VILLAGER_EYES = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/zombie_villager.png");
    private static final Identifier ZOMBIE_EYES = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/zombie.png");
    private static final Identifier SKELETON_EYES = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/skeleton.png");
    private static final Identifier PIGLIN_EYES = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/piglin.png");
    private static final Identifier ILLAGER_EYES = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/obedience/illager.png");

    private VanillaOverlayTexturePicker() {
    }

    @Nullable
    public static Identifier pickHumanoidTexture(LivingEntity entity) {
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
        if (ObedienceMobEffect.canBeObedientMob(entity) && entity.hasEffect(AnnoyingVillagersModMobEffects.OBEDIENCE)) {
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
    public static Identifier pickIllagerTexture(AbstractIllager entity) {
        return ObedienceMobEffect.canBeObedientMob(entity) && entity.hasEffect(AnnoyingVillagersModMobEffects.OBEDIENCE)
                ? ILLAGER_EYES
                : null;
    }

    public static boolean isBloodTexture(Identifier texture) {
        return INFECTED_PLAYER_BLOOD.equals(texture);
    }
}
