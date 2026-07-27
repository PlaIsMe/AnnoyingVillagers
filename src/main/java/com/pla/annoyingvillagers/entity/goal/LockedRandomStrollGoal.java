package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

public class LockedRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
    public LockedRandomStrollGoal(PathfinderMob mob, double speed) {
        super(mob, speed);
    }

    private boolean isPlayingIdle() {
        if (mob instanceof AVNpc avNpc) return avNpc.isPlayingIdle();
        return false;
    }

    private void setStrolling(boolean strolling) {
        if (mob instanceof AVNpc avNpc) avNpc.setStrolling(strolling);
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null) return false;
        if (isPlayingIdle()) return false;
        LivingEntityPatch<?> patch = null;
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch == null) return false;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, patch)) return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.getTarget() != null) return false;
        if (isPlayingIdle()) return false;
        LivingEntityPatch<?> patch = null;
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch == null) return false;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, patch)) return false;
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        setStrolling(true);
        LivingEntityPatch<?> patch = null;
        if (mob instanceof AVNpc avNpc) {
            patch = avNpc.getLivingEntityPatch();
        }
        if (patch != null) {
        patch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
        }
        super.start();
    }

    @Override
    public void stop() {
        setStrolling(false);
        super.stop();
    }
}
