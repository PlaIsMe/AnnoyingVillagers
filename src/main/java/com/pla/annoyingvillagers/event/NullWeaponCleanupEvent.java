package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.NullWeaponItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = EventBusSubscriber.Bus.GAME)
public class NullWeaponCleanupEvent {
    private static final String NBT_SPENT_STACKS = "AV_NullWeaponSpentStacks";

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        CompoundTag data = player.getPersistentData();
        data.remove(NBT_SPENT_STACKS);
        NullWeaponItem.beginLoginReconciliation(player);
    }
}
