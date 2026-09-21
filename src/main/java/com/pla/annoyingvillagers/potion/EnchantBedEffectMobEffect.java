package com.pla.annoyingvillagers.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EnchantBedEffectMobEffect extends MobEffect {

    public EnchantBedEffectMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -39322);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.enchant_bed_effect";
    }

    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }

}
