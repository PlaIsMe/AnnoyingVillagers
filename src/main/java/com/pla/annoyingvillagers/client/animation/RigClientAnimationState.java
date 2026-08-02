package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
public final class RigClientAnimationState {
    private static final int DEFAULT_BLEND_IN_TICKS = 2;
    private static final int DEFAULT_BLEND_OUT_TICKS = 4;
    private static final Map<Integer, Active> ACTIVE_ANIMATIONS = new ConcurrentHashMap<>();

    private RigClientAnimationState() {
    }

    public static void start(int entityId, RigAnimationId animationId, int durationTicks) {
        Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
        int startTick = entity == null ? 0 : entity.tickCount;
        Active active = new Active(
                animationId,
                startTick,
                durationTicks,
                DEFAULT_BLEND_IN_TICKS,
                DEFAULT_BLEND_OUT_TICKS
        );
        ACTIVE_ANIMATIONS.put(entityId, active);
    }

    public static Active getActive(Entity entity, float ageInTicks) {
        Active active = ACTIVE_ANIMATIONS.get(entity.getId());
        if (active == null) {
            return null;
        }

        if (active.expired(ageInTicks)) {
            ACTIVE_ANIMATIONS.remove(entity.getId(), active);
            return null;
        }

        return active;
    }

    public record Active(
            RigAnimationId animationId,
            int startedAtTick,
            int durationTicks,
            int blendInTicks,
            int blendOutTicks
    ) {
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
