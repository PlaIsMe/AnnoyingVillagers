package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.potion.ObedienceMobEffect;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobTargetRedirectEvent {
    public static boolean shouldPreserveRedirectTarget(Mob mob) {
        LivingEntity currentTarget = mob.getTarget();
        return currentTarget != null
                && (getRedirectTarget(mob, currentTarget) != null || isActiveRedirectObjective(currentTarget));
    }

    @Nullable
    public static LivingEntity getRedirectTarget(Mob mob, @Nullable LivingEntity currentTarget) {
        if (currentTarget == null || mob instanceof BlueDemonEntity || mob instanceof BbqEntity) {
            return null;
        }

        if (currentTarget instanceof HerobrineMob herobrineMob
                && (herobrineMob.isSacrificing() || herobrineMob.isHealing())) {
            if (herobrineMob.getFirstPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            } else if (herobrineMob.getSecondPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            } else if (herobrineMob.getThirdPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            } else if (herobrineMob.getFourthPossessedHerobrine() instanceof LivingEntity living) {
                return normalizeRedirectTarget(living);
            }
        }

        if (currentTarget instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                && lowHerobrineCloneEntity.isHealing()
                && lowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowHerobrineCloneEntity.isAlive()) {
            return lowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (currentTarget instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowShadowHerobrineCloneEntity.isAlive()) {
            return lowShadowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (currentTarget instanceof NullEntity nullEntity) {
            if (nullEntity.getFirstWitherSkeleton() != null) {
                return normalizeRedirectTarget(nullEntity.getFirstWitherSkeleton());
            } else if (nullEntity.getSecondWitherSkeleton() != null) {
                return normalizeRedirectTarget(nullEntity.getSecondWitherSkeleton());
            }
        }

        if (currentTarget instanceof NullSkeletonEntity nullSkeletonEntity
                && nullSkeletonEntity.getNullEntity() != null
                && !nullSkeletonEntity.isAlive()) {
            return nullSkeletonEntity.getNullEntity();
        }

        if (currentTarget instanceof ReaperHerobrineEntity reaperHerobrineEntity
                && reaperHerobrineEntity.isPassenger()
                && reaperHerobrineEntity.getVehicle() instanceof HerobrineDragonEntity herobrineDragonEntity) {
            return herobrineDragonEntity;
        }

        if (currentTarget instanceof HerobrineDragonEntity herobrineDragonEntity
                && herobrineDragonEntity.getSummoner() instanceof ReaperHerobrineEntity reaperHerobrineEntity
                && !reaperHerobrineEntity.isPassenger()) {
            return reaperHerobrineEntity;
        }

        return null;
    }

    private static boolean shouldBlockVillagerKnightJevTarget(Mob mob, @Nullable LivingEntity target) {
        return target instanceof JevEntity && isVillagerKnight(mob);
    }

    @Nullable
    private static LivingEntity getVillagerKnightJevReplacementTarget(Mob mob, @Nullable LivingEntity target) {
        if (!(target instanceof JevEntity jev)) {
            return null;
        }

        AlexEntity alex = jev.getFollowTarget();
        if (alex != null && alex.isAlive() && !alex.isSpectator() && !mob.isAlliedTo(alex)) {
            return alex;
        }

        return null;
    }

    private static boolean isVillagerKnight(Mob mob) {
        return mob instanceof RedVillagerKnightEntity
                || mob instanceof BlueVillagerKnightEntity
                || mob instanceof GreenVillagerKnightEntity
                || mob instanceof PurpleVillagerKnightEntity;
    }

    private static LivingEntity normalizeRedirectTarget(LivingEntity redirectTarget) {
        if (redirectTarget instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                && lowHerobrineCloneEntity.isHealing()
                && lowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowHerobrineCloneEntity.isAlive()) {
            return lowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (redirectTarget instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                && (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null
                && !lowShadowHerobrineCloneEntity.isAlive()) {
            return lowShadowHerobrineCloneEntity.getPossessedByEntity();
        }

        if (redirectTarget instanceof NullSkeletonEntity nullSkeletonEntity
                && nullSkeletonEntity.getNullEntity() != null
                && !nullSkeletonEntity.isAlive()) {
            return nullSkeletonEntity.getNullEntity();
        }

        return redirectTarget;
    }

    private static boolean isActiveRedirectObjective(LivingEntity currentTarget) {
        if (currentTarget instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
            HerobrineMob owner = lowHerobrineCloneEntity.getPossessedByEntity();
            return lowHerobrineCloneEntity.isHealing()
                    && owner != null
                    && owner.isAlive()
                    && owner.isHealing();
        }

        if (currentTarget instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
            HerobrineMob owner = lowShadowHerobrineCloneEntity.getPossessedByEntity();
            return (lowShadowHerobrineCloneEntity.isSacrificing() || lowShadowHerobrineCloneEntity.isHealing())
                    && owner != null
                    && owner.isAlive()
                    && (owner.isSacrificing() || owner.isHealing());
        }

        if (currentTarget instanceof NullSkeletonEntity nullSkeletonEntity) {
            NullEntity nullEntity = nullSkeletonEntity.getNullEntity();
            return nullEntity != null
                    && nullEntity.isAlive()
                    && (nullEntity.getFirstWitherSkeleton() == nullSkeletonEntity
                    || nullEntity.getSecondWitherSkeleton() == nullSkeletonEntity);
        }

        if (currentTarget instanceof HerobrineDragonEntity herobrineDragonEntity) {
            return herobrineDragonEntity.getSummoner() instanceof ReaperHerobrineEntity reaperHerobrineEntity
                    && reaperHerobrineEntity.isAlive()
                    && reaperHerobrineEntity.isPassenger()
                    && reaperHerobrineEntity.getVehicle() == herobrineDragonEntity;
        }

        return false;
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Mob mob) {
            if (mob instanceof BlueDemonEntity || mob instanceof BbqEntity) return;

            LivingEntity currentTarget = mob.getTarget();
            if (shouldBlockVillagerKnightJevTarget(mob, currentTarget)) {
                mob.setTarget(getVillagerKnightJevReplacementTarget(mob, currentTarget));
                return;
            }

            LivingEntity redirectTarget = getRedirectTarget(mob, currentTarget);
            if (redirectTarget != null) {
                mob.setTarget(redirectTarget);
                if (currentTarget instanceof ReaperHerobrineEntity
                        && redirectTarget instanceof HerobrineDragonEntity
                        && (mob instanceof AVNpc || (ModList.get().isLoaded("smart_npc") && SmartNpc.isSmartNpc(mob)))) {
                    CommonUtil.swapToBow(mob);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        if (!ObedienceMobEffect.isObedientMob(mob)) {
            return;
        }

        LivingEntity newTarget = event.getNewTarget();
        if (shouldBlockVillagerKnightJevTarget(mob, newTarget)) {
            event.setNewTarget(getVillagerKnightJevReplacementTarget(mob, newTarget));
            return;
        }

        if (newTarget != null && ObedienceMobEffect.shouldBlockTarget(mob, newTarget)) {
            event.setNewTarget(null);
        }
    }
}
