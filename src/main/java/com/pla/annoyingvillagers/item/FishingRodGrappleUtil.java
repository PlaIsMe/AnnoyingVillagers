package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.ItemProjectile;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.TransporterHerobrineCloneEntity;
import com.pla.annoyingvillagers.mixin.FishingHookAccessor;
import java.util.Optional;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;

public final class FishingRodGrappleUtil {
    private enum HerobrineEscapeHookResult {
        NONE,
        CANCELLED,
        FAILED
    }

    private static final String KEY_GRAPPLE_HOOK = "avGrappleFishingRod";
    private static final String KEY_RETURNING = "avReturningToRod";
    private static final String KEY_STICKY_TARGET_ID = "avStickyTargetId";
    private static final String KEY_STICKY_ITEM_PROJECTILE_ID = "avStickyItemProjectileId";
    private static final String KEY_COLLECT_RETURNING_ITEM = "avCollectReturningItem";
    private static final String KEY_SUPPRESS_STICKY_ITEM_RELEASE = "avSuppressStickyItemRelease";
    private static final String KEY_PENDING_RETURN_DAMAGE = "avPendingReturnDamage";
    private static final String KEY_LATCHED = "avLatched";
    private static final String KEY_TARGET_PLUNGED = "avTargetPlunged";
    private static final String KEY_HEROBRINE_ESCAPE_HOOK_ATTEMPTED_TARGET_ID = "avHerobrineEscapeHookAttemptedTargetId";
    private static final String KEY_NPC_COMBAT_HOOK = "avNpcCombatFishingHook";
    private static final String KEY_NPC_HOOK_RETURNING = "avNpcHookReturning";
    private static final String KEY_NPC_HOOK_LIFE = "avNpcHookLife";
    private static final String KEY_NPC_HOOK_RESOLVED = "avNpcHookResolved";
    private static final String KEY_NPC_HOOK_TIMED_OUT = "avNpcHookTimedOut";
    private static final String KEY_NPC_HOOK_TARGET_X = "avNpcHookTargetX";
    private static final String KEY_NPC_HOOK_TARGET_Y = "avNpcHookTargetY";
    private static final String KEY_NPC_HOOK_TARGET_Z = "avNpcHookTargetZ";
    private static final String KEY_NPC_HOOK_TARGET_ENTITY_ID = "avNpcHookTargetEntityId";
    private static final String KEY_ANCHOR_X = "avAX";
    private static final String KEY_ANCHOR_Y = "avAY";
    private static final String KEY_ANCHOR_Z = "avAZ";
    private static final double LATCH_STOPPED_SPEED_SQR = 1.0e-3D;
    private static final double TONY_PLAYER_GROUNDED_PLUNGE_POWER = 4.1D;
    private static final double TONY_PLAYER_AIRBORNE_PLUNGE_POWER = 3.1D;
    private static final double ADVANCED_PLAYER_GROUNDED_PLUNGE_POWER = 2.7D;
    private static final double ADVANCED_PLAYER_AIRBORNE_PLUNGE_POWER = 2.0D;
    private static final double TONY_TARGET_GROUNDED_PLUNGE_POWER = 2.0D;
    private static final double TONY_TARGET_AIRBORNE_PLUNGE_POWER = 1.5D;
    private static final double ADVANCED_TARGET_GROUNDED_PLUNGE_POWER = 1.2D;
    private static final double ADVANCED_TARGET_AIRBORNE_PLUNGE_POWER = 0.9D;
    private static final double HOOKED_TARGET_FACE_STOP_DISTANCE = 0.9D;
    private static final double TONY_TARGET_DISTANCE_POWER_SCALE = 0.35D;
    private static final double ADVANCED_TARGET_DISTANCE_POWER_SCALE = 0.22D;
    private static final double HOOKED_TARGET_SEARCH_RADIUS = 0.65D;
    private static final double TONY_RETURN_SPEED = 1.35D;
    private static final double TONY_RETURN_ARRIVE_DISTANCE = 0.65D;
    private static final double TONY_STICKY_TARGET_PUSH_DISTANCE = 2.0D;
    private static final double TONY_ENTITY_STICK_CHANCE_MIN = 0.30D;
    private static final double TONY_ENTITY_STICK_CHANCE_MAX = 0.50D;
    private static final double TONY_DETACHED_HOOK_GRAVITY = 0.03D;
    private static final double ITEM_ENTITY_STOP_SEARCH_INFLATION = 0.6D;
    private static final double ITEM_ENTITY_STOP_BOX_INFLATION = 0.35D;
    private static final double NPC_COMBAT_HOOK_CAST_SPEED = 1.65D;
    private static final double NPC_COMBAT_HOOK_RETURN_SPEED = 1.85D;
    private static final double NPC_COMBAT_HOOK_ARRIVE_DISTANCE = 0.55D;
    private static final int NPC_COMBAT_HOOK_MAX_LIFE = 80;
    private static final int NPC_COMBAT_HOOK_MAX_RETURN_LIFE = 140;
    private static final int GRAPPLE_COOLDOWN_TICKS = 20;

    private FishingRodGrappleUtil() {
    }

    public static void inventoryTick(ItemStack stack, Level level, Entity entity) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        if (player.getMainHandItem() != stack && player.getOffhandItem() != stack) {
            return;
        }

