package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPoseClip;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ModelHerobrineObsidianDiamondChestplate<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelherobrineobsidiandiamondchestplate"), "main");
    private final ModelPart modelRoot;
    public final ModelPart Body;
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
    private final ModelPart cube_r3;
    private final ModelPart obsidian_3_bone;
    private final ModelPart obsidian_3_tile_01;
    private final ModelPart obsidian_3_tile_02;
    private final ModelPart obsidian_3_tile_03;
    private final ModelPart obsidian_3_tile_04;
    private final ModelPart obsidian_3_tile_05;
    private final ModelPart obsidian_3_tile_06;
    private final ModelPart obsidian_3_tile_07;
    private final ModelPart obsidian_3_tile_08;
    private final ModelPart obsidian_3_tile_09;
    private final ModelPart obsidian_3_tile_10;
    private final ModelPart obsidian_3_tile_11;
    private final ModelPart obsidian_3_tile_12;
    private final ModelPart obsidian_3_tile_13;
    private final ModelPart obsidian_3_tile_14;
    private final ModelPart obsidian_3_tile_15;
    private final ModelPart obsidian_3_tile_16;
    private final ModelPart obsidian_3_tile_17;
    private final ModelPart obsidian_3_tile_18;
    private final ModelPart cube_r4;
    private final ModelPart obsidian_4_bone;
    private final ModelPart obsidian_4_tile_01;
    private final ModelPart obsidian_4_tile_02;
    private final ModelPart obsidian_4_tile_03;
    private final ModelPart obsidian_4_tile_04;
    private final ModelPart obsidian_4_tile_05;
    private final ModelPart obsidian_4_tile_06;
    private final ModelPart obsidian_4_tile_07;
    private final ModelPart obsidian_4_tile_08;
    private final ModelPart obsidian_4_tile_09;
    private final ModelPart obsidian_4_tile_10;
    private final ModelPart obsidian_4_tile_11;
    private final ModelPart obsidian_4_tile_12;
    private final ModelPart obsidian_4_tile_13;
    private final ModelPart obsidian_4_tile_14;
    private final ModelPart obsidian_4_tile_15;
    private final ModelPart obsidian_4_tile_16;
    private final ModelPart obsidian_4_tile_17;
    private final ModelPart obsidian_4_tile_18;
    private final ModelPart obsidian_4_tile_19;
    private final ModelPart obsidian_4_tile_20;
    private final ModelPart obsidian_4_tile_21;
    private final ModelPart obsidian_4_tile_22;
    private final ModelPart obsidian_4_tile_23;
    private final ModelPart obsidian_4_tile_24;
    private final ModelPart cube_r5;
    private final ModelPart cube_r6;
    private final ModelPart obsidian_6_bone;
    private final ModelPart obsidian_6_tile_01;
    private final ModelPart obsidian_6_tile_02;
    private final ModelPart obsidian_6_tile_03;
    private final ModelPart obsidian_6_tile_04;
    private final ModelPart obsidian_6_tile_05;
    private final ModelPart obsidian_6_tile_06;
    private final ModelPart obsidian_6_tile_07;
    private final ModelPart obsidian_6_tile_08;
    private final ModelPart obsidian_6_tile_09;
    private final ModelPart obsidian_6_tile_10;
    private final ModelPart obsidian_6_tile_11;
    private final ModelPart obsidian_6_tile_12;
    private final ModelPart obsidian_6_tile_13;
    private final ModelPart obsidian_6_tile_14;
    private final ModelPart obsidian_6_tile_15;
    private final ModelPart obsidian_6_tile_16;
    private final ModelPart obsidian_6_tile_17;
    private final ModelPart obsidian_6_tile_18;
    private final ModelPart obsidian_6_tile_19;
    private final ModelPart obsidian_6_tile_20;
    private final ModelPart obsidian_6_tile_21;
    private final ModelPart obsidian_6_tile_22;
    private final ModelPart obsidian_6_tile_23;
    private final ModelPart obsidian_6_tile_24;
    private final ModelPart obsidian_6_tile_25;
    private final ModelPart obsidian_6_tile_26;
    private final ModelPart obsidian_6_tile_27;
    private final ModelPart obsidian_6_tile_28;
    private final ModelPart obsidian_6_tile_29;
    private final ModelPart obsidian_6_tile_30;
    public final ModelPart RightArm;
    private final ModelPart cube_r7;
    private final ModelPart obsidian_7_bone;
    private final ModelPart obsidian_7_tile_01;
    private final ModelPart obsidian_7_tile_02;
    private final ModelPart obsidian_7_tile_03;
    private final ModelPart obsidian_7_tile_04;
    private final ModelPart obsidian_7_tile_05;
    private final ModelPart obsidian_7_tile_06;
    private final ModelPart obsidian_7_tile_07;
    private final ModelPart obsidian_7_tile_08;
    private final ModelPart obsidian_7_tile_09;
    private final ModelPart obsidian_7_tile_10;
    private final ModelPart obsidian_7_tile_11;
    private final ModelPart obsidian_7_tile_12;
    private final ModelPart obsidian_7_tile_13;
    private final ModelPart obsidian_7_tile_14;
    private final ModelPart obsidian_7_tile_15;
    private final ModelPart obsidian_7_tile_16;
    private final ModelPart obsidian_7_tile_17;
    private final ModelPart obsidian_7_tile_18;
    private final ModelPart obsidian_7_tile_19;
    private final ModelPart obsidian_7_tile_20;
    private final ModelPart obsidian_7_tile_21;
    private final ModelPart obsidian_7_tile_22;
    private final ModelPart obsidian_7_tile_23;
    private final ModelPart obsidian_7_tile_24;
    private final ModelPart cube_r8;
    private final ModelPart obsidian_8_bone;
    private final ModelPart obsidian_8_tile_01;
    private final ModelPart obsidian_8_tile_02;
    private final ModelPart obsidian_8_tile_03;
    private final ModelPart obsidian_8_tile_04;
    private final ModelPart obsidian_8_tile_05;
    private final ModelPart obsidian_8_tile_06;
    private final ModelPart obsidian_8_tile_07;
    private final ModelPart obsidian_8_tile_08;
    private final ModelPart obsidian_8_tile_09;
    private final ModelPart obsidian_8_tile_10;
    private final ModelPart obsidian_8_tile_11;
    private final ModelPart obsidian_8_tile_12;
    private final ModelPart obsidian_8_tile_13;
    private final ModelPart obsidian_8_tile_14;
    private final ModelPart obsidian_8_tile_15;
    private final ModelPart obsidian_8_tile_16;
    private final ModelPart obsidian_8_tile_17;
    private final ModelPart obsidian_8_tile_18;
    private final ModelPart obsidian_8_tile_19;
    private final ModelPart obsidian_8_tile_20;
    private final ModelPart cube_r9;
    private final ModelPart obsidian_9_bone;
    private final ModelPart obsidian_9_tile_01;
    private final ModelPart obsidian_9_tile_02;
    private final ModelPart obsidian_9_tile_03;
    private final ModelPart obsidian_9_tile_04;
    private final ModelPart obsidian_9_tile_05;
    private final ModelPart obsidian_9_tile_06;
    private final ModelPart obsidian_9_tile_07;
    private final ModelPart obsidian_9_tile_08;
    private final ModelPart obsidian_9_tile_09;
    private final ModelPart obsidian_9_tile_10;
    private final ModelPart obsidian_9_tile_11;
    private final ModelPart obsidian_9_tile_12;
    private final ModelPart obsidian_9_tile_13;
    private final ModelPart obsidian_9_tile_14;
    private final ModelPart obsidian_9_tile_15;
    private final ModelPart obsidian_9_tile_16;
    private final ModelPart obsidian_9_tile_17;
    private final ModelPart obsidian_9_tile_18;
    private final ModelPart obsidian_9_tile_19;
    private final ModelPart obsidian_9_tile_20;
    private final ModelPart obsidian_9_tile_21;
    private final ModelPart obsidian_9_tile_22;
    private final ModelPart obsidian_9_tile_23;
    private final ModelPart obsidian_9_tile_24;
    public final ModelPart LeftArm;

    public ModelHerobrineObsidianDiamondChestplate(ModelPart root) {
        this.modelRoot = root;
        this.Body = root.getChild("Body");
        this.obsidian_0_bone = this.Body.getChild("obsidian_0_bone");
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
        this.cube_r1 = this.Body.getChild("cube_r1");
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
        this.cube_r2 = this.Body.getChild("cube_r2");
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
        this.cube_r3 = this.Body.getChild("cube_r3");
        this.obsidian_3_bone = this.cube_r3.getChild("obsidian_3_bone");
        this.obsidian_3_tile_01 = this.obsidian_3_bone.getChild("obsidian_3_tile_01");
        this.obsidian_3_tile_02 = this.obsidian_3_bone.getChild("obsidian_3_tile_02");
        this.obsidian_3_tile_03 = this.obsidian_3_bone.getChild("obsidian_3_tile_03");
        this.obsidian_3_tile_04 = this.obsidian_3_bone.getChild("obsidian_3_tile_04");
        this.obsidian_3_tile_05 = this.obsidian_3_bone.getChild("obsidian_3_tile_05");
        this.obsidian_3_tile_06 = this.obsidian_3_bone.getChild("obsidian_3_tile_06");
        this.obsidian_3_tile_07 = this.obsidian_3_bone.getChild("obsidian_3_tile_07");
        this.obsidian_3_tile_08 = this.obsidian_3_bone.getChild("obsidian_3_tile_08");
        this.obsidian_3_tile_09 = this.obsidian_3_bone.getChild("obsidian_3_tile_09");
        this.obsidian_3_tile_10 = this.obsidian_3_bone.getChild("obsidian_3_tile_10");
        this.obsidian_3_tile_11 = this.obsidian_3_bone.getChild("obsidian_3_tile_11");
        this.obsidian_3_tile_12 = this.obsidian_3_bone.getChild("obsidian_3_tile_12");
        this.obsidian_3_tile_13 = this.obsidian_3_bone.getChild("obsidian_3_tile_13");
        this.obsidian_3_tile_14 = this.obsidian_3_bone.getChild("obsidian_3_tile_14");
        this.obsidian_3_tile_15 = this.obsidian_3_bone.getChild("obsidian_3_tile_15");
        this.obsidian_3_tile_16 = this.obsidian_3_bone.getChild("obsidian_3_tile_16");
        this.obsidian_3_tile_17 = this.obsidian_3_bone.getChild("obsidian_3_tile_17");
        this.obsidian_3_tile_18 = this.obsidian_3_bone.getChild("obsidian_3_tile_18");
        this.cube_r4 = this.Body.getChild("cube_r4");
        this.obsidian_4_bone = this.cube_r4.getChild("obsidian_4_bone");
        this.obsidian_4_tile_01 = this.obsidian_4_bone.getChild("obsidian_4_tile_01");
        this.obsidian_4_tile_02 = this.obsidian_4_bone.getChild("obsidian_4_tile_02");
        this.obsidian_4_tile_03 = this.obsidian_4_bone.getChild("obsidian_4_tile_03");
        this.obsidian_4_tile_04 = this.obsidian_4_bone.getChild("obsidian_4_tile_04");
        this.obsidian_4_tile_05 = this.obsidian_4_bone.getChild("obsidian_4_tile_05");
        this.obsidian_4_tile_06 = this.obsidian_4_bone.getChild("obsidian_4_tile_06");
        this.obsidian_4_tile_07 = this.obsidian_4_bone.getChild("obsidian_4_tile_07");
        this.obsidian_4_tile_08 = this.obsidian_4_bone.getChild("obsidian_4_tile_08");
        this.obsidian_4_tile_09 = this.obsidian_4_bone.getChild("obsidian_4_tile_09");
        this.obsidian_4_tile_10 = this.obsidian_4_bone.getChild("obsidian_4_tile_10");
        this.obsidian_4_tile_11 = this.obsidian_4_bone.getChild("obsidian_4_tile_11");
        this.obsidian_4_tile_12 = this.obsidian_4_bone.getChild("obsidian_4_tile_12");
        this.obsidian_4_tile_13 = this.obsidian_4_bone.getChild("obsidian_4_tile_13");
        this.obsidian_4_tile_14 = this.obsidian_4_bone.getChild("obsidian_4_tile_14");
        this.obsidian_4_tile_15 = this.obsidian_4_bone.getChild("obsidian_4_tile_15");
        this.obsidian_4_tile_16 = this.obsidian_4_bone.getChild("obsidian_4_tile_16");
        this.obsidian_4_tile_17 = this.obsidian_4_bone.getChild("obsidian_4_tile_17");
        this.obsidian_4_tile_18 = this.obsidian_4_bone.getChild("obsidian_4_tile_18");
        this.obsidian_4_tile_19 = this.obsidian_4_bone.getChild("obsidian_4_tile_19");
        this.obsidian_4_tile_20 = this.obsidian_4_bone.getChild("obsidian_4_tile_20");
        this.obsidian_4_tile_21 = this.obsidian_4_bone.getChild("obsidian_4_tile_21");
        this.obsidian_4_tile_22 = this.obsidian_4_bone.getChild("obsidian_4_tile_22");
        this.obsidian_4_tile_23 = this.obsidian_4_bone.getChild("obsidian_4_tile_23");
        this.obsidian_4_tile_24 = this.obsidian_4_bone.getChild("obsidian_4_tile_24");
        this.cube_r5 = this.Body.getChild("cube_r5");
        this.cube_r6 = this.Body.getChild("cube_r6");
        this.obsidian_6_bone = this.cube_r6.getChild("obsidian_6_bone");
        this.obsidian_6_tile_01 = this.obsidian_6_bone.getChild("obsidian_6_tile_01");
        this.obsidian_6_tile_02 = this.obsidian_6_bone.getChild("obsidian_6_tile_02");
        this.obsidian_6_tile_03 = this.obsidian_6_bone.getChild("obsidian_6_tile_03");
        this.obsidian_6_tile_04 = this.obsidian_6_bone.getChild("obsidian_6_tile_04");
        this.obsidian_6_tile_05 = this.obsidian_6_bone.getChild("obsidian_6_tile_05");
        this.obsidian_6_tile_06 = this.obsidian_6_bone.getChild("obsidian_6_tile_06");
        this.obsidian_6_tile_07 = this.obsidian_6_bone.getChild("obsidian_6_tile_07");
        this.obsidian_6_tile_08 = this.obsidian_6_bone.getChild("obsidian_6_tile_08");
        this.obsidian_6_tile_09 = this.obsidian_6_bone.getChild("obsidian_6_tile_09");
        this.obsidian_6_tile_10 = this.obsidian_6_bone.getChild("obsidian_6_tile_10");
        this.obsidian_6_tile_11 = this.obsidian_6_bone.getChild("obsidian_6_tile_11");
        this.obsidian_6_tile_12 = this.obsidian_6_bone.getChild("obsidian_6_tile_12");
        this.obsidian_6_tile_13 = this.obsidian_6_bone.getChild("obsidian_6_tile_13");
        this.obsidian_6_tile_14 = this.obsidian_6_bone.getChild("obsidian_6_tile_14");
        this.obsidian_6_tile_15 = this.obsidian_6_bone.getChild("obsidian_6_tile_15");
        this.obsidian_6_tile_16 = this.obsidian_6_bone.getChild("obsidian_6_tile_16");
        this.obsidian_6_tile_17 = this.obsidian_6_bone.getChild("obsidian_6_tile_17");
        this.obsidian_6_tile_18 = this.obsidian_6_bone.getChild("obsidian_6_tile_18");
        this.obsidian_6_tile_19 = this.obsidian_6_bone.getChild("obsidian_6_tile_19");
        this.obsidian_6_tile_20 = this.obsidian_6_bone.getChild("obsidian_6_tile_20");
        this.obsidian_6_tile_21 = this.obsidian_6_bone.getChild("obsidian_6_tile_21");
        this.obsidian_6_tile_22 = this.obsidian_6_bone.getChild("obsidian_6_tile_22");
        this.obsidian_6_tile_23 = this.obsidian_6_bone.getChild("obsidian_6_tile_23");
        this.obsidian_6_tile_24 = this.obsidian_6_bone.getChild("obsidian_6_tile_24");
        this.obsidian_6_tile_25 = this.obsidian_6_bone.getChild("obsidian_6_tile_25");
        this.obsidian_6_tile_26 = this.obsidian_6_bone.getChild("obsidian_6_tile_26");
        this.obsidian_6_tile_27 = this.obsidian_6_bone.getChild("obsidian_6_tile_27");
        this.obsidian_6_tile_28 = this.obsidian_6_bone.getChild("obsidian_6_tile_28");
        this.obsidian_6_tile_29 = this.obsidian_6_bone.getChild("obsidian_6_tile_29");
        this.obsidian_6_tile_30 = this.obsidian_6_bone.getChild("obsidian_6_tile_30");
        this.RightArm = root.getChild("RightArm");
        this.cube_r7 = this.RightArm.getChild("cube_r7");
        this.obsidian_7_bone = this.cube_r7.getChild("obsidian_7_bone");
        this.obsidian_7_tile_01 = this.obsidian_7_bone.getChild("obsidian_7_tile_01");
        this.obsidian_7_tile_02 = this.obsidian_7_bone.getChild("obsidian_7_tile_02");
        this.obsidian_7_tile_03 = this.obsidian_7_bone.getChild("obsidian_7_tile_03");
        this.obsidian_7_tile_04 = this.obsidian_7_bone.getChild("obsidian_7_tile_04");
        this.obsidian_7_tile_05 = this.obsidian_7_bone.getChild("obsidian_7_tile_05");
        this.obsidian_7_tile_06 = this.obsidian_7_bone.getChild("obsidian_7_tile_06");
        this.obsidian_7_tile_07 = this.obsidian_7_bone.getChild("obsidian_7_tile_07");
        this.obsidian_7_tile_08 = this.obsidian_7_bone.getChild("obsidian_7_tile_08");
        this.obsidian_7_tile_09 = this.obsidian_7_bone.getChild("obsidian_7_tile_09");
        this.obsidian_7_tile_10 = this.obsidian_7_bone.getChild("obsidian_7_tile_10");
        this.obsidian_7_tile_11 = this.obsidian_7_bone.getChild("obsidian_7_tile_11");
        this.obsidian_7_tile_12 = this.obsidian_7_bone.getChild("obsidian_7_tile_12");
        this.obsidian_7_tile_13 = this.obsidian_7_bone.getChild("obsidian_7_tile_13");
        this.obsidian_7_tile_14 = this.obsidian_7_bone.getChild("obsidian_7_tile_14");
        this.obsidian_7_tile_15 = this.obsidian_7_bone.getChild("obsidian_7_tile_15");
        this.obsidian_7_tile_16 = this.obsidian_7_bone.getChild("obsidian_7_tile_16");
        this.obsidian_7_tile_17 = this.obsidian_7_bone.getChild("obsidian_7_tile_17");
        this.obsidian_7_tile_18 = this.obsidian_7_bone.getChild("obsidian_7_tile_18");
        this.obsidian_7_tile_19 = this.obsidian_7_bone.getChild("obsidian_7_tile_19");
        this.obsidian_7_tile_20 = this.obsidian_7_bone.getChild("obsidian_7_tile_20");
        this.obsidian_7_tile_21 = this.obsidian_7_bone.getChild("obsidian_7_tile_21");
        this.obsidian_7_tile_22 = this.obsidian_7_bone.getChild("obsidian_7_tile_22");
        this.obsidian_7_tile_23 = this.obsidian_7_bone.getChild("obsidian_7_tile_23");
        this.obsidian_7_tile_24 = this.obsidian_7_bone.getChild("obsidian_7_tile_24");
        this.cube_r8 = this.RightArm.getChild("cube_r8");
        this.obsidian_8_bone = this.cube_r8.getChild("obsidian_8_bone");
        this.obsidian_8_tile_01 = this.obsidian_8_bone.getChild("obsidian_8_tile_01");
        this.obsidian_8_tile_02 = this.obsidian_8_bone.getChild("obsidian_8_tile_02");
        this.obsidian_8_tile_03 = this.obsidian_8_bone.getChild("obsidian_8_tile_03");
        this.obsidian_8_tile_04 = this.obsidian_8_bone.getChild("obsidian_8_tile_04");
        this.obsidian_8_tile_05 = this.obsidian_8_bone.getChild("obsidian_8_tile_05");
        this.obsidian_8_tile_06 = this.obsidian_8_bone.getChild("obsidian_8_tile_06");
        this.obsidian_8_tile_07 = this.obsidian_8_bone.getChild("obsidian_8_tile_07");
        this.obsidian_8_tile_08 = this.obsidian_8_bone.getChild("obsidian_8_tile_08");
        this.obsidian_8_tile_09 = this.obsidian_8_bone.getChild("obsidian_8_tile_09");
        this.obsidian_8_tile_10 = this.obsidian_8_bone.getChild("obsidian_8_tile_10");
        this.obsidian_8_tile_11 = this.obsidian_8_bone.getChild("obsidian_8_tile_11");
        this.obsidian_8_tile_12 = this.obsidian_8_bone.getChild("obsidian_8_tile_12");
        this.obsidian_8_tile_13 = this.obsidian_8_bone.getChild("obsidian_8_tile_13");
        this.obsidian_8_tile_14 = this.obsidian_8_bone.getChild("obsidian_8_tile_14");
        this.obsidian_8_tile_15 = this.obsidian_8_bone.getChild("obsidian_8_tile_15");
        this.obsidian_8_tile_16 = this.obsidian_8_bone.getChild("obsidian_8_tile_16");
        this.obsidian_8_tile_17 = this.obsidian_8_bone.getChild("obsidian_8_tile_17");
        this.obsidian_8_tile_18 = this.obsidian_8_bone.getChild("obsidian_8_tile_18");
        this.obsidian_8_tile_19 = this.obsidian_8_bone.getChild("obsidian_8_tile_19");
        this.obsidian_8_tile_20 = this.obsidian_8_bone.getChild("obsidian_8_tile_20");
        this.cube_r9 = this.RightArm.getChild("cube_r9");
        this.obsidian_9_bone = this.cube_r9.getChild("obsidian_9_bone");
        this.obsidian_9_tile_01 = this.obsidian_9_bone.getChild("obsidian_9_tile_01");
        this.obsidian_9_tile_02 = this.obsidian_9_bone.getChild("obsidian_9_tile_02");
        this.obsidian_9_tile_03 = this.obsidian_9_bone.getChild("obsidian_9_tile_03");
        this.obsidian_9_tile_04 = this.obsidian_9_bone.getChild("obsidian_9_tile_04");
        this.obsidian_9_tile_05 = this.obsidian_9_bone.getChild("obsidian_9_tile_05");
        this.obsidian_9_tile_06 = this.obsidian_9_bone.getChild("obsidian_9_tile_06");
        this.obsidian_9_tile_07 = this.obsidian_9_bone.getChild("obsidian_9_tile_07");
        this.obsidian_9_tile_08 = this.obsidian_9_bone.getChild("obsidian_9_tile_08");
        this.obsidian_9_tile_09 = this.obsidian_9_bone.getChild("obsidian_9_tile_09");
        this.obsidian_9_tile_10 = this.obsidian_9_bone.getChild("obsidian_9_tile_10");
        this.obsidian_9_tile_11 = this.obsidian_9_bone.getChild("obsidian_9_tile_11");
        this.obsidian_9_tile_12 = this.obsidian_9_bone.getChild("obsidian_9_tile_12");
        this.obsidian_9_tile_13 = this.obsidian_9_bone.getChild("obsidian_9_tile_13");
        this.obsidian_9_tile_14 = this.obsidian_9_bone.getChild("obsidian_9_tile_14");
        this.obsidian_9_tile_15 = this.obsidian_9_bone.getChild("obsidian_9_tile_15");
        this.obsidian_9_tile_16 = this.obsidian_9_bone.getChild("obsidian_9_tile_16");
        this.obsidian_9_tile_17 = this.obsidian_9_bone.getChild("obsidian_9_tile_17");
        this.obsidian_9_tile_18 = this.obsidian_9_bone.getChild("obsidian_9_tile_18");
        this.obsidian_9_tile_19 = this.obsidian_9_bone.getChild("obsidian_9_tile_19");
        this.obsidian_9_tile_20 = this.obsidian_9_bone.getChild("obsidian_9_tile_20");
        this.obsidian_9_tile_21 = this.obsidian_9_bone.getChild("obsidian_9_tile_21");
        this.obsidian_9_tile_22 = this.obsidian_9_bone.getChild("obsidian_9_tile_22");
        this.obsidian_9_tile_23 = this.obsidian_9_bone.getChild("obsidian_9_tile_23");
        this.obsidian_9_tile_24 = this.obsidian_9_bone.getChild("obsidian_9_tile_24");
        this.LeftArm = root.getChild("LeftArm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_0_bone = Body.addOrReplaceChild("obsidian_0_bone", CubeListBuilder.create().texOffs(54, 7).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.2385F, 2.609F, 1.8F));
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
        PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, -1.4835F, 0.0873F, 0.0F));
        PartDefinition obsidian_1_bone = cube_r1.addOrReplaceChild("obsidian_1_bone", CubeListBuilder.create().texOffs(51, 4).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.909F, -1.2615F, 17.3F));
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
        PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 1.6552F, 0.3049F, -0.0119F));
        PartDefinition obsidian_2_bone = cube_r2.addOrReplaceChild("obsidian_2_bone", CubeListBuilder.create().texOffs(51, 4).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.909F, -2.2615F, -19.7F));
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
        PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 1.4835F, 0.1745F, 0.0F));
        PartDefinition obsidian_3_bone = cube_r3.addOrReplaceChild("obsidian_3_bone", CubeListBuilder.create().texOffs(56, 2).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-0.909F, 0.7385F, -17.7F));
        PartDefinition obsidian_3_tile_01 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_02 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_03 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_04 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_05 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_06 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_07 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_08 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_09 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_10 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_11 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_12 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_13 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_14 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_15 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_16 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_17 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_3_tile_18 = obsidian_3_bone.addOrReplaceChild("obsidian_3_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r4 = Body.addOrReplaceChild("cube_r4", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0F, -15.0F, -10.0F, 0.0F, -1.9199F, 0.0F));
        PartDefinition obsidian_4_bone = cube_r4.addOrReplaceChild("obsidian_4_bone", CubeListBuilder.create().texOffs(51, 4).addBox(0.0F, -1.05F, -0.95F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offset(5.4F, 20.25F, 5.05F));
        PartDefinition obsidian_4_tile_01 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_02 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_03 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_04 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_05 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_06 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_07 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_08 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_09 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_10 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_11 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_12 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_13 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_14 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_15 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_16 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_17 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_18 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_19 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_20 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_21 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_22 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_22", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_23 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_23", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_4_tile_24 = obsidian_4_bone.addOrReplaceChild("obsidian_4_tile_24", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r5 = Body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(51, 4).addBox(11.4F, 18.2F, 9.1F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -15.0F, -10.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition cube_r6 = Body.addOrReplaceChild("cube_r6", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -15.0F, -2.0F, 0.0F, -0.0873F, 0.0F));
        PartDefinition obsidian_6_bone = cube_r6.addOrReplaceChild("obsidian_6_bone", CubeListBuilder.create().texOffs(52, 1).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(-3.2385F, 20.609F, 3.8F));
        PartDefinition obsidian_6_tile_01 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_02 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_03 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_04 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_05 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_06 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_07 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_08 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_09 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_10 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_11 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_12 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_13 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_14 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_15 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_16 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_17 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_18 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_19 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_20 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_21 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_22 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_22", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_23 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_23", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_24 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_24", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_25 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_25", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_26 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_26", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_27 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_27", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_28 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_28", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_29 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_29", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_6_tile_30 = obsidian_6_bone.addOrReplaceChild("obsidian_6_tile_30", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)).texOffs(54, 7).addBox(-3.2385F, -0.391F, 1.8F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));
        PartDefinition cube_r7 = RightArm.addOrReplaceChild("cube_r7", CubeListBuilder.create(), PartPose.offsetAndRotation(15.0F, -17.0F, -10.0F, 2.1639F, 1.4137F, 2.1562F));
        PartDefinition obsidian_7_bone = cube_r7.addOrReplaceChild("obsidian_7_bone", CubeListBuilder.create().texOffs(51, 4).addBox(-5.0F, -1.05F, -0.95F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offset(-7.6F, 17.25F, -17.95F));
        PartDefinition obsidian_7_tile_01 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_02 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_03 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_04 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_05 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_06 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_07 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_08 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_09 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_10 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_11 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_12 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_13 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_14 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_15 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_16 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_17 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_18 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_19 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_20 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_21 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_22 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_22", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_23 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_23", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_7_tile_24 = obsidian_7_bone.addOrReplaceChild("obsidian_7_tile_24", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r8 = RightArm.addOrReplaceChild("cube_r8", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -17.0F, -2.0F, 1.7301F, 0.3982F, -0.0424F));
        PartDefinition obsidian_8_bone = cube_r8.addOrReplaceChild("obsidian_8_bone", CubeListBuilder.create().texOffs(51, 4).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.909F, -6.2615F, -15.7F));
        PartDefinition obsidian_8_tile_01 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_02 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_03 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_04 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_05 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_06 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_07 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_08 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_09 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_10 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_11 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_12 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_13 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_14 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_15 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_16 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_17 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_18 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_19 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_8_tile_20 = obsidian_8_bone.addOrReplaceChild("obsidian_8_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cube_r9 = RightArm.addOrReplaceChild("cube_r9", CubeListBuilder.create(), PartPose.offsetAndRotation(15.0F, -17.0F, -10.0F, -0.81F, -1.2543F, 0.9694F));
        PartDefinition obsidian_9_bone = cube_r9.addOrReplaceChild("obsidian_9_bone", CubeListBuilder.create().texOffs(49, 10).addBox(0.0F, -1.05F, -0.95F, 5.0F, 2.1F, 1.9F, new CubeDeformation(0.0F)), PartPose.offset(4.4F, 15.25F, 16.05F));
        PartDefinition obsidian_9_tile_01 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_01", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_02 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_02", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_03 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_03", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_04 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_04", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_05 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_05", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_06 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_06", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_07 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_07", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_08 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_08", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_09 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_09", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_10 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_10", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_11 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_11", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_12 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_12", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_13 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_13", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_14 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_14", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_15 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_15", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_16 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_16", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_17 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_18 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_18", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_19 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_19", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_20 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_20", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_21 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_21", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_22 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_22", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_23 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_23", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition obsidian_9_tile_24 = obsidian_9_bone.addOrReplaceChild("obsidian_9_tile_24", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public ModelPart root() {
        return this.modelRoot;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.RightArm.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
        this.LeftArm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.modelRoot.render(poseStack, buffer, packedLight, packedOverlay, color);
        renderRandomizedUvTiles(poseStack, buffer, packedLight, packedOverlay, color);
    }

    /** Draw only the animated tiles; the armor shell is rendered by the owning backend. */
    public void renderExtensionTiles(boolean rightArm, PoseStack poseStack, VertexConsumer buffer,
                                     int packedLight, int packedOverlay) {
        if (!rightArm) {
            renderObsidian0Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
            renderObsidian1Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
            renderObsidian2Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
            renderObsidian3Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
            renderObsidian4Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
            renderObsidian6Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
        }
        if (rightArm) {
            renderObsidian7Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
            renderObsidian8Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
            renderObsidian9Tiles(poseStack, buffer, packedLight, packedOverlay, -1);
        }
    }

    private void renderRandomizedUvTiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        renderObsidian0Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian1Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian2Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian3Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian4Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian6Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian7Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian8Tiles(poseStack, buffer, packedLight, packedOverlay, color);
        renderObsidian9Tiles(poseStack, buffer, packedLight, packedOverlay, color);
    }

    private void renderObsidian0Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_0_tile_01.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 270, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_02.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 270, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_03.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 270, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 180, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_04.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 270, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_05.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 180, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 270, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 180, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_06.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 270, 62.0F, 10.0F, 64.0F, 12.0F, 270, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_07.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 180, 59.0F, 10.0F, 62.0F, 12.0F, 0, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_08.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 180, 59.0F, 10.0F, 62.0F, 12.0F, 270, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_09.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 90, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_10.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 90, 62.0F, 10.0F, 64.0F, 12.0F, 180, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_11.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 270, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_12.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 270, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 0, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_13.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_14.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 0, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_15.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 270, 62.0F, 10.0F, 64.0F, 12.0F, 180, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_16.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 270, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_17.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_18.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 0, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_19.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_20.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_21.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_21.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 180, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 180, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_22.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_22.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 270, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_23.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_23.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 180, 54.0F, 10.0F, 57.0F, 12.0F, 270, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 0, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_24.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_24.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 90, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_25.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_25.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 270, 54.0F, 10.0F, 57.0F, 12.0F, 90, 62.0F, 10.0F, 64.0F, 12.0F, 270, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 180, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_26.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_26.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 90, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 270, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_27.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_27.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 270, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_28.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_28.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_29.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_29.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 270, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 270, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_30.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_30.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 180, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_31.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_31.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 270, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 180, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_32.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_32.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 270, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_33.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_33.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 90, 54.0F, 10.0F, 57.0F, 12.0F, 90, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 0, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_34.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_34.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 270, 62.0F, 10.0F, 64.0F, 12.0F, 90, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_35.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_35.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 180, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 180, 59.0F, 10.0F, 57.0F, 7.0F, 180, 61.0F, 7.0F, 59.0F, 10.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_36.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_36.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 270, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_37.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_37.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 270, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 270, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_38.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_38.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 180, 54.0F, 10.0F, 57.0F, 12.0F, 180, 62.0F, 10.0F, 64.0F, 12.0F, 0, 59.0F, 10.0F, 62.0F, 12.0F, 90, 59.0F, 10.0F, 57.0F, 7.0F, 0, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_39.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_39.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 180, 54.0F, 10.0F, 57.0F, 12.0F, 0, 62.0F, 10.0F, 64.0F, 12.0F, 180, 59.0F, 10.0F, 62.0F, 12.0F, 270, 59.0F, 10.0F, 57.0F, 7.0F, 90, 61.0F, 7.0F, 59.0F, 10.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_0_tile_40.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.obsidian_0_bone.translateAndRotate(poseStack);
            this.obsidian_0_tile_40.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, 57.0F, 10.0F, 59.0F, 12.0F, 0, 54.0F, 10.0F, 57.0F, 12.0F, 90, 62.0F, 10.0F, 64.0F, 12.0F, 180, 59.0F, 10.0F, 62.0F, 12.0F, 0, 59.0F, 10.0F, 57.0F, 7.0F, 270, 61.0F, 7.0F, 59.0F, 10.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian1Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_1_tile_01.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_02.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 180, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_03.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_04.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_05.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_06.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_07.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 270, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_08.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_09.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_10.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 90, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_11.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 90, 55.0F, 6.0F, 57.0F, 12.0F, 90, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_12.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_13.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 270, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_14.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 270, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_15.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 180, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_16.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 90, 57.0F, 6.0F, 59.0F, 12.0F, 270, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_17.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_18.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 90, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_19.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_1_tile_20.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r1.translateAndRotate(poseStack);
            this.obsidian_1_bone.translateAndRotate(poseStack);
            this.obsidian_1_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 180, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian2Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_2_tile_01.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_02.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_03.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_04.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_05.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_06.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_07.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 90, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_08.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_09.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_10.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 270, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_11.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 180, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_12.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 270, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_13.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_14.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_15.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 180, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_16.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 270, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_17.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 90, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_18.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 270, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_19.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_2_tile_20.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r2.translateAndRotate(poseStack);
            this.obsidian_2_bone.translateAndRotate(poseStack);
            this.obsidian_2_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian3Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_3_tile_01.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 180, 56.0F, 4.0F, 58.0F, 11.0F, 180, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 0, 60.0F, 4.0F, 58.0F, 2.0F, 90, 62.0F, 2.0F, 60.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_02.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 90, 62.0F, 2.0F, 60.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_03.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 0, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 180, 62.0F, 2.0F, 60.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_04.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 0, 60.0F, 4.0F, 58.0F, 2.0F, 180, 62.0F, 2.0F, 60.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_05.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 180, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 0, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 270, 62.0F, 2.0F, 60.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_06.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 180, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 0, 60.0F, 4.0F, 62.0F, 11.0F, 270, 60.0F, 4.0F, 58.0F, 2.0F, 180, 62.0F, 2.0F, 60.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_07.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 0, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 90, 62.0F, 2.0F, 60.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_08.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 180, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 0, 60.0F, 4.0F, 58.0F, 2.0F, 90, 62.0F, 2.0F, 60.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_09.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 270, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 0, 60.0F, 4.0F, 62.0F, 11.0F, 270, 60.0F, 4.0F, 58.0F, 2.0F, 270, 62.0F, 2.0F, 60.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_10.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 180, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 0, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 180, 62.0F, 2.0F, 60.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_11.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 90, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 0, 60.0F, 4.0F, 58.0F, 2.0F, 90, 62.0F, 2.0F, 60.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_12.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 270, 60.0F, 4.0F, 62.0F, 11.0F, 270, 60.0F, 4.0F, 58.0F, 2.0F, 90, 62.0F, 2.0F, 60.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_13.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 90, 62.0F, 2.0F, 60.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_14.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 270, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 270, 60.0F, 4.0F, 62.0F, 11.0F, 0, 60.0F, 4.0F, 58.0F, 2.0F, 180, 62.0F, 2.0F, 60.0F, 4.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_15.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 180, 56.0F, 4.0F, 58.0F, 11.0F, 270, 62.0F, 4.0F, 64.0F, 11.0F, 0, 60.0F, 4.0F, 62.0F, 11.0F, 270, 60.0F, 4.0F, 58.0F, 2.0F, 180, 62.0F, 2.0F, 60.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_16.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 180, 56.0F, 4.0F, 58.0F, 11.0F, 90, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 180, 62.0F, 2.0F, 60.0F, 4.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_17.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 0, 56.0F, 4.0F, 58.0F, 11.0F, 180, 62.0F, 4.0F, 64.0F, 11.0F, 180, 60.0F, 4.0F, 62.0F, 11.0F, 270, 60.0F, 4.0F, 58.0F, 2.0F, 0, 62.0F, 2.0F, 60.0F, 4.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_3_tile_18.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r3.translateAndRotate(poseStack);
            this.obsidian_3_bone.translateAndRotate(poseStack);
            this.obsidian_3_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -6.9F, -0.9F, 0.9F, -0.1F, 0.9F, 58.0F, 4.0F, 60.0F, 11.0F, 180, 56.0F, 4.0F, 58.0F, 11.0F, 0, 62.0F, 4.0F, 64.0F, 11.0F, 90, 60.0F, 4.0F, 62.0F, 11.0F, 180, 60.0F, 4.0F, 58.0F, 2.0F, 270, 62.0F, 2.0F, 60.0F, 4.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian4Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_4_tile_01.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_02.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_03.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_04.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_05.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_06.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_07.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_08.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_09.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 270, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_10.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 270, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_11.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 270, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_12.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_13.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_14.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 90, 59.8F, 5.9F, 64.8F, 8.0F, 90, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_15.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 270, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_16.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_17.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_18.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_19.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 270, 51.0F, 5.9F, 52.9F, 8.0F, 270, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_20.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 270, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_21.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_21.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 90, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_22.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_22.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 270, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_23.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_23.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 90, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_4_tile_24.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r4.translateAndRotate(poseStack);
            this.obsidian_4_bone.translateAndRotate(poseStack);
            this.obsidian_4_tile_24.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 90, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 90, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian6Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_6_tile_01.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 270, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_02.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 270, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 90, 60.0F, 1.0F, 58.0F, 5.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_03.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 0, 60.0F, 1.0F, 58.0F, 5.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_04.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_05.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 0, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_06.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 270, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 0, 60.0F, 1.0F, 58.0F, 5.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_07.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 180, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 0, 58.0F, 5.0F, 62.0F, 7.0F, 90, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_08.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 270, 52.0F, 5.0F, 56.0F, 7.0F, 90, 62.0F, 5.0F, 64.0F, 7.0F, 0, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 90, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_09.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 180, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 0, 58.0F, 5.0F, 62.0F, 7.0F, 90, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_10.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 90, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 270, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_11.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 90, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 90, 58.0F, 5.0F, 56.0F, 1.0F, 0, 60.0F, 1.0F, 58.0F, 5.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_12.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 90, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_13.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 0, 60.0F, 1.0F, 58.0F, 5.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_14.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_15.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 90, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 270, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_16.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 270, 62.0F, 5.0F, 64.0F, 7.0F, 0, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 270, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_17.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 270, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 270, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_18.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 90, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_19.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_20.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 180, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 0, 58.0F, 5.0F, 62.0F, 7.0F, 270, 58.0F, 5.0F, 56.0F, 1.0F, 270, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_21.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_21.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 180, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 0, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 0, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_22.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_22.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 90, 62.0F, 5.0F, 64.0F, 7.0F, 270, 58.0F, 5.0F, 62.0F, 7.0F, 270, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_23.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_23.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 180, 52.0F, 5.0F, 56.0F, 7.0F, 270, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_24.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_24.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 0, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_25.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_25.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 90, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_26.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_26.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 270, 52.0F, 5.0F, 56.0F, 7.0F, 270, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 0, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_27.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_27.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 180, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_28.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_28.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 270, 62.0F, 5.0F, 64.0F, 7.0F, 270, 58.0F, 5.0F, 62.0F, 7.0F, 270, 58.0F, 5.0F, 56.0F, 1.0F, 90, 60.0F, 1.0F, 58.0F, 5.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_29.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_29.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 90, 52.0F, 5.0F, 56.0F, 7.0F, 0, 62.0F, 5.0F, 64.0F, 7.0F, 180, 58.0F, 5.0F, 62.0F, 7.0F, 270, 58.0F, 5.0F, 56.0F, 1.0F, 180, 60.0F, 1.0F, 58.0F, 5.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_6_tile_30.visible) {
            poseStack.pushPose();
            this.Body.translateAndRotate(poseStack);
            this.cube_r6.translateAndRotate(poseStack);
            this.obsidian_6_bone.translateAndRotate(poseStack);
            this.obsidian_6_tile_30.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -0.9F, -0.9F, 0.1F, 0.9F, 0.9F, 3.9F, 56.0F, 5.0F, 58.0F, 7.0F, 0, 52.0F, 5.0F, 56.0F, 7.0F, 180, 62.0F, 5.0F, 64.0F, 7.0F, 90, 58.0F, 5.0F, 62.0F, 7.0F, 270, 58.0F, 5.0F, 56.0F, 1.0F, 0, 60.0F, 1.0F, 58.0F, 5.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian7Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_7_tile_01.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_02.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_03.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_04.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_05.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_06.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_07.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_08.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_09.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 90, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_10.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 270, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_11.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 90, 57.9F, 5.9F, 52.9F, 4.0F, 270, 62.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_12.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 90, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_13.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_14.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 270, 51.0F, 5.9F, 52.9F, 8.0F, 270, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 270, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_15.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 270, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_16.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_17.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 270, 62.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_18.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 270, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_19.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 90, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_20.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 0, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 0, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_21.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_21.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 180, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_22.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_22.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 90, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 0, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_23.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_23.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 180, 51.0F, 5.9F, 52.9F, 8.0F, 90, 59.8F, 5.9F, 64.8F, 8.0F, 180, 57.9F, 5.9F, 59.8F, 8.0F, 270, 57.9F, 5.9F, 52.9F, 4.0F, 180, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_7_tile_24.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r7.translateAndRotate(poseStack);
            this.obsidian_7_bone.translateAndRotate(poseStack);
            this.obsidian_7_tile_24.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -5.0F, -1.05F, -0.95F, 0.0F, 1.05F, 0.95F, 52.9F, 5.9F, 57.9F, 8.0F, 0, 51.0F, 5.9F, 52.9F, 8.0F, 180, 59.8F, 5.9F, 64.8F, 8.0F, 0, 57.9F, 5.9F, 59.8F, 8.0F, 90, 57.9F, 5.9F, 52.9F, 4.0F, 90, 62.9F, 4.0F, 57.9F, 5.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian8Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_8_tile_01.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_02.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 180, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_03.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_04.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_05.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_06.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_07.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_08.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_09.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 270, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 90, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_10.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 90, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_11.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 270, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_12.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_13.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 90, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_14.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 90, 51.0F, 6.0F, 53.0F, 12.0F, 270, 57.0F, 6.0F, 59.0F, 12.0F, 270, 55.0F, 6.0F, 57.0F, 12.0F, 270, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_15.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 270, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_16.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 0, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 0, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_17.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 90, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 90, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 90, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_18.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 270, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 180, 55.0F, 6.0F, 53.0F, 4.0F, 90, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_19.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 0, 51.0F, 6.0F, 53.0F, 12.0F, 90, 57.0F, 6.0F, 59.0F, 12.0F, 180, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_8_tile_20.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r8.translateAndRotate(poseStack);
            this.obsidian_8_bone.translateAndRotate(poseStack);
            this.obsidian_8_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, -1.0F, -6.0F, -1.0F, 1.0F, 0.0F, 1.0F, 53.0F, 6.0F, 55.0F, 12.0F, 180, 51.0F, 6.0F, 53.0F, 12.0F, 180, 57.0F, 6.0F, 59.0F, 12.0F, 0, 55.0F, 6.0F, 57.0F, 12.0F, 0, 55.0F, 6.0F, 53.0F, 4.0F, 270, 57.0F, 4.0F, 55.0F, 6.0F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
    }

    private void renderObsidian9Tiles(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        if (this.obsidian_9_tile_01.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_01.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_02.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_02.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_03.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_03.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 0, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 0, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_04.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_04.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 0, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_05.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_05.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 180, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 0, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_06.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_06.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 0, 49.0F, 11.9F, 50.9F, 14.0F, 180, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 0, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_07.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_07.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 0, 49.0F, 11.9F, 50.9F, 14.0F, 180, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_08.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_08.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 0, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_09.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_09.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 0, 49.0F, 11.9F, 50.9F, 14.0F, 90, 57.8F, 11.9F, 62.8F, 14.0F, 90, 55.9F, 11.9F, 57.8F, 14.0F, 270, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_10.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_10.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 270, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 0, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_11.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_11.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 90, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_12.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_12.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 180, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 270, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_13.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_13.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 0, 49.0F, 11.9F, 50.9F, 14.0F, 270, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_14.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_14.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 90, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_15.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_15.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 0, 49.0F, 11.9F, 50.9F, 14.0F, 180, 57.8F, 11.9F, 62.8F, 14.0F, 90, 55.9F, 11.9F, 57.8F, 14.0F, 270, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_16.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_16.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 90, 49.0F, 11.9F, 50.9F, 14.0F, 90, 57.8F, 11.9F, 62.8F, 14.0F, 0, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_17.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_17.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 270, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_18.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_18.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_19.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_19.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 90, 49.0F, 11.9F, 50.9F, 14.0F, 270, 57.8F, 11.9F, 62.8F, 14.0F, 180, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_20.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_20.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 0, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 270, 60.9F, 10.0F, 55.9F, 11.9F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_21.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_21.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 270, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 0, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_22.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_22.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 270, 57.8F, 11.9F, 62.8F, 14.0F, 0, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 180, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_23.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_23.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 180, 49.0F, 11.9F, 50.9F, 14.0F, 0, 57.8F, 11.9F, 62.8F, 14.0F, 270, 55.9F, 11.9F, 57.8F, 14.0F, 180, 55.9F, 11.9F, 50.9F, 10.0F, 90, 60.9F, 10.0F, 55.9F, 11.9F, 270, 64.0F, 32.0F);
            poseStack.popPose();
        }
        if (this.obsidian_9_tile_24.visible) {
            poseStack.pushPose();
            this.RightArm.translateAndRotate(poseStack);
            this.cube_r9.translateAndRotate(poseStack);
            this.obsidian_9_bone.translateAndRotate(poseStack);
            this.obsidian_9_tile_24.translateAndRotate(poseStack);
            PerFaceUvCubeRenderer.renderBox(poseStack, buffer, packedLight, packedOverlay, color, 0.0F, -1.05F, -0.95F, 5.0F, 1.05F, 0.95F, 50.9F, 11.9F, 55.9F, 14.0F, 0, 49.0F, 11.9F, 50.9F, 14.0F, 180, 57.8F, 11.9F, 62.8F, 14.0F, 270, 55.9F, 11.9F, 57.8F, 14.0F, 90, 55.9F, 11.9F, 50.9F, 10.0F, 0, 60.9F, 10.0F, 55.9F, 11.9F, 180, 64.0F, 32.0F);
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
        this.obsidian_3_bone.resetPose();
        this.obsidian_3_tile_01.resetPose();
        this.obsidian_3_tile_02.resetPose();
        this.obsidian_3_tile_03.resetPose();
        this.obsidian_3_tile_04.resetPose();
        this.obsidian_3_tile_05.resetPose();
        this.obsidian_3_tile_06.resetPose();
        this.obsidian_3_tile_07.resetPose();
        this.obsidian_3_tile_08.resetPose();
        this.obsidian_3_tile_09.resetPose();
        this.obsidian_3_tile_10.resetPose();
        this.obsidian_3_tile_11.resetPose();
        this.obsidian_3_tile_12.resetPose();
        this.obsidian_3_tile_13.resetPose();
        this.obsidian_3_tile_14.resetPose();
        this.obsidian_3_tile_15.resetPose();
        this.obsidian_3_tile_16.resetPose();
        this.obsidian_3_tile_17.resetPose();
        this.obsidian_3_tile_18.resetPose();
        this.obsidian_4_bone.resetPose();
        this.obsidian_4_tile_01.resetPose();
        this.obsidian_4_tile_02.resetPose();
        this.obsidian_4_tile_03.resetPose();
        this.obsidian_4_tile_04.resetPose();
        this.obsidian_4_tile_05.resetPose();
        this.obsidian_4_tile_06.resetPose();
        this.obsidian_4_tile_07.resetPose();
        this.obsidian_4_tile_08.resetPose();
        this.obsidian_4_tile_09.resetPose();
        this.obsidian_4_tile_10.resetPose();
        this.obsidian_4_tile_11.resetPose();
        this.obsidian_4_tile_12.resetPose();
        this.obsidian_4_tile_13.resetPose();
        this.obsidian_4_tile_14.resetPose();
        this.obsidian_4_tile_15.resetPose();
        this.obsidian_4_tile_16.resetPose();
        this.obsidian_4_tile_17.resetPose();
        this.obsidian_4_tile_18.resetPose();
        this.obsidian_4_tile_19.resetPose();
        this.obsidian_4_tile_20.resetPose();
        this.obsidian_4_tile_21.resetPose();
        this.obsidian_4_tile_22.resetPose();
        this.obsidian_4_tile_23.resetPose();
        this.obsidian_4_tile_24.resetPose();
        this.obsidian_6_bone.resetPose();
        this.obsidian_6_tile_01.resetPose();
        this.obsidian_6_tile_02.resetPose();
        this.obsidian_6_tile_03.resetPose();
        this.obsidian_6_tile_04.resetPose();
        this.obsidian_6_tile_05.resetPose();
        this.obsidian_6_tile_06.resetPose();
        this.obsidian_6_tile_07.resetPose();
        this.obsidian_6_tile_08.resetPose();
        this.obsidian_6_tile_09.resetPose();
        this.obsidian_6_tile_10.resetPose();
        this.obsidian_6_tile_11.resetPose();
        this.obsidian_6_tile_12.resetPose();
        this.obsidian_6_tile_13.resetPose();
        this.obsidian_6_tile_14.resetPose();
        this.obsidian_6_tile_15.resetPose();
        this.obsidian_6_tile_16.resetPose();
        this.obsidian_6_tile_17.resetPose();
        this.obsidian_6_tile_18.resetPose();
        this.obsidian_6_tile_19.resetPose();
        this.obsidian_6_tile_20.resetPose();
        this.obsidian_6_tile_21.resetPose();
        this.obsidian_6_tile_22.resetPose();
        this.obsidian_6_tile_23.resetPose();
        this.obsidian_6_tile_24.resetPose();
        this.obsidian_6_tile_25.resetPose();
        this.obsidian_6_tile_26.resetPose();
        this.obsidian_6_tile_27.resetPose();
        this.obsidian_6_tile_28.resetPose();
        this.obsidian_6_tile_29.resetPose();
        this.obsidian_6_tile_30.resetPose();
        this.obsidian_7_bone.resetPose();
        this.obsidian_7_tile_01.resetPose();
        this.obsidian_7_tile_02.resetPose();
        this.obsidian_7_tile_03.resetPose();
        this.obsidian_7_tile_04.resetPose();
        this.obsidian_7_tile_05.resetPose();
        this.obsidian_7_tile_06.resetPose();
        this.obsidian_7_tile_07.resetPose();
        this.obsidian_7_tile_08.resetPose();
        this.obsidian_7_tile_09.resetPose();
        this.obsidian_7_tile_10.resetPose();
        this.obsidian_7_tile_11.resetPose();
        this.obsidian_7_tile_12.resetPose();
        this.obsidian_7_tile_13.resetPose();
        this.obsidian_7_tile_14.resetPose();
        this.obsidian_7_tile_15.resetPose();
        this.obsidian_7_tile_16.resetPose();
        this.obsidian_7_tile_17.resetPose();
        this.obsidian_7_tile_18.resetPose();
        this.obsidian_7_tile_19.resetPose();
        this.obsidian_7_tile_20.resetPose();
        this.obsidian_7_tile_21.resetPose();
        this.obsidian_7_tile_22.resetPose();
        this.obsidian_7_tile_23.resetPose();
        this.obsidian_7_tile_24.resetPose();
        this.obsidian_8_bone.resetPose();
        this.obsidian_8_tile_01.resetPose();
        this.obsidian_8_tile_02.resetPose();
        this.obsidian_8_tile_03.resetPose();
        this.obsidian_8_tile_04.resetPose();
        this.obsidian_8_tile_05.resetPose();
        this.obsidian_8_tile_06.resetPose();
        this.obsidian_8_tile_07.resetPose();
        this.obsidian_8_tile_08.resetPose();
        this.obsidian_8_tile_09.resetPose();
        this.obsidian_8_tile_10.resetPose();
        this.obsidian_8_tile_11.resetPose();
        this.obsidian_8_tile_12.resetPose();
        this.obsidian_8_tile_13.resetPose();
        this.obsidian_8_tile_14.resetPose();
        this.obsidian_8_tile_15.resetPose();
        this.obsidian_8_tile_16.resetPose();
        this.obsidian_8_tile_17.resetPose();
        this.obsidian_8_tile_18.resetPose();
        this.obsidian_8_tile_19.resetPose();
        this.obsidian_8_tile_20.resetPose();
        this.obsidian_9_bone.resetPose();
        this.obsidian_9_tile_01.resetPose();
        this.obsidian_9_tile_02.resetPose();
        this.obsidian_9_tile_03.resetPose();
        this.obsidian_9_tile_04.resetPose();
        this.obsidian_9_tile_05.resetPose();
        this.obsidian_9_tile_06.resetPose();
        this.obsidian_9_tile_07.resetPose();
        this.obsidian_9_tile_08.resetPose();
        this.obsidian_9_tile_09.resetPose();
        this.obsidian_9_tile_10.resetPose();
        this.obsidian_9_tile_11.resetPose();
        this.obsidian_9_tile_12.resetPose();
        this.obsidian_9_tile_13.resetPose();
        this.obsidian_9_tile_14.resetPose();
        this.obsidian_9_tile_15.resetPose();
        this.obsidian_9_tile_16.resetPose();
        this.obsidian_9_tile_17.resetPose();
        this.obsidian_9_tile_18.resetPose();
        this.obsidian_9_tile_19.resetPose();
        this.obsidian_9_tile_20.resetPose();
        this.obsidian_9_tile_21.resetPose();
        this.obsidian_9_tile_22.resetPose();
        this.obsidian_9_tile_23.resetPose();
        this.obsidian_9_tile_24.resetPose();
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
        this.obsidian_3_tile_01.visible = false;
        this.obsidian_3_tile_02.visible = false;
        this.obsidian_3_tile_03.visible = false;
        this.obsidian_3_tile_04.visible = false;
        this.obsidian_3_tile_05.visible = false;
        this.obsidian_3_tile_06.visible = false;
        this.obsidian_3_tile_07.visible = false;
        this.obsidian_3_tile_08.visible = false;
        this.obsidian_3_tile_09.visible = false;
        this.obsidian_3_tile_10.visible = false;
        this.obsidian_3_tile_11.visible = false;
        this.obsidian_3_tile_12.visible = false;
        this.obsidian_3_tile_13.visible = false;
        this.obsidian_3_tile_14.visible = false;
        this.obsidian_3_tile_15.visible = false;
        this.obsidian_3_tile_16.visible = false;
        this.obsidian_3_tile_17.visible = false;
        this.obsidian_3_tile_18.visible = false;
        this.obsidian_4_tile_01.visible = false;
        this.obsidian_4_tile_02.visible = false;
        this.obsidian_4_tile_03.visible = false;
        this.obsidian_4_tile_04.visible = false;
        this.obsidian_4_tile_05.visible = false;
        this.obsidian_4_tile_06.visible = false;
        this.obsidian_4_tile_07.visible = false;
        this.obsidian_4_tile_08.visible = false;
        this.obsidian_4_tile_09.visible = false;
        this.obsidian_4_tile_10.visible = false;
        this.obsidian_4_tile_11.visible = false;
        this.obsidian_4_tile_12.visible = false;
        this.obsidian_4_tile_13.visible = false;
        this.obsidian_4_tile_14.visible = false;
        this.obsidian_4_tile_15.visible = false;
        this.obsidian_4_tile_16.visible = false;
        this.obsidian_4_tile_17.visible = false;
        this.obsidian_4_tile_18.visible = false;
        this.obsidian_4_tile_19.visible = false;
        this.obsidian_4_tile_20.visible = false;
        this.obsidian_4_tile_21.visible = false;
        this.obsidian_4_tile_22.visible = false;
        this.obsidian_4_tile_23.visible = false;
        this.obsidian_4_tile_24.visible = false;
        this.obsidian_6_tile_01.visible = false;
        this.obsidian_6_tile_02.visible = false;
        this.obsidian_6_tile_03.visible = false;
        this.obsidian_6_tile_04.visible = false;
        this.obsidian_6_tile_05.visible = false;
        this.obsidian_6_tile_06.visible = false;
        this.obsidian_6_tile_07.visible = false;
        this.obsidian_6_tile_08.visible = false;
        this.obsidian_6_tile_09.visible = false;
        this.obsidian_6_tile_10.visible = false;
        this.obsidian_6_tile_11.visible = false;
        this.obsidian_6_tile_12.visible = false;
        this.obsidian_6_tile_13.visible = false;
        this.obsidian_6_tile_14.visible = false;
        this.obsidian_6_tile_15.visible = false;
        this.obsidian_6_tile_16.visible = false;
        this.obsidian_6_tile_17.visible = false;
        this.obsidian_6_tile_18.visible = false;
        this.obsidian_6_tile_19.visible = false;
        this.obsidian_6_tile_20.visible = false;
        this.obsidian_6_tile_21.visible = false;
        this.obsidian_6_tile_22.visible = false;
        this.obsidian_6_tile_23.visible = false;
        this.obsidian_6_tile_24.visible = false;
        this.obsidian_6_tile_25.visible = false;
        this.obsidian_6_tile_26.visible = false;
        this.obsidian_6_tile_27.visible = false;
        this.obsidian_6_tile_28.visible = false;
        this.obsidian_6_tile_29.visible = false;
        this.obsidian_6_tile_30.visible = false;
        this.obsidian_7_tile_01.visible = false;
        this.obsidian_7_tile_02.visible = false;
        this.obsidian_7_tile_03.visible = false;
        this.obsidian_7_tile_04.visible = false;
        this.obsidian_7_tile_05.visible = false;
        this.obsidian_7_tile_06.visible = false;
        this.obsidian_7_tile_07.visible = false;
        this.obsidian_7_tile_08.visible = false;
        this.obsidian_7_tile_09.visible = false;
        this.obsidian_7_tile_10.visible = false;
        this.obsidian_7_tile_11.visible = false;
        this.obsidian_7_tile_12.visible = false;
        this.obsidian_7_tile_13.visible = false;
        this.obsidian_7_tile_14.visible = false;
        this.obsidian_7_tile_15.visible = false;
        this.obsidian_7_tile_16.visible = false;
        this.obsidian_7_tile_17.visible = false;
        this.obsidian_7_tile_18.visible = false;
        this.obsidian_7_tile_19.visible = false;
        this.obsidian_7_tile_20.visible = false;
        this.obsidian_7_tile_21.visible = false;
        this.obsidian_7_tile_22.visible = false;
        this.obsidian_7_tile_23.visible = false;
        this.obsidian_7_tile_24.visible = false;
        this.obsidian_8_tile_01.visible = false;
        this.obsidian_8_tile_02.visible = false;
        this.obsidian_8_tile_03.visible = false;
        this.obsidian_8_tile_04.visible = false;
        this.obsidian_8_tile_05.visible = false;
        this.obsidian_8_tile_06.visible = false;
        this.obsidian_8_tile_07.visible = false;
        this.obsidian_8_tile_08.visible = false;
        this.obsidian_8_tile_09.visible = false;
        this.obsidian_8_tile_10.visible = false;
        this.obsidian_8_tile_11.visible = false;
        this.obsidian_8_tile_12.visible = false;
        this.obsidian_8_tile_13.visible = false;
        this.obsidian_8_tile_14.visible = false;
        this.obsidian_8_tile_15.visible = false;
        this.obsidian_8_tile_16.visible = false;
        this.obsidian_8_tile_17.visible = false;
        this.obsidian_8_tile_18.visible = false;
        this.obsidian_8_tile_19.visible = false;
        this.obsidian_8_tile_20.visible = false;
        this.obsidian_9_tile_01.visible = false;
        this.obsidian_9_tile_02.visible = false;
        this.obsidian_9_tile_03.visible = false;
        this.obsidian_9_tile_04.visible = false;
        this.obsidian_9_tile_05.visible = false;
        this.obsidian_9_tile_06.visible = false;
        this.obsidian_9_tile_07.visible = false;
        this.obsidian_9_tile_08.visible = false;
        this.obsidian_9_tile_09.visible = false;
        this.obsidian_9_tile_10.visible = false;
        this.obsidian_9_tile_11.visible = false;
        this.obsidian_9_tile_12.visible = false;
        this.obsidian_9_tile_13.visible = false;
        this.obsidian_9_tile_14.visible = false;
        this.obsidian_9_tile_15.visible = false;
        this.obsidian_9_tile_16.visible = false;
        this.obsidian_9_tile_17.visible = false;
        this.obsidian_9_tile_18.visible = false;
        this.obsidian_9_tile_19.visible = false;
        this.obsidian_9_tile_20.visible = false;
        this.obsidian_9_tile_21.visible = false;
        this.obsidian_9_tile_22.visible = false;
        this.obsidian_9_tile_23.visible = false;
        this.obsidian_9_tile_24.visible = false;
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
        applyPartPose(this.obsidian_3_bone, "obsidian_3_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_01, "obsidian_3_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_02, "obsidian_3_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_03, "obsidian_3_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_04, "obsidian_3_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_05, "obsidian_3_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_06, "obsidian_3_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_07, "obsidian_3_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_08, "obsidian_3_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_09, "obsidian_3_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_10, "obsidian_3_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_11, "obsidian_3_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_12, "obsidian_3_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_13, "obsidian_3_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_14, "obsidian_3_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_15, "obsidian_3_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_16, "obsidian_3_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_17, "obsidian_3_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_3_tile_18, "obsidian_3_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_bone, "obsidian_4_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_01, "obsidian_4_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_02, "obsidian_4_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_03, "obsidian_4_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_04, "obsidian_4_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_05, "obsidian_4_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_06, "obsidian_4_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_07, "obsidian_4_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_08, "obsidian_4_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_09, "obsidian_4_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_10, "obsidian_4_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_11, "obsidian_4_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_12, "obsidian_4_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_13, "obsidian_4_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_14, "obsidian_4_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_15, "obsidian_4_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_16, "obsidian_4_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_17, "obsidian_4_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_18, "obsidian_4_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_19, "obsidian_4_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_20, "obsidian_4_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_21, "obsidian_4_tile_21", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_22, "obsidian_4_tile_22", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_23, "obsidian_4_tile_23", clip, elapsedTicks);
        applyPartPose(this.obsidian_4_tile_24, "obsidian_4_tile_24", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_bone, "obsidian_6_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_01, "obsidian_6_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_02, "obsidian_6_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_03, "obsidian_6_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_04, "obsidian_6_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_05, "obsidian_6_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_06, "obsidian_6_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_07, "obsidian_6_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_08, "obsidian_6_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_09, "obsidian_6_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_10, "obsidian_6_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_11, "obsidian_6_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_12, "obsidian_6_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_13, "obsidian_6_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_14, "obsidian_6_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_15, "obsidian_6_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_16, "obsidian_6_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_17, "obsidian_6_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_18, "obsidian_6_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_19, "obsidian_6_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_20, "obsidian_6_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_21, "obsidian_6_tile_21", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_22, "obsidian_6_tile_22", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_23, "obsidian_6_tile_23", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_24, "obsidian_6_tile_24", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_25, "obsidian_6_tile_25", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_26, "obsidian_6_tile_26", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_27, "obsidian_6_tile_27", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_28, "obsidian_6_tile_28", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_29, "obsidian_6_tile_29", clip, elapsedTicks);
        applyPartPose(this.obsidian_6_tile_30, "obsidian_6_tile_30", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_bone, "obsidian_7_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_01, "obsidian_7_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_02, "obsidian_7_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_03, "obsidian_7_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_04, "obsidian_7_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_05, "obsidian_7_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_06, "obsidian_7_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_07, "obsidian_7_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_08, "obsidian_7_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_09, "obsidian_7_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_10, "obsidian_7_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_11, "obsidian_7_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_12, "obsidian_7_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_13, "obsidian_7_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_14, "obsidian_7_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_15, "obsidian_7_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_16, "obsidian_7_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_17, "obsidian_7_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_18, "obsidian_7_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_19, "obsidian_7_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_20, "obsidian_7_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_21, "obsidian_7_tile_21", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_22, "obsidian_7_tile_22", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_23, "obsidian_7_tile_23", clip, elapsedTicks);
        applyPartPose(this.obsidian_7_tile_24, "obsidian_7_tile_24", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_bone, "obsidian_8_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_01, "obsidian_8_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_02, "obsidian_8_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_03, "obsidian_8_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_04, "obsidian_8_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_05, "obsidian_8_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_06, "obsidian_8_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_07, "obsidian_8_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_08, "obsidian_8_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_09, "obsidian_8_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_10, "obsidian_8_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_11, "obsidian_8_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_12, "obsidian_8_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_13, "obsidian_8_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_14, "obsidian_8_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_15, "obsidian_8_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_16, "obsidian_8_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_17, "obsidian_8_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_18, "obsidian_8_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_19, "obsidian_8_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_8_tile_20, "obsidian_8_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_bone, "obsidian_9_bone", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_01, "obsidian_9_tile_01", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_02, "obsidian_9_tile_02", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_03, "obsidian_9_tile_03", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_04, "obsidian_9_tile_04", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_05, "obsidian_9_tile_05", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_06, "obsidian_9_tile_06", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_07, "obsidian_9_tile_07", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_08, "obsidian_9_tile_08", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_09, "obsidian_9_tile_09", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_10, "obsidian_9_tile_10", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_11, "obsidian_9_tile_11", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_12, "obsidian_9_tile_12", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_13, "obsidian_9_tile_13", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_14, "obsidian_9_tile_14", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_15, "obsidian_9_tile_15", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_16, "obsidian_9_tile_16", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_17, "obsidian_9_tile_17", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_18, "obsidian_9_tile_18", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_19, "obsidian_9_tile_19", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_20, "obsidian_9_tile_20", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_21, "obsidian_9_tile_21", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_22, "obsidian_9_tile_22", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_23, "obsidian_9_tile_23", clip, elapsedTicks);
        applyPartPose(this.obsidian_9_tile_24, "obsidian_9_tile_24", clip, elapsedTicks);
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
            case "obsidian_3_bone" -> this.obsidian_3_bone;
            case "obsidian_3_tile_01" -> this.obsidian_3_tile_01;
            case "obsidian_3_tile_02" -> this.obsidian_3_tile_02;
            case "obsidian_3_tile_03" -> this.obsidian_3_tile_03;
            case "obsidian_3_tile_04" -> this.obsidian_3_tile_04;
            case "obsidian_3_tile_05" -> this.obsidian_3_tile_05;
            case "obsidian_3_tile_06" -> this.obsidian_3_tile_06;
            case "obsidian_3_tile_07" -> this.obsidian_3_tile_07;
            case "obsidian_3_tile_08" -> this.obsidian_3_tile_08;
            case "obsidian_3_tile_09" -> this.obsidian_3_tile_09;
            case "obsidian_3_tile_10" -> this.obsidian_3_tile_10;
            case "obsidian_3_tile_11" -> this.obsidian_3_tile_11;
            case "obsidian_3_tile_12" -> this.obsidian_3_tile_12;
            case "obsidian_3_tile_13" -> this.obsidian_3_tile_13;
            case "obsidian_3_tile_14" -> this.obsidian_3_tile_14;
            case "obsidian_3_tile_15" -> this.obsidian_3_tile_15;
            case "obsidian_3_tile_16" -> this.obsidian_3_tile_16;
            case "obsidian_3_tile_17" -> this.obsidian_3_tile_17;
            case "obsidian_3_tile_18" -> this.obsidian_3_tile_18;
            case "obsidian_4_bone" -> this.obsidian_4_bone;
            case "obsidian_4_tile_01" -> this.obsidian_4_tile_01;
            case "obsidian_4_tile_02" -> this.obsidian_4_tile_02;
            case "obsidian_4_tile_03" -> this.obsidian_4_tile_03;
            case "obsidian_4_tile_04" -> this.obsidian_4_tile_04;
            case "obsidian_4_tile_05" -> this.obsidian_4_tile_05;
            case "obsidian_4_tile_06" -> this.obsidian_4_tile_06;
            case "obsidian_4_tile_07" -> this.obsidian_4_tile_07;
            case "obsidian_4_tile_08" -> this.obsidian_4_tile_08;
            case "obsidian_4_tile_09" -> this.obsidian_4_tile_09;
            case "obsidian_4_tile_10" -> this.obsidian_4_tile_10;
            case "obsidian_4_tile_11" -> this.obsidian_4_tile_11;
            case "obsidian_4_tile_12" -> this.obsidian_4_tile_12;
            case "obsidian_4_tile_13" -> this.obsidian_4_tile_13;
            case "obsidian_4_tile_14" -> this.obsidian_4_tile_14;
            case "obsidian_4_tile_15" -> this.obsidian_4_tile_15;
            case "obsidian_4_tile_16" -> this.obsidian_4_tile_16;
            case "obsidian_4_tile_17" -> this.obsidian_4_tile_17;
            case "obsidian_4_tile_18" -> this.obsidian_4_tile_18;
            case "obsidian_4_tile_19" -> this.obsidian_4_tile_19;
            case "obsidian_4_tile_20" -> this.obsidian_4_tile_20;
            case "obsidian_4_tile_21" -> this.obsidian_4_tile_21;
            case "obsidian_4_tile_22" -> this.obsidian_4_tile_22;
            case "obsidian_4_tile_23" -> this.obsidian_4_tile_23;
            case "obsidian_4_tile_24" -> this.obsidian_4_tile_24;
            case "obsidian_6_bone" -> this.obsidian_6_bone;
            case "obsidian_6_tile_01" -> this.obsidian_6_tile_01;
            case "obsidian_6_tile_02" -> this.obsidian_6_tile_02;
            case "obsidian_6_tile_03" -> this.obsidian_6_tile_03;
            case "obsidian_6_tile_04" -> this.obsidian_6_tile_04;
            case "obsidian_6_tile_05" -> this.obsidian_6_tile_05;
            case "obsidian_6_tile_06" -> this.obsidian_6_tile_06;
            case "obsidian_6_tile_07" -> this.obsidian_6_tile_07;
            case "obsidian_6_tile_08" -> this.obsidian_6_tile_08;
            case "obsidian_6_tile_09" -> this.obsidian_6_tile_09;
            case "obsidian_6_tile_10" -> this.obsidian_6_tile_10;
            case "obsidian_6_tile_11" -> this.obsidian_6_tile_11;
            case "obsidian_6_tile_12" -> this.obsidian_6_tile_12;
            case "obsidian_6_tile_13" -> this.obsidian_6_tile_13;
            case "obsidian_6_tile_14" -> this.obsidian_6_tile_14;
            case "obsidian_6_tile_15" -> this.obsidian_6_tile_15;
            case "obsidian_6_tile_16" -> this.obsidian_6_tile_16;
            case "obsidian_6_tile_17" -> this.obsidian_6_tile_17;
            case "obsidian_6_tile_18" -> this.obsidian_6_tile_18;
            case "obsidian_6_tile_19" -> this.obsidian_6_tile_19;
            case "obsidian_6_tile_20" -> this.obsidian_6_tile_20;
            case "obsidian_6_tile_21" -> this.obsidian_6_tile_21;
            case "obsidian_6_tile_22" -> this.obsidian_6_tile_22;
            case "obsidian_6_tile_23" -> this.obsidian_6_tile_23;
            case "obsidian_6_tile_24" -> this.obsidian_6_tile_24;
            case "obsidian_6_tile_25" -> this.obsidian_6_tile_25;
            case "obsidian_6_tile_26" -> this.obsidian_6_tile_26;
            case "obsidian_6_tile_27" -> this.obsidian_6_tile_27;
            case "obsidian_6_tile_28" -> this.obsidian_6_tile_28;
            case "obsidian_6_tile_29" -> this.obsidian_6_tile_29;
            case "obsidian_6_tile_30" -> this.obsidian_6_tile_30;
            case "obsidian_7_bone" -> this.obsidian_7_bone;
            case "obsidian_7_tile_01" -> this.obsidian_7_tile_01;
            case "obsidian_7_tile_02" -> this.obsidian_7_tile_02;
            case "obsidian_7_tile_03" -> this.obsidian_7_tile_03;
            case "obsidian_7_tile_04" -> this.obsidian_7_tile_04;
            case "obsidian_7_tile_05" -> this.obsidian_7_tile_05;
            case "obsidian_7_tile_06" -> this.obsidian_7_tile_06;
            case "obsidian_7_tile_07" -> this.obsidian_7_tile_07;
            case "obsidian_7_tile_08" -> this.obsidian_7_tile_08;
            case "obsidian_7_tile_09" -> this.obsidian_7_tile_09;
            case "obsidian_7_tile_10" -> this.obsidian_7_tile_10;
            case "obsidian_7_tile_11" -> this.obsidian_7_tile_11;
            case "obsidian_7_tile_12" -> this.obsidian_7_tile_12;
            case "obsidian_7_tile_13" -> this.obsidian_7_tile_13;
            case "obsidian_7_tile_14" -> this.obsidian_7_tile_14;
            case "obsidian_7_tile_15" -> this.obsidian_7_tile_15;
            case "obsidian_7_tile_16" -> this.obsidian_7_tile_16;
            case "obsidian_7_tile_17" -> this.obsidian_7_tile_17;
            case "obsidian_7_tile_18" -> this.obsidian_7_tile_18;
            case "obsidian_7_tile_19" -> this.obsidian_7_tile_19;
            case "obsidian_7_tile_20" -> this.obsidian_7_tile_20;
            case "obsidian_7_tile_21" -> this.obsidian_7_tile_21;
            case "obsidian_7_tile_22" -> this.obsidian_7_tile_22;
            case "obsidian_7_tile_23" -> this.obsidian_7_tile_23;
            case "obsidian_7_tile_24" -> this.obsidian_7_tile_24;
            case "obsidian_8_bone" -> this.obsidian_8_bone;
            case "obsidian_8_tile_01" -> this.obsidian_8_tile_01;
            case "obsidian_8_tile_02" -> this.obsidian_8_tile_02;
            case "obsidian_8_tile_03" -> this.obsidian_8_tile_03;
            case "obsidian_8_tile_04" -> this.obsidian_8_tile_04;
            case "obsidian_8_tile_05" -> this.obsidian_8_tile_05;
            case "obsidian_8_tile_06" -> this.obsidian_8_tile_06;
            case "obsidian_8_tile_07" -> this.obsidian_8_tile_07;
            case "obsidian_8_tile_08" -> this.obsidian_8_tile_08;
            case "obsidian_8_tile_09" -> this.obsidian_8_tile_09;
            case "obsidian_8_tile_10" -> this.obsidian_8_tile_10;
            case "obsidian_8_tile_11" -> this.obsidian_8_tile_11;
            case "obsidian_8_tile_12" -> this.obsidian_8_tile_12;
            case "obsidian_8_tile_13" -> this.obsidian_8_tile_13;
            case "obsidian_8_tile_14" -> this.obsidian_8_tile_14;
            case "obsidian_8_tile_15" -> this.obsidian_8_tile_15;
            case "obsidian_8_tile_16" -> this.obsidian_8_tile_16;
            case "obsidian_8_tile_17" -> this.obsidian_8_tile_17;
            case "obsidian_8_tile_18" -> this.obsidian_8_tile_18;
            case "obsidian_8_tile_19" -> this.obsidian_8_tile_19;
            case "obsidian_8_tile_20" -> this.obsidian_8_tile_20;
            case "obsidian_9_bone" -> this.obsidian_9_bone;
            case "obsidian_9_tile_01" -> this.obsidian_9_tile_01;
            case "obsidian_9_tile_02" -> this.obsidian_9_tile_02;
            case "obsidian_9_tile_03" -> this.obsidian_9_tile_03;
            case "obsidian_9_tile_04" -> this.obsidian_9_tile_04;
            case "obsidian_9_tile_05" -> this.obsidian_9_tile_05;
            case "obsidian_9_tile_06" -> this.obsidian_9_tile_06;
            case "obsidian_9_tile_07" -> this.obsidian_9_tile_07;
            case "obsidian_9_tile_08" -> this.obsidian_9_tile_08;
            case "obsidian_9_tile_09" -> this.obsidian_9_tile_09;
            case "obsidian_9_tile_10" -> this.obsidian_9_tile_10;
            case "obsidian_9_tile_11" -> this.obsidian_9_tile_11;
            case "obsidian_9_tile_12" -> this.obsidian_9_tile_12;
            case "obsidian_9_tile_13" -> this.obsidian_9_tile_13;
            case "obsidian_9_tile_14" -> this.obsidian_9_tile_14;
            case "obsidian_9_tile_15" -> this.obsidian_9_tile_15;
            case "obsidian_9_tile_16" -> this.obsidian_9_tile_16;
            case "obsidian_9_tile_17" -> this.obsidian_9_tile_17;
            case "obsidian_9_tile_18" -> this.obsidian_9_tile_18;
            case "obsidian_9_tile_19" -> this.obsidian_9_tile_19;
            case "obsidian_9_tile_20" -> this.obsidian_9_tile_20;
            case "obsidian_9_tile_21" -> this.obsidian_9_tile_21;
            case "obsidian_9_tile_22" -> this.obsidian_9_tile_22;
            case "obsidian_9_tile_23" -> this.obsidian_9_tile_23;
            case "obsidian_9_tile_24" -> this.obsidian_9_tile_24;
            default -> null;
        };
    }

}
