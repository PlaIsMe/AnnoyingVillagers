package com.pla.annoyingvillagers.mixin.compat.smartnpc;

import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.smart_npc.entity.PlayerNpcEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {PlayerNpcEntity.class}, remap = false)
public abstract class PlayerNpcEntityMixin {
    @Inject(method = "isSmartNpcCompatPlayerLikeTarget", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$isSmartNpcCompatPlayerLikeTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (SmartNpc.isPlayerLikeTarget(target)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isSmartNpcCompatMonsterTarget", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$isSmartNpcCompatMonsterTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (SmartNpc.isMonsterTarget(target)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isSmartNpcCompatVillagerTarget", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$isSmartNpcCompatVillagerTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (SmartNpc.isVillagerTarget(target)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isSmartNpcCompatHighDangerThreat", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$isSmartNpcCompatHighDangerThreat(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (SmartNpc.isHighDangerThreat(target)) {
            cir.setReturnValue(true);
        }
    }
}
