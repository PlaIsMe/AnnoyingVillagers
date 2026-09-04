package com.pla.annoyingvillagers.block;

import java.util.*;

import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.client.particle.HitParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ObsidianBlock extends HerobrineObsidianBlock implements EntityBlock {
    public ObsidianBlock() {
        super(Properties.of()
                .sound(new ForgeSoundType(1.0F, 1.0F,
                        () -> SoundEvents.STONE_BREAK,
                        () -> SoundEvents.STONE_STEP,
                        () -> SoundEvents.STONE_PLACE,
                        () -> SoundEvents.STONE_HIT,
                        () -> SoundEvents.STONE_FALL
                ))
                .strength(60.0F, 40.0F)
                .lightLevel((blockstate) -> 4)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false));
    }

    public void appendHoverText(@NotNull ItemStack itemstack, BlockGetter blockgetter, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, blockgetter, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.obsidian"));
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 17.0D);
    }

    @Override
    public void customTickSound(ServerLevel serverLevel, BlockPos blockPos) {
        super.customTickSound(serverLevel, blockPos);
    }

    @Override
    public void customPlaceSound(ServerLevel serverLevel, BlockPos blockPos) {
        super.customPlaceSound(serverLevel, blockPos);
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                SoundEvents.STONE_PLACE,
                SoundSource.BLOCKS,
                new Random().nextFloat(0.0F, 0.7F), 1.0F
        );
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                AnnoyingVillagersModSounds.OB_PLACE.get(),
                SoundSource.BLOCKS,
                0.5F, 1.0F
        );
    }

    @Override
    public boolean conditionEveryTickEntityInside(Entity entity) {
        return entity.tickCount % 5 == 0;
    }

    @Override
    public void customHurtLogic(Entity entity, Entity owner, ServerLevel serverLevel, BlockPos blockPos) {
        super.customHurtLogic(entity, owner, serverLevel, blockPos);
        AnnoyingVillagersModParticleTypes.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, entity, entity);
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                AnnoyingVillagersModSounds.OBSIDIAN_HIT.get(),
                SoundSource.BLOCKS,
                0.5F, 1.0F
        );
        if (owner != null) {
            if (owner instanceof Player player) {
                entity.hurt(entity.level().damageSources().playerAttack(player), 1.0F);
            } else {
                entity.hurt(entity.level().damageSources().mobAttack((LivingEntity) owner), 1.0F);
            }
        } else {
            entity.hurt(entity.level().damageSources().generic(), 1.0F);
        }
        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -2.0D, 0.4D, entity.getLookAngle().z * -2.0D));
        applyEpicFightRandomStun(entity);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ObsidianBlockEntity(pPos, pState);
    }
}
