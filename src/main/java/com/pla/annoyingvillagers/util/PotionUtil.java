package com.pla.annoyingvillagers.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.ArrayList;
import java.util.List;

/** Item-component replacements for the removed 1.20 PotionUtils helpers. */
public final class PotionUtil {
    private PotionUtil() {
    }

    public static ItemStack setPotion(ItemStack stack, Holder<Potion> potion) {
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
        return stack;
    }

    public static ItemStack setCustomEffects(ItemStack stack, List<MobEffectInstance> effects) {
        PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        for (MobEffectInstance effect : effects) {
            contents = contents.withEffectAdded(effect);
        }
        stack.set(DataComponents.POTION_CONTENTS, contents);
        return stack;
    }

    public static List<MobEffectInstance> getMobEffects(ItemStack stack) {
        List<MobEffectInstance> effects = new ArrayList<>();
        stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getAllEffects().forEach(effects::add);
        return effects;
    }
}
