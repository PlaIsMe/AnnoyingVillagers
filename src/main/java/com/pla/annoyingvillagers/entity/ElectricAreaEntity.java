package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class ElectricAreaEntity extends Entity {
    private static final String TAG_OWNER_UUID = "OwnerUUID";
    private static final String TAG_HALF_SIZE = "HalfSize";
    private static final String TAG_DURATION_TICKS = "DurationTicks";
    private static final String TAG_DAMAGE_AMOUNT = "DamageAmount";
    private static final String TAG_DAMAGE_INTERVAL = "DamageInterval";

    @Nullable
    private UUID ownerUUID;

    private double halfSize = 1.5D;
    private int durationTicks = 100;
    private float damageAmount = 4.0F;
    private int damageInterval = 10;

    public ElectricAreaEntity(EntityType<? extends ElectricAreaEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public ElectricAreaEntity(Level level, LivingEntity owner, Vec3 pos, double halfSize, int durationTicks, float damageAmount, int damageInterval) {
        this(AnnoyingVillagersModEntities.ELECTRIC_AREA.get(), level);
        this.ownerUUID = owner.getUUID();
        this.halfSize = halfSize;
        this.durationTicks = durationTicks;
        this.damageAmount = damageAmount;
        this.damageInterval = Math.max(1, damageInterval);
        this.setPos(pos.x, pos.y, pos.z);
    }

    @Nullable
    public LivingEntity getOwnerLiving() {
        if (!(this.level() instanceof ServerLevel serverLevel) || this.ownerUUID == null) {
            return null;
        }

        Entity entity = serverLevel.getEntity(this.ownerUUID);
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public void tick() {
        super.tick();

        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (this.tickCount % this.damageInterval == 0 || this.tickCount == 1) {
            this.damageEntitiesInZone();
        }

        if (this.tickCount % 5 == 0) {
            serverLevel.sendParticles(
                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                    this.getX(),
                    this.getY() + 0.2D,
                    this.getZ(),
                    4,
                    this.halfSize * 0.6D,
                    0.25D,
                    this.halfSize * 0.6D,
                    0.0D
            );
        }

        if (this.tickCount >= this.durationTicks) {
            this.discard();
        }
    }

    private void damageEntitiesInZone() {
        LivingEntity owner = this.getOwnerLiving();
        for (LivingEntity target : this.level().getEntitiesOfClass(LivingEntity.class,
                this.makeDamageBox(),
                living -> this.isValidTarget(owner, living))) {
            target.setDeltaMovement(0, 0, 0);
            target.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.ELECTRIFY, 12, 2));
        }
    }

    private boolean isValidTarget(@Nullable LivingEntity owner, LivingEntity target) {
        if (!target.isAlive() || target.isSpectator()) {
            return false;
        }

        if (owner == null) {
            return true;
        }

        if (target == owner) {
            return false;
        }

        if (target instanceof Player player && player.isCreative()) {
            return false;
        }

        if (owner instanceof BlueDemonEntity blueDemonEntity
                && blueDemonEntity.getBbqEntity() != null
                && target == blueDemonEntity.getBbqEntity()) {
            return false;
        }

        return !owner.isAlliedTo(target);
    }

    private AABB makeDamageBox() {
        return new AABB(
                this.getX() - this.halfSize,
                this.getY() - 1.0D,
                this.getZ() - this.halfSize,
                this.getX() + this.halfSize,
                this.getY() + 2.5D,
                this.getZ() + this.halfSize
        );
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        CompoundTag tag = com.pla.annoyingvillagers.util.LegacyValueIO.read(input);
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(tag, TAG_OWNER_UUID)) {
            this.ownerUUID = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(tag, TAG_OWNER_UUID);
        }

        this.halfSize = tag.getDoubleOr(TAG_HALF_SIZE, 0.0D);
        this.durationTicks = tag.getIntOr(TAG_DURATION_TICKS, 0);
        this.damageAmount = tag.getFloatOr(TAG_DAMAGE_AMOUNT, 0.0F);
        this.damageInterval = Math.max(1, tag.getIntOr(TAG_DAMAGE_INTERVAL, 0));
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        CompoundTag tag = new CompoundTag();
        if (this.ownerUUID != null) {
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, TAG_OWNER_UUID, this.ownerUUID);
        }

        tag.putDouble(TAG_HALF_SIZE, this.halfSize);
        tag.putInt(TAG_DURATION_TICKS, this.durationTicks);
        tag.putFloat(TAG_DAMAGE_AMOUNT, this.damageAmount);
        tag.putInt(TAG_DAMAGE_INTERVAL, this.damageInterval);
    
        com.pla.annoyingvillagers.util.LegacyValueIO.write(output, tag);
    }

    @Override
    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource source, float amount) {
        return false;
    }

    }
