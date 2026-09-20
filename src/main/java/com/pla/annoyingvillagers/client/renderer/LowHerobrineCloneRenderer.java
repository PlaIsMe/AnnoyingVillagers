package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.client.model.ModelRig;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class LowHerobrineCloneRenderer extends RigMobRenderer<LowHerobrineCloneEntity> {
    private final ModelRig<LowHerobrineCloneEntity> defaultModel;
    private final ModelRig<LowHerobrineCloneEntity> slimModel;

    public LowHerobrineCloneRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.defaultModel = this.model;
        this.slimModel = new ModelRig<>(context.bakeLayer(ModelRig.SLIM_LAYER_LOCATION),true);
    }

    @Override
    public void submit(LegacyEntityRenderState<LowHerobrineCloneEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        this.model = FakePlayerTextureUtils.getPlayerSkinType(state.entity.getProfile()) == FakePlayerTextureUtils.SkinType.SLIM ? this.slimModel : this.defaultModel;
        super.submit(state, poseStack, collector, camera);
    }

    @Override
    protected void scale(@NotNull LegacyEntityRenderState<LowHerobrineCloneEntity> state,@NotNull PoseStack poseStack) {
        poseStack.scale(0.9375F,0.9375F,0.9375F);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull LowHerobrineCloneEntity entity) {
        return FakePlayerTextureUtils.getPlayerSkin(entity);
    }
}
