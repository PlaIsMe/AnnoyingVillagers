package com.pla.annoyinglib.world.structure;

import com.pla.annoyinglib.AnnoyingLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class StructureWorldgen {
    private static final DeferredRegister<StructureType<?>> TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, AnnoyingLib.MODID);
    private static final DeferredRegister<StructurePieceType> PIECES = DeferredRegister.create(Registries.STRUCTURE_PIECE, AnnoyingLib.MODID);
    private static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, AnnoyingLib.MODID);
    public static final RegistryObject<StructureType<TerrainCompoundStructure>> TERRAIN_COMPOUND = TYPES.register("terrain_compound", () -> () -> TerrainCompoundStructure.CODEC);
    public static final RegistryObject<StructureType<ChoiceCompoundStructure>> CHOICE_COMPOUND = TYPES.register("choice_compound", () -> () -> ChoiceCompoundStructure.CODEC);
    public static final RegistryObject<StructurePieceType> TERRAIN_TEMPLATE = PIECES.register("terrain_template", () -> (context, tag) -> new TerrainTemplatePiece(context.structureTemplateManager(), tag));
    public static final RegistryObject<StructurePieceType> TERRAIN_PATH = PIECES.register("terrain_path", () -> (context, tag) -> new TerrainPathPiece(tag));
    public static final RegistryObject<StructurePieceType> DEFERRED_TEMPLATE = PIECES.register("deferred_template", () -> (context, tag) -> new DeferredTerrainPiece(tag));
    public static final RegistryObject<StructurePieceType> TERRAIN_WALL = PIECES.register("terrain_wall", () -> (context, tag) -> new TerrainWallPiece(tag));
    public static final RegistryObject<StructureProcessorType<TerrainProcessor>> TERRAIN_PROCESSOR = PROCESSORS.register("terrain_surface", () -> () -> TerrainProcessor.CODEC);

    private StructureWorldgen() {}

    public static void register(IEventBus bus) {
        TYPES.register(bus);
        PIECES.register(bus);
        PROCESSORS.register(bus);
    }
}
