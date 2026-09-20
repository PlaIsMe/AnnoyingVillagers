package com.pla.annoyingvillagers.util;

import com.mojang.serialization.MapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/** Bridges legacy entity NBT bodies onto the 26.1 value-I/O callbacks. */
public final class LegacyValueIO {
    private LegacyValueIO() {}

    public static CompoundTag read(ValueInput input) {
        return input.read(MapCodec.assumeMapUnsafe(CompoundTag.CODEC)).orElseGet(CompoundTag::new);
    }

    public static void write(ValueOutput output, CompoundTag tag) {
        output.store(tag);
    }
}
