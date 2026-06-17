package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransporterFragmentItem extends Item {
    private static final int PORTAL_COUNT = 6;
    private static final int MAX_ACTIVE_PORTALS_PER_OWNER = 6;
    private static final double LOOK_PORTAL_RANGE = 32.0D;
    private static final int HORIZONTAL_SEARCH_RADIUS = 30;
    private static final int VERTICAL_SEARCH_RADIUS = 15;
    private static final int TARGET_PRIORITY_RADIUS = 16;
    private static final double MIN_PORTAL_GAP = 3.0D;
    private static final double MAX_PORTAL_GAP = 6.0D;
    private static final double TARGET_CLUSTER_DISTANCE = 8.0D;
    private static final double CASTER_PORTAL_MIN_DISTANCE = 3.0D;
    private static final double CASTER_PORTAL_MAX_DISTANCE = 5.0D;
    private static final int COOLDOWN_TICKS = 20;

    public TransporterFragmentItem() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    public static UseResult tryUseSpecialAttack(Player player) {
        Item transporterFragment = AnnoyingVillagersModItems.TRANSPORTER_FRAGMENT.get();
        UseMode mode = getUseMode(player, transporterFragment);
        if (mode == UseMode.NONE) {
            return UseResult.missed();
        }

        if (player.getCooldowns().isOnCooldown(transporterFragment)) {
            return UseResult.consumed(mode, false);
        }

        boolean activated = false;
        if (player.level() instanceof ServerLevel serverLevel) {
            List<PortalEntity> activePortals = findOwnedActivePortals(serverLevel, player);
            int requestedPortals = mode == UseMode.BOTH_HANDS ? PORTAL_COUNT : 1;
            if (activePortals.size() + requestedPortals > MAX_ACTIVE_PORTALS_PER_OWNER) {
                return UseResult.consumed(mode, false);
            }

            int spawned = mode == UseMode.BOTH_HANDS
                    ? spawnPortalPairs(serverLevel, player)
                    : spawnLookPortal(serverLevel, player, activePortals);
            if (spawned > 0) {
                player.getCooldowns().addCooldown(transporterFragment, COOLDOWN_TICKS);
                activated = true;
            }
        }

        return UseResult.consumed(mode, activated);
    }

    private static UseMode getUseMode(Player player, Item transporterFragment) {
        boolean hasMainHandFragment = player.getMainHandItem().is(transporterFragment);
        boolean hasOffHandFragment = player.getOffhandItem().is(transporterFragment);

        if (hasMainHandFragment && hasOffHandFragment) {
            return UseMode.BOTH_HANDS;
        }
        if (hasMainHandFragment) {
            return UseMode.MAIN_HAND;
        }
        if (hasOffHandFragment) {
            return UseMode.OFF_HAND;
        }
        return UseMode.NONE;
    }

    public static int spawnPortalPairs(Level level, LivingEntity caster) {
        if (level instanceof ServerLevel serverLevel
                && findOwnedActivePortals(serverLevel, caster).size() + PORTAL_COUNT > MAX_ACTIVE_PORTALS_PER_OWNER) {
            return 0;
        }

        RandomSource random = level.getRandom();
        List<LivingEntity> priorityTargets = clusterPriorityTargets(findPriorityTargets(level, caster));
        List<Vec3> portalPositions = buildPortalPositions(level, caster, priorityTargets, PORTAL_COUNT, random);
        UUID portalGroup = UUID.randomUUID();
        int spawned = 0;

        for (int order = 0; order < portalPositions.size(); order += 2) {
            if (order + 1 < portalPositions.size()
                    && spawnPair(level, caster, portalGroup, order, portalPositions.get(order), portalPositions.get(order + 1))) {
                spawned += 2;
            } else if (order == portalPositions.size() - 1
                    && spawnSinglePortal(level, caster, portalGroup, order, portalPositions.get(order), yawFacing(portalPositions.get(order), caster.getEyePosition()), order == 0)) {
                spawned++;
            }
        }

        return spawned;
    }

    private static int spawnLookPortal(ServerLevel level, Player caster, List<PortalEntity> activePortals) {
        LookPortalTarget target = findLookPortalTarget(level, caster);
        Vec3 portalPos = findLookPortalPosition(level, target.portalPos);
        if (portalPos == null) {
            return 0;
        }

        PortalEntity pendingPortal = findPendingPortal(activePortals);
        UUID portalGroup = selectPortalGroup(activePortals, pendingPortal);
        int portalOrder = nextPortalOrder(activePortals);
        float yaw = yawFacing(portalPos, target.facingTarget);
        PortalEntity portal = createPortal(level, caster, portalGroup, portalOrder, portalPos, yaw, portalOrder == 0);
        if (portal == null) {
            return 0;
        }

        if (pendingPortal != null) {
            linkPortalPair(pendingPortal, portal, portalGroup);
        }

        return 1;
    }

    private static LookPortalTarget findLookPortalTarget(ServerLevel level, Player caster) {
        Vec3 eyePos = caster.getEyePosition(1.0F);
        Vec3 look = caster.getLookAngle();
        Vec3 maxPos = eyePos.add(look.scale(LOOK_PORTAL_RANGE));
        BlockHitResult blockHit = level.clip(new ClipContext(
                eyePos,
                maxPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                caster
        ));

        double blockDistanceSqr = blockHit.getType() == HitResult.Type.BLOCK
                ? eyePos.distanceToSqr(blockHit.getLocation())
                : LOOK_PORTAL_RANGE * LOOK_PORTAL_RANGE;

        LookEntityHit entityHit = findLookEntity(level, caster, eyePos, maxPos);
        if (entityHit != null && entityHit.distanceSqr <= blockDistanceSqr) {
            return new LookPortalTarget(new Vec3(entityHit.entity.getX(), entityHit.entity.getY(), entityHit.entity.getZ()), getEntityCenter(entityHit.entity));
        }

        if (blockHit.getType() == HitResult.Type.BLOCK) {
            BlockPos spawnBlock = blockHit.getBlockPos().relative(blockHit.getDirection());
            return new LookPortalTarget(Vec3.atBottomCenterOf(spawnBlock), eyePos);
        }

        return new LookPortalTarget(maxPos, eyePos);
    }

    private static LookEntityHit findLookEntity(ServerLevel level, Player caster, Vec3 start, Vec3 end) {
        AABB searchBox = caster.getBoundingBox().expandTowards(end.subtract(start)).inflate(1.0D);
        Entity closestEntity = null;
        double closestDistanceSqr = LOOK_PORTAL_RANGE * LOOK_PORTAL_RANGE;

        for (Entity entity : level.getEntities(caster, searchBox, TransporterFragmentItem::canLookTargetEntity)) {
            AABB targetBox = entity.getBoundingBox().inflate(Math.max(0.3D, entity.getPickRadius()));
            Optional<Vec3> clip = targetBox.clip(start, end);
            Vec3 hitPos = null;

            if (targetBox.contains(start)) {
                hitPos = start;
            } else if (clip.isPresent()) {
                hitPos = clip.get();
            }

            if (hitPos == null) {
                continue;
            }

            double distanceSqr = start.distanceToSqr(hitPos);
            if (distanceSqr < closestDistanceSqr) {
                closestEntity = entity;
                closestDistanceSqr = distanceSqr;
            }
        }

        return closestEntity == null ? null : new LookEntityHit(closestEntity, closestDistanceSqr);
    }

    private static boolean canLookTargetEntity(Entity entity) {
        return entity.isAlive() && !entity.isSpectator() && entity.isPickable();
    }

    private static Vec3 findLookPortalPosition(Level level, Vec3 preferredPos) {
        Vec3 base = snapPortalPosition(preferredPos);
        if (isLookPortalPositionValid(level, base)) {
            return base;
        }

        for (int radius = 1; radius <= 3; radius++) {
            for (int dy = -1; dy <= 2; dy++) {
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        if (Math.abs(dx) != radius && Math.abs(dz) != radius) {
                            continue;
                        }

                        Vec3 candidate = base.add(dx, dy, dz);
                        if (isLookPortalPositionValid(level, candidate)) {
                            return candidate;
                        }
                    }
                }
            }
        }

        return null;
    }

    private static Vec3 snapPortalPosition(Vec3 pos) {
        return new Vec3(Math.floor(pos.x) + 0.5D, Math.floor(pos.y), Math.floor(pos.z) + 0.5D);
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

    private static boolean isLookPortalPositionValid(Level level, Vec3 pos) {
        return level.getWorldBorder().isWithinBounds(BlockPos.containing(pos)) && isAreaClear(level, pos);
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

    private static boolean spawnSinglePortal(Level level, LivingEntity caster, UUID portalGroup, int order, Vec3 pos, float yaw, boolean starterPortal) {
        return createPortal(level, caster, portalGroup, order, pos, yaw, starterPortal) != null;
    }

    private static PortalEntity createPortal(Level level, LivingEntity caster, UUID portalGroup, int order, Vec3 pos, float yaw, boolean starterPortal) {
        PortalEntity portal = AnnoyingVillagersModEntities.PORTAL.get().create(level);
        if (portal == null) {
            return null;
        }

        portal.setOwnerUUID(caster.getUUID());
        portal.setPortalGroupUUID(portalGroup);
        portal.setPortalOrder(order);
        portal.setStarterPortal(starterPortal);

        placePortal(portal, pos, yaw);
        level.addFreshEntity(portal);
        return portal;
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
        setPortalYaw(portal, yaw);
    }

    private static void setPortalYaw(PortalEntity portal, float yaw) {
        portal.setYRot(yaw);
        portal.yRotO = yaw;
    }

    private static float yawFacing(Vec3 from, Vec3 to) {
        Vec3 delta = to.subtract(from);
        return (float) (Mth.atan2(-delta.x, delta.z) * Mth.RAD_TO_DEG);
    }

    private static Vec3 getEntityCenter(Entity entity) {
        return new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() * 0.5D, entity.getZ());
    }

    private static List<PortalEntity> findOwnedActivePortals(ServerLevel level, LivingEntity caster) {
        List<PortalEntity> portals = new ArrayList<>();
        UUID owner = caster.getUUID();

        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof PortalEntity portal
                    && !portal.isRemoved()
                    && portal.isAlive()
                    && portal.tickCount < PortalEntity.LIFETIME_TICKS
                    && owner.equals(portal.getOwnerUUID())) {
                portals.add(portal);
            }
        }

        portals.sort(Comparator
                .comparingInt(PortalEntity::getPortalOrder)
                .thenComparingInt(Entity::getId));
        return portals;
    }

    private static PortalEntity findPendingPortal(List<PortalEntity> activePortals) {
        PortalEntity pendingPortal = null;
        for (PortalEntity portal : activePortals) {
            if (portal.getLinkedPortalUUID() != null) {
                continue;
            }

            if (pendingPortal == null || portal.getPortalOrder() > pendingPortal.getPortalOrder()) {
                pendingPortal = portal;
            }
        }
        return pendingPortal;
    }

    private static UUID selectPortalGroup(List<PortalEntity> activePortals, PortalEntity pendingPortal) {
        if (pendingPortal != null && pendingPortal.getPortalGroupUUID() != null) {
            return pendingPortal.getPortalGroupUUID();
        }

        for (int i = activePortals.size() - 1; i >= 0; i--) {
            UUID portalGroup = activePortals.get(i).getPortalGroupUUID();
            if (portalGroup != null) {
                return portalGroup;
            }
        }

        return UUID.randomUUID();
    }

    private static int nextPortalOrder(List<PortalEntity> activePortals) {
        int nextOrder = 0;
        for (PortalEntity portal : activePortals) {
            nextOrder = Math.max(nextOrder, portal.getPortalOrder() + 1);
        }
        return nextOrder;
    }

    private static void linkPortalPair(PortalEntity first, PortalEntity second, UUID portalGroup) {
        first.setLinkedPortalUUID(second.getUUID());
        second.setLinkedPortalUUID(first.getUUID());
        first.setPortalGroupUUID(portalGroup);
        second.setPortalGroupUUID(portalGroup);
        setPortalYaw(first, yawFacing(first.position(), second.position()));
        setPortalYaw(second, yawFacing(second.position(), first.position()));
    }

    private static final class LookPortalTarget {
        private final Vec3 portalPos;
        private final Vec3 facingTarget;

        private LookPortalTarget(Vec3 portalPos, Vec3 facingTarget) {
            this.portalPos = portalPos;
            this.facingTarget = facingTarget;
        }
    }

    private static final class LookEntityHit {
        private final Entity entity;
        private final double distanceSqr;

        private LookEntityHit(Entity entity, double distanceSqr) {
            this.entity = entity;
            this.distanceSqr = distanceSqr;
        }
    }

    public enum UseMode {
        NONE,
        MAIN_HAND,
        OFF_HAND,
        BOTH_HANDS
    }

    public static final class UseResult {
        private static final UseResult MISSED = new UseResult(false, false, UseMode.NONE);

        private final boolean consumed;
        private final boolean activated;
        private final UseMode mode;

        private UseResult(boolean consumed, boolean activated, UseMode mode) {
            this.consumed = consumed;
            this.activated = activated;
            this.mode = mode;
        }

        public static UseResult missed() {
            return MISSED;
        }

        public static UseResult consumed(UseMode mode, boolean activated) {
            return new UseResult(true, activated, mode);
        }

        public boolean consumed() {
            return this.consumed;
        }

        public boolean activated() {
            return this.activated;
        }

        public UseMode mode() {
            return this.mode;
        }
    }
}
