package com.pla.annoyingvillagers.item;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

import javax.annotation.Nullable;

/** Pre-1.21 armor constructor routed into the component-backed armor implementation. */
public class LegacyArmorItem extends Item {
    private final Type type;

    protected LegacyArmorItem(LegacyArmorMaterial material, Type type, Properties properties) {
        super(material.applyProperties(type, properties));
        this.type = type;
    }

    public Type getType() { return type; }
    public EquipmentSlot getEquipmentSlot() { return type.getSlot(); }

    /**
     * Compatibility hook retained by the generated 1.20 armor classes.
     * Their overrides are intentionally left untouched and are dispatched by
     * the 1.21 NeoForge hook below.
     */
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return null;
    }

    public enum Type {
        HELMET(ArmorType.HELMET),
        CHESTPLATE(ArmorType.CHESTPLATE),
        LEGGINGS(ArmorType.LEGGINGS),
        BOOTS(ArmorType.BOOTS),
        BODY(ArmorType.BODY);

        private final ArmorType armorType;
        Type(ArmorType armorType) { this.armorType = armorType; }
        public EquipmentSlot getSlot() { return armorType.getSlot(); }
        public String getName() { return armorType.getName(); }
    }
}
