package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.client.model.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
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
        registerlayerdefinitions.registerLayerDefinition(ModelAvWarden.LAYER_LOCATION, ModelAvWarden::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelAvGolem.LAYER_LOCATION, ModelAvGolem::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelAvGolemArmor.LAYER_LOCATION, ModelAvGolemArmor::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelGolemArm.LAYER_LOCATION, ModelGolemArm::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelHerobrineDragon.LAYER_LOCATION, ModelHerobrineDragon::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelDragonMeteorite.LAYER_LOCATION, ModelDragonMeteorite::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelFlyingShockwave.LAYER_LOCATION, ModelFlyingShockwave::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRig.LAYER_LOCATION, ModelRig::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRig.SLIM_LAYER_LOCATION, ModelRig::createSlimBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRigVillager.LAYER_LOCATION, ModelRigVillager::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRigArmor.INNER_LAYER_LOCATION, ModelRigArmor::createInnerLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRigArmor.OUTER_LAYER_LOCATION, ModelRigArmor::createOuterLayer);
    }
}
