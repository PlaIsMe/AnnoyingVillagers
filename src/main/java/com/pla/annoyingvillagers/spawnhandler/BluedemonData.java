package com.pla.annoyingvillagers.spawnhandler;

import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BluedemonData extends SavedData {
    public static final String ID = "av_singleton_blue_demon";
    private UUID activeId = null;
    private long claimTick = 0L;
    private static final long COOLDOWN_TICKS = 20L * 60L * 10L;

    public static BluedemonData get(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(new SavedData.Factory<>(BluedemonData::new, (tag, provider) -> BluedemonData.load(tag)), ID);
    }

    public static BluedemonData load(CompoundTag compoundTag) {
        BluedemonData bluedemonData = new BluedemonData();
        if (compoundTag.hasUUID("activeId")) {
            bluedemonData.activeId = compoundTag.getUUID("activeId");
        }
        if (compoundTag.contains("claimTick", Tag.TAG_LONG)) {
            bluedemonData.claimTick = compoundTag.getLong("claimTick");
        }
        return bluedemonData;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag, @NotNull net.minecraft.core.HolderLookup.Provider provider) {
        if (activeId != null) {
            compoundTag.putUUID("activeId", activeId);
        }
        compoundTag.putLong("claimTick", claimTick);
        return compoundTag;
    }

    private static long now(ServerLevel level) {
        return level.getGameTime();
    }

    public boolean isOccupied(ServerLevel serverLevel) {
        if (activeId != null) {
            Entity entity = serverLevel.getEntity(activeId);
            if (entity instanceof BlueDemonEntity && entity.isAlive()) {
                return true;
            } else {
                activeId = null;
                setDirty();
                return false;
            }
        }
        if (claimTick <= 0L) return false;
        long elapsed = now(serverLevel) - claimTick;
        return elapsed < COOLDOWN_TICKS;
    }

    public boolean tryClaim(ServerLevel serverLevel, UUID id) {
        if (isOccupied(serverLevel)) {
            return false;
        }
        activeId = id;
        claimTick = now(serverLevel);
        setDirty();
        return true;
    }

    public void forceClaim(ServerLevel serverLevel, UUID id) {
        activeId = id;
        claimTick = now(serverLevel);
        setDirty();
    }

    public void releaseIfMatches(ServerLevel serverLevel, UUID id) {
        if (activeId != null && activeId.equals(id)) {
            activeId = null;
            claimTick = now(serverLevel);
            setDirty();
        }
    }
}
