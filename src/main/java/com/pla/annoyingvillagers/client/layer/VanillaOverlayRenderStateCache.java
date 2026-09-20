package com.pla.annoyingvillagers.client.layer;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import org.jspecify.annotations.Nullable;

/** Carries entity-dependent overlay selection into Minecraft's extracted render states. */
public final class VanillaOverlayRenderStateCache {
    private static final Map<LivingEntityRenderState, Identifier> TEXTURES = new IdentityHashMap<>();
    private static final Map<LivingEntityRenderState, LivingEntity> ENTITIES = new IdentityHashMap<>();

    private VanillaOverlayRenderStateCache() {}

    public static void update(LivingEntity entity, LivingEntityRenderState state) {
        ENTITIES.put(state, entity);
        Identifier texture = entity instanceof AbstractIllager illager
                ? VanillaOverlayTexturePicker.pickIllagerTexture(illager)
                : VanillaOverlayTexturePicker.pickHumanoidTexture(entity);
        if (texture == null) TEXTURES.remove(state);
        else TEXTURES.put(state, texture);
    }

    public static @Nullable Identifier get(LivingEntityRenderState state) {
        return TEXTURES.get(state);
    }

    public static @Nullable LivingEntity getEntity(LivingEntityRenderState state) {
        return ENTITIES.get(state);
    }
}
