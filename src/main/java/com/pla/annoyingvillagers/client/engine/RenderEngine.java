package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class RenderEngine {
    @SubscribeEvent
    public static void registerRenderer(PatchedRenderersEvent.RegisterItemRenderer add) {
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "legendary_sword"), RenderLegendarySword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "great_sword"), RenderGreatSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "obsidian_weapon"), RenderObsidianWeapon::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "shadow_obsidian_weapon"), RenderShadowObsidianWeapon::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "shadow_obsidian_pillar"), RenderShadowObsidianPillar::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "shadow_obsidian_sword"), RenderShadowObsidianSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "diamond_blaster_sword"), RenderDiamondBlasterSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "diamond_bolt"), RenderDiamondBolt::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "dnax_hooked_sword"), RenderDNAxHookSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(AnnoyingVillagers.MODID, "red_axe"), RenderRedAxe::new);
    }
}
