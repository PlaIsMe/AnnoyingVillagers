package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.item.BlueDemonChestplateItem;
import com.pla.annoyinglib.item.BlueDemonTridentItem;
import com.pla.annoyinglib.util.GlintColorHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public final class ColoredGlintState {
    public static final int NONE = GlintColorHelper.NONE;
    public static final int ORANGE = GlintColorHelper.ORANGE;
    public static final int CYAN = GlintColorHelper.CYAN;
    public static final int BLUE = GlintColorHelper.BLUE;
    public static final int GREEN = GlintColorHelper.GREEN;
    public static final int LIGHT_BLUE = GlintColorHelper.LIGHT_BLUE;
    public static final int LIME = GlintColorHelper.LIME;
    public static final int MAGENTA = GlintColorHelper.MAGENTA;
    public static final int PINK = GlintColorHelper.PINK;
    public static final int PURPLE = GlintColorHelper.PURPLE;
    public static final int RED = GlintColorHelper.RED;
    public static final int YELLOW = GlintColorHelper.YELLOW;

    private static final ThreadLocal<Integer> MODE = ThreadLocal.withInitial(() -> NONE);

    private ColoredGlintState() {
    }

    public static void setTargetStack(ItemStack stack) {
        setTargetStack(stack, null);
    }

    public static void setTargetStack(ItemStack stack, LivingEntity wearer) {
        int mode = GlintColorHelper.getColor(stack);

        if (mode == NONE) {
//        add this in herobrine_vessels
//            if (stack.is(AnnoyingLibModItems.HEROBRINE_ENDER_EYE.get())) {
//                mode = ORANGE;
//            } else
                if (stack.is(AnnoyingLibModItems.RED_AXE.get()) || stack.is(AnnoyingLibModItems.GIANT_RED_AXE.get())) {
                mode = RED;
//            } else if (HerobrineObsidianArmorCharge.isObsidianArmor(stack) && (HerobrineObsidianArmorCharge.isFullyCharged(stack) || HerobrineObsidianArmorCharge.hasForcedPurpleFoil(stack) || wearer instanceof ArmoredHerobrineEntity)) {
//                mode = PURPLE;
            } else if (
                    (stack.is(AnnoyingLibModItems.BLUE_DEMON_TRIDENT.get()) && BlueDemonTridentItem.isFullyCharged(stack))
                            || (stack.is(AnnoyingLibModItems.BLUE_DEMON_CHESTPLATE.get())
                            && (BlueDemonChestplateItem.isFullyCharged(stack)
                            || BlueDemonChestplateItem.isBuffActive(stack)
                            || BlueDemonChestplateItem.hasBlueDemonHealingFoil(stack)))
            ) {
                mode = CYAN;
            }
        }

        MODE.set(mode);
    }

    public static int getMode() {
        return MODE.get();
    }

    public static void clear() {
        MODE.remove();
    }
}
