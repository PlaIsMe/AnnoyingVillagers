package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class WaterEnderPearlEscapeGoal extends Goal {
    private static final double SEARCH_RADIUS = 48.0D;
    private static final double SEARCH_RADIUS_SQR = SEARCH_RADIUS * SEARCH_RADIUS;
    private static final int SEARCH_INTERVAL_TICKS = 10;
    private static final float YAW_STEP = 10.0F;
    private static final float[] PITCHES = {-20.0F, -8.0F, 0.0F, 2.0F, 4.0F, 8.0F, 14.0F, 24.0F, 36.0F};

    private final Mob mob;
    private Vec3 pearlTarget;
    private long nextSearchTick;

    public WaterEnderPearlEscapeGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(this.mob.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        if (!this.mob.isAlive()
                || this.mob.isRemoved()
                || this.mob.isDeadOrDying()
                || this.mob.isNoAi()
                || this.mob.isPassenger()
                || !this.mob.isInWater()
                || !this.canUsePearl()
                || this.isLongHitAnimationActive()) {
            return false;
        }

        long gameTime = serverLevel.getGameTime();
        if (gameTime < this.nextSearchTick) {
            return false;
        }

        this.nextSearchTick = gameTime + SEARCH_INTERVAL_TICKS + this.mob.getRandom().nextInt(5);
        this.pearlTarget = this.findVisibleEscapeTarget(serverLevel);
        return this.pearlTarget != null;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        if (this.pearlTarget == null) {
            return;
        }

        LivingEntityPatch<?> patch = this.getLivingEntityPatch();
        if (patch != null) {
            patch.playAnimationSynchronized(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F);
        }

        this.mob.getNavigation().stop();
        this.mob.getLookControl().setLookAt(this.pearlTarget.x, this.pearlTarget.y, this.pearlTarget.z, 60.0F, 60.0F);
//        this.mob.swing(InteractionHand.OFF_HAND, true);
        if (CombatBehaviour.throwEnderPearlAt(this.mob, this.pearlTarget)) {
            this.setPearlCooldown();
        }
        this.pearlTarget = null;
    }

    private boolean canUsePearl() {
        if (this.mob instanceof AVNpc avNpc) {
            return !avNpc.isHealing()
                    && avNpc.getEnderPearlCooldown() == 0
                    && InventoryUtils.hasItem(avNpc, Items.ENDER_PEARL);
        }

        return false;
    }

    private void setPearlCooldown() {
        if (this.mob instanceof AVNpc avNpc) {
            avNpc.setEnderPearlCooldown();
        }
    }

    private boolean isLongHitAnimationActive() {
        LivingEntityPatch<?> patch = this.getLivingEntityPatch();
        if (patch == null) {
            return false;
        }

        var player = patch.getAnimator().getPlayerFor(null);
        if (player == null) {
            return false;
        }

        AssetAccessor<? extends StaticAnimation> animation = player.getRealAnimation();
        return animation != null && EpicfightUtil.isLongHitAnimation(animation, patch);
    }

    private LivingEntityPatch<?> getLivingEntityPatch() {
        return EpicFightCapabilities.getEntityPatch(this.mob, LivingEntityPatch.class);
    }

    private Vec3 findVisibleEscapeTarget(ServerLevel level) {
        Vec3 eyePos = this.mob.getEyePosition(1.0F);
        Set<BlockPos> checked = new HashSet<>();
        Vec3 bestTarget = null;
        double bestDistance = Double.MAX_VALUE;

        for (float pitch : PITCHES) {
            for (float yawOffset = 0.0F; yawOffset < 360.0F; yawOffset += YAW_STEP) {
                Vec3 direction = directionFromRotation(pitch, this.mob.getYRot() + yawOffset);
                Vec3 end = eyePos.add(direction.scale(SEARCH_RADIUS));
                BlockHitResult hit = level.clip(new ClipContext(
                        eyePos,
                        end,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        this.mob
                ));

                if (hit.getType() != HitResult.Type.BLOCK) {
                    continue;
                }

                BlockPos hitPos = hit.getBlockPos().immutable();
                if (!checked.add(hitPos)) {
                    continue;
                }

                Vec3 target = this.getEscapeTarget(level, hitPos);
                if (target == null) {
                    continue;
                }

                double distance = eyePos.distanceToSqr(target);
                if (distance <= SEARCH_RADIUS_SQR && distance < bestDistance) {
                    bestDistance = distance;
                    bestTarget = target;
                }
            }
        }

        return bestTarget;
    }

    private Vec3 getEscapeTarget(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        VoxelShape shape = state.getCollisionShape(level, pos);

        if (state.isAir()
                || !state.blocksMotion()
                || shape.isEmpty()
                || !level.getFluidState(pos).isEmpty()) {
            return null;
        }

        double surfaceY = pos.getY() + shape.max(Direction.Axis.Y);
        Vec3 standCenter = new Vec3(pos.getX() + 0.5D, surfaceY, pos.getZ() + 0.5D);
        AABB currentBox = this.mob.getBoundingBox();
        double halfWidth = currentBox.getXsize() * 0.5D;
        AABB standBox = new AABB(
                standCenter.x - halfWidth,
                surfaceY,
                standCenter.z - halfWidth,
                standCenter.x + halfWidth,
                surfaceY + currentBox.getYsize(),
                standCenter.z + halfWidth
        );

        if (!level.noCollision(this.mob, standBox) || level.containsAnyLiquid(standBox)) {
            return null;
        }

        return standCenter.add(0.0D, 0.15D, 0.0D);
    }

    private static Vec3 directionFromRotation(float pitch, float yaw) {
        float radians = (float) (Math.PI / 180.0D);
        float pitchRad = pitch * radians;
        float yawRad = -yaw * radians - (float) Math.PI;
        float x = Mth.sin(yawRad) * Mth.cos(pitchRad);
        float y = -Mth.sin(pitchRad);
        float z = Mth.cos(yawRad) * Mth.cos(pitchRad);
        return new Vec3(x, y, z);
    }
}
