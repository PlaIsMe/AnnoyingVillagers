package com.pla.annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.PathfinderMob;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.entitypatch.Factions;

public class EliteHerobrineKnockedPatch extends CEHumanoidPatch<PathfinderMob> {
    public EliteHerobrineKnockedPatch() {
        super(Factions.UNDEAD);
    }

    @Override
    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, AVAnimations.KNOCKED_ELITE);
        animator.addLivingAnimation(LivingMotions.WALK, AVAnimations.KNOCKED_ELITE);
        animator.addLivingAnimation(LivingMotions.CHASE, AVAnimations.KNOCKED_ELITE);
        animator.addLivingAnimation(LivingMotions.DEATH, AVAnimations.KNOCKED_ELITE);
    }

    @Override
    protected void setWeaponMotions() {
    }
}
