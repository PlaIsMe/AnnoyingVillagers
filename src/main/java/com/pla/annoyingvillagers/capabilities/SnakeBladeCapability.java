package com.pla.annoyingvillagers.capabilities;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.UUID;

/** Persistent state stored through the 1.21 data-attachment system. */
public final class SnakeBladeCapability {
    private static final String NBT_HAS_SNAKE_BLADE = "hasSnakeBlade";
    private static final String NBT_LAST_ID = "getLastSnakeBladeID";
    private static final String NBT_LAST_UUID = "getLastSnakeBladeUUID";

    private SnakeBladeCapability() {
    }

    public interface ISnakeBladeCapability {
        void setHasSnakeBlade(boolean hasSnakeBlade);

        boolean hasSnakeBlade();

        void setLastSnakeBladeID(int id);

        int getLastSnakeBladeID();

        @Nullable
        UUID getLastSnakeBladeUUID();

        void setLastSnakeBladeUUID(@Nullable UUID uuid);
    }

    public static final class SnakeBladeCapabilityImp implements ISnakeBladeCapability {
        private boolean hasSnakeBlade;
        private int lastSnakeBladeId = -1;
        @Nullable
        private UUID lastSnakeBladeUuid;

        @Override
        public void setHasSnakeBlade(boolean hasSnakeBlade) {
            this.hasSnakeBlade = hasSnakeBlade;
        }

        @Override
        public boolean hasSnakeBlade() {
            return hasSnakeBlade;
        }

        @Override
        public void setLastSnakeBladeID(int id) {
            this.lastSnakeBladeId = id;
        }

        @Override
        public int getLastSnakeBladeID() {
            return lastSnakeBladeId;
        }

        @Override
        public @Nullable UUID getLastSnakeBladeUUID() {
            return lastSnakeBladeUuid;
        }

        @Override
        public void setLastSnakeBladeUUID(@Nullable UUID uuid) {
            this.lastSnakeBladeUuid = uuid;
        }

        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean(NBT_HAS_SNAKE_BLADE, hasSnakeBlade);
            tag.putInt(NBT_LAST_ID, lastSnakeBladeId);
            if (lastSnakeBladeUuid != null) {
                tag.putUUID(NBT_LAST_UUID, lastSnakeBladeUuid);
            }
            return tag;
        }

        public void deserializeNBT(CompoundTag tag) {
            hasSnakeBlade = tag.getBoolean(NBT_HAS_SNAKE_BLADE);
            lastSnakeBladeId = tag.contains(NBT_LAST_ID) ? tag.getInt(NBT_LAST_ID) : -1;
            lastSnakeBladeUuid = tag.hasUUID(NBT_LAST_UUID) ? tag.getUUID(NBT_LAST_UUID) : null;
        }
    }
}
