package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.rig.RigCriticalUtil;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/** Default burst-protection policy; entity-specific interface overrides still take precedence. */
public final class BurstProtectionUtil {
    private BurstProtectionUtil() {}

    public static boolean shouldIgnoreBurstProtection(LivingEntity self, DamageSource source) {
        return RigStunController.isStunned(self) || RigCriticalUtil.isCriticalDamage(self, source);
    }
}
