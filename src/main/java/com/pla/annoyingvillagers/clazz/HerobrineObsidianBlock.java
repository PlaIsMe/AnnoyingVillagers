package com.pla.annoyingvillagers.clazz;

import java.util.*;

import com.pla.annoyingvillagers.blockentity.*;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class HerobrineObsidianBlock extends Block {
    public static final BooleanProperty FROM_PLAYER = BooleanProperty.create("from_player");
    public static final IntegerProperty REPLACE_BY_LIQUID = IntegerProperty.create("replace_by_liquid", 0, 2);

    private static final String NBT_LIFE = "life";
    private static final int LIFE_TICKS = 25;

    public HerobrineObsidianBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FROM_PLAYER, Boolean.FALSE)
                        .setValue(REPLACE_BY_LIQUID, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FROM_PLAYER, REPLACE_BY_LIQUID);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState base = super.getStateForPlacement(blockPlaceContext);
        if (base == null) base = this.defaultBlockState();
        return base.setValue(FROM_PLAYER, blockPlaceContext.getPlayer() != null);
    }

    public int getLightBlock(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos) {
        return 15;
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return Shapes.empty();
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState blockstate, LootParams.@NotNull Builder builder) {
        List<ItemStack> list = super.getDrops(blockstate, builder);
        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(this, 1));
    }

    public void customTickSound(ServerLevel serverLevel, BlockPos blockPos) {
    }

    public void customPlaceSound(ServerLevel serverLevel, BlockPos blockPos) {
    }

    @Override
    public void onPlace(@NotNull BlockState blockstate, @NotNull Level level, @NotNull BlockPos blockPos,
                        @NotNull BlockState oldState, boolean flag) {
        super.onPlace(blockstate, level, blockPos, oldState, flag);

        if (level instanceof ServerLevel serverLevel) {
            customPlaceSound(serverLevel, blockPos);

            BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
            if (blockEntity != null) {
                CompoundTag data = blockEntity.getPersistentData();
                if (!data.contains(NBT_LIFE)) {
                    data.putInt(NBT_LIFE, LIFE_TICKS);
                    blockEntity.setChanged();
                }
            }
        }
        level.scheduleTick(blockPos, this, 1);
    }

    public boolean conditionEveryTickEntityInside(Entity entity) {
        return true;
    }

    public void customHurtLogic(Entity entity, Entity owner, ServerLevel serverLevel, BlockPos blockPos) {
    }

    @Override
    public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel,
                     @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        super.tick(blockState, serverLevel, blockPos, randomSource);
        customTickSound(serverLevel, blockPos);

        for (Entity entity : serverLevel.getEntities(null, new AABB(blockPos))) {
            runEntityInsideLogic(blockState, serverLevel, blockPos, entity);
        }

        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity != null) {
            CompoundTag data = blockEntity.getPersistentData();
            int life = data.contains(NBT_LIFE) ? data.getInt(NBT_LIFE) : LIFE_TICKS;

            if (life > 0) {
                data.putInt(NBT_LIFE, life - 1);
                blockEntity.setChanged();
                serverLevel.scheduleTick(blockPos, this, 1);
                return;
            }
        } else {
            serverLevel.scheduleTick(blockPos, this, 1);
            return;
        }

        BlockState current = serverLevel.getBlockState(blockPos);
        int replace = 0;
        if (current.getBlock() instanceof HerobrineObsidianBlock && current.hasProperty(HerobrineObsidianBlock.REPLACE_BY_LIQUID)) {
            replace = current.getValue(HerobrineObsidianBlock.REPLACE_BY_LIQUID);
        }

        BlockState replacement = switch (replace) {
            case 1 -> Blocks.WATER.defaultBlockState();
            case 2 -> Blocks.LAVA.defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };

        serverLevel.setBlock(blockPos, replacement, 3);
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                AnnoyingVillagersModSounds.POP.get(),
                SoundSource.BLOCKS,
                0.5F, 1.0F
        );
    }

    private void runEntityInsideLogic(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel,
                                      @NotNull BlockPos blockPos, @NotNull Entity entity) {
        if (!conditionEveryTickEntityInside(entity)) return;

        boolean fromPlayer =
                blockState.getBlock() instanceof HerobrineObsidianBlock
                        && blockState.hasProperty(HerobrineObsidianBlock.FROM_PLAYER)
                        && blockState.getValue(HerobrineObsidianBlock.FROM_PLAYER);

        if (!fromPlayer && HerobrineUtil.isHerobrineFaction(entity)) {
            return;
        }
        if (entity instanceof Player && fromPlayer && !serverLevel.getServer().isPvpAllowed()) {
            return;
        }

        Entity owner = null;

        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
            UUID ownerUuid = obsidianBlockEntity.getOwner();
            if (ownerUuid != null) {
                if (ownerUuid.equals(entity.getUUID())) return;
                owner = fromPlayer ? serverLevel.getPlayerByUUID(ownerUuid) : serverLevel.getEntity(ownerUuid);
            }
        } else if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
            UUID ownerUuid = shadowObsidianBlockEntity.getOwner();
            if (ownerUuid != null) {
                if (ownerUuid.equals(entity.getUUID())) return;
                owner = fromPlayer ? serverLevel.getPlayerByUUID(ownerUuid) : serverLevel.getEntity(ownerUuid);
            }
        } else if (blockEntity instanceof CryingObsidianBlockEntity cryingObsidianBlockEntity) {
            UUID ownerUuid = cryingObsidianBlockEntity.getOwner();
            if (ownerUuid != null) {
                if (ownerUuid.equals(entity.getUUID())) return;
                owner = fromPlayer ? serverLevel.getPlayerByUUID(ownerUuid) : serverLevel.getEntity(ownerUuid);
            }
        } else if (blockEntity instanceof ShadowObsidianShortPillarBlockEntity shadowObsidianShortPillarBlockEntity) {
            UUID ownerUuid = shadowObsidianShortPillarBlockEntity.getOwner();
            if (ownerUuid != null) {
                if (ownerUuid.equals(entity.getUUID())) return;
                owner = fromPlayer ? serverLevel.getPlayerByUUID(ownerUuid) : serverLevel.getEntity(ownerUuid);
            }
        } else if (blockEntity instanceof ShadowObsidianMiddlePillarBlockEntity shadowObsidianMiddlePillarBlockEntity) {
            UUID ownerUuid = shadowObsidianMiddlePillarBlockEntity.getOwner();
            if (ownerUuid != null) {
                if (ownerUuid.equals(entity.getUUID())) return;
                owner = fromPlayer ? serverLevel.getPlayerByUUID(ownerUuid) : serverLevel.getEntity(ownerUuid);
            }
        } else if (blockEntity instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
            UUID ownerUuid = shadowObsidianLongPillarBlockEntity.getOwner();
            if (ownerUuid != null) {
                if (ownerUuid.equals(entity.getUUID())) return;
                owner = fromPlayer ? serverLevel.getPlayerByUUID(ownerUuid) : serverLevel.getEntity(ownerUuid);
            }
        }

        if (entity instanceof Player player) {
            CompoundTag data = player.getPersistentData();
            if (data.contains("StunEscapeCooldown")) {
                int coolDownValue = data.getInt("StunEscapeCooldown");
                if (coolDownValue < 5) {
                    data.putInt("StunEscapeCooldown", coolDownValue + 1);
                }
            }
        }

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            int currentValue = playerNpcEntity.getStunEscapeCooldown();
            if (currentValue < 100) {
                playerNpcEntity.setStunEscapeCooldown(currentValue + 20);
            }
        }

        if (entity instanceof HerobrineMob herobrineMob) {
            int currentValue = herobrineMob.getStunEscapeCooldown();
            if (currentValue < 100) {
                herobrineMob.setStunEscapeCooldown(currentValue + 20);
            }
        }

        if (entity instanceof AVNpc avNpc) {
            int currentValue = avNpc.getStunEscapeCooldown();
            if (currentValue < 100) {
                avNpc.setStunEscapeCooldown(currentValue + 20);
            }
        }

        if (entity instanceof BlueDemonEntity blueDemonEntity) {
            int currentValue = blueDemonEntity.getStunEscapeCooldown();
            if (currentValue < 100) {
                blueDemonEntity.setStunEscapeCooldown(currentValue + 20);
            }
        }

        customHurtLogic(entity, owner, serverLevel, blockPos);
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state,
                            LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level instanceof ServerLevel serverLevel) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
                    obsidianBlockEntity.setOwner(placer.getUUID());
                } else if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                    shadowObsidianBlockEntity.setOwner(placer.getUUID());
                } else if (blockEntity instanceof CryingObsidianBlockEntity cryingObsidianBlockEntity) {
                    cryingObsidianBlockEntity.setOwner(placer.getUUID());
                } else if (blockEntity instanceof ShadowObsidianShortPillarBlockEntity shadowObsidianShortPillarBlockEntity) {
                    shadowObsidianShortPillarBlockEntity.setOwner(placer.getUUID());
                } else if (blockEntity instanceof ShadowObsidianMiddlePillarBlockEntity shadowObsidianMiddlePillarBlockEntity) {
                    shadowObsidianMiddlePillarBlockEntity.setOwner(placer.getUUID());
                } else if (blockEntity instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
                    shadowObsidianLongPillarBlockEntity.setOwner(placer.getUUID());
                }

                CompoundTag data = blockEntity.getPersistentData();
                if (!data.contains(NBT_LIFE)) {
                    data.putInt(NBT_LIFE, LIFE_TICKS);
                }

                blockEntity.setChanged();
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            }
        }
    }
}