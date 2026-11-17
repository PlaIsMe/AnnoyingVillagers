package com.pla.annoyinglib.world.structure;

import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraftforge.common.world.PieceBeardifierModifier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

public final class TerrainPathPiece extends StructurePiece implements PieceBeardifierModifier {
    private final List<BlockPos> positions;
    private final boolean followTerrain;
    @Override public TerrainAdjustment getTerrainAdjustment(){return TerrainAdjustment.NONE;}
    @Override public BoundingBox getBeardifierBox(){return boundingBox;}
    @Override public int getGroundLevelDelta(){return 0;}

    public TerrainPathPiece(List<BlockPos> positions) {
        this(positions,false,-64,320);
    }
    public TerrainPathPiece(List<BlockPos> positions,boolean followTerrain,int minY,int maxY) {
        super(StructureWorldgen.TERRAIN_PATH.get(), 0, bounds(positions)); this.positions = List.copyOf(positions);this.followTerrain=followTerrain;
        if(followTerrain)boundingBox=new BoundingBox(boundingBox.minX(),minY,boundingBox.minZ(),boundingBox.maxX(),maxY-1,boundingBox.maxZ());
    }

    public TerrainPathPiece(CompoundTag tag) {
        super(StructureWorldgen.TERRAIN_PATH.get(), tag); positions = Arrays.stream(tag.getLongArray("Path")).mapToObj(BlockPos::of).toList();followTerrain=tag.getBoolean("FollowTerrain");
    }

    private static BoundingBox bounds(List<BlockPos> positions) {
        BoundingBox box = BoundingBox.fromCorners(positions.get(0), positions.get(0));
        for (BlockPos pos : positions) box = LayoutPlanner.union(box, BoundingBox.fromCorners(pos.below(4), pos.offset(1, 3, 1)));
        return box;
    }

    @Override public void postProcess(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random,
                                      BoundingBox clip, ChunkPos chunk, BlockPos pivot) {
        java.util.Map<Long,Integer> ground=new java.util.HashMap<>();
        if(followTerrain)for(BlockPos p:positions)for(int dx=0;dx<=1;dx++)for(int dz=0;dz<=1;dz++) {
            BlockPos q=p.offset(dx,0,dz);
            if(q.getX()>=clip.minX()&&q.getX()<=clip.maxX()&&q.getZ()>=clip.minZ()&&q.getZ()<=clip.maxZ())
                ground.put(BlockPos.asLong(q.getX(),0,q.getZ()),level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.OCEAN_FLOOR_WG,q.getX(),q.getZ())-1);
        }
        for (BlockPos center : positions) for (int dx = 0; dx <= 1; dx++) for (int dz = 0; dz <= 1; dz++) {
            BlockPos p = center.offset(dx, 0, dz);
            if(followTerrain) {
                Integer y=ground.get(BlockPos.asLong(p.getX(),0,p.getZ()));
                if(y==null)continue;
                p=p.atY(y+center.getY());
            }
            if (!clip.isInside(p) || !TerrainMaterials.natural(level.getBlockState(p)) || level.getBlockEntity(p) != null) continue;
            BlockState surface = TerrainMaterials.surface(level, p);
            BlockState path = surface.is(Blocks.SAND) ? Blocks.SANDSTONE.defaultBlockState()
                    : surface.is(Blocks.RED_SAND) ? Blocks.RED_SANDSTONE.defaultBlockState() : Blocks.DIRT_PATH.defaultBlockState();
            for (int down = 1; down <= 4; down++) {
                BlockPos support = p.below(down);
                if (!clip.isInside(support) || level.getBlockState(support).canOcclude() || level.getBlockEntity(support) != null) break;
                level.setBlock(support, TerrainMaterials.fill(level, support), 2);
            }
            level.setBlock(p, path, 2);
            for (int up = 1; up <= 3; up++) {
                BlockPos head = p.above(up);
                if (clip.isInside(head) && TerrainMaterials.natural(level.getBlockState(head)) && level.getBlockEntity(head) == null) level.setBlock(head, Blocks.AIR.defaultBlockState(), 2);
            }
        }
    }

    @Override protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putLongArray("Path", positions.stream().mapToLong(BlockPos::asLong).toArray());
        tag.putBoolean("FollowTerrain",followTerrain);
    }
}
