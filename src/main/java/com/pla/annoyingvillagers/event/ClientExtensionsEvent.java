package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelGreenVillagerKnightArmor;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondChestplate;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondChestplateArmor;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondHelmet;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondHelmetArmor;
import com.pla.annoyingvillagers.client.model.ModelVillagerKnightArmor;
import com.pla.annoyingvillagers.client.model.ModelVillagerScoutHelmet;
import com.pla.annoyingvillagers.client.renderer.HookGunItemRenderer;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingVillagers.MODID)
public final class ClientExtensionsEvent {
    private ClientExtensionsEvent() {
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(villagerKnightHelmetExtensions(),
                AnnoyingVillagersModItems.BLUE_VILLAGER_KNIGHT_HELMET.get(),
                AnnoyingVillagersModItems.GREEN_VILLAGER_KNIGHT_HELMET.get(),
                AnnoyingVillagersModItems.PURPLE_VILLAGER_KNIGHT_HELMET.get(),
                AnnoyingVillagersModItems.RED_VILLAGER_KNIGHT_HELMET.get());
        event.registerItem(greenVillagerKnightChestplateExtensions(),
                AnnoyingVillagersModItems.GREEN_VILLAGER_KNIGHT_CHESTPLATE.get());
        event.registerItem(herobrineObsidianChestplateExtensions(),
                AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE.get());
        event.registerItem(herobrineObsidianHelmetExtensions(),
                AnnoyingVillagersModItems.HEROBRINE_OBSIDIAN_DIAMOND_HELMET.get());
        event.registerItem(villagerScoutHelmetExtensions(),
                AnnoyingVillagersModItems.VILLAGER_SCOUT_HELMET.get());
        event.registerItem(hookGunExtensions(), AnnoyingVillagersModItems.HOOK_GUN.get());

        IClientMobEffectExtensions hiddenEffectExtensions = new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean isVisibleInGui(MobEffectInstance instance) {
                return false;
            }
        };
        event.registerMobEffect(hiddenEffectExtensions,
                AnnoyingVillagersModMobEffects.CAPTIVE.get(),
                AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get());
    }

    private static IClientItemExtensions villagerKnightHelmetExtensions() {
        return new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                    EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                ModelVillagerKnightArmor<?> helmet = new ModelVillagerKnightArmor<>(
                        Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerKnightArmor.LAYER_LOCATION));
                HumanoidModel<LivingEntity> armorModel = createArmorModel(helmet.Head, null, null, null);
                copyRenderState(armorModel, livingEntity, original);
                return armorModel;
            }
        };
    }

    private static IClientItemExtensions greenVillagerKnightChestplateExtensions() {
        return new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                    EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                ModelGreenVillagerKnightArmor<?> chestplate = new ModelGreenVillagerKnightArmor<>(
                        Minecraft.getInstance().getEntityModels().bakeLayer(ModelGreenVillagerKnightArmor.LAYER_LOCATION));
                HumanoidModel<LivingEntity> armorModel = createArmorModel(
                        null, chestplate.Body, chestplate.LeftArm, chestplate.RightArm);
                copyRenderState(armorModel, livingEntity, original);
                return armorModel;
            }
        };
    }

    private static IClientItemExtensions herobrineObsidianChestplateExtensions() {
        return new IClientItemExtensions() {
            private ModelHerobrineObsidianDiamondChestplateArmor model;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                    EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.model == null) {
                    this.model = new ModelHerobrineObsidianDiamondChestplateArmor(
                            Minecraft.getInstance().getEntityModels().bakeLayer(
                                    ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION));
                }
                this.model.prepareForRender(livingEntity, original);
                return this.model;
            }
        };
    }

    private static IClientItemExtensions herobrineObsidianHelmetExtensions() {
        return new IClientItemExtensions() {
            private ModelHerobrineObsidianDiamondHelmetArmor model;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                    EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.model == null) {
                    this.model = new ModelHerobrineObsidianDiamondHelmetArmor(
                            Minecraft.getInstance().getEntityModels().bakeLayer(
                                    ModelHerobrineObsidianDiamondHelmet.LAYER_LOCATION));
                }
                this.model.prepareForRender(livingEntity, original);
                return this.model;
            }
        };
    }

    private static IClientItemExtensions villagerScoutHelmetExtensions() {
        return new IClientItemExtensions() {
            private HumanoidModel<LivingEntity> armorModel;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                    EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.armorModel == null) {
                    ModelVillagerScoutHelmet<?> helmet = new ModelVillagerScoutHelmet<>(
                            Minecraft.getInstance().getEntityModels().bakeLayer(ModelVillagerScoutHelmet.LAYER_LOCATION));
                    this.armorModel = createArmorModel(helmet.Head, null, null, null);
                }

                this.armorModel.crouching = livingEntity.isCrouching();
                this.armorModel.riding = livingEntity.isPassenger();
                this.armorModel.young = livingEntity.isBaby();
                return this.armorModel;
            }
        };
    }

    private static IClientItemExtensions hookGunExtensions() {
        return new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return HookGunItemRenderer.getInstance();
            }
        };
    }

    private static HumanoidModel<LivingEntity> createArmorModel(ModelPart head, ModelPart body,
                                                                 ModelPart leftArm, ModelPart rightArm) {
        ModelPart root = new ModelPart(Collections.emptyList(), Map.of(
                "head", partOrEmpty(head),
                "hat", emptyPart(),
                "body", partOrEmpty(body),
                "left_arm", partOrEmpty(leftArm),
                "right_arm", partOrEmpty(rightArm),
                "left_leg", emptyPart(),
                "right_leg", emptyPart()
        ));
        return new HumanoidModel<>(root);
    }

    private static ModelPart partOrEmpty(ModelPart part) {
        return part != null ? part : emptyPart();
    }

    private static ModelPart emptyPart() {
        return new ModelPart(Collections.emptyList(), Collections.emptyMap());
    }

    private static void copyRenderState(HumanoidModel<?> armorModel, LivingEntity livingEntity,
                                        HumanoidModel<?> original) {
        armorModel.crouching = livingEntity.isShiftKeyDown();
        armorModel.riding = original.riding;
        armorModel.young = livingEntity.isBaby();
    }
}
