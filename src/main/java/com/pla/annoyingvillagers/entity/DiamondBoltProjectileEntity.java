package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiamondBoltProjectileEntity extends AbstractArrow implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_STACK =
            SynchedEntityData.defineId(DiamondBoltProjectileEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Byte> ID_LOYALTY =
            SynchedEntityData.defineId(DiamondBoltProjectileEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ID_FOIL =
            SynchedEntityData.defineId(DiamondBoltProjectileEntity.class, EntityDataSerializers.BOOLEAN);

    public boolean dealtDamage;
    public int clientSideReturnTickCount;

    public DiamondBoltProjectileEntity(EntityType<? extends DiamondBoltProjectileEntity> type, Level level) {
        super(type, level);
    }

    public DiamondBoltProjectileEntity(PlayMessages.SpawnEntity packet, Level level) {
        this(AnnoyingVillagersModEntities.DIAMOND_BOLT_PROJECTILE.get(), level);
    }

    public DiamondBoltProjectileEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(AnnoyingVillagersModEntities.DIAMOND_BOLT_PROJECTILE.get(), shooter, level);
        this.setThrownStack(stack);
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
        this.entityData.set(ID_FOIL, stack.hasFoil());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STACK, ItemStack.EMPTY);
        this.entityData.define(ID_LOYALTY, (byte) 0);
        this.entityData.define(ID_FOIL, false);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity owner = this.getOwner();
        int loyalty = this.entityData.get(ID_LOYALTY);

        if (loyalty > 0 && (this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!this.isAcceptableReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 returnVector = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + returnVector.y * 0.015D * (double) loyalty, this.getZ());

                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double returnSpeed = 0.05D * (double) loyalty;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(returnVector.normalize().scale(returnSpeed)));

                if (this.clientSideReturnTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnTickCount;
            }
        }

        super.tick();
    }

    private boolean isAcceptableReturnOwner() {
        Entity owner = this.getOwner();
        return owner != null && owner.isAlive() && (!(owner instanceof ServerPlayer serverPlayer) || !serverPlayer.isSpectator());
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        return this.getThrownStack().copy();
    }

    @Override
    public @NotNull ItemStack getItem() {
        return this.getPickupItem();
    }

    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    @Override
    protected @Nullable EntityHitResult findHitEntity(@NotNull Vec3 start, @NotNull Vec3 end) {
        return this.dealtDamage ? null : super.findHitEntity(start, end);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        this.dealtDamage = true;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        Entity target = result.getEntity();
        float damage = 8.0F;
        ItemStack thrownStack = this.getThrownStack();

        if (target instanceof LivingEntity livingTarget) {
            damage += EnchantmentHelper.getDamageBonus(thrownStack, livingTarget.getMobType());
        }

        Entity owner = this.getOwner();
        DamageSource damageSource = this.damageSources().trident(this, owner == null ? this : owner);
        this.dealtDamage = true;

        SoundEvent hitSound = SoundEvents.TRIDENT_HIT;
        float soundVolume = 1.0F;

        if (target.hurt(damageSource, damage)) {
            if (target.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (target instanceof LivingEntity livingTarget) {
                if (owner instanceof LivingEntity livingOwner) {
                    EnchantmentHelper.doPostHurtEffects(livingTarget, owner);
                    EnchantmentHelper.doPostDamageEffects(livingOwner, livingTarget);
                }

                this.doPostHurtEffects(livingTarget);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));

        if (this.level() instanceof ServerLevel
                && this.level().isThundering()
                && this.isChanneling()) {
            BlockPos blockPos = target.blockPosition();

            if (this.level().canSeeSky(blockPos)) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(this.level());

                if (lightningBolt != null) {
                    lightningBolt.moveTo(Vec3.atBottomCenterOf(blockPos));
                    lightningBolt.setCause(owner instanceof ServerPlayer serverPlayer ? serverPlayer : null);
                    this.level().addFreshEntity(lightningBolt);
                    hitSound = SoundEvents.TRIDENT_THUNDER;
                    soundVolume = 5.0F;
                }
            }
        }

        this.playSound(hitSound, soundVolume, 1.0F);
    }

    public boolean isChanneling() {
        return EnchantmentHelper.hasChanneling(this.getThrownStack());
    }

    @Override
    protected boolean tryPickup(@NotNull Player player) {
        return super.tryPickup(player)
                || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        if (!this.level().isClientSide
                && (this.inGround || this.isNoPhysics() || this.dealtDamage)
                && this.shakeTime <= 0
                && this.tryPickup(player)) {
            player.take(this, 1);
            this.discard();
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("DiamondBolt", 10)) {
            this.setThrownStack(ItemStack.of(tag.getCompound("DiamondBolt")));
        }

        this.dealtDamage = tag.getBoolean("DealtDamage");
        ItemStack thrownStack = this.getThrownStack();
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(thrownStack));
        this.entityData.set(ID_FOIL, thrownStack.hasFoil());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("DiamondBolt", this.getThrownStack().save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void tickDespawn() {
        int loyalty = this.entityData.get(ID_LOYALTY);

        if (this.pickup != Pickup.ALLOWED || loyalty <= 0) {
            super.tickDespawn();
        }
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void setThrownStack(ItemStack stack) {
        this.entityData.set(DATA_STACK, stack.copy());
    }

    private ItemStack getThrownStack() {
        ItemStack stack = this.entityData.get(DATA_STACK);
        return stack.isEmpty() ? new ItemStack(AnnoyingVillagersModItems.DIAMOND_BOLT.get()) : stack;
    }
}
