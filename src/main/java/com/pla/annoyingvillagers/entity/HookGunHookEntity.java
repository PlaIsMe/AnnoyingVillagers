package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

/*
 * Hook-gun projectile behavior is adapted from the local
 * Grappling-Hook-Mod-Reforged-main motorized hook design by yyonne, GPL-3.0.
 */
public class HookGunHookEntity extends Projectile implements ItemSupplier {
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_ATTACHED =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_DOUBLE_MODE =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_RIGHT_HAND =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_RETURNING =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> DATA_BOUND_STACK =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Float> DATA_ANCHOR_X =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_ANCHOR_Y =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_ANCHOR_Z =
            SynchedEntityData.defineId(HookGunHookEntity.class, EntityDataSerializers.FLOAT);

    private static final String TAG_OWNER = "HookGunOwner";
    private static final String TAG_ATTACHED = "Attached";
    private static final String TAG_DOUBLE_MODE = "DoubleMode";
    private static final String TAG_RIGHT_HAND = "RightHand";
    private static final String TAG_RETURNING = "Returning";
    private static final String TAG_BOUND_STACK = "BoundStack";
    private static final String TAG_ANCHOR_X = "AnchorX";
    private static final String TAG_ANCHOR_Y = "AnchorY";
    private static final String TAG_ANCHOR_Z = "AnchorZ";

    private static final double HOOK_GRAVITY = 0.10D;
    private static final double AIR_DRAG = 0.99D;
    private static final double ENTITY_YANK_SCALE = 0.40D;
    private static final double RETURN_SPEED = 2.4D;
    private static final double RETURN_ARRIVE_DISTANCE = 0.55D;
    private static final int MAX_FLYING_LIFE = 80;

    private Vec3 anchor = Vec3.ZERO;
    @Nullable
    private UUID ownerUuid;

