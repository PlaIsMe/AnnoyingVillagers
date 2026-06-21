package com.pla.annoyingvillagers.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class ClassicGoldenSetArmorItem extends ArmorItem {

    public ClassicGoldenSetArmorItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 27;
                    case LEGGINGS   -> 15 * 27;
                    case CHESTPLATE -> 16 * 27;
                    case HELMET     -> 11 * 27;
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 3;
                    case LEGGINGS   -> 5;
                    case CHESTPLATE -> 6;
                    case HELMET     -> 3;
                };
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "classic_golden_armor";
            }

            public float getToughness() {
                return 0.7F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Boots extends ClassicGoldenSetArmorItem {

        public Boots() {
            super(Type.BOOTS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold_layer_1.png";
        }
    }

    public static class Leggings extends ClassicGoldenSetArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold_layer_2.png";
        }
    }

    public static class Chestplate extends ClassicGoldenSetArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold_layer_1.png";
        }
    }

    public static class Helmet extends ClassicGoldenSetArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/gold_layer_1.png";
        }
    }
}
