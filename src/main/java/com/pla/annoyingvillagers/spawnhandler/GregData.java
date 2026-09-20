package com.pla.annoyingvillagers.spawnhandler;

import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GregData extends SavedData {
    public static final String ID = "av_singleton_greg";
    private UUID activeId = null;
    private long claimTick = 0L;
    private static final long COOLDOWN_TICKS = 20L * 60L * 10L;

    public UUID getActiveId() {
        return activeId;
    }

    public static GregData get(ServerLevel serverLevel) {
        return com.pla.annoyingvillagers.util.LegacySavedData.computeIfAbsent(serverLevel.getDataStorage(), ID, GregData::new, GregData::load, (value, provider) -> value.save(new CompoundTag(), provider));
    }

    public static GregData load(CompoundTag compoundTag) {
        GregData gregData = new GregData();
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(compoundTag, "activeId")) {
            gregData.activeId = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(compoundTag, "activeId");
        }
        if (compoundTag.contains("claimTick")) {
            gregData.claimTick = compoundTag.getLongOr("claimTick", 0L);
        }
        return gregData;
    }
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag, @NotNull net.minecraft.core.HolderLookup.Provider provider) {
        if (activeId != null) {
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(compoundTag, "activeId", activeId);
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
            if (entity instanceof HerobrineGregEntity && entity.isAlive()) {
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
    public void releaseIfMatches(ServerLevel serverLevel, UUID id) {
        if (activeId != null && activeId.equals(id)) {
            activeId = null;
            claimTick = now(serverLevel);
            setDirty();
        }
    }
}
