package com.pla.annoyingvillagers.client.animation;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Prevents entity-id reuse from inheriting one-shot state after leaving a world. */
@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
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
