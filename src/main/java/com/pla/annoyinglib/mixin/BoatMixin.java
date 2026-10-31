package com.pla.annoyinglib.mixin;

import com.pla.annoyinglib.clazz.AVNpc;
import com.pla.annoyinglib.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public abstract class BoatMixin {
    @Inject(method = "canAddPassenger", at = @At("HEAD"), cancellable = true)
    private void blockSpecificMobsFromBoat(Entity passenger, CallbackInfoReturnable<Boolean> cir) {
        if (annoyingLib$shouldIgnoreBoat(passenger)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private static boolean annoyingLib$shouldIgnoreBoat(Entity entity) {
        return entity instanceof BlueDemonEntity
                || entity instanceof AVNpc
                || entity instanceof BbqEntity;
                // add this in herobrine_vessels
                // || entity instanceof HerobrineMob
                // || entity instanceof LowHerobrineCloneEntity
                // || entity instanceof LowShadowHerobrineCloneEntity
                // || entity instanceof HerobrineGregEntity
                // || entity instanceof NullSkeletonEntity;
    }
}
