package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

/** Prevents entity-id reuse from inheriting one-shot state after leaving a world. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class RigClientAnimationEvents {
    private RigClientAnimationEvents() {}

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide()) {
            RigClientAnimationState.clear();
            SpecialClientAnimationState.clear();
            ObsidianArmorClientAnimationState.clear();
        }
    }
}
