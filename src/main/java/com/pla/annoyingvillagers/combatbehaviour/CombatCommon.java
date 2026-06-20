package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.compat.EfKick;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.task.MobExecutionTask;
import com.pla.annoyingvillagers.util.BowFunction;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.EscapeUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.util.BehaviorUtils;
import net.shelmarow.combat_evolution.ai.util.CEPatchUtils;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import net.shelmarow.combat_evolution.tickTask.TickTaskManager;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.StunType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CombatCommon {
    private static final int RANDOM_COMBAT_CHAIN_COUNT = 50;
    private static final int MAX_RANDOM_OPENING_STEPS = 2;
    private static final int RANDOM_FOLLOW_UP_STEPS = 3;
    private static final double MAX_PLACE_BLOCK_GROUND_GAP = 2.0D;
    private static final int PLACE_BLOCK_INITIAL_DELAY = 1;
    private static final int PLACE_BLOCK_LAYER_INTERVAL = 3;
    private static final int PLACE_BLOCK_LANE_INTERVAL = 2;
    private static final double NPC_COMBAT_FISHING_ROD_RADIUS = 32.0D;
    private static final double NPC_COMBAT_FISHING_ROD_RADIUS_SQR = NPC_COMBAT_FISHING_ROD_RADIUS * NPC_COMBAT_FISHING_ROD_RADIUS;
    private static final int NPC_COMBAT_FISHING_ROD_MIN_COOLDOWN = 120;
    private static final int NPC_COMBAT_FISHING_ROD_RANDOM_COOLDOWN = 120;
    private static final int NPC_COMBAT_FISHING_ROD_MAX_WAIT_TICKS = 80;
    private static final int NPC_COMBAT_FISHING_ROD_AROUND_SEARCH_RADIUS = 12;
    private static final double NPC_COMBAT_FISHING_ROD_STICK_CHANCE_MIN = 0.30D;
    private static final double NPC_COMBAT_FISHING_ROD_STICK_CHANCE_MAX = 0.50D;
    private static final double NPC_COMBAT_FISHING_ROD_STICK_LOSE_CHANCE = 0.35D;
    private static final int NPC_FISHING_ROD_ACTION_PULL_TARGET = 0;
    private static final int NPC_FISHING_ROD_ACTION_SELF_TO_TARGET = 1;
    private static final int NPC_FISHING_ROD_ACTION_AROUND = 2;
    private static final int NPC_FISHING_ROD_ACTION_JESSICA_PULL_TARGET = 3;
    private static final int NPC_LAVA_BUCKET_MIN_COOLDOWN = 160;
    private static final int NPC_LAVA_BUCKET_RANDOM_COOLDOWN = 140;
    private static final int NPC_LAVA_BUCKET_ACTION_DELAY = 6;
    private static final int NPC_LAVA_BUCKET_PICKUP_DELAY = 40;
    private static final int NPC_LAVA_BUCKET_RESTORE_DELAY = 4;
    private static final int AVNPC_WATER_BUCKET_MIN_COOLDOWN = 220;
    private static final int AVNPC_WATER_BUCKET_RANDOM_COOLDOWN = 180;
    private static final int AVNPC_WATER_BUCKET_ACTION_DELAY = 4;
    private static final int AVNPC_WATER_BUCKET_PICKUP_DELAY = 40;
    private static final int AVNPC_WATER_BUCKET_RESTORE_DELAY = 4;
    private static final String KEY_NPC_COMBAT_FISHING_ROD_ACTIVE = "avNpcCombatFishingRodActive";
    private static final String KEY_NPC_COMBAT_FISHING_ROD_ORIGINAL_OFFHAND = "avNpcCombatFishingRodOriginalOffhand";
    private static final String KEY_NPC_COMBAT_FISHING_ROD_USE_COUNT = "avNpcCombatFishingRodUseCount";
    private static final String KEY_NPC_COMBAT_FISHING_ROD_COOLDOWN_UNTIL = "avNpcCombatFishingRodCooldownUntil";
    private static final String KEY_NPC_COMBAT_FISHING_ROD_STICKY_TARGET_ID = "avNpcCombatFishingRodStickyTargetId";
    private static final String KEY_NPC_LAVA_BUCKET_ORIGINAL_OFFHAND = "avNpcLavaBucketOriginalOffhand";
    private static final String KEY_NPC_LAVA_BUCKET_COOLDOWN_UNTIL = "avNpcLavaBucketCooldownUntil";
    private static final String KEY_AVNPC_WATER_BUCKET_ACTIVE = "avNpcWaterBucketActive";
    private static final String KEY_AVNPC_WATER_BUCKET_ORIGINAL_OFFHAND = "avNpcWaterBucketOriginalOffhand";
    private static final String KEY_AVNPC_WATER_BUCKET_COOLDOWN_UNTIL = "avNpcWaterBucketCooldownUntil";

    public static boolean isHoldingWeapon(LivingEntity entity) {
        CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapability(entity.getItemInHand(InteractionHand.MAIN_HAND));
        return capabilityItem.getWeaponCategory() != CapabilityItem.WeaponCategories.NOT_WEAPON && capabilityItem.getWeaponCategory() != CapabilityItem.WeaponCategories.FIST;
    }

    public static boolean targetIsInRange(LivingEntity attacker, LivingEntity target, double minDist, double maxDist, double maxAngleDegrees) {
        Vec3 targetPos = target.position();
        Vec3 playerPos = attacker.position();

        double distance = playerPos.distanceTo(targetPos);
        if (distance < minDist || distance > maxDist) return false;

        float yaw = target.getYRot();
        double yawRad = Math.toRadians(yaw);
        Vec3 forward = new Vec3(-Math.sin(yawRad), 0, Math.cos(yawRad)).normalize();
        Vec3 toPlayer = playerPos.subtract(targetPos).normalize();

        double dot = forward.dot(toPlayer);
        double angle = Math.toDegrees(Math.acos(dot));

        return angle <= maxAngleDegrees;
    }

    public static boolean canExecute(LivingEntity attacker, LivingEntity victim, LivingEntityPatch<?> attackerEntityPatch, LivingEntityPatch<?> victimEntityPatch) {
        float maxDist = ExecutionHandler.EXECUTION_DISTANCE;
        return attacker.isAlive() && victim.isAlive()
                && AnnoyingVillagersConfig.AV_MOB_CAN_EXECUTE.get()
                && !ExecutionHandler.isExecutingTarget(attacker, victim)
                && ExecutionHandler.isTargetSupported(attackerEntityPatch, victimEntityPatch)
                && isHoldingWeapon(attacker)
                && targetIsInRange(attacker, victim, 0, maxDist, 180);
    }

    @Nullable
    private static ExecutionHandler.ExecutionTransform calculateExecutionPosition(Level level, LivingEntity executor, LivingEntity target, Vec3 offset) {
        float yaw = target.getYRot();
        ExecutionHandler.ExecutionTransform executionTransform = findPosAround(level, executor, target, offset, yaw, 360.0F, 0.5F);
        if (executionTransform == null) {
            Vec3 executorPos = executor.position();
            Vec3 targetPos = target.position();
            Vec3 deltaVec = executorPos.subtract(targetPos);
            float startAngle = (float) (Math.toDegrees(Mth.atan2(deltaVec.z, deltaVec.x)) - (double) 90.0F);
            float allowedY = 0.5F;
            executionTransform = findPosAround(level, executor, target, offset, startAngle, 12.0F, allowedY);
            if (executionTransform == null) {
                allowedY = 0.95F;
                executionTransform = findPosAround(level, executor, target, offset, startAngle, 12.0F, allowedY);
            }
        }

        return executionTransform;
    }

    @Nullable
    private static ExecutionHandler.ExecutionTransform findPosAround(Level level, LivingEntity executor, LivingEntity target, Vec3 offset, float startAngle, float angleStep, float allowedY) {
        for (float angleOffset = 0.0F; angleOffset < 360.0F; angleOffset += angleStep) {
            float yaw = startAngle + angleOffset;
            double rad = Math.toRadians(yaw);
            double forwardX = -Math.sin(rad);
            double forwardZ = Math.cos(rad);
            double rightX = Math.cos(rad);
            double rightZ = Math.sin(rad);
            double offsetX = forwardX * offset.x + rightX * offset.z;
            double offsetY = offset.y;
            double offsetZ = forwardZ * offset.x + rightZ * offset.z;
            Vec3 testPos = target.position().add(offsetX, offsetY, offsetZ);
            Vec3 executionPos = canStandHere(level, testPos, executor, target, allowedY);
            if (executionPos != null) {
                return new ExecutionHandler.ExecutionTransform(executionPos, yaw);
            }
        }

        return null;
    }

    @Nullable
    public static Vec3 canStandHere(Level level, Vec3 pos, LivingEntity executor, LivingEntity target, float allowedY) {
        AABB entityBox = executor.getBoundingBox();
        double width = entityBox.getXsize();
        double height = entityBox.getYsize();

        for (float i = allowedY; i > -allowedY; i -= 0.05F) {
            BlockPos blockPosBelow = BlockPos.containing(pos.x, pos.y + (double) i, pos.z);
            BlockState stateBelow = level.getBlockState(blockPosBelow);
            VoxelShape shapeBelow = stateBelow.getCollisionShape(level, blockPosBelow);
            if (!shapeBelow.isEmpty()) {
                double offsetY = shapeBelow.max(Direction.Axis.Y);
                AABB checkBox = new AABB(pos.x - width / (double) 2.0F, (double) blockPosBelow.getY() + offsetY, pos.z - width / (double) 2.0F, pos.x + width / (double) 2.0F, (double) blockPosBelow.getY() + offsetY + height, pos.z + width / (double) 2.0F);
                Vec3 standPos = new Vec3(pos.x, (double) blockPosBelow.getY() + offsetY, pos.z);
                if (level.noCollision(checkBox) && getEntityInView(executor, new Vec3(standPos.x, executor.getEyePosition().y, standPos.z), target) != null) {
                    return standPos;
                }
            }
        }

        return null;
    }

    private static LivingEntity getEntityInView(LivingEntity executor, Vec3 startPos, Entity target) {
        BlockHitResult blockHit = executor.level().clip(new ClipContext(startPos, target.getEyePosition(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, executor));
        double blockDistanceSqr = blockHit.getType() != HitResult.Type.MISS ? startPos.distanceToSqr(blockHit.getLocation()) : Double.MAX_VALUE;
        double entityDistanceSqr = startPos.distanceToSqr(target.getEyePosition());
        return entityDistanceSqr < blockDistanceSqr && blockDistanceSqr - entityDistanceSqr > target.getBoundingBox().minX ? (LivingEntity) target : null;
    }

    public static boolean canExecute(MobPatch<?> mobPatch) {
        Mob attacker = mobPatch.getOriginal();
        LivingEntity victim = attacker.getTarget();
        if (victim == null || !victim.isAlive()) return false;

        LivingEntityPatch<?> victimEntityPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
        if (victimEntityPatch != null
                && (attacker instanceof PlayerNpcEntity || attacker instanceof AVNpc || attacker instanceof HerobrineMob || attacker instanceof NullSkeletonEntity || attacker instanceof BlueDemonEntity)) {
            AssetAccessor<? extends StaticAnimation> currentAnimation =
                    Objects.requireNonNull(victimEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

            if (ExecutionHandler.isTargetGuardBreak(currentAnimation, victimEntityPatch) && canExecute(attacker, victim, mobPatch, victimEntityPatch)) {
                ExecutionTypeManager.Type executionType = ExecutionHandler.getExecutionType(mobPatch, victimEntityPatch);
                return calculateExecutionPosition(attacker.level(), attacker, victim, executionType.offset()) != null;
            }
        }
        return false;
    }

    public static boolean isTargetKnockedDown(MobPatch<?> mobpatch) {
        LivingEntity victim = mobpatch.getOriginal().getTarget();
        if (victim != null) {
            LivingEntityPatch<?> victimPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
            if (victimPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(victimPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                return dynamicAnimation.get() instanceof KnockdownAnimation;
            }
            return false;
        }
        return false;
    }

    public static boolean canPerformNormalAttackLogic(MobPatch<?> mobpatch) {
        LivingEntity attacker = mobpatch.getOriginal();
        LivingEntity victim = mobpatch.getOriginal().getTarget();
        if (!mobpatch.getEntityState().canBasicAttack()) {
            return false;
        }
        if (attacker instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.isHealing()) {
            return false;
        }
        if (attacker instanceof AVNpc AVNpc && AVNpc.isHealing()) {
            return false;
        }
        if (attacker instanceof AVNpc AVNpc && AVNpc.hasPlaceBlockParryCooldown()) {
            return false;
        }
        if (attacker instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity
                && swordsmanHerobrineEntity.getMainHandItem().getTag() != null
                && swordsmanHerobrineEntity.getMainHandItem().getTag().contains("SnakeAnimation")) {
            return false;
        }
        if (victim != null) {
            if (isTargetKnockedDown(mobpatch) || canExecute(mobpatch) || canEscape(mobpatch)) {
                return false;
            } else {
                return !ExecutionHandler.isExecutingTarget(attacker, victim);
            }
        }
        return false;
    }

    public static boolean canJump(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().onGround() && !mobpatch.getOriginal().isPassenger();
    }

    public static boolean canPerformTridentAttack(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity && blueDemonEntity.level() instanceof ServerLevel serverLevel) {
            List<BlueDemonThrownTridentEntity> tridents = BlueDemonTridentItem.getGroundedOwnerTridents(serverLevel, blueDemonEntity);
            return !tridents.isEmpty();
        }
        return false;
    }

    public static boolean isNotRiding(MobPatch<?> mobpatch) {
        return !mobpatch.getOriginal().isPassenger();
    }

    public static boolean isRiding(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().isPassenger();
    }

    public static boolean hasClearBowShot(MobPatch<?> mobpatch) {
        Mob mob = mobpatch.getOriginal();
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive() && BowFunction.hasClearShot(mob, target);
    }

    public static boolean usesStepMoveset(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal() instanceof AVNpc;
    }

    public static boolean usesRollMoveset(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal() instanceof PlayerNpcEntity
                || mobpatch.getOriginal() instanceof LowHerobrineCloneEntity
                || mobpatch.getOriginal() instanceof LowShadowHerobrineCloneEntity;
    }

    public static boolean canAttackWhileNotHealing(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof AVNpc AVNpc) {
            return !AVNpc.isHealing();
        }
        return mobpatch.getOriginal() instanceof LowShadowHerobrineCloneEntity || mobpatch.getOriginal() instanceof LowHerobrineCloneEntity;
    }

    public static boolean canEscape(MobPatch<?> mobpatch) {
        Mob entity = mobpatch.getOriginal();
        var animationPlayer = mobpatch.getAnimator().getPlayerFor(null);
        if (animationPlayer == null) return false;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
        StaticAnimation currentAnimation = dynamicAnimation != null ? dynamicAnimation.get() : null;
        if (currentAnimation instanceof ExecutionAttackAnimation || currentAnimation instanceof ExecutionHitAnimation) {
            return false;
        }
        if (EscapeUtil.checkEscape(entity)) {
            if (entity instanceof HerobrineMob || entity instanceof BlueDemonEntity) {
                return true;
            } else if (entity instanceof AVNpc avNpc
                    && avNpc.rollsPlaceBlockToParryChance()) {
                return true;
            } else return entity instanceof PlayerNpcEntity playerNpcEntity
                    && new Random().nextDouble() <= playerNpcEntity.getPlaceBlockToParryChance();
        }
        return false;
    }

    public static boolean isWrongWeapon(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        return !canEscape(mobpatch) && entity instanceof LivingEntity livingEntity
                && !(livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SwordItem
                || livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AxeItem
                || livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem);
    }

    public static boolean canBlueDemonPerformHealing(MobPatch<?> mobpatch) {
        if (canExecute(mobpatch)) return false;
        if (mobpatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity) {
            if (blueDemonEntity.getHealingCooldown() > 0) {
                return false;
            }
            return blueDemonEntity.getHealingTick() == 0;
        }
        return false;
    }

    public static boolean canPerformEating(MobPatch<?> mobpatch) {
        if (canExecute(mobpatch)) return false;
        if (!mobpatch.getEntityState().canBasicAttack()) return false;
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            if (playerNpcEntity.getGapCooldown() > 0) {
                return false;
            }
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof AVNpc AVNpc) {
            if (AVNpc.getGapCooldown() > 0) {
                return false;
            }
            return !AVNpc.isHealing();
        }
        return false;
    }

    public static boolean canPerformGuarding(MobPatch<?> mobpatch) {
        if (canEscape(mobpatch)) return false;
        if (!mobpatch.getEntityState().canBasicAttack()) return false;
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return !playerNpcEntity.isHealing();
        }
        if (mobpatch.getOriginal() instanceof AVNpc AVNpc) {
            return !AVNpc.isHealing();
        }
        if (mobpatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity
                && blueDemonEntity.getBbqEntity() != null
                && blueDemonEntity.getTarget() instanceof Mob mob) {
            return !(mob.getTarget() instanceof BbqEntity);
        }
        return false;
    }

    public static boolean isTargetingHerobrineDragon(MobPatch<?> mobpatch) {
        return mobpatch.getOriginal().getTarget() instanceof HerobrineDragonEntity;
    }

    public static boolean canThrowEnderPearl(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal().isPassenger()) return false;

        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            if (playerNpcEntity.isHealing()) {
                return false;
            }
            return playerNpcEntity.getEnderPearlCooldown() == 0;
        }
        if (mobpatch.getOriginal() instanceof AVNpc AVNpc) {
            if (AVNpc.isHealing()) {
                return false;
            }
            return AVNpc.getEnderPearlCooldown() == 0;
        }
        return false;
    }

    public static boolean isGeneral(MobPatch<?> mobpatch) {
        return isGeneralMob(mobpatch.getOriginal());
    }

    public static boolean canUseNpcCombatFishingRod(MobPatch<?> mobpatch) {
        Mob mob = mobpatch.getOriginal();
        LivingEntity target = getNpcCombatFishingRodStickyTarget(mob);
        if (target == null) {
            target = mob.getTarget();
        }
        if (!isNpcCombatFishingRodUser(mobpatch) || target == null || !target.isAlive()) {
            return false;
        }
        if (mob.level().isClientSide || mob.isPassenger()) {
            return false;
        }
        if (mob.distanceToSqr(target) > NPC_COMBAT_FISHING_ROD_RADIUS_SQR) {
            return false;
        }
        if (isStevePhaseOneFishingRodBlocked(mob)) {
            return false;
        }

        return isNpcCombatFishingRodSessionActive(mob)
                || mob.level().getGameTime() >= getPersistentLong(mob, KEY_NPC_COMBAT_FISHING_ROD_COOLDOWN_UNTIL);
    }

    public static boolean canUseNpcCombatFishingRodEscape(MobPatch<?> mobpatch) {
        Mob mob = mobpatch.getOriginal();
        if (!isNpcCombatFishingRodUser(mobpatch) || mob.level().isClientSide || mob.isPassenger()) {
            return false;
        }
        LivingEntity target = mob.getTarget();
        if (target != null && mob.distanceToSqr(target) > NPC_COMBAT_FISHING_ROD_RADIUS_SQR) {
            return false;
        }
        if (isStevePhaseOneFishingRodBlocked(mob)) {
            return false;
        }

        return isNpcCombatFishingRodSessionActive(mob)
                || mob.level().getGameTime() >= getPersistentLong(mob, KEY_NPC_COMBAT_FISHING_ROD_COOLDOWN_UNTIL);
    }

    public static boolean canUseVillagerGeneralLavaBucket(MobPatch<?> mobpatch) {
        Mob mob = mobpatch.getOriginal();
        LivingEntity target = mob.getTarget();
        if (!isGeneral(mobpatch) || target == null || !target.isAlive()) {
            return false;
        }
        if (!(mob.level() instanceof ServerLevel serverLevel)
                || !mob.onGround()
                || mob.isPassenger()
                || isNpcCombatFishingRodSessionActive(mob)) {
            return false;
        }
        if (mob.distanceToSqr(target) > 144.0D) {
            return false;
        }
        if (mob.level().getGameTime() < getPersistentLong(mob, KEY_NPC_LAVA_BUCKET_COOLDOWN_UNTIL)) {
            return false;
        }

        return findLavaPlacement(serverLevel, target) != null;
    }

    public static boolean tryPerformAvNpcWaterBucketSelfExtinguish(AVNpc avNpc) {
        if (!canUseAvNpcWaterBucketSelfExtinguish(avNpc) || !(avNpc.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        LivingEntityPatch<?> entityPatch = avNpc.getLivingEntityPatch();
        if (entityPatch != null) {
            entityPatch.playAnimationSynchronized(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F);
        }

        avNpc.getPersistentData().putBoolean(KEY_AVNPC_WATER_BUCKET_ACTIVE, true);
        setPersistentLong(avNpc, KEY_AVNPC_WATER_BUCKET_COOLDOWN_UNTIL,
                avNpc.level().getGameTime() + AVNPC_WATER_BUCKET_MIN_COOLDOWN + new Random().nextInt(AVNPC_WATER_BUCKET_RANDOM_COOLDOWN + 1));
        equipTemporaryOffhand(avNpc, new ItemStack(Items.WATER_BUCKET), KEY_AVNPC_WATER_BUCKET_ORIGINAL_OFFHAND);
        avNpc.getNavigation().stop();
        avNpc.swing(InteractionHand.OFF_HAND, true);

        new DelayedTask(AVNPC_WATER_BUCKET_ACTION_DELAY) {
            @Override
            public void run() {
                if (!avNpc.isAlive()) {
                    avNpc.getPersistentData().remove(KEY_AVNPC_WATER_BUCKET_ACTIVE);
                    return;
                }
                if (!avNpc.onGround()) {
                    finishAvNpcWaterBucketSelfExtinguish(avNpc);
                    return;
                }

                final BlockPos placement = findSelfWaterPlacement(serverLevel, avNpc);
                if (placement == null) {
                    finishAvNpcWaterBucketSelfExtinguish(avNpc);
                    return;
                }

                avNpc.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                serverLevel.setBlockAndUpdate(placement, Blocks.WATER.defaultBlockState());
                avNpc.clearFire();
                avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.BUCKET));

                new DelayedTask(AVNPC_WATER_BUCKET_PICKUP_DELAY) {
                    @Override
                    public void run() {
                        if (!avNpc.isAlive()) {
                            avNpc.getPersistentData().remove(KEY_AVNPC_WATER_BUCKET_ACTIVE);
                            return;
                        }

                        avNpc.swing(InteractionHand.OFF_HAND, true);
                        BlockState placementState = serverLevel.getBlockState(placement);
                        if (placementState.is(Blocks.WATER)) {
                            avNpc.playSound(SoundEvents.BUCKET_FILL, 1.0F, 1.0F);
                            serverLevel.setBlockAndUpdate(placement, Blocks.AIR.defaultBlockState());
                        } else if (placementState.getBlock() instanceof HerobrineObsidianBlock
                                && placementState.hasProperty(HerobrineObsidianBlock.REPLACE_BY_LIQUID)) {
                            serverLevel.setBlock(
                                    placement,
                                    placementState.setValue(HerobrineObsidianBlock.REPLACE_BY_LIQUID, 0),
                                    3
                            );
                        }
                        avNpc.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.WATER_BUCKET));

                        new DelayedTask(AVNPC_WATER_BUCKET_RESTORE_DELAY) {
                            @Override
                            public void run() {
                                if (avNpc.isAlive()) {
                                    finishAvNpcWaterBucketSelfExtinguish(avNpc);
                                } else {
                                    avNpc.getPersistentData().remove(KEY_AVNPC_WATER_BUCKET_ACTIVE);
                                }
                            }
                        };
                    }
                };
            }
        };

        return true;
    }

    private static boolean canUseAvNpcWaterBucketSelfExtinguish(AVNpc avNpc) {
        if (!avNpc.isAlive()
                || !avNpc.isOnFire()
                || avNpc.level().isClientSide
                || !avNpc.onGround()
                || avNpc.isPassenger()
                || avNpc.isHealing()
                || isNpcCombatFishingRodSessionActive(avNpc)
                || avNpc.getPersistentData().getBoolean(KEY_AVNPC_WATER_BUCKET_ACTIVE)
                || avNpc.level().getGameTime() < getPersistentLong(avNpc, KEY_AVNPC_WATER_BUCKET_COOLDOWN_UNTIL)) {
            return false;
        }

        return avNpc.level() instanceof ServerLevel serverLevel
                && findSelfWaterPlacement(serverLevel, avNpc) != null;
    }

    public static boolean canSwapToBow(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            return playerNpcEntity.isUseBow() && playerNpcEntity.getSwapToBowCooldown() == 0;
        }

        if (mobpatch.getOriginal() instanceof AVNpc AVNpc) {
            if ((AVNpc instanceof SteveEntity || AVNpc instanceof AngrySteveEntity
                    || AVNpc instanceof AlexEntity || AVNpc instanceof ChrisEntity)) {
                if (target instanceof HerobrineMob) {
                    return false;
                }
                ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
                if (key.getNamespace().equals("torchesbecomesunlight")
                        && (key.getPath().equals("gun_knight_patriot") || key.getPath().equals("turret"))) {
                    return false;
                }
                if (key.getNamespace().equals("nightfall_invade")
                        && (key.getPath().equals("arterius"))) {
                    return false;
                }
                if (AVNpc instanceof SteveEntity steveEntity) {
                    if (steveEntity.getItemInHand(InteractionHand.OFF_HAND).getItem().equals(Items.TOTEM_OF_UNDYING))
                        return false;
                }
            }

            return AVNpc.isUseBow() && AVNpc.getSwapToBowCooldown() == 0;
        }

        return false;
    }

    public static boolean canSwitchWeapon(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (mobpatch.getOriginal() instanceof SteveEntity steveEntity) {
            return steveEntity.getBlockDamage() == null && steveEntity.getSwapWeaponCooldown() == 0 || (steveEntity.getState() == 0 && steveEntity.getHealth() <= 20 && !steveEntity.getMainHandItem().getItem().equals(Items.DIAMOND_SWORD));
        } else if (mobpatch.getOriginal() instanceof HerobrineMob herobrineMob) {
            return (herobrineMob instanceof ArmoredHerobrineEntity || herobrineMob instanceof ShadowHerobrineEntity) && herobrineMob.getSwapWeaponCooldown() == 0;
        } else if (mobpatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity) {
            return blueDemonEntity.getState() == 3 && blueDemonEntity.getSwapWeaponCooldown() == 0;
        }

        return false;
    }

    public static void performEnderPearlToTarget(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        LivingEntity entity = mobpatch.getOriginal();

        double dx = target.getX() - entity.getX();
        double dz = target.getZ() - entity.getZ();
        double dy = target.getEyeY() - entity.getEyeY();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180F / (float) Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, horizontal) * (180F / (float) Math.PI)));

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);

        entity.yRotO = yaw;
        entity.xRotO = pitch;
        entity.yBodyRotO = yaw;
        entity.yHeadRotO = yaw;

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setEnderPearlCooldown();
        }

        if (entity instanceof AVNpc AVNpc) {
            AVNpc.setEnderPearlCooldown();
        }

        CombatBehaviour.throwEnderPearl(entity, 0.0F);
    }

    public static void performEnderPearlAway(MobPatch<?> mobpatch) {
        LivingEntity target = mobpatch.getOriginal().getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        LivingEntity entity = mobpatch.getOriginal();

        double dx = entity.getX() - target.getX();
        double dz = entity.getZ() - target.getZ();

        float yaw = (float) (Mth.atan2(dz, dx) * (180F / (float) Math.PI)) - 90.0F;

        float basePitch = -15.0F;
        float randomPitchOffset = (entity.getRandom().nextFloat() - 0.5F) * 10.0F;
        float randomYawOffset = (entity.getRandom().nextFloat() - 0.5F) * 30.0F;

        float pitch = basePitch + randomPitchOffset;
        yaw += randomYawOffset;

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);

        entity.yRotO = yaw;
        entity.xRotO = pitch;
        entity.yBodyRotO = yaw;
        entity.yHeadRotO = yaw;

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setEnderPearlCooldown();
        }
        if (entity instanceof AVNpc AVNpc) {
            AVNpc.setEnderPearlCooldown();
        }
        CombatBehaviour.throwEnderPearl(entity, 0.0F);
    }

    public static void performNpcCombatFishingRod(MobPatch<?> mobpatch) {
        performNpcCombatFishingRod(mobpatch, false);
    }

    public static void performNpcCombatFishingRodEscape(MobPatch<?> mobpatch) {
        performNpcCombatFishingRod(mobpatch, true);
    }

    private static void performNpcCombatFishingRod(MobPatch<?> mobpatch, boolean escape) {
        if (escape) {
            if (!canUseNpcCombatFishingRodEscape(mobpatch)) return;
        } else if (!canUseNpcCombatFishingRod(mobpatch)) {
            return;
        }

        final Mob mob = mobpatch.getOriginal();
        LivingEntity stickyTarget = getNpcCombatFishingRodStickyTarget(mob);
        final LivingEntity target = stickyTarget != null ? stickyTarget : mob.getTarget();
        if (tryRestoreNpcCombatFishingRodBeforeNextHook(mob)) {
            return;
        }

        beginNpcCombatFishingRodSession(mob);
        cancelCombatEvolutionGuard(mobpatch);
        mob.getNavigation().stop();
        mob.swing(InteractionHand.OFF_HAND, true);
        mob.playSound(SoundEvents.FISHING_BOBBER_THROW, 1.0F, 1.0F);
        if (target != null) {
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        final int action = escape ? NPC_FISHING_ROD_ACTION_AROUND : chooseNpcCombatFishingRodAction(mob, target);
        final Vec3 hookAnchor = resolveNpcCombatFishingRodAnchor(mob, target, action, escape);
        Vec3 visualHookTarget = hookAnchor;
        if (visualHookTarget == null && target != null) {
            visualHookTarget = target.position().add(0.0D, target.getBbHeight() * 0.55D, 0.0D);
        }
        final Entity trackedHookTarget = target != null && (isNpcCombatFishingRodTargetPullAction(action) || hookAnchor == null) ? target : null;
        final FishingHook hook = visualHookTarget != null ? FishingRodGrappleUtil.spawnNpcCombatFishingHook(mob, visualHookTarget, trackedHookTarget) : null;
        final ItemStack stuckItem = shouldUseSteveJessicaHook(mob, action) ? new ItemStack(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get()) : ItemStack.EMPTY;
        if (!stuckItem.isEmpty()) {
            FishingRodGrappleUtil.attachNpcCombatFishingHookPayload(hook, mob, stuckItem);
        }

        scheduleNpcCombatFishingRodResolution(mob, target, action, hookAnchor, stuckItem, hook, 0);
        incrementNpcCombatFishingRodUseCount(mob);
    }

    private static void scheduleNpcCombatFishingRodResolution(
            Mob mob,
            @Nullable LivingEntity target,
            int action,
            @Nullable Vec3 hookAnchor,
            ItemStack stuckItem,
            @Nullable FishingHook hook,
            int waitedTicks
    ) {
        new DelayedTask(1) {
            @Override
            public void run() {
                if (!mob.isAlive()) {
                    FishingRodGrappleUtil.forceNpcCombatFishingHookReturn(hook);
                    return;
                }

                boolean maxWaitReached = waitedTicks >= NPC_COMBAT_FISHING_ROD_MAX_WAIT_TICKS;
                if (!FishingRodGrappleUtil.isNpcCombatFishingHookResolved(hook) && !maxWaitReached) {
                    scheduleNpcCombatFishingRodResolution(mob, target, action, hookAnchor, stuckItem, hook, waitedTicks + 1);
                    return;
                }

                if (maxWaitReached) {
                    FishingRodGrappleUtil.forceNpcCombatFishingHookReturn(hook);
                }

                LivingEntity currentTarget = target != null && target.isAlive() ? target : mob.getTarget();
                if (currentTarget != null) {
                    mob.getLookControl().setLookAt(currentTarget, 30.0F, 30.0F);
                }

                if (isNpcCombatFishingRodTargetPullAction(action) && currentTarget != null && currentTarget.isAlive()) {
                    pullTargetToMob(mob, currentTarget);
                    updateNpcCombatFishingRodStickyTarget(mob, currentTarget, action);
                    if (!stuckItem.isEmpty()) {
                        damageEnemyHitByNpcHookedFishingRodItem(mob, currentTarget, stuckItem);
                    }
                } else {
                    Vec3 destination = hookAnchor;
                    if (destination == null && currentTarget != null) {
                        destination = currentTarget.position().add(0.0D, currentTarget.getBbHeight() * 0.45D, 0.0D);
                    }
                    if (destination != null) {
                        pullEntityToward(mob, destination, 1.25D, 0.25D);
                    }
                }

                mob.playSound(SoundEvents.FISHING_BOBBER_RETRIEVE, 1.0F, 1.0F);
            }
        };
    }

    public static boolean damageEnemyHitByNpcHookedFishingRodItem(Mob owner, LivingEntity target, ItemStack stuckItem) {
        if (!owner.isAlive() || !target.isAlive() || target.isSpectator() || owner.isAlliedTo(target)) {
            return false;
        }

        float damage = calculateNpcHookedFishingRodItemDamage(stuckItem, target);
        if (!target.hurt(target.level().damageSources().mobAttack(owner), damage)) {
            return false;
        }

        if (stuckItem.is(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get())) {
            LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
            if (targetPatch != null) {
                if (!targetPatch.isStunned()) {
                    targetPatch.applyStun(StunType.LONG, 0.0F);
                }
                if (targetPatch.isStunned()) {
                    targetPatch.playAnimationSynchronized(AnimsPugilistSteve.GUARD_BREAK_ATTACK, 0.0F);
                }
            }
            target.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F);
        }

        return true;
    }

    public static void performVillagerGeneralLavaBucket(MobPatch<?> mobpatch) {
        if (!canUseVillagerGeneralLavaBucket(mobpatch)) {
            return;
        }

        final Mob mob = mobpatch.getOriginal();
        final LivingEntity target = mob.getTarget();
        if (!(mob.level() instanceof ServerLevel serverLevel) || target == null) {
            return;
        }

        cancelCombatEvolutionGuard(mobpatch);
        equipTemporaryOffhand(mob, new ItemStack(Items.LAVA_BUCKET), KEY_NPC_LAVA_BUCKET_ORIGINAL_OFFHAND);
        setPersistentLong(mob, KEY_NPC_LAVA_BUCKET_COOLDOWN_UNTIL,
                mob.level().getGameTime() + NPC_LAVA_BUCKET_MIN_COOLDOWN + new Random().nextInt(NPC_LAVA_BUCKET_RANDOM_COOLDOWN + 1));
        mob.swing(InteractionHand.OFF_HAND, true);
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        new DelayedTask(NPC_LAVA_BUCKET_ACTION_DELAY) {
            @Override
            public void run() {
                if (!mob.isAlive()) return;
                if (!target.isAlive()) {
                    restoreTemporaryOffhand(mob, KEY_NPC_LAVA_BUCKET_ORIGINAL_OFFHAND);
                    return;
                }
                if (!mob.onGround()) {
                    restoreTemporaryOffhand(mob, KEY_NPC_LAVA_BUCKET_ORIGINAL_OFFHAND);
                    return;
                }

                final BlockPos placement = findLavaPlacement(serverLevel, target);
                if (placement != null) {
                    mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    mob.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 1.0F, 1.0F);
                    serverLevel.setBlockAndUpdate(placement, Blocks.LAVA.defaultBlockState());
                    mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.BUCKET));

                    new DelayedTask(NPC_LAVA_BUCKET_PICKUP_DELAY) {
                        @Override
                        public void run() {
                            if (!mob.isAlive()) return;

                            mob.swing(InteractionHand.OFF_HAND, true);
                            if (serverLevel.getBlockState(placement).is(Blocks.LAVA)) {
                                mob.playSound(SoundEvents.BUCKET_FILL_LAVA, 1.0F, 1.0F);
                                serverLevel.setBlockAndUpdate(placement, Blocks.AIR.defaultBlockState());
                            }
                            mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Items.LAVA_BUCKET));

                            new DelayedTask(NPC_LAVA_BUCKET_RESTORE_DELAY) {
                                @Override
                                public void run() {
                                    if (mob.isAlive()) {
                                        restoreTemporaryOffhand(mob, KEY_NPC_LAVA_BUCKET_ORIGINAL_OFFHAND);
                                    }
                                }
                            };
                        }
                    };
                } else {
                    restoreTemporaryOffhand(mob, KEY_NPC_LAVA_BUCKET_ORIGINAL_OFFHAND);
                }
            }
        };
    }

    public static void placeRandomFrontWall(MobPatch<?> mobpatch) {
        final Mob mob = mobpatch.getOriginal();
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;
        if (!isGroundWithin(mob, MAX_PLACE_BLOCK_GROUND_GAP)) return;

        final LivingEntity target = mob.getTarget();
        final Direction dir = (target != null)
                ? Direction.getNearest(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ())
                : mob.getDirection();

        BlockState placeState;
        if (mob instanceof HerobrineChrisEntity || mob instanceof HerobrineCloneEntity) {
            placeState = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get().defaultBlockState().setValue(ShadowObsidianBlock.FROM_PLAYER, false);
        } else if (mob instanceof ShadowHerobrineCloneEntity || mob instanceof Herobrine7Entity || mob instanceof ArmoredHerobrineEntity || mob instanceof ShadowHerobrineEntity) {
            placeState = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get().defaultBlockState().setValue(ShadowObsidianBlock.FROM_PLAYER, false);
        } else {
            final ItemStack handStack = mob.getItemInHand(InteractionHand.MAIN_HAND);
            placeState = Blocks.COBBLESTONE.defaultBlockState();
            if (handStack.getItem() instanceof BlockItem blockItem) {
                placeState = blockItem.getBlock().defaultBlockState();
            }
        }

        final Random random = new Random();
        final int lanes = 1 + random.nextInt(3);
        final float missChancePerLane = 0.25F;

        for (int dist = 1; dist <= lanes; dist++) {
            if (random.nextFloat() < missChancePerLane) continue;

            final int pattern = random.nextInt(11);
            final int rot = random.nextInt(4);
            final BiFunction<Integer, Integer, int[]> toWorld = getIntegerIntegerBiFunction(mob, rot);
            final BlockState finalPlaceState = placeState;

            final BlockPos baseXZ = mob.blockPosition().relative(dir, dist);
            final int topY = Mth.floor(mob.getY() + mob.getBbHeight());
            final int laneStartDelay = PLACE_BLOCK_INITIAL_DELAY + (dist - 1) * PLACE_BLOCK_LANE_INTERVAL;

            final int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
            final BlockPos projXZ = new BlockPos(baseXZ.getX(), 0, baseXZ.getZ());

            for (int y = surfaceY; y <= topY; y++) {
                final int layer = y - surfaceY;

                final BlockPos center = new BlockPos(projXZ.getX(), y, projXZ.getZ());
                if (!serverLevel.getBlockState(center).canBeReplaced()) break;

                final int[][] extrasLocal = switch (pattern) {
                    case 0 -> new int[][]{};

                    case 1 -> (layer == 3) ? new int[][]{{1, 0}} : new int[][]{};

                    case 2 -> {
                        if (layer == 0) yield new int[][]{{-1, 0}, {1, 0}, {2, 0}};
                        else if (layer == 1) yield new int[][]{{1, 0}};
                        else yield new int[][]{};
                    }

                    case 3 -> (layer == 1) ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};

                    case 4 -> (layer == 0) ? new int[][]{{-1, 0}, {1, 0}} : new int[][]{};

                    case 5 -> new int[][]{{1, 0}};

                    case 6 -> (layer <= 1) ? new int[][]{{1, 0}} : new int[][]{};

                    case 7 -> (layer == 0) ? new int[][]{{1, 0}} : new int[][]{};

                    case 8 -> (layer == 1) ? new int[][]{{1, 0}} : new int[][]{};

                    case 9 -> (layer == 0) ? new int[][]{{-1, 0}} : new int[][]{};

                    default -> (layer == 1) ? new int[][]{{-1, 0}} : new int[][]{};
                };

                final BlockPos layerCenter = center;
                final int delay = laneStartDelay + layer * PLACE_BLOCK_LAYER_INTERVAL;
                new DelayedTask(delay) {
                    @Override
                    public void run() {
                        if (!mob.isAlive() || !isGroundWithin(mob, MAX_PLACE_BLOCK_GROUND_GAP)) return;
                        if (!serverLevel.getBlockState(layerCenter).canBeReplaced()) return;

                        placeIfReplaceable(serverLevel, layerCenter, finalPlaceState, mob);

                        for (int[] ab : extrasLocal) {
                            int[] dzdx = toWorld.apply(ab[0], ab[1]);
                            int dx = dzdx[0];
                            int dz = dzdx[1];

                            BlockPos p = layerCenter.offset(dx, 0, dz);
                            placeIfReplaceable(serverLevel, p, finalPlaceState, mob);
                        }
                    }
                };
            }
        }
    }

    static BiFunction<Integer, Integer, int[]> getIntegerIntegerBiFunction(Entity anchor, int rot) {
        Direction facing = anchor.getDirection();

        int fx = facing.getStepX();
        int fz = facing.getStepZ();
        int rx = -fz;
        int rz = fx;

        for (int i = 0; i < rot; i++) {
            int nfx = rx, nfz = rz;
            int nrx = -fz, nrz = fx;
            fx = nfx;
            fz = nfz;
            rx = nrx;
            rz = nrz;
        }

        int finalRx = rx;
        int finalFx = fx;
        int finalRz = rz;
        int finalFz = fz;

        return (a, b) -> new int[]{a * finalRx + b * finalFx, a * finalRz + b * finalFz};
    }

    private static void placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state, Mob mob) {
        if (mob instanceof HerobrineMob) {
            mob.swing(InteractionHand.MAIN_HAND, true);
            HerobrineUtil.placeIfReplaceable(level, pos, state, mob);
        } else {
            if (!level.getBlockState(pos).canBeReplaced()) return;
            mob.swing(InteractionHand.MAIN_HAND, true);
            mob.playSound(SoundEvents.STONE_PLACE, 2.0F, 1.0F);
            level.setBlockAndUpdate(pos, state);
        }
    }

    public static void performEscapeRunAway(MobPatch<?> mobpatch) {
        final Mob mob = mobpatch.getOriginal();
        if (!(mob.level() instanceof ServerLevel)) return;

        final LivingEntity target = mob.getTarget();
        if (target != null) {
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        Vec3 away;
        if (target != null) {
            Vec3 toTarget = new Vec3(target.getX() - mob.getX(), 0.0D, target.getZ() - mob.getZ());
            away = toTarget.lengthSqr() > 1.0E-6D ? toTarget.normalize().scale(-1.0D) : Vec3.ZERO;
        } else {
            float yawRad = mob.yBodyRot * Mth.DEG_TO_RAD;
            away = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad)).normalize().scale(-1.0D);
        }
        if (away == Vec3.ZERO) return;
        Vec3 right = new Vec3(-away.z, 0.0D, away.x).normalize();

        mob.getNavigation().stop();
        Random r = new Random();
        double backMag = 0.55D + r.nextDouble() * 0.35D;
        double strafeMag = (r.nextBoolean() ? 1 : -1) * (0.05D + r.nextDouble() * 0.15D);
        Vec3 impulse = away.scale(backMag).add(right.scale(strafeMag));

        mob.setDeltaMovement(mob.getDeltaMovement().add(impulse.x, 0.0D, impulse.z));
        mob.hasImpulse = true;
        if (mob instanceof HerobrineMob herobrineMob
                && target != null
                && herobrineMob.getGregUUID() != null) {
            Entity entity = ((ServerLevel) mob.level()).getEntity(herobrineMob.getGregUUID());
            if (entity instanceof HerobrineGregEntity greg && greg.isAlive()) {
                greg.requestRetreatPortalFor(herobrineMob, target);
            }
        }

        int pulses = 2 + r.nextInt(2);
        for (int i = 1; i <= pulses; i++) {
            Vec3 tail = away.scale(0.16D + r.nextDouble() * 0.10D)
                    .add(right.scale((r.nextDouble() - 0.5D) * 0.10D));
            int delay = i * 2;
            new DelayedTask(delay) {
                @Override
                public void run() {
                    if (!mob.isAlive()) return;
                    mob.setDeltaMovement(mob.getDeltaMovement().add(tail.x, 0.0D, tail.z));
                    mob.hasImpulse = true;
                }
            };
        }

        int jumpDelay = pulses * 2 + 1;
        new DelayedTask(jumpDelay) {
            @Override
            public void run() {
                if (!mob.isAlive() || !mob.onGround()) return;

                if (mob instanceof AVNpc AVNpc) {
                    AVNpc.shortPillarJump();
                }
                if (mob instanceof PlayerNpcEntity playerNpcEntity) {
                    playerNpcEntity.shortPillarJump();
                }
                mobpatch.playAnimationSynchronized(Animations.BIPED_JUMP, 0.0F);
            }
        };

        if (mob instanceof SteveEntity || mob instanceof AngrySteveEntity
                || mob instanceof HerobrineCloneEntity || mob instanceof HerobrineChrisEntity
                || mob instanceof ShadowHerobrineCloneEntity || mob instanceof Herobrine7Entity
                || mob instanceof ArmoredHerobrineEntity || mob instanceof ShadowHerobrineEntity) {
            new DelayedTask(1) {
                @Override
                public void run() {
                    if (isGroundWithin(mob, MAX_PLACE_BLOCK_GROUND_GAP)) {
                        placeRandomFrontWall(mobpatch);
                    }
                }
            };
        }
    }

    public static void swapToBlockAndPerformEscapeRunAway(MobPatch<?> mobpatch) {
        swapToBlockToEscape(mobpatch);
        performEscapeRunAway(mobpatch);
    }

    public static boolean isGroundWithin(Entity e, double maxGap) {
        Level level = e.level();
        AABB bb = e.getBoundingBox();
        double feetY = bb.minY;

        int x = Mth.floor(e.getX());
        int z = Mth.floor(e.getZ());
        int startY = Mth.floor(feetY - 1.0e-4);

        int maxSteps = Mth.ceil(maxGap) + 2;

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, startY, z);

        for (int i = 0; i <= maxSteps; i++) {
            pos.setY(startY - i);

            BlockState state = level.getBlockState(pos);
            if (state.isAir()) continue;
            VoxelShape shape = state.getCollisionShape(level, pos);
            if (shape.isEmpty()) continue;

            double topY = pos.getY() + shape.max(Direction.Axis.Y);
            double gap = feetY - topY;

            if (gap >= -1.0e-3 && gap <= maxGap + 1.0e-3) {
                return true;
            }
        }

        return false;
    }

    public static void performEatingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        boolean isEnchanted;

        if (entity instanceof AVNpc AVNpc
                && new Random().nextDouble() <= AVNpc.getPlaceBlockToParryChance()) {
            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
            isEnchanted = true;
        } else {
            entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_APPLE));
            isEnchanted = false;
        }
        if (new Random().nextBoolean()) {
            CombatBehaviour.throwEnderPearl(entity, new Random().nextFloat(0.0F, 180.0F));
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                playerNpcEntity.setEnderPearlCooldown();
            }
            if (entity instanceof AVNpc AVNpc) {
                AVNpc.setEnderPearlCooldown();
            }
        } else {
            performEscapeRunAway(mobpatch);
        }

        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setGapCooldown();
        }
        if (entity instanceof AVNpc AVNpc) {
            AVNpc.setGapCooldown();
        }

        CombatBehaviour.eatingGoldenApple(
                entity,
                entity.level(),
                20.0D,
                isEnchanted
        );
    }

    public static void performDrinkingAnimation(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();

        if (!entity.level().isClientSide) {
            ItemStack stack = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING);
            entity.setItemInHand(InteractionHand.MAIN_HAND, stack);
        }
        if (entity instanceof AVNpc AVNpc) {
            AVNpc.setGapCooldown();
        }

        CombatBehaviour.drinkingHealingPotion(
                entity,
                entity.level(),
                false,
                20.0D
        );
    }

    public static void swapToBow(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        ItemStack bow = new ItemStack(Items.BOW);
        if (entity instanceof AVNpc avNpc) bow = avNpc.getBowItem();

        if (entity instanceof VillagerScoutCaptainEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.PUNCH_ARROWS, 1);
        }
        if (entity instanceof RedVillagerGeneralEntity) {
            bow.enchant(Enchantments.FLAMING_ARROWS, 2);
        }
        if (entity instanceof BlueVillagerGeneralEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
        }
        if (entity instanceof GreenVillagerGeneralEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 1);
            bow.enchant(Enchantments.FLAMING_ARROWS, 1);
        }
        if (entity instanceof PurpleVillagerGeneralEntity) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }
        if ((entity instanceof SteveEntity steveEntity && steveEntity.getState() == 1)
                || entity instanceof AngrySteveEntity) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
            if (entity instanceof AngrySteveEntity) {
                bow.enchant(Enchantments.FLAMING_ARROWS, 2);
            }
        }
        if (entity instanceof AlexEntity alexEntity && alexEntity.getState() == 1) {
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.FLAMING_ARROWS, 1);
        }
        if (entity instanceof ChrisEntity chrisEntity && chrisEntity.getState() == 1) {
            bow.enchant(Enchantments.POWER_ARROWS, 2);
            bow.enchant(Enchantments.PUNCH_ARROWS, 2);
        }

        entity.setItemInHand(InteractionHand.MAIN_HAND, bow.copy());
        entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
    }

    public static void switchWeapon(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        if (entity instanceof SteveEntity steveEntity) {
            steveEntity.rollItem();
        }
        if (entity instanceof HerobrineMob herobrineMob) {
            herobrineMob.rollItem();
        }
        if (entity instanceof BlueDemonEntity blueDemonEntity) {
            blueDemonEntity.rollItem();
        }
    }

    public static void swapToMelee(MobPatch<?> mobpatch) {
        if (mobpatch.getOriginal() instanceof PlayerNpcEntity playerNpcEntity) {
            ItemStack mainWeaponItem = playerNpcEntity.getMainWeaponItem();
            ItemStack offWeaponItem = playerNpcEntity.getOffWeaponItem();
            playerNpcEntity.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
            playerNpcEntity.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
            playerNpcEntity.setSwapToBowCooldown();
        }

        if (mobpatch.getOriginal() instanceof AVNpc AVNpc) {
            ItemStack mainWeaponItem = AVNpc.getMainWeaponItem();
            ItemStack offWeaponItem = AVNpc.getOffWeaponItem();
            if (AVNpc instanceof SteveEntity) {
                if (canSwitchWeapon(mobpatch)) {
                    switchWeapon(mobpatch);
                } else {
                    AVNpc.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
                    AVNpc.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
                }
            } else {
                AVNpc.setItemInHand(InteractionHand.MAIN_HAND, mainWeaponItem.copy());
                AVNpc.setItemInHand(InteractionHand.OFF_HAND, offWeaponItem.copy());
            }
            AVNpc.setSwapToBowCooldown();
        }
    }

    private static boolean isNpcCombatFishingRodUser(MobPatch<?> mobpatch) {
        Mob mob = mobpatch.getOriginal();
        return isGeneral(mobpatch) || mob instanceof SteveEntity || mob instanceof AngrySteveEntity;
    }

    private static boolean isGeneralMob(Mob mob) {
        return mob instanceof RedVillagerGeneralEntity
                || mob instanceof BlueVillagerGeneralEntity
                || mob instanceof GreenVillagerGeneralEntity
                || mob instanceof PurpleVillagerGeneralEntity;
    }

    private static boolean isNpcCombatFishingRodSessionActive(Mob mob) {
        return mob.getPersistentData().getBoolean(KEY_NPC_COMBAT_FISHING_ROD_ACTIVE);
    }

    private static boolean isStevePhaseOneFishingRodBlocked(Mob mob) {
        return mob instanceof SteveEntity steveEntity
                && steveEntity.getState() == 0
                && !isNpcCombatFishingRodSessionActive(mob);
    }

    private static Item getNpcCombatFishingRodItem(Mob mob) {
        return isGeneralMob(mob)
                ? AnnoyingVillagersModItems.ADVANCED_FISHING_ROD.get()
                : AnnoyingVillagersModItems.TONY_THE_FISHING_ROD.get();
    }

    private static void beginNpcCombatFishingRodSession(Mob mob) {
        CompoundTag data = mob.getPersistentData();
        if (!data.getBoolean(KEY_NPC_COMBAT_FISHING_ROD_ACTIVE)) {
            saveOffhand(mob, KEY_NPC_COMBAT_FISHING_ROD_ORIGINAL_OFFHAND);
            data.putBoolean(KEY_NPC_COMBAT_FISHING_ROD_ACTIVE, true);
            data.putInt(KEY_NPC_COMBAT_FISHING_ROD_USE_COUNT, 0);
        }

        Item rodItem = getNpcCombatFishingRodItem(mob);
        if (!mob.getOffhandItem().is(rodItem)) {
            mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(rodItem));
        }
    }

    private static boolean tryRestoreNpcCombatFishingRodBeforeNextHook(Mob mob) {
        if (!isNpcCombatFishingRodSessionActive(mob)) {
            return false;
        }
        if (getNpcCombatFishingRodStickyTarget(mob) != null) {
            return false;
        }

        int useCount = Math.max(0, mob.getPersistentData().getInt(KEY_NPC_COMBAT_FISHING_ROD_USE_COUNT));
        if (useCount == 0) {
            return false;
        }

        double restoreChance = Math.min(0.6D, (double) useCount * 0.2D);
        if (new Random().nextDouble() > restoreChance) {
            return false;
        }

        restoreNpcCombatFishingRodSession(mob, true);
        return true;
    }

    private static void incrementNpcCombatFishingRodUseCount(Mob mob) {
        CompoundTag data = mob.getPersistentData();
        data.putInt(KEY_NPC_COMBAT_FISHING_ROD_USE_COUNT, data.getInt(KEY_NPC_COMBAT_FISHING_ROD_USE_COUNT) + 1);
    }

    private static void restoreNpcCombatFishingRodSession(Mob mob, boolean setCooldown) {
        ItemStack originalOffhand = loadOffhand(mob, KEY_NPC_COMBAT_FISHING_ROD_ORIGINAL_OFFHAND);
        mob.setItemInHand(InteractionHand.OFF_HAND, originalOffhand.copy());

        CompoundTag data = mob.getPersistentData();
        data.remove(KEY_NPC_COMBAT_FISHING_ROD_ACTIVE);
        data.remove(KEY_NPC_COMBAT_FISHING_ROD_ORIGINAL_OFFHAND);
        data.remove(KEY_NPC_COMBAT_FISHING_ROD_USE_COUNT);
        data.remove(KEY_NPC_COMBAT_FISHING_ROD_STICKY_TARGET_ID);

        if (setCooldown) {
            setPersistentLong(mob, KEY_NPC_COMBAT_FISHING_ROD_COOLDOWN_UNTIL,
                    mob.level().getGameTime() + NPC_COMBAT_FISHING_ROD_MIN_COOLDOWN + new Random().nextInt(NPC_COMBAT_FISHING_ROD_RANDOM_COOLDOWN + 1));
        }
    }

    private static int chooseNpcCombatFishingRodAction(Mob mob, @Nullable LivingEntity target) {
        if (target == null) {
            return NPC_FISHING_ROD_ACTION_AROUND;
        }

        double roll = new Random().nextDouble();
        double distance = mob.distanceTo(target);
        if (getNpcCombatFishingRodStickyTarget(mob) != null) {
            return NPC_FISHING_ROD_ACTION_PULL_TARGET;
        }
        if (mob instanceof SteveEntity steveEntity && steveEntity.getState() == 1) {
            if (roll < 0.50D) return NPC_FISHING_ROD_ACTION_JESSICA_PULL_TARGET;
            if (roll < 0.70D) return NPC_FISHING_ROD_ACTION_PULL_TARGET;
            if (roll < 0.90D) return NPC_FISHING_ROD_ACTION_SELF_TO_TARGET;
            return NPC_FISHING_ROD_ACTION_AROUND;
        }
        if (mob instanceof AngrySteveEntity) {
            if (roll < 0.30D) return NPC_FISHING_ROD_ACTION_PULL_TARGET;
            if (roll < 0.70D || distance > 8.0D) return NPC_FISHING_ROD_ACTION_SELF_TO_TARGET;
            return NPC_FISHING_ROD_ACTION_AROUND;
        }
        if (distance > 12.0D) {
            return roll < 0.55D ? NPC_FISHING_ROD_ACTION_SELF_TO_TARGET : NPC_FISHING_ROD_ACTION_PULL_TARGET;
        }
        if (distance < 3.0D) {
            return roll < 0.45D ? NPC_FISHING_ROD_ACTION_PULL_TARGET : NPC_FISHING_ROD_ACTION_AROUND;
        }

        if (roll < 0.45D) return NPC_FISHING_ROD_ACTION_PULL_TARGET;
        if (roll < 0.80D) return NPC_FISHING_ROD_ACTION_SELF_TO_TARGET;
        return NPC_FISHING_ROD_ACTION_AROUND;
    }

    private static boolean shouldUseSteveJessicaHook(Mob mob, int action) {
        return action == NPC_FISHING_ROD_ACTION_JESSICA_PULL_TARGET
                && mob instanceof SteveEntity steveEntity
                && steveEntity.getState() == 1;
    }

    private static boolean isNpcCombatFishingRodTargetPullAction(int action) {
        return action == NPC_FISHING_ROD_ACTION_PULL_TARGET
                || action == NPC_FISHING_ROD_ACTION_JESSICA_PULL_TARGET;
    }

    @Nullable
    private static LivingEntity getNpcCombatFishingRodStickyTarget(Mob mob) {
        int stickyTargetId = mob.getPersistentData().getInt(KEY_NPC_COMBAT_FISHING_ROD_STICKY_TARGET_ID);
        if (stickyTargetId <= 0) {
            return null;
        }

        Entity entity = mob.level().getEntity(stickyTargetId);
        if (!(entity instanceof LivingEntity livingEntity)
                || !livingEntity.isAlive()
                || livingEntity.isRemoved()
                || livingEntity == mob
                || mob.isAlliedTo(livingEntity)) {
            mob.getPersistentData().remove(KEY_NPC_COMBAT_FISHING_ROD_STICKY_TARGET_ID);
            return null;
        }

        return livingEntity;
    }

    private static void updateNpcCombatFishingRodStickyTarget(Mob mob, LivingEntity target, int action) {
        if (action != NPC_FISHING_ROD_ACTION_PULL_TARGET) {
            return;
        }

        CompoundTag data = mob.getPersistentData();
        int stickyTargetId = data.getInt(KEY_NPC_COMBAT_FISHING_ROD_STICKY_TARGET_ID);
        if (stickyTargetId == target.getId()) {
            if (new Random().nextDouble() < NPC_COMBAT_FISHING_ROD_STICK_LOSE_CHANCE) {
                data.remove(KEY_NPC_COMBAT_FISHING_ROD_STICKY_TARGET_ID);
            }
            return;
        }

        double stickChance = NPC_COMBAT_FISHING_ROD_STICK_CHANCE_MIN
                + new Random().nextDouble() * (NPC_COMBAT_FISHING_ROD_STICK_CHANCE_MAX - NPC_COMBAT_FISHING_ROD_STICK_CHANCE_MIN);
        if (new Random().nextDouble() < stickChance) {
            data.putInt(KEY_NPC_COMBAT_FISHING_ROD_STICKY_TARGET_ID, target.getId());
        }
    }

    @Nullable
    private static Vec3 resolveNpcCombatFishingRodAnchor(Mob mob, @Nullable LivingEntity target, int action, boolean escape) {
        if (action == NPC_FISHING_ROD_ACTION_SELF_TO_TARGET) {
            Vec3 blockBetween = findHookBlockBetween(mob, target);
            if (blockBetween != null) {
                return blockBetween;
            }
            return target == null ? null : target.position().add(0.0D, target.getBbHeight() * 0.45D, 0.0D);
        }

        if (action == NPC_FISHING_ROD_ACTION_AROUND) {
            Vec3 aroundAnchor = findNpcCombatFishingRodAroundAnchor(mob, target, escape);
            if (aroundAnchor != null) {
                return aroundAnchor;
            }
            if (escape && target != null) {
                Vec3 away = mob.position().subtract(target.position());
                if (away.lengthSqr() > 1.0E-6D) {
                    Vec3 horizontal = new Vec3(away.x, 0.0D, away.z).normalize();
                    return mob.position().add(horizontal.scale(6.0D)).add(0.0D, 2.0D, 0.0D);
                }
            }
            return target == null ? null : target.position().add(0.0D, target.getBbHeight() * 0.45D, 0.0D);
        }

        return null;
    }

    @Nullable
    private static Vec3 findHookBlockBetween(Mob mob, @Nullable LivingEntity target) {
        if (target == null) {
            return null;
        }

        Level level = mob.level();
        Vec3 start = mob.getEyePosition();
        Vec3 end = target.getEyePosition();
        Vec3 delta = end.subtract(start);
        for (int i = 2; i <= 14; i++) {
            double t = (double) i / 16.0D;
            BlockPos pos = BlockPos.containing(start.add(delta.scale(t)));
            BlockState state = level.getBlockState(pos);
            if (isHookAnchorBlock(level, pos, state) && hasHookLine(level, mob, pos)) {
                return Vec3.atCenterOf(pos);
            }
        }

        return null;
    }

    @Nullable
    private static Vec3 findNpcCombatFishingRodAroundAnchor(Mob mob, @Nullable LivingEntity target, boolean escape) {
        Level level = mob.level();
        BlockPos origin = mob.blockPosition();
        Vec3 targetDirection = Vec3.ZERO;
        if (target != null) {
            Vec3 towardTarget = target.position().subtract(mob.position());
            Vec3 horizontal = new Vec3(towardTarget.x, 0.0D, towardTarget.z);
            if (horizontal.lengthSqr() > 1.0E-6D) {
                targetDirection = horizontal.normalize();
            }
        }

        BlockPos bestPos = null;
        double bestScore = -Double.MAX_VALUE;
        int radius = NPC_COMBAT_FISHING_ROD_AROUND_SEARCH_RADIUS;
        int radiusSqr = radius * radius;
        for (int dy = -2; dy <= 12; dy++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int distSqr = dx * dx + dy * dy + dz * dz;
                    if (distSqr < 9 || distSqr > radiusSqr) {
                        continue;
                    }

                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (!isHookAnchorBlock(level, pos, state) || !hasHookLine(level, mob, pos)) {
                        continue;
                    }

                    double score = Math.sqrt(distSqr);
                    if (state.is(BlockTags.LEAVES)) {
                        score += 1000.0D;
                    }
                    if (dy > 0) {
                        score += (double) dy * 4.0D;
                    }

                    if (targetDirection != Vec3.ZERO) {
                        Vec3 toAnchor = new Vec3(dx, 0.0D, dz);
                        if (toAnchor.lengthSqr() > 1.0E-6D) {
                            double dot = toAnchor.normalize().dot(targetDirection);
                            if (escape) {
                                score -= dot * 90.0D;
                            } else {
                                score += dot > 0.75D ? -60.0D : (0.35D - Math.min(dot, 0.35D)) * 35.0D;
                            }
                        }
                    }

                    if (score > bestScore) {
                        bestScore = score;
                        bestPos = pos;
                    }
                }
            }
        }

        return bestPos == null ? null : Vec3.atCenterOf(bestPos);
    }

    private static boolean isHookAnchorBlock(Level level, BlockPos pos, BlockState state) {
        return state.is(BlockTags.LEAVES) || !state.getCollisionShape(level, pos).isEmpty();
    }

    private static boolean hasHookLine(Level level, Mob mob, BlockPos pos) {
        BlockHitResult hit = level.clip(new ClipContext(
                mob.getEyePosition(),
                Vec3.atCenterOf(pos),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                mob
        ));
        return hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(pos);
    }

    private static void pullTargetToMob(Mob mob, LivingEntity target) {
        Vec3 destination = mob.position().add(0.0D, mob.getBbHeight() * 0.45D, 0.0D);
        pullEntityToward(target, destination, 1.05D, 0.20D);
    }

    private static void pullEntityToward(Entity entity, Vec3 destination, double power, double yBoost) {
        Vec3 center = entity.position().add(0.0D, entity.getBbHeight() * 0.5D, 0.0D);
        Vec3 delta = destination.subtract(center);
        if (delta.lengthSqr() < 1.0E-4D) {
            return;
        }

        Vec3 impulse = delta.normalize().scale(power);
        impulse = new Vec3(impulse.x, Math.max(impulse.y + yBoost, yBoost), impulse.z);
        entity.setDeltaMovement(entity.getDeltaMovement().add(impulse));
        entity.hasImpulse = true;
        entity.hurtMarked = true;
        entity.fallDistance = 0.0F;
        if (entity instanceof Mob mob) {
            mob.getNavigation().stop();
        }
    }

    private static float calculateNpcHookedFishingRodItemDamage(ItemStack stuckItem, LivingEntity target) {
        if (stuckItem.is(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get())) {
            return 10.0F;
        }
        if (stuckItem.getItem() instanceof ShieldItem) {
            return 8.0F;
        }
        return 4.0F;
    }

    @Nullable
    private static BlockPos findLavaPlacement(ServerLevel level, LivingEntity target) {
        BlockPos foot = target.blockPosition();
        if (canPlaceLavaAt(level, foot)) {
            return foot;
        }
        BlockPos above = foot.above();
        if (canPlaceLavaAt(level, above)) {
            return above;
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos side = foot.relative(direction);
            if (canPlaceLavaAt(level, side)) {
                return side;
            }
        }
        return null;
    }

    private static boolean canPlaceLavaAt(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).canBeReplaced();
    }

    @Nullable
    private static BlockPos findSelfWaterPlacement(ServerLevel level, AVNpc avNpc) {
        BlockPos feet = avNpc.blockPosition();
        if (canPlaceSelfWaterAt(level, feet)) {
            return feet;
        }
        BlockPos above = feet.above();
        if (canPlaceSelfWaterAt(level, above)) {
            return above;
        }
        return null;
    }

    private static boolean canPlaceSelfWaterAt(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos).canBeReplaced() && level.getFluidState(pos).isEmpty();
    }

    private static void finishAvNpcWaterBucketSelfExtinguish(AVNpc avNpc) {
        restoreTemporaryOffhand(avNpc, KEY_AVNPC_WATER_BUCKET_ORIGINAL_OFFHAND);
        avNpc.getPersistentData().remove(KEY_AVNPC_WATER_BUCKET_ACTIVE);
    }

    private static void equipTemporaryOffhand(Mob mob, ItemStack stack, String originalKey) {
        saveOffhand(mob, originalKey);
        mob.setItemInHand(InteractionHand.OFF_HAND, stack.copy());
    }

    private static void restoreTemporaryOffhand(Mob mob, String originalKey) {
        ItemStack original = loadOffhand(mob, originalKey);
        mob.setItemInHand(InteractionHand.OFF_HAND, original.copy());
        mob.getPersistentData().remove(originalKey);
    }

    private static void saveOffhand(Mob mob, String key) {
        CompoundTag data = mob.getPersistentData();
        ItemStack stack = mob.getOffhandItem();
        if (stack.isEmpty()) {
            data.remove(key);
            return;
        }

        CompoundTag stackTag = new CompoundTag();
        stack.save(stackTag);
        data.put(key, stackTag);
    }

    private static ItemStack loadOffhand(Mob mob, String key) {
        CompoundTag data = mob.getPersistentData();
        if (!data.contains(key, Tag.TAG_COMPOUND)) {
            return ItemStack.EMPTY;
        }
        return ItemStack.of(data.getCompound(key));
    }

    private static long getPersistentLong(Mob mob, String key) {
        return mob.getPersistentData().getLong(key);
    }

    private static void setPersistentLong(Mob mob, String key, long value) {
        mob.getPersistentData().putLong(key, value);
    }

    public static void jump(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.jump();
        }
        if (entity instanceof AVNpc AVNpc) {
            AVNpc.jump();
        }
    }

    public static void shortPillarJump(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.shortPillarJump();
        }
        if (entity instanceof AVNpc AVNpc) {
            AVNpc.shortPillarJump();
        }
    }

    public static void swapToBlockToEscape(MobPatch<?> mobpatch) {
        Entity entity = mobpatch.getOriginal();
        if (entity instanceof LivingEntity livingEntity) {
            cancelCombatEvolutionGuard(mobpatch);
            double chance = new Random().nextDouble(0.0, 1.0);
            if (chance <= 0.2) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.COBBLESTONE));
            } else if (chance <= 0.4) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.MOSSY_COBBLESTONE));
            } else if (chance <= 0.6) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIRT));
            } else if (chance <= 0.8) {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DARK_OAK_PLANKS));
            } else {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.OAK_PLANKS));
            }
            if (livingEntity instanceof AVNpc avNpc) {
                avNpc.setPlaceBlockParryCooldown();
            }
        }
    }

    public static void swapToBlock(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        if (entity instanceof PlayerNpcEntity || entity instanceof AVNpc) {
            cancelCombatEvolutionGuard(mobpatch);
            double chance = new Random().nextDouble(0.0, 1.0);
            if (chance <= 0.2) {
                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.COBBLESTONE));
            } else if (chance <= 0.4) {
                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.MOSSY_COBBLESTONE));
            } else if (chance <= 0.6) {
                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIRT));
            } else if (chance <= 0.8) {
                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DARK_OAK_PLANKS));
            } else {
                entity.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.OAK_PLANKS));
            }
            if (entity instanceof AVNpc avNpc) {
                avNpc.setPlaceBlockParryCooldown();
            }
        }
    }

    public static void cancelCombatEvolutionGuard(MobPatch<?> mobpatch) {
        LivingEntity livingEntity = mobpatch.getOriginal();
        CECombatBehaviors.Behavior<?> currentBehavior = BehaviorUtils.getCurrentBehavior(mobpatch);

        if (isCombatEvolutionGuardBehavior(currentBehavior)) {
            BehaviorUtils.stopCurrentBehavior(livingEntity);
        }

        CEPatchUtils.setGuard(mobpatch, false);
        CEPatchUtils.setWander(mobpatch, false);
        CEPatchUtils.setInCounter(mobpatch, false);
        livingEntity.stopUsingItem();
        stopCombatEvolutionBlockAnimation(mobpatch);
    }

    private static boolean isCombatEvolutionGuardBehavior(CECombatBehaviors.Behavior<?> behavior) {
        if (behavior == null) {
            return false;
        }

        CECombatBehaviors.BehaviorType type = behavior.getType();
        return type == CECombatBehaviors.BehaviorType.GUARD
                || type == CECombatBehaviors.BehaviorType.GUARD_WANDER;
    }

    private static void stopCombatEvolutionBlockAnimation(MobPatch<?> mobpatch) {
        AssetAccessor<? extends StaticAnimation> blockAnimation = mobpatch.getAnimator()
                .getLivingAnimation(LivingMotions.BLOCK, Animations.SWORD_GUARD);

        if (mobpatch.isLogicalClient()) {
            mobpatch.getAnimator().stopPlaying(blockAnimation);
        } else {
            mobpatch.stopPlaying(blockAnimation);
        }
    }

    public static void performExecute(MobPatch<?> mobPatch) {
        final Mob attacker = mobPatch.getOriginal();
        final LivingEntity victim = attacker.getTarget();
        if (victim == null) return;
        if (attacker.isPassenger()) attacker.stopRiding();

        final LivingEntityPatch<?> victimPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
        if (victimPatch == null) return;

        final ExecutionTypeManager.Type execType = ExecutionHandler.getExecutionType(mobPatch, victimPatch);
        faceTargetHard(attacker, victim);
        ExecutionHandler.ExecutionTransform transform = calculateExecutionPosition(attacker.level(), attacker, victim, execType.offset());
        if (transform != null) {
            Vec3 executionPos = transform.position();
            attacker.teleportTo(executionPos.x, executionPos.y, executionPos.z);
            faceTargetHard(attacker, victim);
            TickTaskManager.addTask(victim.getUUID(),
                    new MobExecutionTask(attacker, victim, execType, execType.totalTick()));
        }
    }

    private static void faceTargetHard(Mob self, LivingEntity target) {
        Vec3 from = self.getEyePosition(1.0F);
        Vec3 to = target.getEyePosition(1.0F);
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double dz = to.z - from.z;

        double horiz = Math.sqrt(dx * dx + dz * dz);
        if (horiz < 1.0E-6) horiz = 1.0E-6;

        float yaw = (float) (Mth.atan2(dz, dx) * (180F / Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, horiz) * (180F / Math.PI)));

        self.getNavigation().stop();
        self.setYRot(yaw);
        self.setXRot(pitch);
        self.setYBodyRot(yaw);
        self.setYHeadRot(yaw);
        self.yRotO = yaw;
        self.xRotO = pitch;
        self.yBodyRotO = yaw;
        self.yHeadRotO = yaw;
        self.getLookControl().setLookAt(target, 90.0F, 90.0F);
    }

    public static void performBlueDemonHealing(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        if (entity instanceof BlueDemonEntity blueDemonEntity && blueDemonEntity.level() instanceof ServerLevel) {
            blueDemonEntity.setHealingCooldown();
            blueDemonEntity.setHealingTick(600);
        }
    }

    public static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addRandomCombatChains(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] group1,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] group2,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] group3,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] kicks,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] rolls
    ) {
        return addRandomCombatChainsFromGroups(root, conditions(), group1, group2, group3, kicks, rolls);
    }

    @SafeVarargs
    public static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addRandomCombatChains(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            MobPatchCondition[] customConditions,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] group1,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        return addRandomCombatChainsFromGroups(root, customConditions, group1, groups);
    }

    @SafeVarargs
    public static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addRandomCombatChains(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] group1,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        return addRandomCombatChainsFromGroups(root, conditions(), group1, groups);
    }

    @SafeVarargs
    public static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addRandomCombatChains(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            CombatChainStep[] group1,
            CombatChainStep[]... groups
    ) {
        return addRandomCombatChainsFromStepGroups(root, conditions(), group1, groups);
    }

    @SafeVarargs
    public static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addRandomCombatChains(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            MobPatchCondition[] customConditions,
            CombatChainStep[] group1,
            CombatChainStep[]... groups
    ) {
        return addRandomCombatChainsFromStepGroups(root, customConditions, group1, groups);
    }

    public static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addAnimationBehaviors(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            double minDistance,
            double maxDistance,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] animations
    ) {
        return addAnimationBehaviors(root, minDistance, maxDistance, conditions(), animations);
    }

    public static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addAnimationBehaviors(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            double minDistance,
            double maxDistance,
            MobPatchCondition[] customConditions,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] animations
    ) {
        for (AnimationManager.AnimationAccessor<? extends StaticAnimation> animation : animations) {
            root = root.addFirstBehavior(animationStep(animation, minDistance, maxDistance, customConditions));
        }

        return root;
    }

    @SafeVarargs
    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] animations(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>... animations
    ) {
        return animations;
    }

    @SafeVarargs
    public static MobPatchCondition[] conditions(MobPatchCondition... conditions) {
        return conditions;
    }

    @SafeVarargs
    public static CombatChainStep[] steps(CombatChainStep... steps) {
        return steps;
    }

    @SafeVarargs
    public static CombatChainStep[] steps(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>... animations
    ) {
        CombatChainStep[] steps = new CombatChainStep[animations.length];
        for (int i = 0; i < animations.length; i++) {
            steps[i] = animation(animations[i]);
        }
        return steps;
    }

    @SafeVarargs
    public static CombatChainStep animation(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation,
            MobPatchCondition... customConditions
    ) {
        return new CombatChainStep(animation, -1, null, customConditions);
    }

    @SafeVarargs
    public static CombatChainStep animation(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation,
            double maxDistance,
            MobPatchCondition... customConditions
    ) {
        return new CombatChainStep(animation, -1, maxDistance, customConditions);
    }

    @SafeVarargs
    public static CombatChainStep guard(int ticks, MobPatchCondition... customConditions) {
        return new CombatChainStep(null, ticks, null, customConditions);
    }

    @SafeVarargs
    public static CombatChainStep guard(int ticks, double maxDistance, MobPatchCondition... customConditions) {
        return new CombatChainStep(null, ticks, maxDistance, customConditions);
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] kickAnimations() {
        return ModList.get().isLoaded("efkick") ? EfKick.kickAnimations() : animations();
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] fistKickAnimations() {
        return ModList.get().isLoaded("efkick") ? EfKick.fistKickAnimations() : animations();
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] basicKickAnimations() {
        return ModList.get().isLoaded("efkick") ? EfKick.basicKickAnimations() : animations();
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] rollAnimations() {
        return animations(
                Animations.BIPED_ROLL_BACKWARD,
                Animations.BIPED_ROLL_FORWARD
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] stepAnimations() {
        return animations(
                Animations.BIPED_STEP_BACKWARD,
                Animations.BIPED_STEP_FORWARD,
                Animations.BIPED_STEP_LEFT,
                Animations.BIPED_STEP_RIGHT
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] rollStepAnimations() {
        return animations(
                Animations.BIPED_ROLL_BACKWARD,
                Animations.BIPED_ROLL_FORWARD,
                Animations.BIPED_STEP_BACKWARD,
                Animations.BIPED_STEP_FORWARD,
                Animations.BIPED_STEP_LEFT,
                Animations.BIPED_STEP_RIGHT
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] enderStepAnimations() {
        return animations(
                WOMAnimations.ENDERSTEP_FORWARD,
                WOMAnimations.ENDERSTEP_BACKWARD,
                WOMAnimations.ENDERSTEP_LEFT,
                WOMAnimations.ENDERSTEP_RIGHT
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] shadowStepAnimations() {
        return animations(
                WOMAnimations.SHADOWSTEP_FORWARD,
                WOMAnimations.SHADOWSTEP_BACKWARD,
                WOMAnimations.SHADOWSTEP_RIGHT,
                WOMAnimations.SHADOWSTEP_LEFT
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] enderStepRollAnimations() {
        return animations(
                WOMAnimations.ENDERSTEP_FORWARD,
                WOMAnimations.ENDERSTEP_BACKWARD,
                WOMAnimations.ENDERSTEP_LEFT,
                WOMAnimations.ENDERSTEP_RIGHT,
                Animations.BIPED_STEP_BACKWARD,
                Animations.BIPED_STEP_FORWARD,
                Animations.BIPED_STEP_LEFT,
                Animations.BIPED_STEP_RIGHT,
                Animations.BIPED_ROLL_BACKWARD,
                Animations.BIPED_ROLL_FORWARD
        );
    }

    @SuppressWarnings("unchecked")
    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addRandomCombatChainsFromGroups(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            MobPatchCondition[] customConditions,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] group1,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        CombatChainStep[][] stepGroups = new CombatChainStep[groups.length][];
        for (int groupIndex = 0; groupIndex < groups.length; groupIndex++) {
            stepGroups[groupIndex] = toSteps(groups[groupIndex]);
        }

        return addRandomCombatChainsFromStepGroups(root, customConditions, toSteps(group1), stepGroups);
    }

    @SuppressWarnings("unchecked")
    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addRandomCombatChainsFromStepGroups(
            CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root,
            MobPatchCondition[] customConditions,
            CombatChainStep[] group1,
            CombatChainStep[]... groups
    ) {
        List<CombatChainStep[]> openingGroups = new java.util.ArrayList<>(2);
        int openingStepCount = 0;
        if (group1.length > 0) {
            openingGroups.add(group1);
            openingStepCount += group1.length;
        }
        if (groups.length > 0 && groups[0].length > 0) {
            openingGroups.add(groups[0]);
            openingStepCount += groups[0].length;
        }
        if (openingGroups.isEmpty()) {
            return root;
        }

        List<CombatChainStep[]> followUpGroups = new java.util.ArrayList<>(Math.max(0, groups.length - 1));
        for (int groupIndex = 1; groupIndex < groups.length; groupIndex++) {
            if (groups[groupIndex].length > 0) {
                followUpGroups.add(groups[groupIndex]);
            }
        }

        int maxOpeningSteps = Math.min(MAX_RANDOM_OPENING_STEPS, openingStepCount);
        Random random = new Random(0xA7C0B);
        for (int combo = 0; combo < RANDOM_COMBAT_CHAIN_COUNT; combo++) {
            int openingSteps = maxOpeningSteps == 1 ? 1 : 1 + random.nextInt(maxOpeningSteps);
            int followUpSteps = followUpGroups.isEmpty() ? 0 : RANDOM_FOLLOW_UP_STEPS;
            CombatChainStep[] chain = new CombatChainStep[openingSteps + followUpSteps];
            int index = 0;

            for (int openingIndex = 0; openingIndex < openingSteps; openingIndex++) {
                CombatChainStep[] group = openingGroups.get(random.nextInt(openingGroups.size()));
                chain[index++] = group[random.nextInt(group.length)];
            }

            for (int followUpIndex = 0; followUpIndex < followUpSteps; followUpIndex++) {
                CombatChainStep[] group = followUpGroups.get(random.nextInt(followUpGroups.size()));
                chain[index++] = group[random.nextInt(group.length)];
            }

            root = root.addFirstBehavior(combatChain(customConditions, chain));
        }

        return root;
    }

    @SafeVarargs
    private static CECombatBehaviors.Behavior.Builder<MobPatch<?>> combatChain(
            MobPatchCondition[] customConditions,
            CombatChainStep... steps
    ) {
        CECombatBehaviors.Behavior.Builder<MobPatch<?>> chain = combatStep(steps[steps.length - 1], steps.length - 1, customConditions);

        for (int i = steps.length - 2; i >= 0; i--) {
            chain = combatStep(steps[i], i, customConditions).addNextBehavior(chain);
        }

        return chain;
    }

    private static CECombatBehaviors.Behavior.Builder<MobPatch<?>> combatStep(
            CombatChainStep step,
            int index,
            MobPatchCondition[] customConditions
    ) {
        double maxDistance = step.maxDistance != null ? step.maxDistance : index < 2 ? 3.0D : index < 4 ? 4.0D : 5.0D;
        CECombatBehaviors.Behavior.Builder<MobPatch<?>> builder = applyCombatConditions(
                CECombatBehaviors.Behavior.builder(),
                customConditions,
                step.customConditions
        ).withinDistance(0.0D, maxDistance);

        if (step.guardTicks >= 0) {
            return builder.guard(step.guardTicks);
        }

        return builder.animationBehavior(step.animation, 0.0F);
    }

    private static CECombatBehaviors.Behavior.Builder<MobPatch<?>> animationStep(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation,
            double minDistance,
            double maxDistance,
            MobPatchCondition[] customConditions
    ) {
        return applyCombatConditions(
                CECombatBehaviors.Behavior.builder(),
                customConditions
        ).withinDistance(minDistance, maxDistance).animationBehavior(animation, 0.0F);
    }

    private static CECombatBehaviors.Behavior.Builder<MobPatch<?>> applyCombatConditions(
            CECombatBehaviors.Behavior.Builder<MobPatch<?>> builder,
            MobPatchCondition[]... conditionGroups
    ) {
        builder = builder.custom(CombatCommon::canPerformNormalAttackLogic);
        for (MobPatchCondition[] conditionGroup : conditionGroups) {
            for (MobPatchCondition condition : conditionGroup) {
                builder = builder.custom(condition);
            }
        }
        return builder;
    }

    private static CombatChainStep[] toSteps(AnimationManager.AnimationAccessor<? extends StaticAnimation>[] animations) {
        CombatChainStep[] steps = new CombatChainStep[animations.length];
        for (int i = 0; i < animations.length; i++) {
            steps[i] = animation(animations[i]);
        }
        return steps;
    }

    @FunctionalInterface
    public interface MobPatchCondition extends Function<MobPatch<?>, Boolean> {
    }

    public static final class CombatChainStep {
        private final AnimationManager.AnimationAccessor<? extends StaticAnimation> animation;
        private final int guardTicks;
        private final Double maxDistance;
        private final MobPatchCondition[] customConditions;

        private CombatChainStep(
                AnimationManager.AnimationAccessor<? extends StaticAnimation> animation,
                int guardTicks,
                Double maxDistance,
                MobPatchCondition[] customConditions
        ) {
            this.animation = animation;
            this.guardTicks = guardTicks;
            this.maxDistance = maxDistance;
            this.customConditions = customConditions;
        }
    }
}
