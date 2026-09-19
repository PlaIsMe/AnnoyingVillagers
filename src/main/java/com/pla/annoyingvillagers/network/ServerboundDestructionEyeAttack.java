package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.item.DestructionEyeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ServerboundDestructionEyeAttack()  implements AnnoyingVillagersPayload {
    public static void encode(ServerboundDestructionEyeAttack message, FriendlyByteBuf buffer) {
    }

    public static ServerboundDestructionEyeAttack decode(FriendlyByteBuf buffer) {
        return new ServerboundDestructionEyeAttack();
    }

    public static void handle(ServerboundDestructionEyeAttack message, IPayloadContext contextSupplier) {
        IPayloadContext context = contextSupplier;
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof DestructionEyeItem)) return;
            GolemArms arms = DestructionEyeItem.getOrCreateArms(player.serverLevel(), player, stack);
            if (arms != null) arms.attackFromOwner();
        });
    }
}
