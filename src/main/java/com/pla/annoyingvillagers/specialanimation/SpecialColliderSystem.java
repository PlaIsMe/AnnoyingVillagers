package com.pla.annoyingvillagers.specialanimation;

import com.pla.annoyingvillagers.rig.RigOrientedBox;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class SpecialColliderSystem {
    private static final int MOTION_SAMPLES_PER_TICK = 8;

    private SpecialColliderSystem() {
    }

    public static List<LivingEntity> findHits(Mob mob, SpecialAnimationId animationId, SpecialAttackWindow window, int elapsedTick) {
        List<RigOrientedBox> boxes = collisionBoxes(mob, animationId, window, elapsedTick, mob.yBodyRot);
        if (boxes.isEmpty()) return List.of();
        AABB search = boxes.get(0).bounds();
        for (int i = 1; i < boxes.size(); i++) search = union(search, boxes.get(i).bounds());
        Set<LivingEntity> hits = new LinkedHashSet<>();
        for (LivingEntity entity : mob.level().getEntitiesOfClass(LivingEntity.class, search, entity -> entity != mob && entity.isAlive() && !entity.isRemoved())) {
            for (RigOrientedBox box : boxes) {
                if (!box.intersects(entity.getBoundingBox())) continue;
                hits.add(entity);
                break;
            }
        }
        return List.copyOf(hits);
    }

    public static List<RigOrientedBox> collisionBoxes(Mob mob, SpecialAnimationId animationId, SpecialAttackWindow window, float elapsedTick, float bodyYaw) {
        float current = Math.max(0.0F, elapsedTick);
        float previous = Math.max(0.0F, current - 1.0F);
        boolean active = window.contains(current);
        int samples = active ? MOTION_SAMPLES_PER_TICK : 1;
        List<RigOrientedBox> boxes = new ArrayList<>(window.colliders().length * samples);
        for (SpecialCollider collider : window.colliders()) {
            for (int i = 0; i < samples; i++) {
                float alpha = samples == 1 ? 1.0F : i / (float)(samples - 1);
                float sampleTick = previous + (current - previous) * alpha;
                boxes.add(collider.worldBox(mob, animationId, sampleTick, bodyYaw));
            }
        }
        return boxes;
    }

    private static AABB union(AABB a, AABB b) {
        return new AABB(Math.min(a.minX, b.minX), Math.min(a.minY, b.minY), Math.min(a.minZ, b.minZ), Math.max(a.maxX, b.maxX), Math.max(a.maxY, b.maxY), Math.max(a.maxZ, b.maxZ));
    }
}
