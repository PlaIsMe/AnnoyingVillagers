package com.pla.annoyingvillagers.block;

import com.pla.annoyingvillagers.blockentity.ShadowObsidianShortPillarBlockEntity;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.client.particle.HitParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShadowObsidianShortPillarBlock extends HerobrineObsidianBlock implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public ShadowObsidianShortPillarBlock() {
        super(Properties.of()
                .offsetType(OffsetType.XYZ)
                .sound(SoundType.STONE)
                .strength(3.0F, 50.0F)
                .noOcclusion()
                .hasPostProcess((state, getter, pos) -> true)
                .emissiveRendering((state, getter, pos) -> true)
                .isRedstoneConductor((state, getter, pos) -> false)
                .dynamicShape()
        );
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
        return box(5.0D, 6.0D, 0.0D, 9.0D, 10.0D, 16.0D);
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

    public void increaseSkillPoint(Entity entity, float value) {
        if (!(entity instanceof Player pEntity)) return;
//      ADD THIS CODE IN AV_EFM

//        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(pEntity, PlayerPatch.class);
//        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;
//
//        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.SHADOW_OBSIDIAN_PILLAR);
//        if (skillContainer == null) return;
//
//        ShadowObsidianPillarSkill skill = (ShadowObsidianPillarSkill) skillContainer.getSkill();
//
//        float currentResource = skillContainer.getResource();
//        float neededResource = skillContainer.getNeededResource();
//        float addResource = Math.min(value, neededResource);
//
//        skill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
    }

    @Override
    public void customHurtLogic(Entity entity, Entity owner, ServerLevel serverLevel, BlockPos blockPos) {
        super.customHurtLogic(entity, owner, serverLevel, blockPos);
        AnnoyingVillagersModParticleTypes.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, entity, entity);
        serverLevel.sendParticles(
                AnnoyingVillagersModParticleTypes.SPARK.get(),
                entity.getX(), entity.getY() + 1.5, entity.getZ() + 0.8,
                5,
                0, 0, 0,
                0.1
        );
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                AnnoyingVillagersModSounds.OB_PLACE.get(),
                SoundSource.BLOCKS,
                0.5F, 1.0F
        );
        if (owner != null) {
            if (owner instanceof Player player) {
                entity.hurt(entity.level().damageSources().playerAttack(player), 1.0F);
                increaseSkillPoint(player, 0.2F);
            } else {
                entity.hurt(entity.level().damageSources().mobAttack((LivingEntity) owner), 1.0F);
            }
        } else {
            entity.hurt(entity.level().damageSources().generic(), 1.0F);
        }
        entity.setDeltaMovement(new Vec3((-6.0 + Math.random() * 5.0) * entity.getLookAngle().x, 0.0D, (-6.0 + Math.random() * 5.0) * entity.getLookAngle().z));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ShadowObsidianShortPillarBlockEntity(pPos, pState);
    }
}
