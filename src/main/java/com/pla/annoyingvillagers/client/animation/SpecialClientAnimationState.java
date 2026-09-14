package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
public final class SpecialClientAnimationState {
    private static final Map<Integer, Active> ACTIVE = new ConcurrentHashMap<>();

    private SpecialClientAnimationState() {
    }

    public static void start(int entityId, SpecialAnimationId animationId, int durationTicks) {
        if (durationTicks <= 0) {
            ACTIVE.remove(entityId);
            return;
        }
        Entity entity = Minecraft.getInstance().level == null ? null : Minecraft.getInstance().level.getEntity(entityId);
        int startTick = entity == null ? -1 : entity.tickCount;
        UUID uuid = entity == null ? null : entity.getUUID();
        ACTIVE.put(entityId, new Active(animationId, uuid, startTick, durationTicks));
    }

    public static Active getActive(Entity entity, float ageInTicks) {
        Active active = ACTIVE.get(entity.getId());
        if (active == null) return null;
        if (active.startedAtTick() < 0) {
            Active attached = new Active(active.animationId(), entity.getUUID(), entity.tickCount, active.durationTicks());
            ACTIVE.replace(entity.getId(), active, attached);
            active = attached;
        }
        if (active.entityUuid() != null && !active.entityUuid().equals(entity.getUUID())) {
            ACTIVE.remove(entity.getId(), active);
            return null;
        }
        if (active.elapsedTicks(ageInTicks) > active.durationTicks()) {
            ACTIVE.remove(entity.getId(), active);
            return null;
        }
        return active;
    }

    public static void clear() {
        ACTIVE.clear();
    }

    public record Active(SpecialAnimationId animationId, UUID entityUuid, int startedAtTick, int durationTicks) {
        public float elapsedTicks(float ageInTicks) {
            return Math.max(0.0F, ageInTicks - this.startedAtTick);
        }
    }
}
