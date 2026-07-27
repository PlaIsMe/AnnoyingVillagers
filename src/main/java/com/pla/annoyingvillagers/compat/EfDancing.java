package com.pla.annoyingvillagers.compat;

import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.efdancing.gameasset.EFDancingAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

public class EfDancing {
    public static AssetAccessor<? extends StaticAnimation> resolveIdleAnimation(IdleAnimation idle) {
        return switch (idle) {
            case PUSH_UP -> EFDancingAnimations.PUSH_UP_EMOTE;
            case LAY -> EFDancingAnimations.LAY_EMOTE;
            case SLEEP -> EFDancingAnimations.DEATH_EMOTE;
            case SIT -> EFDancingAnimations.SIT_EMOTE;
            case FUN_SIT -> EFDancingAnimations.FUNNY_EMOTE;
            case SLIGHT -> EFDancingAnimations.SLIGHT_EMOTE;
            case LAY_RELAX_EMOTE -> EFDancingAnimations.LAY_RELAX_EMOTE;
            case ONE_ARM_LAY_EMOTE -> EFDancingAnimations.ONE_ARM_LAY_EMOTE;
            case SALUTE_LEFT_HAND_EMOTE -> EFDancingAnimations.SALUTE_LEFT_HAND_EMOTE;
            case SIT_NO_WEAPON_EMOTE -> EFDancingAnimations.SIT_NO_WEAPON_EMOTE;
            case SORROW_EMOTE -> EFDancingAnimations.SORROW_EMOTE;
            case SURRENDER_EMOTE -> EFDancingAnimations.SURRENDER_EMOTE;
            case ATTENTION_EMOTE -> EFDancingAnimations.ATTENTION_EMOTE;
            case FLAPPING_EMOTE -> EFDancingAnimations.FLAPPING_EMOTE;
            case FUN_JUMP_EMOTE -> EFDancingAnimations.FUN_JUMP_EMOTE;
            case JUMP_EMOTE -> EFDancingAnimations.JUMP_EMOTE;
            case PRONE_EMOTE -> EFDancingAnimations.PRONE_EMOTE;
            case SALUTE_EMOTE -> EFDancingAnimations.SALUTE_EMOTE;
        };
    }
}
