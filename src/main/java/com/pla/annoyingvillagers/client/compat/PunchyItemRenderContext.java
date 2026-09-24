package com.pla.annoyingvillagers.client.compat;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Limits legacy item-transform compatibility to Punchy's own held-item pass. */
public final class PunchyItemRenderContext {
    private static final ThreadLocal<Integer> DEPTH = ThreadLocal.withInitial(() -> 0);
    // The weapons configured by the 1.21.1 compatibility pack, not every AV
    // item (bows, shields, tools without a legacy profile, armor and eggs).
    private static final TagKey<Item> LEGACY_HAND_DISPLAY = TagKey.create(Registries.ITEM,
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "punchy_legacy_hand_display"));

    private PunchyItemRenderContext() {
    }

    public static boolean isAvItem(ItemStack stack) {
        return stack != null && !stack.isEmpty()
                && AnnoyingVillagers.MODID.equals(BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace());
    }

    public static boolean keepAuthoredHandDisplay(ItemStack stack) {
        // Retain the namespace check even if a datapack adds a foreign item to
        // the tag. Our compatibility must not change another mod's weapons.
        return isAvItem(stack) && stack.is(LEGACY_HAND_DISPLAY);
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
