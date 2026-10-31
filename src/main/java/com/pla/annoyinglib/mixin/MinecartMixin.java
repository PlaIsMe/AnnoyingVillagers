package com.pla.annoyinglib.mixin;

import com.pla.annoyinglib.clazz.AVNpc;
import com.pla.annoyinglib.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MinecartMixin {
    @Inject(method = "canAddPassenger(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void blockSpecificMobsFromMinecart(Entity passenger, CallbackInfoReturnable<Boolean> cir) {
        Entity vehicle = (Entity) (Object) this;
        if (vehicle instanceof AbstractMinecart && annoyingLib$shouldIgnoreMinecart(passenger)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private static boolean annoyingLib$shouldIgnoreMinecart(Entity entity) {
        return entity instanceof BlueDemonEntity
                || entity instanceof AVNpc
                || entity instanceof BbqEntity;
                // add this in herobrine_vessels
                // || entity instanceof HerobrineMob
                // || entity instanceof HerobrineGregEntity
                // || entity instanceof NullSkeletonEntity
                // || entity instanceof LowHerobrineCloneEntity
                // || entity instanceof LowShadowHerobrineCloneEntity;
    }
}
