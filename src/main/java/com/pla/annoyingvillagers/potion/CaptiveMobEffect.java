package com.pla.annoyingvillagers.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CaptiveMobEffect extends MobEffect {

    public CaptiveMobEffect() {
        super(MobEffectCategory.NEUTRAL, -1);
    }

    public String getDescriptionId() {
        return "effect.annoyingvillagers.captive";
    }

    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }

}