        FishingHook hook = player.fishing;
        if (hook != null && hook.isAlive() && hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)) {
            latchHookIfReady(hook);
        }
    }

    public static InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        FishingHook hook = player.fishing;
        boolean disablePlunge = player.isShiftKeyDown();

        if (hook != null) {
            if (item instanceof TonyTheFishingRod || item instanceof AdvancedFishingRod) {
                if (hook.getPersistentData().getBoolean(KEY_RETURNING)) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                    if (!level.isClientSide) {
                        if (!handleTonyReturningStickyLivingTargetOnPull(item, player, hook, disablePlunge)) {
                            recastHookFromReturn(item, level, player, stack, hook, disablePlunge);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(item));
                    player.gameEvent(GameEvent.ITEM_INTERACT_START);
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
                }

                if (!level.isClientSide) {
                    int damage;
                    if (item instanceof TonyTheFishingRod) {
                        if (disablePlunge) {
                            if (!handleTonySneakItemTargetOnPull(player, hook)) {
                                releaseTonyPlungePayloads(hook, player);
                            }
                        } else if (!handleTonyStickyLivingTargetOnPull(item, player, hook)
                                && !handleTonyHookedTargetOnPull(item, player, hook)) {
                            tryPlunge(item, player, hook);
                        }

                        damage = getTonyReturnDamage(hook);
                    } else {
                        if (!disablePlunge && !tryPlungeHookedTarget(item, player, hook, true)) {
                            tryPlunge(item, player, hook);
                        }

                        damage = getReturnDamage(hook);
                    }

                    stack.hurtAndBreak(damage, player, brokenPlayer -> brokenPlayer.broadcastBreakEvent(hand));
                }

                startHookReturn(hook);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }

            if (!level.isClientSide) {
                if (!disablePlunge && !tryPlungeHookedTarget(item, player, hook, true)) {
                    tryPlunge(item, player, hook);
                }

                int damage = hook.retrieve(stack);
                stack.hurtAndBreak(damage, player, brokenPlayer -> brokenPlayer.broadcastBreakEvent(hand));
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                int lureSpeed = EnchantmentHelper.getFishingSpeedBonus(stack);
                int luck = EnchantmentHelper.getFishingLuckBonus(stack);
                FishingHook grappleHook = new FishingHook(player, level, luck, lureSpeed);
                grappleHook.getPersistentData().putBoolean(KEY_GRAPPLE_HOOK, true);
                level.addFreshEntity(grappleHook);
            }

            player.awardStat(Stats.ITEM_USED.get(item));
            player.gameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public static boolean tickTonyReturningHook(FishingHook hook) {
        if (!hook.getPersistentData().getBoolean(KEY_RETURNING) && getStickyTarget(hook) != null) {
            if (!hasValidLivingOwner(hook)) {
                clearTonyPayload(hook);
                hook.discard();
                return true;
            }

            followStickyTargetWithHook(hook);
            return true;
        }

        if (hasTonyStickyPayload(hook) && shouldLetStickyHookFlyWithoutRod(hook)) {
            tickDetachedStickyHook(hook);
            return true;
        }

        if (!hook.getPersistentData().getBoolean(KEY_RETURNING)) {
            return false;
        }

        Entity ownerEntity = hook.getOwner();
        if (!(ownerEntity instanceof LivingEntity owner) || !owner.isAlive() || owner.isRemoved()) {
            releaseTonyPayloadWithoutOwner(hook);
            hook.discard();
            return true;
        }

        if (!isHoldingGrappleRod(owner) && !hasTonyStickyPayload(hook)) {
            hook.discard();
            return true;
        }

        Vec3 target = getTonyReturnTarget(owner);
        Vec3 current = hook.position();
        Vec3 toTarget = target.subtract(current);
        double distance = toTarget.length();
        if (distance <= TONY_RETURN_ARRIVE_DISTANCE) {
            hook.setDeltaMovement(Vec3.ZERO);
            hook.setPos(target.x, target.y, target.z);
            moveTonyPayloadWithHook(hook, Vec3.ZERO);

            if (hook.level().isClientSide) {
                return true;
            }

            if (hook.getPersistentData().getBoolean(KEY_COLLECT_RETURNING_ITEM)) {
                collectReturningItemPayload(hook, owner);
                hook.discard();
                return true;
            }

            if (!hasTonyStickyPayload(hook)) {
                hook.discard();
            } else if (getStickyTarget(hook) != null) {
                hook.getPersistentData().putBoolean(KEY_RETURNING, false);
                followStickyTargetWithHook(hook);
            }

            return true;
        }

        Vec3 step = toTarget.scale(Math.min(TONY_RETURN_SPEED, distance) / distance);
        hook.setNoGravity(true);
        hook.fallDistance = 0.0F;
        hook.setDeltaMovement(step);
        hook.setPos(current.x + step.x, current.y + step.y, current.z + step.z);
        rotateHookToward(hook, step);
        moveTonyPayloadWithHook(hook, step);
        hook.hasImpulse = true;
        return true;
    }

    public static void afterTonyHookVanillaTick(FishingHook hook) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)) {
            return;
        }

        ItemProjectile stickyProjectile = getStickyItemProjectile(hook);
        if (stickyProjectile != null && hook.getHookedIn() != null && hook.getHookedIn() != stickyProjectile) {
            setVanillaHookedEntity(hook, null);
        }

        resolveHerobrineEscapeHookOnHit(hook);

        if (!hook.getPersistentData().getBoolean(KEY_RETURNING) && !hasTonyStickyPayload(hook)) {
            stopHookAtHitItemEntity(hook);
        }

        if (hook.getPersistentData().getBoolean(KEY_RETURNING) || !hasTonyStickyPayload(hook)) {
            return;
        }

        moveTonyPayloadWithHook(hook, hook.getDeltaMovement());
    }

    public static boolean shouldIgnoreHookEntityHit(FishingHook hook, Entity target) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)) {
            return false;
        }

        ItemProjectile stickyProjectile = getStickyItemProjectile(hook);
        return stickyProjectile != null && stickyProjectile.isHookAttached();
    }

    public static boolean isHookControllingItemProjectile(FishingHook hook, ItemProjectile projectile) {
        return hook != null
                && projectile != null
                && hook.isAlive()
                && (hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)
                || hook.getPersistentData().getBoolean(KEY_NPC_COMBAT_HOOK))
                && hook.getPersistentData().getInt(KEY_STICKY_ITEM_PROJECTILE_ID) == projectile.getId();
    }

    @Nullable
    public static FishingHook spawnNpcCombatFishingHook(LivingEntity owner, Vec3 destination) {
        return spawnNpcCombatFishingHook(owner, destination, null);
    }

    @Nullable
    public static FishingHook spawnNpcCombatFishingHook(LivingEntity owner, Vec3 destination, @Nullable Entity trackedTarget) {
        if (owner.level().isClientSide || destination == null) {
            return null;
        }

        Vec3 start = getNpcCombatHookCastOrigin(owner);
        Vec3 toDestination = destination.subtract(start);
        if (toDestination.lengthSqr() < 1.0e-6D) {
            toDestination = owner.getLookAngle().scale(4.0D);
            destination = start.add(toDestination);
        }

        FishingHook hook = new FishingHook(EntityType.FISHING_BOBBER, owner.level());
        hook.setOwner(owner);
        hook.moveTo(start.x, start.y, start.z, owner.getYRot(), owner.getXRot());
        Vec3 velocity = toDestination.normalize().scale(NPC_COMBAT_HOOK_CAST_SPEED);
        hook.setDeltaMovement(velocity);
        rotateHookToward(hook, velocity);
        hook.getPersistentData().putBoolean(KEY_NPC_COMBAT_HOOK, true);
        hook.getPersistentData().putBoolean(KEY_NPC_HOOK_RETURNING, false);
        hook.getPersistentData().putInt(KEY_NPC_HOOK_LIFE, 0);
        hook.getPersistentData().putBoolean(KEY_NPC_HOOK_RESOLVED, false);
        hook.getPersistentData().putBoolean(KEY_NPC_HOOK_TIMED_OUT, false);
        hook.getPersistentData().putDouble(KEY_NPC_HOOK_TARGET_X, destination.x);
        hook.getPersistentData().putDouble(KEY_NPC_HOOK_TARGET_Y, destination.y);
        hook.getPersistentData().putDouble(KEY_NPC_HOOK_TARGET_Z, destination.z);
        if (trackedTarget != null && trackedTarget.isAlive() && trackedTarget != owner) {
            hook.getPersistentData().putInt(KEY_NPC_HOOK_TARGET_ENTITY_ID, trackedTarget.getId());
        }
        owner.level().addFreshEntity(hook);
        return hook;
    }

    public static void attachNpcCombatFishingHookPayload(@Nullable FishingHook hook, LivingEntity owner, ItemStack stack) {
        if (hook == null || !hook.isAlive() || stack.isEmpty()
                || !hook.getPersistentData().getBoolean(KEY_NPC_COMBAT_HOOK)) {
            return;
        }

        ItemProjectile projectile = ItemProjectile.createHookPayload(hook.level(), hook, stack.copy(), hook.position());
        projectile.setDiscardWhenHookLost(true);
        hook.level().addFreshEntity(projectile);

        hook.getPersistentData().putInt(KEY_STICKY_ITEM_PROJECTILE_ID, projectile.getId());
        hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);
        hook.getPersistentData().remove(KEY_STICKY_TARGET_ID);
        projectile.moveWithHook(hook.position(), owner);
    }

    public static boolean isNpcCombatFishingHookResolved(@Nullable FishingHook hook) {
        return hook == null
                || !hook.isAlive()
                || hook.getPersistentData().getBoolean(KEY_NPC_HOOK_RESOLVED)
                || hook.getPersistentData().getBoolean(KEY_NPC_HOOK_TIMED_OUT);
    }

    public static void forceNpcCombatFishingHookReturn(@Nullable FishingHook hook) {
        if (hook == null || !hook.isAlive() || !hook.getPersistentData().getBoolean(KEY_NPC_COMBAT_HOOK)) {
            return;
        }

        markNpcCombatHookResolved(hook, true);
    }

    public static boolean tickNpcCombatFishingHook(FishingHook hook) {
        Entity ownerEntity = hook.getOwner();
        boolean serverHook = hook.getPersistentData().getBoolean(KEY_NPC_COMBAT_HOOK);
        if (!serverHook && !isNpcCombatFishingHookOwner(ownerEntity)) {
            return false;
        }
        if (!(ownerEntity instanceof LivingEntity owner) || !owner.isAlive() || owner.isRemoved()) {
            discardNpcCombatHookPayload(hook);
            hook.discard();
            return true;
        }

        if (!serverHook) {
            tickClientNpcCombatFishingHook(hook);
            return true;
        }

        int life = hook.getPersistentData().getInt(KEY_NPC_HOOK_LIFE) + 1;
        hook.getPersistentData().putInt(KEY_NPC_HOOK_LIFE, life);

        boolean returning = hook.getPersistentData().getBoolean(KEY_NPC_HOOK_RETURNING);
        if (!returning && life >= NPC_COMBAT_HOOK_MAX_LIFE) {
            markNpcCombatHookResolved(hook, true);
            returning = true;
        } else if (returning && life > NPC_COMBAT_HOOK_MAX_RETURN_LIFE) {
            discardNpcCombatHookPayload(hook);
            hook.discard();
            return true;
        }

        Vec3 destination = returning ? getNpcCombatHookCastOrigin(owner) : getNpcCombatHookTarget(hook);
        Vec3 current = hook.position();
        Vec3 toDestination = destination.subtract(current);
        double distance = toDestination.length();

        if (distance <= NPC_COMBAT_HOOK_ARRIVE_DISTANCE) {
            hook.setDeltaMovement(Vec3.ZERO);
            hook.setNoGravity(true);
            hook.setPos(destination.x, destination.y, destination.z);
            moveTonyPayloadWithHook(hook, Vec3.ZERO);
            if (returning) {
                discardNpcCombatHookPayload(hook);
                hook.discard();
            } else {
                markNpcCombatHookResolved(hook, false);
            }
            return true;
        }

        if (distance <= 1.0e-6D) {
            return true;
        }

        double speed = returning ? NPC_COMBAT_HOOK_RETURN_SPEED : NPC_COMBAT_HOOK_CAST_SPEED;
        Vec3 step = toDestination.scale(Math.min(speed, distance) / distance);
        hook.setNoGravity(true);
        hook.fallDistance = 0.0F;
        hook.setDeltaMovement(step);
        hook.setPos(current.x + step.x, current.y + step.y, current.z + step.z);
        rotateHookToward(hook, step);
        moveTonyPayloadWithHook(hook, step);
        hook.hasImpulse = true;
        return true;
    }

    private static void discardNpcCombatHookPayload(FishingHook hook) {
        ItemProjectile projectile = getStickyItemProjectile(hook);
        if (projectile != null) {
            projectile.discard();
        }

        hook.getPersistentData().remove(KEY_STICKY_ITEM_PROJECTILE_ID);
        hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);
        setVanillaHookedEntity(hook, null);
    }

    private static void markNpcCombatHookResolved(FishingHook hook, boolean timedOut) {
        hook.getPersistentData().putBoolean(KEY_NPC_HOOK_RESOLVED, true);
        hook.getPersistentData().putBoolean(KEY_NPC_HOOK_TIMED_OUT, timedOut);
        hook.getPersistentData().putBoolean(KEY_NPC_HOOK_RETURNING, true);
    }

    public static boolean isNpcCombatFishingHookOwner(Entity entity) {
        return entity instanceof LivingEntity owner
                && !(owner instanceof Player)
                && isHoldingGrappleRod(owner);
    }

    public static void onGrappleHookRemoved(FishingHook hook) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)
                || hook.getPersistentData().getBoolean(KEY_SUPPRESS_STICKY_ITEM_RELEASE)) {
            return;
        }

        ItemProjectile projectile = getStickyItemProjectile(hook);
        if (projectile != null && projectile.isHookAttached()) {
            projectile.dropAsItem(hook.getDeltaMovement());
        }

        clearTonyPayload(hook);
    }

    public static float getCastProperty(ItemStack stack, LivingEntity entity) {
        if (entity == null) {
            return 0.0F;
        }

        boolean mainHand = entity.getMainHandItem() == stack;
        boolean offHand = entity.getOffhandItem() == stack;
        if (entity.getMainHandItem().getItem() instanceof FishingRodItem) {
            offHand = false;
        }

        return (mainHand || offHand) && entity instanceof Player player && player.fishing != null ? 1.0F : 0.0F;
    }

    public static boolean shouldOffhandFishingRodTakeRightClick(Player player) {
        ItemStack offhand = player.getOffhandItem();
        return (offhand.getItem() instanceof FishingRodItem || offhand.canPerformAction(ToolActions.FISHING_ROD_CAST))
                && !player.getCooldowns().isOnCooldown(offhand.getItem());
    }

    public static boolean shouldForceOffhandFishingRodRender(LivingEntity entity) {
        Item offhandItem = entity.getOffhandItem().getItem();
        return offhandItem instanceof TonyTheFishingRod || offhandItem instanceof AdvancedFishingRod;
    }

    private static void startHookReturn(FishingHook hook) {
        hook.getPersistentData().putBoolean(KEY_RETURNING, true);
        hook.getPersistentData().putBoolean(KEY_LATCHED, false);
        hook.setNoGravity(true);
        hook.setDeltaMovement(Vec3.ZERO);
    }

    private static void recastHookFromReturn(Item item, Level level, Player player, ItemStack stack, FishingHook returningHook, boolean disablePlunge) {
        Vec3 start = returningHook.position();
        boolean tonyRod = item instanceof TonyTheFishingRod;
        Entity stickyTarget = tonyRod ? getStickyTarget(returningHook) : null;
        ItemProjectile stickyProjectile = tonyRod ? getStickyItemProjectile(returningHook) : null;
        boolean collectReturningItem = tonyRod && returningHook.getPersistentData().getBoolean(KEY_COLLECT_RETURNING_ITEM);
        boolean collectItemNow = tonyRod && disablePlunge && stickyProjectile != null;
        returningHook.getPersistentData().putBoolean(KEY_SUPPRESS_STICKY_ITEM_RELEASE, true);
        returningHook.discard();

        if (collectItemNow) {
            stickyProjectile.giveToOwnerOrDrop(player);
            stickyProjectile = null;
            collectReturningItem = false;
        }

        int lureSpeed = EnchantmentHelper.getFishingSpeedBonus(stack);
        int luck = EnchantmentHelper.getFishingLuckBonus(stack);
        FishingHook grappleHook = new FishingHook(player, level, luck, lureSpeed);
        Vec3 castVelocity = grappleHook.getDeltaMovement();
        grappleHook.moveTo(start.x, start.y, start.z, player.getYRot(), player.getXRot());
        grappleHook.setDeltaMovement(castVelocity);
        rotateHookToward(grappleHook, castVelocity);
        grappleHook.getPersistentData().putBoolean(KEY_GRAPPLE_HOOK, true);

        if (tonyRod && !disablePlunge && stickyProjectile != null && stickyProjectile.isAlive()) {
            grappleHook.getPersistentData().putInt(KEY_STICKY_ITEM_PROJECTILE_ID, stickyProjectile.getId());
            grappleHook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, collectReturningItem);
            stickyProjectile.moveWithHook(start, player);
        }

        if (tonyRod && !disablePlunge && stickyTarget != null && stickyTarget.isAlive()) {
            if (isStickyTargetCloseToOwner(player, stickyTarget)) {
                plungeTargetAlongHookCast(item, player, stickyTarget, castVelocity);
            } else {
                plungeTargetTowardOwner(item, player, stickyTarget);
            }

            if (shouldKeepStickyEntityAttachment(level)) {
                grappleHook.getPersistentData().putInt(KEY_STICKY_TARGET_ID, stickyTarget.getId());
                setVanillaHookedEntity(grappleHook, stickyTarget);
            }
        }

        level.addFreshEntity(grappleHook);
    }

    private static int getTonyReturnDamage(FishingHook hook) {
        if (hook.getPersistentData().contains(KEY_PENDING_RETURN_DAMAGE)) {
            int damage = hook.getPersistentData().getInt(KEY_PENDING_RETURN_DAMAGE);
            hook.getPersistentData().remove(KEY_PENDING_RETURN_DAMAGE);
            return damage;
        }

        return getReturnDamage(hook);
    }

    private static int getReturnDamage(FishingHook hook) {
        if (hook.getPersistentData().getInt(KEY_STICKY_ITEM_PROJECTILE_ID) > 0) {
            return 3;
        }

        Entity hookedTarget = hook.getHookedIn();
        if (hookedTarget instanceof ItemEntity || hookedTarget instanceof ItemProjectile) {
            return 3;
        }

        if (hookedTarget != null) {
            return 5;
        }

        return hook.onGround() ? 2 : 0;
    }

    private static Vec3 getTonyReturnTarget(LivingEntity owner) {
        return new Vec3(owner.getX(), owner.getEyeY() - 0.1D, owner.getZ());
    }

    private static boolean isHoldingTonyRod(LivingEntity owner) {
        return owner.getMainHandItem().getItem() instanceof TonyTheFishingRod
                || owner.getOffhandItem().getItem() instanceof TonyTheFishingRod;
    }

    private static boolean isHoldingGrappleRod(LivingEntity owner) {
        return owner.getMainHandItem().getItem() instanceof TonyTheFishingRod
                || owner.getOffhandItem().getItem() instanceof TonyTheFishingRod
                || owner.getMainHandItem().getItem() instanceof AdvancedFishingRod
                || owner.getOffhandItem().getItem() instanceof AdvancedFishingRod;
    }

    private static Vec3 getNpcCombatHookCastOrigin(LivingEntity owner) {
        return new Vec3(owner.getX(), owner.getEyeY() - 0.1D, owner.getZ());
    }

    private static Vec3 getNpcCombatHookTarget(FishingHook hook) {
        Entity trackedTarget = getNpcCombatHookTrackedTarget(hook);
        if (trackedTarget != null) {
            return trackedTarget.position().add(0.0D, trackedTarget.getBbHeight() * 0.55D, 0.0D);
        }

        return new Vec3(
                hook.getPersistentData().getDouble(KEY_NPC_HOOK_TARGET_X),
                hook.getPersistentData().getDouble(KEY_NPC_HOOK_TARGET_Y),
                hook.getPersistentData().getDouble(KEY_NPC_HOOK_TARGET_Z)
        );
    }

    @Nullable
    private static Entity getNpcCombatHookTrackedTarget(FishingHook hook) {
        int targetId = hook.getPersistentData().getInt(KEY_NPC_HOOK_TARGET_ENTITY_ID);
        if (targetId <= 0) {
            return null;
        }

        Entity target = hook.level().getEntity(targetId);
        if (target == null || !target.isAlive() || target.isRemoved() || target == hook.getOwner()) {
            hook.getPersistentData().remove(KEY_NPC_HOOK_TARGET_ENTITY_ID);
            return null;
        }

        return target;
    }

    private static void tickClientNpcCombatFishingHook(FishingHook hook) {
        Vec3 current = hook.position();
        Vec3 movement = hook.getDeltaMovement();
        hook.setPos(current.x + movement.x, current.y + movement.y, current.z + movement.z);
        if (!hook.isNoGravity()) {
            hook.setDeltaMovement(movement.x * 0.98D, movement.y - TONY_DETACHED_HOOK_GRAVITY, movement.z * 0.98D);
        }
        rotateHookToward(hook, movement);
        hook.hasImpulse = true;
    }

    private static void rotateHookToward(FishingHook hook, Vec3 movement) {
        if (movement.lengthSqr() < 1.0e-6D) {
            return;
        }

        hook.setYRot((float)(Mth.atan2(movement.x, movement.z) * (double)(180F / (float)Math.PI)));
        hook.setXRot((float)(Mth.atan2(movement.y, movement.horizontalDistance()) * (double)(180F / (float)Math.PI)));
    }

    private static void stopHookAtHitItemEntity(FishingHook hook) {
        Entity hookedTarget = hook.getHookedIn();
        if (hookedTarget instanceof ItemEntity itemEntity && itemEntity.isAlive() && !itemEntity.isRemoved()) {
            stopHookAtItemEntity(hook, itemEntity, getItemHookPosition(itemEntity));
            return;
        }

        if (hookedTarget != null) {
            return;
        }

        if (!(hook.getOwner() instanceof LivingEntity owner)) {
            return;
        }

        Vec3 from = new Vec3(hook.xo, hook.yo, hook.zo);
        Vec3 to = hook.position();
        if (from.distanceToSqr(to) < 1.0e-7D) {
            return;
        }

        AABB searchBox = new AABB(from, to).inflate(ITEM_ENTITY_STOP_SEARCH_INFLATION);
        ItemEntity closestItem = null;
        Vec3 closestHit = null;
        double closestDistance = Double.MAX_VALUE;

        for (ItemEntity itemEntity : hook.level().getEntitiesOfClass(
                ItemEntity.class,
                searchBox,
                target -> isPullableHookTarget(owner, target)
        )) {
            AABB itemBox = itemEntity.getBoundingBox().inflate(ITEM_ENTITY_STOP_BOX_INFLATION);
            Vec3 hitPosition;
            if (itemBox.contains(from)) {
                hitPosition = from;
            } else {
                Optional<Vec3> hit = itemBox.clip(from, to);
                if (hit.isEmpty()) {
                    continue;
                }

                hitPosition = hit.get();
            }

            double distance = from.distanceToSqr(hitPosition);
            if (distance < closestDistance) {
                closestItem = itemEntity;
                closestHit = hitPosition;
                closestDistance = distance;
            }
        }

        if (closestItem != null) {
            stopHookAtItemEntity(hook, closestItem, closestHit);
        }
    }

    private static Vec3 getItemHookPosition(ItemEntity itemEntity) {
        return itemEntity.position().add(0.0D, itemEntity.getBbHeight() * 0.5D, 0.0D);
    }

    private static void stopHookAtItemEntity(FishingHook hook, ItemEntity itemEntity, Vec3 position) {
        hook.setNoGravity(true);
        hook.fallDistance = 0.0F;
        hook.setDeltaMovement(Vec3.ZERO);
        hook.setPos(position.x, position.y, position.z);
        setVanillaHookedEntity(hook, itemEntity);
        hook.hasImpulse = true;
    }

    private static boolean handleTonyReturningStickyLivingTargetOnPull(Item item, LivingEntity owner, FishingHook hook, boolean disablePlunge) {
        if (disablePlunge || !(item instanceof TonyTheFishingRod)) {
            return false;
        }

        Entity target = getStickyTarget(hook);
        if (!(target instanceof LivingEntity)) {
            return false;
        }

        if (isStickyTargetCloseToOwner(owner, target)) {
            return false;
        }

        pullStickyLivingTargetTowardOwner(item, owner, hook, target);
        return true;
    }

    private static boolean handleTonyStickyLivingTargetOnPull(Item item, LivingEntity owner, FishingHook hook) {
        if (!(item instanceof TonyTheFishingRod)) {
            return false;
        }

        Entity target = getStickyTarget(hook);
        if (!(target instanceof LivingEntity)) {
            return false;
        }

        if (isStickyTargetCloseToOwner(owner, target)) {
            plungeTargetAwayFromOwner(item, owner, target);
        } else {
            plungeTargetTowardOwner(item, owner, target);
        }

        rollStickyLivingTargetAttachment(hook, target);
        return true;
    }

    private static void pullStickyLivingTargetTowardOwner(Item item, LivingEntity owner, FishingHook hook, Entity target) {
        plungeTargetTowardOwner(item, owner, target);
        rollStickyLivingTargetAttachment(hook, target);
    }

    private static void rollStickyLivingTargetAttachment(FishingHook hook, Entity target) {
        hook.getPersistentData().putBoolean(KEY_TARGET_PLUNGED, true);
        hook.getPersistentData().putInt(KEY_PENDING_RETURN_DAMAGE, 5);
        hook.getPersistentData().remove(KEY_STICKY_ITEM_PROJECTILE_ID);
        hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);

        if (shouldKeepStickyEntityAttachment(hook.level())) {
            hook.getPersistentData().putInt(KEY_STICKY_TARGET_ID, target.getId());
            setVanillaHookedEntity(hook, target);
        } else {
            hook.getPersistentData().remove(KEY_STICKY_TARGET_ID);
            setVanillaHookedEntity(hook, null);
        }
    }

    private static boolean isStickyTargetCloseToOwner(LivingEntity owner, Entity target) {
        return owner.distanceToSqr(target) <= TONY_STICKY_TARGET_PUSH_DISTANCE * TONY_STICKY_TARGET_PUSH_DISTANCE;
    }

    private static boolean handleTonyHookedTargetOnPull(Item item, LivingEntity owner, FishingHook hook) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)
                || hook.getPersistentData().getBoolean(KEY_TARGET_PLUNGED)) {
            return false;
        }

        ItemProjectile stickyProjectile = getStickyItemProjectile(hook);
        if (stickyProjectile != null) {
            hook.getPersistentData().putBoolean(KEY_TARGET_PLUNGED, true);
            hook.getPersistentData().putInt(KEY_PENDING_RETURN_DAMAGE, 3);
            attachItemProjectileForReturn(hook, stickyProjectile, false);
            return true;
        }

        Entity target = getHookedTarget(owner, hook);
        if (target == null) {
            clearTonyPayload(hook);
            return false;
        }

        HerobrineEscapeHookResult escapeHookResult = tryCancelHerobrineEscapeWithFishingHook(hook, target);
        if (escapeHookResult == HerobrineEscapeHookResult.FAILED) {
            hook.getPersistentData().putBoolean(KEY_TARGET_PLUNGED, true);
            hook.getPersistentData().remove(KEY_STICKY_ITEM_PROJECTILE_ID);
            hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);
            setVanillaHookedEntity(hook, null);
            return true;
        }

        hook.getPersistentData().putBoolean(KEY_TARGET_PLUNGED, true);
        hook.getPersistentData().putInt(
                KEY_PENDING_RETURN_DAMAGE,
                target instanceof ItemEntity || target instanceof ItemProjectile ? 3 : 5
        );

        if (target instanceof ItemEntity itemEntity) {
            ItemProjectile projectile = convertItemEntityToProjectile(owner, hook, itemEntity);
            attachItemProjectileForReturn(hook, projectile, false);
            return true;
        }

        if (target instanceof ItemProjectile projectile) {
            attachItemProjectileForReturn(hook, projectile, false);
            return true;
        }

        plungeTargetTowardOwner(item, owner, target);
        if (shouldKeepStickyEntityAttachment(hook.level())) {
            hook.getPersistentData().putInt(KEY_STICKY_TARGET_ID, target.getId());
            setVanillaHookedEntity(hook, target);
        } else {
            hook.getPersistentData().remove(KEY_STICKY_TARGET_ID);
            setVanillaHookedEntity(hook, null);
        }

        hook.getPersistentData().remove(KEY_STICKY_ITEM_PROJECTILE_ID);
        hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);
        return true;
    }

    private static boolean handleTonySneakItemTargetOnPull(LivingEntity owner, FishingHook hook) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)) {
            return false;
        }

        Entity target = getHookedTarget(owner, hook);
        if (target instanceof ItemEntity itemEntity) {
            ItemProjectile projectile = convertItemEntityToProjectile(owner, hook, itemEntity);
            attachItemProjectileForReturn(hook, projectile, false);
            return true;
        }

        if (target instanceof ItemProjectile projectile) {
            attachItemProjectileForReturn(hook, projectile, false);
            return true;
        }

        return false;
    }

    private static ItemProjectile convertItemEntityToProjectile(LivingEntity owner, FishingHook hook, ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem().copy();
        ItemProjectile projectile = ItemProjectile.createHookPayload(hook.level(), owner, itemStack, hook.position());
        hook.level().addFreshEntity(projectile);
        itemEntity.discard();
        return projectile;
    }

    private static void attachItemProjectileForReturn(FishingHook hook, ItemProjectile projectile, boolean keepStuck) {
        hook.getPersistentData().putInt(KEY_STICKY_ITEM_PROJECTILE_ID, projectile.getId());
        hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, !keepStuck);
        hook.getPersistentData().remove(KEY_STICKY_TARGET_ID);
        setVanillaHookedEntity(hook, keepStuck ? projectile : null);
        projectile.moveWithHook(hook.position(), hook.getOwner() != null ? hook.getOwner() : hook);
    }

    private static boolean shouldKeepStickyEntityAttachment(Level level) {
        return rollStickyAttachment(level, TONY_ENTITY_STICK_CHANCE_MIN, TONY_ENTITY_STICK_CHANCE_MAX);
    }

    private static boolean rollStickyAttachment(Level level, double minChance, double maxChance) {
        double chance = minChance + level.getRandom().nextDouble() * (maxChance - minChance);
        return level.getRandom().nextDouble() < chance;
    }

    private static boolean hasTonyStickyPayload(FishingHook hook) {
        return hook.getPersistentData().getInt(KEY_STICKY_TARGET_ID) > 0
                || hook.getPersistentData().getInt(KEY_STICKY_ITEM_PROJECTILE_ID) > 0
                || getClientSyncedStickyTarget(hook) != null
                || getClientSyncedStickyItemProjectile(hook) != null;
    }

    private static Entity getStickyTarget(FishingHook hook) {
        int targetId = hook.getPersistentData().getInt(KEY_STICKY_TARGET_ID);
        if (targetId <= 0) {
            return getClientSyncedStickyTarget(hook);
        }

        Entity target = hook.level().getEntity(targetId);
        if (!isValidStickyTarget(hook, target)) {
            hook.getPersistentData().remove(KEY_STICKY_TARGET_ID);
            return null;
        }

        return target;
    }

    private static ItemProjectile getStickyItemProjectile(FishingHook hook) {
        int projectileId = hook.getPersistentData().getInt(KEY_STICKY_ITEM_PROJECTILE_ID);
        if (projectileId <= 0) {
            return getClientSyncedStickyItemProjectile(hook);
        }

        Entity entity = hook.level().getEntity(projectileId);
        if (!(entity instanceof ItemProjectile projectile) || !projectile.isAlive()) {
            hook.getPersistentData().remove(KEY_STICKY_ITEM_PROJECTILE_ID);
            hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);
            return null;
        }

        return projectile;
    }

    private static Entity getClientSyncedStickyTarget(FishingHook hook) {
        if (!hook.level().isClientSide) {
            return null;
        }

        Entity target = hook.getHookedIn();
        if (target instanceof ItemEntity || target instanceof ItemProjectile) {
            return null;
        }

        return isValidStickyTarget(hook, target) ? target : null;
    }

    private static ItemProjectile getClientSyncedStickyItemProjectile(FishingHook hook) {
        if (!hook.level().isClientSide) {
            return null;
        }

        Entity target = hook.getHookedIn();
        return target instanceof ItemProjectile projectile && projectile.isAlive() && !projectile.isRemoved()
                ? projectile
                : null;
    }

    private static boolean isValidStickyTarget(FishingHook hook, Entity target) {
        if (target == null || !target.isAlive() || target.isRemoved()) {
            return false;
        }

        Entity owner = hook.getOwner();
        return owner == null || target != owner && !target.getUUID().equals(owner.getUUID());
    }

    private static void setVanillaHookedEntity(FishingHook hook, Entity target) {
        ((FishingHookAccessor) hook).annoyingVillagers$invokeSetHookedEntity(target);
    }

    private static void moveTonyPayloadWithHook(FishingHook hook, Vec3 hookMotion) {
        Entity owner = hook.getOwner() != null ? hook.getOwner() : hook;
        Entity stickyTarget = getStickyTarget(hook);
        if (stickyTarget != null) {
            followStickyTargetWithHook(hook, stickyTarget);
        }

        ItemProjectile projectile = getStickyItemProjectile(hook);
        if (projectile != null) {
            projectile.moveWithHook(hook.position(), owner);
        }
    }

    private static void followStickyTargetWithHook(FishingHook hook) {
        Entity stickyTarget = getStickyTarget(hook);
        if (stickyTarget != null) {
            followStickyTargetWithHook(hook, stickyTarget);
        }
    }

    private static void followStickyTargetWithHook(FishingHook hook, Entity target) {
        Vec3 oldPos = hook.position();
        Vec3 targetPos = target.position().add(0.0D, target.getBbHeight() * 0.65D, 0.0D);
        Vec3 motion = targetPos.subtract(oldPos);

        hook.setNoGravity(true);
        hook.fallDistance = 0.0F;
        hook.setDeltaMovement(Vec3.ZERO);
        hook.setPos(targetPos.x, targetPos.y, targetPos.z);
        rotateHookToward(hook, motion);
    }

    private static boolean shouldLetStickyHookFlyWithoutRod(FishingHook hook) {
        if (hook.getPersistentData().getBoolean(KEY_COLLECT_RETURNING_ITEM)) {
            return false;
        }

        Entity owner = hook.getOwner();
        return owner instanceof LivingEntity livingOwner
                && livingOwner.isAlive()
                && !isHoldingTonyRod(livingOwner);
    }

    private static boolean hasValidLivingOwner(FishingHook hook) {
        return hook.getOwner() instanceof LivingEntity owner
                && owner.isAlive()
                && !owner.isRemoved();
    }

    private static void tickDetachedStickyHook(FishingHook hook) {
        Vec3 motion = hook.getDeltaMovement();
        if (motion.lengthSqr() < 1.0e-7D) {
            if (hook.onGround()) {
                moveTonyPayloadWithHook(hook, Vec3.ZERO);
                return;
            }

            motion = hook.getLookAngle().scale(0.2D);
        }

        hook.setNoGravity(false);
        hook.move(MoverType.SELF, motion);
        rotateHookToward(hook, motion);
        moveTonyPayloadWithHook(hook, motion);

        Vec3 nextMotion = motion.scale(0.92D).add(0.0D, -TONY_DETACHED_HOOK_GRAVITY, 0.0D);
        if (hook.onGround()) {
            nextMotion = Vec3.ZERO;
        }

        hook.setDeltaMovement(nextMotion);
    }

    private static void releaseTonyPayloadWithoutOwner(FishingHook hook) {
        ItemProjectile projectile = getStickyItemProjectile(hook);
        if (projectile != null && hook.getPersistentData().getBoolean(KEY_COLLECT_RETURNING_ITEM)) {
            projectile.dropAsItem(hook.getDeltaMovement());
        }

        clearTonyPayload(hook);
    }

    private static void collectReturningItemPayload(FishingHook hook, Entity receiver) {
        ItemProjectile projectile = getStickyItemProjectile(hook);
        if (projectile != null) {
            projectile.giveToOwnerOrDrop(receiver);
        }

        hook.getPersistentData().remove(KEY_STICKY_ITEM_PROJECTILE_ID);
        hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);
        setVanillaHookedEntity(hook, null);
    }

    private static void clearTonyPayload(FishingHook hook) {
        hook.getPersistentData().remove(KEY_STICKY_TARGET_ID);
        hook.getPersistentData().remove(KEY_STICKY_ITEM_PROJECTILE_ID);
        hook.getPersistentData().putBoolean(KEY_COLLECT_RETURNING_ITEM, false);
        hook.getPersistentData().remove(KEY_PENDING_RETURN_DAMAGE);
        hook.getPersistentData().remove(KEY_HEROBRINE_ESCAPE_HOOK_ATTEMPTED_TARGET_ID);
        setVanillaHookedEntity(hook, null);
    }

    private static void releaseTonyPlungePayloads(FishingHook hook, Entity receiver) {
        ItemProjectile projectile = getStickyItemProjectile(hook);
        if (projectile != null) {
            projectile.giveToOwnerOrDrop(receiver);
        }

        clearTonyPayload(hook);
    }

    private static void plungeTargetAlongHookCast(Item item, LivingEntity owner, Entity target, Vec3 castVelocity) {
        Vec3 direction = castVelocity.lengthSqr() > 1.0e-6D
                ? castVelocity.normalize()
                : owner.getLookAngle();
        boolean grounded = target.onGround();
        Vec3 velocity = target.getDeltaMovement();

        if (grounded) {
            velocity = velocity.add(0.0D, 0.35D, 0.0D);
        }

        velocity = velocity.add(direction.scale(getTargetPlungePower(item, grounded)));
        target.setDeltaMovement(velocity);
        target.hasImpulse = true;
        target.hurtMarked = true;
        target.fallDistance = 0.0F;
    }

    private static void plungeTargetAwayFromOwner(Item item, LivingEntity owner, Entity target) {
        Vec3 playerCenter = owner.position().add(0.0D, owner.getEyeHeight(), 0.0D);
        Vec3 targetCenter = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        Vec3 direction = targetCenter.subtract(playerCenter);
        if (direction.lengthSqr() < 1.0e-6D) {
            direction = owner.getLookAngle();
        }

        direction = direction.normalize();
        boolean grounded = target.onGround();
        double maxY = grounded ? 1.0D : 0.7D;
        direction = new Vec3(direction.x, Math.max(-maxY, Math.min(maxY, direction.y)), direction.z);

        Vec3 velocity = target.getDeltaMovement();
        if (grounded) {
            velocity = velocity.add(0.0D, 0.35D, 0.0D);
        }

        velocity = velocity.add(direction.scale(getTargetPlungePower(item, grounded)));
        target.setDeltaMovement(velocity);
        target.hasImpulse = true;
        target.hurtMarked = true;
        target.fallDistance = 0.0F;
    }

    private static void tryPlunge(Item item, Player player, FishingHook hook) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)) {
            return;
        }

        if (getHookedTarget(player, hook) != null) {
            return;
        }

        if (player.getCooldowns().isOnCooldown(item)) {
            return;
        }

        if (hook.level().getFluidState(hook.blockPosition()).is(FluidTags.WATER)) {
            return;
        }

        latchHookIfReady(hook);
        if (!hook.getPersistentData().getBoolean(KEY_LATCHED)) {
            return;
        }

        Vec3 anchor = new Vec3(
                hook.getPersistentData().getDouble(KEY_ANCHOR_X),
                hook.getPersistentData().getDouble(KEY_ANCHOR_Y),
                hook.getPersistentData().getDouble(KEY_ANCHOR_Z)
        );
        Vec3 eye = player.position().add(0.0D, player.getEyeHeight(), 0.0D);
        Vec3 direction = anchor.subtract(eye);
        if (direction.lengthSqr() < 1.0e-6D) {
            return;
        }

        direction = direction.normalize();
        boolean grounded = player.onGround();
        double maxY = grounded ? 1.0D : 0.7D;
        direction = new Vec3(direction.x, Math.max(-maxY, Math.min(maxY, direction.y)), direction.z);

        Vec3 velocity = player.getDeltaMovement();
        if (grounded) {
            velocity = velocity.add(0.0D, 0.42D, 0.0D);
        }

        player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, 1, false, false));
        velocity = velocity.add(direction.scale(getPlayerPlungePower(item, grounded)));

        player.setDeltaMovement(velocity);
        player.hurtMarked = true;
        player.fallDistance = 0.0F;
        player.getCooldowns().addCooldown(item, GRAPPLE_COOLDOWN_TICKS);
        hook.getPersistentData().putBoolean(KEY_LATCHED, false);
    }

    private static void latchHookIfReady(FishingHook hook) {
        if (hook.getPersistentData().getBoolean(KEY_LATCHED)) {
            return;
        }

        if (hook.level().getFluidState(hook.blockPosition()).is(FluidTags.WATER)) {
            return;
        }

        if (!hook.onGround() && hook.getDeltaMovement().lengthSqr() >= LATCH_STOPPED_SPEED_SQR) {
            return;
        }

        Vec3 anchor = hook.position();
        hook.getPersistentData().putBoolean(KEY_LATCHED, true);
        hook.getPersistentData().putDouble(KEY_ANCHOR_X, anchor.x);
        hook.getPersistentData().putDouble(KEY_ANCHOR_Y, anchor.y);
        hook.getPersistentData().putDouble(KEY_ANCHOR_Z, anchor.z);
    }

    private static boolean tryPlungeHookedTarget(Item item, Player player, FishingHook hook, boolean allowFallbackSearch) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)
                || hook.getPersistentData().getBoolean(KEY_TARGET_PLUNGED)) {
            return false;
        }

        Entity hookedTarget = hook.getHookedIn();
        Entity target = hookedTarget != null && isPullableHookTarget(player, hookedTarget)
                ? hookedTarget
                : allowFallbackSearch ? findHookedTargetNearHook(player, hook) : null;
        if (target == null) {
            return false;
        }

        HerobrineEscapeHookResult escapeHookResult = tryCancelHerobrineEscapeWithFishingHook(hook, target);
        if (escapeHookResult == HerobrineEscapeHookResult.FAILED) {
            hook.getPersistentData().putBoolean(KEY_TARGET_PLUNGED, true);
            setVanillaHookedEntity(hook, null);
            return true;
        }

        plungeTargetTowardOwner(item, player, target);
        hook.getPersistentData().putBoolean(KEY_TARGET_PLUNGED, true);
        return true;
    }

    private static HerobrineEscapeHookResult tryCancelHerobrineEscapeWithFishingHook(FishingHook hook, Entity target) {
        if (target instanceof HerobrineGregEntity greg && greg.canFishingHookCancelEscape()) {
            if (greg.tryFishingHookCancelEscape()) {
                setVanillaHookedEntity(hook, greg);
                return HerobrineEscapeHookResult.CANCELLED;
            }

            setVanillaHookedEntity(hook, null);
            return HerobrineEscapeHookResult.FAILED;
        }

        if (target instanceof TransporterHerobrineCloneEntity transporter && transporter.canFishingHookCancelEscape()) {
            if (transporter.tryFishingHookCancelEscape()) {
                setVanillaHookedEntity(hook, transporter);
                return HerobrineEscapeHookResult.CANCELLED;
            }

            setVanillaHookedEntity(hook, null);
            return HerobrineEscapeHookResult.FAILED;
        }

        return HerobrineEscapeHookResult.NONE;
    }

    private static void resolveHerobrineEscapeHookOnHit(FishingHook hook) {
        if (!hook.getPersistentData().getBoolean(KEY_GRAPPLE_HOOK)) {
            return;
        }

        Entity target = hook.getHookedIn();
        if (target == null) {
            return;
        }

        int targetId = target.getId();
        if (hook.getPersistentData().getInt(KEY_HEROBRINE_ESCAPE_HOOK_ATTEMPTED_TARGET_ID) == targetId) {
            return;
        }

        HerobrineEscapeHookResult result = tryCancelHerobrineEscapeWithFishingHook(hook, target);
        if (result == HerobrineEscapeHookResult.NONE) {
            return;
        }

        hook.getPersistentData().putInt(KEY_HEROBRINE_ESCAPE_HOOK_ATTEMPTED_TARGET_ID, targetId);
        if (result == HerobrineEscapeHookResult.FAILED) {
            hook.getPersistentData().putBoolean(KEY_TARGET_PLUNGED, true);
        }
    }

    private static Entity getHookedTarget(LivingEntity owner, FishingHook hook) {
        Entity hookedTarget = hook.getHookedIn();
        return hookedTarget != null && isPullableHookTarget(owner, hookedTarget)
                ? hookedTarget
                : findHookedTargetNearHook(owner, hook);
    }

    private static Entity findHookedTargetNearHook(LivingEntity owner, FishingHook hook) {
        AABB searchBox = hook.getBoundingBox().inflate(HOOKED_TARGET_SEARCH_RADIUS);
        return hook.level().getEntities(hook, searchBox, target -> isPullableHookTarget(owner, target))
                .stream()
                .min((left, right) -> Double.compare(left.distanceToSqr(hook), right.distanceToSqr(hook)))
                .orElse(null);
    }

    private static boolean isPullableHookTarget(LivingEntity owner, Entity target) {
        if (target == owner || target.isSpectator() || !target.isAlive()) {
            return false;
        }

        return target instanceof ItemEntity || target instanceof ItemProjectile || target.isPickable();
    }

    private static void plungeTargetTowardOwner(Item item, LivingEntity owner, Entity target) {
        Vec3 playerCenter = owner.position().add(0.0D, owner.getEyeHeight(), 0.0D);
        Vec3 targetCenter = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        Vec3 direction = playerCenter.subtract(targetCenter);
        double distance = direction.length();
        if (distance < 1.0e-6D) {
            return;
        }

        double plungeDistance = Math.max(0.0D, distance - HOOKED_TARGET_FACE_STOP_DISTANCE);
        if (plungeDistance <= 0.0D) {
            return;
        }

        direction = direction.scale(1.0D / distance);
        boolean grounded = target.onGround();
        double maxY = grounded ? 1.0D : 0.7D;
        direction = new Vec3(direction.x, Math.max(-maxY, Math.min(maxY, direction.y)), direction.z);

        Vec3 velocity = Vec3.ZERO;
        if (grounded) {
            velocity = velocity.add(0.0D, 0.42D, 0.0D);
        }

        if (target instanceof LivingEntity livingTarget) {
            livingTarget.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, 1, false, false));
        }

        double maxPower = getTargetPlungePower(item, grounded);
        double distancePowerScale = item instanceof AdvancedFishingRod
                ? ADVANCED_TARGET_DISTANCE_POWER_SCALE
                : TONY_TARGET_DISTANCE_POWER_SCALE;
        double power = Math.min(maxPower, plungeDistance * distancePowerScale);
        target.setDeltaMovement(velocity.add(direction.scale(power)));
        target.hasImpulse = true;
        target.hurtMarked = true;
        target.fallDistance = 0.0F;
    }

    private static double getPlayerPlungePower(Item item, boolean grounded) {
        if (item instanceof AdvancedFishingRod) {
            return grounded ? ADVANCED_PLAYER_GROUNDED_PLUNGE_POWER : ADVANCED_PLAYER_AIRBORNE_PLUNGE_POWER;
        }

        return grounded ? TONY_PLAYER_GROUNDED_PLUNGE_POWER : TONY_PLAYER_AIRBORNE_PLUNGE_POWER;
    }

    private static double getTargetPlungePower(Item item, boolean grounded) {
        if (item instanceof AdvancedFishingRod) {
            return grounded ? ADVANCED_TARGET_GROUNDED_PLUNGE_POWER : ADVANCED_TARGET_AIRBORNE_PLUNGE_POWER;
        }

        return grounded ? TONY_TARGET_GROUNDED_PLUNGE_POWER : TONY_TARGET_AIRBORNE_PLUNGE_POWER;
    }
}
