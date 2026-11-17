package com.pla.annoyinglib.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

final class TerrainMaterials {
    private TerrainMaterials() {}

    static BlockState surface(LevelReader level, BlockPos pos) {
        var biome = level.getBiome(pos);
        if (biome.is(BiomeTags.IS_BADLANDS)) return Blocks.RED_SAND.defaultBlockState();
        if (biome.unwrapKey().map(key -> key.location().getPath().equals("desert")).orElse(false)) return Blocks.SAND.defaultBlockState();
        return Blocks.GRASS_BLOCK.defaultBlockState();
    }

    static BlockState fill(LevelReader level, BlockPos pos) {
        BlockState top = surface(level, pos);
        if (top.is(Blocks.RED_SAND)) return Blocks.RED_SANDSTONE.defaultBlockState();
        if (top.is(Blocks.SAND)) return Blocks.SANDSTONE.defaultBlockState();
        return Blocks.DIRT.defaultBlockState();
    }

    static boolean natural(BlockState state) {
        return state.isAir() || state.is(BlockTags.DIRT) || state.is(BlockTags.BASE_STONE_OVERWORLD)
                || state.is(BlockTags.SAND) || state.is(BlockTags.LEAVES) || state.is(BlockTags.FLOWERS)
                || state.is(Blocks.GRAVEL) || state.is(Blocks.CLAY) || state.is(Blocks.SNOW)
                || state.is(Blocks.GRASS) || state.is(Blocks.TALL_GRASS) || state.is(Blocks.FERN)
                || state.is(Blocks.LARGE_FERN) || state.is(Blocks.DEAD_BUSH);
    }
}
