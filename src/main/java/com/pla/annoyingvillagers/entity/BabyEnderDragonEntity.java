package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import java.util.UUID;

public class BabyEnderDragonEntity extends FlyingMob {
    private UUID followTargetUUID;
    private Player followTarget;
    private int breathCooldown = 0;
    private int lifeLeft = 600;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public void setFollowTarget(Player followTarget) {
        this.followTarget = followTarget;
    }

    public void setFollowTargetUUID(UUID followTargetUUID) {
        this.followTargetUUID = followTargetUUID;
    }

    public UUID getFollowTargetUUID() {
        return followTargetUUID;
    }

    public BabyEnderDragonEntity(PlayMessages.SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.BABY_ENDER_DRAGON.get(), level);
    }

    public BabyEnderDragonEntity(EntityType<BabyEnderDragonEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setNoGravity(true);
        this.setPersistenceRequired();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanPassDoors(true);
        nav.setCanFloat(true);
        return nav;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new Goal() {
            @Override
            public boolean canUse() {
                return followTarget != null && followTarget.isAlive();
            }

            @Override
            public void tick() {
                if (followTarget != null && followTarget.isAlive()) {
                    copyOwnerLook(followTarget);
                    Vec3 posBehind3D = posBehind3D(followTarget, 1.0D, 2.0D, 1.0D);
                    getNavigation().moveTo(posBehind3D.x, posBehind3D.y, posBehind3D.z, 9999.0D);
                }
            }

            @Override
            public boolean canContinueToUse() {
                return followTarget != null && followTarget.isAlive() && distanceTo(followTarget) > 1.0D;
            }
        });
    }

    @Override
    public boolean hurt(DamageSource damagesource, float f) {
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        if (damagesource.is(DamageTypes.PLAYER_ATTACK)) return false;
        if (damagesource.is(DamageTypes.THROWN)) return false;
        if (damagesource.is(DamageTypes.EXPLOSION)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.TRIDENT)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.FALLING_ANVIL)) return false;
        return super.hurt(damagesource, f);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.is(DamageTypes.FELL_OUT_OF_WORLD);
    }

    private static Vec3 posBehind3D(Player p, double back, double upFromGround, double right) {
        Vec3 look = p.getLookAngle().normalize();

        Vec3 forwardXZ = new Vec3(look.x, 0, look.z);
        if (forwardXZ.lengthSqr() < 1e-6) {
            float yaw = p.getYRot() * ((float)Math.PI/180F);
            forwardXZ = new Vec3(-Mth.sin(yaw), 0, Mth.cos(yaw));
        }
        forwardXZ = forwardXZ.normalize();

        Vec3 rightVec = new Vec3(-forwardXZ.z, 0, forwardXZ.x);

        double tx = p.getX() - forwardXZ.x * back + rightVec.x * right;
        double tz = p.getZ() - forwardXZ.z * back + rightVec.z * right;

        int blockY = Mth.floor(p.getY());
        double ty = blockY + upFromGround;
        if ( (blockY & 1) != 0 ) {
//            ty += 1.0D;
        }

        return new Vec3(tx, ty, tz);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BodyRotationControl(this) {
            @Override
            public void clientTick() {
                BabyEnderDragonEntity self = BabyEnderDragonEntity.this;
                self.yHeadRot = self.getYRot();
                self.yBodyRot = self.getYRot();
            }
        };
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.followTarget != null && this.followTarget.isAlive()) {
            copyOwnerLook(this.followTarget);
        }
    }

    public static LivingEntity getNearestLivingEntity(Level level, Entity sourceEntity, double range) {
        AABB searchBox = sourceEntity.getBoundingBox().inflate(range);

        return level.getNearestEntity(
                level.getEntitiesOfClass(LivingEntity.class, searchBox, e -> e != sourceEntity && !(e instanceof BabyEnderDragonEntity) && e.isAlive()),
                TargetingConditions.DEFAULT,
                (LivingEntity) sourceEntity,
                sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ()
        );
    }

    private void copyOwnerLook(Player owner) {
        float targetYaw   = owner.getYRot();
        float targetPitch = owner.getXRot() * 0.35F;

        this.setYRot(Mth.approachDegrees(this.getYRot(), targetYaw, 10.0F));
        this.setXRot(Mth.approachDegrees(this.getXRot(), targetPitch, 8.0F));

        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            this.setupAnimationStates();
        }
        if (!level().isClientSide) {
            if (--lifeLeft <= 0) {
                this.discard();
                return;
            }
            if (followTarget == null && followTargetUUID != null) {
                if (!(this.level() instanceof ServerLevel serverLevel)) return;
                var server = serverLevel.getServer();
                var serverPlayer = server.getPlayerList().getPlayer(this.followTargetUUID);

                if (serverPlayer != null) {
                    this.followTarget = serverPlayer;
                }
            }
            if (followTarget != null && !followTarget.isAlive()) {
                followTarget = null;
                followTargetUUID = null;
                this.discard();
            }
            if (followTarget != null && followTarget.isAlive()) {
                double distanceSq = this.distanceToSqr(followTarget);

                if (distanceSq > 4.0D) {
                    Vec3 posBehind3D = posBehind3D(followTarget, 1.0D, 2.0D, 1.0D);
                    this.teleportTo(
                            posBehind3D.x,
                            posBehind3D.y,
                            posBehind3D.z
                    );
                }

                if (breathCooldown <= 0) {
                    LivingEntity target;
                    target = followTarget.getLastHurtMob();
                    if (target == null || !target.isAlive()) {
                        target = followTarget.getLastHurtByMob();
                    }
                    if (target == null || !target.isAlive()) {
                        target = getNearestLivingEntity(followTarget.level(), followTarget, 30.0D);
                    }
                    if (target != null) {
                        Vec3 look = this.getLookAngle().normalize();
                        double dist = 0.5;
                        double sx = this.getX() + look.x * dist;
                        double sy = this.getEyeY();
                        double sz = this.getZ() + look.z * dist;

                        BabyDragonBeamEntity beam = new BabyDragonBeamEntity(
                                AnnoyingVillagersModEntities.BABY_DRAGON_BEAM.get(),
                                level(),
                                this,
                                target,
                                sx, sy, sz,
                                100, 2
                        );
                        level().addFreshEntity(beam);
                        breathCooldown = 120;
                    } else {
                    }
                }
            }
            if (breathCooldown > 0) breathCooldown--;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (followTargetUUID != null) {
            tag.putUUID("FollowTarget", followTargetUUID);
            tag.putInt("BreathCooldown", breathCooldown);
            tag.putInt("LifeLeft", lifeLeft);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("FollowTarget")) {
            followTargetUUID = tag.getUUID("FollowTarget");
        }
        breathCooldown = tag.getInt("BreathCooldown");
        lifeLeft = tag.contains("LifeLeft") ? tag.getInt("LifeLeft") : 600;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MAX_HEALTH, 150.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 3.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24.0D);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.0D);
        return builder;
    }
}
