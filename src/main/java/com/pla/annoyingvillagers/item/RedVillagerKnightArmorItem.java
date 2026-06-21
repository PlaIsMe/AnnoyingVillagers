package com.pla.annoyingvillagers.item;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.pla.annoyingvillagers.client.model.ModelVillagerKnightArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public abstract class RedVillagerKnightArmorItem extends ArmorItem {

    public RedVillagerKnightArmorItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 25;
                    case LEGGINGS   -> 15 * 25;
                    case CHESTPLATE -> 16 * 25;
                    case HELMET     -> 11 * 25;
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.@NotNull Type type) {
                return switch (type) {
                    case BOOTS      -> 4;
                    case LEGGINGS   -> 5;
                    case CHESTPLATE -> 7;
                    case HELMET     -> 5;
                };
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public @NotNull SoundEvent getEquipSound() {
                return (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "item.armor.equip_chain")));
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public @NotNull String getName() {
                return "red_villager_knight_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Boots extends RedVillagerKnightArmorItem {

        public Boots() {
            super(Type.BOOTS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/red_villager_knight_armor_layer_1.png";
        }
    }

    public static class Leggings extends RedVillagerKnightArmorItem {

        public Leggings() {
            super(Type.LEGGINGS, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/red_villager_knight_armor_layer_2.png";
        }
    }

    public static class Chestplate extends RedVillagerKnightArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (new Properties()));
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/red_villager_knight_armor_layer_1.png";
        }
    }

    public static class Armor extends RedVillagerKnightArmorItem {

        public Armor() {
            super(Type.HELMET, (new Properties()));
        }

        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    HumanoidModel humanoidmodel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("head", (new ModelVillagerKnightArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerKnightArmor.LAYER_LOCATION))).Head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));

                    humanoidmodel.crouching = livingEntity.isShiftKeyDown();
                    humanoidmodel.riding = original.riding;
                    humanoidmodel.young = livingEntity.isBaby();
                    return humanoidmodel;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/red.png";
        }
    }
}

