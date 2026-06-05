package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.UUID;

public class FloatingLookBlockEntity extends LivingEntity {
    public static final int PHASE_RISING = 0;
    public static final int PHASE_FLOATING = 1;
    public static final int PHASE_FALLING = 2;

    private static final int RISE_TICKS = 16;
    private static final int FLOAT_TICKS = 200;
    private static final int HARD_DESPAWN_TICKS = 20 * 30;
    private static final int OWNER_ATTRACT_TICKS = RISE_TICKS + FLOAT_TICKS;

    private static final double LIFT_HEIGHT = 2.25D;
    private static final double FLOAT_BOB = 0.08D;
    private static final double FALL_SPEED = 0.09D;

    private static final float LAUNCHED_PROJECTILE_DAMAGE = 1.5F;
    private static final float LAUNCHED_PROJECTILE_SPEED = 1.85F;

    private static final EntityDataAccessor<BlockPos> DATA_ORIGINAL_POS =
            SynchedEntityData.defineId(FloatingLookBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE =
            SynchedEntityData.defineId(FloatingLookBlockEntity.class, EntityDataSerializers.BLOCK_STATE);

    private static final EntityDataAccessor<Integer> DATA_PHASE =
            SynchedEntityData.defineId(FloatingLookBlockEntity.class, EntityDataSerializers.INT);

    private int phaseTicks;

    @Nullable
    private UUID ownerUuid;

    @Nullable
    private UUID ownerPreviousTargetUuid;

    @Nullable
    private CompoundTag carriedBlockEntityTag;

    private boolean launched;
    private boolean attractingOwner;
    private boolean ownerAttractionFinished;
    private int ownerAttractTicks;

    public FloatingLookBlockEntity(EntityType<? extends FloatingLookBlockEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public FloatingLookBlockEntity(
            Level level,
            BlockPos originalPos,
            BlockState blockState,
            @Nullable UUID ownerUuid,
            @Nullable CompoundTag carriedBlockEntityTag
    ) {
        this(AnnoyingVillagersModEntities.FLOATING_LOOK_BLOCK.get(), level);

        this.setOriginalPos(originalPos);
        this.setCarriedBlock(blockState);
        this.ownerUuid = ownerUuid;
        this.carriedBlockEntityTag = carriedBlockEntityTag == null ? null : carriedBlockEntityTag.copy();

        double x = originalPos.getX() + 0.5D;
        double y = getStartY(originalPos);
        double z = originalPos.getZ() + 0.5D;

        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ORIGINAL_POS, BlockPos.ZERO);
        this.entityData.define(DATA_BLOCK_STATE, Blocks.AIR.defaultBlockState());
        this.entityData.define(DATA_PHASE, PHASE_RISING);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D);
    }

    public BlockPos getOriginalPos() {
        return this.entityData.get(DATA_ORIGINAL_POS);
    }

    public void setOriginalPos(BlockPos pos) {
        this.entityData.set(DATA_ORIGINAL_POS, pos);
    }

    public BlockState getCarriedBlock() {
        return this.entityData.get(DATA_BLOCK_STATE);
    }

    public void setCarriedBlock(BlockState state) {
        this.entityData.set(DATA_BLOCK_STATE, state);
    }

    public int getPhase() {
        return this.entityData.get(DATA_PHASE);
    }

    private void setPhase(int phase) {
        this.entityData.set(DATA_PHASE, phase);
        this.phaseTicks = 0;
    }

    private static double getStartY(BlockPos originalPos) {
        return originalPos.getY() + 0.5D;
    }

    private static double getFloatY(BlockPos originalPos) {
        return originalPos.getY() + 0.5D + LIFT_HEIGHT;
    }

    private static double easeOutCubic(double t) {
        t = Math.max(0.0D, Math.min(1.0D, t));
        return 1.0D - Math.pow(1.0D - t, 3.0D);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            return;
        }

        if (this.tickCount > HARD_DESPAWN_TICKS) {
            this.discard();
            return;
        }

        BlockState state = this.getCarriedBlock();

        if (state.isAir() || state.getRenderShape() != RenderShape.MODEL) {
            this.discard();
            return;
        }

        this.phaseTicks++;

        switch (this.getPhase()) {
            case PHASE_RISING -> tickRising();
            case PHASE_FLOATING -> tickFloating();
            case PHASE_FALLING -> tickFalling();
            default -> this.discard();
        }

