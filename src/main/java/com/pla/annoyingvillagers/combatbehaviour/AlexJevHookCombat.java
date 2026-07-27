package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.JevEntity;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.HookUtil;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.shelmarow.combat_evolution.ai.util.CEPatchUtils;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class AlexJevHookCombat {
    private static final String KEY_SESSION_ACTIVE = "AlexJevHookSessionActive";
    private static final String KEY_SESSION_STARTED_AT = "AlexJevHookSessionStartedAt";
    private static final String KEY_ORIGINAL_MAINHAND = "AlexJevHookOriginalMainhand";
    private static final String KEY_ORIGINAL_OFFHAND = "AlexJevHookOriginalOffhand";
    private static final String KEY_SAVED_MAINHAND = "AlexJevHookSavedMainhand";
    private static final String KEY_SAVED_OFFHAND = "AlexJevHookSavedOffhand";
    private static final String KEY_ALEX_COOLDOWN_UNTIL = "AlexHookCooldownUntil";
    private static final String KEY_JEV_COOLDOWN_UNTIL = "JevHookCooldownUntil";
    private static final String KEY_JEV_RUN_AWAY_UNTIL = "JevAlexDeathRunAwayUntil";
    private static final String KEY_LAST_MAINHAND_HOOK_BOUND_ITEM = "AlexJevLastMainhandHookBoundItem";
    private static final String KEY_LAST_OFFHAND_HOOK_BOUND_ITEM = "AlexJevLastOffhandHookBoundItem";
    private static final String KEY_ALEX_SWORD_HOOK_BURST_REMAINING = "AlexSwordHookBurstRemaining";

    private static final int SHOOT_DELAY_TICKS = 7;
    private static final int DEFAULT_RETRIEVE_DELAY_TICKS = 44;
    private static final int DEFAULT_RESTORE_DELAY_TICKS = 58;
    private static final int GRAPPLE_RETRIEVE_DELAY_TICKS = 54;
    private static final int GRAPPLE_RESTORE_DELAY_TICKS = 70;
    private static final long HOOK_SESSION_RESTORE_WITHOUT_HOOK_TICKS = 100L;
    private static final int PICKAXE_HOOK_ATTACH_TIMEOUT_TICKS = 60;
    private static final int HOOK_SESSION_ABSOLUTE_RESTORE_TICKS = 140;
    private static final int ALEX_MIN_COOLDOWN_TICKS = 90;
    private static final int ALEX_RANDOM_COOLDOWN_TICKS = 80;
    private static final int JEV_MIN_COOLDOWN_TICKS = 25;
    private static final int JEV_RANDOM_COOLDOWN_TICKS = 35;
    private static final int HOOK_SEARCH_RADIUS = 30;
    private static final int HOOK_SEARCH_MIN_HORIZONTAL_DISTANCE_SQR = 10 * 10;
    private static final int HOOK_SEARCH_FALLBACK_MIN_HORIZONTAL_DISTANCE_SQR = 6 * 6;
    private static final double HOOK_SEARCH_IDEAL_DISTANCE = 22.0D;
    private static final int GROUND_HOOK_MIN_HORIZONTAL_DISTANCE_SQR = 10 * 10;
    private static final int GROUND_HOOK_MAX_HORIZONTAL_DISTANCE_SQR = 28 * 28;
    private static final double GROUND_HOOK_IDEAL_DISTANCE = 22.0D;
    private static final double MAX_HOOK_TARGET_DISTANCE_SQR = 34.0D * 34.0D;
    private static final double PICKAXE_ENTITY_PULL_MAX_DISTANCE_SQR = 22.0D * 22.0D;
    private static final double ALEX_PICKAXE_ENTITY_PULL_MIN_DISTANCE_SQR = 4.0D * 4.0D;
    private static final double JEV_PICKAXE_ENTITY_PULL_MIN_DISTANCE_SQR = 7.0D * 7.0D;
    private static final double ALEX_PICKAXE_ENTITY_PULL_CHANCE = 0.38D;
    private static final double JEV_PICKAXE_ENTITY_PULL_CHANCE = 0.24D;
    private static final double SAFE_PLACE_PULL_RADIUS = 3.0D;
    private static final double SAFE_PLACE_PULL_MIN_DISTANCE_SQR = SAFE_PLACE_PULL_RADIUS * SAFE_PLACE_PULL_RADIUS;
    private static final double ALEX_PULL_JEV_TO_SAFE_PLACE_CHANCE = 0.18D;
    private static final double JEV_PULL_ALEX_TO_SAFE_PLACE_CHANCE = 0.22D;
    private static final double ALEX_SWORD_HOOK_BURST_STATE_ZERO_MIN_DISTANCE_SQR = 11.0D * 11.0D;
    private static final double ALEX_SWORD_HOOK_BURST_STATE_ONE_MIN_DISTANCE_SQR = 5.0D * 5.0D;
    private static final double ALEX_SWORD_HOOK_BURST_STATE_ZERO_START_CHANCE = 0.24D;
    private static final double ALEX_SWORD_HOOK_BURST_STATE_ONE_START_CHANCE = 0.86D;
    private static final int ALEX_SWORD_HOOK_BURST_STATE_ZERO_MIN_SHOTS = 2;
    private static final int ALEX_SWORD_HOOK_BURST_STATE_ZERO_RANDOM_SHOTS = 1;
    private static final int ALEX_SWORD_HOOK_BURST_STATE_ONE_MIN_SHOTS = 4;
    private static final int ALEX_SWORD_HOOK_BURST_STATE_ONE_RANDOM_SHOTS = 3;

    private AlexJevHookCombat() {
    }

    public static void tickAlex(MobPatch<?> mobPatch) {
        if (!(mobPatch.getOriginal() instanceof AlexEntity alex)
                || !(alex.level() instanceof ServerLevel serverLevel)
                || !alex.isAlive()) {
            return;
        }

        syncAlexAndJevTarget(alex, alex.getProtectingJev());
        cleanupFinishedSession(alex);

        LivingEntity target = alex.getTarget();
        if (target == null || !target.isAlive() || alex.distanceToSqr(target) > MAX_HOOK_TARGET_DISTANCE_SQR) {
            return;
        }

        if (isHookSessionActive(alex) || HookGunItem.hasActiveHook(alex.level(), alex)) {
            return;
        }

        if (!CombatCommon.canPerformNormalAttackLogic(mobPatch)
                || serverLevel.getGameTime() < alex.getPersistentData().getLong(KEY_ALEX_COOLDOWN_UNTIL)) {
            return;
        }

        if (tryAlexSwordHookBurst(alex, target)) {
            setCooldown(alex, KEY_ALEX_COOLDOWN_UNTIL, 18, 12);
            return;
        }

        JevEntity jev = alex.getProtectingJev();
        if (jev != null && jev.isAlive()
                && tryPullPartnerToSafePlace(alex, jev, createAlexDefaultPickaxe(), ALEX_PULL_JEV_TO_SAFE_PLACE_CHANCE)) {
            setCooldown(alex, KEY_ALEX_COOLDOWN_UNTIL, ALEX_MIN_COOLDOWN_TICKS + 20, ALEX_RANDOM_COOLDOWN_TICKS);
            return;
        }

        if (alex.getState() == 1 && alex.canDualHookInSecondPhase() && alex.getRandom().nextDouble() < 0.24D
                && performAlexDualHook(alex, target)) {
            setCooldown(alex, KEY_ALEX_COOLDOWN_UNTIL, ALEX_MIN_COOLDOWN_TICKS + 40, ALEX_RANDOM_COOLDOWN_TICKS);
            return;
        }

        if (alex.getState() == 1 && alex.getRandom().nextDouble() < 0.12D
                && performAlexFlintHook(alex, target)) {
            setCooldown(alex, KEY_ALEX_COOLDOWN_UNTIL, ALEX_MIN_COOLDOWN_TICKS + 45, ALEX_RANDOM_COOLDOWN_TICKS);
            return;
        }

        maybeSwitchAlexBoundHook(alex);
        ItemStack bound = alex.getCurrentBoundHook();
        if (bound.isEmpty()) {
            bound = createAlexDefaultPickaxe();
            alex.setCurrentBoundHook(bound);
        }

        boolean fired;
        if (HookUtil.isPickaxe(bound)) {
            fired = shouldTryPickaxeEntityPull(alex, target, ALEX_PICKAXE_ENTITY_PULL_CHANCE, ALEX_PICKAXE_ENTITY_PULL_MIN_DISTANCE_SQR)
                    && shootPickaxeHookAtEntity(alex, InteractionHand.OFF_HAND, bound, target, alex::setCurrentBoundHook);
            if (!fired) {
                Vec3 anchor = findHookAnchor(alex, target, false);
                fired = anchor != null && shootHook(
                        alex,
                        InteractionHand.OFF_HAND,
                        bound,
                        () -> anchor,
                        GRAPPLE_RETRIEVE_DELAY_TICKS,
                        GRAPPLE_RESTORE_DELAY_TICKS,
                        alex::setCurrentBoundHook
                );
            }
        } else {
            fired = shootHookAtEntity(
                    alex,
                    InteractionHand.OFF_HAND,
                    bound,
                    target,
                    DEFAULT_RETRIEVE_DELAY_TICKS,
                    DEFAULT_RESTORE_DELAY_TICKS,
                    alex::setCurrentBoundHook
            );
        }

        if (fired) {
            setCooldown(alex, KEY_ALEX_COOLDOWN_UNTIL, ALEX_MIN_COOLDOWN_TICKS, ALEX_RANDOM_COOLDOWN_TICKS);
        }
    }

    public static void tickJev(MobPatch<?> mobPatch) {
        if (!(mobPatch.getOriginal() instanceof JevEntity jev)
                || !(jev.level() instanceof ServerLevel serverLevel)
                || !jev.isAlive()) {
            return;
        }

        AlexEntity alex = jev.getFollowTarget();
        cleanupFinishedSession(jev);
        moveJevAroundPartner(jev, alex);

        if (alex == null || !alex.isAlive()) {
            return;
        }

        LivingEntity alexTarget = alex.getTarget();
        if (alexTarget == null || !alexTarget.isAlive()) {
            return;
        }

        syncAlexAndJevTarget(alex, jev);

        if (jev.getPersistentData().getLong(KEY_JEV_RUN_AWAY_UNTIL) > serverLevel.getGameTime()) {
            tryJevHookAway(jev, alex);
        }

        if (isHookSessionActive(jev)
                || HookGunItem.hasActiveHook(jev.level(), jev)
                || serverLevel.getGameTime() < jev.getPersistentData().getLong(KEY_JEV_COOLDOWN_UNTIL)) {
            return;
        }

        LivingEntity target = jev.getTarget() != null && jev.getTarget().isAlive() ? jev.getTarget() : alexTarget;
        Random random = new Random();

        if (alex.isOnFire() && shootJevBurningSupportSnowball(jev, alex)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 30, 20);
            return;
        }

        if (alex.getHealth() <= alex.getMaxHealth() * 0.55F
                && shootJevSupportPotion(jev, alex, random)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 30, 25);
            return;
        }

        if (isValidJevEnemyTarget(jev, alex, target)
                && isHoldingBowLike(target)
                && random.nextDouble() < 0.82D
                && shootJevCoverBlock(jev, alex, target)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 18, 18);
            return;
        }

        if (target != null && target.isAlive() && target.distanceToSqr(jev) < 8.0D * 8.0D
                && random.nextDouble() < 0.85D && tryJevHookAway(jev, target)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 25, 20);
            return;
        }

        if (tryPullPartnerToSafePlace(jev, alex, createJevPickaxe(), JEV_PULL_ALEX_TO_SAFE_PLACE_CHANCE)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 26, 28);
            return;
        }

        if (isValidJevEnemyTarget(jev, alex, target)
                && shouldTryPickaxeEntityPull(jev, target, JEV_PICKAXE_ENTITY_PULL_CHANCE, JEV_PICKAXE_ENTITY_PULL_MIN_DISTANCE_SQR)
                && shootPickaxeHookAtEntity(jev, InteractionHand.OFF_HAND, createJevPickaxe(), target, null)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 24, 26);
            return;
        }

        if (isValidJevEnemyTarget(jev, alex, target)
                && random.nextDouble() < 0.62D
                && shootJevEnemyHarassment(jev, target, random)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 22, 22);
            return;
        }

        if (isValidJevEnemyTarget(jev, alex, target)
                && random.nextDouble() < 0.38D
                && shootJevEnemyDistractionBlock(jev, target)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 20, 24);
            return;
        }

        if (random.nextDouble() < 0.42D && shootJevBoneMealSapling(jev)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 18, 22);
            return;
        }

        if (isMissingHealth(alex) && random.nextDouble() < 0.42D && shootJevSupportFood(jev, alex, random)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 18, 20);
            return;
        }

        if (isMissingHealth(alex) && random.nextDouble() < 0.45D && shootJevSupportPotion(jev, alex, random)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 24, 24);
            return;
        }

        if (isMissingHealth(alex)
                && (alex.getHealth() <= alex.getMaxHealth() * 0.75F || random.nextDouble() < 0.18D)
                && shootJevSupportFood(jev, alex, random)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 35, 35);
            return;
        }

        if (alex.getState() == 1 && alex.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && random.nextDouble() < 0.35D
                && shootHookAtEntity(jev, InteractionHand.OFF_HAND, createAlexHelmet(), alex,
                DEFAULT_RETRIEVE_DELAY_TICKS, DEFAULT_RESTORE_DELAY_TICKS, null)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 80, 50);
            return;
        }

        if (random.nextDouble() < 0.65D && tryJevHookAway(jev, target != null && target.isAlive() ? target : alex)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 20, 25);
            return;
        }

        if (alex.getState() == 1 && isMissingHealth(alex) && random.nextDouble() < 0.22D
                && shootJevSupportPotion(jev, alex, random)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 55, 35);
            return;
        }

        if (random.nextDouble() < 0.45D && shootJevSupportBlock(jev, alex)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, JEV_MIN_COOLDOWN_TICKS, JEV_RANDOM_COOLDOWN_TICKS);
            return;
        }

        if (isMissingHealth(alex) && random.nextDouble() < 0.55D && shootJevSupportFood(jev, alex, random)) {
            setCooldown(jev, KEY_JEV_COOLDOWN_UNTIL, 18, 20);
        }
    }

    public static void onAlexDeath(AlexEntity alex) {
        JevEntity jev = alex.getProtectingJev();
        if (jev == null || !jev.isAlive() || !(alex.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        jev.getPersistentData().putLong(KEY_JEV_RUN_AWAY_UNTIL, serverLevel.getGameTime() + 360L);
        shootHook(jev, InteractionHand.OFF_HAND, createJevPickaxe(), alex::getEyePosition,
                GRAPPLE_RETRIEVE_DELAY_TICKS, GRAPPLE_RESTORE_DELAY_TICKS, null);
    }

    public static void onJevDeath(JevEntity jev) {
        AlexEntity alex = jev.getFollowTarget();
        if (alex == null || !alex.isAlive()) {
            return;
        }

        shootHook(alex, InteractionHand.OFF_HAND, createAlexDefaultPickaxe(), jev::getEyePosition,
                GRAPPLE_RETRIEVE_DELAY_TICKS, GRAPPLE_RESTORE_DELAY_TICKS, null);
    }

    public static ItemStack createBoundHookGun(ItemStack boundItem) {
        ItemStack hookGun = new ItemStack(AnnoyingVillagersModItems.HOOK_GUN.get());
        HookGunItem.setBoundItem(hookGun, boundItem);
        return hookGun;
    }

    public static ItemStack createAlexDefaultPickaxe() {
        ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
        pickaxe.enchant(Enchantments.MENDING, 1);
        pickaxe.enchant(Enchantments.UNBREAKING, 3);
        pickaxe.enchant(Enchantments.BLOCK_EFFICIENCY, 3);
        return pickaxe;
    }

    public static ItemStack createAlexHookSword() {
        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        sword.enchant(Enchantments.SHARPNESS, 5);
        sword.enchant(Enchantments.SMITE, 5);
        return sword;
    }

    public static ItemStack createJevPickaxe() {
        return new ItemStack(Items.IRON_PICKAXE);
    }

    public static ItemStack createAlexHelmet() {
        ItemStack helmet = new ItemStack(Items.DIAMOND_HELMET);
        helmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
        helmet.enchant(Enchantments.UNBREAKING, 3);
        return helmet;
    }

    public static ItemStack createRandomJevPlantLoot(Random random) {
        return switch (random.nextInt(14)) {
            case 0, 1, 2 -> new ItemStack(Items.BONE_MEAL);
            case 3 -> new ItemStack(Blocks.POPPY);
            case 4 -> new ItemStack(Blocks.DANDELION);
            case 5 -> new ItemStack(Blocks.BLUE_ORCHID);
            case 6 -> new ItemStack(Blocks.ALLIUM);
            case 7 -> new ItemStack(Blocks.OAK_SAPLING);
            case 8 -> new ItemStack(Blocks.SPRUCE_SAPLING);
            case 9 -> new ItemStack(Blocks.BIRCH_SAPLING);
            case 10 -> new ItemStack(Blocks.JUNGLE_SAPLING);
            case 11 -> new ItemStack(Blocks.ACACIA_SAPLING);
            case 12 -> new ItemStack(Blocks.DARK_OAK_SAPLING);
            default -> new ItemStack(Blocks.CHERRY_SAPLING);
        };
    }

    private static void maybeSwitchAlexBoundHook(AlexEntity alex) {
        ItemStack current = alex.getCurrentBoundHook();
        double roll = alex.getRandom().nextDouble();
        if (HookUtil.isPickaxe(current)) {
            if (roll > 0.22D) {
                return;
            }
            alex.setCurrentBoundHook(createAlexHookSword());
        } else if (roll < 0.34D) {
            alex.setCurrentBoundHook(createAlexDefaultPickaxe());
        } else {
            return;
        }

        playHookGunAnimation(alex);
        rememberLastHookBoundItem(alex, InteractionHand.OFF_HAND, alex.getCurrentBoundHook());
        alex.level().playSound(null, alex.getX(), alex.getY(), alex.getZ(),
                SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.HOSTILE, 0.7F, 1.2F);
    }

    private static boolean tryAlexSwordHookBurst(AlexEntity alex, LivingEntity target) {
        CompoundTag data = alex.getPersistentData();
        int remaining = data.getInt(KEY_ALEX_SWORD_HOOK_BURST_REMAINING);
        double distanceSqr = alex.distanceToSqr(target);
        if (!canAlexUseSwordHookBurst(alex, target, distanceSqr)) {
            data.remove(KEY_ALEX_SWORD_HOOK_BURST_REMAINING);
            return false;
        }

        if (remaining <= 0) {
            if (alex.getRandom().nextDouble() >= getAlexSwordHookBurstStartChance(alex)) {
                return false;
            }
            remaining = getAlexSwordHookBurstMinShots(alex)
                    + alex.getRandom().nextInt(getAlexSwordHookBurstRandomShots(alex) + 1);
            data.putInt(KEY_ALEX_SWORD_HOOK_BURST_REMAINING, remaining);
        }

        ItemStack sword = createAlexHookSword();
        alex.setCurrentBoundHook(sword);
        boolean fired = shootHookAtEntity(
                alex,
                InteractionHand.OFF_HAND,
                sword,
                target,
                DEFAULT_RETRIEVE_DELAY_TICKS,
                DEFAULT_RESTORE_DELAY_TICKS,
                updatedBound -> alex.setCurrentBoundHook(sword)
        );
        if (!fired) {
            return false;
        }

        remaining--;
        if (remaining <= 0) {
            data.remove(KEY_ALEX_SWORD_HOOK_BURST_REMAINING);
        } else {
            data.putInt(KEY_ALEX_SWORD_HOOK_BURST_REMAINING, remaining);
        }
        return true;
    }

    private static boolean canAlexUseSwordHookBurst(AlexEntity alex, LivingEntity target, double distanceSqr) {
        if (!alex.hasLineOfSight(target)) {
            return false;
        }

        if (alex.getState() == 0
                && (alex.getHealth() <= alex.getMaxHealth() * 0.45F || alex.getOffhandItem().is(Items.TOTEM_OF_UNDYING))) {
            return false;
        }

        double minDistanceSqr = alex.getState() == 1
                ? ALEX_SWORD_HOOK_BURST_STATE_ONE_MIN_DISTANCE_SQR
                : ALEX_SWORD_HOOK_BURST_STATE_ZERO_MIN_DISTANCE_SQR;
        return distanceSqr >= minDistanceSqr;
    }

    private static double getAlexSwordHookBurstStartChance(AlexEntity alex) {
        return alex.getState() == 1
                ? ALEX_SWORD_HOOK_BURST_STATE_ONE_START_CHANCE
                : ALEX_SWORD_HOOK_BURST_STATE_ZERO_START_CHANCE;
    }

    private static int getAlexSwordHookBurstMinShots(AlexEntity alex) {
        return alex.getState() == 1
                ? ALEX_SWORD_HOOK_BURST_STATE_ONE_MIN_SHOTS
                : ALEX_SWORD_HOOK_BURST_STATE_ZERO_MIN_SHOTS;
    }

    private static int getAlexSwordHookBurstRandomShots(AlexEntity alex) {
        return alex.getState() == 1
                ? ALEX_SWORD_HOOK_BURST_STATE_ONE_RANDOM_SHOTS
                : ALEX_SWORD_HOOK_BURST_STATE_ZERO_RANDOM_SHOTS;
    }

    private static boolean performAlexDualHook(AlexEntity alex, LivingEntity target) {
        if (isHookSessionActive(alex) || HookGunItem.hasActiveHook(alex.level(), alex)) {
            return false;
        }

        double roll = alex.getRandom().nextDouble();
        ItemStack leftBound;
        ItemStack rightBound;
        Supplier<Vec3> leftTarget;
        Supplier<Vec3> rightTarget;

        if (roll < 0.18D && canPickaxeEntityPullTarget(alex, target, ALEX_PICKAXE_ENTITY_PULL_MIN_DISTANCE_SQR)) {
            leftBound = createAlexDefaultPickaxe();
            rightBound = createAlexHookSword();
            leftTarget = () -> target.getEyePosition().add(0.35D, 0.0D, 0.0D);
            rightTarget = () -> target.getEyePosition().add(-0.35D, 0.0D, 0.0D);
        } else if (roll < 0.34D) {
            leftBound = createAlexDefaultPickaxe();
            rightBound = createAlexHookSword();
            Vec3 anchor = findHookAnchor(alex, target, false);
            if (anchor == null) return false;
            leftTarget = () -> anchor;
            rightTarget = () -> target.getEyePosition();
        } else if (roll < 0.56D) {
            leftBound = createAlexHookSword();
            rightBound = createAlexHookSword();
            leftTarget = () -> target.getEyePosition().add(0.35D, 0.0D, 0.0D);
            rightTarget = () -> target.getEyePosition().add(-0.35D, 0.0D, 0.0D);
        } else if (roll < 0.78D) {
            leftBound = createAlexDefaultPickaxe();
            rightBound = createAlexDefaultPickaxe();
            Vec3 leftAnchor = findHookAnchor(alex, target, false);
            Vec3 rightAnchor = findHookAnchor(alex, target, true);
            if (leftAnchor == null || rightAnchor == null) return false;
            leftTarget = () -> leftAnchor;
            rightTarget = () -> rightAnchor;
        } else {
            BlockPos support = findPlacementSupportBlock(target.level(), target.blockPosition());
            if (support == null) return false;
            leftBound = new ItemStack(Items.LAVA_BUCKET);
            rightBound = new ItemStack(Items.WATER_BUCKET);
            leftTarget = () -> Vec3.atCenterOf(support);
            rightTarget = () -> Vec3.atCenterOf(support.relative(Direction.Plane.HORIZONTAL.getRandomDirection(alex.getRandom())));
        }

        return shootDualHook(alex, leftBound, leftTarget, rightBound, rightTarget,
                GRAPPLE_RETRIEVE_DELAY_TICKS, GRAPPLE_RESTORE_DELAY_TICKS);
    }

    private static boolean performAlexFlintHook(AlexEntity alex, LivingEntity target) {
        return shootHookAtEntity(alex, InteractionHand.OFF_HAND, new ItemStack(Items.FLINT_AND_STEEL), target,
                DEFAULT_RETRIEVE_DELAY_TICKS, DEFAULT_RESTORE_DELAY_TICKS, null);
    }

    private static boolean shootJevBurningSupportSnowball(JevEntity jev, AlexEntity alex) {
        ItemStack snowball = new ItemStack(Items.SNOWBALL);
        boolean fired = InventoryUtils.hasItem(jev, Items.SNOWBALL)
                && shootHookAtEntity(jev, InteractionHand.OFF_HAND, snowball, alex,
                DEFAULT_RETRIEVE_DELAY_TICKS, DEFAULT_RESTORE_DELAY_TICKS, null);
        if (fired) {
            InventoryUtils.consumeItem(jev, Items.SNOWBALL, 1);
        }
        return fired;
    }

    private static boolean shootPickaxeHookAtEntity(
            LivingEntity shooter,
            InteractionHand hand,
            ItemStack pickaxe,
            LivingEntity target,
            @Nullable Consumer<ItemStack> completion
    ) {
        return shootHookAtEntity(shooter, hand, pickaxe, target,
                GRAPPLE_RETRIEVE_DELAY_TICKS, GRAPPLE_RESTORE_DELAY_TICKS, completion);
    }

    private static boolean tryPullPartnerToSafePlace(
            LivingEntity puller,
            LivingEntity partner,
            ItemStack pickaxe,
            double chance
    ) {
        return puller.getRandom().nextDouble() < chance
                && canPullPartnerToSafePlace(puller, partner)
                && shootPickaxeHookAtEntity(puller, InteractionHand.OFF_HAND, pickaxe, partner, null);
    }

    private static boolean canPullPartnerToSafePlace(LivingEntity puller, @Nullable LivingEntity partner) {
        if (partner == null
                || !partner.isAlive()
                || partner.isSpectator()
                || partner == puller
                || !puller.hasLineOfSight(partner)
                || hasNearbyEnemyTargeting(puller, SAFE_PLACE_PULL_RADIUS)) {
            return false;
        }

        double distanceSqr = puller.distanceToSqr(partner);
        return distanceSqr >= SAFE_PLACE_PULL_MIN_DISTANCE_SQR
                && distanceSqr <= PICKAXE_ENTITY_PULL_MAX_DISTANCE_SQR;
    }

    private static boolean hasNearbyEnemyTargeting(LivingEntity entity, double radius) {
        return !entity.level().getEntitiesOfClass(Mob.class, entity.getBoundingBox().inflate(radius),
                mob -> mob != entity
                        && mob.isAlive()
                        && !mob.isSpectator()
                        && mob.getTarget() == entity
                        && !entity.isAlliedTo(mob)
                        && !mob.isAlliedTo(entity)
        ).isEmpty();
    }

    private static boolean tryJevHookAway(JevEntity jev, @Nullable LivingEntity awayFrom) {
        if (isHookSessionActive(jev) || HookGunItem.hasActiveHook(jev.level(), jev)) {
            return false;
        }

        Vec3 anchor = findHookAnchor(jev, awayFrom, true);
        if (anchor == null) {
            anchor = findNearbyGroundHookAnchor(jev, awayFrom);
        }
        if (anchor == null) {
            return false;
        }

        Vec3 selectedAnchor = anchor;
        return shootHook(jev, InteractionHand.OFF_HAND, createJevPickaxe(), () -> selectedAnchor,
                GRAPPLE_RETRIEVE_DELAY_TICKS, GRAPPLE_RESTORE_DELAY_TICKS, null);
    }

    private static boolean shootJevSupportBlock(JevEntity jev, AlexEntity alex) {
        return shootJevBlockAtArea(jev, alex.blockPosition(), 5, true);
    }

    private static boolean shootJevCoverBlock(JevEntity jev, AlexEntity alex, LivingEntity enemy) {
        return shootJevBlockAtArea(jev, getAlexCoverBlockCenter(alex, enemy), 3, true);
    }

    private static boolean shootJevEnemyDistractionBlock(JevEntity jev, LivingEntity target) {
        return shootJevBlockAtArea(jev, target.blockPosition(), 4, false);
    }

    private static boolean shootJevBlockAtArea(JevEntity jev, BlockPos center, int radius, boolean coverBlock) {
        SimpleContainer inventory = jev.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack inventoryStack = inventory.getItem(slot);
            if (inventoryStack.isEmpty() || !(inventoryStack.getItem() instanceof BlockItem)) {
                continue;
            }

            ItemStack block = oneOf(inventoryStack);
            BlockPos support = findSupportBlockAround(jev.level(), center, radius, block);
            if (support == null) {
                continue;
            }

            BlockPos selectedSupport = support;
            boolean fired = shootHook(jev, InteractionHand.OFF_HAND, block,
                    () -> getGroundHookAnchorAimPosition(selectedSupport),
                    DEFAULT_RETRIEVE_DELAY_TICKS, DEFAULT_RESTORE_DELAY_TICKS, null);
            if (fired) {
                InventoryUtils.consumeItem(inventory, stack -> ItemStack.isSameItemSameTags(stack, block), 1);
            }
            return fired;
        }

        return false;
    }

    private static boolean shootJevBoneMealSapling(JevEntity jev) {
        BlockPos sapling = findVisibleSaplingForBoneMeal(jev);
        if (sapling == null) {
            return false;
        }

        Vec3 aim = Vec3.atCenterOf(sapling);
        ItemStack boneMeal = new ItemStack(Items.BONE_MEAL);
        boolean fired = InventoryUtils.hasItem(jev, Items.BONE_MEAL)
                && shootHook(jev, InteractionHand.OFF_HAND, boneMeal,
                () -> aim, DEFAULT_RETRIEVE_DELAY_TICKS, DEFAULT_RESTORE_DELAY_TICKS, null);
        if (fired) {
            InventoryUtils.consumeItem(jev, Items.BONE_MEAL, 1);
        }
        return fired;
    }

    private static boolean shootJevSupportFood(JevEntity jev, AlexEntity alex, Random random) {
        return selectInventoryItem(jev, stack -> isPositiveFoodStack(stack, alex), random)
                .map(stack -> shootInventoryItemAtEntity(jev, stack, alex))
                .orElse(false);
    }

    private static boolean shootJevSupportPotion(JevEntity jev, AlexEntity alex, Random random) {
        return selectInventoryItem(jev, AlexJevHookCombat::isPositivePotionStack, random)
                .map(stack -> shootInventoryItemAtEntity(jev, stack, alex))
                .orElse(false);
    }

    private static boolean shootJevEnemyHarassment(JevEntity jev, LivingEntity target, Random random) {
        return selectInventoryItem(jev, AlexJevHookCombat::isEnemyHarassmentStack, random)
                .map(stack -> shootInventoryItemAtEntity(jev, stack, target))
                .orElse(false);
    }

    private static boolean shootInventoryItemAtEntity(JevEntity jev, ItemStack stack, LivingEntity target) {
        ItemStack shot = oneOf(stack);
        boolean fired = shootHookAtEntity(jev, InteractionHand.OFF_HAND, shot, target,
                DEFAULT_RETRIEVE_DELAY_TICKS, DEFAULT_RESTORE_DELAY_TICKS, null);
        if (fired) {
            InventoryUtils.consumeItem(jev.getInventory(), candidate -> ItemStack.isSameItemSameTags(candidate, shot), 1);
        }
        return fired;
    }

    private static Optional<ItemStack> selectInventoryItem(JevEntity jev, Predicate<ItemStack> matcher, Random random) {
        List<ItemStack> candidates = new ArrayList<>();
        SimpleContainer inventory = jev.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && matcher.test(stack)) {
                candidates.add(oneOf(stack));
            }
        }

        if (candidates.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(candidates.get(random.nextInt(candidates.size())));
    }

    private static boolean isValidJevEnemyTarget(JevEntity jev, AlexEntity alex, @Nullable LivingEntity target) {
        return target != null
                && target.isAlive()
                && !target.isSpectator()
                && target != jev
                && target != alex
                && !target.isAlliedTo(jev)
                && !target.isAlliedTo(alex);
    }

    private static boolean isMissingHealth(LivingEntity entity) {
        return entity.getHealth() < entity.getMaxHealth() - 0.5F;
    }

    private static boolean isHoldingBowLike(LivingEntity entity) {
        return isBowLike(entity.getMainHandItem()) || isBowLike(entity.getOffhandItem());
    }

    private static boolean isBowLike(ItemStack stack) {
        return stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem;
    }

    private static boolean shouldTryPickaxeEntityPull(
            LivingEntity shooter,
            LivingEntity target,
            double chance,
            double minDistanceSqr
    ) {
        return shooter.getRandom().nextDouble() < chance
                && canPickaxeEntityPullTarget(shooter, target, minDistanceSqr);
    }

    private static boolean canPickaxeEntityPullTarget(
            LivingEntity shooter,
            @Nullable LivingEntity target,
            double minDistanceSqr
    ) {
        if (target == null
                || !target.isAlive()
                || target.isSpectator()
                || target == shooter
                || shooter.isAlliedTo(target)
                || target.isAlliedTo(shooter)
                || !shooter.hasLineOfSight(target)) {
            return false;
        }

        double distanceSqr = shooter.distanceToSqr(target);
        return distanceSqr >= minDistanceSqr && distanceSqr <= PICKAXE_ENTITY_PULL_MAX_DISTANCE_SQR;
    }

    private static boolean shootHookAtEntity(
            LivingEntity shooter,
            InteractionHand hand,
            ItemStack boundItem,
            LivingEntity target,
            int retrieveDelayTicks,
            int restoreDelayTicks,
            @Nullable Consumer<ItemStack> completion
    ) {
        return shootHook(shooter, hand, boundItem, target::getEyePosition, retrieveDelayTicks, restoreDelayTicks, completion);
    }

    private static boolean shootHook(
            LivingEntity shooter,
            InteractionHand hand,
            ItemStack boundItem,
            Supplier<Vec3> targetSupplier,
            int retrieveDelayTicks,
            int restoreDelayTicks,
            @Nullable Consumer<ItemStack> completion
    ) {
        return shootHook(shooter, hand, boundItem, targetSupplier, retrieveDelayTicks, restoreDelayTicks, completion, true);
    }

    private static boolean shootHook(
            LivingEntity shooter,
            InteractionHand hand,
            ItemStack boundItem,
            Supplier<Vec3> targetSupplier,
            int retrieveDelayTicks,
            int restoreDelayTicks,
            @Nullable Consumer<ItemStack> completion,
            boolean allowHookGunAnimation
    ) {
        if (boundItem.isEmpty()
                || shooter.level().isClientSide
                || isHookSessionActive(shooter)
                || HookGunItem.hasActiveHook(shooter.level(), shooter)) {
            return false;
        }

        boolean playAnimation = allowHookGunAnimation && shouldPlayHookGunAnimationForHand(shooter, hand, boundItem);
        beginHookSession(shooter, hand == InteractionHand.MAIN_HAND, hand == InteractionHand.OFF_HAND);
        shooter.setItemInHand(hand, createBoundHookGun(boundItem));
        shooter.swing(hand, true);
        if (playAnimation) {
            playHookGunAnimation(shooter);
        }

        new DelayedTask(SHOOT_DELAY_TICKS) {
            @Override
            public void run() {
                if (!shooter.isAlive() || !isHookSessionActive(shooter)) {
                    return;
                }

                ItemStack hookGun = shooter.getItemInHand(hand);
                if (!(hookGun.getItem() instanceof HookGunItem)) {
                    restoreHookSession(shooter);
                    return;
                }

                Vec3 target = targetSupplier.get();
                if (target == null) {
                    restoreHookSession(shooter);
                    return;
                }

                aimAt(shooter, target);
                ItemStack currentBound = HookGunItem.getBoundItem(hookGun);
                HookGunItem.launchHookAt(shooter.level(), shooter, target, false, hand == InteractionHand.MAIN_HAND, currentBound);
                shooter.level().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(),
                        SoundEvents.ARROW_SHOOT, SoundSource.HOSTILE, 0.9F, 1.35F);
            }
        };

        scheduleRetrieveAndRestore(shooter, hand, HookUtil.isPickaxe(boundItem), completion);
        return true;
    }

    private static boolean shootDualHook(
            LivingEntity shooter,
            ItemStack leftBoundItem,
            Supplier<Vec3> leftTargetSupplier,
            ItemStack rightBoundItem,
            Supplier<Vec3> rightTargetSupplier,
            int retrieveDelayTicks,
            int restoreDelayTicks
    ) {
        if (leftBoundItem.isEmpty()
                || rightBoundItem.isEmpty()
                || shooter.level().isClientSide
                || isHookSessionActive(shooter)
                || HookGunItem.hasActiveHook(shooter.level(), shooter)) {
            return false;
        }

        beginHookSession(shooter, true, true);
        boolean playLeftAnimation = shouldPlayHookGunAnimationForHand(shooter, InteractionHand.OFF_HAND, leftBoundItem);
        boolean playRightAnimation = shouldPlayHookGunAnimationForHand(shooter, InteractionHand.MAIN_HAND, rightBoundItem);
        shooter.setItemInHand(InteractionHand.OFF_HAND, createBoundHookGun(leftBoundItem));
        shooter.setItemInHand(InteractionHand.MAIN_HAND, createBoundHookGun(rightBoundItem));
        shooter.swing(InteractionHand.OFF_HAND, true);
        shooter.swing(InteractionHand.MAIN_HAND, true);
        if (playLeftAnimation || playRightAnimation) {
            playHookGunAnimation(shooter);
        }

        new DelayedTask(SHOOT_DELAY_TICKS) {
            @Override
            public void run() {
                if (!shooter.isAlive() || !isHookSessionActive(shooter)) {
                    return;
                }

                Vec3 leftTarget = leftTargetSupplier.get();
                Vec3 rightTarget = rightTargetSupplier.get();
                if (leftTarget == null || rightTarget == null) {
                    restoreHookSession(shooter);
                    return;
                }

                ItemStack leftHookGun = shooter.getOffhandItem();
                ItemStack rightHookGun = shooter.getMainHandItem();
                if (!(leftHookGun.getItem() instanceof HookGunItem) || !(rightHookGun.getItem() instanceof HookGunItem)) {
                    restoreHookSession(shooter);
                    return;
                }

                aimAt(shooter, rightTarget);
                HookGunItem.launchHookAt(shooter.level(), shooter, leftTarget, true, false, HookGunItem.getBoundItem(leftHookGun));
                HookGunItem.launchHookAt(shooter.level(), shooter, rightTarget, true, true, HookGunItem.getBoundItem(rightHookGun));
                shooter.level().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(),
                        SoundEvents.ARROW_SHOOT, SoundSource.HOSTILE, 1.0F, 1.25F);
            }
        };

        scheduleRetrieveAndRestore(
                shooter,
                InteractionHand.MAIN_HAND,
                HookUtil.isPickaxe(leftBoundItem) || HookUtil.isPickaxe(rightBoundItem),
                null
        );
        return true;
    }

    private static void scheduleRetrieveAndRestore(
            LivingEntity shooter,
            InteractionHand hand,
            boolean waitForPickaxeHook,
            @Nullable Consumer<ItemStack> completion
    ) {
        new DelayedTask(SHOOT_DELAY_TICKS + 1) {
            @Override
            public void run() {
                scheduleHookSessionMonitor(shooter, hand, waitForPickaxeHook, completion, SHOOT_DELAY_TICKS + 1);
            }
        };
    }

    private static void scheduleHookSessionMonitor(
            LivingEntity shooter,
            InteractionHand hand,
            boolean waitForPickaxeHook,
            @Nullable Consumer<ItemStack> completion,
            int waitedTicks
    ) {
        new DelayedTask(1) {
            @Override
            public void run() {
                if (!shooter.isAlive() || !isHookSessionActive(shooter)) {
                    return;
                }

                boolean hasActiveHook = HookGunItem.hasActiveHook(shooter.level(), shooter);
                if (!hasActiveHook && waitedTicks >= SHOOT_DELAY_TICKS) {
                    completeAndRestoreHookSession(shooter, hand, completion);
                    return;
                }

                if (waitForPickaxeHook
                        && waitedTicks >= PICKAXE_HOOK_ATTACH_TIMEOUT_TICKS
                        && HookGunItem.hasActiveGrappleHook(shooter.level(), shooter)
                        && !HookGunItem.hasAttachedGrappleHook(shooter.level(), shooter)) {
                    HookGunItem.returnActiveHooks(shooter.level(), shooter, true);
                }

                if (waitForPickaxeHook
                        && waitedTicks >= HOOK_SESSION_ABSOLUTE_RESTORE_TICKS
                        && HookGunItem.hasActiveGrappleHook(shooter.level(), shooter)) {
                    HookGunItem.returnActiveHooks(shooter.level(), shooter, true);
                }

                scheduleHookSessionMonitor(shooter, hand, waitForPickaxeHook, completion, waitedTicks + 1);
            }
        };
    }

    private static void completeAndRestoreHookSession(
            LivingEntity shooter,
            InteractionHand hand,
            @Nullable Consumer<ItemStack> completion
    ) {
        ItemStack hookGun = shooter.getItemInHand(hand);
        if (completion != null && hookGun.getItem() instanceof HookGunItem) {
            ItemStack updatedBound = HookGunItem.getBoundItem(hookGun);
            if (!updatedBound.isEmpty()) {
                completion.accept(updatedBound);
            }
        }

        restoreHookSession(shooter);
    }

    private static void beginHookSession(LivingEntity entity, boolean saveMainHand, boolean saveOffhand) {
        CompoundTag data = entity.getPersistentData();
        if (!data.getBoolean(KEY_SESSION_ACTIVE)) {
            data.putBoolean(KEY_SESSION_ACTIVE, true);
            data.putLong(KEY_SESSION_STARTED_AT, entity.level().getGameTime());
            if (entity instanceof Mob mob) {
                mob.getNavigation().stop();
            }
            if (saveMainHand) {
                data.putBoolean(KEY_SAVED_MAINHAND, true);
                saveHand(entity, InteractionHand.MAIN_HAND, KEY_ORIGINAL_MAINHAND);
            }
            if (saveOffhand) {
                data.putBoolean(KEY_SAVED_OFFHAND, true);
                saveHand(entity, InteractionHand.OFF_HAND, KEY_ORIGINAL_OFFHAND);
            }
        }
    }

    private static void cleanupFinishedSession(LivingEntity entity) {
        long startedAt = entity.getPersistentData().getLong(KEY_SESSION_STARTED_AT);
        if (!isHookSessionActive(entity)) {
            return;
        }

        long age = entity.level().getGameTime() - startedAt;
        boolean hasActiveHook = HookGunItem.hasActiveHook(entity.level(), entity);

        if (HookGunItem.hasActiveGrappleHook(entity.level(), entity)
                && !HookGunItem.hasAttachedGrappleHook(entity.level(), entity)
                && age > PICKAXE_HOOK_ATTACH_TIMEOUT_TICKS) {
            HookGunItem.returnActiveHooks(entity.level(), entity, true);
            return;
        }

        if (!hasActiveHook
                && age > HOOK_SESSION_RESTORE_WITHOUT_HOOK_TICKS
                && entity.tickCount % 20 == 0) {
            restoreHookSession(entity);
        }
    }

    private static boolean isHookSessionActive(LivingEntity entity) {
        return entity.getPersistentData().getBoolean(KEY_SESSION_ACTIVE);
    }

    private static void restoreHookSession(LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        if (!data.getBoolean(KEY_SESSION_ACTIVE)) {
            return;
        }

        if (data.getBoolean(KEY_SAVED_MAINHAND)) {
            ItemStack stack = data.contains(KEY_ORIGINAL_MAINHAND, Tag.TAG_COMPOUND)
                    ? ItemStack.of(data.getCompound(KEY_ORIGINAL_MAINHAND))
                    : ItemStack.EMPTY;
            entity.setItemInHand(InteractionHand.MAIN_HAND, stack);
        }
        if (data.getBoolean(KEY_SAVED_OFFHAND)) {
            ItemStack stack = data.contains(KEY_ORIGINAL_OFFHAND, Tag.TAG_COMPOUND)
                    ? ItemStack.of(data.getCompound(KEY_ORIGINAL_OFFHAND))
                    : ItemStack.EMPTY;
            entity.setItemInHand(InteractionHand.OFF_HAND, stack);
        }

        data.remove(KEY_SESSION_ACTIVE);
        data.remove(KEY_SESSION_STARTED_AT);
        data.remove(KEY_ORIGINAL_MAINHAND);
        data.remove(KEY_ORIGINAL_OFFHAND);
        data.remove(KEY_SAVED_MAINHAND);
        data.remove(KEY_SAVED_OFFHAND);
    }

    private static InteractionHand getHookSessionHand(LivingEntity entity) {
        if (entity.getMainHandItem().getItem() instanceof HookGunItem) {
            return InteractionHand.MAIN_HAND;
        }
        return InteractionHand.OFF_HAND;
    }

    private static void saveHand(LivingEntity entity, InteractionHand hand, String key) {
        CompoundTag data = entity.getPersistentData();
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.isEmpty()) {
            data.remove(key);
            return;
        }

        CompoundTag stackTag = new CompoundTag();
        stack.save(stackTag);
        data.put(key, stackTag);
    }

    private static boolean shouldPlayHookGunAnimationForHand(LivingEntity entity, InteractionHand hand, ItemStack boundItem) {
        ItemStack previousBoundItem = getLastHookBoundItem(entity, hand);
        boolean shouldPlay = isConsumableHookItem(entity, boundItem)
                || !isSameHookGunAnimationItem(previousBoundItem, boundItem);
        rememberLastHookBoundItem(entity, hand, boundItem);
        return shouldPlay;
    }

    private static ItemStack getLastHookBoundItem(LivingEntity entity, InteractionHand hand) {
        CompoundTag data = entity.getPersistentData();
        String key = getLastHookBoundItemKey(hand);
        if (!data.contains(key, Tag.TAG_COMPOUND)) {
            return ItemStack.EMPTY;
        }

        return ItemStack.of(data.getCompound(key));
    }

    private static void rememberLastHookBoundItem(LivingEntity entity, InteractionHand hand, ItemStack boundItem) {
        CompoundTag data = entity.getPersistentData();
        String key = getLastHookBoundItemKey(hand);
        if (boundItem.isEmpty()) {
            data.remove(key);
            return;
        }

        ItemStack stored = boundItem.copy();
        stored.setCount(1);
        data.put(key, stored.save(new CompoundTag()));
    }

    private static String getLastHookBoundItemKey(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND
                ? KEY_LAST_MAINHAND_HOOK_BOUND_ITEM
                : KEY_LAST_OFFHAND_HOOK_BOUND_ITEM;
    }

    private static boolean isSameHookGunAnimationItem(ItemStack previousBoundItem, ItemStack boundItem) {
        if (previousBoundItem.isEmpty() || boundItem.isEmpty()) {
            return previousBoundItem.isEmpty() && boundItem.isEmpty();
        }

        if (previousBoundItem.getItem() instanceof BucketItem && boundItem.getItem() instanceof BucketItem) {
            return true;
        }

        return ItemStack.isSameItemSameTags(previousBoundItem, boundItem);
    }

    private static boolean isConsumableHookItem(LivingEntity entity, ItemStack boundItem) {
        return !boundItem.isEmpty()
                && (boundItem.getFoodProperties(entity) != null
                || boundItem.getItem() instanceof ThrowablePotionItem
                || !PotionUtils.getMobEffects(boundItem).isEmpty());
    }

    private static void playHookGunAnimation(LivingEntity entity) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch != null && !entity.level().isClientSide()) {
            patch.playAnimationSynchronized(AnimsPugilistSteve.HOOK_GUN, 0.0F);
        }
    }

    private static void aimAt(LivingEntity entity, Vec3 target) {
        Vec3 delta = target.subtract(entity.getEyePosition());
        double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);
        if (horizontal <= 1.0E-7D && Math.abs(delta.y) <= 1.0E-7D) {
            return;
        }

        float yaw = (float) (Mth.atan2(delta.z, delta.x) * Mth.RAD_TO_DEG) - 90.0F;
        float pitch = (float) (-(Mth.atan2(delta.y, horizontal) * Mth.RAD_TO_DEG));
        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);
        entity.yRotO = yaw;
        entity.xRotO = pitch;
        entity.yBodyRotO = yaw;
        entity.yHeadRotO = yaw;
    }

    private static void setCooldown(LivingEntity entity, String key, int minTicks, int randomTicks) {
        int extra = randomTicks <= 0 ? 0 : entity.getRandom().nextInt(randomTicks + 1);
        entity.getPersistentData().putLong(key, entity.level().getGameTime() + minTicks + extra);
    }

    private static void syncAlexAndJevTarget(@Nullable AlexEntity alex, @Nullable JevEntity jev) {
        if (alex == null || jev == null || !alex.isAlive() || !jev.isAlive()) {
            return;
        }

        LivingEntity alexTarget = alex.getTarget();
        LivingEntity jevTarget = jev.getTarget();
        if (alexTarget != null && alexTarget.isAlive() && !alex.isAlliedTo(alexTarget)) {
            if (jevTarget == null || !jevTarget.isAlive()) {
                jev.setTarget(alexTarget);
            }
        } else if (jevTarget != null && jevTarget.isAlive() && !jev.isAlliedTo(jevTarget)) {
            alex.setTarget(jevTarget);
        }
    }

    private static void moveJevAroundPartner(JevEntity jev, @Nullable AlexEntity alex) {
        if (alex == null
                || !alex.isAlive()
                || isHookSessionActive(jev)
                || HookGunItem.hasActiveHook(jev.level(), jev)
                || jev.tickCount % 45 != 0
                || jev.getNavigation().isInProgress()) {
            return;
        }

        Random random = new Random();
        double angle = random.nextDouble() * Math.PI * 2.0D;
        double radius = 4.0D + random.nextDouble() * 5.0D;
        jev.getNavigation().moveTo(
                alex.getX() + Math.cos(angle) * radius,
                alex.getY(),
                alex.getZ() + Math.sin(angle) * radius,
                1.25D
        );
    }

    @Nullable
    private static Vec3 findHookAnchor(LivingEntity shooter, @Nullable LivingEntity target, boolean escape) {
        Vec3 farAnchor = findHookAnchor(shooter, target, escape, HOOK_SEARCH_MIN_HORIZONTAL_DISTANCE_SQR);
        if (farAnchor != null) {
            return farAnchor;
        }

        return findHookAnchor(shooter, target, escape, HOOK_SEARCH_FALLBACK_MIN_HORIZONTAL_DISTANCE_SQR);
    }

    @Nullable
    private static Vec3 findHookAnchor(LivingEntity shooter, @Nullable LivingEntity target, boolean escape, int minHorizontalDistanceSqr) {
        Level level = shooter.level();
        BlockPos origin = shooter.blockPosition();
        Vec3 targetDirection = Vec3.ZERO;
        if (target != null) {
            Vec3 horizontal = target.position().subtract(shooter.position());
            horizontal = new Vec3(horizontal.x, 0.0D, horizontal.z);
            if (horizontal.lengthSqr() > 1.0E-6D) {
                targetDirection = horizontal.normalize();
            }
        }

        Vec3 bestAnchor = null;
        double bestScore = -Double.MAX_VALUE;
        int radiusSqr = HOOK_SEARCH_RADIUS * HOOK_SEARCH_RADIUS;
        for (int dy = -2; dy <= 12; dy++) {
            for (int dx = -HOOK_SEARCH_RADIUS; dx <= HOOK_SEARCH_RADIUS; dx++) {
                for (int dz = -HOOK_SEARCH_RADIUS; dz <= HOOK_SEARCH_RADIUS; dz++) {
                    int distSqr = dx * dx + dy * dy + dz * dz;
                    int horizontalDistSqr = dx * dx + dz * dz;
                    if (distSqr < 9 || distSqr > radiusSqr) {
                        continue;
                    }
                    if (horizontalDistSqr < minHorizontalDistanceSqr) {
                        continue;
                    }

                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    Vec3 anchor = getHookAnchorAimPosition(level, pos);
                    if (!isHookAnchorBlock(level, pos, state) || !hasHookLine(level, shooter, pos, anchor)) {
                        continue;
                    }

                    double horizontalDistance = Math.sqrt(horizontalDistSqr);
                    double score = 350.0D - Math.abs(horizontalDistance - HOOK_SEARCH_IDEAL_DISTANCE) * 12.0D;
                    score += Math.sqrt(distSqr) * 1.5D;
                    if (state.is(BlockTags.LEAVES)) {
                        score += 1000.0D;
                    }
                    if (dy > 0) {
                        score += (double) dy * 8.0D;
                    } else if (dy < 0) {
                        score += (double) dy * 8.0D;
                    }
                    if (targetDirection != Vec3.ZERO) {
                        Vec3 toAnchor = new Vec3(dx, 0.0D, dz);
                        if (toAnchor.lengthSqr() > 1.0E-6D) {
                            double dot = toAnchor.normalize().dot(targetDirection);
                            score += escape ? -dot * 100.0D : (0.35D - Math.min(dot, 0.35D)) * 35.0D;
                        }
                    }
                    if (score > bestScore) {
                        bestScore = score;
                        bestAnchor = anchor;
                    }
                }
            }
        }

        return bestAnchor;
    }

    private static boolean isHookAnchorBlock(Level level, BlockPos pos, BlockState state) {
        return state.is(BlockTags.LEAVES) || !state.getCollisionShape(level, pos).isEmpty();
    }

    @Nullable
    private static Vec3 findNearbyGroundHookAnchor(LivingEntity shooter, @Nullable LivingEntity awayFrom) {
        Level level = shooter.level();
        BlockPos origin = shooter.blockPosition();
        Vec3 awayDirection = Vec3.ZERO;
        if (awayFrom != null) {
            Vec3 away = shooter.position().subtract(awayFrom.position());
            away = new Vec3(away.x, 0.0D, away.z);
            if (away.lengthSqr() > 1.0E-6D) {
                awayDirection = away.normalize();
            }
        }

        Vec3 bestAnchor = null;
        double bestScore = -Double.MAX_VALUE;
        for (int dx = -28; dx <= 28; dx++) {
            for (int dz = -28; dz <= 28; dz++) {
                int distSqr = dx * dx + dz * dz;
                if (distSqr < GROUND_HOOK_MIN_HORIZONTAL_DISTANCE_SQR
                        || distSqr > GROUND_HOOK_MAX_HORIZONTAL_DISTANCE_SQR) {
                    continue;
                }

                for (int dy = 1; dy >= -3; dy--) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    Vec3 anchor = getGroundHookAnchorAimPosition(pos);
                    if (state.getCollisionShape(level, pos).isEmpty()
                            || !level.getBlockState(pos.above()).canBeReplaced()
                            || !hasHookLine(level, shooter, pos, anchor)) {
                        continue;
                    }

                    double horizontalDistance = Math.sqrt(distSqr);
                    double score = 320.0D - Math.abs(horizontalDistance - GROUND_HOOK_IDEAL_DISTANCE) * 11.0D;
                    if (awayDirection != Vec3.ZERO) {
                        Vec3 toAnchor = new Vec3(dx, 0.0D, dz);
                        if (toAnchor.lengthSqr() > 1.0E-6D) {
                            score += toAnchor.normalize().dot(awayDirection) * 120.0D;
                        }
                    }
                    if (dy <= 0) {
                        score += 10.0D;
                    }

                    if (score > bestScore) {
                        bestScore = score;
                        bestAnchor = anchor;
                    }
                    break;
                }
            }
        }

        return bestAnchor;
    }

    private static Vec3 getHookAnchorAimPosition(Level level, BlockPos pos) {
        if (level.getBlockState(pos.above()).canBeReplaced()) {
            return getGroundHookAnchorAimPosition(pos);
        }

        return Vec3.atCenterOf(pos);
    }

    private static Vec3 getGroundHookAnchorAimPosition(BlockPos pos) {
        return new Vec3(pos.getX() + 0.5D, pos.getY() + 0.95D, pos.getZ() + 0.5D);
    }

    private static boolean hasHookLine(Level level, LivingEntity shooter, BlockPos pos, Vec3 anchor) {
        BlockHitResult hit = level.clip(new ClipContext(
                shooter.getEyePosition(),
                anchor,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                shooter
        ));
        return hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(pos);
    }

    @Nullable
    private static BlockPos findVisibleSaplingForBoneMeal(JevEntity jev) {
        Level level = jev.level();
        BlockPos origin = jev.blockPosition();
        BlockPos best = null;
        double bestScore = Double.MAX_VALUE;
        int radius = 18;

        for (int dy = -3; dy <= 5; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx * dx + dz * dz > radius * radius) {
                        continue;
                    }

                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (!level.getBlockState(pos).is(BlockTags.SAPLINGS)) {
                        continue;
                    }

                    Vec3 aim = Vec3.atCenterOf(pos);
                    if (!hasOutlineHookLine(level, jev, pos, aim)) {
                        continue;
                    }

                    double score = jev.distanceToSqr(aim);
                    if (score < bestScore) {
                        bestScore = score;
                        best = pos;
                    }
                }
            }
        }

        return best;
    }

    private static boolean hasOutlineHookLine(Level level, LivingEntity shooter, BlockPos pos, Vec3 anchor) {
        BlockHitResult hit = level.clip(new ClipContext(
                shooter.getEyePosition(),
                anchor,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                shooter
        ));
        return hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(pos);
    }

    @Nullable
    private static BlockPos findPlacementSupportBlock(Level level, BlockPos center) {
        if (canPlaceAt(level, center) && isSolidSupport(level, center.below())) {
            return center.below();
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos side = center.relative(direction);
            if (canPlaceAt(level, side) && isSolidSupport(level, side.below())) {
                return side.below();
            }
        }

        return findSupportBlockAround(level, center, 3);
    }

    @Nullable
    private static BlockPos findSupportBlockAround(Level level, BlockPos center, int radius) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos place = center.offset(dx, dy, dz);
                    if (canPlaceAt(level, place) && isSolidSupport(level, place.below())) {
                        return place.below();
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    private static BlockPos findSupportBlockAround(Level level, BlockPos center, int radius, ItemStack blockStack) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos place = center.offset(dx, dy, dz);
                    BlockPos support = place.below();
                    if (canPlaceBlockOnSupport(level, support, blockStack)) {
                        return support;
                    }
                }
            }
        }
        return null;
    }

    private static boolean canPlaceAt(Level level, BlockPos pos) {
        return level.getBlockState(pos).canBeReplaced() && level.getFluidState(pos).isEmpty();
    }

    private static boolean isSolidSupport(Level level, BlockPos pos) {
        return !level.getBlockState(pos).getCollisionShape(level, pos).isEmpty();
    }

    private static boolean canPlaceBlockOnSupport(Level level, BlockPos support, ItemStack blockStack) {
        if (!(blockStack.getItem() instanceof BlockItem blockItem) || !isSolidSupport(level, support)) {
            return false;
        }

        BlockPos place = support.above();
        return canPlaceAt(level, place)
                && blockItem.getBlock().defaultBlockState().canSurvive(level, place);
    }

    private static ItemStack oneOf(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1);
        return copy;
    }

    private static boolean isPositiveFoodStack(ItemStack stack, LivingEntity eater) {
        return !stack.isEmpty()
                && stack.getFoodProperties(eater) != null
                && !stack.is(Items.POISONOUS_POTATO)
                && !stack.is(Items.PUFFERFISH);
    }

    private static boolean isPositivePotionStack(ItemStack stack) {
        return stack.getItem() instanceof ThrowablePotionItem
                && !PotionUtils.getMobEffects(stack).isEmpty()
                && PotionUtils.getMobEffects(stack).stream().allMatch(effect -> effect.getEffect().isBeneficial());
    }

    private static boolean isNegativePotionStack(ItemStack stack) {
        return stack.getItem() instanceof ThrowablePotionItem
                && PotionUtils.getMobEffects(stack).stream().anyMatch(effect -> !effect.getEffect().isBeneficial());
    }

    private static boolean isEnemyHarassmentStack(ItemStack stack) {
        return isNegativePotionStack(stack)
                || stack.is(Items.POISONOUS_POTATO)
                || stack.is(Items.PUFFERFISH)
                || stack.is(Items.FLINT_AND_STEEL)
                || stack.is(Items.FIRE_CHARGE);
    }

    private static BlockPos getAlexCoverBlockCenter(AlexEntity alex, LivingEntity enemy) {
        Vec3 toEnemy = enemy.position().subtract(alex.position());
        Vec3 horizontal = new Vec3(toEnemy.x, 0.0D, toEnemy.z);
        if (horizontal.lengthSqr() < 1.0E-6D) {
            return alex.blockPosition();
        }

        Vec3 direction = horizontal.normalize();
        return BlockPos.containing(
                alex.getX() + direction.x * 2.0D,
                alex.getY(),
                alex.getZ() + direction.z * 2.0D
        );
    }

}
