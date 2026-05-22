package com.pla.annoyingvillagers.entity;

import java.util.*;

import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import se.gory_moon.player_mobs.utils.NameManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class NullEntity extends HerobrineMob {
    private NullWeapon nullSwordEntity;
    private UUID nullSwordUUID;

    private NullWeapon nullAxeEntity;
    private UUID nullAxeUUID;

    private NullWeapon nullPickaxeEntity;
    private UUID nullPickaxeUUID;

    private NullWeapon nullShovelEntity;
    private UUID nullShovelUUID;

    private NullWeapon nullHoeEntity;
    private UUID nullHoeUUID;

    private NullSkeletonEntity firstWitherSkeleton;
    private UUID firstWitherSkeletonUuid;

    private NullSkeletonEntity secondWitherSkeleton;
    private UUID secondWitherSkeletonUuid;

    public boolean isAvailableWitherSkeletonSlot() {
        return firstWitherSkeletonUuid == null || secondWitherSkeletonUuid == null;
    }

    public NullSkeletonEntity getFirstWitherSkeleton() {
        return firstWitherSkeleton;
    }

    public NullSkeletonEntity getSecondWitherSkeleton() {
        return secondWitherSkeleton;
    }

    public void claimWitherSkeletonSlot(NullSkeletonEntity witherSkeleton) {
        if (firstWitherSkeletonUuid == null) {
            firstWitherSkeletonUuid = witherSkeleton.getUUID();
            firstWitherSkeleton = witherSkeleton;
        } else {
            secondWitherSkeletonUuid = witherSkeleton.getUUID();
            secondWitherSkeleton = witherSkeleton;
        }
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        return AnnoyingVillagersModSounds.NULL_SAY.get();
    }

    private boolean spawnNullWeapon = false;

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

    public void setNullWeapon(String slot, NullWeapon nullWeapon) {
        switch (slot) {
            case "sword" -> {
                this.nullSwordUUID = nullWeapon.getUUID();
                this.nullSwordEntity = nullWeapon;
            }
            case "pickaxe" -> {
                this.nullPickaxeUUID = nullWeapon.getUUID();
                this.nullPickaxeEntity = nullWeapon;
            }
            case "axe" -> {
                this.nullAxeUUID = nullWeapon.getUUID();
                this.nullAxeEntity = nullWeapon;
            }
            case "hoe" -> {
                this.nullHoeUUID = nullWeapon.getUUID();
                this.nullHoeEntity = nullWeapon;
            }
            default -> {
                this.nullShovelUUID = nullWeapon.getUUID();
                this.nullShovelEntity = nullWeapon;
            }
        }
    }

    public NullEntity(SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.NULL.get(), level);
    }

    public NullEntity(EntityType<NullEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(3.0F);
        this.xpReward = 80;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setChatName("§5Null§r");
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.NULL_WEAPON.get()));
    }

    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new FlyingPathNavigation(this, level);
    }

    public void releaseRandomWeapons(int stack) {
        if (stack <= 0) return;
        List<NullWeapon> weapons = new ArrayList<>(5);

        if (this.nullSwordEntity != null) weapons.add(this.nullSwordEntity);
        if (this.nullAxeEntity != null) weapons.add(this.nullAxeEntity);
        if (this.nullPickaxeEntity != null) weapons.add(this.nullPickaxeEntity);
        if (this.nullShovelEntity != null) weapons.add(this.nullShovelEntity);
        if (this.nullHoeEntity != null) weapons.add(this.nullHoeEntity);

        if (weapons.isEmpty()) return;
        Collections.shuffle(weapons, new Random());
        for (int i = 0; i < Math.min(stack, weapons.size()); i++) {
            weapons.get(i).releaseForAWhile();
        }
    }

    public void randomlyParryWithWeapon(ServerLevel serverLevel, Entity attacker) {
        List<NullWeapon> weapons = new ArrayList<>(5);
        if (this.nullSwordEntity != null && !this.nullSwordEntity.isReleased()) weapons.add(this.nullSwordEntity);
        if (this.nullAxeEntity != null && !this.nullAxeEntity.isReleased()) weapons.add(this.nullAxeEntity);
        if (this.nullPickaxeEntity != null && !this.nullPickaxeEntity.isReleased()) weapons.add(this.nullPickaxeEntity);
        if (this.nullShovelEntity != null && !this.nullShovelEntity.isReleased()) weapons.add(this.nullShovelEntity);
        if (this.nullHoeEntity != null && !this.nullHoeEntity.isReleased()) weapons.add(this.nullHoeEntity);

        if (weapons.isEmpty()) return;
        NullWeapon chosen = weapons.get(this.getRandom().nextInt(weapons.size()));
        EpicfightUtil.damageBlockedForce(chosen, attacker, serverLevel);
        chosen.moveTo(this.getX(), this.getY(), this.getZ());
        chosen.spinfor5seconds();
    }


    public void setSpinningToAllWeaponsAvailable(boolean spinning) {
        setSpinningIfAvailable(this.nullSwordEntity, spinning);
        setSpinningIfAvailable(this.nullAxeEntity, spinning);
        setSpinningIfAvailable(this.nullPickaxeEntity, spinning);
        setSpinningIfAvailable(this.nullShovelEntity, spinning);
        setSpinningIfAvailable(this.nullHoeEntity, spinning);
    }

    public void setSpinningToAllWeaponsAvailableFor5seconds() {
        setSpinningFor5SecondsIfAvailable(this.nullSwordEntity);
        setSpinningFor5SecondsIfAvailable(this.nullAxeEntity);
        setSpinningFor5SecondsIfAvailable(this.nullPickaxeEntity);
        setSpinningFor5SecondsIfAvailable(this.nullShovelEntity);
        setSpinningFor5SecondsIfAvailable(this.nullHoeEntity);
    }

    private static void setSpinningIfAvailable(NullWeapon weapon, boolean spinning) {
        if (weapon == null) return;
        if (weapon.isReleased()) return;
        weapon.setSpinning(spinning);
    }

    private static void setSpinningFor5SecondsIfAvailable(NullWeapon weapon) {
        if (weapon == null) return;
        if (weapon.isReleased()) weapon.stopRelease();
        weapon.spinfor5seconds();
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
        tag.putBoolean("SpawnNullWeapon", spawnNullWeapon);
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
        spawnNullWeapon = tag.getBoolean("SpawnNullWeapon");
    }

    private void initialSpawn() {
        if (this.level() instanceof ServerLevel serverLevel) {
            NullWeapon nullSwordEntity = new NullSwordEntity(AnnoyingVillagersModEntities.NULL_SWORD.get(), serverLevel);
            nullSwordEntity.summonNullWeaponForNullEntity(serverLevel, this, "sword");

            NullWeapon nullAxeEntity = new NullAxeEntity(AnnoyingVillagersModEntities.NULL_AXE.get(), serverLevel);
            nullAxeEntity.summonNullWeaponForNullEntity(serverLevel, this, "axe");

            NullWeapon nullPickaxeEntity = new NullPickaxeEntity(AnnoyingVillagersModEntities.NULL_PICKAXE.get(), serverLevel);
            nullPickaxeEntity.summonNullWeaponForNullEntity(serverLevel, this, "pickaxe");

            NullWeapon nullShovelEntity = new NullShovelEntity(AnnoyingVillagersModEntities.NULL_SHOVEL.get(), serverLevel);
            nullShovelEntity.summonNullWeaponForNullEntity(serverLevel, this, "shovel");

            NullWeapon nullHoeEntity = new NullHoeEntity(AnnoyingVillagersModEntities.NULL_HOE.get(), serverLevel);
            nullHoeEntity.summonNullWeaponForNullEntity(serverLevel, this, "hoe");
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (!spawnNullWeapon) {
                this.spawnNullWeapon = true;
                initialSpawn();
            } else if (this.tickCount == 20 && this.getLivingEntityPatch() != null) {
                this.getLivingEntityPatch().playAnimationSynchronized(AnimsWom.CLONE_ANTITHEUS_ASCENSION, 0.0F);
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
                if (entity instanceof NullSkeletonEntity witherSkeleton) {
                    this.firstWitherSkeleton = witherSkeleton;
                } else {
                    this.firstWitherSkeletonUuid = null;
                }
            }
            if (secondWitherSkeleton == null && secondWitherSkeletonUuid != null) {
                Entity entity = ((ServerLevel) this.level()).getEntity(secondWitherSkeletonUuid);
                if (entity instanceof NullSkeletonEntity witherSkeleton) {
                    this.secondWitherSkeleton = witherSkeleton;
                } else {
                    this.secondWitherSkeletonUuid = null;
                }
            }

            if (firstWitherSkeleton != null && !firstWitherSkeleton.isAlive()) {
                firstWitherSkeleton = null;
                firstWitherSkeletonUuid = null;
            }
            if (secondWitherSkeleton != null && !secondWitherSkeleton.isAlive()) {
                secondWitherSkeleton = null;
                secondWitherSkeletonUuid = null;
            }

            if (this.tickCount % 10 == 0 && this.tickCount >= 20) {
                if (nullSwordEntity != null) {
                    nullSwordEntity.processTeleportByNullEntity();
                }
                if (nullAxeEntity != null) {
                    nullAxeEntity.processTeleportByNullEntity();
                }
                if (nullPickaxeEntity != null) {
                    nullPickaxeEntity.processTeleportByNullEntity();
                }
                if (nullHoeEntity != null) {
                    nullHoeEntity.processTeleportByNullEntity();
                }
                if (nullShovelEntity != null) {
                    nullShovelEntity.processTeleportByNullEntity();
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
                LivingEntity livingEntity = NullEntity.this.getTarget();
                if (livingEntity != null) {
                    Vec3 vec3 = livingEntity.getEyePosition(1.0F);
                    NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                }
            }

            public void tick() {
                LivingEntity livingEntity = NullEntity.this.getTarget();

                if (livingEntity != null) {
                    if (NullEntity.this.getBoundingBox().intersects(livingEntity.getBoundingBox())) {
                        NullEntity.this.doHurtTarget(livingEntity);
                    } else {
                        double d0 = NullEntity.this.distanceToSqr(livingEntity);
                        if (d0 < 16.0D) {
                            Vec3 vec3 = livingEntity.getEyePosition(1.0F);
                            NullEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 5.0D);
                        }
                    }
                }
            }
        });
    }

    public boolean causeFallDamage(float f, float f1, @NotNull DamageSource damagesource) {
        return false;
    }

    public boolean hurt(@NotNull DamageSource damageSource, float f) {
        if (damageSource.is(DamageTypes.FALL)) return false;
        if (damageSource.is(DamageTypes.CACTUS)) return false;
        if (damageSource.is(DamageTypes.WITHER)) return false;
        if (damageSource.is(DamageTypes.DROWN)) return false;
        if (damageSource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damageSource.is(DamageTypes.DRAGON_BREATH)) return false;
        if (damageSource.is(DamageTypes.ON_FIRE)) return false;
        if (damageSource.is(DamageTypes.IN_FIRE)) return false;
        if (!(damageSource.getDirectEntity() instanceof EnchantedArrowEntity)
                && damageSource.getDirectEntity() instanceof AbstractArrow
                && !(damageSource.getDirectEntity() instanceof BlueDemonThrownTridentEntity)) return false;
        if (new Random().nextFloat() <= (this.getState() == 2 ? 0.5F : 0.25F)) {
            if (this.level() instanceof ServerLevel serverLevel) {
                randomlyParryWithWeapon(serverLevel, damageSource.getEntity());
            }
            return false;
        }
        return super.hurt(damageSource, f);
    }

    public void die(@NotNull DamageSource damagesource) {
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

            InfectedPlayerNpcEntity corpse = new InfectedPlayerNpcEntity(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(), serverLevel);
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
        if (this.level() instanceof ServerLevel) {
            LivingEntityPatch<?> livingEntityPatch = this.getLivingEntityPatch();
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Animations.EMPTY_ANIMATION;
            if (livingEntityPatch != null) {
                AnimationPlayer animationPlayer = livingEntityPatch.getAnimator().getPlayerFor(null);
                if (animationPlayer != null) {
                    dynamicAnimation = animationPlayer.getRealAnimation();
                }
            }

            if (this.getTarget() == null || EpicfightUtil.isLongHitAnimation(dynamicAnimation, getLivingEntityPatch()) || this.getLivingEntityPatch().isStunned() || dynamicAnimation.get() instanceof ExecutionAttackAnimation) {
                this.getNavigation().stop();
                this.setDeltaMovement(Vec3.ZERO);
            } else {
                this.setDeltaMovement(new Vec3(this.getLookAngle().x * 0.2D, this.getLookAngle().y * 0.2D, this.getLookAngle().z * 0.2D));
            }
        } else {
            if (this.getLivingEntityPatch() == null) return;
            if (this.getLivingEntityPatch().getAnimator() == null) return;
            if (this.getLivingEntityPatch().getArmature() == null) return;
            if (Armatures.BIPED.get() == null || Armatures.BIPED.get().toolL == null) return;
            if (this.getLivingEntityPatch().getOriginal() == null) return;

            byte poseSampleCount = 3;
            float poseStep = 1.0F / (float) (poseSampleCount - 1);
            float poseProgress = 0.0F;

            OpenMatrix4f toolLeftTransform;
            int particleIndex;
            int poseSampleIndex;
            OpenMatrix4f jointTransform;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                Pose pose;
                try {
                    pose = this.getLivingEntityPatch().getAnimator().getPose(poseProgress);
                } catch (Throwable t) {
                    return;
                }
                if (pose == null) return;

                toolLeftTransform = this.getLivingEntityPatch().getArmature()
                        .getBoundTransformFor(pose, Armatures.BIPED.get().toolL);

                if (toolLeftTransform == null) {
                    poseProgress += poseStep;
                    continue;
                }

                toolLeftTransform = new OpenMatrix4f(toolLeftTransform);
                toolLeftTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        toolLeftTransform,
                        toolLeftTransform
                );

                for (particleIndex = 0; particleIndex < 1; ++particleIndex) {
                    this.getLivingEntityPatch().getOriginal().level().addParticle(
                            AnnoyingVillagersModParticleTypes.NULL.get(),
                            (double) toolLeftTransform.m30 + this.getLivingEntityPatch().getOriginal().getX(),
                            (double) toolLeftTransform.m31 + this.getLivingEntityPatch().getOriginal().getY(),
                            (double) toolLeftTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ(),
                            ((new Random()).nextFloat() - 0.5F) * 0.15F,
                            ((new Random()).nextFloat() - 0.5F) * 0.15F,
                            ((new Random()).nextFloat() - 0.5F) * 0.15F
                    );
                }

                for (particleIndex = 0; particleIndex < 1; ++particleIndex) {
                    this.getLivingEntityPatch().getOriginal().level().addParticle(
                            AnnoyingVillagersModParticleTypes.NULL.get(),
                            (double) toolLeftTransform.m30 + this.getLivingEntityPatch().getOriginal().getX(),
                            (double) toolLeftTransform.m31 + this.getLivingEntityPatch().getOriginal().getY(),
                            (double) toolLeftTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ(),
                            0.0D, 0.0D, 0.0D
                    );
                }

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().toolR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 1.8F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, -((new Random()).nextFloat() * 4.0F)));

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX(),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY(),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ(),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );
                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX(),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY(),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ(),
                        0.0D, 0.0D, 0.0D
                );

                poseProgress += poseStep;
            }

            for (particleIndex = 0; particleIndex < 14; ++particleIndex) {
                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        this.getLivingEntityPatch().getOriginal().getX(),
                        this.getLivingEntityPatch().getOriginal().getY() + 0.029999999329447746D,
                        this.getLivingEntityPatch().getOriginal().getZ(),
                        ((new Random()).nextFloat() - 0.5F) * 0.65F,
                        ((new Random()).nextFloat() - 0.5F) * 0.05F,
                        ((new Random()).nextFloat() - 0.5F) * 0.65F
                );
            }

            poseStep = 1.0F;
            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().head
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() + 0.1F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().chest
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().armL
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().armR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().torso
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().thighL
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().thighR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().legL
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }

            poseProgress = 0.0F;

            for (poseSampleIndex = 0; poseSampleIndex < poseSampleCount; ++poseSampleIndex) {
                jointTransform = this.getLivingEntityPatch().getArmature().getBoundTransformFor(
                        this.getLivingEntityPatch().getAnimator().getPose(poseProgress),
                        Armatures.BIPED.get().legR
                );
                jointTransform.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                OpenMatrix4f.mul(
                        (new OpenMatrix4f()).rotate(
                                -((float) Math.toRadians(this.getLivingEntityPatch().getOriginal().yBodyRotO + 180.0F)),
                                new Vec3f(0.0F, 1.0F, 0.0F)
                        ),
                        jointTransform,
                        jointTransform
                );

                this.getLivingEntityPatch().getOriginal().level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(),
                        (double) jointTransform.m30 + this.getLivingEntityPatch().getOriginal().getX() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m31 + this.getLivingEntityPatch().getOriginal().getY() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        (double) jointTransform.m32 + this.getLivingEntityPatch().getOriginal().getZ() + (double) (((new Random()).nextFloat() - 0.5F) * 0.55F),
                        ((new Random()).nextFloat() - 0.5F) * 0.15F,
                        ((new Random()).nextFloat() - 1.0F) * 0.55F,
                        ((new Random()).nextFloat() - 0.5F) * 0.15F
                );

                poseProgress += poseStep;
            }
        }
    }

    protected void checkFallDamage(double d0, boolean flag, @NotNull BlockState blockstate, @NotNull BlockPos blockpos) {}

    public void setNoGravity(boolean flag) {
        super.setNoGravity(true);
    }

    @Override
    public void remove(@NotNull RemovalReason pReason) {
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
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 3.0D)
                .add(Attributes.FLYING_SPEED, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(EpicFightAttributes.IMPACT.get(), 4.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);
    }
}
