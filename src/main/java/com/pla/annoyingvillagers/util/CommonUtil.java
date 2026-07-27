package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.item.FlankerHookedSwordItem;
import com.pla.annoyingvillagers.item.HookedDiamondSwordItem;
import com.pla.annoyingvillagers.item.HookedGoldenSwordItem;
import com.pla.annoyingvillagers.item.HookedIronSwordItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonUtil {
    public static boolean isAvDamageableEfnWeaponsMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AngrySteveEntity
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    public static boolean isAvRunawayJudgementCutEndMob(Entity livingEntity) {
        return livingEntity instanceof BlueDemonEntity
                || livingEntity instanceof AVNpc
                || livingEntity instanceof HerobrineMob
                || livingEntity instanceof HerobrineGregEntity
                || livingEntity instanceof LowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity;
    }

    public static void forceRotate(LivingEntity entity, LivingEntity lookAtEntity) {
        if (entity == null || lookAtEntity == null) {
            return;
        }

        forceRotate(entity, lookAtEntity.getEyePosition());
    }

    public static void forceRotate(LivingEntity entity, BlockPos lookAtPos) {
        if (entity == null || lookAtPos == null) {
            return;
        }

        forceRotate(entity, Vec3.atCenterOf(lookAtPos));
    }

    public static void forceRotate(LivingEntity entity, Vec3 lookAtPos) {
        if (entity == null || lookAtPos == null) {
            return;
        }

        Vec3 eyePos = entity.getEyePosition();

        double dx = lookAtPos.x - eyePos.x;
        double dy = lookAtPos.y - eyePos.y;
        double dz = lookAtPos.z - eyePos.z;

        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);

        if (horizontalDistance < 1.0E-7D) {
            return;
        }

        float yaw = (float) (Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
        float pitch = (float) -(Mth.atan2(dy, horizontalDistance) * Mth.RAD_TO_DEG);

        yaw = Mth.wrapDegrees(yaw);
        pitch = Mth.clamp(Mth.wrapDegrees(pitch), -90.0F, 90.0F);

        entity.setYRot(yaw);
        entity.setXRot(pitch);

        entity.yRotO = yaw;
        entity.xRotO = pitch;

        entity.setYHeadRot(yaw);
        entity.yHeadRotO = yaw;

        entity.yBodyRot = yaw;
        entity.yBodyRotO = yaw;
    }

    public static void pullEntityTowardCaster(LivingEntity target, LivingEntity caster) {
        pullEntityTowardCaster(target, caster, 0.22D, 0.04D, true);
    }

    public static void pullEntityTowardCaster(
            LivingEntity target,
            LivingEntity caster,
            double strength,
            double yBoost,
            boolean forceLookAtCaster
    ) {
        if (target == null || caster == null) {
            return;
        }

        if (!target.isAlive() || !caster.isAlive()) {
            return;
        }

        if (forceLookAtCaster) {
            forceRotate(target, caster);
        }

        pullEntityTowardPosition(target, caster.position(), strength, yBoost);
    }

    public static void pullEntityTowardPosition(
            Entity target,
            Vec3 targetPos,
            double strength,
            double yBoost
    ) {
        if (target == null || targetPos == null) {
            return;
        }

        Vec3 direction = targetPos.subtract(target.position());
        applyHorizontalDirectionalMotion(target, direction, strength, yBoost);
    }

    public static void pushEntityFromCaster(LivingEntity target, LivingEntity caster) {
        forceRotate(target, caster);
        pushEntityFromCaster(target, caster, 0.35D, 0.08D);
    }

    public static void pushEntityFromCaster(
            Entity target,
            Entity caster,
            double strength,
            double yBoost
    ) {
        if (target == null || caster == null) {
            return;
        }

        pushEntityFromPosition(target, caster.position(), strength, yBoost);
    }

    public static void pushEntityFromPosition(
            Entity target,
            Vec3 sourcePos,
            double strength,
            double yBoost
    ) {
        if (target == null || sourcePos == null) {
            return;
        }

        Vec3 direction = target.position().subtract(sourcePos);

        if (direction.horizontalDistanceSqr() < 1.0E-7D) {
            direction = target.getLookAngle();
        }

        applyHorizontalDirectionalMotion(target, direction, strength, yBoost);
    }

    private static void applyHorizontalDirectionalMotion(
            Entity entity,
            Vec3 direction,
            double strength,
            double yBoost
    ) {
        if (entity == null || direction == null) {
            return;
        }

        if (strength <= 0.0D) {
            return;
        }

        Vec3 horizontal = new Vec3(direction.x, 0.0D, direction.z);

        if (horizontal.lengthSqr() < 1.0E-7D) {
            return;
        }

        Vec3 motion = horizontal.normalize().scale(strength);

        entity.setDeltaMovement(
                entity.getDeltaMovement().add(
                        motion.x,
                        yBoost,
                        motion.z
                )
        );

        entity.hasImpulse = true;
        entity.hurtMarked = true;
    }

    private static boolean matchesItemEntry(ItemStack stack, String entry) {
        if (stack.isEmpty() || entry == null || entry.isBlank()) {
            return false;
        }

        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.getItem());

        if (itemId == null) {
            return false;
        }

        if (entry.startsWith("#")) {
            ResourceLocation tagId = ResourceLocation.tryParse(entry.substring(1));

            if (tagId == null) {
                return false;
            }

            return stack.is(TagKey.create(Registries.ITEM, tagId));
        }

        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            return itemId.getNamespace().equals(namespace);
        }
        if (!entry.contains(":")) {
            return itemId.getNamespace().equals(entry);
        }
        return itemId.toString().equals(entry);
    }

    private static boolean matchesEntityEntry(LivingEntity entity, String entry) {
        if (entry == null || entry.isBlank()) {
            return false;
        }

        EntityType<?> type = entity.getType();
        ResourceLocation typeId = ForgeRegistries.ENTITY_TYPES.getKey(type);

        if (typeId == null) {
            return false;
        }

        if (entry.startsWith("#")) {
            ResourceLocation tagId = ResourceLocation.tryParse(entry.substring(1));

            if (tagId == null) {
                return false;
            }

            return type.is(TagKey.create(Registries.ENTITY_TYPE, tagId));
        }

        if (entry.endsWith(":*")) {
            String namespace = entry.substring(0, entry.length() - 2);
            return typeId.getNamespace().equals(namespace);
        }

        if (!entry.contains(":")) {
            return typeId.getNamespace().equals(entry);
        }

        return typeId.toString().equals(entry);
    }

    public static boolean isPullableWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();

        return item instanceof SwordItem
                || item instanceof DiggerItem
                || item instanceof TridentItem;
    }

    public static boolean isBlacklistedWeapon(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        for (String entry : AnnoyingVillagersConfig.WEAPON_DISARMS_BLACKLIST.get()) {
            if (matchesItemEntry(stack, entry)) {
                return true;
            }
        }

        return false;
    }

    public static boolean entityCanBeDisarmed(LivingEntity entity) {
        List<? extends String> entries =
                AnnoyingVillagersConfig.WEAPON_DISARMS_AFFECTED_ENTITY_TYPES.get();

        if (entries.isEmpty()) {
            return false;
        }

        for (String entry : entries) {
            if (matchesEntityEntry(entity, entry)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isHookSword(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();
        return item instanceof HookedIronSwordItem
                || item instanceof HookedGoldenSwordItem
                || item instanceof HookedDiamondSwordItem
                || item instanceof FlankerHookedSwordItem;
    }

    public static boolean isHookSwordClashAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation) {
        return dynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO1
                || dynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO2
                || dynamicAnimation == AnimsEpicFight.HOOK_DANCING_EDGE
                || dynamicAnimation == AnimsWom.HOOK_HERRSCHER_UP;
    }

    public static void applyHookClashDisarmLogic(
            LivingEntity livingEntity,
            LivingEntity attackerLivingEntity,
            ServerLevel serverLevel,
            AssetAccessor<? extends StaticAnimation> knockdownAnimation,
            HookDisarmLaunch launch
    ) {
        if (attackerLivingEntity == null || !attackerLivingEntity.isAlive()) {
            return;
        }

        CommonUtil.forceRotate(attackerLivingEntity, livingEntity);
        playForcedKnockdown(attackerLivingEntity, knockdownAnimation);
        tryDisarmAndLaunchWeapon(serverLevel, livingEntity, attackerLivingEntity, launch);
    }

    public static void applyHookClashDisarmLogic(
            LivingEntity livingEntity,
            DamageSource damageSource,
            ServerLevel serverLevel,
            AssetAccessor<? extends StaticAnimation> knockdownAnimation,
            HookDisarmLaunch launch
    ) {
        LivingEntity target = getClashLivingTarget(damageSource, livingEntity);
        applyHookClashDisarmLogic(livingEntity, target, serverLevel, knockdownAnimation, launch);
    }

    private static LivingEntity getClashLivingTarget(DamageSource damageSource, LivingEntity player) {
        Entity entity = damageSource.getEntity();

        if (entity == player || !(entity instanceof LivingEntity)) {
            entity = damageSource.getDirectEntity();
        }

        if (entity instanceof LivingEntity livingEntity && livingEntity != player) {
            return livingEntity;
        }

        return null;
    }

    private static void playForcedKnockdown(
            LivingEntity target,
            AssetAccessor<? extends StaticAnimation> knockdownAnimation
    ) {
        LivingEntityPatch<?> targetPatch =
                EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (targetPatch != null) {
            targetPatch.playAnimationSynchronized(knockdownAnimation, 0.0F);
        }
    }

    public static void fallBackOnBlackListWeapon(
            LivingEntity owner,
            Entity source,
            ItemStack blacklistedStack
    ) {

    }

    private static void tryDisarmAndLaunchWeapon(
            ServerLevel serverLevel,
            LivingEntity livingEntity,
            LivingEntity target,
            HookDisarmLaunch launch
    ) {
        if (!entityCanBeDisarmed(target)) {
            return;
        }

        List<InteractionHand> candidateHands = new ArrayList<>(2);
        ItemStack blacklistedStack = ItemStack.EMPTY;

        ItemStack mainHand = target.getMainHandItem();
        ItemStack offHand = target.getOffhandItem();
        if (isPullableWeapon(mainHand)) {
            if (isBlacklistedWeapon(mainHand)) {
                blacklistedStack = mainHand;
            } else {
                candidateHands.add(InteractionHand.MAIN_HAND);
            }
        }
        if (isPullableWeapon(offHand)) {
            if (isBlacklistedWeapon(offHand)) {
                blacklistedStack = offHand;
            } else {
                candidateHands.add(InteractionHand.OFF_HAND);
            }
        }
        if (candidateHands.isEmpty()) {
            if (!blacklistedStack.isEmpty()) {
                fallBackOnBlackListWeapon(livingEntity, target, blacklistedStack);
            }

            return;
        }
        InteractionHand chosenHand = candidateHands.get(
                serverLevel.random.nextInt(candidateHands.size())
        );
        ItemStack chosenStack = target.getItemInHand(chosenHand);
        if (chosenStack.isEmpty()) {
            return;
        }
        ItemStack droppedStack = chosenStack.copy();
        clearCachedNpcWeapon(target, chosenHand);
        target.setItemInHand(chosenHand, ItemStack.EMPTY);
        if (chosenHand == InteractionHand.MAIN_HAND) {
            tryMoveOffhandWeaponToMainhand(target);
        }
        spawnDisarmedItem(serverLevel, livingEntity, target, droppedStack, launch);
    }

    public static int getRandomDamage(ItemStack itemStack) {
        int maxDamage = itemStack.getMaxDamage();
        int min = maxDamage / 3;
        int max = maxDamage * 3 / 4;
        return new Random().nextInt(max - min + 1) + min;
    }

    private static void tryMoveOffhandWeaponToMainhand(LivingEntity target) {
        ItemStack offhandStack = target.getOffhandItem();
        if (offhandStack.isEmpty()) {
            return;
        }
        if (!isPullableWeapon(offhandStack)) {
            return;
        }
        ItemStack movedStack = offhandStack.copy();
        target.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        target.setItemInHand(InteractionHand.MAIN_HAND, movedStack.copy());
        if (target instanceof AVNpc avNpc) {
            avNpc.setOffWeaponItem(ItemStack.EMPTY);
            avNpc.setMainWeaponItem(movedStack.copy());
            avNpc.setMainWeaponDisarmed(false);
        }
        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.disarmMainWeapon(target, movedStack);
        }
    }

    private static void clearCachedNpcWeapon(LivingEntity target, InteractionHand hand) {
        if (target instanceof AVNpc avNpc) {
            if (hand == InteractionHand.MAIN_HAND) {
                avNpc.setMainWeaponItem(ItemStack.EMPTY);
                avNpc.setMainWeaponDisarmed(true);
            } else {
                avNpc.setOffWeaponItem(ItemStack.EMPTY);
            }
        }

        if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.clearCachedWeapon(target, hand);
        }
    }

    private static void spawnDisarmedItem(
            ServerLevel serverLevel,
            LivingEntity livingEntity,
            LivingEntity target,
            ItemStack stack,
            HookDisarmLaunch launch
    ) {
        if (stack.isEmpty()) {
            return;
        }

        Vec3 spawnPos;
        spawnPos = EpicfightUtil.getJointWithTranslation(
                target, new Vec3f(0,0,0), Armatures.BIPED.get().handR, 0.0F, 0.0F
        );
        if (spawnPos == null) {
            spawnPos = target.getEyePosition().subtract(0.0D, 0.35D, 0.0D);
        }

        Vec3 towardAttacker = livingEntity.position().subtract(target.position());
        towardAttacker = new Vec3(towardAttacker.x, 0.0D, towardAttacker.z);

        if (towardAttacker.lengthSqr() < 1.0E-7D) {
            towardAttacker = target.getLookAngle();
            towardAttacker = new Vec3(towardAttacker.x, 0.0D, towardAttacker.z);
        }

        if (towardAttacker.lengthSqr() < 1.0E-7D) {
            towardAttacker = new Vec3(0.0D, 0.0D, 1.0D);
        }

        towardAttacker = towardAttacker.normalize();

        Vec3 right = new Vec3(-towardAttacker.z, 0.0D, towardAttacker.x).normalize();

        Vec3 motion;
        int dropAfterTicks;

        switch (launch) {
            case RIGHT -> {
                motion = right.scale(0.72D)
                        .add(towardAttacker.scale(0.12D))
                        .add(0.0D, 0.40D, 0.0D);

                dropAfterTicks = 16;
            }

            case LEFT -> {
                motion = right.scale(-0.72D)
                        .add(towardAttacker.scale(0.12D))
                        .add(0.0D, 0.40D, 0.0D);

                dropAfterTicks = 16;
            }

            case BACKWARD -> {
                Vec3 backward = towardAttacker.scale(-1.0D);
                spawnPos = target.getEyePosition().add(0.0D, 0.10D, 0.0D);
                motion = backward.scale(0.85D)
                        .add(0.0D, 0.78D, 0.0D);

                dropAfterTicks = 22;
            }

            default -> {
                motion = new Vec3(0.0D, 0.45D, 0.0D);
                dropAfterTicks = 16;
            }
        }

        ItemProjectile projectile = ItemProjectile.createDisarmLaunch(
                serverLevel,
                livingEntity,
                stack.copy(),
                spawnPos,
                motion,
                dropAfterTicks
        );

        serverLevel.addFreshEntity(projectile);
    }
}
