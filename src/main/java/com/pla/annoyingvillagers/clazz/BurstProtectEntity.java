package com.pla.annoyingvillagers.clazz;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface BurstProtectEntity {
    float getRecentDamageTaken();
    void setRecentDamageTaken(float value);

    int getRecentHitCounter();
    void setRecentHitCounter(int value);

    default float getBurstProtectCapRatio() {
        return 0.2F;
    }

    default float getBurstProtectMinDamage() {
        return 0.3F;
    }

    default void tickBurstProtectionDecay(LivingEntity self) {
        if (getRecentDamageTaken() > 0.0F) {
            setRecentDamageTaken(Mth.approach(
                    getRecentDamageTaken(),
                    0.0F,
                    self.getMaxHealth() * 0.07F / 160.0F
            ));
        }

        if (self.tickCount % 4 == 0 && getRecentHitCounter() > 0) {
            setRecentHitCounter(Mth.clamp(getRecentHitCounter() - 1, 0, 5));
        }
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

        return false;
    }

    default float applyBurstProtection(LivingEntity self, DamageSource source, float damage) {
        if (shouldIgnoreBurstProtection(self, source)) {
            return damage;
        }

        if (damage <= 0.0F) {
            return 0.0F;
        }

        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return damage;
        }

        float cap = self.getMaxHealth() * getBurstProtectCapRatio();
        damage = Mth.clamp(damage, 0.0F, cap);

        float damageScale = 1.0F - Mth.clamp(
                getRecentDamageTaken() / (self.getMaxHealth() * 0.07F),
                0.0F,
                0.9F
        );

        float hitScale = 1.0F - Mth.clamp(
                (float) getRecentHitCounter() / 5.0F,
                0.0F,
                0.9F
        );

        damage *= damageScale;

        if (getRecentHitCounter() >= 5) {
            damage = getBurstProtectMinDamage();
        } else {
            damage *= hitScale;
        }

        setRecentHitCounter(getRecentHitCounter() + 1);
        setRecentDamageTaken(getRecentDamageTaken() + damage);

        return damage;
    }
}