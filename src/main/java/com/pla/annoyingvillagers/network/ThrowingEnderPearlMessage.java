package com.pla.annoyingvillagers.network;



import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.event.ThrowingPearlKeyPressedEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ThrowingEnderPearlMessage  implements AnnoyingVillagersPayload {

    int type;
    int pressedms;

    public ThrowingEnderPearlMessage(int i, int j) {
        this.type = i;
        this.pressedms = j;
    }

    public ThrowingEnderPearlMessage(FriendlyByteBuf friendlybytebuf) {
        this.type = friendlybytebuf.readInt();
        this.pressedms = friendlybytebuf.readInt();
    }

    public static void buffer(ThrowingEnderPearlMessage throwingEnderPearlMessage, FriendlyByteBuf friendlybytebuf) {
        friendlybytebuf.writeInt(throwingEnderPearlMessage.type);
        friendlybytebuf.writeInt(throwingEnderPearlMessage.pressedms);
    }

    public static void handler(ThrowingEnderPearlMessage throwingEnderPearlMessage, IPayloadContext supplier) {
        IPayloadContext context = supplier;

        context.enqueueWork(() -> {
            pressAction(context.player(), throwingEnderPearlMessage.type, throwingEnderPearlMessage.pressedms);
        });
    }

    public static void pressAction(Player player, int i, int j) {
        Level level = player.level();

        if (level.hasChunkAt(player.blockPosition()) && !level.isClientSide()) {
            if (i == 0) {
                ThrowingPearlKeyPressedEvent.execute(player);
            }
        }
    }
}

