package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlueDemonThrownTridentEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.BlueDemonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.pla.annoyingvillagers.util.ArmorUtil.dropArmorSlot;

public abstract class BlueDemonChestplateItem extends LegacyArmorItem {
    private static final String TAG_CHEST_CHARGE = "BlueDemonChestCharge";
    public static final int MAX_CHEST_CHARGE = 100;

    private static final int CHEST_CHARGE_METER_STEPS = 10;
    private static final int CHEST_CHARGE_COLOR = 0x55F7FF;
    private static final int CHEST_CHARGE_DIM_COLOR = 0x233338;
    private static final int CHEST_CHARGE_TEXT_COLOR = 0xBDFBFF;
    private static final int CHEST_CHARGE_FULL_COLOR = 0x7CFFFF;

    private static final String TAG_CHEST_BUFF_TICKS = "BlueDemonChestBuffTicks";
    public static final int CHEST_BUFF_DURATION_TICKS = 20 * 30;
    public static final double CHEST_TRIDENT_ABSORB_BOX_HALF = 2.5D;
    private static final String TAG_BLUE_DEMON_HEALING_FOIL = "BlueDemonHealingFoil";

    public BlueDemonChestplateItem(LegacyArmorItem.Type type, Properties properties) {
        super(new LegacyArmorMaterial() {
            @Override
            public int getDurabilityForType(@NotNull Type pType) {
                return switch (pType) {
                    case BOOTS -> 13 * 31;
                    case LEGGINGS -> 15 * 31;
                    case CHESTPLATE -> 25 * 31;
                    case HELMET -> 11 * 31;
                    case BODY -> 25 * 31;
                };
            }

            @Override
            public int getDefenseForType(@NotNull Type pType) {
                return switch (pType) {
                    case BOOTS -> 2;
                    case LEGGINGS -> 5;
                    case CHESTPLATE -> 30;
                    case HELMET -> 2;
                    case BODY -> 30;
                };
            }

            @Override
            public int getEnchantmentValue() {
                return 9;
            }

            @Override
            public Object getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_GENERIC;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS);
            }

            @Override
            public @NotNull String getName() {
                return "blue_demon_chestplate";
            }

            @Override
            public float getToughness() {
                return 2.0F;
            }

            @Override
            public float getKnockbackResistance() {
                return 0.0F;
            }
        }, type, properties);
    }

    public static boolean isBlueDemonChestplate(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof BlueDemonChestplateItem;
    }

    public static int getStoredCharge(ItemStack stack) {
        if (!isBlueDemonChestplate(stack)) {
            return 0;
        }

        CompoundTag tag = LegacyItemData.get(stack);
        return tag == null ? 0 : Mth.clamp(tag.getIntOr(TAG_CHEST_CHARGE, 0), 0, MAX_CHEST_CHARGE);
    }

    public static void setStoredCharge(ItemStack stack, int amount) {
        if (!isBlueDemonChestplate(stack)) {
            return;
        }

        int charge = Mth.clamp(amount, 0, MAX_CHEST_CHARGE);
        LegacyItemData.update(stack, tag -> tag.putInt(TAG_CHEST_CHARGE, charge));
    }

    public static void addStoredCharge(ItemStack stack, int amount) {
        if (!isBlueDemonChestplate(stack) || amount <= 0) {
            return;
        }

        int current = getStoredCharge(stack);
        int added = Math.min(amount, MAX_CHEST_CHARGE - current);

        if (added > 0) {
            setStoredCharge(stack, current + added);
        }

    }

    public static boolean isFullyCharged(ItemStack stack) {
        return getStoredCharge(stack) >= MAX_CHEST_CHARGE;
    }

    public static boolean hasBlueDemonHealingFoil(ItemStack stack) {
        if (!isBlueDemonChestplate(stack)) {
            return false;
        }

        CompoundTag tag = LegacyItemData.get(stack);
        return tag != null && tag.getBooleanOr(TAG_BLUE_DEMON_HEALING_FOIL, false);
    }

    public static void setBlueDemonHealingFoil(ItemStack stack, boolean foil) {
        if (!isBlueDemonChestplate(stack)) {
            return;
        }

        if (foil) {
            LegacyItemData.update(stack, tag -> tag.putBoolean(TAG_BLUE_DEMON_HEALING_FOIL, true));
        } else {
            if (LegacyItemData.get(stack) != null) {
                LegacyItemData.update(stack, tag -> tag.remove(TAG_BLUE_DEMON_HEALING_FOIL));
            }
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return super.isFoil(stack)
                || isFullyCharged(stack)
                || isBuffActive(stack)
                || hasBlueDemonHealingFoil(stack);
    }

    public static int getBuffTicks(ItemStack stack) {
        if (!isBlueDemonChestplate(stack)) {
            return 0;
        }

        CompoundTag tag = LegacyItemData.get(stack);
        return tag == null ? 0 : Math.max(0, tag.getIntOr(TAG_CHEST_BUFF_TICKS, 0));
    }

    public static void setBuffTicks(ItemStack stack, int ticks) {
        if (!isBlueDemonChestplate(stack)) {
            return;
        }

        int clamped = Math.max(0, ticks);

        if (clamped == 0) {
            if (LegacyItemData.get(stack) != null) {
                LegacyItemData.update(stack, tag -> tag.remove(TAG_CHEST_BUFF_TICKS));
            }
            return;
        }

        LegacyItemData.update(stack, tag -> tag.putInt(TAG_CHEST_BUFF_TICKS, clamped));
    }

    public static void stopBuff(ItemStack stack) {
        if (!isBlueDemonChestplate(stack)) {
            return;
        }

        setBuffTicks(stack, 0);
    }

    public static void activateBuff(ItemStack stack) {
        if (!isBlueDemonChestplate(stack)) {
            return;
        }

        if (!isFullyCharged(stack)) {
            return;
        }
        stopBuff(stack);

        setBuffTicks(stack, CHEST_BUFF_DURATION_TICKS);
        setStoredCharge(stack, 0);
    }

    public static boolean isBuffActive(ItemStack stack) {
        return getBuffTicks(stack) > 0;
    }

    public static void tickActiveBuff(ItemStack stack, Player player) {
        if (!isBlueDemonChestplate(stack)) {
            return;
        }

        int ticks = getBuffTicks(stack);
        if (ticks <= 0) {
            return;
        }

        if (player.level() instanceof ServerLevel serverLevel) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.SPEED,
                    1,
                    1,
                    false,
                    false,
                    false
            ));

            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.JUMP_BOOST,
                    1,
                    1,
                    false,
                    false,
                    false
            ));

            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.RESISTANCE,
                    1,
                    2,
                    false,
                    false,
                    false
            ));

            if (serverLevel.getRandom().nextDouble() <= 0.1D) {
                BlueDemonUtil.spawnBlueDemonChestplateEffect(serverLevel, player);

                if (serverLevel.getRandom().nextDouble() <= 0.8D) {
                    float volume = (float) Mth.nextDouble(serverLevel.getRandom(), 0.05D, 0.5D);
                    float pitch = (float) Mth.nextDouble(serverLevel.getRandom(), 0.8D, 1.1D);

                    serverLevel.playSound(
                            null,
                            BlockPos.containing(player.getX(), player.getY(), player.getZ()),
                            AnnoyingVillagersModSounds.ELECTRIFY.get(),
                            SoundSource.NEUTRAL,
                            volume,
                            pitch
                    );
                }
            }

            if (player.tickCount % 2 == 0) {
                absorbNearbyGroundedOwnerTridents(serverLevel, player);
            }
        }

        setBuffTicks(stack, ticks - 1);
    }

    private static void absorbNearbyGroundedOwnerTridents(ServerLevel serverLevel, Player player) {
        AABB box = new AABB(
                player.getX() - CHEST_TRIDENT_ABSORB_BOX_HALF,
                player.getY() - CHEST_TRIDENT_ABSORB_BOX_HALF,
                player.getZ() - CHEST_TRIDENT_ABSORB_BOX_HALF,
                player.getX() + CHEST_TRIDENT_ABSORB_BOX_HALF,
                player.getY() + CHEST_TRIDENT_ABSORB_BOX_HALF,
                player.getZ() + CHEST_TRIDENT_ABSORB_BOX_HALF
        );

        List<BlueDemonThrownTridentEntity> tridents = serverLevel.getEntitiesOfClass(
                BlueDemonThrownTridentEntity.class,
                box,
                trident -> trident.isAlive()
                        && trident.isGroundedTrident()
                        && trident.belongsToOwner(player)
                        && !trident.isAbsorbingToWearer()
        );

        for (BlueDemonThrownTridentEntity trident : tridents) {
            trident.beginAbsorbToWearer(player);
        }
    }

    public static class Chestplate extends BlueDemonChestplateItem {
        public Chestplate() {
            super(Type.CHESTPLATE, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()).fireResistant());
        }

        public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlot equipmentslot, String s) {
            return AnnoyingVillagers.MODID + ":textures/models/armor/blue_demon_chestplate_layer.png";
        }

        @Override
        public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotIndex = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
            if (!(entity instanceof Player player)) return;
            super.inventoryTick(stack, level, entity, equipmentSlot);

            if (player.getItemBySlot(EquipmentSlot.CHEST) != stack) {
                if (isBuffActive(stack)) {
                    stopBuff(stack);
                }
                return;
            }

            dropArmorSlot(player, EquipmentSlot.FEET, "Blue Demon Chestplate");
            dropArmorSlot(player, EquipmentSlot.LEGS, "Blue Demon Chestplate");
            dropArmorSlot(player, EquipmentSlot.HEAD, "Blue Demon Chestplate");

            tickActiveBuff(stack, player);
        }

        @Override
        public void appendHoverText(@NotNull ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, @NotNull TooltipFlag flag) {
            super.appendHoverText(stack, level, display, tooltip, flag);

            int charge = getStoredCharge(stack);

            tooltip.accept(Component.translatable("tooltip.annoyingvillagers.blue_demon_chestplate"));
            addChestChargeTooltip(tooltip, charge);
        }

        private static void addChestChargeTooltip(java.util.function.Consumer<Component> tooltip, int charge) {
            tooltip.accept(
                    Component.translatable("tooltip.annoyingvillagers.blue_demon_chestplate_thunder_charge")
                            .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHEST_CHARGE_COLOR)))
            );

            tooltip.accept(
                    Component.literal(charge + " / " + MAX_CHEST_CHARGE)
                            .withStyle(style -> style.withColor(TextColor.fromRgb(CHEST_CHARGE_TEXT_COLOR)))
            );

            tooltip.accept(buildChestChargeMeter(charge));

            if (charge >= MAX_CHEST_CHARGE) {
                tooltip.accept(
                        Component.translatable("tooltip.annoyingvillagers.thunder_charged")
                                .withStyle(style -> style.withBold(true).withColor(TextColor.fromRgb(CHEST_CHARGE_FULL_COLOR)))
                );
            }
        }

        private static Component buildChestChargeMeter(int charge) {
            int filledSteps = Math.round((charge / (float) MAX_CHEST_CHARGE) * CHEST_CHARGE_METER_STEPS);
            filledSteps = Mth.clamp(filledSteps, 0, CHEST_CHARGE_METER_STEPS);

            MutableComponent meter = Component.empty();

            meter.append(
                    Component.literal("â›¨ ")
                            .withStyle(style -> style.withColor(TextColor.fromRgb(CHEST_CHARGE_COLOR)))
            );

            for (int i = 0; i < CHEST_CHARGE_METER_STEPS; i++) {
                boolean filled = i < filledSteps;

                meter.append(
                        Component.literal(filled ? "â–°" : "â–±")
                                .withStyle(style -> style.withColor(TextColor.fromRgb(
                                        filled ? CHEST_CHARGE_COLOR : CHEST_CHARGE_DIM_COLOR
                                )))
                );
            }

            return meter;
        }
    }
}
