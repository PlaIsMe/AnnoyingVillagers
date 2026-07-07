package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
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
        if (annoyingVillagers$shouldIgnoreBoat(passenger)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private static boolean annoyingVillagers$shouldIgnoreBoat(Entity entity) {
        return entity instanceof BlueDemonEntity
                || entity instanceof HerobrineMob
                || entity instanceof AVNpc
                || entity instanceof LowHerobrineCloneEntity
                || entity instanceof LowShadowHerobrineCloneEntity
                || entity instanceof BbqEntity
                || entity instanceof HerobrineGregEntity
                || entity instanceof NullSkeletonEntity;
    }
}
