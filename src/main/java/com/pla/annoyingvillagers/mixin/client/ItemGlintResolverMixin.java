package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import com.pla.annoyingvillagers.client.renderer.GlintColorCarrier;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class ItemGlintResolverMixin {
    @Inject(method = "updateForTopItem", at = @At("RETURN"))
    private void av$extractColor(ItemStackRenderState state, ItemStack stack, ItemDisplayContext context, Level level, ItemOwner owner, int seed, CallbackInfo ci) {
        int previous = ColoredGlintState.getMode();
        ColoredGlintState.setTargetStack(stack, owner instanceof LivingEntity living ? living : null);
        ((GlintColorCarrier) state).av$setGlintColor(ColoredGlintState.getMode());
        ColoredGlintState.setMode(previous);
    }
}
