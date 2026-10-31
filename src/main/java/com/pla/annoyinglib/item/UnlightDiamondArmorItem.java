package com.pla.annoyinglib.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class UnlightDiamondArmorItem extends ArmorItem {

    public UnlightDiamondArmorItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 46;
                    case LEGGINGS   -> 15 * 46;
                    case CHESTPLATE -> 16 * 46;
                    case HELMET     -> 11 * 46;
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 4;
                    case LEGGINGS   -> 5;
                    case CHESTPLATE -> 8;
                    case HELMET     -> 5;
                };
            }
            public int getEnchantmentValue() {
                return 10;
            }

            public @NotNull SoundEvent getEquipSound() {
                return (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(Items.DIAMOND)});
            }

            public @NotNull String getName() {
                return "unlight_diamond_armor";
            }

            public float getToughness() {
                return 2.1F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Boots extends UnlightDiamondArmorItem {

        public Boots() {
            super(Type.BOOTS, (new Properties()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/unlight_diamond_layer_1.png";
        }
    }

    public static class Leggings extends UnlightDiamondArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (new Properties()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/unlight_diamond_layer_2.png";
        }
    }

    public static class Chestplate extends UnlightDiamondArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/unlight_diamond_layer_1.png";
        }
    }

    public static class Helmet extends UnlightDiamondArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/unlight_diamond_layer_1.png";
        }
    }
}
