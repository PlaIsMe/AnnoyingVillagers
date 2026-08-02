package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundRigAnimation;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
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

import java.util.Arrays;

public final class RigAnimationController {
    private static final int LUNGE_MOVEMENT_TICKS = 6;
    private static final int JUMP_LUNGE_DELAY_TICKS = 4;
    private static final int JUMP_STARTUP_TICKS = 5;

    private RigAnimationController() {
    }

    public static void play(Mob mob, RigAnimationId animationId) {
        play(mob, RigAnimationSpecs.get(animationId), null);
    }

    public static void play(Mob mob, RigAnimationSpec spec, LivingEntity target) {
        if (mob.level().isClientSide || !mob.isAlive() || mob.isRemoved()) {
            return;
        }

        int startupTicks = jumpStartupTicks(spec);
        if (target != null && target.isAlive()) {
            faceTarget(mob, target);
        }

        if (startupTicks > 0) {
            sendAnimation(mob, RigAnimationId.JUMP, startupTicks);
            applyJump(mob, spec.jumpStrength());
            new DelayedTask(startupTicks) {
                @Override
                public void run() {
                    playAfterStartup(mob, spec, target, false);
                }
            };
            return;
        }

        playAfterStartup(mob, spec, target, true);
    }

    public static int animationPlaybackTicks(RigAnimationSpec spec) {
        return spec.durationTicks() + jumpStartupTicks(spec);
    }

    private static void playAfterStartup(Mob mob, RigAnimationSpec spec, LivingEntity target, boolean applyJumpMovement) {
        if (mob.level().isClientSide || !mob.isAlive() || mob.isRemoved()) {
            return;
        }

        if (target != null && target.isAlive()) {
            faceTarget(mob, target);
        }

        if (spec.animationId().isAttack()) {
            mob.swing(InteractionHand.MAIN_HAND, true);
        }
        mob.setAggressive(true);
        sendAnimation(mob, spec.animationId(), spec.durationTicks());
        logAvNpcAnimation(mob, target, spec);
        scheduleRigSounds(mob, spec);
        applyMovement(mob, target, spec, applyJumpMovement);

        if (spec.damagesTarget() && target != null) {
            scheduleImpacts(mob, target, spec);
        }
    }

    private static int jumpStartupTicks(RigAnimationSpec spec) {
        return spec.animationId() != RigAnimationId.JUMP && usesJumpMovement(spec) ? JUMP_STARTUP_TICKS : 0;
    }

    private static boolean usesJumpMovement(RigAnimationSpec spec) {
        return spec.movementType() == RigMovementType.JUMP || spec.movementType() == RigMovementType.JUMP_LUNGE;
    }

    private static void logAvNpcAnimation(Mob mob, LivingEntity target, RigAnimationSpec spec) {
        if (!(mob instanceof AVNpc)) {
            return;
        }

        AnnoyingVillagers.LOGGER.info(
                "[AV RIG] AVNpc {} played {} target={} duration={} attackWindows={} movement={} lunge={} jump={} pos={}",
                entityLabel(mob),
                spec.animationId(),
                target == null ? "none" : entityLabel(target),
                spec.durationTicks(),
                Arrays.toString(spec.attackWindows()),
                spec.movementType(),
                spec.lungeDistanceBlocks(),
                spec.jumpStrength(),
                mob.blockPosition()
        );
    }

    private static String entityLabel(Entity entity) {
        String type = entity.getEncodeId();
        if (type == null) {
            type = entity.getType().toString();
        }

        return type + "#" + entity.getId();
    }

