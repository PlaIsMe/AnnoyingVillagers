package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.task.MobExecutionTask;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.EscapeUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.efkick.gameasset.EFKickAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import net.shelmarow.combat_evolution.tickTask.TickTaskManager;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

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
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(mobpatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (dynamicAnimation.get() instanceof ExecutionAttackAnimation || dynamicAnimation.get() instanceof ExecutionHitAnimation) {
            return false;
        }
        if (EscapeUtil.checkEscape(entity)) {
            if (entity instanceof HerobrineMob || entity instanceof BlueDemonEntity) {
                return true;
            } else if (entity instanceof AVNpc AVNpc
                    && new Random().nextDouble() <= AVNpc.getPlaceBlockToParryChance()) {
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

    public static void placeRandomFrontWall(MobPatch<?> mobpatch) {
        final Mob mob = mobpatch.getOriginal();
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

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

            final BlockPos baseXZ = mob.blockPosition().relative(dir, dist);
            final int topY = Mth.floor(mob.getY() + mob.getBbHeight());

            final int surfaceY = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, baseXZ).getY();
            final BlockPos projXZ = new BlockPos(baseXZ.getX(), 0, baseXZ.getZ());

            for (int y = surfaceY; y <= topY; y++) {
                final int layer = y - surfaceY;

                final BlockPos center = new BlockPos(projXZ.getX(), y, projXZ.getZ());
                if (!serverLevel.getBlockState(center).canBeReplaced()) break;

                placeIfReplaceable(serverLevel, center, placeState, mobpatch, mob);

                int[][] extrasLocal = switch (pattern) {
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

                for (int[] ab : extrasLocal) {
                    int[] dzdx = toWorld.apply(ab[0], ab[1]);
                    int dx = dzdx[0];
                    int dz = dzdx[1];

                    BlockPos p = center.offset(dx, 0, dz);
                    placeIfReplaceable(serverLevel, p, placeState, mobpatch, mob);
                }
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

    private static void placeIfReplaceable(ServerLevel level, BlockPos pos, BlockState state, MobPatch<?> mobpatch, Mob mob) {
        if (mob instanceof HerobrineMob) {
            HerobrineUtil.placeIfReplaceable(level, pos, state, mob);
        } else {
            if (!level.getBlockState(pos).canBeReplaced()) return;
            mobpatch.playAnimationSynchronized(AVAnimations.PLACE_BLOCK, 0.0F);
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
                    if (isGroundWithin(mob, 3.0)) {
                        placeRandomFrontWall(mobpatch);
                    }
                }
            };
        }
    }

    private static boolean isGroundWithin(Entity e, double maxGap) {
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
        }
    }

    public static void swapToBlock(MobPatch<?> mobpatch) {
        LivingEntity entity = mobpatch.getOriginal();
        if (entity instanceof PlayerNpcEntity || entity instanceof AVNpc) {
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
        return animations(
                EFKickAnimations.KICK_1,
                EFKickAnimations.KICK_2,
                EFKickAnimations.KICK_3,
                EFKickAnimations.KICK_4,
                EFKickAnimations.KICK_C,
                EFKickAnimations.KICK_RUSH,
                EFKickAnimations.KICK_H
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] fistKickAnimations() {
        return animations(
                EFKickAnimations.KICK_1,
                EFKickAnimations.KICK_2,
                EFKickAnimations.KICK_3,
                EFKickAnimations.KICK_4,
                EFKickAnimations.KICK_C,
                EFKickAnimations.KICK_RUSH,
                EFKickAnimations.KICK_COMBO
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] basicKickAnimations() {
        return animations(
                EFKickAnimations.KICK_1,
                EFKickAnimations.KICK_2,
                EFKickAnimations.KICK_3,
                EFKickAnimations.KICK_4
        );
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
