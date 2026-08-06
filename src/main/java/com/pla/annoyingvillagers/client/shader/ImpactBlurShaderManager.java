package com.pla.annoyingvillagers.client.shader;

import com.mojang.blaze3d.platform.Window;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public final class ImpactBlurShaderManager {
    private static PostChain chain;
    private static boolean active;
    private static float progress;
    private static float strength;
    private static int startTime;
    private static int totalTime;
    private static float duration;
    private static int width;
    private static int height;

    private ImpactBlurShaderManager() {
    }

    public static void init() {
        try {
            chain = new PostChain(
                    Minecraft.getInstance().getTextureManager(),
                    Minecraft.getInstance().getResourceManager(),
                    Minecraft.getInstance().getMainRenderTarget(),
                    ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shaders/post/impact_blur.json")
            );
            resize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resize() {
        if (chain != null) {
            Window window = Minecraft.getInstance().getWindow();
            int currentWidth = window.getWidth();
            int currentHeight = window.getHeight();

            if (width != currentWidth || height != currentHeight) {
                height = currentHeight;
                width = currentWidth;
                chain.resize(width, height);
            }
        }
    }

    public static void trigger(float strengthMultiplier, int ticks) {
        active = true;
        startTime = -1;
        totalTime = ticks;
        duration = 0.0F;
        progress = 0.0F;
        strength = 0.1F * strengthMultiplier;
        resize();
    }

    public static void render(int ticks, float partialTick) {
        if (chain == null) {
            return;
        }

        if (active) {
            if (startTime == -1) {
                startTime = ticks;
            }

            if (progress >= 1.0F) {
                active = false;
            }

            if (!Minecraft.getInstance().isPaused()) {
                duration = ticks + partialTick - startTime;
                progress = duration / totalTime;
            }

            EffectInstance effect = ((PostPass) chain.passes.get(0)).getEffect();
            effect.safeGetUniform("center").set(0.5F, 0.5F);
            effect.safeGetUniform("strength").set(strength);
            effect.safeGetUniform("intensity").set(progress);
            effect.safeGetUniform("samples").set(10);
            chain.process(partialTick);
        }

        resize();
    }

    public static boolean isInitialized() {
        return chain != null;
    }
}
