package com.pla.annoyingvillagers.compat;

import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.efkick.animations.KickAttackAnimation;
import com.pla.efkick.config.EFKickConfig;
import com.pla.efkick.gameasset.EFKickAnimations;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.fml.ModList;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class EfKick {
    public static void tryDealKickStaminaDamage(
            DamageSource damageSource,
            LivingEntityPatch<?> attackerLivingEntityPatch,
            AssetAccessor<? extends StaticAnimation> attackerDynamicAnimation
    ) {
        if (attackerDynamicAnimation != null && attackerDynamicAnimation.get() instanceof KickAttackAnimation) {
            EpicfightUtil.dealStaminaDamageByPercentage(
                    damageSource,
                    attackerLivingEntityPatch,
                    EFKickConfig.KICK_STAMINA_DECREASE_PERCENTAGE.get(),
                    true
            );
        }
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] kickAnimations() {
        return CombatCommon.animations(
                EFKickAnimations.KICK_1,
                EFKickAnimations.KICK_2,
                EFKickAnimations.KICK_3,
                EFKickAnimations.KICK_4,
                EFKickAnimations.KICK_C,
                EFKickAnimations.KICK_RUSH,
                EFKickAnimations.KICK_H
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] fistKickAnimations() {
        return CombatCommon.animations(
                EFKickAnimations.KICK_1,
                EFKickAnimations.KICK_2,
                EFKickAnimations.KICK_3,
                EFKickAnimations.KICK_4,
                EFKickAnimations.KICK_C,
                EFKickAnimations.KICK_RUSH,
                EFKickAnimations.KICK_COMBO
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] basicKickAnimations() {
        return CombatCommon.animations(
                EFKickAnimations.KICK_1,
                EFKickAnimations.KICK_2,
                EFKickAnimations.KICK_3,
                EFKickAnimations.KICK_4
        );
    }
}
