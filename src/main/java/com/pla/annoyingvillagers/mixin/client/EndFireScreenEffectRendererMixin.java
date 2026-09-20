package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.EndFireUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ScreenEffectRenderer.class)
public abstract class EndFireScreenEffectRendererMixin {
    @Unique
    private static final Identifier ANNOYINGVILLAGERS_END_FIRE_OVERLAY =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "block/end_fire_1");

    @ModifyArg(method = "renderScreenEffect", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/sprite/SpriteGetter;get(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"),
            index = 0)
    private Identifier annoyingVillagers$endFireOverlay(Identifier original) {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.player != null && EndFireUtil.isEndFireBurning(minecraft.player)
                ? ANNOYINGVILLAGERS_END_FIRE_OVERLAY : original;
    }
}
