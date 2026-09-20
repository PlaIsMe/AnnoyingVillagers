package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.layer.VanillaOverlayRenderStateCache;
import com.pla.annoyingvillagers.util.EndFireUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Selects the end-fire atlas sprites in the extracted flame feature pipeline. */
@Mixin(FlameFeatureRenderer.class)
public abstract class EndFireEntityRenderDispatcherMixin {
    @Unique
    private static final Identifier ANNOYINGVILLAGERS_END_FIRE_0 =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "block/end_fire_0");
    @Unique
    private static final Identifier ANNOYINGVILLAGERS_END_FIRE_1 =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "block/end_fire_1");
    @Unique
    private static final ThreadLocal<Boolean> ANNOYINGVILLAGERS_END_FIRE =
            ThreadLocal.withInitial(() -> false);

    @Inject(method = "renderFlame", at = @At("HEAD"))
    private void annoyingVillagers$captureEndFire(PoseStack.Pose pose,
                                                   MultiBufferSource bufferSource,
                                                   EntityRenderState state, Quaternionf rotation,
                                                   AtlasManager atlasManager, CallbackInfo ci) {
        LivingEntity entity = state instanceof LivingEntityRenderState livingState
                ? VanillaOverlayRenderStateCache.getEntity(livingState) : null;
        ANNOYINGVILLAGERS_END_FIRE.set(entity != null && EndFireUtil.isEndFireBurning(entity));
    }

    @ModifyArg(method = "renderFlame", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/sprite/AtlasManager;get(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;"),
            index = 0)
    private Identifier annoyingVillagers$replaceFireSprite(Identifier original) {
        if (!ANNOYINGVILLAGERS_END_FIRE.get()) return original;
        return original.getPath().endsWith("fire_0")
                ? ANNOYINGVILLAGERS_END_FIRE_0 : ANNOYINGVILLAGERS_END_FIRE_1;
    }

    @Inject(method = "renderFlame", at = @At("RETURN"))
    private void annoyingVillagers$clearEndFire(PoseStack.Pose pose,
                                                 MultiBufferSource bufferSource,
                                                 EntityRenderState state, Quaternionf rotation,
                                                 AtlasManager atlasManager, CallbackInfo ci) {
        ANNOYINGVILLAGERS_END_FIRE.remove();
    }
}
