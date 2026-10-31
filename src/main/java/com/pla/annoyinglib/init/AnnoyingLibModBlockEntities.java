package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.blockentity.FractureBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AnnoyingLibModBlockEntities {
    private AnnoyingLibModBlockEntities() {}
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AnnoyingLib.MODID);

    public static final RegistryObject<BlockEntityType<FractureBlockEntity>> FRACTURE_BLOCK = REGISTRY.register("fracture_block", () -> BlockEntityType.Builder.of(FractureBlockEntity::new, AnnoyingLibModBlocks.FRACTURE_BLOCK.get()).build(null));







    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}