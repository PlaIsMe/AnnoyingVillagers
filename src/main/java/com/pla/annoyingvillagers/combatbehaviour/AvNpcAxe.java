package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsOrbit;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Arrays;

public class AvNpcAxe {
    public static final Builder<MobPatch<?>> AXE = guardedAxe(
            CombatCommon.animations(
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2
            ),
            axeFinisher(
                    Animations.THE_GUILLOTINE
            )
    );

    public static final Builder<MobPatch<?>> HALBERD = guardedAxe(
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.BAXE_AUTO_1,
                    AnimsEpicFightBattleArts.BAXE_AUTO_2,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.BAXE_DASH_ATTACK,
                    AnimsEpicFightBattleArts.BAXE_AIR_ATTACK,
                    AnimsEpicFightBattleArts.BAXE_SEISMIC_IMPACT
            )
    );

    public static final Builder<MobPatch<?>> DOUBLE_HALBERD = guardedAxe(
            CombatCommon.animations(
                    WOMAnimations.STAFF_AUTO_1,
                    AnimsOrbit.ORBIT_ATTACK_1,
                    AnimsOrbit.ORBIT_ATTACK_3,
                    AnimsOrbit.ORBIT_ATTACK_4,
                    Animations.SPEAR_DASH
            ),
            CombatCommon.animations(
                    AnimsOrbit.ORBIT_SATELITE,
                    WOMAnimations.STAFF_KINKONG,
                    AnimsEpicFightBattleArts.BAXE_SEISMIC_IMPACT
            )
    );

    public static final Builder<MobPatch<?>> KILLER_AXE = guardedAxe(
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.AXE_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO1,
                    AnimsEpicFightBattleArts.AXE_AUTO2,
                    AnimsEpicFightBattleArts.AXE_AUTO3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.AXE_DASH,
                    AnimsEpicFightBattleArts.AXE_AIRSLASH,
                    AnimsEpicFightBattleArts.AXE_INNATE
            )
    );

    public static final Builder<MobPatch<?>> EARTH_AXE = aggressiveAxe(
            standardAxeOpener(),
            axeFinisher(
                    AVAnimations.EARTH_AXE,
                    AVAnimations.EARTH_AXE_SHOOT
            )
    );

    public static final Builder<MobPatch<?>> RED_AXE = aggressiveAxe(
            standardAxeOpener(),
            axeFinisher(
                    AVAnimations.RED_AXE_ATTACK
            )
    );

    public static final Builder<MobPatch<?>> EXTERMINATOR_BATTLE_AXE = aggressiveAxe(
            standardAxeOpener(),
            axeFinisher(
                    Animations.THE_GUILLOTINE
            )
    );

    public static final Builder<MobPatch<?>> DUAL_EXTERMINATOR_BATTLE_AXE = AvNpcCombatBehaviorBuilder.weapon(
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

    private static Builder<MobPatch<?>> guardedAxe(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] finisher
    ) {
        return AvNpcCombatBehaviorBuilder.weapon(opener, axeHeavyAnimations(), finisher);
    }

    private static Builder<MobPatch<?>> aggressiveAxe(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] finisher
    ) {
        return AvNpcCombatBehaviorBuilder.weapon(opener, axeHeavyAnimations(), finisher);
    }

    private static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] standardAxeOpener() {
        return CombatCommon.animations(
                Animations.AXE_AUTO1,
                Animations.AXE_AUTO2,
                Animations.SWORD_AUTO1,
                Animations.SWORD_AUTO2,
                Animations.SWORD_AUTO3
        );
    }

    private static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] axeHeavyAnimations() {
        return CombatCommon.animations(
                AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                AnimsPugilistSteve.AXE_FUN_SKILL
        );
    }

    @SafeVarargs
    private static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] axeFinisher(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>... innate
    ) {
        return animationsWith(
                CombatCommon.animations(
                        Animations.AXE_DASH,
                        Animations.AXE_AIRSLASH
                ),
                innate
        );
    }

    @SafeVarargs
    private static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] animationsWith(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] base,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>... extra
    ) {
        AnimationManager.AnimationAccessor<? extends StaticAnimation>[] result = Arrays.copyOf(base, base.length + extra.length);
        System.arraycopy(extra, 0, result, base.length, extra.length);
        return result;
    }
}
