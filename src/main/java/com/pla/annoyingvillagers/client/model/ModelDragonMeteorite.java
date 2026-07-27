/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-or-later AND Apache-2.0
 *
 * Upstream: Skyfall: Meteorites - Yoshi01111
 * Source: https://www.curseforge.com/minecraft/mc-mods/skyfall-meteorites
 *
 * This file contains code adapted from the upstream project.
 * Required upstream notices must be preserved (including Apache-2.0 NOTICE if provided).
 *
 * License texts:
 *   - third_party/licenses/GPL-3.0.md
 *   - third_party/licenses/Apache-2.0.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelDragonMeteorite<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modeldragonmeteorite"), "main");
    public final ModelPart bone;

    public ModelDragonMeteorite(ModelPart modelpart) {
        this.bone = modelpart.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int j, float f, float f1, float f2, float f3) {
        this.bone.render(poseStack, vertexConsumer, i, j, f, f1, f2, f3);
    }

    public void setupAnim(@NotNull T t0, float f, float f1, float f2, float f3, float f4) {
        this.bone.yRot = f2 / 20.0F;
    }
}
