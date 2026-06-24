package com.pla.annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.annoyingvillagers.compat.EfDancing;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Factions;

public class InfectedTheMostMoistBurrit0Patch extends CEHumanoidPatch {
    public InfectedTheMostMoistBurrit0Patch() {
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
