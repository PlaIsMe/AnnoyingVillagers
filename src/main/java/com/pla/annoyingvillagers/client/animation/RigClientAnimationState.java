package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.api.distmarker.Dist;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class RigClientAnimationState {
    private static final int DEFAULT_BLEND_IN_TICKS = 2;
    private static final int DEFAULT_BLEND_OUT_TICKS = 4;
    private static final Map<Integer, Active> ACTIVE_ANIMATIONS = new ConcurrentHashMap<>();

    private RigClientAnimationState() {
    }

    public static void start(int entityId, RigAnimationId animationId, int durationTicks,
                             int trailStartTick, int trailEndTickExclusive) {
        if (durationTicks <= 0) {
            ACTIVE_ANIMATIONS.remove(entityId);
            return;
        }

        Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
        int startTick = entity == null ? 0 : entity.tickCount;
        UUID entityUuid = entity == null ? null : entity.getUUID();
        Active active = new Active(animationId, entityUuid, startTick, durationTicks,
                DEFAULT_BLEND_IN_TICKS, DEFAULT_BLEND_OUT_TICKS, trailStartTick, trailEndTickExclusive);
        ACTIVE_ANIMATIONS.put(entityId, active);
    }

    public static void clear() {
        ACTIVE_ANIMATIONS.clear();
    }

    public static Map<Integer, Active> snapshot() {
        return Map.copyOf(ACTIVE_ANIMATIONS);
    }

    public static Active getActive(Entity entity, float ageInTicks) {
        Active active = ACTIVE_ANIMATIONS.get(entity.getId());
        if (active == null) {
            return null;
        }

        if (active.entityUuid() != null && !active.entityUuid().equals(entity.getUUID())) {
            ACTIVE_ANIMATIONS.remove(entity.getId(), active);
            return null;
        }

        if (active.expired(ageInTicks)) {
            ACTIVE_ANIMATIONS.remove(entity.getId(), active);
            return null;
        }

        return active;
    }

    public static boolean isToolHidden(Entity entity, HumanoidArm arm) {
        Active active = getActive(entity, entity.tickCount);
        return active != null && RigAnimationSpecs.get(active.animationId()).isToolHidden(arm, active.elapsedTicks(entity.tickCount));
    }

    public record Active(RigAnimationId animationId, UUID entityUuid, int startedAtTick, int durationTicks,
                         int blendInTicks, int blendOutTicks, int trailStartTick, int trailEndTickExclusive) {
        public boolean hasTrailWindow() {
            return this.trailStartTick >= 0 && this.trailEndTickExclusive > this.trailStartTick;
        }

        public boolean trailWindowContains(float elapsedTicks) {
            return this.hasTrailWindow() && elapsedTicks >= this.trailStartTick && elapsedTicks < this.trailEndTickExclusive;
        }
        public float elapsedTicks(float ageInTicks) {
            return Math.max(0.0F, ageInTicks - this.startedAtTick);
        }

        public float sampleTicks(float ageInTicks) {
            return Math.min(this.elapsedTicks(ageInTicks), this.durationTicks);
        }

        public float weight(float ageInTicks) {
            float elapsedTicks = this.elapsedTicks(ageInTicks);
            if (this.blendInTicks > 0 && elapsedTicks < this.blendInTicks) {
                return smoothStep(elapsedTicks / this.blendInTicks);
            }
            if (this.blendOutTicks > 0 && elapsedTicks > this.durationTicks) {
                return 1.0F - smoothStep((elapsedTicks - this.durationTicks) / this.blendOutTicks);
            }

            return elapsedTicks <= this.durationTicks ? 1.0F : 0.0F;
        }

        public boolean expired(float ageInTicks) {
            return this.elapsedTicks(ageInTicks) > this.durationTicks + this.blendOutTicks;
        }

        private static float smoothStep(float value) {
            float clamped = Math.max(0.0F, Math.min(1.0F, value));
            return clamped * clamped * (3.0F - 2.0F * clamped);
        }
    }
}
