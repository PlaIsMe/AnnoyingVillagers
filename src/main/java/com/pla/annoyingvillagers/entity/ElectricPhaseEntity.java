package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.ThunderDiamondBladeItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import javax.annotation.Nullable;
import java.util.UUID;

public class ElectricPhaseEntity extends Entity implements IEntityAdditionalSpawnData {
    private static final String TAG_OWNER_UUID = "OwnerUUID";
    private static final String TAG_HALF_SIZE = "HalfSize";
    private static final String TAG_DURATION_TICKS = "DurationTicks";
    private static final String TAG_DAMAGE_AMOUNT = "DamageAmount";
    private static final String TAG_DAMAGE_INTERVAL = "DamageInterval";
    private static final String TAG_KNOCKBACK = "Knockback";
    private static final String TAG_ELECTRIFY_TICKS = "ElectrifyTicks";
    private static final String TAG_ELECTRIFY_AMPLIFIER = "ElectrifyAmplifier";
    private static final int PARTICLE_INTERVAL_TICKS = 10;
    private static final String TAG_MODE = "Mode";
    private static final String TAG_VEL_X = "VelX";
    private static final String TAG_VEL_Y = "VelY";
    private static final String TAG_VEL_Z = "VelZ";
    private static final String TAG_OFFHAND = "Offhand";

