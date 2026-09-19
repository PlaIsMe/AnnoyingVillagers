package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;



public class VanillaAttackKeyMessage  implements AnnoyingVillagersPayload {
    public VanillaAttackKeyMessage() {
    }

    public VanillaAttackKeyMessage(FriendlyByteBuf buffer) {
    }

    public static void buffer(VanillaAttackKeyMessage message, FriendlyByteBuf buffer) {
    }

    public static void handler(VanillaAttackKeyMessage message, IPayloadContext supplier) {
        IPayloadContext context = supplier;
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player) || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
            if (EnderSlayerScytheItem.isDragonActive(player.getMainHandItem())) {
                EnderSlayerScytheItem.commandThunder(player, null);
                return;
            }
            DemoniacVoltageReaverItem.activateVanillaNormalAttack(player);
        });
    }
}