    public HookGunHookEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(AnnoyingVillagersModEntities.HOOK_GUN_HOOK.get(), level);
    }

    public HookGunHookEntity(EntityType<? extends HookGunHookEntity> entityType, Level level) {
        super(entityType, level);
    }

    public HookGunHookEntity(Level level, LivingEntity owner, boolean doubleMode) {
        this(level, owner, doubleMode, true);
    }

    public HookGunHookEntity(Level level, LivingEntity owner, boolean doubleMode, boolean rightHand) {
        this(level, owner, doubleMode, rightHand, ItemStack.EMPTY);
    }

    public HookGunHookEntity(Level level, LivingEntity owner, boolean doubleMode, boolean rightHand, ItemStack boundStack) {
        this(AnnoyingVillagersModEntities.HOOK_GUN_HOOK.get(), level);
        this.setOwner(owner);
        this.ownerUuid = owner.getUUID();
        this.setOwnerId(owner.getId());
        this.setDoubleMode(doubleMode);
        this.setRightHand(rightHand);
        this.setBoundItem(boundStack);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_OWNER_ID, 0);
        this.entityData.define(DATA_ATTACHED, false);
        this.entityData.define(DATA_DOUBLE_MODE, false);
        this.entityData.define(DATA_RIGHT_HAND, true);
        this.entityData.define(DATA_RETURNING, false);
        this.entityData.define(DATA_BOUND_STACK, ItemStack.EMPTY);
        this.entityData.define(DATA_ANCHOR_X, 0.0F);
        this.entityData.define(DATA_ANCHOR_Y, 0.0F);
        this.entityData.define(DATA_ANCHOR_Z, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            LivingEntity owner = this.getHookOwner();
            if (owner == null || !owner.isAlive() || !HookGunItem.isHoldingHookGun(owner)) {
                this.discard();
                return;
            }
            this.setOwnerId(owner.getId());

            if (!this.isReturning() && this.distanceToSqr(owner) > HookGunItem.HOOK_DESPAWN_DISTANCE_SQR) {
                this.discard();
                return;
            }
        }

        LivingEntity owner = this.getHookOwner();
        if (this.isReturning()) {
            if (owner == null) {
                this.discard();
                return;
            }

            this.tickReturning(owner);
            return;
        }

        if (this.isAttached()) {
            this.anchor = this.getSyncedAnchor();
            this.setDeltaMovement(Vec3.ZERO);
            this.setNoGravity(true);
            this.noPhysics = true;
            this.setPos(this.anchor.x, this.anchor.y, this.anchor.z);
            return;
        }

        if (!this.level().isClientSide && this.tickCount > MAX_FLYING_LIFE) {
            this.startReturning();
            return;
        }

        BlockHitResult emptyBucketFluidHit = this.getEmptyBucketFluidHit();
        if (emptyBucketFluidHit != null && !ForgeEventFactory.onProjectileImpact(this, emptyBucketFluidHit)) {
            this.onHit(emptyBucketFluidHit);
        }

        if (this.isRemoved() || this.isAttached() || this.isReturning()) {
            return;
        }

        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
            this.onHit(hitResult);
        }

        if (this.isRemoved() || this.isAttached() || this.isReturning()) {
            return;
        }

        Vec3 motion = this.getDeltaMovement();
        this.move(MoverType.SELF, motion);
        this.updateRotationFromMotion(motion);
        this.setDeltaMovement(motion.scale(AIR_DRAG).add(0.0D, -HOOK_GRAVITY, 0.0D));
    }

    @Nullable
    private BlockHitResult getEmptyBucketFluidHit() {
        if (!this.getBoundItem().is(Items.BUCKET)) {
            return null;
        }

        Vec3 start = this.position();
        Vec3 end = start.add(this.getDeltaMovement());
        HitResult hitResult = this.level().clip(new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.SOURCE_ONLY,
                this
        ));

        if (hitResult instanceof BlockHitResult blockHitResult && this.isSourceFluid(blockHitResult.getBlockPos())) {
            return blockHitResult;
        }

        BlockPos currentPos = BlockPos.containing(start);
        if (this.isSourceFluid(currentPos)) {
            return new BlockHitResult(start, Direction.UP, currentPos, false);
        }

        BlockPos nextPos = BlockPos.containing(end);
        if (this.isSourceFluid(nextPos)) {
            return new BlockHitResult(end, Direction.UP, nextPos, false);
        }

        return null;
    }

    private boolean isSourceFluid(BlockPos pos) {
        return !this.level().getFluidState(pos).isEmpty() && this.level().getFluidState(pos).isSource();
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        if (this.level().isClientSide || this.isAttached()) {
            return;
        }

        if (hitResult instanceof BlockHitResult blockHitResult) {
            this.handleBlockHit(blockHitResult);
        } else if (hitResult instanceof EntityHitResult entityHitResult) {
            this.handleEntityHit(entityHitResult);
        }
    }

    private void handleBlockHit(BlockHitResult hitResult) {
        ItemStack boundItem = this.getBoundItem();
        this.setPos(hitResult.getLocation());
        if (this.isGrappleHook()) {
            this.attachToBlock(hitResult);
            return;
        }

        ItemStack mutableBoundItem = boundItem.copy();
        HookUtil.ItemInteractionResult itemResult =
                HookUtil.handleBlockHitWithResult(this.level(), mutableBoundItem, this, this.getHookOwner(), hitResult);
        if (itemResult.handled()) {
            this.updateSourceBoundItem(itemResult.itemStack());
        }

        this.startReturning();
    }

    private void handleEntityHit(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        ItemStack boundItem = this.getBoundItem();
        this.setPos(hitResult.getLocation());
        if (this.isGrappleHook()) {
            this.yankEntity(hitResult);
            return;
        }

        if (boundItem.isEmpty()) {
            this.startReturning();
            return;
        }

        if (target instanceof LivingEntity livingTarget) {
            ItemStack mutableBoundItem = boundItem.copy();
            HookUtil.ItemInteractionResult itemResult =
                    HookUtil.handleEntityHitWithResult(this.level(), mutableBoundItem, this, this.getHookOwner(), livingTarget);
            if (itemResult.handled()) {
                this.updateSourceBoundItem(itemResult.itemStack());
            }
        }

        this.startReturning();
    }

    private void attachToBlock(BlockHitResult hitResult) {
        BlockState blockState = this.level().getBlockState(hitResult.getBlockPos());
        if (blockState.isAir() || blockState.getCollisionShape(this.level(), hitResult.getBlockPos()).isEmpty()) {
            return;
        }

        this.setAnchor(hitResult.getLocation());
        this.entityData.set(DATA_ATTACHED, true);
        this.setDeltaMovement(Vec3.ZERO);
        this.setNoGravity(true);
        this.noPhysics = true;
        this.setPos(this.anchor.x, this.anchor.y, this.anchor.z);
    }

    private void yankEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        LivingEntity owner = this.getHookOwner();
        if (owner == null || target == owner) {
            return;
        }

        Vec3 pull = owner.position().add(0.0D, owner.getEyeHeight(), 0.0D)
                .subtract(target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D))
                .scale(ENTITY_YANK_SCALE);
        pull = new Vec3(pull.x, Math.min(pull.y, 1.2D), pull.z);
        target.setDeltaMovement(target.getDeltaMovement().add(pull));
        target.hasImpulse = true;
        target.hurtMarked = true;
        target.fallDistance = 0.0F;
        this.startReturning();
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity target) {
        if (this.isReturning()) {
            return false;
        }

        LivingEntity owner = this.getHookOwner();
        if (target == owner || !target.isAlive() || target.isSpectator()) {
            return false;
        }
        return super.canHitEntity(target);
    }

    public boolean isAttached() {
        return this.entityData.get(DATA_ATTACHED);
    }

    public boolean isDoubleMode() {
        return this.entityData.get(DATA_DOUBLE_MODE);
    }

    public void setDoubleMode(boolean doubleMode) {
        this.entityData.set(DATA_DOUBLE_MODE, doubleMode);
    }

    public boolean isRightHand() {
        return this.entityData.get(DATA_RIGHT_HAND);
    }

    public boolean isReturning() {
        return this.entityData.get(DATA_RETURNING);
    }

    public void setRightHand(boolean rightHand) {
        this.entityData.set(DATA_RIGHT_HAND, rightHand);
    }

    public ItemStack getBoundItem() {
        return this.entityData.get(DATA_BOUND_STACK);
    }

    public void setBoundItem(ItemStack boundStack) {
        if (boundStack.isEmpty()) {
            this.entityData.set(DATA_BOUND_STACK, ItemStack.EMPTY);
            return;
        }

        ItemStack stored = boundStack.copy();
        stored.setCount(1);
        this.entityData.set(DATA_BOUND_STACK, stored);
    }

    public boolean isGrappleHook() {
        return HookUtil.isPickaxe(this.getBoundItem());
    }

    private void startReturning() {
        this.entityData.set(DATA_ATTACHED, false);
        this.entityData.set(DATA_RETURNING, true);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.setDeltaMovement(Vec3.ZERO);
    }

    private void tickReturning(LivingEntity owner) {
        Vec3 target = HookGunItem.getHookStartPosition(owner, this.isRightHand());
        Vec3 current = this.position();
        Vec3 toTarget = target.subtract(current);
        double distance = toTarget.length();

        this.noPhysics = true;
        this.setNoGravity(true);
        this.fallDistance = 0.0F;

        if (distance <= RETURN_ARRIVE_DISTANCE) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setPos(target.x, target.y, target.z);
            if (!this.level().isClientSide) {
                this.discard();
            }
            return;
        }

        Vec3 step = toTarget.scale(Math.min(RETURN_SPEED, distance) / distance);
        this.setDeltaMovement(step);
        this.setPos(current.x + step.x, current.y + step.y, current.z + step.z);
        this.updateRotationFromMotion(step);
        this.hasImpulse = true;
    }

    public Vec3 getAnchor() {
        return this.isAttached() ? this.getSyncedAnchor() : this.position();
    }

    private void setAnchor(Vec3 anchor) {
        this.anchor = anchor;
        this.entityData.set(DATA_ANCHOR_X, (float) anchor.x);
        this.entityData.set(DATA_ANCHOR_Y, (float) anchor.y);
        this.entityData.set(DATA_ANCHOR_Z, (float) anchor.z);
    }

    private Vec3 getSyncedAnchor() {
        return new Vec3(
                this.entityData.get(DATA_ANCHOR_X),
                this.entityData.get(DATA_ANCHOR_Y),
                this.entityData.get(DATA_ANCHOR_Z)
        );
    }

    public boolean isOwnedBy(LivingEntity owner) {
        return owner != null && (owner.equals(this.getOwner())
                || owner.getId() == this.entityData.get(DATA_OWNER_ID)
                || owner.getUUID().equals(this.ownerUuid));
    }

    private void setOwnerId(int ownerId) {
        this.entityData.set(DATA_OWNER_ID, ownerId);
    }

    @Nullable
    public LivingEntity getHookOwner() {
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity livingOwner) {
            return livingOwner;
        }

        int ownerId = this.entityData.get(DATA_OWNER_ID);
        if (ownerId > 0) {
            Entity entity = this.level().getEntity(ownerId);
            if (entity instanceof LivingEntity livingOwner) {
                this.setOwner(livingOwner);
                return livingOwner;
            }
        }

        if (this.ownerUuid != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.ownerUuid);
            if (entity instanceof LivingEntity livingOwner) {
                this.setOwner(livingOwner);
                this.setOwnerId(livingOwner.getId());
                return livingOwner;
            }
        }

        return null;
    }

    private void updateRotationFromMotion(Vec3 motion) {
        if (motion.lengthSqr() <= 1.0E-7D) {
            return;
        }

        double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        this.setYRot((float) (Mth.atan2(motion.x, motion.z) * Mth.RAD_TO_DEG));
        this.setXRot((float) (Mth.atan2(motion.y, horizontal) * Mth.RAD_TO_DEG));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.ownerUuid != null) {
            tag.putUUID(TAG_OWNER, this.ownerUuid);
        }
        tag.putBoolean(TAG_ATTACHED, this.isAttached());
        tag.putBoolean(TAG_DOUBLE_MODE, this.isDoubleMode());
        tag.putBoolean(TAG_RIGHT_HAND, this.isRightHand());
        tag.putBoolean(TAG_RETURNING, this.isReturning());
        ItemStack boundStack = this.getBoundItem();
        if (!boundStack.isEmpty()) {
            tag.put(TAG_BOUND_STACK, boundStack.save(new CompoundTag()));
        }
        tag.putDouble(TAG_ANCHOR_X, this.anchor.x);
        tag.putDouble(TAG_ANCHOR_Y, this.anchor.y);
        tag.putDouble(TAG_ANCHOR_Z, this.anchor.z);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID(TAG_OWNER)) {
            this.ownerUuid = tag.getUUID(TAG_OWNER);
        }
        this.entityData.set(DATA_ATTACHED, tag.getBoolean(TAG_ATTACHED));
        this.entityData.set(DATA_DOUBLE_MODE, tag.getBoolean(TAG_DOUBLE_MODE));
        this.entityData.set(DATA_RIGHT_HAND, !tag.contains(TAG_RIGHT_HAND) || tag.getBoolean(TAG_RIGHT_HAND));
        this.entityData.set(DATA_RETURNING, tag.getBoolean(TAG_RETURNING));
        if (tag.contains(TAG_BOUND_STACK, 10)) {
            this.setBoundItem(ItemStack.of(tag.getCompound(TAG_BOUND_STACK)));
        } else {
            this.setBoundItem(ItemStack.EMPTY);
        }
        this.setAnchor(new Vec3(tag.getDouble(TAG_ANCHOR_X), tag.getDouble(TAG_ANCHOR_Y), tag.getDouble(TAG_ANCHOR_Z)));
        this.noPhysics = this.isAttached() || this.isReturning();
        this.setNoGravity(this.isAttached() || this.isReturning());
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        LivingEntity owner = this.getHookOwner();
        if (owner == null) {
            return super.getBoundingBoxForCulling();
        }

        return new AABB(this.position(), owner.getEyePosition()).inflate(1.0D);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.getBoundItem();
    }

    private void updateSourceBoundItem(ItemStack updatedBoundItem) {
        this.setBoundItem(updatedBoundItem);

        ItemStack hookGunStack = this.getOwnerHookGunStack();
        if (!hookGunStack.isEmpty()) {
            HookGunItem.setBoundItem(hookGunStack, updatedBoundItem);
        }
    }

    private ItemStack getOwnerHookGunStack() {
        LivingEntity owner = this.getHookOwner();
        if (owner == null) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = this.isRightHand() ? owner.getMainHandItem() : owner.getOffhandItem();
        return stack.getItem() instanceof HookGunItem ? stack : ItemStack.EMPTY;
    }
}
