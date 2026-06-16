package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.blockentity.ShadowObsidianLongPillarBlockEntity;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.UUID;

public class BlockProjectileEntity extends ThrowableProjectile {
    private static final EntityDataAccessor<BlockState> DATA_BLOCK =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<Float> ROT_X =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Y =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Z =
            SynchedEntityData.defineId(BlockProjectileEntity.class, EntityDataSerializers.FLOAT);

    private boolean notReadyForShoot = false;
    private UUID ownerUUID;
    private static final float NO_DAMAGE_OVERRIDE = -1.0F;
    private float damageOverride = NO_DAMAGE_OVERRIDE;

    public void setDamageOverride(float damageOverride) {
        this.damageOverride = Math.max(0.0F, damageOverride);
    }

    public void clearDamageOverride() {
        this.damageOverride = NO_DAMAGE_OVERRIDE;
    }

    private float resolveImpactDamage() {
        if (this.damageOverride >= 0.0F) {
            return this.damageOverride;
        }

        if (getCarriedBlock().is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get())
                || getCarriedBlock().is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get())
                || getCarriedBlock().is(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get())) {
            return 10.0F;
        }

        return 2.0F;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public void setNotReadyForShoot(boolean notReadyForShoot) {
        this.notReadyForShoot = notReadyForShoot;
    }

    public BlockProjectileEntity(EntityType<? extends BlockProjectileEntity> type, Level level) {
        super(type, level);
        initRandomRotation();
    }

    public BlockProjectileEntity(Level level, LivingEntity shooter, BlockState block) {
        super(AnnoyingVillagersModEntities.BLOCK_PROJECTILE.get(), shooter, level);
        setCarriedBlock(block);
        initRandomRotation();
    }

    public void setRotX(float v){ this.entityData.set(ROT_X, v); }
    public void setRotY(float v){ this.entityData.set(ROT_Y, v); }
    public void setRotZ(float v){ this.entityData.set(ROT_Z, v); }
    public float getRotX(){ return this.entityData.get(ROT_X); }
    public float getRotY(){ return this.entityData.get(ROT_Y); }
    public float getRotZ(){ return this.entityData.get(ROT_Z); }

    private void initRandomRotation() {
        if (!level().isClientSide) {
            var r = this.random;
            setRotX((r.nextFloat() - 0.5f) * 10f);
            setRotY((r.nextFloat() - 0.5f) * 10f);
            setRotZ((r.nextFloat() - 0.5f) * 10f);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_BLOCK, Blocks.STONE.defaultBlockState());
        this.entityData.define(ROT_X, 0f);
        this.entityData.define(ROT_Y, 0f);
        this.entityData.define(ROT_Z, 0f);
    }

    public void setCarriedBlock(BlockState state) {
        this.entityData.set(DATA_BLOCK, state);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (this.notReadyForShoot) return;
        Entity target = result.getEntity();
        final UUID ownerId = this.ownerUUID;
        final boolean isHerobrine = HerobrineUtil.isHerobrineFaction(target);

        boolean blockDamage =
                (ownerId == null && isHerobrine)
                        || (ownerId != null && target instanceof Player p
                        && p.getUUID().equals(ownerId));

        if (blockDamage) return;

        if (target.level() instanceof ServerLevel serverLevel) {
            EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, this, target);
            serverLevel.playSound(
                    null,
                    this.getX(), this.getY(), this.getZ(),
                    AnnoyingVillagersModSounds.OBSIDIAN_HIT.get(),
                    SoundSource.BLOCKS,
                    0.5F, 1.0F
            );

            float damage = resolveImpactDamage();
            if (this.getOwner() == null) {
                target.hurt(target.level().damageSources().generic(), damage);
            } else {
                target.hurt(target.level().damageSources().indirectMagic(this, this.getOwner()), damage);
            }

            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.applyStun(StunType.LONG, 20.0F);
            }

            if (target instanceof LivingEntity livingEntity) {
                float strength = 1.0F;
                double dx = this.getX() - target.getX();
                double dz = this.getZ() - target.getZ();
                livingEntity.knockback(strength, dx, dz);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide && !this.isRemoved() && !this.notReadyForShoot) {
            BlockPos pos = this.blockPosition();
            if (tryPlaceInLiquid(pos)) {
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        if (this.notReadyForShoot) return;
        BlockPos pos = result.getBlockPos();
        BlockState hitState = level().getBlockState(pos);

        if (!level().isClientSide) {
            if (tryPlaceInLiquid(pos)) {
                this.discard();
                return;
            }

            if (hitState.canBeReplaced()) {
                return;
            }

            BlockPos placePos = pos.relative(result.getDirection());
            if (canPlaceAt(placePos)) {
                placeCarriedBlock(placePos, getReplaceByLiquid(level().getFluidState(placePos)));
            }
            this.discard();
        }
    }

    private boolean tryPlaceInLiquid(BlockPos pos) {
        FluidState fluidState = level().getFluidState(pos);
        int replaceByLiquid = getReplaceByLiquid(fluidState);
        if (replaceByLiquid == 0) {
            return false;
        }

        if (!level().getBlockState(pos).canBeReplaced()) {
            return false;
        }

        placeCarriedBlock(pos, replaceByLiquid);
        return true;
    }

    private boolean canPlaceAt(BlockPos pos) {
        BlockState state = level().getBlockState(pos);
        FluidState fluidState = level().getFluidState(pos);
        if (!fluidState.isEmpty()) {
            return state.canBeReplaced() && getReplaceByLiquid(fluidState) != 0;
        }
        return state.isAir() || state.canBeReplaced();
    }

    private int getReplaceByLiquid(FluidState fluidState) {
        if (fluidState.is(FluidTags.WATER)) {
            return 1;
        }
        if (fluidState.is(FluidTags.LAVA)) {
            return 2;
        }
        return 0;
    }

    private void placeCarriedBlock(BlockPos placePos, int replaceByLiquid) {
        UUID owner = this.ownerUUID;
        BlockState placeState = getPlacementState(owner, replaceByLiquid);

        level().setBlockAndUpdate(placePos, placeState);

        BlockEntity blockEntity = level().getBlockEntity(placePos);
        if (owner != null && blockEntity != null) {
            setBlockEntityOwner(blockEntity, owner);
        }
    }

    private BlockState getPlacementState(UUID owner, int replaceByLiquid) {
        BlockState placeState;
        if (getCarriedBlock().is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR.get())
                || getCarriedBlock().is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get())) {
            placeState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get().defaultBlockState();
        } else if (getCarriedBlock().is(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get())) {
            placeState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState();
        } else if (getCarriedBlock().is(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get())) {
            placeState = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState();
        } else {
            placeState = getCarriedBlock();
        }

        if (owner != null && placeState.hasProperty(HerobrineObsidianBlock.FROM_PLAYER)) {
            placeState = placeState.setValue(HerobrineObsidianBlock.FROM_PLAYER, true);
        }

        if (replaceByLiquid != 0 && placeState.hasProperty(HerobrineObsidianBlock.REPLACE_BY_LIQUID)) {
            placeState = placeState.setValue(HerobrineObsidianBlock.REPLACE_BY_LIQUID, replaceByLiquid);
        }

        return placeState;
    }

    private void setBlockEntityOwner(BlockEntity blockEntity, UUID owner) {
        if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
            obsidianBlockEntity.setOwner(owner);
        } else if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
            shadowObsidianBlockEntity.setOwner(owner);
        } else if (blockEntity instanceof ShadowObsidianLongPillarBlockEntity shadowObsidianLongPillarBlockEntity) {
            shadowObsidianLongPillarBlockEntity.setOwner(owner);
        }

        blockEntity.setChanged();
    }

    public BlockState getCarriedBlock() {
        return this.entityData.get(DATA_BLOCK);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("Block", NbtUtils.writeBlockState(getCarriedBlock()));
        tag.putFloat("RotX", getRotX());
        tag.putFloat("RotY", getRotY());
        tag.putFloat("RotZ", getRotZ());
        tag.putBoolean("NotReadyForShoot", notReadyForShoot);
        tag.putFloat("DamageOverride", this.damageOverride);
        if (this.ownerUUID != null) {
            tag.putUUID("OwnerUUID", this.ownerUUID);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Block")) {
            setCarriedBlock(NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("Block")));
        }
        setRotX(tag.contains("RotX") ? tag.getFloat("RotX") : 0f);
        setRotY(tag.contains("RotY") ? tag.getFloat("RotY") : 0f);
        setRotZ(tag.contains("RotZ") ? tag.getFloat("RotZ") : 0f);
        notReadyForShoot = tag.getBoolean("NotReadyForShoot");
        this.damageOverride = tag.contains("DamageOverride")
                ? tag.getFloat("DamageOverride")
                : NO_DAMAGE_OVERRIDE;

        this.ownerUUID = tag.hasUUID("OwnerUUID")
                ? tag.getUUID("OwnerUUID")
                : null;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return EntityDimensions.fixed(0.9F, 0.9F);
    }

    @Override
    protected float getGravity() {
        return 0.005F;
    }
}
