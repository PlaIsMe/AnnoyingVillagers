package com.pla.annoyinglib.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import java.util.List;

/** Locate/start checks retain only geometry. NBT and exact floor heights are resolved during placement. */
public final class DeferredTerrainPiece extends StructurePiece {
    public static final int NATURAL_FLOOR = Integer.MIN_VALUE;
    private final LayoutSpec.Part part;
    private final BlockPos horizontal, anchor;
    private final Rotation rotation;
    private final String mode;
    private final int foundation, forcedFloor, maxFloor;
    private CompoundTag savedPlacement;
    private TerrainTemplatePiece delegate;

    public DeferredTerrainPiece(LayoutSpec.Part part, BlockPos horizontal, BlockPos anchor, Rotation rotation,
            String mode, int foundation, int forcedFloor, int minY, int maxY, int maxFloor) {
        super(StructureWorldgen.DEFERRED_TEMPLATE.get(), 0, bounds(part, horizontal, rotation, minY, maxY));
        this.part=part;this.horizontal=horizontal;this.anchor=anchor;this.rotation=rotation;
        this.mode=mode;this.foundation=foundation;this.forcedFloor=forcedFloor;this.maxFloor=maxFloor;
    }
    public DeferredTerrainPiece(CompoundTag tag) {
        super(StructureWorldgen.DEFERRED_TEMPLATE.get(), tag);
        part=LayoutSpec.Part.CODEC.parse(NbtOps.INSTANCE,tag.get("Part")).getOrThrow(false,s->{throw new IllegalArgumentException(s);});
        horizontal=BlockPos.of(tag.getLong("Origin"));anchor=BlockPos.of(tag.getLong("Anchor"));rotation=Rotation.valueOf(tag.getString("Rotation"));
        mode=tag.getString("Mode");foundation=tag.getInt("Foundation");forcedFloor=tag.getInt("ForcedFloor");
        maxFloor=tag.contains("MaxFloor")?tag.getInt("MaxFloor"):boundingBox.maxY()-part.size().getY()-part.offset().getY()+part.anchor()-1;
        if(tag.contains("Placed"))savedPlacement=tag.getCompound("Placed");
    }
    private static BoundingBox bounds(LayoutSpec.Part p,BlockPos origin,Rotation rot,int minY,int maxY) {
        BoundingBox b=LayoutPlanner.box(origin.atY(0),p.size(),rot);
        return new BoundingBox(b.minX(),minY,b.minZ(),b.maxX(),maxY-1,b.maxZ());
    }
    private int height(WorldGenLevel level,ChunkGenerator generator,BlockPos p,Heightmap.Types type) {
        return generator.getFirstFreeHeight(p.getX(),p.getZ(),type,level,level.getLevel().getChunkSource().randomState());
    }
    private TerrainTemplatePiece resolve(WorldGenLevel level,ChunkGenerator generator) {
        StructureTemplateManager manager=level.getLevel().getStructureManager();
        if(delegate!=null)return delegate;
        if(savedPlacement!=null)return delegate=new TerrainTemplatePiece(manager,savedPlacement);
        int floor=forcedFloor;
        if(floor==NATURAL_FLOOR) {
            Heightmap.Types type=mode.equals("ship")?Heightmap.Types.WORLD_SURFACE_WG:Heightmap.Types.OCEAN_FLOOR_WG;
            floor=height(level,generator,anchor,type);
            if(part.role().equals("wall")||mode.equals("submerged")||mode.equals("airship")) {
                for(int x:new int[]{boundingBox.minX(),boundingBox.maxX()})for(int z:new int[]{boundingBox.minZ(),boundingBox.maxZ()})
                    floor=Math.max(floor,height(level,generator,new BlockPos(x,0,z),type));
            }
            if(mode.equals("airship"))floor+=28;
            if(part.role().equals("bridge")) {
                BlockPos end=horizontal.offset(new BlockPos(part.size().getX()/2,0,part.size().getZ()-2).rotate(rotation));
                BlockPos start=horizontal.offset(new BlockPos(part.size().getX()/2,0,1).rotate(rotation));
                floor=Math.max(height(level,generator,start,Heightmap.Types.OCEAN_FLOOR_WG),height(level,generator,end,Heightmap.Types.OCEAN_FLOOR_WG))+1;
            }
        } else if(mode.equals("airship"))floor+=28;
        floor=Math.min(floor,maxFloor);
        BlockPos pos=horizontal.atY(floor+part.offset().getY()-part.anchor());
        var placed=new LayoutPlanner.Placed(part,pos,rotation,LayoutPlanner.box(pos,part.size(),rotation),floor);
        var spec=new LayoutSpec(mode,List.of(part),List.of(),0,0,32,part.role().equals("wall")?Math.max(64,foundation):foundation,0,false,List.of());
        return delegate=new TerrainTemplatePiece(manager,placed,spec,null);
    }
    @Override public void postProcess(WorldGenLevel level,StructureManager manager,ChunkGenerator generator,RandomSource random,BoundingBox clip,ChunkPos chunk,BlockPos pivot) {
        resolve(level,generator).postProcess(level,manager,generator,random,clip,chunk,pivot);
    }
    @Override protected void addAdditionalSaveData(StructurePieceSerializationContext context,CompoundTag tag) {
        tag.put("Part",LayoutSpec.Part.CODEC.encodeStart(NbtOps.INSTANCE,part).getOrThrow(false,s->{throw new IllegalArgumentException(s);}));
        tag.putLong("Origin",horizontal.asLong());tag.putLong("Anchor",anchor.asLong());tag.putString("Rotation",rotation.name());
        tag.putString("Mode",mode);tag.putInt("Foundation",foundation);tag.putInt("ForcedFloor",forcedFloor);
        tag.putInt("MaxFloor",maxFloor);
        if(delegate!=null)tag.put("Placed",delegate.createTag(context));else if(savedPlacement!=null)tag.put("Placed",savedPlacement);
    }
}
