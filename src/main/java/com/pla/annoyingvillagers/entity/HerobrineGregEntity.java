package com.pla.annoyingvillagers.entity;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.blockentity.CryingObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ObsidianBlockEntity;
import com.pla.annoyingvillagers.blockentity.ShadowObsidianBlockEntity;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AnimsSculkSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.HerobrinePortalCombatUtil;
import com.pla.annoyingvillagers.util.HerobrinePortalUtil;
import com.pla.annoyingvillagers.spawnhandler.GregData;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.util.ChatUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.*;

public class HerobrineGregEntity extends Monster {
    private static final EntityDataAccessor<Boolean> WHITE_EYE =
            SynchedEntityData.defineId(HerobrineGregEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> USE_HEROBRINE_TEXTURE =
            SynchedEntityData.defineId(HerobrineGregEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SUPPORTING_HEROBRINE =
            SynchedEntityData.defineId(HerobrineGregEntity.class, EntityDataSerializers.BOOLEAN);
    private static final int TICKS_PER_SECOND = 20;
    private static final int PORTAL_SUPPORT_COOLDOWN_MIN_TICKS = 15 * TICKS_PER_SECOND;
    private static final int PORTAL_SUPPORT_COOLDOWN_MAX_TICKS = 30 * TICKS_PER_SECOND;
    private static final int PORTAL_SUPPORT_RETRY_TICKS = 10 * TICKS_PER_SECOND;
    private static final int SIX_PORTAL_SUPPORT_COOLDOWN_MIN_TICKS = 60 * TICKS_PER_SECOND;
    private static final int SIX_PORTAL_SUPPORT_COOLDOWN_MAX_TICKS = 120 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS = 90 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUPPORT_COOLDOWN_MAX_TICKS = 180 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUPPORT_RETRY_TICKS = 10 * TICKS_PER_SECOND;
    private static final int RANGED_COUNTER_PORTAL_COOLDOWN_MIN_TICKS = 10 * TICKS_PER_SECOND;
    private static final int RANGED_COUNTER_PORTAL_COOLDOWN_MAX_TICKS = 15 * TICKS_PER_SECOND;
    private static final int RANGED_COUNTER_PORTAL_RETRY_TICKS = 3 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUPPORT_ENEMY_RADIUS = 48;
    private static final int LOW_CLONE_SUPPORT_SPAWN_ATTEMPTS = 24;
    private static final int SUPPORT_REPOSITION_RETRY_TICKS = 6 * TICKS_PER_SECOND;
    private static final int SUPPORT_REPOSITION_MIN_COOLDOWN_TICKS = 35 * TICKS_PER_SECOND;
    private static final int SUPPORT_REPOSITION_MAX_COOLDOWN_TICKS = 70 * TICKS_PER_SECOND;
    private static final int ACTIVE_SUPPORT_RETREAT_TICKS = 90;
    private static final int PORTAL_PAIR_CAST_TICKS = 18;
    private static final int SIX_PORTAL_CAST_TICKS = 30;
    private static final int PORTAL_SUMMON_AI_LOCK_TICKS = 5 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUMMON_WEAK_POINT_TICKS = 5 * TICKS_PER_SECOND;
    private static final int QUEUED_SIX_PORTAL_SUPPORT_LOCK_TICKS = 12 * TICKS_PER_SECOND;
    private static final int REQUESTED_SUPPORT_PORTAL_TICKS = 4 * TICKS_PER_SECOND;
    private static final int SUPPORT_CATCH_UP_COOLDOWN_MIN_TICKS = 8 * TICKS_PER_SECOND;
    private static final int SUPPORT_CATCH_UP_COOLDOWN_MAX_TICKS = 14 * TICKS_PER_SECOND;
    private static final int SUPPORT_CATCH_UP_RETRY_TICKS = 4 * TICKS_PER_SECOND;
    private static final byte SUPPORT_PORTAL_REQUEST_NONE = 0;
    private static final byte SUPPORT_PORTAL_REQUEST_APPROACH = 1;
    private static final byte SUPPORT_PORTAL_REQUEST_RETREAT = 2;
    private static final double QUEUED_SIX_PORTAL_SUPPORT_READY_DISTANCE_SQR = 4.0D * 4.0D;
    private boolean summoning = false;
    private int summonTiming = -1;
    private int escapeTiming = -1;
    private int summonTimestamp = -1;
    private boolean combatMode = false;
    private int recallTime;
    private int portalSupportCooldown = PORTAL_SUPPORT_COOLDOWN_MIN_TICKS;
    private int sixPortalSupportCooldown;
    private int lowCloneSupportCooldown = LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS;
    private int rangedCounterPortalCooldown = RANGED_COUNTER_PORTAL_RETRY_TICKS;
    private int portalCastTicks;
    private int portalSummonAiLockTicks;
    private int lowCloneSummonWeakPointTicks;
    private int queuedSixPortalSupportLockTicks;
    private int requestedSupportPortalTicks;
    private int idleAvoidRepathCooldown;
    private int supportRepositionCooldown = SUPPORT_REPOSITION_RETRY_TICKS;
    private int supportCatchUpCooldown = SUPPORT_CATCH_UP_RETRY_TICKS;
    private int supportRetreatPanicTicks;
    private int activeSupportRetreatTicks;
    private byte requestedSupportPortalMode;
    @Nullable
    private Vec3 activeSupportRetreatPos;
    private long lastRetreatPortalAssistTick = Long.MIN_VALUE;
    private long lastApproachPortalAssistTick = Long.MIN_VALUE;
    private int supportingHerobrineVisualTicks;
    @Nullable
    private UUID queuedSixPortalSupportTargetUUID;
    @Nullable
    private UUID requestedSupportPortalSupportUUID;
    @Nullable
    private UUID requestedSupportPortalTargetUUID;
    private static final double SUPPORT_SEARCH_RADIUS = 40.0D;
    private static final double SUPPORT_STAND_DISTANCE_SQR = 10.0D * 10.0D;
    private static final double SUPPORT_STAND_RADIUS = 7.0D;
    private static final double SUPPORT_DANGER_STAND_RADIUS = 15.0D;
    private static final double SUPPORT_DANGER_STAND_DISTANCE_SQR = 18.0D * 18.0D;
    private static final double SUPPORT_SAFE_ENEMY_SEARCH_RADIUS = 24.0D;
    private static final double SUPPORT_SAFE_DISTANCE_SQR = 12.0D * 12.0D;
    private static final double SUPPORT_STAND_REACHED_DISTANCE_SQR = 2.0D * 2.0D;
    private static final double SUPPORT_MOVE_SPEED = 1.15D;
    private static final double SUPPORT_DANGER_MOVE_SPEED = 1.25D;
    private static final double SUPPORT_ACTIVE_RETREAT_MOVE_SPEED = 1.3D;
    private static final double SUPPORT_CATCH_UP_DISTANCE_SQR = 50.0D * 50.0D;
    private static final double SUPPORT_CATCH_UP_SEARCH_RADIUS = 96.0D;
    private static final double IDLE_AVOID_SEARCH_RADIUS = 32.0D;
    private static final double IDLE_AVOID_TRIGGER_DISTANCE_SQR = 18.0D * 18.0D;
    private static final double IDLE_AVOID_MIN_DISTANCE = 12.0D;
    private static final double IDLE_AVOID_MAX_DISTANCE = 20.0D;
    private static final int IDLE_AVOID_REPATH_TICKS = 15;
    private static final double IDLE_AVOID_MOVE_SPEED = 1.15D;
    private static final double AVOID_WALK_SPEED = 1.0D;
    private static final double AVOID_SPRINT_SPEED = 1.35D;
    private static final double LOW_CLONE_SUPPORT_TRIGGER_DISTANCE_SQR = 24.0D * 24.0D;
    private static final double SUPPORT_REPOSITION_GREG_DANGER_SQR = 12.0D * 12.0D;
    private static final double SUPPORT_REPOSITION_SUPPORT_DANGER_SQR = 10.0D * 10.0D;
    private static final double SUPPORT_REPOSITION_RETREAT_MIN_DISTANCE = 16.0D;
    private static final double SUPPORT_REPOSITION_RETREAT_MAX_DISTANCE = 24.0D;
    private static final double SUPPORT_REPOSITION_RETURN_SIDE_DISTANCE = 6.0D;
    private static final int SUPPORT_SAFE_SAMPLE_COUNT = 16;
    private static final int SUPPORT_STAND_REPATH_TICKS = 30;
    private static final int SUPPORT_DANGER_REPATH_TICKS = 8;
    private static final int SUPPORT_VISUAL_TICKS = 40;

    private Entity firstSummonedHerobrine;
    private Entity secondSummonedHerobrine;
    private Entity thirdSummonedHerobrine;

    private UUID firstSummonedHerobrineUUID;
    private UUID secondSummonedHerobrineUUID;
    private UUID thirdSummonedHerobrineUUID;

    private BlockPos lastFeetPos = null;
    private String chatName;

    private final List<Item> listWeapons = new ArrayList<>(Arrays.asList(
            Items.DIAMOND_SWORD,
            Items.DIAMOND_AXE,
            AnnoyingVillagersModItems.DIAMOND_ATTRACTOR_SWORD.get(),
            AnnoyingVillagersModItems.DIAMOND_BLASTER_SWORD.get(),
            AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD.get(),
            AnnoyingVillagersModItems.DIAMOND_WARBLADE.get(),
            AnnoyingVillagersModItems.DIAMOND_FALCHION.get(),
            AnnoyingVillagersModItems.DIAMOND_GREAT_FALCHION.get(),
            AnnoyingVillagersModItems.DIAMOND_SABRE.get(),
            AnnoyingVillagersModItems.DIAMOND_LONGSWORD.get(),
            AnnoyingVillagersModItems.DIAMOND_CHIPPED_LONGSWORD.get(),
            AnnoyingVillagersModItems.PALADIN_SWORD.get(),
            AnnoyingVillagersModItems.DIAMOND_GREATAXE.get(),
            AnnoyingVillagersModItems.DIAMOND_ARMBLADE.get(),
            AnnoyingVillagersModItems.DIAMOND_SICKLE.get(),
            AnnoyingVillagersModItems.DOUBLE_DIAMOND_GLAIVE.get(),
            AnnoyingVillagersModItems.DIAMOND_MOON_BLADE.get()
    ));

    @Nullable
    public LivingEntityPatch<?> getLivingEntityPatch() {
        return EpicFightCapabilities.getEntityPatch(this, LivingEntityPatch.class);
    }

    public void setWhiteEye(boolean whiteEye) {
        this.entityData.set(WHITE_EYE, whiteEye);
    }

    public boolean isWhiteEye() {
        return this.entityData.get(WHITE_EYE);
    }

    public void setUseHerobrineTexture(boolean useHerobrineTexture) {
        this.entityData.set(USE_HEROBRINE_TEXTURE, useHerobrineTexture);
    }

    public int getEscapeTiming() {
        return escapeTiming;
    }

    public void setEscapeTiming(int escapeTiming) {
        this.escapeTiming = escapeTiming;
    }

    public boolean isUseHerobrineTexture() { return this.entityData.get(USE_HEROBRINE_TEXTURE); }

    public void markSupportingHerobrine() {
        this.supportingHerobrineVisualTicks = SUPPORT_VISUAL_TICKS;
        this.setSupportingHerobrine(true);
    }

    public boolean isSupportingHerobrine() {
        return this.entityData.get(SUPPORTING_HEROBRINE);
    }

    private void setSupportingHerobrine(boolean supportingHerobrine) {
        this.entityData.set(SUPPORTING_HEROBRINE, supportingHerobrine);
    }

    private int randomPortalSupportCooldown() {
        return PORTAL_SUPPORT_COOLDOWN_MIN_TICKS
                + this.random.nextInt(PORTAL_SUPPORT_COOLDOWN_MAX_TICKS - PORTAL_SUPPORT_COOLDOWN_MIN_TICKS + 1);
    }

    private int randomSixPortalSupportCooldown() {
        return SIX_PORTAL_SUPPORT_COOLDOWN_MIN_TICKS
                + this.random.nextInt(SIX_PORTAL_SUPPORT_COOLDOWN_MAX_TICKS - SIX_PORTAL_SUPPORT_COOLDOWN_MIN_TICKS + 1);
    }

    private int randomLowCloneSupportCooldown() {
        return LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS
                + this.random.nextInt(LOW_CLONE_SUPPORT_COOLDOWN_MAX_TICKS - LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS + 1);
    }

    private int randomRangedCounterPortalCooldown() {
        return RANGED_COUNTER_PORTAL_COOLDOWN_MIN_TICKS
                + this.random.nextInt(RANGED_COUNTER_PORTAL_COOLDOWN_MAX_TICKS - RANGED_COUNTER_PORTAL_COOLDOWN_MIN_TICKS + 1);
    }

    private int randomSupportRepositionCooldown() {
        return SUPPORT_REPOSITION_MIN_COOLDOWN_TICKS
                + this.random.nextInt(SUPPORT_REPOSITION_MAX_COOLDOWN_TICKS - SUPPORT_REPOSITION_MIN_COOLDOWN_TICKS + 1);
    }

    private int randomSupportCatchUpCooldown() {
        return SUPPORT_CATCH_UP_COOLDOWN_MIN_TICKS
                + this.random.nextInt(SUPPORT_CATCH_UP_COOLDOWN_MAX_TICKS - SUPPORT_CATCH_UP_COOLDOWN_MIN_TICKS + 1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WHITE_EYE, false);
        this.entityData.define(USE_HEROBRINE_TEXTURE, false);
        this.entityData.define(SUPPORTING_HEROBRINE, false);
    }

    public boolean isSummoning() {
        return summoning;
    }

    public void requestApproachPortalFor(@Nullable LivingEntity support, @Nullable LivingEntity target) {
        queueSupportPortalRequest(SUPPORT_PORTAL_REQUEST_APPROACH, support, target);
    }

    public void requestRetreatPortalFor(@Nullable LivingEntity support, @Nullable LivingEntity threat) {
        queueSupportPortalRequest(SUPPORT_PORTAL_REQUEST_RETREAT, support, threat);
    }

    public boolean canUseSixPortalSupport() {
        return this.sixPortalSupportCooldown <= 0 && this.portalCastTicks <= 0;
    }

    public boolean canAnswerSixPortalSupportRequest() {
        return this.canUseSixPortalSupport()
                && !this.summoning
                && this.escapeTiming < 0
                && this.summonTiming < 0
                && !this.isNoAi()
                && !this.isPortalSummonAiLocked();
    }

    public void onSixPortalSupportUsed() {
        this.sixPortalSupportCooldown = randomSixPortalSupportCooldown();
    }

    public void reserveSixPortalSupport(@Nullable LivingEntity support) {
        if (support == null || !support.isAlive()) {
            return;
        }
        this.queuedSixPortalSupportTargetUUID = support.getUUID();
        this.queuedSixPortalSupportLockTicks = Math.max(this.queuedSixPortalSupportLockTicks, QUEUED_SIX_PORTAL_SUPPORT_LOCK_TICKS);
        this.markSupportingHerobrine();
    }

    public void clearSixPortalSupportReservation() {
        this.queuedSixPortalSupportTargetUUID = null;
        this.queuedSixPortalSupportLockTicks = 0;
    }

    private void queueSupportPortalRequest(byte mode, @Nullable LivingEntity support, @Nullable LivingEntity target) {
        if (mode == SUPPORT_PORTAL_REQUEST_NONE
                || support == null
                || target == null
                || !support.isAlive()
                || !target.isAlive()
                || support.level() != this.level()
                || target.level() != this.level()
                || isRidingHerobrineDragon(support)) {
            return;
        }

        this.requestedSupportPortalMode = mode;
        this.requestedSupportPortalSupportUUID = support.getUUID();
        this.requestedSupportPortalTargetUUID = target.getUUID();
        this.requestedSupportPortalTicks = Math.max(this.requestedSupportPortalTicks, REQUESTED_SUPPORT_PORTAL_TICKS);
        this.markSupportingHerobrine();
    }

    private void clearSupportPortalRequest() {
        this.requestedSupportPortalMode = SUPPORT_PORTAL_REQUEST_NONE;
        this.requestedSupportPortalSupportUUID = null;
        this.requestedSupportPortalTargetUUID = null;
        this.requestedSupportPortalTicks = 0;
    }

    public boolean isSixPortalSupportReserved() {
        return this.queuedSixPortalSupportLockTicks > 0 && this.queuedSixPortalSupportTargetUUID != null;
    }

    public boolean isPortalCasting() {
        return this.portalCastTicks > 0;
    }

    public void startPortalPairCast() {
        this.startPortalCast(PORTAL_PAIR_CAST_TICKS);
    }

    public void startSixPortalCast() {
        this.startPortalCast(SIX_PORTAL_CAST_TICKS);
    }

    public boolean isPortalSummonAiLocked() {
        return this.portalSummonAiLockTicks > 0;
    }

    public boolean isLowCloneSummonWeakPointActive() {
        return this.lowCloneSummonWeakPointTicks > 0;
    }

    public void beginPortalSummonAiLock() {
        this.portalSummonAiLockTicks = Math.max(this.portalSummonAiLockTicks, PORTAL_SUMMON_AI_LOCK_TICKS);
        this.setNoAi(true);
        this.getNavigation().stop();
        this.setSprinting(false);
        this.xxa = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);
    }

    private void startLowCloneSummonWeakPoint() {
        this.lowCloneSummonWeakPointTicks = Math.max(this.lowCloneSummonWeakPointTicks, LOW_CLONE_SUMMON_WEAK_POINT_TICKS);
        this.setNoAi(true);
        this.getNavigation().stop();
        this.setSprinting(false);
        this.xxa = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);
    }

