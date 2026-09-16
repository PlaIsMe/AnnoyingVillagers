package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.client.animation.rig_special_animation.*;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.client.animation.AnimationDefinition;

public final class SpecialAnimationResolver {
    private SpecialAnimationResolver() {
    }

    public static AnimationDefinition resolve(SpecialAnimationId id) {
        return switch (id) {
            case WARDEN_ATK_1_1 -> AvWardenAttackAnimations1.ATK_1_1;
            case WARDEN_ATK_1_2 -> AvWardenAttackAnimations1.ATK_1_2;
            case WARDEN_ATK_2_1 -> AvWardenAttackAnimations2.ATK_2_1;
            case WARDEN_ATK_2_2 -> AvWardenAttackAnimations2.ATK_2_2;
            case WARDEN_ATK_2_3 -> AvWardenAttackAnimations2.ATK_2_3;
            case WARDEN_ATK_3_1 -> AvWardenAttackAnimations3.ATK_3_1;
            case WARDEN_ATK_3_2 -> AvWardenAttackAnimations3.ATK_3_2;
            case WARDEN_ATK_3_3 -> AvWardenAttackAnimations3.ATK_3_3;
            case WARDEN_ATK_4_1 -> AvWardenAttackAnimations4.ATK_4_1;
            case WARDEN_ATK_4_2 -> AvWardenAttackAnimations4.ATK_4_2;
            case WARDEN_SKILL_1 -> AvWardenSkillAnimations1.SKILL_1;
            case WARDEN_SKILL_2 -> AvWardenSkillAnimations1.SKILL_2;
            case WARDEN_SONIC_BOOM -> AvWardenSkillAnimations2.SONIC_BOOM;
            case GOLEM_ATK_1_1 -> AvGolemAttackAnimations1.ATK_1_1;
            case GOLEM_ATK_1_2 -> AvGolemAttackAnimations1.ATK_1_2;
            case GOLEM_ATK_2_1 -> AvGolemAttackAnimations2.ATK_2_1;
            case GOLEM_ATK_2_2 -> AvGolemAttackAnimations2.ATK_2_2;
            case GOLEM_ATK_2_3 -> AvGolemAttackAnimations2.ATK_2_3;
            case GOLEM_ATK_3_1 -> AvGolemAttackAnimations3.ATK_3_1;
            case GOLEM_ATK_3_2 -> AvGolemAttackAnimations3.ATK_3_2;
            case GOLEM_ATK_3_3 -> AvGolemAttackAnimations3.ATK_3_3;
            case GOLEM_SKILL_1 -> AvGolemSkillAnimations.SKILL_1;
            case GOLEM_SKILL_2 -> AvGolemSkillAnimations.SKILL_2;
            case GOLEM_SKILL_3 -> AvGolemSkillAnimations.SKILL_3;
            case GOLEM_SWORD_ATK_1_1 -> AvGolemSwordAttackAnimations.SWORD_ATK_1_1;
            case GOLEM_SWORD_ATK_1_2 -> AvGolemSwordAttackAnimations.SWORD_ATK_1_2;
            case GOLEM_SWORD_SKILL_1 -> AvGolemSwordLivingSkillAnimations.SWORD_SKILL_1;
            case GOLEM_DUAL_SWORD_ATK_1_1 -> AvGolemDualSwordAttackAnimations.DUAL_SWORD_ATK_1_1;
            case GOLEM_DUAL_SWORD_ATK_1_2 -> AvGolemDualSwordAttackAnimations.DUAL_SWORD_ATK_1_2;
            case GOLEM_DUAL_SWORD_SKILL_1 -> AvGolemDualSwordLivingSkillAnimations.DUAL_SWORD_SKILL_1;
            case GOLEM_AXE_ATK_1_1 -> AvGolemAxeAttackAnimations.AXE_ATK_1_1;
            case GOLEM_AXE_ATK_1_2 -> AvGolemAxeAttackAnimations.AXE_ATK_1_2;
            case GOLEM_AXE_SKILL_1 -> AvGolemAxeLivingSkillAnimations.AXE_SKILL_1;
            case GOLEM_DUAL_AXE_ATK_1_1 -> AvGolemDualAxeAttackAnimations1.DUAL_AXE_ATK_1_1;
            case GOLEM_DUAL_AXE_ATK_1_2 -> AvGolemDualAxeAttackAnimations1.DUAL_AXE_ATK_1_2;
            case GOLEM_DUAL_AXE_ATK_1_3 -> AvGolemDualAxeAttackAnimations2.DUAL_AXE_ATK_1_3;
            case GOLEM_DUAL_AXE_SKILL_1 -> AvGolemDualAxeLivingSkillAnimations.DUAL_AXE_SKILL_1;
            case GOLEM_SPEAR_ATK_1_1 -> AvGolemSpearAttackAnimations.SPEAR_ATK_1_1;
            case GOLEM_SPEAR_ATK_1_2 -> AvGolemSpearAttackAnimations.SPEAR_ATK_1_2;
            case GOLEM_SPEAR_SKILL_1 -> AvGolemSpearLivingSkillAnimations.SPEAR_SKILL_1;
            case ARMS_ATK_1 -> GolemArmsAttackAnimations1.ATK_1;
            case ARMS_ATK_2 -> GolemArmsAttackAnimations1.ATK_2;
            case ARMS_ATK_3 -> GolemArmsAttackAnimations2.ATK_3;
            case ARMS_RUN_ATK -> GolemArmsAttackAnimations3.RUN_ATK;
            case ARMS_AIR_ATK -> GolemArmsAttackAnimations3.AIR_ATK;
            case ARMS_GUARD_TRANSFORM -> GolemArmsLivingAnimations.GUARD_TRANSFORM;
            case ARMS_GUARD -> GolemArmsLivingAnimations.GUARD;
            case ARMS_GUARD_FINISH -> GolemArmsLivingAnimations.GUARD_FINISH;
            case OBSIDIAN_HELMET_EXTENDED_1 -> ObsidianDiamondHelmetAnimations1.EXTENDED_1;
            case OBSIDIAN_HELMET_EXTENDED_2 -> ObsidianDiamondHelmetAnimations1.EXTENDED_2;
            case OBSIDIAN_HELMET_EXTENDED_3 -> ObsidianDiamondHelmetAnimations1.EXTENDED_3;
            case OBSIDIAN_HELMET_EXTENDED_4 -> ObsidianDiamondHelmetAnimations1.EXTENDED_4;
            case OBSIDIAN_HELMET_EXTENDED_5 -> ObsidianDiamondHelmetAnimations1.EXTENDED_5;
            case OBSIDIAN_HELMET_EXTENDED_6 -> ObsidianDiamondHelmetAnimations1.EXTENDED_6;
            case OBSIDIAN_HELMET_EXTENDED_7 -> ObsidianDiamondHelmetAnimations1.EXTENDED_7;
            case OBSIDIAN_HELMET_EXTENDED_8 -> ObsidianDiamondHelmetAnimations2.EXTENDED_8;
            case OBSIDIAN_HELMET_EXTENDED_9 -> ObsidianDiamondHelmetAnimations2.EXTENDED_9;
            case OBSIDIAN_HELMET_EXTENDED_10 -> ObsidianDiamondHelmetAnimations2.EXTENDED_10;
            case OBSIDIAN_HELMET_EXTENDED_11 -> ObsidianDiamondHelmetAnimations2.EXTENDED_11;
            case OBSIDIAN_HELMET_EXTENDED_12 -> ObsidianDiamondHelmetAnimations2.EXTENDED_12;
            case OBSIDIAN_HELMET_EXTENDED_13 -> ObsidianDiamondHelmetAnimations2.EXTENDED_13;
            case OBSIDIAN_HELMET_EXTENDED_14 -> ObsidianDiamondHelmetAnimations2.EXTENDED_14;
            case OBSIDIAN_HELMET_EXTENDED_15 -> ObsidianDiamondHelmetAnimations3.EXTENDED_15;
            case OBSIDIAN_HELMET_EXTENDED_16 -> ObsidianDiamondHelmetAnimations3.EXTENDED_16;
            case OBSIDIAN_HELMET_EXTENDED_17 -> ObsidianDiamondHelmetAnimations3.EXTENDED_17;
            case OBSIDIAN_HELMET_EXTENDED_18 -> ObsidianDiamondHelmetAnimations3.EXTENDED_18;
            case OBSIDIAN_HELMET_EXTENDED_19 -> ObsidianDiamondHelmetAnimations3.EXTENDED_19;
            case OBSIDIAN_HELMET_EXTENDED_20 -> ObsidianDiamondHelmetAnimations3.EXTENDED_20;
            case OBSIDIAN_HELMET_EXTENDED_21 -> ObsidianDiamondHelmetAnimations4.EXTENDED_21;
            case OBSIDIAN_HELMET_EXTENDED_22 -> ObsidianDiamondHelmetAnimations4.EXTENDED_22;
            case OBSIDIAN_HELMET_EXTENDED_23 -> ObsidianDiamondHelmetAnimations4.EXTENDED_23;
            case OBSIDIAN_HELMET_EXTENDED_24 -> ObsidianDiamondHelmetAnimations4.EXTENDED_24;
            case OBSIDIAN_HELMET_EXTENDED_25 -> ObsidianDiamondHelmetAnimations4.EXTENDED_25;
            case OBSIDIAN_HELMET_EXTENDED_26 -> ObsidianDiamondHelmetAnimations4.EXTENDED_26;
            case OBSIDIAN_CHESTPLATE_EXTENDED_1 -> ObsidianDiamondChestplateAnimations1.EXTENDED_1;
            case OBSIDIAN_CHESTPLATE_EXTENDED_2 -> ObsidianDiamondChestplateAnimations1.EXTENDED_2;
            case OBSIDIAN_CHESTPLATE_EXTENDED_3 -> ObsidianDiamondChestplateAnimations2.EXTENDED_3;
            case OBSIDIAN_CHESTPLATE_EXTENDED_4 -> ObsidianDiamondChestplateAnimations2.EXTENDED_4;
            case OBSIDIAN_CHESTPLATE_EXTENDED_5 -> ObsidianDiamondChestplateAnimations3.EXTENDED_5;
            case OBSIDIAN_CHESTPLATE_EXTENDED_6 -> ObsidianDiamondChestplateAnimations3.EXTENDED_6;
            case OBSIDIAN_CHESTPLATE_EXTENDED_7 -> ObsidianDiamondChestplateAnimations4.EXTENDED_7;
            case OBSIDIAN_CHESTPLATE_EXTENDED_8 -> ObsidianDiamondChestplateAnimations4.EXTENDED_8;
            case OBSIDIAN_CHESTPLATE_EXTENDED_9 -> ObsidianDiamondChestplateAnimations5.EXTENDED_9;
            case OBSIDIAN_CHESTPLATE_EXTENDED_10 -> ObsidianDiamondChestplateAnimations5.EXTENDED_10;
            case OBSIDIAN_CHESTPLATE_EXTENDED_11 -> ObsidianDiamondChestplateAnimations6.EXTENDED_11;
            case OBSIDIAN_CHESTPLATE_EXTENDED_12 -> ObsidianDiamondChestplateAnimations6.EXTENDED_12;
            case OBSIDIAN_CHESTPLATE_EXTENDED_13 -> ObsidianDiamondChestplateAnimations7.EXTENDED_13;
            case OBSIDIAN_CHESTPLATE_EXTENDED_14 -> ObsidianDiamondChestplateAnimations7.EXTENDED_14;
            case OBSIDIAN_CHESTPLATE_EXTENDED_15 -> ObsidianDiamondChestplateAnimations8.EXTENDED_15;
            case OBSIDIAN_CHESTPLATE_EXTENDED_16 -> ObsidianDiamondChestplateAnimations8.EXTENDED_16;
            case OBSIDIAN_CHESTPLATE_EXTENDED_17 -> ObsidianDiamondChestplateAnimations9.EXTENDED_17;
            case OBSIDIAN_CHESTPLATE_EXTENDED_18 -> ObsidianDiamondChestplateAnimations9.EXTENDED_18;
            case OBSIDIAN_CHESTPLATE_EXTENDED_19 -> ObsidianDiamondChestplateAnimations10.EXTENDED_19;
            case OBSIDIAN_CHESTPLATE_EXTENDED_20 -> ObsidianDiamondChestplateAnimations10.EXTENDED_20;
        };
    }
}
