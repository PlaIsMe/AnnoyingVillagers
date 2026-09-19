package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.compat.BetterCombatClientCompat;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.network.FriendlyByteBuf;


import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundBetterCombatAnimation(
        int playerId,
        AnimatedHand animatedHand,
        String animation,
        float swingDurationTicks,
        float upswing
)  implements AnnoyingVillagersPayload {
    public enum AnimatedHand {
        MAIN_HAND,
        OFF_HAND,
        TWO_HANDED
    }

    public static void encode(ClientboundBetterCombatAnimation msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.playerId);
        buf.writeEnum(msg.animatedHand);
        buf.writeUtf(msg.animation);
        buf.writeFloat(msg.swingDurationTicks);
        buf.writeFloat(msg.upswing);
    }

    public static ClientboundBetterCombatAnimation decode(FriendlyByteBuf buf) {
        return new ClientboundBetterCombatAnimation(
                buf.readVarInt(),
                buf.readEnum(AnimatedHand.class),
                buf.readUtf(),
                buf.readFloat(),
                buf.readFloat()
        );
    }

    public static void handle(ClientboundBetterCombatAnimation msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (ModList.get().isLoaded(VanillaWeaponAbilityUtil.BETTER_COMBAT_MOD_ID)) {
                BetterCombatClientCompat.playAnimation(msg);
            }
            if (ModList.get().isLoaded("punchy")) {
                com.pla.annoyingvillagers.client.compat.PunchyClientCompat.queueAbility(msg);
            }
        });
    }
}
