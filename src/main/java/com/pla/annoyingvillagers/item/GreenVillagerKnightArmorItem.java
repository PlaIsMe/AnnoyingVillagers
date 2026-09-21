package com.pla.annoyingvillagers.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class GreenVillagerKnightArmorItem extends LegacyArmorItem {

    public GreenVillagerKnightArmorItem(ArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 25;
                    case LEGGINGS   -> 15 * 25;
                    case CHESTPLATE -> 16 * 25;
                    case HELMET     -> 11 * 25;
                    case BODY       -> 16 * 25;
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 4;
                    case LEGGINGS   -> 5;
                    case CHESTPLATE -> 7;
                    case HELMET     -> 5;
                    case BODY       -> 7;
                };
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Object getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "green_villager_knight_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Chestplate extends GreenVillagerKnightArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/green_villager_knight_armor_layer.png";
        }
    }

    public static class Helmet extends GreenVillagerKnightArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/green.png";
        }
    }
}

