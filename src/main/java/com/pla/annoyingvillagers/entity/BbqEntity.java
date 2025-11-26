package com.pla.annoyingvillagers.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;


public class BbqEntity extends PathfinderMob implements RangedAttackMob {
    private UUID followTargetUUID;
    private Entity followTarget;

    public void setFollowTarget(Entity followTarget) {
        this.followTarget = followTarget;
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public BbqEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BBQ.get(), level);
    }

    public BbqEntity(EntityType<BbqEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(0.6F);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomName(this.getDisplayName());
        this.setCustomNameVisible(true);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (!this.level().isClientSide) {
            ThrownPoisonEggEntity projectile = new ThrownPoisonEggEntity(
                    AnnoyingVillagersModEntities.THROWN_POISON_EGG.get(), this, this.level()
            );
            double dX = target.getX() - this.getX();
            double dY = target.getEyeY() - projectile.getY();
            double dZ = target.getZ() - this.getZ();

            projectile.setOwner(this);
            projectile.setPos(this.getX(), this.getEyeY() - 0.1D, this.getZ());
            projectile.shoot(dX, dY, dZ, 1.5F, 8.0F); // power and inaccuracy

            this.level().addFreshEntity(projectile);
        }
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        this.goalSelector.addGoal(1, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive();
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    getNavigation().moveTo(followTarget, 2.0D);
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 10.0D;
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, HerobrineCloneEntity.class, false, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, ShadowHerobrineCloneEntity.class, false, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, LowHerobrineCloneEntity.class, false, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, HerobrineChrisEntity.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, (target) -> followTarget != null
                && followTarget.isAlive()
                && target != null
                && target.getLastHurtMob() == followTarget));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, VillagerScoutEntity.class, false, false));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, RedVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, BlueVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, GreenVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, PurpleVillagerGeneralEntity.class, false, false));
        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal(this, VillagerScoutCaptainEntity.class, false, false));
        this.targetSelector.addGoal(11, new NearestAttackableTargetGoal(this, Player.class, false, false));
        this.targetSelector.addGoal(14, new NearestAttackableTargetGoal(this, SteveEntity.class, false, false));
        this.targetSelector.addGoal(19, new NearestAttackableTargetGoal(this, Monster.class, false, (target) -> !((target instanceof BlueDemonEntity) || (target instanceof BlueDemon2Entity))));
        this.targetSelector.addGoal(20, new NearestAttackableTargetGoal(this, Steve2Entity.class, false, false));
        this.goalSelector.addGoal(23, new RangedAttackGoal(this, 1.25D, 20, 15.0F));
        this.goalSelector.addGoal(24, new MeleeAttackGoal(this, 1.5D, false) {
            protected double getAttackReachSqr(LivingEntity livingentity) {
                return (double) (this.mob.getBbWidth() * this.mob.getBbWidth() + livingentity.getBbWidth());
            }
        });
        this.targetSelector.addGoal(25, new NearestAttackableTargetGoal(this, HerobrineChrisEntity.class, false, false));
        this.targetSelector.addGoal(26, new NearestAttackableTargetGoal(this, LowShadowHerobrineCloneEntity.class, false, false));
        this.goalSelector.addGoal(27, new RandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(28, new HurtByTargetGoal(this, new Class[0]));
        this.goalSelector.addGoal(29, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(30, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(31, new OpenDoorGoal(this, false));
        this.goalSelector.addGoal(32, new FloatGoal(this));
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData spawngroupdata1 = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team add blue_demon",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team modify blue_demon friendlyFire false",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "team join blue_demon @s",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {
            }
        }
        return spawngroupdata1;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (followTargetUUID != null) {
            tag.putUUID("FollowTargetUUID", followTargetUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("FollowTargetUUID")) {
            followTargetUUID = tag.getUUID("FollowTargetUUID");
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (followTarget == null && followTargetUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(followTargetUUID);
                if (entity instanceof Monster monster) {
                    followTarget = monster;
                } else {
                    followTargetUUID = null;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                followTarget = null;
                followTargetUUID = null;
            }
            if (followTarget != null && followTarget.isAlive()) {
                double distanceSq = this.distanceToSqr(followTarget);

                if (distanceSq > 600.0D) {
                    this.teleportTo(
                            followTarget.getX(),
                            followTarget.getY(),
                            followTarget.getZ()
                    );
                }
            }
        }
    }

    public boolean hurt(DamageSource damagesource, float f) {
        Level level = this.level();
        if (!level.isClientSide()) {
            ThrownPoisonEggEntity projectile = new ThrownPoisonEggEntity(
                    AnnoyingVillagersModEntities.THROWN_POISON_EGG.get(), this, level
            );
            projectile.setOwner(this);
            projectile.setPos(this.getX(), this.getEyeY() - 0.1D, this.getZ());
            projectile.shoot(this.getLookAngle().x, this.getLookAngle().y, this.getLookAngle().z, 2.0F, 0.0F);
            level.addFreshEntity(projectile);
        }
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.FALLING_ANVIL)) return false;
        return super.hurt(damagesource, f);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27D);
        builder = builder.add(Attributes.MAX_HEALTH, 150.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 7.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
        return builder;
    }
}
