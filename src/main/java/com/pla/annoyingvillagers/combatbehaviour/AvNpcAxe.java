package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsOrbit;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcAxe {
    public static final Builder<MobPatch<?>> AXE = AvNpcCombatBehaviorBuilder.guarded(
            CombatCommon.animations(
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    Animations.AXE_DASH,
                    Animations.AXE_AIRSLASH,
                    Animations.THE_GUILLOTINE
            )
    );

    public static final Builder<MobPatch<?>> HALBERD = AvNpcCombatBehaviorBuilder.guarded(
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.BAXE_AUTO_1,
                    AnimsEpicFightBattleArts.BAXE_AUTO_2,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.BAXE_DASH_ATTACK,
                    AnimsEpicFightBattleArts.BAXE_AIR_ATTACK,
                    AnimsEpicFightBattleArts.BAXE_SEISMIC_IMPACT
            )
    );

    public static final Builder<MobPatch<?>> DOUBLE_HALBERD = AvNpcCombatBehaviorBuilder.guarded(
            CombatCommon.animations(
                    WOMAnimations.STAFF_AUTO_1,
                    AnimsOrbit.ORBIT_ATTACK_1,
                    AnimsOrbit.ORBIT_ATTACK_3,
                    AnimsOrbit.ORBIT_ATTACK_4,
                    Animations.SPEAR_DASH
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    AnimsOrbit.ORBIT_SATELITE,
                    WOMAnimations.STAFF_KINKONG,
                    AnimsEpicFightBattleArts.BAXE_SEISMIC_IMPACT
            )
    );

    public static final Builder<MobPatch<?>> KILLER_AXE = AvNpcCombatBehaviorBuilder.guarded(
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.AXE_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO1,
                    AnimsEpicFightBattleArts.AXE_AUTO2,
                    AnimsEpicFightBattleArts.AXE_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.AXE_DASH,
                    AnimsEpicFightBattleArts.AXE_AIRSLASH,
                    AnimsEpicFightBattleArts.AXE_INNATE
            )
    );

    public static final Builder<MobPatch<?>> EARTH_AXE = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    Animations.AXE_DASH,
                    Animations.AXE_AIRSLASH,
                    AnimsWom.EARTH_AXE,
                    AVAnimations.EARTH_AXE_SHOOT
            )
    );

    public static final Builder<MobPatch<?>> RED_AXE = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    Animations.AXE_DASH,
                    Animations.AXE_AIRSLASH,
                    AVAnimations.RED_AXE_ATTACK
            )
    );

    public static final Builder<MobPatch<?>> EXTERMINATOR_BATTLE_AXE = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    Animations.AXE_DASH,
                    Animations.AXE_AIRSLASH,
                    Animations.THE_GUILLOTINE
            )
    );

    public static final Builder<MobPatch<?>> DUAL_EXTERMINATOR_BATTLE_AXE = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_1,
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_2,
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_3,
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO5
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsPugilistSteve.DUAL_SWORD_SKILL
            )
    );
}
