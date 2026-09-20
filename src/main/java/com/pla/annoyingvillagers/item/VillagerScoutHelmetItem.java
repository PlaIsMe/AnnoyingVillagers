package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.client.model.ModelVillagerScoutHelmet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class VillagerScoutHelmetItem extends LegacyArmorItem {

    public VillagerScoutHelmetItem(LegacyArmorItem.Type type, Properties properties) {
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
                    case BOOTS      -> 1;
                    case LEGGINGS   -> 3;
                    case CHESTPLATE -> 5;
                    case HELMET     -> 4;
                    case BODY       -> 5;
                };
            }

            public int getEnchantmentValue() {
                return 9;
            }

            public Object getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            public Ingredient getRepairIngredient() {
                return null;
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
            super(Type.HELMET, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
        }

        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                HumanoidModel<HumanoidRenderState> armorModel = null;

                @Override
                public @NotNull Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
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

                        armorModel = new HumanoidModel<>(com.pla.annoyingvillagers.client.compat.LegacyHumanoidModel.adaptLegacyRoot(root));
                    }

                    return armorModel;
                }
            });
        }


        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/villager_scout_layer.png";
        }
    }
}
