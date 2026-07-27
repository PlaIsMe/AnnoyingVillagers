package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlackFireSwordItem;
import com.pla.annoyingvillagers.network.ClientboundBlackFireFx;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import javax.annotation.Nullable;
import java.util.UUID;

public class BlackFireEntity extends Entity implements IEntityAdditionalSpawnData {
    private static final String TAG_OWNER_UUID = "OwnerUUID";
    private static final String TAG_HALF_SIZE = "HalfSize";
    private static final String TAG_DURATION_TICKS = "DurationTicks";
    private static final String TAG_DAMAGE_AMOUNT = "DamageAmount";
    private static final String TAG_DAMAGE_INTERVAL = "DamageInterval";
    private static final String TAG_KNOCKBACK = "Knockback";
    private static final String TAG_FIRE_SECONDS = "FireSeconds";
    private static final String TAG_MODE = "Mode";
    private static final String TAG_VEL_X = "VelX";
    private static final String TAG_VEL_Y = "VelY";
    private static final String TAG_VEL_Z = "VelZ";

    private static final EntityDataAccessor<Integer> DATA_OWNER_ID =
            SynchedEntityData.defineId(BlackFireEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DATA_MODE =
            SynchedEntityData.defineId(BlackFireEntity.class, EntityDataSerializers.INT);

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
    private int durationTicks = 60;
    private float damageAmount = 4.0F;
    private int damageInterval = 10;
    private double knockback = 0.65D;
    private int fireSeconds = 4;

    private Vec3 projectileVelocity = Vec3.ZERO;

    public BlackFireEntity(EntityType<? extends BlackFireEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public BlackFireEntity(Level level, LivingEntity owner, Vec3 pos) {
        this(level, owner, pos, 1.0D, 40, 4.0F, 10, 0.65D, 4);
    }

    public BlackFireEntity(Level level,
                           LivingEntity owner,
                           Vec3 pos,
                           double halfSize,
                           int durationTicks,
                           float damageAmount,
                           int damageInterval,
                           double knockback,
                           int fireSeconds) {
        this(AnnoyingVillagersModEntities.BLACK_FIRE.get(), level);

        this.setOwner(owner);
        this.halfSize = halfSize;
        this.durationTicks = durationTicks;
        this.damageAmount = damageAmount;
        this.damageInterval = Math.max(1, damageInterval);
        this.knockback = knockback;
        this.fireSeconds = fireSeconds;

        this.setPos(pos.x, pos.y, pos.z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_OWNER_ID, -1);
        this.entityData.define(DATA_MODE, Mode.PROJECTILE.id());
    }

    private static boolean isHoldingBlackFireSword(LivingEntity owner) {
        return owner.getMainHandItem().getItem() instanceof BlackFireSwordItem;
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

        if (!(this.level() instanceof ServerLevel)) {
            return;
        }

        if (this.isFollowOwnerSwordMode()) {
            this.updateFollowOwnerSwordPosition();
        } else if (this.isProjectileMode()) {
            this.moveProjectile();
        }

        if (this.tickCount == 1) {
            AnnoyingVillagers.PACKET_HANDLER.send(
                    PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this),
                    new ClientboundBlackFireFx(this)
            );
            this.playSound(AnnoyingVillagersModSounds.BLACK_FIRE.get(), 1.0F, 1.0F);
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

        if (!isHoldingBlackFireSword(owner)) {
            this.discard();
            return;
        }

        Vec3 swordPos = getOwnerSwordPosition(owner);
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

    private static Vec3 getOwnerSwordPosition(LivingEntity owner) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    (float) 1.0,
                    0.0F
            );

            if (pos != null) {
                return pos;
            }
        } catch (Exception ignored) {
        }

        Vec3 look = owner.getLookAngle();
        return owner.position()
                .add(0.0D, owner.getBbHeight() * 0.65D, 0.0D)
                .add(look.scale(0.75D));
    }

