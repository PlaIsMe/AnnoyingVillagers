package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.layer.HumanoidMobVanillaLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin<T extends Mob, S extends HumanoidRenderState, M extends HumanoidModel<S>> extends MobRenderer<T, S, M> {
    protected HumanoidMobRendererMixin(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    // Every humanoid constructor delegates here, including the adult/baby model overload.
    @Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/client/model/HumanoidModel;FLnet/minecraft/client/renderer/entity/layers/CustomHeadLayer$Transforms;)V",
            at = @At("TAIL"), require = 1)
    private void annoyingVillagers$addOverlayLayer(EntityRendererProvider.Context context, M model,
                                                    M babyModel, float shadowRadius,
                                                    CustomHeadLayer.Transforms customHeadTransforms, CallbackInfo ci) {
        this.addLayer(new HumanoidMobVanillaLayer<>(this));
    }
}
