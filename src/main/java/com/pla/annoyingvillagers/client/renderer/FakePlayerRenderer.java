package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.PlayerModelType;
import org.jetbrains.annotations.NotNull;

public class FakePlayerRenderer<T extends FakePlayer>
        extends HumanoidMobRenderer<T, AvatarRenderState, PlayerModel> {
    private final PlayerModel defaultModel;
    private final PlayerModel slimModel;

    public FakePlayerRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.defaultModel = this.model;
        this.slimModel = new PlayerModel(context.bakeLayer(ModelLayers.PLAYER_SLIM), true);
        this.addLayer(new HumanoidArmorLayer<>(this,
                ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), part -> new PlayerModel(part, false)),
                context.getEquipmentRenderer()));
        this.addLayer(new ArrowLayer<>(this, context));
        this.addLayer(new CapeLayer(this, context.getModelSet(), context.getEquipmentAssets()));
    }

    @Override
    public void submit(AvatarRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        this.model = state.skin.model() == PlayerModelType.SLIM ? this.slimModel : this.defaultModel;
        super.submit(state, poseStack, collector, camera);
    }

    @Override
    public AvatarRenderState createRenderState() {
        return new AvatarRenderState();
    }

    @Override
    public void extractRenderState(T entity, AvatarRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.skin = FakePlayerTextureUtils.getPlayerSkinData(entity);
        state.arrowCount = entity.getArrowCount();
        state.showCape = state.skin.cape() != null;

        double cloakX = Mth.lerp(partialTick, entity.xCloakO, entity.xCloak) - Mth.lerp(partialTick, entity.xo, entity.getX());
        double cloakY = Mth.lerp(partialTick, entity.yCloakO, entity.yCloak) - Mth.lerp(partialTick, entity.yo, entity.getY());
        double cloakZ = Mth.lerp(partialTick, entity.zCloakO, entity.zCloak) - Mth.lerp(partialTick, entity.zo, entity.getZ());
        float bodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        double bodySin = Mth.sin(bodyRot * ((float)Math.PI / 180.0F));
        double bodyCos = -Mth.cos(bodyRot * ((float)Math.PI / 180.0F));
        state.capeFlap = Mth.clamp((float)cloakY * 10.0F, -6.0F, 32.0F);
        state.capeLean = Mth.clamp((float)(cloakX * bodySin + cloakZ * bodyCos) * 100.0F, 0.0F, 150.0F);
        state.capeLean2 = Mth.clamp((float)(cloakX * bodyCos - cloakZ * bodySin) * 100.0F, -20.0F, 20.0F);
    }

    @Override
    protected void scale(@NotNull AvatarRenderState state, @NotNull PoseStack poseStack) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull AvatarRenderState state) {
        return state.skin.body().texturePath();
    }
}
