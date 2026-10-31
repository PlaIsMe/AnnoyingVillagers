package com.pla.annoyinglib.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class EmeraldArmorItem extends ArmorItem {

    public EmeraldArmorItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 48;  // 624
                    case LEGGINGS   -> 15 * 48;  // 720
                    case CHESTPLATE -> 16 * 48;  // 768
                    case HELMET     -> 11 * 48;  // 528
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
                return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.EMERALD));
            }

            public @NotNull String getName() {
                return "emerald_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.2F;
            }
        }, type, properties);
    }

    public static class Boots extends EmeraldArmorItem {

        public Boots() {
            super(Type.BOOTS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
            super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
            if (player.getItemBySlot(EquipmentSlot.FEET) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1));
                }
            }
        }
    }

    public static class Leggings extends EmeraldArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/emerald_armor_layer_2.png";
        }

        @Override
        public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
            super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
            if (player.getItemBySlot(EquipmentSlot.LEGS) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1));
                }
            }
        }
    }

    public static class Chestplate extends EmeraldArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
            super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
            if (player.getItemBySlot(EquipmentSlot.CHEST) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1));
                }
            }
        }
    }

    public static class Helmet extends EmeraldArmorItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyinglib:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
            super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
            if (player.getItemBySlot(EquipmentSlot.HEAD) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 100, 1));
                }
            }
        }
    }
}
