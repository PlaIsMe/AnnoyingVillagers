package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.shader.ImpactBlurShaderManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class EpicFightCameraFxClient {
    private static float fovModifier;
    private static int fovTicks;

    private EpicFightCameraFxClient() {
    }

    public static void zoomIn(float modifier, int ticks) {
        fovModifier = modifier;
        fovTicks = Math.max(1, ticks);
    }

    public static void resetZoomAndBlur(float blurStrength, int ticks) {
        resetZoom();
        triggerBlur(blurStrength, ticks);
    }

    public static void triggerBlur(float blurStrength, int ticks) {
        if (blurStrength <= 0.0F || ticks <= 0) {
            return;
        }

        ImpactBlurShaderManager.trigger(blurStrength, ticks);
    }

    public static void resetZoom() {
        fovModifier = 0.0F;
        fovTicks = 0;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (Minecraft.getInstance().isPaused()) {
            return;
        }

        if (fovTicks > 0 && --fovTicks <= 0) {
            resetZoom();
        }
    }

    @SubscribeEvent
    public static void onFovUpdate(ComputeFovModifierEvent event) {
        if (fovModifier != 0.0F) {
            event.setNewFovModifier(event.getNewFovModifier() + fovModifier);
        }
    }

}
