package com.pla.annoyingvillagers.util;

import net.minecraft.core.UUIDUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

/** Small compatibility helpers for UUIDs removed from CompoundTag in 26.1. */
public final class LegacyNbt {
    private LegacyNbt() {}

    public static void putUUID(CompoundTag tag, String key, UUID value) {
        tag.putIntArray(key, UUIDUtil.uuidToIntArray(value));
    }

    public static boolean hasUUID(CompoundTag tag, String key) {
        return tag.read(key, UUIDUtil.CODEC).isPresent();
    }

    public static @Nullable UUID getUUID(CompoundTag tag, String key) {
        return tag.read(key, UUIDUtil.CODEC).orElse(null);
    }

    public static CompoundTag saveItem(ItemStack stack, HolderLookup.Provider registries) {
        Tag encoded = ItemStack.OPTIONAL_CODEC
                .encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), stack)
                .getOrThrow();
        return encoded instanceof CompoundTag compound ? compound : new CompoundTag();
    }

    public static ItemStack loadItem(CompoundTag tag, HolderLookup.Provider registries) {
        return ItemStack.OPTIONAL_CODEC
                .parse(registries.createSerializationContext(NbtOps.INSTANCE), tag)
                .resultOrPartial(error -> {})
                .orElse(ItemStack.EMPTY);
    }
}
