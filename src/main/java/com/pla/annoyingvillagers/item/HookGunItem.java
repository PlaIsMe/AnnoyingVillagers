package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.renderer.HookGunItemRenderer;
import com.pla.annoyingvillagers.entity.HookGunHookEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/*
 * Motor constants and double-hook launch behavior are adapted from the local
 * Grappling-Hook-Mod-Reforged-main source by yyonne, GPL-3.0.
 */
public class HookGunItem extends Item {
    private static final String TAG_BOUND_ITEM = "HookGunBoundItem";
    private static final String TAG_VISUAL_HOOK_OUT = "HookGunVisualHookOut";
    private static final String TAG_LEFT_HOOK_ANIMATION = "HookGunLeftHookAnimation";
    private static final String TAG_RIGHT_HOOK_ANIMATION = "HookGunRightHookAnimation";

    public static final double MAX_ROPE_LENGTH = 30.0D;
    public static final double HOOK_DESPAWN_DISTANCE = MAX_ROPE_LENGTH + 12.0D;
    public static final double HOOK_DESPAWN_DISTANCE_SQR = HOOK_DESPAWN_DISTANCE * HOOK_DESPAWN_DISTANCE;

    private static final float THROW_SPEED = 2.0F;
    private static final double DOUBLE_HOOK_ANGLE = 20.0D;
    private static final double SNEAKING_DOUBLE_HOOK_ANGLE = 10.0D;
    private static final double MOTOR_ACCELERATION = 0.20D;
    private static final double MOTOR_MAX_SPEED = 4.0D;
    private static final double ROPE_CORRECTION_ACCELERATION = 0.10D;
    private static final double CLOSE_TO_ANCHOR_DISTANCE = 2.35D;
    private static final double COLLISION_DAMPING = 0.25D;
    private static final int USE_COOLDOWN_TICKS = 8;
    private static final byte HOOK_ANIMATION_NONE = 0;
    private static final byte HOOK_ANIMATION_NORMAL = 1;
    private static final byte HOOK_ANIMATION_TOP = 2;
    private static final double HOOK_ANIMATION_TOP_Y = 0.55D;
    private static final double HOOK_ANIMATION_BACK_DOT = -0.20D;

    public HookGunItem() {
        super(new Item.Properties().stacksTo(1).durability(384));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.annoyingvillagers.hook_gun"));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (level.isClientSide || !isVisualHookOut(stack) || !(entity instanceof LivingEntity owner)) {
            return;
        }

        if (stack == owner.getMainHandItem() && hasActiveHook(level, owner, true)) {
            return;
        }
        if (stack == owner.getOffhandItem() && hasActiveHook(level, owner, false)) {
            return;
        }

