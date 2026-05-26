package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.client.model.ModelVillagerScoutHelmet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class VillagerScoutHelmetItem extends ArmorItem {

    public VillagerScoutHelmetItem(ArmorItem.Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 13 * 25;
                    case LEGGINGS   -> 15 * 25;
                    case CHESTPLATE -> 16 * 25;
                    case HELMET     -> 11 * 25;
                };
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return switch (type) {
                    case BOOTS      -> 1;
                    case LEGGINGS   -> 3;
                    case CHESTPLATE -> 5;
                    case HELMET     -> 4;
                };
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            public String getName() {
                return "villager_scout_helmet";
            }

            public float getToughness() {
                return 1.0F;
            }

            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static class Helmet extends VillagerScoutHelmetItem {

        public Helmet() {
            super(Type.HELMET, (new Properties()));
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                HumanoidModel<LivingEntity> armorModel = null;

                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    if (armorModel == null) {
                        ModelVillagerScoutHelmet<?> helmetModel = new ModelVillagerScoutHelmet<>(
                                Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerScoutHelmet.LAYER_LOCATION)
                        );

                        ModelPart root = new ModelPart(Collections.emptyList(), Map.of(
                                "head", helmetModel.Head,
                                "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
                        ));

                        armorModel = new HumanoidModel<>(root);
                    }

                    armorModel.crouching = livingEntity.isCrouching();
                    armorModel.riding = livingEntity.isPassenger();
                    armorModel.young = livingEntity.isBaby();

                    return armorModel;
                }
            });
        }


        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/villager_scout_layer.png";
        }
    }
}