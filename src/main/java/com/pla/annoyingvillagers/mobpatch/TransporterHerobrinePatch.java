package com.pla.annoyingvillagers.mobpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.pla.annoyingvillagers.combatbehaviour.TransporterHerobrineFist;
import com.pla.annoyingvillagers.entity.TransporterHerobrineCloneEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

import java.util.Set;

public class TransporterHerobrinePatch extends HerobrineClonePatch {
    private boolean hookedLivingAnimations;

    @Override
    public void tick(LivingTickEvent livingTickEvent) {
        super.tick(livingTickEvent);
        boolean hooked = this.isHookedEntity();
        if (this.hookedLivingAnimations != hooked) {
            this.hookedLivingAnimations = hooked;
            this.modifyLivingMotionByCurrentItem(false);
            this.updateMotion(true);
        }
    }

    @Override
    public void modifyLivingMotionByCurrentItem(boolean resendPacket) {
        super.modifyLivingMotionByCurrentItem(resendPacket);
        if (this.isHookedEntity()) {
            this.applyHookedLivingAnimations();
        }
    }

    private boolean isHookedEntity() {
        return this.getOriginal() instanceof TransporterHerobrineCloneEntity transporter && transporter.isHooked();
    }

    private void applyHookedLivingAnimations() {
        Animator animator = this.getAnimator();
        animator.addLivingAnimation(LivingMotions.IDLE, AnimsPugilistSteve.LAYING_DEATH);
        animator.addLivingAnimation(LivingMotions.WALK, AnimsPugilistSteve.LAYING_DEATH);
        animator.addLivingAnimation(LivingMotions.RUN, AnimsPugilistSteve.LAYING_DEATH);
        animator.addLivingAnimation(LivingMotions.CHASE, AnimsPugilistSteve.LAYING_DEATH);
        animator.addLivingAnimation(LivingMotions.DEATH, AnimsPugilistSteve.LAYING_DEATH_DEAD);
    }

    @Override
    protected void setWeaponMotions() {
        this.weaponLivingMotions
                .put(WeaponCategories.NOT_WEAPON,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsPugilistSteve.FIST_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AVAnimations.HEROBRINE_RUN),
                                        Pair.of(LivingMotions.CHASE, AVAnimations.HEROBRINE_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WeaponCategories.NOT_WEAPON,
                        ImmutableMap.of(Styles.ONE_HAND, TransporterHerobrineFist.TRANSPORTER_HEROBRINE_FIST));

        this.weaponLivingMotions
                .put(WeaponCategories.FIST,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsPugilistSteve.FIST_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AVAnimations.HEROBRINE_RUN),
                                        Pair.of(LivingMotions.CHASE, AVAnimations.HEROBRINE_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WeaponCategories.FIST,
                        ImmutableMap.of(Styles.ONE_HAND, TransporterHerobrineFist.TRANSPORTER_HEROBRINE_FIST));
    }
}
