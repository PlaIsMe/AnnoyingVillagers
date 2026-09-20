package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.util.ExplosionFxMute;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Suppresses a marked explosion's new packet-driven effects without dropping its knockback. */
@Mixin(ClientPacketListener.class)
public abstract class ClientExplosionMixin {
    @Inject(method = "handleExplosion", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"),
            cancellable = true)
    private void annoyingVillagers$muteExplosionEffects(ClientboundExplodePacket packet, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;
        Vec3 center = packet.center();
        long key = BlockPos.asLong(Mth.floor(center.x), Mth.floor(center.y), Mth.floor(center.z));
        if (!ExplosionFxMute.shouldMute(key, minecraft.level.getGameTime())) return;
        packet.playerKnockback().ifPresent(minecraft.player::addDeltaMovement);
        ci.cancel();
    }
}
