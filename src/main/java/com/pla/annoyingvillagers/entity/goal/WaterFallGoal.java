package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class WaterFallGoal extends Goal {
    private static final float FALL_DAMAGE_DISTANCE = 3.0F;
    private static final double MIN_FALLING_Y_VELOCITY = -0.20D;
    private static final double MAX_GROUND_SCAN_DISTANCE = 6.0D;
    private static final double MIN_PLACE_DISTANCE = 1.5D;
    private static final double MAX_PLACE_DISTANCE = 5.5D;
    private static final double PLACE_DISTANCE_PADDING = 1.25D;
    private static final int PICKUP_DELAY_AFTER_CONTACT_TICKS = 5;
    private static final int MAX_ACTIVE_TICKS = 80;

    private final AVNpc avNpc;
    private BlockPos placePos;
    private BlockPos placedWaterPos;
    private ItemStack restoreOffhand = ItemStack.EMPTY;
    private int activeTicks;
    private int pickupDelayTicks = -1;
    private boolean finished;
    private boolean rigAttackLocked;

    public WaterFallGoal(AVNpc avNpc) {
        this.avNpc = avNpc;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel)
                || !this.avNpc.isAlive()
                || this.avNpc.isRemoved()
                || this.avNpc.isDeadOrDying()
                || this.avNpc.isNoAi()
                || this.avNpc.isPassenger()
                || this.avNpc.isHealing()
                || this.avNpc.onGround()
                || this.avNpc.isInWater()
                || this.avNpc.getDeltaMovement().y >= MIN_FALLING_Y_VELOCITY
                || this.avNpc.getWaterBucketCooldown() > 0
                || !InventoryUtils.hasItem(this.avNpc, Items.WATER_BUCKET)) {
            return false;
        }

        this.placePos = this.findTimedWaterPlacement(serverLevel);
        return this.placePos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.finished
                && this.placedWaterPos != null
                && this.activeTicks < MAX_ACTIVE_TICKS
                && this.avNpc.isAlive()
                && !this.avNpc.isRemoved()
                && !this.avNpc.isDeadOrDying()
                && this.avNpc.level() instanceof ServerLevel;
    }

    @Override
    public void start() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel)
                || this.placePos == null
                || this.avNpc.getWaterBucketCooldown() > 0
                || InventoryUtils.consumeItem(this.avNpc, Items.WATER_BUCKET, 1).isEmpty()) {
            this.reset();
            return;
        }

        this.lockRigAttack();
        this.restoreOffhand = this.avNpc.getItemInHand(InteractionHand.OFF_HAND).copy();
        if (this.avNpc.isUsingItem()) this.avNpc.stopUsingItem();
        this.avNpc.getNavigation().stop();
        this.avNpc.getLookControl().setLookAt(this.placePos.getX() + 0.5D, this.placePos.getY() + 0.5D, this.placePos.getZ() + 0.5D, 70.0F, 70.0F);
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.WATER_BUCKET));
        this.avNpc.swing(InteractionHand.OFF_HAND, true);

        serverLevel.setBlockAndUpdate(this.placePos, Blocks.WATER.defaultBlockState());
        this.placedWaterPos = this.placePos.immutable();
        this.giveOrDrop(new ItemStack(Items.BUCKET));
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.BUCKET));
        serverLevel.playSound(null, this.placePos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.avNpc.setWaterBucketCooldown();

        this.activeTicks = 0;
        this.pickupDelayTicks = -1;
        this.finished = false;
        this.placePos = null;
    }

    @Override
    public void tick() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel) || this.placedWaterPos == null) {
            this.finished = true;
            return;
        }

        this.activeTicks++;
        this.avNpc.getNavigation().stop();
        this.avNpc.getLookControl().setLookAt(this.placedWaterPos.getX() + 0.5D, this.placedWaterPos.getY() + 0.5D, this.placedWaterPos.getZ() + 0.5D, 70.0F, 70.0F);

        FluidState fluidState = serverLevel.getFluidState(this.placedWaterPos);
        if (!fluidState.is(FluidTags.WATER) || !fluidState.isSource()) {
            this.finished = true;
            return;
        }

        if (this.pickupDelayTicks < 0) {
            if (this.hasReachedLandingWater()) this.pickupDelayTicks = PICKUP_DELAY_AFTER_CONTACT_TICKS;
            return;
        }

        if (this.pickupDelayTicks > 0) {
            this.pickupDelayTicks--;
            return;
        }

        if (this.tryPickupWater(serverLevel)) this.finished = true;
    }

    @Override
    public void stop() {
        if (!this.finished && this.avNpc.level() instanceof ServerLevel serverLevel) this.tryPickupWater(serverLevel);
        this.restoreOffhand();
        this.reset();
    }

    private BlockPos findTimedWaterPlacement(ServerLevel serverLevel) {
        Vec3 motion = this.avNpc.getDeltaMovement();
        double projectedX = this.avNpc.getX() + motion.x;
        double projectedZ = this.avNpc.getZ() + motion.z;
        double feetY = this.avNpc.getBoundingBox().minY + 0.05D;
        Vec3 start = new Vec3(projectedX, feetY, projectedZ);
        Vec3 end = start.add(0.0D, -MAX_GROUND_SCAN_DISTANCE, 0.0D);
        BlockHitResult hitResult = serverLevel.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.avNpc));
        if (hitResult.getType() == HitResult.Type.MISS) return null;

        double distanceToGround = start.y - hitResult.getLocation().y;
        double placeDistance = Mth.clamp(-motion.y + PLACE_DISTANCE_PADDING, MIN_PLACE_DISTANCE, MAX_PLACE_DISTANCE);
        if (distanceToGround < 0.0D || distanceToGround > placeDistance) return null;
        if (this.avNpc.fallDistance + distanceToGround <= FALL_DAMAGE_DISTANCE) return null;

        BlockPos waterPos = hitResult.getBlockPos().above();
        return this.canPlaceWater(serverLevel, waterPos) ? waterPos.immutable() : null;
    }

    private boolean canPlaceWater(ServerLevel serverLevel, BlockPos pos) {
        if (!serverLevel.isInWorldBounds(pos) || !serverLevel.getWorldBorder().isWithinBounds(pos)) return false;

        FluidState fluidState = serverLevel.getFluidState(pos);
        if (fluidState.is(FluidTags.WATER)) return false;
        if (fluidState.is(FluidTags.LAVA)) return true;
        if (!fluidState.isEmpty()) return false;

        BlockState blockState = serverLevel.getBlockState(pos);
        if (!blockState.isAir() && !blockState.canBeReplaced()) return false;

        BlockPos belowPos = pos.below();
        BlockState belowState = serverLevel.getBlockState(belowPos);
        return belowState.isFaceSturdy(serverLevel, belowPos, Direction.UP) || !belowState.getCollisionShape(serverLevel, belowPos).isEmpty();
    }

    private boolean hasReachedLandingWater() {
        return this.avNpc.isInWater() || this.avNpc.onGround() || this.avNpc.fallDistance <= 0.0F && this.avNpc.getDeltaMovement().y >= -0.08D;
    }

    private boolean tryPickupWater(ServerLevel serverLevel) {
        if (this.placedWaterPos == null) return false;

        FluidState fluidState = serverLevel.getFluidState(this.placedWaterPos);
        if (!fluidState.is(FluidTags.WATER) || !fluidState.isSource()) return false;
        if (InventoryUtils.consumeItem(this.avNpc, Items.BUCKET, 1).isEmpty()) return false;

        serverLevel.setBlockAndUpdate(this.placedWaterPos, Blocks.AIR.defaultBlockState());
        this.giveOrDrop(new ItemStack(Items.WATER_BUCKET));
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.WATER_BUCKET));
        this.avNpc.swing(InteractionHand.OFF_HAND, true);
        serverLevel.playSound(null, this.placedWaterPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    private void lockRigAttack() {
        if (this.rigAttackLocked) return;
        this.avNpc.lock();
        this.rigAttackLocked = true;
    }

    private void unlockRigAttack() {
        if (!this.rigAttackLocked) return;
        this.avNpc.unlock();
        this.rigAttackLocked = false;
    }

    private void restoreOffhand() {
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, this.restoreOffhand.copy());
        this.restoreOffhand = ItemStack.EMPTY;
    }

    private void giveOrDrop(ItemStack stack) {
        if (!InventoryUtils.addItem(this.avNpc, stack)) this.avNpc.spawnAtLocation(stack);
    }

    private void reset() {
        this.unlockRigAttack();
        this.placePos = null;
        this.placedWaterPos = null;
        this.activeTicks = 0;
        this.pickupDelayTicks = -1;
        this.finished = false;
    }
}
