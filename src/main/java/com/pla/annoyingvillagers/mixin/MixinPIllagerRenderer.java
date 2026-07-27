package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.client.overlaylayer.IllagerMobEpicFightOverlayLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.renderer.patched.entity.PIllagerRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@Mixin(value = PIllagerRenderer.class, remap = false)
public abstract class MixinPIllagerRenderer {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void annoyingvillagers$addEyes(
            EntityRendererProvider.Context context,
            EntityType<?> entityType,
            CallbackInfo ci
    ) {
        PatchedLivingEntityRenderer<?, ?, ?, ?, ?> self =
                (PatchedLivingEntityRenderer<?, ?, ?, ?, ?>) (Object) this;

        self.addCustomLayer(new IllagerMobEpicFightOverlayLayer(Meshes.ILLAGER));
    }
}