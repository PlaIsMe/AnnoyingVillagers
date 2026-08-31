package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.client.model.ModelRig;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
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
    public void render(@NotNull LowHerobrineCloneEntity entity,float entityYaw,float partialTick,@NotNull PoseStack poseStack,@NotNull MultiBufferSource buffer,int packedLight) {
        this.model = FakePlayerTextureUtils.getPlayerSkinType(entity.getProfile()) == FakePlayerTextureUtils.SkinType.SLIM ? this.slimModel : this.defaultModel;
        super.render(entity,entityYaw,partialTick,poseStack,buffer,packedLight);
    }

    @Override
    protected void scale(@NotNull LowHerobrineCloneEntity entity,@NotNull PoseStack poseStack,float partialTickTime) {
        poseStack.scale(0.9375F,0.9375F,0.9375F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LowHerobrineCloneEntity entity) {
        return FakePlayerTextureUtils.getPlayerSkin(entity);
    }
}
