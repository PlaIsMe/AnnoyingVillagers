package com.pla.annoyingvillagers.task;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import net.shelmarow.combat_evolution.tickTask.TickTask;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

public class MobExecutionTask extends TickTask {
    private final LivingEntity executor;
    private final LivingEntity target;
    private final ExecutionTypeManager.Type executionType;
    private boolean cancelled = false;

    public MobExecutionTask(LivingEntity executor, LivingEntity target,
                            ExecutionTypeManager.Type executionType, int durationTicks) {
        super(durationTicks);
        this.executor = executor;
        this.target = target;
        this.executionType = executionType;
    }

    @Override
    public void onStart() {
        ExecutionHandler.addExecutingTarget(target, executor);

        LivingEntityPatch<?> executorPatch = EpicFightCapabilities.getEntityPatch(executor, LivingEntityPatch.class);
        LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);

        executor.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 100, 1, true, false));
        target.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 100, 1, true, false));
        executor.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 4));

        if (executorPatch != null && targetPatch != null) {
            executorPatch.playAnimationSynchronized(executionType.executionAnimation(), 0.0F);
            targetPatch.playAnimationSynchronized(executionType.executedAnimation(), 0.0F);

            Vec3 from = executor.getEyePosition();
            Vec3 to = target.getEyePosition();
            double dx = to.x - from.x;
            double dz = to.z - from.z;
            float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0F) + executionType.rotationOffset();
            executorPatch.setYRot(yaw);
        }
    }

    @Override
    public void onTick() {
        if (cancelled) {
            return;
        }

        if (target.isAlive()) {
            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);

            if (targetPatch == null) {
                cancelExecution(false);
                return;
            }

            if (targetPatch.getAnimator().getPlayerFor(null) == null) {
                cancelExecution(false);
                return;
            }

            AssetAccessor<? extends StaticAnimation> targetDynamicAnimation =
                    Objects.requireNonNull(targetPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

            if (!(targetDynamicAnimation.get() instanceof ExecutionHitAnimation)) {
                cancelExecution(true);
            }
        }
    }

    private void cancelExecution(boolean rollExecutorBackward) {
        if (cancelled) {
            return;
        }
        cancelled = true;

        LivingEntityPatch<?> executorPatch = EpicFightCapabilities.getEntityPatch(executor, LivingEntityPatch.class);
        LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);

        if (executorPatch != null) {
            executorPatch.stopPlaying(executionType.executionAnimation());

            if (rollExecutorBackward) {
                executorPatch.playAnimationInstantly(Animations.BIPED_ROLL_BACKWARD);
            }
        }

        if (targetPatch != null) {
            targetPatch.stopPlaying(executionType.executedAnimation());
        }

        onFinish();
        this.tickTimer = this.maxTime;
    }

    @Override
    public void onFinish() {
        ExecutionHandler.removeExecutingTarget(target);
    }
}