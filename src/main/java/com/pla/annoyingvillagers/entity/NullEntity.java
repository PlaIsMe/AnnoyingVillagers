package com.pla.annoyingvillagers.entity;

import java.util.*;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.procedures.NullOnHurtProcedure;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import net.corruptdog.cdm.api.animation.types.ExecutedAnimation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class NullEntity extends HerobrineMob {
    private NullWeapon nullSwordEntity;
    private UUID nullSwordUUID;
    private int witherSkeletonSummonCooldown = 0;

    private NullWeapon nullAxeEntity;
    private UUID nullAxeUUID;

    private NullWeapon nullPickaxeEntity;
    private UUID nullPickaxeUUID;

    private NullWeapon nullShovelEntity;
    private UUID nullShovelUUID;

    private NullWeapon nullHoeEntity;
    private UUID nullHoeUUID;

    private WitherSkeleton firstWitherSkeleton;
    private UUID firstWitherSkeletonUuid;

    private WitherSkeleton secondWitherSkeleton;
    private UUID secondWitherSkeletonUuid;

    private WitherSkeleton thirdWitherSkeleton;
    private UUID thirdWitherSkeletonUuid;

    final LivingEntityPatch<?> livingentitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);

    public boolean isAvailableWitherSkeletonSlot() {
        return firstWitherSkeletonUuid == null || secondWitherSkeletonUuid == null || thirdWitherSkeletonUuid == null;
    }

    public int getWitherSkeletonSummonCooldown() {
        return witherSkeletonSummonCooldown;
    }

    public void claimWitherSkeletonSlot(WitherSkeleton witherSkeleton) {
        if (firstWitherSkeletonUuid == null) {
            firstWitherSkeletonUuid = witherSkeleton.getUUID();
            firstWitherSkeleton = witherSkeleton;
        } else if (secondWitherSkeletonUuid == null) {
            secondWitherSkeletonUuid = witherSkeleton.getUUID();
            secondWitherSkeleton = witherSkeleton;
        } else {
            thirdWitherSkeletonUuid = witherSkeleton.getUUID();
            thirdWitherSkeleton = witherSkeleton;
        }
    }

    private boolean spawnNullWeapon = false;
    private boolean wasShooting = false;

    public NullWeapon getNullSwordEntity() {
        return nullSwordEntity;
    }

    public NullWeapon getNullAxeEntity() {
        return nullAxeEntity;
    }

    public NullWeapon getNullPickaxeEntity() {
        return nullPickaxeEntity;
    }

    public NullWeapon getNullShovelEntity() {
        return nullShovelEntity;
    }

    public NullWeapon getNullHoeEntity() {
        return nullHoeEntity;
    }

    public NullEntity(SpawnEntity spawnentity, Level level) {
        this((EntityType) AnnoyingVillagersModEntities.NULL.get(), level);
    }

    public NullEntity(EntityType<NullEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setChatName("§5Null§r");
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (nullSwordUUID != null) {
            tag.putUUID("NullSwordUUID", nullSwordUUID);
        }
        if (nullAxeUUID != null) {
            tag.putUUID("NullAxeUUID", nullAxeUUID);
        }
        if (nullPickaxeUUID != null) {
            tag.putUUID("NullPickaxeUUID", nullPickaxeUUID);
        }
        if (nullShovelUUID != null) {
            tag.putUUID("NullShovelUUID", nullShovelUUID);
        }
        if (nullHoeUUID != null) {
            tag.putUUID("NullHoeUUID", nullHoeUUID);
        }
        if (firstWitherSkeletonUuid != null) {
            tag.putUUID("FirstWitherSkeletonUuid", firstWitherSkeletonUuid);
        }
        if (secondWitherSkeletonUuid != null) {
            tag.putUUID("SecondWitherSkeletonUuid", secondWitherSkeletonUuid);
        }
        if (thirdWitherSkeletonUuid != null) {
            tag.putUUID("ThirdWitherSkeletonUuid", thirdWitherSkeletonUuid);
        }
        tag.putBoolean("SpawnNullWeapon", spawnNullWeapon);
        tag.putInt("WitherSkeletonSummonCooldown", witherSkeletonSummonCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("NullSwordUUID")) {
            nullSwordUUID = tag.getUUID("NullSwordUUID");
        }
        if (tag.hasUUID("NullAxeUUID")) {
            nullAxeUUID = tag.getUUID("NullAxeUUID");
        }
        if (tag.hasUUID("NullPickaxeUUID")) {
            nullPickaxeUUID = tag.getUUID("NullPickaxeUUID");
        }
        if (tag.hasUUID("NullShovelUUID")) {
            nullShovelUUID = tag.getUUID("NullShovelUUID");
        }
        if (tag.hasUUID("NullHoeUUID")) {
            nullHoeUUID = tag.getUUID("NullHoeUUID");
        }
        if (tag.hasUUID("FirstWitherSkeletonUuid")) {
            firstWitherSkeletonUuid = tag.getUUID("FirstWitherSkeletonUuid");
        }
        if (tag.hasUUID("SecondWitherSkeletonUuid")) {
            secondWitherSkeletonUuid = tag.getUUID("SecondWitherSkeletonUuid");
        }
        if (tag.hasUUID("ThirdWitherSkeletonUuid")) {
            thirdWitherSkeletonUuid = tag.getUUID("ThirdWitherSkeletonUuid");
        }
        spawnNullWeapon = tag.getBoolean("SpawnNullWeapon");
        witherSkeletonSummonCooldown = tag.getInt("WitherSkeletonSummonCooldown");
    }

    private void initialSpawn() {
        if (this.level() instanceof ServerLevel levelaccessor) {
            ServerLevel serverlevel = (ServerLevel) levelaccessor;

            NullWeapon nullSwordEntity = new NullSwordEntity((EntityType) AnnoyingVillagersModEntities.NULL_SWORD.get(), serverlevel);
            nullSwordEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullSwordEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullSwordEntity);
            this.nullSwordUUID = nullSwordEntity.getUUID();
            this.nullSwordEntity = nullSwordEntity;
            nullSwordEntity.setNullEntity(this);
            nullSwordEntity.setNullUUID(this.getUUID());

            NullWeapon nullAxeEntity = new NullAxeEntity((EntityType) AnnoyingVillagersModEntities.NULL_AXE.get(), serverlevel);
            nullAxeEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullAxeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullAxeEntity);
            this.nullAxeUUID = nullAxeEntity.getUUID();
            this.nullAxeEntity = nullAxeEntity;
            nullAxeEntity.setNullEntity(this);
            nullAxeEntity.setNullUUID(this.getUUID());

            NullWeapon nullPickaxeEntity = new NullPickaxeEntity((EntityType) AnnoyingVillagersModEntities.NULL_PICKAXE.get(), serverlevel);
            nullPickaxeEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullPickaxeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullPickaxeEntity);
            this.nullPickaxeUUID = nullPickaxeEntity.getUUID();
            this.nullPickaxeEntity = nullPickaxeEntity;
            nullPickaxeEntity.setNullEntity(this);
            nullPickaxeEntity.setNullUUID(this.getUUID());

            NullWeapon nullShovelEntity = new NullShovelEntity((EntityType) AnnoyingVillagersModEntities.NULL_SHOVEL.get(), serverlevel);
            nullShovelEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullShovelEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullShovelEntity);
            this.nullShovelUUID = nullShovelEntity.getUUID();
            this.nullShovelEntity = nullShovelEntity;
            nullShovelEntity.setNullEntity(this);
            nullShovelEntity.setNullUUID(this.getUUID());

            NullWeapon nullHoeEntity = new NullHoeEntity((EntityType) AnnoyingVillagersModEntities.NULL_HOE.get(), serverlevel);
            nullHoeEntity.moveTo(this.getX() + new Random().nextDouble(1.0D, 10.0D), this.getY() + new Random().nextDouble(1.0D, 10.0D), this.getZ() + new Random().nextDouble(1.0D, 10.0D), levelaccessor.getRandom().nextFloat() * 360.0F, 0.0F);
            nullHoeEntity.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null, (CompoundTag) null);
            levelaccessor.addFreshEntity(nullHoeEntity);
            this.nullHoeUUID = nullHoeEntity.getUUID();
            this.nullHoeEntity = nullHoeEntity;
            nullHoeEntity.setNullEntity(this);
            nullHoeEntity.setNullUUID(this.getUUID());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (!spawnNullWeapon) {
                this.spawnNullWeapon = true;
                initialSpawn();
            }
            if (nullSwordEntity == null && nullSwordUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullSwordUUID);
                if (entity instanceof NullWeapon nullSword) {
                    this.nullSwordEntity = nullSword;
                } else {
                    this.nullSwordUUID = null;
                }
            }
            if (nullAxeEntity == null && nullAxeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullAxeUUID);
                if (entity instanceof NullWeapon nullAxe) {
                    this.nullAxeEntity = nullAxe;
                } else {
                    this.nullAxeUUID = null;
                }
            }
            if (nullPickaxeEntity == null && nullPickaxeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullPickaxeUUID);
                if (entity instanceof NullWeapon nullPickaxe) {
                    this.nullPickaxeEntity = nullPickaxe;
                } else {
                    this.nullPickaxeUUID = null;
                }
            }
            if (nullShovelEntity == null && nullShovelUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullShovelUUID);
                if (entity instanceof NullWeapon nullShovel) {
                    this.nullShovelEntity = nullShovel;
                } else {
                    this.nullShovelUUID = null;
                }
            }
            if (nullHoeEntity == null && nullHoeUUID != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(nullHoeUUID);
                if (entity instanceof NullWeapon nullHoe) {
                    this.nullHoeEntity = nullHoe;
                } else {
                    nullHoeUUID = null;
                }
            }

            if (firstWitherSkeleton == null && firstWitherSkeletonUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(firstWitherSkeletonUuid);
                if (entity instanceof WitherSkeleton witherSkeleton) {
                    this.firstWitherSkeleton = witherSkeleton;
                } else {
                    this.firstWitherSkeletonUuid = null;
                }
            }
            if (secondWitherSkeleton == null && secondWitherSkeletonUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(secondWitherSkeletonUuid);
                if (entity instanceof WitherSkeleton witherSkeleton) {
                    this.secondWitherSkeleton = witherSkeleton;
                } else {
                    this.secondWitherSkeletonUuid = null;
                }
            }
            if (thirdWitherSkeleton == null && thirdWitherSkeletonUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(thirdWitherSkeletonUuid);
                if (entity instanceof WitherSkeleton witherSkeleton) {
                    this.thirdWitherSkeleton = witherSkeleton;
                } else {
                    this.thirdWitherSkeletonUuid = null;
                }
            }
            if (witherSkeletonSummonCooldown > 0) {
                witherSkeletonSummonCooldown = witherSkeletonSummonCooldown - 1;
            }
            if (firstWitherSkeleton != null && !firstWitherSkeleton.isAlive()) {
                firstWitherSkeleton = null;
                firstWitherSkeletonUuid = null;
                if (witherSkeletonSummonCooldown == 0) {
                    witherSkeletonSummonCooldown = 1200;
                }
            }
            if (secondWitherSkeleton != null && !secondWitherSkeleton.isAlive()) {
                secondWitherSkeleton = null;
                secondWitherSkeletonUuid = null;
                if (witherSkeletonSummonCooldown == 0) {
                    witherSkeletonSummonCooldown = 1200;
                }
            }
            if (thirdWitherSkeleton != null && !thirdWitherSkeleton.isAlive()) {
                thirdWitherSkeleton = null;
                thirdWitherSkeletonUuid = null;
                if (witherSkeletonSummonCooldown == 0) {
                    witherSkeletonSummonCooldown = 1200;
                }
            }

            String animId = currentEfAnimIdOrNull(this);
            boolean shootingNow = isShooting(animId);
            if (shootingNow && !wasShooting && this.getTarget() != null) {
                double d0 = this.getTarget().getX();
                double d1 = this.getTarget().getY();
                double d2 = this.getTarget().getZ();
                double chance = Math.random();
                if (chance <= 0.2D && this.nullSwordEntity != null && !this.nullSwordEntity.isReleased()) {
                    this.nullSwordEntity.releaseForAWhile();
                    this.nullSwordEntity.moveTo(d0, d1, d2);
                } else if (chance <= 0.4D && this.nullAxeEntity != null && !this.nullAxeEntity.isReleased()) {
                    this.nullAxeEntity.releaseForAWhile();
                    this.nullAxeEntity.moveTo(d0, d1, d2);
                } else if (chance <= 0.6D && this.nullPickaxeEntity != null && !this.nullPickaxeEntity.isReleased()) {
                    this.nullPickaxeEntity.releaseForAWhile();
                    this.nullPickaxeEntity.moveTo(d0, d1, d2);
                } else if (chance <= 0.8D && this.nullShovelEntity != null && !this.nullShovelEntity.isReleased()) {
                    this.nullShovelEntity.releaseForAWhile();
                    this.nullShovelEntity.moveTo(d0, d1, d2);
                } else if (this.nullHoeEntity != null && !this.nullHoeEntity.isReleased()) {
                    this.nullHoeEntity.releaseForAWhile();
                    this.nullHoeEntity.moveTo(d0, d1, d2);
                }
            }
            wasShooting = shootingNow;

            if (this.tickCount % 20 == 0) {
                DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
                if (this.nullSwordEntity != null) {
                   if (!this.nullSwordEntity.isReleased()) {
                        this.nullSwordEntity.moveTo(getRandomPosition(this.getX(), -4, 4), getRandomPosition(this.getY(), -2, 2), getRandomPosition(this.getZ(), -4, 4));
                    } else if (this.nullSwordEntity.isReleased()) {
                       if ((this.getTarget() != null && !(dynamicanimation instanceof LongHitAnimation) && dynamicanimation != Animations.BIPED_COMMON_NEUTRALIZED && dynamicanimation != Animations.BIPED_KNOCKDOWN && !(dynamicanimation instanceof ExecutedAnimation)) || livingentitypatch.isStunned()) {
                           this.nullSwordEntity.stopRelease();
                       } else {
                           LivingEntity target = this.getTarget();
                           if (target != null && target.isAlive()) {
                               if (this.isHealing()) {
                                   this.nullSwordEntity.stopRelease();
                               }
                               this.nullSwordEntity.moveTo(getRandomPosition(target.getX(), -4, 4), getRandomPosition(target.getY(), -2, 2), getRandomPosition(target.getZ(), -4, 4));
                           } else {
                               this.nullSwordEntity.stopRelease();
                           }
                       }
                    }
                }
                if (this.nullAxeEntity != null) {
                    if (!this.nullAxeEntity.isReleased()) {
                        this.nullAxeEntity.moveTo(getRandomPosition(this.getX(), -4, 4), getRandomPosition(this.getY(), -2, 2), getRandomPosition(this.getZ(), -4, 4));
                    } else if (this.nullAxeEntity.isReleased()) {
                        if ((this.getTarget() != null && !(dynamicanimation instanceof LongHitAnimation) && dynamicanimation != Animations.BIPED_COMMON_NEUTRALIZED && dynamicanimation != Animations.BIPED_KNOCKDOWN && !(dynamicanimation instanceof ExecutedAnimation)) || livingentitypatch.isStunned()) {
                            this.nullAxeEntity.stopRelease();
                        } else {
                            LivingEntity target = this.getTarget();
                            if (target != null && target.isAlive()) {
                                if (this.isHealing()) {
                                    this.nullAxeEntity.stopRelease();
                                }
                                this.nullAxeEntity.moveTo(getRandomPosition(target.getX(), -4, 4), getRandomPosition(target.getY(), -2, 2), getRandomPosition(target.getZ(), -4, 4));
                            } else {
                                this.nullAxeEntity.stopRelease();
                            }
                        }
                    }
                }
                if (this.nullPickaxeEntity != null) {
                    if (!this.nullPickaxeEntity.isReleased()) {
                        this.nullPickaxeEntity.moveTo(getRandomPosition(this.getX(), -4, 4), getRandomPosition(this.getY(), -2, 2), getRandomPosition(this.getZ(), -4, 4));
                    } else if (this.nullPickaxeEntity.isReleased()) {
                        if ((this.getTarget() != null && !(dynamicanimation instanceof LongHitAnimation) && dynamicanimation != Animations.BIPED_COMMON_NEUTRALIZED && dynamicanimation != Animations.BIPED_KNOCKDOWN && !(dynamicanimation instanceof ExecutedAnimation)) || livingentitypatch.isStunned()) {
                            this.nullPickaxeEntity.stopRelease();
                        } else {
                            LivingEntity target = this.getTarget();
                            if (target != null && target.isAlive()) {
                                if (this.isHealing()) {
                                    this.nullPickaxeEntity.stopRelease();
                                }
                                this.nullPickaxeEntity.moveTo(getRandomPosition(target.getX(), -4, 4), getRandomPosition(target.getY(), -2, 2), getRandomPosition(target.getZ(), -4, 4));
                            } else {
                                this.nullPickaxeEntity.stopRelease();
                            }
                        }
                    }
                }
                if (this.nullShovelEntity != null) {
                    if (!this.nullShovelEntity.isReleased()) {
                        this.nullShovelEntity.moveTo(getRandomPosition(this.getX(), -4, 4), getRandomPosition(this.getY(), -2, 2), getRandomPosition(this.getZ(), -4, 4));
                    } else if (this.nullShovelEntity.isReleased()) {
                        if (this.getTarget() != null && !(dynamicanimation instanceof LongHitAnimation) && dynamicanimation != Animations.BIPED_COMMON_NEUTRALIZED && dynamicanimation != Animations.BIPED_KNOCKDOWN && !(dynamicanimation instanceof ExecutedAnimation)) {
                            this.nullShovelEntity.stopRelease();
                        } else {
                            LivingEntity target = this.getTarget();
                            if (target != null && target.isAlive()) {
                                if (this.isHealing()) {
                                    this.nullShovelEntity.stopRelease();
                                }
                                this.nullShovelEntity.moveTo(getRandomPosition(target.getX(), -4, 4), getRandomPosition(target.getY(), -2, 2), getRandomPosition(target.getZ(), -4, 4));
                            } else {
                                this.nullShovelEntity.stopRelease();
                            }
                        }
                    }
                }
                if (this.nullHoeEntity != null) {
                    if (!this.nullHoeEntity.isReleased()) {
                        this.nullShovelEntity.moveTo(getRandomPosition(this.getX(), -4, 4), getRandomPosition(this.getY(), -2, 2), getRandomPosition(this.getZ(), -4, 4));
                    } else if (this.nullHoeEntity.isReleased()) {
                        if (this.getTarget() != null && !(dynamicanimation instanceof LongHitAnimation) && dynamicanimation != Animations.BIPED_COMMON_NEUTRALIZED && dynamicanimation != Animations.BIPED_KNOCKDOWN && !(dynamicanimation instanceof ExecutedAnimation)) {
                            this.nullHoeEntity.stopRelease();
                        } else {
                            LivingEntity target = this.getTarget();
                            if (target != null && target.isAlive()) {
                                if (this.isHealing()) {
                                    this.nullHoeEntity.stopRelease();
                                }
                                this.nullHoeEntity.moveTo(getRandomPosition(target.getX(), -4, 4), getRandomPosition(target.getY(), -2, 2), getRandomPosition(target.getZ(), -4, 4));
                            } else {
                                this.nullHoeEntity.stopRelease();
                            }
                        }
                    }
                }
            }
        }
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(24, new Goal() {
            {
                this.setFlags(EnumSet.of(Flag.MOVE));
            }

            public boolean canUse() {
                return NullEntity.this.getTarget() != null && !NullEntity.this.getMoveControl().hasWanted();
            }

            public boolean canContinueToUse() {
                return NullEntity.this.getMoveControl().hasWanted() && NullEntity.this.getTarget() != null && NullEntity.this.getTarget().isAlive();
            }

            public void start() {
                LivingEntity livingentity = NullEntity.this.getTarget();
                Vec3 vec3 = livingentity.getEyePosition(1.0F);

                NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }

            public void tick() {
                LivingEntity livingentity = NullEntity.this.getTarget();

                if (NullEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    NullEntity.this.doHurtTarget(livingentity);
                } else {
                    double d0 = NullEntity.this.distanceToSqr(livingentity);

                    if (d0 < 16.0D) {
                        Vec3 vec3 = livingentity.getEyePosition(1.0F);

                        NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 5.0D);
                    }
                }
            }
        });
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public @NotNull SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    public @NotNull SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damagesource) {
        return false;
    }

    private static String currentEfAnimIdOrNull(LivingEntity self) {
        try {
            var patch = EpicFightCapabilities
                    .getEntityPatch(self, LivingEntityPatch.class);
            if (patch == null) return null;

            var player = patch.getAnimator().getPlayerFor((DynamicAnimation) null);
            if (player == null) return null;

            var anim = player.getAnimation();
            if (anim == null) return null;
            try {
                var m = anim.getClass().getMethod("getLocation");
                var rl = (net.minecraft.resources.ResourceLocation) m.invoke(anim);
                return rl != null ? rl.getPath().toLowerCase(java.util.Locale.ROOT) : null;
            } catch (Exception ignored) {
                return anim.toString().toLowerCase(java.util.Locale.ROOT);
            }
        } catch (Throwable t) {
            return null;
        }
    }

    private static boolean isShooting(String id) {
        if (id == null) return false;
        return id.contains("biped/skill/antitheus_shoot") || id.endsWith("/antitheus_shoot") || id.contains("antitheus_shoot");
    }

    public boolean hurt(@NotNull DamageSource damagesource, float f) {
        if (!this.isHealing()) {
            NullOnHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, damagesource.getEntity());
        }
        if (damagesource.is(DamageTypes.FALL)) return false;
        if (damagesource.is(DamageTypes.CACTUS)) return false;
        if (damagesource.is(DamageTypes.WITHER)) return false;
        if (damagesource.is(DamageTypes.DROWN)) return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damagesource.is(DamageTypes.ON_FIRE)) return false;
        if (damagesource.is(DamageTypes.IN_FIRE)) return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow) return false;
        return super.hurt(damagesource, f);
    }

    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.nullSwordEntity != null) {
                this.nullSwordEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullAxeEntity != null) {
                this.nullAxeEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullHoeEntity != null) {
                this.nullHoeEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullShovelEntity != null) {
                this.nullShovelEntity.remove(RemovalReason.KILLED);
            }
            if (this.nullPickaxeEntity != null) {
                this.nullPickaxeEntity.remove(RemovalReason.KILLED);
            }

            InfectedPlayerMobEntity corpse = new InfectedPlayerMobEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_MOB.get(), serverLevel);
            corpse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            String killedName = this.getPersistentData().getString("killed_name");
            corpse.getPersistentData().putString("possessed_by", "null");
            if (killedName.isEmpty()) {
                killedName = String.valueOf(NameManager.INSTANCE.getRandomName());
            }
            corpse.setUsername(killedName);
            corpse.setCustomName(Component.literal(killedName));
            corpse.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()),
                    MobSpawnType.MOB_SUMMONED, null, null);
            this.setInvisible(true);
            this.remove(RemovalReason.KILLED);
            serverLevel.addFreshEntity(corpse);
        }
    }

    public void baseTick() {
        super.baseTick();
        if (!this.level().isClientSide() && this.getServer() != null) {
            try {
                this.getServer().getCommands().getDispatcher().execute(
                        "execute as @s at @s run particle annoyingvillagers:null ~ ~0.8 ~ 0.2 0.2 0.2 0.07 50 force",
                        this.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }
        DynamicAnimation dynamicanimation = livingentitypatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
        if (this.getTarget() != null && (!(dynamicanimation instanceof LongHitAnimation) && dynamicanimation != Animations.BIPED_COMMON_NEUTRALIZED && dynamicanimation != Animations.BIPED_KNOCKDOWN && !(dynamicanimation instanceof ExecutedAnimation) || !livingentitypatch.isStunned())) {
            this.setDeltaMovement(new Vec3(this.getLookAngle().x * 0.2D, this.getLookAngle().y * 0.2D, this.getLookAngle().z * 0.2D));
        }
    }

    private double getRandomPosition(double original, int min, int max) {
        return original + (double) new Random().nextDouble(min, max);
    }

    protected void checkFallDamage(double d0, boolean flag, BlockState blockstate, BlockPos blockpos) {}

    public void setNoGravity(boolean flag) {
        super.setNoGravity(true);
    }

    public void aiStep() {
        super.aiStep();
        this.setNoGravity(true);
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (this.level() instanceof ServerLevel serverLevel && pReason.equals(RemovalReason.DISCARDED)) {
            if (this.nullSwordEntity != null) {
                this.nullSwordEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullAxeEntity != null) {
                this.nullAxeEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullHoeEntity != null) {
                this.nullHoeEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullShovelEntity != null) {
                this.nullShovelEntity.remove(RemovalReason.DISCARDED);
            }
            if (this.nullPickaxeEntity != null) {
                this.nullPickaxeEntity.remove(RemovalReason.DISCARDED);
            }
        }
        super.remove(pReason);
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 3.0D);
        builder = builder.add(Attributes.MAX_HEALTH, 250.0D);
        builder = builder.add(Attributes.ARMOR, 40.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        builder = builder.add(Attributes.FLYING_SPEED, 3.0D);
        return builder;
    }
}
