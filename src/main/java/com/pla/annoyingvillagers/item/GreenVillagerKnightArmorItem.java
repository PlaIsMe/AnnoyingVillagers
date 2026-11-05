package com.pla.annoyingvillagers.item;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelVillagerKnightArmor;
import com.pla.annoyingvillagers.client.model.ModelGreenVillagerKnightArmor;
import com.pla.annoyingvillagers.client.model.ModelPoseSyncedArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public abstract class GreenVillagerKnightArmorItem extends LegacyArmorItem {

    public GreenVillagerKnightArmorItem(LegacyArmorItem.Type type, Properties properties) {
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
                    case BOOTS      -> 4;
                    case LEGGINGS   -> 5;
                    case CHESTPLATE -> 7;
                    case HELMET     -> 5;
                    case BODY       -> 7;
                };
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Object getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            public Ingredient getRepairIngredient() {
                return null;
            }

            public String getName() {
                return "green_villager_knight_armor";
            }

            public float getToughness() {
                return 2.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Chestplate extends GreenVillagerKnightArmorItem {

        public Chestplate() {
            super(Type.CHESTPLATE, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                private Model model;

                @Override
                public @NotNull Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
                    if (this.model == null) {
                        ModelGreenVillagerKnightArmor<LivingEntity> geometry = new ModelGreenVillagerKnightArmor<>(
                                Minecraft.getInstance().getEntityModels().bakeLayer(ModelGreenVillagerKnightArmor.LAYER_LOCATION));
                        ModelPart emptyHead = new ModelPart(Collections.emptyList(), Map.of(
                                "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap())));
                        ModelPart root = new ModelPart(Collections.emptyList(), Map.of(
                                "body", geometry.Body,
                                "left_arm", geometry.LeftArm,
                                "right_arm", geometry.RightArm,
                                "head", emptyHead,
                                "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())));
                        this.model = new ModelPoseSyncedArmor(root);
                    }
                    return this.model;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/green_villager_knight_armor_layer.png";
        }
    }

    public static class Helmet extends GreenVillagerKnightArmorItem {

        public Helmet() {
            super(Type.HELMET, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
                    return new HumanoidModel<>(com.pla.annoyingvillagers.client.compat.LegacyHumanoidModel.adaptLegacyRoot(new ModelPart(Collections.emptyList(), Map.of("head", (new ModelVillagerKnightArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerKnightArmor.LAYER_LOCATION))).Head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())))));
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/entities/green.png";
        }
    }
}

