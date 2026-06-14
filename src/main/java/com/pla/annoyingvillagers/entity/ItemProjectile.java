package com.pla.annoyingvillagers.entity;

import com.google.common.collect.Multimap;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.HookUtil;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.HashMap;
import java.util.Map;

public class ItemProjectile extends Projectile implements ItemSupplier {
    private static final int MIN_ARC_TRAVEL_TICKS = 14;
    private static final int MAX_ARC_TRAVEL_TICKS = 34;

    private boolean arcInitialized = false;
    private Vec3 arcStart = Vec3.ZERO;
    private Vec3 arcSide = Vec3.ZERO;
    private int arcTravelTicks = MIN_ARC_TRAVEL_TICKS;
    private double arcHeight = 1.0D;

    private static final EntityDataAccessor<ItemStack> DATA_STACK =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.ITEM_STACK
            );
    private static final EntityDataAccessor<Boolean> DATA_DISARM_LAUNCH_MODE =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.BOOLEAN
            );

    private static final EntityDataAccessor<Integer> DATA_DISARM_DROP_AFTER_TICKS =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.INT
            );

    private static final EntityDataAccessor<Float> DATA_DISARM_MOTION_X =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.FLOAT
            );

    private static final EntityDataAccessor<Float> DATA_DISARM_MOTION_Y =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.FLOAT
            );

    private static final EntityDataAccessor<Float> DATA_DISARM_MOTION_Z =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.FLOAT
            );
    private static final EntityDataAccessor<Boolean> DATA_HOOK_ATTACHED =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.BOOLEAN
            );
    private static final EntityDataAccessor<Boolean> DATA_DISCARD_WHEN_HOOK_LOST =
            SynchedEntityData.defineId(
                    ItemProjectile.class,
                    EntityDataSerializers.BOOLEAN
            );

    private static final double ARRIVE_DISTANCE = 0.65D;
    private static final int MAX_LIFE = 80;
    private static final int HIT_COOLDOWN_TICKS = 8;

    private final Map<Integer, Integer> recentHits = new HashMap<>();

    public ItemProjectile(EntityType<? extends ItemProjectile> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public ItemProjectile(Level level, LivingEntity owner, ItemStack stack, Vec3 spawnPos) {
        this(AnnoyingVillagersModEntities.ITEM_PROJECTILE.get(), level);
        this.setOwner(owner);
        this.setWeaponStack(stack);
        this.setPos(spawnPos.x, spawnPos.y + 0.25D, spawnPos.z);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_STACK, ItemStack.EMPTY);
        this.entityData.define(DATA_DISARM_LAUNCH_MODE, false);
        this.entityData.define(DATA_DISARM_DROP_AFTER_TICKS, 18);
        this.entityData.define(DATA_DISARM_MOTION_X, 0.0F);
        this.entityData.define(DATA_DISARM_MOTION_Y, 0.0F);
        this.entityData.define(DATA_DISARM_MOTION_Z, 0.0F);
        this.entityData.define(DATA_HOOK_ATTACHED, false);
        this.entityData.define(DATA_DISCARD_WHEN_HOOK_LOST, false);
    }

    private boolean isDisarmLaunchMode() {
        return this.entityData.get(DATA_DISARM_LAUNCH_MODE);
    }

    private int getDisarmDropAfterTicks() {
        return this.entityData.get(DATA_DISARM_DROP_AFTER_TICKS);
    }

    private Vec3 getSyncedDisarmLaunchMotion() {
        return new Vec3(
                this.entityData.get(DATA_DISARM_MOTION_X),
                this.entityData.get(DATA_DISARM_MOTION_Y),
                this.entityData.get(DATA_DISARM_MOTION_Z)
        );
    }

    private void tickDisarmLaunchMode() {
        this.noPhysics = false;
        this.setNoGravity(true);

        Vec3 oldPos = this.position();
        Vec3 motion = this.getDeltaMovement();

        if (motion.lengthSqr() < 1.0E-7D) {
            motion = this.getSyncedDisarmLaunchMotion();
        }

        this.move(MoverType.SELF, motion);

        Vec3 moved = this.position().subtract(oldPos);
        this.updateRotationFromMotion(moved.lengthSqr() > 1.0E-7D ? moved : motion);

        Vec3 nextMotion = motion
                .multiply(0.94D, 0.96D, 0.94D)
                .add(0.0D, -0.045D, 0.0D);

        this.setDeltaMovement(nextMotion);

        if (!this.level().isClientSide
                && (this.tickCount >= this.getDisarmDropAfterTicks()
                || this.onGround()
                || this.horizontalCollision
                || this.verticalCollision)) {
            this.dropBackToItem(nextMotion);
        }
    }

    public static ItemProjectile createDisarmLaunch(
            Level level,
            LivingEntity owner,
            ItemStack stack,
            Vec3 spawnPos,
            Vec3 launchMotion,
            int dropAfterTicks
    ) {
        ItemProjectile projectile = new ItemProjectile(level, owner, stack, spawnPos);
        projectile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

        projectile.entityData.set(DATA_DISARM_LAUNCH_MODE, true);
        projectile.entityData.set(DATA_DISARM_DROP_AFTER_TICKS, Mth.clamp(dropAfterTicks, 4, 80));

        projectile.entityData.set(DATA_DISARM_MOTION_X, (float) launchMotion.x);
        projectile.entityData.set(DATA_DISARM_MOTION_Y, (float) launchMotion.y);
        projectile.entityData.set(DATA_DISARM_MOTION_Z, (float) launchMotion.z);

        projectile.noPhysics = false;
        projectile.setNoGravity(true);
        projectile.setDeltaMovement(launchMotion);

        return projectile;
    }

    public static ItemProjectile createHookPayload(Level level, Entity owner, ItemStack stack, Vec3 spawnPos) {
        ItemProjectile projectile = new ItemProjectile(AnnoyingVillagersModEntities.ITEM_PROJECTILE.get(), level);
        projectile.setOwner(owner);
        projectile.setWeaponStack(stack);
        projectile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        projectile.noPhysics = true;
        projectile.setNoGravity(true);
        projectile.setHookAttached(true);
        return projectile;
    }

    public void setWeaponStack(ItemStack stack) {
        this.entityData.set(DATA_STACK, stack.copy());
    }

    public ItemStack getWeaponStack() {
        return this.entityData.get(DATA_STACK);
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.getWeaponStack();
    }

    public boolean isHookAttached() {
        return this.entityData.get(DATA_HOOK_ATTACHED);
    }

    public void setHookAttached(boolean hookAttached) {
        this.entityData.set(DATA_HOOK_ATTACHED, hookAttached);
    }

    public void setDiscardWhenHookLost(boolean discardWhenHookLost) {
        this.entityData.set(DATA_DISCARD_WHEN_HOOK_LOST, discardWhenHookLost);
    }

    public void moveWithHook(Vec3 newPos, Entity ownerEntity) {
        Vec3 oldPos = this.position();
        Vec3 motion = newPos.subtract(oldPos);

        this.setHookAttached(true);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.setDeltaMovement(motion);
        this.setPos(newPos.x, newPos.y, newPos.z);
        this.updateRotationFromMotion(motion);

        if (!this.level().isClientSide) {
            this.damageEntitiesAlongPath(oldPos, newPos, ownerEntity);
        }
    }

    public void dropAsItem(Vec3 motion) {
        this.dropBackToItem(motion);
    }

    public void giveToOwnerOrDrop(Entity receiver) {
        if (this.level().isClientSide) {
            return;
        }

        ItemStack stack = this.getWeaponStack().copy();
        if (stack.isEmpty()) {
            this.discard();
            return;
        }

        if (receiver instanceof Player player) {
            ItemStack remaining = stack.copy();
            player.getInventory().add(remaining);

            if (remaining.isEmpty()) {
                this.discard();
                return;
            }

            this.dropStack(remaining, receiver.position(), Vec3.ZERO);
            this.discard();
            return;
        }

        this.dropStack(stack, receiver.position(), Vec3.ZERO);
        this.discard();
    }

    private void initializeArcPath(Vec3 firstTargetPos) {
        if (this.arcInitialized) {
            return;
        }

        this.arcInitialized = true;
        this.arcStart = this.position();

        double distance = this.arcStart.distanceTo(firstTargetPos);

        this.arcTravelTicks = Mth.clamp(
                (int) Math.round(distance * 4.5D),
                MIN_ARC_TRAVEL_TICKS,
                MAX_ARC_TRAVEL_TICKS
        );

        this.arcHeight = Mth.clamp(
                0.65D + distance * 0.22D,
                0.75D,
                2.25D
        );

        Vec3 direction = firstTargetPos.subtract(this.arcStart);

        if (direction.horizontalDistanceSqr() > 0.0001D) {
            Vec3 flat = new Vec3(direction.x, 0.0D, direction.z).normalize();

            this.arcSide = new Vec3(-flat.z, 0.0D, flat.x)
                    .scale((this.random.nextBoolean() ? 1.0D : -1.0D) * Mth.clamp(distance * 0.12D, 0.15D, 0.55D));
        } else {
            this.arcSide = Vec3.ZERO;
        }
    }

    private Vec3 getArcPosition(double progress, Vec3 currentTargetPos) {
        Vec3 start = this.arcStart;
        Vec3 end = currentTargetPos;

        Vec3 middle = start.add(end)
                .scale(0.5D)
                .add(0.0D, this.arcHeight, 0.0D)
                .add(this.arcSide);

        double inverse = 1.0D - progress;
        return start.scale(inverse * inverse)
                .add(middle.scale(2.0D * inverse * progress))
                .add(end.scale(progress * progress));
    }

    @Override
    public void tick() {
        super.tick();

        this.noPhysics = true;
        this.setNoGravity(true);

        ItemStack stack = this.getWeaponStack();
        if (stack.isEmpty()) {
            this.discard();
            return;
        }

        if (this.isDisarmLaunchMode()) {
            this.tickDisarmLaunchMode();
            return;
        }

        if (this.isHookAttached()) {
            this.tickHookAttached();
            return;
        }

        Entity ownerEntity = this.getOwner();
        if (!(ownerEntity instanceof LivingEntity owner) || !owner.isAlive()) {
            if (!this.level().isClientSide) {
                this.dropBackToItem();
            }

            return;
        }

        Vec3 oldPos = this.position();
        Vec3 targetPos = this.getTargetHandPosition(owner);

        this.initializeArcPath(targetPos);

        double rawProgress = Mth.clamp(
                (double) this.tickCount / (double) this.arcTravelTicks,
                0.0D,
                1.0D
        );

        double progress = rawProgress * rawProgress * (3.0D - 2.0D * rawProgress);

        Vec3 newPos = this.getArcPosition(progress, targetPos);
        Vec3 motion = newPos.subtract(oldPos);

        this.setDeltaMovement(motion);
        this.setPos(newPos.x, newPos.y, newPos.z);

        if (!this.level().isClientSide) {
            this.damageEntitiesAlongPath(oldPos, newPos, owner);
        }

        this.updateRotationFromMotion(motion);
        this.clearOldHitCooldowns();

        if (rawProgress >= 1.0D) {
            if (!this.level().isClientSide) {
                this.dropBackToItem();
            }

            return;
        }
    }

    private void tickHookAttached() {
        this.noPhysics = true;
        this.setNoGravity(true);
        this.clearOldHitCooldowns();

        if (!this.level().isClientSide && !this.hasActiveHookController()) {
            this.setHookAttached(false);
            if (this.entityData.get(DATA_DISCARD_WHEN_HOOK_LOST)) {
                this.discard();
                return;
            }
            this.dropBackToItem();
        }
    }

    private boolean hasActiveHookController() {
        Entity ownerEntity = this.getOwner();
        if (ownerEntity instanceof Player player) {
            return FishingRodGrappleUtil.isHookControllingItemProjectile(player.fishing, this);
        }

        if (ownerEntity instanceof FishingHook hook) {
            return FishingRodGrappleUtil.isHookControllingItemProjectile(hook, this);
        }

        return false;
    }

    private Vec3 getTargetHandPosition(LivingEntity owner) {
        Vec3 jointPos = null;

        try {
            jointPos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    0.0F,
                    0.0D
            );
        } catch (Exception ignored) {
            // Fallback below.
        }

        if (jointPos != null) {
            return jointPos;
        }

        // Fallback if Epic Fight patch/armature is not available.
        return owner.getEyePosition()
                .add(owner.getLookAngle().scale(0.45D))
                .subtract(0.0D, 0.25D, 0.0D);
    }

    private void damageEntitiesAlongPath(Vec3 from, Vec3 to, Entity owner) {
        if (this.level().isClientSide) {
            return;
        }

        AABB sweepBox = new AABB(from, to).inflate(0.75D);

        for (LivingEntity target : this.level().getEntitiesOfClass(
                LivingEntity.class,
                sweepBox,
                entity -> this.canDamage(entity, owner)
        )) {
            int nextAllowedHitTick = this.recentHits.getOrDefault(target.getId(), 0);
            if (nextAllowedHitTick > this.tickCount) {
                continue;
            }

            if (target.getBoundingBox().inflate(0.3D).clip(from, to).isEmpty()) {
                continue;
            }

            boolean damaged = this.isHookAttached()
                    ? this.damageEnemyHitByHookedItem(target, owner)
                    : this.damageEnemyHitByThrownItem(target, owner);
            if (damaged) {
                this.recentHits.put(target.getId(), this.tickCount + HIT_COOLDOWN_TICKS);
            }
        }
    }

    protected boolean damageEnemyHitByHookedItem(LivingEntity target, Entity owner) {
        if (this.tryHandleSpecialBoundItemHit(target, owner)) {
            return true;
        }

        DamageSource source = this.level().damageSources().thrown(this, owner);
        if (!target.hurt(source, this.calculateHookAttachedItemDamage(target))) {
            return false;
        }

        if (owner instanceof LivingEntity livingOwner) {
            this.applyWeaponEnchantEffects(livingOwner, target);
        }

        this.afterHookAttachedItemHit(target, owner);
        this.playSound(AnnoyingVillagersModSounds.OB_PLACE.get(), 0.5F, 1.0F);
        return true;
    }

    protected float calculateHookAttachedItemDamage(LivingEntity target) {
        ItemStack stack = this.getWeaponStack();
        if (stack.getItem() instanceof ShieldItem) {
            return 15.0F;
        }

        return this.calculateWeaponDamage(target);
    }

    protected void afterHookAttachedItemHit(LivingEntity target, Entity owner) {
        ItemStack stack = this.getWeaponStack();
        if (stack.getItem() instanceof ShieldItem) {
            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
            if (targetPatch != null && !targetPatch.isStunned()) {
                targetPatch.applyStun(StunType.LONG, 0.0F);
            }
        }
    }

    private boolean damageEnemyHitByThrownItem(LivingEntity target, Entity owner) {
        if (this.tryHandleSpecialBoundItemHit(target, owner)) {
            return true;
        }

        DamageSource source = this.level().damageSources().thrown(this, owner);
        if (!target.hurt(source, this.calculateWeaponDamage(target))) {
            return false;
        }

        if (owner instanceof LivingEntity livingOwner) {
            this.applyWeaponEnchantEffects(livingOwner, target);
        }

        this.playSound(AnnoyingVillagersModSounds.OB_PLACE.get(), 0.5F, 1.0F);
        return true;
    }

    private boolean tryHandleSpecialBoundItemHit(LivingEntity target, Entity owner) {
        ItemStack mutableStack = this.getWeaponStack().copy();
        LivingEntity livingOwner = owner instanceof LivingEntity ownerLiving ? ownerLiving : null;
        if (HookUtil.handleEntityHit(this.level(), mutableStack, this, livingOwner, target) != HookUtil.HitResult.HANDLED) {
            return false;
        }

        this.setWeaponStack(mutableStack);
        if (mutableStack.isEmpty()) {
            this.discard();
        }
        return true;
    }

    private boolean canDamage(LivingEntity target, Entity owner) {
        if (!target.isAlive()) {
            return false;
        }

        if (target.isSpectator()) {
            return false;
        }

        if (target == owner || target.getUUID().equals(owner.getUUID())) {
            return false;
        }

        return !(owner instanceof LivingEntity livingOwner) || !target.isAlliedTo(livingOwner);
    }

    private float calculateWeaponDamage(LivingEntity target) {
        ItemStack stack = this.getWeaponStack();

        double damage = 1.0D;

        Multimap<Attribute, AttributeModifier> modifiers =
                stack.getAttributeModifiers(EquipmentSlot.MAINHAND);

        for (AttributeModifier modifier : modifiers.get(Attributes.ATTACK_DAMAGE)) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                damage += modifier.getAmount();
            } else if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE) {
                damage += damage * modifier.getAmount();
            } else if (modifier.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
                damage *= 1.0D + modifier.getAmount();
            }
        }

        damage += EnchantmentHelper.getDamageBonus(stack, target.getMobType());

        return (float) Math.max(1.0D, damage);
    }

    private void applyWeaponEnchantEffects(LivingEntity owner, LivingEntity target) {
        ItemStack stack = this.getWeaponStack();

        int fireAspect = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
        if (fireAspect > 0) {
            target.setSecondsOnFire(fireAspect * 4);
        }

        EnchantmentHelper.doPostHurtEffects(target, owner);
        EnchantmentHelper.doPostDamageEffects(owner, target);
    }

    private void dropBackToItem() {
        this.dropBackToItem(new Vec3(0.0D, -0.05D, 0.0D));
    }

    private void dropBackToItem(Vec3 motion) {
        if (!this.level().isClientSide) {
            ItemStack stack = this.getWeaponStack().copy();

            if (!stack.isEmpty()) {
                this.dropStack(stack, this.position(), motion);
            }
        }

        this.discard();
    }

    private void dropStack(ItemStack stack, Vec3 position, Vec3 motion) {
        ItemEntity itemEntity = new ItemEntity(
                this.level(),
                position.x,
                position.y,
                position.z,
                stack
        );

        itemEntity.setPickUpDelay(20);
        itemEntity.setDeltaMovement(motion);

        this.level().addFreshEntity(itemEntity);
    }

    private void updateRotationFromMotion(Vec3 motion) {
        double horizontal = motion.horizontalDistance();

        if (horizontal > 1.0E-7D) {
            this.setYRot((float) (Mth.atan2(motion.x, motion.z) * Mth.RAD_TO_DEG));
            this.setXRot((float) (Mth.atan2(motion.y, horizontal) * Mth.RAD_TO_DEG));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
    }

    private void clearOldHitCooldowns() {
        if (this.recentHits.isEmpty()) {
            return;
        }

        this.recentHits.entrySet().removeIf(entry -> entry.getValue() <= this.tickCount);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("WeaponStack", this.getWeaponStack().save(new CompoundTag()));
        tag.putBoolean("DisarmLaunchMode", this.entityData.get(DATA_DISARM_LAUNCH_MODE));
        tag.putInt("DisarmDropAfterTicks", this.entityData.get(DATA_DISARM_DROP_AFTER_TICKS));
        tag.putFloat("DisarmMotionX", this.entityData.get(DATA_DISARM_MOTION_X));
        tag.putFloat("DisarmMotionY", this.entityData.get(DATA_DISARM_MOTION_Y));
        tag.putFloat("DisarmMotionZ", this.entityData.get(DATA_DISARM_MOTION_Z));
        tag.putBoolean("HookAttached", this.entityData.get(DATA_HOOK_ATTACHED));
        tag.putBoolean("DiscardWhenHookLost", this.entityData.get(DATA_DISCARD_WHEN_HOOK_LOST));
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("WeaponStack", 10)) {
            this.setWeaponStack(ItemStack.of(tag.getCompound("WeaponStack")));
        }
        this.entityData.set(DATA_DISARM_LAUNCH_MODE, tag.getBoolean("DisarmLaunchMode"));

        if (tag.contains("DisarmDropAfterTicks")) {
            this.entityData.set(DATA_DISARM_DROP_AFTER_TICKS, tag.getInt("DisarmDropAfterTicks"));
        }

        if (tag.contains("DisarmMotionX")) {
            this.entityData.set(DATA_DISARM_MOTION_X, tag.getFloat("DisarmMotionX"));
            this.entityData.set(DATA_DISARM_MOTION_Y, tag.getFloat("DisarmMotionY"));
            this.entityData.set(DATA_DISARM_MOTION_Z, tag.getFloat("DisarmMotionZ"));

            this.setDeltaMovement(
                    tag.getFloat("DisarmMotionX"),
                    tag.getFloat("DisarmMotionY"),
                    tag.getFloat("DisarmMotionZ")
            );
        }

        this.entityData.set(DATA_HOOK_ATTACHED, tag.getBoolean("HookAttached"));
        this.entityData.set(DATA_DISCARD_WHEN_HOOK_LOST, tag.getBoolean("DiscardWhenHookLost"));
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
