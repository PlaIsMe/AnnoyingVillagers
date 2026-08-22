package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.blockentity.CryingObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.FractureBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianLongPillarBlockEntity;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class AnnoyingVillagersModBlockEntities {
    private AnnoyingVillagersModBlockEntities() {}
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AnnoyingVillagers.MODID);

    public static final RegistryObject<BlockEntityType<FractureBlockEntity>> FRACTURE_BLOCK = REGISTRY.register("fracture_block", () -> BlockEntityType.Builder.of(FractureBlockEntity::new, AnnoyingVillagersModBlocks.FRACTURE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_SHORT_PILLAR =
            REGISTRY.register("shadow_obsidian_short_pillar",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_MIDDLE_PILLAR =
            REGISTRY.register("shadow_obsidian_middle_pillar",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<ShadowObsidianLongPillarBlockEntity>> SHADOW_OBSIDIAN_LONG_PILLAR =
            REGISTRY.register("shadow_obsidian_long_pillar",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianLongPillarBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<ShadowObsidianBlockEntity>> SHADOW_OBSIDIAN_BLOCK =
            REGISTRY.register("shadow_obsidian",
                    () -> BlockEntityType.Builder.of(
                            ShadowObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<ObsidianBlockEntity>> OBSIDIAN_BLOCK =
            REGISTRY.register("obsidian",
                    () -> BlockEntityType.Builder.of(
                            ObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<CryingObsidianBlockEntity>> CRYING_OBSIDIAN_BLOCK =
            REGISTRY.register("crying_obsidian",
                    () -> BlockEntityType.Builder.of(
                            CryingObsidianBlockEntity::new,
                            AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get()
                    ).build(null));

    public static void register(IEventBus modEventBus) {
        REGISTRY.register(modEventBus);
    }
}