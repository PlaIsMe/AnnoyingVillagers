package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.*;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcSword {
    public static final Builder<MobPatch<?>> SWORD = AvNpcCombatBehaviorBuilder.guarded(
            true,
            100,
            false,
            0.0D,
            CombatCommon.animations(
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.SWORD_DASH,
                    Animations.SWORD_AIR_SLASH,
                    Animations.SWEEPING_EDGE
            )
    );

    public static final Builder<MobPatch<?>> DUAL_SWORD = AvNpcCombatBehaviorBuilder.guarded(
            true,
            100,
            false,
            0.0D,
            CombatCommon.animations(
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    Animations.SWORD_DUAL_DASH,
                    Animations.SWORD_DUAL_AIR_SLASH,
                    Animations.DANCING_EDGE
            )
    );

    public static final Builder<MobPatch<?>> AV_SWORD = AvNpcCombatBehaviorBuilder.guarded(
            true,
            40,
            false,
            0.0D,
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AnimsHerrscher.HERRSCHER_AUTO_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DUSK_REAVER_2
            )
    );

    public static final Builder<MobPatch<?>> AV_DUAL_SWORD = AvNpcCombatBehaviorBuilder.guarded(
            true,
            100,
            false,
            0.0D,
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsEpicFightAwaken.HOOK_SLASH_GROUND
            )
    );

    public static final Builder<MobPatch<?>> BLACK_FIRE_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AnimsHerrscher.HERRSCHER_AUTO_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AVAnimations.BLACK_FIRE_SWORD_SKILL
            )
    );

    public static final Builder<MobPatch<?>> DUAL_BLACK_FIRE_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AVAnimations.BLACK_FIRE_SWORD_SKILL
            )
    );

    public static final Builder<MobPatch<?>> BLUE_FLAME_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AnimsHerrscher.HERRSCHER_AUTO_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsHerrscher.HERRSCHER_AUSROTTUNG
            )
    );

    public static final Builder<MobPatch<?>> DUAL_BLUE_FLAME_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsHerrscher.HERRSCHER_AUSROTTUNG
            )
    );

    public static final Builder<MobPatch<?>> CLOW_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AnimsHerrscher.HERRSCHER_AUTO_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsHerrscher.HERRSCHER_BEFREIUNG
            )
    );

    public static final Builder<MobPatch<?>> DUAL_CLOW_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsHerrscher.HERRSCHER_BEFREIUNG
            )
    );

    public static final Builder<MobPatch<?>> DIAMOND_ATTRACTOR_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AnimsHerrscher.HERRSCHER_AUTO_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsYonchiChikito.DIAMOND_ATTRACTOR_SKILL
            )
    );

    public static final Builder<MobPatch<?>> DUAL_DIAMOND_ATTRACTOR_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsYonchiChikito.DIAMOND_ATTRACTOR_SKILL
            )
    );

    public static final Builder<MobPatch<?>> DIAMOND_BLASTER_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AnimsHerrscher.HERRSCHER_AUTO_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AVAnimations.DIAMOND_BLASTER_SKILL
            )
    );

    public static final Builder<MobPatch<?>> DUAL_DIAMOND_BLASTER_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AVAnimations.DIAMOND_BLASTER_SKILL
            )
    );

    public static final Builder<MobPatch<?>> HACKER_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AnimsHerrscher.HERRSCHER_AUTO_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsWom.HACKER_SWORD_SKILL
            )
    );

    public static final Builder<MobPatch<?>> DUAL_HACKER_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsWom.HACKER_SWORD_SKILL
            )
    );

    public static final Builder<MobPatch<?>> HOOK_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsEpicFight.HOOK_AXE_AUTO1,
                    AnimsEpicFight.HOOK_AXE_AUTO2
            )
    );

    public static final Builder<MobPatch<?>> DUAL_HOOK_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsEpicFight.HOOK_DANCING_EDGE
            )
    );

    public static final Builder<MobPatch<?>> FLANKER_HOOK_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsWom.HOOK_HERRSCHER_UP
            )
    );

    public static final Builder<MobPatch<?>> DNAX_HOOK_SWORD = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                    AnimsPugilistSteve.SWORD_DASH,
                    AnimsPugilistSteve.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsEpicFight.DNAX_HOOK_SWEEPING_EDGE
            )
    );

    public static final Builder<MobPatch<?>> DUAL_DNAX_HOOK_SWORD = AvNpcCombatBehaviorBuilder.dual(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsEpicFight.DNAX_HOOK_DANCING_EDGE
            )
    );
}
