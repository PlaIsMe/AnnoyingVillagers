/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: MIT
 *
 * Upstream: Epic Fight x Iron's Spells: Enhanced Animations - YukamiNeeSan
 * Source: https://github.com/domanhthang2110/efiscompat
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/MIT.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightIronSpell {
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_TOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_INWARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CASTING_ONE_HAND_BUFF;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        CASTING_ONE_HAND_TOP = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_top",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        CASTING_ONE_HAND_INWARD = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_inward",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        CASTING_ONE_HAND_BUFF = builder.nextAccessor("biped/epicfight_ironspell/casting_one_hand_buff",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
    }
}
