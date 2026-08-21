package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundRigAnimation;
import com.pla.annoyingvillagers.rig.pose.RigPoseLibrary;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class RigAnimationController {
    private static final Map<UUID, ActiveAnimationState> ACTIVE_ANIMATIONS = new HashMap<>();

    private RigAnimationController() {
    }

    public static void play(Mob mob, RigAnimationId animationId) {
        play(mob, RigAnimationSpecs.get(animationId), null);
    }

    public static void play(Mob mob, RigAnimationSpec spec, LivingEntity target) {
        if (mob.level().isClientSide || !mob.isAlive() || mob.isRemoved() || !canPlayWhileMounted(mob, spec) || isProfileAttackLocked(mob, spec.animationId())) return;
        playNow(mob, spec, target);
    }

    public static void lockProfileAttacksFor(Mob mob, int ticks) {
        if (mob.level().isClientSide || !(mob instanceof LockableRigAttackAnimation lockable) || ticks <= 0) return;
        lockable.lock();
        new DelayedTask(ticks) {
            @Override
            public void run() {
                lockable.unlock();
            }
        };
    }

    public static void lockProfileAttacksFor(Mob mob, RigAnimationId animationId) {
        lockProfileAttacksFor(mob, RigAnimationSpecs.get(animationId).durationTicks());
    }

    public static int animationPlaybackTicks(RigAnimationSpec spec) {
        return spec.durationTicks();
    }

    private static void playNow(Mob mob, RigAnimationSpec spec, LivingEntity target) {
        if (mob.level().isClientSide || !mob.isAlive() || mob.isRemoved() || !canPlayWhileMounted(mob, spec) || isProfileAttackLocked(mob, spec.animationId())) return;
        if (target != null && target.isAlive()) faceTarget(mob, target);
        if (spec.animationId().isAttack()) mob.swing(InteractionHand.MAIN_HAND, true);
        if (spec.jumpOnStart() && mob.onGround()) mob.getJumpControl().jump();

        ActiveAnimationState state = recordActiveAnimation(mob, spec);
        runHooks(mob, spec, RigAnimationSpec.RigTimedAnimationHook.START);
        scheduleTimedHooks(mob, spec, state);
        scheduleAnimationEnd(mob, spec, state);
        sendAnimation(mob, spec.animationId(), spec.durationTicks());
        scheduleRigSounds(mob, spec);
        scheduleAnimationMotion(mob, target, spec, state);
        if (spec.damagesTarget()) scheduleCollisions(mob, spec, state);
    }

    public static boolean isInActiveAttackWindow(Mob mob) {
        ActiveAnimationState state = getActiveAnimationState(mob);
        if (state == null || !state.spec().damagesTarget()) return false;
        int elapsedTicks = state.elapsedTicks(mob);
        for (RigAttackWindow attackWindow : state.spec().attackWindows()) if (attackWindow.contains(elapsedTicks)) return true;
        return false;
    }

    public static boolean hasActiveAnimation(Mob mob) {
        return getActiveAnimationState(mob) != null;
    }

    public static boolean hasActiveProfileAttack(Mob mob) {
        ActiveAnimationState state = getActiveAnimationState(mob);
        return state != null && RigCombatProfiles.isProfileAttack(state.spec().animationId());
    }

    public static void clearActiveAnimations() {
        ACTIVE_ANIMATIONS.clear();
    }

    private static boolean isProfileAttackLocked(Mob mob, RigAnimationId animationId) {
        return RigCombatProfiles.isProfileAttack(animationId) && mob instanceof LockableRigAttackAnimation lockable && lockable.isLocked();
    }

    private static boolean canPlayWhileMounted(Mob mob, RigAnimationSpec spec) {
        return !mob.isPassenger() || !spec.animationId().isAttack() || spec.animationId().isMountedAttack();
    }

    private static ActiveAnimationState recordActiveAnimation(Mob mob, RigAnimationSpec spec) {
        ActiveAnimationState state = new ActiveAnimationState(spec, mob.tickCount);
        ACTIVE_ANIMATIONS.put(mob.getUUID(), state);
        return state;
    }

    private static ActiveAnimationState getActiveAnimationState(Mob mob) {
        ActiveAnimationState state = ACTIVE_ANIMATIONS.get(mob.getUUID());
        if (state == null) return null;
        int elapsedTicks = state.elapsedTicks(mob);
        if (elapsedTicks < 0 || elapsedTicks > state.spec().durationTicks()) {
            ACTIVE_ANIMATIONS.remove(mob.getUUID(), state);
            return null;
        }
        return state;
    }

    private record ActiveAnimationState(RigAnimationSpec spec, int startTick) {
        private int elapsedTicks(Mob mob) {
            return mob.tickCount - this.startTick;
        }
    }

    private static boolean isCurrentAnimation(Mob mob, ActiveAnimationState state) {
        return ACTIVE_ANIMATIONS.get(mob.getUUID()) == state;
    }

    private static void sendAnimation(Mob mob, RigAnimationId animationId, int durationTicks) {
        AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob), new ClientboundRigAnimation(mob.getId(), animationId, durationTicks));
    }

    private static void scheduleAnimationEnd(Mob mob, RigAnimationSpec spec, ActiveAnimationState state) {
        new DelayedTask(spec.durationTicks()) {
            @Override
            public void run() {
                if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) {
                    ACTIVE_ANIMATIONS.remove(mob.getUUID(), state);
                    return;
                }
                if (ACTIVE_ANIMATIONS.remove(mob.getUUID(), state)) runHooks(mob, spec, RigAnimationSpec.RigTimedAnimationHook.END);
            }
        };
    }

    private static void scheduleTimedHooks(Mob mob, RigAnimationSpec spec, ActiveAnimationState state) {
        for (RigAnimationSpec.RigTimedAnimationHook timedHook : spec.timedHooks()) {
            if (!timedHook.isTimed()) continue;
            new DelayedTask(timedHook.tick()) {
                @Override
                public void run() {
                    if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying() || !isCurrentAnimation(mob, state)) return;
                    runHook(mob, timedHook.action());
                }
            };
        }
    }

    private static void runHooks(Mob mob, RigAnimationSpec spec, int tick) {
        for (RigAnimationSpec.RigTimedAnimationHook timedHook : spec.timedHooks()) if (timedHook.tick() == tick) runHook(mob, timedHook.action());
    }

    private static void runHook(Mob mob, RigAnimationSpec.RigAnimationHook hook) {
        try {
            hook.run(mob);
        } catch (Exception e) {
            AnnoyingVillagers.LOGGER.error("[AV MOD DEBUG] Rig animation hook failed for {}", mob.getName().getString(), e);
        }
    }

    private static void scheduleRigSounds(Mob mob, RigAnimationSpec spec) {
        RigAnimationId animationId = spec.animationId();
        if (animationId.isAttack()) {
            scheduleAttackSwingSounds(mob, spec);
            return;
        }
        if (animationId.isRollAnimation()) {
            playSound(mob, AnnoyingVillagersModSounds.ROLL.get(), 0.9F, randomPitch(mob, 0.95F, 0.1F));
            return;
        }
        if (!animationId.isUtilityAnimation()) playStepSound(mob);
    }

    private static void scheduleAttackSwingSounds(Mob mob, RigAnimationSpec spec) {
        RigAttackWindow[] attackWindows = spec.attackWindows();
        SoundEvent sound = attackWindows.length > 1 ? AnnoyingVillagersModSounds.WHOOSH_SHARP.get() : AnnoyingVillagersModSounds.SWORD_WHOOSH.get();
        if (attackWindows.length == 0) {
            playSound(mob, sound, 0.9F, randomPitch(mob, 0.95F, 0.1F));
            return;
        }
        for (RigAttackWindow attackWindow : attackWindows) {
            new DelayedTask(attackWindow.startTickInclusive()) {
                @Override
                public void run() {
                    if (mob.isAlive() && !mob.isRemoved() && !mob.isDeadOrDying()) playSound(mob, sound, 0.9F, randomPitch(mob, 0.95F, 0.1F));
                }
            };
        }
    }

    private static void playStepSound(Mob mob) {
        BlockPos onPos = mob.getOnPos();
        BlockState state = mob.level().getBlockState(onPos);
        SoundType soundType = state.getSoundType();
        playSound(mob, soundType.getHitSound(), soundType.getVolume() * 0.35F, soundType.getPitch());
    }

    private static void playHitSound(LivingEntity target) {
        playSound(target, AnnoyingVillagersModSounds.BLADE_HIT.get(), 0.9F, randomPitch(target, 0.95F, 0.1F));
    }

    private static float randomPitch(Entity entity, float basePitch, float variance) {
        return basePitch + (entity.level().random.nextFloat() * 2.0F - 1.0F) * variance;
    }

    private static void playSound(Entity entity, SoundEvent sound, float volume, float pitch) {
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.HOSTILE, volume, pitch);
    }

    private static void scheduleCollisions(Mob mob, RigAnimationSpec spec, ActiveAnimationState state) {
        RigAttackWindow[] attackWindows = spec.attackWindows();
        boolean forceDamageThroughHurtCooldown = attackWindows.length > 1;
        for (RigAttackWindow attackWindow : attackWindows) scheduleAttackWindow(mob, attackWindow, state, forceDamageThroughHurtCooldown);
    }

    private static void scheduleAttackWindow(Mob mob, RigAttackWindow attackWindow, ActiveAnimationState state, boolean forceDamageThroughHurtCooldown) {
        Set<UUID> hitEntities = new HashSet<>();
        for (int elapsedTick = attackWindow.startTickInclusive(); elapsedTick < attackWindow.endTickExclusive(); elapsedTick++) {
            final int sampleTick = elapsedTick;
            new DelayedTask(sampleTick) {
                @Override
                public void run() {
                    if (!isCurrentAnimation(mob, state) || !mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
                    for (LivingEntity target : RigColliderSystem.findHits(mob, state.spec(), attackWindow, sampleTick)) {
                        if (!canDamageTarget(mob, target) || !hitEntities.add(target.getUUID())) continue;
                        if (hurtTarget(mob, target, forceDamageThroughHurtCooldown)) playHitSound(target);
                    }
                }
            };
        }
    }

    private static boolean hurtTarget(Mob mob, LivingEntity target, boolean forceDamageThroughHurtCooldown) {
        if (!forceDamageThroughHurtCooldown) {
            boolean hurt = mob.doHurtTarget(target);
            if (hurt) spawnHitParticle(mob, target);
            return hurt;
        }
        int previousInvulnerableTime = target.invulnerableTime;
        target.invulnerableTime = 0;
        boolean hurt = mob.doHurtTarget(target);
        target.invulnerableTime = previousInvulnerableTime;
        if (hurt) spawnHitParticle(mob, target);
        return hurt;
    }

    private static void spawnHitParticle(Mob mob, LivingEntity target) {
        if (!(mob.level() instanceof ServerLevel)) return;
//        AnnoyingVillagersModParticleTypes.BLADE_RUSH.get().spawnParticleWithArgument(...);
    }

    private static boolean canDamageTarget(Mob mob, LivingEntity target) {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return false;
        if (!target.isAlive() || target.isRemoved() || target.isDeadOrDying()) return false;
        if (target == mob.getVehicle()) return false;
        return !mob.isAlliedTo(target) && !target.isAlliedTo(mob) && mob.canAttack(target);
    }

    private static void scheduleAnimationMotion(Mob mob, LivingEntity target, RigAnimationSpec spec, ActiveAnimationState state) {
        RigAnimationId animationId = spec.animationId();
        if (!RigPoseLibrary.hasMotion(animationId)) return;
        Vec3 forward = horizontalDirection(mob, target);
        for (int elapsedTick = 1; elapsedTick <= spec.durationTicks(); elapsedTick++) {
            final int currentElapsedTick = elapsedTick;
            new DelayedTask(currentElapsedTick - 1) {
                @Override
                public void run() {
                    if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying() || !isCurrentAnimation(mob, state)) return;
                    Vec3 delta = RigPoseLibrary.worldMotionDelta(animationId, currentElapsedTick - 1.0F, currentElapsedTick, forward);
                    if (delta.lengthSqr() < 1.0E-8D) return;
                    mob.move(MoverType.SELF, delta);
                    mob.hasImpulse = true;
                }
            };
        }
    }

    private static void faceTarget(Mob mob, LivingEntity target) {
        mob.getLookControl().setLookAt(target, 60.0F, 60.0F);
        mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
        if (mob.isPassenger() && mob.getVehicle() instanceof Mob mount) {
            mount.getLookControl().setLookAt(target, 60.0F, 60.0F);
            mount.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
        }
    }

    private static Vec3 horizontalDirection(Mob mob, Entity target) {
        Vec3 direction = target != null ? target.position().subtract(mob.position()) : mob.getLookAngle();
        direction = new Vec3(direction.x, 0.0D, direction.z);
        if (direction.lengthSqr() < 1.0E-6D) {
            direction = Vec3.directionFromRotation(0.0F, mob.getYRot());
            direction = new Vec3(direction.x, 0.0D, direction.z);
        }
        return direction.lengthSqr() < 1.0E-6D ? new Vec3(0.0D, 0.0D, 1.0D) : direction.normalize();
    }
}
