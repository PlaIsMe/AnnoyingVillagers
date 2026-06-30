package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FakePlayerRenderer<T extends FakePlayer> extends HumanoidMobRenderer<T, PlayerModel<T>> {
    private final PlayerModel<T> defaultModel;
    private final PlayerModel<T> slimModel;
    private final RenderLayer<T, PlayerModel<T>> defaultArmorLayer;
    private final RenderLayer<T, PlayerModel<T>> slimArmorLayer;
    private final int armorLayerIndex;

    public FakePlayerRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.defaultModel = this.model;
        this.slimModel = new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM), true);
        this.defaultArmorLayer = new HumanoidArmorLayer<>(
                this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager());
        this.slimArmorLayer = new HumanoidArmorLayer<>(
                this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR)),
                context.getModelManager());

        ArrowLayer<T, PlayerModel<T>> arrowLayer = new ArrowLayer<>(context, this);
        this.addLayer(arrowLayer);
        this.armorLayerIndex = this.layers.indexOf(arrowLayer);
        this.addLayer(new FakePlayerCapeLayer<>(this));
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        boolean slim = FakePlayerTextureUtils.getPlayerSkinType(entity.getProfile()) == FakePlayerTextureUtils.SkinType.SLIM;
        this.model = slim ? this.slimModel : this.defaultModel;
        this.layers.remove(this.defaultArmorLayer);
        this.layers.remove(this.slimArmorLayer);
        this.layers.add(Math.min(this.armorLayerIndex, this.layers.size()), slim ? this.slimArmorLayer : this.defaultArmorLayer);

        this.model.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        this.model.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack mainHand = entity.getMainHandItem();
        if (!mainHand.isEmpty()) {
            if (mainHand.getItem() instanceof CrossbowItem) {
                setHandPose(entity, entity.isUsingItem() ? HumanoidModel.ArmPose.CROSSBOW_CHARGE : HumanoidModel.ArmPose.CROSSBOW_HOLD);
            } else if (mainHand.getItem() instanceof BowItem && entity.isAggressive()) {
                setHandPose(entity, HumanoidModel.ArmPose.BOW_AND_ARROW);
            } else {
                setHandPose(entity, HumanoidModel.ArmPose.ITEM);
            }
        }

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void setHandPose(T entity, HumanoidModel.ArmPose pose) {
        if (entity.getMainArm() == HumanoidArm.RIGHT) {
            this.model.rightArmPose = pose;
        } else {
            this.model.leftArmPose = pose;
        }
    }

    @Override
    protected void scale(@NotNull T entity, @NotNull PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        return FakePlayerTextureUtils.getPlayerSkin(entity);
    }
}
