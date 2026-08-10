package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NullWeaponCleanupEvent {
    private static final String NBT_SPENT_STACKS = "AV_NullWeaponSpentStacks";
    private static final List<String> NULL_WEAPON_KEYS = List.of(
            "NullSwordUUID",
            "NullAxeUUID",
            "NullPickaxeUUID",
            "NullHoeUUID",
            "NullShovelUUID"
    );

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        CompoundTag data = player.getPersistentData();
        data.remove(NBT_SPENT_STACKS);

        for (String key : NULL_WEAPON_KEYS) {
            data.remove(key);
        }
    }
}