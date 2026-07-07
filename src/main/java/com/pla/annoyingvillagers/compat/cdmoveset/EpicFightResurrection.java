package com.pla.annoyingvillagers.compat.cdmoveset;

import com.pla.annoyingvillagers.combatbehaviour.AvNpcDagger;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcSpear;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcSword;
import com.pla.annoyingvillagers.compat.refm.AvNpcRapier;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.corruptdog.cdm.world.item.CDAddonItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import yesman.epicfight.gameasset.Animations;
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
                CorruptAnimations.YAMATO_JUDGEMENT_CUT.get().getRegistryName().toString(),
                CorruptAnimations.YAMATO_JUDGEMENT_CUT_JUST.get().getRegistryName().toString(),
                CorruptAnimations.YAMATO_JUDGEMENT_CUT_END.get().getRegistryName().toString()
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
        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SPEAR) {
            if (entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.SPEAR_THRUST, 0.0F);
                return true;
            }
        }

        if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD
                || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_DAGGER
                || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.GREAT_TACHI)
                && (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD
                || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.GREAT_TACHI
                || playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_DAGGER)) {
            if (entity.level() instanceof ServerLevel) {
                if (!entity.getPersistentData().contains("DualSwordCombo")) {
                    livingEntityPatch.playAnimationSynchronized(Animations.DAGGER_DUAL_DASH, 0.0F);
                    entity.getPersistentData().putDouble("DualSwordCombo", 1.0);
                } else if (entity.getPersistentData().getDouble("DualSwordCombo") == 1.0) {
                    livingEntityPatch.playAnimationSynchronized(Animations.LONGSWORD_AUTO2, 0.0F);
                    entity.getPersistentData().putDouble("DualSwordCombo", 2.0);
                } else if (entity.getPersistentData().getDouble("DualSwordCombo") == 2.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.DUAL_DANCING_EDGE, 0.0F);
                    entity.getPersistentData().putDouble("DualSwordCombo", 3.0);
                } else if (entity.getPersistentData().getDouble("DualSwordCombo") == 3.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE, 0.0F);
                    entity.getPersistentData().remove("DualSwordCombo");
                }
                return true;
            }
        }

        if ((playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD
                || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_LONGSWORD
                || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_TACHI
                || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_DAGGER
                || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.GREAT_TACHI
                || playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.KATANA)
                && (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CorruptWeaponCategories.S_SWORD
                && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CorruptWeaponCategories.GREAT_TACHI
                && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CorruptWeaponCategories.S_DAGGER)) {
            if (entity.level() instanceof ServerLevel) {
                if (!entity.getPersistentData().contains("SwordCombo")) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.SWORD_HEAVY_AUTO_1, 0.0F);
                    entity.getPersistentData().putDouble("SwordCombo", 1.0);
                } else if (entity.getPersistentData().getDouble("SwordCombo") == 1.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.SWORD_HEAVY_AUTO_2, 0.0F);
                    entity.getPersistentData().putDouble("SwordCombo", 2.0);
                } else if (entity.getPersistentData().getDouble("SwordCombo") == 2.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.SWORD_HEAVY_AUTO_3, 0.0F);
                    entity.getPersistentData().remove("SwordCombo");
                }
                return true;
            }
        }

        if (playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.S_GREATSWORD) {
            if (entity.level() instanceof ServerLevel) {
                livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.GIANT_WHIRLWIND, 0.0F);
                return true;
            }
        }

        return false;
    }
}
