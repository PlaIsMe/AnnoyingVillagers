package com.pla.annoyinglib.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class TerrainCompoundStructure extends Structure {
    public static final Codec<TerrainCompoundStructure> CODEC = RecordCodecBuilder.create(i -> i.group(
            settingsCodec(i), LayoutSpec.CODEC.fieldOf("layout").forGetter(s -> s.layout)
    ).apply(i, TerrainCompoundStructure::new));
    private final LayoutSpec layout;

    public TerrainCompoundStructure(StructureSettings settings, LayoutSpec layout) { super(settings); this.layout = layout; }
    public LayoutSpec layout() { return layout; }

    private final java.util.concurrent.ConcurrentMap<Integer, Optional<LayoutPlanner.Plan>> geometry = new java.util.concurrent.ConcurrentHashMap<>();

    /** Pure geometry cache: no chunk access, terrain noise, or structure-template loading. */
    public Optional<LayoutPlanner.Plan> geometry(long seed) {
        int variant = (int)Math.floorMod(seed, 8L);
        return geometry.computeIfAbsent(variant, key -> {
            LayoutPlanner.Terrain flat = new LayoutPlanner.Terrain() {
                public int surface(int x,int z) { return 80; }
                public int floor(int x,int z) {
                    if(layout.mode().equals("ship")||layout.mode().equals("submerged"))return 40;
                    if(layout.mode().equals("bridge")&&Math.abs(z)<30)return 50;
                    return 80;
                }
                public int minY(){return -64;}
                public int maxY(){return 512;}
            };
            for(int attempt=0;attempt<32;attempt++) {
                var result=new LayoutPlanner(layout,BlockPos.ZERO,Rotation.NONE,
                        net.minecraft.util.RandomSource.create(key+attempt*8L),flat).plan();
                if(result.isPresent())return result;
            }
            return Optional.empty();
        });
    }

    public java.util.List<net.minecraft.world.level.levelgen.structure.StructurePiece> pieces(
            LayoutPlanner.Plan plan,BlockPos center,Rotation rotation,int forcedFloor,int minY,int maxY) {
        java.util.List<net.minecraft.world.level.levelgen.structure.StructurePiece> pieces=new java.util.ArrayList<>();
        Map<String, net.minecraft.world.level.levelgen.structure.BoundingBox> clusters=new HashMap<>();
        Map<String,Integer> ceilings=new HashMap<>();
        for(var placed:plan.pieces())if(!placed.part().cluster().isEmpty())
        {
            clusters.merge(placed.part().cluster(),placed.box(),LayoutPlanner::union);
            var part=placed.part();ceilings.merge(part.cluster(),maxY-part.size().getY()-part.offset().getY()+part.anchor()-2,Math::min);
        }
        for(var placed:plan.pieces()) {
            BlockPos anchor=placed.box().getCenter().atY(0);
            if(!placed.part().cluster().isEmpty())anchor=clusters.get(placed.part().cluster()).getCenter().atY(0);
            else if(!placed.part().doors().isEmpty())anchor=placed.transform(placed.part().doors().get(0).pos()).atY(0);
            pieces.add(new DeferredTerrainPiece(placed.part(),center.atY(0).offset(placed.origin().atY(0).rotate(rotation)),
                    center.atY(0).offset(anchor.rotate(rotation)),rotation.getRotated(placed.rotation()),layout.mode(),
                    layout.foundationDepth(),forcedFloor,minY,maxY,ceilings.getOrDefault(placed.part().cluster(),
                            maxY-placed.part().size().getY()-placed.part().offset().getY()+placed.part().anchor()-2)));
        }
        if(!plan.paths().isEmpty()) {
            boolean terrain=forcedFloor==DeferredTerrainPiece.NATURAL_FLOOR;
            var paths=plan.paths().stream().map(p->center.atY(0).offset(p.atY(0).rotate(rotation)).atY(
                    terrain?p.getY()-79:forcedFloor+p.getY()-80)).toList();
            pieces.add(new TerrainPathPiece(paths,terrain,minY,maxY));
        }
        return pieces;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos center=new BlockPos(context.chunkPos().getMiddleBlockX(),context.chunkGenerator().getSeaLevel()+1,context.chunkPos().getMiddleBlockZ());
        if(!context.validBiome().test(context.biomeSource().getNoiseBiome(QuartPos.fromBlock(center.getX()),
                QuartPos.fromBlock(center.getY()),QuartPos.fromBlock(center.getZ()),context.randomState().sampler())))return Optional.empty();
        Rotation rotation=Rotation.getRandom(context.random());
        var plan=geometry(context.random().nextLong());
        if(plan.isEmpty()||!waterSite(context,plan.get(),center,rotation))return Optional.empty();
        return Optional.of(new GenerationStub(center,builder->pieces(plan.get(),center,rotation,
                DeferredTerrainPiece.NATURAL_FLOOR,context.heightAccessor().getMinBuildHeight(),context.heightAccessor().getMaxBuildHeight()).forEach(builder::addPiece)));
    }

    private boolean waterSite(GenerationContext context,LayoutPlanner.Plan plan,BlockPos center,Rotation rotation) {
        if(!java.util.List.of("ship","submerged","bridge").contains(layout.mode()))return true;
        NoiseTerrain terrain=new NoiseTerrain(context);
        for(var p:plan.pieces()) {
            if(layout.mode().equals("bridge")&&!p.part().role().equals("bridge"))continue;
            Rotation turn=rotation.getRotated(p.rotation());
            BlockPos origin=center.atY(0).offset(p.origin().atY(0).rotate(rotation));
            if(p.part().role().equals("bridge")) {
                int length=p.part().size().getZ(),x=p.part().size().getX()/2,wet=0;
                BlockPos a=origin.offset(new BlockPos(x,0,1).rotate(turn)),b=origin.offset(new BlockPos(x,0,length-2).rotate(turn));
                if(terrain.surface(a.getX(),a.getZ())!=terrain.floor(a.getX(),a.getZ())||terrain.surface(b.getX(),b.getZ())!=terrain.floor(b.getX(),b.getZ())
                        ||Math.abs(terrain.floor(a.getX(),a.getZ())-terrain.floor(b.getX(),b.getZ()))>6)return false;
                for(int z=8;z<length-8;z+=8){BlockPos q=origin.offset(new BlockPos(x,0,z).rotate(turn));if(terrain.surface(q.getX(),q.getZ())-terrain.floor(q.getX(),q.getZ())>=4)wet+=8;}
                if(wet<32)return false;
            } else {
                int required=Math.max(layout.minWaterDepth(),layout.mode().equals("ship")?p.part().anchor()+2:p.part().size().getY()-p.part().anchor()+2);
                for(int x:new int[]{0,p.part().size().getX()/2,p.part().size().getX()-1})for(int z:new int[]{0,p.part().size().getZ()-1}) {
                    BlockPos q=origin.offset(new BlockPos(x,0,z).rotate(turn));
                    if(terrain.surface(q.getX(),q.getZ())-terrain.floor(q.getX(),q.getZ())<required)return false;
                }
            }
        }
        return true;
    }

    @Override public StructureType<?> type() { return StructureWorldgen.TERRAIN_COMPOUND.get(); }

    private static final class SamplingLimit extends RuntimeException {
        private SamplingLimit() { super(null, null, false, false); }
    }

    public static final class NoiseTerrain implements LayoutPlanner.Terrain {
        private final GenerationContext context;
        private final Map<Long, Integer> floors = new HashMap<>(), surfaces = new HashMap<>();
        private int samples;
        public NoiseTerrain(GenerationContext context) { this.context = context; }
        private int height(int x, int z, Heightmap.Types type, Map<Long, Integer> cache) {
            return cache.computeIfAbsent(BlockPos.asLong(x, 0, z), key -> {
                if (++samples > 4096 && context.chunkGenerator() instanceof NoiseBasedChunkGenerator) throw new SamplingLimit();
                return context.chunkGenerator().getFirstFreeHeight(x, z, type, context.heightAccessor(), context.randomState());
            });
        }
        @Override public int floor(int x, int z) { return height(x, z, Heightmap.Types.OCEAN_FLOOR_WG, floors); }
        @Override public int surface(int x, int z) { return height(x, z, Heightmap.Types.WORLD_SURFACE_WG, surfaces); }
        @Override public int minY() { return context.heightAccessor().getMinBuildHeight(); }
        @Override public int maxY() { return context.heightAccessor().getMaxBuildHeight(); }
    }
}
