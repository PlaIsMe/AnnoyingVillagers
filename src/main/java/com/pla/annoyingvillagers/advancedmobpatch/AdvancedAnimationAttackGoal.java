/*
 * SPDX-License-Identifier: GPL-3.0-only
 * Goal behavior adapted from Combat Evolution by ShelMarow.
 */
package com.pla.annoyingvillagers.advancedmobpatch;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.function.BooleanSupplier;

public final class AdvancedAnimationAttackGoal<T extends MobPatch<?>> extends Goal {
    private final T mobPatch;
    private final AdvancedCombatBehaviors<T> combatBehaviors;
    private final BooleanSupplier actionAllowed;

    public AdvancedAnimationAttackGoal(
            T mobPatch,
            AdvancedCombatBehaviors<T> combatBehaviors,
            BooleanSupplier actionAllowed
    ) {
        this.mobPatch = mobPatch;
        this.combatBehaviors = combatBehaviors;
        this.actionAllowed = actionAllowed;
    }

    @Override
    public boolean canUse() {
        boolean finishingAction = this.combatBehaviors.getCurrentBehavior() != null
                && !this.mobPatch.getEntityState().inaction();
        return this.actionAllowed.getAsBoolean() && (this.hasValidTarget() || finishingAction);
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void tick() {
        if (!this.actionAllowed.getAsBoolean() || !this.hasValidTarget()) {
            this.combatBehaviors.clearCurrentBehavior();
            return;
        }
        this.combatBehaviors.tick(this.mobPatch);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private boolean hasValidTarget() {
        LivingEntity target = this.mobPatch.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        return !(target instanceof Player player) || (!player.isSpectator() && !player.isCreative());
    }
}