    private static final EntityDataAccessor<Boolean> DATA_OFFHAND =
            SynchedEntityData.defineId(ElectricPhaseEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_OWNER_ID =
            SynchedEntityData.defineId(ElectricPhaseEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_MODE =
            SynchedEntityData.defineId(ElectricPhaseEntity.class, EntityDataSerializers.INT);

    public enum Mode {
        FOLLOW_OWNER_SWORD(0),
        PROJECTILE(1);

        private final int id;

        Mode(int id) {
            this.id = id;
        }

        public int id() {
            return this.id;
        }

        public static Mode byId(int id) {
            for (Mode mode : values()) {
                if (mode.id == id) {
                    return mode;
                }
            }

            return PROJECTILE;
        }
    }

    @Nullable
    private UUID ownerUUID;

    private double halfSize = 0.5D;
    private int durationTicks = 40;
    private float damageAmount = 3.0F;
    private int damageInterval = 8;
    private double knockback = 0.45D;

    private int electrifyTicks = 60;
    private int electrifyAmplifier = 0;

    private Vec3 projectileVelocity = Vec3.ZERO;

    public ElectricPhaseEntity(EntityType<? extends ElectricPhaseEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public ElectricPhaseEntity(Level level, LivingEntity owner, Vec3 pos) {
        this(level, owner, pos, 1.0D, 40, 3.0F, 8, 0.45D, 80, 0);
    }

    public ElectricPhaseEntity(Level level,
                               LivingEntity owner,
                               Vec3 pos,
                               double halfSize,
                               int durationTicks,
                               float damageAmount,
                               int damageInterval,
                               double knockback,
                               int electrifyTicks,
                               int electrifyAmplifier) {
        this(AnnoyingVillagersModEntities.ELECTRIC_PHASE.get(), level);

        this.setOwner(owner);
        this.halfSize = halfSize;
        this.durationTicks = durationTicks;
        this.damageAmount = damageAmount;
        this.damageInterval = Math.max(1, damageInterval);
        this.knockback = knockback;
        this.electrifyTicks = electrifyTicks;
        this.electrifyAmplifier = electrifyAmplifier;

        this.setPos(pos.x, pos.y, pos.z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_OWNER_ID, -1);
        this.entityData.define(DATA_MODE, Mode.PROJECTILE.id());
        this.entityData.define(DATA_OFFHAND, false);
    }

    public boolean isOffhand() {
        return this.entityData.get(DATA_OFFHAND);
    }

    public boolean isMainhand() {
        return !this.isOffhand();
    }

    public void setOffhand(boolean offhand) {
        this.entityData.set(DATA_OFFHAND, offhand);
    }

    private static boolean isHoldingThunderDiamondBlade(LivingEntity owner, boolean offhand) {
        ItemStack stack = offhand ? owner.getOffhandItem() : owner.getMainHandItem();
        return stack.getItem() instanceof ThunderDiamondBladeItem;
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
    public Entity getOwnerEntity() {
        int ownerId = this.entityData.get(DATA_OWNER_ID);

        if (ownerId != -1) {
            Entity entity = this.level().getEntity(ownerId);

            if (entity != null) {
                return entity;
            }
        }

        if (this.level() instanceof ServerLevel serverLevel && this.ownerUUID != null) {
            return serverLevel.getEntity(this.ownerUUID);
        }

        return null;
    }

    @Nullable
    public LivingEntity getOwnerLiving() {
        Entity entity = this.getOwnerEntity();
        return entity instanceof LivingEntity living ? living : null;
    }

    public Mode getMode() {
        return Mode.byId(this.entityData.get(DATA_MODE));
    }

    public void setMode(Mode mode) {
        this.entityData.set(DATA_MODE, mode.id());
    }

    public boolean isFollowOwnerSwordMode() {
        return this.getMode() == Mode.FOLLOW_OWNER_SWORD;
    }

    public boolean isProjectileMode() {
        return this.getMode() == Mode.PROJECTILE;
    }

    public void setProjectileVelocity(Vec3 velocity) {
        this.projectileVelocity = velocity == null ? Vec3.ZERO : velocity;
        this.setDeltaMovement(this.projectileVelocity);
    }

    @Override
    public void tick() {
        super.tick();

        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (this.isFollowOwnerSwordMode()) {
            this.updateFollowOwnerSwordPosition();
        } else if (this.isProjectileMode()) {
            this.moveProjectile();
        }

        this.spawnElectricParticlesAroundWeapon(serverLevel);

        if (this.tickCount == 1) {
            this.playElectricSound(serverLevel);
        }

        if (this.tickCount == 1 || this.tickCount % this.damageInterval == 0) {
            this.damageEntitiesInZone();
        }

        if (this.tickCount >= this.durationTicks) {
            this.discard();
        }
    }

    private void updateFollowOwnerSwordPosition() {
        LivingEntity owner = this.getOwnerLiving();

        if (owner == null || !owner.isAlive() || owner.isRemoved()) {
            this.discard();
            return;
        }

        boolean offhand = this.isOffhand();

        if (!isHoldingThunderDiamondBlade(owner, offhand)) {
            boolean otherHand = !offhand;

            if (isHoldingThunderDiamondBlade(owner, otherHand)) {
                this.setOffhand(otherHand);
                offhand = otherHand;
            } else {
                this.discard();
                return;
            }
        }

        Vec3 swordPos = getOwnerSwordPosition(owner, offhand);
        this.setPos(swordPos.x, swordPos.y, swordPos.z);
        this.setDeltaMovement(Vec3.ZERO);
    }

    private void moveProjectile() {
        if (this.projectileVelocity.lengthSqr() < 1.0E-7D) {
            this.setDeltaMovement(Vec3.ZERO);
            return;
        }

        this.setDeltaMovement(this.projectileVelocity);
        this.setPos(
                this.getX() + this.projectileVelocity.x,
                this.getY() + this.projectileVelocity.y,
                this.getZ() + this.projectileVelocity.z
        );
    }

    private void spawnElectricParticlesAroundWeapon(ServerLevel serverLevel) {
        if (!this.isFollowOwnerSwordMode()) {
            return;
        }

        if (this.tickCount % PARTICLE_INTERVAL_TICKS != 0) {
            return;
        }

        LivingEntity owner = this.getOwnerLiving();

        if (owner == null || !owner.isAlive() || owner.isRemoved()) {
            return;
        }

        boolean offhand = this.isOffhand();

        if (!isHoldingThunderDiamondBlade(owner, offhand)) {
            boolean otherHand = !offhand;

            if (isHoldingThunderDiamondBlade(owner, otherHand)) {
                this.setOffhand(otherHand);
                offhand = otherHand;
            } else {
                return;
            }
        }

        Vec3 swordPos = getOwnerSwordPosition(owner, offhand);

        serverLevel.sendParticles(
                AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                swordPos.x,
                swordPos.y,
                swordPos.z,
                1,
                0.0D,
                0.0D,
                0.0D,
                0.0D
        );
    }

    private void playElectricSound(ServerLevel serverLevel) {
        float volume = (float) Mth.nextDouble(serverLevel.random, 0.35D, 0.8D);
        float pitch = (float) Mth.nextDouble(serverLevel.random, 0.9D, 1.25D);

        serverLevel.playSound(
                null,
                this.blockPosition(),
                AnnoyingVillagersModSounds.ELECTRIFY.get(),
                SoundSource.NEUTRAL,
                volume,
                pitch
        );
    }

    private static Vec3 getOwnerSwordPosition(LivingEntity owner, boolean offhand) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    offhand ? Armatures.BIPED.get().toolL : Armatures.BIPED.get().toolR,
                    1.0F,
                    0.25F
            );

            if (pos != null) {
                return pos;
            }
        } catch (Exception ignored) {
        }

        Vec3 look = owner.getLookAngle();
        Vec3 side = new Vec3(-look.z, 0.0D, look.x);

        if (side.lengthSqr() > 1.0E-7D) {
            side = side.normalize();
        } else {
            side = Vec3.ZERO;
        }

        double sideOffset = offhand ? -0.35D : 0.35D;

        return owner.position()
                .add(0.0D, owner.getBbHeight() * 0.65D, 0.0D)
                .add(look.scale(0.75D))
                .add(side.scale(sideOffset));
    }

    private void damageEntitiesInZone() {
        LivingEntity owner = this.getOwnerLiving();

        for (LivingEntity target : this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.makeDamageBox(),
                living -> this.isValidTarget(owner, living)
        )) {
            this.applyElectrify(target);
        }
    }

