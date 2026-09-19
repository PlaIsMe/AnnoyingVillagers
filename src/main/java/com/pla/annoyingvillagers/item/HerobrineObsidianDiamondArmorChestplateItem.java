package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondChestplate;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondChestplateArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import static com.pla.annoyingvillagers.util.ArmorUtil.dropArmorSlot;

public abstract class HerobrineObsidianDiamondArmorChestplateItem extends LegacyArmorItem {
    private static final int CHARGE_METER_STEPS = 10;
    private static final int CHARGE_COLOR = 0xB05CFF;
    private static final int CHARGE_DIM_COLOR = 0x352243;
    private static final int CHARGE_TEXT_COLOR = 0xDDBBFF;
    private static final int CHARGE_FULL_COLOR = 0xD37CFF;

    public HerobrineObsidianDiamondArmorChestplateItem(ArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(Type pType) {
                return switch (pType) {
                    case BOOTS -> 13 * 25;
                    case LEGGINGS -> 15 * 25;
                    case CHESTPLATE -> 600;
                    case HELMET -> 11 * 25;
                    case BODY -> 600;
                };
            }

            @Override
            public int getDefenseForType(Type pType) {
                return switch (pType) {
                    case BOOTS -> 0;
                    case LEGGINGS -> 0;
                    case CHESTPLATE -> 24;
                    case HELMET -> 0;
                    case BODY -> 24;
                };
            }

            public int getEnchantmentValue() { return 0; }
            public Object getEquipSound() { return SoundEvents.ARMOR_EQUIP_GENERIC; }
            public Ingredient getRepairIngredient() { return Ingredient.of(); }
            public String getName() { return "herobrine_obsidian_diamond_armor"; }
            public float getToughness() { return 2.0F; }
            public float getKnockbackResistance() { return 0.0F; }
        }, type, properties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack) || HerobrineObsidianArmorCharge.isFullyCharged(stack) || HerobrineObsidianArmorCharge.hasForcedPurpleFoil(stack);
    }

    protected static void appendChargeTooltip(ItemStack stack, List<Component> tooltip) {
        int charge = HerobrineObsidianArmorCharge.getCharge(stack);
        int max = HerobrineObsidianArmorCharge.CHESTPLATE_MAX_CHARGE;
        tooltip.add(Component.translatable("tooltip.annoyingvillagers.herobrine_obsidian_armor_charge").withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHARGE_COLOR))));
        tooltip.add(Component.literal(charge + " / " + max).withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_TEXT_COLOR))));
        tooltip.add(buildChargeMeter(charge, max));
    }

    private static Component buildChargeMeter(int charge, int max) {
        int filledSteps = Mth.clamp(Math.round((charge / (float)max) * CHARGE_METER_STEPS), 0, CHARGE_METER_STEPS);
        MutableComponent meter = Component.empty();
        meter.append(Component.literal("❒ ").withStyle(style -> style.withColor(TextColor.fromRgb(CHARGE_COLOR))));
        for (int i = 0; i < CHARGE_METER_STEPS; i++) {
            int finalI = i;
            meter.append(Component.literal(i < filledSteps ? "▰" : "▱").withStyle(style -> style.withColor(TextColor.fromRgb(finalI < filledSteps ? CHARGE_COLOR : CHARGE_DIM_COLOR))));
        }
        return meter;
    }

    public static class Chestplate extends HerobrineObsidianDiamondArmorChestplateItem {
        public Chestplate() {
            super(Type.CHESTPLATE, new Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                private ModelHerobrineObsidianDiamondChestplateArmor model;

                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    if (this.model == null) this.model = new ModelHerobrineObsidianDiamondChestplateArmor(Minecraft.getInstance().getEntityModels().bakeLayer(ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION));
                    this.model.prepareForRender(livingEntity, original);
                    return this.model;
                }
            });
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return "annoyingvillagers:textures/models/armor/herobrine_obsidian_armor_layer_1.png";
        }

        @Override
        public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotIndex, boolean selected) {
            if (!(entity instanceof Player player)) return;
            super.inventoryTick(stack, level, entity, slotIndex, selected);
            if (player.getItemBySlot(EquipmentSlot.CHEST) == stack) {
                dropArmorSlot(player, EquipmentSlot.FEET, "Herobrine Obsidian Diamond Chestplate");
                dropArmorSlot(player, EquipmentSlot.LEGS, "Herobrine Obsidian Diamond Chestplate");
            }
        }

        @Override
        public void appendHoverText(@NotNull ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
            super.appendHoverText(stack, level, tooltip, flag);
            tooltip.add(Component.translatable("tooltip.annoyingvillagers.herobrine_obsidian_chestplate"));
            appendChargeTooltip(stack, tooltip);
        }
    }
}