        setVisualHookOut(stack, false);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return HookGunItemRenderer.getInstance();
            }
        });
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            if (isHoldingHookGunInBothHands(player) && hasActiveHook(level, player)) {
                swingBothHands(player);
            } else if (!hasLaunchableBoundItem(player, hand)) {
                return InteractionResultHolder.pass(stack);
            } else if (isHoldingHookGunInBothHands(player)) {
                swingLaunchableHands(player);
            }
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        boolean retrievingHooks = hasActiveHook(level, player);
        if (!useHookGun(level, player, hand)) {
            return InteractionResultHolder.pass(stack);
        }
        if (!retrievingHooks) {
            player.getCooldowns().addCooldown(this, USE_COOLDOWN_TICKS);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return true;
    }

    public static boolean useHookGun(Level level, LivingEntity owner, InteractionHand hand) {
        if (level.isClientSide) {
            return false;
        }

        List<HookGunHookEntity> activeHooks = getHooks(level, owner, false);
        if (!activeHooks.isEmpty()) {
            if (isHoldingHookGunInBothHands(owner)) {
                swingBothHands(owner);
            }
            returnHooks(activeHooks, false);
            cancelHookHandAnimations(owner);
            playRetrieveSound(level, owner);
            return true;
        }

        boolean doubleMode = isHoldingHookGunInBothHands(owner);
        if (doubleMode) {
            ItemStack offHand = owner.getOffhandItem();
            ItemStack mainHand = owner.getMainHandItem();
            ItemStack offBoundItem = getBoundItem(offHand);
            ItemStack mainBoundItem = getBoundItem(mainHand);
            boolean launchOffHand = !offBoundItem.isEmpty();
            boolean launchMainHand = !mainBoundItem.isEmpty();

            if (!launchOffHand && !launchMainHand) {
                return false;
            }

            if (launchOffHand && launchMainHand) {
                double angle = getDoubleHookAngle(owner);
                launchHook(level, owner, -angle, true, false, offBoundItem);
                launchHook(level, owner, angle, true, true, mainBoundItem);
            } else if (launchOffHand) {
                launchHook(level, owner, 0.0D, false, false, offBoundItem);
            } else {
                launchHook(level, owner, 0.0D, false, true, mainBoundItem);
            }

            swingLaunchedHands(owner, launchMainHand, launchOffHand);
            damageLaunchedStacks(owner, launchMainHand, launchOffHand);
        } else {
            ItemStack hookGunStack = owner.getItemInHand(hand);
            ItemStack boundItem = getBoundItem(hookGunStack);
            if (boundItem.isEmpty()) {
                return false;
            }

            launchHook(level, owner, 0.0D, false, hand == InteractionHand.MAIN_HAND, boundItem);
            damageStack(owner, hookGunStack, hand);
        }

        level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 0.9F, 1.35F);
        return true;
    }

    public static boolean isHoldingHookGun(LivingEntity entity) {
        return entity.getMainHandItem().getItem() instanceof HookGunItem
                || entity.getOffhandItem().getItem() instanceof HookGunItem;
    }

    public static boolean isHoldingHookGunInBothHands(LivingEntity entity) {
        return entity.getMainHandItem().getItem() instanceof HookGunItem
                && entity.getOffhandItem().getItem() instanceof HookGunItem;
    }

    public static boolean shouldForceOffhandHookGunRender(LivingEntity entity) {
        return entity != null && entity.getOffhandItem().getItem() instanceof HookGunItem;
    }

    public static boolean shouldOffhandHookGunTakeRightClick(Player player) {
        if (player == null) {
            return false;
        }

        ItemStack offhand = player.getOffhandItem();
        return offhand.getItem() instanceof HookGunItem
                && !player.getCooldowns().isOnCooldown(offhand.getItem());
    }

    public static double getDoubleHookAngle(LivingEntity entity) {
        return entity.isCrouching() ? SNEAKING_DOUBLE_HOOK_ANGLE : DOUBLE_HOOK_ANGLE;
    }

    private static boolean hasLaunchableBoundItem(LivingEntity owner, InteractionHand hand) {
        if (isHoldingHookGunInBothHands(owner)) {
            return !getBoundItem(owner.getMainHandItem()).isEmpty()
                    || !getBoundItem(owner.getOffhandItem()).isEmpty();
        }

        return !getBoundItem(owner.getItemInHand(hand)).isEmpty();
    }

    public static boolean hasAttachedHook(Level level, LivingEntity owner) {
        return level != null && owner != null && !getHooks(level, owner, true).isEmpty();
    }

    public static boolean hasActiveHook(Level level, LivingEntity owner) {
        return level != null && owner != null && !getHooks(level, owner, false).isEmpty();
    }

    public static boolean hasActiveHook(Level level, LivingEntity owner, boolean rightHand) {
        return level != null
                && owner != null
                && getHooks(level, owner, false)
                .stream()
                .anyMatch(hook -> hook.isRightHand() == rightHand);
    }

    public static boolean hasActiveGrappleHook(Level level, LivingEntity owner) {
        return level != null
                && owner != null
                && getHooks(level, owner, false)
                .stream()
                .anyMatch(HookGunHookEntity::isGrappleHook);
    }

    public static boolean hasAttachedGrappleHook(Level level, LivingEntity owner) {
        return level != null
                && owner != null
                && getHooks(level, owner, true)
                .stream()
                .anyMatch(HookGunHookEntity::isGrappleHook);
    }

    public static boolean returnActiveHooks(Level level, LivingEntity owner, boolean grappleOnly) {
        if (level == null || owner == null) {
            return false;
        }

        boolean returned = returnHooks(getHooks(level, owner, false), grappleOnly);
        if (returned) {
            cancelHookHandAnimations(owner);
            playRetrieveSound(level, owner);
        }
        return returned;
    }

    public static ItemStack getBoundItem(ItemStack hookGunStack) {
        if (hookGunStack.isEmpty() || !(hookGunStack.getItem() instanceof HookGunItem) || !hookGunStack.hasTag()) {
            return ItemStack.EMPTY;
        }

        CompoundTag tag = hookGunStack.getTag();
        if (tag == null || !tag.contains(TAG_BOUND_ITEM, 10)) {
            return ItemStack.EMPTY;
        }

        return ItemStack.of(tag.getCompound(TAG_BOUND_ITEM));
    }

    public static void setBoundItem(ItemStack hookGunStack, ItemStack boundItem) {
        if (hookGunStack.isEmpty() || !(hookGunStack.getItem() instanceof HookGunItem)) {
            return;
        }

        if (boundItem.isEmpty()) {
            clearBoundItem(hookGunStack);
            return;
        }

        ItemStack stored = boundItem.copy();
        stored.setCount(1);
        hookGunStack.getOrCreateTag().put(TAG_BOUND_ITEM, stored.save(new CompoundTag()));
    }

    public static boolean isVisualHookOut(ItemStack hookGunStack) {
        return !hookGunStack.isEmpty()
                && hookGunStack.getItem() instanceof HookGunItem
                && hookGunStack.hasTag()
                && hookGunStack.getOrCreateTag().getBoolean(TAG_VISUAL_HOOK_OUT);
    }

    public static void setVisualHookOut(ItemStack hookGunStack, boolean visualHookOut) {
        if (hookGunStack.isEmpty() || !(hookGunStack.getItem() instanceof HookGunItem)) {
            return;
        }

        if (visualHookOut) {
            hookGunStack.getOrCreateTag().putBoolean(TAG_VISUAL_HOOK_OUT, true);
            return;
        }

        if (!hookGunStack.hasTag()) {
            return;
        }

        CompoundTag tag = hookGunStack.getTag();
        if (tag != null) {
            tag.remove(TAG_VISUAL_HOOK_OUT);
        }
    }

    public static void clearBoundItem(ItemStack hookGunStack) {
        if (!hookGunStack.hasTag()) {
            return;
        }

        CompoundTag tag = hookGunStack.getTag();
        if (tag != null) {
            tag.remove(TAG_BOUND_ITEM);
            tag.remove(TAG_VISUAL_HOOK_OUT);
        }
    }

    public static boolean tryBindFromSpecialAttack(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        boolean mainHookGun = mainHand.getItem() instanceof HookGunItem;
        boolean offHookGun = offHand.getItem() instanceof HookGunItem;

        if (mainHookGun && !offHookGun) {
            return bindOrUnbind(player, mainHand, offHand, InteractionHand.OFF_HAND);
        }

        if (offHookGun && !mainHookGun) {
            return bindOrUnbind(player, offHand, mainHand, InteractionHand.MAIN_HAND);
        }

        return false;
    }

    private static boolean bindOrUnbind(Player player, ItemStack hookGunStack, ItemStack sourceStack, InteractionHand sourceHand) {
        if (sourceStack.isEmpty()) {
            ItemStack boundItem = getBoundItem(hookGunStack);
            if (boundItem.isEmpty()) {
                return false;
            }

            clearBoundItem(hookGunStack);
            giveOrDrop(player, boundItem);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.6F, 0.8F);
            return true;
        }

        if (sourceStack.getItem() instanceof HookGunItem) {
            return false;
        }

        ItemStack previousBoundItem = getBoundItem(hookGunStack);
        ItemStack boundItem = sourceStack.copy();
        boundItem.setCount(1);
        setBoundItem(hookGunStack, boundItem);
        if (!player.getAbilities().instabuild) {
            sourceStack.shrink(1);
        }
        returnPreviousBoundItem(player, previousBoundItem, sourceHand);

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 0.7F, 1.2F);
        return true;
    }

    private static void giveOrDrop(Player player, ItemStack stack) {
        ItemStack remaining = stack.copy();
        player.getInventory().add(remaining);
        if (!remaining.isEmpty()) {
            player.drop(remaining, false);
        }
    }

    private static void returnPreviousBoundItem(Player player, ItemStack previousBoundItem, InteractionHand sourceHand) {
        if (previousBoundItem.isEmpty()) {
            return;
        }

        ItemStack returned = previousBoundItem.copy();
        if (tryMoveToInventoryAwayFromSourceHand(player, returned, sourceHand)) {
            return;
        }

        ItemStack sourceHandStack = player.getItemInHand(sourceHand);
        if (sourceHandStack.isEmpty()) {
            player.setItemInHand(sourceHand, returned);
            return;
        }

        giveOrDrop(player, returned);
    }

    private static boolean tryMoveToInventoryAwayFromSourceHand(Player player, ItemStack stack, InteractionHand sourceHand) {
        int avoidedSlot = sourceHand == InteractionHand.MAIN_HAND ? player.getInventory().selected : -1;

        for (int slot = 0; slot < player.getInventory().items.size(); slot++) {
            if (slot == avoidedSlot) {
                continue;
            }

            ItemStack target = player.getInventory().items.get(slot);
            if (target.isEmpty()
                    || !target.isStackable()
                    || !ItemStack.isSameItemSameTags(target, stack)) {
                continue;
            }

            int maxCount = Math.min(target.getMaxStackSize(), player.getInventory().getMaxStackSize());
            int moved = Math.min(stack.getCount(), maxCount - target.getCount());
            if (moved <= 0) {
                continue;
            }

            target.grow(moved);
            target.setPopTime(5);
            stack.shrink(moved);
            player.getInventory().setChanged();
            if (stack.isEmpty()) {
                return true;
            }
        }

        for (int slot = 0; slot < player.getInventory().items.size(); slot++) {
            if (slot == avoidedSlot || !player.getInventory().items.get(slot).isEmpty()) {
                continue;
            }

            ItemStack inserted = stack.copy();
            inserted.setPopTime(5);
            player.getInventory().items.set(slot, inserted);
            stack.setCount(0);
            player.getInventory().setChanged();
            return true;
        }

        return stack.isEmpty();
    }

    public static HookGunHookEntity launchHook(Level level, LivingEntity owner, double yawOffset, boolean doubleMode, boolean rightHand, ItemStack boundItem) {
        Vec3 origin = getHookStartPosition(owner, rightHand);
        Vec3 target = getHookAimTarget(level, owner, yawOffset);
        Vec3 direction = target.subtract(origin);
        if (direction.lengthSqr() <= 1.0E-7D) {
            direction = getHookAimDirection(owner, yawOffset);
        }
        return launchHook(level, owner, origin, direction, doubleMode, rightHand, boundItem);
    }

    public static HookGunHookEntity launchHookAt(Level level, LivingEntity owner, Vec3 target, boolean doubleMode, boolean rightHand, ItemStack boundItem) {
        Vec3 origin = getHookStartPosition(owner, rightHand);
        Vec3 direction = target.subtract(origin);
        if (direction.lengthSqr() <= 1.0E-7D) {
            direction = owner.getLookAngle();
        }
        return launchHook(level, owner, origin, direction.normalize(), doubleMode, rightHand, boundItem);
    }

    private static HookGunHookEntity launchHook(Level level, LivingEntity owner, Vec3 origin, Vec3 direction, boolean doubleMode, boolean rightHand, ItemStack boundItem) {
        HookGunHookEntity hook = new HookGunHookEntity(level, owner, doubleMode, rightHand, boundItem);
        double horizontal = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        float hookYaw = (float) (Mth.atan2(direction.x, direction.z) * Mth.RAD_TO_DEG);
        float hookPitch = (float) (Mth.atan2(direction.y, horizontal) * Mth.RAD_TO_DEG);
        hook.moveTo(origin.x, origin.y, origin.z, hookYaw, hookPitch);

        double extraVelocity = Math.max(0.0D, owner.getDeltaMovement().dot(direction));
        hook.shoot(direction.x, direction.y, direction.z, THROW_SPEED + (float) extraVelocity, 0.0F);
        level.addFreshEntity(hook);
        setVisualHookOut(getHookGunStack(owner, rightHand), true);
        updateHookHandAnimation(owner, rightHand, hook);
        return hook;
    }

    public static Vec3 getHookAimDirection(LivingEntity owner, double yawOffset) {
        return Vec3.directionFromRotation(owner.getXRot(), owner.getYRot() + (float) yawOffset).normalize();
    }

    private static Vec3 getHookAimTarget(Level level, LivingEntity owner, double yawOffset) {
        Vec3 eye = owner.getEyePosition();
        Vec3 direction = getHookAimDirection(owner, yawOffset);
        Vec3 end = eye.add(direction.scale(HOOK_DESPAWN_DISTANCE));
        HitResult hitResult = level.clip(new ClipContext(
                eye,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                owner
        ));
        return hitResult.getType() == HitResult.Type.MISS ? end : hitResult.getLocation();
    }

    public static Vec3 getHookStartPosition(LivingEntity owner, boolean rightHand) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, -0.3F, 0.0F),
                    rightHand ? Armatures.BIPED.get().toolR : Armatures.BIPED.get().toolL,
                    0.0F,
                    0.0D
            );

            if (pos != null) {
                return pos;
            }
        } catch (Exception ignored) {
        }

        Vec3 look = owner.getLookAngle();
        Vec3 side = new Vec3(-look.z, 0.0D, look.x);
        if (side.lengthSqr() > 1.0E-7D) {
            side = side.normalize();
        } else {
            side = Vec3.ZERO;
        }

        return owner.getEyePosition()
                .add(look.scale(0.45D))
                .add(side.scale(rightHand ? 0.35D : -0.35D))
                .add(0.0D, -0.18D, 0.0D);
    }

    public static ItemStack getHookGunStack(LivingEntity owner, boolean rightHand) {
        if (owner == null) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = rightHand ? owner.getMainHandItem() : owner.getOffhandItem();
        return stack.getItem() instanceof HookGunItem ? stack : ItemStack.EMPTY;
    }

    private static void tickMotor(LivingEntity owner) {
        List<HookGunHookEntity> hooks = getHooks(owner.level(), owner, true);
        if (hooks.isEmpty()) {
            return;
        }

        long grappleHookCount = hooks.stream().filter(HookGunHookEntity::isGrappleHook).count();
        if (grappleHookCount <= 0) {
            return;
        }

        Vec3 eye = owner.getEyePosition();
        Vec3 currentMotion = owner.getDeltaMovement();
        Vec3 totalPull = Vec3.ZERO;
        int pullingHooks = 0;
        boolean closeToAnchor = false;
        boolean correctedRopeMotion = false;

        for (HookGunHookEntity hook : hooks) {
            if (!hook.isGrappleHook()) {
                continue;
            }

            Vec3 toAnchor = hook.getAnchor().subtract(eye);
            double distance = toAnchor.length();
            if (distance <= 1.0E-5D) {
                continue;
            }

            Vec3 direction = toAnchor.scale(1.0D / distance);
            if (distance <= CLOSE_TO_ANCHOR_DISTANCE) {
                closeToAnchor = true;
            } else {
                totalPull = totalPull.add(direction.scale(MOTOR_ACCELERATION / grappleHookCount));
            }

            if (distance > MAX_ROPE_LENGTH) {
                Vec3 awayFromAnchor = direction.scale(-1.0D);
                double outwardSpeed = currentMotion.dot(awayFromAnchor);
                if (outwardSpeed > 0.0D) {
                    currentMotion = currentMotion.subtract(awayFromAnchor.scale(outwardSpeed));
                    correctedRopeMotion = true;
                }
                totalPull = totalPull.add(direction.scale(Math.min(0.45D, (distance - MAX_ROPE_LENGTH) * ROPE_CORRECTION_ACCELERATION)));
            }
            pullingHooks++;
        }

        if (pullingHooks <= 0) {
            return;
        }

        if (closeToAnchor && (owner.horizontalCollision || owner.verticalCollision || owner.onGround())) {
            Vec3 damped = currentMotion.scale(COLLISION_DAMPING);
            if (owner.horizontalCollision || Math.abs(damped.y) < 0.18D) {
                damped = new Vec3(damped.x, 0.0D, damped.z);
            }
            owner.setDeltaMovement(damped);
            owner.hurtMarked = true;
            owner.fallDistance = 0.0F;
            return;
        }

        Vec3 newMotion = currentMotion;
        if (totalPull.lengthSqr() > 1.0E-7D) {
            Vec3 pull = clampPullToMotorMaxSpeed(currentMotion, totalPull);
            newMotion = currentMotion.add(pull);
        }

        if (correctedRopeMotion || totalPull.lengthSqr() > 1.0E-7D) {
            owner.setDeltaMovement(newMotion);
        }
        owner.hurtMarked = true;
        owner.fallDistance = 0.0F;
    }

    private static Vec3 clampPullToMotorMaxSpeed(Vec3 currentMotion, Vec3 pull) {
        double pullLength = pull.length();
        if (pullLength <= 1.0E-7D) {
            return Vec3.ZERO;
        }

        Vec3 pullDirection = pull.scale(1.0D / pullLength);
        double currentSpeedAlongPull = currentMotion.dot(pullDirection);
        if (currentSpeedAlongPull + pullLength <= MOTOR_MAX_SPEED) {
            return pull;
        }

        double allowedPull = Math.max(0.0D, MOTOR_MAX_SPEED - currentSpeedAlongPull);
        return pullDirection.scale(allowedPull);
    }

    private static List<HookGunHookEntity> getHooks(Level level, LivingEntity owner, boolean attachedOnly) {
        AABB searchBox = owner.getBoundingBox().inflate(HOOK_DESPAWN_DISTANCE);
        return level.getEntitiesOfClass(HookGunHookEntity.class, searchBox,
                        hook -> hook.isOwnedBy(owner) && (!attachedOnly || hook.isAttached()))
                .stream()
                .sorted(Comparator.comparingDouble(owner::distanceToSqr))
                .toList();
    }

    private static boolean returnHooks(List<HookGunHookEntity> hooks, boolean grappleOnly) {
        boolean returned = false;
        for (HookGunHookEntity hook : hooks) {
            if (grappleOnly && !hook.isGrappleHook()) {
                continue;
            }
            hook.returnToOwner();
            returned = true;
        }
        return returned;
    }

    private static void damageLaunchedStacks(LivingEntity owner, boolean mainHand, boolean offHand) {
        if (mainHand) {
            damageStack(owner, owner.getMainHandItem(), InteractionHand.MAIN_HAND);
        }
        if (offHand) {
            damageStack(owner, owner.getOffhandItem(), InteractionHand.OFF_HAND);
        }
    }

    private static void damageStack(LivingEntity owner, ItemStack stack, InteractionHand hand) {
        if ((owner instanceof Player player && player.getAbilities().instabuild)
                || !(stack.getItem() instanceof HookGunItem)) {
            return;
        }

        stack.hurtAndBreak(1, owner, brokenOwner -> {
            if (brokenOwner instanceof ServerPlayer serverPlayer) {
                serverPlayer.broadcastBreakEvent(hand);
            } else {
                brokenOwner.broadcastBreakEvent(hand);
            }
        });
    }

    private static void swingBothHands(LivingEntity owner) {
        owner.swing(InteractionHand.MAIN_HAND, true);
        owner.swing(InteractionHand.OFF_HAND, true);
    }

    private static void swingLaunchableHands(LivingEntity owner) {
        swingLaunchedHands(
                owner,
                !getBoundItem(owner.getMainHandItem()).isEmpty(),
                !getBoundItem(owner.getOffhandItem()).isEmpty()
        );
    }

    private static void swingLaunchedHands(LivingEntity owner, boolean mainHand, boolean offHand) {
        if (mainHand && offHand) {
            swingBothHands(owner);
        } else if (mainHand) {
            owner.swing(InteractionHand.MAIN_HAND, true);
        } else if (offHand) {
            owner.swing(InteractionHand.OFF_HAND, true);
        }
    }

    private static void playRetrieveSound(Level level, LivingEntity owner) {
        level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.CROSSBOW_LOADING_END,
                SoundSource.PLAYERS, 0.8F, 0.8F);
    }

    private static void tickHookGunState(LivingEntity owner) {
        if (!isHoldingHookGun(owner)) {
            cancelHookHandAnimations(owner);
            return;
        }

        tickMotor(owner);
        updateHookHandAnimations(owner);
    }

    private static void updateHookHandAnimations(LivingEntity owner) {
        HookGunHookEntity leftHook = null;
        HookGunHookEntity rightHook = null;

        for (HookGunHookEntity hook : getHooks(owner.level(), owner, false)) {
            if (hook.isRightHand()) {
                if (rightHook == null) {
                    rightHook = hook;
                }
            } else if (leftHook == null) {
                leftHook = hook;
            }
        }

        updateHookHandAnimation(owner, false, leftHook);
        updateHookHandAnimation(owner, true, rightHook);
    }

    private static void updateHookHandAnimation(LivingEntity owner, boolean rightHand, HookGunHookEntity hook) {
        if (!isHoldingHookGunInHand(owner, rightHand) || hook == null || hook.isRemoved() || hook.isReturning()) {
            cancelHookHandAnimation(owner, rightHand);
            return;
        }

        setHookHandAnimationState(owner, rightHand, getHookHandAnimationState(owner, hook));
    }

    private static boolean isHoldingHookGunInHand(LivingEntity owner, boolean rightHand) {
        ItemStack stack = rightHand ? owner.getMainHandItem() : owner.getOffhandItem();
        return stack.getItem() instanceof HookGunItem;
    }

    private static byte getHookHandAnimationState(LivingEntity owner, HookGunHookEntity hook) {
        Vec3 toHook = hook.getAnchor().subtract(owner.getEyePosition());
        if (toHook.lengthSqr() <= 1.0E-7D) {
            return HOOK_ANIMATION_NORMAL;
        }

        Vec3 direction = toHook.normalize();
        Vec3 look = owner.getLookAngle().normalize();
        if (direction.y > HOOK_ANIMATION_TOP_Y || direction.dot(look) < HOOK_ANIMATION_BACK_DOT) {
            return HOOK_ANIMATION_TOP;
        }

        return HOOK_ANIMATION_NORMAL;
    }

    private static void setHookHandAnimationState(LivingEntity owner, boolean rightHand, byte nextState) {
        if (nextState == HOOK_ANIMATION_NONE) {
            cancelHookHandAnimation(owner, rightHand);
            return;
        }

        String tagName = getHookHandAnimationTag(rightHand);
        byte currentState = owner.getPersistentData().getByte(tagName);
        if (currentState == nextState) {
            return;
        }

        stopHookHandAnimations(owner, rightHand);

        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(owner, LivingEntityPatch.class);
        AssetAccessor<? extends StaticAnimation> nextAnimation = getHookHandAnimation(rightHand, nextState);
        if (livingEntityPatch == null || nextAnimation == null) {
            owner.getPersistentData().remove(tagName);
            return;
        }

        owner.getPersistentData().putByte(tagName, nextState);
        livingEntityPatch.playAnimationSynchronized(nextAnimation, 0.0F);
    }

    public static void cancelHookHandAnimations(LivingEntity owner) {
        cancelHookHandAnimation(owner, false);
        cancelHookHandAnimation(owner, true);
    }

    public static void cancelHookHandAnimation(LivingEntity owner, boolean rightHand) {
        stopHookHandAnimations(owner, rightHand);
        owner.getPersistentData().remove(getHookHandAnimationTag(rightHand));
    }

    private static void stopHookHandAnimations(LivingEntity owner, boolean rightHand) {
        EpicfightUtil.stopAnimationSynchronized(owner, getHookHandAnimation(rightHand, HOOK_ANIMATION_NORMAL));
        EpicfightUtil.stopAnimationSynchronized(owner, getHookHandAnimation(rightHand, HOOK_ANIMATION_TOP));
    }

    private static String getHookHandAnimationTag(boolean rightHand) {
        return rightHand ? TAG_RIGHT_HOOK_ANIMATION : TAG_LEFT_HOOK_ANIMATION;
    }

    private static AssetAccessor<? extends StaticAnimation> getHookHandAnimation(boolean rightHand, byte state) {
        if (state == HOOK_ANIMATION_NORMAL) {
            return rightHand ? AVAnimations.HOOK_HAND_RIGHT : AVAnimations.HOOK_HAND_LEFT;
        }
        if (state == HOOK_ANIMATION_TOP) {
            return rightHand ? AVAnimations.HOOK_HAND_RIGHT_TOP : AVAnimations.HOOK_HAND_LEFT_TOP;
        }
        return null;
    }

    @Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {
        @SubscribeEvent
        public static void onLivingTick(LivingEvent.LivingTickEvent event) {
            LivingEntity owner = event.getEntity();
            if (owner.level().isClientSide) {
                return;
            }

            if (!isHoldingHookGun(owner)
                    && !owner.getPersistentData().contains(TAG_LEFT_HOOK_ANIMATION)
                    && !owner.getPersistentData().contains(TAG_RIGHT_HOOK_ANIMATION)) {
                return;
            }

            tickHookGunState(owner);
        }
    }
}
