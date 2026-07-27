package com.pla.annoyingvillagers.compat.dualgreatsword;

import M6FGR.dualgreatswords.gameassets.DualGreatSwordsAnimations;
import M6FGR.dualgreatswords.gameassets.DualGreatSwordsSkills;
import com.asanginxst.epicfightx.gameassets.EpicFightSkillsX;
import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@SuppressWarnings({"deprecation", "removal"})
public class EpicFightXDualGreatsword {
    public static final Function<Item, CapabilityItem.Builder> X_GREATSWORD_DUAL =
            (item) -> (CapabilityItem.Builder) WeaponCapability.builder()
            .category(CapabilityItem.WeaponCategories.GREATSWORD)
            .styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.GREATSWORD ? CapabilityItem.Styles.OCHS : CapabilityItem.Styles.TWO_HAND).collider(ColliderPreset.GREATSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .swingSound(EpicFightSounds.WHOOSH_BIG.get())
            .newStyleCombo(CapabilityItem.Styles.TWO_HAND, AnimationsX.GREATSWORD_AUTO1, AnimationsX.GREATSWORD_AUTO2, ExtraAnimations.GREATSWORD_AUTO3, ExtraAnimations.GREATSWORD_AUTO4, ExtraAnimations.GREATSWORD_AUTO5, AnimationsX.GREATSWORD_DASH, AnimationsX.GREATSWORD_AIR_SLASH)
            .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> EpicFightSkillsX.STEEL_WHIRLWIND).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, ExtraAnimations.BIPED_HOLD_TWOHAND_HEAVY).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, ExtraAnimations.BIPED_WALK_TWOHAND_HEAVY).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, ExtraAnimations.BIPED_RUN_TWOHAND_HEAVY).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.JUMP, ExtraAnimations.BIPED_JUMP_TWOHAND_HEAVY).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, AnimationsX.BIPED_WALK_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, AnimationsX.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, AnimationsX.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, AnimationsX.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLY, AnimationsX.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CREATIVE_FLY, AnimationsX.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CREATIVE_IDLE, AnimationsX.BIPED_HOLD_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, AnimationsX.GREATSWORD_GUARD)
            .newStyleCombo(CapabilityItem.Styles.OCHS, DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_1, DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_2, DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_3, DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_4, DualGreatSwordsAnimations.GREATSWORD_DUAL_DASH, DualGreatSwordsAnimations.GREATSWORD_DUAL_AIRSLASH)
            .innateSkill(CapabilityItem.Styles.OCHS, (itemstack) -> DualGreatSwordsSkills.EARTHQUAKE).livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.IDLE, DualGreatSwordsAnimations.GREATSWORD_DUAL_IDLE)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.WALK, DualGreatSwordsAnimations.GREATSWORD_DUAL_WALK)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.CHASE, DualGreatSwordsAnimations.GREATSWORD_DUAL_IDLE)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.RUN, DualGreatSwordsAnimations.GREATSWORD_DUAL_RUN)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.JUMP, DualGreatSwordsAnimations.GREATSWORD_DUAL_IDLE)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.KNEEL, DualGreatSwordsAnimations.GREATSWORD_DUAL_IDLE)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SNEAK, DualGreatSwordsAnimations.GREATSWORD_DUAL_IDLE)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SWIM, DualGreatSwordsAnimations.GREATSWORD_DUAL_IDLE)
            .livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.GREATSWORD);

}
