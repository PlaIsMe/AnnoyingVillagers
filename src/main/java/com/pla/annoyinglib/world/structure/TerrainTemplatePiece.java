package com.pla.annoyinglib.world.structure;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public final class TerrainTemplatePiece extends TemplateStructurePiece {
    private final String mode, role;
    private final int floorY, foundationDepth;
    private final int[] heightOffsets;
    private final boolean[] footprint;
    private final BlockState wallMaterial;

    public TerrainTemplatePiece(StructureTemplateManager manager, LayoutPlanner.Placed part, LayoutSpec spec, LayoutPlanner.Terrain terrain) {
        super(StructureWorldgen.TERRAIN_TEMPLATE.get(), 0, manager, part.part().template(), part.part().template().toString(), settings(part.rotation()), part.origin());
        this.mode = spec.mode(); this.role = part.part().role(); this.floorY = part.floorY(); this.foundationDepth = spec.foundationDepth();
        this.heightOffsets = mode.equals("scar") ? new int[boundingBox.getXSpan() * boundingBox.getZSpan()] : new int[0];
        this.footprint = footprint();
        this.wallMaterial = wallMaterial();
        configure();
    }

    public TerrainTemplatePiece(StructureTemplateManager manager, CompoundTag tag) {
        super(StructureWorldgen.TERRAIN_TEMPLATE.get(), tag, manager, id -> settings(Rotation.valueOf(tag.getString("Rot"))));
        mode = tag.getString("Mode"); role = tag.getString("Role"); floorY = tag.getInt("FloorY"); foundationDepth = tag.getInt("FoundationDepth");
        heightOffsets = tag.getIntArray("HeightOffsets"); footprint = footprint(); wallMaterial = wallMaterial(); configure();
    }

    private static StructurePlaceSettings settings(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation).setIgnoreEntities(false).setFinalizeEntities(true);
    }

    private void configure() {
        BoundingBox original = template.getBoundingBox(placeSettings, templatePosition);
        placeSettings.setKeepLiquids(!mode.equals("ship") && !mode.equals("scar"));
        placeSettings.addProcessor(new TerrainProcessor(mode, original.minX(), original.minZ(), original.getXSpan(), heightOffsets));
        // Excavation reads the already-generated local chunk heightmap at placement time.
        // A fixed margin keeps saved bounds stable before/after partial chunk generation.
        int low = mode.equals("scar") ? -32 : 0, high = mode.equals("scar") ? 32 : 0;
        boundingBox = new BoundingBox(original.minX(), Math.min(original.minY() + low, floorY - foundationDepth), original.minZ(),
                original.maxX(), original.maxY() + high, original.maxZ());
    }

    private boolean[] footprint() {
        int width = template.getSize().getX(), depth = template.getSize().getZ();
        boolean[] mask = new boolean[width * depth];
        CompoundTag tag = template.save(new CompoundTag());
        ListTag blocks = tag.getList("blocks", Tag.TAG_COMPOUND);
        for (int i = 0; i < blocks.size(); i++) {
            ListTag pos = blocks.getCompound(i).getList("pos", Tag.TAG_INT);
            // Only columns reaching the floor support the building; upper castle tiles never make pillars.
            if (templatePosition.getY() + pos.getInt(1) <= floorY + 1) mask[pos.getInt(2) * width + pos.getInt(0)] = true;
        }
        return mask;
    }

    private BlockState wallMaterial() {
        if (!role.equals("wall")) return Blocks.COBBLESTONE.defaultBlockState();
        CompoundTag tag = template.save(new CompoundTag());
        ListTag palette = tag.getList("palette", Tag.TAG_COMPOUND);
        ListTag blocks = tag.getList("blocks", Tag.TAG_COMPOUND);
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < blocks.size(); i++) {
            String name = palette.getCompound(blocks.getCompound(i).getInt("state")).getString("Name");
            if (name.equals("minecraft:obsidian") || name.endsWith("_planks") || name.endsWith("_log")
                    || name.equals("minecraft:stone_bricks") || name.equals("minecraft:cobblestone")) counts.merge(name, 1, Integer::sum);
        }
        String name = counts.entrySet().stream().max(Map.Entry.<String,Integer>comparingByValue().thenComparing(Map.Entry::getKey))
                .map(Map.Entry::getKey).orElse("minecraft:cobblestone");
        return BuiltInRegistries.BLOCK.get(ResourceLocation.parse(name)).defaultBlockState();
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager structures, ChunkGenerator generator, RandomSource random,
                            BoundingBox clip, ChunkPos chunk, BlockPos pivot) {
        boolean support = !mode.equals("scar") && !mode.equals("ship") && !mode.equals("airship") && !role.equals("interior");
        if (support) support(level, clip);
        if (mode.equals("scar")) {
            BoundingBox original = template.getBoundingBox(placeSettings, templatePosition);
            for (int z = Math.max(original.minZ(), clip.minZ()); z <= Math.min(original.maxZ(), clip.maxZ()); z++)
                for (int x = Math.max(original.minX(), clip.minX()); x <= Math.min(original.maxX(), clip.maxX()); x++)
                    heightOffsets[(z - original.minZ()) * original.getXSpan() + x - original.minX()] =
                            Math.max(-32, Math.min(32, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z) - floorY));
        }
        placeSettings.setBoundingBox(clip);
        template.placeInWorld(level, templatePosition, templatePosition, placeSettings, random, 2);
    }

    private void support(WorldGenLevel level, BoundingBox clip) {
        int width = template.getSize().getX(), depth = template.getSize().getZ();
        for (int z = 0; z < depth; z++) for (int x = 0; x < width; x++) {
            if (!footprint[z * width + x]) continue;
            if (role.equals("bridge") && z > 4 && z < depth - 5) continue; // Abutments only; preserve the river.
            BlockPos column = templatePosition.offset(new BlockPos(x, 0, z).rotate(getRotation())).atY(floorY - 1);
            if (!clip.isInside(column)) continue;
            BlockState fill = TerrainMaterials.fill(level, column);
            if (role.equals("wall")) fill = wallMaterial;
            if (role.equals("bridge")) fill = Blocks.COBBLESTONE.defaultBlockState();
            for (int down = 0; down < foundationDepth; down++) {
                BlockPos pos = column.below(down);
                if (!clip.isInside(pos) || pos.getY() <= level.getMinBuildHeight()) break;
                BlockState old = level.getBlockState(pos);
                if (old.canOcclude() && !old.is(Blocks.SNOW_BLOCK)) break;
                if (level.getBlockEntity(pos) != null || old.is(Blocks.BEDROCK)) break;
                level.setBlock(pos, down == 0 && !role.equals("wall") && !role.equals("bridge") ? TerrainMaterials.surface(level, pos) : fill, 2);
            }
        }
    }

    @Override protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rot", getRotation().name()); tag.putString("Mode", mode); tag.putString("Role", role);
        tag.putInt("FloorY", floorY); tag.putInt("FoundationDepth", foundationDepth); tag.putIntArray("HeightOffsets", heightOffsets);
    }

    @Override protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {}
}
