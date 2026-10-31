package com.pla.annoyinglib.event;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.item.RedAxeItem;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.ApiStatus;
import java.util.Set;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModModelPredicateProvider {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingLibModItems.JESSICA_THE_DARK_SHIELD.get()
            );
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingLibModItems.HEATER_SHIELD.get()
            );
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingLibModItems.GEM_SHIELD.get()
            );
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingLibModItems.NETHERITE_SHIELD.get()
            );
            if (VanillaWeaponAbilityUtil.abilitiesEnabled()) ItemProperties.register(AnnoyingLibModItems.RED_AXE.get(), ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "giant_form"), (stack, world, entity, seed) -> RedAxeItem.isGiantForm(stack, world) ? 1.0F : 0.0F);
        });
    }

    private static void addShieldPropertyOverrides(ResourceLocation override, ClampedItemPropertyFunction propertyGetter, ItemLike... shields) {
        for (ItemLike shield : shields) {
            ItemProperties.register(shield.asItem(), override, propertyGetter);
        }
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onStitch(Pre event) {
        if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            for (Material textures : new Material[] {
                    LOCATION_JESSICA_THE_DARK_SHIELD,
                    LOCATION_HEATER_SHIELD,
                    LOCATION_GEM_SHIELD,
                    LOCATION_NETHERITE_SHIELD
            }) {
                event.addSprite(textures.texture());
            }
        }
    }

    public static class Pre extends TextureStitchEvent {
        private final Set<ResourceLocation> sprites;

        @ApiStatus.Internal
        public Pre(TextureAtlas map, Set<ResourceLocation> sprites) {
            super(map);
            this.sprites = sprites;
        }

        public boolean addSprite(ResourceLocation sprite) {
            return this.sprites.add(sprite);
        }
    }

    public static final Material LOCATION_JESSICA_THE_DARK_SHIELD = material("item/jessica_the_dark_shield");
    public static final Material LOCATION_HEATER_SHIELD = material("item/heater_shield");
    public static final Material LOCATION_GEM_SHIELD = material("item/gem_shield");
    public static final Material LOCATION_NETHERITE_SHIELD = material("item/netherite_shield");

    @SuppressWarnings("deprecation")
    private static Material material(String path) {
        return new Material(
                TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, path));
    }

}
