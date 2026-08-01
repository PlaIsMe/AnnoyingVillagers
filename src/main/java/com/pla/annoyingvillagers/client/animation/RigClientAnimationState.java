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
    private static final Map<Integer, Active> ACTIVE_ANIMATIONS = new ConcurrentHashMap<>();

    private RigClientAnimationState() {
    }

    public static void start(int entityId, RigAnimationId animationId, int durationTicks) {
        Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
        int startTick = entity == null ? 0 : entity.tickCount;
        ACTIVE_ANIMATIONS.put(entityId, new Active(animationId, startTick, durationTicks));
    }

    public static Active getActive(Entity entity, float ageInTicks) {
        Active active = ACTIVE_ANIMATIONS.get(entity.getId());
        if (active == null) {
            return null;
        }

        if (active.elapsedTicks(ageInTicks) > active.durationTicks()) {
            ACTIVE_ANIMATIONS.remove(entity.getId(), active);
            return null;
        }

        return active;
    }

    public record Active(RigAnimationId animationId, int startedAtTick, int durationTicks) {
        public float elapsedTicks(float ageInTicks) {
            return Math.max(0.0F, ageInTicks - this.startedAtTick);
        }
    }
}
