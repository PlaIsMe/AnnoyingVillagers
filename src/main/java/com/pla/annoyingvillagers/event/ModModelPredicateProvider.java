package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingVillagers.MODID)
public class ModModelPredicateProvider {

    public static final Material LOCATION_JESSICA_THE_DARK_SHIELD = material("item/jessica_the_dark_shield");
    public static final Material LOCATION_HEATER_SHIELD = material("item/heater_shield");
    public static final Material LOCATION_GEM_SHIELD = material("item/gem_shield");
    public static final Material LOCATION_NETHERITE_SHIELD = material("item/netherite_shield");
    public static final Material LOCATION_ENDER_AEGIS = material("item/ender_aegis");

    @SuppressWarnings("deprecation")
    private static Material material(String path) {
        return new Material(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, path));
    }

}
