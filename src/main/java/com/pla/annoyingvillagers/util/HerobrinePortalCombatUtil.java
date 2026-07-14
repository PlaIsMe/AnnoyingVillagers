package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.entity.LowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.entity.TransporterHerobrineCloneEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class HerobrinePortalCombatUtil {
    private static final double WALK_ENTRANCE_RADIUS = 32.0D;
    private static final double WALK_EXIT_TARGET_RADIUS = 14.0D;
    private static final double PROJECTILE_ENTRANCE_RADIUS = 24.0D;
    private static final double PROJECTILE_EXIT_TARGET_RADIUS = 18.0D;
    private static final double COUNTER_BOW_THREAT_RADIUS = 30.0D;
    private static final double COUNTER_BOW_MIN_TARGET_DISTANCE_SQR = 4.0D * 4.0D;
    private static final double COUNTER_BOW_EXIT_DISTANCE = 3.0D;
    private static final double COUNTER_BOW_AIM_DOT_THRESHOLD = 0.9D;
    private static final double SUPPORT_HEROBRINE_RADIUS = 36.0D;
    private static final double SUPPORT_ENEMY_RADIUS = 64.0D;
    private static final double GREG_SUPPORT_PORTAL_ENEMY_DISTANCE_SQR = 10.0D * 10.0D;
    private static final double SUPPORT_GATHER_DISTANCE_SQR = 14.0D * 14.0D;
    private static final double VANILLA_ESCAPE_CLOSE_THREAT_SQR = 5.5D * 5.5D;
    private static final double VANILLA_ESCAPE_LOW_HEALTH_RATIO = 0.45D;

    private HerobrinePortalCombatUtil() {
    }

    public record PortalRoute(PortalEntity entrance, PortalEntity exit) {
    }

    private record SupportTarget(LivingEntity support, @Nullable LivingEntity enemy, double enemyDistanceSqr) {
    }

    private record SupportPortalPlan(LivingEntity entrance, LivingEntity exit) {
    }

    private record BowCounterThreat(LivingEntity attacker, LivingEntity target) {
    }

    public static boolean isHerobrineSide(Entity entity) {
        return entity instanceof HerobrineMob
                || entity instanceof HerobrineGregEntity
                || entity instanceof LowHerobrineCloneEntity
                || entity instanceof LowShadowHerobrineCloneEntity
                || entity instanceof NullWeapon;
    }

    public static boolean isEnemyOf(LivingEntity caster, LivingEntity entity) {
        return entity != caster
                && entity.isAlive()
                && !entity.isSpectator()
                && !(entity instanceof Player player && player.isCreative())
                && !entity.isAlliedTo(caster)
                && !caster.isAlliedTo(entity)
                && !isHerobrineSide(entity);
    }

    public static boolean canUsePortalApproach(Mob mob) {
        if (!isHerobrineSide(mob)) {
            return false;
        }
        if (mob instanceof HerobrineDragonEntity) {
            return false;
        }
        if (mob.isPassenger() && mob.getVehicle() instanceof HerobrineDragonEntity) {
            return false;
        }
        return !(mob instanceof NullWeapon nullWeapon) || nullWeapon.isReleased();
    }

    public static boolean canUsePortalOwnedBy(LivingEntity user, @Nullable UUID ownerUuid) {
        if (ownerUuid == null || ownerUuid.equals(user.getUUID())) {
            return true;
        }
        if (!(user.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        Entity owner = serverLevel.getEntity(ownerUuid);
        return owner != null && isHerobrineSide(user) && isHerobrineSide(owner);
    }

    public static boolean isVanillaEscapePressure(Mob mob) {
        LivingEntity target = mob.getTarget();
        if (target == null || !target.isAlive() || !isEnemyOf(mob, target)) {
            return false;
        }
        if (mob.getHealth() <= mob.getMaxHealth() * VANILLA_ESCAPE_LOW_HEALTH_RATIO) {
            return true;
        }
        if (mob.distanceToSqr(target) <= VANILLA_ESCAPE_CLOSE_THREAT_SQR) {
            return true;
        }
        return mob.getLastHurtByMob() == target || target.getLastHurtMob() == mob;
    }

    @Nullable
    public static PortalRoute findRouteToTarget(Mob mob, LivingEntity target) {
        if (!canUsePortalApproach(mob)) {
            return null;
        }
        return findRouteNearEntity(mob, target, WALK_ENTRANCE_RADIUS, WALK_EXIT_TARGET_RADIUS, true);
    }

    @Nullable
    public static Vec3 getProjectilePortalAim(Entity shooter, LivingEntity target) {
        PortalRoute route = findRouteNearEntity(shooter, target, PROJECTILE_ENTRANCE_RADIUS, PROJECTILE_EXIT_TARGET_RADIUS, false);
        return route == null ? null : route.entrance().getPortalCenter();
    }

    @Nullable
    private static PortalRoute findRouteNearEntity(Entity source, LivingEntity target, double entranceRadius, double exitRadius, boolean walkingRoute) {
        if (!(source.level() instanceof ServerLevel) || target == null || !target.isAlive()) {
            return null;
        }
        if (walkingRoute && source instanceof Mob mob && !canUsePortalApproach(mob)) {
            return null;
        }

        AABB searchBox = source.getBoundingBox().inflate(entranceRadius);
        Vec3 sourceCenter = source.position().add(0.0D, source.getBbHeight() * 0.5D, 0.0D);
        Vec3 targetCenter = entityCenter(target);
        double directTargetDistance = sourceCenter.distanceToSqr(targetCenter);
        PortalRoute bestRoute = null;
        double bestScore = Double.MAX_VALUE;

        for (PortalEntity portal : source.level().getEntitiesOfClass(PortalEntity.class, searchBox)) {
            if (!isUsablePortalFor(source, portal)) {
                continue;
            }

            PortalEntity linkedPortal = portal.getLinkedPortal();
            if (linkedPortal == null || !isUsablePortalFor(source, linkedPortal)) {
                continue;
            }

            double exitDistance = linkedPortal.getPortalCenter().distanceToSqr(targetCenter);
            if (exitDistance > exitRadius * exitRadius) {
                continue;
            }

            double entranceDistance = portal.getPortalCenter().distanceToSqr(sourceCenter);
            if (walkingRoute && entranceDistance >= directTargetDistance) {
                continue;
            }

            double score = walkingRoute ? exitDistance + entranceDistance * 0.35D : entranceDistance + exitDistance * 0.35D;
            if (score < bestScore) {
                bestScore = score;
                bestRoute = new PortalRoute(portal, linkedPortal);
            }
        }

        return bestRoute;
    }

    private static boolean isUsablePortalFor(Entity user, PortalEntity portal) {
        if (portal == null || portal.isRemoved() || !portal.isAlive()) {
            return false;
        }

        UUID ownerUuid = portal.getOwnerUUID();
        if (ownerUuid == null || ownerUuid.equals(user.getUUID())) {
            return true;
        }
        if (!(user.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        Entity owner = serverLevel.getEntity(ownerUuid);
        if (owner == null) {
            return false;
        }
        if (user instanceof HerobrineDragonEntity) {
            return isHerobrineSide(owner);
        }
        return isHerobrineSide(user) && isHerobrineSide(owner);
    }

    public static boolean tryTransporterPortalSupport(LivingEntity caster) {
        if (!(caster.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) {
            return false;
        }
        LivingEntity fallbackEnemy = findNearestEnemy(caster, SUPPORT_ENEMY_RADIUS);
        SupportPortalPlan plan = pickSupportPortalPlan(caster, findSupportHerobrines(caster, SUPPORT_HEROBRINE_RADIUS), fallbackEnemy, true);
        if (plan == null) {
            return false;
        }
        return spawnSupportPortalPair(caster, plan.entrance(), plan.exit());
    }

    public static boolean canTransporterPortalSupport(LivingEntity caster) {
        if (!(caster.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) {
            return false;
        }
        LivingEntity fallbackEnemy = findNearestEnemy(caster, SUPPORT_ENEMY_RADIUS);
        SupportPortalPlan plan = pickSupportPortalPlan(caster, findSupportHerobrines(caster, SUPPORT_HEROBRINE_RADIUS), fallbackEnemy, true);
        return plan != null;
    }

    public static boolean tryGregPortalSupport(HerobrineGregEntity greg) {
        if (!(greg.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, greg, 2)) {
            return false;
        }
        List<LivingEntity> supports = findSupportHerobrines(greg, SUPPORT_HEROBRINE_RADIUS).stream()
                .filter(HerobrinePortalCombatUtil::canUseGregGeneralSupport)
                .toList();
        if (supports.isEmpty()) {
            return false;
        }

        SupportPortalPlan plan = pickSupportPortalPlan(greg, supports, findNearestEnemy(greg, SUPPORT_ENEMY_RADIUS), false);
        if (plan == null) {
            return false;
        }
        greg.markSupportingHerobrine();
        return spawnSupportPortalPair(greg, plan.entrance(), plan.exit());
    }

    public static boolean canGregPortalSupport(HerobrineGregEntity greg) {
        if (!(greg.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, greg, 2)) {
            return false;
        }
        List<LivingEntity> supports = findSupportHerobrines(greg, SUPPORT_HEROBRINE_RADIUS).stream()
                .filter(HerobrinePortalCombatUtil::canUseGregGeneralSupport)
                .toList();
        if (supports.isEmpty()) {
            return false;
        }

        SupportPortalPlan plan = pickSupportPortalPlan(greg, supports, findNearestEnemy(greg, SUPPORT_ENEMY_RADIUS), false);
        return plan != null;
    }

    private static boolean canUseGregGeneralSupport(LivingEntity support) {
        return !(support instanceof TransporterHerobrineCloneEntity)
                && !(support instanceof LowHerobrineCloneEntity)
                && !(support instanceof LowShadowHerobrineCloneEntity);
    }

    public static boolean tryBowCounterPortalSupport(LivingEntity caster) {
        if (!(caster.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) {
            return false;
        }

        BowCounterThreat threat = findBowCounterThreat(caster, COUNTER_BOW_THREAT_RADIUS);
        if (threat == null) {
            return false;
        }

        Vec3 entrancePreferred = buildBowCounterEntrance(threat.attacker(), threat.target());
        Vec3 exitPreferred = buildBowCounterExit(threat.attacker(), threat.target());
        if (entrancePreferred == null || exitPreferred == null) {
            return false;
        }

        if (TransporterFragmentItem.spawnLinkedPortalPair(caster.level(), caster, entrancePreferred, exitPreferred) <= 0) {
            return false;
        }

        playPortalPairSummon(caster);
        triggerCounterPortalRetreat(caster, threat.attacker());
        return true;
    }

    public static boolean canBowCounterPortalSupport(LivingEntity caster) {
        if (!(caster.level() instanceof ServerLevel serverLevel)
                || !TransporterFragmentItem.canSpawnOwnedPortals(serverLevel, caster, 2)) {
            return false;
        }

        BowCounterThreat threat = findBowCounterThreat(caster, COUNTER_BOW_THREAT_RADIUS);
        if (threat == null) {
            return false;
        }

        Vec3 entrancePreferred = buildBowCounterEntrance(threat.attacker(), threat.target());
        Vec3 exitPreferred = buildBowCounterExit(threat.attacker(), threat.target());
        return entrancePreferred != null && exitPreferred != null;
    }

    public static boolean spawnSupportPortalPair(LivingEntity caster, LivingEntity entranceEntity, LivingEntity exitEntity) {
        if (!(caster.level() instanceof ServerLevel)) {
            return false;
        }

        RandomSource random = caster.getRandom();
        Vec3 entrancePreferred = randomPortalPreferredPosNear(caster, entranceEntity, random, 2.8D, 5.0D);
        Vec3 exitPreferred = randomPortalPreferredPosNear(caster, exitEntity, random, 2.8D, 5.0D);
        int spawned = TransporterFragmentItem.spawnLinkedPortalPair(caster.level(), caster, entrancePreferred, exitPreferred);
        if (spawned <= 0) {
            return false;
        }

        playPortalPairSummon(caster);
        return true;
    }

    public static void playSixPortalSummon(LivingEntity entity) {
        if (entity instanceof HerobrineGregEntity greg) {
            greg.markSupportingHerobrine();
        }
        if (!entity.level().isClientSide()) {
            entity.level().playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.PORTAL_NATURAL.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
            entity.swing(InteractionHand.MAIN_HAND, true);
        }
    }

    public static void playPortalPairSummon(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
            entity.level().playSound(null, entity.blockPosition(), AnnoyingVillagersModSounds.PORTAL_NATURAL.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
            entity.swing(InteractionHand.MAIN_HAND, true);
        }
    }

    @Nullable
    public static LivingEntity findSupportHerobrine(LivingEntity caster, double radius) {
        List<LivingEntity> candidates = findSupportHerobrines(caster, radius);
        return candidates.isEmpty() ? null : candidates.get(0);
    }

    public static List<LivingEntity> findSupportHerobrines(LivingEntity caster, double radius) {
        AABB searchBox = caster.getBoundingBox().inflate(radius);
        List<LivingEntity> candidates = caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity ->
                entity != caster
                        && entity.isAlive()
                        && isHerobrineSide(entity)
                        && !(entity instanceof HerobrineGregEntity)
        );

        candidates.sort(Comparator.comparingDouble(caster::distanceToSqr));
        return candidates;
    }

    public static boolean hasNearbyPortalGroup(LivingEntity anchor, @Nullable UUID ownerUuid, int requiredCount, double radius) {
        if (requiredCount <= 0) {
            return true;
        }

        Map<UUID, Integer> portalGroupCounts = new HashMap<>();
        for (PortalEntity portal : anchor.level().getEntitiesOfClass(PortalEntity.class, anchor.getBoundingBox().inflate(radius))) {
            if (portal.isRemoved() || !portal.isAlive() || portal.tickCount >= PortalEntity.LIFETIME_TICKS) {
                continue;
            }

            UUID portalGroupUuid = portal.getPortalGroupUUID();
            if (portalGroupUuid == null) {
                continue;
            }
            if (ownerUuid != null && !ownerUuid.equals(portal.getOwnerUUID())) {
                continue;
            }

            int count = portalGroupCounts.merge(portalGroupUuid, 1, Integer::sum);
            if (count >= requiredCount) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public static LivingEntity findPortalSupportHerobrine(LivingEntity caster, double radius) {
        List<LivingEntity> candidates = findSupportHerobrines(caster, radius);
        if (candidates.isEmpty()) {
            return null;
        }

        List<SupportTarget> supportTargets = buildSupportTargets(caster, candidates, findNearestEnemy(caster, SUPPORT_ENEMY_RADIUS), SUPPORT_ENEMY_RADIUS);
        List<SupportTarget> farTargets = supportTargets.stream()
                .filter(target -> target.enemy() != null && target.enemyDistanceSqr() >= GREG_SUPPORT_PORTAL_ENEMY_DISTANCE_SQR)
                .toList();
        if (!farTargets.isEmpty()) {
            return pickRandomTopSupportTarget(farTargets, caster.getRandom()).support();
        }

        List<SupportTarget> engagedTargets = supportTargets.stream()
                .filter(target -> target.enemy() != null)
                .toList();
        if (!engagedTargets.isEmpty()) {
            return pickRandomTopSupportTarget(engagedTargets, caster.getRandom()).support();
        }

        return candidates.get(caster.getRandom().nextInt(candidates.size()));
    }

    @Nullable
    public static LivingEntity findEnemyForSupport(LivingEntity support, @Nullable LivingEntity fallback, double radius) {
        if (support instanceof Mob mob && mob.getTarget() != null && isEnemyOf(support, mob.getTarget())) {
            return mob.getTarget();
        }
        if (fallback != null && isEnemyOf(support, fallback)) {
            return fallback;
        }
        return findNearestEnemy(support, radius);
    }

    @Nullable
    public static LivingEntity findThreateningEnemy(LivingEntity caster, @Nullable LivingEntity support, double radius) {
        LivingEntity recentThreat = chooseNearestThreat(caster, support, radius,
                caster.getLastHurtByMob(),
                support != null ? support.getLastHurtByMob() : null);
        if (recentThreat != null) {
            return recentThreat;
        }

        LivingEntity targetedThreat = chooseNearestThreat(caster, support, radius,
                caster instanceof Mob mob ? mob.getTarget() : null,
                support instanceof Mob mob ? mob.getTarget() : null);
        if (targetedThreat != null) {
            return targetedThreat;
        }

        BowCounterThreat rangedThreat = findBowCounterThreat(caster, support, radius);
        if (rangedThreat != null) {
            return rangedThreat.attacker();
        }

        AABB searchBox = support == null
                ? caster.getBoundingBox().inflate(radius)
                : caster.getBoundingBox().minmax(support.getBoundingBox()).inflate(radius);
        return caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isThreateningEnemy(caster, support, entity, radius))
                .stream()
                .min(Comparator.comparingDouble(entity -> threatDistanceSqr(caster, support, entity)))
                .orElse(null);
    }

    @Nullable
    private static LivingEntity findNearestEnemy(LivingEntity caster, double radius) {
        if (caster instanceof Mob mob && mob.getTarget() != null && isEnemyOf(caster, mob.getTarget())) {
            return mob.getTarget();
        }

        AABB searchBox = caster.getBoundingBox().inflate(radius);
        return caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isEnemyOf(caster, entity))
                .stream()
                .min(Comparator.comparingDouble(caster::distanceToSqr))
                .orElse(null);
    }

    @Nullable
    private static LivingEntity chooseNearestThreat(LivingEntity caster, @Nullable LivingEntity support, double radius, @Nullable LivingEntity first, @Nullable LivingEntity second) {
        LivingEntity best = null;
        double bestDistance = Double.MAX_VALUE;
        for (LivingEntity candidate : new LivingEntity[]{first, second}) {
            if (candidate == null || !isThreatCandidate(caster, support, candidate, radius)) {
                continue;
            }
            double distance = threatDistanceSqr(caster, support, candidate);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }
        return best;
    }

    private static boolean isThreatCandidate(LivingEntity caster, @Nullable LivingEntity support, LivingEntity candidate, double radius) {
        if (!(isEnemyOf(caster, candidate) || support != null && isEnemyOf(support, candidate))) {
            return false;
        }
        double radiusSqr = radius * radius;
        return threatDistanceSqr(caster, support, candidate) <= radiusSqr;
    }

    private static boolean isThreateningEnemy(LivingEntity caster, @Nullable LivingEntity support, LivingEntity candidate, double radius) {
        if (!isThreatCandidate(caster, support, candidate, radius)) {
            return false;
        }
        if (isBowCounterThreat(caster, support, candidate, radius)) {
            return true;
        }
        if (candidate instanceof Mob mob) {
            return mob.getTarget() == caster || support != null && mob.getTarget() == support;
        }
        return false;
    }

    private static double threatDistanceSqr(LivingEntity caster, @Nullable LivingEntity support, LivingEntity entity) {
        double distance = caster.distanceToSqr(entity);
        if (support != null) {
            distance = Math.min(distance, support.distanceToSqr(entity));
        }
        return distance;
    }

    private static boolean isRidingHerobrineDragon(Entity entity) {
        return entity.isPassenger() && entity.getVehicle() instanceof HerobrineDragonEntity;
    }

    @Nullable
    private static SupportPortalPlan pickSupportPortalPlan(LivingEntity caster, List<LivingEntity> supports, @Nullable LivingEntity fallbackEnemy, boolean allowSelfFallback) {
        if (!supports.isEmpty()) {
            List<SupportTarget> supportTargets = buildSupportTargets(caster, supports, fallbackEnemy, SUPPORT_ENEMY_RADIUS);
            List<SupportTarget> farTargets = supportTargets.stream()
                    .filter(target -> target.enemy() != null && target.enemyDistanceSqr() >= GREG_SUPPORT_PORTAL_ENEMY_DISTANCE_SQR)
                    .sorted(Comparator.comparingDouble(SupportTarget::enemyDistanceSqr).reversed())
                    .toList();
            if (!farTargets.isEmpty()) {
                SupportTarget chosen = pickRandomTopSupportTarget(farTargets, caster.getRandom());
                return new SupportPortalPlan(chosen.support(), chosen.enemy());
            }

            SupportPortalPlan gatherPlan = findGatherPlan(supports);
            if (gatherPlan != null) {
                return gatherPlan;
            }
        }

        if (allowSelfFallback && fallbackEnemy != null) {
            return new SupportPortalPlan(caster, fallbackEnemy);
        }
        return null;
    }

    private static List<SupportTarget> buildSupportTargets(LivingEntity caster, List<LivingEntity> supports, @Nullable LivingEntity fallbackEnemy, double radius) {
        List<SupportTarget> targets = new ArrayList<>();
        for (LivingEntity support : supports) {
            if (!support.isAlive() || isRidingHerobrineDragon(support)) {
                continue;
            }
            LivingEntity enemy = findEnemyForSupport(support, fallbackEnemy, radius);
            double enemyDistanceSqr = enemy == null ? -1.0D : support.distanceToSqr(enemy);
            targets.add(new SupportTarget(support, enemy, enemyDistanceSqr));
        }
        return targets;
    }

    private static SupportTarget pickRandomTopSupportTarget(List<SupportTarget> targets, RandomSource random) {
        int limit = Math.min(3, targets.size());
        return targets.get(random.nextInt(limit));
    }

    @Nullable
    private static SupportPortalPlan findGatherPlan(List<LivingEntity> supports) {
        if (supports.size() < 2) {
            return null;
        }

        LivingEntity first = null;
        LivingEntity second = null;
        double bestDistanceSqr = SUPPORT_GATHER_DISTANCE_SQR;
        for (int i = 0; i < supports.size() - 1; i++) {
            LivingEntity left = supports.get(i);
            if (!left.isAlive() || isRidingHerobrineDragon(left)) {
                continue;
            }

            for (int j = i + 1; j < supports.size(); j++) {
                LivingEntity right = supports.get(j);
                if (!right.isAlive() || isRidingHerobrineDragon(right)) {
                    continue;
                }

                double distanceSqr = left.distanceToSqr(right);
                if (distanceSqr > bestDistanceSqr) {
                    bestDistanceSqr = distanceSqr;
                    first = left;
                    second = right;
                }
            }
        }

        return first != null && second != null ? new SupportPortalPlan(first, second) : null;
    }

    private static Vec3 randomPortalPreferredPosNear(LivingEntity caster, Entity entity, RandomSource random, double minDistance, double maxDistance) {
        double angle = random.nextDouble() * Math.PI * 2.0D;
        double distance = minDistance + random.nextDouble() * (maxDistance - minDistance);
        Vec3 preferred = new Vec3(
                entity.getX() + Math.cos(angle) * distance,
                Math.floor(entity.getY()),
                entity.getZ() + Math.sin(angle) * distance
        );
        return applySupportPortalYOffset(caster, preferred);
    }

    private static Vec3 entityCenter(Entity entity) {
        return new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() * 0.5D, entity.getZ());
    }

    @Nullable
    public static Vec3 applySupportPortalYOffset(LivingEntity caster, @Nullable Vec3 preferred) {
        if (preferred == null) {
            return null;
        }
        if (!(caster instanceof HerobrineGregEntity greg)) {
            return preferred;
        }
        return preferred.add(0.0D, greg.getRandom().nextInt(6), 0.0D);
    }

    @Nullable
    private static BowCounterThreat findBowCounterThreat(LivingEntity caster, double radius) {
        return findBowCounterThreat(caster, null, radius);
    }

    @Nullable
    private static BowCounterThreat findBowCounterThreat(LivingEntity caster, @Nullable LivingEntity support, double radius) {
        AABB searchBox = caster.getBoundingBox().inflate(radius);
        BowCounterThreat bestThreat = null;
        double bestCasterDistance = Double.MAX_VALUE;
        double bestTargetDistance = Double.MAX_VALUE;

        for (LivingEntity attacker : caster.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isPotentialBowCounterAttacker(caster, entity))) {
            LivingEntity target = resolveBowCounterTarget(caster, support, attacker, radius);
            if (target == null) {
                continue;
            }

            double casterDistance = caster.distanceToSqr(attacker);
            double targetDistance = attacker.distanceToSqr(target);
            if (bestThreat == null
                    || casterDistance < bestCasterDistance
                    || casterDistance == bestCasterDistance && targetDistance < bestTargetDistance) {
                bestThreat = new BowCounterThreat(attacker, target);
                bestCasterDistance = casterDistance;
                bestTargetDistance = targetDistance;
            }
        }

        return bestThreat;
    }

    private static boolean isPotentialBowCounterAttacker(LivingEntity caster, LivingEntity attacker) {
        return attacker != caster
                && attacker.isAlive()
                && isEnemyOf(caster, attacker)
                && hasBowReady(attacker);
    }

    private static boolean hasBowReady(LivingEntity attacker) {
        return attacker.getMainHandItem().getItem() instanceof BowItem
                || attacker.getOffhandItem().getItem() instanceof BowItem
                || attacker.getUseItem().getItem() instanceof BowItem;
    }

    private static boolean isBowCounterThreat(LivingEntity caster, @Nullable LivingEntity support, LivingEntity attacker, double radius) {
        return resolveBowCounterTarget(caster, support, attacker, radius) != null;
    }

    @Nullable
    private static LivingEntity resolveBowCounterTarget(LivingEntity caster, @Nullable LivingEntity support, LivingEntity attacker, double radius) {
        if (!isPotentialBowCounterAttacker(caster, attacker)) {
            return null;
        }

        if (attacker instanceof Mob mob) {
            LivingEntity mobTarget = mob.getTarget();
            if (isValidBowCounterTarget(caster, support, attacker, mobTarget)) {
                return mobTarget;
            }
        }

        AABB searchBox = attacker.getBoundingBox().inflate(radius);
        LivingEntity bestTarget = null;
        double bestAim = COUNTER_BOW_AIM_DOT_THRESHOLD;
        double bestDistance = Double.MAX_VALUE;
        Vec3 look = attacker.getLookAngle();
        if (look.lengthSqr() < 1.0E-4D) {
            return null;
        }
        look = look.normalize();

        for (LivingEntity candidate : attacker.level().getEntitiesOfClass(LivingEntity.class, searchBox, entity -> isValidBowCounterTarget(caster, support, attacker, entity))) {
            Vec3 direction = entityCenter(candidate).subtract(attacker.getEyePosition());
            if (direction.lengthSqr() < 1.0E-4D) {
                continue;
            }

            double aimDot = look.dot(direction.normalize());
            double distance = attacker.distanceToSqr(candidate);
            if (aimDot > bestAim || aimDot == bestAim && distance < bestDistance) {
                bestTarget = candidate;
                bestAim = aimDot;
                bestDistance = distance;
            }
        }

        return bestTarget;
    }

    private static boolean isValidBowCounterTarget(LivingEntity caster, @Nullable LivingEntity support, LivingEntity attacker, @Nullable LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (!(target == caster
                || target == support
                || isSupportedPortalDefenseTarget(caster, target))) {
            return false;
        }
        return attacker.hasLineOfSight(target)
                && attacker.distanceToSqr(target) >= COUNTER_BOW_MIN_TARGET_DISTANCE_SQR;
    }

    private static boolean isSupportedPortalDefenseTarget(LivingEntity caster, LivingEntity target) {
        return target == caster
                || target instanceof HerobrineMob
                || target instanceof LowHerobrineCloneEntity
                || target instanceof LowShadowHerobrineCloneEntity;
    }

    @Nullable
    private static Vec3 buildBowCounterEntrance(LivingEntity attacker, LivingEntity target) {
        Vec3 attackerCenter = entityCenter(attacker);
        Vec3 targetCenter = entityCenter(target);
        Vec3 direction = horizontalDirection(targetCenter.subtract(attackerCenter));
        if (direction.lengthSqr() < 1.0E-4D) {
            return null;
        }

        Vec3 midpoint = attackerCenter.add(targetCenter).scale(0.5D);
        return new Vec3(midpoint.x, Math.max(attacker.getY(), target.getY()), midpoint.z);
    }

    @Nullable
    private static Vec3 buildBowCounterExit(LivingEntity attacker, LivingEntity target) {
        Vec3 attackerCenter = entityCenter(attacker);
        Vec3 targetCenter = entityCenter(target);
        Vec3 direction = horizontalDirection(targetCenter.subtract(attackerCenter));
        if (direction.lengthSqr() < 1.0E-4D) {
            return null;
        }

        Vec3 side = new Vec3(-direction.z, 0.0D, direction.x);
        int sideChoice = attacker.getRandom().nextInt(3);
        Vec3 offset = switch (sideChoice) {
            case 1 -> side.scale(COUNTER_BOW_EXIT_DISTANCE);
            case 2 -> side.scale(-COUNTER_BOW_EXIT_DISTANCE);
            default -> direction.scale(-COUNTER_BOW_EXIT_DISTANCE);
        };
        Vec3 position = attacker.position().add(offset);
        return new Vec3(position.x, attacker.getY() + attacker.getRandom().nextInt(4), position.z);
    }

    private static void triggerCounterPortalRetreat(LivingEntity caster, LivingEntity attacker) {
        if (caster instanceof HerobrineGregEntity greg) {
            greg.triggerRangedCounterRetreat(attacker);
        } else if (caster instanceof com.pla.annoyingvillagers.entity.TransporterHerobrineCloneEntity transporter) {
            transporter.triggerRangedCounterRetreat(attacker);
        }
    }

    private static Vec3 horizontalDirection(Vec3 vector) {
        Vec3 flattened = new Vec3(vector.x, 0.0D, vector.z);
        if (flattened.lengthSqr() < 1.0E-4D) {
            return Vec3.ZERO;
        }
        return flattened.normalize();
    }
}
