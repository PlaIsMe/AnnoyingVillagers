package com.pla.annoyingvillagers.client.engine;

import com.mojang.blaze3d.systems.RenderSystem;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class ElectrifyScreenEffect {
    private static final int SPARK_FRAME_COUNT = 27;
    private static final ResourceLocation[] ELECTRIC_SPARK_TEXTURES = createSparkTextures();

    private ElectrifyScreenEffect() {}

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        MobEffectInstance effect = getElectrifyEffect(player);
        if (effect == null || minecraft.isPaused() || !useVanillaPlayerShockFx()) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();
        float time = player.tickCount + event.getPartialTick();
        float pulse = 0.85F + 0.15F * Math.abs((float)Math.sin(time * 2.6F));
        boolean strongShock = effect.getAmplifier() > 1;
        long flashTick = player.tickCount / 2L;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        for (int corner = 0; corner < 4; corner++) renderCornerSpark(guiGraphics, player, width, height, corner, flashTick, pulse, strongShock);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }

    private static void renderCornerSpark(GuiGraphics guiGraphics, Player player, int width, int height, int corner, long flashTick, float pulse, boolean strongShock) {
        Random random = new Random(flashTick * 341873128712L + corner * 132897987541L + player.getId() * 31L);
        int frame = random.nextInt(SPARK_FRAME_COUNT);
        int size = (strongShock ? 72 : 56) + random.nextInt(strongShock ? 41 : 33);
        int cornerWidth = Math.max(size, (int)(width * 0.45F));
        int cornerHeight = Math.max(size, (int)(height * 0.45F));
        int offsetX = random.nextInt(Math.max(1, cornerWidth - size + 1));
        int offsetY = random.nextInt(Math.max(1, cornerHeight - size + 1));
        int x = (corner & 1) == 0 ? offsetX : width - size - offsetX;
        int y = corner < 2 ? offsetY : height - size - offsetY;
        float alpha = (strongShock ? 0.90F : 0.70F) * (0.85F + random.nextFloat() * 0.15F) * pulse;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        guiGraphics.blit(ELECTRIC_SPARK_TEXTURES[frame], x, y, 0, 0, size, size, 256, 256);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        MobEffectInstance effect = getElectrifyEffect(player);
        if (effect == null || minecraft.isPaused() || !useVanillaPlayerShockFx()) return;

        double time = player.tickCount + event.getPartialTick();
        float strength = effect.getAmplifier() > 1 ? 2.0F : 0.85F;
        event.setPitch(event.getPitch() + (float)Math.sin(time * 3.8D) * strength);
        event.setYaw(event.getYaw() + (float)Math.cos(time * 4.6D + 0.7D) * strength);
        event.setRoll(event.getRoll() + (float)Math.sin(time * 5.4D + 1.2D) * strength * 0.8F);
    }

    private static MobEffectInstance getElectrifyEffect(Player player) {
        return player == null ? null : player.getEffect(AnnoyingVillagersModMobEffects.ELECTRIFY.get());
    }

    private static boolean useVanillaPlayerShockFx() {
        return !ModList.get().isLoaded("epicfight_annoyingvillagers");
    }

    private static ResourceLocation[] createSparkTextures() {
        ResourceLocation[] textures = new ResourceLocation[SPARK_FRAME_COUNT];
        for (int i = 0; i < SPARK_FRAME_COUNT; i++) textures[i] = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/particle/electric_spark_" + (i + 1) + ".png");
        return textures;
    }
}
