package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelBbq;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

public class BbqRenderer extends MobRenderer<Chicken, BbqRenderState, ModelBbq> {
    public BbqRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelBbq(context.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
        this.addLayer(new BbqHeldItemLayer(this));
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull BbqRenderState state) {
        return Identifier.fromNamespaceAndPath(
                AnnoyingVillagers.MODID,
                "textures/entities/chicken.png"
        );
    }

    @Override
    public BbqRenderState createRenderState() {
        return new BbqRenderState();
    }

    @Override
    public void extractRenderState(Chicken entity, BbqRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.flap = Mth.lerp(partialTick, entity.oFlap, entity.flap);
        state.flapSpeed = Mth.lerp(partialTick, entity.oFlapSpeed, entity.flapSpeed);
        this.itemModelResolver.updateForLiving(state.mainHand, entity.getMainHandItem(), ItemDisplayContext.GROUND, entity);
        this.itemModelResolver.updateForLiving(state.offHand, entity.getOffhandItem(), ItemDisplayContext.GROUND, entity);
    }
}
