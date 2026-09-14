package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.clazz.ForceTickEntity;
import com.pla.annoyingvillagers.mixin.BrainAccessor;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationController;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class AvWarden extends Warden implements ForceTickEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState eatingAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int eatingAnimationTimeout = 0;
    private EliteHerobrineKnockedEntity eatingHerobrine;
    private UUID eatingUUID;
    private boolean burrowStarted = false;
    private int burrowRemoveAt = -1;
    private static final int DIG_TICKS = 100;
    private static final EntityDataAccessor<Boolean> DATA_BONE_OPEN =
            SynchedEntityData.defineId(AvWarden.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_AV_CHASING =
            SynchedEntityData.defineId(AvWarden.class, EntityDataSerializers.BOOLEAN);
    private boolean infectedSculk = false;
    private boolean avBrainConfigured;
    private final Map<SpecialAnimationId, Integer> avSeriesCooldowns = new EnumMap<>(SpecialAnimationId.class);
    private List<SpecialAnimationId> avCombo = List.of();
    private int avComboIndex;

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BONE_OPEN, false);
        this.entityData.define(DATA_AV_CHASING, false);
    }

    private boolean isBoneOpen() {
        return this.entityData.get(DATA_BONE_OPEN);
    }

    private void setBoneOpen(boolean open) {
        if (!this.level().isClientSide()) this.entityData.set(DATA_BONE_OPEN, open);
    }

    public boolean isAvChasing() {
        return this.entityData.get(DATA_AV_CHASING);
    }

    public void setEatingUUID(UUID eatingUUID) {
        this.eatingUUID = eatingUUID;
    }

    public AvWarden(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void setupIdleAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    private void setupEatingAnimationStates() {
        if (this.eatingAnimationTimeout <= 0) {
            this.eatingAnimationTimeout = 40;
            this.eatingAnimationState.start(this.tickCount);
        } else {
            --this.eatingAnimationTimeout;
        }
    }

    public void burrowThenDespawn() {
        if (this.level().isClientSide() || burrowStarted) return;
        burrowStarted = true;
        this.getNavigation().stop();
        this.setDeltaMovement(Vec3.ZERO);

        Brain<Warden> brain = this.getBrain();
        brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
        brain.eraseMemory(MemoryModuleType.ROAR_TARGET);
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.DISTURBANCE_LOCATION);
        brain.eraseMemory(MemoryModuleType.SNIFF_COOLDOWN);
        brain.eraseMemory(MemoryModuleType.DIG_COOLDOWN);

        this.setPose(Pose.DIGGING);
        burrowRemoveAt = this.tickCount + DIG_TICKS + 1;
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level().isClientSide()) configureAvCombatBrain();
        super.customServerAiStep();
        if (this.level().isClientSide()) return;
        LivingEntity currentTarget = this.getTarget();
        this.entityData.set(DATA_AV_CHASING, currentTarget != null && currentTarget.isAlive());
        tickAvCombat();

        if (eatingHerobrine == null && eatingUUID != null) {
            Entity e = ((ServerLevel) level()).getEntity(eatingUUID);
            if (e instanceof EliteHerobrineKnockedEntity herobrine && herobrine.isAlive()) {
                eatingHerobrine = herobrine;
            }
        }

        if (eatingUUID == null || eatingHerobrine == null || !eatingHerobrine.isAlive()) {
            return;
        }

        LivingEntity brainTarget = this.getTarget();
        if (brainTarget == null || !eatingUUID.equals(brainTarget.getUUID())) {
            this.setAttackTarget(eatingHerobrine);
        }

        int bump = AngerLevel.ANGRY.getMinimumAnger() + 20;
        this.increaseAngerAt(eatingHerobrine, bump, false);

        this.getEntityAngryAt().ifPresent(angry -> {
            if (!eatingUUID.equals(angry.getUUID())) {
                this.clearAnger(angry);
            }
        });

        var brain = this.getBrain();
        brain.eraseMemory(MemoryModuleType.HURT_BY);
        brain.eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
        brain.eraseMemory(MemoryModuleType.RECENT_PROJECTILE);

        WardenAi.updateActivity(this);
        brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        this.getEntityAngryAt().map(Entity::getUUID);
    }

    @Override
    public boolean canTargetEntity(@org.jetbrains.annotations.Nullable Entity e) {
        if (!(e instanceof LivingEntity living)) return false;
        if (eatingUUID == null) return super.canTargetEntity(e);
        return eatingUUID.equals(living.getUUID()) && super.canTargetEntity(e);
    }

    @Override
    public void setAttackTarget(@NotNull LivingEntity target) {
        if (eatingUUID != null && !eatingUUID.equals(target.getUUID())) {
            return;
        }
        super.setAttackTarget(target);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount == 1 && !level().isClientSide() && !infectedSculk) {
            final RandomSource randomSource = this.getRandom();

            final int attempts = new Random().nextInt(25, 50);
            for (int i = 0; i < attempts; i++) {
                int dx = Mth.nextInt(randomSource, -5, 5);
                int dz = Mth.nextInt(randomSource, -5, 5);

                BlockPos pos = new BlockPos((int) (this.getX() + dx), this.getOnPos().getY(), (int) (this.getZ() + dz));
                BlockState current = level().getBlockState(pos);

                if (current.isAir()) continue;
                if (!current.getFluidState().isEmpty()) continue;
                if (current.getDestroySpeed(level(), pos) == -1.0F) continue;
                if (current.hasBlockEntity()) continue;

                BlockState newState = (randomSource.nextFloat() < 0.20f)
                        ? Blocks.SCULK_CATALYST.defaultBlockState()
                        : Blocks.SCULK.defaultBlockState();

                level().setBlock(pos, newState, 3);
            }
            infectedSculk = true;
        }
        if (level().isClientSide) {
            this.setupIdleAnimationStates();
            if (isBoneOpen()) {
                this.setupEatingAnimationStates();
            }
        }
        if (burrowStarted && this.tickCount >= burrowRemoveAt) {
            super.remove(RemovalReason.DISCARDED);
        }
        if (eatingHerobrine != null && !eatingHerobrine.isAlive()) {
            eatingHerobrine = null;
            eatingUUID = null;
            if (isBoneOpen()) setBoneOpen(false);
            burrowThenDespawn();
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        return false;
    }

    @SuppressWarnings("unchecked")
    private void configureAvCombatBrain() {
        if (this.avBrainConfigured) return;
        this.avBrainConfigured = true;
        BrainAccessor<Warden> accessor = (BrainAccessor<Warden>)(Object)this.getBrain();
        Map<Integer, Map<Activity, Set<BehaviorControl<? super Warden>>>> priorities = accessor.annoyingVillagers$getAvailableBehaviorsByPriority();
        removeFirstFightBehavior(priorities, 14, behavior -> behavior instanceof SonicBoom);
        removeFirstFightBehavior(priorities, 15, behavior -> behavior instanceof OneShot);
    }

    private void removeFirstFightBehavior(Map<Integer, Map<Activity, Set<BehaviorControl<? super Warden>>>> priorities, int priority, java.util.function.Predicate<BehaviorControl<? super Warden>> predicate) {
        Map<Activity, Set<BehaviorControl<? super Warden>>> byActivity = priorities.get(priority);
        if (byActivity == null) return;
        Set<BehaviorControl<? super Warden>> fight = byActivity.get(Activity.FIGHT);
        if (fight == null) return;
        BehaviorControl<? super Warden> match = null;
        for (BehaviorControl<? super Warden> behavior : fight) {
            if (!predicate.test(behavior)) continue;
            match = behavior;
            break;
        }
        if (match != null) fight.remove(match);
    }

    private void tickAvCombat() {
        this.avSeriesCooldowns.replaceAll((id, ticks) -> Math.max(0, ticks - 1));
        if (this.burrowStarted || this.hasPose(Pose.DIGGING) || this.hasPose(Pose.EMERGING)) return;
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            clearAvCombo();
            return;
        }
        if (SpecialAnimationController.hasActiveAnimation(this)) {
            this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            this.getNavigation().stop();
            this.getLookControl().setLookAt(target, 60.0F, 30.0F);
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.25D, 1.0D, 0.25D));
            return;
        }
        if (this.avComboIndex < this.avCombo.size()) {
            if (this.avComboIndex == 0 || targetInAvGrid(target, 1, 0, -1, 3)) playNextAvComboStep(target);
            else clearAvCombo();
            return;
        }

        List<AvCombatChoice> choices = new ArrayList<>();
        addAvChoice(choices, SpecialAnimationId.WARDEN_ATK_1_1, 20, 10, targetInAvGrid(target, 1, 1, -1, 2), SpecialAnimationId.WARDEN_ATK_1_1, SpecialAnimationId.WARDEN_ATK_1_2);
        addAvChoice(choices, SpecialAnimationId.WARDEN_ATK_2_1, 30, 10, targetInAvGrid(target, 1, 0, -1, 2), SpecialAnimationId.WARDEN_ATK_2_1, SpecialAnimationId.WARDEN_ATK_2_2, SpecialAnimationId.WARDEN_ATK_2_3);
        addAvChoice(choices, SpecialAnimationId.WARDEN_ATK_3_1, 50, 10, targetInAvGrid(target, 1, 1, -1, 2), SpecialAnimationId.WARDEN_ATK_3_1, SpecialAnimationId.WARDEN_ATK_3_2, SpecialAnimationId.WARDEN_ATK_3_3);
        addAvChoice(choices, SpecialAnimationId.WARDEN_ATK_4_1, 20, 10, targetInAvGrid(target, 1, 0, -1, 2), SpecialAnimationId.WARDEN_ATK_4_1, SpecialAnimationId.WARDEN_ATK_4_2);
        addAvChoice(choices, SpecialAnimationId.WARDEN_SKILL_1, 50, 10, targetInAvGrid(target, 1, 6, -1, 9), SpecialAnimationId.WARDEN_SKILL_1);
        addAvChoice(choices, SpecialAnimationId.WARDEN_SKILL_2, 50, 150, targetInAvGrid(target, 1, 1, -1, 6), SpecialAnimationId.WARDEN_SKILL_2);
        float distance = this.distanceTo(target);
        addAvChoice(choices, SpecialAnimationId.WARDEN_SONIC_BOOM, 1, 120, distance >= 5.0F && distance <= 30.0F, SpecialAnimationId.WARDEN_SONIC_BOOM);

        AvCombatChoice selected = chooseAvChoice(choices);
        if (selected == null) return;
        this.avSeriesCooldowns.put(selected.cooldownKey(), selected.cooldownTicks());
        this.avCombo = selected.combo();
        this.avComboIndex = 0;
        playNextAvComboStep(target);
    }

    private void addAvChoice(List<AvCombatChoice> choices, SpecialAnimationId cooldownKey, int weight, int cooldownTicks, boolean condition, SpecialAnimationId... animations) {
        if (!condition || this.avSeriesCooldowns.getOrDefault(cooldownKey, 0) > 0) return;
        choices.add(new AvCombatChoice(cooldownKey, weight, cooldownTicks, List.of(animations)));
    }

    private AvCombatChoice chooseAvChoice(List<AvCombatChoice> choices) {
        int total = 0;
        for (AvCombatChoice choice : choices) total += choice.weight();
        if (total <= 0) return null;
        int roll = this.getRandom().nextInt(total);
        for (AvCombatChoice choice : choices) {
            roll -= choice.weight();
            if (roll < 0) return choice;
        }
        return choices.get(choices.size() - 1);
    }

    private void playNextAvComboStep(LivingEntity target) {
        if (this.avComboIndex >= this.avCombo.size()) return;
        SpecialAnimationId next = this.avCombo.get(this.avComboIndex);
        if (playAvAttack(next, target)) this.avComboIndex++;
    }

    private void clearAvCombo() {
        this.avCombo = List.of();
        this.avComboIndex = 0;
    }

    private boolean targetInAvGrid(LivingEntity target, int x1, int z1, int x2, int z2) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        double yaw = Math.toRadians(this.getYRot());
        double cos = Math.cos(yaw);
        double sin = Math.sin(yaw);
        int gridX = (int)Math.floor(dx * cos + dz * sin + 0.5D);
        int gridZ = (int)Math.floor(-dx * sin + dz * cos + 0.5D);
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minZ = Math.min(z1, z2);
        int maxZ = Math.max(z1, z2);
        return Math.abs(gridX) < 24 && Math.abs(gridZ) < 24 && gridX >= minX && gridX <= maxX && gridZ >= minZ && gridZ <= maxZ;
    }

    private boolean playAvAttack(SpecialAnimationId animationId, LivingEntity target) {
        boolean played = SpecialAnimationController.play(this, animationId, target);
        if (played && this.eatingUUID != null && this.eatingUUID.equals(target.getUUID()) && !isBoneOpen()) setBoneOpen(true);
        return played;
    }

    private record AvCombatChoice(SpecialAnimationId cooldownKey, int weight, int cooldownTicks, List<SpecialAnimationId> combo) {
    }

    public void fireAvSonicBoom(float damage) {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;
        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) return;
        Vec3 start = this.getEyePosition().add(0.0D, -0.25D, 0.0D);
        Vec3 end = target.getEyePosition();
        Vec3 delta = end.subtract(start);
        double length = delta.length();
        if (length < 1.0E-4D) return;
        Vec3 direction = delta.scale(1.0D / length);
        for (double distance = 0.0D; distance <= length; distance += 0.5D) {
            Vec3 point = start.add(direction.scale(distance));
            serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        this.playSound(net.minecraft.sounds.SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
        target.invulnerableTime = 0;
        if (target.hurt(this.damageSources().sonicBoom(this), damage)) {
            double resistance = 1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            target.push(direction.x * 2.5D * resistance, direction.y * 0.5D * resistance, direction.z * 2.5D * resistance);
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (eatingUUID != null) {
            tag.putUUID("EatingUUID", eatingUUID);
        }
        tag.putBoolean("BurrowStarted", burrowStarted);
        tag.putInt("BurrowRemoveAt", burrowRemoveAt);
        tag.putBoolean("InfectedSculk", infectedSculk);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("EatingUUID")) {
            eatingUUID = tag.getUUID("EatingUUID");
        }
        burrowStarted = tag.getBoolean("BurrowStarted");
        burrowRemoveAt = tag.getInt("BurrowRemoveAt");
        infectedSculk = tag.getBoolean("InfectedSculk");
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_POSE.equals(key)) {
            if (this.hasPose(Pose.DIGGING)) {
                this.diggingAnimationState.start(this.tickCount);
            }
        }

        if (level().isClientSide() && DATA_BONE_OPEN.equals(key)) {
            if (isBoneOpen()) {
                this.eatingAnimationTimeout = 0;
                this.setupEatingAnimationStates();
            } else {
                this.eatingAnimationState.stop();
            }
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData returnSpawnGroupData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);

        this.setPose(Pose.EMERGING);
        this.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenAi.EMERGE_DURATION);

        return returnSpawnGroupData;
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.FELL_OUT_OF_WORLD)
                || pSource.is(DamageTypes.GENERIC_KILL)) {
            return super.hurt(pSource, pAmount);
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            CommonUtil.damageBlocked(pSource, this, serverLevel);
        }
        return false;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 500.0D);
        builder = builder.add(Attributes.ARMOR, 20.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 30.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 24.0D);
        return builder;
    }
}
