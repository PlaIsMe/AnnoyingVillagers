package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.item.DestructionEyeItem;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationController;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GolemArms extends Mob {
    private static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(GolemArms.class, EntityDataSerializers.INT);
    private static final int GUARD_RELEASE_CONFIRM_TICKS = 2;
    private UUID ownerUuid;
    private int normalAttackIndex;
    private GuardPhase guardPhase = GuardPhase.IDLE;
    private int guardReleaseTicks;

    public GolemArms(EntityType<? extends GolemArms> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_ID, -1);
    }

    public void setOwner(Player owner) {
        this.ownerUuid = owner.getUUID();
        this.entityData.set(OWNER_ID, owner.getId());
    }

    public @Nullable LivingEntity getOwnerLiving() {
        int ownerId = this.entityData.get(OWNER_ID);
        Entity byId = ownerId < 0 ? null : this.level().getEntity(ownerId);
        if (byId instanceof LivingEntity living) return living;
        if (!this.level().isClientSide && this.ownerUuid != null && this.level() instanceof ServerLevel serverLevel) {
            Entity byUuid = serverLevel.getEntity(this.ownerUuid);
            if (byUuid instanceof LivingEntity living) {
                this.entityData.set(OWNER_ID, living.getId());
                return living;
            }
        }
        return null;
    }

    public boolean attackFromOwner() {
        LivingEntity owner = getOwnerLiving();
        if (!(owner instanceof Player player) || DestructionEyeItem.isGuarding(player) || this.guardPhase != GuardPhase.IDLE || SpecialAnimationController.hasActiveAnimation(this)) return false;
        SpecialAnimationId animation;
        if (!player.onGround()) animation = SpecialAnimationId.ARMS_AIR_ATK;
        // ServerPlayer#getDeltaMovement() is not a reliable way to tell whether a real player
        // is moving, because normal client movement arrives primarily as position packets.
        // The old velocity check could therefore reject RUN_ATK even while the player was
        // actively sprinting. Sprint state itself is synchronized to the server, so use it
        // directly for the sprint attack selection.
        else if (player.isSprinting()) animation = SpecialAnimationId.ARMS_RUN_ATK;
        else {
            animation = switch (this.normalAttackIndex++ % 3) {
                case 0 -> SpecialAnimationId.ARMS_ATK_1;
                case 1 -> SpecialAnimationId.ARMS_ATK_2;
                default -> SpecialAnimationId.ARMS_ATK_3;
            };
        }
        return SpecialAnimationController.play(this, animation, null);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity owner = getOwnerLiving();
        if (owner == null || !owner.isAlive()) {
            if (!this.level().isClientSide) this.discard();
            return;
        }
        if (!this.level().isClientSide) {
            if (!(owner.getMainHandItem().getItem() instanceof DestructionEyeItem) || !DestructionEyeItem.isBoundTo(owner.getMainHandItem(), this)) {
                SpecialAnimationController.clear(this);
                this.discard();
                return;
            }
            if (owner instanceof Player player) tickGuardState(player);
        }
        syncToOwner(owner);
    }

    private void tickGuardState(Player owner) {
        boolean requested = DestructionEyeItem.isGuarding(owner);
        SpecialAnimationId active = SpecialAnimationController.getActiveAnimationId(this);

        if (requested) {
            this.guardReleaseTicks = 0;
            if (this.guardPhase == GuardPhase.IDLE || this.guardPhase == GuardPhase.FINISH) {
                SpecialAnimationController.clear(this);
                this.guardPhase = GuardPhase.TRANSFORM;
                SpecialAnimationController.play(this, SpecialAnimationId.ARMS_GUARD_TRANSFORM, null);
                return;
            }
            if (this.guardPhase == GuardPhase.TRANSFORM && active == null) {
                this.guardPhase = GuardPhase.GUARD;
                SpecialAnimationController.play(this, SpecialAnimationId.ARMS_GUARD, null);
                return;
            }
            if (this.guardPhase == GuardPhase.GUARD && active == null) SpecialAnimationController.play(this, SpecialAnimationId.ARMS_GUARD, null);
            return;
        }

        if (this.guardPhase == GuardPhase.TRANSFORM || this.guardPhase == GuardPhase.GUARD) {
            // Require a real release for two consecutive server ticks. A one-tick
            // use-state gap must not turn into a full 0.5 s GUARD_FINISH animation.
            if (++this.guardReleaseTicks < GUARD_RELEASE_CONFIRM_TICKS) return;
            this.guardReleaseTicks = 0;
            SpecialAnimationController.clear(this);
            this.guardPhase = GuardPhase.FINISH;
            SpecialAnimationController.play(this, SpecialAnimationId.ARMS_GUARD_FINISH, null);
            return;
        }

        this.guardReleaseTicks = 0;
        if (this.guardPhase == GuardPhase.FINISH && active == null) this.guardPhase = GuardPhase.IDLE;
    }

    private void syncToOwner(LivingEntity owner) {
        this.xo = owner.xo;
        this.yo = owner.yo;
        this.zo = owner.zo;
        this.xOld = owner.xOld;
        this.yOld = owner.yOld;
        this.zOld = owner.zOld;
        this.xRotO = owner.xRotO;
        this.yRotO = owner.yBodyRotO;
        this.yBodyRotO = owner.yBodyRotO;
        this.yHeadRotO = owner.yHeadRotO;
        this.setPos(owner.getX(), owner.getY(), owner.getZ());
        this.setXRot(owner.getXRot());
        this.setYRot(owner.yBodyRot);
        this.setYBodyRot(owner.yBodyRot);
        this.setYHeadRot(owner.getYHeadRot());
        this.setDeltaMovement(owner.getDeltaMovement());
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
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.ownerUuid != null) tag.putUUID("Owner", this.ownerUuid);
        tag.putInt("NormalAttackIndex", this.normalAttackIndex);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("Owner")) this.ownerUuid = tag.getUUID("Owner");
        this.normalAttackIndex = tag.getInt("NormalAttackIndex");
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D).add(Attributes.ATTACK_DAMAGE, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    private enum GuardPhase {
        IDLE,
        TRANSFORM,
        GUARD,
        FINISH
    }
}
