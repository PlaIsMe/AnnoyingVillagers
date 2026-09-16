package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.ArmoredHerobrineEntity;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorController;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public final class ArmoredHerobrineArmorGoal extends AnimatedMobGoal {
    private static final int MIN_COOLDOWN_TICKS = 20 * 20;
    private static final int MAX_COOLDOWN_TICKS = 45 * 20;
    private static final int RETRY_DELAY_TICKS = 20;
    private final ArmoredHerobrineEntity mob;
    private int nextUseTick;

    public ArmoredHerobrineArmorGoal(ArmoredHerobrineEntity mob) {
        super(mob);
        this.mob = mob;
        this.nextUseTick = mob.tickCount + randomCooldown();
    }

    @Override
    public boolean canUse() {
        if (!canAct() || this.mob.tickCount < this.nextUseTick
                || ObsidianArmorController.isActive(this.mob)) return false;
        LivingEntity target = this.mob.getTarget();
        return target != null && target.isAlive() && !target.isRemoved() && this.mob.canAttack(target);
    }

    @Override
    public void start() {
        boolean activated = ObsidianArmorController.activateArmoredHerobrine(this.mob);
        this.nextUseTick = this.mob.tickCount + (activated ? randomCooldown() : RETRY_DELAY_TICKS);
    }

    @Override
    public boolean canContinueToUse() {
        return canAct() && ObsidianArmorController.isActive(this.mob);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        ObsidianArmorController.clear(this.mob);
    }

    private boolean canAct() {
        return this.mob.level() instanceof ServerLevel && this.mob.isAlive()
                && !this.mob.isRemoved() && !this.mob.isNoAi() && !isAnimationStunned();
    }

    private int randomCooldown() {
        return MIN_COOLDOWN_TICKS + this.mob.getRandom().nextInt(MAX_COOLDOWN_TICKS - MIN_COOLDOWN_TICKS + 1);
    }
}
