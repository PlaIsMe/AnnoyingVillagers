package com.pla.annoyinglib.potion;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

public class EnchantBedEffectMobEffect extends MobEffect {

    public EnchantBedEffectMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -39322);
    }

    public String getDescriptionId() {
        return "effect.annoyinglib.enchant_bed_effect";
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }

    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            public boolean shouldRender(MobEffectInstance mobeffectinstance) {
                return false;
            }

            public boolean shouldRenderInvText(MobEffectInstance mobeffectinstance) {
                return false;
            }

            public boolean shouldRenderHUD(MobEffectInstance mobeffectinstance) {
                return false;
            }

            public void renderInventoryEffect(MobEffectInstance mobeffectinstance, EffectRenderingInventoryScreen<?> effectrenderinginventoryscreen, PoseStack posestack, int i, int j, float f) {}

            public void renderHUDEffect(MobEffectInstance mobeffectinstance, RenderGuiOverlayEvent renderGuiOverlayEvent, PoseStack posestack, int i, int j, float f, float f1) {}
        });
    }
}
