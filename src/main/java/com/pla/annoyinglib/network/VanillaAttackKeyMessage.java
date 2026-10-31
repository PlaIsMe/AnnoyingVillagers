package com.pla.annoyinglib.network;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

@EventBusSubscriber(bus = Bus.MOD)
public class VanillaAttackKeyMessage {
public VanillaAttackKeyMessage() {
    }

    public VanillaAttackKeyMessage(FriendlyByteBuf buffer) {
    }

    public static void buffer(VanillaAttackKeyMessage message, FriendlyByteBuf buffer) {
    }

    public static void handler(VanillaAttackKeyMessage message, Supplier<Context> supplier) {
        Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
            // add this in herobrine_vessels
            // if (EnderSlayerScytheItem.isDragonActive(player.getMainHandItem())) {
            //     EnderSlayerScytheItem.commandThunder(player, null);
            //     return;
            // }
            // DemoniacVoltageReaverItem.activateVanillaNormalAttack(player);
        });
        context.setPacketHandled(true);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        AnnoyingLib.addNetworkMessage(VanillaAttackKeyMessage.class, VanillaAttackKeyMessage::buffer, VanillaAttackKeyMessage::new, VanillaAttackKeyMessage::handler);
    }
}
