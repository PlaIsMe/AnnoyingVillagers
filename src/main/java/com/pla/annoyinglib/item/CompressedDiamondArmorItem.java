package com.pla.annoyinglib.item;

import com.pla.annoyinglib.init.AnnoyingLibModItems;
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

public abstract class CompressedDiamondArmorItem extends ArmorItem {

    public CompressedDiamondArmorItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 71;  // 923
                    case LEGGINGS   -> 15 * 71;  // 1065
                    case CHESTPLATE -> 16 * 71;  // 1136
                    case HELMET     -> 11 * 71;  // 781
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 5;
                    case LEGGINGS   -> 8;
                    case CHESTPLATE -> 9;
                    case HELMET     -> 7;
                };
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public @NotNull SoundEvent getEquipSound() {
                return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack[]{new ItemStack(AnnoyingLibModItems.COMPRESSED_DIAMOND.get()), new ItemStack(Items.DIAMOND_HELMET), new ItemStack(Items.DIAMOND)});
            }

            public @NotNull String getName() {
                return "compressed_diamond_armor";
            }

            public float getToughness() {
                return 1.8F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Boots extends CompressedDiamondArmorItem {

        public Boots() {
            super(Type.BOOTS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/compressed_diamond_armor_layer_1.png";
        }
    }

    public static class Leggings extends CompressedDiamondArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/compressed_diamond_armor_layer_2.png";
        }
    }

    public static class Chestplate extends CompressedDiamondArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/compressed_diamond_armor_layer_1.png";
        }
    }

    public static class Helmet extends CompressedDiamondArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/compressed_diamond_armor_layer_1.png";
        }
    }
}
