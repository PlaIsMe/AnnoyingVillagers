package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingLibModBlocks {

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, AnnoyingLib.MODID);
    public static final RegistryObject<FractureBlock> FRACTURE_BLOCK = REGISTRY.register("fracture_block", () -> new FractureBlock(BlockBehaviour.Properties.of()));

}
