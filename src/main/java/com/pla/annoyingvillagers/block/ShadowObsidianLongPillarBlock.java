package com.pla.annoyingvillagers.block;

import com.pla.annoyingvillagers.blockentity.ShadowObsidianLongPillarBlockEntity;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShadowObsidianLongPillarBlock extends HerobrineObsidianBlock implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ShadowObsidianLongPillarBlock() {
        super(Properties.of()
                .sound(SoundType.STONE)
                .offsetType(OffsetType.XYZ)
                .strength(3.0F, 50.0F)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false)
                .dynamicShape());
        super.registerDefaultState(
                super.getStateDefinition().any().setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState state = super.getStateForPlacement(blockPlaceContext);
        if (state == null) state = this.defaultBlockState();
        return state.setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return Shapes.or(box(6.0D, 0.0D, 12.0D, 10.0D, 16.0D, 16.0D), box(6.0D, 16.0D, 12.0D, 10.0D, 32.0D, 16.0D), box(6.0D, -16.0D, 12.0D, 10.0D, 0.0D, 16.0D));
    }

    @Override
    public void customPlaceSound(ServerLevel serverLevel, BlockPos blockPos) {
        super.customPlaceSound(serverLevel, blockPos);
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                AnnoyingVillagersModSounds.OB_PLACE.get(),
                SoundSource.BLOCKS,
                0.5F, 1.0F
        );
    }

    @Override
    public void customTickSound(ServerLevel serverLevel, BlockPos blockPos) {
        super.customTickSound(serverLevel, blockPos);
    }

    @Override
    public void customHurtLogic(Entity entity, Entity owner, ServerLevel serverLevel, BlockPos blockPos) {
        super.customHurtLogic(entity, owner, serverLevel, blockPos);
        serverLevel.sendParticles(
                AnnoyingVillagersModParticleTypes.SPARK.get(),
                entity.getX(), entity.getY() + 1.5, entity.getZ() + 0.8,
                5,
                0, 0, 0,
                0.1
        );
        if (entity instanceof Mob mob) {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2, 8, false, false));
            applyEpicFightShortStun(entity);
        }
        if (owner != null) {
            if (owner instanceof Player player) {
                entity.hurt(entity.level().damageSources().playerAttack(player), 1.0F);
            } else {
                entity.hurt(entity.level().damageSources().mobAttack((LivingEntity) owner), 1.0F);
            }
        } else {
            entity.hurt(entity.level().damageSources().generic(), 1.0F);
        }
        entity.setDeltaMovement(new Vec3(0.0D, 0.0D, 0.0D));
        if (Math.random() <= 0.2D && entity.tickCount % 10 == 0) {
            if (entity instanceof LivingEntity livingEntity) {
                float strength = 1.0F;
                double dx = blockPos.getX() - entity.getX();
                double dz = blockPos.getZ() - entity.getZ();
                livingEntity.knockback(strength, dx, dz);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ShadowObsidianLongPillarBlockEntity(pPos, pState);
    }
}
