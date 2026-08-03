package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.client.animation.rig_animation.*;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class RigAnimationResolver {
    private RigAnimationResolver() {
    }

    public static AnimationDefinition get(RigAnimationId animationId) {
        return switch (animationId) {
            case SWORD_AUTO1 -> RigSwordAnimations1.SWORD_AUTO1;
            case SWORD_AUTO2 -> RigSwordAnimations1.SWORD_AUTO2;
            case SWORD_AUTO3 -> RigSwordAnimations1.SWORD_AUTO3;
            case SWORD_AUTO4 -> RigSwordAnimations1.SWORD_AUTO4;
            case SWORD_DASH -> RigSwordAnimations2.SWORD_DASH;
            case SWORD_AIRSLASH -> RigSwordAnimations2.SWORD_AIRSLASH;
            case SWEEPING_EDGE -> RigSwordAnimations2.SWEEPING_EDGE;
            case SWORD_MOUNT_ATTACK -> RigMountAttackAnimations.SWORD_MOUNT_ATTACK;
            case SWORD_DUAL_AUTO1 -> RigDualSwordAnimations1.SWORD_DUAL_AUTO1;
            case SWORD_DUAL_AUTO2 -> RigDualSwordAnimations1.SWORD_DUAL_AUTO2;
            case SWORD_DUAL_AUTO3 -> RigDualSwordAnimations1.SWORD_DUAL_AUTO3;
            case SWORD_DUAL_DASH -> RigDualSwordAnimations2.SWORD_DUAL_DASH;
            case SWORD_DUAL_AIRSLASH -> RigDualSwordAnimations2.SWORD_DUAL_AIRSLASH;
            case DANCING_EDGE -> RigDualSwordAnimations2.DANCING_EDGE;
            case ROLL_BACKWARD -> RigRollAnimations.ROLL_BACKWARD;
            case ROLL_FORWARD -> RigRollAnimations.ROLL_FORWARD;
            case STEP_FORWARD -> RigStepAnimations.STEP_FORWARD;
            case STEP_BACKWARD -> RigStepAnimations.STEP_BACKWARD;
            case STEP_LEFT -> RigStepAnimations.STEP_LEFT;
            case STEP_RIGHT -> RigStepAnimations.STEP_RIGHT;
            case JUMP -> RigJumpAnimations.JUMP;
            case BOW_AIM_DOWN -> RigBowAimAnimations.BOW_AIM_DOWN;
            case BOW_AIM_MID -> RigBowAimAnimations.BOW_AIM_MID;
            case BOW_AIM_UP -> RigBowAimAnimations.BOW_AIM_UP;
            case BOW_SHOT_DOWN -> RigBowShotAnimations.BOW_SHOT_DOWN;
            case BOW_SHOT_MID -> RigBowShotAnimations.BOW_SHOT_MID;
            case BOW_SHOT_UP -> RigBowShotAnimations.BOW_SHOT_UP;
            case KNOCKDOWN_WAKEUP_LEFT -> RigKnockdownWakeUpAnimations.KNOCKDOWN_WAKEUP_LEFT;
            case KNOCKDOWN_WAKEUP_RIGHT -> RigKnockdownWakeUpAnimations.KNOCKDOWN_WAKEUP_RIGHT;
            case EAT_OFFHAND -> RigHandOnlyAnimations.EAT_OFFHAND;
            case EAT_MAINHAND -> RigHandOnlyAnimations.EAT_MAINHAND;
            case THROW_ENDER_PEARL -> RigHandOnlyAnimations.THROW_ENDER_PEARL;
        };
    }
}
