package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.renderer.HookGunItemRenderer;
import com.pla.annoyingvillagers.entity.HookGunHookEntity;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/*
 * Motor constants and double-hook launch behavior are adapted from the local
 * Grappling-Hook-Mod-Reforged-main source by yyonne, GPL-3.0.
 */
public class HookGunItem extends Item {
    private static final String TAG_BOUND_ITEM = "HookGunBoundItem";

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
    private static final float HAND_TO_TIP = 0.3F;
    private static final int USE_COOLDOWN_TICKS = 8;

    public HookGunItem() {
        super(new Item.Properties().stacksTo(1).durability(384));
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
            if (isHoldingHookGunInBothHands(player)) {
                swingBothHands(player);
            }
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        List<HookGunHookEntity> activeHooks = getHooks(level, player, false);
        if (!activeHooks.isEmpty()) {
            if (isHoldingHookGunInBothHands(player)) {
                swingBothHands(player);
            }
            detachHooks(activeHooks);
            playRetrieveSound(level, player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(stack, false);
        }

        boolean doubleMode = isHoldingHookGunInBothHands(player);
        if (doubleMode) {
            double angle = getDoubleHookAngle(player);
            launchHook(level, player, -angle, true, false, getBoundItem(player.getOffhandItem()));
            launchHook(level, player, angle, true, true, getBoundItem(player.getMainHandItem()));
            swingBothHands(player);
        } else {
            launchHook(level, player, 0.0D, false, hand == InteractionHand.MAIN_HAND, getBoundItem(stack));
        }

        damageUsedStacks(player, hand, doubleMode);
        player.getCooldowns().addCooldown(this, USE_COOLDOWN_TICKS);
        player.awardStat(Stats.ITEM_USED.get(this));
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 0.9F, 1.35F);
        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
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

    public static double getDoubleHookAngle(LivingEntity entity) {
        return entity.isCrouching() ? SNEAKING_DOUBLE_HOOK_ANGLE : DOUBLE_HOOK_ANGLE;
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

    public static void clearBoundItem(ItemStack hookGunStack) {
        if (!hookGunStack.hasTag()) {
            return;
        }

        CompoundTag tag = hookGunStack.getTag();
        if (tag != null) {
            tag.remove(TAG_BOUND_ITEM);
        }
    }

    public static boolean tryBindFromSpecialAttack(Player player) {
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        boolean mainHookGun = mainHand.getItem() instanceof HookGunItem;
        boolean offHookGun = offHand.getItem() instanceof HookGunItem;

        if (mainHookGun && !offHookGun) {
            return bindOrUnbind(player, mainHand, offHand);
        }

        if (offHookGun && !mainHookGun) {
            return bindOrUnbind(player, offHand, mainHand);
        }

        return false;
    }

    private static boolean bindOrUnbind(Player player, ItemStack hookGunStack, ItemStack sourceStack) {
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

        ItemStack boundItem = sourceStack.copy();
        boundItem.setCount(1);
        setBoundItem(hookGunStack, boundItem);
        if (!player.getAbilities().instabuild) {
            sourceStack.shrink(1);
        }

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

    private static void launchHook(Level level, Player player, double yawOffset, boolean doubleMode, boolean rightHand, ItemStack boundItem) {
        Vec3 direction = Vec3.directionFromRotation(player.getXRot(), player.getYRot() + (float) yawOffset).normalize();
        Vec3 origin = getHookStartPosition(player, rightHand);
        HookGunHookEntity hook = new HookGunHookEntity(level, player, doubleMode, rightHand, boundItem);
        hook.moveTo(origin.x, origin.y, origin.z, player.getYRot(), player.getXRot());

        double extraVelocity = Math.max(0.0D, player.getDeltaMovement().dot(direction));
        hook.shoot(direction.x, direction.y, direction.z, THROW_SPEED + (float) extraVelocity, 0.0F);
        level.addFreshEntity(hook);
    }

    public static Vec3 getHookStartPosition(LivingEntity owner, boolean rightHand) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    rightHand ? Armatures.BIPED.get().toolR : Armatures.BIPED.get().toolL,
                    HAND_TO_TIP,
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

    private static void tickMotor(Player player) {
        List<HookGunHookEntity> hooks = getHooks(player.level(), player, true);
        if (hooks.isEmpty()) {
            return;
        }

        long grappleHookCount = hooks.stream().filter(HookGunHookEntity::isGrappleHook).count();
        if (grappleHookCount <= 0) {
            return;
        }

        Vec3 eye = player.getEyePosition();
        Vec3 currentMotion = player.getDeltaMovement();
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

        if (closeToAnchor && (player.horizontalCollision || player.verticalCollision || player.onGround())) {
            Vec3 damped = currentMotion.scale(COLLISION_DAMPING);
            if (player.horizontalCollision || Math.abs(damped.y) < 0.18D) {
                damped = new Vec3(damped.x, 0.0D, damped.z);
            }
            player.setDeltaMovement(damped);
            player.hurtMarked = true;
            player.fallDistance = 0.0F;
            return;
        }

        Vec3 newMotion = currentMotion;
        if (totalPull.lengthSqr() > 1.0E-7D) {
            Vec3 pull = clampPullToMotorMaxSpeed(currentMotion, totalPull);
            newMotion = currentMotion.add(pull);
        }

        if (correctedRopeMotion || totalPull.lengthSqr() > 1.0E-7D) {
            player.setDeltaMovement(newMotion);
        }
        player.hurtMarked = true;
        player.fallDistance = 0.0F;
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

    private static void detachHooks(List<HookGunHookEntity> hooks) {
        for (HookGunHookEntity hook : hooks) {
            hook.discard();
        }
    }

    private static void damageUsedStacks(Player player, InteractionHand hand, boolean doubleMode) {
        damageStack(player, player.getItemInHand(hand), hand);
        if (doubleMode) {
            InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            damageStack(player, player.getItemInHand(otherHand), otherHand);
        }
    }

    private static void damageStack(Player player, ItemStack stack, InteractionHand hand) {
        if (player.getAbilities().instabuild || !(stack.getItem() instanceof HookGunItem)) {
            return;
        }

        stack.hurtAndBreak(1, player, brokenPlayer -> {
            if (brokenPlayer instanceof ServerPlayer serverPlayer) {
                serverPlayer.broadcastBreakEvent(hand);
            } else {
                brokenPlayer.broadcastBreakEvent(hand);
            }
        });
    }

    private static void swingBothHands(Player player) {
        player.swing(InteractionHand.MAIN_HAND, true);
        player.swing(InteractionHand.OFF_HAND, true);
    }

    private static void playRetrieveSound(Level level, Player player) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_LOADING_END,
                SoundSource.PLAYERS, 0.8F, 0.8F);
    }

    @Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Events {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide || !isHoldingHookGun(event.player)) {
                return;
            }

            tickMotor(event.player);
        }
    }
}
