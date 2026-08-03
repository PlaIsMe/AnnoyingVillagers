package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.EnumSet;

public class UseLiquidBucketGoal extends Goal {
    private static final int WATER_PICKUP_DELAY_TICKS = 5;
    private static final int LAVA_PICKUP_DELAY_TICKS = 40;
    private static final int MAX_PICKUP_TICKS = 80;
    private static final int COMBAT_SEARCH_INTERVAL_TICKS = 20;
    private static final double MAX_COMBAT_PLACE_DISTANCE_SQR = 36.0D;
    private static final double RANDOM_COMBAT_PLACE_CHANCE = 0.08D;

    private final AVNpc avNpc;
    private BlockPos placePos;
    private BlockPos placedLiquidPos;
    private Item bucketItem = Items.AIR;
    private Block liquidBlock = Blocks.AIR;
    private SoundEvent emptySound = SoundEvents.BUCKET_EMPTY;
    private ItemStack restoreOffhand = ItemStack.EMPTY;
    private long nextCombatSearchTick;
    private int pickupDelayTicks;
    private int pickupTicks;
    private boolean pickupLiquid;
    private boolean finished;

    public UseLiquidBucketGoal(AVNpc avNpc) {
        this.avNpc = avNpc;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel)
                || !this.avNpc.isAlive()
                || this.avNpc.isNoAi()
                || this.avNpc.isPassenger()
                || this.avNpc.isHealing()
                || !this.avNpc.onGround()
                || this.avNpc.getWaterBucketCooldown() > 0
                || !InventoryUtils.hasItem(this.avNpc, InventoryUtils::isLiquidBucketStack)) {
            return false;
        }

        if ((this.avNpc.isOnFire() || this.avNpc.isInLava())
                && this.canUseWaterBucket()
                && InventoryUtils.hasItem(this.avNpc, Items.WATER_BUCKET)) {
            LivingEntity target = this.avNpc.getTarget();
            this.placePos = target == null ? null : this.findCombatPlacement(serverLevel, target, Items.WATER_BUCKET);
            if (this.placePos == null) {
                this.placePos = this.findEmergencyWaterPlacement(serverLevel);
            }
            if (this.placePos != null) {
                this.bucketItem = Items.WATER_BUCKET;
                this.liquidBlock = Blocks.WATER;
                this.emptySound = SoundEvents.BUCKET_EMPTY;
                this.pickupLiquid = true;
                return true;
            }
        }

        long gameTime = serverLevel.getGameTime();
        if (gameTime < this.nextCombatSearchTick) {
            return false;
        }
        this.nextCombatSearchTick = gameTime + COMBAT_SEARCH_INTERVAL_TICKS + this.avNpc.getRandom().nextInt(10);

        LivingEntity target = this.avNpc.getTarget();
        if (target == null
                || !target.isAlive()
                || target.isRemoved()
                || target.isDeadOrDying()
                || this.avNpc.distanceToSqr(target) > MAX_COMBAT_PLACE_DISTANCE_SQR
                || this.avNpc.getRandom().nextDouble() > RANDOM_COMBAT_PLACE_CHANCE) {
            return false;
        }

        Item chosenBucket = this.chooseCombatBucket(target);
        if (chosenBucket == Items.AIR) {
            return false;
        }

        this.placePos = this.findCombatPlacement(serverLevel, target, chosenBucket);
        if (this.placePos == null) {
            return false;
        }

        this.bucketItem = chosenBucket;
        this.liquidBlock = chosenBucket == Items.LAVA_BUCKET ? Blocks.LAVA : Blocks.WATER;
        this.emptySound = chosenBucket == Items.LAVA_BUCKET ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        this.pickupLiquid = true;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.pickupLiquid
                && !this.finished
                && this.placedLiquidPos != null
                && this.pickupTicks < MAX_PICKUP_TICKS
                && this.avNpc.isAlive()
                && !this.avNpc.isNoAi()
                && !this.avNpc.isPassenger()
                && this.avNpc.level() instanceof ServerLevel;
    }

    @Override
    public void start() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel)
                || this.placePos == null
                || this.bucketItem == Items.AIR
                || this.liquidBlock == Blocks.AIR
                || InventoryUtils.consumeItem(this.avNpc, this.bucketItem, 1).isEmpty()) {
            this.reset();
            return;
        }

        this.restoreOffhand = this.avNpc.getItemInHand(InteractionHand.OFF_HAND).copy();
        this.avNpc.getNavigation().stop();
        this.avNpc.getLookControl().setLookAt(this.placePos.getX() + 0.5D, this.placePos.getY() + 0.5D, this.placePos.getZ() + 0.5D, 40.0F, 40.0F);
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(this.bucketItem));
        this.avNpc.swing(InteractionHand.OFF_HAND, true);

        serverLevel.setBlockAndUpdate(this.placePos, this.liquidBlock.defaultBlockState());
        this.giveOrDrop(new ItemStack(Items.BUCKET));
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.BUCKET));
        serverLevel.playSound(null, this.placePos, this.emptySound, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.avNpc.setWaterBucketCooldown();

        if (this.liquidBlock == Blocks.WATER) {
            this.avNpc.clearFire();
        }

        if (this.pickupLiquid) {
            this.placedLiquidPos = this.placePos.immutable();
            this.pickupDelayTicks = pickupDelayFor(this.liquidBlock);
            this.pickupTicks = 0;
            this.finished = false;
        } else {
            this.finished = true;
        }
        this.placePos = null;
    }

    @Override
    public void stop() {
        if (this.pickupLiquid
                && !this.finished
                && this.avNpc.level() instanceof ServerLevel serverLevel) {
            this.tryPickupLiquid(serverLevel);
        }

        this.restoreOffhand();
        this.reset();
    }

    @Override
    public void tick() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel) || this.placedLiquidPos == null) {
            this.finished = true;
            return;
        }

        this.pickupTicks++;
        this.avNpc.getNavigation().stop();
        this.avNpc.getLookControl().setLookAt(this.placedLiquidPos.getX() + 0.5D, this.placedLiquidPos.getY() + 0.5D, this.placedLiquidPos.getZ() + 0.5D, 40.0F, 40.0F);

        if (this.pickupDelayTicks > 0) {
            this.pickupDelayTicks--;
            return;
        }

        FluidState fluidState = serverLevel.getFluidState(this.placedLiquidPos);
        if (filledBucketFor(fluidState) == Items.AIR) {
            this.finished = true;
            return;
        }

        if (this.tryPickupLiquid(serverLevel)) {
            this.finished = true;
        }
    }

    private Item chooseCombatBucket(LivingEntity target) {
        boolean hasLava = InventoryUtils.hasItem(this.avNpc, Items.LAVA_BUCKET);

        if (hasLava && !target.isInWater()) {
            return Items.LAVA_BUCKET;
        }
        return Items.AIR;
    }

    private boolean canUseWaterBucket() {
        return this.avNpc.getEnderPearlCooldown() == 0;
    }

    private BlockPos findEmergencyWaterPlacement(ServerLevel serverLevel) {
        BlockPos feet = this.avNpc.blockPosition();
        BlockPos[] candidates = {
                feet,
                feet.relative(this.avNpc.getDirection()),
                feet.relative(this.avNpc.getDirection().getOpposite())
        };

        for (BlockPos candidate : candidates) {
            if (this.canPlaceLiquid(serverLevel, candidate, Items.WATER_BUCKET, true)) {
                return candidate.immutable();
            }
        }

        return null;
    }

    private BlockPos findCombatPlacement(ServerLevel serverLevel, LivingEntity target, Item bucket) {
        BlockPos feet = target.blockPosition();
        Direction facing = Direction.fromYRot(target.getYRot());
        BlockPos[] candidates = {
                feet,
                feet.relative(facing.getOpposite()),
                feet.relative(facing.getClockWise()),
                feet.relative(facing.getCounterClockWise())
        };

        for (BlockPos candidate : candidates) {
            if (this.canPlaceLiquid(serverLevel, candidate, bucket, false)) {
                return candidate.immutable();
            }
        }

        return null;
    }

    private boolean canPlaceLiquid(ServerLevel serverLevel, BlockPos pos, Item bucket, boolean allowReplacingLava) {
        if (!serverLevel.isInWorldBounds(pos) || !serverLevel.getWorldBorder().isWithinBounds(pos)) {
            return false;
        }

        if (!this.hasGroundSupport(serverLevel, pos)) {
            return false;
        }

        FluidState fluidState = serverLevel.getFluidState(pos);
        if (bucket == Items.WATER_BUCKET && allowReplacingLava && fluidState.is(FluidTags.LAVA)) {
            return true;
        }

        BlockState blockState = serverLevel.getBlockState(pos);
        return (blockState.isAir() || blockState.canBeReplaced()) && fluidState.isEmpty();
    }

    private boolean hasGroundSupport(ServerLevel serverLevel, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = serverLevel.getBlockState(belowPos);
        return belowState.getFluidState().isEmpty()
                && (belowState.isFaceSturdy(serverLevel, belowPos, Direction.UP)
                || !belowState.getCollisionShape(serverLevel, belowPos).isEmpty());
    }

    private boolean tryPickupLiquid(ServerLevel serverLevel) {
        if (this.placedLiquidPos == null) {
            return false;
        }

        FluidState fluidState = serverLevel.getFluidState(this.placedLiquidPos);
        Item filledBucket = filledBucketFor(fluidState);
        if (filledBucket == Items.AIR) {
            return false;
        }
        if (InventoryUtils.consumeItem(this.avNpc, Items.BUCKET, 1).isEmpty()) {
            return false;
        }

        serverLevel.setBlockAndUpdate(this.placedLiquidPos, Blocks.AIR.defaultBlockState());
        this.giveOrDrop(new ItemStack(filledBucket));
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(filledBucket));
        this.avNpc.swing(InteractionHand.OFF_HAND, true);
        serverLevel.playSound(null, this.placedLiquidPos, fillSoundFor(filledBucket), SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    private static Item filledBucketFor(FluidState fluidState) {
        if (!fluidState.isSource()) {
            return Items.AIR;
        }
        if (fluidState.is(FluidTags.WATER)) {
            return Items.WATER_BUCKET;
        }
        if (fluidState.is(FluidTags.LAVA)) {
            return Items.LAVA_BUCKET;
        }

        return Items.AIR;
    }

    private static SoundEvent fillSoundFor(Item filledBucket) {
        return filledBucket == Items.LAVA_BUCKET ? SoundEvents.BUCKET_FILL_LAVA : SoundEvents.BUCKET_FILL;
    }

    private static int pickupDelayFor(Block liquidBlock) {
        return liquidBlock == Blocks.LAVA ? LAVA_PICKUP_DELAY_TICKS : WATER_PICKUP_DELAY_TICKS;
    }

    private void restoreOffhand() {
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, this.restoreOffhand.copy());
        this.restoreOffhand = ItemStack.EMPTY;
    }

    private void giveOrDrop(ItemStack stack) {
        if (!InventoryUtils.addItem(this.avNpc, stack)) {
            this.avNpc.spawnAtLocation(stack);
        }
    }

    private void reset() {
        this.placePos = null;
        this.placedLiquidPos = null;
        this.bucketItem = Items.AIR;
        this.liquidBlock = Blocks.AIR;
        this.emptySound = SoundEvents.BUCKET_EMPTY;
        this.pickupDelayTicks = 0;
        this.pickupTicks = 0;
        this.pickupLiquid = false;
        this.finished = false;
    }
}
