package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.skill.BasicAttack;

@Mixin(BasicAttack.class)
public abstract class BasicAttackMixin {
    @Redirect(
            method = "executeOnServer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;isPassenger()Z"
            )
    )
    private boolean annoyingvillagers$dragonTreatAsNotPassenger(ServerPlayer player) {
        Entity vehicle = player.getVehicle();
        if (vehicle instanceof HerobrineDragonEntity) {
            return false;
        }
        return player.isPassenger();
    }
}