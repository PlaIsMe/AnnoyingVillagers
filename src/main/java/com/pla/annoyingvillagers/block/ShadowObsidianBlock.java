package com.pla.annoyingvillagers.block;

import java.util.List;
import java.util.Random;

import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.skill.ObsidianWeaponSkill;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

public class ShadowObsidianBlock extends HerobrineObsidianBlock implements EntityBlock {
    public ShadowObsidianBlock() {
        super(Properties.of()
                .sound(new ForgeSoundType(1.0F, 1.0F,
                        AnnoyingVillagersModSounds.LOST,
                        () -> SoundEvents.DEEPSLATE_STEP,
                        () -> SoundEvents.DEEPSLATE_PLACE,
                        () -> SoundEvents.STONE_HIT,
                        AnnoyingVillagersModSounds.SILENT
                ))
                .strength(30.0F, 40.0F)
                .lightLevel((blockstate) -> 4)
                .noOcclusion()
                .hasPostProcess((blockstate, blockgetter, blockpos) -> true)
                .emissiveRendering((blockstate, blockgetter, blockpos) -> true)
                .isRedstoneConductor((blockstate, blockgetter, blockpos) -> false));
    }

    public void appendHoverText(@NotNull ItemStack itemstack, BlockGetter blockgetter, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, blockgetter, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.shadow_obsidian"));
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState blockstate, @NotNull BlockGetter blockgetter, @NotNull BlockPos blockpos, @NotNull CollisionContext collisioncontext) {
        return box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 17.0D);
    }

    @Override
    public void customTickSound(ServerLevel serverLevel, BlockPos blockPos) {
        super.customTickSound(serverLevel, blockPos);
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                SoundEvents.DEEPSLATE_BREAK,
                SoundSource.BLOCKS,
                1.0F, 1.0F
        );
    }

    @Override
    public void customPlaceSound(ServerLevel serverLevel, BlockPos blockPos) {
        super.customPlaceSound(serverLevel, blockPos);
        serverLevel.playSound(
                null,
                blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                SoundEvents.DEEPSLATE_PLACE,
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
        new DelayedTask(new Random().nextInt(5, 15)) {
            @Override
            public void run() {
                serverLevel.playSound(
                        null,
                        blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                        AnnoyingVillagersModSounds.POP.get(),
                        SoundSource.BLOCKS,
                        1.0F, 1.0F
                );
            }
        };
    }

    public void increaseSkillPoint(Entity entity, float value) {
        if (!(entity instanceof Player pEntity)) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(pEntity, PlayerPatch.class);
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_WEAPON);
        if (skillContainer == null) return;

        ObsidianWeaponSkill skill = (ObsidianWeaponSkill) skillContainer.getSkill();

        float currentResource = skillContainer.getResource();
        float neededResource = skillContainer.getNeededResource();
        float addResource = Math.min(value, neededResource);

        skill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
    }

    @Override
    public boolean conditionEveryTickEntityInside(Entity entity) {
        return entity.tickCount % 5 == 0;
    }

    @Override
    public void customHurtLogic(Entity entity, Entity owner, ServerLevel serverLevel, BlockPos blockPos) {
        super.customHurtLogic(entity, owner, serverLevel, blockPos);
        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, entity, entity);
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
                increaseSkillPoint(player, 1.0F);
            } else {
                entity.hurt(entity.level().damageSources().mobAttack((LivingEntity) owner), 1.0F);
            }
        } else {
            entity.hurt(entity.level().damageSources().generic(), 1.0F);
        }
        entity.setDeltaMovement(new Vec3(entity.getLookAngle().x * -2.0D, 0.4D, entity.getLookAngle().z * -2.0D));
        if (Math.random() <= 0.5D) {
            new DelayedTask(1) {
                @Override
                public void run() {
                    if (entity.level() instanceof ServerLevel && entity instanceof Mob mob) {
                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingEntityPatch != null && !livingEntityPatch.isStunned()) {
                            livingEntityPatch.applyStun(StunType.LONG, 10.0F);
                        }
                    }
                }
            };

            if (Math.random() <= 0.3D) {
                new DelayedTask(1) {
                    @Override
                    public void run() {
                        if (entity.level() instanceof ServerLevel && entity instanceof Mob mob) {
                            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                            if (livingEntityPatch != null && !livingEntityPatch.isStunned()) {
                                livingEntityPatch.applyStun(StunType.KNOCKDOWN, 10.0F);
                            }
                        }
                    }
                };
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ShadowObsidianBlockEntity(pPos, pState);
    }
}
