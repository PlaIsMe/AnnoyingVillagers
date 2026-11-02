package com.pla.annoyinglib.event;
import com.pla.annoyinglib.init.AnnoyingLibModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid = "annoyinglib", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModModelPredicateProvider {
    public static final Material LOCATION_JESSICA_THE_DARK_SHIELD = new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath("annoyinglib", "item/jessica_the_dark_shield"));
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) { event.enqueueWork(() -> {
        ItemProperties.register(AnnoyingLibModItems.JESSICA_THE_DARK_SHIELD.get(), ResourceLocation.fromNamespaceAndPath("annoyinglib", "blocking"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }); }
}
