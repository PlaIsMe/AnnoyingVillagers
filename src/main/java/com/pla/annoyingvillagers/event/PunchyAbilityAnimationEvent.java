package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.compat.PunchyClientCompat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class PunchyAbilityAnimationEvent {
    private PunchyAbilityAnimationEvent() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClientTick(ClientTickEvent.Post event) {
        // Follow Punchy's input processing so generic use/attack poses do not replace the ability.
        if (ModList.get().isLoaded("punchy")) PunchyClientCompat.flushAbility();
    }
}