    private void damageEntitiesInZone() {
        LivingEntity owner = this.getOwnerLiving();

        for (LivingEntity target : this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.makeDamageBox(),
                living -> this.isValidTarget(owner, living)
        )) {
            DamageSource source = this.makeDamageSource(owner);

            if (target.hurt(source, this.damageAmount)) {
                if (this.fireSeconds > 0) {
                    target.setSecondsOnFire(this.fireSeconds);
                }

                this.knockbackTarget(target);
            }
        }
    }

    private DamageSource makeDamageSource(@Nullable LivingEntity owner) {
        if (owner != null) {
            return this.damageSources().indirectMagic(this, owner);
        }

        return this.damageSources().magic();
    }

    private void knockbackTarget(LivingEntity target) {
        Vec3 dir = target.position().subtract(this.position());
        dir = new Vec3(dir.x, 0.0D, dir.z);

        if (dir.lengthSqr() < 1.0E-6D) {
            dir = new Vec3(
                    this.random.nextDouble() - 0.5D,
                    0.0D,
                    this.random.nextDouble() - 0.5D
            );
        }

        dir = dir.normalize();

        target.push(
                dir.x * this.knockback,
                0.25D,
                dir.z * this.knockback
        );

        target.hurtMarked = true;
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

    public static BlackFireEntity spawnOnOwnerSword(Level level, LivingEntity owner) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return null;
        }

        Vec3 pos = getOwnerSwordPosition(owner);

        BlackFireEntity fire = new BlackFireEntity(
                level,
                owner,
                pos,
                1.0D,
                40,
                2.0F,
                10,
                0.65D,
                4
        );

        fire.setMode(Mode.FOLLOW_OWNER_SWORD);
        serverLevel.addFreshEntity(fire);
        return fire;
    }

    public static BlackFireEntity shootFromOwnerLook(Level level, LivingEntity owner) {
        return shootFromOwnerLook(
                level,
                owner,
                0.55D,
                40,
                1.0D,
                6.0F,
                5,
                0.65D,
                4
        );
    }

    public static BlackFireEntity shootFromOwnerLook(Level level,
                                                     LivingEntity owner,
                                                     double speed,
                                                     int durationTicks,
                                                     double halfSize,
                                                     float damageAmount,
                                                     int damageInterval,
                                                     double knockback,
                                                     int fireSeconds) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return null;
        }

        Vec3 look = owner.getViewVector(1.0F);

        if (look.lengthSqr() < 1.0E-7D) {
            look = owner.getLookAngle();
        }

        look = look.normalize();

        Vec3 startPos = getOwnerSwordPosition(owner);
        Vec3 velocity = look.scale(speed);

        BlackFireEntity fire = new BlackFireEntity(
                level,
                owner,
                startPos,
                halfSize,
                durationTicks,
                damageAmount,
                Math.max(1, damageInterval),
                knockback,
                fireSeconds
        );

        fire.setMode(Mode.PROJECTILE);
        fire.setProjectileVelocity(velocity);
        fire.setYRot(owner.getYRot());
        fire.setXRot(owner.getXRot());

        serverLevel.addFreshEntity(fire);
        return fire;
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
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

        if (tag.contains(TAG_FIRE_SECONDS)) {
            this.fireSeconds = tag.getInt(TAG_FIRE_SECONDS);
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
        tag.putInt(TAG_FIRE_SECONDS, this.fireSeconds);
        tag.putInt(TAG_MODE, this.getMode().id());

        tag.putDouble(TAG_VEL_X, this.projectileVelocity.x);
        tag.putDouble(TAG_VEL_Y, this.projectileVelocity.y);
        tag.putDouble(TAG_VEL_Z, this.projectileVelocity.z);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityData.get(DATA_OWNER_ID));
        buf.writeVarInt(this.entityData.get(DATA_MODE));
        buf.writeDouble(this.projectileVelocity.x);
        buf.writeDouble(this.projectileVelocity.y);
        buf.writeDouble(this.projectileVelocity.z);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buf) {
        this.entityData.set(DATA_OWNER_ID, buf.readVarInt());
        this.entityData.set(DATA_MODE, buf.readVarInt());

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