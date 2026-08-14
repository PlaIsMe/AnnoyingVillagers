package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import net.minecraft.world.entity.LivingEntity;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = ExecutionHandler.class, remap = false)
public abstract class ExecutionHandlerMixin {
    @Inject(method = "isHoldingWeapon", at = @At("HEAD"), cancellable = true)
    private static void allowExecuteByFist(LivingEntity executor, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isTargetSupported", at = @At("HEAD"), cancellable = true)
    private static void supportAdvancedMobPatchExecutions(
            LivingEntityPatch<?> executorPatch,
            LivingEntityPatch<?> targetPatch,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (targetPatch instanceof AdvancedMobPatch<?> advancedMobPatch) {
            cir.setReturnValue(advancedMobPatch.canBeExecuted(executorPatch));
        }
    }
}
