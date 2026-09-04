package com.pla.annoyingvillagers.entity;

import java.util.*;

import com.pla.annoyingvillagers.clazz.FakePlayer;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.entity.goal.NullSummonSkeletonGoal;
import com.pla.annoyingvillagers.util.CommonUtil;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private int nullSkeletonSummonCooldown = 0;

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

    public boolean canSummonNullSkeleton() {
        return this.getState() == 2 && this.isAvailableWitherSkeletonSlot() && this.nullSkeletonSummonCooldown <= 0;
    }

    public void resetNullSkeletonSummonCooldown() {
        this.nullSkeletonSummonCooldown = 600 + this.getRandom().nextInt(601);
    }

    public int getNullSkeletonSummonCooldown() {
        return this.nullSkeletonSummonCooldown;
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

    public void releaseRandomNullWeapon(LivingEntity target) {
        if (target == null || !target.isAlive()) return;

        List<NullWeapon> allWeapons = this.getAvailableNullWeapons();
        if (allWeapons.isEmpty()) return;

        if (this.getState() < 2) {
            List<NullWeapon> previousWeapons = new ArrayList<>();
            for (NullWeapon weapon : allWeapons) {
                if (!weapon.isReleased()) continue;
                previousWeapons.add(weapon);
                weapon.returnToNullImmediately();
            }

            List<NullWeapon> candidates = new ArrayList<>(allWeapons);
            if (candidates.size() > previousWeapons.size()) candidates.removeAll(previousWeapons);
            NullWeapon chosen = candidates.get(this.getRandom().nextInt(candidates.size()));
            chosen.release(target);
            return;
        }

        List<NullWeapon> candidates = new ArrayList<>();
        for (NullWeapon weapon : allWeapons) {
            if (!weapon.isReleased()) candidates.add(weapon);
        }

        if (candidates.isEmpty()) {
            this.recallAllNullWeapons();
            return;
        }

        NullWeapon chosen = candidates.get(this.getRandom().nextInt(candidates.size()));
        chosen.release(target);
    }

    public void recallAllNullWeapons() {
        for (NullWeapon weapon : this.getAvailableNullWeapons()) {
            if (weapon.isReleased()) weapon.returnToNullImmediately();
        }
    }

    private List<NullWeapon> getAvailableNullWeapons() {
        List<NullWeapon> weapons = new ArrayList<>(5);
        if (this.nullSwordEntity != null && this.nullSwordEntity.isAlive()) weapons.add(this.nullSwordEntity);
        if (this.nullAxeEntity != null && this.nullAxeEntity.isAlive()) weapons.add(this.nullAxeEntity);
        if (this.nullPickaxeEntity != null && this.nullPickaxeEntity.isAlive()) weapons.add(this.nullPickaxeEntity);
        if (this.nullShovelEntity != null && this.nullShovelEntity.isAlive()) weapons.add(this.nullShovelEntity);
        if (this.nullHoeEntity != null && this.nullHoeEntity.isAlive()) weapons.add(this.nullHoeEntity);
        return weapons;
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
        CommonUtil.damageBlockedForce(chosen, attacker, serverLevel);
        chosen.moveTo(this.getX(), this.getY(), this.getZ());
        chosen.spinfor5seconds();
    }

    public void setSpinningToAllWeaponsAvailableFor5seconds() {
        setSpinningFor5SecondsIfAvailable(this.nullSwordEntity);
        setSpinningFor5SecondsIfAvailable(this.nullAxeEntity);
        setSpinningFor5SecondsIfAvailable(this.nullPickaxeEntity);
        setSpinningFor5SecondsIfAvailable(this.nullShovelEntity);
        setSpinningFor5SecondsIfAvailable(this.nullHoeEntity);
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
        tag.putInt("NullSkeletonSummonCooldown", this.nullSkeletonSummonCooldown);
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
        this.nullSkeletonSummonCooldown = Math.max(0, tag.getInt("NullSkeletonSummonCooldown"));
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

    private void ensureNullWeapons(ServerLevel serverLevel) {
        if (this.nullSwordEntity != null && !this.nullSwordEntity.isAlive()) {
            this.nullSwordEntity = null;
            this.nullSwordUUID = null;
        }
        if (this.nullAxeEntity != null && !this.nullAxeEntity.isAlive()) {
            this.nullAxeEntity = null;
            this.nullAxeUUID = null;
        }
        if (this.nullPickaxeEntity != null && !this.nullPickaxeEntity.isAlive()) {
            this.nullPickaxeEntity = null;
            this.nullPickaxeUUID = null;
        }
        if (this.nullShovelEntity != null && !this.nullShovelEntity.isAlive()) {
            this.nullShovelEntity = null;
            this.nullShovelUUID = null;
        }
        if (this.nullHoeEntity != null && !this.nullHoeEntity.isAlive()) {
            this.nullHoeEntity = null;
            this.nullHoeUUID = null;
        }

        if (this.nullSwordEntity == null && this.nullSwordUUID == null) {
            NullWeapon weapon = new NullSwordEntity(AnnoyingVillagersModEntities.NULL_SWORD.get(), serverLevel);
            weapon.summonNullWeaponForNullEntity(serverLevel, this, "sword");
        }
        if (this.nullAxeEntity == null && this.nullAxeUUID == null) {
            NullWeapon weapon = new NullAxeEntity(AnnoyingVillagersModEntities.NULL_AXE.get(), serverLevel);
            weapon.summonNullWeaponForNullEntity(serverLevel, this, "axe");
        }
        if (this.nullPickaxeEntity == null && this.nullPickaxeUUID == null) {
            NullWeapon weapon = new NullPickaxeEntity(AnnoyingVillagersModEntities.NULL_PICKAXE.get(), serverLevel);
            weapon.summonNullWeaponForNullEntity(serverLevel, this, "pickaxe");
        }
        if (this.nullShovelEntity == null && this.nullShovelUUID == null) {
            NullWeapon weapon = new NullShovelEntity(AnnoyingVillagersModEntities.NULL_SHOVEL.get(), serverLevel);
            weapon.summonNullWeaponForNullEntity(serverLevel, this, "shovel");
        }
        if (this.nullHoeEntity == null && this.nullHoeUUID == null) {
            NullWeapon weapon = new NullHoeEntity(AnnoyingVillagersModEntities.NULL_HOE.get(), serverLevel);
            weapon.summonNullWeaponForNullEntity(serverLevel, this, "hoe");
        }
    }

    @Override
    public void tick() {
        this.freezeDuringSecondFormTransformation();
        super.tick();
        this.freezeDuringSecondFormTransformation();

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

            if (this.tickCount % 20 == 0) this.ensureNullWeapons((ServerLevel) this.level());
            if (this.tickCount % 10 == 0 && this.tickCount >= 20) {
                if (this.nullSwordEntity != null) this.nullSwordEntity.processTeleportByNullEntity();
                if (this.nullAxeEntity != null) this.nullAxeEntity.processTeleportByNullEntity();
                if (this.nullPickaxeEntity != null) this.nullPickaxeEntity.processTeleportByNullEntity();
                if (this.nullHoeEntity != null) this.nullHoeEntity.processTeleportByNullEntity();
                if (this.nullShovelEntity != null) this.nullShovelEntity.processTeleportByNullEntity();
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

            boolean skeletonSlotOpened = false;
            if (firstWitherSkeleton != null && !firstWitherSkeleton.isAlive()) {
                firstWitherSkeleton = null;
                firstWitherSkeletonUuid = null;
                skeletonSlotOpened = true;
            }
            if (secondWitherSkeleton != null && !secondWitherSkeleton.isAlive()) {
                secondWitherSkeleton = null;
                secondWitherSkeletonUuid = null;
                skeletonSlotOpened = true;
            }
            if (skeletonSlotOpened && this.getState() == 2) this.resetNullSkeletonSummonCooldown();
            if (this.nullSkeletonSummonCooldown > 0) this.nullSkeletonSummonCooldown--;
        }
    }

    private void freezeDuringSecondFormTransformation() {
        if (!this.isSacrificing()) return;
        this.getNavigation().stop();
        this.setDeltaMovement(Vec3.ZERO);
        this.getMoveControl().setWantedPosition(this.getX(), this.getY(), this.getZ(), 0.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new NullSummonSkeletonGoal(this));
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
                killedName = FakePlayer.getRandomHardcodedName(this.getRandom());
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
        this.spawnVanillaNullParticles();
    }

    private void spawnVanillaNullParticles() {
        if (!this.level().isClientSide()) {
            return;
        }

        double width = Math.max(0.35D, this.getBbWidth());
        double height = Math.max(1.0D, this.getBbHeight());

        for (int i = 0; i < 4; i++) {
            double x = this.getX() + (this.random.nextDouble() - 0.5D) * width * 1.2D;
            double y = this.getY() + this.random.nextDouble() * height;
            double z = this.getZ() + (this.random.nextDouble() - 0.5D) * width * 1.2D;
            double vx = (this.random.nextDouble() - 0.5D) * 0.12D;
            double vy = (this.random.nextDouble() - 0.5D) * 0.08D;
            double vz = (this.random.nextDouble() - 0.5D) * 0.12D;

            this.level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), x, y, z, vx, vy, vz);
        }

        if ((this.tickCount & 1) == 0) {
            for (int i = 0; i < 3; i++) {
                double x = this.getX() + (this.random.nextDouble() - 0.5D) * width * 1.8D;
                double z = this.getZ() + (this.random.nextDouble() - 0.5D) * width * 1.8D;
                double vx = (this.random.nextDouble() - 0.5D) * 0.18D;
                double vz = (this.random.nextDouble() - 0.5D) * 0.18D;

                this.level().addParticle(
                        AnnoyingVillagersModParticleTypes.NULL.get(), x, this.getY() + 0.03D, z, vx, (this.random.nextDouble() - 0.5D) * 0.02D, vz
                );
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

    public static Builder addEpicFightAttributes(Builder builder) {
        //      ADD THIS CODE IN AV_EFM
//        return builder.add(EpicFightAttributes.IMPACT.get(), 4.0D)
//                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
//                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
//                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
//                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
//                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);

        return builder;
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.FLYING_SPEED, 0.70D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
                .add(Attributes.ARMOR, 80.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 40.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return addEpicFightAttributes(builder);
    }
}
