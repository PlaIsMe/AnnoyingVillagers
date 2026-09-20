package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {AbstractIllager.class}, remap = true)
public class IllagerMixin {
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void monsterTargetNpc(CallbackInfo ci) {
        AbstractIllager self = (AbstractIllager) (Object) this;
        CommonGoals.registerGoalForHostileNpc(self);
    }
}