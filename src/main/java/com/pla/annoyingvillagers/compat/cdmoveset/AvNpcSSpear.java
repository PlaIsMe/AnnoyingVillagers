package com.pla.annoyingvillagers.compat.cdmoveset;

import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviourBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcSSpear {
    public static final Builder<MobPatch<?>> S_SPEAR_SHIELD = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.SSPEAR_ONEHAND_AUTO
            ),
            CombatCommon.animations(
                    CorruptAnimations.SSPEAR_DASH,
                    Animations.SPEAR_ONEHAND_AIR_SLASH
            ),
            spearSpecialAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.SPEAR_SLASH
            )
    );

    public static final Builder<MobPatch<?>> S_SPEAR = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.SSPEAR_TWOHAND_AUTO1,
                    CorruptAnimations.SSPEAR_TWOHAND_AUTO2
            ),
            spearSpecialAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.SSPEAR_DASH,
                    Animations.SPEAR_TWOHAND_AIR_SLASH
            ),
            CombatCommon.animations(
                    CorruptAnimations.SPEAR_SLASH
            )
    );

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] spearSpecialAnimations() {
        return CombatCommon.animations(
                AnimsPugilistSteve.SPEAR_THRUST
        );
    }
}
