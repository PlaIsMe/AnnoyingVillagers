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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FractureBlockEntity>> FRACTURE_BLOCK = REGISTRY.register("fracture_block", () -> BlockEntityType.Builder.of(FractureBlockEntity::new, AnnoyingVillagersModBlocks.FRACTURE_BLOCK.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_SHORT_PILLAR =
            REGISTRY.register("shadow_obsidian_short_pillar",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get()
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_MIDDLE_PILLAR =
            REGISTRY.register("shadow_obsidian_middle_pillar",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_LONG_PILLAR =
            REGISTRY.register("shadow_obsidian_long_pillar",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShadowObsidianBlockEntity>> SHADOW_OBSIDIAN_BLOCK =
            REGISTRY.register("shadow_obsidian",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ObsidianBlockEntity>> OBSIDIAN_BLOCK =
            REGISTRY.register("obsidian",
                    () -> BlockEntityType.Builder.of(
                            ObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                    ).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CryingObsidianBlockEntity>> CRYING_OBSIDIAN_BLOCK =
            REGISTRY.register("crying_obsidian",
                    () -> BlockEntityType.Builder.of(
                            CryingObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get()
                    ).build(null));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}