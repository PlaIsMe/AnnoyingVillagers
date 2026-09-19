package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber
public class AnnoyingVillagersModEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AnnoyingVillagers.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<BlueDemonEntity>> BLUE_DEMON = register("blue_demon", Builder.<BlueDemonEntity>of(BlueDemonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(126).updateInterval(3).fireImmune().sized(0.6F, 1.8F));

    public static final DeferredHolder<EntityType<?>, EntityType<VillagerScoutCaptainEntity>> VILLAGER_SCOUT_CAPTAIN = register("villager_scout_captain", Builder.<VillagerScoutCaptainEntity>of(VillagerScoutCaptainEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<VillagerScoutEntity>> VILLAGER_SCOUT = register("villager_scout", Builder.<VillagerScoutEntity>of(VillagerScoutEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<BlueVillagerKnightEntity>> BLUE_VILLAGER_KNIGHT = register("blue_villager_knight", Builder.<BlueVillagerKnightEntity>of(BlueVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<GreenVillagerKnightEntity>> GREEN_VILLAGER_KNIGHT = register("green_villager_knight", Builder.<GreenVillagerKnightEntity>of(GreenVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<RedVillagerKnightEntity>> RED_VILLAGER_KNIGHT = register("red_villager_knight", Builder.<RedVillagerKnightEntity>of(RedVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<PurpleVillagerKnightEntity>> PURPLE_VILLAGER_KNIGHT = register("purple_villager_knight", Builder.<PurpleVillagerKnightEntity>of(PurpleVillagerKnightEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).sized(0.6F, 1.8F));

    public static final DeferredHolder<EntityType<?>, EntityType<AlexEntity>> ALEX = register("alex", Builder.<AlexEntity>of(AlexEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(256).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<JevEntity>> JEV = register("jev", Builder.<JevEntity>of(JevEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(256).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<BbqEntity>> BBQ = register("bbq", Builder.<BbqEntity>of(BbqEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).sized(0.4F, 0.7F));
    public static final DeferredHolder<EntityType<?>, EntityType<ChrisEntity>> CHRIS = register("chris", Builder.<ChrisEntity>of(ChrisEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<SteveEntity>> STEVE = register("steve", Builder.<SteveEntity>of(SteveEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(256).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<AngrySteveEntity>> ANGRY_STEVE = register("angry_steve", Builder.<AngrySteveEntity>of(AngrySteveEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(256).updateInterval(3).sized(0.6F, 1.8F));

    public static final DeferredHolder<EntityType<?>, EntityType<InfectedTheMostMoistBurrit0Entity>> INFECTED_THEMOSTMOISTBURRIT0 = register("infected_the_most_moist_burrit0", Builder.<InfectedTheMostMoistBurrit0Entity>of(InfectedTheMostMoistBurrit0Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<InfectedPlayerNpcEntity>> INFECTED_PLAYER_NPC = register("infected_player_npc", Builder.<InfectedPlayerNpcEntity>of(InfectedPlayerNpcEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(3).sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<InfectedChrisEntity>> INFECTED_CHRIS = register("infected_chris", Builder.<InfectedChrisEntity>of(InfectedChrisEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(3).sized(0.6F, 1.8F));

    public static final DeferredHolder<EntityType<?>, EntityType<EnderAegisProjectile>> ENDER_AEGIS_PROJECTILE = register("ender_aegis_projectile", Builder.<EnderAegisProjectile>of(EnderAegisProjectile::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(1).sized(0.5F, 0.5F));
    public static final DeferredHolder<EntityType<?>, EntityType<VacuumSliceEntity>> VACUUM_SLICE = REGISTRY.register("vacuum_slice",() -> EntityType.Builder.of(VacuumSliceEntity::new,MobCategory.MISC).sized(1.0F,0.5F).clientTrackingRange(10).updateInterval(1).setShouldReceiveVelocityUpdates(true).noSave().fireImmune().build("vacuum_slice"));
    public static final DeferredHolder<EntityType<?>, EntityType<EnchantedEnderPearlEntity>> ENCHANTED_ENDER_PEARL_PROJECTILE = register("projectile_enchanted_ender_pearl", Builder.<EnchantedEnderPearlEntity>of(EnchantedEnderPearlEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(1).sized(0.5F, 0.5F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownPoisonEggEntity>> THROWN_POISON_EGG = register("thrown_poison_egg", Builder.<ThrownPoisonEggEntity>of(ThrownPoisonEggEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(1).sized(0.5F, 0.5F));
    public static final DeferredHolder<EntityType<?>, EntityType<HookGunHookEntity>> HOOK_GUN_HOOK = register("hook_gun_hook", Builder.<HookGunHookEntity>of(HookGunHookEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).clientTrackingRange(96).updateInterval(1).sized(0.25F, 0.25F));

    public static final DeferredHolder<EntityType<?>, EntityType<HerobrineCloneEntity>> HEROBRINE_CLONE = register("herobrine_clone", Builder.<HerobrineCloneEntity>of(HerobrineCloneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<ShadowHerobrineCloneEntity>> SHADOW_HEROBRINE_CLONE = register("shadow_herobrine_clone", Builder.<ShadowHerobrineCloneEntity>of(ShadowHerobrineCloneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<TransporterHerobrineCloneEntity>> TRANSPORTER_HEROBRINE_CLONE = register("transporter_herobrine_clone", Builder.<TransporterHerobrineCloneEntity>of(TransporterHerobrineCloneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(64).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<HerobrineGregEntity>> HEROBRINE_GREG = register("herobrine_greg", Builder.<HerobrineGregEntity>of(HerobrineGregEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<LowHerobrineCloneEntity>> LOW_HEROBRINE_CLONE = register("low_herobrine_clone", Builder.<LowHerobrineCloneEntity>of(LowHerobrineCloneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<LowShadowHerobrineCloneEntity>> LOW_SHADOW_HEROBRINE_CLONE = register("low_shadow_herobrine_clone", Builder.<LowShadowHerobrineCloneEntity>of(LowShadowHerobrineCloneEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<Herobrine7Entity>> HEROBRINE_7 = register("herobrine_7", Builder.<Herobrine7Entity>of(Herobrine7Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(256).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<ArmoredHerobrineEntity>> ARMORED_HEROBRINE = register("armored_herobrine", Builder.<ArmoredHerobrineEntity>of(ArmoredHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(256).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<ShadowHerobrineEntity>> SHADOW_HEROBRINE = register("shadow_herobrine", Builder.<ShadowHerobrineEntity>of(ShadowHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<GlaiveHerobrineEntity>> GLAIVE_HEROBRINE = register("glaive_herobrine", Builder.<GlaiveHerobrineEntity>of(GlaiveHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<ReaperHerobrineEntity>> REAPER_HEROBRINE = register("reaper_herobrine", Builder.<ReaperHerobrineEntity>of(ReaperHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<SwordsmanHerobrineEntity>> SWORDSMAN_HEROBRINE = register("swordsman_herobrine", Builder.<SwordsmanHerobrineEntity>of(SwordsmanHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<SledgehammerHerobrineEntity>> SLEDGEHAMMER_HEROBRINE = register("sledgehammer_herobrine", Builder.<SledgehammerHerobrineEntity>of(SledgehammerHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<AegisHerobrineEntity>> AEGIS_HEROBRINE = register("aegis_herobrine", Builder.<AegisHerobrineEntity>of(AegisHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<EliteHerobrineKnockedEntity>> ELITE_HEROBRINE_KNOCKED = register("elite_herobrine_knocked", Builder.<EliteHerobrineKnockedEntity>of(EliteHerobrineKnockedEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).clientTrackingRange(256).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<NullEntity>> NULL = register("null", Builder.<NullEntity>of(NullEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<NullSwordEntity>> NULL_SWORD = register("null_sword", Builder.<NullSwordEntity>of(NullSwordEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<NullAxeEntity>> NULL_AXE = register("null_axe", Builder.<NullAxeEntity>of(NullAxeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<NullPickaxeEntity>> NULL_PICKAXE = register("null_pickaxe", Builder.<NullPickaxeEntity>of(NullPickaxeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<NullShovelEntity>> NULL_SHOVEL = register("null_shovel", Builder.<NullShovelEntity>of(NullShovelEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<NullHoeEntity>> NULL_HOE = register("null_hoe", Builder.<NullHoeEntity>of(NullHoeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));
    public static final DeferredHolder<EntityType<?>, EntityType<AvWarden>> AV_WARDEN = register("av_warden", EntityType.Builder.<AvWarden>of(AvWarden::new, MobCategory.MONSTER).sized(0.9F, 2.9F).clientTrackingRange(16).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<IronGolemWarrior>> IRON_GOLEM_WARRIOR = register("iron_golem_warrior", EntityType.Builder.<IronGolemWarrior>of(IronGolemWarrior::new, MobCategory.CREATURE).sized(1.4F, 2.7F).clientTrackingRange(16));
    public static final DeferredHolder<EntityType<?>, EntityType<GolemArms>> GOLEM_ARMS = register("golem_arms", EntityType.Builder.<GolemArms>of(GolemArms::new, MobCategory.MISC).sized(1.0F, 2.3F).clientTrackingRange(16).updateInterval(1).noSave());
    public static final DeferredHolder<EntityType<?>, EntityType<NullSkeletonEntity>> NULL_SKELETON = register("null_skeleton", Builder.<NullSkeletonEntity>of(NullSkeletonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(128).updateInterval(3).fireImmune().sized(0.6F, 1.8F));

    public static final DeferredHolder<EntityType<?>, EntityType<BlackHoleEntity>> BLACK_HOLE = register("black_hole", Builder.<BlackHoleEntity>of(BlackHoleEntity::new, MobCategory.MISC).sized(6.0F, 3.0F).clientTrackingRange(192).updateInterval(1).fireImmune());

    public static final DeferredHolder<EntityType<?>, EntityType<PortalEntity>> PORTAL = register("portal", Builder.<PortalEntity>of(PortalEntity::new, MobCategory.MISC).sized(PortalEntity.WIDTH, PortalEntity.HEIGHT).clientTrackingRange(64).updateInterval(1).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<SnakeBladeEntity>> SNAKE_BLADE = register("snake_blade", Builder.<SnakeBladeEntity>of(SnakeBladeEntity::new, MobCategory.MISC).sized(0.1F, 0.1F));
    public static final DeferredHolder<EntityType<?>, EntityType<DragonBeamEntity>> DRAGON_BEAM = register("dragon_beam", Builder.<DragonBeamEntity>of(DragonBeamEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final DeferredHolder<EntityType<?>, EntityType<BlockProjectileEntity>> BLOCK_PROJECTILE = register("block_projectile", Builder.<BlockProjectileEntity>of(BlockProjectileEntity::new, MobCategory.MISC).sized(0.9F, 0.9F).clientTrackingRange(64).updateInterval(2).fireImmune());
    public static final DeferredHolder<EntityType<?>, EntityType<HerobrineDragonEntity>> HEROBRINE_DRAGON = register("herobrine_dragon", Builder.of(HerobrineDragonEntity::new, MobCategory.CREATURE).sized(HerobrineDragonEntity.BASE_WIDTH, HerobrineDragonEntity.BASE_HEIGHT).clientTrackingRange(10).updateInterval(3));
    public static final DeferredHolder<EntityType<?>, EntityType<DragonMeteoriteEntity>> DRAGON_METEORITE = register("dragon_meteorite", Builder.<DragonMeteoriteEntity>of(DragonMeteoriteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).clientTrackingRange(2000).updateInterval(3).fireImmune().sized(1.0F, 1.0F));
    public static final DeferredHolder<EntityType<?>, EntityType<ShockWaveBlockEntity>> SHOCKWAVE_BLOCK = register("shockwave_block", Builder.<ShockWaveBlockEntity>of(ShockWaveBlockEntity::new, MobCategory.MISC).clientTrackingRange(10).updateInterval(20).fireImmune().sized(0.98F, 0.98F));
    public static final DeferredHolder<EntityType<?>, EntityType<BlueDemonThunderBeamEntity>> BLUE_DEMON_THUNDER_BEAM = register("blue_demon_thunder_beam", Builder.<BlueDemonThunderBeamEntity>of(BlueDemonThunderBeamEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final DeferredHolder<EntityType<?>, EntityType<TridentLightningBolt>> TRIDENT_LIGHTNING_BOLT = register("trident_lightning_bolt", Builder.<TridentLightningBolt>of(TridentLightningBolt::new, MobCategory.MISC).noSave().sized(0.0F, 0.0F).clientTrackingRange(16).updateInterval(Integer.MAX_VALUE));
    public static final DeferredHolder<EntityType<?>, EntityType<BlueDemonThrownTridentEntity>> BLUE_DEMON_THROWN_TRIDENT = register("blue_demon_thrown_trident", Builder.<BlueDemonThrownTridentEntity>of(BlueDemonThrownTridentEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final DeferredHolder<EntityType<?>, EntityType<DiamondBoltProjectileEntity>> DIAMOND_BOLT_PROJECTILE = register("diamond_bolt_projectile", Builder.<DiamondBoltProjectileEntity>of(DiamondBoltProjectileEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final DeferredHolder<EntityType<?>, EntityType<ElectricAreaEntity>> ELECTRIC_AREA = REGISTRY.register("electric_area", () -> EntityType.Builder.<ElectricAreaEntity>of(ElectricAreaEntity::new, MobCategory.MISC).sized(0.1F, 0.1F).clientTrackingRange(8).updateInterval(10).build("blue_demon_area_damage_zone"));
    public static final DeferredHolder<EntityType<?>, EntityType<BlackFireEntity>> BLACK_FIRE = REGISTRY.register("black_fire", () -> EntityType.Builder.<BlackFireEntity>of(BlackFireEntity::new, MobCategory.MISC).sized(0.2F, 0.2F).clientTrackingRange(64).updateInterval(1).build("black_fire"));
    public static final DeferredHolder<EntityType<?>, EntityType<EnchantedArrowEntity>> ENCHANTED_ARROW = register("enchanted_arrow", Builder.<EnchantedArrowEntity>of(EnchantedArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));
    public static final DeferredHolder<EntityType<?>, EntityType<ItemProjectile>> ITEM_PROJECTILE = register("item_projectile", Builder.<ItemProjectile>of(ItemProjectile::new, MobCategory.MISC).sized(0.35F, 0.35F).clientTrackingRange(64).updateInterval(1));
    public static final DeferredHolder<EntityType<?>, EntityType<FlyingShockwaveProjectile>> FLYING_SHOCKWAVE = register("flying_shockwave", Builder.of(FlyingShockwaveProjectile::new, MobCategory.MISC).sized(1.5f, 3f).clientTrackingRange(12));
    public static final DeferredHolder<EntityType<?>, EntityType<ElectricPhaseEntity>> ELECTRIC_PHASE = REGISTRY.register("electric_phase", () -> EntityType.Builder.<ElectricPhaseEntity>of(ElectricPhaseEntity::new, MobCategory.MISC).sized(0.8F, 0.8F).clientTrackingRange(64).updateInterval(1).fireImmune().build("electric_phase"));
    public static final DeferredHolder<EntityType<?>, EntityType<RisingWallBlockEntity>> RISING_WALL_BLOCK = REGISTRY.register("rising_wall_block", () -> EntityType.Builder.<RisingWallBlockEntity>of(RisingWallBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).fireImmune().build("rising_wall_block"));
    public static final DeferredHolder<EntityType<?>, EntityType<FloatingLookBlockEntity>> FLOATING_LOOK_BLOCK = REGISTRY.register("floating_look_block", () -> EntityType.Builder.<FloatingLookBlockEntity>of(FloatingLookBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(64).updateInterval(1).fireImmune().build("floating_look_block"));

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String s, Builder<T> builder) {
        return AnnoyingVillagersModEntities.REGISTRY.register(s, () -> {
            return builder.build(s);
        });
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                HerobrineCloneEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ShadowHerobrineCloneEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TransporterHerobrineCloneEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.HEROBRINE_GREG.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                HerobrineGregEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LowShadowHerobrineCloneEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.HEROBRINE_7.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Herobrine7Entity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.HEROBRINE_7.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Herobrine7Entity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ArmoredHerobrineEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.BLUE_DEMON.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlueDemonEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.STEVE.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SteveEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.ALEX.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AlexEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.CHRIS.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ChrisEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                VillagerScoutEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                VillagerScoutCaptainEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                RedVillagerKnightEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlueVillagerKnightEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PurpleVillagerKnightEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
                AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GreenVillagerKnightEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent entityAttributeCreationEvent) {
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(), HerobrineCloneEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(), ShadowHerobrineCloneEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(), TransporterHerobrineCloneEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.HEROBRINE_7.get(), Herobrine7Entity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), VillagerScoutCaptainEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), VillagerScoutEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(), BlueVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(), GreenVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(), RedVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get(), PurpleVillagerKnightEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.ALEX.get(), AlexEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.JEV.get(), JevEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.BBQ.get(), BbqEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.CHRIS.get(), ChrisEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.INFECTED_CHRIS.get(), InfectedChrisEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(), ArmoredHerobrineEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.STEVE.get(), SteveEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.ANGRY_STEVE.get(), AngrySteveEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), InfectedTheMostMoistBurrit0Entity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), ShadowHerobrineEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), GlaiveHerobrineEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(), InfectedPlayerNpcEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), ReaperHerobrineEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(), SwordsmanHerobrineEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(), SledgehammerHerobrineEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), AegisHerobrineEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), EliteHerobrineKnockedEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), LowHerobrineCloneEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), LowShadowHerobrineCloneEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.NULL.get(), NullEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.NULL_SWORD.get(), NullSwordEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.NULL_AXE.get(), NullAxeEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.NULL_PICKAXE.get(), NullPickaxeEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.NULL_SHOVEL.get(), NullShovelEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.NULL_HOE.get(), NullHoeEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.HEROBRINE_GREG.get(), HerobrineGregEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.AV_WARDEN.get(), AvWarden.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.IRON_GOLEM_WARRIOR.get(), IronGolemWarrior.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.GOLEM_ARMS.get(), GolemArms.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.HEROBRINE_DRAGON.get(), HerobrineDragonEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.DRAGON_METEORITE.get(), DragonMeteoriteEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.NULL_SKELETON.get(), NullSkeletonEntity.createAttributes().build());
        entityAttributeCreationEvent.put(AnnoyingVillagersModEntities.FLOATING_LOOK_BLOCK.get(), FloatingLookBlockEntity.createAttributes().build());
    }
}
