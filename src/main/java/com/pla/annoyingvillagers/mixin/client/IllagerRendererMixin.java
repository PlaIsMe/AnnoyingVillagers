package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.overlaylayer.IllagerMobVanillaOverlayLayer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.monster.AbstractIllager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerRenderer.class)
public abstract class IllagerRendererMixin<T extends AbstractIllager> extends MobRenderer<T, IllagerModel<T>> {
    protected IllagerRendererMixin(EntityRendererProvider.Context context, IllagerModel<T> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void annoyingVillagers$addOverlayLayer(EntityRendererProvider.Context context, IllagerModel<T> model, float shadowRadius, CallbackInfo ci) {
        this.addLayer(new IllagerMobVanillaOverlayLayer<>(this));
    }
}
