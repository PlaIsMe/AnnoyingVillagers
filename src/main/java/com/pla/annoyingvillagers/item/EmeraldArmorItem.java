package com.pla.annoyingvillagers.item;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class EmeraldArmorItem extends LegacyArmorItem {

    public EmeraldArmorItem(LegacyArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(LegacyArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 48;  // 624
                    case LEGGINGS   -> 15 * 48;  // 720
                    case CHESTPLATE -> 16 * 48;  // 768
                    case HELMET     -> 11 * 48;  // 528
                    case BODY       -> 16 * 48;
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
                return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("minecraft", "item.armor.equip_diamond")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.EMERALD);
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
            super(Type.BOOTS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotIndex = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
            if (!(entity instanceof Player player)) return;
            super.inventoryTick(stack, level, entity, equipmentSlot);
            if (player.getItemBySlot(EquipmentSlot.FEET) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 100, 1));
                }
            }
        }
    }

    public static class Leggings extends EmeraldArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_2.png";
        }

        @Override
        public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotIndex = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
            if (!(entity instanceof Player player)) return;
            super.inventoryTick(stack, level, entity, equipmentSlot);
            if (player.getItemBySlot(EquipmentSlot.LEGS) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 100, 1));
                }
            }
        }
    }

    public static class Chestplate extends EmeraldArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotIndex = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
            if (!(entity instanceof Player player)) return;
            super.inventoryTick(stack, level, entity, equipmentSlot);
            if (player.getItemBySlot(EquipmentSlot.CHEST) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 100, 1));
                }
            }
        }
    }

    public static class Helmet extends EmeraldArmorItem {

        public Helmet() {
            super(Type.HELMET, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/emerald_armor_layer_1.png";
        }

        @Override
        public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotIndex = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
            if (!(entity instanceof Player player)) return;
            super.inventoryTick(stack, level, entity, equipmentSlot);
            if (player.getItemBySlot(EquipmentSlot.HEAD) == stack) {
                if (!player.level().isClientSide()) {
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 100, 1));
                }
            }
        }
    }
}
