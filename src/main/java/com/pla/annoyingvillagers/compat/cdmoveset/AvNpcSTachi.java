package com.pla.annoyingvillagers.compat.cdmoveset;

import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviourBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcSTachi {
    public static final Builder<MobPatch<?>> S_TACHI = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.LONGSWORD_OLD_AUTO1,
                    CorruptAnimations.LONGSWORD_OLD_AUTO2,
                    CorruptAnimations.LONGSWORD_OLD_AUTO3,
                    CorruptAnimations.LONGSWORD_OLD_AUTO4
            ),
            swordHeavyAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.LONGSWORD_OLD_DASH,
                    CorruptAnimations.LONGSWORD_OLD_AIRSLASH,
                    CorruptAnimations.LETHAL_SLICING_START,
                    CorruptAnimations.LETHAL_SLICING_ONCE,
                    CorruptAnimations.LETHAL_SLICING_TWICE,
                    CorruptAnimations.TACHI_SLASH
            )
    );

    public static final Builder<MobPatch<?>> GREAT_TACHI = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.GREAT_TACHI_AUTO1,
                    CorruptAnimations.GREAT_TACHI_AUTO2,
                    CorruptAnimations.GREAT_TACHI_AUTO3,
                    CorruptAnimations.GREAT_TACHI_AUTO4
            ),
            swordHeavyAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.UCHIGATANA_DASH,
                    CorruptAnimations.LONGSWORD_OLD_AIRSLASH,
                    CorruptAnimations.UCHIGATANA_HEAVY1,
                    CorruptAnimations.UCHIGATANA_HEAVY2
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
