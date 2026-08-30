package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ShockWaveBlockEntity extends Entity {
    private static final int HARD_DESPAWN_TICKS = 300;
    private static final double MAX_VISIBLE_ABOVE_GROUND = 1.0D / 3.0D;
    private static final double START_BELOW_SURFACE_EPS = 0.02D;

    private static final double GRAVITY = 0.04D;
    private static final double DRAG = 0.98D;

    private static final double TARGET_RISE = MAX_VISIBLE_ABOVE_GROUND + START_BELOW_SURFACE_EPS;
    private static final double INITIAL_UPWARD_VELOCITY = Math.sqrt(2.0D * GRAVITY * TARGET_RISE);

    private static final double HITBOX_INFLATE = 0.05D;
    private static final float DAMAGE = 2.0F;
    private static final double KNOCKBACK = 0.6D;
    private static final double KNOCKUP = 0.15D;
    @Nullable
    private UUID ownerUuid;

    private final Set<UUID> hitOnce = new HashSet<>();

    private static final EntityDataAccessor<BlockPos> SOURCE_BLOCK_POS =
            SynchedEntityData.defineId(ShockWaveBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    private static final EntityDataAccessor<BlockState> RENDER_BLOCK_STATE =
            SynchedEntityData.defineId(ShockWaveBlockEntity.class, EntityDataSerializers.BLOCK_STATE);

    private int lifetimeTicks = 10;

    public ShockWaveBlockEntity(EntityType<? extends ShockWaveBlockEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }


    public ShockWaveBlockEntity(Level level, double x, double surfaceY, double z, BlockState blockState, int lifetimeTicks) {
        this(AnnoyingVillagersModEntities.SHOCKWAVE_BLOCK.get(), level);

        this.setBlockState(blockState);
        this.lifetimeTicks = Math.max(1, lifetimeTicks);

        BlockPos sourcePos = BlockPos.containing(x, surfaceY - 1.0D, z);
        this.setSourceBlockPos(sourcePos);
        double startBottomY = (sourcePos.getY() + 1.0D) - (1.0D + START_BELOW_SURFACE_EPS);

        this.setPos(x, startBottomY, z);
        this.setDeltaMovement(0.0D, INITIAL_UPWARD_VELOCITY, 0.0D);

        this.xo = x;
        this.yo = startBottomY;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SOURCE_BLOCK_POS, BlockPos.ZERO);
        this.entityData.define(RENDER_BLOCK_STATE, Blocks.AIR.defaultBlockState());
    }

    public BlockPos getSourceBlockPos() {
        return this.entityData.get(SOURCE_BLOCK_POS);
    }

    public void setSourceBlockPos(BlockPos pos) {
        this.entityData.set(SOURCE_BLOCK_POS, pos);
    }

    public BlockState getBlockState() {
        return this.entityData.get(RENDER_BLOCK_STATE);
    }

    public void setBlockState(BlockState blockState) {
        this.entityData.set(RENDER_BLOCK_STATE, blockState);
    }

    public void setOwnerUuid(@Nullable UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount > this.lifetimeTicks || this.tickCount > HARD_DESPAWN_TICKS) {
            this.discard();
            return;
        }

        if (!this.isNoGravity()) {
            Vec3 motion = this.getDeltaMovement();
            this.setDeltaMovement(motion.x, motion.y - GRAVITY, motion.z);
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(DRAG));
        double maxBottomY = this.getSourceBlockPos().getY() + MAX_VISIBLE_ABOVE_GROUND;
        if (this.getY() > maxBottomY) {
            this.setPos(this.getX(), maxBottomY, this.getZ());

            Vec3 motion = this.getDeltaMovement();
            if (motion.y > 0.0D) {
                this.setDeltaMovement(motion.x, 0.0D, motion.z);
            }
        }

        if (!this.level().isClientSide) {
            handleEntityHits();
        }
    }

    private void handleEntityHits() {
        AABB hitBox = this.getBoundingBox().inflate(HITBOX_INFLATE, 0.0D, HITBOX_INFLATE);

        for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class, hitBox, this::canHitTarget)) {
            if (!hitOnce.add(target.getUUID())) {
                continue;
            }
            onHitLivingEntity(target);
        }
    }

    private boolean canHitTarget(LivingEntity target) {
        if (!target.isAlive()) return false;
        if (target.isSpectator()) return false;
        return this.ownerUuid == null || !this.ownerUuid.equals(target.getUUID());
    }

    @Nullable
    private Entity getOwnerEntity() {
        if (this.ownerUuid == null) return null;
        if (this.level() instanceof ServerLevel serverLevel) {
            return serverLevel.getEntity(this.ownerUuid);
        }
        return null;
    }

    private DamageSource getShockwaveDamageSource() {
        Entity owner = getOwnerEntity();

        if (owner instanceof Player player) {
            return this.level().damageSources().playerAttack(player);
        }
        if (owner instanceof LivingEntity living) {
            return this.level().damageSources().mobAttack(living);
        }
        return this.level().damageSources().generic();
    }

    public static void playTriedAnimation(Entity target) {
//      ADD THIS CODE IN AV_EFM
//        LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
//        if (targetPatch != null) {
//            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(targetPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
//            if (dynamicAnimation != null && !EpicfightUtil.isLongHitAnimation(dynamicAnimation, targetPatch)) {
//                targetPatch.playAnimationSynchronized(AnimsPugilistSteve.TRIED, 0.0F);
//            }
//        }
//        Create VANILLA_ANIMATION
        if (target.level().isClientSide) {
            return;
        }

        if (target instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 40, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0));
            return;
        }

        if (target instanceof Mob mob
                && RigStunController.supports(mob)
                && !RigStunController.isLongHitAnimation(mob)) {
            RigStunController.applyLegendarySwordKnockdown(mob);
        }
    }

    private void onHitLivingEntity(LivingEntity target) {
        DamageSource source = getShockwaveDamageSource();

        target.hurt(source, DAMAGE);
        playTriedAnimation(target);
        Vec3 dir = target.position().subtract(this.position());
        Vec3 horizontal = new Vec3(dir.x, 0.0D, dir.z);

        if (horizontal.lengthSqr() < 1.0E-6D) {
            horizontal = new Vec3(this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        Vec3 push = horizontal.normalize().scale(KNOCKBACK);
        target.push(push.x, KNOCKUP, push.z);
        target.hurtMarked = true;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("BlockState", NbtUtils.writeBlockState(this.getBlockState()));
        tag.put("SourceBlockPos", NbtUtils.writeBlockPos(this.getSourceBlockPos()));
        tag.putInt("LifetimeTicks", this.lifetimeTicks);
        if (this.ownerUuid != null) {
            tag.putUUID("Owner", this.ownerUuid);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setBlockState(NbtUtils.readBlockState(
                this.level().holderLookup(Registries.BLOCK),
                tag.getCompound("BlockState")
        ));
        this.setSourceBlockPos(NbtUtils.readBlockPos(tag.getCompound("SourceBlockPos")));
        this.lifetimeTicks = Math.max(1, tag.getInt("LifetimeTicks"));
        this.ownerUuid = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}