package com.pla.annoyingvillagers.mixin.compat.punchy;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pla.annoyingvillagers.client.compat.PunchyItemRenderContext;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.cuboid.ItemTransform;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import punchy.mixin.client.accessor.ItemStackRenderStateAccessor;
import punchy.mixin.client.accessor.ItemStackRenderStateLayerAccessor;

/**
 * Retains the selected model's per-layer display transforms, as 1.21.1 did.
 * Punchy's replacement-model exclusions do NOT disable its Item/context-only
 * transform cache. This bridge is still required for changing Reaver forms and
 * composite Hook Gun layers, and runs only inside Punchy's AV held-item call.
 */
@Mixin(value = ItemModelResolver.class, priority = 500)
public abstract class PunchyItemModelResolverMixin {
    @Unique
    private static final ThreadLocal<ItemTransform[]> av$authoredTransforms = new ThreadLocal<>();
    @Unique
    private static final ThreadLocal<ItemStackRenderState> av$capturingState = new ThreadLocal<>();

    @WrapMethod(method = "updateForTopItem")
    private void av$preserveAuthoredTransforms(ItemStackRenderState state, ItemStack stack,
                                              ItemDisplayContext context, Level level, ItemOwner owner,
                                              int seed, Operation<Void> original) {
        if (!PunchyItemRenderContext.isActive() || (!context.firstPerson()
                && context != ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                && context != ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                || !PunchyItemRenderContext.isAvItem(stack)) {
            original.call(state, stack, context, level, owner, seed);
            return;
        }

        // Punchy resolves additional models recursively while choosing a display
        // context. Each resolution needs its own snapshot, including each hand.
        ItemTransform[] previous = av$authoredTransforms.get();
        ItemStackRenderState previousState = av$capturingState.get();
        av$authoredTransforms.remove();
        av$capturingState.set(state);
        try {
            original.call(state, stack, context, level, owner, seed);
            ItemTransform[] authored = av$authoredTransforms.get();
            if (authored != null) {
                ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) state;
                ItemStackRenderState.LayerRenderState[] layers = accessor.punchy$getLayers();
                int count = Math.min(authored.length, accessor.punchy$getActiveLayerCount());
                for (int i = 0; i < count; i++) {
                    if (authored[i] != null) {
                        layers[i].setItemTransform(authored[i]);
                    }
                }
            }
        } finally {
            if (previous == null) {
                av$authoredTransforms.remove();
            } else {
                av$authoredTransforms.set(previous);
            }
            if (previousState == null) {
                av$capturingState.remove();
            } else {
                av$capturingState.set(previousState);
            }
        }
    }

    @WrapOperation(
            method = "updateForTopItem",
            at = @At(value = "INVOKE", target =
                    "Lnet/minecraft/client/renderer/item/ItemModelResolver;appendItemLayers(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/ItemOwner;I)V"),
            require = 1
    )
    private void av$captureSelectedModel(ItemModelResolver resolver, ItemStackRenderState state, ItemStack stack,
                                         ItemDisplayContext context, Level level, ItemOwner owner, int seed,
                                         Operation<Void> original) {
        original.call(resolver, state, stack, context, level, owner, seed);
        if (av$capturingState.get() == state) {
            // Capture BEFORE Punchy's RETURN hook replaces every layer with a
            // transform cached only by Item + display context. That cache cannot
            // distinguish Reaver forms or the Hook Gun's separate bound-item layer.
            // Restoring after that hook also gives its orientation heuristic the
            // current model's real first/third-person transforms.
            ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) state;
            ItemStackRenderState.LayerRenderState[] layers = accessor.punchy$getLayers();
            ItemTransform[] transforms = new ItemTransform[accessor.punchy$getActiveLayerCount()];
            for (int i = 0; i < transforms.length; i++) {
                transforms[i] = ((ItemStackRenderStateLayerAccessor) layers[i]).punchy$getItemTransform();
            }
            av$authoredTransforms.set(transforms);
        }
    }
}
