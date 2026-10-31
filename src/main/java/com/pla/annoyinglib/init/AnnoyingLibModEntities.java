package com.pla.annoyinglib.init;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
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
    public static final DeferredRegister<EntityType<?>> DIAMOND_BOLT_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "annoyingvillagers");
    public static final RegistryObject<EntityType<DiamondBoltProjectileEntity>> DIAMOND_BOLT_PROJECTILE = DIAMOND_BOLT_REGISTRY.register("diamond_bolt_projectile", () -> Builder.<DiamondBoltProjectileEntity>of(DiamondBoltProjectileEntity::new, MobCategory.MISC).setCustomClientFactory(DiamondBoltProjectileEntity::new).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("diamond_bolt_projectile"));
    public static final DeferredRegister<EntityType<?>> GOLEM_ARMS_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "annoyingvillagers");
    public static final RegistryObject<EntityType<GolemArms>> GOLEM_ARMS = GOLEM_ARMS_REGISTRY.register("golem_arms", () -> EntityType.Builder.<GolemArms>of(GolemArms::new, MobCategory.MISC).sized(1.0F, 2.3F).clientTrackingRange(16).updateInterval(1).noSave().build("golem_arms"));
    // Keep the existing entity ID when returning ownership from HerobrineVessels.
    public static final DeferredRegister<EntityType<?>> BLOCK_PROJECTILE_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "annoyingvillagers");
    public static final RegistryObject<EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE = BLOCK_PROJECTILE_REGISTRY.register("block_projectile", () -> Builder.<BlockProjectileEntity>of(BlockProjectileEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(64).updateInterval(2).fireImmune().build("block_projectile"));

    public static final RegistryObject<EntityType<BlueDemonEntity>> BLUE_DEMON = register("blue_demon", Builder.<BlueDemonEntity>of(BlueDemonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(126).setUpdateInterval(3).setCustomClientFactory(BlueDemonEntity::new).fireImmune().sized(0.6F, 1.8F));

    public static final RegistryObject<EntityType<VillagerScoutCaptainEntity>> VILLAGER_SCOUT_CAPTAIN = register("villager_scout_captain", Builder.<VillagerScoutCaptainEntity>of(VillagerScoutCaptainEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(VillagerScoutCaptainEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<VillagerScoutEntity>> VILLAGER_SCOUT = register("villager_scout", Builder.<VillagerScoutEntity>of(VillagerScoutEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(VillagerScoutEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BlueVillagerKnightEntity>> BLUE_VILLAGER_KNIGHT = register("blue_villager_knight", Builder.<BlueVillagerKnightEntity>of(BlueVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(BlueVillagerKnightEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<GreenVillagerKnightEntity>> GREEN_VILLAGER_KNIGHT = register("green_villager_knight", Builder.<GreenVillagerKnightEntity>of(GreenVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(GreenVillagerKnightEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<RedVillagerKnightEntity>> RED_VILLAGER_KNIGHT = register("red_villager_knight", Builder.<RedVillagerKnightEntity>of(RedVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(RedVillagerKnightEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<PurpleVillagerKnightEntity>> PURPLE_VILLAGER_KNIGHT = register("purple_villager_knight", Builder.<PurpleVillagerKnightEntity>of(PurpleVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(PurpleVillagerKnightEntity::new).sized(0.6F, 1.8F));

    public static final RegistryObject<EntityType<AlexEntity>> ALEX = register("alex", Builder.<AlexEntity>of(AlexEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(AlexEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<JevEntity>> JEV = register("jev", Builder.<JevEntity>of(JevEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(JevEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<BbqEntity>> BBQ = register("bbq", Builder.<BbqEntity>of(BbqEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(BbqEntity::new).sized(0.4F, 0.7F));
    public static final RegistryObject<EntityType<ChrisEntity>> CHRIS = register("chris", Builder.<ChrisEntity>of(ChrisEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(ChrisEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<SteveEntity>> STEVE = register("steve", Builder.<SteveEntity>of(SteveEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(SteveEntity::new).sized(0.6F, 1.8F));
    public static final RegistryObject<EntityType<AngrySteveEntity>> ANGRY_STEVE = register("angry_steve", Builder.<AngrySteveEntity>of(AngrySteveEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).setCustomClientFactory(AngrySteveEntity::new).sized(0.6F, 1.8F));








    public static final RegistryObject<EntityType<ThrownPoisonEggEntity>> THROWN_POISON_EGG = register("thrown_poison_egg", Builder.<ThrownPoisonEggEntity>of(ThrownPoisonEggEntity::new, MobCategory.MISC).setCustomClientFactory(ThrownPoisonEggEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<HookGunHookEntity>> HOOK_GUN_HOOK = register("hook_gun_hook", Builder.<HookGunHookEntity>of(HookGunHookEntity::new, MobCategory.MISC).setCustomClientFactory(HookGunHookEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(96).setUpdateInterval(1).sized(0.25F, 0.25F));























    public static final RegistryObject<EntityType<IronGolemWarrior>> IRON_GOLEM_WARRIOR = register("iron_golem_warrior", EntityType.Builder.<IronGolemWarrior>of(IronGolemWarrior::new, MobCategory.CREATURE).sized(1.4F, 2.7F).clientTrackingRange(16));











    public static final RegistryObject<EntityType<ShockWaveBlockEntity>> SHOCKWAVE_BLOCK = register("shockwave_block", Builder.<ShockWaveBlockEntity>of(ShockWaveBlockEntity::new, MobCategory.MISC).setTrackingRange(10).setUpdateInterval(20).fireImmune().sized(0.98F, 0.98F));
    public static final RegistryObject<EntityType<BlueDemonThunderBeamEntity>> BLUE_DEMON_THUNDER_BEAM = register("blue_demon_thunder_beam", Builder.<BlueDemonThunderBeamEntity>of(BlueDemonThunderBeamEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<TridentLightningBolt>> TRIDENT_LIGHTNING_BOLT = register("trident_lightning_bolt", Builder.<TridentLightningBolt>of(TridentLightningBolt::new, MobCategory.MISC).noSave().sized(0.0F, 0.0F).clientTrackingRange(16).updateInterval(Integer.MAX_VALUE));
    public static final RegistryObject<EntityType<BlueDemonThrownTridentEntity>> BLUE_DEMON_THROWN_TRIDENT = register("blue_demon_thrown_trident", Builder.<BlueDemonThrownTridentEntity>of(BlueDemonThrownTridentEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

    public static final RegistryObject<EntityType<ElectricAreaEntity>> ELECTRIC_AREA = REGISTRY.register("electric_area", () -> EntityType.Builder.<ElectricAreaEntity>of(ElectricAreaEntity::new, MobCategory.MISC).sized(0.1F, 0.1F).clientTrackingRange(8).updateInterval(10).build("blue_demon_area_damage_zone"));
    public static final RegistryObject<EntityType<BlackFireEntity>> BLACK_FIRE = REGISTRY.register("black_fire", () -> EntityType.Builder.<BlackFireEntity>of(BlackFireEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).clientTrackingRange(64).updateInterval(1).build("black_fire"));
    public static final RegistryObject<EntityType<EnchantedArrowEntity>> ENCHANTED_ARROW = register("enchanted_arrow", Builder.<EnchantedArrowEntity>of(EnchantedArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<ItemProjectile>> ITEM_PROJECTILE = register("item_projectile", Builder.<ItemProjectile>of(ItemProjectile::new, MobCategory.MISC).sized(0.35F, 0.35F).clientTrackingRange(64).updateInterval(1));
    public static final RegistryObject<EntityType<FlyingShockwaveProjectile>> FLYING_SHOCKWAVE = register("flying_shockwave", Builder.of(FlyingShockwaveProjectile::new, MobCategory.MISC).sized(1.5f, 3f).clientTrackingRange(12));
    public static final RegistryObject<EntityType<ElectricPhaseEntity>> ELECTRIC_PHASE = REGISTRY.register("electric_phase", () -> EntityType.Builder.<ElectricPhaseEntity>of(ElectricPhaseEntity::new, MobCategory.MISC).sized(0.8F, 0.8F).clientTrackingRange(64).updateInterval(1).fireImmune().build("electric_phase"));
    public static final RegistryObject<EntityType<RisingWallBlockEntity>> RISING_WALL_BLOCK = REGISTRY.register("rising_wall_block", () -> EntityType.Builder.<RisingWallBlockEntity>of(RisingWallBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).fireImmune().build("rising_wall_block"));
    public static final RegistryObject<EntityType<FloatingLookBlockEntity>> FLOATING_LOOK_BLOCK = REGISTRY.register("floating_look_block", () -> EntityType.Builder.<FloatingLookBlockEntity>of(FloatingLookBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).fireImmune().build("floating_look_block"));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String s, Builder<T> builder) {
        return AnnoyingLibModEntities.REGISTRY.register(s, () -> {
            return builder.build(s);
        });
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {








        event.register(
                AnnoyingLibModEntities.BLUE_DEMON.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlueDemonEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.STEVE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SteveEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.ALEX.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AlexEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.CHRIS.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ChrisEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.VILLAGER_SCOUT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                VillagerScoutEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                VillagerScoutCaptainEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.RED_VILLAGER_KNIGHT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                RedVillagerKnightEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.BLUE_VILLAGER_KNIGHT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlueVillagerKnightEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.PURPLE_VILLAGER_KNIGHT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PurpleVillagerKnightEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingLibModEntities.GREEN_VILLAGER_KNIGHT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GreenVillagerKnightEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent entityAttributeCreationEvent) {
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.GOLEM_ARMS.get(), GolemArms.createAttributes().build());

        entityAttributeCreationEvent.put(AnnoyingLibModEntities.BLUE_DEMON.get(), BlueDemonEntity.createAttributes().build());



        entityAttributeCreationEvent.put(AnnoyingLibModEntities.VILLAGER_SCOUT_CAPTAIN.get(), VillagerScoutCaptainEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.VILLAGER_SCOUT.get(), VillagerScoutEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.BLUE_VILLAGER_KNIGHT.get(), BlueVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.GREEN_VILLAGER_KNIGHT.get(), GreenVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.RED_VILLAGER_KNIGHT.get(), RedVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.PURPLE_VILLAGER_KNIGHT.get(), PurpleVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.ALEX.get(), AlexEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.JEV.get(), JevEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.BBQ.get(), BbqEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.CHRIS.get(), ChrisEntity.createAttributes().build());


        entityAttributeCreationEvent.put(AnnoyingLibModEntities.STEVE.get(), SteveEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingLibModEntities.ANGRY_STEVE.get(), AngrySteveEntity.createAttributes().build());



















        entityAttributeCreationEvent.put(AnnoyingLibModEntities.IRON_GOLEM_WARRIOR.get(), IronGolemWarrior.createAttributes().build());




        entityAttributeCreationEvent.put(AnnoyingLibModEntities.FLOATING_LOOK_BLOCK.get(), FloatingLookBlockEntity.createAttributes().build());
    }
}
