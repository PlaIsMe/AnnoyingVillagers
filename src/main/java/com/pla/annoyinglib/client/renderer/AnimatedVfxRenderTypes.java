package com.pla.annoyinglib.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.pla.annoyinglib.AnnoyingLib;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/** Native, full-bright VFX using the existing texture assets, without a Photon/AAA runtime dependency. */
@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AnimatedVfxRenderTypes extends RenderType {
    private static ShaderInstance blackFire;
    private static ShaderInstance blackFireGlow;
    private static ShaderInstance aura;
    private static final Map<ResourceLocation, RenderType> FIRE = new HashMap<>();
    private static final Map<ResourceLocation, RenderType> GLOW = new HashMap<>();
    private static final Map<ResourceLocation, RenderType> AURA = new HashMap<>();

    private AnimatedVfxRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int size,
                                  boolean crumbling, boolean sorted, Runnable setup, Runnable clear) {
        super(name, format, mode, size, crumbling, sorted, setup, clear);
    }

    @SubscribeEvent public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "black_fire"), DefaultVertexFormat.POSITION_COLOR_TEX),
                shader -> blackFire = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "black_fire_glow"), DefaultVertexFormat.POSITION_COLOR_TEX),
                shader -> blackFireGlow = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(),
                ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "golden_aura"), DefaultVertexFormat.POSITION_COLOR_TEX),
                shader -> aura = shader);
    }

    public static RenderType fire(ResourceLocation texture, boolean glow) {
        return (glow ? GLOW : FIRE).computeIfAbsent(texture, tex -> create("native_black_fire" + (glow ? "_glow" : ""),
                DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 4096, false, true,
                CompositeState.builder().setShaderState(new ShaderStateShard(() -> glow ? blackFireGlow : blackFire))
                        .setTextureState(new TextureStateShard(tex, true, false)).setCullState(NO_CULL)
                        .setTransparencyState(glow ? LIGHTNING_TRANSPARENCY : TRANSLUCENT_TRANSPARENCY)
                        .setWriteMaskState(COLOR_WRITE).setDepthTestState(LEQUAL_DEPTH_TEST)
                        .setOutputState(PARTICLES_TARGET).createCompositeState(false)));
    }

    public static RenderType aura(ResourceLocation texture) {
        return AURA.computeIfAbsent(texture, tex -> create("native_golden_aura", DefaultVertexFormat.POSITION_COLOR_TEX,
                VertexFormat.Mode.QUADS, 4096, false, true,
                CompositeState.builder().setShaderState(new ShaderStateShard(() -> aura))
                        .setTextureState(new TextureStateShard(tex, true, false)).setCullState(NO_CULL)
                        .setTransparencyState(LIGHTNING_TRANSPARENCY).setWriteMaskState(COLOR_WRITE)
                        .setDepthTestState(LEQUAL_DEPTH_TEST).setOutputState(PARTICLES_TARGET).createCompositeState(false)));
    }
}
