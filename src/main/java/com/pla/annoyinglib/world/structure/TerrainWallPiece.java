package com.pla.annoyinglib.world.structure;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraftforge.common.world.PieceBeardifierModifier;

/** Per-column perimeter outside an existing village. No template/pool replacement or noise flattening. */
public final class TerrainWallPiece extends StructurePiece implements PieceBeardifierModifier {
    private final boolean alongX;
    private final int gate,height;
    public TerrainWallPiece(BoundingBox box,boolean alongX,int gate,int height){
        super(StructureWorldgen.TERRAIN_WALL.get(),0,box);this.alongX=alongX;this.gate=gate;this.height=height;
    }
    public TerrainWallPiece(CompoundTag tag){super(StructureWorldgen.TERRAIN_WALL.get(),tag);alongX=tag.getBoolean("AlongX");gate=tag.getInt("Gate");height=tag.getInt("Height");}
    @Override public TerrainAdjustment getTerrainAdjustment(){return TerrainAdjustment.NONE;}
    @Override public BoundingBox getBeardifierBox(){return boundingBox;}
    @Override public int getGroundLevelDelta(){return 0;}
    @Override public void postProcess(WorldGenLevel level,StructureManager manager,ChunkGenerator generator,RandomSource random,BoundingBox clip,ChunkPos chunk,BlockPos pivot){
        Map<Long,Integer> floors=new HashMap<>();
        for(int x=Math.max(clip.minX(),boundingBox.minX());x<=Math.min(clip.maxX(),boundingBox.maxX());x++)
            for(int z=Math.max(clip.minZ(),boundingBox.minZ());z<=Math.min(clip.maxZ(),boundingBox.maxZ());z++)
                floors.put(BlockPos.asLong(x,0,z),level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG,x,z));
        floors.forEach((key,floor)->{
            BlockPos column=BlockPos.of(key);int lane=alongX?column.getX():column.getZ();boolean opening=Math.abs(lane-gate)<=3;
            for(int dy=-8;dy<height;dy++) {
                BlockPos p=column.atY(floor+dy);if(!clip.isInside(p))continue;
                if(opening&&dy>=0&&dy<6){if(TerrainMaterials.natural(level.getBlockState(p)))level.setBlock(p,Blocks.AIR.defaultBlockState(),2);continue;}
                if(dy<0&&level.getBlockState(p).canOcclude())continue;
                if(level.getBlockEntity(p)!=null)continue;
                level.setBlock(p,opening&&dy==-1?Blocks.STONE_BRICKS.defaultBlockState():Blocks.OBSIDIAN.defaultBlockState(),2);
            }
            BlockPos top=column.atY(floor+height);
            if(clip.isInside(top))level.setBlock(top,Math.floorMod(lane,4)<2?Blocks.STONE_BRICKS.defaultBlockState():Blocks.STONE_BRICK_SLAB.defaultBlockState(),2);
        });
    }
    @Override protected void addAdditionalSaveData(StructurePieceSerializationContext context,CompoundTag tag){tag.putBoolean("AlongX",alongX);tag.putInt("Gate",gate);tag.putInt("Height",height);}
}
