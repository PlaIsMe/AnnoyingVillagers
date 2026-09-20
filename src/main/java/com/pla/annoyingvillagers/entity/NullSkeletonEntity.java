package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.util.EnchantmentUtil;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class NullSkeletonEntity extends AbstractSkeleton {
    protected UUID nullUUID;
    protected NullEntity nullEntity;

    protected UUID playerUUID;
    protected Player player;

        public void setPlayer(Player player) {
        this.playerUUID = player.getUUID();
        this.player = player;
    }

    public NullEntity getNullEntity() {
        return nullEntity;
    }

    public void setNullEntity(NullEntity nullEntity) {
        this.nullUUID = nullEntity.getUUID();
        this.nullEntity = nullEntity;
    }

    public NullSkeletonEntity(EntityType<NullSkeletonEntity> entitytype, Level level) {
        super(entitytype, level);
        this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(2.0F);
        this.xpReward = 2;
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.LEGS, 0.0F);
        this.setDropChance(EquipmentSlot.FEET, 0.0F);
    }

        private boolean isOwner(LivingEntity livingEntity) {
        return livingEntity instanceof Player playerEntity && playerUUID != null && playerUUID.equals(playerEntity.getUUID());
    }

    private boolean validTarget(LivingEntity livingEntity) {
        return livingEntity != null && livingEntity.isAlive() && !isOwner(livingEntity);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return nullEntity != null && nullEntity.isAlive() && distanceTo(nullEntity) > (float)20.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (nullEntity != null && nullEntity.isAlive()) {
                    getNavigation().moveTo(nullEntity, 2.0D);
                    getLookControl().setLookAt(nullEntity, 30.0F, 30.0F);
                    if (distanceToSqr(nullEntity) > 20.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(nullEntity, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return nullEntity != null && nullEntity.isAlive() && distanceTo(nullEntity) > 50.0D;
            }
        });
        this.targetSelector.addGoal(1, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> validTarget(livingEntity)
                        && player != null && player.isAlive()
                        && player.getLastHurtByMob() == livingEntity
        ));
        this.targetSelector.addGoal(2, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> validTarget(livingEntity)
                        && player != null && player.isAlive()
                        && player.getLastHurtMob() == livingEntity
        ));

        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return player != null && player.isAlive() && distanceTo(player) > (float)20.0D * 0.9F;
            }

            @Override
            public void tick() {
                if (player != null && player.isAlive()) {
                    getNavigation().moveTo(player, 2.0D);
                    getLookControl().setLookAt(player, 30.0F, 30.0F);
                    if (distanceToSqr(player) > 20.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(player, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return player != null && player.isAlive() && distanceTo(player) > 50.0D;
            }
        });
        this.targetSelector.addGoal(1, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> validTarget(livingEntity)
                        && nullEntity != null && nullEntity.isAlive()
                        && nullEntity.getTarget() == livingEntity
        ));
        this.targetSelector.addGoal(1, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> validTarget(livingEntity)
                        && nullEntity != null && nullEntity.isAlive()
                        && nullEntity.getLastHurtByMob() == livingEntity
        ));
        this.targetSelector.addGoal(2, new com.pla.annoyingvillagers.util.LegacyNearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                livingEntity -> validTarget(livingEntity)
                        && nullEntity != null && nullEntity.isAlive()
                        && nullEntity.getLastHurtMob() == livingEntity
        ));
    }

        public double getMyRidingOffset() {
        return -0.35D;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    protected @NotNull SoundEvent getStepSound() {
        return SoundEvents.WITHER_SKELETON_STEP;
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull EntitySpawnReason mobSpawnType, @Nullable SpawnGroupData spawngroupdata) {
        if (this.nullEntity != null) {
            TeamUtil.addOrJoinTeam(this, "herobrine");
        }
        this.setCanPickUpLoot(true);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawngroupdata);
    }

    @Override
    public boolean doHurtTarget(net.minecraft.server.level.ServerLevel serverLevel, Entity pEntity) {
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
            }

            return flag;
        } else {
            return super.doHurtTarget(serverLevel, pEntity);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.tickCount == 1) {
                ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
                EnchantmentUtil.enchant(sword, Enchantments.FIRE_ASPECT, 1);
                EnchantmentUtil.enchant(sword, Enchantments.UNBREAKING, 1);
                EnchantmentUtil.enchant(sword, Enchantments.KNOCKBACK, 1);
                EnchantmentUtil.enchant(sword, Enchantments.SHARPNESS, 1);
                this.setItemSlot(EquipmentSlot.MAINHAND, sword);
                ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
                EnchantmentUtil.enchant(helmet, Enchantments.THORNS, 1);
                EnchantmentUtil.enchant(helmet, Enchantments.UNBREAKING, 1);
                EnchantmentUtil.enchant(helmet, Enchantments.PROTECTION, 1);
                this.setItemSlot(EquipmentSlot.HEAD, helmet);
            }
            if (nullEntity == null && nullUUID != null) {
                Entity entity = serverLevel.getEntity(nullUUID);
                if (entity instanceof NullEntity entityNull) {
                    nullEntity = entityNull;
                } else {
                    nullEntity = null;
                }
            }
            if (nullEntity != null && !nullEntity.isAlive()) {
                nullEntity = null;
                nullUUID = null;
                com.pla.annoyingvillagers.util.LegacyEntityOps.kill(this);
            }
            if (nullEntity != null && nullEntity.isAlive()) {
                double distanceSq = this.distanceToSqr(nullEntity);

                if (distanceSq > 600.0D) {
                    this.teleportTo(
                            nullEntity.getX(),
                            nullEntity.getY(),
                            nullEntity.getZ()
                    );
                }
            }

            if (player == null && playerUUID != null) {
                player = serverLevel.getPlayerByUUID(playerUUID);
            }
            if (player != null && !player.isAlive()) {
                player = null;
                playerUUID = null;
                com.pla.annoyingvillagers.util.LegacyEntityOps.kill(this);
            }
            if (player != null && player.isAlive()) {
                double distanceSq = this.distanceToSqr(player);

                if (distanceSq > 600.0D) {
                    this.teleportTo(
                            player.getX(),
                            player.getY(),
                            player.getZ()
                    );
                }
            }
        }
    }

    public boolean isOwnedBy(Player owner) {
        return owner != null && playerUUID != null && playerUUID.equals(owner.getUUID());
    }

    @Override
    public boolean hurtServer(net.minecraft.server.level.ServerLevel serverLevel, DamageSource pSource, float pAmount) {
        if (player != null && pSource.getEntity() == player) return false;
        if (nullEntity != null && pSource.getEntity() == nullEntity) return false;
        if (!pSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            float health = this.getHealth();
            if (health - pAmount <= 5.0F) {
                this.setHealth(0.0F);
                this.die(this.damageSources().fellOutOfWorld());
                return true;
            }
        }
        return super.hurtServer(serverLevel, pSource, pAmount);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
        CompoundTag tag = new CompoundTag();
        super.addAdditionalSaveData(output);
        if (nullUUID != null) {
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, "NullUUID", nullUUID);
        }
        if (playerUUID != null) {
            com.pla.annoyingvillagers.util.LegacyNbt.putUUID(tag, "PlayerUUID", playerUUID);
        }
    
        com.pla.annoyingvillagers.util.LegacyValueIO.write(output, tag);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        CompoundTag tag = com.pla.annoyingvillagers.util.LegacyValueIO.read(input);
        super.readAdditionalSaveData(input);
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(tag, "NullUUID")) {
            nullUUID = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(tag, "NullUUID");
        }
        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(tag, "PlayerUUID")) {
            playerUUID = com.pla.annoyingvillagers.util.LegacyNbt.getUUID(tag, "PlayerUUID");
        }
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35D);
        builder = builder.add(Attributes.MAX_HEALTH, 30.0D);
        builder = builder.add(Attributes.ARMOR, 10.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24.0D);
        return builder;
    }
}
