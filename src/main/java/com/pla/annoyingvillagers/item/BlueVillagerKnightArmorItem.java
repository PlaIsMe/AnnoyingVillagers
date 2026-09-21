package com.pla.annoyingvillagers.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.NotNull;

public abstract class BlueVillagerKnightArmorItem extends LegacyArmorItem {

    public BlueVillagerKnightArmorItem(ArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(Type pType) {
                return switch (pType) {
                    case BOOTS      -> 13 * 25;
                    case LEGGINGS   -> 15 * 25;
                    case CHESTPLATE -> 16 * 25;
                    case HELMET     -> 11 * 25;
                    case BODY       -> 16 * 25;
                };
            }

            @Override
            public int getDefenseForType(Type pType) {
                return switch (pType) {
                    case BOOTS      -> 4;
                    case LEGGINGS   -> 6;
                    case CHESTPLATE -> 7;
                    case HELMET     -> 5;
                    case BODY       -> 7;
                };
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Object getEquipSound() {
                return (SoundEvent) BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_chain"));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public @NotNull String getName() {
                return "blue_villager_knight_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Chestplate extends BlueVillagerKnightArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/blue_villager_knight_armor_layer.png";
        }
    }

    public static class Helmet extends BlueVillagerKnightArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/blue.png";
        }
    }
}
