package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SnakeBladeCleanupEvent {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;

        if (event.loadedFromDisk()
                && event.getEntity().getType() == AnnoyingVillagersModEntities.SNAKE_BLADE.get()) {
            event.setCanceled(true);
            event.getEntity().discard();
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        DemoniacVoltageReaverItem.clearInterruptedSnakeAnimation(event.getEntity());
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get())) {
                DemoniacVoltageReaverItem.clearSnakeAnimation(stack);
            }
        }
    }
}
