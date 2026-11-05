package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPoseClip;
import com.pla.annoyingvillagers.client.compat.LegacyHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ModelHerobrineObsidianDiamondHelmet<T extends Entity> extends LegacyHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelherobrineobsidiandiamondhelmet"), "main");
    private final ModelPart modelRoot;
    public final ModelPart Head;
    private final ModelPart obsidian_0_bone;
    private final ModelPart obsidian_0_tile_01;
    private final ModelPart obsidian_0_tile_02;
    private final ModelPart obsidian_0_tile_03;
    private final ModelPart obsidian_0_tile_04;
    private final ModelPart obsidian_0_tile_05;
    private final ModelPart obsidian_0_tile_06;
    private final ModelPart obsidian_0_tile_07;
    private final ModelPart obsidian_0_tile_08;
    private final ModelPart obsidian_0_tile_09;
    private final ModelPart obsidian_0_tile_10;
    private final ModelPart obsidian_0_tile_11;
    private final ModelPart obsidian_0_tile_12;
    private final ModelPart obsidian_0_tile_13;
    private final ModelPart obsidian_0_tile_14;
    private final ModelPart obsidian_0_tile_15;
    private final ModelPart obsidian_0_tile_16;
    private final ModelPart obsidian_0_tile_17;
    private final ModelPart obsidian_0_tile_18;
    private final ModelPart obsidian_0_tile_19;
    private final ModelPart obsidian_0_tile_20;
    private final ModelPart obsidian_0_tile_21;
    private final ModelPart obsidian_0_tile_22;
    private final ModelPart obsidian_0_tile_23;
    private final ModelPart obsidian_0_tile_24;
    private final ModelPart obsidian_0_tile_25;
    private final ModelPart obsidian_0_tile_26;
    private final ModelPart obsidian_0_tile_27;
    private final ModelPart obsidian_0_tile_28;
    private final ModelPart obsidian_0_tile_29;
    private final ModelPart obsidian_0_tile_30;
    private final ModelPart obsidian_0_tile_31;
    private final ModelPart obsidian_0_tile_32;
    private final ModelPart obsidian_0_tile_33;
    private final ModelPart obsidian_0_tile_34;
    private final ModelPart obsidian_0_tile_35;
    private final ModelPart obsidian_0_tile_36;
    private final ModelPart obsidian_0_tile_37;
    private final ModelPart obsidian_0_tile_38;
    private final ModelPart obsidian_0_tile_39;
    private final ModelPart obsidian_0_tile_40;
    private final ModelPart cube_r1;
    private final ModelPart obsidian_1_bone;
    private final ModelPart obsidian_1_tile_01;
    private final ModelPart obsidian_1_tile_02;
    private final ModelPart obsidian_1_tile_03;
    private final ModelPart obsidian_1_tile_04;
    private final ModelPart obsidian_1_tile_05;
    private final ModelPart obsidian_1_tile_06;
    private final ModelPart obsidian_1_tile_07;
    private final ModelPart obsidian_1_tile_08;
    private final ModelPart obsidian_1_tile_09;
    private final ModelPart obsidian_1_tile_10;
    private final ModelPart obsidian_1_tile_11;
    private final ModelPart obsidian_1_tile_12;
    private final ModelPart obsidian_1_tile_13;
    private final ModelPart obsidian_1_tile_14;
    private final ModelPart obsidian_1_tile_15;
    private final ModelPart obsidian_1_tile_16;
    private final ModelPart obsidian_1_tile_17;
    private final ModelPart obsidian_1_tile_18;
    private final ModelPart obsidian_1_tile_19;
    private final ModelPart obsidian_1_tile_20;
    private final ModelPart cube_r2;
    private final ModelPart obsidian_2_bone;
    private final ModelPart obsidian_2_tile_01;
    private final ModelPart obsidian_2_tile_02;
    private final ModelPart obsidian_2_tile_03;
    private final ModelPart obsidian_2_tile_04;
    private final ModelPart obsidian_2_tile_05;
    private final ModelPart obsidian_2_tile_06;
    private final ModelPart obsidian_2_tile_07;
    private final ModelPart obsidian_2_tile_08;
    private final ModelPart obsidian_2_tile_09;
    private final ModelPart obsidian_2_tile_10;
    private final ModelPart obsidian_2_tile_11;
    private final ModelPart obsidian_2_tile_12;
    private final ModelPart obsidian_2_tile_13;
    private final ModelPart obsidian_2_tile_14;
    private final ModelPart obsidian_2_tile_15;
    private final ModelPart obsidian_2_tile_16;
    private final ModelPart obsidian_2_tile_17;
    private final ModelPart obsidian_2_tile_18;
    private final ModelPart obsidian_2_tile_19;
    private final ModelPart obsidian_2_tile_20;
    private final ModelPart obsidian_2_tile_21;
    private final ModelPart obsidian_2_tile_22;
    private final ModelPart obsidian_2_tile_23;
    private final ModelPart obsidian_2_tile_24;

    public ModelHerobrineObsidianDiamondHelmet(ModelPart root) {
        super(root);
        this.modelRoot = root;
        this.Head = root.getChild("Head");
        this.obsidian_0_bone = this.Head.getChild("obsidian_0_bone");
        this.obsidian_0_tile_01 = this.obsidian_0_bone.getChild("obsidian_0_tile_01");
        this.obsidian_0_tile_02 = this.obsidian_0_bone.getChild("obsidian_0_tile_02");
        this.obsidian_0_tile_03 = this.obsidian_0_bone.getChild("obsidian_0_tile_03");
        this.obsidian_0_tile_04 = this.obsidian_0_bone.getChild("obsidian_0_tile_04");
        this.obsidian_0_tile_05 = this.obsidian_0_bone.getChild("obsidian_0_tile_05");
        this.obsidian_0_tile_06 = this.obsidian_0_bone.getChild("obsidian_0_tile_06");
        this.obsidian_0_tile_07 = this.obsidian_0_bone.getChild("obsidian_0_tile_07");
        this.obsidian_0_tile_08 = this.obsidian_0_bone.getChild("obsidian_0_tile_08");
        this.obsidian_0_tile_09 = this.obsidian_0_bone.getChild("obsidian_0_tile_09");
        this.obsidian_0_tile_10 = this.obsidian_0_bone.getChild("obsidian_0_tile_10");
        this.obsidian_0_tile_11 = this.obsidian_0_bone.getChild("obsidian_0_tile_11");
        this.obsidian_0_tile_12 = this.obsidian_0_bone.getChild("obsidian_0_tile_12");
        this.obsidian_0_tile_13 = this.obsidian_0_bone.getChild("obsidian_0_tile_13");
        this.obsidian_0_tile_14 = this.obsidian_0_bone.getChild("obsidian_0_tile_14");
        this.obsidian_0_tile_15 = this.obsidian_0_bone.getChild("obsidian_0_tile_15");
        this.obsidian_0_tile_16 = this.obsidian_0_bone.getChild("obsidian_0_tile_16");
        this.obsidian_0_tile_17 = this.obsidian_0_bone.getChild("obsidian_0_tile_17");
        this.obsidian_0_tile_18 = this.obsidian_0_bone.getChild("obsidian_0_tile_18");
        this.obsidian_0_tile_19 = this.obsidian_0_bone.getChild("obsidian_0_tile_19");
        this.obsidian_0_tile_20 = this.obsidian_0_bone.getChild("obsidian_0_tile_20");
        this.obsidian_0_tile_21 = this.obsidian_0_bone.getChild("obsidian_0_tile_21");
        this.obsidian_0_tile_22 = this.obsidian_0_bone.getChild("obsidian_0_tile_22");
        this.obsidian_0_tile_23 = this.obsidian_0_bone.getChild("obsidian_0_tile_23");
        this.obsidian_0_tile_24 = this.obsidian_0_bone.getChild("obsidian_0_tile_24");
        this.obsidian_0_tile_25 = this.obsidian_0_bone.getChild("obsidian_0_tile_25");
        this.obsidian_0_tile_26 = this.obsidian_0_bone.getChild("obsidian_0_tile_26");
        this.obsidian_0_tile_27 = this.obsidian_0_bone.getChild("obsidian_0_tile_27");
        this.obsidian_0_tile_28 = this.obsidian_0_bone.getChild("obsidian_0_tile_28");
        this.obsidian_0_tile_29 = this.obsidian_0_bone.getChild("obsidian_0_tile_29");
        this.obsidian_0_tile_30 = this.obsidian_0_bone.getChild("obsidian_0_tile_30");
        this.obsidian_0_tile_31 = this.obsidian_0_bone.getChild("obsidian_0_tile_31");
        this.obsidian_0_tile_32 = this.obsidian_0_bone.getChild("obsidian_0_tile_32");
        this.obsidian_0_tile_33 = this.obsidian_0_bone.getChild("obsidian_0_tile_33");
        this.obsidian_0_tile_34 = this.obsidian_0_bone.getChild("obsidian_0_tile_34");
        this.obsidian_0_tile_35 = this.obsidian_0_bone.getChild("obsidian_0_tile_35");
        this.obsidian_0_tile_36 = this.obsidian_0_bone.getChild("obsidian_0_tile_36");
        this.obsidian_0_tile_37 = this.obsidian_0_bone.getChild("obsidian_0_tile_37");
        this.obsidian_0_tile_38 = this.obsidian_0_bone.getChild("obsidian_0_tile_38");
        this.obsidian_0_tile_39 = this.obsidian_0_bone.getChild("obsidian_0_tile_39");
        this.obsidian_0_tile_40 = this.obsidian_0_bone.getChild("obsidian_0_tile_40");
        this.cube_r1 = this.Head.getChild("cube_r1");
        this.obsidian_1_bone = this.cube_r1.getChild("obsidian_1_bone");
        this.obsidian_1_tile_01 = this.obsidian_1_bone.getChild("obsidian_1_tile_01");
        this.obsidian_1_tile_02 = this.obsidian_1_bone.getChild("obsidian_1_tile_02");
        this.obsidian_1_tile_03 = this.obsidian_1_bone.getChild("obsidian_1_tile_03");
        this.obsidian_1_tile_04 = this.obsidian_1_bone.getChild("obsidian_1_tile_04");
        this.obsidian_1_tile_05 = this.obsidian_1_bone.getChild("obsidian_1_tile_05");
        this.obsidian_1_tile_06 = this.obsidian_1_bone.getChild("obsidian_1_tile_06");
        this.obsidian_1_tile_07 = this.obsidian_1_bone.getChild("obsidian_1_tile_07");
        this.obsidian_1_tile_08 = this.obsidian_1_bone.getChild("obsidian_1_tile_08");
        this.obsidian_1_tile_09 = this.obsidian_1_bone.getChild("obsidian_1_tile_09");
        this.obsidian_1_tile_10 = this.obsidian_1_bone.getChild("obsidian_1_tile_10");
        this.obsidian_1_tile_11 = this.obsidian_1_bone.getChild("obsidian_1_tile_11");
        this.obsidian_1_tile_12 = this.obsidian_1_bone.getChild("obsidian_1_tile_12");
        this.obsidian_1_tile_13 = this.obsidian_1_bone.getChild("obsidian_1_tile_13");
        this.obsidian_1_tile_14 = this.obsidian_1_bone.getChild("obsidian_1_tile_14");
        this.obsidian_1_tile_15 = this.obsidian_1_bone.getChild("obsidian_1_tile_15");
        this.obsidian_1_tile_16 = this.obsidian_1_bone.getChild("obsidian_1_tile_16");
        this.obsidian_1_tile_17 = this.obsidian_1_bone.getChild("obsidian_1_tile_17");
        this.obsidian_1_tile_18 = this.obsidian_1_bone.getChild("obsidian_1_tile_18");
        this.obsidian_1_tile_19 = this.obsidian_1_bone.getChild("obsidian_1_tile_19");
        this.obsidian_1_tile_20 = this.obsidian_1_bone.getChild("obsidian_1_tile_20");
        this.cube_r2 = this.Head.getChild("cube_r2");
        this.obsidian_2_bone = this.cube_r2.getChild("obsidian_2_bone");
        this.obsidian_2_tile_01 = this.obsidian_2_bone.getChild("obsidian_2_tile_01");
        this.obsidian_2_tile_02 = this.obsidian_2_bone.getChild("obsidian_2_tile_02");
        this.obsidian_2_tile_03 = this.obsidian_2_bone.getChild("obsidian_2_tile_03");
        this.obsidian_2_tile_04 = this.obsidian_2_bone.getChild("obsidian_2_tile_04");
        this.obsidian_2_tile_05 = this.obsidian_2_bone.getChild("obsidian_2_tile_05");
        this.obsidian_2_tile_06 = this.obsidian_2_bone.getChild("obsidian_2_tile_06");
        this.obsidian_2_tile_07 = this.obsidian_2_bone.getChild("obsidian_2_tile_07");
        this.obsidian_2_tile_08 = this.obsidian_2_bone.getChild("obsidian_2_tile_08");
        this.obsidian_2_tile_09 = this.obsidian_2_bone.getChild("obsidian_2_tile_09");
        this.obsidian_2_tile_10 = this.obsidian_2_bone.getChild("obsidian_2_tile_10");
        this.obsidian_2_tile_11 = this.obsidian_2_bone.getChild("obsidian_2_tile_11");
        this.obsidian_2_tile_12 = this.obsidian_2_bone.getChild("obsidian_2_tile_12");
        this.obsidian_2_tile_13 = this.obsidian_2_bone.getChild("obsidian_2_tile_13");
        this.obsidian_2_tile_14 = this.obsidian_2_bone.getChild("obsidian_2_tile_14");
        this.obsidian_2_tile_15 = this.obsidian_2_bone.getChild("obsidian_2_tile_15");
        this.obsidian_2_tile_16 = this.obsidian_2_bone.getChild("obsidian_2_tile_16");
        this.obsidian_2_tile_17 = this.obsidian_2_bone.getChild("obsidian_2_tile_17");
        this.obsidian_2_tile_18 = this.obsidian_2_bone.getChild("obsidian_2_tile_18");
        this.obsidian_2_tile_19 = this.obsidian_2_bone.getChild("obsidian_2_tile_19");
        this.obsidian_2_tile_20 = this.obsidian_2_bone.getChild("obsidian_2_tile_20");
        this.obsidian_2_tile_21 = this.obsidian_2_bone.getChild("obsidian_2_tile_21");
        this.obsidian_2_tile_22 = this.obsidian_2_bone.getChild("obsidian_2_tile_22");
        this.obsidian_2_tile_23 = this.obsidian_2_bone.getChild("obsidian_2_tile_23");
        this.obsidian_2_tile_24 = this.obsidian_2_bone.getChild("obsidian_2_tile_24");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_bone = Head.addOrReplaceChild("obsidian_0_bone", CubeListBuilder.create().texOffs(51, 4).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.2385F, -2.391F, 3.8F));
        PartDefinition obsidian_0_tile_01 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_02 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_03 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_04 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_05 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_06 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_07 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_08 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_09 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_10 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_11 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_12 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_13 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_14 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_15 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_16 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_17 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_18 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_19 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_20 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_21 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_22 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_22", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_23 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_23", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_24 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_24", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_25 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_25", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_26 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_26", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_27 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_27", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_28 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_28", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_29 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_29", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_30 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_30", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_31 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_31", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_32 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_32", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_33 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_33", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_34 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_34", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_35 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_35", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_36 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_36", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_37 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_37", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_38 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_38", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_39 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_39", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_tile_40 = obsidian_0_bone.addOrReplaceChild("obsidian_0_tile_40", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r1 = Head.addOrReplaceChild("cube_r1", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 0.0F, -0.6545F, 0.3927F));
        PartDefinition obsidian_1_bone = cube_r1.addOrReplaceChild("obsidian_1_bone", CubeListBuilder.create().texOffs(51, 4).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.091F, 1.7385F, 1.3F));
        PartDefinition obsidian_1_tile_01 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_02 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_03 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_04 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_05 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_06 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_07 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_08 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_09 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_10 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_11 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_12 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_13 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_14 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_15 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_16 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_17 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_18 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_19 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_1_tile_20 = obsidian_1_bone.addOrReplaceChild("obsidian_1_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r2 = Head.addOrReplaceChild("cube_r2", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0F, -15.0F, -10.0F, 1.0472F, -0.6545F, 0.7854F));
        PartDefinition obsidian_2_bone = cube_r2.addOrReplaceChild("obsidian_2_bone", CubeListBuilder.create().texOffs(51, 4).addBox(-5.0F, -1.05F, -0.95F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.1F)), PartPose.offset(1.4F, 14.25F, -8.95F));
        PartDefinition obsidian_2_tile_01 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_02 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_03 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_04 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_05 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_06 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_07 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_08 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_09 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_10 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_11 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_12 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_13 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_14 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_15 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_16 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_17 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_18 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_19 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_20 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_21 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_22 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_22", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_23 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_23", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_2_tile_24 = obsidian_2_bone.addOrReplaceChild("obsidian_2_tile_24", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.yRot = netHeadYaw / 57.295776F;
        this.Head.xRot = headPitch / 57.295776F;
    }
    public void renderLegacy(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.modelRoot.render(poseStack, buffer, packedLight, packedOverlay, color);
        renderRandomizedUvTiles(poseStack, buffer, packedLight, packedOverlay, color);
    }

    /** Draw only the animated tiles, without putting the helmet shell around the camera. */
    public void renderExtensionTiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay) {
        renderRandomizedUvTiles(poseStack, buffer, packedLight, packedOverlay, -1);
    }

    private void renderRandomizedUvTiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        renderObsidian0Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian1Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian2Tiles(poseStack, buffer, packedLight, packedOverlay, color);
    }

    private void renderObsidian0Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_0_tile_01.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 180, 58.0F, 4.0F, 56.0F, 7.0F, 90, 54.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 180, 56.0F, 7.0F, 54.0F, 4.0F, 0, 54.0F, 9.0F, 51.0F, 7.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_02.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 180, 59.0F, 7.0F, 56.0F, 9.0F, 0, 54.0F, 7.0F, 56.0F, 9.0F, 270, 56.0F, 7.0F, 58.0F, 4.0F, 90, 51.0F, 9.0F, 54.0F, 7.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_03.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 9.0F, 59.0F, 7.0F, 270, 56.0F, 7.0F, 54.0F, 4.0F, 90, 54.0F, 7.0F, 56.0F, 9.0F, 90, 56.0F, 7.0F, 59.0F, 9.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 90, 56.0F, 7.0F, 58.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_04.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 90, 54.0F, 7.0F, 51.0F, 9.0F, 180, 54.0F, 7.0F, 56.0F, 9.0F, 180, 56.0F, 9.0F, 59.0F, 7.0F, 0, 58.0F, 4.0F, 56.0F, 7.0F, 180, 56.0F, 4.0F, 54.0F, 7.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_05.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 270, 59.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 90, 58.0F, 7.0F, 56.0F, 4.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_06.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 90, 56.0F, 4.0F, 54.0F, 7.0F, 90, 56.0F, 7.0F, 54.0F, 9.0F, 270, 51.0F, 9.0F, 54.0F, 7.0F, 0, 58.0F, 7.0F, 56.0F, 4.0F, 0, 59.0F, 7.0F, 56.0F, 9.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_07.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 9.0F, 59.0F, 7.0F, 180, 58.0F, 4.0F, 56.0F, 7.0F, 90, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 90, 59.0F, 9.0F, 56.0F, 7.0F, 270, 54.0F, 7.0F, 51.0F, 9.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_08.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 180, 54.0F, 7.0F, 56.0F, 9.0F, 270, 58.0F, 7.0F, 56.0F, 4.0F, 270, 56.0F, 4.0F, 54.0F, 7.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_09.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 180, 54.0F, 7.0F, 56.0F, 4.0F, 270, 54.0F, 7.0F, 56.0F, 9.0F, 270, 58.0F, 7.0F, 56.0F, 4.0F, 90, 56.0F, 9.0F, 59.0F, 7.0F, 270, 54.0F, 9.0F, 51.0F, 7.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_10.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 90, 56.0F, 9.0F, 59.0F, 7.0F, 180, 54.0F, 9.0F, 56.0F, 7.0F, 180, 56.0F, 4.0F, 54.0F, 7.0F, 270, 51.0F, 7.0F, 54.0F, 9.0F, 90, 56.0F, 4.0F, 58.0F, 7.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_11.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 0, 56.0F, 7.0F, 58.0F, 4.0F, 270, 54.0F, 7.0F, 56.0F, 9.0F, 0, 51.0F, 9.0F, 54.0F, 7.0F, 180, 56.0F, 7.0F, 59.0F, 9.0F, 270, 54.0F, 7.0F, 56.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_12.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 90, 56.0F, 7.0F, 59.0F, 9.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 9.0F, 51.0F, 7.0F, 180, 56.0F, 7.0F, 54.0F, 4.0F, 180, 56.0F, 7.0F, 58.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_13.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 90, 54.0F, 4.0F, 56.0F, 7.0F, 270, 56.0F, 7.0F, 54.0F, 9.0F, 0, 54.0F, 7.0F, 51.0F, 9.0F, 0, 56.0F, 4.0F, 58.0F, 7.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_14.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 270, 59.0F, 7.0F, 56.0F, 9.0F, 90, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 270, 58.0F, 7.0F, 56.0F, 4.0F, 90, 51.0F, 7.0F, 54.0F, 9.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_15.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 180, 58.0F, 4.0F, 56.0F, 7.0F, 90, 54.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 90, 56.0F, 7.0F, 54.0F, 4.0F, 0, 54.0F, 9.0F, 51.0F, 7.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_16.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 270, 54.0F, 7.0F, 56.0F, 4.0F, 90, 54.0F, 7.0F, 56.0F, 9.0F, 270, 58.0F, 7.0F, 56.0F, 4.0F, 90, 56.0F, 9.0F, 59.0F, 7.0F, 270, 54.0F, 9.0F, 51.0F, 7.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_17.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 9.0F, 59.0F, 7.0F, 270, 56.0F, 7.0F, 54.0F, 4.0F, 90, 54.0F, 7.0F, 56.0F, 9.0F, 180, 56.0F, 7.0F, 59.0F, 9.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 90, 56.0F, 7.0F, 58.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_18.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 9.0F, 59.0F, 7.0F, 180, 58.0F, 4.0F, 56.0F, 7.0F, 180, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 90, 59.0F, 9.0F, 56.0F, 7.0F, 270, 54.0F, 7.0F, 51.0F, 9.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_19.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 180, 59.0F, 7.0F, 56.0F, 9.0F, 90, 54.0F, 7.0F, 56.0F, 9.0F, 90, 56.0F, 7.0F, 58.0F, 4.0F, 90, 51.0F, 9.0F, 54.0F, 7.0F, 0, 54.0F, 4.0F, 56.0F, 7.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_20.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 9.0F, 59.0F, 7.0F, 90, 58.0F, 4.0F, 56.0F, 7.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 90, 59.0F, 9.0F, 56.0F, 7.0F, 270, 54.0F, 7.0F, 51.0F, 9.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_21.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_21.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 180, 54.0F, 7.0F, 56.0F, 9.0F, 270, 58.0F, 7.0F, 56.0F, 4.0F, 0, 56.0F, 4.0F, 54.0F, 7.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_22.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_22.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 180, 54.0F, 7.0F, 51.0F, 9.0F, 0, 54.0F, 7.0F, 56.0F, 9.0F, 180, 56.0F, 9.0F, 59.0F, 7.0F, 0, 58.0F, 4.0F, 56.0F, 7.0F, 180, 56.0F, 4.0F, 54.0F, 7.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_23.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_23.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 180, 59.0F, 7.0F, 56.0F, 9.0F, 0, 54.0F, 7.0F, 56.0F, 9.0F, 90, 56.0F, 7.0F, 58.0F, 4.0F, 180, 51.0F, 9.0F, 54.0F, 7.0F, 0, 54.0F, 4.0F, 56.0F, 7.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_24.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_24.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 90, 54.0F, 4.0F, 56.0F, 7.0F, 90, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 7.0F, 51.0F, 9.0F, 0, 56.0F, 4.0F, 58.0F, 7.0F, 270, 56.0F, 9.0F, 59.0F, 7.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_25.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_25.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 270, 59.0F, 7.0F, 56.0F, 9.0F, 0, 54.0F, 7.0F, 56.0F, 9.0F, 90, 56.0F, 7.0F, 58.0F, 4.0F, 90, 51.0F, 9.0F, 54.0F, 7.0F, 90, 54.0F, 4.0F, 56.0F, 7.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_26.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_26.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 0, 59.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 180, 54.0F, 4.0F, 56.0F, 7.0F, 90, 58.0F, 7.0F, 56.0F, 4.0F, 270, 51.0F, 7.0F, 54.0F, 9.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_27.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_27.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 270, 59.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 90, 54.0F, 4.0F, 56.0F, 7.0F, 180, 58.0F, 7.0F, 56.0F, 4.0F, 90, 51.0F, 7.0F, 54.0F, 9.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_28.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_28.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 9.0F, 59.0F, 7.0F, 270, 56.0F, 7.0F, 54.0F, 4.0F, 180, 54.0F, 7.0F, 56.0F, 9.0F, 270, 56.0F, 7.0F, 59.0F, 9.0F, 180, 51.0F, 7.0F, 54.0F, 9.0F, 270, 56.0F, 7.0F, 58.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_29.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_29.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 180, 54.0F, 9.0F, 56.0F, 7.0F, 0, 56.0F, 4.0F, 54.0F, 7.0F, 270, 51.0F, 7.0F, 54.0F, 9.0F, 270, 56.0F, 4.0F, 58.0F, 7.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_30.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_30.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 270, 59.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 180, 54.0F, 4.0F, 56.0F, 7.0F, 90, 58.0F, 7.0F, 56.0F, 4.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_31.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_31.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 90, 56.0F, 7.0F, 59.0F, 9.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 9.0F, 51.0F, 7.0F, 90, 56.0F, 7.0F, 54.0F, 4.0F, 180, 56.0F, 7.0F, 58.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_32.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_32.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 270, 59.0F, 7.0F, 56.0F, 9.0F, 90, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 0, 58.0F, 7.0F, 56.0F, 4.0F, 270, 51.0F, 7.0F, 54.0F, 9.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_33.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_33.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 90, 56.0F, 9.0F, 59.0F, 7.0F, 180, 54.0F, 9.0F, 56.0F, 7.0F, 180, 56.0F, 4.0F, 54.0F, 7.0F, 180, 51.0F, 7.0F, 54.0F, 9.0F, 270, 56.0F, 4.0F, 58.0F, 7.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_34.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_34.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 0, 59.0F, 7.0F, 56.0F, 9.0F, 270, 56.0F, 7.0F, 54.0F, 9.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 90, 58.0F, 7.0F, 56.0F, 4.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_35.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_35.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 180, 59.0F, 7.0F, 56.0F, 9.0F, 0, 54.0F, 7.0F, 56.0F, 9.0F, 270, 56.0F, 7.0F, 58.0F, 4.0F, 270, 51.0F, 9.0F, 54.0F, 7.0F, 270, 54.0F, 4.0F, 56.0F, 7.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_36.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_36.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 9.0F, 61.0F, 7.0F, 270, 54.0F, 7.0F, 51.0F, 9.0F, 90, 54.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 0, 58.0F, 4.0F, 56.0F, 7.0F, 180, 56.0F, 4.0F, 54.0F, 7.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_37.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_37.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 90, 51.0F, 7.0F, 54.0F, 9.0F, 180, 54.0F, 7.0F, 56.0F, 9.0F, 90, 58.0F, 7.0F, 56.0F, 4.0F, 90, 56.0F, 4.0F, 54.0F, 7.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_38.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_38.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 7.0F, 59.0F, 9.0F, 0, 56.0F, 9.0F, 59.0F, 7.0F, 180, 54.0F, 9.0F, 56.0F, 7.0F, 270, 56.0F, 4.0F, 54.0F, 7.0F, 90, 51.0F, 7.0F, 54.0F, 9.0F, 90, 56.0F, 4.0F, 58.0F, 7.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_39.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_39.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 59.0F, 7.0F, 61.0F, 9.0F, 90, 59.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 7.0F, 54.0F, 9.0F, 180, 54.0F, 4.0F, 56.0F, 7.0F, 90, 58.0F, 7.0F, 56.0F, 4.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_40.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_40.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 61.0F, 9.0F, 59.0F, 7.0F, 0, 56.0F, 7.0F, 54.0F, 4.0F, 0, 54.0F, 7.0F, 56.0F, 9.0F, 0, 56.0F, 7.0F, 59.0F, 9.0F, 0, 51.0F, 7.0F, 54.0F, 9.0F, 0, 56.0F, 7.0F, 58.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian1Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_1_tile_01.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 12.0F, 59.0F, 6.0F, 180, 53.0F, 12.0F, 55.0F, 6.0F, 0, 53.0F, 12.0F, 51.0F, 6.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 57.0F, 6.0F, 55.0F, 4.0F, 0, 55.0F, 4.0F, 53.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_02.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 4.0F, 270, 53.0F, 6.0F, 55.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_03.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 12.0F, 59.0F, 6.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 55.0F, 12.0F, 53.0F, 6.0F, 180, 57.0F, 6.0F, 55.0F, 12.0F, 0, 55.0F, 4.0F, 57.0F, 6.0F, 270, 53.0F, 6.0F, 55.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_04.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 12.0F, 59.0F, 6.0F, 180, 57.0F, 12.0F, 55.0F, 6.0F, 0, 53.0F, 6.0F, 51.0F, 12.0F, 0, 53.0F, 6.0F, 55.0F, 12.0F, 180, 57.0F, 4.0F, 55.0F, 6.0F, 0, 53.0F, 6.0F, 55.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_05.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 53.0F, 6.0F, 51.0F, 12.0F, 0, 55.0F, 12.0F, 57.0F, 6.0F, 0, 59.0F, 12.0F, 57.0F, 6.0F, 0, 53.0F, 6.0F, 55.0F, 12.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_06.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 55.0F, 6.0F, 57.0F, 12.0F, 0, 51.0F, 12.0F, 53.0F, 6.0F, 0, 55.0F, 12.0F, 53.0F, 6.0F, 0, 57.0F, 12.0F, 59.0F, 6.0F, 0, 57.0F, 6.0F, 55.0F, 4.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_07.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 55.0F, 6.0F, 57.0F, 12.0F, 0, 51.0F, 12.0F, 53.0F, 6.0F, 0, 55.0F, 12.0F, 53.0F, 6.0F, 0, 57.0F, 12.0F, 59.0F, 6.0F, 270, 57.0F, 6.0F, 55.0F, 4.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_08.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 53.0F, 6.0F, 51.0F, 12.0F, 0, 55.0F, 12.0F, 57.0F, 6.0F, 0, 59.0F, 12.0F, 57.0F, 6.0F, 270, 53.0F, 6.0F, 55.0F, 12.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_09.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 55.0F, 6.0F, 57.0F, 12.0F, 180, 51.0F, 12.0F, 53.0F, 6.0F, 90, 55.0F, 12.0F, 53.0F, 6.0F, 90, 57.0F, 12.0F, 59.0F, 6.0F, 0, 57.0F, 6.0F, 55.0F, 4.0F, 90, 55.0F, 6.0F, 53.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_10.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 12.0F, 59.0F, 6.0F, 180, 53.0F, 12.0F, 55.0F, 6.0F, 0, 53.0F, 12.0F, 51.0F, 6.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 90, 57.0F, 6.0F, 55.0F, 4.0F, 0, 55.0F, 4.0F, 53.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_11.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 53.0F, 6.0F, 51.0F, 12.0F, 90, 55.0F, 12.0F, 57.0F, 6.0F, 0, 59.0F, 12.0F, 57.0F, 6.0F, 0, 53.0F, 6.0F, 55.0F, 12.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 90, 55.0F, 6.0F, 53.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_12.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 53.0F, 6.0F, 51.0F, 12.0F, 0, 55.0F, 12.0F, 57.0F, 6.0F, 0, 59.0F, 12.0F, 57.0F, 6.0F, 0, 53.0F, 6.0F, 55.0F, 12.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_13.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 12.0F, 59.0F, 6.0F, 180, 53.0F, 12.0F, 55.0F, 6.0F, 180, 53.0F, 12.0F, 51.0F, 6.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 57.0F, 6.0F, 55.0F, 4.0F, 0, 55.0F, 4.0F, 53.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_14.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 180, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 4.0F, 90, 53.0F, 6.0F, 55.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_15.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 55.0F, 6.0F, 57.0F, 12.0F, 0, 51.0F, 12.0F, 53.0F, 6.0F, 0, 55.0F, 12.0F, 53.0F, 6.0F, 0, 57.0F, 12.0F, 59.0F, 6.0F, 0, 57.0F, 6.0F, 55.0F, 4.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_16.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 4.0F, 180, 53.0F, 6.0F, 55.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_17.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 90, 55.0F, 6.0F, 57.0F, 4.0F, 270, 53.0F, 6.0F, 55.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_18.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 270, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 4.0F, 270, 53.0F, 6.0F, 55.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_19.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 6.0F, 59.0F, 12.0F, 90, 55.0F, 6.0F, 57.0F, 12.0F, 90, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 4.0F, 90, 53.0F, 6.0F, 55.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_20.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, 0.0F, -1.0F, 1.0F, 6.0F, 1.0F, 57.0F, 12.0F, 59.0F, 6.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 90, 55.0F, 12.0F, 53.0F, 6.0F, 270, 57.0F, 6.0F, 55.0F, 12.0F, 0, 55.0F, 4.0F, 57.0F, 6.0F, 270, 53.0F, 6.0F, 55.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian2Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_2_tile_01.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 0, 57.9F, 8.0F, 52.9F, 5.9F, 0, 52.9F, 8.0F, 51.0F, 5.9F, 180, 62.9F, 5.9F, 57.9F, 4.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_02.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 59.8F, 5.9F, 57.9F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 8.0F, 0, 52.9F, 5.9F, 51.0F, 8.0F, 180, 57.9F, 5.9F, 62.9F, 4.0F, 0, 57.9F, 4.0F, 52.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_03.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 8.0F, 59.8F, 5.9F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 52.9F, 5.9F, 57.9F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_04.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 8.0F, 52.9F, 5.9F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 0, 57.9F, 5.9F, 62.9F, 4.0F, 180, 52.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_05.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 5.9F, 64.8F, 8.0F, 0, 59.8F, 5.9F, 57.9F, 8.0F, 180, 52.9F, 8.0F, 57.9F, 5.9F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 57.9F, 4.0F, 62.9F, 5.9F, 0, 52.9F, 5.9F, 57.9F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_06.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 5.9F, 52.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 180, 52.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_07.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 8.0F, 64.8F, 5.9F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 180, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 52.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_08.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 8.0F, 59.8F, 5.9F, 0, 52.9F, 8.0F, 57.9F, 5.9F, 180, 51.0F, 8.0F, 52.9F, 5.9F, 180, 62.9F, 5.9F, 57.9F, 4.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_09.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 0, 57.9F, 8.0F, 52.9F, 5.9F, 0, 52.9F, 8.0F, 51.0F, 5.9F, 180, 62.9F, 5.9F, 57.9F, 4.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_10.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 59.8F, 5.9F, 57.9F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 8.0F, 0, 52.9F, 5.9F, 51.0F, 8.0F, 180, 57.9F, 5.9F, 62.9F, 4.0F, 0, 57.9F, 4.0F, 52.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_11.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 8.0F, 64.8F, 5.9F, 90, 57.9F, 8.0F, 59.8F, 5.9F, 180, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 52.9F, 4.0F, 57.9F, 5.9F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_12.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 8.0F, 64.8F, 5.9F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 180, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 52.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_13.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 8.0F, 64.8F, 5.9F, 90, 57.9F, 8.0F, 59.8F, 5.9F, 180, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 52.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_14.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 8.0F, 59.8F, 5.9F, 0, 52.9F, 8.0F, 57.9F, 5.9F, 180, 51.0F, 8.0F, 52.9F, 5.9F, 90, 62.9F, 5.9F, 57.9F, 4.0F, 90, 57.9F, 5.9F, 52.9F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_15.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 59.8F, 5.9F, 57.9F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 8.0F, 0, 52.9F, 5.9F, 51.0F, 8.0F, 180, 57.9F, 5.9F, 62.9F, 4.0F, 0, 57.9F, 4.0F, 52.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_16.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 8.0F, 59.8F, 5.9F, 270, 57.9F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 5.9F, 52.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 52.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_17.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 0, 57.9F, 8.0F, 52.9F, 5.9F, 270, 51.0F, 8.0F, 52.9F, 5.9F, 0, 57.9F, 5.9F, 62.9F, 4.0F, 180, 52.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_18.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 8.0F, 52.9F, 5.9F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 180, 57.9F, 5.9F, 62.9F, 4.0F, 90, 52.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_19.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 0, 57.9F, 8.0F, 52.9F, 5.9F, 0, 52.9F, 8.0F, 51.0F, 5.9F, 0, 62.9F, 5.9F, 57.9F, 4.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_20.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 8.0F, 59.8F, 5.9F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 0, 57.9F, 5.9F, 52.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 52.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_21.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_21.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 64.8F, 5.9F, 59.8F, 8.0F, 270, 57.9F, 8.0F, 59.8F, 5.9F, 180, 57.9F, 8.0F, 52.9F, 5.9F, 180, 52.9F, 8.0F, 51.0F, 5.9F, 0, 62.9F, 5.9F, 57.9F, 4.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_22.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_22.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 8.0F, 64.8F, 5.9F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 180, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 270, 62.9F, 4.0F, 57.9F, 5.9F, 180, 52.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_23.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_23.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 5.9F, 64.8F, 8.0F, 270, 59.8F, 5.9F, 57.9F, 8.0F, 180, 52.9F, 8.0F, 57.9F, 5.9F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 57.9F, 4.0F, 62.9F, 5.9F, 0, 52.9F, 5.9F, 57.9F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_24.visible) {
            poseStack.pushPose();
            this.Head.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_24.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.1F, -1.15F, -1.05F, 0.1F, 1.15F, 1.05F, 59.8F, 8.0F, 64.8F, 5.9F, 0, 57.9F, 8.0F, 59.8F, 5.9F, 180, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 8.0F, 52.9F, 5.9F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 52.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    public void resetAnimationPose() {
        this.obsidian_0_bone.resetPose();
        this.obsidian_0_tile_01.resetPose();
        this.obsidian_0_tile_02.resetPose();
        this.obsidian_0_tile_03.resetPose();
        this.obsidian_0_tile_04.resetPose();
        this.obsidian_0_tile_05.resetPose();
        this.obsidian_0_tile_06.resetPose();
        this.obsidian_0_tile_07.resetPose();
        this.obsidian_0_tile_08.resetPose();
        this.obsidian_0_tile_09.resetPose();
        this.obsidian_0_tile_10.resetPose();
        this.obsidian_0_tile_11.resetPose();
        this.obsidian_0_tile_12.resetPose();
        this.obsidian_0_tile_13.resetPose();
        this.obsidian_0_tile_14.resetPose();
        this.obsidian_0_tile_15.resetPose();
        this.obsidian_0_tile_16.resetPose();
        this.obsidian_0_tile_17.resetPose();
        this.obsidian_0_tile_18.resetPose();
        this.obsidian_0_tile_19.resetPose();
        this.obsidian_0_tile_20.resetPose();
        this.obsidian_0_tile_21.resetPose();
        this.obsidian_0_tile_22.resetPose();
        this.obsidian_0_tile_23.resetPose();
        this.obsidian_0_tile_24.resetPose();
        this.obsidian_0_tile_25.resetPose();
        this.obsidian_0_tile_26.resetPose();
        this.obsidian_0_tile_27.resetPose();
        this.obsidian_0_tile_28.resetPose();
        this.obsidian_0_tile_29.resetPose();
        this.obsidian_0_tile_30.resetPose();
        this.obsidian_0_tile_31.resetPose();
        this.obsidian_0_tile_32.resetPose();
        this.obsidian_0_tile_33.resetPose();
        this.obsidian_0_tile_34.resetPose();
        this.obsidian_0_tile_35.resetPose();
        this.obsidian_0_tile_36.resetPose();
        this.obsidian_0_tile_37.resetPose();
        this.obsidian_0_tile_38.resetPose();
        this.obsidian_0_tile_39.resetPose();
        this.obsidian_0_tile_40.resetPose();
        this.obsidian_1_bone.resetPose();
        this.obsidian_1_tile_01.resetPose();
        this.obsidian_1_tile_02.resetPose();
        this.obsidian_1_tile_03.resetPose();
        this.obsidian_1_tile_04.resetPose();
        this.obsidian_1_tile_05.resetPose();
        this.obsidian_1_tile_06.resetPose();
        this.obsidian_1_tile_07.resetPose();
        this.obsidian_1_tile_08.resetPose();
        this.obsidian_1_tile_09.resetPose();
        this.obsidian_1_tile_10.resetPose();
        this.obsidian_1_tile_11.resetPose();
        this.obsidian_1_tile_12.resetPose();
        this.obsidian_1_tile_13.resetPose();
        this.obsidian_1_tile_14.resetPose();
        this.obsidian_1_tile_15.resetPose();
        this.obsidian_1_tile_16.resetPose();
        this.obsidian_1_tile_17.resetPose();
        this.obsidian_1_tile_18.resetPose();
        this.obsidian_1_tile_19.resetPose();
        this.obsidian_1_tile_20.resetPose();
        this.obsidian_2_bone.resetPose();
        this.obsidian_2_tile_01.resetPose();
        this.obsidian_2_tile_02.resetPose();
        this.obsidian_2_tile_03.resetPose();
        this.obsidian_2_tile_04.resetPose();
        this.obsidian_2_tile_05.resetPose();
        this.obsidian_2_tile_06.resetPose();
        this.obsidian_2_tile_07.resetPose();
        this.obsidian_2_tile_08.resetPose();
        this.obsidian_2_tile_09.resetPose();
        this.obsidian_2_tile_10.resetPose();
        this.obsidian_2_tile_11.resetPose();
        this.obsidian_2_tile_12.resetPose();
        this.obsidian_2_tile_13.resetPose();
        this.obsidian_2_tile_14.resetPose();
        this.obsidian_2_tile_15.resetPose();
        this.obsidian_2_tile_16.resetPose();
        this.obsidian_2_tile_17.resetPose();
        this.obsidian_2_tile_18.resetPose();
        this.obsidian_2_tile_19.resetPose();
        this.obsidian_2_tile_20.resetPose();
        this.obsidian_2_tile_21.resetPose();
        this.obsidian_2_tile_22.resetPose();
        this.obsidian_2_tile_23.resetPose();
        this.obsidian_2_tile_24.resetPose();
        this.obsidian_0_tile_01.visible = false;
        this.obsidian_0_tile_02.visible = false;
        this.obsidian_0_tile_03.visible = false;
        this.obsidian_0_tile_04.visible = false;
        this.obsidian_0_tile_05.visible = false;
        this.obsidian_0_tile_06.visible = false;
        this.obsidian_0_tile_07.visible = false;
        this.obsidian_0_tile_08.visible = false;
        this.obsidian_0_tile_09.visible = false;
        this.obsidian_0_tile_10.visible = false;
        this.obsidian_0_tile_11.visible = false;
        this.obsidian_0_tile_12.visible = false;
        this.obsidian_0_tile_13.visible = false;
        this.obsidian_0_tile_14.visible = false;
        this.obsidian_0_tile_15.visible = false;
        this.obsidian_0_tile_16.visible = false;
        this.obsidian_0_tile_17.visible = false;
        this.obsidian_0_tile_18.visible = false;
        this.obsidian_0_tile_19.visible = false;
        this.obsidian_0_tile_20.visible = false;
        this.obsidian_0_tile_21.visible = false;
        this.obsidian_0_tile_22.visible = false;
        this.obsidian_0_tile_23.visible = false;
        this.obsidian_0_tile_24.visible = false;
        this.obsidian_0_tile_25.visible = false;
        this.obsidian_0_tile_26.visible = false;
        this.obsidian_0_tile_27.visible = false;
        this.obsidian_0_tile_28.visible = false;
        this.obsidian_0_tile_29.visible = false;
        this.obsidian_0_tile_30.visible = false;
        this.obsidian_0_tile_31.visible = false;
        this.obsidian_0_tile_32.visible = false;
        this.obsidian_0_tile_33.visible = false;
        this.obsidian_0_tile_34.visible = false;
        this.obsidian_0_tile_35.visible = false;
        this.obsidian_0_tile_36.visible = false;
        this.obsidian_0_tile_37.visible = false;
        this.obsidian_0_tile_38.visible = false;
        this.obsidian_0_tile_39.visible = false;
        this.obsidian_0_tile_40.visible = false;
        this.obsidian_1_tile_01.visible = false;
        this.obsidian_1_tile_02.visible = false;
        this.obsidian_1_tile_03.visible = false;
        this.obsidian_1_tile_04.visible = false;
        this.obsidian_1_tile_05.visible = false;
        this.obsidian_1_tile_06.visible = false;
        this.obsidian_1_tile_07.visible = false;
        this.obsidian_1_tile_08.visible = false;
        this.obsidian_1_tile_09.visible = false;
        this.obsidian_1_tile_10.visible = false;
        this.obsidian_1_tile_11.visible = false;
        this.obsidian_1_tile_12.visible = false;
        this.obsidian_1_tile_13.visible = false;
        this.obsidian_1_tile_14.visible = false;
        this.obsidian_1_tile_15.visible = false;
        this.obsidian_1_tile_16.visible = false;
        this.obsidian_1_tile_17.visible = false;
        this.obsidian_1_tile_18.visible = false;
        this.obsidian_1_tile_19.visible = false;
        this.obsidian_1_tile_20.visible = false;
        this.obsidian_2_tile_01.visible = false;
        this.obsidian_2_tile_02.visible = false;
        this.obsidian_2_tile_03.visible = false;
        this.obsidian_2_tile_04.visible = false;
        this.obsidian_2_tile_05.visible = false;
        this.obsidian_2_tile_06.visible = false;
        this.obsidian_2_tile_07.visible = false;
        this.obsidian_2_tile_08.visible = false;
        this.obsidian_2_tile_09.visible = false;
        this.obsidian_2_tile_10.visible = false;
        this.obsidian_2_tile_11.visible = false;
        this.obsidian_2_tile_12.visible = false;
        this.obsidian_2_tile_13.visible = false;
        this.obsidian_2_tile_14.visible = false;
        this.obsidian_2_tile_15.visible = false;
        this.obsidian_2_tile_16.visible = false;
        this.obsidian_2_tile_17.visible = false;
        this.obsidian_2_tile_18.visible = false;
        this.obsidian_2_tile_19.visible = false;
        this.obsidian_2_tile_20.visible = false;
        this.obsidian_2_tile_21.visible = false;
        this.obsidian_2_tile_22.visible = false;
        this.obsidian_2_tile_23.visible = false;
        this.obsidian_2_tile_24.visible = false;
    }

    public void applyAnimationPose(ObsidianArmorPoseClip clip, float elapsedTicks) {
        resetAnimationPose();
        applyPartPose(this.obsidian_0_bone, "obsidian_0_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_01, "obsidian_0_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_02, "obsidian_0_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_03, "obsidian_0_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_04, "obsidian_0_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_05, "obsidian_0_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_06, "obsidian_0_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_07, "obsidian_0_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_08, "obsidian_0_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_09, "obsidian_0_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_10, "obsidian_0_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_11, "obsidian_0_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_12, "obsidian_0_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_13, "obsidian_0_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_14, "obsidian_0_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_15, "obsidian_0_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_16, "obsidian_0_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_17, "obsidian_0_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_18, "obsidian_0_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_19, "obsidian_0_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_20, "obsidian_0_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_21, "obsidian_0_tile_21", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_22, "obsidian_0_tile_22", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_23, "obsidian_0_tile_23", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_24, "obsidian_0_tile_24", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_25, "obsidian_0_tile_25", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_26, "obsidian_0_tile_26", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_27, "obsidian_0_tile_27", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_28, "obsidian_0_tile_28", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_29, "obsidian_0_tile_29", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_30, "obsidian_0_tile_30", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_31, "obsidian_0_tile_31", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_32, "obsidian_0_tile_32", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_33, "obsidian_0_tile_33", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_34, "obsidian_0_tile_34", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_35, "obsidian_0_tile_35", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_36, "obsidian_0_tile_36", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_37, "obsidian_0_tile_37", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_38, "obsidian_0_tile_38", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_39, "obsidian_0_tile_39", clip, elapsedTicks);
        applyPartPose(this.obsidian_0_tile_40, "obsidian_0_tile_40", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_bone, "obsidian_1_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_01, "obsidian_1_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_02, "obsidian_1_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_03, "obsidian_1_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_04, "obsidian_1_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_05, "obsidian_1_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_06, "obsidian_1_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_07, "obsidian_1_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_08, "obsidian_1_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_09, "obsidian_1_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_10, "obsidian_1_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_11, "obsidian_1_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_12, "obsidian_1_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_13, "obsidian_1_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_14, "obsidian_1_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_15, "obsidian_1_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_16, "obsidian_1_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_17, "obsidian_1_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_18, "obsidian_1_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_19, "obsidian_1_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_1_tile_20, "obsidian_1_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_bone, "obsidian_2_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_01, "obsidian_2_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_02, "obsidian_2_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_03, "obsidian_2_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_04, "obsidian_2_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_05, "obsidian_2_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_06, "obsidian_2_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_07, "obsidian_2_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_08, "obsidian_2_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_09, "obsidian_2_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_10, "obsidian_2_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_11, "obsidian_2_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_12, "obsidian_2_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_13, "obsidian_2_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_14, "obsidian_2_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_15, "obsidian_2_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_16, "obsidian_2_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_17, "obsidian_2_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_18, "obsidian_2_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_19, "obsidian_2_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_20, "obsidian_2_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_21, "obsidian_2_tile_21", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_22, "obsidian_2_tile_22", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_23, "obsidian_2_tile_23", clip, elapsedTicks);
        applyPartPose(this.obsidian_2_tile_24, "obsidian_2_tile_24", clip, elapsedTicks);
    }

    private static void applyPartPose(ModelPart part, String name, ObsidianArmorPoseClip clip, float elapsedTicks) {
        if (!clip.hasTrack(name)) return;
        ObsidianArmorPoseClip.Pose pose = clip.sampleModel(name, elapsedTicks);
        part.x += pose.x();
        part.y += pose.y();
        part.z += pose.z();
        part.xRot += pose.xRot();
        part.yRot += pose.yRot();
        part.zRot += pose.zRot();
        if (name.contains("_tile_")) part.visible = true;
    }

    public ModelPart animationPart(String name) {
        return switch (name) {
            case "obsidian_0_bone" -> this.obsidian_0_bone;
            case "obsidian_0_tile_01" -> this.obsidian_0_tile_01;
            case "obsidian_0_tile_02" -> this.obsidian_0_tile_02;
            case "obsidian_0_tile_03" -> this.obsidian_0_tile_03;
            case "obsidian_0_tile_04" -> this.obsidian_0_tile_04;
            case "obsidian_0_tile_05" -> this.obsidian_0_tile_05;
            case "obsidian_0_tile_06" -> this.obsidian_0_tile_06;
            case "obsidian_0_tile_07" -> this.obsidian_0_tile_07;
            case "obsidian_0_tile_08" -> this.obsidian_0_tile_08;
            case "obsidian_0_tile_09" -> this.obsidian_0_tile_09;
            case "obsidian_0_tile_10" -> this.obsidian_0_tile_10;
            case "obsidian_0_tile_11" -> this.obsidian_0_tile_11;
            case "obsidian_0_tile_12" -> this.obsidian_0_tile_12;
            case "obsidian_0_tile_13" -> this.obsidian_0_tile_13;
            case "obsidian_0_tile_14" -> this.obsidian_0_tile_14;
            case "obsidian_0_tile_15" -> this.obsidian_0_tile_15;
            case "obsidian_0_tile_16" -> this.obsidian_0_tile_16;
            case "obsidian_0_tile_17" -> this.obsidian_0_tile_17;
            case "obsidian_0_tile_18" -> this.obsidian_0_tile_18;
            case "obsidian_0_tile_19" -> this.obsidian_0_tile_19;
            case "obsidian_0_tile_20" -> this.obsidian_0_tile_20;
            case "obsidian_0_tile_21" -> this.obsidian_0_tile_21;
            case "obsidian_0_tile_22" -> this.obsidian_0_tile_22;
            case "obsidian_0_tile_23" -> this.obsidian_0_tile_23;
            case "obsidian_0_tile_24" -> this.obsidian_0_tile_24;
            case "obsidian_0_tile_25" -> this.obsidian_0_tile_25;
            case "obsidian_0_tile_26" -> this.obsidian_0_tile_26;
            case "obsidian_0_tile_27" -> this.obsidian_0_tile_27;
            case "obsidian_0_tile_28" -> this.obsidian_0_tile_28;
            case "obsidian_0_tile_29" -> this.obsidian_0_tile_29;
            case "obsidian_0_tile_30" -> this.obsidian_0_tile_30;
            case "obsidian_0_tile_31" -> this.obsidian_0_tile_31;
            case "obsidian_0_tile_32" -> this.obsidian_0_tile_32;
            case "obsidian_0_tile_33" -> this.obsidian_0_tile_33;
            case "obsidian_0_tile_34" -> this.obsidian_0_tile_34;
            case "obsidian_0_tile_35" -> this.obsidian_0_tile_35;
            case "obsidian_0_tile_36" -> this.obsidian_0_tile_36;
            case "obsidian_0_tile_37" -> this.obsidian_0_tile_37;
            case "obsidian_0_tile_38" -> this.obsidian_0_tile_38;
            case "obsidian_0_tile_39" -> this.obsidian_0_tile_39;
            case "obsidian_0_tile_40" -> this.obsidian_0_tile_40;
            case "obsidian_1_bone" -> this.obsidian_1_bone;
            case "obsidian_1_tile_01" -> this.obsidian_1_tile_01;
            case "obsidian_1_tile_02" -> this.obsidian_1_tile_02;
            case "obsidian_1_tile_03" -> this.obsidian_1_tile_03;
            case "obsidian_1_tile_04" -> this.obsidian_1_tile_04;
            case "obsidian_1_tile_05" -> this.obsidian_1_tile_05;
            case "obsidian_1_tile_06" -> this.obsidian_1_tile_06;
            case "obsidian_1_tile_07" -> this.obsidian_1_tile_07;
            case "obsidian_1_tile_08" -> this.obsidian_1_tile_08;
            case "obsidian_1_tile_09" -> this.obsidian_1_tile_09;
            case "obsidian_1_tile_10" -> this.obsidian_1_tile_10;
            case "obsidian_1_tile_11" -> this.obsidian_1_tile_11;
            case "obsidian_1_tile_12" -> this.obsidian_1_tile_12;
            case "obsidian_1_tile_13" -> this.obsidian_1_tile_13;
            case "obsidian_1_tile_14" -> this.obsidian_1_tile_14;
            case "obsidian_1_tile_15" -> this.obsidian_1_tile_15;
            case "obsidian_1_tile_16" -> this.obsidian_1_tile_16;
            case "obsidian_1_tile_17" -> this.obsidian_1_tile_17;
            case "obsidian_1_tile_18" -> this.obsidian_1_tile_18;
            case "obsidian_1_tile_19" -> this.obsidian_1_tile_19;
            case "obsidian_1_tile_20" -> this.obsidian_1_tile_20;
            case "obsidian_2_bone" -> this.obsidian_2_bone;
            case "obsidian_2_tile_01" -> this.obsidian_2_tile_01;
            case "obsidian_2_tile_02" -> this.obsidian_2_tile_02;
            case "obsidian_2_tile_03" -> this.obsidian_2_tile_03;
            case "obsidian_2_tile_04" -> this.obsidian_2_tile_04;
            case "obsidian_2_tile_05" -> this.obsidian_2_tile_05;
            case "obsidian_2_tile_06" -> this.obsidian_2_tile_06;
            case "obsidian_2_tile_07" -> this.obsidian_2_tile_07;
            case "obsidian_2_tile_08" -> this.obsidian_2_tile_08;
            case "obsidian_2_tile_09" -> this.obsidian_2_tile_09;
            case "obsidian_2_tile_10" -> this.obsidian_2_tile_10;
            case "obsidian_2_tile_11" -> this.obsidian_2_tile_11;
            case "obsidian_2_tile_12" -> this.obsidian_2_tile_12;
            case "obsidian_2_tile_13" -> this.obsidian_2_tile_13;
            case "obsidian_2_tile_14" -> this.obsidian_2_tile_14;
            case "obsidian_2_tile_15" -> this.obsidian_2_tile_15;
            case "obsidian_2_tile_16" -> this.obsidian_2_tile_16;
            case "obsidian_2_tile_17" -> this.obsidian_2_tile_17;
            case "obsidian_2_tile_18" -> this.obsidian_2_tile_18;
            case "obsidian_2_tile_19" -> this.obsidian_2_tile_19;
            case "obsidian_2_tile_20" -> this.obsidian_2_tile_20;
            case "obsidian_2_tile_21" -> this.obsidian_2_tile_21;
            case "obsidian_2_tile_22" -> this.obsidian_2_tile_22;
            case "obsidian_2_tile_23" -> this.obsidian_2_tile_23;
            case "obsidian_2_tile_24" -> this.obsidian_2_tile_24;
            default -> null;
        };
    }

}
