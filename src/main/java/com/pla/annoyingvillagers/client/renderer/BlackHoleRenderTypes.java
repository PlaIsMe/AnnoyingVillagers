package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;

/** Alpha-masked darkness: source RGB removes destination light instead of emitting white. */
public final class BlackHoleRenderTypes {
    private static final RenderPipeline DARKNESS_PIPELINE = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "pipeline/black_hole_darkness"))
            .withCull(false)
            .withColorTargetState(new ColorTargetState(new BlendFunction(
                    SourceFactor.ZERO, DestFactor.ONE_MINUS_SRC_ALPHA,
                    SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA)))
            .build();

    private BlackHoleRenderTypes() {}

    public static RenderType darkness(Identifier texture) {
        return RenderType.create("annoyingvillagers_black_hole", RenderSetup.builder(DARKNESS_PIPELINE)
                .withTexture("Sampler0", texture).useLightmap().useOverlay().sortOnUpload().createRenderSetup());
    }

    public static void registerPipelines(RegisterRenderPipelinesEvent event) {
        event.registerPipeline(DARKNESS_PIPELINE);
    }
}
