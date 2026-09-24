package com.pla.annoyingvillagers.client.compat;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

/** Limits legacy item-transform compatibility to Punchy's own held-item pass. */
public final class PunchyItemRenderContext {
    private static final ThreadLocal<Integer> DEPTH = ThreadLocal.withInitial(() -> 0);

    private PunchyItemRenderContext() {
    }

    public static boolean isAvItem(ItemStack stack) {
        return stack != null && !stack.isEmpty()
                && AnnoyingVillagers.MODID.equals(BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace());
    }

    public static boolean keepAuthoredHandDisplay(ItemStack stack) {
        if (!isAvItem(stack)) return false;
        return switch (BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath()) {
            case "blue_demon_trident", "ender_glaive" -> true;
            default -> false;
        };
    }

    public static boolean isActive() {
        return DEPTH.get() > 0;
    }

    public static void begin() {
        DEPTH.set(DEPTH.get() + 1);
    }

    public static void end() {
        int depth = DEPTH.get() - 1;
        if (depth <= 0) {
            DEPTH.remove();
        } else {
            DEPTH.set(depth);
        }
    }
}
