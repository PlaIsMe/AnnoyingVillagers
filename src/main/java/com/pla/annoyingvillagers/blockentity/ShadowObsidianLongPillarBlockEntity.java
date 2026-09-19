package com.pla.annoyingvillagers.blockentity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class ShadowObsidianLongPillarBlockEntity extends BlockEntity {
    private @Nullable UUID owner;

    public ShadowObsidianLongPillarBlockEntity(BlockPos pos, BlockState state) {
        super(AnnoyingVillagersModBlockEntities.SHADOW_OBSIDIAN_LONG_PILLAR.get(), pos, state);
    }

    public void setOwner(@Nullable UUID id) {
        this.owner = id;
        setChanged();
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (owner != null) tag.putUUID("Owner", owner);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        if (owner != null) tag.putUUID("Owner", owner);
        return tag;
    }
}
