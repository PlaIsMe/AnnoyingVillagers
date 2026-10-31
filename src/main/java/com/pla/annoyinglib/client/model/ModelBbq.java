package com.pla.annoyinglib.client.model;

import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Chicken;

public class ModelBbq<T extends Chicken> extends ChickenModel<T> {
    private final ModelPart beak;

    public ModelBbq(ModelPart root) {
        super(root);
        this.beak = root.getChild("beak");
    }

    public ModelPart getBeak() {
        return this.beak;
    }
}