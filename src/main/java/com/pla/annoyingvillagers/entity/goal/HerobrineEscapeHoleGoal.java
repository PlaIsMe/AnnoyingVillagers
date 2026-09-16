package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.blockentity.CryingObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.pose.RigPoseLibrary;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Four-block authored FLY_UP pillar cycles for non-Null Herobrine mobs. */
public class HerobrineEscapeHoleGoal extends AdvancedEscapeHoleGoal<HerobrineMob> {
    private static final int BLOCKS_PER_CYCLE = 4;
    private static final int[] PILLAR_PLACEMENT_TICKS = {3, 6, 9, 12};
    private static final String ESCAPE_TRANSIENT_TAG = "AVHerobrineEscapeTransient";
    private static final Map<UUID, PillarCycle> ACTIVE_PILLARS = new HashMap<>();

    private int cyclesRemaining;
    private int cycleStartTick;
    private double cycleStartY;
    private boolean previousNoGravity;
    public RigAnimationId exitRoll;
    private boolean clearingCycle;
    private int retryAfterTick;
    private final RigEscapeObstructionBreaker obstructionBreaker = new RigEscapeObstructionBreaker();

    public HerobrineEscapeHoleGoal(HerobrineMob mob) {
        super(mob);
        PillarCycle stale = ACTIVE_PILLARS.get(mob.getUUID());
        if (stale != null && stale.mob != mob) ACTIVE_PILLARS.remove(mob.getUUID(), stale);
    }

    public static int cyclesForUpAmount(int upAmountBlocks) {
        return Math.max(0, (upAmountBlocks + BLOCKS_PER_CYCLE - 1) / BLOCKS_PER_CYCLE);
    }

    public static void clearActivePillarCycles() {
        ACTIVE_PILLARS.clear();
    }

    protected boolean isFlyUpAnimationActive() {
//            AV_EFM patch with epicfight animations
        return RigAnimationController.getActiveAnimationId(this.mob) == RigAnimationId.FLY_UP;
    }

    /** Called only by the four FLY_UP RigAnimationSpecs hooks. */
    public static void placeFlyUpPillarBlock(Mob mob, int blockIndex) {
        PillarCycle cycle = ACTIVE_PILLARS.get(mob.getUUID());
        if (cycle == null || cycle.mob != mob) return;
        // Animation hooks and the authoritative goal tick may reach the same checkpoint.
        if (blockIndex < cycle.nextBlockIndex) return;
        if (blockIndex != cycle.nextBlockIndex || cycle.failed || !cycle.owner.ownsLock()
                || !cycle.owner.canAct() || !cycle.owner.canContinueSpecializedEscape()
                || !cycle.owner.isFlyUpAnimationActive()) return;
        cycle.nextBlockIndex++;
        cycle.applyPlacementClearance(blockIndex);
        if (cycle.failed) return;
        cycle.place(blockIndex);
    }

    public boolean ownsEscape() {
        return this.ownsLock();
    }

    public void forceCancel() {
        if (this.ownsLock()) {
            this.stop();
            return;
        }
        PillarCycle cycle = ACTIVE_PILLARS.get(this.mob.getUUID());
        if (cycle != null && cycle.mob == this.mob) ACTIVE_PILLARS.remove(this.mob.getUUID(), cycle);
        this.obstructionBreaker.reset(this.mob);
        if (this.mob.getPersistentData().getBoolean(ESCAPE_TRANSIENT_TAG)) this.recoverAfterLoad();
        else this.stopEscapeAnimation();
    }

    public void recoverAfterLoad() {
        if (this.ownsLock() || !this.mob.getPersistentData().getBoolean(ESCAPE_TRANSIENT_TAG)) return;
        PillarCycle stale = ACTIVE_PILLARS.get(this.mob.getUUID());
        if (stale != null && stale.mob != this.mob) ACTIVE_PILLARS.remove(this.mob.getUUID(), stale);
        this.stopEscapeAnimation();
        this.mob.setNoGravity(false);
        this.mob.setDeltaMovement(this.mob.getDeltaMovement().x, 0.0D, this.mob.getDeltaMovement().z);
        this.mob.getPersistentData().remove(ESCAPE_TRANSIENT_TAG);
    }

