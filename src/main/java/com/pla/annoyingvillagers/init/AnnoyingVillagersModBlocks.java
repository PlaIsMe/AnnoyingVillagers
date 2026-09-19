package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AnnoyingVillagersModBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK, AnnoyingVillagers.MODID);
    public static final DeferredHolder<Block, FractureBlock> FRACTURE_BLOCK = REGISTRY.register("fracture_block", () -> new FractureBlock(BlockBehaviour.Properties.of()));
    public static final DeferredHolder<Block, Block> ENCHANT_BED = AnnoyingVillagersModBlocks.REGISTRY.register("enchant_bed", EnchantBedBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_SHORT_PILLAR = AnnoyingVillagersModBlocks.REGISTRY.register("shadow_obsidian_short_pillar", ShadowObsidianShortPillarBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_MIDDLE_PILLAR = AnnoyingVillagersModBlocks.REGISTRY.register("shadow_obsidian_middle_pillar", ShadowObsidianMiddlePillarBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_LONG_PILLAR = AnnoyingVillagersModBlocks.REGISTRY.register("shadow_obsidian_long_pillar", ShadowObsidianLongPillarBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_BLOCK = AnnoyingVillagersModBlocks.REGISTRY.register("shadow_obsidian", ShadowObsidianBlock::new);
    public static final DeferredHolder<Block, Block> OBSIDIAN_BLOCK = AnnoyingVillagersModBlocks.REGISTRY.register("obsidian", ObsidianBlock::new);
    public static final DeferredHolder<Block, Block> CRYING_OBSIDIAN_BLOCK = AnnoyingVillagersModBlocks.REGISTRY.register("crying_obsidian", CryingObsidianBlock::new);
    public static final DeferredHolder<Block, EndFireBlock> END_FIRE = AnnoyingVillagersModBlocks.REGISTRY.register(
            "end_fire",
            () -> new EndFireBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .replaceable().noCollission().instabreak()
                            .lightLevel(s -> 15)
                            .sound(SoundType.WOOL)
                            .pushReaction(PushReaction.DESTROY)
            )
    );
}
