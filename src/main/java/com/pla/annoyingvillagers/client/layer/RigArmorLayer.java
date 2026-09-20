package com.pla.annoyingvillagers.client.layer;

import com.pla.annoyingvillagers.client.model.ModelRig;
import net.minecraft.world.entity.Mob;

import com.pla.annoyingvillagers.client.model.ModelRigArmor;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;

public class RigArmorLayer<T extends Mob> extends HumanoidArmorLayer<LegacyEntityRenderState<T>, ModelRig<T>, ModelRigArmor<T>> {

    public RigArmorLayer(RenderLayerParent<LegacyEntityRenderState<T>, ModelRig<T>> renderer,
                         EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, ModelRigArmor.createArmorSet(), equipmentRenderer);
    }
}
