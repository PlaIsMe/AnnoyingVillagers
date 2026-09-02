    package com.pla.annoyingvillagers.block;

    import net.minecraft.core.BlockPos;
    import net.minecraft.core.Direction;
    import net.minecraft.server.level.ServerLevel;
    import net.minecraft.util.RandomSource;
    import com.pla.annoyingvillagers.util.EndFireUtil;
    import net.minecraft.world.entity.Entity;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.level.BlockGetter;
    import net.minecraft.world.level.Level;
    import net.minecraft.world.level.LevelAccessor;
    import net.minecraft.world.level.block.BaseFireBlock;
    import net.minecraft.world.level.block.Block;
    import net.minecraft.world.level.block.Blocks;
    import net.minecraft.world.level.block.state.BlockBehaviour;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.world.level.block.state.StateDefinition;
    import net.minecraft.world.level.block.state.properties.BlockStateProperties;
    import net.minecraft.world.level.block.state.properties.IntegerProperty;

    public class EndFireBlock extends BaseFireBlock {
        public static final IntegerProperty AGE = BlockStateProperties.AGE_15;

        public EndFireBlock(BlockBehaviour.Properties properties) {
            super(properties, 3.0F);
            this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(AGE);
        }

        @Override public ItemStack getCloneItemStack(BlockGetter g, BlockPos p, BlockState s) {
            return ItemStack.EMPTY;
        }

        @Override
        public BlockState updateShape(BlockState state, Direction facing, BlockState facingState,
                                      LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
            return state.canSurvive(level, currentPos) ? state : Blocks.AIR.defaultBlockState();
        }

        @Override
        protected boolean canBurn(BlockState pState) {
            return true;
        }

        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            if (!entity.fireImmune()) {
                EndFireUtil.setEndFireBurning(entity, true);

                if (!level.isClientSide && entity.getRemainingFireTicks() < 160) {
                    entity.setSecondsOnFire(8);
                }
            }

            super.entityInside(state, level, pos, entity);
        }

        private static int getEndFireTickDelay(RandomSource random) {
            return 40 + random.nextInt(20);
        }

        @Override
        public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
            super.onPlace(state, level, pos, oldState, isMoving);
            if (!level.isClientSide) {
                level.scheduleTick(pos, this, getEndFireTickDelay(level.random));
            }
        }

        @Override
        public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
            level.scheduleTick(pos, this, getEndFireTickDelay(random));

            if (!state.canSurvive(level, pos)) {
                level.removeBlock(pos, false);
                return;
            }

            int age = state.getValue(AGE);
            int newAge = Math.min(15, age + 1 + random.nextInt(2));
            if (newAge != age) {
                level.setBlock(pos, state.setValue(AGE, newAge), 4);
                state = level.getBlockState(pos);
                age = newAge;
            }

            if (level.isRainingAt(pos) && random.nextFloat() < 0.05F + age * 0.01F) {
                level.removeBlock(pos, false);
                return;
            }

            if (age >= 15) {
                if (random.nextInt(6) == 0) {
                    level.removeBlock(pos, false);
                }
            }
        }
    }
