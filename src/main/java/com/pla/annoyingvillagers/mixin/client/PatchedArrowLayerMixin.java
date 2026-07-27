package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.EnchantedArrowEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.GlintColorHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
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
import yesman.epicfight.client.renderer.patched.layer.PatchedArrowLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = PatchedArrowLayer.class, remap = false)
public abstract class PatchedArrowLayerMixin<
        E extends LivingEntity,
        T extends LivingEntityPatch<E>,
        M extends PlayerModel<E>> {

    @Final
    @Shadow
    private EntityRenderDispatcher dispatcher;

    @Inject(method = "renderStuckItem", at = @At("HEAD"), cancellable = true)
    private void av$renderColoredStuckArrow(
            PoseStack poseStack, MultiBufferSource buffer, int packedLight, Entity entity, float f1, float f2, float f3, float partialTick,
            CallbackInfo ci
    ) {
        if (entity instanceof HerobrineMob) {
            float f = Mth.sqrt(f1 * f1 + f3 * f3);

            EnchantedArrowEntity arrow =
                    new EnchantedArrowEntity(AnnoyingVillagersModEntities.ENCHANTED_ARROW.get(), entity.level());

            arrow.setPos(entity.getX(), entity.getY(), entity.getZ());
            arrow.setYRot((float) (Math.atan2(f1, f3) * (double) (180F / (float) Math.PI)));
            arrow.setXRot((float) (Math.atan2(f2, f) * (double) (180F / (float) Math.PI)));
            arrow.yRotO = arrow.getYRot();
            arrow.xRotO = arrow.getXRot();

            arrow.setColorGlint(annoyingVillagers$pickMode(entity, f1, f2, f3));

            this.dispatcher.render(
                    arrow,
                    0.0D, 0.0D, 0.0D,
                    0.0F,
                    partialTick,
                    poseStack,
                    buffer,
                    packedLight
            );

            ci.cancel();
        }
    }

    @Unique
    private static int annoyingVillagers$pickMode(Entity entity, float x, float y, float z) {
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