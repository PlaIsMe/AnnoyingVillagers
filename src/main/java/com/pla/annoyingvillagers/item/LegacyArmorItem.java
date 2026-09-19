package com.pla.annoyingvillagers.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/** Pre-1.21 armor constructor routed into the component-backed armor implementation. */
public class LegacyArmorItem extends ArmorItem {
    protected LegacyArmorItem(LegacyArmorMaterial material, Type type, Properties properties) {
        super(material.asHolder(), type, properties.durability(material.getDurabilityForType(type)));
    }

    /**
     * Compatibility hook retained by the generated 1.20 armor classes.
     * Their overrides are intentionally left untouched and are dispatched by
     * the 1.21 NeoForge hook below.
     */
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return null;
    }

    @Override
    @Nullable
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot,
                                            ArmorMaterial.Layer layer, boolean innerModel) {
        String texture = this.getArmorTexture(stack, entity, slot, innerModel ? "2" : "1");
        return texture == null ? null : ResourceLocation.parse(texture);
    }
}
