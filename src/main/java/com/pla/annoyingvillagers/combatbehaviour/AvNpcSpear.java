package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightGuandao;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.gameasset.AnimsYonchiChikito;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsOrbit;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcSpear {
    public static final Builder<MobPatch<?>> SPEAR_SHIELD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    Animations.SPEAR_ONEHAND_AUTO
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SPEAR_THRUST
            ),
            CombatCommon.animations(
                    Animations.SPEAR_DASH,
                    Animations.SPEAR_ONEHAND_AIR_SLASH,
                    Animations.HEARTPIERCER
            )
    );

    public static final Builder<MobPatch<?>> SPEAR = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    Animations.SPEAR_TWOHAND_AUTO1,
                    Animations.SPEAR_TWOHAND_AUTO2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SPEAR_THRUST
            ),
            CombatCommon.animations(
                    Animations.SPEAR_DASH,
                    Animations.SPEAR_TWOHAND_AIR_SLASH,
                    Animations.GRASPING_SPIRAL_FIRST,
                    Animations.GRASPING_SPIRAL_SECOND
            )
    );

    public static final Builder<MobPatch<?>> GUANDAO = avSpearMoveset(
            CombatCommon.animations(
                    AnimsEpicFightGuandao.FALCHION_AUTO1,
                    AnimsEpicFightGuandao.FALCHION_AUTO2,
                    AnimsEpicFightGuandao.FALCHION_AUTO3,
                    AnimsOrbit.ORBIT_ATTACK_4,
                    AnimsOrbit.ORBIT_ATTACK_3
            ),
            CombatCommon.animations(
                    AnimsAgony.AGONY_CLAWSTRIKE,
                    AnimsOrbit.ORBIT_MAD_REACH,
                    AnimsPugilistSteve.SPEAR_THRUST
            )
    );

    public static final Builder<MobPatch<?>> SPEAR_STAFF = avSpearMoveset(
            CombatCommon.animations(
                    AnimsEpicFightGuandao.FALCHION_AUTO1,
                    AnimsEpicFightGuandao.FALCHION_AUTO2,
                    WOMAnimations.STAFF_AUTO_2,
                    WOMAnimations.STAFF_AUTO_3,
                    WOMAnimations.STAFF_CHARYBDIS
            ),
            CombatCommon.animations(
                    AnimsYonchiChikito.SAKURA_STAFF_DASH,
                    AnimsAgony.AGONY_RIPPING_FANGS,
                    AnimsPugilistSteve.SPEAR_THRUST
            )
    );

    public static final Builder<MobPatch<?>> SICKLE = avSpearMoveset(
            CombatCommon.animations(
                    AnimsEpicFightGuandao.FALCHION_AUTO2,
                    AnimsEpicFightGuandao.FALCHION_AUTO1,
                    AnimsWom.CLONE_ANTITHEUS_AUTO_2,
                    AnimsWom.CLONE_ANTITHEUS_AUTO_1,
                    AnimsWom.CLONE_ANTITHEUS_AUTO_4
            ),
            CombatCommon.animations(
                    AnimsOrbit.ORBIT_SATELITE,
                    AnimsWom.CLONE_ANTITHEUS_GUILLOTINE,
                    AnimsPugilistSteve.SPEAR_THRUST
            )
    );

    public static final Builder<MobPatch<?>> BOLT = avSpearMoveset(
            CombatCommon.animations(
                    AnimsEpicFightGuandao.FALCHION_AUTO1,
                    AnimsEpicFightGuandao.FALCHION_AUTO2,
                    AnimsEpicFightGuandao.FALCHION_AUTO3,
                    AnimsPugilistSteve.SPEAR_THRUST
            ),
            CombatCommon.animations(
                    AnimsAgony.AGONY_CLAWSTRIKE,
                    AnimsOrbit.ORBIT_MAD_REACH,
                    AnimsPugilistSteve.SPEAR_THRUST
            )
    );

    public static final Builder<MobPatch<?>> BLACK_SCRATCHER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AVAnimations.BLACKSCRATCHER_ATTACK,
                    AVAnimations.BLACKSCRATCHER_ATTACK,
                    AVAnimations.BLACKSCRATCHER_ATTACK
            ),
            CombatCommon.animations(
                    AVAnimations.BLACKSCRATCHER_ATTACK
            ),
            CombatCommon.animations(
                    AVAnimations.BLACKSCRATCHER_ATTACK
            )
    );

    public static final Builder<MobPatch<?>> STAFF = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    WOMAnimations.STAFF_AUTO_1,
                    WOMAnimations.STAFF_AUTO_2,
                    WOMAnimations.STAFF_AUTO_3
            ),
            CombatCommon.animations(
                    WOMAnimations.STAFF_SQUALL,
                    WOMAnimations.STAFF_KINKONG
            ),
            CombatCommon.animations(
                    WOMAnimations.STAFF_CHARYBDIS,
                    AnimsPugilistSteve.SPEAR_THRUST
            )
    );

    private static Builder<MobPatch<?>> avSpearMoveset(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] utility
    ) {
        return AvNpcCombatBehaviorBuilder.weapon(opener,
                utility,
                CombatCommon.animations(
                        AnimsEpicFightGuandao.FALCHION_FORWARD,
                        AnimsEpicFightGuandao.FALCHION_BACKWARD,
                        AnimsEpicFightGuandao.FALCHION_SIDE
                )
        );
    }

    private static Builder<MobPatch<?>> avSpearStaffMoveset(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] utility
    ) {
        return AvNpcCombatBehaviorBuilder.weapon(opener,
                utility,
                CombatCommon.animations(
                        AnimsEpicFightGuandao.FALCHION_FORWARD,
                        AnimsEpicFightGuandao.FALCHION_BACKWARD,
                        AnimsEpicFightGuandao.FALCHION_SIDE
                )
        );
    }
}
