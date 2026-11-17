package com.pla.annoyinglib.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

/** Soil substitutions never touch authored masonry; excavation air is deliberately retained. */
public final class TerrainProcessor extends StructureProcessor {
    public static final Codec<TerrainProcessor> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("mode").forGetter(p -> p.mode),
            Codec.INT.fieldOf("min_x").forGetter(p -> p.minX),
            Codec.INT.fieldOf("min_z").forGetter(p -> p.minZ),
            Codec.INT.fieldOf("width").forGetter(p -> p.width),
            Codec.INT_STREAM.xmap(s -> s.toArray(), Arrays::stream).fieldOf("height_offsets").forGetter(p -> p.offsets)
    ).apply(i, TerrainProcessor::new));
    private final String mode;
    private final int minX, minZ, width;
    private final int[] offsets;

    public TerrainProcessor(String mode, int minX, int minZ, int width, int[] offsets) {
        this.mode = mode; this.minX = minX; this.minZ = minZ; this.width = width; this.offsets = offsets;
    }

    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos pivot,
            StructureTemplate.StructureBlockInfo original, StructureTemplate.StructureBlockInfo placed,
            StructurePlaceSettings settings, StructureTemplate template) {
        BlockPos pos = placed.pos();
        if (offsets.length > 0 && width > 0) {
            int x = pos.getX() - minX, z = pos.getZ() - minZ;
            int index = z * width + x;
            if (x >= 0 && x < width && z >= 0 && index < offsets.length) pos = pos.above(offsets[index]);
        }
        BlockState state = placed.state();
        if (state.is(Blocks.STRUCTURE_BLOCK) || state.is(Blocks.STRUCTURE_VOID) || state.is(Blocks.JIGSAW)) return null;
        if (mode.equals("submerged") && state.isAir()) state = Blocks.WATER.defaultBlockState();
        if (state.is(Blocks.GRASS_BLOCK)) state = TerrainMaterials.surface(level, pos);
        else if (state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT)) state = TerrainMaterials.fill(level, pos);
        return new StructureTemplate.StructureBlockInfo(pos, state, placed.nbt());
    }

    @Override protected StructureProcessorType<?> getType() { return StructureWorldgen.TERRAIN_PROCESSOR.get(); }

    @Override
    public StructureTemplate.StructureEntityInfo processEntity(LevelReader level, BlockPos origin,
            StructureTemplate.StructureEntityInfo original, StructureTemplate.StructureEntityInfo placed,
            StructurePlaceSettings settings, StructureTemplate template) {
        CompoundTag tag = placed.nbt.copy();
        // Frames/paintings retain absolute source-world attachment coordinates in saved NBT.
        // Forge transforms the structure entity position before this hook, so update its attachment too.
        if (tag.contains("TileX") && tag.contains("TileY") && tag.contains("TileZ")) {
            tag.putInt("TileX", placed.blockPos.getX()); tag.putInt("TileY", placed.blockPos.getY()); tag.putInt("TileZ", placed.blockPos.getZ());
        }
        return new StructureTemplate.StructureEntityInfo(placed.pos, placed.blockPos, tag);
    }
}
