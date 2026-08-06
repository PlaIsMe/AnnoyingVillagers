package com.pla.annoyingvillagers.clazz;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.entity.goal.PortalApproachGoal;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.NullWeaponItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import reascer.wom.world.entity.mob.EnderHand;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class NullWeapon extends Monster {
    protected UUID nullUUID;
    protected NullEntity nullEntity;

    protected UUID playerUUID;
    protected Player player;

    protected String weapon;
    private boolean spinning = false;

    private int releaseCooldown = 0;

    protected boolean released = false;

    public boolean isReleased() {
        return released;
    }

    public void stopRelease() {
        this.releaseCooldown = 1;
    }

    public void releaseForAWhile() {
        this.releaseCooldown = new Random().nextInt(300, 600);
        this.released = true;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setReleased(boolean released) {
        this.released = released;
        if (released) {
            spinfor5seconds();
        }
    }

    public void spinfor5seconds() {
        final LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.playAnimationSynchronized(AnimsWom.GLOWING_AGONY_GUARD, 0.0F);
            new DelayedTask(100) {
                @Override
                public void run() {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                }
            };
        }
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

    protected NullWeapon(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(4.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new FlyingPathNavigation(this, level);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this,
                LivingEntity.class,
                10, true, false,
                target -> {
                    if (this.player == null || !this.player.isAlive()) return false;
                    var lastHurtBy = this.player.getLastHurtByMob();
                    var lastHurt = this.player.getLastHurtMob();
                    return (target == lastHurtBy || target == lastHurt)
                            && target.isAlive()
                            && !target.isAlliedTo(this.player);
                }
        ));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                && nullEntity.isAlive()
                && target != null
                && nullEntity.getTarget() == target));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> nullEntity != null
                && nullEntity.isAlive()
                && target != null
                && target.getLastHurtMob() == nullEntity));
        this.goalSelector.addGoal(2, new PortalApproachGoal(this));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.4D, 20) {
            protected Vec3 getPosition() {
                Random random = new Random();
                double d0 = NullWeapon.this.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double d1 = NullWeapon.this.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F));
                double d2 = NullWeapon.this.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F));

                return new Vec3(d0, d1, d2);
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, NullEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(6, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            public boolean canUse() {
                return NullWeapon.this.getTarget() != null && !NullWeapon.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return NullWeapon.this.getMoveControl().hasWanted() && NullWeapon.this.getTarget() != null && NullWeapon.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = NullWeapon.this.getTarget();
                if (livingentity == null) return;
                Vec3 vec3 = livingentity.getEyePosition(1.0F);

                NullWeapon.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
            }

            public void tick() {
                LivingEntity livingentity = NullWeapon.this.getTarget();
                if (livingentity == null) return;
                if (NullWeapon.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    NullWeapon.this.doHurtTarget(livingentity);
                } else {
                    double d0 = NullWeapon.this.distanceToSqr(livingentity);

                    if (d0 < 16.0D) {
                        Vec3 vec3 = livingentity.getEyePosition(1.0F);

                        NullWeapon.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 2.0D);
                    }
                }

            }
        });
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Weapon", weapon);
        if (nullUUID != null) {
            tag.putUUID("NullUUID", nullUUID);
        }
        if (playerUUID != null) {
            tag.putUUID("OwnerUUID", playerUUID);
        }
        tag.putBoolean("Released", released);
        tag.putInt("ReleaseCooldown", releaseCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("NullUUID")) {
            nullUUID = tag.getUUID("NullUUID");
        }
        if (tag.hasUUID("OwnerUUID")) {
            playerUUID = tag.getUUID("OwnerUUID");
        }
        weapon = tag.getString("Weapon");
        released = tag.getBoolean("Released");
        releaseCooldown = tag.getInt("ReleaseCooldown");
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource damagesource) {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("", "")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("", "")));
    }

    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damagesource) {
        return false;
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        return false;
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverlevelaccessor, @NotNull DifficultyInstance difficultyinstance, @NotNull MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        TeamUtil.addOrJoinTeam(this, "herobrine");
        this.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        this.setInvulnerable(true);
        return super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
    }

    protected void checkFallDamage(double d0, boolean flag, @NotNull BlockState blockstate, @NotNull BlockPos blockpos) {}

    public void setNoGravity(boolean flag) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    public void increaseSkillPoint(Entity entity, float value) {
        if (entity instanceof Player player) {
            NullWeaponItem.addCharge(player, value);
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
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
                f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)pEntity).getMobType());
                f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
            }

            int i = EnchantmentHelper.getFireAspect(this);
            if (i > 0) {
                pEntity.setSecondsOnFire(i * 4);
            }

            boolean flag = pEntity.hurt(this.damageSources().playerAttack(this.player), f);
            increaseSkillPoint(this.player, 5.0F);
            if (flag) {
                if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                    ((LivingEntity)pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0F, 0.6));
                }
                this.doEnchantDamageEffects(this, pEntity);
                this.setLastHurtMob(pEntity);
            }

            return flag;
        } else if (this.nullEntity != null) {
            float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
            if (pEntity instanceof LivingEntity) {
                f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)pEntity).getMobType());
                f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
            }

            int i = EnchantmentHelper.getFireAspect(this);
            if (i > 0) {
                pEntity.setSecondsOnFire(i * 4);
            }

            boolean flag = pEntity.hurt(this.damageSources().mobAttack(this.nullEntity), f);
            if (flag) {
                if (f1 > 0.0F && pEntity instanceof LivingEntity) {
                    ((LivingEntity)pEntity).knockback(f1 * 0.5F, Mth.sin(this.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180F)));
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0F, 0.6));
                }
                this.doEnchantDamageEffects(this, pEntity);
                this.setLastHurtMob(pEntity);
            }

            return flag;
        } else {
            return super.doHurtTarget(pEntity);
        }
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
        if (this.getItemBySlot(EquipmentSlot.OFFHAND) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.HEAD) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.CHEST) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.LEGS) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        }
        if (this.getItemBySlot(EquipmentSlot.FEET) != ItemStack.EMPTY) {
            this.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        }
        if (!level().isClientSide) {
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
                    this.nullEntity = entityNull;
                } else {
                    this.nullEntity = null;
                }
            }
            if (nullEntity != null && !nullEntity.isAlive()) {
                nullEntity = null;
                nullUUID = null;
            }
            if (player == null && playerUUID != null) {
                this.player = level().getPlayerByUUID(playerUUID);
            }
            if (player != null && !player.isAlive()) {
                this.remove(RemovalReason.KILLED);
            }
            if (player != null && player.isAlive()) {
                if (!NullWeaponItem.isHeldBy(player) || !NullWeaponItem.isTrackedByOwner(player, this)) {
                    this.remove(RemovalReason.KILLED);
                } else {
                    updatePlayerOwnedTarget();
                }
            }
        }

        if (this.getTarget() == null && this.released) {
            this.released = false;
        }

        if (this.releaseCooldown > 0) {
            this.releaseCooldown = this.releaseCooldown - 1;
        }
        if (this.releaseCooldown == 0 && this.nullEntity != null && this.released) {
            this.released = false;
        }
    }

    private void updatePlayerOwnedTarget() {
        LivingEntity currentTarget = this.getTarget();
        if (currentTarget != null && currentTarget.isAlive()) {
            return;
        }

        LivingEntity nearbyTarget = NullWeapon.getNearestLivingEntity(player.level(), player, 12.0D);
        if (nearbyTarget != null) {
            this.setTarget(nearbyTarget);
        }
    }

    public static LivingEntity getNearestLivingEntity(Level level, Entity sourceEntity, double range) {
        AABB searchBox = sourceEntity.getBoundingBox().inflate(range);

        return level.getNearestEntity(
                level.getEntitiesOfClass(LivingEntity.class, searchBox,
                        e -> e != sourceEntity
                                && !(e instanceof NullWeapon)
                                && !(e instanceof EnderHand)
                                && !e.isAlliedTo(sourceEntity)
                                && e.isAlive()),
                TargetingConditions.DEFAULT,
                (LivingEntity) sourceEntity,
                sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ()
        );
    }

    public void processTeleportByPlayer() {
        if (player == null) return;
        if (!this.isReleased()) {
            this.moveTo(player.getX() + new Random().nextDouble(-4, 4), player.getY() + new Random().nextDouble(-2, 2), player.getZ() + new Random().nextDouble(-4, 4));
        } else if (this.isReleased() && (player.getLastHurtByMob() != null || player.getLastHurtMob() != null)) {
            LivingEntity target = player.getLastHurtByMob() != null ? player.getLastHurtByMob() : (player.getLastHurtMob() != null ? player.getLastHurtMob() : null);
            if (target == null) {
                target = NullWeapon.getNearestLivingEntity(player.level(), player, 12.0D);
            }
            if (target != null && target.isAlive()) {
                this.moveTo(target.getX() + new Random().nextDouble(-4, 4), target.getY() + new Random().nextDouble(-2, 2), target.getZ() + new Random().nextDouble(-4, 4));
            } else {
                this.released = false;
            }
        }
    }

    public void processTeleportByNullEntity() {
        if (nullEntity == null) return;
        if (!this.isReleased()) {
            this.moveTo(nullEntity.getX() + new Random().nextDouble(-4, 4), nullEntity.getY() + new Random().nextDouble(-2, 2), nullEntity.getZ() + new Random().nextDouble(-4, 4));
        } else if (this.isReleased() && (nullEntity.getLastHurtByMob() != null || nullEntity.getLastHurtMob() != null)) {
            LivingEntity target = nullEntity.getTarget() != null ? nullEntity.getTarget() : (nullEntity.getLastHurtByMob() != null ? nullEntity.getLastHurtByMob() : (nullEntity.getLastHurtMob() != null ? nullEntity.getLastHurtMob() : null));
            if (target == null) {
                target = NullWeapon.getNearestLivingEntity(nullEntity.level(), nullEntity, 12.0D);
            }
            if (target != null && target.isAlive()) {
                this.moveTo(target.getX() + new Random().nextDouble(-4, 4), target.getY() + new Random().nextDouble(-2, 2), target.getZ() + new Random().nextDouble(-4, 4));
            } else {
                this.stopRelease();
            }
        }
    }

    public void summonNullWeaponForPlayer(String uuidNbt, ServerLevel serverLevel, Player summoner) {
        this.moveTo(summoner.getX() + new Random().nextDouble(-4, 4), summoner.getY() + new Random().nextDouble(-2, 2), summoner.getZ() + new Random().nextDouble(-4, 4));
        this.playerUUID = summoner.getUUID();
        this.player = summoner;
        this.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        serverLevel.addFreshEntity(this);
        summoner.getPersistentData().putUUID(uuidNbt, this.getUUID());
    }

    public void summonNullWeaponForNullEntity(ServerLevel serverLevel, NullEntity summoner, String toolName) {
        this.moveTo(summoner.getX() + new Random().nextDouble(-4, 4), summoner.getY() + new Random().nextDouble(-2, 2), summoner.getZ() + new Random().nextDouble(-4, 4));
        this.nullUUID = summoner.getUUID();
        this.nullEntity = summoner;
        this.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        serverLevel.addFreshEntity(this);
        summoner.setNullWeapon(toolName, this);
        spinfor5seconds();
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.player != null) {
                switch (this.weapon) {
                    case "sword" -> {
                        this.player.getPersistentData().remove("NullSwordUUID");
                    }
                    case "pickaxe" -> {
                        this.player.getPersistentData().remove("NullPickaxeUUID");
                    }
                    case "axe" -> {
                        this.player.getPersistentData().remove("NullAxeUUID");
                    }
                    case "hoe" -> {
                        this.player.getPersistentData().remove("NullHoeUUID");
                    }
                    default -> {
                        this.player.getPersistentData().remove("NullShovelUUID");
                    }
                }
            } else {
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
