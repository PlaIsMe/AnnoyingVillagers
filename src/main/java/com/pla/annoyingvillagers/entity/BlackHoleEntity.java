package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Native Forge 1.20.1 port of the Electroblob's Wizardry black-hole construct.
 *
 * Wizardry's black-hole spell uses a base effect radius of 3 but computes the actual suction radius as twice that
 * value. Its default spell duration is 400 ticks and its suction acceleration is 0.075. This AV implementation keeps
 * those combat values while using modern entity networking, particles and rendering.
 */
public class BlackHoleEntity extends Entity {
    public static final int DEFAULT_LIFETIME = 400;
    public static final float DEFAULT_SIZE_MULTIPLIER = 1.0F;
    public static final float BASE_EFFECT_RADIUS = 3.0F;
    public static final double SUCTION_STRENGTH = 0.075D;
    public static final double DAMAGE_RADIUS = 2.0D;
    public static final float BASE_DAMAGE = 2.0F;

    private static final String TAG_OWNER_UUID = "OwnerUUID";
    private static final String TAG_LIFETIME = "Lifetime";
    private static final String TAG_SIZE_MULTIPLIER = "SizeMultiplier";
    private static final String TAG_DAMAGE_MULTIPLIER = "DamageMultiplier";

    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(BlackHoleEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_LIFETIME = SynchedEntityData.defineId(BlackHoleEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_SIZE_MULTIPLIER = SynchedEntityData.defineId(BlackHoleEntity.class, EntityDataSerializers.FLOAT);

    @Nullable
    private UUID ownerUUID;
    private float damageMultiplier = 1.0F;

    public BlackHoleEntity(EntityType<? extends BlackHoleEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public BlackHoleEntity(Level level, LivingEntity owner, Vec3 position) {
        this(AnnoyingVillagersModEntities.BLACK_HOLE.get(), level);
        this.setOwner(owner);
        this.setPos(position.x, position.y, position.z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_OWNER_ID, -1);
        this.entityData.define(DATA_LIFETIME, DEFAULT_LIFETIME);
        this.entityData.define(DATA_SIZE_MULTIPLIER, DEFAULT_SIZE_MULTIPLIER);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        if (owner == null) {
            this.ownerUUID = null;
            this.entityData.set(DATA_OWNER_ID, -1);
            return;
        }

        this.ownerUUID = owner.getUUID();
        this.entityData.set(DATA_OWNER_ID, owner.getId());
    }

    @Nullable
    public LivingEntity getOwnerLiving() {
        int ownerId = this.entityData.get(DATA_OWNER_ID);
        if (ownerId != -1) {
            Entity entity = this.level().getEntity(ownerId);
            if (entity instanceof LivingEntity living && living.isAlive()) return living;
        }

        if (this.level() instanceof ServerLevel serverLevel && this.ownerUUID != null) {
            Entity entity = serverLevel.getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity living && living.isAlive()) return living;
        }

        return null;
    }

    public int getLifetime() {
        return this.entityData.get(DATA_LIFETIME);
    }

    public void setLifetime(int lifetime) {
        this.entityData.set(DATA_LIFETIME, Math.max(1, lifetime));
    }

    public float getSizeMultiplier() {
        return this.entityData.get(DATA_SIZE_MULTIPLIER);
    }

    public void setSizeMultiplier(float sizeMultiplier) {
        this.entityData.set(DATA_SIZE_MULTIPLIER, Math.max(0.1F, sizeMultiplier));
    }

    public float getDamageMultiplier() {
        return this.damageMultiplier;
    }

    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = Math.max(0.0F, damageMultiplier);
    }

    public double getSuctionRadius() {
        return BASE_EFFECT_RADIUS * 2.0D * this.getSizeMultiplier();
    }

    @Override
    public void tick() {
        super.tick();
        this.noPhysics = true;
        this.setNoGravity(true);
        this.setDeltaMovement(Vec3.ZERO);

        if (this.level().isClientSide) {
            this.spawnClientParticles();
            return;
        }

        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        int lifetime = this.getLifetime();
        if (lifetime - this.tickCount == 75) {
            this.level().playSound(null, this.blockPosition(), AnnoyingVillagersModSounds.BLACK_HOLE_VANISH.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
        } else if (this.tickCount % 80 == 1 && this.tickCount + 80 < lifetime) {
            this.level().playSound(null, this.blockPosition(), AnnoyingVillagersModSounds.BLACK_HOLE_AMBIENT.get(), SoundSource.HOSTILE, 1.5F, 1.0F);
        }

        this.pullLivingEntities(serverLevel);

        if (this.tickCount >= lifetime) this.discard();
    }

    private void pullLivingEntities(ServerLevel serverLevel) {
        double radius = this.getSuctionRadius();
        Vec3 centre = this.position();
        AABB searchBox = new AABB(centre.x - radius, centre.y - radius, centre.z - radius, centre.x + radius, centre.y + radius, centre.z + radius);
        LivingEntity owner = this.getOwnerLiving();

        for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, searchBox, living -> this.isValidTarget(owner, living))) {
            Vec3 targetCentre = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
            Vec3 pullVector = centre.subtract(targetCentre);
            double distance = pullVector.length();
            if (distance <= 1.0E-5D || distance > radius) continue;

            double closeness = 1.0D - Mth.clamp(distance / radius, 0.0D, 1.0D);
            double strength = SUCTION_STRENGTH * (1.0D + closeness * 0.75D);
            Vec3 acceleration = pullVector.normalize().scale(strength);
            Vec3 movement = target.getDeltaMovement().scale(0.985D).add(acceleration);

            double maxSpeed = 1.15D;
            if (movement.lengthSqr() > maxSpeed * maxSpeed) movement = movement.normalize().scale(maxSpeed);

            target.setDeltaMovement(movement);
            target.fallDistance = 0.0F;
            target.hurtMarked = true;
            target.hasImpulse = true;

            if (target instanceof ServerPlayer serverPlayer) serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(target));

            if (distance <= DAMAGE_RADIUS) {
                DamageSource source = owner == null ? this.damageSources().magic() : this.damageSources().indirectMagic(this, owner);
                target.hurt(source, BASE_DAMAGE * this.damageMultiplier);
            }
        }
    }

    private boolean isValidTarget(@Nullable LivingEntity owner, LivingEntity target) {
        if (!target.isAlive() || target.isSpectator()) return false;
        if (target == owner) return false;
        if (target instanceof Player player && player.isCreative()) return false;
        return owner == null || (!owner.isAlliedTo(target) && !target.isAlliedTo(owner));
    }

    private void spawnClientParticles() {
        int lifetime = this.getLifetime();
        if (this.tickCount + 40 >= lifetime) return;

        for (int i = 0; i < 5; i++) {
            Vec3 shell = randomSphereVector(this.random.nextDouble() * 1.8D + 0.35D);
            Vec3 velocity = shell.scale(-0.025D - this.random.nextDouble() * 0.025D);
            this.level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), this.getX() + shell.x, this.getY() + shell.y, this.getZ() + shell.z, velocity.x, velocity.y, velocity.z);
        }

        if ((this.tickCount & 1) == 0) {
            for (int i = 0; i < 4; i++) {
                Vec3 shell = randomSphereVector(this.random.nextDouble() * 2.5D + 2.0D);
                Vec3 velocity = shell.scale(-0.035D - this.random.nextDouble() * 0.02D);
                this.level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), this.getX() + shell.x, this.getY() + shell.y, this.getZ() + shell.z, velocity.x, velocity.y, velocity.z);
            }
        }
    }

    private Vec3 randomSphereVector(double radius) {
        double theta = Math.PI * 2.0D * this.random.nextDouble();
        double phi = Math.acos(2.0D * this.random.nextDouble() - 1.0D);
        double sinPhi = Math.sin(phi);
        return new Vec3(radius * sinPhi * Math.cos(theta), radius * Math.cos(phi), radius * sinPhi * Math.sin(theta));
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        if (tag.hasUUID(TAG_OWNER_UUID)) this.ownerUUID = tag.getUUID(TAG_OWNER_UUID);
        if (tag.contains(TAG_LIFETIME)) this.setLifetime(tag.getInt(TAG_LIFETIME));
        if (tag.contains(TAG_SIZE_MULTIPLIER)) this.setSizeMultiplier(tag.getFloat(TAG_SIZE_MULTIPLIER));
        if (tag.contains(TAG_DAMAGE_MULTIPLIER)) this.damageMultiplier = tag.getFloat(TAG_DAMAGE_MULTIPLIER);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        if (this.ownerUUID != null) tag.putUUID(TAG_OWNER_UUID, this.ownerUUID);
        tag.putInt(TAG_LIFETIME, this.getLifetime());
        tag.putFloat(TAG_SIZE_MULTIPLIER, this.getSizeMultiplier());
        tag.putFloat(TAG_DAMAGE_MULTIPLIER, this.damageMultiplier);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
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
