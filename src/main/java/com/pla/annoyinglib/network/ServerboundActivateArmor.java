package com.pla.annoyinglib.network;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
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
                if ((ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonChestplateItem_isBlueDemonChestplate(chest) : false)
                        && (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonChestplateItem_isFullyCharged(chest) : false)) {
                    if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.blueDemonChestplateItem_activateBuff(chest);
                }
                if (ModList.get().isLoaded("herobrine_vessels")) {
                    HerobrineVessels.activateArmor(player);
                }
            }
        });
        context.setPacketHandled(true);
    }
}
