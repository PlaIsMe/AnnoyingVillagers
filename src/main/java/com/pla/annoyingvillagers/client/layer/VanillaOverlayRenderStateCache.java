package com.pla.annoyingvillagers.client.layer;

import com.google.common.reflect.TypeToken;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import org.jspecify.annotations.Nullable;

/** Carries entity-dependent overlay selection into Minecraft's extracted render states. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class VanillaOverlayRenderStateCache {
    private static final ContextKey<Identifier> TEXTURE = new ContextKey<>(Identifier.fromNamespaceAndPath("annoyingvillagers", "vanilla_overlay_texture"));
    private static final ContextKey<LivingEntity> ENTITY = new ContextKey<>(Identifier.fromNamespaceAndPath("annoyingvillagers", "vanilla_overlay_entity"));

    private VanillaOverlayRenderStateCache() {}

    @SubscribeEvent
    public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
        // NeoForge clears extension data after normal extraction, before modifiers run.
        event.registerEntityModifier(new TypeToken<LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>>() {}, VanillaOverlayRenderStateCache::update);
    }

    public static void update(LivingEntity entity, LivingEntityRenderState state) {
        // Extracted states are created continuously, including by other mods' renderers.
        // Keep their data on the state itself: a static map would retain every past frame.
        state.setRenderData(ENTITY, entity);
        Identifier texture = entity instanceof AbstractIllager illager
                ? VanillaOverlayTexturePicker.pickIllagerTexture(illager)
                : VanillaOverlayTexturePicker.pickHumanoidTexture(entity);
        state.setRenderData(TEXTURE, texture);
    }

    public static @Nullable Identifier get(LivingEntityRenderState state) {
        return state.getRenderData(TEXTURE);
    }

    public static @Nullable LivingEntity getEntity(LivingEntityRenderState state) {
        return state.getRenderData(ENTITY);
    }
}
