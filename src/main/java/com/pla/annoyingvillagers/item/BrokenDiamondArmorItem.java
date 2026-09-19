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
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class BrokenDiamondArmorItem extends LegacyArmorItem {

    public BrokenDiamondArmorItem(ArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 10;
                    case LEGGINGS   -> 15 * 10;
                    case CHESTPLATE -> 16 * 10;
                    case HELMET     -> 11 * 10;
                    case BODY       -> 16 * 10;
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 2;
                    case LEGGINGS   -> 6;
                    case CHESTPLATE -> 5;
                    case HELMET     -> 3;
                    case BODY       -> 5;
                };
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public Object getEquipSound() {
                return (SoundEvent) Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public @NotNull String getName() {
                return "broken_diamond_armor";
            }

            public float getToughness() {
                return 1.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Boots extends BrokenDiamondArmorItem {

        public Boots() {
            super(Type.BOOTS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/broken_diamond_armor_layer_1.png";
        }
    }

    public static class Leggings extends BrokenDiamondArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/broken_diamond_armor_layer_2.png";
        }
    }

    public static class Chestplate extends BrokenDiamondArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/broken_diamond_armor_layer_1.png";
        }
    }

    public static class Helmet extends BrokenDiamondArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/broken_diamond_armor_layer_1.png";
        }
    }
}
