package com.pla.annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.minecraft.world.entity.PathfinderMob;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Factions;

public class InfectedPlayerNpcPatch extends CEHumanoidPatch<PathfinderMob> {
    public InfectedPlayerNpcPatch() {
        super(Factions.VILLAGER);
    }

    @Override
    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, AnimsPugilistSteve.LAYING_DEATH);
        animator.addLivingAnimation(LivingMotions.WALK, AnimsPugilistSteve.LAYING_DEATH);
        animator.addLivingAnimation(LivingMotions.CHASE, AnimsPugilistSteve.LAYING_DEATH);
        animator.addLivingAnimation(LivingMotions.DEATH, AnimsPugilistSteve.LAYING_DEATH_DEAD);
    }

    @Override
    protected void setWeaponMotions() {
    }
}
