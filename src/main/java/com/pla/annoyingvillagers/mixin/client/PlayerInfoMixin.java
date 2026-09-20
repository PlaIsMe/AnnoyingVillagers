package com.pla.annoyingvillagers.mixin.client;

import com.mojang.authlib.GameProfile;
import com.pla.annoyingvillagers.util.NpcTabSkin;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.PlayerSkin;
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
     * ResourceLocation.  The old getSkinLocation injection was therefore never
     * invoked, leaving the fake AVNpc tab-list profiles with their default skin.
     */
    @Inject(method = "getSkin", at = @At("HEAD"), cancellable = true)
    private void av$useNpcSkin(CallbackInfoReturnable<PlayerSkin> callback) {
        NpcTabSkin skin = NpcTabSkin.fromProfile(getProfile());
        if (skin != null) {
            PlayerSkin.Model model = skin == NpcTabSkin.ALEX ? PlayerSkin.Model.SLIM : PlayerSkin.Model.WIDE;
            callback.setReturnValue(new PlayerSkin(skin.texture(), null, null, null, model, true));
        }
    }
}
