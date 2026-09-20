package com.pla.annoyingvillagers.util;

import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueInput;
import org.slf4j.Logger;

/** Bridges legacy block-entity CompoundTag payloads to the 26.1 ValueInput API. */
public final class LegacyBlockEntityData {
    private static final Logger LOGGER = LogUtils.getLogger();

    private LegacyBlockEntityData() {
    }

    public static void load(BlockEntity blockEntity, CompoundTag tag, HolderLookup.Provider registries) {
        try (ProblemReporter.ScopedCollector reporter =
                     new ProblemReporter.ScopedCollector(blockEntity.problemPath(), LOGGER)) {
            blockEntity.loadWithComponents(TagValueInput.create(reporter, registries, tag));
        }
    }
}
