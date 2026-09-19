package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;



public final class ServerboundActivateArmor  implements AnnoyingVillagersPayload {
    public static void encode(ServerboundActivateArmor msg, FriendlyByteBuf buf) {}

    public static ServerboundActivateArmor decode(FriendlyByteBuf buf) {
        return new ServerboundActivateArmor();
    }

    public static void handle(ServerboundActivateArmor msg, IPayloadContext ctx) {
        IPayloadContext context = ctx;
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player && player.isAlive() && !player.isRemoved() && !player.isSpectator()) {
                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                if (BlueDemonChestplateItem.isBlueDemonChestplate(chest)
                        && BlueDemonChestplateItem.isFullyCharged(chest)) {
                    BlueDemonChestplateItem.activateBuff(chest);
                }
                ObsidianArmorController.activateChargedArmor(player);
            }
        });
    }
}