    @Override
    protected boolean canStartSpecializedEscape() {
        this.recoverAfterLoad();
        return this.mob.tickCount >= this.retryAfterTick && !(this.mob instanceof NullEntity)
                && !this.mob.isHealing() && !this.mob.isSacrificing()
                && ForgeEventFactory.getMobGriefingEvent(this.mob.level(), this.mob);
    }

    @Override
    protected boolean canContinueSpecializedEscape() {
        PillarCycle cycle = ACTIVE_PILLARS.get(this.mob.getUUID());
        boolean valid = !this.mob.isHealing() && !this.mob.isSacrificing()
                && (cycle == null || cycle.mob == this.mob && !cycle.failed);
        if (!valid && this.ownsLock()) this.delayRetry();
        return valid;
    }

    @Override
    protected int plannedVerticalTravel() {
        return cyclesForUpAmount(this.upAmountBlocks) * BLOCKS_PER_CYCLE;
    }

    @Override
    protected void startEscape() {
        this.mob.getPersistentData().putBoolean(ESCAPE_TRANSIENT_TAG, true);
        this.previousNoGravity = this.mob.isNoGravity();
        this.mob.setNoGravity(true);
        this.mob.fallDistance = 0.0F;
        this.cyclesRemaining = cyclesForUpAmount(this.upAmountBlocks);
        this.exitRoll = null;
        this.startFlyCycle();
    }

    private void startFlyCycle() {
        this.cycleStartTick = this.mob.tickCount;
        this.cycleStartY = this.mob.getY();
        BlockPos base = BlockPos.containing(this.mob.getX(), this.cycleStartY, this.mob.getZ());
        ACTIVE_PILLARS.put(this.mob.getUUID(), new PillarCycle(this, this.mob, base, this.cycleStartY));
        this.clearingCycle = true;
    }

    /**
     * Animation events remain useful visual checkpoints, but the server goal owns
     * progression so a missing animation callback cannot invalidate the cycle.
     */
    private void placeDuePillarBlocks() {
        PillarCycle cycle = ACTIVE_PILLARS.get(this.mob.getUUID());
        if (cycle == null || cycle.mob != this.mob || cycle.failed || !this.isFlyUpAnimationActive()) return;

        int elapsedTicks = Math.max(0, this.mob.tickCount - this.cycleStartTick);
        while (cycle.nextBlockIndex < BLOCKS_PER_CYCLE
                && elapsedTicks >= PILLAR_PLACEMENT_TICKS[cycle.nextBlockIndex]) {
            int blockIndex = cycle.nextBlockIndex;
            placeFlyUpPillarBlock(this.mob, blockIndex);
            if (cycle.failed || cycle.nextBlockIndex == blockIndex) break;
        }
    }

    protected void playFlyUpAnimation() {
//            AV_EFM patch with epicfight animations
        RigAnimationController.play(this.mob, RigAnimationId.FLY_UP);
        if (!this.isFlyUpAnimationActive()) this.finished = true;
    }

    protected int getFlyUpAnimationDurationTicks() {
//            AV_EFM patch with epicfight animations
        return RigAnimationSpecs.get(RigAnimationId.FLY_UP).durationTicks();
    }

    protected boolean isExitRollAnimationActive() {
//            AV_EFM patch with epicfight animations
        return RigAnimationController.getActiveAnimationId(this.mob) == this.exitRoll;
    }

    protected int getExitRollAnimationDurationTicks() {
//            AV_EFM patch with epicfight animations
        return RigAnimationSpecs.get(this.exitRoll).durationTicks();
    }

    protected void playExitRollAnimation() {
//            AV_EFM patch with epicfight animations
        RigAnimationController.play(this.mob, this.exitRoll);
    }

