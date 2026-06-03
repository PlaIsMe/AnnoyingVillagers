package com.pla.annoyingvillagers.compat.dualaxes;

import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import M6FGR.dualaxes.gameassets.DualAxesSkills;
import com.asanginxst.epicfightx.gameassets.EpicFightSkillsX;
import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@SuppressWarnings({"deprecation", "removal"})
public class EpicFightXDualAxe {
    public static final Function<Item, CapabilityItem.Builder> X_AXE_DUAL =
            (item) -> (CapabilityItem.Builder) WeaponCapability.builder()
                    .category(CapabilityItem.WeaponCategories.AXE).styleProvider((entityPatch) -> {
                        if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                            return playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE
                                    ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND;
                        } else if (entityPatch instanceof LivingEntityPatch<?>) {
                            return entityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.AXE ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND;
                        }
                        return CapabilityItem.Styles.ONE_HAND;
                    })
                    .collider(ColliderPreset.TOOLS)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(CapabilityItem.Styles.ONE_HAND, AnimationsX.AXE_AUTO1, AnimationsX.AXE_AUTO2, ExtraAnimations.AXE_AUTO3, ExtraAnimations.AXE_AUTO4, ExtraAnimations.AXE_AUTO5, AnimationsX.AXE_DASH, AnimationsX.AXE_AIRSLASH)
                    .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> EpicFightSkillsX.GUILLOTINE_AXE)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, AnimationsX.SWORD_GUARD)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.JUMP, ExtraAnimations.BIPED_JUMP_ONEHAND_LIGHT)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, ExtraAnimations.BIPED_RUN_ONEHAND_LIGHT_ALT)
                    .newStyleCombo(CapabilityItem.Styles.TWO_HAND, DualAxesAnimations.AXE_DUAL_AUTO_1, DualAxesAnimations.AXE_DUAL_AUTO_2, DualAxesAnimations.AXE_DUAL_AUTO_3, DualAxesAnimations.AXE_DUAL_DASH, DualAxesAnimations.AXE_DUAL_AIRSLASH)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> DualAxesSkills.SPINNING_DEATH).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, DualAxesAnimations.AXE_DUAL_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_KNEEL)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .weaponCombinationPredicator((livingEntityPatch) -> true);
}
