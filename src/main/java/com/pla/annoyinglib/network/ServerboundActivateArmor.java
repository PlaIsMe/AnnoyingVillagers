package com.pla.annoyinglib.network;

import com.pla.annoyinglib.item.BlueDemonChestplateItem;
import com.pla.annoyinglib.rig.armor.ObsidianArmorController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public final class ServerboundActivateArmor {
    public static void encode(ServerboundActivateArmor msg, FriendlyByteBuf buf) {}

    public static ServerboundActivateArmor decode(FriendlyByteBuf buf) {
        return new ServerboundActivateArmor();
    }

    public static void handle(ServerboundActivateArmor msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null && player.isAlive() && !player.isRemoved() && !player.isSpectator()) {
                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                if (BlueDemonChestplateItem.isBlueDemonChestplate(chest)
                        && BlueDemonChestplateItem.isFullyCharged(chest)) {
                    BlueDemonChestplateItem.activateBuff(chest);
                }
                // add this in herobrine_vessels
                // ObsidianArmorController.activateChargedArmor(player);
            }
        });
        context.setPacketHandled(true);
    }
}
