package com.pla.annoyingvillagers.mixin.client;

import com.mojang.authlib.GameProfile;
import com.pla.annoyingvillagers.util.NpcTabSkin;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.core.ClientAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInfo.class)
public abstract class PlayerInfoMixin {
    @Shadow public abstract GameProfile getProfile();

    /**
     * Since 1.21, PlayerInfo exposes a complete PlayerSkin rather than a skin
     * Identifier.  The old getSkinLocation injection was therefore never
     * invoked, leaving the fake AVNpc tab-list profiles with their default skin.
     */
    @Inject(method = "getSkin", at = @At("HEAD"), cancellable = true)
    private void av$useNpcSkin(CallbackInfoReturnable<PlayerSkin> callback) {
        NpcTabSkin skin = NpcTabSkin.fromProfile(getProfile());
        if (skin != null) {
            PlayerModelType model = skin == NpcTabSkin.ALEX ? PlayerModelType.SLIM : PlayerModelType.WIDE;
            callback.setReturnValue(new PlayerSkin(new ClientAsset.ResourceTexture(skin.texture()), null, null, model, true));
        }
    }
}
