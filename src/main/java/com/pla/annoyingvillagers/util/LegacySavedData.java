package com.pla.annoyingvillagers.util;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.DimensionDataStorage;

/** Bridges the old CompoundTag SavedData contract to 1.21.11's codec-backed storage. */
public final class LegacySavedData {
    private LegacySavedData() {
    }

    public static <T extends SavedData> T computeIfAbsent(
            DimensionDataStorage storage,
            String name,
            Supplier<T> factory,
            Function<CompoundTag, T> loader,
            BiFunction<T, HolderLookup.Provider, CompoundTag> saver
    ) {
        SavedDataType<T> type = new SavedDataType<>(
                name,
                ignored -> factory.get(),
                level -> CompoundTag.CODEC.xmap(loader, value -> saver.apply(value, level.registryAccess()))
        );
        return storage.computeIfAbsent(type);
    }
}
