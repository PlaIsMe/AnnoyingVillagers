package com.pla.annoyingvillagers;





import com.mojang.serialization.MapCodec;
import com.pla.annoyingvillagers.client.gui.InventoryViewerScreen;
import com.pla.annoyingvillagers.client.engine.CameraEngine;
import com.pla.annoyingvillagers.client.engine.SpriteArrowsCommonEntrypoint;
import com.pla.annoyingvillagers.client.particle.WhiteAfterimageParticle;
import com.pla.annoyingvillagers.client.compat.LegacyItemRangeProperty;
import com.pla.annoyingvillagers.client.renderer.BlackHoleRenderTypes;
import com.pla.annoyingvillagers.client.renderer.HookGunItemRenderer;
import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig;
import com.pla.annoyingvillagers.config.AnnoyingVillagersSpawnConfig;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.item.BlueVillagerKnightArmorItem;
import com.pla.annoyingvillagers.item.GreenVillagerKnightArmorItem;
import com.pla.annoyingvillagers.item.HerobrineObsidianDiamondArmorChestplateItem;
import com.pla.annoyingvillagers.item.HerobrineObsidianDiamondArmorHelmetItem;
import com.pla.annoyingvillagers.item.PurpleVillagerKnightArmorItem;
import com.pla.annoyingvillagers.item.RedVillagerKnightArmorItem;
import com.pla.annoyingvillagers.item.VillagerScoutHelmetItem;
import com.pla.annoyingvillagers.network.*;
import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.world.AVMobSpawnBiomeModifier;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLEnvironment;



import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AnnoyingVillagers.MODID)
public class AnnoyingVillagers {
    public static final Logger LOGGER = LogManager.getLogger(AnnoyingVillagers.class);
    public static final String MODID = "annoyingvillagers";

    public AnnoyingVillagers(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(NetworkRegister::register);

        AnnoyingVillagersModBlocks.REGISTRY.register(modEventBus);
        AnnoyingVillagersModBlockEntities.REGISTRY.register(modEventBus);
        AnnoyingVillagersModItems.REGISTRY.register(modEventBus);
        AnnoyingVillagersModMenus.REGISTRY.register(modEventBus);
        AnnoyingVillagersModEntities.REGISTRY.register(modEventBus);
        AnnoyingVillagersModMobEffects.REGISTRY.register(modEventBus);
        AnnoyingVillagersModParticleTypes.REGISTRY.register(modEventBus);
        AnnoyingVillagersModCapabilities.REGISTRY.register(modEventBus);
        AnnoyingVillagersModCreativeTabs.register(modEventBus);
        AnnoyingVillagersModSounds.register(modEventBus);
        AnnoyingVillagersModTicketTypes.REGISTRY.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, AnnoyingVillagersConfig.SPEC, "annoyingvillagers-server.toml");
        DeferredRegister<MapCodec<? extends BiomeModifier>> biomeModifiers =
                DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
        biomeModifiers.register(modEventBus);
        biomeModifiers.register("av_mob_spawns", AVMobSpawnBiomeModifier::makeCodec);
        modContainer.registerConfig(ModConfig.Type.COMMON, AnnoyingVillagersSpawnConfig.SPEC, "annoyingvillagers-spawns.toml");

        if (FMLEnvironment.getDist().isClient()) {
            modContainer.registerConfig(ModConfig.Type.CLIENT, AnnoyingVillagersClientConfig.SPEC, "annoyingvillagers-client.toml");
            modEventBus.addListener(this::clientSetup);
            modEventBus.addListener(this::registerMenuScreens);
            modEventBus.addListener(WhiteAfterimageParticle::registerGroup);
            modEventBus.addListener(BlackHoleRenderTypes::registerPipelines);
            modEventBus.addListener(EventPriority.LOWEST, ClassLoadingProtection::listen);
        }
    }

    private static class ClassLoadingProtection {
        private static void listen(FMLClientSetupEvent event) {
            event.enqueueWork(SpriteArrowsCommonEntrypoint::replace);
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        new CameraEngine();
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(AnnoyingVillagersModMenus.INVENTORY_VIEWER.get(), InventoryViewerScreen::new);
    }



    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerItemModelProperties(RegisterRangeSelectItemModelPropertyEvent event) {
            event.register(Identifier.fromNamespaceAndPath(MODID, "legacy"), LegacyItemRangeProperty.MAP_CODEC);
        }

        @SubscribeEvent
        public static void registerItemModels(RegisterItemModelsEvent event) {
            event.register(HookGunItemRenderer.TYPE, HookGunItemRenderer.Unbaked.MAP_CODEC);
        }

        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
            BlueVillagerKnightArmorItem.Helmet blue =
                    (BlueVillagerKnightArmorItem.Helmet) AnnoyingVillagersModItems.BLUE_VILLAGER_KNIGHT_HELMET.get();
            blue.initializeClient(extension -> event.registerItem(extension, blue));

            GreenVillagerKnightArmorItem.Helmet greenHelmet =
                    (GreenVillagerKnightArmorItem.Helmet) AnnoyingVillagersModItems.GREEN_VILLAGER_KNIGHT_HELMET.get();
            greenHelmet.initializeClient(extension -> event.registerItem(extension, greenHelmet));
            GreenVillagerKnightArmorItem.Chestplate greenChestplate =
                    (GreenVillagerKnightArmorItem.Chestplate) AnnoyingVillagersModItems.GREEN_VILLAGER_KNIGHT_CHESTPLATE.get();
            greenChestplate.initializeClient(extension -> event.registerItem(extension, greenChestplate));

            PurpleVillagerKnightArmorItem.Helmet purple =
                    (PurpleVillagerKnightArmorItem.Helmet) AnnoyingVillagersModItems.PURPLE_VILLAGER_KNIGHT_HELMET.get();
            purple.initializeClient(extension -> event.registerItem(extension, purple));

            RedVillagerKnightArmorItem.Armor red =
                    (RedVillagerKnightArmorItem.Armor) AnnoyingVillagersModItems.RED_VILLAGER_KNIGHT_HELMET.get();
            red.initializeClient(extension -> event.registerItem(extension, red));

            VillagerScoutHelmetItem.Helmet scout =
                    (VillagerScoutHelmetItem.Helmet) AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET.get();
            scout.initializeClient(extension -> event.registerItem(extension, scout));

            HerobrineObsidianDiamondArmorHelmetItem.Helmet obsidianHelmet =
                    (HerobrineObsidianDiamondArmorHelmetItem.Helmet) AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_HELMET.get();
            obsidianHelmet.initializeClient(extension -> event.registerItem(extension, obsidianHelmet));
            HerobrineObsidianDiamondArmorChestplateItem.Chestplate obsidianChestplate =
                    (HerobrineObsidianDiamondArmorChestplateItem.Chestplate) AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE.get();
            obsidianChestplate.initializeClient(extension -> event.registerItem(extension, obsidianChestplate));
        }
    }
}