        if (!this.isRemoved() && !this.launched) {
            this.tickOwnerAttraction();
        }
    }

    private void tickRising() {
        BlockPos originalPos = this.getOriginalPos();

        double x = originalPos.getX() + 0.5D;
        double z = originalPos.getZ() + 0.5D;

        double startY = getStartY(originalPos);
        double targetY = getFloatY(originalPos);

        double progress = (double) this.phaseTicks / (double) RISE_TICKS;
        double eased = easeOutCubic(progress);

        double y = startY + (targetY - startY) * eased;

        this.setPos(x, y, z);

        if (this.phaseTicks >= RISE_TICKS) {
            this.setPos(x, targetY, z);
            this.setPhase(PHASE_FLOATING);
        }
    }

    private void tickFloating() {
        BlockPos originalPos = this.getOriginalPos();

        double x = originalPos.getX() + 0.5D;
        double z = originalPos.getZ() + 0.5D;
        double y = getFloatY(originalPos) + Math.sin(this.tickCount * 0.18D) * FLOAT_BOB;

        this.setPos(x, y, z);

        if (this.phaseTicks >= FLOAT_TICKS) {
            this.setPhase(PHASE_FALLING);
        }
    }

    private void tickFalling() {
        this.restoreOwnerTarget();
        this.setPos(this.getX(), this.getY() - FALL_SPEED, this.getZ());

        if (this.level() instanceof ServerLevel serverLevel) {
            tryRestoreIfTouchingSupport(serverLevel);
        }

        if (this.getY() < this.level().getMinBuildHeight() - 8) {
            this.discard();
        }
    }

    private void tickOwnerAttraction() {
        if (this.ownerAttractionFinished) {
            return;
        }

        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Mob owner = this.getOwnerMob(serverLevel);

        if (owner == null || this.getPhase() == PHASE_FALLING) {
            this.restoreOwnerTarget();
            return;
        }

        if (!this.attractingOwner) {
            LivingEntity previousTarget = owner.getTarget();
            this.ownerPreviousTargetUuid = previousTarget != null && previousTarget != this
                    ? previousTarget.getUUID()
                    : null;
            this.attractingOwner = true;
            this.ownerAttractTicks = 0;
        }

        if (this.ownerAttractTicks++ >= OWNER_ATTRACT_TICKS) {
            this.restoreOwnerTarget();
            return;
        }

        owner.setTarget(this);
        owner.getLookControl().setLookAt(this, 30.0F, 30.0F);
    }

    @Nullable
    private Mob getOwnerMob(ServerLevel serverLevel) {
        if (this.ownerUuid == null) {
            return null;
        }

        Entity owner = serverLevel.getEntity(this.ownerUuid);
        return owner instanceof Mob mob && mob.isAlive() ? mob : null;
    }

    private void restoreOwnerTarget() {
        if (!this.attractingOwner || !(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Mob owner = this.getOwnerMob(serverLevel);

        if (owner != null && owner.getTarget() == this) {
            LivingEntity previousTarget = this.getPreviousOwnerTarget(serverLevel);
            owner.setTarget(previousTarget);
        }

        this.attractingOwner = false;
        this.ownerAttractionFinished = true;
        this.ownerAttractTicks = 0;
        this.ownerPreviousTargetUuid = null;
    }

    @Nullable
    private LivingEntity getPreviousOwnerTarget(ServerLevel serverLevel) {
        if (this.ownerPreviousTargetUuid == null) {
            return null;
        }

        Entity entity = serverLevel.getEntity(this.ownerPreviousTargetUuid);

        if (entity == null) {
            entity = serverLevel.getPlayerByUUID(this.ownerPreviousTargetUuid);
        }

        return entity instanceof LivingEntity livingEntity && livingEntity.isAlive()
                ? livingEntity
                : null;
    }

    private void tryRestoreIfTouchingSupport(ServerLevel level) {
        double bottomY = this.getY() - 0.5D;

        BlockPos supportPos = BlockPos.containing(
                this.getX(),
                bottomY - 0.04D,
                this.getZ()
        );

        if (!isSolidSupport(level, supportPos)) {
            return;
        }

        BlockPos placePos = supportPos.above();

        if (!canRestoreAt(level, placePos)) {
            this.discard();
            return;
        }

        restoreAsBlock(level, placePos);
    }

    private boolean isSolidSupport(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        if (state.isAir()) {
            return false;
        }

        if (!state.getFluidState().isEmpty()) {
            return false;
        }

        return !state.getCollisionShape(level, pos).isEmpty();
    }

    private boolean canRestoreAt(ServerLevel level, BlockPos pos) {
        if (!level.getFluidState(pos).isEmpty()) {
            return false;
        }

        BlockState current = level.getBlockState(pos);
        return current.isAir() || current.canBeReplaced();
    }

    private void restoreAsBlock(ServerLevel level, BlockPos placePos) {
        BlockState blockState = this.getCarriedBlock();
        BlockState oldState = level.getBlockState(placePos);

        level.setBlockAndUpdate(placePos, blockState);

        if (this.carriedBlockEntityTag != null) {
            BlockEntity blockEntity = level.getBlockEntity(placePos);

            if (blockEntity != null) {
                CompoundTag tag = this.carriedBlockEntityTag.copy();
                tag.putInt("x", placePos.getX());
                tag.putInt("y", placePos.getY());
                tag.putInt("z", placePos.getZ());

                blockEntity.load(tag);
                blockEntity.setChanged();

                level.sendBlockUpdated(placePos, oldState, blockState, 3);
            }
        }

        level.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                placePos.getX() + 0.5D,
                placePos.getY() + 0.5D,
                placePos.getZ() + 0.5D,
                25,
                0.35D,
                0.35D,
                0.35D,
                0.08D
        );

        level.playSound(
                null,
                placePos,
                blockState.getSoundType().getPlaceSound(),
                SoundSource.BLOCKS,
                1.0F,
                0.85F + level.random.nextFloat() * 0.25F
        );

        this.discard();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.level().isClientSide) {
            return true;
        }

        if (this.launched || this.isRemoved()) {
            return true;
        }

        this.launched = true;
        this.launchAsProjectile(source);
        this.discard();

        return true;
    }

    private void launchAsProjectile(DamageSource source) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        BlockState blockState = this.getCarriedBlock();

        if (blockState.isAir()) {
            return;
        }

        LivingEntity shooter = findShooter(source);

        BlockProjectileEntity projectile;

        if (shooter != null) {
            projectile = new BlockProjectileEntity(serverLevel, shooter, blockState);
            projectile.setOwnerUUID(shooter.getUUID());
        } else {
            projectile = new BlockProjectileEntity(
                    AnnoyingVillagersModEntities.BLOCK_PROJECTILE.get(),
                    serverLevel
            );
            projectile.setCarriedBlock(blockState);

            if (this.ownerUuid != null) {
                projectile.setOwnerUUID(this.ownerUuid);
            }
        }

        projectile.setPos(this.getX(), this.getY(), this.getZ());
        projectile.setDamageOverride(LAUNCHED_PROJECTILE_DAMAGE);

        Vec3 direction = this.getLaunchDirection(serverLevel, shooter, projectile.position());

        if (direction.lengthSqr() < 1.0E-6D) {
            direction = new Vec3(0.0D, 0.1D, 1.0D);
        }

        projectile.shoot(
                direction.x,
                direction.y,
                direction.z,
                LAUNCHED_PROJECTILE_SPEED,
                0.05F
        );

        serverLevel.addFreshEntity(projectile);

        serverLevel.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                this.getX(),
                this.getY(),
                this.getZ(),
                30,
                0.35D,
                0.35D,
                0.35D,
                0.1D
        );

        serverLevel.playSound(
                null,
                this.blockPosition(),
                blockState.getSoundType().getBreakSound(),
                SoundSource.BLOCKS,
                1.0F,
                0.9F + serverLevel.random.nextFloat() * 0.2F
        );
    }

    private Vec3 getLaunchDirection(ServerLevel serverLevel, @Nullable LivingEntity shooter, Vec3 projectilePos) {
        if (shooter != null && !(shooter instanceof Player)) {
            LivingEntity previousTarget = this.getPreviousOwnerTarget(serverLevel);
            if (previousTarget != null && previousTarget != shooter && previousTarget != this) {
                Vec3 direction = previousTarget.getEyePosition().subtract(projectilePos);
                if (direction.lengthSqr() > 1.0E-6D) {
                    return direction;
                }
            }
        }

        if (shooter != null) {
            return shooter.getLookAngle();
        }

        return this.getDeltaMovement();
    }

    @Nullable
    private LivingEntity findShooter(DamageSource source) {
        Entity sourceEntity = source.getEntity();

        if (sourceEntity instanceof LivingEntity livingEntity) {
            return livingEntity;
        }

        Entity directEntity = source.getDirectEntity();

        if (directEntity instanceof LivingEntity livingEntity) {
            return livingEntity;
        }

        if (this.ownerUuid != null && this.level() instanceof ServerLevel serverLevel) {
            Entity owner = serverLevel.getEntity(this.ownerUuid);

            if (owner instanceof LivingEntity livingEntity) {
                return livingEntity;
            }
        }

        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("OriginalPos", NbtUtils.writeBlockPos(this.getOriginalPos()));
        tag.put("BlockState", NbtUtils.writeBlockState(this.getCarriedBlock()));
        tag.putInt("Phase", this.getPhase());
        tag.putInt("PhaseTicks", this.phaseTicks);
        tag.putBoolean("Launched", this.launched);

        if (this.ownerUuid != null) {
            tag.putUUID("Owner", this.ownerUuid);
        }

        if (this.carriedBlockEntityTag != null) {
            tag.put("BlockEntityTag", this.carriedBlockEntityTag.copy());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setOriginalPos(NbtUtils.readBlockPos(tag.getCompound("OriginalPos")));

        this.setCarriedBlock(NbtUtils.readBlockState(
                this.level().holderLookup(Registries.BLOCK),
                tag.getCompound("BlockState")
        ));

        this.entityData.set(DATA_PHASE, tag.getInt("Phase"));
        this.phaseTicks = tag.getInt("PhaseTicks");
        this.launched = tag.getBoolean("Launched");

        this.ownerUuid = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;

        this.carriedBlockEntityTag = tag.contains("BlockEntityTag", Tag.TAG_COMPOUND)
                ? tag.getCompound("BlockEntityTag").copy()
                : null;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean skipAttackInteraction(@NotNull Entity attacker) {
        return false;
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        this.restoreOwnerTarget();
        super.remove(reason);
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return EntityDimensions.fixed(1.0F, 1.0F);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
