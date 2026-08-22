package com.pla.annoyingvillagers.block;

import com.google.common.collect.ImmutableMap;
import com.pla.annoyingvillagers.blockentity.FractureBlockEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Function;

public class FractureBlock extends BaseEntityBlock {
    protected final StateDefinition<Block, BlockState> stateDefinition;
    private static FractureBlockState fractureBlockState;

    public FractureBlock(Properties properties) {
        super(properties);
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.stateDefinition = builder.create(FractureBlock::getDefaultFractureBlockState, FractureBlockState::new);
        fractureBlockState = (FractureBlockState) this.stateDefinition.any();
    }

    public static FractureBlockState getDefaultFractureBlockState(Block block) {
        return fractureBlockState;
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> shapeFunction) {
        return this.stateDefinition.getPossibleStates().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), shapeFunction));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? createTickerHelper(blockEntityType, AnnoyingVillagersModBlockEntities.FRACTURE_BLOCK.get(), FractureBlockEntity::lifeTimeTick) : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return blockState instanceof FractureBlockState fractureState ? new FractureBlockEntity(blockPos, blockState, fractureState) : null;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }
}
