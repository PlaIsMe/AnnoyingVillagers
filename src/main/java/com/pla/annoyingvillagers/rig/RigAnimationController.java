package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.network.ClientboundRigAnimation;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public final class RigAnimationController {
    private static final int LUNGE_MOVEMENT_TICKS = 6;
    private static final int JUMP_LUNGE_DELAY_TICKS = 4;

    private RigAnimationController() {
    }

    public static void play(Mob mob, RigAnimationId animationId) {
        play(mob, RigAnimationSpecs.get(animationId), null);
    }

    public static void play(Mob mob, RigAnimationSpec spec, LivingEntity target) {
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
        applyMovement(mob, target, spec);

        if (spec.damagesTarget() && target != null) {
            scheduleImpact(mob, target, spec);
        }
    }

    private static void logAvNpcAnimation(Mob mob, LivingEntity target, RigAnimationSpec spec) {
        if (!(mob instanceof AVNpc)) {
            return;
        }

        AnnoyingVillagers.LOGGER.info(
                "[AV RIG] AVNpc {} played {} target={} duration={} impactDelay={} movement={} lunge={} jump={} pos={}",
                entityLabel(mob),
                spec.animationId(),
                target == null ? "none" : entityLabel(target),
                spec.durationTicks(),
                spec.impactDelayTicks(),
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

    private static void scheduleImpact(Mob mob, LivingEntity target, RigAnimationSpec spec) {
        int delayTicks = Math.max(0, spec.impactDelayTicks());

        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (canDamageTarget(mob, target, spec)) {
                    mob.doHurtTarget(target);
                }
            }
        };
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

    private static void applyMovement(Mob mob, LivingEntity target, RigAnimationSpec spec) {
        RigMovementType movementType = spec.movementType();
        if (movementType == RigMovementType.NONE) {
            return;
        }

        Vec3 forward = horizontalDirection(mob, target);
        if (movementType == RigMovementType.JUMP || movementType == RigMovementType.JUMP_LUNGE) {
            applyJump(mob, spec.jumpStrength());
        }

        switch (movementType) {
            case LUNGE -> scheduleMove(mob, forward, spec.lungeDistanceBlocks(), 0, LUNGE_MOVEMENT_TICKS);
            case JUMP_LUNGE -> scheduleMove(mob, forward, spec.lungeDistanceBlocks(), JUMP_LUNGE_DELAY_TICKS, LUNGE_MOVEMENT_TICKS);
            case ROLL_FORWARD -> scheduleMove(mob, forward, spec.lungeDistanceBlocks(), 0, LUNGE_MOVEMENT_TICKS);
            case ROLL_BACKWARD -> scheduleMove(mob, forward.scale(-1.0D), spec.lungeDistanceBlocks(), 0, LUNGE_MOVEMENT_TICKS);
            case ROLL_RIGHT -> scheduleMove(mob, rightOf(forward), spec.lungeDistanceBlocks(), 0, LUNGE_MOVEMENT_TICKS);
            case ROLL_LEFT -> scheduleMove(mob, rightOf(forward).scale(-1.0D), spec.lungeDistanceBlocks(), 0, LUNGE_MOVEMENT_TICKS);
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
        Vec3 right = new Vec3(forward.z, 0.0D, -forward.x);
        if (right.lengthSqr() < 1.0E-6D) {
            return new Vec3(1.0D, 0.0D, 0.0D);
        }

        return right.normalize();
    }
}
