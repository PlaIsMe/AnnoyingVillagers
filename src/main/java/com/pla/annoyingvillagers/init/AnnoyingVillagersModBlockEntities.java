package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.blockentity.CryingObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.FractureBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianLongPillarBlockEntity;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class AnnoyingVillagersModBlockEntities {
    private AnnoyingVillagersModBlockEntities() {}
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AnnoyingVillagers.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FractureBlockEntity>> FRACTURE_BLOCK = REGISTRY.register("fracture_block", () -> new BlockEntityType<>(FractureBlockEntity::new, AnnoyingVillagersModBlocks.FRACTURE_BLOCK.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_SHORT_PILLAR =
            REGISTRY.register("shadow_obsidian_short_pillar",
                    () -> new BlockEntityType<>(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get()
                    ));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_MIDDLE_PILLAR =
            REGISTRY.register("shadow_obsidian_middle_pillar",
                    () -> new BlockEntityType<>(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                    ));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_LONG_PILLAR =
            REGISTRY.register("shadow_obsidian_long_pillar",
                    () -> new BlockEntityType<>(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                    ));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianBlockEntity>> SHADOW_OBSIDIAN_BLOCK =
            REGISTRY.register("shadow_obsidian",
                    () -> new BlockEntityType<>(
                            ShadowObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                    ));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ObsidianBlockEntity>> OBSIDIAN_BLOCK =
            REGISTRY.register("obsidian",
                    () -> new BlockEntityType<>(
                            ObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                    ));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CryingObsidianBlockEntity>> CRYING_OBSIDIAN_BLOCK =
            REGISTRY.register("crying_obsidian",
                    () -> new BlockEntityType<>(
                            CryingObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get()
                    ));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}
