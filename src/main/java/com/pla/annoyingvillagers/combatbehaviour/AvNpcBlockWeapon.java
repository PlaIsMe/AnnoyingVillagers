package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcBlockWeapon {
    public static final Builder<MobPatch<?>> WOODEN_DOOR = AvNpcCombatBehaviorBuilder.guarded(
            CombatCommon.animations(
                    Animations.GREATSWORD_AUTO1,
                    Animations.GREATSWORD_AUTO2,
                    WOMAnimations.TORMENT_AUTO_2,
                    WOMAnimations.TORMENT_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.GREATSWORD_DASH,
                    WOMAnimations.TORMENT_CHARGED_ATTACK_2,
                    WOMAnimations.TORMENT_BERSERK_DASH,
                    WOMAnimations.TORMENT_AIRSLAM
            )
    );

    public static final Builder<MobPatch<?>> TRAPDOOR = AvNpcCombatBehaviorBuilder.guarded(
            CombatCommon.animations(
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO3,
                    Animations.SWORD_AUTO2,
                    AnimsHerrscher.HERRSCHER_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.LONGSWORD_AUTO2,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_DASH,
                    Animations.SWORD_AIR_SLASH,
                    AnimsPugilistSteve.GIANT_WHIRLWIND,
                    AnimsHerrscher.HERRSCHER_VERDAMMNIS
            )
    );

    public static final Builder<MobPatch<?>> LADDER = AvNpcCombatBehaviorBuilder.guarded(
            CombatCommon.animations(
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO3,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    Animations.TACHI_AUTO3
            ),
            CombatCommon.animations(
                    Animations.SWORD_DASH,
                    Animations.VINDICATOR_SWING_AXE3,
                    Animations.SWORD_AIR_SLASH,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            )
    );

    public static final Builder<MobPatch<?>> CRAFTING_TABLE = AvNpcCombatBehaviorBuilder.aggressive(
            CombatCommon.animations(
                    Animations.TACHI_AUTO2,
                    Animations.TACHI_AUTO3,
                    AnimsRuine.RUINE_AUTO_1,
                    AnimsRuine.RUINE_AUTO_2
            ),
            CombatCommon.animations(
                    WOMAnimations.TORMENT_AIRSLAM,
                    Animations.LONGSWORD_AIR_SLASH,
                    AnimsRuine.RUINE_CHATIMENT,
                    AnimsSolar.SOLAR_AUTO_2
            )
    );
}
