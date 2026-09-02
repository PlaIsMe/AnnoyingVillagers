package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.EndFireUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderDispatcher.class)
public abstract class EndFireEntityRenderDispatcherMixin {
    @Unique
    private static final Material ANNOYINGVILLAGERS_END_FIRE_0 = new Material(InventoryMenu.BLOCK_ATLAS,ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"block/end_fire_0"));
    @Unique
    private static final Material ANNOYINGVILLAGERS_END_FIRE_1 = new Material(InventoryMenu.BLOCK_ATLAS,ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"block/end_fire_1"));

    @Redirect(method = "renderFlame",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;",ordinal = 0))
    private TextureAtlasSprite annoyingVillagers$endFireSprite0(Material original,PoseStack poseStack,MultiBufferSource buffer,Entity entity) {
        return EndFireUtil.isEndFireBurning(entity) ? ANNOYINGVILLAGERS_END_FIRE_0.sprite() : original.sprite();
    }

    @Redirect(method = "renderFlame",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;",ordinal = 1))
    private TextureAtlasSprite annoyingVillagers$endFireSprite1(Material original,PoseStack poseStack,MultiBufferSource buffer,Entity entity) {
        return EndFireUtil.isEndFireBurning(entity) ? ANNOYINGVILLAGERS_END_FIRE_1.sprite() : original.sprite();
    }
}