    private void startPortalCast(int ticks) {
        this.portalCastTicks = Math.max(this.portalCastTicks, ticks);
        this.markSupportingHerobrine();
        this.getNavigation().stop();
        this.xxa = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);
    }

    private void tickPortalSummonAiLock() {
        if (this.portalSummonAiLockTicks <= 0) {
            return;
        }

        this.portalSummonAiLockTicks--;
        this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 3, false, false));
        this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 2, 3, false, false));
        this.setNoAi(true);
        this.getNavigation().stop();
        this.setSprinting(false);
        this.xxa = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);
        if (this.getTarget() != null && this.getTarget().isAlive()) {
            this.getLookControl().setLookAt(this.getTarget(), 30.0F, 30.0F);
        }

        if (this.portalSummonAiLockTicks == 0 && !this.shouldStayNoAiWithoutPortalLock()) {
            this.setNoAi(false);
        }
    }

    private void tickLowCloneSummonWeakPoint() {
        if (this.lowCloneSummonWeakPointTicks <= 0) {
            return;
        }

        this.lowCloneSummonWeakPointTicks--;
        this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 3, false, false));
        this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 2, 3, false, false));
        this.setNoAi(true);
        this.getNavigation().stop();
        this.setSprinting(false);
        this.setTarget(null);
        this.xxa = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);

        if (this.lowCloneSummonWeakPointTicks == 0 && !this.shouldStayNoAiWithoutPortalLock()) {
            this.setNoAi(false);
        }
    }

    private void tickQueuedSixPortalSupportReservation() {
        if (this.queuedSixPortalSupportLockTicks <= 0 || this.queuedSixPortalSupportTargetUUID == null) {
            this.clearSixPortalSupportReservation();
            return;
        }

        this.queuedSixPortalSupportLockTicks--;
        LivingEntity support = getQueuedSixPortalSupportTarget();
        if (support == null) {
            this.clearSixPortalSupportReservation();
            return;
        }

        this.markSupportingHerobrine();
        this.getLookControl().setLookAt(support, 30.0F, 30.0F);
        if (!this.isPortalCasting()
                && !this.isPortalSummonAiLocked()
                && this.distanceToSqr(support) > QUEUED_SIX_PORTAL_SUPPORT_READY_DISTANCE_SQR) {
            this.getNavigation().moveTo(support, 1.35D);
        }

        if (this.queuedSixPortalSupportLockTicks == 0) {
            this.clearSixPortalSupportReservation();
        }
    }

    private boolean tickRequestedSupportPortalAction() {
        if (this.requestedSupportPortalTicks <= 0
                || this.requestedSupportPortalMode == SUPPORT_PORTAL_REQUEST_NONE
                || this.requestedSupportPortalSupportUUID == null
                || this.requestedSupportPortalTargetUUID == null
                || !(this.level() instanceof ServerLevel serverLevel)) {
            this.clearSupportPortalRequest();
            return false;
        }

        this.requestedSupportPortalTicks--;
        Entity supportEntity = serverLevel.getEntity(this.requestedSupportPortalSupportUUID);
        Entity targetEntity = serverLevel.getEntity(this.requestedSupportPortalTargetUUID);
        if (!(supportEntity instanceof LivingEntity support)
                || !(targetEntity instanceof LivingEntity target)
                || !support.isAlive()
                || !target.isAlive()
                || isRidingHerobrineDragon(support)) {
            this.clearSupportPortalRequest();
            return false;
        }

        if (this.distanceToSqr(support) > SUPPORT_CATCH_UP_DISTANCE_SQR) {
            if (this.supportCatchUpCooldown <= 0 && this.tryOpenSupportCatchUpPortal(serverLevel, support)) {
                this.supportCatchUpCooldown = randomSupportCatchUpCooldown();
                this.requestedSupportPortalTicks = Math.max(this.requestedSupportPortalTicks, TICKS_PER_SECOND);
                return true;
            }
            if (this.requestedSupportPortalTicks <= 0) {
                this.clearSupportPortalRequest();
            }
            return false;
        }

        boolean activated = this.requestedSupportPortalMode == SUPPORT_PORTAL_REQUEST_RETREAT
                ? this.tryOpenRetreatPortalFor(support, target)
                : this.tryOpenApproachPortalFor(support, target);
        if (activated || this.requestedSupportPortalTicks <= 0) {
            this.clearSupportPortalRequest();
        }
        return activated;
    }

    private boolean tickFarSupportPortalCatchUp() {
        if (this.supportCatchUpCooldown > 0) {
            return false;
        }
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        LivingEntity support = this.findCatchUpSupport(serverLevel);
        if (support == null || !support.isAlive() || isRidingHerobrineDragon(support)) {
            return false;
        }
        if (this.distanceToSqr(support) <= SUPPORT_CATCH_UP_DISTANCE_SQR) {
            return false;
        }
        if (!this.tryOpenSupportCatchUpPortal(serverLevel, support)) {
            return false;
        }

        this.supportCatchUpCooldown = randomSupportCatchUpCooldown();
        return true;
    }

    @Nullable
    private LivingEntity findCatchUpSupport(ServerLevel serverLevel) {
        LivingEntity linkedSupport = this.findLinkedSupportHerobrine(serverLevel, SUPPORT_CATCH_UP_SEARCH_RADIUS);
        if (linkedSupport != null) {
            return linkedSupport;
        }

        LivingEntity nearbySupport = HerobrinePortalCombatUtil.findPortalSupportHerobrine(this, SUPPORT_CATCH_UP_SEARCH_RADIUS);
        if (nearbySupport != null && nearbySupport.isAlive() && !isRidingHerobrineDragon(nearbySupport)) {
            return nearbySupport;
        }
        return null;
    }

    @Nullable
    private LivingEntity findLinkedSupportHerobrine(ServerLevel serverLevel, double radius) {
        UUID gregUuid = this.getUUID();
        LivingEntity nearest = null;
        double nearestDistanceSqr = Double.MAX_VALUE;
        for (HerobrineMob herobrineMob : serverLevel.getEntitiesOfClass(HerobrineMob.class, this.getBoundingBox().inflate(radius),
                mob -> mob.isAlive() && gregUuid.equals(mob.getGregUUID()))) {
            if (isRidingHerobrineDragon(herobrineMob)) {
                continue;
            }
            double distanceSqr = this.distanceToSqr(herobrineMob);
            if (distanceSqr < nearestDistanceSqr) {
                nearest = herobrineMob;
                nearestDistanceSqr = distanceSqr;
            }
        }
        return nearest;
    }

    private boolean tryOpenSupportCatchUpPortal(ServerLevel serverLevel, LivingEntity support) {
        if (!TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this, 2)) {
            return false;
        }

        Vec3 exit = this.findCatchUpExitPosition(serverLevel, support);
        if (exit == null) {
            return false;
        }

        int spawned = TransporterFragmentItem.spawnLinkedPortalPair(
                this.level(),
                this,
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, this.position()),
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, exit)
        );
        if (spawned <= 0) {
            return false;
        }

        this.markSupportingHerobrine();
        this.getNavigation().stop();
        this.teleportTo(exit.x, exit.y, exit.z);
        this.setDeltaMovement(Vec3.ZERO);
        this.hasImpulse = true;
        this.getLookControl().setLookAt(support, 30.0F, 30.0F);
        HerobrinePortalCombatUtil.playPortalPairSummon(this);
        return true;
    }

    @Nullable
    private Vec3 findCatchUpExitPosition(ServerLevel serverLevel, LivingEntity support) {
        Vec3 away = horizontalDirection(this.position().subtract(support.position()));
        if (away.lengthSqr() < 1.0E-4D) {
            away = horizontalDirection(support.getLookAngle().scale(-1.0D));
        }
        if (away.lengthSqr() < 1.0E-4D) {
            away = new Vec3(1.0D, 0.0D, 0.0D);
        }

        for (int attempt = 0; attempt < 12; attempt++) {
            double turn = (this.random.nextDouble() - 0.5D) * 1.6D;
            Vec3 direction = rotateHorizontal(away, turn);
            double distance = 1.75D + this.random.nextDouble() * 2.0D;
            Vec3 raw = support.position().add(direction.scale(distance));
            Vec3 candidate = surfacePosition(serverLevel, raw.x, raw.z);
            if (isValidSupportRetreatPosition(serverLevel, candidate)) {
                return candidate;
            }
        }

        Vec3 fallback = surfacePosition(serverLevel, support.getX(), support.getZ());
        return isValidSupportRetreatPosition(serverLevel, fallback) ? fallback : null;
    }

    @Nullable
    private LivingEntity getQueuedSixPortalSupportTarget() {
        if (this.queuedSixPortalSupportTargetUUID == null || !(this.level() instanceof ServerLevel serverLevel)) {
            return null;
        }
        Entity entity = serverLevel.getEntity(this.queuedSixPortalSupportTargetUUID);
        return entity instanceof LivingEntity livingEntity && livingEntity.isAlive() ? livingEntity : null;
    }

    private boolean shouldStayNoAiWithoutPortalLock() {
        return this.summoning
                || this.summonTiming >= 0
                || this.escapeTiming >= 0
                || this.lowCloneSummonWeakPointTicks > 0;
    }

    public void setSummoning(boolean summoning) {
        this.summoning = summoning;
    }

    public int getSummonTimestamp() {
        return summonTimestamp;
    }

    public HerobrineGregEntity(SpawnEntity spawnentity, Level level) {
        this(AnnoyingVillagersModEntities.HEROBRINE_GREG.get(), level);
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public HerobrineGregEntity(EntityType<HerobrineGregEntity> entitytype, Level level) {
        super(entitytype, level);
        this.setMaxUpStep(2.5F);
        this.xpReward = 50;
        this.setNoAi(false);
        this.setPersistenceRequired();
        this.setCustomName(Component.literal("Greg"));
        this.setChatName(this.getDisplayName().getString());
        this.setCustomNameVisible(true);

        int min = AnnoyingVillagersConfig.HEROBRINE_RECALL_MIN_TIME.get();
        int max = AnnoyingVillagersConfig.HEROBRINE_RECALL_MAX_TIME.get();
        int randomMin = Math.min(min, max);
        int randomMax = Math.max(min, max);
        this.recallTime = (randomMin + new Random().nextInt(randomMax - randomMin + 1)) * 60 * 20;
        this.portalSupportCooldown = randomPortalSupportCooldown();
        this.lowCloneSupportCooldown = randomLowCloneSupportCooldown();
        this.rangedCounterPortalCooldown = randomRangedCounterPortalCooldown();

        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new Goal() {
            private LivingEntity support;
            private Vec3 standPosition;
            private int repathCooldown;

            {
                this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            }

            @Override
            public boolean canUse() {
                if (!canMoveForSupport()) {
                    return false;
                }

                this.support = HerobrinePortalCombatUtil.findPortalSupportHerobrine(HerobrineGregEntity.this, SUPPORT_SEARCH_RADIUS);
                return isValidSupport(this.support);
            }

            @Override
            public boolean canContinueToUse() {
                return canMoveForSupport()
                        && isValidSupport(this.support)
                        && distanceToSqr(this.support) <= SUPPORT_SEARCH_RADIUS * SUPPORT_SEARCH_RADIUS;
            }

            @Override
            public void tick() {
                if (!isValidSupport(this.support)) {
                    return;
                }

                markSupportingHerobrine();
                getLookControl().setLookAt(this.support, 30.0F, 30.0F);
                double distanceSqr = distanceToSqr(this.support);
                LivingEntity threat = findNearestSupportThreat(this.support);
                if (activeSupportRetreatTicks > 0 && activeSupportRetreatPos != null) {
                    activeSupportRetreatTicks--;
                    if (threat != null) {
                        getLookControl().setLookAt(threat, 30.0F, 30.0F);
                    }
                    if (position().distanceToSqr(activeSupportRetreatPos) > SUPPORT_STAND_REACHED_DISTANCE_SQR) {
                        getNavigation().moveTo(activeSupportRetreatPos.x, activeSupportRetreatPos.y, activeSupportRetreatPos.z, SUPPORT_ACTIVE_RETREAT_MOVE_SPEED);
                    } else {
                        getNavigation().stop();
                    }
                    return;
                }

                boolean currentSpotSafe = isCurrentSupportSpotSafe(this.support, threat);
                double maxStandDistanceSqr = threat == null ? SUPPORT_STAND_DISTANCE_SQR : SUPPORT_DANGER_STAND_DISTANCE_SQR;
                if (distanceSqr <= maxStandDistanceSqr && currentSpotSafe) {
                    getNavigation().stop();
                    this.repathCooldown = SUPPORT_STAND_REPATH_TICKS;
                    return;
                }
                if (this.repathCooldown-- <= 0 || getNavigation().isDone() || hasReachedStandPosition()) {
                    moveToSupportStandPosition(this.support, threat);
                }
            }

            private boolean canMoveForSupport() {
                return !summoning && escapeTiming < 0 && summonTiming < 0 && !isNoAi() && !isPortalCasting();
            }

            private boolean isValidSupport(@Nullable LivingEntity entity) {
                return entity != null
                        && entity.isAlive()
                        && !(entity.isPassenger() && entity.getVehicle() instanceof HerobrineDragonEntity);
            }

            @Nullable
            private LivingEntity findNearestSupportThreat(LivingEntity support) {
                LivingEntity threat = HerobrinePortalCombatUtil.findThreateningEnemy(
                        HerobrineGregEntity.this,
                        support,
                        SUPPORT_SAFE_ENEMY_SEARCH_RADIUS
                );
                if (threat != null) {
                    return threat;
                }
                return HerobrinePortalCombatUtil.findEnemyForSupport(support, getTarget(), SUPPORT_SAFE_ENEMY_SEARCH_RADIUS);
            }

            private boolean isCurrentSupportSpotSafe(LivingEntity support, @Nullable LivingEntity threat) {
                double maxSupportDistanceSqr = threat == null ? SUPPORT_STAND_DISTANCE_SQR : SUPPORT_DANGER_STAND_DISTANCE_SQR;
                if (distanceToSqr(support) > maxSupportDistanceSqr) {
                    return false;
                }
                return threat == null || distanceToSqr(threat) >= SUPPORT_SAFE_DISTANCE_SQR;
            }

            private boolean hasReachedStandPosition() {
                return this.standPosition != null
                        && position().distanceToSqr(this.standPosition) <= SUPPORT_STAND_REACHED_DISTANCE_SQR;
            }

            private void moveToSupportStandPosition(LivingEntity support, @Nullable LivingEntity threat) {
                this.standPosition = findSupportStandPosition(support, threat);
                getNavigation().moveTo(this.standPosition.x, this.standPosition.y, this.standPosition.z,
                        threat == null ? SUPPORT_MOVE_SPEED : SUPPORT_DANGER_MOVE_SPEED);
                this.repathCooldown = threat == null
                        ? SUPPORT_STAND_REPATH_TICKS + random.nextInt(10)
                        : SUPPORT_DANGER_REPATH_TICKS + random.nextInt(5);
            }

            private Vec3 findSupportStandPosition(LivingEntity support, @Nullable LivingEntity threat) {
                double baseAngle = Math.atan2(getZ() - support.getZ(), getX() - support.getX());
                if (Double.isNaN(baseAngle)) {
                    baseAngle = random.nextDouble() * Math.PI * 2.0D;
                }
                double standRadius = threat == null ? SUPPORT_STAND_RADIUS : SUPPORT_DANGER_STAND_RADIUS;

                if (threat == null) {
                    Vec3 currentSidePosition = standPositionAt(support, baseAngle, standRadius);
                    if (currentSidePosition != null) {
                        return currentSidePosition;
                    }
                    return new Vec3(support.getX(), support.getY(), support.getZ());
                }

                double awayFromThreatAngle = Math.atan2(support.getZ() - threat.getZ(), support.getX() - threat.getX());
                if (Double.isNaN(awayFromThreatAngle)) {
                    awayFromThreatAngle = baseAngle;
                }

                Vec3 bestPosition = null;
                double bestScore = Double.NEGATIVE_INFINITY;
                for (int sample = 0; sample < SUPPORT_SAFE_SAMPLE_COUNT; sample++) {
                    double angle = awayFromThreatAngle + (Math.PI * 2.0D * sample / SUPPORT_SAFE_SAMPLE_COUNT);
                    Vec3 candidate = standPositionAt(support, angle, standRadius);
                    if (candidate == null) {
                        continue;
                    }

                    double threatDistanceSqr = candidate.distanceToSqr(threat.position());
                    double movePenalty = candidate.distanceToSqr(position()) * 0.08D;
                    double score = threatDistanceSqr - movePenalty;
                    if (threatDistanceSqr < SUPPORT_SAFE_DISTANCE_SQR) {
                        score -= 300.0D;
                    }
                    if (score > bestScore) {
                        bestScore = score;
                        bestPosition = candidate;
                    }
                }

                if (bestPosition != null) {
                    return bestPosition;
                }
                Vec3 fallback = standPositionAt(support, awayFromThreatAngle, standRadius);
                return fallback != null ? fallback : new Vec3(support.getX(), support.getY(), support.getZ());
            }

            @Nullable
            private Vec3 standPositionAt(LivingEntity support, double angle, double radius) {
                double x = support.getX() + Math.cos(angle) * radius;
                double z = support.getZ() + Math.sin(angle) * radius;
                double y = support.getY();
                if (level() instanceof ServerLevel serverLevel) {
                    y = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(x, y, z)).getY();
                }

                Vec3 candidate = new Vec3(x, y, z);
                AABB movedBox = getBoundingBox().move(candidate.subtract(position()));
                return level().noCollision(HerobrineGregEntity.this, movedBox) ? candidate : null;
            }
        });
        this.goalSelector.addGoal(1, new Goal() {
            private LivingEntity threat;

            {
                this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            }

            @Override
            public boolean canUse() {
                if (!canIdleAvoid()) {
                    return false;
                }

                this.threat = findIdleThreat();
                return this.threat != null
                        && this.threat.isAlive()
                        && distanceToSqr(this.threat) <= IDLE_AVOID_TRIGGER_DISTANCE_SQR;
            }

            @Override
            public boolean canContinueToUse() {
                return canIdleAvoid()
                        && this.threat != null
                        && this.threat.isAlive()
                        && distanceToSqr(this.threat) <= IDLE_AVOID_SEARCH_RADIUS * IDLE_AVOID_SEARCH_RADIUS;
            }

            @Override
            public void tick() {
                if (idleAvoidRepathCooldown > 0) {
                    idleAvoidRepathCooldown--;
                }

                this.threat = findIdleThreat();
                setTarget(null);
                if (this.threat == null || !this.threat.isAlive()) {
                    return;
                }

                getLookControl().setLookAt(this.threat, 30.0F, 30.0F);
                if (distanceToSqr(this.threat) > IDLE_AVOID_TRIGGER_DISTANCE_SQR) {
                    return;
                }
                if (idleAvoidRepathCooldown > 0 && !getNavigation().isDone()) {
                    return;
                }

                Vec3 retreatPos = findIdleRetreatPosition(this.threat);
                if (retreatPos == null) {
                    return;
                }

                getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, IDLE_AVOID_MOVE_SPEED);
                idleAvoidRepathCooldown = IDLE_AVOID_REPATH_TICKS;
            }

            private boolean canIdleAvoid() {
                return !combatMode
                        && !summoning
                        && escapeTiming < 0
                        && summonTiming < 0
                        && !isNoAi()
                        && !isPortalCasting()
                        && !isSupportingHerobrine()
                        && HerobrinePortalCombatUtil.findPortalSupportHerobrine(HerobrineGregEntity.this, SUPPORT_SEARCH_RADIUS) == null;
            }

            @Nullable
            private LivingEntity findIdleThreat() {
                LivingEntity threat = HerobrinePortalCombatUtil.findThreateningEnemy(
                        HerobrineGregEntity.this,
                        null,
                        IDLE_AVOID_SEARCH_RADIUS
                );
                if (threat != null) {
                    return threat;
                }
                return HerobrinePortalCombatUtil.findEnemyForSupport(
                        HerobrineGregEntity.this,
                        null,
                        IDLE_AVOID_SEARCH_RADIUS
                );
            }

            @Nullable
            private Vec3 findIdleRetreatPosition(LivingEntity threat) {
                if (!(level() instanceof ServerLevel serverLevel)) {
                    return null;
                }

                double awayAngle = Math.atan2(getZ() - threat.getZ(), getX() - threat.getX());
                if (Double.isNaN(awayAngle)) {
                    awayAngle = getRandom().nextDouble() * Math.PI * 2.0D;
                }

                for (int attempt = 0; attempt < 10; attempt++) {
                    double angle = awayAngle + (getRandom().nextDouble() - 0.5D) * 1.4D;
                    double distance = IDLE_AVOID_MIN_DISTANCE
                            + getRandom().nextDouble() * (IDLE_AVOID_MAX_DISTANCE - IDLE_AVOID_MIN_DISTANCE);
                    double x = getX() + Math.cos(angle) * distance;
                    double z = getZ() + Math.sin(angle) * distance;
                    BlockPos surface = serverLevel.getHeightmapPos(
                            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                            BlockPos.containing(x, getY(), z)
                    );

                    if (!serverLevel.isLoaded(surface) || !serverLevel.getWorldBorder().isWithinBounds(surface)) {
                        continue;
                    }
                    if (!serverLevel.isEmptyBlock(surface)
                            || !serverLevel.isEmptyBlock(surface.above())
                            || serverLevel.isEmptyBlock(surface.below())) {
                        continue;
                    }

                    Vec3 candidate = new Vec3(surface.getX() + 0.5D, surface.getY(), surface.getZ() + 0.5D);
                    if (candidate.distanceToSqr(threat.position()) <= position().distanceToSqr(threat.position())) {
                        continue;
                    }
                    if (!serverLevel.noCollision(HerobrineGregEntity.this,
                            getBoundingBox().move(candidate.subtract(position())).deflate(1.0E-4D))) {
                        continue;
                    }
                    return candidate;
                }

                return null;
            }
        });
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return !isPortalCasting()
                        && firstSummonedHerobrine != null
                        && firstSummonedHerobrine.isAlive()
                        && distanceTo(firstSummonedHerobrine) > 8.0D;
            }

            @Override
            public void tick() {
                if (firstSummonedHerobrine != null && firstSummonedHerobrine.isAlive()) {
                    getNavigation().moveTo(firstSummonedHerobrine, 2.0D);
                    getLookControl().setLookAt(firstSummonedHerobrine, 30.0F, 30.0F);
                    if (distanceToSqr(firstSummonedHerobrine) > 25.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(firstSummonedHerobrine, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return !isPortalCasting()
                        && firstSummonedHerobrine != null
                        && firstSummonedHerobrine.isAlive()
                        && distanceTo(firstSummonedHerobrine) > 5.0D;
            }
        });
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return !isPortalCasting()
                        && secondSummonedHerobrine != null
                        && secondSummonedHerobrine.isAlive()
                        && distanceTo(secondSummonedHerobrine) > 8.0D;
            }

            @Override
            public void tick() {
                if (secondSummonedHerobrine != null && secondSummonedHerobrine.isAlive()) {
                    getNavigation().moveTo(secondSummonedHerobrine, 2.0D);
                    getLookControl().setLookAt(secondSummonedHerobrine, 30.0F, 30.0F);
                    if (distanceToSqr(secondSummonedHerobrine) > 25.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(secondSummonedHerobrine, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return !isPortalCasting()
                        && secondSummonedHerobrine != null
                        && secondSummonedHerobrine.isAlive()
                        && distanceTo(secondSummonedHerobrine) > 5.0D;
            }
        });
        this.goalSelector.addGoal(1, new Goal() {
            @Override
            public boolean canUse() {
                return !isPortalCasting()
                        && thirdSummonedHerobrine != null
                        && thirdSummonedHerobrine.isAlive()
                        && distanceTo(thirdSummonedHerobrine) > 8.0D;
            }

            @Override
            public void tick() {
                if (thirdSummonedHerobrine != null && thirdSummonedHerobrine.isAlive()) {
                    getNavigation().moveTo(thirdSummonedHerobrine, 2.0D);
                    getLookControl().setLookAt(thirdSummonedHerobrine, 30.0F, 30.0F);
                    if (distanceToSqr(thirdSummonedHerobrine) > 25.0D) {
                        if (getNavigation().isDone()) {
                            getNavigation().moveTo(thirdSummonedHerobrine, 2.0D);
                        }
                    } else {
                        getNavigation().stop();
                    }
                }
            }

            @Override
            public boolean canContinueToUse() {
                return !isPortalCasting()
                        && thirdSummonedHerobrine != null
                        && thirdSummonedHerobrine.isAlive()
                        && distanceTo(thirdSummonedHerobrine) > 5.0D;
            }
        });
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, VillagerScoutEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, VillagerScoutCaptainEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, BlueVillagerKnightEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, GreenVillagerKnightEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, RedVillagerKnightEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PurpleVillagerKnightEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerNpcEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, SteveEntity.class, 24.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, AlexEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, JevEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, ChrisEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, BlueDemonEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, BbqEntity.class, 12.0F, AVOID_WALK_SPEED, AVOID_SPRINT_SPEED));

        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
    }

    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new HerobrineMob.AnyFluidPathNavigation(this, level);
    }

    @Override
    public boolean isInWater() {
        FluidState fs = this.level().getFluidState(this.blockPosition());
        if (!fs.isEmpty() && this.canStandOnFluid(fs)) return false;
        return super.isInWater();
    }

    @Override
    public boolean canStandOnFluid(FluidState state) {
        return !state.isEmpty();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    private void tickSupportingHerobrineVisuals() {
        if (this.supportingHerobrineVisualTicks > 0) {
            this.supportingHerobrineVisualTicks--;
        }

        boolean supportingAppearance = this.supportingHerobrineVisualTicks > 0 || this.hasNearbyHerobrineToSupport();
        if (!supportingAppearance) {
            if (this.isSupportingHerobrine()) {
                this.setSupportingHerobrine(false);
            }
            return;
        }

        if (!this.isSupportingHerobrine()) {
            this.setSupportingHerobrine(true);
        }
        if (!this.isWhiteEye()) {
            this.setWhiteEye(true);
        }
        if (!this.getItemBySlot(EquipmentSlot.CHEST).is(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get())) {
            this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get()));
        }
    }

    private boolean hasNearbyHerobrineToSupport() {
        LivingEntity support = HerobrinePortalCombatUtil.findPortalSupportHerobrine(this, SUPPORT_SEARCH_RADIUS);
        return support != null
                && support.isAlive()
                && !(support.isPassenger() && support.getVehicle() instanceof HerobrineDragonEntity);
    }

    private void tickPortalCast() {
        if (this.portalCastTicks <= 0) {
            return;
        }

        this.portalCastTicks--;
        this.getNavigation().stop();
        this.setSprinting(false);
        this.xxa = 0.0F;
        this.zza = 0.0F;

        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);

        if (this.getTarget() != null && this.getTarget().isAlive()) {
            this.getLookControl().setLookAt(this.getTarget(), 30.0F, 30.0F);
        }
    }

    private void assignProtect(Entity entity, UUID protectUUID, EliteHerobrineKnockedEntity protectEntity) {
        if (entity != null && entity.isAlive()) {
            if (entity instanceof HerobrineMob herobrineMob) {
                herobrineMob.setProtectUUID(protectUUID);
                herobrineMob.setProtectEntity(protectEntity);
            } else if (entity instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                lowShadowHerobrineCloneEntity.setProtectUUID(protectUUID);
                lowShadowHerobrineCloneEntity.setProtectEntity(protectEntity);
            }
        }
    }

    public void requestProtect(UUID protectUUID, EliteHerobrineKnockedEntity protectEntity) {
        assignProtect(firstSummonedHerobrine, protectUUID, protectEntity);
        assignProtect(secondSummonedHerobrine, protectUUID, protectEntity);
        assignProtect(thirdSummonedHerobrine, protectUUID, protectEntity);
    }

    private static boolean isRidingHerobrineDragon(Entity entity) {
        return entity.isPassenger() && entity.getVehicle() instanceof HerobrineDragonEntity;
    }

    public boolean tryOpenRetreatPortalFor(LivingEntity support, @Nullable LivingEntity threat) {
        if (!(this.level() instanceof ServerLevel serverLevel)
                || support == null
                || !support.isAlive()
                || isRidingHerobrineDragon(support)
                || threat == null
                || !threat.isAlive()
                || this.summoning
                || this.escapeTiming >= 0
                || this.summonTiming >= 0
                || this.isNoAi()
                || this.isPortalSummonAiLocked()
                || this.isPortalCasting()) {
            return false;
        }
        if (this.level().getGameTime() - this.lastRetreatPortalAssistTick < 40L) {
            return false;
        }
        if (!TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this, 2)) {
            return false;
        }

        Vec3 retreat = findActiveSupportRetreatPosition(serverLevel, support, threat);
        if (retreat == null) {
            return false;
        }

        int spawned = TransporterFragmentItem.spawnLinkedPortalPair(
                this.level(),
                this,
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, support.position()),
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, retreat)
        );
        if (spawned <= 0) {
            return false;
        }

        this.lastRetreatPortalAssistTick = this.level().getGameTime();
        this.activeSupportRetreatPos = retreat;
        this.activeSupportRetreatTicks = ACTIVE_SUPPORT_RETREAT_TICKS;
        this.markSupportingHerobrine();
        HerobrinePortalCombatUtil.playPortalPairSummon(this);
        return true;
    }

    public boolean tryOpenApproachPortalFor(LivingEntity support, LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)
                || support == null
                || target == null
                || !support.isAlive()
                || !target.isAlive()
                || isRidingHerobrineDragon(support)
                || this.summoning
                || this.escapeTiming >= 0
                || this.summonTiming >= 0
                || this.isNoAi()
                || this.isPortalSummonAiLocked()
                || this.isPortalCasting()) {
            return false;
        }
        if (support.distanceToSqr(target) < 10.0D * 10.0D) {
            return false;
        }
        if (this.level().getGameTime() - this.lastApproachPortalAssistTick < 40L) {
            return false;
        }
        if (!TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this, 2)) {
            return false;
        }

        if (this.distanceToSqr(support) > SUPPORT_STAND_DISTANCE_SQR) {
            this.getNavigation().moveTo(support, SUPPORT_MOVE_SPEED);
            this.getLookControl().setLookAt(support, 30.0F, 30.0F);
            return false;
        }

        if (!HerobrinePortalCombatUtil.spawnSupportPortalPair(this, support, target)) {
            return false;
        }

        this.lastApproachPortalAssistTick = this.level().getGameTime();
        this.markSupportingHerobrine();
        return true;
    }

    private void floatOnAnyFluid() {
        BlockPos pos = this.blockPosition();
        FluidState fluidState = this.level().getFluidState(pos);
        if (fluidState.isEmpty()) {
            return;
        }

        CollisionContext collisionContext = CollisionContext.of(this);
        Fluid typeHere = fluidState.getType();
        FluidState above = this.level().getFluidState(pos.above());

        if (collisionContext.isAbove(LiquidBlock.STABLE_SHAPE, pos, true) && above.getType() != typeHere) {
            this.setOnGround(true);

            double surfaceY = pos.getY() + fluidState.getHeight(this.level(), pos);
            double bottomY  = this.getBoundingBox().minY;
            double diff     = surfaceY - bottomY - 0.001D;

            if (diff > 0.0D) {
                Vec3 vel = this.getDeltaMovement();
                this.setDeltaMovement(vel.x, Math.max(vel.y, Math.min(0.2D, diff * 0.2D)), vel.z);
            }
        } else {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
        }

        this.fallDistance = 0.0F;
    }

    private void placeObsidianBlockWhenInWater(Block block) {
        BlockPos feet = this.getOnPos();
        if (lastFeetPos == null) lastFeetPos = feet;
        if (!feet.equals(lastFeetPos)) {
            if (!this.level().getBlockState(lastFeetPos).is(block)) {
                FluidState fluidState = this.level().getFluidState(lastFeetPos);
                if (!fluidState.isEmpty()) {
                    int replace = fluidState.isSource()
                            ? (fluidState.is(FluidTags.WATER) ? 1 : (fluidState.is(FluidTags.LAVA) ? 2 : 0))
                            : 0;
                    BlockState state = block.defaultBlockState().setValue(HerobrineObsidianBlock.REPLACE_BY_LIQUID, replace);
                    this.level().setBlockAndUpdate(
                            lastFeetPos,
                            state
                    );
                    BlockEntity blockEntity = this.level().getBlockEntity(lastFeetPos);
                    if (blockEntity instanceof ObsidianBlockEntity obsidianBlockEntity) {
                        obsidianBlockEntity.setOwner(this.getUUID());
                        obsidianBlockEntity.setChanged();
                        this.level().sendBlockUpdated(lastFeetPos, state, state, 3);
                    }
                    if (blockEntity instanceof ShadowObsidianBlockEntity shadowObsidianBlockEntity) {
                        shadowObsidianBlockEntity.setOwner(this.getUUID());
                        shadowObsidianBlockEntity.setChanged();
                        this.level().sendBlockUpdated(lastFeetPos, state, state, 3);
                    }
                    if (blockEntity instanceof CryingObsidianBlockEntity cryingObsidianBlockEntity) {
                        cryingObsidianBlockEntity.setOwner(this.getUUID());
                        cryingObsidianBlockEntity.setChanged();
                        this.level().sendBlockUpdated(lastFeetPos, state, state, 3);
                    }
                }
            }
            lastFeetPos = feet;
        }
    }


    @Override
    public void tick() {
        super.tick();
        this.floatOnAnyFluid();
        this.checkInsideBlocks();
        this.tickPortalSummonAiLock();
        this.tickLowCloneSummonWeakPoint();
        this.tickPortalCast();
        if (!this.level().isClientSide) {
            placeObsidianBlockWhenInWater(AnnoyingVillagersModBlocks.CRYING_OBSIDIAN_BLOCK.get());
            tickSupportingHerobrineVisuals();
            if (!isDay(this.level())) {
                if (!this.isWhiteEye()) {
                    setWhiteEye(true);
                }
                if (!this.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get())) {
                    this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get()));
                }
            } else {
                if (this.isWhiteEye()
                        && this.summonTiming == -1
                        && !this.isSupportingHerobrine()
                        && !this.isUseHerobrineTexture()) {
                    setWhiteEye(false);
                }
                if (!this.combatMode
                        && !this.isSupportingHerobrine()
                        && !this.isUseHerobrineTexture()
                        && this.getItemBySlot(EquipmentSlot.CHEST).getItem().equals(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get())) {
                    this.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
                }
            }
            if (this.sixPortalSupportCooldown > 0) {
                this.sixPortalSupportCooldown--;
            }

            if (this.level().getDayTime() % 24000L == 13001 && this.summonTimestamp == -1) {
                if (new Random().nextBoolean()) {
                    Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> " +
                            Component.translatable("subtitles.herobrine_prepare_for_fight").getString()), false);
                    this.summonTimestamp = new Random().nextInt(13100, 22200);
                    AnnoyingVillagers.LOGGER.info("[AV MOD DEBUG]: Greg will summon elites at {}", this.summonTimestamp);
                } else {
                    Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> " +
                            Component.translatable("subtitles.herobrine_no_fight").getString()), false);
                }
            }

            if (this.level().getDayTime() % 24000L == this.summonTimestamp) {
                this.summonTimestamp = -2; // Greg will never summon again
                this.combatMode = true;
                this.setNoAi(true);
                this.summoning = true;
                this.summonTiming = 20;
                this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 120, 3, false, false));
                this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 120, 3, false, false));
            }

            if (this.getHealth() <= 2 && this.summonTiming == -1) {
                if (!isDay(this.level())) {
                    this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get()));
                }
                setWhiteEye(true);
                this.setNoAi(true);
                this.summoning = true;
                this.summonTiming = 20;
                this.setHealth(1);
                this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 120, 3, false, false));
                this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 120, 3, false, false));
            }

            if (this.summonTiming > 0) {
                this.summonTiming = this.summonTiming - 1;
            }
            if (this.summonTiming == 10) {
                this.playSound(AnnoyingVillagersModSounds.PORTAL_SUMMON.get(), 1.0F, 1.0F);
                Objects.requireNonNull(this.level().getServer()).getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> " +
                        Component.translatable("subtitles.herobrine_summon").getString()), false);
            }
            if (this.summonTiming == 1) {
                if (this.combatMode) {
                    summonHerobrines();
                } else {
                    summonHerobrinesAndEscape();
                }
            }

            this.tickQueuedSixPortalSupportReservation();
            if (!this.summoning && this.escapeTiming < 0) {
                if (!this.isPortalCasting() && !this.isPortalSummonAiLocked() && !this.isSixPortalSupportReserved()) {
                    boolean portalActionUsed = false;
                    if (this.supportCatchUpCooldown > 0) {
                        this.supportCatchUpCooldown--;
                    }
                    portalActionUsed = this.tickRequestedSupportPortalAction();
                    if (!portalActionUsed) {
                        portalActionUsed = this.tickFarSupportPortalCatchUp();
                    }
                    if (this.rangedCounterPortalCooldown > 0) {
                        this.rangedCounterPortalCooldown--;
                    }
                    if (!portalActionUsed && this.rangedCounterPortalCooldown <= 0) {
                        portalActionUsed = HerobrinePortalCombatUtil.tryBowCounterPortalSupport(this);
                        this.rangedCounterPortalCooldown = portalActionUsed
                                ? randomRangedCounterPortalCooldown()
                                : RANGED_COUNTER_PORTAL_RETRY_TICKS;
                    }

                    if (!portalActionUsed && this.portalSupportCooldown > 0) {
                        this.portalSupportCooldown--;
                    }
                    if (!portalActionUsed && this.portalSupportCooldown <= 0) {
                        portalActionUsed = HerobrinePortalCombatUtil.tryGregPortalSupport(this);
                        this.portalSupportCooldown = portalActionUsed ? randomPortalSupportCooldown() : PORTAL_SUPPORT_RETRY_TICKS;
                    }

                    if (!portalActionUsed && this.summonTiming < 0) {
                        if (this.lowCloneSupportCooldown > 0) {
                            this.lowCloneSupportCooldown--;
                        }
                        if (this.lowCloneSupportCooldown <= 0) {
                            int spawned = summonCombatLowCloneSupport();
                            portalActionUsed = spawned > 0;
                            this.lowCloneSupportCooldown = spawned > 0 ? randomLowCloneSupportCooldown() : LOW_CLONE_SUPPORT_RETRY_TICKS;
                        }
                    }

                    if (!portalActionUsed) {
                        tickActiveSupportReposition();
                    }
                } else {
                    this.getNavigation().stop();
                }
            }

            if (this.escapeTiming > 0) {
                this.escapeTiming = this.escapeTiming - 1;
            }
            if (this.escapeTiming == 60 && this.combatMode) {
                this.playSound(AnnoyingVillagersModSounds.PORTAL_NATURAL.get());
                if (getLivingEntityPatch() != null) {
                    getLivingEntityPatch().playAnimationSynchronized(AnimsSculkSteve.PORTAL_SUMMON, 0.0F);
                }
                AnnoyingVillagers.PACKET_HANDLER.send(
                        PacketDistributor.TRACKING_ENTITY.with(() -> this),
                        new ClientboundHerobrinePortalFx(this.getOnPos().getCenter().add(0.0, 0.5, 0.0))
                );
            }
            if (this.escapeTiming == 40) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    HerobrinePortalUtil.sinkIntoGround(serverLevel, this, 0.06);
                }
            }
            if (this.escapeTiming == 1) {
                this.level().getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + this.getChatName() + "> " +
                        Component.translatable("subtitles.herobrine_will_be_back").getString()), false);
                if (this.firstSummonedHerobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                    lowShadowHerobrineCloneEntity.setAutoKill(true);
                    lowShadowHerobrineCloneEntity.kill();
                }
                if (this.secondSummonedHerobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                    lowShadowHerobrineCloneEntity.setAutoKill(true);
                    lowShadowHerobrineCloneEntity.kill();
                }
                if (this.thirdSummonedHerobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                    lowShadowHerobrineCloneEntity.setAutoKill(true);
                    lowShadowHerobrineCloneEntity.kill();
                }
                this.discard();
            }

            if (firstSummonedHerobrine == null && firstSummonedHerobrineUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(firstSummonedHerobrineUUID);
                if (entity instanceof HerobrineMob || entity instanceof LowShadowHerobrineCloneEntity) {
                    firstSummonedHerobrine = entity;
                } else {
                    firstSummonedHerobrineUUID = null;
                }
            }
            if (firstSummonedHerobrine != null && !firstSummonedHerobrine.isAlive()) {
                // handle logic when a summoned die
                firstSummonedHerobrineUUID = null;
            }

            if (secondSummonedHerobrine == null && secondSummonedHerobrineUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(secondSummonedHerobrineUUID);
                if (entity instanceof HerobrineMob || entity instanceof LowShadowHerobrineCloneEntity) {
                    secondSummonedHerobrine = entity;
                } else {
                    secondSummonedHerobrineUUID = null;
                }
            }
            if (secondSummonedHerobrine != null && !secondSummonedHerobrine.isAlive()) {
                // handle logic when a summoned die
                secondSummonedHerobrineUUID = null;
            }

            if (thirdSummonedHerobrine == null && thirdSummonedHerobrineUUID != null) {
                Entity entity = ((ServerLevel) level()).getEntity(thirdSummonedHerobrineUUID);
                if (entity instanceof HerobrineMob || entity instanceof LowShadowHerobrineCloneEntity) {
                    thirdSummonedHerobrine = entity;
                } else {
                    thirdSummonedHerobrineUUID = null;
                }
            }
            if (thirdSummonedHerobrine != null && !thirdSummonedHerobrine.isAlive()) {
                // handle logic when a summoned die
                thirdSummonedHerobrineUUID = null;
            }

            if (this.combatMode && this.escapeTiming == -1 && this.summonTiming == -2
                    && this.firstSummonedHerobrineUUID == null
                    && this.secondSummonedHerobrineUUID == null
                    && this.thirdSummonedHerobrineUUID == null) {
                this.escapeTiming = 80;
                this.setNoAi(true);
            }

            if (this.combatMode && this.escapeTiming == -1 && this.recallTime >= 0) {
                this.recallTime = this.recallTime - 1;
                if (this.recallTime == 20) {
                    this.setNoAi(true);
                }
                if (this.recallTime <= 0) {
                    this.escapeTiming = 61;
                }
            }

            if (this.combatMode) {
                this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 1, 3, false, false));
            }
        }
    }

    private void tickActiveSupportReposition() {
        if (this.supportRetreatPanicTicks > 0) {
            this.supportRetreatPanicTicks--;
        }
        if (this.supportRepositionCooldown > 0) {
            this.supportRepositionCooldown--;
        }
        if (this.supportRepositionCooldown > 0) {
            return;
        }

        boolean panic = this.supportRetreatPanicTicks > 0;
        if (!panic && this.random.nextFloat() > 0.35F) {
            this.supportRepositionCooldown = SUPPORT_REPOSITION_RETRY_TICKS;
            return;
        }

        boolean activated = tryActiveSupportReposition(panic);
        this.supportRepositionCooldown = activated ? randomSupportRepositionCooldown() : SUPPORT_REPOSITION_RETRY_TICKS;
    }

    private boolean tryActiveSupportReposition(boolean panic) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        LivingEntity support = HerobrinePortalCombatUtil.findPortalSupportHerobrine(this, SUPPORT_SEARCH_RADIUS);
        if (support == null || !support.isAlive()
                || (support.isPassenger() && support.getVehicle() instanceof HerobrineDragonEntity)) {
            return false;
        }

        LivingEntity enemy = HerobrinePortalCombatUtil.findThreateningEnemy(this, support, SUPPORT_SAFE_ENEMY_SEARCH_RADIUS);
        if (enemy == null) {
            enemy = HerobrinePortalCombatUtil.findEnemyForSupport(support, this.getTarget(), SUPPORT_SAFE_ENEMY_SEARCH_RADIUS);
        }
        if (enemy == null) {
            return false;
        }

        boolean dangerClose = panic
                || this.distanceToSqr(enemy) <= SUPPORT_REPOSITION_GREG_DANGER_SQR
                || support.distanceToSqr(enemy) <= SUPPORT_REPOSITION_SUPPORT_DANGER_SQR;
        if (!dangerClose) {
            return false;
        }
        if (!TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, this, 4)) {
            return false;
        }

        Vec3 retreat = findActiveSupportRetreatPosition(serverLevel, support, enemy);
        if (retreat == null) {
            return false;
        }

        Vec3 returnEntrance = findRetreatReturnEntrance(serverLevel, retreat, support, enemy);
        Vec3 returnExit = findEnemyFlankReturnExit(serverLevel, enemy, support);
        int spawned = 0;
        spawned += TransporterFragmentItem.spawnLinkedPortalPair(
                this.level(),
                this,
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, support.position()),
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, retreat)
        );
        spawned += TransporterFragmentItem.spawnLinkedPortalPair(
                this.level(),
                this,
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, returnEntrance),
                HerobrinePortalCombatUtil.applySupportPortalYOffset(this, returnExit)
        );
        if (spawned <= 0) {
            return false;
        }

        this.activeSupportRetreatPos = retreat;
        this.activeSupportRetreatTicks = ACTIVE_SUPPORT_RETREAT_TICKS;
        this.markSupportingHerobrine();
        HerobrinePortalCombatUtil.playPortalPairSummon(this);
        return true;
    }

    @Nullable
    private Vec3 findActiveSupportRetreatPosition(ServerLevel serverLevel, LivingEntity support, LivingEntity enemy) {
        Vec3 away = horizontalDirection(support.position().subtract(enemy.position()));
        if (away.lengthSqr() < 1.0E-4D) {
            away = horizontalDirection(this.position().subtract(enemy.position()));
        }
        if (away.lengthSqr() < 1.0E-4D) {
            away = Vec3.directionFromRotation(0.0F, this.getYRot());
        }

        for (int attempt = 0; attempt < 32; attempt++) {
            double turn = (this.random.nextDouble() - 0.5D) * 1.2D;
            Vec3 direction = rotateHorizontal(away, turn);
            double distance = SUPPORT_REPOSITION_RETREAT_MIN_DISTANCE
                    + this.random.nextDouble() * (SUPPORT_REPOSITION_RETREAT_MAX_DISTANCE - SUPPORT_REPOSITION_RETREAT_MIN_DISTANCE);
            Vec3 raw = support.position().add(direction.scale(distance));
            Vec3 candidate = surfacePosition(serverLevel, raw.x, raw.z);
            if (isValidSupportRetreatPosition(serverLevel, candidate)) {
                return candidate;
            }
        }

        return null;
    }

    public void triggerRangedCounterRetreat(@Nullable LivingEntity threat) {
        if (this.level().isClientSide || threat == null || !threat.isAlive()) {
            return;
        }

        this.markSupportingHerobrine();
        this.supportRetreatPanicTicks = Math.max(this.supportRetreatPanicTicks, ACTIVE_SUPPORT_RETREAT_TICKS);
        this.supportRepositionCooldown = 0;
        this.getLookControl().setLookAt(threat, 30.0F, 30.0F);
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        LivingEntity support = HerobrinePortalCombatUtil.findPortalSupportHerobrine(this, SUPPORT_SEARCH_RADIUS);
        Vec3 retreat = support != null && support.isAlive()
                ? findActiveSupportRetreatPosition(serverLevel, support, threat)
                : findDirectRetreatPosition(serverLevel, threat);
        if (retreat == null) {
            return;
        }

        this.activeSupportRetreatPos = retreat;
        this.activeSupportRetreatTicks = ACTIVE_SUPPORT_RETREAT_TICKS;
        this.getNavigation().moveTo(retreat.x, retreat.y, retreat.z, SUPPORT_ACTIVE_RETREAT_MOVE_SPEED);
    }

    @Nullable
    private Vec3 findDirectRetreatPosition(ServerLevel serverLevel, LivingEntity enemy) {
        Vec3 away = horizontalDirection(this.position().subtract(enemy.position()));
        if (away.lengthSqr() < 1.0E-4D) {
            away = Vec3.directionFromRotation(0.0F, this.getYRot());
        }

        for (int attempt = 0; attempt < 24; attempt++) {
            double turn = (this.random.nextDouble() - 0.5D) * 1.4D;
            Vec3 direction = rotateHorizontal(away, turn);
            double distance = SUPPORT_REPOSITION_RETREAT_MIN_DISTANCE
                    + this.random.nextDouble() * (SUPPORT_REPOSITION_RETREAT_MAX_DISTANCE - SUPPORT_REPOSITION_RETREAT_MIN_DISTANCE);
            Vec3 raw = this.position().add(direction.scale(distance));
            Vec3 candidate = surfacePosition(serverLevel, raw.x, raw.z);
            if (isValidSupportRetreatPosition(serverLevel, candidate)) {
                return candidate;
            }
        }

        return null;
    }

    private Vec3 findRetreatReturnEntrance(ServerLevel serverLevel, Vec3 retreat, LivingEntity support, LivingEntity enemy) {
        Vec3 away = horizontalDirection(retreat.subtract(enemy.position()));
        Vec3 side = new Vec3(-away.z, 0.0D, away.x);
        if (side.lengthSqr() < 1.0E-4D) {
            side = new Vec3(1.0D, 0.0D, 0.0D);
        }
        if (this.random.nextBoolean()) {
            side = side.scale(-1.0D);
        }

        for (int attempt = 0; attempt < 8; attempt++) {
            Vec3 raw = retreat.add(side.scale(2.5D + attempt * 0.75D));
            Vec3 candidate = surfacePosition(serverLevel, raw.x, raw.z);
            if (isValidSupportRetreatPosition(serverLevel, candidate)) {
                return candidate;
            }
        }
        return retreat;
    }

    private Vec3 findEnemyFlankReturnExit(ServerLevel serverLevel, LivingEntity enemy, LivingEntity support) {
        Vec3 fromEnemyToSupport = horizontalDirection(support.position().subtract(enemy.position()));
        if (fromEnemyToSupport.lengthSqr() < 1.0E-4D) {
            fromEnemyToSupport = Vec3.directionFromRotation(0.0F, enemy.getYRot());
        }

        Vec3 side = new Vec3(-fromEnemyToSupport.z, 0.0D, fromEnemyToSupport.x);
        if (this.random.nextBoolean()) {
            side = side.scale(-1.0D);
        }

        for (int attempt = 0; attempt < 16; attempt++) {
            double sideDistance = SUPPORT_REPOSITION_RETURN_SIDE_DISTANCE + this.random.nextDouble() * 4.0D;
            double backDistance = 2.0D + this.random.nextDouble() * 4.0D;
            Vec3 raw = enemy.position()
                    .add(side.scale(sideDistance))
                    .add(fromEnemyToSupport.scale(backDistance));
            Vec3 candidate = surfacePosition(serverLevel, raw.x, raw.z);
            if (isValidSupportRetreatPosition(serverLevel, candidate)) {
                return candidate;
            }
        }

        Vec3 fallbackRaw = enemy.position().add(fromEnemyToSupport.scale(SUPPORT_REPOSITION_RETURN_SIDE_DISTANCE));
        return surfacePosition(serverLevel, fallbackRaw.x, fallbackRaw.z);
    }

    private Vec3 surfacePosition(ServerLevel serverLevel, double x, double z) {
        int y = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(x, this.getY(), z)).getY();
        return new Vec3(x, y, z);
    }

    private boolean isValidSupportRetreatPosition(ServerLevel serverLevel, Vec3 pos) {
        BlockPos blockPos = BlockPos.containing(pos);
        if (!serverLevel.isLoaded(blockPos) || !serverLevel.getWorldBorder().isWithinBounds(blockPos)) {
            return false;
        }
        if (!serverLevel.isEmptyBlock(blockPos) || !serverLevel.isEmptyBlock(blockPos.above()) || serverLevel.isEmptyBlock(blockPos.below())) {
            return false;
        }

        AABB movedBox = this.getBoundingBox().move(pos.subtract(this.position()));
        return serverLevel.noCollision(this, movedBox);
    }

    private Vec3 horizontalDirection(Vec3 vector) {
        Vec3 horizontal = new Vec3(vector.x, 0.0D, vector.z);
        return horizontal.lengthSqr() < 1.0E-4D ? Vec3.ZERO : horizontal.normalize();
    }

    private Vec3 rotateHorizontal(Vec3 vector, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vec3(vector.x * cos - vector.z * sin, 0.0D, vector.x * sin + vector.z * cos).normalize();
    }

    private int summonCombatLowCloneSupport() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return 0;
        }

        LivingEntity support = HerobrinePortalCombatUtil.findPortalSupportHerobrine(this, SUPPORT_SEARCH_RADIUS);
        if (support == null || !support.isAlive()
                || (support.isPassenger() && support.getVehicle() instanceof HerobrineDragonEntity)) {
            return 0;
        }

        LivingEntity enemy = HerobrinePortalCombatUtil.findThreateningEnemy(this, support, LOW_CLONE_SUPPORT_ENEMY_RADIUS);
        if (enemy == null) {
            enemy = HerobrinePortalCombatUtil.findEnemyForSupport(support, this.getTarget(), LOW_CLONE_SUPPORT_ENEMY_RADIUS);
        }
        if (enemy == null) {
            return 0;
        }

        int count = 1 + this.random.nextInt(3);
        int spawned = 0;
        for (int i = 0; i < count; i++) {
            if (spawnCombatLowCloneNear(serverLevel, support, enemy)) {
                spawned++;
            }
        }

        if (spawned > 0) {
            this.markSupportingHerobrine();
            this.startLowCloneSummonWeakPoint();
            HerobrinePortalCombatUtil.playClonePortalSummon(this);
        }
        return spawned;
    }

    private boolean spawnCombatLowCloneNear(ServerLevel serverLevel, Entity anchor, LivingEntity enemy) {
        boolean shadowClone = this.random.nextBoolean();
        for (int attempt = 0; attempt < LOW_CLONE_SUPPORT_SPAWN_ATTEMPTS; attempt++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double radius = 2.5D + this.random.nextDouble() * 5.5D;
            double x = anchor.getX() + Math.cos(angle) * radius;
            double z = anchor.getZ() + Math.sin(angle) * radius;
            int y = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(x), Mth.floor(z));
            BlockPos spawnPos = BlockPos.containing(x, y, z);
            if (!isValidCombatLowCloneSpawn(serverLevel, spawnPos)) {
                continue;
            }

            Mob clone = shadowClone
                    ? new LowShadowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), serverLevel)
                    : new LowHerobrineCloneEntity(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), serverLevel);
            clone.moveTo(x, y, z, this.getYRot(), this.getXRot());
            if (!serverLevel.noCollision(clone)) {
                continue;
            }

            if (clone instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                lowHerobrineCloneEntity.setSummoned(true);
                lowHerobrineCloneEntity.setRenderPortal(false);
            } else if (clone instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                lowShadowHerobrineCloneEntity.setSummoned(true);
                lowShadowHerobrineCloneEntity.setRenderPortal(false);
            }

            equipGearForLowHerobrineClone(clone);
            clone.setTarget(enemy);
            clone.lookAt(EntityAnchorArgument.Anchor.EYES, enemy.getEyePosition());
            clone.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(spawnPos), MobSpawnType.MOB_SUMMONED, null, null);
            serverLevel.addFreshEntity(clone);
            AnnoyingVillagers.PACKET_HANDLER.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> clone),
                    new ClientboundHerobrinePortalFx(new Vec3(x, y, z))
            );
            return true;
        }
        return false;
    }

    private boolean isValidCombatLowCloneSpawn(ServerLevel serverLevel, BlockPos spawnPos) {
        return serverLevel.isLoaded(spawnPos)
                && serverLevel.getWorldBorder().isWithinBounds(spawnPos)
                && serverLevel.isEmptyBlock(spawnPos)
                && serverLevel.isEmptyBlock(spawnPos.above())
                && !serverLevel.isEmptyBlock(spawnPos.below());
    }

    private void summonHerobrine(String herobrineMobId, double spawnX, double spawnY,
                                 double spawnZ, double summonLookX, double summonLookZ, boolean renderPortal) {
        if (this.level() instanceof ServerLevel levelaccessor) {
            String[] parts = herobrineMobId.split(":");
            ResourceLocation mobResourceLocation = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(mobResourceLocation);
            if (type != null && type.create(level()) instanceof Mob herobrine) {
                if (herobrine instanceof HerobrineMob herobrineMob) {
                    herobrineMob.setGregUUID(this.getUUID());
                    herobrineMob.setRenderPortal(renderPortal);
                    herobrineMob.setRecallTicks(this.recallTime);
                } else if (herobrine instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity) {
                    lowHerobrineCloneEntity.setSummoned(true);
                    equipGearForLowHerobrineClone(lowHerobrineCloneEntity);
                } else if (herobrine instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
                    if (renderPortal) {
                        AnnoyingVillagers.PACKET_HANDLER.send(
                                PacketDistributor.TRACKING_ENTITY.with(() -> this),
                                new ClientboundHerobrinePortalFx(new Vec3(spawnX, spawnY, spawnZ))
                        );
                    } else {
                        equipGearForLowHerobrineClone(lowShadowHerobrineCloneEntity);
                    }
                    lowShadowHerobrineCloneEntity.setSummoned(true);
                }

                herobrine.moveTo(spawnX, spawnY, spawnZ, this.getYRot(), this.getXRot());
                herobrine.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(summonLookX, spawnY, summonLookZ));
                herobrine.finalizeSpawn(levelaccessor, levelaccessor.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                levelaccessor.addFreshEntity(herobrine);

                if (this.combatMode) {
                    if (this.firstSummonedHerobrineUUID == null) {
                        this.firstSummonedHerobrineUUID = herobrine.getUUID();
                        this.firstSummonedHerobrine = herobrine;
                    } else if (this.secondSummonedHerobrineUUID == null) {
                        this.secondSummonedHerobrineUUID = herobrine.getUUID();
                        this.secondSummonedHerobrine = herobrine;
                    }  else {
                        this.thirdSummonedHerobrineUUID = herobrine.getUUID();
                        this.thirdSummonedHerobrine = herobrine;
                    }
                }
            }
        }
    }

    private void spawnHerobrineOffset(String id,
                                      double forwardDist, double lateralDist, double baseY,
                                      double fx, double fz, double lx, double lz) {
        double spawnX = this.getX() + fx * forwardDist + lx * lateralDist;
        double spawnZ = this.getZ() + fz * forwardDist + lz * lateralDist;

        double lookX = spawnX + fx * 10.0;
        double lookZ = spawnZ + fz * 10.0;

        summonHerobrine(id, spawnX, baseY, spawnZ, lookX, lookZ, false);
    }

    private void spawnRandomHerobrinesInRadius(String id, int count) {
        if (!(this.level() instanceof ServerLevel sl)) return;

        int cx = Mth.floor(this.getX());
        int cz = Mth.floor(this.getZ());

        List<BlockPos> candidates = new ArrayList<>();
        int r2 = 20 * 20;
        for (int dx = -20; dx <= 20; dx++) {
            for (int dz = -20; dz <= 20; dz++) {
                if (dx == 0 && dz == 0) continue;
                if (dx * dx + dz * dz > r2) continue;
                int x = cx + dx;
                int z = cz + dz;

                int y = sl.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
                candidates.add(new BlockPos(x, y, z));
            }
        }

        Collections.shuffle(candidates, new java.util.Random(this.getRandom().nextLong()));
        double yawRad = Math.toRadians(this.getYRot());
        double fx = -Math.sin(yawRad);
        double fz =  Math.cos(yawRad);

        int spawned = 0;
        for (BlockPos pos : candidates) {
            if (spawned >= count) break;

            if (!sl.isLoaded(pos)) continue;
            if (!sl.getWorldBorder().isWithinBounds(pos)) continue;

            if (!sl.isEmptyBlock(pos) || !sl.isEmptyBlock(pos.above())) continue;
            if (sl.isEmptyBlock(pos.below())) continue;

            double spawnX = pos.getX() + 0.5D;
            double spawnY = pos.getY();
            double spawnZ = pos.getZ() + 0.5D;

            double lookX = spawnX + fx * 10.0D;
            double lookZ = spawnZ + fz * 10.0D;

            summonHerobrine(id, spawnX, spawnY, spawnZ, lookX, lookZ, true);
            spawned++;
        }
    }

    private void summonEscapeAtDay() {
        this.escapeTiming = 70;
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY.with(() -> this),
                new ClientboundHerobrinePortalFx(this.getOnPos().getCenter().add(0.0, 0.5, 0.0))
        );

        double yawRad = Math.toRadians(this.getYRot());

        double fx = -Math.sin(yawRad);
        double fz =  Math.cos(yawRad);
        double lx =  Math.cos(yawRad);
        double lz =  Math.sin(yawRad);

        double y = this.getY();
        double front = 1.0;
        double side  = 1.0;

        // Herobrine on the left side
        String leftHerobrine;
        if (Math.random() <= 0.5D) {
            leftHerobrine = "annoyingvillagers:low_herobrine_clone";
        } else {
            leftHerobrine = "annoyingvillagers:low_shadow_herobrine_clone";
        }
        spawnHerobrineOffset(leftHerobrine, 0.0, +side, y, fx, fz, lx, lz);

        String rightHerobrine;
        if (Math.random() <= 0.5D) {
            rightHerobrine = "annoyingvillagers:low_herobrine_clone";
        } else {
            rightHerobrine = "annoyingvillagers:low_shadow_herobrine_clone";
        }
        spawnHerobrineOffset(rightHerobrine, 0.0, -side, y, fx, fz, lx, lz);

        // 70% for 2 Herobrines, 30% for 3 Herobrines
        if (Math.random() >= 0.7D) {
            String frontHerobrine;
            if (Math.random() <= 0.5D) {
                frontHerobrine = "annoyingvillagers:low_herobrine_clone";
            } else {
                frontHerobrine = "annoyingvillagers:low_shadow_herobrine_clone";
            }

            spawnHerobrineOffset(frontHerobrine, front, 0.0, y, fx, fz, lx, lz);
        }
    }

    private void summonEscapeAtNight() {
        this.escapeTiming = 70;
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY.with(() -> this),
                new ClientboundHerobrinePortalFx(this.getOnPos().getCenter().add(0.0, 0.5, 0.0))
        );

        List<String> herobrines = new ArrayList<>();
        herobrines.add("annoyingvillagers:herobrine_clone");
        herobrines.add("annoyingvillagers:shadow_herobrine_clone");
        herobrines.add("annoyingvillagers:herobrine_chris");
        herobrines.add("annoyingvillagers:herobrine_7");
        herobrines.add("annoyingvillagers:armored_herobrine");
        herobrines.add("annoyingvillagers:low_shadow_herobrine_clone");

        Random random = new Random();
        String herobrineId = herobrines.get(random.nextInt(herobrines.size()));

        // if low_shadow_herobrine_clone, spawn 10 to 20 low_shadow_herobrine_clone around
        if (herobrineId.equals("annoyingvillagers:low_shadow_herobrine_clone")) {
            spawnRandomHerobrinesInRadius(herobrineId, new Random().nextInt(10, 20));
        } else {
            double yawRad = Math.toRadians(this.getYRot());

            double fx = -Math.sin(yawRad);
            double fz =  Math.cos(yawRad);
            double lx =  Math.cos(yawRad);
            double lz =  Math.sin(yawRad);

            double y = this.getY();
            double front = 1.0;

            spawnHerobrineOffset(herobrineId, front, 0.0, y, fx, fz, lx, lz);
        }
    }

    private enum ElitePattern {
        SOLO_1E,
        ONEE_PLUS_1S,
        ONEE_PLUS_2S,
        TWO_E,
        TWOE_PLUS_1S,
        THREE_E
    }

    private ElitePattern pickWeightedElitePattern(Random random) {
        double roll = random.nextDouble();
        if (roll <= 0.1F) {
            return ElitePattern.THREE_E;
        } else if (roll <= 0.2F) {
            return ElitePattern.TWOE_PLUS_1S;
        } else if (roll <= 0.3F) {
            return ElitePattern.TWO_E;
        } else if (roll <= 0.4F) {
            return ElitePattern.ONEE_PLUS_2S;
        } else if (roll <= 0.5F) {
            return ElitePattern.ONEE_PLUS_1S;
        } else {
            return ElitePattern.SOLO_1E;
        }
    }

    private void clearSummonSpace(ServerLevel serverLevel) {
        BlockPos center = this.getOnPos();
        final int feetY = center.getY();

        for (int dy = 1; dy <= 2; dy++) {
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);

                    BlockState state = serverLevel.getBlockState(pos);
                    if (state.isAir()) continue;
                    if (state.getDestroySpeed(serverLevel, pos) < 0.0F) continue;

                    serverLevel.destroyBlock(pos, true, this);
                }
            }
        }

        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                BlockPos pos = new BlockPos(center.getX() + dx, feetY, center.getZ() + dz);

                if (serverLevel.getBlockState(pos).isAir()) {
                    serverLevel.setBlockAndUpdate(pos, Blocks.CRYING_OBSIDIAN.defaultBlockState());
                }
            }
        }
    }

    private static <T> T pickRandom(List<T> list, Random random) {
        return list.remove(random.nextInt(list.size()));
    }

    private void summonAtNight() {
        List<String> herobrines = new ArrayList<>();
        herobrines.add("annoyingvillagers:shadow_herobrine");
        herobrines.add("annoyingvillagers:elite");
        herobrines.add("annoyingvillagers:null");
        herobrines.add("annoyingvillagers:elite");

        List<String> elites = new ArrayList<>();
        elites.add("annoyingvillagers:swordsman_herobrine");
        elites.add("annoyingvillagers:aegis_herobrine");
        elites.add("annoyingvillagers:glaive_herobrine");
        elites.add("annoyingvillagers:reaper_herobrine");
        elites.add("annoyingvillagers:sledgehammer_herobrine");

        float yaw = this.getYRot();
        double rad = Math.toRadians(yaw);
        double fx = -Math.sin(rad);
        double fz =  Math.cos(rad);
        double lx =  Math.cos(rad);
        double lz =  Math.sin(rad);

        double baseY = this.getY();
        double centerForward = 3.0;
        double side = 1.0;
        double thirdForward = 4.0;

        double centerX = this.getX() + fx * centerForward;
        double centerZ = this.getZ() + fz * centerForward;
        double lookX   = centerX + fx * 10.0;
        double lookZ   = centerZ + fz * 10.0;

        this.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(centerX, baseY, centerZ));

        if (!(this.level() instanceof ServerLevel)) return;
        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY.with(() -> this),
                new ClientboundHerobrinePortalFx(new Vec3(centerX, baseY, centerZ))
        );

        Random random = new Random();
        String pick = herobrines.get(random.nextInt(herobrines.size()));

        if (pick.equals("annoyingvillagers:shadow_herobrine") || pick.equals("annoyingvillagers:null")) {
            summonHerobrine(pick, centerX, baseY, centerZ, lookX, lookZ, false);
            return;
        }

        ElitePattern pattern = pickWeightedElitePattern(random);

        switch (pattern) {
            case SOLO_1E -> {
                summonHerobrine(pickRandom(elites, random), centerX, baseY, centerZ, lookX, lookZ, false);
            }
            case ONEE_PLUS_1S -> {
                // Two mobs: left + right around the portal; elite on left, shadow on right (arbitrary)
                // Left
                spawnHerobrineOffset(pickRandom(elites, random), centerForward, +side, baseY, fx, fz, lx, lz);
                // Right
                spawnHerobrineOffset("annoyingvillagers:low_shadow_herobrine_clone", centerForward, -side, baseY, fx, fz, lx, lz);
            }
            case ONEE_PLUS_2S -> {
                // Three mobs: left (shadow), right (shadow), middle+1 forward (elite)
                spawnHerobrineOffset("annoyingvillagers:low_shadow_herobrine_clone", centerForward, +side, baseY, fx, fz, lx, lz);
                spawnHerobrineOffset("annoyingvillagers:low_shadow_herobrine_clone", centerForward, -side, baseY, fx, fz, lx, lz);
                spawnHerobrineOffset(pickRandom(elites, random), thirdForward, 0.0, baseY, fx, fz, lx, lz);
            }
            case TWO_E -> {
                // Two elites: left + right
                spawnHerobrineOffset(pickRandom(elites, random), centerForward, +side, baseY, fx, fz, lx, lz);
                spawnHerobrineOffset(pickRandom(elites, random), centerForward, -side, baseY, fx, fz, lx, lz);
            }
            case TWOE_PLUS_1S -> {
                // Three mobs: left (shadow), right (elite), middle+1 forward (elite)
                spawnHerobrineOffset("annoyingvillagers:low_shadow_herobrine_clone", centerForward, +side, baseY, fx, fz, lx, lz);
                spawnHerobrineOffset(pickRandom(elites, random), centerForward, -side, baseY, fx, fz, lx, lz);
                spawnHerobrineOffset(pickRandom(elites, random), thirdForward, 0.0, baseY, fx, fz, lx, lz);
            }
            case THREE_E -> {
                // Three elites: left, right, and middle+1 forward (super rare)
                spawnHerobrineOffset(pickRandom(elites, random), centerForward, +side, baseY, fx, fz, lx, lz);
                spawnHerobrineOffset(pickRandom(elites, random), centerForward, -side, baseY, fx, fz, lx, lz);
                spawnHerobrineOffset(pickRandom(elites, random), thirdForward, 0.0, baseY, fx, fz, lx, lz);
            }
        }
    }

    private void summonHerobrines() {
        if (getLivingEntityPatch() != null) {
            getLivingEntityPatch().playAnimationSynchronized(AnimsSculkSteve.PORTAL_SUMMON, 0.0F);
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            this.clearSummonSpace(serverLevel);
        }
        this.setCustomNameVisible(false);
        setUseHerobrineTexture(true);
        summonAtNight();
        this.summonTiming = -2;
    }

    private void summonHerobrinesAndEscape() {
        if (getLivingEntityPatch() != null) {
            getLivingEntityPatch().playAnimationSynchronized(AnimsSculkSteve.PORTAL_SUMMON, 0.0F);
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            this.clearSummonSpace(serverLevel);
        }
        if (isDay(this.level())) {
            summonEscapeAtDay();
        } else {
            summonEscapeAtNight();
        }
    }

    public boolean removeWhenFarAway(double d0) {
        return false;
    }

    public @NotNull SoundEvent getHurtSound(@NotNull DamageSource damagesource) {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.hurt")));
    }

    public @NotNull SoundEvent getDeathSound() {
        return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.generic.death")));
    }

    public boolean isDay(Level level) {
        long timeOfDay = level.getDayTime() % 24000L;
        return timeOfDay >= 0 && timeOfDay < 13000;
    }

    public boolean hurt(@NotNull DamageSource pSource, float f) {
        if (this.summoning) {
            return false;
        }
        if (this.escapeTiming < 0) {
            markSupportPanicFromHit(pSource);
        }
        if (this.isLowCloneSummonWeakPointActive()) {
            if (pSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                return super.hurt(pSource, f);
            }
            if (this.random.nextFloat() >= 0.5F) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    EpicfightUtil.damageBlocked(pSource, this, serverLevel);
                }
                return false;
            }
            return super.hurt(pSource, 1.0F);
        }
        if (this.escapeTiming >= 0) {
            if (pSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                return super.hurt(pSource, f);
            }
            if (this.random.nextFloat() >= 0.5F) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    EpicfightUtil.damageBlocked(pSource, this, serverLevel);
                }
                return false;
            }
            return super.hurt(pSource, 1.0F);
        } else if (this.getHealth() == 1 || this.combatMode) {
            if (this.level() instanceof ServerLevel serverLevel) {
                EpicfightUtil.damageBlocked(pSource, this, serverLevel);
            }
            return false;
        }
        if (pSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return super.hurt(pSource, f);
        } else {
            return super.hurt(pSource, 1.0F);
        }
    }

    private void markSupportPanicFromHit(DamageSource source) {
        if (this.level().isClientSide || source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return;
        }

        this.supportRetreatPanicTicks = Math.max(this.supportRetreatPanicTicks, ACTIVE_SUPPORT_RETREAT_TICKS);
        this.supportRepositionCooldown = Math.min(this.supportRepositionCooldown, 1 + this.random.nextInt(20));
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource damageSource, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(damageSource, looting, recentlyHit);
        if (this.escapeTiming >= 0) {
            this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get()));
        }
    }

    private ItemStack randomDamage(ItemStack itemStack) {
        int maxDamage = itemStack.getMaxDamage();
        itemStack.setDamageValue(new Random().nextInt(maxDamage / 3, maxDamage * 3 / 4));
        return itemStack;
    }

    private void equipGearForLowHerobrineClone(Entity entity) {
        if (entity instanceof LowShadowHerobrineCloneEntity && random.nextFloat() < 0.2F) {
            entity.setItemSlot(EquipmentSlot.HEAD, randomDamage(new ItemStack(Items.NETHERITE_HELMET)));
            entity.setItemSlot(EquipmentSlot.CHEST, randomDamage(new ItemStack(Items.NETHERITE_CHESTPLATE)));
            entity.setItemSlot(EquipmentSlot.LEGS, randomDamage(new ItemStack(Items.NETHERITE_LEGGINGS)));
            entity.setItemSlot(EquipmentSlot.FEET, randomDamage(new ItemStack(Items.NETHERITE_BOOTS)));
            entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            entity.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.GOLDEN_SWORD));
        } else {
            if (random.nextFloat() < 0.3f) {
                entity.setItemSlot(EquipmentSlot.HEAD, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get())));
            }
            if (random.nextFloat() < 0.3f) {
                entity.setItemSlot(EquipmentSlot.CHEST, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get())));
            }
            if (random.nextFloat() < 0.3f) {
                entity.setItemSlot(EquipmentSlot.LEGS, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_LEGGINGS.get())));
            }
            if (random.nextFloat() < 0.3f) {
                entity.setItemSlot(EquipmentSlot.FEET, randomDamage(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_BOOTS.get())));
            }
            entity.setItemSlot(EquipmentSlot.MAINHAND, randomDamage(new ItemStack(listWeapons.get(random.nextInt(listWeapons.size())))));
        }
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverlevelaccessor, @NotNull DifficultyInstance difficultyinstance, @NotNull MobSpawnType mobspawntype, @Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        if (mobspawntype == MobSpawnType.NATURAL || mobspawntype == MobSpawnType.CHUNK_GENERATION) {
            ServerLevel serverLevel = serverlevelaccessor.getLevel();
            GregData gregData = GregData.get(serverLevel);

            if (!gregData.tryClaim(serverLevel, this.getUUID())) {
                this.discard();
                return null;
            } else {
            }

            BlockPos blockPos = this.getOnPos();
            int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY();
            BlockPos spawnPos = new BlockPos(blockPos.getX(), surfaceY, blockPos.getZ());
            this.moveTo(spawnPos, this.getYRot(), this.getXRot());
        }

        ChatUtil.joinGame(this, "Greg");
        return super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
    }

    public void awardKillScore(@NotNull Entity entity, int i, @NotNull DamageSource damagesource) {
        super.awardKillScore(entity, i, damagesource);
    }

    public void baseTick() {
        super.baseTick();
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setWhiteEye(pCompound.getBoolean("WhiteEye"));
        setUseHerobrineTexture(pCompound.getBoolean("UseHerobrineTexture"));
        summoning = pCompound.getBoolean("Summoning");
        summonTiming = pCompound.getInt("SummonTiming");
        escapeTiming = pCompound.getInt("EscapeTiming");
        summonTimestamp = pCompound.getInt("SummonTimestamp");
        combatMode = pCompound.getBoolean("CombatMode");
        recallTime = pCompound.getInt("RecallTime");
        portalSupportCooldown = pCompound.contains("PortalSupportCooldown") ? pCompound.getInt("PortalSupportCooldown") : randomPortalSupportCooldown();
        sixPortalSupportCooldown = pCompound.contains("SixPortalSupportCooldown") ? pCompound.getInt("SixPortalSupportCooldown") : 0;
        lowCloneSupportCooldown = pCompound.contains("LowCloneSupportCooldown") ? pCompound.getInt("LowCloneSupportCooldown") : randomLowCloneSupportCooldown();
        rangedCounterPortalCooldown = pCompound.contains("RangedCounterPortalCooldown") ? pCompound.getInt("RangedCounterPortalCooldown") : randomRangedCounterPortalCooldown();
        portalCastTicks = pCompound.getInt("PortalCastTicks");
        portalSummonAiLockTicks = pCompound.getInt("PortalSummonAiLockTicks");
        lowCloneSummonWeakPointTicks = pCompound.getInt("LowCloneSummonWeakPointTicks");
        queuedSixPortalSupportLockTicks = pCompound.getInt("QueuedSixPortalSupportLockTicks");
        requestedSupportPortalTicks = pCompound.getInt("RequestedSupportPortalTicks");
        requestedSupportPortalMode = pCompound.getByte("RequestedSupportPortalMode");
        supportCatchUpCooldown = pCompound.contains("SupportCatchUpCooldown") ? pCompound.getInt("SupportCatchUpCooldown") : SUPPORT_CATCH_UP_RETRY_TICKS;
        if (portalSummonAiLockTicks > 0 || lowCloneSummonWeakPointTicks > 0) {
            this.setNoAi(true);
        }
        if (pCompound.hasUUID("QueuedSixPortalSupportTargetUUID")) {
            queuedSixPortalSupportTargetUUID = pCompound.getUUID("QueuedSixPortalSupportTargetUUID");
        }
        if (pCompound.hasUUID("RequestedSupportPortalSupportUUID")) {
            requestedSupportPortalSupportUUID = pCompound.getUUID("RequestedSupportPortalSupportUUID");
        }
        if (pCompound.hasUUID("RequestedSupportPortalTargetUUID")) {
            requestedSupportPortalTargetUUID = pCompound.getUUID("RequestedSupportPortalTargetUUID");
        }
        if (pCompound.hasUUID("FirstSummonedHerobrineUUID")) {
            firstSummonedHerobrineUUID = pCompound.getUUID("FirstSummonedHerobrineUUID");
        }
        if (pCompound.hasUUID("SecondSummonedHerobrineUUID")) {
            secondSummonedHerobrineUUID = pCompound.getUUID("SecondSummonedHerobrineUUID");
        }
        if (pCompound.hasUUID("ThirdSummonedHerobrineUUID")) {
            thirdSummonedHerobrineUUID = pCompound.getUUID("ThirdSummonedHerobrineUUID");
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("WhiteEye", isWhiteEye());
        pCompound.putBoolean("UseHerobrineTexture", isUseHerobrineTexture());
        pCompound.putBoolean("Summoning", summoning);
        pCompound.putInt("SummonTiming", summonTiming);
        pCompound.putInt("EscapeTiming", escapeTiming);
        pCompound.putInt("SummonTimestamp", summonTimestamp);
        pCompound.putBoolean("CombatMode", combatMode);
        pCompound.putInt("RecallTime", recallTime);
        pCompound.putInt("PortalSupportCooldown", portalSupportCooldown);
        pCompound.putInt("SixPortalSupportCooldown", sixPortalSupportCooldown);
        pCompound.putInt("LowCloneSupportCooldown", lowCloneSupportCooldown);
        pCompound.putInt("RangedCounterPortalCooldown", rangedCounterPortalCooldown);
        pCompound.putInt("PortalCastTicks", portalCastTicks);
        pCompound.putInt("PortalSummonAiLockTicks", portalSummonAiLockTicks);
        pCompound.putInt("LowCloneSummonWeakPointTicks", lowCloneSummonWeakPointTicks);
        pCompound.putInt("QueuedSixPortalSupportLockTicks", queuedSixPortalSupportLockTicks);
        pCompound.putInt("RequestedSupportPortalTicks", requestedSupportPortalTicks);
        pCompound.putByte("RequestedSupportPortalMode", requestedSupportPortalMode);
        pCompound.putInt("SupportCatchUpCooldown", supportCatchUpCooldown);
        if (queuedSixPortalSupportTargetUUID != null) {
            pCompound.putUUID("QueuedSixPortalSupportTargetUUID", queuedSixPortalSupportTargetUUID);
        }
        if (requestedSupportPortalSupportUUID != null) {
            pCompound.putUUID("RequestedSupportPortalSupportUUID", requestedSupportPortalSupportUUID);
        }
        if (requestedSupportPortalTargetUUID != null) {
            pCompound.putUUID("RequestedSupportPortalTargetUUID", requestedSupportPortalTargetUUID);
        }
        if (firstSummonedHerobrineUUID != null) {
            pCompound.putUUID("FirstSummonedHerobrineUUID", firstSummonedHerobrineUUID);
        }
        if (secondSummonedHerobrineUUID != null) {
            pCompound.putUUID("SecondSummonedHerobrineUUID", secondSummonedHerobrineUUID);
        }
        if (thirdSummonedHerobrineUUID != null) {
            pCompound.putUUID("ThirdSummonedHerobrineUUID", thirdSummonedHerobrineUUID);
        }
    }

    public static boolean canSpawn(EntityType<HerobrineGregEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        ServerLevel serverLevel = level.getLevel();
        int passesDay = (int) (serverLevel.getGameTime() / 24000);
        if (passesDay % 5 != 0) {
            return false;
        }
        if (GregData.get(serverLevel).isOccupied(serverLevel)) {
            return false;
        }
        return Monster.checkMonsterSpawnRules(entityType, level, spawnType, position, random);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
        if (!level().isClientSide && level() instanceof ServerLevel serverLevel &&
                (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED)) {
            GregData.get(serverLevel).releaseIfMatches(serverLevel, this.getUUID());
        }
    }

    public static Builder createAttributes() {
        Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.5D);
        builder = builder.add(Attributes.MAX_HEALTH, 40.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0D);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
        return builder;
    }
}
