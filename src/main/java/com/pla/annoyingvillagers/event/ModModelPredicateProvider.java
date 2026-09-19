package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.item.RedAxeItem;
import com.pla.annoyingvillagers.item.ShadowObsidianPillarItem;
import com.pla.annoyingvillagers.item.ShadowObsidianSwordItem;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingVillagers.MODID)
public class ModModelPredicateProvider {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get()
            );
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingVillagersModItems.HEATER_SHIELD.get()
            );
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingVillagersModItems.GEM_SHIELD.get()
            );
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,
                    AnnoyingVillagersModItems.NETHERITE_SHIELD.get()
            );
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"blocking"),(stack,world,entity,seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F,AnnoyingVillagersModItems.ENDER_AEGIS.get());
            addShieldPropertyOverrides(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"second_form"),(stack,world,entity,seed) -> EnderAegisItem.isSecondForm(stack) ? 1.0F : 0.0F,AnnoyingVillagersModItems.ENDER_AEGIS.get());
            if (VanillaWeaponAbilityUtil.abilitiesEnabled()) ItemProperties.register(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get(), ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "burst"), (stack, world, entity, seed) -> ShadowObsidianPillarItem.isBurst(stack) ? 1.0F : 0.0F);
            if (VanillaWeaponAbilityUtil.abilitiesEnabled()) ItemProperties.register(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get(), ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "straight_form"), (stack, world, entity, seed) -> ShadowObsidianSwordItem.isStraightForm(stack) ? 1.0F : 0.0F);
            if (VanillaWeaponAbilityUtil.abilitiesEnabled()) ItemProperties.register(AnnoyingVillagersModItems.RED_AXE.get(), ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "giant_form"), (stack, world, entity, seed) -> RedAxeItem.isGiantForm(stack, world) ? 1.0F : 0.0F);
        });
    }

    private static void addShieldPropertyOverrides(ResourceLocation override, ClampedItemPropertyFunction propertyGetter, ItemLike... shields) {
        for (ItemLike shield : shields) {
            ItemProperties.register(shield.asItem(), override, propertyGetter);
        }
    }

    public static final Material LOCATION_JESSICA_THE_DARK_SHIELD = material("item/jessica_the_dark_shield");
    public static final Material LOCATION_HEATER_SHIELD = material("item/heater_shield");
    public static final Material LOCATION_GEM_SHIELD = material("item/gem_shield");
    public static final Material LOCATION_NETHERITE_SHIELD = material("item/netherite_shield");
    public static final Material LOCATION_ENDER_AEGIS = material("item/ender_aegis");

    @SuppressWarnings("deprecation")
    private static Material material(String path) {
        return new Material(
                TextureAtlas.LOCATION_BLOCKS, ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, path));
    }

}