    private void applyElectrify(LivingEntity target) {
        if (this.electrifyTicks <= 0) {
            return;
        }

        target.addEffect(new MobEffectInstance(
                AnnoyingVillagersModMobEffects.ELECTRIFY.get(),
                this.electrifyTicks,
                this.electrifyAmplifier,
                false,
                true,
                true
        ));
    }

    private boolean isValidTarget(@Nullable LivingEntity owner, LivingEntity target) {
        if (!target.isAlive() || target.isSpectator()) {
            return false;
        }

        if (target instanceof Player player && player.isCreative()) {
            return false;
        }

        if (owner != null) {
            if (target == owner) {
                return false;
            }

            if (owner.isAlliedTo(target)) {
                return false;
            }
        }

        return true;
    }

    private AABB makeDamageBox() {
        return new AABB(
                this.getX() - this.halfSize,
                this.getY() - 0.25D,
                this.getZ() - this.halfSize,
                this.getX() + this.halfSize,
                this.getY() + 1.75D,
                this.getZ() + this.halfSize
        );
    }

    public static void spawnOnOwnerSword(Level level, LivingEntity owner) {
        boolean offhand = shouldUseOffhand(owner);
        spawnOnOwnerSword(level, owner, offhand);
    }

    public static void spawnOnOwnerSword(Level level, LivingEntity owner, boolean offhand) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!isHoldingThunderDiamondBlade(owner, offhand)) {
            boolean otherHand = !offhand;

            if (isHoldingThunderDiamondBlade(owner, otherHand)) {
                offhand = otherHand;
            } else {
                return;
            }
        }

        Vec3 pos = getOwnerSwordPosition(owner, offhand);

        ElectricPhaseEntity electricPhase = new ElectricPhaseEntity(
                level,
                owner,
                pos,
                1.0D,
                20,
                2.5F,
                7,
                0.45D,
                20,
                0
        );

        electricPhase.setOffhand(offhand);
        electricPhase.setMode(Mode.FOLLOW_OWNER_SWORD);

        serverLevel.addFreshEntity(electricPhase);
    }

    private static boolean shouldUseOffhand(LivingEntity owner) {
        boolean holdingMainhand = isHoldingThunderDiamondBlade(owner, false);
        boolean holdingOffhand = isHoldingThunderDiamondBlade(owner, true);

        return !holdingMainhand && holdingOffhand;
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        if (tag.contains(TAG_OFFHAND)) {
            this.setOffhand(tag.getBoolean(TAG_OFFHAND));
        }

        if (tag.hasUUID(TAG_OWNER_UUID)) {
            this.ownerUUID = tag.getUUID(TAG_OWNER_UUID);
        }

        if (tag.contains(TAG_HALF_SIZE)) {
            this.halfSize = tag.getDouble(TAG_HALF_SIZE);
        }

        if (tag.contains(TAG_DURATION_TICKS)) {
            this.durationTicks = tag.getInt(TAG_DURATION_TICKS);
        }

        if (tag.contains(TAG_DAMAGE_AMOUNT)) {
            this.damageAmount = tag.getFloat(TAG_DAMAGE_AMOUNT);
        }

        if (tag.contains(TAG_DAMAGE_INTERVAL)) {
            this.damageInterval = Math.max(1, tag.getInt(TAG_DAMAGE_INTERVAL));
        }

        if (tag.contains(TAG_KNOCKBACK)) {
            this.knockback = tag.getDouble(TAG_KNOCKBACK);
        }

        if (tag.contains(TAG_ELECTRIFY_TICKS)) {
            this.electrifyTicks = tag.getInt(TAG_ELECTRIFY_TICKS);
        }

        if (tag.contains(TAG_ELECTRIFY_AMPLIFIER)) {
            this.electrifyAmplifier = tag.getInt(TAG_ELECTRIFY_AMPLIFIER);
        }

        if (tag.contains(TAG_MODE)) {
            this.setMode(Mode.byId(tag.getInt(TAG_MODE)));
        }

        if (tag.contains(TAG_VEL_X) && tag.contains(TAG_VEL_Y) && tag.contains(TAG_VEL_Z)) {
            this.projectileVelocity = new Vec3(
                    tag.getDouble(TAG_VEL_X),
                    tag.getDouble(TAG_VEL_Y),
                    tag.getDouble(TAG_VEL_Z)
            );

            this.setDeltaMovement(this.projectileVelocity);
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        if (this.ownerUUID != null) {
            tag.putUUID(TAG_OWNER_UUID, this.ownerUUID);
        }

        tag.putDouble(TAG_HALF_SIZE, this.halfSize);
        tag.putInt(TAG_DURATION_TICKS, this.durationTicks);
        tag.putFloat(TAG_DAMAGE_AMOUNT, this.damageAmount);
        tag.putInt(TAG_DAMAGE_INTERVAL, this.damageInterval);
        tag.putDouble(TAG_KNOCKBACK, this.knockback);
        tag.putInt(TAG_ELECTRIFY_TICKS, this.electrifyTicks);
        tag.putInt(TAG_ELECTRIFY_AMPLIFIER, this.electrifyAmplifier);
        tag.putInt(TAG_MODE, this.getMode().id());

        tag.putDouble(TAG_VEL_X, this.projectileVelocity.x);
        tag.putDouble(TAG_VEL_Y, this.projectileVelocity.y);
        tag.putDouble(TAG_VEL_Z, this.projectileVelocity.z);
        tag.putBoolean(TAG_OFFHAND, this.isOffhand());
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityData.get(DATA_OWNER_ID));
        buf.writeVarInt(this.entityData.get(DATA_MODE));
        buf.writeBoolean(this.isOffhand());
        buf.writeDouble(this.projectileVelocity.x);
        buf.writeDouble(this.projectileVelocity.y);
        buf.writeDouble(this.projectileVelocity.z);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buf) {
        this.entityData.set(DATA_OWNER_ID, buf.readVarInt());
        this.entityData.set(DATA_MODE, buf.readVarInt());
        this.entityData.set(DATA_OFFHAND, buf.readBoolean());

        this.projectileVelocity = new Vec3(
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble()
        );

        this.setDeltaMovement(this.projectileVelocity);
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
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}