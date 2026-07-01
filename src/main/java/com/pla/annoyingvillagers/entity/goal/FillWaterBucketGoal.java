package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FillWaterBucketGoal extends Goal {
    private static final int SEARCH_RADIUS = 10;
    private static final int SEARCH_INTERVAL_TICKS = 40;
    private static final int MAX_USE_TICKS = 120;

    private final Mob mob;
    private final double speedModifier;
    private BlockPos waterPos;
    private long nextSearchTick;
    private int useTicks;

    public FillWaterBucketGoal(Mob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(this.mob.level() instanceof ServerLevel serverLevel)) {
            return false;
        }
        if (!this.mob.isAlive()
                || this.mob.isPassenger()
                || this.mob.isNoAi()
                || this.mob.getTarget() != null
                || this.isHealing()
                || !InventoryUtils.hasItem(this.mob, Items.BUCKET)) {
            return false;
        }

        long gameTime = serverLevel.getGameTime();
        if (gameTime < this.nextSearchTick) {
            return false;
        }

        this.nextSearchTick = gameTime + SEARCH_INTERVAL_TICKS + this.mob.getRandom().nextInt(20);
        this.waterPos = this.findNearestSourceWater(serverLevel);
        return this.waterPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.waterPos != null
                && this.useTicks < MAX_USE_TICKS
                && this.mob.getTarget() == null
                && InventoryUtils.hasItem(this.mob, Items.BUCKET)
                && this.mob.level() instanceof ServerLevel serverLevel
                && this.isSourceWater(serverLevel, this.waterPos);
    }

    @Override
    public void start() {
        this.useTicks = 0;
        this.moveToWater();
    }

    @Override
    public void stop() {
        this.waterPos = null;
        this.useTicks = 0;
    }

    @Override
    public void tick() {
        if (!(this.mob.level() instanceof ServerLevel serverLevel) || this.waterPos == null) {
            return;
        }

        this.useTicks++;
        this.mob.getLookControl().setLookAt(
                this.waterPos.getX() + 0.5D,
                this.waterPos.getY() + 0.5D,
                this.waterPos.getZ() + 0.5D,
                30.0F,
                30.0F
        );

        if (this.mob.distanceToSqr(Vec3.atCenterOf(this.waterPos)) > 4.0D) {
            if (this.useTicks % 20 == 0) {
                this.moveToWater();
            }
            return;
        }

        if (!this.isSourceWater(serverLevel, this.waterPos)
                || InventoryUtils.consumeItem(this.mob, Items.BUCKET, 1).isEmpty()) {
            return;
        }

        serverLevel.setBlockAndUpdate(this.waterPos, Blocks.AIR.defaultBlockState());
        InventoryUtils.addItem(this.mob, new ItemStack(Items.WATER_BUCKET));
        serverLevel.playSound(null, this.waterPos, SoundEvents.BUCKET_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        this.stop();
    }

    private void moveToWater() {
        if (this.waterPos == null) {
            return;
        }

        this.mob.getNavigation().moveTo(
                this.waterPos.getX() + 0.5D,
                this.waterPos.getY(),
                this.waterPos.getZ() + 0.5D,
                this.speedModifier
        );
    }

    private BlockPos findNearestSourceWater(ServerLevel serverLevel) {
        BlockPos origin = this.mob.blockPosition();
        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;

        for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
            for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; dz++) {
                for (int dy = -3; dy <= 3; dy++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (!this.isSourceWater(serverLevel, pos)) {
                        continue;
                    }

                    double distance = origin.distSqr(pos);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        best = pos.immutable();
                    }
                }
            }
        }

        return best;
    }

    private boolean isSourceWater(ServerLevel serverLevel, BlockPos pos) {
        return serverLevel.getFluidState(pos).is(FluidTags.WATER)
                && serverLevel.getFluidState(pos).isSource();
    }

    private boolean isHealing() {
        if (this.mob instanceof PlayerNpcEntity playerNpcEntity) {
            return playerNpcEntity.isHealing();
        }
        if (this.mob instanceof AVNpc avNpc) {
            return avNpc.isHealing();
        }
        return false;
    }
}
