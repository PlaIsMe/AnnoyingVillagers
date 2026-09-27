package com.pla.annoyingvillagers.block;

import com.mojang.serialization.MapCodec;
import com.pla.annoyingvillagers.blockentity.FractureBlockEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class FractureBlock extends BaseEntityBlock {
    public static final MapCodec<FractureBlock> CODEC = simpleCodec(FractureBlock::new);
    public FractureBlock(Properties properties) {
        // Chunk packets must contain states from Block.BLOCK_STATE_REGISTRY.
        // Per-position animation data belongs in the block entity, not a second StateDefinition.
        super(properties.noOcclusion().dynamicShape());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? createTickerHelper(blockEntityType, AnnoyingVillagersModBlockEntities.FRACTURE_BLOCK.get(), FractureBlockEntity::lifeTimeTick) : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FractureBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        BlockState original = getOriginalState(level, pos);
        return original == null ? Shapes.block() : original.getCollisionShape(level, pos, context);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState original = getOriginalState(level, pos);
        return original == null ? 0 : original.getLightEmission(level, pos);
    }

    @Nullable
    private static BlockState getOriginalState(BlockGetter level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof FractureBlockEntity fracture ? fracture.getOriginalBlockState() : null;
    }
}
