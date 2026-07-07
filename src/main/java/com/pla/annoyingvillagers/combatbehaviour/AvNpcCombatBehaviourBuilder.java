package com.pla.annoyingvillagers.combatbehaviour;

import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public final class AvNpcCombatBehaviourBuilder {
    private AvNpcCombatBehaviourBuilder() {
    }

    @SafeVarargs
    public static Builder<MobPatch<?>> weapon(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        return AvNpcCombatBehaviorBuilder.weapon(opener, groups);
    }

    @SafeVarargs
    public static Builder<MobPatch<?>> fist(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        return AvNpcCombatBehaviorBuilder.fist(opener, groups);
    }
}
