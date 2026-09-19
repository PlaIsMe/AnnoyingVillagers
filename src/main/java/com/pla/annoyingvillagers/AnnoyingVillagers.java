package com.pla.annoyingvillagers;





import com.mojang.serialization.MapCodec;
import com.pla.annoyingvillagers.client.gui.InventoryViewerScreen;
import com.pla.annoyingvillagers.client.engine.CameraEngine;
import com.pla.annoyingvillagers.client.engine.SpriteArrowsCommonEntrypoint;
import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig;
import com.pla.annoyingvillagers.config.AnnoyingVillagersSpawnConfig;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.network.*;
import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.world.AVMobSpawnBiomeModifier;
import net.minecraft.client.renderer.item.ItemProperties;

import net.minecraft.resources.ResourceLocation;
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
        modContainer.registerConfig(ModConfig.Type.COMMON, AnnoyingVillagersConfig.SPEC, "annoyingvillagers-server.toml");
        DeferredRegister<MapCodec<? extends BiomeModifier>> biomeModifiers =
                DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
        biomeModifiers.register(modEventBus);
        biomeModifiers.register("av_mob_spawns", AVMobSpawnBiomeModifier::makeCodec);
        modContainer.registerConfig(ModConfig.Type.COMMON, AnnoyingVillagersSpawnConfig.SPEC, "annoyingvillagers-spawns.toml");

        if (FMLEnvironment.dist.isClient()) {
            modContainer.registerConfig(ModConfig.Type.CLIENT, AnnoyingVillagersClientConfig.SPEC, "annoyingvillagers-client.toml");
            modEventBus.addListener(this::clientSetup);
            modEventBus.addListener(this::registerMenuScreens);
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

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(
                        AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "second_form"),
                        (stack, level, entity, seed) -> {
                            if (LegacyItemData.has(stack) && LegacyItemData.get(stack).getBoolean("SecondForm")) {
                                return 1.0F;
                            }
                            return 0.0F;
                        }
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "snake_animation"),
                        (stack, level, entity, seed) -> {
                            if (LegacyItemData.has(stack) && LegacyItemData.get(stack).getBoolean("SnakeAnimation")) {
                                return 1.0F;
                            }
                            return 0.0F;
                        }
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "snake_animation_ready"),
                        (stack, level, entity, seed) -> {
                            if (LegacyItemData.has(stack) && LegacyItemData.get(stack).getInt("HitCount") == 5) {
                                return 1.0F;
                            }
                            return 0.0F;
                        }
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.ADVANCED_FISHING_ROD.get(),
                        ResourceLocation.fromNamespaceAndPath("minecraft", "cast"),
                        (stack, level, entity, seed) -> FishingRodGrappleUtil.getCastProperty(stack, entity)
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.TONY_THE_FISHING_ROD.get(),
                        ResourceLocation.fromNamespaceAndPath("minecraft", "cast"),
                        (stack, level, entity, seed) -> FishingRodGrappleUtil.getCastProperty(stack, entity)
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.HOOK_GUN.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "hook"),
                        (stack, level, entity, seed) -> LegacyItemData.has(stack) && LegacyItemData.get(stack).contains("hook") ? 1.0F : 0.0F
                );
                ItemProperties.register(
                        AnnoyingVillagersModItems.HOOK_GUN.get(),
                        ResourceLocation.fromNamespaceAndPath(MODID, "attached"),
                        (stack, level, entity, seed) -> entity != null
                                && HookGunItem.hasAttachedHook(entity.level(), entity) ? 1.0F : 0.0F
                );
            });
        }
    }
}
