package com.pla.annoyingvillagers.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.execution.ExecutionTask;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import net.shelmarow.combat_evolution.tickTask.TickTask;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@Mixin(value = ExecutionTask.class, remap = false)
public abstract class ExecutionTaskMixin extends TickTask {
    @Shadow @Final private LivingEntity executor;
    @Shadow @Final private LivingEntity target;
    @Shadow @Final private ExecutionTypeManager.Type executionType;

    @Shadow
    public abstract void onFinish();

    @Unique
    private boolean cancelled;

    protected ExecutionTaskMixin(int durationTicks) {
        super(durationTicks);
    }

    @Inject(method = "onTick", at = @At("HEAD"), cancellable = true, remap = false)
    private void onTick(CallbackInfo ci) {
        if (this.cancelled) {
            ci.cancel();
            return;
        }

        if (this.target.isAlive()) {
            LivingEntityPatch<?> targetPatch =
                    EpicFightCapabilities.getEntityPatch(this.target, LivingEntityPatch.class);
            if (targetPatch == null) {
                this.annoyingVillagers$cancelExecution(false);
                ci.cancel();
                return;
            }
            if (targetPatch.getAnimator().getPlayerFor(null) == null) {
                this.annoyingVillagers$cancelExecution(false);
                ci.cancel();
                return;
            }

            AssetAccessor<? extends StaticAnimation> targetDynamicAnimation =
                    Objects.requireNonNull(targetPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

            if (!(targetDynamicAnimation.get() instanceof ExecutionHitAnimation)) {
                this.annoyingVillagers$cancelExecution(true);
                ci.cancel();
            }
        }
    }

    @Unique
    private void annoyingVillagers$cancelExecution(boolean rollExecutorBackward) {
        if (this.cancelled) {
            return;
        }

        this.cancelled = true;

        LivingEntityPatch<?> executorPatch =
                EpicFightCapabilities.getEntityPatch(this.executor, LivingEntityPatch.class);
        LivingEntityPatch<?> targetPatch =
                EpicFightCapabilities.getEntityPatch(this.target, LivingEntityPatch.class);

        if (executorPatch != null) {
            executorPatch.stopPlaying(this.executionType.executionAnimation());

            if (rollExecutorBackward) {
                executorPatch.playAnimationInstantly(Animations.BIPED_ROLL_BACKWARD);
            }
        }

        if (targetPatch != null) {
            targetPatch.stopPlaying(this.executionType.executedAnimation());
        }

        ExecutionHandler.removeExecutingTarget(this.target);
        this.tickTimer = this.maxTime;
    }
}