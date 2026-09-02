package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.EndFireUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ScreenEffectRenderer.class)
public abstract class EndFireScreenEffectRendererMixin {
    @Unique
    private static final Material ANNOYINGVILLAGERS_END_FIRE_OVERLAY = new Material(InventoryMenu.BLOCK_ATLAS,ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"block/end_fire_1"));

    @Redirect(method = "renderFire",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"))
    private static TextureAtlasSprite annoyingVillagers$endFireOverlay(Material original) {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.player != null && EndFireUtil.isEndFireBurning(minecraft.player) ? ANNOYINGVILLAGERS_END_FIRE_OVERLAY.sprite() : original.sprite();
    }
}
