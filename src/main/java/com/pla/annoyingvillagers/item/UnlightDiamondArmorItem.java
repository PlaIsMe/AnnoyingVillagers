package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
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

public abstract class UnlightDiamondArmorItem extends LegacyArmorItem {

    public UnlightDiamondArmorItem(LegacyArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(LegacyArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 46;
                    case LEGGINGS   -> 15 * 46;
                    case CHESTPLATE -> 16 * 46;
                    case HELMET     -> 11 * 46;
                    case BODY       -> 16 * 46;
                };
            }

            @Override
            public int getDefenseForType(LegacyArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 4;
                    case LEGGINGS   -> 5;
                    case CHESTPLATE -> 8;
                    case HELMET     -> 5;
                    case BODY       -> 8;
                };
            }
            public int getEnchantmentValue() {
                return 10;
            }

            public Object getEquipSound() {
                return (SoundEvent) Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.DIAMOND);
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
            super(Type.BOOTS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/unlight_diamond_layer_1.png";
        }
    }

    public static class Leggings extends UnlightDiamondArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/unlight_diamond_layer_2.png";
        }
    }

    public static class Chestplate extends UnlightDiamondArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/unlight_diamond_layer_1.png";
        }
    }

    public static class Helmet extends UnlightDiamondArmorItem {

        public Helmet() {
            super(Type.HELMET, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/unlight_diamond_layer_1.png";
        }
    }
}
