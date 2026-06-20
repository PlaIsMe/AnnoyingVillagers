package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.gameasset.AnimsSculkSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.network.ClientboundHerobrinePortalFx;
import com.pla.annoyingvillagers.spawnhandler.HerobrineMobData;
import com.pla.annoyingvillagers.util.HerobrinePortalCombatUtil;
import com.pla.annoyingvillagers.util.HerobrinePortalUtil;
import com.pla.annoyingvillagers.util.TeamUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public class TransporterHerobrineCloneEntity extends HerobrineMob {
    private static final int TICKS_PER_SECOND = 20;
    private static final int ESCAPE_DURATION_TICKS = 70;
    private static final float TRANSPORTER_FRAGMENT_DROP_CHANCE = 0.1F;
    private static final double LOW_HEALTH_ESCAPE_RATIO = 0.35D;
    private static final float ESCAPE_DAMAGE_SUCCESS_CHANCE = 0.15F;
    private static final int LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS = 30 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUPPORT_COOLDOWN_MAX_TICKS = 60 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUPPORT_RETRY_TICKS = 10 * TICKS_PER_SECOND;
    private static final int LOW_CLONE_SUMMON_ANIMATION_TICKS = 5 * TICKS_PER_SECOND;
    private static final int RANGED_COUNTER_PORTAL_COOLDOWN_MIN_TICKS = 10 * TICKS_PER_SECOND;
    private static final int RANGED_COUNTER_PORTAL_COOLDOWN_MAX_TICKS = 15 * TICKS_PER_SECOND;
    private static final int RANGED_COUNTER_PORTAL_RETRY_TICKS = 3 * TICKS_PER_SECOND;
    private static final double SUPPORT_AVOID_SEARCH_RADIUS = 32.0D;
    private static final double SUPPORT_AVOID_TRIGGER_DISTANCE_SQR = 18.0D * 18.0D;
    private static final double SUPPORT_AVOID_MIN_DISTANCE = 12.0D;
    private static final double SUPPORT_AVOID_MAX_DISTANCE = 20.0D;
    private static final int SUPPORT_AVOID_REPATH_TICKS = 15;
    private static final double SUPPORT_AVOID_MOVE_SPEED = 1.15D;
    private static final List<Item> LOW_CLONE_SUPPORT_WEAPONS = List.of(
            Items.IRON_SWORD,
            Items.DIAMOND_SWORD,
            AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get(),
            AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get()
    );

    private int escapeTiming = -1;
    private int escapeRetryCooldown = 0;
    private int lowCloneSummonCooldown = LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS;
    private int portalSupportCooldown = 120;
    private int rangedCounterPortalCooldown = RANGED_COUNTER_PORTAL_RETRY_TICKS;
    private int supportAvoidRepathCooldown = 0;
    private int lowCloneSummonAnimationTicks = 0;

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
        this.lowCloneSummonCooldown = randomLowCloneSupportCooldown();
        this.rangedCounterPortalCooldown = randomRangedCounterPortalCooldown();
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
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            return;
        }

        this.tickEscape();
        if (this.escapeTiming >= 0) {
            this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 1, 3, false, false));
            return;
        }
        if (this.tickLowCloneSummonAnimation()) {
            return;
        }

        if (this.escapeRetryCooldown > 0) {
            this.escapeRetryCooldown--;
        }
        if (this.getHealth() <= this.getMaxHealth() * LOW_HEALTH_ESCAPE_RATIO && this.escapeRetryCooldown <= 0) {
            this.escapeRetryCooldown = 80;
            this.startEscape();
            return;
        }

        if (this.lowCloneSummonCooldown > 0) {
            this.lowCloneSummonCooldown--;
        }
        if (this.lowCloneSummonCooldown <= 0) {
            int spawned = this.summonLowCloneSupport();
            this.lowCloneSummonCooldown = spawned > 0 ? randomLowCloneSupportCooldown() : LOW_CLONE_SUPPORT_RETRY_TICKS;
        }

        this.tickSupportAvoidance();

        boolean portalActionUsed = false;
        if (this.rangedCounterPortalCooldown > 0) {
            this.rangedCounterPortalCooldown--;
        }
        if (this.rangedCounterPortalCooldown <= 0) {
            portalActionUsed = HerobrinePortalCombatUtil.tryBowCounterPortalSupport(this);
            this.rangedCounterPortalCooldown = portalActionUsed
                    ? randomRangedCounterPortalCooldown()
                    : RANGED_COUNTER_PORTAL_RETRY_TICKS;
        }

        if (!portalActionUsed && this.portalSupportCooldown > 0) {
            this.portalSupportCooldown--;
        }
        if (!portalActionUsed && this.portalSupportCooldown <= 0) {
            this.portalSupportCooldown = 220 + this.getRandom().nextInt(180);
            HerobrinePortalCombatUtil.tryTransporterPortalSupport(this);
        }
    }

    private void tickSupportAvoidance() {
        if (this.supportAvoidRepathCooldown > 0) {
            this.supportAvoidRepathCooldown--;
        }

        LivingEntity threat = HerobrinePortalCombatUtil.findThreateningEnemy(this, null, SUPPORT_AVOID_SEARCH_RADIUS);
        if (threat == null) {
            threat = HerobrinePortalCombatUtil.findEnemyForSupport(this, null, SUPPORT_AVOID_SEARCH_RADIUS);
        }
        this.setTarget(null);
        if (threat == null || !threat.isAlive()) {
            return;
        }

        this.getLookControl().setLookAt(threat, 30.0F, 30.0F);
        if (this.distanceToSqr(threat) > SUPPORT_AVOID_TRIGGER_DISTANCE_SQR) {
            return;
        }
        if (this.supportAvoidRepathCooldown > 0 && !this.getNavigation().isDone()) {
            return;
        }

        Vec3 retreatPos = findSupportRetreatPosition(threat);
        if (retreatPos == null) {
            return;
        }

        this.getNavigation().moveTo(retreatPos.x, retreatPos.y, retreatPos.z, SUPPORT_AVOID_MOVE_SPEED);
        this.supportAvoidRepathCooldown = SUPPORT_AVOID_REPATH_TICKS;
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
                    new ClientboundHerobrinePortalFx(this.position().add(0.0D, 1.5D, 0.0D))
            );
        }
    }

    private void cancelEscapeAndDropFragment() {
        this.escapeTiming = -1;
        this.setNoAi(false);
        this.noPhysics = false;
        this.setNoGravity(false);
        this.setInvulnerable(false);
        this.getPersistentData().remove(HerobrinePortalUtil.NBT_SINKING);
        this.getPersistentData().remove(HerobrinePortalUtil.NBT_SINK_TARGET_Y);
        this.getPersistentData().remove(HerobrinePortalUtil.NBT_SINK_SPEED);
        this.getPersistentData().remove(HerobrinePortalUtil.NBT_SINK_TICKS);
        this.getPersistentData().remove(HerobrinePortalUtil.NBT_SINK_MAX_TICKS);
        this.spawnAtLocation(new ItemStack(AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get()));
    }

    private int randomLowCloneSupportCooldown() {
        return LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS
                + this.getRandom().nextInt(LOW_CLONE_SUPPORT_COOLDOWN_MAX_TICKS - LOW_CLONE_SUPPORT_COOLDOWN_MIN_TICKS + 1);
    }

    private int randomRangedCounterPortalCooldown() {
        return RANGED_COUNTER_PORTAL_COOLDOWN_MIN_TICKS
                + this.getRandom().nextInt(RANGED_COUNTER_PORTAL_COOLDOWN_MAX_TICKS - RANGED_COUNTER_PORTAL_COOLDOWN_MIN_TICKS + 1);
    }

    private int summonLowCloneSupport() {
        int count = 1 + this.getRandom().nextInt(3);
        int spawned = 0;
        for (int i = 0; i < count; i++) {
            if (this.summonLowClone()) {
                spawned++;
            }
        }
        if (spawned > 0) {
            this.startLowCloneSummonAnimation();
            HerobrinePortalCombatUtil.playClonePortalSummon(this);
        }
        return spawned;
    }

    private void startLowCloneSummonAnimation() {
        this.lowCloneSummonAnimationTicks = LOW_CLONE_SUMMON_ANIMATION_TICKS;
        this.setNoAi(true);
        this.getNavigation().stop();
        this.setSprinting(false);
        this.setTarget(null);
        this.xxa = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);
    }

    private boolean tickLowCloneSummonAnimation() {
        if (this.lowCloneSummonAnimationTicks <= 0) {
            return false;
        }

        this.lowCloneSummonAnimationTicks--;
        this.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 2, 3, false, false));
        this.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 2, 3, false, false));
        this.getNavigation().stop();
        this.setTarget(null);
        this.setSprinting(false);
        this.xxa = 0.0F;
        this.zza = 0.0F;
        Vec3 deltaMovement = this.getDeltaMovement();
        this.setDeltaMovement(0.0D, deltaMovement.y, 0.0D);
        LivingEntity threat = HerobrinePortalCombatUtil.findThreateningEnemy(this, null, SUPPORT_AVOID_SEARCH_RADIUS);
        if (threat != null && threat.isAlive()) {
            this.getLookControl().setLookAt(threat, 30.0F, 30.0F);
        }
        if (this.lowCloneSummonAnimationTicks <= 0 && this.escapeTiming < 0) {
            this.setNoAi(false);
        }
        return true;
    }

    private boolean summonLowClone() {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        Vec3 spawnPos = this.findLowCloneSpawnPosition(serverLevel);
        if (spawnPos == null) {
            return false;
        }

        Entity entity = (this.getRandom().nextBoolean()
                ? AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get()
                : AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get()).create(serverLevel);
        if (!(entity instanceof Mob clone)) {
            return false;
        }

        clone.moveTo(spawnPos.x, spawnPos.y, spawnPos.z, this.getYRot(), this.getXRot());
        if (!serverLevel.noCollision(clone)) {
            return false;
        }
        clone.lookAt(EntityAnchorArgument.Anchor.EYES, this.getEyePosition());
        if (clone instanceof LowHerobrineCloneEntity lowHerobrineClone) {
            lowHerobrineClone.setRenderPortal(true);
        } else if (clone instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineClone) {
            lowShadowHerobrineClone.setRenderPortal(true);
        }

        equipLowCloneGear(clone);
        LivingEntity enemy = HerobrinePortalCombatUtil.findThreateningEnemy(this, null, SUPPORT_AVOID_SEARCH_RADIUS);
        if (enemy == null) {
            enemy = HerobrinePortalCombatUtil.findEnemyForSupport(this, null, SUPPORT_AVOID_SEARCH_RADIUS);
        }
        if (enemy != null && enemy.isAlive()) {
            clone.setTarget(enemy);
            clone.lookAt(EntityAnchorArgument.Anchor.EYES, enemy.getEyePosition());
        }
        clone.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(clone.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        serverLevel.addFreshEntity(clone);
        TeamUtil.addOrJoinTeam(clone, "herobrine");
        return true;
    }

    private void equipLowCloneGear(Mob clone) {
        if (clone instanceof LowShadowHerobrineCloneEntity && this.getRandom().nextFloat() < 0.2F) {
            clone.setItemSlot(EquipmentSlot.HEAD, damageRandomly(new ItemStack(Items.NETHERITE_HELMET)));
            clone.setItemSlot(EquipmentSlot.CHEST, damageRandomly(new ItemStack(Items.NETHERITE_CHESTPLATE)));
            clone.setItemSlot(EquipmentSlot.LEGS, damageRandomly(new ItemStack(Items.NETHERITE_LEGGINGS)));
            clone.setItemSlot(EquipmentSlot.FEET, damageRandomly(new ItemStack(Items.NETHERITE_BOOTS)));
            clone.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            clone.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.GOLDEN_SWORD));
            return;
        }

        if (this.getRandom().nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.HEAD, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_HELMET.get())));
        }
        if (this.getRandom().nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.CHEST, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_CHESTPLATE.get())));
        }
        if (this.getRandom().nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.LEGS, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_LEGGINGS.get())));
        }
        if (this.getRandom().nextFloat() < 0.3F) {
            clone.setItemSlot(EquipmentSlot.FEET, damageRandomly(new ItemStack(AnnoyingVillagersModItems.BROKEN_DIAMOND_BOOTS.get())));
        }

        Item weapon = LOW_CLONE_SUPPORT_WEAPONS.get(this.getRandom().nextInt(LOW_CLONE_SUPPORT_WEAPONS.size()));
        clone.setItemSlot(EquipmentSlot.MAINHAND, damageRandomly(new ItemStack(weapon)));
        if (clone instanceof LowShadowHerobrineCloneEntity && this.getRandom().nextFloat() < 0.35F) {
            clone.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.GOLDEN_SWORD));
        }
    }

    private ItemStack damageRandomly(ItemStack itemStack) {
        if (!itemStack.isDamageableItem()) {
            return itemStack;
        }
        int maxDamage = itemStack.getMaxDamage();
        itemStack.setDamageValue(this.getRandom().nextInt(Math.max(1, maxDamage / 3), Math.max(2, maxDamage * 3 / 4)));
        return itemStack;
    }

    @Nullable
    private Vec3 findLowCloneSpawnPosition(ServerLevel serverLevel) {
        RandomSource random = this.getRandom();
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double distance = 3.0D + random.nextDouble() * 7.0D;
            double x = this.getX() + Math.cos(angle) * distance;
            double z = this.getZ() + Math.sin(angle) * distance;
            int groundX = Mth.floor(x);
            int groundZ = Mth.floor(z);
            int y = serverLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, groundX, groundZ);
            BlockPos surface = BlockPos.containing(groundX, y, groundZ);

            if (!serverLevel.isLoaded(surface) || !serverLevel.getWorldBorder().isWithinBounds(surface)) {
                continue;
            }
            if (!serverLevel.isEmptyBlock(surface) || !serverLevel.isEmptyBlock(surface.above()) || serverLevel.isEmptyBlock(surface.below())) {
                continue;
            }

            return new Vec3(x, surface.getY(), z);
        }
        return null;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (this.lowCloneSummonAnimationTicks > 0) {
            if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                return super.hurt(damageSource, amount);
            }
            return this.getRandom().nextFloat() < ESCAPE_DAMAGE_SUCCESS_CHANCE
                    && super.hurt(damageSource, 1.0F);
        }
        if (this.escapeTiming >= 0) {
            if (damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                return super.hurt(damageSource, amount);
            }
            return this.getRandom().nextFloat() < ESCAPE_DAMAGE_SUCCESS_CHANCE
                    && super.hurt(damageSource, 1.0F);
        }

        if (damageSource.is(DamageTypes.FALL)) return false;
        if (damageSource.is(DamageTypes.CACTUS)) return false;
        if (damageSource.is(DamageTypes.WITHER)) return false;
        if (damageSource.is(DamageTypes.DROWN)) return false;
        if (damageSource.is(DamageTypes.WITHER_SKULL)) return false;
        if (damageSource.is(DamageTypes.DRAGON_BREATH)) return false;

        return damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)
                ? super.hurt(damageSource, amount)
                : super.hurt(damageSource, 1.0F);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource damageSource, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(damageSource, looting, recentlyHit);
        if (this.escapeTiming >= 0) {
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
        this.lowCloneSummonCooldown = compoundTag.contains("LowCloneSummonCooldown") ? compoundTag.getInt("LowCloneSummonCooldown") : randomLowCloneSupportCooldown();
        this.portalSupportCooldown = compoundTag.contains("PortalSupportCooldown") ? compoundTag.getInt("PortalSupportCooldown") : 120;
        this.rangedCounterPortalCooldown = compoundTag.contains("RangedCounterPortalCooldown") ? compoundTag.getInt("RangedCounterPortalCooldown") : randomRangedCounterPortalCooldown();
        this.supportAvoidRepathCooldown = compoundTag.contains("SupportAvoidRepathCooldown") ? compoundTag.getInt("SupportAvoidRepathCooldown") : 0;
        this.lowCloneSummonAnimationTicks = compoundTag.contains("LowCloneSummonAnimationTicks") ? compoundTag.getInt("LowCloneSummonAnimationTicks") : 0;
        this.setNoAi(this.escapeTiming >= 0 || this.lowCloneSummonAnimationTicks > 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TransporterEscapeTiming", this.escapeTiming);
        compoundTag.putInt("TransporterEscapeRetryCooldown", this.escapeRetryCooldown);
        compoundTag.putInt("LowCloneSummonCooldown", this.lowCloneSummonCooldown);
        compoundTag.putInt("PortalSupportCooldown", this.portalSupportCooldown);
        compoundTag.putInt("RangedCounterPortalCooldown", this.rangedCounterPortalCooldown);
        compoundTag.putInt("SupportAvoidRepathCooldown", this.supportAvoidRepathCooldown);
        compoundTag.putInt("LowCloneSummonAnimationTicks", this.lowCloneSummonAnimationTicks);
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
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
}
