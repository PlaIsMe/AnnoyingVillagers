package com.pla.annoyingvillagers.util.projectile;

import com.pla.annoyingvillagers.clazz.ProjectileBreakableBlocks;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class ProjectileBlockBreaker {
    private ProjectileBlockBreaker() {}

    private static final float FULL_ARROW_SPEED = 3.0f;
    private static final float FULL_TRIDENT_SPEED = 2.5f;

    public static float computeInitialPower(AbstractArrow proj) {
        float speed = (float) proj.getDeltaMovement().length();
        float full = (proj instanceof ThrownTrident) ? FULL_TRIDENT_SPEED : FULL_ARROW_SPEED;
        return Mth.clamp(speed / full, 0.0f, 1.0f);
    }

    public static BlockHitResult clip(Level level, ClipContext ctx, AbstractArrow proj) {
        if (!AnnoyingVillagersConfig.ARROW_CAN_BREAK_BLOCK.get()) {
            return level.clip(ctx);
        }

        if (!(proj instanceof Arrow) && !(proj instanceof ThrownTrident)) {
            return level.clip(ctx);
        }
        if (!(proj instanceof BreakPowerHolder holder)) {
            return level.clip(ctx);
        }
        if (level.isClientSide) {
            return level.clip(ctx);
        }

        float power = holder.getBreakPower();
        if (power <= 0.0f) {
            return level.clip(ctx);
        }

        Vec3 from = ctx.getFrom();
        Vec3 to = ctx.getTo();
        CollisionContext collision = CollisionContext.of(proj);

        BiFunction<ClipContext, BlockPos, BlockHitResult> hitTest = (c, pos) -> {
            BlockState state = level.getBlockState(pos);
            ProjectileBreakableBlocks rule = ProjectileBreakableBlocks.find(state);
            VoxelShape shape = state.getCollisionShape(level, pos, collision);
            if (rule != null && shape.isEmpty()) {
                shape = state.getShape(level, pos, collision);
            }

            BlockHitResult bhr = level.clipWithInteractionOverride(from, to, pos, shape, state);
            if (bhr == null) return null;

            if (state.getDestroySpeed(level, pos) < 0.0f) {
                holder.setBreakPower(0.0f);
                return bhr;
            }

            if (rule == null) {
                holder.setBreakPower(0.0f);
                return bhr;
            }

            float currentPower = holder.getBreakPower();
            if (currentPower < rule.requiredPower) {
                holder.setBreakPower(0.0f);
                return bhr;
            }

            Entity breaker = proj.getOwner();

            if (!canBreak(level, breaker, pos, state)) {
                holder.setBreakPower(0.0f);
                return bhr;
            }

            level.destroyBlock(pos, true, breaker);
            holder.setBreakPower(currentPower - rule.powerCost);
            return null;
        };

        Function<ClipContext, BlockHitResult> miss = (c) -> {
            Vec3 d = from.subtract(to);
            return BlockHitResult.miss(to, Direction.getNearest(d.x, d.y, d.z), BlockPos.containing(to));
        };

        return BlockGetter.traverseBlocks(from, to, ctx, hitTest, miss);
    }

    private static boolean canBreak(Level level, Entity breaker, BlockPos pos, BlockState state) {
        if (breaker instanceof Player player) {
            return !NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level, pos, state, player)).isCanceled();
        }

        return true;
    }
}