    protected void stopEscapeAnimation() {
//            AV_EFM patch with epicfight animations
        RigAnimationId active = RigAnimationController.getActiveAnimationId(this.mob);
        if (active == RigAnimationId.FLY_UP || active == this.exitRoll) RigAnimationController.stop(this.mob, active);
    }

    protected int getFlyUpAnimationStartTick() {
//            AV_EFM patch with epicfight animations
        return RigAnimationController.getActiveAnimationStartTick(this.mob);
    }

    protected double getRemainingAuthoredFlyRise(float elapsed) {
//            AV_EFM patch with epicfight animations
        return RigPoseLibrary.worldMotionDelta(RigAnimationId.FLY_UP, elapsed,
                RigAnimationSpecs.get(RigAnimationId.FLY_UP).durationTicks(), Vec3.ZERO, true).y;
    }

    @Override
    protected void tickEscape() {
        if (this.exitRoll != null) {
            if (this.mob.tickCount - this.cycleStartTick > this.getExitRollAnimationDurationTicks()
                    && !this.isExitRollAnimationActive()) this.finished = true;
            return;
        }

        this.mob.setDeltaMovement(this.mob.getDeltaMovement().x, 0.0D, this.mob.getDeltaMovement().z);
        this.mob.fallDistance = 0.0F;

        if (this.clearingCycle) {
            // Two-block body plus the authored four-block rise: clear the complete six-block column first.
            AABB clearance = new AABB(this.mob.getBoundingBox().minX, this.mob.getBoundingBox().minY,
                    this.mob.getBoundingBox().minZ, this.mob.getBoundingBox().maxX,
                    this.mob.getBoundingBox().minY + 6.0D, this.mob.getBoundingBox().maxZ);
            if (this.obstructionBreaker.clear(this.mob, clearance)) return;
            this.clearingCycle = false;
            this.cycleStartTick = this.mob.tickCount;
            playFlyUpAnimation();
            return;
        }

        this.placeDuePillarBlocks();
        if (this.isFlyUpAnimationActive()) {
            return;
        }
        if (this.mob.tickCount - this.cycleStartTick + 1 < this.getFlyUpAnimationDurationTicks()) {
            this.delayRetry();
            this.finished = true;
            return;
        }

        PillarCycle completed = ACTIVE_PILLARS.remove(this.mob.getUUID());
        if (completed == null || completed.failed || completed.nextBlockIndex != BLOCKS_PER_CYCLE
                || !this.ensureCycleHeight()) {
            this.delayRetry();
            this.finished = true;
            return;
        }

        this.cyclesRemaining--;
        if (this.cyclesRemaining > 0) {
            this.startFlyCycle();
            return;
        }

        this.mob.setNoGravity(this.previousNoGravity);
        this.exitRoll = this.prepareRandomExitRoll();
        this.cycleStartTick = this.mob.tickCount;
        this.playExitRollAnimation();
    }

    private boolean ensureCycleHeight() {
        double missing = this.cycleStartY + BLOCKS_PER_CYCLE - this.mob.getY();
        if (missing > 1.0E-4D) this.mob.move(MoverType.SELF, new Vec3(0.0D, missing, 0.0D));
        return this.mob.getY() >= this.cycleStartY + BLOCKS_PER_CYCLE - 0.02D;
    }

    @Override
    protected void stopEscape() {
        if (!this.ownsLock()) return;
        ACTIVE_PILLARS.remove(this.mob.getUUID());
        this.obstructionBreaker.reset(this.mob);
        this.stopEscapeAnimation();
        this.mob.setNoGravity(this.previousNoGravity);
        this.mob.fallDistance = 0.0F;
        this.exitRoll = null;
        this.cyclesRemaining = 0;
        this.clearingCycle = false;
        this.mob.getPersistentData().remove(ESCAPE_TRANSIENT_TAG);
    }

    private void delayRetry() {
        this.retryAfterTick = Math.max(this.retryAfterTick, this.mob.tickCount + 100);
    }

