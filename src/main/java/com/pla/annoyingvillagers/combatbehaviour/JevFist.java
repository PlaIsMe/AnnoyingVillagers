package com.pla.annoyingvillagers.combatbehaviour;

import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class JevFist {
    public static final Builder<MobPatch<?>> FIST = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot());
}
