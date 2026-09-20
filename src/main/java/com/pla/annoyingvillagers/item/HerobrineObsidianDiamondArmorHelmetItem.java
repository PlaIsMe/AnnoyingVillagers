package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondHelmet;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondHelmetArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import static com.pla.annoyingvillagers.util.ArmorUtil.dropArmorSlot;

public abstract class HerobrineObsidianDiamondArmorHelmetItem extends LegacyArmorItem {
    private static final int CHARGE_METER_STEPS = 10;
    private static final int CHARGE_COLOR = 0xB05CFF;
    private static final int CHARGE_DIM_COLOR = 0x352243;
    private static final int CHARGE_TEXT_COLOR = 0xDDBBFF;
    private static final int CHARGE_FULL_COLOR = 0xD37CFF;

    public HerobrineObsidianDiamondArmorHelmetItem(LegacyArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            public int getDurabilityForType(Type pType) {
                return switch (pType) {
                    case BOOTS -> 13 * 25;
                    case LEGGINGS -> 15 * 25;
                    case CHESTPLATE -> 16 * 25;
                    case HELMET -> 500;
                    case BODY -> 16 * 25;
                };
            }

            @Override
            public int getDefenseForType(Type pType) {
                return switch (pType) {
                    case BOOTS -> 0;
                    case LEGGINGS -> 0;
                    case CHESTPLATE -> 0;
                    case HELMET -> 16;
                    case BODY -> 0;
                };
            }

            public int getEnchantmentValue() { return 0; }
            public Object getEquipSound() { return SoundEvents.ARMOR_EQUIP_GENERIC; }
            public Ingredient getRepairIngredient() { return null; }
            public String getName() { return "herobrine_obsidian_diamond_armor"; }
            public float getToughness() { return 2.0F; }
            public float getKnockbackResistance() { return 0.0F; }
        }, type, properties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack) || HerobrineObsidianArmorCharge.isFullyCharged(stack) || HerobrineObsidianArmorCharge.hasForcedPurpleFoil(stack);
    }

    protected static void appendChargeTooltip(ItemStack stack, java.util.function.Consumer<Component> tooltip) {
        int charge = HerobrineObsidianArmorCharge.getCharge(stack);
        int max = HerobrineObsidianArmorCharge.HELMET_MAX_CHARGE;
        tooltip.accept(Component.translatable("tooltip.annoyingvillagers.herobrine_obsidian_armor_charge").withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHARGE_COLOR))));
        tooltip.accept(Component.literal(charge + " / " + max).withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_TEXT_COLOR))));
        tooltip.accept(buildChargeMeter(charge, max));
    }

    private static Component buildChargeMeter(int charge, int max) {
        int filledSteps = Mth.clamp(Math.round((charge / (float)max) * CHARGE_METER_STEPS), 0, CHARGE_METER_STEPS);
        MutableComponent meter = Component.empty();
        meter.append(Component.literal("â’ ").withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_COLOR))));
        for (int i = 0; i < CHARGE_METER_STEPS; i++) {
            int finalI = i;
            meter.append(Component.literal(i < filledSteps ? "â–°" : "â–±").withStyle(style -> style.withColor(TextColor.fromRgb(finalI < filledSteps ? CHARGE_COLOR : CHARGE_DIM_COLOR))));
        }
        return meter;
    }

    public static class Helmet extends HerobrineObsidianDiamondArmorHelmetItem {
        public Helmet() {
            super(Type.HELMET, com.pla.annoyingvillagers.util.LegacyItemProperties.create());
        }

        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                private ModelHerobrineObsidianDiamondHelmetArmor model;

                @Override
                public @NotNull Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
                    if (this.model == null) this.model = new ModelHerobrineObsidianDiamondHelmetArmor(Minecraft.getInstance().getEntityModels().bakeLayer(ModelHerobrineObsidianDiamondHelmet.LAYER_LOCATION));
                    return this.model;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/herobrine_obsidian_armor_layer_2.png";
        }

        @Override
        public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotIndex = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
            if (!(entity instanceof Player player)) return;
            super.inventoryTick(stack, level, entity, equipmentSlot);
            if (player.getItemBySlot(EquipmentSlot.HEAD) == stack) {
                dropArmorSlot(player, EquipmentSlot.FEET, "Herobrine Obsidian Diamond Helmet");
                dropArmorSlot(player, EquipmentSlot.LEGS, "Herobrine Obsidian Diamond Helmet");
            }
        }

        @Override
        public void appendHoverText(@NotNull ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, @NotNull TooltipFlag flag) {
            super.appendHoverText(stack, level, display, tooltip, flag);
            tooltip.accept(Component.translatable("tooltip.annoyingvillagers.herobrine_obsidian_helmet"));
            appendChargeTooltip(stack, tooltip);
        }
    }
}