    private static Block blockFor(HerobrineMob mob) {
        if (mob instanceof HerobrineCloneEntity) return AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get();
        if (mob instanceof ShadowHerobrineEntity || mob instanceof ShadowHerobrineCloneEntity
                || mob instanceof Herobrine7Entity || mob instanceof ArmoredHerobrineEntity
                || mob instanceof TransporterHerobrineCloneEntity) {
            return AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get();
        }
        return AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get();
    }

    private static void setOwner(ServerLevel level, BlockPos pos, HerobrineMob mob, BlockState state) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ObsidianBlockEntity obsidian) obsidian.setOwner(mob.getUUID());
        if (blockEntity instanceof ShadowObsidianBlockEntity shadow) shadow.setOwner(mob.getUUID());
        if (blockEntity instanceof CryingObsidianBlockEntity crying) crying.setOwner(mob.getUUID());
        if (blockEntity != null) {
            blockEntity.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }

    private static final class PillarCycle {
        private final HerobrineEscapeHoleGoal owner;
        private final HerobrineMob mob;
        private final BlockPos base;
        private final double startY;
        private int nextBlockIndex;
        private boolean failed;

        private PillarCycle(HerobrineEscapeHoleGoal owner, HerobrineMob mob, BlockPos base, double startY) {
            this.owner = owner;
            this.mob = mob;
            this.base = base;
            this.startY = startY;
        }

        private void applyPlacementClearance(int blockIndex) {
            BlockPos topBlock = this.base.above(blockIndex);
            double clearNow = topBlock.getY() + 1.001D - this.mob.getBoundingBox().minY;
            if (clearNow > 1.0E-4D) {
                this.mob.move(MoverType.SELF, new Vec3(0.0D, clearNow, 0.0D));
                if (this.mob.getBoundingBox().minY < topBlock.getY() + 0.999D) {
                    this.failed = true;
                    return;
                }
            }
            if (blockIndex == BLOCKS_PER_CYCLE - 1) {
                int animationStart = this.owner.getFlyUpAnimationStartTick();
                float elapsed = animationStart < 0 ? 12.0F : Math.max(0.0F, this.mob.tickCount - animationStart);
                double missingAtEnd = this.startY + BLOCKS_PER_CYCLE
                        - (this.mob.getY() + this.owner.getRemainingAuthoredFlyRise(elapsed));
                if (missingAtEnd > 1.0E-4D) this.mob.move(MoverType.SELF, new Vec3(0.0D, missingAtEnd, 0.0D));
            }
        }

        private void place(int blockIndex) {
            if (!(this.mob.level() instanceof ServerLevel level)) {
                this.failed = true;
                return;
            }
            BlockPos pos = this.base.above(blockIndex);
            if (!level.hasChunkAt(pos) || !level.getWorldBorder().isWithinBounds(pos) || !level.isInWorldBounds(pos)) {
                this.failed = true;
                return;
            }
            Block block = blockFor(this.mob);
            BlockState existing = level.getBlockState(pos);
            if (!ForgeEventFactory.getMobGriefingEvent(level, this.mob)
                    || !existing.getFluidState().isEmpty()
                    || existing.hasBlockEntity() && !existing.is(block)
                    || !existing.canBeReplaced() && !existing.is(block)
                    || this.mob.getBoundingBox().intersects(new AABB(pos))) {
                this.failed = true;
                return;
            }
            BlockState state = block.defaultBlockState();
            if (state.hasProperty(HerobrineObsidianBlock.FROM_PLAYER)) {
                state = state.setValue(HerobrineObsidianBlock.FROM_PLAYER, false);
            }
            if (!existing.is(block)) {
                BlockSnapshot snapshot = BlockSnapshot.create(level.dimension(), level, pos);
                if (!level.setBlockAndUpdate(pos, state)) {
                    this.failed = true;
                    return;
                }
                if (ForgeEventFactory.onBlockPlace(this.mob, snapshot, net.minecraft.core.Direction.UP)) {
                    snapshot.restore(true, false);
                    this.failed = true;
                    return;
                }
            }
            setOwner(level, pos, this.mob, state);
        }
    }
}
