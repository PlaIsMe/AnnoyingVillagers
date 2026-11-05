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

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(AnnoyingVillagers.MODID);
    public static final DeferredHolder<Block, FractureBlock> FRACTURE_BLOCK = REGISTRY.registerBlock("fracture_block", FractureBlock::new);
    public static final DeferredHolder<Block, Block> ENCHANT_BED = REGISTRY.registerBlock("enchant_bed", EnchantBedBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_SHORT_PILLAR = REGISTRY.registerBlock("shadow_obsidian_short_pillar", ShadowObsidianShortPillarBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_MIDDLE_PILLAR = REGISTRY.registerBlock("shadow_obsidian_middle_pillar", ShadowObsidianMiddlePillarBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_LONG_PILLAR = REGISTRY.registerBlock("shadow_obsidian_long_pillar", ShadowObsidianLongPillarBlock::new);
    public static final DeferredHolder<Block, Block> SHADOW_OBSIDIAN_BLOCK = REGISTRY.registerBlock("shadow_obsidian", ShadowObsidianBlock::new);
    public static final DeferredHolder<Block, Block> OBSIDIAN_BLOCK = REGISTRY.registerBlock("obsidian", ObsidianBlock::new);
    public static final DeferredHolder<Block, Block> CRYING_OBSIDIAN_BLOCK = REGISTRY.registerBlock("crying_obsidian", CryingObsidianBlock::new);
    public static final DeferredHolder<Block, EndFireBlock> END_FIRE = REGISTRY.registerBlock(
            "end_fire",
            EndFireBlock::new,
            properties -> properties
                            .mapColor(MapColor.COLOR_PURPLE)
                            .replaceable().noCollision().instabreak()
                            .lightLevel(s -> 15)
                            .sound(SoundType.WOOL)
                            .pushReaction(PushReaction.DESTROY)
    );
}