    private static void sendAnimation(Mob mob, RigAnimationId animationId, int durationTicks) {
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> mob),
                new ClientboundRigAnimation(mob.getId(), animationId, durationTicks)
        );
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

        playStepSound(mob);
    }

    private static void scheduleAttackSwingSounds(Mob mob, RigAnimationSpec spec) {
        RigAttackWindow[] attackWindows = spec.attackWindows();
        SoundEvent sound = spec.animationId().isUltimateAttack()
                ? AnnoyingVillagersModSounds.WHOOSH_SHARP.get()
                : AnnoyingVillagersModSounds.SWORD_WHOOSH.get();

        if (attackWindows.length == 0) {
            playSound(mob, sound, 0.9F, randomPitch(mob, 0.95F, 0.1F));
            return;
        }

        for (RigAttackWindow attackWindow : attackWindows) {
            new DelayedTask(attackWindow.startTickInclusive()) {
                @Override
                public void run() {
                    if (mob.isAlive() && !mob.isRemoved() && !mob.isDeadOrDying()) {
                        playSound(mob, sound, 0.9F, randomPitch(mob, 0.95F, 0.1F));
                    }
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
        entity.level().playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                sound,
                SoundSource.HOSTILE,
                volume,
                pitch
        );
    }

    private static void scheduleImpacts(Mob mob, LivingEntity target, RigAnimationSpec spec) {
        RigAttackWindow[] attackWindows = spec.attackWindows();
        boolean forceDamageThroughHurtCooldown = attackWindows.length > 1;

        for (RigAttackWindow attackWindow : attackWindows) {
            scheduleAttackWindow(mob, target, spec, attackWindow, forceDamageThroughHurtCooldown);
        }
    }

    private static void scheduleAttackWindow(Mob mob, LivingEntity target, RigAnimationSpec spec, RigAttackWindow attackWindow, boolean forceDamageThroughHurtCooldown) {
        boolean[] attemptedHit = new boolean[1];
        for (int delayTicks = attackWindow.startTickInclusive(); delayTicks < attackWindow.endTickExclusive(); delayTicks++) {
            new DelayedTask(delayTicks) {
                @Override
                public void run() {
                    if (attemptedHit[0] || !canDamageTarget(mob, target, spec)) {
                        return;
                    }

                    attemptedHit[0] = true;
                    if (hurtTarget(mob, target, forceDamageThroughHurtCooldown)) {
                        playHitSound(target);
                    }
                }
            };
        }
    }

    private static boolean hurtTarget(Mob mob, LivingEntity target, boolean forceDamageThroughHurtCooldown) {
        if (!forceDamageThroughHurtCooldown) {
            return mob.doHurtTarget(target);
        }

        int previousInvulnerableTime = target.invulnerableTime;
        target.invulnerableTime = 0;
        boolean hurt = mob.doHurtTarget(target);
        target.invulnerableTime = previousInvulnerableTime;
        return hurt;
    }

    private static boolean canDamageTarget(Mob mob, LivingEntity target, RigAnimationSpec spec) {
        if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) {
            return false;
        }
        if (!target.isAlive() || target.isRemoved() || target.isDeadOrDying()) {
            return false;
        }
        if (mob.isAlliedTo(target) || target.isAlliedTo(mob) || !mob.canAttack(target)) {
            return false;
        }

        double reach = Math.max(spec.attackReachBlocks(), mob.getBbWidth() * 2.0D + target.getBbWidth());
        return mob.distanceToSqr(target) <= reach * reach;
    }

    private static void applyMovement(Mob mob, LivingEntity target, RigAnimationSpec spec, boolean applyJumpMovement) {
        RigMovementType movementType = spec.movementType();
        if (movementType == RigMovementType.NONE) {
            return;
        }

        Vec3 forward = horizontalDirection(mob, target);
        if (applyJumpMovement && usesJumpMovement(spec)) {
            applyJump(mob, spec.jumpStrength());
        }

        switch (movementType) {
            case LUNGE -> scheduleMove(mob, forward, spec.lungeDistanceBlocks(), 0, LUNGE_MOVEMENT_TICKS);
            case JUMP_LUNGE -> scheduleMove(mob, forward, spec.lungeDistanceBlocks(), JUMP_LUNGE_DELAY_TICKS, LUNGE_MOVEMENT_TICKS);
            case MOVE_FORWARD -> scheduleMove(mob, forward, spec.lungeDistanceBlocks(), 0, spec.durationTicks());
            case MOVE_BACKWARD -> scheduleMove(mob, forward.scale(-1.0D), spec.lungeDistanceBlocks(), 0, spec.durationTicks());
            case MOVE_RIGHT -> scheduleMove(mob, rightOf(forward), spec.lungeDistanceBlocks(), 0, spec.durationTicks());
            case MOVE_LEFT -> scheduleMove(mob, rightOf(forward).scale(-1.0D), spec.lungeDistanceBlocks(), 0, spec.durationTicks());
            default -> {
            }
        }
    }

    private static void applyJump(Mob mob, double jumpStrength) {
        if (jumpStrength <= 0.0D) {
            return;
        }

        Vec3 motion = mob.getDeltaMovement();
        mob.setDeltaMovement(motion.x, Math.max(motion.y, jumpStrength), motion.z);
        mob.hasImpulse = true;
    }

    private static void scheduleMove(Mob mob, Vec3 direction, double distanceBlocks, int startDelayTicks, int movementTicks) {
        if (distanceBlocks <= 0.0D || movementTicks <= 0 || direction.lengthSqr() < 1.0E-6D) {
            return;
        }

        Vec3 step = direction.normalize().scale(distanceBlocks / movementTicks);
        for (int i = 0; i < movementTicks; i++) {
            new DelayedTask(startDelayTicks + i) {
                @Override
                public void run() {
                    if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) {
                        return;
                    }

                    mob.move(MoverType.SELF, step);
                    mob.hasImpulse = true;
                }
            };
        }
    }

    private static void faceTarget(Mob mob, LivingEntity target) {
        mob.getLookControl().setLookAt(target, 60.0F, 60.0F);
        mob.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
    }

    private static Vec3 horizontalDirection(Mob mob, Entity target) {
        Vec3 direction;
        if (target != null) {
            direction = target.position().subtract(mob.position());
        } else {
            direction = mob.getLookAngle();
        }

        direction = new Vec3(direction.x, 0.0D, direction.z);
        if (direction.lengthSqr() < 1.0E-6D) {
            direction = Vec3.directionFromRotation(0.0F, mob.getYRot());
            direction = new Vec3(direction.x, 0.0D, direction.z);
        }

        if (direction.lengthSqr() < 1.0E-6D) {
            return new Vec3(0.0D, 0.0D, 1.0D);
        }

        return direction.normalize();
    }

    private static Vec3 rightOf(Vec3 forward) {
        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);
        if (right.lengthSqr() < 1.0E-6D) {
            return new Vec3(1.0D, 0.0D, 0.0D);
        }

        return right.normalize();
    }
}
