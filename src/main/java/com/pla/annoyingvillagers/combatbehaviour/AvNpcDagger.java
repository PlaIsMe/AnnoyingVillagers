package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightSanji;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcDagger {
    public static final Builder<MobPatch<?>> DAGGER = AvNpcCombatBehaviorBuilder.animationEscape(
            AnimsPugilistSteve.KNIFE_CHECK,
            CombatCommon.animations(
                    Animations.DAGGER_AUTO1,
                    Animations.DAGGER_AUTO2,
                    Animations.DAGGER_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DASH,
                    Animations.DAGGER_AIR_SLASH,
                    Animations.EVISCERATE_FIRST,
                    Animations.EVISCERATE_SECOND
            )
    );

    public static final Builder<MobPatch<?>> DUAL_DAGGER = AvNpcCombatBehaviorBuilder.animationEscape(
            AnimsPugilistSteve.KNIFE_CHECK,
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_AUTO1,
                    Animations.DAGGER_DUAL_AUTO2,
                    Animations.DAGGER_DUAL_AUTO3,
                    AnimsPugilistSteve.DAGGER_DUAL_AUTO4
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.DAGGER_AIR_SLASH,
                    Animations.LONGSWORD_AUTO2,
                    Animations.BLADE_RUSH_COMBO1,
                    Animations.BLADE_RUSH_COMBO2,
                    Animations.BLADE_RUSH_COMBO3,
                    Animations.DAGGER_DUAL_AUTO4
            )
    );

    public static final Builder<MobPatch<?>> ARM_BLADE = AvNpcCombatBehaviorBuilder.animationEscape(
            AnimsPugilistSteve.KNIFE_CHECK,
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO1,
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO2,
                    AnimsPugilistSteve.FIST_UP,
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO3,
                    Animations.REVELATION_TWOHAND
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.IRON_LOTUS_DASH_ATTACK,
                    Animations.REVELATION_ONEHAND,
                    AnimsEpicFightSanji.SANJI_CONCASSER
            )
    );

    public static final Builder<MobPatch<?>> KNIFE = AvNpcCombatBehaviorBuilder.animationEscape(
            AnimsPugilistSteve.KNIFE_CHECK,
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.THIEF_AUTO1,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsPugilistSteve.DAGGER_AUTO2,
                    AnimsPugilistSteve.DAGGER_AUTO3,
                    AnimsEpicFightBattleArts.THIEF_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.THIEF_DASH_ATTACK,
                    AnimsEpicFightBattleArts.THIEF_AIRSLASH,
                    AnimsEpicFightBattleArts.THIEF_STEAL
            )
    );

    public static final Builder<MobPatch<?>> DUAL_KNIFE = AvNpcCombatBehaviorBuilder.animationEscape(
            AnimsPugilistSteve.KNIFE_CHECK,
            CombatCommon.animations(
                    AnimsPugilistSteve.DAGGER_DUAL_AUTO1,
                    AnimsPugilistSteve.DAGGER_DUAL_AUTO2,
                    AnimsPugilistSteve.DAGGER_DUAL_AUTO3,
                    AnimsPugilistSteve.DAGGER_DUAL_AUTO4,
                    AnimsEpicFightBattleArts.DUAL_BLADES_AUTO3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.SWORD_DASH_ATTACK,
                    AnimsEpicFightBattleArts.DUAL_BLADES_AIRSLAM
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.DAGGER_AIR_SLASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsEpicFightBattleArts.DUAL_BLADES_WHIRLEDGE
            )
    );

    public static final Builder<MobPatch<?>> MOON_BLADE = AvNpcCombatBehaviorBuilder.animationEscape(
            AnimsPugilistSteve.KNIFE_CHECK,
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO1,
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO2,
                    AnimsPugilistSteve.FIST_UP,
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO3,
                    Animations.REVELATION_TWOHAND
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.IRON_LOTUS_DASH_ATTACK,
                    Animations.REVELATION_ONEHAND,
                    AnimsEpicFightSanji.SANJI_DIABLE
            )
    );

    public static final Builder<MobPatch<?>> CLAW = AvNpcCombatBehaviorBuilder.animationEscape(
            AnimsPugilistSteve.KNIFE_CHECK,
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO1,
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO2,
                    AnimsPugilistSteve.FIST_UP,
                    AnimsEpicFightBattleArts.IRON_LOTUS_AUTO3,
                    Animations.REVELATION_TWOHAND
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.IRON_LOTUS_DASH_ATTACK,
                    Animations.REVELATION_ONEHAND,
                    Animations.FIST_AIR_SLASH
            )
    );
}
