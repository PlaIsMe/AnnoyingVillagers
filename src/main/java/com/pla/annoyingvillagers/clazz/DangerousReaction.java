package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiFunction;

public interface DangerousReaction {
    double DANGEROUS_REACTION_DISTANCE = 8.0D;
    double MAX_PLACE_BLOCK_GROUND_GAP = 2.0D;
    int PLACE_BLOCK_INITIAL_DELAY = 1;
    int PLACE_BLOCK_LAYER_INTERVAL = 3;
    int PLACE_BLOCK_LANE_INTERVAL = 2;

    default RigAnimationId getDangerousReactionAnimation(Mob mob) {
        return RigAnimationId.ROLL_BACKWARD;
    }

    default void performDangerousReaction(Mob mob) {
        if (!(mob.level() instanceof ServerLevel serverLevel) || !canReact(mob)) return;

        LivingEntity target = mob.getTarget();
        if (target != null) mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        Vec3 away;
        if (target != null) {
            Vec3 toTarget = new Vec3(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ());
            away = toTarget.lengthSqr() > 1.0E-6D ? toTarget.normalize().scale(-1.0D) : Vec3.ZERO;
        } else {
            float yawRad = mob.yBodyRot * Mth.DEG_TO_RAD;
            away = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad)).normalize().scale(-1.0D);
        }

        if (away.lengthSqr() <= 1.0E-6D) return;
        Vec3 right = new Vec3(-away.z, 0.0D, away.x).normalize();

        mob.getNavigation().stop();
        RigAnimationController.play(mob, this.getDangerousReactionAnimation(mob));

        double backMag = 0.55D + mob.getRandom().nextDouble() * 0.35D;
        double strafeMag = (mob.getRandom().nextBoolean() ? 1 : -1) * (0.05D + mob.getRandom().nextDouble() * 0.15D);
        Vec3 impulse = away.scale(backMag).add(right.scale(strafeMag));
        mob.setDeltaMovement(mob.getDeltaMovement().add(impulse.x, 0.0D, impulse.z));
        mob.hasImpulse = true;

        int pulses = 2 + mob.getRandom().nextInt(2);
        for (int i = 1; i <= pulses; i++) {
            Vec3 tail = away.scale(0.16D + mob.getRandom().nextDouble() * 0.10D)
                    .add(right.scale((mob.getRandom().nextDouble() - 0.5D) * 0.10D));
            int delay = i * 2;
            new DelayedTask(delay) {
                @Override
                public void run() {
                    if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying() || RigStunController.isStunned(mob)) return;
                    mob.setDeltaMovement(mob.getDeltaMovement().add(tail.x, 0.0D, tail.z));
                    mob.hasImpulse = true;
                }
            };
        }

        int jumpDelay = pulses * 2 + 1;
        new DelayedTask(jumpDelay) {
            @Override
            public void run() {
                if (!mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying() || !mob.onGround() || RigStunController.isStunned(mob)) return;
                if (mob instanceof AVNpc avNpc) avNpc.shortPillarJump();
                else mob.getJumpControl().jump();
                RigAnimationController.play(mob, RigAnimationId.JUMP);
            }
        };

        if ((mob instanceof SteveEntity || mob instanceof AngrySteveEntity)
                && mob instanceof AVNpc avNpc
                && InventoryUtils.hasPlaceableBlock(avNpc)) {
            new DelayedTask(1) {
                @Override
                public void run() {
                    if (mob.isAlive() && !mob.isRemoved() && CommonUtil.isGroundWithin(mob, MAX_PLACE_BLOCK_GROUND_GAP)) {
                        placeRandomFrontWall(serverLevel, avNpc);
                    }
                }
            };
        }

        this.afterDangerousReaction(mob, serverLevel);
    }

    default void afterDangerousReaction(Mob mob, ServerLevel serverLevel) {
    }

    static boolean hasDangerousTarget(Mob mob) {
        LivingEntity target = mob == null ? null : mob.getTarget();
        return target instanceof Mob targetMob && target.isAlive() && !target.isRemoved() && RigAnimationController.isDangerous(targetMob);
    }

    static boolean canReact(Mob mob) {
        LivingEntity target = mob == null ? null : mob.getTarget();
        return mob != null
                && !mob.level().isClientSide
                && mob.isAlive()
                && !mob.isRemoved()
                && !mob.isDeadOrDying()
                && !mob.isNoAi()
                && !(mob instanceof ReaperHerobrineEntity reaper && reaper.isSecondFormDragonRider())
                && !RigStunController.isStunned(mob)
                && target instanceof Mob targetMob
                && target.isAlive()
                && !target.isRemoved()
                && RigAnimationController.isDangerous(targetMob)
                && mob.distanceToSqr(target) <= DANGEROUS_REACTION_DISTANCE * DANGEROUS_REACTION_DISTANCE;
    }

    static BiFunction<Integer, Integer, int[]> getIntegerIntegerBiFunction(Mob mob, int rot) {
        Direction facing = mob.getDirection();
        int fx = facing.getStepX();
        int fz = facing.getStepZ();
        int rx = -fz;
        int rz = fx;

        for (int i = 0; i < rot; i++) {
            int nfx = rx;
            int nfz = rz;
            int nrx = -fz;
            int nrz = fx;
            fx = nfx;
            fz = nfz;
            rx = nrx;
            rz = nrz;
        }

        int finalRx = rx;
        int finalFx = fx;
        int finalRz = rz;
        int finalFz = fz;
        return (a, b) -> new int[]{a * finalRx + b * finalFx, a * finalRz + b * finalFz};
    }

    private static void placeRandomFrontWall(ServerLevel serverLevel, AVNpc avNpc) {
        if (!CommonUtil.isGroundWithin(avNpc, MAX_PLACE_BLOCK_GROUND_GAP) || !InventoryUtils.hasPlaceableBlock(avNpc)) return;

        LivingEntity target = avNpc.getTarget();
        Direction dir = target != null
                ? Direction.getNearest(target.getX() - avNpc.getX(), 0.0D, target.getZ() - avNpc.getZ())
                : avNpc.getDirection();

        int lanes = 1 + avNpc.getRandom().nextInt(3);
        for (int dist = 1; dist <= lanes; dist++) {
            if (avNpc.getRandom().nextFloat() < 0.25F) continue;

            int pattern = avNpc.getRandom().nextInt(11);
            int rot = avNpc.getRandom().nextInt(4);
            BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(avNpc, rot);
            BlockPos baseXZ = avNpc.blockPosition().relative(dir, dist);
            int topY = Mth.floor(avNpc.getY() + avNpc.getBbHeight());
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
            int laneStartDelay = PLACE_BLOCK_INITIAL_DELAY + (dist - 1) * PLACE_BLOCK_LANE_INTERVAL;

            for (int y = surfaceY; y <= topY; y++) {
                int layer = y - surfaceY;
                BlockPos center = new BlockPos(baseXZ.getX(), y, baseXZ.getZ());
                if (!serverLevel.getBlockState(center).canBeReplaced()) break;

                int[][] extras = switch (pattern) {
                    case 0 -> new int[][]{};
                    case 1 -> layer == 3 ? new int[][]{{1, 0}} : new int[][]{};
                    case 2 -> layer == 0 ? new int[][]{{-1, 0}, {1, 0}, {2, 0}} : layer == 1 ? new int[][]{{1, 0}} : new int[][]{};
                    case 3 -> layer == 1 ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};
                    case 4 -> layer == 0 ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};
                    case 5 -> new int[][]{{1, 0}};
                    case 6 -> layer <= 1 ? new int[][]{{1, 0}} : new int[][]{};
                    case 7 -> layer == 0 ? new int[][]{{1, 0}} : new int[][]{};
                    case 8 -> layer == 1 ? new int[][]{{1, 0}} : new int[][]{};
                    case 9 -> layer == 0 ? new int[][]{{-1, 0}} : new int[][]{};
                    default -> layer == 1 ? new int[][]{{-1, 0}} : new int[][]{};
                };

                int delay = laneStartDelay + layer * PLACE_BLOCK_LAYER_INTERVAL;
                new DelayedTask(delay) {
                    @Override
                    public void run() {
                        if (!avNpc.isAlive() || avNpc.isRemoved() || !CommonUtil.isGroundWithin(avNpc, MAX_PLACE_BLOCK_GROUND_GAP)) return;
                        placeIfReplaceable(serverLevel, center, avNpc);
                        for (int[] extra : extras) {
                            int[] dxz = toWorld.apply(extra[0], extra[1]);
                            placeIfReplaceable(serverLevel, center.offset(dxz[0], 0, dxz[1]), avNpc);
                        }
                    }
                };
            }
        }
    }

    private static void placeIfReplaceable(ServerLevel serverLevel, BlockPos pos, AVNpc avNpc) {
        if (!serverLevel.getBlockState(pos).canBeReplaced()) return;
        ItemStack blockStack = InventoryUtils.consumePlaceableBlock(avNpc).orElse(ItemStack.EMPTY);
        if (blockStack.isEmpty()) return;
        BlockState state = InventoryUtils.getBlockState(blockStack);
        if (state == null) return;
        avNpc.swing(InteractionHand.MAIN_HAND, true);
        avNpc.playSound(SoundEvents.STONE_PLACE, 2.0F, 1.0F);
        serverLevel.setBlockAndUpdate(pos, state);
    }
}
