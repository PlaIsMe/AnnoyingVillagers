package com.pla.annoyingvillagers.item;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.registries.BuiltInRegistries;

public abstract class PurpleVillagerKnightArmorItem extends LegacyArmorItem {

    public PurpleVillagerKnightArmorItem(ArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 25;  // 325
                    case LEGGINGS   -> 15 * 25;  // 375
                    case CHESTPLATE -> 16 * 25;  // 400
                    case HELMET     -> 11 * 25;  // 275
                    case BODY       -> 16 * 25;
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return switch (type) {
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
                return (SoundEvent) Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_chain")));
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "purple_villager_knight_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Chestplate extends PurpleVillagerKnightArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/purple_villager_knight_armor_layer.png";
        }
    }

    public static class Helmet extends PurpleVillagerKnightArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/purple.png";
        }
    }
}

