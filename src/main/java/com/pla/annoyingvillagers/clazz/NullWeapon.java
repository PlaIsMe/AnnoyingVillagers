package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.util.EnchantmentUtil;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.goal.PortalApproachGoal;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.NullWeaponItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunnableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class NullWeapon extends Monster implements ForceTickEntity, RigStunnableEntity {
    private static final EntityDataAccessor<Boolean> DATA_SPINNING = SynchedEntityData.defineId(NullWeapon.class, EntityDataSerializers.BOOLEAN);
    protected UUID nullUUID;
    protected NullEntity nullEntity;

    protected UUID playerUUID;
    protected Player player;

    protected String weapon;
    private int spinAnimationSequence = 0;
    private int randomSpinCooldown = 120;
    private int weaponAttackCooldown = 0;
    private boolean suppressRemovalDrop;
    private int releaseCooldown = 0;
    private UUID releaseTargetUUID;
    private LivingEntity releaseTarget;

    protected boolean released = false;

    public boolean isReleased() {
        return released;
    }

    public LivingEntity getOrbitOwner() {
        if (this.nullEntity != null && this.nullEntity.isAlive()) return this.nullEntity;
        if (this.player != null && this.player.isAlive()) return this.player;
        return null;
    }

    public void stopRelease() {
        this.released = false;
        this.releaseCooldown = 0;
        this.weaponAttackCooldown = 0;
        this.releaseTargetUUID = null;
        this.releaseTarget = null;
        this.setTarget(null);
    }

    public void setReleaseTarget(@Nullable LivingEntity target) {
        if (!this.isValidReleaseTarget(target)) {
            this.releaseTargetUUID = null;
            this.releaseTarget = null;
            return;
        }

        this.releaseTargetUUID = target.getUUID();
        this.releaseTarget = target;
        this.setTarget(target);
    }

    @Nullable
    public LivingEntity getReleaseTarget() {
        if (this.isValidReleaseTarget(this.releaseTarget)) return this.releaseTarget;

        this.releaseTarget = null;
        if (this.releaseTargetUUID != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.releaseTargetUUID);
            if (entity instanceof LivingEntity livingEntity && this.isValidReleaseTarget(livingEntity)) {
                this.releaseTarget = livingEntity;
                return livingEntity;
            }
        }

        return null;
    }

    public void returnToNullImmediately() {
        this.stopRelease();
        if (this.nullEntity == null || !this.nullEntity.isAlive()) return;

        Vec3 returnPosition = this.nullEntity.position().add(0.0D, this.nullEntity.getBbHeight() * 0.65D, 0.0D);
        this.snapTo(returnPosition.x, returnPosition.y, returnPosition.z, this.getYRot(), this.getXRot());
        this.setDeltaMovement(Vec3.ZERO);
    }

    public void release() {
        this.release(this.findReleaseTarget());
    }

    public void release(@Nullable LivingEntity target) {
        LivingEntity releaseTarget = this.isValidReleaseTarget(target) ? target : this.findReleaseTarget();
        this.setReleaseTarget(releaseTarget);
        this.releaseCooldown = this.nullEntity != null && this.nullEntity.getState() >= 2 ? -1 : 300 + this.getRandom().nextInt(301);
        this.weaponAttackCooldown = 0;
        this.released = true;
        this.spinfor5seconds();
    }

    public void releaseForTicks(@Nullable LivingEntity target, int ticks) {
        LivingEntity releaseTarget = this.isValidReleaseTarget(target) ? target : this.findReleaseTarget();
        this.setReleaseTarget(releaseTarget);
        this.releaseCooldown = Math.max(1, ticks);
        this.weaponAttackCooldown = 0;
        this.released = true;
        if (releaseTarget != null) this.teleportRandomlyAround(releaseTarget, 4.0D, -2.0D, 2.0D);
        this.spinfor5seconds();
    }

    @Nullable
    public LivingEntity findReleaseTarget() {
        LivingEntity target = this.getReleaseTarget();
        if (this.isValidReleaseTarget(target)) return target;

        target = this.getTarget();
        if (this.isValidReleaseTarget(target)) return target;

        if (this.nullEntity != null && this.nullEntity.isAlive()) {
            target = this.nullEntity.getTarget();
            if (!this.isValidReleaseTarget(target)) target = this.nullEntity.getLastHurtMob();
            if (!this.isValidReleaseTarget(target)) target = this.nullEntity.getLastHurtByMob();
            if (this.isValidReleaseTarget(target)) return target;
        }

        if (this.player != null && this.player.isAlive()) {
            target = this.player.getLastHurtMob();
            if (!this.isValidReleaseTarget(target)) target = this.player.getLastHurtByMob();
            if (this.isValidReleaseTarget(target)) return target;
        }

        return getNearestLivingEntity(this.level(), this, 18.0D);
    }


    @Nullable
    private LivingEntity getPlayerAssistTarget() {
        if (this.player == null || !this.player.isAlive()) return null;
        LivingEntity target = this.player.getLastHurtMob();
        if (!this.isValidReleaseTarget(target)) target = this.player.getLastHurtByMob();
        return this.isValidReleaseTarget(target) ? target : null;
    }

    public boolean hasPlayerAssistTarget() {
        return this.getPlayerAssistTarget() != null;
    }

    @Nullable
    private LivingEntity getCombatTarget() {
        return this.released ? this.getReleaseTarget() : null;
    }
    private boolean isValidReleaseTarget(@Nullable LivingEntity target) {
        if (target == null || !target.isAlive() || target.isRemoved() || target == this.nullEntity || target == this.player || target instanceof NullWeapon) return false;
        // A Null-owned weapon belongs to the Herobrine side even if scoreboard team
        // data has not been restored yet (for example, just after loading a world).
        if ((this.nullEntity != null || this.nullUUID != null) && HerobrineUtil.isHerobrineSide(target)) return false;
        if (this.nullEntity != null && (target.isAlliedTo(this.nullEntity) || this.nullEntity.isAlliedTo(target))) return false;
        if (this.player != null && (target.isAlliedTo(this.player) || this.player.isAlliedTo(target))) return false;
        return !target.isAlliedTo(this);
    }

    public void setSpinning(boolean spinning) {
        if (this.isSpinning() == spinning) return;
        this.entityData.set(DATA_SPINNING, spinning);
        this.spinAnimationSequence++;

        if (this.level().isClientSide()) return;
        if (spinning) {
            RigAnimationController.playHeldPose(this, RigAnimationId.SPINNING_WEAPON);
        } else {
            RigAnimationController.stop(this, RigAnimationId.SPINNING_WEAPON);
        }
    }

    public boolean isSpinning() {
        return this.entityData.get(DATA_SPINNING);
    }

    public void setReleased(boolean released) {
        if (released) {
            this.release();
        } else {
            this.stopRelease();
        }
    }

    public void spinfor5seconds() {
        if (this.level().isClientSide() || !this.isAlive() || this.isRemoved()) return;

        if (this.isSpinning()) this.setSpinning(false);
        this.setSpinning(true);
        final int sequence = this.spinAnimationSequence;
        new DelayedTask(100) {
            @Override
            public void run() {
                if (!NullWeapon.this.isAlive() || NullWeapon.this.isRemoved()) return;
                if (sequence != NullWeapon.this.spinAnimationSequence) return;
                NullWeapon.this.setSpinning(false);
            }
        };
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
        switch (weapon) {
            case "sword" -> {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.NULL_SWORD.get()));
            }
            case "pickaxe" -> {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.NULL_PICKAXE.get()));
            }
            case "axe" -> {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.NULL_AXE.get()));
            }
            case "hoe" -> {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.NULL_HOE.get()));
            }
            default -> {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.NULL_SHOVEL.get()));
            }
        }
    }

    public void setNullUUID(UUID nullUUID) {
        this.nullUUID = nullUUID;
    }

    public void setNullEntity(NullEntity nullEntity) {
        this.nullEntity = nullEntity;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public UUID getNullUUID() {
        return nullUUID;
    }

    public NullEntity getNullEntity() {
        return nullEntity;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SPINNING, false);
    }

    protected NullWeapon(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(4.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.refreshDimensions();
    }

        protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new FlyingPathNavigation(this, level);
    }

    protected void registerGoals() {
        super.registerGoals();

        this.targetSelector.addGoal(1, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, target -> {
            if (!this.released || this.player == null || !this.player.isAlive()) return false;
            LivingEntity lastHurtBy = this.player.getLastHurtByMob();
            LivingEntity lastHurt = this.player.getLastHurtMob();
            return (target == lastHurtBy || target == lastHurt) && target.isAlive() && !target.isAlliedTo(this.player);
        }));
        this.targetSelector.addGoal(1, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, target -> this.released
                && this.nullEntity != null && this.nullEntity.isAlive() && target != null
                && this.nullEntity.getTarget() == target && this.isValidReleaseTarget(target)));
        this.targetSelector.addGoal(2, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, target -> this.released
                && this.nullEntity != null && this.nullEntity.isAlive() && target != null
                && target.getLastHurtMob() == this.nullEntity && this.isValidReleaseTarget(target)));
        this.targetSelector.addGoal(3, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                target -> this.released && this.isValidReleaseTarget(target)));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return NullWeapon.this.isReleased() && super.canUse();
            }
        });

        this.goalSelector.addGoal(2, new PortalApproachGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.4D, 20) {
            @Override
            protected Vec3 getPosition() {
                LivingEntity anchor = NullWeapon.this.released ? NullWeapon.this.getReleaseTarget() : NullWeapon.this.getOrbitOwner();

                double x = NullWeapon.this.getX() + NullWeapon.this.getRandom().nextDouble() * 2.0D - 1.0D;
                double y = NullWeapon.this.getY() + NullWeapon.this.getRandom().nextDouble() * 2.0D - 1.0D;
                double z = NullWeapon.this.getZ() + NullWeapon.this.getRandom().nextDouble() * 2.0D - 1.0D;

                if (anchor != null && anchor.isAlive()) {
                    y = Mth.clamp(y, anchor.getY() - 2.5D, anchor.getY() + 3.5D);
                }

                return new Vec3(x, y, z);
            }
        });

        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, NullEntity.class, 6.0F));

        // Restore the old target-approach behavior. The teleport is the chaotic reposition;
        // FlyingMoveControl is the smooth movement that happens after each teleport.
        this.goalSelector.addGoal(7, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            }

            @Override
            public boolean canUse() {
                LivingEntity target = NullWeapon.this.getCombatTarget();
                return NullWeapon.this.isValidReleaseTarget(target);
            }

            @Override
            public boolean canContinueToUse() {
                LivingEntity target = NullWeapon.this.getCombatTarget();
                return NullWeapon.this.isValidReleaseTarget(target);
            }

            @Override
            public boolean requiresUpdateEveryTick() {
                return true;
            }

            @Override
            public void start() {
                this.approachTarget();
            }

            @Override
            public void tick() {
                LivingEntity target = NullWeapon.this.getCombatTarget();
                if (!NullWeapon.this.isValidReleaseTarget(target) && NullWeapon.this.released) {
                    target = NullWeapon.this.findReleaseTarget();
                    NullWeapon.this.setReleaseTarget(target);
                }
                if (target == null) return;

                NullWeapon.this.getLookControl().setLookAt(target, 90.0F, 90.0F);
                double distanceSqr = NullWeapon.this.distanceToSqr(target);

                if (distanceSqr < 16.0D) {
                    Vec3 targetPosition = target.getEyePosition(1.0F);
                    NullWeapon.this.moveControl.setWantedPosition(targetPosition.x, targetPosition.y, targetPosition.z, 2.0D);
                }

                RigAnimationId activeAnimation = RigAnimationController.getActiveAnimationId(NullWeapon.this);
                if (distanceSqr <= 12.25D && NullWeapon.this.weaponAttackCooldown <= 0
                        && (activeAnimation == null || activeAnimation == RigAnimationId.SPINNING_WEAPON)) {
                    if (NullWeapon.this.isSpinning()) NullWeapon.this.setSpinning(false);

                    RigAnimationId attack = switch (NullWeapon.this.getRandom().nextInt(3)) {
                        case 0 -> RigAnimationId.SWORD_ATTACK1;
                        case 1 -> RigAnimationId.SWORD_ATTACK2;
                        default -> RigAnimationId.SWORD_ATTACK3;
                    };

                    RigAnimationController.play(NullWeapon.this, RigAnimationSpecs.get(attack), target);
                    NullWeapon.this.weaponAttackCooldown = RigAnimationSpecs.get(attack).durationTicks() + 2;
                }
            }

            private void approachTarget() {
                LivingEntity target = NullWeapon.this.getCombatTarget();
                if (target == null) return;

                Vec3 targetPosition = target.getEyePosition(1.0F);
                NullWeapon.this.moveControl.setWantedPosition(targetPosition.x, targetPosition.y, targetPosition.z, 2.0D);
            }
        });
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        CompoundTag tag = new CompoundTag();
        super.addAdditionalSaveData(output);
        tag.putString("Weapon", weapon);
        if (nullUUID != null) {
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, "NullUUID", nullUUID);
        }
        if (playerUUID != null) {
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, "OwnerUUID", playerUUID);
        }
        tag.putBoolean("Released", released);
        tag.putInt("ReleaseCooldown", releaseCooldown);
        if (releaseTargetUUID != null) com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, "ReleaseTargetUUID", releaseTargetUUID);
    
        com.pla.annoyingvillagers.util.LegacyValueIO.write(output, tag);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        CompoundTag tag = com.pla.annoyingvillagers.util.LegacyValueIO.read(input);
        super.readAdditionalSaveData(input);
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(tag, "NullUUID")) {
            nullUUID = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(tag, "NullUUID");
        }
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(tag, "OwnerUUID")) {
            playerUUID = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(tag, "OwnerUUID");
        }
        weapon = tag.getStringOr("Weapon", "");
        released = tag.getBooleanOr("Released", false);
        releaseCooldown = tag.getIntOr("ReleaseCooldown", 0);
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(tag, "ReleaseTargetUUID")) releaseTargetUUID = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(tag, "ReleaseTargetUUID");
    }

    @Override
    protected @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return EntityDimensions.fixed(0.0F, 0.0F);
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
    public boolean canBeCollidedWith(@Nullable Entity other) {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

        public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource damagesource) {
        return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("", "")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.fromNamespaceAndPath("", "")));
    }

    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damagesource) {
        return false;
    }

    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource damagesource, float f) {
        return false;
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverlevelaccessor, @NotNull DifficultyInstance difficultyinstance, @NotNull EntitySpawnReason mobspawntype, @Nullable SpawnGroupData spawngroupdata) {
        TeamUtil.addOrJoinTeam(this, "herobrine");
        this.setInvulnerable(true);
        return super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata);
    }

    protected void checkFallDamage(double d0, boolean flag, @NotNull BlockState blockstate, @NotNull BlockPos blockpos) {}

    public void setNoGravity(boolean flag) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    @Override
    public boolean doHurtTarget(net.minecraft.server.level.ServerLevel serverLevel, Entity pEntity) {
        if (this.player != null && !this.released) return false;
        if (pEntity instanceof LivingEntity livingEntity && !this.isValidReleaseTarget(livingEntity)) return false;
        if (pEntity instanceof Player hurtPlayer && this.playerUUID != null && this.playerUUID.equals(hurtPlayer.getUUID())) {
            return false;
        }
        if (pEntity instanceof NullEntity hurtNull && this.nullUUID != null && this.nullUUID.equals(hurtNull.getUUID())) {
            return false;
        }

        if (this.player != null) {
            float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
            if (pEntity instanceof LivingEntity) {
                f += EnchantmentUtil.getDamageBonus(this.getMainHandItem(), (LivingEntity) pEntity);
                f1 += EnchantmentUtil.getLevel(Enchantments.KNOCKBACK, this.getMainHandItem());
            }

            int i = EnchantmentUtil.getLevel(Enchantments.FIRE_ASPECT, this.getMainHandItem());
            if (i > 0) {
                pEntity.igniteForSeconds(i * 4.0F);
            }

            DamageSource attackSource = this.damageSources().playerAttack(this.player);
            boolean flag = pEntity.hurtOrSimulate(attackSource, f);
            if (flag) {
                if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                    ((LivingEntity)pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0F, 0.6));
                }
                if (true) {
                    EnchantmentHelper.doPostAttackEffects(serverLevel, pEntity, attackSource);
                }
                this.setLastHurtMob(pEntity);
                com.pla.annoyingvillagers.item.NullWeaponItem.onOwnedWeaponHit(this.player);
            }

            return flag;
        } else if (this.nullEntity != null) {
            float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
            if (pEntity instanceof LivingEntity) {
                f += EnchantmentUtil.getDamageBonus(this.getMainHandItem(), (LivingEntity) pEntity);
                f1 += EnchantmentUtil.getLevel(Enchantments.KNOCKBACK, this.getMainHandItem());
            }

            int i = EnchantmentUtil.getLevel(Enchantments.FIRE_ASPECT, this.getMainHandItem());
            if (i > 0) {
                pEntity.igniteForSeconds(i * 4.0F);
            }

            DamageSource attackSource = this.damageSources().mobAttack(this.nullEntity);
            boolean flag = pEntity.hurtOrSimulate(attackSource, f);
            if (flag) {
                if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                    ((LivingEntity)pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0F, 0.6));
                }
                if (true) {
                    EnchantmentHelper.doPostAttackEffects(serverLevel, pEntity, attackSource);
                }
                this.setLastHurtMob(pEntity);
            }

            return flag;
        } else {
            return super.doHurtTarget(serverLevel, pEntity);
        }
    }

    private static boolean isAllowedHeldCategory(Player p) {
        return true;
    }

    private static boolean hasNullSword(Player p) {
        for (ItemStack s : p.getInventory().getNonEquipmentItems()) {
            if (s.getItem() instanceof NullWeaponItem) return true;
        }
        if (p.getOffhandItem().getItem() instanceof NullWeaponItem) return true;
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        ItemStack check;
        switch (this.weapon) {
            case "sword" -> {
                check = new ItemStack(AnnoyingVillagersModItems.NULL_SWORD.get());
            }
            case "pickaxe" -> {
                check = new ItemStack(AnnoyingVillagersModItems.NULL_PICKAXE.get());
            }
            case "axe" -> {
                check = new ItemStack(AnnoyingVillagersModItems.NULL_AXE.get());
            }
            case "hoe" -> {
                check = new ItemStack(AnnoyingVillagersModItems.NULL_HOE.get());
            }
            default -> {
                check = new ItemStack(AnnoyingVillagersModItems.NULL_SHOVEL.get());
            }
        }
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).getItem() != check.getItem()) {
            if (this.nullEntity == null && this.player != null) {
                this.discard();
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, check);
        }
        if (!level().isClientSide()) {
            if (nullEntity != null && !nullEntity.isAlive()) {
                this.discard();
                return;
            }

            if (nullEntity == null && nullUUID == null && player == null && playerUUID == null) {
                this.discard();
                return;
            }

            ItemStack stack = this.getMainHandItem();
            this.setHealth(stack.getMaxDamage() - stack.getDamageValue());
            if (nullEntity == null && nullUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(nullUUID);
                if (entity instanceof NullEntity entityNull) {
                    if (!entityNull.reconnectNullWeapon(this.weapon, this)) {
                        this.suppressRemovalDrop = true;
                        this.discard();
                        return;
                    }
                    this.nullEntity = entityNull;
                }
            }
            if (nullEntity != null && !nullEntity.isAlive()) {
                nullEntity = null;
                nullUUID = null;
            }
            if (player == null && playerUUID != null) {
                this.player = level().getPlayerByUUID(playerUUID);
            }
            if (player != null && player.isAlive() && !this.reconcilePlayerTracking()) {
                this.discard();
                return;
            }
            if (player != null && !player.isAlive()) {
                this.remove(RemovalReason.KILLED);
            }
            if (player != null && player.isAlive()) {
                if (!hasNullSword(player) || !isAllowedHeldCategory(player)) {
                    this.remove(RemovalReason.KILLED);
                }
            }
        }

        if (this.weaponAttackCooldown > 0) this.weaponAttackCooldown--;

        if (!this.level().isClientSide()) {
            if (this.randomSpinCooldown > 0) this.randomSpinCooldown--;
            if (!this.released && !this.isSpinning() && this.randomSpinCooldown <= 0) {
                this.randomSpinCooldown = 180 + this.getRandom().nextInt(321);
                this.spinfor5seconds();
            }
        }

        if (this.released) {
            LivingEntity target = this.getReleaseTarget();
            if (!this.isValidReleaseTarget(target)) {
                target = this.findReleaseTarget();
                this.setReleaseTarget(target);
            } else if (this.getTarget() != target) {
                this.setTarget(target);
            }
        }

        if (!this.level().isClientSide()) this.correctExcessiveVerticalDrift();

        // Player-owned weapons use this entity tick to drive the legacy ten-tick teleport.
        if (!this.level().isClientSide() && this.player != null && this.tickCount % 10 == 0) this.processTeleportByPlayer();

        if (this.releaseCooldown > 0) this.releaseCooldown--;
        if (this.releaseCooldown == 0 && this.released) this.stopRelease();
    }

    public static LivingEntity getNearestLivingEntity(Level level, Entity sourceEntity, double range) {
        AABB searchBox = sourceEntity.getBoundingBox().inflate(range);

        return level.getEntitiesOfClass(LivingEntity.class, searchBox,
                        e -> e != sourceEntity
                                && !(e instanceof NullWeapon)
                                && e.isAlive()
                                && (sourceEntity instanceof NullWeapon weapon
                                ? weapon.isValidReleaseTarget(e)
                                : !e.isAlliedTo(sourceEntity)))
                .stream().min(java.util.Comparator.comparingDouble(sourceEntity::distanceToSqr)).orElse(null);
    }

    public void teleportRandomlyAround(LivingEntity anchor, double horizontalRange, double minYOffset, double maxYOffset) {
        if (anchor == null || !anchor.isAlive()) return;

        double x = anchor.getX() + (this.getRandom().nextDouble() * 2.0D - 1.0D) * horizontalRange;
        double y = anchor.getY() + minYOffset + this.getRandom().nextDouble() * (maxYOffset - minYOffset);
        double z = anchor.getZ() + (this.getRandom().nextDouble() * 2.0D - 1.0D) * horizontalRange;
        this.snapTo(x, y, z, this.getYRot(), this.getXRot());
    }

    private void correctExcessiveVerticalDrift() {
        LivingEntity anchor = this.released ? this.getReleaseTarget() : this.getOrbitOwner();
        if (anchor == null || !anchor.isAlive()) anchor = this.getOrbitOwner();
        if (anchor == null || !anchor.isAlive()) return;

        double minY = anchor.getY() - 4.0D;
        double maxY = anchor.getY() + 5.0D;
        if (this.getY() >= minY && this.getY() <= maxY) return;

        this.setPos(this.getX(), Mth.clamp(this.getY(), minY, maxY), this.getZ());
        Vec3 movement = this.getDeltaMovement();
        this.setDeltaMovement(movement.x, 0.0D, movement.z);
    }

    public void processTeleportByPlayer() {
        if (this.player == null || !this.player.isAlive()) return;

        if (!this.released) {
            this.setTarget(null);
            this.teleportRandomlyAround(this.player, 4.0D, -2.0D, 2.0D);
            return;
        }

        LivingEntity target = this.findReleaseTarget();
        this.setReleaseTarget(target);
        if (target != null) this.teleportRandomlyAround(target, 4.0D, -2.0D, 2.0D);
    }

    public void processTeleportByNullEntity() {
        if (this.nullEntity == null || !this.nullEntity.isAlive()) return;

        if (!this.released) {
            this.teleportRandomlyAround(this.nullEntity, 4.0D, -2.0D, 2.0D);
            return;
        }

        LivingEntity target = this.findReleaseTarget();
        this.setReleaseTarget(target);
        if (target != null) this.teleportRandomlyAround(target, 4.0D, -2.0D, 2.0D);
    }

    public void summonNullWeaponForPlayer(String uuidNbt, ServerLevel serverLevel, Player summoner) {
        this.snapTo(summoner.getX() + new Random().nextDouble(-4, 4), summoner.getY() + new Random().nextDouble(-2, 2), summoner.getZ() + new Random().nextDouble(-4, 4));
        this.playerUUID = summoner.getUUID();
        this.player = summoner;
        this.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), EntitySpawnReason.MOB_SUMMONED, null);
        serverLevel.addFreshEntity(this);
        com.pla.annoyingvillagers.util.LegacyNbt.putUUID(summoner.getPersistentData(), uuidNbt, this.getUUID());
    }

    public void summonNullWeaponForNullEntity(ServerLevel serverLevel, NullEntity summoner, String toolName) {
        this.snapTo(summoner.getX() + new Random().nextDouble(-4, 4), summoner.getY() + new Random().nextDouble(-2, 2), summoner.getZ() + new Random().nextDouble(-4, 4));
        this.nullUUID = summoner.getUUID();
        this.nullEntity = summoner;
        this.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), EntitySpawnReason.MOB_SUMMONED, null);
        serverLevel.addFreshEntity(this);
        summoner.setNullWeapon(toolName, this);
        spinfor5seconds();
    }

    private String getPlayerTrackingKey() {
        return switch (this.weapon) {
            case "sword" -> "NullSwordUUID";
            case "pickaxe" -> "NullPickaxeUUID";
            case "axe" -> "NullAxeUUID";
            case "hoe" -> "NullHoeUUID";
            default -> "NullShovelUUID";
        };
    }

    private boolean reconcilePlayerTracking() {
        if (this.player == null || this.playerUUID == null) return true;
        String trackingKey = this.getPlayerTrackingKey();
        CompoundTag playerData = this.player.getPersistentData();
        if (!com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(playerData, trackingKey)) {
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(playerData, trackingKey, this.getUUID());
            return true;
        }
        return this.getUUID().equals(com.pla.annoyingvillagers.util.LegacyNbt.getUUID(playerData, trackingKey));
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        if (this.isSpinning() && !this.level().isClientSide()) this.setSpinning(false);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.player != null) {
                String trackingKey = this.getPlayerTrackingKey();
                CompoundTag playerData = this.player.getPersistentData();
                if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(playerData, trackingKey) && this.getUUID().equals(com.pla.annoyingvillagers.util.LegacyNbt.getUUID(playerData, trackingKey))) {
                    playerData.remove(trackingKey);
                }
            } else if (!this.suppressRemovalDrop) {
                var item = new ItemEntity(serverLevel, this.getX(), this.getY(), this.getZ(), this.getMainHandItem());
                item.setPickUpDelay(10);
                serverLevel.addFreshEntity(item);
            }
        }
        super.remove(pReason);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 2.0D);
        builder = builder.add(Attributes.MAX_HEALTH, 100.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 2.0D);
        return builder;
    }
}
