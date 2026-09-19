package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RisingWallBlockEntity extends Entity {
    private static final double START_BELOW_TARGET = 1.15D;
    private static final int HARD_DESPAWN_TICKS = 200;

    private static final EntityDataAccessor<BlockPos> FINAL_BLOCK_POS =
            SynchedEntityData.defineId(RisingWallBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    private static final EntityDataAccessor<BlockState> RENDER_BLOCK_STATE =
            SynchedEntityData.defineId(RisingWallBlockEntity.class, EntityDataSerializers.BLOCK_STATE);

    private static final EntityDataAccessor<Integer> START_DELAY_TICKS =
            SynchedEntityData.defineId(RisingWallBlockEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> RISE_TICKS =
            SynchedEntityData.defineId(RisingWallBlockEntity.class, EntityDataSerializers.INT);

    private boolean converted;

    public RisingWallBlockEntity(EntityType<? extends RisingWallBlockEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public RisingWallBlockEntity(
            Level level,
            BlockPos finalBlockPos,
            BlockState blockState,
            int startDelayTicks,
            int riseTicks
    ) {
        this(AnnoyingVillagersModEntities.RISING_WALL_BLOCK.get(), level);

        this.setFinalBlockPos(finalBlockPos);
        this.setBlockState(blockState);
        this.setStartDelayTicks(startDelayTicks);
        this.setRiseTicks(riseTicks);

        double x = finalBlockPos.getX() + 0.5D;
        double y = getStartY(finalBlockPos);
        double z = finalBlockPos.getZ() + 0.5D;

        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(FINAL_BLOCK_POS, BlockPos.ZERO);
        builder.define(RENDER_BLOCK_STATE, Blocks.AIR.defaultBlockState());
        builder.define(START_DELAY_TICKS, 0);
        builder.define(RISE_TICKS, 10);
    }

    public BlockPos getFinalBlockPos() {
        return this.entityData.get(FINAL_BLOCK_POS);
    }

    public void setFinalBlockPos(BlockPos pos) {
        this.entityData.set(FINAL_BLOCK_POS, pos);
    }

    public BlockState getBlockState() {
        return this.entityData.get(RENDER_BLOCK_STATE);
    }

    public void setBlockState(BlockState blockState) {
        this.entityData.set(RENDER_BLOCK_STATE, blockState);
    }

    public int getStartDelayTicks() {
        return this.entityData.get(START_DELAY_TICKS);
    }

    public void setStartDelayTicks(int ticks) {
        this.entityData.set(START_DELAY_TICKS, Math.max(0, ticks));
    }

    public int getRiseTicks() {
        return this.entityData.get(RISE_TICKS);
    }

    public void setRiseTicks(int ticks) {
        this.entityData.set(RISE_TICKS, Math.max(1, ticks));
    }

    public boolean isRiseStarted() {
        return this.tickCount >= this.getStartDelayTicks();
    }

    private static double getStartY(BlockPos finalBlockPos) {
        return finalBlockPos.getY() - START_BELOW_TARGET;
    }

    private static double easeOutCubic(double t) {
        t = Math.max(0.0D, Math.min(1.0D, t));
        return 1.0D - Math.pow(1.0D - t, 3.0D);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount > HARD_DESPAWN_TICKS) {
            this.discard();
            return;
        }

        BlockPos finalPos = this.getFinalBlockPos();

        int activeTicks = this.tickCount - this.getStartDelayTicks();

        if (activeTicks < 0) {
            this.setPos(
                    finalPos.getX() + 0.5D,
                    getStartY(finalPos),
                    finalPos.getZ() + 0.5D
            );
            return;
        }

        double progress = (double) activeTicks / (double) this.getRiseTicks();
        double eased = easeOutCubic(progress);

        double startY = getStartY(finalPos);
        double targetY = finalPos.getY();

        double y = startY + (targetY - startY) * eased;

        this.setPos(
                finalPos.getX() + 0.5D,
                y,
                finalPos.getZ() + 0.5D
        );

        if (!this.level().isClientSide && !this.converted && activeTicks >= this.getRiseTicks()) {
            this.converted = true;
            this.convertToRealBlock();
        }
    }

    private void convertToRealBlock() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockState blockState = this.getBlockState();

        if (blockState.isAir() || blockState.getRenderShape() != RenderShape.MODEL) {
            this.discard();
            return;
        }

        BlockPos finalPos = this.getFinalBlockPos();

        if (canPlaceRealBlockAt(finalPos)) {
            serverLevel.setBlockAndUpdate(finalPos, blockState);

            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                    finalPos.getX() + 0.5D,
                    finalPos.getY() + 0.5D,
                    finalPos.getZ() + 0.5D,
                    20,
                    0.35D,
                    0.35D,
                    0.35D,
                    0.08D
            );

            serverLevel.playSound(
                    null,
                    finalPos,
                    blockState.getSoundType().getPlaceSound(),
                    SoundSource.BLOCKS,
                    1.0F,
                    0.85F + serverLevel.random.nextFloat() * 0.25F
            );
        }

        this.discard();
    }

    private boolean canPlaceRealBlockAt(BlockPos pos) {
        if (!this.level().getFluidState(pos).isEmpty()) {
            return false;
        }

        BlockState current = this.level().getBlockState(pos);
        return current.isAir() || current.canBeReplaced();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("FinalBlockPos", NbtUtils.writeBlockPos(this.getFinalBlockPos()));
        tag.put("BlockState", NbtUtils.writeBlockState(this.getBlockState()));
        tag.putInt("StartDelayTicks", this.getStartDelayTicks());
        tag.putInt("RiseTicks", this.getRiseTicks());
        tag.putBoolean("Converted", this.converted);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setFinalBlockPos(NbtUtils.readBlockPos(tag, "FinalBlockPos").orElse(BlockPos.ZERO));

        this.setBlockState(NbtUtils.readBlockState(
                this.level().holderLookup(Registries.BLOCK),
                tag.getCompound("BlockState")
        ));

        this.setStartDelayTicks(tag.getInt("StartDelayTicks"));
        this.setRiseTicks(tag.getInt("RiseTicks"));
        this.converted = tag.getBoolean("Converted");
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    }
