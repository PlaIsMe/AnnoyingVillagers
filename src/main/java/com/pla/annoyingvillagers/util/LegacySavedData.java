package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;

/** Bridges the old CompoundTag SavedData contract to 26.1's codec-backed storage. */
public final class LegacySavedData {
    private LegacySavedData() {
    }

    public static <T extends SavedData> T computeIfAbsent(
            SavedDataStorage storage,
            String name,
            Supplier<T> factory,
            Function<CompoundTag, T> loader,
            BiFunction<T, HolderLookup.Provider, CompoundTag> saver
    ) {
        SavedDataType<T> type = new SavedDataType<>(
                Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, name),
                ignored -> factory.get(),
                level -> CompoundTag.CODEC.xmap(loader, value -> saver.apply(value, level.registryAccess()))
        );
        return storage.computeIfAbsent(type);
    }
}
