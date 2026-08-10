package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.util.GlintColorHelper;
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
        int mode = GlintColorHelper.getColor(stack);

        if (mode == NONE) {
            if (stack.is(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                mode = ORANGE;
            } else if (stack.is(AnnoyingVillagersModItems.RED_AXE.get()) || stack.is(AnnoyingVillagersModItems.GIANT_RED_AXE.get())) {
                mode = RED;
            } else if (
                    (stack.is(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()) && BlueDemonTridentItem.isFullyCharged(stack))
                            || (stack.is(AnnoyingVillagersModItems.BLUE_DEMON_CHESTPLATE.get())
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