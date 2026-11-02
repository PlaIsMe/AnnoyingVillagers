package com.pla.annoyinglib.init;

import com.pla.annoyinglib.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingLibModEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(RegisterRenderers registerrenderers) {

        
        
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.BLOCK_PROJECTILE.get(), BlockProjectileRenderer::new);
        registerrenderers.registerBlockEntityRenderer(AnnoyingLibModBlockEntities.FRACTURE_BLOCK.get(), FractureBlockRenderer::new);

        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.SHOCKWAVE_BLOCK.get(), ShockWaveBlockRenderer::new);

        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.ITEM_PROJECTILE.get(), ItemProjectileRenderer::new);
        
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.RISING_WALL_BLOCK.get(), RisingWallBlockRenderer::new);
        registerrenderers.registerEntityRenderer(AnnoyingLibModEntities.FLOATING_LOOK_BLOCK.get(), FloatingLookBlockRenderer::new);
    }
}
