/*
 * AnnoyingVillagers - Third-Party Compatibility Notice
 *
 * SPDX-License-Identifier: LicenseRef-P1neroEpicBow-Proprietary
 *
 * Upstream: P1nero Epic Bow - P1nero
 * Source: https://github.com/P1neapplell0/Epic-Bow
 *
 * This file registers compatibility animations that reference cloned/copied
 * P1nero Epic Bow animation assets. The cloned assets are included so mobs can
 * play the registered animations when P1nero Epic Bow is installed.
 *
 * Asset path:
 *   - assets/annoyingvillagers/animmodels/animations/biped/p1nero_bow_clone
 *
 * Required upstream notices must be preserved. See THIRD_PARTY_NOTICES.md.
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.compat.p1nero_bow;

import com.p1nero.epicfightbow.gameassets.EFBowColliders;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.util.BowFunction;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsP1neroEpicBow {
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> P1NERO_MOB_BOW_AUTO1;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> P1NERO_MOB_BOW_AUTO2;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> P1NERO_MOB_BOW_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> P1NERO_MOB_BOW_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> P1NERO_MOB_BOW_JUMP_ATTACK;

    public static void build(AnimationManager.AnimationBuilder builder) {
        P1NERO_MOB_BOW_AUTO1 = builder.nextAccessor("biped/p1nero_bow_clone/bow_auto1",
                (accessor) -> (new BowAttackAnimation(0.15F, 0.0F, 0.15F, 1.0833334F, 1.0833334F, InteractionHand.MAIN_HAND, null, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.1F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        )
        );
        P1NERO_MOB_BOW_AUTO2 = builder.nextAccessor("biped/p1nero_bow_clone/bow_auto2",
                (accessor) -> (new BowAttackAnimation(0.15F, 0.0F, 0.15F, 1.0833334F, 1.0833334F, InteractionHand.MAIN_HAND, null, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.1F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        )
        );
        P1NERO_MOB_BOW_AUTO3 = builder.nextAccessor("biped/p1nero_bow_clone/bow_auto3",
                (accessor) -> (new BowAttackAnimation(0.15F, 0.0F, 0.15F, 1.6666666F, 2.0F, InteractionHand.MAIN_HAND, null, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.3333334F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.6666666F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.72F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        )
        );
        P1NERO_MOB_BOW_DASH_ATTACK = builder.nextAccessor("biped/p1nero_bow_clone/bow_dash_attack",
                (accessor) -> (new AttackAnimation(0.15F, 0.0F, 0.0F, 0.6666667F, 1.0F, EFBowColliders.BOW_DASH, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER))
        );
        P1NERO_MOB_BOW_JUMP_ATTACK = builder.nextAccessor("biped/p1nero_bow_clone/bow_jump_attack",
                (accessor) -> (new BowAttackAnimation(0.15F, 0.0F, 0.15F, 0.33333334F, 1.3333334F, InteractionHand.MAIN_HAND, null, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.25F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(0.33333334F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        )
        );
    }
}
