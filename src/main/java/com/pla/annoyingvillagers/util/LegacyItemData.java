package com.pla.annoyingvillagers.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.function.Consumer;

/**
 * Keeps the mod's existing per-stack keys on Minecraft 1.21's custom-data
 * component while the gameplay code continues to use its established NBT
 * schema.
 */
public final class LegacyItemData {
    private LegacyItemData() {
    }

    public static boolean has(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data != null && !data.isEmpty();
    }

    public static CompoundTag get(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data == null ? null : data.getUnsafe();
    }

    public static CompoundTag getOrCreate(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            data = CustomData.of(new CompoundTag());
            stack.set(DataComponents.CUSTOM_DATA, data);
        }
        return data.getUnsafe();
    }

    public static void set(ItemStack stack, CompoundTag tag) {
        if (tag == null || tag.isEmpty()) {
            stack.remove(DataComponents.CUSTOM_DATA);
        } else {
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }
    }

    /**
     * Mutates legacy stack data through the component API so ItemStack notices
     * the change and synchronizes it to clients.
     */
    public static void update(ItemStack stack, Consumer<CompoundTag> updater) {
        CustomData.update(DataComponents.CUSTOM_DATA, stack, updater);
    }
}
