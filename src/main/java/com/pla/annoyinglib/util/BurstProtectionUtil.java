package com.pla.annoyinglib.util;

import com.pla.annoyinglib.rig.RigCriticalUtil;
import com.pla.annoyinglib.rig.RigStunController;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/** Default burst-protection policy; entity-specific interface overrides still take precedence. */
public final class BurstProtectionUtil {
    private BurstProtectionUtil() {}

    public static boolean shouldIgnoreBurstProtection(LivingEntity self, DamageSource source) {
        return RigStunController.isStunned(self) || RigCriticalUtil.isCriticalDamage(self, source);
    }
}
