package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;

public interface HerobrinePortalSupportCaster {
    Mob getPortalSupportMob();

    boolean canUseSupportPortalAction();

    int getPortalActionCooldown();

    void setPortalActionCooldown();

    int getLowCloneSupportCooldown();

    void setLowCloneSupportCooldown();

    int getAvailableCombatLowCloneSupportSlotCount();

    boolean hasAvailableCombatLowCloneSupportSlot();

    boolean claimCombatLowCloneSupportSlot(Entity clone);

    boolean canSupportPortalAlly(LivingEntity ally);

    void playPortalSupportAnimation(RigAnimationId animationId, @Nullable LivingEntity lookTarget);

    default void markPortalSupport() {
    }
}
