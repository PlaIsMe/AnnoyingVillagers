package com.pla.annoyinglib.client.renderer;
import net.minecraft.world.item.Items;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
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

                if (stack.is((ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.getItems_RED_AXE() : Items.AIR)) || stack.is((ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.getItems_GIANT_RED_AXE() : Items.AIR))) {
                mode = RED;

            } else if (
                    (stack.is((ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.getItems_BLUE_DEMON_TRIDENT() : Items.AIR)) && (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonTridentItem_isFullyCharged(stack) : false))
                            || (stack.is((ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.getItems_BLUE_DEMON_CHESTPLATE() : Items.AIR))
                            && ((ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonChestplateItem_isFullyCharged(stack) : false)
                            || (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonChestplateItem_isBuffActive(stack) : false)
                            || (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonChestplateItem_hasBlueDemonHealingFoil(stack) : false)))
            ) {
                mode = CYAN;
            }
        }

        if (mode == NONE && ModList.get().isLoaded("herobrine_vessels")) mode = HerobrineVessels.Client.herobrineGlint(stack, wearer);

        MODE.set(mode);
    }

    public static int getMode() {
        return MODE.get();
    }

    public static void clear() {
        MODE.remove();
    }
}
