package com.pla.annoyingvillagers.blockentity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class ShadowObsidianShortPillarBlockEntity extends BlockEntity {
    private @Nullable UUID owner;

    public ShadowObsidianShortPillarBlockEntity(BlockPos pos, BlockState state) {
        super(AnnoyingVillagersModBlockEntities.SHADOW_OBSIDIAN_SHORT_PILLAR.get(), pos, state);
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
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (owner != null) output.store("Owner", UUIDUtil.CODEC, owner);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        owner = input.read("Owner", UUIDUtil.CODEC).orElse(null);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        if (owner != null) tag.putIntArray("Owner", UUIDUtil.uuidToIntArray(owner));
        return tag;
    }
}
