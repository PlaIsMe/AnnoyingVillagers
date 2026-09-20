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
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public abstract class RubyArmorItem extends LegacyArmorItem {

    public RubyArmorItem(LegacyArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(LegacyArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 25;
                    case LEGGINGS   -> 15 * 25;
                    case CHESTPLATE -> 16 * 25;
                    case HELMET     -> 11 * 25;
                    case BODY       -> 16 * 25;
                };
            }

            @Override
            public int getDefenseForType(LegacyArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 5;
                    case LEGGINGS   -> 6;
                    case CHESTPLATE -> 9;
                    case HELMET     -> 5;
                    case BODY       -> 9;
                };
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public Object getEquipSound() {
                return (SoundEvent) BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond"));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(AnnoyingVillagersModItems.DARK_NETHERITE.get(), Items.GOLD_INGOT);
            }

            public String getName() {
                return "ruby_armor";
            }

            public float getToughness() {
                return 0.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Boots extends RubyArmorItem {

        public Boots() {
            super(Type.BOOTS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ruby_armor_layer_1.png";
        }
    }

    public static class Leggings extends RubyArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ruby_armor_layer_2.png";
        }
    }

    public static class Chestplate extends RubyArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ruby_armor_layer_1.png";
        }
    }

    public static class Helmet extends RubyArmorItem {

        public Helmet() {
            super(Type.HELMET, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/ruby_armor_layer_1.png";
        }
    }
}

