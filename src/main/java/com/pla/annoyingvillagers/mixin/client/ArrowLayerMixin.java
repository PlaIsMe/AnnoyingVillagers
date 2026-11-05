package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.client.layer.VanillaOverlayRenderStateCache;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.util.GlintColorHelper;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Adds the legacy per-arrow colored foil to 26.1's stuck-arrow model submissions. */
@Mixin(StuckInBodyLayer.class)
public abstract class ArrowLayerMixin {
    @Shadow @Final private Model<Object> model;
    @Shadow @Final private Object modelState;

    @Unique
    private static final ThreadLocal<Entity> ANNOYINGVILLAGERS_STUCK_ARROW_OWNER = new ThreadLocal<>();

    @Inject(method = "submit", at = @At("HEAD"))
    private void av$captureArrowOwner(PoseStack poseStack, SubmitNodeCollector collector, int light,
                                      AvatarRenderState state, float yRot, float xRot, CallbackInfo ci) {
        Entity entity = VanillaOverlayRenderStateCache.getEntity(state);
        if (entity instanceof HerobrineMob) ANNOYINGVILLAGERS_STUCK_ARROW_OWNER.set(entity);
        else ANNOYINGVILLAGERS_STUCK_ARROW_OWNER.remove();
    }

    @Inject(method = "submitStuckItem", at = @At("TAIL"))
    private void av$submitColoredStuckArrow(PoseStack poseStack, SubmitNodeCollector collector,
                                             int light, float x, float y, float z, int outlineColor,
                                             CallbackInfo ci) {
        Entity entity = ANNOYINGVILLAGERS_STUCK_ARROW_OWNER.get();
        if (entity == null) return;
        int mode = av$pickMode(entity, x, y, z);
        collector.submitModel(model, modelState, poseStack,
                ColoredGlintRenderTypes.getEntityGlint(mode, RenderTypes.entityGlint()),
                light, OverlayTexture.NO_OVERLAY, -1, null, outlineColor, null);
    }

    @Inject(method = "submit", at = @At("RETURN"))
    private void av$clearArrowOwner(PoseStack poseStack, SubmitNodeCollector collector, int light,
                                    AvatarRenderState state, float yRot, float xRot, CallbackInfo ci) {
        ANNOYINGVILLAGERS_STUCK_ARROW_OWNER.remove();
    }

    @Unique
    private static int av$pickMode(Entity entity, float x, float y, float z) {
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
