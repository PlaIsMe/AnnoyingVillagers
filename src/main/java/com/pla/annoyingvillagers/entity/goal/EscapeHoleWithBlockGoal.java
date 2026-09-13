package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.entity.ai.RecoveryAi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Physical jump/place ascent. Combat height selects a hole; passive recovery proves a bounded open shaft. */
public final class EscapeHoleWithBlockGoal extends AbstractRecoveryGoal {
    private BlockPos column;
    private BlockPos passiveExit;
    private LivingEntity target;
    private double escapeY;
    private boolean jumping;
    private boolean placed;
    private int jumpTick;
    private int nextJump;
    private int displaced;

    public EscapeHoleWithBlockGoal(AVNpc npc) { super(npc); }

    @Override public boolean canUse() {
        if (!canCheck()) return false;
        if (!npc.onGround()) return trace("not_on_ground");
        if (npc.isInWaterOrBubble() || npc.isInLava()) return trace("in_fluid");
        column = npc.blockPosition();
        if (!safeFooting(column)) return trace("unsafe_footing column=" + column);
        if (recovery.blockSlot(column) == -1) return trace("no_suitable_full_block");
        target = npc.getTarget();
        passiveExit = null;
        if (RecoveryAi.validTarget(npc, target)) {
            // A target at the same height or below is a building/route obstruction, not a hole.
            if (target.getY() - npc.getY() <= 2) return trace("target_not_above_hole_threshold");
            if (target.getY() - npc.getY() > 9) return trace("target_too_high");
            if (!RecoveryAi.loadedCorridor(npc, target.position())) return trace("path_region_not_loaded");
            if (!RecoveryAi.admitPath(npc)) return trace("path_budget_exhausted");
            if (RecoveryAi.reachable(RecoveryAi.targetPath(npc, target.blockPosition()))) return trace("target_path_reachable");
            escapeY = target.getY() - 1;
        } else {
            target = null;
            passiveExit = findShaftExit();
            if (passiveExit == null) return false;
            escapeY = passiveExit.getY();
        }
        trace("eligible column=" + column + " escapeY=" + escapeY);
        return true;
    }

    @Override public void start() {
        super.start();
        jumping = false; placed = false; displaced = 0; nextJump = npc.tickCount;
        nextCheck = npc.tickCount;
    }

    @Override public void tick() {
        if (!canContinueToUse()) return;
        elapsed++;
        npc.getNavigation().stop();
        if (jumping) {
            trace("airborne column=" + column + " placed=" + placed + " jumpAge=" + (npc.tickCount - jumpTick));
            // Cheap physical fast path: never rescan or path while airborne.
            npc.getLookControl().setLookAt(column.getX() + .5D, column.getY(), column.getZ() + .5D);
            if (!placed && npc.getY() >= column.getY() + 1.0D
                    && !npc.getBoundingBox().intersects(new AABB(column))) {
                placed = recovery.placeUnderFeet(column);
                trace("placement_attempt result=" + placed + " column=" + column);
            }
            if (npc.onGround() && npc.tickCount > jumpTick + 2) {
                jumping = false;
                if (placed) column = npc.blockPosition();
                nextJump = npc.tickCount + 4;
                trace("landed placed=" + placed + " column=" + column);
            } else if (npc.tickCount - jumpTick > 60) {
                finished = true;
                trace("jump_timeout");
            }
            return;
        }
        if (npc.onGround() && npc.getY() >= escapeY - .05D) { finished = true; trace("escape_height_reached"); return; }
        if (target != null && (!RecoveryAi.validTarget(npc, target) || npc.getTarget() != target
                || target.getY() <= npc.getY())) { finished = true; trace("target_changed_or_invalid"); return; }
        if (!npc.onGround()) { trace("waiting_for_ground"); return; }
        if (!npc.blockPosition().equals(column)) {
            if (npc.tickCount < nextCheck) return;
            nextCheck = npc.tickCount + 20;
            if (++displaced > 3 || !safeFooting(npc.blockPosition())) { finished = true; trace("displaced_or_unsafe_footing"); return; }
            column = npc.blockPosition();
        }
        if (!safeFooting(column)) { finished = true; trace("lost_footing"); return; }
        double dx = column.getX() + .5D - npc.getX();
        double dz = column.getZ() + .5D - npc.getZ();
        if (dx * dx + dz * dz > .015D) {
            trace("centering column=" + column + " dx=" + dx + " dz=" + dz);
            npc.getMoveControl().setWantedPosition(column.getX() + .5D, npc.getY(), column.getZ() + .5D, 1.0D);
            return;
        }
        // Clear only the real headroom above this column, never surrounding side walls.
        BlockPos head = column.above(2);
        if (!npc.level().hasChunkAt(head)) { finished = true; trace("headroom_unloaded"); return; }
        if (!npc.level().getBlockState(head).getCollisionShape(npc.level(), head).isEmpty()) {
            if (!RecoveryAi.canBreak(npc, head)) { finished = true; trace("headroom_unbreakable pos=" + head); return; }
            trace("clearing_headroom pos=" + head);
            recovery.breakBlock(head, false);
            return;
        }
        recovery.stopBreaking();
        if (npc.tickCount < nextJump) { trace("jump_cooldown until=" + nextJump); return; }
        if (npc.tickCount < nextCheck) { trace("block_check_cooldown until=" + nextCheck); return; }
        nextCheck = npc.tickCount + 20;
        int slot = recovery.blockSlot(column);
        if (slot == -1) { finished = true; trace("no_suitable_full_block"); return; }
        if (slot >= 0) recovery.swapToSlot(slot);
        jumping = true; placed = false; jumpTick = npc.tickCount;
        npc.setSprinting(false);
        npc.getLookControl().setLookAt(column.getX() + .5D, column.getY(), column.getZ() + .5D);
        npc.shortPillarJump();
        trace("jump_started slot=" + slot + " column=" + column);
    }

    private boolean safeFooting(BlockPos feet) {
        return HoleEscapePlanner.safeFooting(npc, feet);
    }

    private boolean open(BlockPos feet) {
        return HoleEscapePlanner.open(npc, feet);
    }

    private BlockPos findShaftExit() {
        return HoleEscapePlanner.findPassiveShaftExit(npc, column, 8, this::shaftReject);
    }

    private void shaftReject(String reason, BlockPos pos) {
        trace("shaft_" + reason + " pos=" + pos);
    }

    @Override public void stop() {
        super.stop();
        if (passiveExit != null && npc.isAlive() && npc.onGround() && npc.getY() >= escapeY - .05D
                && RecoveryAi.admitPath(npc)) {
            var path = RecoveryAi.targetPath(npc, passiveExit);
            if (RecoveryAi.reachable(path)) npc.getNavigation().moveTo(path, 1.0D);
        }
    }
}
