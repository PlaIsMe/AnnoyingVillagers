package com.pla.annoyinglib.mixin;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.clazz.AVNpc;
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
        return (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonEntity(entity))
                || entity instanceof AVNpc
                || (ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBbqEntity(entity))
                || (ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.blocksVehicle(entity));

    }
}
