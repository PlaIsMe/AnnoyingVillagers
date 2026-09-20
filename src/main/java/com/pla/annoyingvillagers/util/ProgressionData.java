package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.Difficulty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class ProgressionData extends SavedData {
    private static final String DATA_NAME = "annoyingvillagers_progression";
    private static final String DIFFICULTY_TAG = "Difficulty";
    private static final String MANUAL_DIFFICULTY_TAG = "ManualDifficulty";

    private Difficulty difficulty = Difficulty.EASY;
    private boolean manualDifficulty;

    public static ProgressionData get(MinecraftServer server) {
        return com.pla.annoyingvillagers.util.LegacySavedData.computeIfAbsent(server.overworld().getDataStorage(), DATA_NAME, ProgressionData::new, ProgressionData::load, (value, provider) -> value.save(new CompoundTag(), provider));
    }

    public static ProgressionData load(CompoundTag tag) {
        ProgressionData data = new ProgressionData();
        data.difficulty = Difficulty.byName(tag.getStringOr(DIFFICULTY_TAG, ""));
        data.manualDifficulty = tag.getBooleanOr(MANUAL_DIFFICULTY_TAG, false);
        return data;
    }

    public CompoundTag save(CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        tag.putString(DIFFICULTY_TAG, this.difficulty.id());
        tag.putBoolean(MANUAL_DIFFICULTY_TAG, this.manualDifficulty);
        return tag;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public boolean setDifficulty(Difficulty difficulty) {
        return this.setDifficulty(difficulty, true);
    }

    private boolean setDifficulty(Difficulty difficulty, boolean manual) {
        boolean changed = this.difficulty != difficulty || this.manualDifficulty != manual;
        this.manualDifficulty = manual;
        if (this.difficulty == difficulty) {
            if (changed) {
                this.setDirty();
            }
            return changed;
        }

        this.difficulty = difficulty;
        this.setDirty();
        return true;
    }

    public boolean isManualDifficulty() {
        return this.manualDifficulty;
    }

    public boolean increaseDifficulty(Difficulty difficulty) {
        if (this.difficulty.ordinal() >= difficulty.ordinal()) {
            return false;
        }

        return this.setDifficulty(difficulty, false);
    }
}
