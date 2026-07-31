package com.pla.annoyingvillagers.client.layer;

import com.pla.annoyingvillagers.client.model.ModelRig;
import net.minecraft.world.entity.Mob;

import com.pla.annoyingvillagers.client.model.ModelRigArmor;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;

public class RigArmorLayer<T extends Mob> extends HumanoidArmorLayer<T, ModelRig<T>, ModelRigArmor<T>> {

    public RigArmorLayer(RenderLayerParent<T, ModelRig<T>> renderer, ModelRigArmor<T> innerModel, ModelRigArmor<T> outerModel, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, modelManager);
    }

    @Override
    protected void setPartVisibility(ModelRigArmor<T> model, EquipmentSlot slot) {
        model.setVisibleForSlot(slot);
    }
}