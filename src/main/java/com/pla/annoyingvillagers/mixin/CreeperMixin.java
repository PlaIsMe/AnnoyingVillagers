package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.util.CommonGoals;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Creeper.class}, remap = true)
public class CreeperMixin {
    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void monsterTargetNpc(CallbackInfo ci) {
        Creeper self = (Creeper) (Object) this;
        CommonGoals.registerTargetGoalForVanillaMonster(self);
    }
}
