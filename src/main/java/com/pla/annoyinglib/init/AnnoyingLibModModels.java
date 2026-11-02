package com.pla.annoyinglib.init;

import com.pla.annoyinglib.client.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AnnoyingLibModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions registerlayerdefinitions) {

        registerlayerdefinitions.registerLayerDefinition(ModelRig.LAYER_LOCATION, ModelRig::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRig.SLIM_LAYER_LOCATION, ModelRig::createSlimBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRigVillager.LAYER_LOCATION, ModelRigVillager::createBodyLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRigArmor.INNER_LAYER_LOCATION, ModelRigArmor::createInnerLayer);
        registerlayerdefinitions.registerLayerDefinition(ModelRigArmor.OUTER_LAYER_LOCATION, ModelRigArmor::createOuterLayer);
    }
}
