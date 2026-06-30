package com.pla.annoyingvillagers.client.overlaylayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PlayerMobBloodOverlayLayer extends RenderLayer<FakePlayer, PlayerModel<FakePlayer>> {
    private static final ResourceLocation TEX =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/player_mob_blood.png");

    public PlayerMobBloodOverlayLayer(RenderLayerParent<FakePlayer, PlayerModel<FakePlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buf, int light,
                       FakePlayer e, float limbSwing, float limbSwingAmount,
                       float partialTick, float age, float headYaw, float headPitch) {

        var model = this.getParentModel();
        var vc = buf.getBuffer(RenderType.entityCutoutNoCull(TEX));
        model.renderToBuffer(pose, vc, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
    }
}
