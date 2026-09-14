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
        };
    }
}
