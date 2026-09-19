package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.util.LegacyItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public final class GlintColorHelper {
    public static final String TAG_COLOR_GLINT = "ColorGlint";

    public static final int NONE = 0;
    public static final int ORANGE = 1;
    public static final int CYAN = 2;
    public static final int BLUE = 3;
    public static final int GREEN = 4;
    public static final int LIGHT_BLUE = 5;
    public static final int LIME = 6;
    public static final int MAGENTA = 7;
    public static final int PINK = 8;
    public static final int PURPLE = 9;
    public static final int RED = 10;
    public static final int YELLOW = 11;

    private GlintColorHelper() {
    }

    public static int getRandomColor() {
        return switch (new Random().nextInt(11)) {
            case 0 -> ORANGE;
            case 1 -> CYAN;
            case 2 -> BLUE;
            case 3 -> GREEN;
            case 4 -> LIGHT_BLUE;
            case 5 -> LIME;
            case 6 -> MAGENTA;
            case 7 -> PINK;
            case 8 -> PURPLE;
            case 9 -> RED;
            default -> YELLOW;
        };
    }

    public static Vec3 getParticleColor(int mode) {
        return switch (mode) {
            case ORANGE -> new Vec3(1.0D, 0.55D, 0.10D);
            case CYAN -> new Vec3(0.20D, 0.90D, 1.00D);
            case BLUE -> new Vec3(0.25D, 0.45D, 1.00D);
            case GREEN -> new Vec3(0.20D, 0.90D, 0.20D);
            case LIGHT_BLUE -> new Vec3(0.45D, 0.80D, 1.00D);
            case LIME -> new Vec3(0.65D, 1.00D, 0.20D);
            case MAGENTA -> new Vec3(1.00D, 0.25D, 0.95D);
            case PINK -> new Vec3(1.00D, 0.55D, 0.75D);
            case PURPLE -> new Vec3(0.65D, 0.30D, 1.00D);
            case RED -> new Vec3(1.00D, 0.15D, 0.15D);
            case YELLOW -> new Vec3(1.00D, 0.95D, 0.20D);
            default -> new Vec3(0.50D, 0.50D, 0.50D);
        };
    }

    public static void setColor(ItemStack stack, int mode) {
        if (stack.isEmpty()) {
            return;
        }

        if (mode == NONE) {
            CompoundTag tag = LegacyItemData.get(stack);
            if (tag != null) {
                tag.remove(TAG_COLOR_GLINT);
            }
            return;
        }

        LegacyItemData.getOrCreate(stack).putString(TAG_COLOR_GLINT, toName(mode));
    }

    public static void clearColor(ItemStack stack) {
        if (!LegacyItemData.has(stack)) {
            return;
        }

        LegacyItemData.get(stack).remove(TAG_COLOR_GLINT);

        if (LegacyItemData.get(stack).isEmpty()) {
            LegacyItemData.set(stack, null);
        }
    }

    public static int getColor(ItemStack stack) {
        CompoundTag tag = LegacyItemData.get(stack);
        if (tag == null) {
            return NONE;
        }

        if (tag.contains(TAG_COLOR_GLINT, Tag.TAG_INT)) {
            return sanitize(tag.getInt(TAG_COLOR_GLINT));
        }

        if (tag.contains(TAG_COLOR_GLINT, Tag.TAG_STRING)) {
            return fromName(tag.getString(TAG_COLOR_GLINT));
        }

        return NONE;
    }

    public static boolean hasColor(ItemStack stack) {
        return getColor(stack) != NONE;
    }

    public static int sanitize(int mode) {
        return mode >= NONE && mode <= YELLOW ? mode : NONE;
    }

    public static int fromName(String name) {
        return switch (name) {
            case "orange" -> ORANGE;
            case "cyan" -> CYAN;
            case "blue" -> BLUE;
            case "green" -> GREEN;
            case "light_blue" -> LIGHT_BLUE;
            case "lime" -> LIME;
            case "magenta" -> MAGENTA;
            case "pink" -> PINK;
            case "purple" -> PURPLE;
            case "red" -> RED;
            case "yellow" -> YELLOW;
            default -> NONE;
        };
    }

    public static String toName(int mode) {
        return switch (mode) {
            case ORANGE -> "orange";
            case CYAN -> "cyan";
            case BLUE -> "blue";
            case GREEN -> "green";
            case LIGHT_BLUE -> "light_blue";
            case LIME -> "lime";
            case MAGENTA -> "magenta";
            case PINK -> "pink";
            case PURPLE -> "purple";
            case RED -> "red";
            case YELLOW -> "yellow";
            default -> "";
        };
    }
}