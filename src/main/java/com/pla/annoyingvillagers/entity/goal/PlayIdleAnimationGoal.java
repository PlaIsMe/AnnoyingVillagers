package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.annoyingvillagers.compat.EfDancing;
import com.pla.annoyingvillagers.entity.JevEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraftforge.fml.ModList;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.*;

public class PlayIdleAnimationGoal extends Goal {
    private final Mob mob;
    private final int minDurationTicks;
    private int ticksLeft;

    private static List<String> keys(String prefix) {
        List<String> list = new ArrayList<>(20);
        for (int i = 1; i <= 20; i++) {
            list.add(prefix + "." + i);
        }
        return List.copyOf(list);
    }

    public PlayIdleAnimationGoal(Mob mob, int minDurationTicks) {
        this.mob = mob;
        this.minDurationTicks = minDurationTicks;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!ModList.get().isLoaded("efdancing")) return false;
        if (mob.level().isClientSide) return false;
        if (mob instanceof JevEntity) return false;
        if (mob.tickCount <= 30) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.getNavigation().isInProgress()) return false;
        if (!mob.onGround()) return false;
        if (mob instanceof AVNpc avNpc
                && (avNpc.isHealing()
                || avNpc.getPlayingIdleCooldown() != 0
                || avNpc.isStrolling())) {
            return false;
        }
        LivingEntityPatch<?> patch = null;
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch == null) return false;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, patch)) return false;
        return dynamicAnimation == Animations.EMPTY_ANIMATION;
    }

    @Override
    public boolean canContinueToUse() {
        if (!ModList.get().isLoaded("efdancing")) return false;
        if (mob.level().isClientSide) return false;
        if (mob instanceof JevEntity) return false;
        if (mob.tickCount <= 30) return false;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (mob.isPassenger()) return false;
        if (!mob.onGround()) return false;
        if (mob.getTarget() != null) return false;
        if (mob.getNavigation().isInProgress()) return false;
        if (mob instanceof AVNpc avNpc
                && (avNpc.isHealing()
                || avNpc.getPlayingIdleCooldown() != 0
                || avNpc.isStrolling())) {
            return false;
        }
        LivingEntityPatch<?> patch = null;
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch == null) return false;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, patch)) return false;
        return ticksLeft > 0;
    }

    @Override
    public void start() {
        if (!ModList.get().isLoaded("efdancing")) return;
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
        ticksLeft = minDurationTicks;

        mob.getNavigation().stop();
        mob.setDeltaMovement(0, 0, 0);
        IdleAnimation choice = null;
        if (mob instanceof AVNpc avNpc) {
            choice = avNpc.getIdleAnimationChoice();
        }
        if (choice == null) {
            choice = pickIdleAnimation();
            if (mob instanceof AVNpc avNpc) {
                avNpc.setIdleAnimationChoice(choice);
            }
        }

        AssetAccessor<? extends StaticAnimation> anim = resolveAnimation(choice);
        if (mob instanceof AVNpc avNpc) {
            avNpc.setIdleAnimation(anim);
        }

        if (mob instanceof AVNpc avNpc) {
            avNpc.setPlayingIdle(true);
        }

        IdleAnimation finalChoice = choice;
        new DelayedTask(30) {
            @Override
            public void run() {
                if (mob.getTarget() != null) return;
                if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
                playIdleAnimation(anim);
            }
        };
    }

    @Override
    public void tick() {
        if (!ModList.get().isLoaded("efdancing")) {
            ticksLeft = 0;
            return;
        }
        if (mob.getTarget() != null || mob.getNavigation().isInProgress() || !mob.onGround()) {
            ticksLeft = 0;
            return;
        }

        if (!(mob.level() instanceof ServerLevel)) return;

        mob.getNavigation().stop();
        mob.setDeltaMovement(0, 0, 0);

        LivingEntityPatch<?> patch = null;
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        AssetAccessor<? extends StaticAnimation> idleAnimation = null;
        if (mob instanceof AVNpc avNpc) {
            idleAnimation = avNpc.getIdleAnimation();
        }
        if (patch != null && idleAnimation != null) {
            AssetAccessor<? extends StaticAnimation> staticAnimation =
                    Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (staticAnimation == idleAnimation) {
                // correct animation, do nothing
            } else {
                if (mob instanceof AVNpc avNpc) {
                    playIdleAnimation(avNpc.getIdleAnimation());
                }
            }
        }
        ticksLeft--;
    }

    @Override
    public void stop() {
        if (mob instanceof AVNpc avNpc) {
            avNpc.clearIdleAnimationState();
            LivingEntityPatch<?> patch = avNpc.getLivingEntityPatch();
            if (patch != null) patch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
            avNpc.setPlayingIdle(false);
            avNpc.setPlayingIdleCooldown(new Random().nextInt(400, 1200));
        }
    }

    private void playIdleAnimation(AssetAccessor<? extends StaticAnimation> anim) {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
        LivingEntityPatch<?> patch = null;
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch != null) {
            patch.playAnimationSynchronized(anim, 0.0F);
        }
    }

    private IdleAnimation pickIdleAnimation() {
        IdleAnimation[] all = IdleAnimation.values();
        return all[mob.getRandom().nextInt(all.length)];
    }

    private AssetAccessor<? extends StaticAnimation> resolveAnimation(IdleAnimation idle) {
        return EfDancing.resolveIdleAnimation(idle);
    }
}

