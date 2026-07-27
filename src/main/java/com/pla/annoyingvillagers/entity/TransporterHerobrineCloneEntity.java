package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.gameasset.AnimsSculkSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.HerobrinePortalUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.EnumSet;
import java.util.UUID;

import static com.pla.annoyingvillagers.combatbehaviour.TransporterHerobrineCombatValues.*;

public class TransporterHerobrineCloneEntity extends HerobrineMob {
    private static final int MAX_COMBAT_LOW_CLONE_SUPPORT = 3;
    private static final float TRANSPORTER_FRAGMENT_DROP_CHANCE = 0.1F;
    private static final float FISHING_HOOK_ESCAPE_CANCEL_CHANCE = 0.3F;
    private static final double SECOND_FORM_SUPPORT_SEARCH_RADIUS_SQR = 48.0D * 48.0D;
    private static final EntityDataAccessor<Boolean> HOOKED =
            SynchedEntityData.defineId(TransporterHerobrineCloneEntity.class, EntityDataSerializers.BOOLEAN);

    private int escapeTiming = -1;
    private int escapeRetryCooldown = 0;
    private int supportAvoidRepathCooldown = 0;
    private int lowCloneSupportCooldown = 0;
    private int portalPairCooldown = 0;
    private int rangedCounterPortalCooldown = 0;
    private int supportEscapePortalCooldown = 0;
    private int portalEscapeStepBackCooldown = 0;
    private boolean fishingHookCancelledEscape = false;
    private boolean hookedWaitingForGround = false;
    private boolean hookedLeftGround = false;
    private final Entity[] combatLowCloneSupport = new Entity[MAX_COMBAT_LOW_CLONE_SUPPORT];
    private final UUID[] combatLowCloneSupportUUIDs = new UUID[MAX_COMBAT_LOW_CLONE_SUPPORT];

    public TransporterHerobrineCloneEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(), level);
    }

    public TransporterHerobrineCloneEntity(EntityType<TransporterHerobrineCloneEntity> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(2.0F);
        this.xpReward = 120;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setCustomNameVisible(false);
        this.setChatName(this.getDisplayName().getString());
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get()));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HOOKED, false);
    }

    public boolean isHooked() {
        return this.entityData.get(HOOKED);
    }

    private void setHooked(boolean hooked) {
        this.entityData.set(HOOKED, hooked);
        if (!hooked) {
            this.hookedWaitingForGround = false;
            this.hookedLeftGround = false;
        }
        if (hooked && !this.level().isClientSide() && !this.hookedWaitingForGround) {
            this.enforceHookedNoAiLock();
        }
    }

    private void releaseHookedPhysicsUntilGround() {
        this.hookedWaitingForGround = true;
        this.hookedLeftGround = this.hookedLeftGround || !this.onGround();
        this.noPhysics = false;
        this.setNoGravity(false);
        this.setNoAi(false);
        this.setInvulnerable(false);
        this.getNavigation().stop();
    }

    private void tickHookedGroundRelock() {
        if (!this.hookedWaitingForGround) {
            this.enforceHookedNoAiLock();
            return;
        }

        this.noPhysics = false;
        this.setNoGravity(false);
        this.setInvulnerable(false);
        if (!this.onGround()) {
            this.hookedLeftGround = true;
        }
        if (this.hookedLeftGround && this.onGround()) {
            this.hookedWaitingForGround = false;
            this.hookedLeftGround = false;
            this.enforceHookedNoAiLock();
        }
    }

    private void enforceHookedNoAiLock() {
        this.setNoAi(true);
        this.getNavigation().stop();
        this.setSprinting(false);
        this.setTarget(null);
        this.xxa = 0.0F;
        this.yya = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);
    }

    private void enforceTransporterHealthCap() {
        AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null && maxHealth.getBaseValue() != this.getMaxHealth()) {
            maxHealth.setBaseValue(this.getMaxHealth());
        }
        AttributeInstance armor = this.getAttribute(Attributes.ARMOR);
        if (armor != null && armor.getBaseValue() != 0.0D) {
            armor.setBaseValue(0.0D);
        }
        if (this.getHealth() > this.getMaxHealth()) {
            this.setHealth((float) this.getMaxHealth());
        }
    }

    private int randomCooldownSeconds(int minSeconds, int maxSeconds) {
        return minSeconds * 20 + this.getRandom().nextInt((maxSeconds - minSeconds) * 20 + 1);
    }

    public int getLowCloneSupportCooldown() {
        return lowCloneSupportCooldown;
    }

    public void setLowCloneSupportCooldown() {
        this.lowCloneSupportCooldown = randomCooldownSeconds(90, 180);
    }

    public int getPortalPairCooldown() {
        return portalPairCooldown;
    }

    public void setPortalPairCooldown() {
        this.portalPairCooldown = randomCooldownSeconds(30, 60);
    }

    public int getRangedCounterPortalCooldown() {
        return rangedCounterPortalCooldown;
    }

    public void setRangedCounterPortalCooldown() {
        this.rangedCounterPortalCooldown = randomCooldownSeconds(30, 60);
    }

    public int getSupportEscapePortalCooldown() {
        return supportEscapePortalCooldown;
    }

    public void setSupportEscapePortalCooldown() {
        this.supportEscapePortalCooldown = randomCooldownSeconds(30, 60);
    }

    public int getPortalEscapeStepBackCooldown() {
        return portalEscapeStepBackCooldown;
    }

    public void setPortalEscapeStepBackCooldown() {
        this.portalEscapeStepBackCooldown = randomCooldownSeconds(30, 60);
    }

    private void tickCombatActionCooldowns() {
        if (this.lowCloneSupportCooldown > 0) this.lowCloneSupportCooldown--;
        if (this.portalPairCooldown > 0) this.portalPairCooldown--;
        if (this.rangedCounterPortalCooldown > 0) this.rangedCounterPortalCooldown--;
        if (this.supportEscapePortalCooldown > 0) this.supportEscapePortalCooldown--;
        if (this.portalEscapeStepBackCooldown > 0) this.portalEscapeStepBackCooldown--;
    }

    @Override
    public @Nullable SoundEvent getAttackVoiceSound() {
        return AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY.get();
    }

    @Override
    public @Nullable SoundEvent getHurtVoiceSound() {
        return AnnoyingVillagersModSounds.HEROBRINE_CLONE_SAY_ON_HURT.get();
    }

    @Override
    public float applyBurstProtection(LivingEntity self, DamageSource source, float damage) {
        return damage;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new SafeCombatPositionGoal());
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide() && this.isHooked() && !this.hookedWaitingForGround) {
            this.enforceHookedNoAiLock();
        }
        super.tick();
        if (this.level().isClientSide()) {
            return;
        }
        this.enforceTransporterHealthCap();
        this.tickCombatLowCloneSupportSlots();
        this.tickCombatActionCooldowns();
        if (this.isHooked()) {
            this.tickHookedGroundRelock();
            return;
        }
        if (this.escapeRetryCooldown > 0) {
            this.escapeRetryCooldown--;
        }
        if (this.supportAvoidRepathCooldown > 0) {
            this.supportAvoidRepathCooldown--;
        }
        if (this.shouldStartLegacyEscape()) {
            this.startLegacyEscape();
        }
        this.tickEscape();
        if (this.escapeTiming >= 0) {
            this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 1, 3, false, false));
            return;
        }
    }

    private boolean shouldStartLegacyEscape() {
        return this.escapeTiming < 0
                && !this.isNoAi()
                && this.escapeRetryCooldown <= 0
                && this.getHealth() <= this.getMaxHealth() * LOW_HEALTH_ESCAPE_RATIO;
    }

    private void startLegacyEscape() {
        this.escapeRetryCooldown = ESCAPE_RETRY_COOLDOWN_TICKS;
        this.startEscape();
    }

    public boolean canUseSupportPortalAction() {
        return this.escapeTiming < 0 && !this.isNoAi();
    }

    public boolean canSummonLowCloneSupport() {
        return this.canUseSupportPortalAction()
                && this.onGround()
                && this.lowCloneSupportCooldown <= 0
                && this.hasAvailableCombatLowCloneSupportSlot();
    }

    public boolean isSupportingSecondFormCaster(LivingEntity support) {
        return support instanceof HerobrineMob
                && support.isAlive()
                && this.distanceToSqr(support) <= SECOND_FORM_SUPPORT_SEARCH_RADIUS_SQR;
    }

    public void playSecondFormSupportCast(LivingEntity support) {
        if (support != null && support.isAlive()) {
            this.getLookControl().setLookAt(support, 30.0F, 30.0F);
        }
        this.playSecondFormSupportCastAnimation();
    }

    public boolean canFishingHookCancelEscape() {
        return this.escapeTiming >= 0 || this.getPersistentData().getBoolean(HerobrinePortalUtil.NBT_SINKING);
    }

    public boolean tryFishingHookCancelEscape() {
        if (!this.canFishingHookCancelEscape()) {
            return false;
        }
        if (this.getRandom().nextFloat() >= FISHING_HOOK_ESCAPE_CANCEL_CHANCE) {
            return false;
        }

        this.escapeTiming = -1;
        this.escapeRetryCooldown = ESCAPE_RETRY_COOLDOWN_TICKS;
        this.fishingHookCancelledEscape = true;
        this.hookedWaitingForGround = true;
        this.hookedLeftGround = !this.onGround();
        this.setHooked(true);
        HerobrinePortalUtil.cancelSinkTransition(this);
        EpicfightUtil.cancel(this, AnimsSculkSteve.PORTAL_SUMMON);
        this.releaseHookedPhysicsUntilGround();
        this.getNavigation().stop();
        return true;
    }

    public void triggerRangedCounterRetreat(@Nullable LivingEntity threat) {
        if (threat == null || !threat.isAlive()) {
            return;
        }

        this.getLookControl().setLookAt(threat, 30.0F, 30.0F);
        this.setTarget(null);
        this.supportAvoidRepathCooldown = 0;
        Vec3 retreatPos = this.findSupportRetreatPosition(threat);
        if (retreatPos == null) {
            return;
        }

        this.getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, SUPPORT_AVOID_MOVE_SPEED);
        this.supportAvoidRepathCooldown = SUPPORT_AVOID_REPATH_TICKS;
    }

    @Nullable
    private Vec3 findSupportRetreatPosition(LivingEntity threat) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return null;
        }

        double awayAngle = Math.atan2(this.getZ() - threat.getZ(), this.getX() - threat.getX());
        if (Double.isNaN(awayAngle)) {
            awayAngle = this.getRandom().nextDouble() * Math.PI * 2.0D;
        }

        for (int attempt = 0; attempt < 10; attempt++) {
            double angle = awayAngle + (this.getRandom().nextDouble() - 0.5D) * 1.4D;
            double distance = SUPPORT_AVOID_MIN_DISTANCE + this.getRandom().nextDouble() * (SUPPORT_AVOID_MAX_DISTANCE - SUPPORT_AVOID_MIN_DISTANCE);
            double x = this.getX() + Math.cos(angle) * distance;
            double z = this.getZ() + Math.sin(angle) * distance;
            BlockPos surface = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(x, this.getY(), z));

            if (!serverLevel.isLoaded(surface) || !serverLevel.getWorldBorder().isWithinBounds(surface)) {
                continue;
            }
            if (!serverLevel.isEmptyBlock(surface) || !serverLevel.isEmptyBlock(surface.above()) || serverLevel.isEmptyBlock(surface.below())) {
                continue;
            }

            Vec3 candidate = new Vec3(surface.getX() + 0.5D, surface.getY(), surface.getZ() + 0.5D);
            if (candidate.distanceToSqr(threat.position()) <= this.position().distanceToSqr(threat.position())) {
                continue;
            }
            if (!serverLevel.noCollision(this, this.getBoundingBox().move(candidate.subtract(this.position())).deflate(1.0E-4D))) {
                continue;
            }
            return candidate;
        }

        return null;
    }

    private class SafeCombatPositionGoal extends Goal {
        private SafeCombatPositionGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return canHoldSafeCombatPosition();
        }

        @Override
        public boolean canContinueToUse() {
            return canHoldSafeCombatPosition();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = getTarget();
            if (target == null || !target.isAlive()) {
                getNavigation().stop();
                return;
            }

            getLookControl().setLookAt(target, 30.0F, 30.0F);
            double targetDistanceSqr = distanceToSqr(target);
            if (targetDistanceSqr > SUPPORT_AVOID_TRIGGER_DISTANCE_SQR) {
                getNavigation().stop();
                return;
            }

            if (targetDistanceSqr >= SUPPORT_AVOID_SAFE_DISTANCE_SQR) {
                getNavigation().stop();
                return;
            }

            if (supportAvoidRepathCooldown > 0 || !getNavigation().isDone()) {
                return;
            }

            Vec3 retreatPos = findSupportRetreatPosition(target);
            if (retreatPos == null) {
                getNavigation().stop();
                return;
            }

            getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, SUPPORT_AVOID_MOVE_SPEED);
            supportAvoidRepathCooldown = SUPPORT_AVOID_REPATH_TICKS;
        }

        @Override
        public void stop() {
            getNavigation().stop();
        }
    }

    private boolean canHoldSafeCombatPosition() {
        LivingEntity target = this.getTarget();
        return target != null
                && target.isAlive()
                && this.escapeTiming < 0
                && !this.isNoAi();
    }

    private void tickEscape() {
        if (this.escapeTiming > 0) {
            this.escapeTiming--;
        }

        if (this.escapeTiming == 60) {
            this.playEscapeEffect();
        }

        if (this.escapeTiming == 40 && this.level() instanceof ServerLevel serverLevel) {
            HerobrinePortalUtil.sinkIntoGround(serverLevel, this, 0.06D);
        }

        if (this.escapeTiming == 1) {
            this.discard();
        }
    }

    private void startEscape() {
        this.escapeTiming = ESCAPE_DURATION_TICKS;
        this.setNoAi(true);
        this.playEscapeEffect();
    }

    private void playEscapeEffect() {
        this.playSound(AnnoyingVillagersModSounds.PORTAL_NATURAL.get(), 1.0F, 1.0F);
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
        if (patch != null) {
            patch.playAnimationSynchronized(AnimsSculkSteve.PORTAL_SUMMON, 0.0F);
        }
        if (this.level() instanceof ServerLevel) {
            AnnoyingVillagers.PACKET_HANDLER.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new ClientboundHerobrinePortalFx(this.position().add(0.0D, 0.0D, 0.0D))
            );
        }
    }

    private void cancelEscapeAndDropFragment() {
        this.escapeTiming = -1;
        HerobrinePortalUtil.cancelSinkTransition(this);
        this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get()));
    }

    private void tickCombatLowCloneSupportSlots() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        for (int i = 0; i < MAX_COMBAT_LOW_CLONE_SUPPORT; i++) {
            Entity tracked = this.combatLowCloneSupport[i];
            UUID trackedUuid = this.combatLowCloneSupportUUIDs[i];
            if (tracked == null && trackedUuid != null) {
                tracked = serverLevel.getEntity(trackedUuid);
                if (isTrackedCombatLowClone(tracked)) {
                    this.combatLowCloneSupport[i] = tracked;
                } else {
                    this.clearCombatLowCloneSupportSlot(i);
                    continue;
                }
            }

            if (tracked != null && (!tracked.isAlive() || tracked.isRemoved())) {
                this.clearCombatLowCloneSupportSlot(i);
            }
        }
    }

    private static boolean isTrackedCombatLowClone(@Nullable Entity entity) {
        return entity != null
                && entity.isAlive()
                && (entity instanceof LowHerobrineCloneEntity || entity instanceof LowShadowHerobrineCloneEntity);
    }

    private void clearCombatLowCloneSupportSlot(int index) {
        this.combatLowCloneSupport[index] = null;
        this.combatLowCloneSupportUUIDs[index] = null;
    }

    private int getAvailableCombatLowCloneSupportSlot() {
        for (int i = 0; i < MAX_COMBAT_LOW_CLONE_SUPPORT; i++) {
            if (this.combatLowCloneSupportUUIDs[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public int getAvailableCombatLowCloneSupportSlotCount() {
        int count = 0;
        for (UUID uuid : this.combatLowCloneSupportUUIDs) {
            if (uuid == null) {
                count++;
            }
        }
        return count;
    }

    public boolean hasAvailableCombatLowCloneSupportSlot() {
        return this.getAvailableCombatLowCloneSupportSlot() >= 0;
    }

    public boolean claimCombatLowCloneSupportSlot(Entity clone) {
        int slot = this.getAvailableCombatLowCloneSupportSlot();
        if (slot < 0) {
            return false;
        }

        this.combatLowCloneSupport[slot] = clone;
        this.combatLowCloneSupportUUIDs[slot] = clone.getUUID();
        return true;
    }

    private boolean shouldStayNoAiLocked() {
        return this.escapeTiming >= 0
                || (this.isHooked() && !this.hookedWaitingForGround);
    }

    private void playSecondFormSupportCastAnimation() {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
        if (patch != null && !this.level().isClientSide()) {
            patch.playAnimationSynchronized(AnimsSculkSteve.PORTAL_SUMMON, 0.0F);
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return super.hurt(damageSource, amount);
        }

        if (damageSource.is(DamageTypes.FALL)) return false;
        if (damageSource.is(DamageTypes.CACTUS)) return false;
        if (damageSource.is(DamageTypes.WITHER)) return false;
        if (damageSource.is(DamageTypes.DROWN)) return false;
        if (damageSource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damageSource.is(DamageTypes.DRAGON_BREATH)) return false;

        return super.hurt(damageSource, 1.0F);
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        this.hookedWaitingForGround = false;
        this.hookedLeftGround = false;
        this.setHooked(false);
        this.setNoAi(false);
        this.setInvulnerable(false);
        super.die(damageSource);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource damageSource, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(damageSource, looting, recentlyHit);
        if (this.escapeTiming >= 0 || this.fishingHookCancelledEscape) {
            this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get()));
            return;
        }
        if (this.getRandom().nextFloat() < TRANSPORTER_FRAGMENT_DROP_CHANCE) {
            this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get()));
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.escapeTiming = compoundTag.contains("TransporterEscapeTiming") ? compoundTag.getInt("TransporterEscapeTiming") : -1;
        this.escapeRetryCooldown = compoundTag.contains("TransporterEscapeRetryCooldown") ? compoundTag.getInt("TransporterEscapeRetryCooldown") : 0;
        this.supportAvoidRepathCooldown = compoundTag.contains("SupportAvoidRepathCooldown") ? compoundTag.getInt("SupportAvoidRepathCooldown") : 0;
        this.fishingHookCancelledEscape = compoundTag.getBoolean("FishingHookCancelledEscape");
        this.lowCloneSupportCooldown = compoundTag.getInt("LowCloneSupportCooldown");
        this.portalPairCooldown = compoundTag.getInt("PortalPairCooldown");
        this.rangedCounterPortalCooldown = compoundTag.getInt("RangedCounterPortalCooldown");
        this.supportEscapePortalCooldown = compoundTag.getInt("SupportEscapePortalCooldown");
        this.portalEscapeStepBackCooldown = compoundTag.getInt("PortalEscapeStepBackCooldown");
        this.hookedWaitingForGround = compoundTag.getBoolean("HookedWaitingForGround");
        this.hookedLeftGround = compoundTag.getBoolean("HookedLeftGround");
        this.setHooked(compoundTag.getBoolean("Hooked"));
        if (this.isHooked() && this.hookedWaitingForGround) {
            this.releaseHookedPhysicsUntilGround();
        }
        this.setNoAi(this.shouldStayNoAiLocked());
        for (int i = 0; i < MAX_COMBAT_LOW_CLONE_SUPPORT; i++) {
            String key = "CombatLowCloneSupportUUID" + i;
            if (compoundTag.hasUUID(key)) {
                this.combatLowCloneSupportUUIDs[i] = compoundTag.getUUID(key);
            }
        }
        this.enforceTransporterHealthCap();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TransporterEscapeTiming", this.escapeTiming);
        compoundTag.putInt("TransporterEscapeRetryCooldown", this.escapeRetryCooldown);
        compoundTag.putInt("SupportAvoidRepathCooldown", this.supportAvoidRepathCooldown);
        compoundTag.putBoolean("FishingHookCancelledEscape", this.fishingHookCancelledEscape);
        compoundTag.putInt("LowCloneSupportCooldown", this.lowCloneSupportCooldown);
        compoundTag.putInt("PortalPairCooldown", this.portalPairCooldown);
        compoundTag.putInt("RangedCounterPortalCooldown", this.rangedCounterPortalCooldown);
        compoundTag.putInt("SupportEscapePortalCooldown", this.supportEscapePortalCooldown);
        compoundTag.putInt("PortalEscapeStepBackCooldown", this.portalEscapeStepBackCooldown);
        compoundTag.putBoolean("Hooked", this.isHooked());
        compoundTag.putBoolean("HookedWaitingForGround", this.hookedWaitingForGround);
        compoundTag.putBoolean("HookedLeftGround", this.hookedLeftGround);
        for (int i = 0; i < MAX_COMBAT_LOW_CLONE_SUPPORT; i++) {
            if (this.combatLowCloneSupportUUIDs[i] != null) {
                compoundTag.putUUID("CombatLowCloneSupportUUID" + i, this.combatLowCloneSupportUUIDs[i]);
            }
        }
    }

    public static boolean canSpawn(EntityType<TransporterHerobrineCloneEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        int passesDay = (int) (serverLevel.getGameTime() / 24000);
        if (passesDay != 0 && passesDay % 3 != 0) {
            return false;
        }
        if (HerobrineMobData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        if (!serverLevel.isNight()) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
}
