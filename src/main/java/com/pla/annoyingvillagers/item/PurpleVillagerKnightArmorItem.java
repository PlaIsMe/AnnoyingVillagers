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
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

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

        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    HumanoidModel humanoidmodel1 = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("head", (new ModelVillagerKnightArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerKnightArmor.LAYER_LOCATION))).Head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));

                    humanoidmodel1.crouching = livingEntity.isShiftKeyDown();
                    humanoidmodel1.riding = original.riding;
                    humanoidmodel1.young = livingEntity.isBaby();
                    return humanoidmodel1;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/purple.png";
        }
    }
}

