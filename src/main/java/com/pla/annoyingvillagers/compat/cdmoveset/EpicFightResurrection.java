package com.pla.annoyingvillagers.compat.cdmoveset;

import com.hm.efn.gameasset.animations.EFNSwordAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcDagger;
import com.pla.annoyingvillagers.gameasset.AnimsAVGreatsword;
import com.pla.annoyingvillagers.gameasset.AnimsAVSpear;
import com.pla.annoyingvillagers.gameasset.AnimsAVTachi;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.corruptdog.cdm.world.item.CDAddonItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.ef_awaken.efassets.animations.StraightSwordAnimations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.HashSet;
import java.util.Set;

public class EpicFightResurrection {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                "cdmoveset:biped/new/yamato/skill/yamato_judgement_cut",
                "cdmoveset:biped/new/yamato/skill/yamato_judgement_cut_just",
                "cdmoveset:biped/new/yamato/skill/yamato_judgement_cut_end"
        ));
    }

    public static Set<String> getDangerousAnimations() {
        return DANGEROUS_ANIMATIONS;
    }

    public static CECombatBehaviors.Builder<MobPatch<?>> overideCdMovesetWeapon(CapabilityItem mainHandCap, CapabilityItem offHandCap, Style style) {
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_WOODEN_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_STONE_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_IRON_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_GOLDEN_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_DIAMOND_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_NETHERITE_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSSword.S_SWORD;
            } else {
                return AvNpcSSword.S_DUALSWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_WOODEN_TACHI.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_STONE_TACHI.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_IRON_TACHI.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_GOLDEN_TACHI.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_DIAMOND_TACHI.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_NETHERITE_TACHI.get().getDefaultInstance())) {
            return AvNpcSTachi.S_TACHI;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.GREAT_TACHI.get().getDefaultInstance())) {
            return AvNpcSTachi.GREAT_TACHI;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_WOODEN_SPEAR.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_STONE_SPEAR.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_IRON_SPEAR.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_GOLDEN_SPEAR.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_DIAMOND_SPEAR.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_NETHERITE_SPEAR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSSpear.S_SPEAR;
            } else {
                return AvNpcSSpear.S_SPEAR_SHIELD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_WOODEN_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_STONE_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_IRON_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_GOLDEN_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_DIAMOND_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_NETHERITE_LONGSWORD.get().getDefaultInstance())) {
            return AvNpcSLongsword.S_LONGSWORD;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_WOODEN_GREATSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_STONE_GREATSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_IRON_GREATSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_GOLDEN_GREATSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_DIAMOND_GREATSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_NETHERITE_GREATSWORD.get().getDefaultInstance())) {

            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSGreatsword.DUAL_S_GREATSWORD;
            } else {
                return AvNpcSGreatsword.S_GREATSWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_IRON_DAGGER.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_DIAMOND_DAGGER.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.S_NETHERITE_DAGGER.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcDagger.DAGGER;
            } else {
                return AvNpcDagger.DUAL_DAGGER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(CDAddonItems.KATANA.get().getDefaultInstance())) {
            return AvNpcKatana.KATANA;
        }

        return null;
    }

    public static boolean addMoreSpecialAttack(PlayerPatch<?> playerpatch, Entity entity, LivingEntityPatch<?> livingEntityPatch) {
        CapabilityItem mainHandCapability = playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        if (mainHandCapability.getWeaponCategory() == CorruptWeaponCategories.S_SWORD) {
            if (mainHandCapability.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND && entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(EFNSwordAnimations.NF_SWORD_SKILL, 0.0F);
                return true;
            }

            if (mainHandCapability.getStyle(playerpatch) == CapabilityItem.Styles.TWO_HAND && entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(StraightSwordAnimations.STRAIGHTSWORD_DUAL_DODGE_SLASH, 0.0F);
                return true;
            }
        }

        if (mainHandCapability.getWeaponCategory() == CorruptWeaponCategories.S_TACHI) {
            if (entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(AnimsAVTachi.AV_TACHI_SPECIAL, 0.0F);
                return true;
            }
        }

        if (mainHandCapability.getWeaponCategory() == CorruptWeaponCategories.S_DAGGER) {
            if (entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(EFNSwordAnimations.NF_SWORD_SKILL_SECOND, 0.0F);
                return true;
            }
        }

        if (mainHandCapability.getWeaponCategory() == CorruptWeaponCategories.S_LONGSWORD) {
            if (mainHandCapability.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND && entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(StraightSwordAnimations.STRAIGHTSWORD_DODGE_SLASH1, 0.0F);
                return true;
            }

            if (mainHandCapability.getStyle(playerpatch) == CapabilityItem.Styles.TWO_HAND && entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(StraightSwordAnimations.STRAIGHTSWORD_DUAL_DODGE_PURSUIT, 0.0F);
                return true;
            }
        }

        if (mainHandCapability.getWeaponCategory() == CorruptWeaponCategories.S_GREATSWORD) {
            if (entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(AnimsAVGreatsword.AV_GREATSWORD_SPECIAL, 0.0F);
                return true;
            }
        }

        if (mainHandCapability.getWeaponCategory() == CorruptWeaponCategories.S_SPEAR
                || mainHandCapability.getWeaponCategory() == CapabilityItem.WeaponCategories.TRIDENT) {
            if (entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(AnimsAVSpear.AV_SPEAR_SPECIAL, 0.0F);
            }
        }

        return false;
    }
}
