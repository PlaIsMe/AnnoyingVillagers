package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.rig.RigCriticalUtil;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface BurstProtectEntity {
    default float getBurstProtectCapRatio() {
        return 0.2F;
    }

    default boolean shouldIgnoreBurstProtection(LivingEntity self, DamageSource source) {
//        ADD THIS CODE IN AV_EFM

//        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
//        if (patch == null) {
//            return false;
//        }
//
//        var player = patch.getAnimator().getPlayerFor(null);
//        if (player == null) {
//            return false;
//        }
//
//        AssetAccessor<? extends StaticAnimation> anim = player.getRealAnimation();
//        return EpicfightUtil.isDamagableHitAnimation(anim, patch);
        return RigStunController.isStunned(self) || RigCriticalUtil.isCriticalDamage(self, source);
    }

    default float applyBurstProtection(LivingEntity self, DamageSource source, float damage) {
        if (shouldIgnoreBurstProtection(self, source)) return damage;
        if (damage <= 0.0F) return 0.0F;
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return damage;
        float cap = self.getMaxHealth() * getBurstProtectCapRatio();
        return Mth.clamp(damage, 0.0F, cap);
    }
}
