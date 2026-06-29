package com.pla.annoyingvillagers.block;

import java.util.Collections;
import java.util.List;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import org.jetbrains.annotations.NotNull;

public class EnchantBedBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public EnchantBedBlock() {
        super(Properties.of()
                .sound(SoundType.WOOD)
                .strength(1.25F, 10.0F)
                .lightLevel((blockstate) -> 2)
                .jumpFactor(5.0F)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(EnchantBedBlock.FACING, Direction.NORTH));
    }

    public void appendHoverText(@NotNull ItemStack itemstack, BlockGetter blockgetter, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, blockgetter, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.enchanted_bed"));
    }

    public boolean propagatesSkylightDown(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos) {
        return true;
    }

    public int getLightBlock(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos) {
        return 0;
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return Shapes.empty();
    }

    public @NotNull VoxelShape getShape(BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        VoxelShape voxelshape;

        switch (blockstate.getValue(EnchantBedBlock.FACING)) {
            case NORTH:
                voxelshape = Shapes.or(box(0.0D, 0.0D, 29.0D, 3.0D, 3.0D, 32.0D), box(13.0D, 0.0D, 29.0D, 16.0D, 3.0D, 32.0D), box(0.0D, 3.0D, 16.0D, 16.0D, 9.0D, 32.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D), box(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D));
                break;
            case EAST:
                voxelshape = Shapes.or(box(-16.0D, 0.0D, 0.0D, -13.0D, 3.0D, 3.0D), box(-16.0D, 0.0D, 13.0D, -13.0D, 3.0D, 16.0D), box(-16.0D, 3.0D, 0.0D, 0.0D, 9.0D, 16.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D), box(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D));
                break;
            case WEST:
                voxelshape = Shapes.or(box(29.0D, 0.0D, 13.0D, 32.0D, 3.0D, 16.0D), box(29.0D, 0.0D, 0.0D, 32.0D, 3.0D, 3.0D), box(16.0D, 3.0D, 0.0D, 32.0D, 9.0D, 16.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D), box(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D));
                break;
            default:
                voxelshape = Shapes.or(box(13.0D, 0.0D, -16.0D, 16.0D, 3.0D, -13.0D), box(0.0D, 0.0D, -16.0D, 3.0D, 3.0D, -13.0D), box(0.0D, 3.0D, -16.0D, 16.0D, 9.0D, 0.0D), box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D), box(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D), box(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D));
        }

        return voxelshape;
    }

    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(EnchantBedBlock.FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockplacecontext) {
        return this.defaultBlockState().setValue(EnchantBedBlock.FACING, blockplacecontext.getHorizontalDirection().getOpposite());
    }

    public @NotNull BlockState rotate(BlockState blockstate, Rotation rotation) {
        return blockstate.setValue(EnchantBedBlock.FACING, rotation.rotate(blockstate.getValue(EnchantBedBlock.FACING)));
    }

    public @NotNull BlockState mirror(BlockState blockstate, Mirror mirror) {
        return blockstate.rotate(mirror.getRotation(blockstate.getValue(EnchantBedBlock.FACING)));
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState blockstate, LootParams.@NotNull Builder pParams) {
        List<ItemStack> list = super.getDrops(blockstate, pParams);

        return !list.isEmpty() ? list : Collections.singletonList(new ItemStack(AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get()));
    }

    public @NotNull InteractionResult use(@NotNull BlockState blockstate, @NotNull Level level, @NotNull BlockPos blockpos, @NotNull Player player, @NotNull InteractionHand interactionhand, @NotNull BlockHitResult blockHitResult) {
        super.use(blockstate, level, blockpos, player, interactionhand, blockHitResult);
        if (player.hasEffect(AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get())
                && !player.level().isClientSide()) {
            player.displayClientMessage(Component.literal("You have already used the Enchant Bed!"), true);
        }

        if (player instanceof ServerPlayer serverPlayer) {
            if (player.experienceLevel >= 2) {
                player.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT.get(), MobEffectInstance.INFINITE_DURATION, 0, false, false));
                player.displayClientMessage(Component.literal("You used the Enchant Bed once. Experience level -1."), true);
                player.displayClientMessage(Component.literal("Respawn point has been reset."), false);
                player.giveExperienceLevels(-1);
                serverPlayer.setRespawnPosition(player.level().dimension(), new BlockPos((int) blockHitResult.getLocation().x, (int) blockHitResult.getLocation().y, (int) blockHitResult.getLocation().z), serverPlayer.getYRot(), true, false);
            } else {
                player.displayClientMessage(Component.literal("Your experience level is too low. You must be above level 2 to use this!"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
