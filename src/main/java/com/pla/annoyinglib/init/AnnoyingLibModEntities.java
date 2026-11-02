package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD)
public class AnnoyingLibModEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AnnoyingLib.MODID);
    
    
    // Keep the existing entity ID when returning ownership from HerobrineVessels.
    public static final DeferredRegister<EntityType<?>> BLOCK_PROJECTILE_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "annoyingvillagers");
    public static final RegistryObject<EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE = BLOCK_PROJECTILE_REGISTRY.register("block_projectile", () -> Builder.<BlockProjectileEntity>of(BlockProjectileEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(64).updateInterval(2).fireImmune().build("block_projectile"));

    public static final RegistryObject<EntityType<ShockWaveBlockEntity>> SHOCKWAVE_BLOCK = register("shockwave_block", Builder.<ShockWaveBlockEntity>of(ShockWaveBlockEntity::new, MobCategory.MISC).setTrackingRange(10).setUpdateInterval(20).fireImmune().sized(0.98F, 0.98F));

    public static final RegistryObject<EntityType<ItemProjectile>> ITEM_PROJECTILE = register("item_projectile", Builder.<ItemProjectile>of(ItemProjectile::new, MobCategory.MISC).sized(0.35F, 0.35F).clientTrackingRange(64).updateInterval(1));
    
    public static final RegistryObject<EntityType<RisingWallBlockEntity>> RISING_WALL_BLOCK = REGISTRY.register("rising_wall_block", () -> EntityType.Builder.<RisingWallBlockEntity>of(RisingWallBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).fireImmune().build("rising_wall_block"));
    public static final RegistryObject<EntityType<FloatingLookBlockEntity>> FLOATING_LOOK_BLOCK = REGISTRY.register("floating_look_block", () -> EntityType.Builder.<FloatingLookBlockEntity>of(FloatingLookBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).fireImmune().build("floating_look_block"));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String s, Builder<T> builder) {
        return AnnoyingLibModEntities.REGISTRY.register(s, () -> {
            return builder.build(s);
        });
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {

    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent entityAttributeCreationEvent) {

        entityAttributeCreationEvent.put(AnnoyingLibModEntities.FLOATING_LOOK_BLOCK.get(), FloatingLookBlockEntity.createAttributes().build());
    }

}
