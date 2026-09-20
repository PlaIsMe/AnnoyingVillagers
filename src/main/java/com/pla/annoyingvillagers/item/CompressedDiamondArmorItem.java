package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class CompressedDiamondArmorItem extends LegacyArmorItem {

    public CompressedDiamondArmorItem(LegacyArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(LegacyArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 71;  // 923
                    case LEGGINGS   -> 15 * 71;  // 1065
                    case CHESTPLATE -> 16 * 71;  // 1136
                    case HELMET     -> 11 * 71;  // 781
                    case BODY       -> 16 * 71;
                };
            }

            @Override
            public int getDefenseForType(LegacyArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 5;
                    case LEGGINGS   -> 8;
                    case CHESTPLATE -> 9;
                    case HELMET     -> 7;
                    case BODY       -> 9;
                };
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Object getEquipSound() {
                return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(AnnoyingVillagersModItems.COMPRESSED_DIAMOND.get(), Items.DIAMOND_HELMET, Items.DIAMOND);
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
            super(Type.BOOTS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/compressed_diamond_armor_layer_1.png";
        }
    }

    public static class Leggings extends CompressedDiamondArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/compressed_diamond_armor_layer_2.png";
        }
    }

    public static class Chestplate extends CompressedDiamondArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/compressed_diamond_armor_layer_1.png";
        }
    }

    public static class Helmet extends CompressedDiamondArmorItem {

        public Helmet() {
            super(Type.HELMET, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/compressed_diamond_armor_layer_1.png";
        }
    }
}
