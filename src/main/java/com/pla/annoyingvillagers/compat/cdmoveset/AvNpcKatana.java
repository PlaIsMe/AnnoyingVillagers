package com.pla.annoyingvillagers.compat.cdmoveset;

import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviourBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcKatana {
    public static final Builder<MobPatch<?>> KATANA = AvNpcCombatBehaviourBuilder.weapon(
            CombatCommon.animations(
                    CorruptAnimations.KATANA_AUTO1,
                    CorruptAnimations.KATANA_AUTO2,
                    CorruptAnimations.KATANA_AUTO3
            ),
            swordHeavyAnimations(),
            CombatCommon.animations(
                    CorruptAnimations.YAMATO_DASH,
                    CorruptAnimations.KATANA_SHEATHING_DASH,
                    CorruptAnimations.KATANA_SHEATH_AIR_SLASH
            ),
            CombatCommon.animations(
                    CorruptAnimations.BLADE_RUSH_FINISHER,
                    CorruptAnimations.FATAL_DRAW_DASH
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
