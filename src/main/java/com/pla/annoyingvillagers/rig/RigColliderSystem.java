package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.rig.pose.RigPoseLibrary;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Team;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RigColliderSystem {
    private static final int MOTION_SAMPLES_PER_TICK = 4;
    private static final Map<RigAnimationId, Double> MAX_REACH_CACHE = new EnumMap<>(RigAnimationId.class);

    private RigColliderSystem() {}

    public static List<LivingEntity> findHits(Mob mob, RigAnimationSpec spec, RigAttackWindow window, int elapsedTick) {
        List<RigOrientedBox> boxes = collisionBoxes(mob, spec, window, elapsedTick);
        if (boxes.isEmpty()) return List.of();

        AABB search = boxes.get(0).bounds();
        for (int i = 1; i < boxes.size(); i++) search = union(search, boxes.get(i).bounds());

        Set<LivingEntity> hits = new LinkedHashSet<>();
        for (LivingEntity entity : mob.level().getEntitiesOfClass(LivingEntity.class, search, entity -> entity != mob && entity.isAlive() && !entity.isRemoved())) {
            for (RigOrientedBox box : boxes) {
                if (box.intersects(entity.getBoundingBox())) {
                    hits.add(entity);
                    break;
                }
            }
        }
        return List.copyOf(hits);
    }

    public static List<RigOrientedBox> collisionBoxes(Mob mob, RigAnimationSpec spec, RigAttackWindow window, float elapsedTick) {
        return collisionBoxes(mob, spec, window, elapsedTick, mob.yBodyRot);
    }

    public static List<RigOrientedBox> collisionBoxes(Mob mob, RigAnimationSpec spec, RigAttackWindow window, float elapsedTick, float bodyYaw) {
        float current = Math.max(0.0F, elapsedTick);
        float previous = Math.max(0.0F, current - 1.0F);
        boolean active = window.contains(current);
        int samples = active ? MOTION_SAMPLES_PER_TICK : 1;
        List<RigOrientedBox> boxes = new ArrayList<>(window.colliders().length * samples);

        for (RigCollider collider : window.colliders()) {
            for (int i = 0; i < samples; i++) {
                float alpha = samples == 1 ? 1.0F : i / (float) (samples - 1);
                float sampleTick = previous + (current - previous) * alpha;
                boxes.add(collider.worldBox(mob, spec.animationId(), sampleTick, bodyYaw));
            }
        }
        return boxes;
    }

    public static boolean canStartAttack(Mob mob, LivingEntity target, RigAnimationSpec spec) {
        Team mobTeam = mob.getTeam();
        Team targetTeam = target.getTeam();
        if (mob.isAlliedTo(target) || target.isAlliedTo(mob)) return false;
        if (mobTeam != null && targetTeam != null && (mobTeam == targetTeam || mobTeam.isAlliedTo(targetTeam) || targetTeam.isAlliedTo(mobTeam))) return false;
        double range = MAX_REACH_CACHE.computeIfAbsent(spec.animationId(), ignored -> calculateMaxReach(spec));
        range += RigPoseLibrary.maxHorizontalMotionBlocks(spec.animationId()) + target.getBbWidth() * 0.5D + mob.getBbWidth() * 0.5D;
        double dx = target.getX() - mob.getX();
        double dz = target.getZ() - mob.getZ();
        return dx * dx + dz * dz <= range * range;
    }

    private static double calculateMaxReach(RigAnimationSpec spec) {
        double max = 0.0D;
        for (RigAttackWindow window : spec.attackWindows()) {
            float start = Math.max(0.0F, window.startTickInclusive() - 1.0F);
            float end = window.endTickExclusive();
            for (float tick = start; tick <= end; tick += 0.25F) {
                for (RigCollider collider : window.colliders()) max = Math.max(max, collider.localBox(spec.animationId(), tick).maxHorizontalDistanceFromOrigin());
            }
        }
        return max;
    }

    private static AABB union(AABB a, AABB b) {
        return new AABB(Math.min(a.minX, b.minX), Math.min(a.minY, b.minY), Math.min(a.minZ, b.minZ), Math.max(a.maxX, b.maxX), Math.max(a.maxY, b.maxY), Math.max(a.maxZ, b.maxZ));
    }
}
