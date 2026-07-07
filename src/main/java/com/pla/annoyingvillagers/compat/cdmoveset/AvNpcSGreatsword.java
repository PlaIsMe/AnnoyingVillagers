package com.pla.annoyingvillagers.compat.cdmoveset;

import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviourBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcSGreatsword {
    public static final Builder<MobPatch<?>> S_GREATSWORD = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.GREATSWORD_OLD_AUTO1,
                    CorruptAnimations.GREATSWORD_OLD_AUTO2,
                    CorruptAnimations.GREATSWORD_OLD_AUTO3
            ),
            greatswordSpecialAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.GREATSWORD_OLD_DASH,
                    CorruptAnimations.GREATSWORD_OLD_AIRSLASH
            )
    );

    public static final Builder<MobPatch<?>> DUAL_S_GREATSWORD = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.DUAL_GREATSWORD_AUTO1,
                    CorruptAnimations.DUAL_GREATSWORD_AUTO2,
                    CorruptAnimations.DUAL_GREATSWORD_AUTO3,
                    CorruptAnimations.DUAL_GREATSWORD_AUTO4
            ),
            greatswordSpecialAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.GREATSWORD_OLD_DASH,
                    CorruptAnimations.GREATSWORD_OLD_AIRSLASH
            )
    );

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] greatswordSpecialAnimations() {
        return CombatCommon.animations(
                AnimsPugilistSteve.GIANT_WHIRLWIND
        );
    }
}
