package com.pla.annoyinglib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyinglib.entity.EnchantedArrowEntity;
import com.pla.annoyinglib.init.AnnoyingLibModEntities;
import com.pla.annoyinglib.util.GlintColorHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.util.Mth;
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
         // add this in herobrine_vessels
        // if (entity instanceof HerobrineMob) {
        //     float f = Mth.sqrt(x * x + z * z);

        //     EnchantedArrowEntity arrow =
        //             new EnchantedArrowEntity(AnnoyingLibModEntities.ENCHANTED_ARROW.get(), entity.level());

        //     arrow.setPos(entity.getX(), entity.getY(), entity.getZ());
        //     arrow.setYRot((float) (Math.atan2((double) x, (double) z) * (180F / Math.PI)));
        //     arrow.setXRot((float) (Math.atan2((double) y, (double) f) * (180F / Math.PI)));
        //     arrow.yRotO = arrow.getYRot();
        //     arrow.xRotO = arrow.getXRot();

        //     arrow.setColorGlint(annoyingLib$pickMode(entity, x, y, z));

        //     this.dispatcher.render(
        //             arrow,
        //             0.0D, 0.0D, 0.0D,
        //             0.0F,
        //             partialTick,
        //             poseStack,
        //             buffer,
        //             packedLight
        //     );

        //     ci.cancel();
        // }
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