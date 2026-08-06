package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnderAegisShieldBlockEvent {
    @SubscribeEvent
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        ItemStack main = serverPlayer.getMainHandItem();
        if (!(main.getItem() instanceof EnderAegisItem)) {
            return;
        }

        if (main.hasTag() && Objects.requireNonNull(main.getTag()).getBoolean(EnderAegisItem.SECOND_FORM_TAG)) {
            EnderAegisItem.shieldShoot(serverPlayer.level(), serverPlayer);
        } else {
            EnderAegisItem.addBlockedDamageCharge(main, event.getBlockedDamage());
        }
    }
}
