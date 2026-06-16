package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class PowerItem extends Item {
    private static final int PORTAL_COUNT_LOW = 4;
    private static final int PORTAL_COUNT_HIGH = 6;
    private static final int HORIZONTAL_SEARCH_RADIUS = 30;
    private static final int VERTICAL_SEARCH_RADIUS = 15;
    private static final int TARGET_PRIORITY_RADIUS = 16;
    private static final double MIN_PORTAL_GAP = 3.0D;
    private static final double MAX_PORTAL_GAP = 6.0D;
    private static final double TARGET_CLUSTER_DISTANCE = 8.0D;
    private static final double CASTER_PORTAL_MIN_DISTANCE = 3.0D;
    private static final double CASTER_PORTAL_MAX_DISTANCE = 5.0D;
    private static final int COOLDOWN_TICKS = 80;

    public PowerItem() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            spawnPortalPairs(level, player);
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public static int spawnPortalPairs(Level level, LivingEntity caster) {
        RandomSource random = level.getRandom();
        int portalCount = random.nextBoolean() ? PORTAL_COUNT_LOW : PORTAL_COUNT_HIGH;
        List<LivingEntity> priorityTargets = clusterPriorityTargets(findPriorityTargets(level, caster));
        List<Vec3> portalPositions = buildPortalPositions(level, caster, priorityTargets, portalCount, random);
        UUID portalGroup = UUID.randomUUID();
        int spawned = 0;

        for (int order = 0; order + 1 < portalPositions.size(); order += 2) {
            if (spawnPair(level, caster, portalGroup, order, portalPositions.get(order), portalPositions.get(order + 1))) {
                spawned += 2;
            }
        }

        return spawned;
    }

    private static List<LivingEntity> findPriorityTargets(Level level, LivingEntity attacker) {
        List<LivingEntity> targets = new ArrayList<>();

        for (Entity entity : level.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(TARGET_PRIORITY_RADIUS))) {
            if (!entity.equals(attacker)
                    && !attacker.isAlliedTo(entity)
                    && !entity.isAlliedTo(attacker)
                    && !entity.isSpectator()
                    && !(entity instanceof Player player && player.isCreative())
                    && (entity instanceof Mob || entity instanceof Player)
                    && attacker.hasLineOfSight(entity)) {
                targets.add((LivingEntity) entity);
            }
        }

        targets.sort(Comparator.comparingDouble(attacker::distanceTo));
        return targets;
    }

    private static List<LivingEntity> clusterPriorityTargets(List<LivingEntity> targets) {
        List<LivingEntity> clusteredTargets = new ArrayList<>();

        for (LivingEntity target : targets) {
            boolean joinedExistingCluster = false;
            for (LivingEntity clusteredTarget : clusteredTargets) {
                if (target.distanceTo(clusteredTarget) <= TARGET_CLUSTER_DISTANCE) {
                    joinedExistingCluster = true;
                    break;
                }
            }

            if (!joinedExistingCluster) {
                clusteredTargets.add(target);
            }
        }

        return clusteredTargets;
    }

    private static List<Vec3> buildPortalPositions(
            Level level,
            LivingEntity caster,
            List<LivingEntity> priorityTargets,
            int portalCount,
            RandomSource random
    ) {
        List<Vec3> positions = new ArrayList<>();
        Vec3 casterPortal = findCasterPortalPosition(level, caster, positions, random);
        if (casterPortal == null) {
            return positions;
        }

        positions.add(casterPortal);

        int targetIndex = 0;
        while (positions.size() < portalCount) {
            Vec3 candidate = null;

            boolean exitSlot = positions.size() % 2 == 1;
            if (exitSlot && targetIndex < priorityTargets.size()) {
                candidate = findPortalNearTarget(level, caster, priorityTargets.get(targetIndex), positions, random);
                targetIndex++;
            }

            if (candidate == null) {
                candidate = findRandomDistributedPortal(level, caster, positions, random, positions.size());
            }

            if (candidate == null) {
                break;
            }

            positions.add(candidate);
        }

        return positions;
    }

    private static Vec3 findCasterPortalPosition(Level level, LivingEntity caster, List<Vec3> usedPositions, RandomSource random) {
        for (int attempt = 0; attempt < 80; attempt++) {
            double angle = caster.getYRot() * Mth.DEG_TO_RAD + (attempt < 8
                    ? (Math.PI * 2.0D / 8.0D) * attempt
                    : random.nextDouble() * Math.PI * 2.0D);
            double distance = CASTER_PORTAL_MIN_DISTANCE + random.nextDouble() * (CASTER_PORTAL_MAX_DISTANCE - CASTER_PORTAL_MIN_DISTANCE);
            double y = Math.floor(caster.getY()) + (attempt > 30 ? random.nextInt(VERTICAL_SEARCH_RADIUS + 1) : random.nextInt(4));
            Vec3 candidate = new Vec3(
                    caster.getX() - Math.sin(angle) * distance,
                    Mth.clamp(y, Math.floor(caster.getY()), Math.floor(caster.getY()) + VERTICAL_SEARCH_RADIUS),
                    caster.getZ() + Math.cos(angle) * distance
            );

            if (isValidPortalPosition(level, caster, candidate, usedPositions)) {
                return candidate;
            }
        }

        return findRandomDistributedPortal(level, caster, usedPositions, random, 0);
    }

    private static Vec3 findPortalNearTarget(
            Level level,
            LivingEntity caster,
            LivingEntity target,
            List<Vec3> usedPositions,
            RandomSource random
    ) {
        for (int attempt = 0; attempt < 32; attempt++) {
            Vec3 candidate = randomPositionNearEntity(caster, target, random);
            if (isValidPortalPosition(level, caster, candidate, usedPositions)) {
                return candidate;
            }
        }

        return null;
    }

    private static Vec3 findRandomDistributedPortal(
            Level level,
            LivingEntity caster,
            List<Vec3> usedPositions,
            RandomSource random,
            int slotIndex
    ) {
        for (int attempt = 0; attempt < 140; attempt++) {
            Vec3 candidate = randomDistributedPositionAroundCaster(caster, random, slotIndex, attempt);
            if (isValidPortalPosition(level, caster, candidate, usedPositions)) {
                return candidate;
            }
        }

        for (int attempt = 0; attempt < 120; attempt++) {
            Vec3 candidate = randomPositionAroundCaster(caster, random);
            if (isValidPortalPosition(level, caster, candidate, usedPositions)) {
                return candidate;
            }
        }

        return null;
    }

    private static Vec3 randomDistributedPositionAroundCaster(LivingEntity caster, RandomSource random, int slotIndex, int attempt) {
        int distanceTier = (slotIndex + attempt) % 3;
        double distance;
        if (attempt > 90) {
            distance = 4.0D + random.nextDouble() * 25.0D;
        } else if (distanceTier == 0) {
            distance = 5.0D + random.nextDouble() * 6.0D;
        } else if (distanceTier == 1) {
            distance = 12.0D + random.nextDouble() * 7.0D;
        } else {
            distance = 20.0D + random.nextDouble() * 9.0D;
        }

        double angle = random.nextDouble() * Math.PI * 2.0D;
        double y = Math.floor(caster.getY()) + random.nextInt(VERTICAL_SEARCH_RADIUS + 1);

        return new Vec3(
                caster.getX() + Math.cos(angle) * distance,
                Mth.clamp(y, Math.floor(caster.getY()), Math.floor(caster.getY()) + VERTICAL_SEARCH_RADIUS),
                caster.getZ() + Math.sin(angle) * distance
        );
    }

    private static Vec3 randomPositionNearEntity(LivingEntity caster, LivingEntity target, RandomSource random) {
        double angle = random.nextDouble() * Math.PI * 2.0D;
        double distance = MIN_PORTAL_GAP + random.nextDouble() * (MAX_PORTAL_GAP - MIN_PORTAL_GAP);
        double y = Math.max(Math.floor(caster.getY()), Math.floor(target.getY()));

        return new Vec3(
                target.getX() + Math.cos(angle) * distance,
                Mth.clamp(y, Math.floor(caster.getY()), Math.floor(caster.getY()) + VERTICAL_SEARCH_RADIUS),
                target.getZ() + Math.sin(angle) * distance
        );
    }

    private static Vec3 randomPositionAroundCaster(LivingEntity caster, RandomSource random) {
        return new Vec3(
                caster.getX() + random.nextInt(HORIZONTAL_SEARCH_RADIUS * 2 + 1) - HORIZONTAL_SEARCH_RADIUS,
                Math.floor(caster.getY()) + random.nextInt(VERTICAL_SEARCH_RADIUS + 1),
                caster.getZ() + random.nextInt(HORIZONTAL_SEARCH_RADIUS * 2 + 1) - HORIZONTAL_SEARCH_RADIUS
        );
    }

    private static boolean isValidPortalPosition(Level level, LivingEntity caster, Vec3 pos, List<Vec3> usedPositions) {
        if (pos.y < Math.floor(caster.getY()) || pos.y > Math.floor(caster.getY()) + VERTICAL_SEARCH_RADIUS) {
            return false;
        }
        if (Math.abs(pos.x - caster.getX()) > HORIZONTAL_SEARCH_RADIUS || Math.abs(pos.z - caster.getZ()) > HORIZONTAL_SEARCH_RADIUS) {
            return false;
        }
        if (!level.getWorldBorder().isWithinBounds(BlockPos.containing(pos))) {
            return false;
        }
        if (!isFarEnoughFromExisting(pos, usedPositions)) {
            return false;
        }
        return isAreaClear(level, pos);
    }

    private static boolean isFarEnoughFromExisting(Vec3 pos, List<Vec3> usedPositions) {
        for (Vec3 used : usedPositions) {
            if (used.distanceTo(pos) < MIN_PORTAL_GAP) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAreaClear(Level level, Vec3 pos) {
        if (pos.y < level.getMinBuildHeight() || pos.y + PortalEntity.HEIGHT + 1.0D >= level.getMaxBuildHeight()) {
            return false;
        }

        AABB portalBox = new AABB(
                pos.x - PortalEntity.WIDTH * 0.5D,
                pos.y,
                pos.z - PortalEntity.WIDTH * 0.5D,
                pos.x + PortalEntity.WIDTH * 0.5D,
                pos.y + PortalEntity.HEIGHT,
                pos.z + PortalEntity.WIDTH * 0.5D
        );
        if (!level.noCollision(portalBox)) {
            return false;
        }

        BlockPos min = BlockPos.containing(pos.x - 2.0D, pos.y, pos.z - 2.0D);
        BlockPos max = BlockPos.containing(pos.x + 2.0D, pos.y + PortalEntity.HEIGHT, pos.z + 2.0D);
        for (BlockPos checkPos : BlockPos.betweenClosed(min, max)) {
            BlockState state = level.getBlockState(checkPos);
            if (!state.isAir() || !level.getFluidState(checkPos).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private static boolean spawnPair(Level level, LivingEntity caster, UUID portalGroup, int firstOrder, Vec3 firstPos, Vec3 secondPos) {
        PortalEntity first = AnnoyingVillagersModEntities.PORTAL.get().create(level);
        PortalEntity second = AnnoyingVillagersModEntities.PORTAL.get().create(level);
        if (first == null || second == null) {
            return false;
        }

        UUID owner = caster.getUUID();
        float firstYaw = yawFacing(firstPos, secondPos);
        float secondYaw = yawFacing(secondPos, firstPos);

        first.setOwnerUUID(owner);
        second.setOwnerUUID(owner);
        first.setLinkedPortalUUID(second.getUUID());
        second.setLinkedPortalUUID(first.getUUID());
        first.setPortalGroupUUID(portalGroup);
        second.setPortalGroupUUID(portalGroup);
        first.setPortalOrder(firstOrder);
        second.setPortalOrder(firstOrder + 1);
        first.setStarterPortal(firstOrder == 0);
        second.setStarterPortal(false);

        placePortal(first, firstPos, firstYaw);
        placePortal(second, secondPos, secondYaw);

        level.addFreshEntity(first);
        level.addFreshEntity(second);
        return true;
    }

    private static void placePortal(PortalEntity portal, Vec3 pos, float yaw) {
        portal.setPos(pos.x, pos.y, pos.z);
        portal.setYRot(yaw);
        portal.yRotO = yaw;
    }

    private static float yawFacing(Vec3 from, Vec3 to) {
        Vec3 delta = to.subtract(from);
        return (float) (Mth.atan2(-delta.x, delta.z) * Mth.RAD_TO_DEG);
    }
}
