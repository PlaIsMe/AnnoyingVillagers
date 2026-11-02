package com.pla.annoyinglib;
import com.pla.annoyinglib.init.AnnoyingLibModSounds;
import com.pla.annoyinglib.init.AnnoyingLibModEntities;
import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.network.ClientboundDiamondAttractorFx;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import com.mojang.serialization.Codec;
import com.pla.annoyinglib.client.gui.InventoryViewerScreen;
import com.pla.annoyinglib.client.engine.CameraEngine;
import com.pla.annoyinglib.client.engine.SpriteArrowsCommonEntrypoint;
import com.pla.annoyinglib.config.AnnoyingLibClientConfig;
import com.pla.annoyinglib.config.AnnoyingLibSpawnConfig;
import com.pla.annoyinglib.config.AnnoyingLibConfig;
import com.pla.annoyinglib.init.*;
import com.pla.annoyinglib.network.*;
import com.pla.annoyinglib.world.AVMobSpawnBiomeModifier;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AnnoyingLib.MODID)
public class AnnoyingLib {
    public static final Logger LOGGER = LogManager.getLogger(AnnoyingLib.class);
    public static final String MODID = "annoyinglib";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry
            .newSimpleChannel(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "main"), () -> "8", "8"::equals, "8"::equals);
    private static int messageID = 0;

    public AnnoyingLib(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        AnnoyingLibModBlocks.REGISTRY.register(modEventBus);
        AnnoyingLibModBlockEntities.REGISTRY.register(modEventBus);
        AnnoyingLibModItems.REGISTRY.register(modEventBus);
        AnnoyingLibModCreativeTabs.register(modEventBus);
        AnnoyingLibModMenus.REGISTRY.register(modEventBus);
        AnnoyingLibModEntities.REGISTRY.register(modEventBus);
        AnnoyingLibModEntities.BLOCK_PROJECTILE_REGISTRY.register(modEventBus);
        AnnoyingLibModMobEffects.REGISTRY.register(modEventBus);
        AnnoyingLibModParticleTypes.REGISTRY.register(modEventBus);
        AnnoyingLibModSounds.register(modEventBus);
        context.registerConfig(ModConfig.Type.COMMON, AnnoyingLibConfig.SPEC, "annoyinglib-server.toml");
        DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers =
                DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
        biomeModifiers.register(modEventBus);
        biomeModifiers.register("av_mob_spawns", AVMobSpawnBiomeModifier::makeCodec);
        context.registerConfig(ModConfig.Type.COMMON, AnnoyingLibSpawnConfig.SPEC, "annoyinglib-spawns.toml");

        if (FMLEnvironment.dist.isClient()) {
            context.registerConfig(ModConfig.Type.CLIENT, AnnoyingLibClientConfig.SPEC, "annoyinglib-client.toml");
            modEventBus.addListener(this::clientSetup);
            modEventBus.addListener(EventPriority.LOWEST, ClassLoadingProtection::listen);
        }
    }

    private static class ClassLoadingProtection {
        private static void listen(FMLClientSetupEvent event) {
            event.enqueueWork(SpriteArrowsCommonEntrypoint::replace);
        }
    }

    public static <T> void addNetworkMessage(Class<T> oclass, BiConsumer<T, FriendlyByteBuf> biconsumer, Function<FriendlyByteBuf, T> function, BiConsumer<T, Supplier<Context>> biconsumer1) {
        AnnoyingLib.PACKET_HANDLER.registerMessage(AnnoyingLib.messageID, oclass, biconsumer, function, biconsumer1);
        ++AnnoyingLib.messageID;
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(AnnoyingLibModMenus.INVENTORY_VIEWER.get(), InventoryViewerScreen::new));
        new CameraEngine();
    }

    @EventBusSubscriber(modid = MODID, bus = Bus.MOD)
    public static class initer {
        @SubscribeEvent
        public static void init(FMLCommonSetupEvent fmlCommonSetupEvent) {
            AnnoyingLib.addNetworkMessage(
                    ClientboundMuteExplosionAtPos.class,
                    ClientboundMuteExplosionAtPos::encode,
                    ClientboundMuteExplosionAtPos::decode,
                    ClientboundMuteExplosionAtPos::handle
            );
            AnnoyingLib.addNetworkMessage(
                    CPApplyShake.class,
                    CPApplyShake::encode,
                    CPApplyShake::new,
                    CPApplyShake::handle
            );

            AnnoyingLib.addNetworkMessage(
                    ClientboundPlayerGroundTransitionPosition.class,
                    ClientboundPlayerGroundTransitionPosition::encode,
                    ClientboundPlayerGroundTransitionPosition::decode,
                    ClientboundPlayerGroundTransitionPosition::handle
            );
            AnnoyingLib.addNetworkMessage(
                    ClientboundRigAnimation.class,
                    ClientboundRigAnimation::encode,
                    ClientboundRigAnimation::decode,
                    ClientboundRigAnimation::handle
            );
            AnnoyingLib.addNetworkMessage(ClientboundDiamondAttractorFx.class, ClientboundDiamondAttractorFx::encode, ClientboundDiamondAttractorFx::decode, ClientboundDiamondAttractorFx::handle);
            AnnoyingLib.addNetworkMessage(ClientboundSpecialAnimation.class, ClientboundSpecialAnimation::encode, ClientboundSpecialAnimation::decode, ClientboundSpecialAnimation::handle);

            AnnoyingLib.addNetworkMessage(ServerboundActivateArmor.class, ServerboundActivateArmor::encode, ServerboundActivateArmor::decode, ServerboundActivateArmor::handle);

            AnnoyingLib.addNetworkMessage(
                    ClientboundBetterCombatAnimation.class,
                    ClientboundBetterCombatAnimation::encode,
                    ClientboundBetterCombatAnimation::decode,
                    ClientboundBetterCombatAnimation::handle
            );
            AnnoyingLib.addNetworkMessage(
                    ClientboundGroundFracture.class,
                    ClientboundGroundFracture::encode,
                    ClientboundGroundFracture::decode,
                    ClientboundGroundFracture::handle
            );

        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}
