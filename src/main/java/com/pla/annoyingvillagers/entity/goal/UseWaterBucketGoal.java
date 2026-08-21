package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;

import java.util.EnumSet;

public class UseWaterBucketGoal extends Goal {
    private static final int PICKUP_DELAY_TICKS = 18;
    private static final int MAX_PICKUP_TICKS = 60;

    private final AVNpc avNpc;
    private BlockPos placePos;
    private BlockPos placedWaterPos;
    private ItemStack restoreOffhand = ItemStack.EMPTY;
    private int pickupDelayTicks;
    private int pickupTicks;
    private boolean finished;
    private boolean rigAttackLocked;

    public UseWaterBucketGoal(AVNpc avNpc) {
        this.avNpc = avNpc;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel)
                || !this.avNpc.isAlive()
                || this.avNpc.isNoAi()
                || this.avNpc.isPassenger()
                || this.avNpc.isHealing()
                || RigAnimationController.hasActiveProfileAttack(this.avNpc)
                || !this.avNpc.onGround()
                || !InventoryUtils.hasItem(this.avNpc, Items.WATER_BUCKET)
                || this.avNpc.getWaterBucketCooldown() > 0) {
            return false;
        }

        if (!this.avNpc.isOnFire() && !this.avNpc.isInLava()) {
            return false;
        }

        this.placePos = this.findPlacement(serverLevel);
        return this.placePos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.finished
                && this.placedWaterPos != null
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
                || InventoryUtils.consumeItem(this.avNpc, Items.WATER_BUCKET, 1).isEmpty()) {
            this.placePos = null;
            return;
        }

        this.lockRigAttack();
        this.restoreOffhand = this.avNpc.getItemInHand(InteractionHand.OFF_HAND).copy();
        this.avNpc.getNavigation().stop();
        this.avNpc.getLookControl().setLookAt(this.placePos.getX() + 0.5D, this.placePos.getY() + 0.5D, this.placePos.getZ() + 0.5D, 40.0F, 40.0F);
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.WATER_BUCKET));
        this.avNpc.swing(InteractionHand.OFF_HAND, true);

        serverLevel.setBlockAndUpdate(this.placePos, Blocks.WATER.defaultBlockState());
        this.placedWaterPos = this.placePos.immutable();
        this.pickupDelayTicks = PICKUP_DELAY_TICKS;
        this.pickupTicks = 0;
        this.finished = false;
        this.avNpc.clearFire();
        this.giveOrDrop(new ItemStack(Items.BUCKET));
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.BUCKET));
        serverLevel.playSound(null, this.placePos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        this.avNpc.setWaterBucketCooldown();
        this.placePos = null;
    }

    @Override
    public void stop() {
        if (!this.finished
                && this.pickupDelayTicks <= 0
                && this.avNpc.level() instanceof ServerLevel serverLevel) {
            this.tryPickupWater(serverLevel);
        }

        this.placePos = null;
        this.placedWaterPos = null;
        this.restoreOffhand();
        this.unlockRigAttack();
        this.pickupDelayTicks = 0;
        this.pickupTicks = 0;
        this.finished = false;
    }

    @Override
    public void tick() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel) || this.placedWaterPos == null) {
            this.finished = true;
            return;
        }

        this.pickupTicks++;
        this.avNpc.getNavigation().stop();
        this.avNpc.getLookControl().setLookAt(this.placedWaterPos.getX() + 0.5D, this.placedWaterPos.getY() + 0.5D, this.placedWaterPos.getZ() + 0.5D, 40.0F, 40.0F);

        if (this.pickupDelayTicks > 0) {
            this.pickupDelayTicks--;
            return;
        }

        FluidState fluidState = serverLevel.getFluidState(this.placedWaterPos);
        if (!fluidState.is(FluidTags.WATER) || !fluidState.isSource()) {
            this.finished = true;
            return;
        }

        if (this.tryPickupWater(serverLevel)) {
            this.finished = true;
        }
    }

    private BlockPos findPlacement(ServerLevel serverLevel) {
        BlockPos feet = this.avNpc.blockPosition();
        BlockPos[] candidates = {
                feet,
                feet.above(),
                feet.below(),
                feet.relative(this.avNpc.getDirection()),
                feet.relative(this.avNpc.getDirection().getOpposite())
        };

        for (BlockPos candidate : candidates) {
            if (this.canPlaceWater(serverLevel, candidate)) {
                return candidate.immutable();
            }
        }

        return null;
    }

    private boolean canPlaceWater(ServerLevel serverLevel, BlockPos pos) {
        return serverLevel.isInWorldBounds(pos)
                && serverLevel.getWorldBorder().isWithinBounds(pos)
                && (serverLevel.getBlockState(pos).isAir() || serverLevel.getFluidState(pos).is(FluidTags.LAVA));
    }

    private boolean tryPickupWater(ServerLevel serverLevel) {
        if (this.placedWaterPos == null) {
            return false;
        }

        FluidState fluidState = serverLevel.getFluidState(this.placedWaterPos);
        if (!fluidState.is(FluidTags.WATER) || !fluidState.isSource()) {
            return false;
        }
        if (InventoryUtils.consumeItem(this.avNpc, Items.BUCKET, 1).isEmpty()) {
            return false;
        }

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
        if (!InventoryUtils.addItem(this.avNpc, stack)) {
            this.avNpc.spawnAtLocation(stack);
        }
    }
}
