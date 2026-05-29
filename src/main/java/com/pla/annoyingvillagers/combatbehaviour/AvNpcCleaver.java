package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcCleaver {
    public static final Builder<MobPatch<?>> CLEAVER = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.SQUIRE_SWORD_AUTO_1,
                    AnimsEpicFightBattleArts.SQUIRE_SWORD_AUTO_2,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                    AnimsEpicFightBattleArts.SQUIRE_SWORD_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.SQUIRE_SWORD_DASH_ATTACK,
                    AnimsEpicFightBattleArts.SQUIRE_SWORD_HOP_ATTACK
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.GIANT_WHIRLWIND,
                    AnimsEpicFightBattleArts.SQUIRE_SWORD_HEAVY_BLOW
            )
    );
}
