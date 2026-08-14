package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingVillagersModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions registerlayerdefinitions) {
        registerlayerdefinitions.registerLayerDefinition(ModelVillagerScoutHelmet.LAYER_LOCATION, ModelVillagerScoutHelmet::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelBlueDemonTrident.LAYER_LOCATION, ModelBlueDemonTrident::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelGreenVillagerKnightArmor.LAYER_LOCATION, ModelGreenVillagerKnightArmor::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelVillagerKnightArmor.LAYER_LOCATION, ModelVillagerKnightArmor::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelHerobrineObsidianDiamondHelmet.LAYER_LOCATION, ModelHerobrineObsidianDiamondHelmet::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION, ModelHerobrineObsidianDiamondChestplate::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelSnakeBladeFragment.LAYER_LOCATION, ModelSnakeBladeFragment::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelSnakeBlade.LAYER_LOCATION, ModelSnakeBlade::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelHerobrineWarden.LAYER_LOCATION, ModelHerobrineWarden::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelHerobrineDragon.LAYER_LOCATION, ModelHerobrineDragon::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelDragonMeteorite.LAYER_LOCATION, ModelDragonMeteorite::createBodyLayer);
    }
}
