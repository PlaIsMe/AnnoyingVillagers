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

public class AvNpcSLongsword {
    public static final Builder<MobPatch<?>> S_LONGSWORD = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.TACHI_TWOHAND_AUTO_1,
                    CorruptAnimations.TACHI_TWOHAND_AUTO_2,
                    CorruptAnimations.TACHI_TWOHAND_AUTO_3,
                    CorruptAnimations.TACHI_TWOHAND_AUTO_4
            ),
            swordHeavyAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.LONGSWORD_OLD_DASH,
                    CorruptAnimations.LONGSWORD_OLD_AIRSLASH,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO3
            )
    );

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] swordHeavyAnimations() {
        return CombatCommon.animations(
                AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
        );
    }
}
