package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.FractureBlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class FractureClientEvents {
    private FractureClientEvents() {}

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide()) FractureBlockState.reset();
    }
}
