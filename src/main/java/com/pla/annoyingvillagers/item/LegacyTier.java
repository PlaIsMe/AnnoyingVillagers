package com.pla.annoyingvillagers.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

/** Keeps the legacy numeric harvest levels while exposing the 1.21 tag-based tier contract. */
public interface LegacyTier extends Tier {
    int getLevel();

    @Override
    default TagKey<Block> getIncorrectBlocksForDrops() {
        return switch (getLevel()) {
            case 0 -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
            case 1 -> BlockTags.INCORRECT_FOR_STONE_TOOL;
            case 2 -> BlockTags.INCORRECT_FOR_IRON_TOOL;
            case 3 -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
            default -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        };
    }
}
