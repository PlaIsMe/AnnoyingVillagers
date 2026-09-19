package com.pla.annoyingvillagers.clazz;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public enum ProjectileBreakableBlocks {
    GLASS_LIKE(
            s -> neverBreak(s) && (
                    s.getBlock() instanceof TransparentBlock
                            || s.getBlock() instanceof StainedGlassPaneBlock
            ),
            0.10f, 0.20f
    ),

    STONE_LIKE(
            s -> neverBreak(s)
                    && s.is(BlockTags.MINEABLE_WITH_PICKAXE)
                    && !isOre(s)
                    && !s.is(BlockTags.DRAGON_IMMUNE)
                    && !s.is(BlockTags.WITHER_IMMUNE),
            0.85f, 1.00f
    ),

    WOOD_LIKE(
            s -> neverBreak(s) && (s.is(BlockTags.LOGS) || s.is(BlockTags.LOGS_THAT_BURN)
                    || s.is(BlockTags.PLANKS)
                    || s.is(BlockTags.WOODEN_DOORS)
                    || s.is(BlockTags.WOODEN_TRAPDOORS)
                    || s.is(BlockTags.WOODEN_FENCES)
                    || s.is(BlockTags.FENCE_GATES)
                    || s.is(BlockTags.WOODEN_STAIRS)
                    || s.is(BlockTags.WOODEN_SLABS)
                    || s.is(BlockTags.WOODEN_BUTTONS)
                    || s.is(BlockTags.WOODEN_PRESSURE_PLATES)
                    || s.is(BlockTags.ALL_SIGNS) || s.is(BlockTags.ALL_HANGING_SIGNS)
                    || s.is(Blocks.CRAFTING_TABLE)),
            0.55f, 0.50f
    ),

    SOFT_GROUND(
            s -> neverBreak(s) && (s.is(BlockTags.DIRT)
                    || s.is(BlockTags.SAND)
                    || s.is(BlockTags.SNOW)
                    || s.is(Blocks.GRAVEL)
                    || s.is(BlockTags.MOSS_REPLACEABLE)
                    || s.is(BlockTags.LUSH_GROUND_REPLACEABLE)),
            0.85f, 1.00f
    ),

    PLANTS(
            s -> neverBreak(s) && (s.is(BlockTags.LEAVES)
                    || s.is(BlockTags.FLOWERS) || s.is(BlockTags.SMALL_FLOWERS) || s.is(BlockTags.TALL_FLOWERS)
                    || s.is(BlockTags.CROPS)
                    || s.is(BlockTags.SAPLINGS)
                    || s.is(BlockTags.CAVE_VINES)
                    || s.is(Blocks.VINE)
                    || s.is(Blocks.CACTUS)),
            0.05f, 0.08f
    );

    public final Predicate<BlockState> matcher;
    public final float requiredPower;
    public final float powerCost;

    ProjectileBreakableBlocks(Predicate<BlockState> matcher, float requiredPower, float powerCost) {
        this.matcher = matcher;
        this.requiredPower = requiredPower;
        this.powerCost = powerCost;
    }

    public static ProjectileBreakableBlocks find(BlockState state) {
        for (ProjectileBreakableBlocks rule : values()) {
            if (rule.matcher.test(state)) return rule;
        }
        return null;
    }

    private static boolean isOre(BlockState s) {
        return s.is(BlockTags.COAL_ORES)
                || s.is(BlockTags.IRON_ORES)
                || s.is(BlockTags.GOLD_ORES)
                || s.is(BlockTags.DIAMOND_ORES)
                || s.is(BlockTags.REDSTONE_ORES)
                || s.is(BlockTags.LAPIS_ORES)
                || s.is(BlockTags.EMERALD_ORES)
                || s.is(BlockTags.COPPER_ORES);
    }

    private static boolean neverBreak(BlockState s) {
        return !s.is(Blocks.BEDROCK)
                && !s.is(Blocks.OBSIDIAN)
                && !s.is(Blocks.CRYING_OBSIDIAN)
                && !isSurfaceGrassLike(s);
    }

    private static boolean isSurfaceGrassLike(BlockState s) {
        return s.is(Blocks.GRASS_BLOCK)
                || s.is(Blocks.PODZOL)
                || s.is(Blocks.MYCELIUM)
                || s.is(Blocks.DIRT_PATH)
                || s.is(Blocks.SAND);
    }
}
