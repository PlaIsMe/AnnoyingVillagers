package com.pla.annoyingvillagers.potion;

import java.util.function.Consumer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

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

    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance instance) {
                return false;
            }

            @Override
            public boolean isVisibleInGui(MobEffectInstance instance) {
                return false;
            }
        });
    }
}
