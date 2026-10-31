package com.pla.annoyinglib.network;

import com.pla.annoyinglib.entity.GolemArms;
import com.pla.annoyinglib.item.DestructionEyeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ServerboundDestructionEyeAttack() {
    public static void encode(ServerboundDestructionEyeAttack message, FriendlyByteBuf buffer) {
    }

    public static ServerboundDestructionEyeAttack decode(FriendlyByteBuf buffer) {
        return new ServerboundDestructionEyeAttack();
    }

    public static void handle(ServerboundDestructionEyeAttack message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof DestructionEyeItem)) return;
            GolemArms arms = DestructionEyeItem.getOrCreateArms(player.serverLevel(), player, stack);
            if (arms != null) arms.attackFromOwner();
        });
        context.setPacketHandled(true);
    }
}
