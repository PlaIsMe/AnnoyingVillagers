package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.network.ClientboundTeleportPortalFx;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PortalEntity extends Entity {
    public static final float WIDTH = 2.2F;
    public static final float HEIGHT = 3.0F;
    public static final int LIFETIME_TICKS = 20 * 10;

    private static final String PORTAL_COOLDOWN_TAG = "AnnoyingVillagersPortalCooldown";
    private static final int TELEPORT_COOLDOWN_TICKS = 8;

    private static final EntityDataAccessor<Optional<UUID>> LINKED_PORTAL_UUID =
            SynchedEntityData.defineId(PortalEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID =
            SynchedEntityData.defineId(PortalEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Optional<UUID>> PORTAL_GROUP_UUID =
            SynchedEntityData.defineId(PortalEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> PORTAL_ORDER =
            SynchedEntityData.defineId(PortalEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> STARTER_PORTAL =
            SynchedEntityData.defineId(PortalEntity.class, EntityDataSerializers.BOOLEAN);

    public PortalEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public PortalEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.PORTAL.get(), level);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(LINKED_PORTAL_UUID, Optional.empty());
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(PORTAL_GROUP_UUID, Optional.empty());
        this.entityData.define(PORTAL_ORDER, -1);
        this.entityData.define(STARTER_PORTAL, false);
    }

    @Override
    public void tick() {
        super.tick();
        this.noPhysics = true;
        this.setNoGravity(true);
        this.setDeltaMovement(Vec3.ZERO);

        if (!this.level().isClientSide) {
            if (this.tickCount >= LIFETIME_TICKS) {
                this.discard();
                return;
            }
            this.teleportIntersectingEntities();
        }
    }

    private void teleportIntersectingEntities() {
        PortalEntity linkedPortal = this.getLinkedPortal();
        if (linkedPortal == null || linkedPortal.isRemoved()) {
            return;
        }

        AABB portalBox = this.getTeleportBox();
        for (Entity entity : this.level().getEntities(this, portalBox.inflate(0.35D), this::canTeleportEntity)) {
            if (this.intersectsPortalPath(entity, portalBox)) {
                this.teleportEntity(entity, linkedPortal);
            }
        }
    }

    private boolean canTeleportEntity(Entity entity) {
        if (entity instanceof PortalEntity || entity instanceof SnakeBladeEntity) {
            return false;
        }
        if (entity.isRemoved() || !entity.isAlive() || entity.isPassenger()) {
            return false;
        }
        if (entity instanceof Player player && player.isSpectator()) {
            return false;
        }
        return entity.getPersistentData().getLong(PORTAL_COOLDOWN_TAG) <= this.level().getGameTime();
    }

    private boolean intersectsPortalPath(Entity entity, AABB portalBox) {
        AABB currentBox = entity.getBoundingBox();
        if (currentBox.intersects(portalBox)) {
            return true;
        }

        AABB previousBox = currentBox.move(
                entity.xo - entity.getX(),
                entity.yo - entity.getY(),
                entity.zo - entity.getZ()
        );
        if (previousBox.minmax(currentBox).intersects(portalBox)) {
            return true;
        }

        Vec3 from = new Vec3(entity.xo, entity.yo + entity.getBbHeight() * 0.5D, entity.zo);
        Vec3 to = entity.position().add(0.0D, entity.getBbHeight() * 0.5D, 0.0D);
        return portalBox.clip(from, to).isPresent();
    }

    private void teleportEntity(Entity entity, PortalEntity linkedPortal) {
        Vec3 motion = entity.getDeltaMovement();
        Vec3 sourceNormal = this.getNormal();
        Vec3 entityCenter = entity.position().add(0.0D, entity.getBbHeight() * 0.5D, 0.0D);
        double exitSide = entityCenter.subtract(this.getPortalCenter()).dot(sourceNormal) >= 0.0D ? 1.0D : -1.0D;
        double relativeY = Mth.clamp(entity.getY() - this.getY(), 0.05D, Math.max(0.05D, this.getBbHeight() - entity.getBbHeight()));

        Vec3 exitPos = linkedPortal.findExitPosition(entity, exitSide, relativeY);
        Vec3 exitMotion = this.transformMotion(motion, linkedPortal, exitSide);

        entity.getPersistentData().putLong(PORTAL_COOLDOWN_TAG, this.level().getGameTime() + TELEPORT_COOLDOWN_TICKS);
        entity.teleportTo(exitPos.x, exitPos.y, exitPos.z);
        entity.move(MoverType.SELF, Vec3.ZERO);
        entity.setDeltaMovement(exitMotion);
        entity.fallDistance = 0.0F;

        float yawDelta = linkedPortal.getYRot() - this.getYRot();
        entity.setYRot(entity.getYRot() + yawDelta);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setYHeadRot(livingEntity.getYHeadRot() + yawDelta);
            livingEntity.yBodyRot += yawDelta;
        }

        this.level().playSound(null, this.getX(), this.getY() + 1.5D, this.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.45F, 1.25F);
        linkedPortal.level().playSound(null, linkedPortal.getX(), linkedPortal.getY() + 1.5D, linkedPortal.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.45F, 1.25F);
        this.sendTeleportPortalFx();
        linkedPortal.sendTeleportPortalFx();
    }

    private void sendTeleportPortalFx() {
        if (this.level().isClientSide) {
            return;
        }

        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY.with(() -> this),
                new ClientboundTeleportPortalFx(this.getPortalCenter(), this.getNormal())
        );
    }

    private Vec3 findExitPosition(Entity entity, double exitSide, double relativeY) {
        Vec3 normal = this.getNormal().scale(exitSide);
        double offset = Math.max(1.15D, entity.getBbWidth() * 0.5D + 0.75D);
        double y = this.getY() + Mth.clamp(relativeY, 0.05D, Math.max(0.05D, this.getBbHeight() - entity.getBbHeight()));

        for (int step = 0; step <= 6; step++) {
            Vec3 candidate = new Vec3(this.getX(), y, this.getZ()).add(normal.scale(offset + step * 0.35D));
            if (this.canFit(entity, candidate)) {
                return candidate;
            }
        }

        for (int vertical = 1; vertical <= 3; vertical++) {
            Vec3 candidate = new Vec3(this.getX(), y + vertical * 0.35D, this.getZ()).add(normal.scale(offset + 1.0D));
            if (this.canFit(entity, candidate)) {
                return candidate;
            }
        }

        return new Vec3(this.getX(), y, this.getZ()).add(normal.scale(offset + 1.0D));
    }

    private boolean canFit(Entity entity, Vec3 pos) {
        AABB movedBox = entity.getBoundingBox().move(pos.subtract(entity.position()));
        return this.level().noCollision(entity, movedBox.deflate(1.0E-4D));
    }

    private Vec3 transformMotion(Vec3 motion, PortalEntity linkedPortal, double exitSide) {
        Vec3 sourceNormal = this.getNormal();
        Vec3 sourceRight = rightOf(sourceNormal);
        Vec3 exitNormal = linkedPortal.getNormal().scale(exitSide);
        Vec3 exitRight = rightOf(linkedPortal.getNormal());

        double speed = motion.length();
        double forwardSpeed = Math.max(Math.abs(motion.dot(sourceNormal)), speed * 0.35D);
        double rightSpeed = motion.dot(sourceRight);

        Vec3 transformed = exitNormal.scale(forwardSpeed)
                .add(exitRight.scale(rightSpeed))
                .add(0.0D, motion.y, 0.0D);

        if (transformed.lengthSqr() < 0.035D) {
            return exitNormal.scale(0.25D);
        }
        return transformed;
    }

    public AABB getTeleportBox() {
        return this.getBoundingBox().inflate(0.1D);
    }

    public Vec3 getPortalCenter() {
        return new Vec3(this.getX(), this.getY() + this.getBbHeight() * 0.5D, this.getZ());
    }

    public Vec3 getNormal() {
        float yaw = this.getYRot() * Mth.DEG_TO_RAD;
        return new Vec3(-Mth.sin(yaw), 0.0D, Mth.cos(yaw)).normalize();
    }

    private static Vec3 rightOf(Vec3 normal) {
        Vec3 right = new Vec3(normal.z, 0.0D, -normal.x);
        return right.lengthSqr() < 1.0E-7D ? new Vec3(1.0D, 0.0D, 0.0D) : right.normalize();
    }

    public UUID getLinkedPortalUUID() {
        return this.entityData.get(LINKED_PORTAL_UUID).orElse(null);
    }

    public void setLinkedPortalUUID(UUID uuid) {
        this.entityData.set(LINKED_PORTAL_UUID, Optional.ofNullable(uuid));
    }

    public PortalEntity getLinkedPortal() {
        UUID uuid = this.getLinkedPortalUUID();
        if (uuid == null || !(this.level() instanceof ServerLevel serverLevel)) {
            return null;
        }

        Entity entity = serverLevel.getEntity(uuid);
        return entity instanceof PortalEntity portalEntity ? portalEntity : null;
    }

    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public UUID getPortalGroupUUID() {
        return this.entityData.get(PORTAL_GROUP_UUID).orElse(null);
    }

    public void setPortalGroupUUID(UUID uuid) {
        this.entityData.set(PORTAL_GROUP_UUID, Optional.ofNullable(uuid));
    }

    public int getPortalOrder() {
        return this.entityData.get(PORTAL_ORDER);
    }

    public void setPortalOrder(int order) {
        this.entityData.set(PORTAL_ORDER, order);
    }

    public boolean isStarterPortal() {
        return this.entityData.get(STARTER_PORTAL);
    }

    public void setStarterPortal(boolean starterPortal) {
        this.entityData.set(STARTER_PORTAL, starterPortal);
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
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        this.setLinkedPortalUUID(tag.hasUUID("LinkedPortal") ? tag.getUUID("LinkedPortal") : null);
        this.setOwnerUUID(tag.hasUUID("Owner") ? tag.getUUID("Owner") : null);
        this.setPortalGroupUUID(tag.hasUUID("PortalGroup") ? tag.getUUID("PortalGroup") : null);
        this.setPortalOrder(tag.contains("PortalOrder") ? tag.getInt("PortalOrder") : -1);
        this.setStarterPortal(tag.getBoolean("StarterPortal"));
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        UUID linkedPortal = this.getLinkedPortalUUID();
        if (linkedPortal != null) {
            tag.putUUID("LinkedPortal", linkedPortal);
        }

        UUID owner = this.getOwnerUUID();
        if (owner != null) {
            tag.putUUID("Owner", owner);
        }

        UUID portalGroup = this.getPortalGroupUUID();
        if (portalGroup != null) {
            tag.putUUID("PortalGroup", portalGroup);
        }

        tag.putInt("PortalOrder", this.getPortalOrder());
        tag.putBoolean("StarterPortal", this.isStarterPortal());
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
