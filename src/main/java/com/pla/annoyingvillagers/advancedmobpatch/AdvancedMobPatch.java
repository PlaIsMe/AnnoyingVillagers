package com.pla.annoyingvillagers.advancedmobpatch;

import com.pla.annoyingvillagers.mixin.WeaponCapabilityAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.AttackResult.ResultType;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPChangeLivingMotion;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;
import yesman.epicfight.world.entity.ai.goal.TargetChasingGoal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class AdvancedMobPatch<T extends Mob> extends MobPatch<T> {
    private static final long GENERATED_CHAIN_SEED = 0xA71E5L;
    private static final int DEFAULT_GUARD_DURATION_TICKS = 40;
    private static final int DEFAULT_GUARD_COOLDOWN_TICKS = 20;
    private static final int DEFAULT_GUARD_RETRY_TICKS = 10;
    private static final double DEFAULT_GUARD_DISTANCE = 3.0D;

    private AdvancedCombatBehaviors<MobPatch<?>> combatBehaviors;
    private int attackRadius = 1;
    private double chasingSpeed = 1.25D;
    private int recoverTickCount;
    private int lastActionTime;
    private boolean guardingLocally;
    private int guardTicksRemaining;
    private int guardCooldownTicks;
    private AssetAccessor<? extends StaticAnimation> activeGuardAnimation;
    private final Map<LivingMotion, AssetAccessor<? extends StaticAnimation>> defaultLivingMotions = new HashMap<>();
    private boolean defaultLivingMotionsCaptured;
    private float stamina;
    private AdvancedStaminaStatus staminaStatus = AdvancedStaminaStatus.COMMON;

    protected AdvancedMobPatch(Factions factions) {
        super(factions);
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        this.commonMobUpdateMotion(considerInaction);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.stamina = this.getMaxStamina();
        this.staminaStatus = AdvancedStaminaStatus.COMMON;
        this.modifyLivingMotionByCurrentItem(false);
    }

    @Override
    public void onStartTracking(ServerPlayer trackingPlayer) {
        super.onStartTracking(trackingPlayer);
        this.modifyLivingMotionByCurrentItem(true);
    }

    @Override
    public void tick(LivingTickEvent livingTickEvent) {
        super.tick(livingTickEvent);
        this.tickLocalGuard();

        float maxStamina = this.getMaxStamina();
        float stamina = this.stamina;
        AdvancedStaminaStatus status = this.staminaStatus;

        if (status == AdvancedStaminaStatus.COMMON) {
            this.recoverTickCount = 0;
            if (stamina <= 0.0F) {
                this.staminaStatus = AdvancedStaminaStatus.BREAK;
                return;
            }

            AttributeInstance staminaRegen = this.getOriginal().getAttribute(EpicFightAttributes.STAMINA_REGEN.get());
            if (this.getEntityState().inaction() || this.guardingLocally) {
                this.lastActionTime = this.getOriginal().tickCount;
            } else if (staminaRegen != null
                    && this.getOriginal().tickCount - this.lastActionTime > 60
                    && stamina < maxStamina) {
                float regenerated = maxStamina * 0.01F * (float) staminaRegen.getValue();
                this.setStamina(Math.min(maxStamina, stamina + regenerated));
            }
            return;
        }

        this.recoverTickCount++;
        if (status == AdvancedStaminaStatus.BREAK && this.recoverTickCount >= 40) {
            this.staminaStatus = AdvancedStaminaStatus.RECOVER;
        } else if (status == AdvancedStaminaStatus.RECOVER) {
            float progress = Mth.clamp((this.recoverTickCount - 40) / 60.0F, 0.0F, 1.0F);
            this.setStamina(Mth.lerp(progress, 0.0F, maxStamina));
            if (progress >= 1.0F) {
                this.staminaStatus = AdvancedStaminaStatus.COMMON;
            }
        }
    }

    @Override
    protected void initAI() {
        super.initAI();

        AdvancedCombatBehaviors.Builder<MobPatch<?>> builder = this.createCombatBehaviorBuilder();
        this.combatBehaviors = builder.build();
        this.getOriginal().goalSelector.addGoal(0, new AdvancedAnimationAttackGoal<>(
                this,
                this.combatBehaviors,
                () -> !this.guardingLocally
        ));
        this.getOriginal().goalSelector.addGoal(1, new AdvancedChasingGoal(
                this,
                this.attackRadius,
                this.chasingSpeed,
                () -> !this.guardingLocally
        ));
    }

    @Override
    protected void selectGoalToRemove(Set<Goal> toRemove) {
        super.selectGoalToRemove(toRemove);

        for (WrappedGoal wrappedGoal : this.getOriginal().goalSelector.getAvailableGoals()) {
            Goal goal = wrappedGoal.getGoal();
            if (goal instanceof MeleeAttackGoal
                    || goal instanceof AnimatedAttackGoal<?>
                    || goal instanceof AdvancedAnimationAttackGoal<?>
                    || goal instanceof RangedAttackGoal
                    || goal instanceof TargetChasingGoal
                    || goal instanceof AdvancedChasingGoal) {
                toRemove.add(goal);
            }
        }
    }

    private AdvancedCombatBehaviors.Builder<MobPatch<?>> createCombatBehaviorBuilder() {
        CapabilityItem mainHandCap = this.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        CapabilityItem offHandCap = this.getHoldingItemCapability(InteractionHand.OFF_HAND);
        Style style = mainHandCap.getStyle(this);
        AdvancedCombatBehaviors.Builder<MobPatch<?>> builder = AdvancedCombatBehaviors.builder();

        this.addCustomBehaviorRoots(builder, mainHandCap, offHandCap, style);

        WeaponMoveset moveset = this.getWeaponCapabilityMoveset(mainHandCap, style);
        if (!moveset.isEmpty()) {
            builder.newBehaviorRoot(this.createGeneratedAttackRoot(moveset, mainHandCap, offHandCap, style));
        }
        return builder;
    }

    /** Adds roots owned by a concrete patch to the generated moveset builder. */
    protected void addCustomBehaviorRoots(
            AdvancedCombatBehaviors.Builder<MobPatch<?>> builder,
            CapabilityItem mainHandCap,
            CapabilityItem offHandCap,
            Style style
    ) {
    }

    protected boolean canGenerateWeaponCapabilityMoveset(CapabilityItem mainHandCap, Style style) {
        WeaponCategory category = mainHandCap.getWeaponCategory();
        return !mainHandCap.isEmpty()
                && mainHandCap instanceof WeaponCapability
                && category != WeaponCategories.NOT_WEAPON
                && category != WeaponCategories.FIST
                && category != WeaponCategories.BOW
                && category != WeaponCategories.CROSSBOW
                && category != WeaponCategories.SHIELD;
    }

    /**
     * Splits Epic Fight's auto-attack convention into normal, dash, and jump
     * attacks. Three-entry lists are normal-only; longer lists reserve the last
     * two entries for dash and jump respectively.
     */
    protected WeaponMoveset getWeaponCapabilityMoveset(CapabilityItem mainHandCap, Style style) {
        if (!this.canGenerateWeaponCapabilityMoveset(mainHandCap, style)
                || !(mainHandCap instanceof WeaponCapabilityAccessor accessor)) {
            return WeaponMoveset.empty();
        }

        Map<Style, List<AnimationAccessor<? extends AttackAnimation>>> autoAttackMotions =
                accessor.annoyingvillagers$getAutoAttackMotions();
        List<AnimationAccessor<? extends AttackAnimation>> configured =
                autoAttackMotions.getOrDefault(style, autoAttackMotions.get(Styles.COMMON));
        List<AnimationAccessor<? extends StaticAnimation>> animations = copyAnimations(configured);

        if (animations.size() < 4) {
            return new WeaponMoveset(animations, null, null);
        }

        int dashIndex = animations.size() - 2;
        int jumpIndex = animations.size() - 1;
        return new WeaponMoveset(
                animations.subList(0, dashIndex),
                animations.get(dashIndex),
                animations.get(jumpIndex)
        );
    }

    protected List<AdditionalAttackGroup> getAdditionalAttackGroups(
            CapabilityItem mainHandCap,
            CapabilityItem offHandCap,
            Style style
    ) {
        return List.of();
    }

    protected int getNormalAttacksPerChain() {
        return 3;
    }

    protected int getGeneratedChainCount() {
        return 50;
    }

    protected void setAttackRadius(int attackRadius) {
        this.attackRadius = Math.max(0, attackRadius);
    }

    protected void setChasingSpeed(double chasingSpeed) {
        this.chasingSpeed = Math.max(0.0D, chasingSpeed);
    }

    protected boolean canPerformGeneratedAttack() {
        LivingEntity target = this.getTarget();
        return !this.guardingLocally
                && !this.isStunned()
                && this.getEntityState().canBasicAttack()
                && target != null
                && target.isAlive();
    }

    private AdvancedCombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> createGeneratedAttackRoot(
            WeaponMoveset moveset,
            CapabilityItem mainHandCap,
            CapabilityItem offHandCap,
            Style style
    ) {
        AdvancedCombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root = AdvancedCombatBehaviors.BehaviorRoot.builder()
                .rootName("GeneratedWeaponCombo")
                .priority(1.0D)
                .weight(40.0D)
                .maxCooldown(20);
        List<AdditionalAttackGroup> additionalGroups = this.getAdditionalAttackGroups(mainHandCap, offHandCap, style);
        Random random = new Random(
                GENERATED_CHAIN_SEED
                        + moveset.normalAttacks().size() * 31L
                        + (moveset.dashAttack() == null ? 0L : 1L)
                        + (moveset.jumpAttack() == null ? 0L : 2L)
        );

        for (int combo = 0; combo < Math.max(1, this.getGeneratedChainCount()); combo++) {
            List<AnimationAccessor<? extends StaticAnimation>> chain = new ArrayList<>();
            appendNormalChain(chain, moveset.normalAttacks(), this.getNormalAttacksPerChain(), random);
            insertRandom(chain, moveset.pickMovementAttack(random), random);

            if (additionalGroups != null) {
                for (AdditionalAttackGroup group : additionalGroups) {
                    if (group != null && group.shouldSelect(random)) {
                        insertRandom(chain, group.pick(random), random);
                    }
                }
            }

            if (!chain.isEmpty()) {
                root.addFirstBehavior(combatChain(chain));
            }
        }
        return root;
    }

    private static void appendNormalChain(
            List<AnimationAccessor<? extends StaticAnimation>> chain,
            List<AnimationAccessor<? extends StaticAnimation>> normalAttacks,
            int normalAttacksPerChain,
            Random random
    ) {
        if (normalAttacks.isEmpty()) {
            return;
        }

        int start = random.nextInt(normalAttacks.size());
        int count = Math.min(Math.max(1, normalAttacksPerChain), normalAttacks.size());
        for (int index = 0; index < count; index++) {
            chain.add(normalAttacks.get((start + index) % normalAttacks.size()));
        }
    }

    private AdvancedCombatBehaviors.Behavior.Builder<MobPatch<?>> combatChain(
            List<AnimationAccessor<? extends StaticAnimation>> chain
    ) {
        AdvancedCombatBehaviors.Behavior.Builder<MobPatch<?>> builder = this.combatStep(
                chain.get(chain.size() - 1),
                chain.size() - 1
        );
        for (int index = chain.size() - 2; index >= 0; index--) {
            builder = this.combatStep(chain.get(index), index).addNextBehavior(builder);
        }
        return builder;
    }

    private AdvancedCombatBehaviors.Behavior.Builder<MobPatch<?>> combatStep(
            AnimationAccessor<? extends StaticAnimation> animation,
            int index
    ) {
        double maxDistance = index < 2 ? 3.0D : index < 4 ? 4.0D : 5.0D;
        return AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                .custom(ignored -> this.canPerformGeneratedAttack())
                .withinDistance(0.0D, maxDistance)
                .animationBehavior(animation, 0.0F);
    }

    private static void insertRandom(
            List<AnimationAccessor<? extends StaticAnimation>> chain,
            AnimationAccessor<? extends StaticAnimation> animation,
            Random random
    ) {
        if (animation != null) {
            chain.add(chain.isEmpty() ? 0 : random.nextInt(chain.size() + 1), animation);
        }
    }

    private static List<AnimationAccessor<? extends StaticAnimation>> copyAnimations(
            Collection<? extends AnimationAccessor<? extends StaticAnimation>> animations
    ) {
        if (animations == null || animations.isEmpty()) {
            return List.of();
        }

        List<AnimationAccessor<? extends StaticAnimation>> copy = new ArrayList<>();
        for (AnimationAccessor<? extends StaticAnimation> animation : animations) {
            if (animation != null) {
                copy.add(animation);
            }
        }
        return List.copyOf(copy);
    }

    public final AdvancedCombatBehaviors<?> getAdvancedCombatBehaviors() {
        return this.combatBehaviors;
    }

    @Override
    public void updateHeldItem(
            CapabilityItem fromCap,
            CapabilityItem toCap,
            ItemStack from,
            ItemStack to,
            InteractionHand hand
    ) {
        boolean shouldRefreshCombatGoal = hand == InteractionHand.MAIN_HAND
                ? fromCap.getWeaponCategory() != toCap.getWeaponCategory() || fromCap != toCap
                : hand == InteractionHand.OFF_HAND;

        super.updateHeldItem(fromCap, toCap, from, to, hand);
        this.modifyLivingMotionByCurrentItem(!this.getOriginal().level().isClientSide());
        if (shouldRefreshCombatGoal) {
            this.initAI();
        }
    }

    /**
     * Applies the active item capabilities' living-motion preset without relying on
     * HumanoidMobPatch. The animations registered by the concrete patch remain the
     * fallback for motions the capability does not replace.
     */
    public void modifyLivingMotionByCurrentItem(boolean resendPacket) {
        Animator animator = this.getAnimator();
        if (!this.defaultLivingMotionsCaptured) {
            this.defaultLivingMotions.putAll(animator.getLivingAnimations());
            this.defaultLivingMotionsCaptured = true;
        }

        Map<LivingMotion, AssetAccessor<? extends StaticAnimation>> livingMotions =
                new HashMap<>(this.defaultLivingMotions);
        this.applyCapabilityLivingMotions(
                livingMotions,
                this.getHoldingItemCapability(InteractionHand.MAIN_HAND),
                InteractionHand.MAIN_HAND
        );
        this.applyCapabilityLivingMotions(
                livingMotions,
                this.getAdvancedHoldingItemCapability(InteractionHand.OFF_HAND),
                InteractionHand.OFF_HAND
        );

        animator.resetLivingAnimations();
        livingMotions.forEach(animator::addLivingAnimation);

        if (resendPacket && !this.getOriginal().level().isClientSide()) {
            SPChangeLivingMotion packet = new SPChangeLivingMotion(this.getOriginal().getId());
            packet.putEntries(livingMotions.entrySet());
            EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(packet, this.getOriginal());
        }
    }

    private void applyCapabilityLivingMotions(
            Map<LivingMotion, AssetAccessor<? extends StaticAnimation>> target,
            CapabilityItem capability,
            InteractionHand hand
    ) {
        Map<LivingMotion, AnimationAccessor<? extends StaticAnimation>> modifiers =
                capability.getLivingMotionModifier(this, hand);
        if (modifiers != null) {
            modifiers.forEach(target::put);
        }
    }

    /* Guarding is fully owned here. Concrete patches only decide eligibility and chance. */
    public boolean canGuard() {
        return false;
    }

    public int getGuardChance() {
        return 0;
    }

    protected int getGuardDurationTicks() {
        return DEFAULT_GUARD_DURATION_TICKS;
    }

    protected int getGuardCooldownTicks() {
        return DEFAULT_GUARD_COOLDOWN_TICKS;
    }

    protected int getGuardRetryTicks() {
        return DEFAULT_GUARD_RETRY_TICKS;
    }

    protected double getGuardDistance() {
        return DEFAULT_GUARD_DISTANCE;
    }

    public final boolean isGuardingLocally() {
        return this.guardingLocally;
    }

    public final void cancelGuard() {
        this.stopLocalGuard();
    }

    public final float getStamina() {
        return this.stamina;
    }

    public final void setStamina(float stamina) {
        this.stamina = Mth.clamp(stamina, 0.0F, this.getMaxStamina());
    }

    public final void addStamina(float amount) {
        this.setStamina(this.stamina + amount);
    }

    public final float getMaxStamina() {
        AttributeInstance maxStamina = this.getOriginal().getAttribute(EpicFightAttributes.MAX_STAMINA.get());
        return maxStamina == null ? 15.0F : (float) maxStamina.getValue();
    }

    public final AdvancedStaminaStatus getStaminaStatus() {
        return this.staminaStatus;
    }

    public boolean canBeExecuted(LivingEntityPatch<?> executorPatch) {
        return true;
    }

    private void tickLocalGuard() {
        if (this.getOriginal().level().isClientSide()) {
            return;
        }

        if (this.guardCooldownTicks > 0) {
            this.guardCooldownTicks--;
        }

        LivingEntity target = this.getOriginal().getTarget();
        if (this.guardingLocally) {
            this.guardTicksRemaining--;
            if (!this.canContinueLocalGuard(target)) {
                this.stopLocalGuard();
                return;
            }

            this.getOriginal().getNavigation().stop();
            this.getOriginal().getLookControl().setLookAt(target, 30.0F, 30.0F);
            return;
        }

        if (this.guardCooldownTicks > 0 || !this.canStartLocalGuard(target)) {
            return;
        }

        int chance = Mth.clamp(this.getGuardChance(), 0, 100);
        if (chance > 0 && this.getOriginal().getRandom().nextInt(100) < chance) {
            this.startLocalGuard();
        } else {
            this.guardCooldownTicks = Math.max(1, this.getGuardRetryTicks());
        }
    }

    private boolean canStartLocalGuard(LivingEntity target) {
        return this.canGuard()
                && this.isValidGuardTarget(target)
                && !this.isStunned()
                && !this.getEntityState().inaction()
                && this.staminaStatus == AdvancedStaminaStatus.COMMON
                && this.stamina > 0.0F
                && (this.combatBehaviors == null || this.combatBehaviors.getCurrentBehavior() == null);
    }

    private boolean canContinueLocalGuard(LivingEntity target) {
        return this.guardTicksRemaining > 0
                && this.canGuard()
                && this.isValidGuardTarget(target)
                && !this.isStunned()
                && this.staminaStatus == AdvancedStaminaStatus.COMMON
                && this.stamina > 0.0F;
    }

    private boolean isValidGuardTarget(LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }

        double distance = Math.max(0.0D, this.getGuardDistance());
        return this.getOriginal().distanceToSqr(target) <= distance * distance;
    }

    private void startLocalGuard() {
        this.guardingLocally = true;
        this.guardTicksRemaining = Math.max(1, this.getGuardDurationTicks());
        this.getOriginal().getNavigation().stop();
        this.activeGuardAnimation = this.getLocalGuardAnimation();
        this.playAnimationSynchronized(this.activeGuardAnimation, 0.0F);
    }

    private void stopLocalGuard() {
        if (!this.guardingLocally) {
            return;
        }

        this.guardingLocally = false;
        this.guardTicksRemaining = 0;
        this.guardCooldownTicks = Math.max(this.guardCooldownTicks, Math.max(0, this.getGuardCooldownTicks()));

        AssetAccessor<? extends StaticAnimation> guardAnimation = this.activeGuardAnimation == null
                ? this.getLocalGuardAnimation()
                : this.activeGuardAnimation;
        this.activeGuardAnimation = null;
        if (this.isLogicalClient()) {
            this.getAnimator().stopPlaying(guardAnimation);
        } else {
            this.stopPlaying(guardAnimation);
        }
    }

    protected AssetAccessor<? extends StaticAnimation> getLocalGuardAnimation() {
        AnimationAccessor<? extends StaticAnimation> mainHandGuard = this.getCapabilityGuardAnimation(
                InteractionHand.MAIN_HAND
        );
        if (mainHandGuard != null) {
            return mainHandGuard;
        }

        AnimationAccessor<? extends StaticAnimation> offHandGuard = this.getCapabilityGuardAnimation(
                InteractionHand.OFF_HAND
        );
        if (offHandGuard != null) {
            return offHandGuard;
        }

        return this.getAnimator().getLivingAnimation(LivingMotions.BLOCK, Animations.SWORD_GUARD);
    }

    private AnimationAccessor<? extends StaticAnimation> getCapabilityGuardAnimation(InteractionHand hand) {
        CapabilityItem capability = this.getHoldingItemCapability(hand);
        Map<LivingMotion, AnimationAccessor<? extends StaticAnimation>> livingMotions =
                capability.getLivingMotionModifier(this, hand);
        return livingMotions == null ? null : livingMotions.get(LivingMotions.BLOCK);
    }

    public void playGuardBreakSound() {
        this.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 1.0F, 1.0F);
    }

    public void playGuardHitSound() {
        this.playSound(EpicFightSounds.CLASH.get(), 1.0F, 1.0F);
    }

    public AnimationAccessor<? extends StaticAnimation> getGuardHitAnimation(DamageSource damageSource) {
        CapabilityItem mainHandCap = this.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        if (mainHandCap.getWeaponCategory() == WeaponCategories.SWORD) {
            return mainHandCap.getStyle(this) == Styles.TWO_HAND
                    ? Animations.SWORD_DUAL_GUARD_HIT
                    : this.getOriginal().getRandom().nextBoolean()
                    ? Animations.SWORD_GUARD_ACTIVE_HIT1
                    : Animations.SWORD_GUARD_ACTIVE_HIT2;
        }
        return Animations.EMPTY_ANIMATION;
    }

    public void playGuardHitAnimation(DamageSource damageSource, boolean canCounter) {
        if (this.getOriginal().level() instanceof ServerLevel serverLevel) {
            Vec3 position = this.getOriginal().getEyePosition()
                    .add(this.getOriginal().getLookAngle().normalize().scale(1.25D));
            serverLevel.sendParticles(EpicFightParticles.HIT_BLUNT.get(), position.x, position.y, position.z,
                    1, 0.0D, 0.0D, 0.0D, 1.0D);
        }
        this.playAnimationSynchronized(this.getGuardHitAnimation(damageSource), 0.0F);
        this.playGuardHitSound();
    }

    public boolean dealStaminaDamage(DamageSource damageSource, float amount) {
        if (this.staminaStatus != AdvancedStaminaStatus.COMMON) {
            return false;
        }

        float stamina = this.stamina;
        this.setStamina(stamina - amount);
        if (amount < stamina) {
            return false;
        }

        this.staminaStatus = AdvancedStaminaStatus.BREAK;
        this.stopLocalGuard();
        this.applyStun(StunType.NEUTRALIZE, 0.0F);
        this.playGuardBreakSound();
        return true;
    }

    public void onGuardHit(DamageSource damageSource) {
        EpicFightDamageSource epicFightDamageSource = damageSource instanceof EpicFightDamageSource source ? source : null;
        float impact = epicFightDamageSource == null ? 0.5F : epicFightDamageSource.calculateImpact();
        if (this.dealStaminaDamage(damageSource, impact)) {
            return;
        }

        this.stopLocalGuard();
        this.playGuardHitAnimation(damageSource, false);

        if (this.getOriginal().level() instanceof ServerLevel serverLevel) {
            EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(
                    serverLevel,
                    HitParticleType.FRONT_OF_EYES,
                    HitParticleType.ZERO,
                    this.getOriginal(),
                    damageSource.getEntity()
            );
        }
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        AttackResult result = super.tryHurt(damageSource, amount);
        if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.combatBehaviors != null) {
            result = this.combatBehaviors.executeCurrentBehaviorOnHurt(this, damageSource, result);
        }

        if (damageSource.getDirectEntity() == null
                || result.resultType != ResultType.SUCCESS
                || !this.guardingLocally
                || this.isStunned()
                || damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || damageSource.is(EpicFightDamageTypeTags.UNBLOCKALBE)
                || damageSource.is(EpicFightDamageTypeTags.GUARD_PUNCTURE)) {
            return result;
        }

        this.onGuardHit(damageSource);
        Entity attacker = damageSource.getEntity() == null ? damageSource.getDirectEntity() : damageSource.getEntity();
        LivingEntityPatch<?> attackerPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
        if (attackerPatch != null) {
            attackerPatch.onAttackBlocked(damageSource, this);
        }
        return AttackResult.blocked(0.0F);
    }

    @Override
    public AnimationAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
        return switch (stunType) {
            case LONG -> Animations.BIPED_HIT_LONG;
            case SHORT, HOLD -> Animations.BIPED_HIT_SHORT;
            case KNOCKDOWN -> Animations.BIPED_KNOCKDOWN;
            case NEUTRALIZE -> this.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory()
                    == WeaponCategories.GREATSWORD
                    ? Animations.GREATSWORD_GUARD_BREAK
                    : Animations.BIPED_COMMON_NEUTRALIZED;
            case FALL -> Animations.BIPED_LANDING;
            default -> null;
        };
    }

    public record WeaponMoveset(
            List<AnimationAccessor<? extends StaticAnimation>> normalAttacks,
            AnimationAccessor<? extends StaticAnimation> dashAttack,
            AnimationAccessor<? extends StaticAnimation> jumpAttack
    ) {
        public WeaponMoveset {
            normalAttacks = normalAttacks == null ? List.of() : List.copyOf(normalAttacks);
        }

        public static WeaponMoveset empty() {
            return new WeaponMoveset(List.of(), null, null);
        }

        public boolean isEmpty() {
            return this.normalAttacks.isEmpty() && this.dashAttack == null && this.jumpAttack == null;
        }

        private AnimationAccessor<? extends StaticAnimation> pickMovementAttack(Random random) {
            if (this.dashAttack == null) {
                return this.jumpAttack;
            }
            if (this.jumpAttack == null) {
                return this.dashAttack;
            }
            return random.nextBoolean() ? this.dashAttack : this.jumpAttack;
        }
    }

    public enum AdditionalAttackMode {
        FORCED,
        RANDOM
    }

    public static final class AdditionalAttackGroup {
        private static final float DEFAULT_RANDOM_CHANCE = 0.35F;

        private final AdditionalAttackMode mode;
        private final float chance;
        private final List<AnimationAccessor<? extends StaticAnimation>> animations;

        private AdditionalAttackGroup(
                AdditionalAttackMode mode,
                float chance,
                Collection<? extends AnimationAccessor<? extends StaticAnimation>> animations
        ) {
            this.mode = mode;
            this.chance = Math.max(0.0F, Math.min(1.0F, chance));
            this.animations = copyAnimations(animations);
        }

        @SafeVarargs
        public static AdditionalAttackGroup forced(AnimationAccessor<? extends StaticAnimation>... animations) {
            return new AdditionalAttackGroup(AdditionalAttackMode.FORCED, 1.0F, Arrays.asList(animations));
        }

        @SafeVarargs
        public static AdditionalAttackGroup random(AnimationAccessor<? extends StaticAnimation>... animations) {
            return random(DEFAULT_RANDOM_CHANCE, animations);
        }

        @SafeVarargs
        public static AdditionalAttackGroup random(
                float chance,
                AnimationAccessor<? extends StaticAnimation>... animations
        ) {
            return new AdditionalAttackGroup(AdditionalAttackMode.RANDOM, chance, Arrays.asList(animations));
        }

        private boolean shouldSelect(Random random) {
            return !this.animations.isEmpty()
                    && (this.mode == AdditionalAttackMode.FORCED || random.nextFloat() < this.chance);
        }

        private AnimationAccessor<? extends StaticAnimation> pick(Random random) {
            return this.animations.get(random.nextInt(this.animations.size()));
        }
    }
}
