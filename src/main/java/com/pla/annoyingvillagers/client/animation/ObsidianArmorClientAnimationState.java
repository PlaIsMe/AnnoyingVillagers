package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPart;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ObsidianArmorClientAnimationState {
    private static final Map<Key, State> ACTIVE = new HashMap<>();

    private ObsidianArmorClientAnimationState() {}

    public static void start(int entityId, UUID entityUuid, SpecialAnimationId animationId, int durationTicks) {
        ObsidianArmorPart part = ObsidianArmorPart.fromAnimationId(animationId);
        Key key = new Key(entityId, part);
        if (durationTicks <= 0) {
            ACTIVE.remove(key);
            return;
        }
        ACTIVE.put(key, new State(entityUuid, animationId, durationTicks, currentClientTick()));
    }

    public static State get(LivingEntity entity, ObsidianArmorPart part) {
        Key key = new Key(entity.getId(), part);
        State state = ACTIVE.get(key);
        if (state == null) return null;
        if (!state.entityUuid().equals(entity.getUUID()) || state.elapsedTicks() > state.durationTicks()) {
            ACTIVE.remove(key, state);
            return null;
        }
        return state;
    }

    public static Map<Key, State> snapshot() {
        return Map.copyOf(ACTIVE);
    }

    public static void clear() {
        ACTIVE.clear();
    }

    private static int currentClientTick() {
        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.level == null ? 0 : (int)minecraft.level.getGameTime();
    }

    public record State(UUID entityUuid, SpecialAnimationId animationId, int durationTicks, int startedAtTick) {
        public ObsidianArmorPart part() {
            return ObsidianArmorPart.fromAnimationId(this.animationId);
        }

        public float elapsedTicks() {
            Minecraft minecraft = Minecraft.getInstance();
            float partial = minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
            return Math.max(0.0F, currentClientTick() - this.startedAtTick + partial);
        }
    }

    public record Key(int entityId, ObsidianArmorPart part) {}
}
