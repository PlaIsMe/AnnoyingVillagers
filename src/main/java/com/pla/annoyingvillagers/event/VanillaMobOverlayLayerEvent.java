package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.client.layer.HumanoidMobVanillaLayer;
import com.pla.annoyingvillagers.client.layer.IllagerMobVanillaLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class VanillaMobOverlayLayerEvent {
    private VanillaMobOverlayLayerEvent() {
    }

    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        addHumanoidLayer(event.getRenderer(EntityType.ZOMBIE));
        addHumanoidLayer(event.getRenderer(EntityType.HUSK));
        addHumanoidLayer(event.getRenderer(EntityType.DROWNED));
        addHumanoidLayer(event.getRenderer(EntityType.ZOMBIE_VILLAGER));
        addHumanoidLayer(event.getRenderer(EntityType.SKELETON));
        addHumanoidLayer(event.getRenderer(EntityType.STRAY));
        addHumanoidLayer(event.getRenderer(EntityType.WITHER_SKELETON));
        addHumanoidLayer(event.getRenderer(EntityType.PIGLIN));
        addHumanoidLayer(event.getRenderer(EntityType.PIGLIN_BRUTE));
        addHumanoidLayer(event.getRenderer(EntityType.ZOMBIFIED_PIGLIN));
        addIllagerLayer(event.getRenderer(EntityType.PILLAGER));
        addIllagerLayer(event.getRenderer(EntityType.VINDICATOR));
        addIllagerLayer(event.getRenderer(EntityType.EVOKER));
        addIllagerLayer(event.getRenderer(EntityType.ILLUSIONER));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addHumanoidLayer(EntityRenderer<?, ?> renderer) {
        if (!(renderer instanceof LivingEntityRenderer livingRenderer) || !(livingRenderer.getModel() instanceof HumanoidModel)) return;
        livingRenderer.addLayer(new HumanoidMobVanillaLayer(livingRenderer));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addIllagerLayer(EntityRenderer<?, ?> renderer) {
        if (!(renderer instanceof LivingEntityRenderer livingRenderer) || !(livingRenderer.getModel() instanceof IllagerModel)) return;
        livingRenderer.addLayer(new IllagerMobVanillaLayer(livingRenderer));
    }
}
