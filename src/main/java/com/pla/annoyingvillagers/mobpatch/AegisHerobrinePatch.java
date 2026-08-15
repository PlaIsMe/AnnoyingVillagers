package com.pla.annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.advancedmobpatch.AdvancedCombatBehaviors;
import com.pla.annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AnimsEnderAegis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.AttackResult.ResultType;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

import java.util.List;

public class AegisHerobrinePatch extends AdvancedMobPatch<PathfinderMob> {
    public AegisHerobrinePatch() {
        super(Factions.NEUTRAL);
    }

    @Override
    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.BLOCK, Animations.BIPED_BLOCK);
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
    }

    @Override
    protected void addCustomBehaviorRoots(AdvancedCombatBehaviors.Builder<MobPatch<?>> builder,
                                          CapabilityItem mainHandCap,
                                          CapabilityItem offHandCap, Style style) {
    }

    @Override
    protected List<AdditionalAttackGroup> getAdditionalAttackGroups(CapabilityItem mainHandCap, CapabilityItem offHandCap, Style style) {
        return List.of(
                AdditionalAttackGroup.random(
                        0.35F,
                        AnimsEnderAegis.ENDER_AEGIS_INNATE,
                        AnimsEnderAegis.ENDER_AEGIS_SPECIAL));
    }

    @Override
    protected boolean canPerformGeneratedAttack() {
        return CombatCommon.canPerformNormalAttackLogic(this);
    }

    @Override
    public boolean canGuard() {
        return CombatCommon.canPerformGuarding(this);
    }

    @Override
    public int getGuardChance() {
        return 15;
    }

    @Override
    public void playGuardBreakSound() {
        this.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 0.0F, 0.0F);
    }

    @Override
    public AttackResult attack(EpicFightDamageSource epicFightDamageSource,
                               Entity entity, InteractionHand interactionHand) {
        AttackResult attackResult = super.attack(epicFightDamageSource, entity, interactionHand);
        if (attackResult.resultType == ResultType.SUCCESS && entity.isAlive()) {
            // More logic when the mob's attack succeeds.
        }
        return attackResult;
    }

    @Override
    public void tick(LivingTickEvent livingTickEvent) {
        super.tick(livingTickEvent);
    }

    @Override
    public void onDeath(LivingDeathEvent livingDeathEvent) {
        super.onDeath(livingDeathEvent);
    }

    @Override
    public void playGuardHitAnimation(DamageSource damageSource, boolean canCounter) {
        if (this.getOriginal() instanceof AVNpc avNpc
                && avNpc.getLivingEntityPatch() != null) {
            EpicFightNightFall.playEfnGuardHit(
                    avNpc.getLivingEntityPatch(),
                    avNpc.getEfnGuardHitState(),
                    damageSource
            );
            avNpc.postPlayEfnGuardHit();
        }
    }

    @Override
    public void playGuardHitSound() {
    }

    @Override
    public boolean canBeExecuted(LivingEntityPatch<?> executorPatch) {
        return AnnoyingVillagersConfig.CAN_EXECUTE_AV_MOB.get();
    }
}
