package com.pla.annoyinglib.mixin.client;

import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyinglib.util.GlintColorHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowLayer.class)
public abstract class ArrowLayerMixin<T extends LivingEntity, M extends PlayerModel<T>> {
    @Shadow @Final
    private EntityRenderDispatcher dispatcher;

    @Inject(method = "renderStuckItem", at = @At("HEAD"), cancellable = true)
    private void av$renderColoredStuckArrow(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Entity entity,
         float x, float y, float z, float partialTick, CallbackInfo ci) {
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.Client.renderStuckArrow(poseStack, buffer, packedLight, entity, x, y, z, partialTick, this.dispatcher, annoyingLib$pickMode(entity, x, y, z), ci);
        }
    }

    @Unique
    private static int annoyingLib$pickMode(Entity entity, float x, float y, float z) {
        int seed = entity.getId();
        seed = 31 * seed + Float.floatToIntBits(x);
        seed = 31 * seed + Float.floatToIntBits(y);
        seed = 31 * seed + Float.floatToIntBits(z);

        return switch (Math.floorMod(seed, 11)) {
            case 0 -> GlintColorHelper.ORANGE;
            case 1 -> GlintColorHelper.CYAN;
            case 2 -> GlintColorHelper.BLUE;
            case 3 -> GlintColorHelper.GREEN;
            case 4 -> GlintColorHelper.LIGHT_BLUE;
            case 5 -> GlintColorHelper.LIME;
            case 6 -> GlintColorHelper.MAGENTA;
            case 7 -> GlintColorHelper.PINK;
            case 8 -> GlintColorHelper.PURPLE;
            case 9 -> GlintColorHelper.RED;
            default -> GlintColorHelper.YELLOW;
        };
    }
}
