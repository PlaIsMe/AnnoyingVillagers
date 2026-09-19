package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public final class HerobrineObsidianArmorCharge {
    public static final int HELMET_MAX_CHARGE = 25;
    public static final int CHESTPLATE_MAX_CHARGE = 50;
    private static final String HELMET_CHARGE_TAG = "HerobrineObsidianHelmetCharge";
    private static final String CHESTPLATE_CHARGE_TAG = "HerobrineObsidianChestplateCharge";
    private static final String FORCED_PURPLE_FOIL_TAG = "ArmoredHerobrinePurpleFoil";

    private HerobrineObsidianArmorCharge() {}

    public static boolean isHelmet(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof HerobrineObsidianDiamondArmorHelmetItem;
    }

    public static boolean isChestplate(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof HerobrineObsidianDiamondArmorChestplateItem;
    }

    public static boolean isObsidianArmor(ItemStack stack) {
        return isHelmet(stack) || isChestplate(stack);
    }

    public static int maxCharge(ItemStack stack) {
        if (isHelmet(stack)) return HELMET_MAX_CHARGE;
        if (isChestplate(stack)) return CHESTPLATE_MAX_CHARGE;
        return 0;
    }

    public static int getCharge(ItemStack stack) {
        int max = maxCharge(stack);
        if (max <= 0) return 0;
        CompoundTag tag = LegacyItemData.get(stack);
        if (tag == null) return 0;
        return Mth.clamp(tag.getInt(tagName(stack)), 0, max);
    }

    public static void setCharge(ItemStack stack, int charge) {
        int max = maxCharge(stack);
        if (max <= 0) return;
        LegacyItemData.getOrCreate(stack).putInt(tagName(stack), Mth.clamp(charge, 0, max));
    }

    public static void addCharge(ItemStack stack, int charge) {
        if (charge <= 0) return;
        int max = maxCharge(stack);
        if (max <= 0) return;
        setCharge(stack, Math.min(max, getCharge(stack) + charge));
    }

    public static boolean isFullyCharged(ItemStack stack) {
        int max = maxCharge(stack);
        return max > 0 && getCharge(stack) >= max;
    }

    public static boolean hasForcedPurpleFoil(ItemStack stack) {
        CompoundTag tag = LegacyItemData.get(stack);
        return isObsidianArmor(stack) && tag != null && tag.getBoolean(FORCED_PURPLE_FOIL_TAG);
    }

    public static void setForcedPurpleFoil(ItemStack stack, boolean value) {
        if (!isObsidianArmor(stack)) return;
        LegacyItemData.getOrCreate(stack).putBoolean(FORCED_PURPLE_FOIL_TAG, value);
    }

    private static String tagName(ItemStack stack) {
        return isHelmet(stack) ? HELMET_CHARGE_TAG : CHESTPLATE_CHARGE_TAG;
    }
}
