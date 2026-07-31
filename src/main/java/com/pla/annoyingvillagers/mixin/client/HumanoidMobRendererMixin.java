package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.layer.HumanoidMobVanillaLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin<T extends Mob, M extends HumanoidModel<T>> extends LivingEntityRenderer<T, M> {
    protected HumanoidMobRendererMixin(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;F)V", at = @At("TAIL"))
    private void annoyingVillagers$addOverlayLayerShort(EntityRendererProvider.Context context, HumanoidModel<T> model, float shadowRadius, CallbackInfo ci) {
        this.annoyingVillagers$addOverlayLayerIfMissing();
    }

    @SuppressWarnings("unchecked")
    private void annoyingVillagers$addOverlayLayerIfMissing() {
        List<RenderLayer<T, M>> layers = ((LivingEntityRendererAccessor<T, M>) this).annoyingVillagers$getLayers();
        for (RenderLayer<T, M> layer : layers) {
            if (layer instanceof HumanoidMobVanillaLayer) {
                return;
            }
        }
        this.addLayer(new HumanoidMobVanillaLayer<>(this));
    }
}
