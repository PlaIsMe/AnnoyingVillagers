package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.chicken.ChickenModel;
import net.minecraft.client.model.geom.ModelPart;

public class ModelBbq extends ChickenModel {
    private final ModelPart head;
    private final ModelPart beak;

    public ModelBbq(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.beak = this.head.getChild("beak");
    }

    public void translateToBeak(PoseStack poseStack) {
        this.head.translateAndRotate(poseStack);
        this.beak.translateAndRotate(poseStack);
    }
}
