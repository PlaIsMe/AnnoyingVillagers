package com.pla.annoyingvillagers.mobpatch;

import com.hm.efn.gameasset.animations.EFNSwordAnimations;
import com.pla.annoyingvillagers.advancedmobpatch.AdvancedCombatBehaviors;
import com.pla.annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AnimsAVSword;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.shelmarow.ef_awaken.efassets.animations.StraightSwordAnimations;
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
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

import java.util.List;

public class AlexPatch extends AdvancedMobPatch<PathfinderMob> {
    public AlexPatch() {
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
//        builder
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(4.0D)
//                                .weight(1000.0D)
//                                .maxCooldown(0)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::canExecute)
//                                                .withinDistance(0.0D, 5.0D)
//                                                .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
//                                                .addExBehavior(CombatCommon::performExecute)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(2.0D)
//                                .weight(70.0D)
//                                .maxCooldown(0)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::usesStepMoveset)
//                                                .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
//                                                .custom(CombatCommon::canPerformEating)
//                                                .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::performEatingAnimation)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(2.0D)
//                                .weight(70.0D)
//                                .maxCooldown(0)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::usesRollMoveset)
//                                                .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
//                                                .custom(CombatCommon::canPerformEating)
//                                                .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::performEatingAnimation)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(2.0D)
//                                .weight(100.0D)
//                                .maxCooldown(120)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::usesStepMoveset)
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::canSwapToBow)
//                                                .withinDistance(7.0D, 14.0D)
//                                                .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::swapToBow)
//                                )
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::usesStepMoveset)
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::canSwapToBow)
//                                                .withinDistance(7.0D, 14.0D)
//                                                .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::swapToBow)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(2.0D)
//                                .weight(100.0D)
//                                .maxCooldown(120)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::usesRollMoveset)
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::canSwapToBow)
//                                                .withinDistance(7.0D, 14.0D)
//                                                .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::swapToBow)
//                                )
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::usesRollMoveset)
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::canSwapToBow)
//                                                .withinDistance(7.0D, 14.0D)
//                                                .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::swapToBow)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(2.0D)
//                                .weight(80.0D)
//                                .maxCooldown(120)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::canThrowEnderPearl)
//                                                .withinDistance(7.0D, 48.0D)
//                                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::performEnderPearlToTarget)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(2.0D)
//                                .weight(30.0D)
//                                .maxCooldown(120)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::isGeneral)
//                                                .custom(CombatCommon::canUseVillagerKnightLavaBucket)
//                                                .withinDistance(0.0D, 5.0D)
//                                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::performVillagerKnightLavaBucket)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(3.0D)
//                                .weight(1000.0D)
//                                .maxCooldown(35)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::canEscape)
//                                                .custom(CombatCommon::canUseNpcCombatFishingRodEscape)
//                                                .withinDistance(0.0D, 32.0D)
//                                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::performNpcCombatFishingRodEscape)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(1.0D)
//                                .weight(10.0D)
//                                .maxCooldown(60)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .withinDistance(0.0D, 3.0D)
//                                                .custom(CombatCommon::canThrowEnderPearl)
//                                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::performEnderPearlAway)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(1.0D)
//                                .weight(40.0D)
//                                .maxCooldown(160)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::canJump)
//                                                .withinDistance(5.0D, 14.0D)
//                                                .animationBehavior(Animations.BIPED_JUMP, 0.0F)
//                                                .addExBehavior(CombatCommon::jump)
//                                )
//                )
//                .newBehaviorRoot(
//                        BehaviorRoot.builder()
//                                .priority(2.0D)
//                                .weight(55.0D)
//                                .maxCooldown(35)
//                                .addFirstBehavior(
//                                        Behavior.builder()
//                                                .custom(CombatCommon::canPerformNormalAttackLogic)
//                                                .custom(CombatCommon::canUseNpcCombatFishingRod)
//                                                .withinDistance(0.0D, 32.0D)
//                                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
//                                                .addExBehavior(CombatCommon::performNpcCombatFishingRod)
//                                )
//                );
    }

    @Override
    protected List<AdditionalAttackGroup> getAdditionalAttackGroups(CapabilityItem mainHandCap, CapabilityItem offHandCap, Style style) {
        if (mainHandCap.getWeaponCategory() == WeaponCategories.SWORD
                && this.getOriginal().getMainHandItem().getItem() == AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get()) {
            return style == Styles.TWO_HAND
                    ? List.of(
                    AdditionalAttackGroup.random(
                            0.35F,
                            StraightSwordAnimations.STRAIGHTSWORD_DUAL_DODGE_SLASH,
                            AnimsAVSword.THUNDER_DIAMOND_BLADE_DUAL_INNATE
                    ))
                    : List.of(
                    AdditionalAttackGroup.random(
                            0.35F,
                            EFNSwordAnimations.NF_SWORD_SKILL,
                            AnimsAVSword.THUNDER_DIAMOND_BLADE_INNATE
                    )
            );

        }
        return List.of();
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
