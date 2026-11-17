package com.pla.annoyinglib.network;

import com.pla.annoyinglib.bossbar.BossBarHead;
import com.pla.annoyinglib.bossbar.BossBarStyle;
import com.pla.annoyinglib.bossbar.StyledBossBar;
import com.pla.annoyinglib.client.gui.StyledBossBarOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

/** Metadata only; vanilla owns title, progress, visibility and UUID of the actual boss event. */
public record ClientboundBossBarStyle(UUID id, @Nullable BossBarStyle style, StyledBossBar.Mode mode,
                                     Component subtitle, int phase) {
    public static void encode(ClientboundBossBarStyle msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.id);
        buf.writeBoolean(msg.style != null);
        if (msg.style == null) return;
        BossBarStyle style = msg.style;
        buf.writeInt(style.color());
        buf.writeVarInt(style.phases());
        buf.writeBoolean(style.compact());
        buf.writeResourceLocation(style.frame());
        buf.writeResourceLocation(style.accent());
        buf.writeResourceLocation(style.detail());
        buf.writeBoolean(style.head() != null);
        if (style.head() != null) {
            BossBarHead h = style.head();
            buf.writeResourceLocation(h.texture());
            buf.writeVarInt(h.u()); buf.writeVarInt(h.v());
            buf.writeVarInt(h.width()); buf.writeVarInt(h.height());
            buf.writeVarInt(h.textureWidth()); buf.writeVarInt(h.textureHeight());
        }
        buf.writeEnum(msg.mode);
        buf.writeComponent(msg.subtitle);
        buf.writeVarInt(msg.phase);
    }

    public static ClientboundBossBarStyle decode(FriendlyByteBuf buf) {
        UUID id = buf.readUUID();
        if (!buf.readBoolean()) return new ClientboundBossBarStyle(id, null, StyledBossBar.Mode.HEALTH, Component.empty(), 0);
        int color = buf.readInt(), phases = buf.readVarInt();
        boolean compact = buf.readBoolean();
        var frame = buf.readResourceLocation();
        var accent = buf.readResourceLocation();
        var detail = buf.readResourceLocation();
        BossBarHead head = buf.readBoolean() ? new BossBarHead(buf.readResourceLocation(), buf.readVarInt(),
                buf.readVarInt(), buf.readVarInt(), buf.readVarInt(), buf.readVarInt(), buf.readVarInt()) : null;
        BossBarStyle style = new BossBarStyle(color, head, phases, compact, frame, accent, detail);
        StyledBossBar.Mode mode = buf.readEnum(StyledBossBar.Mode.class);
        Component subtitle = buf.readComponent();
        int phase = buf.readVarInt();
        if (phase < 0 || phase > phases) throw new IllegalArgumentException("Invalid boss bar phase");
        return new ClientboundBossBarStyle(id, style, mode, subtitle, phase);
    }

    public static void handle(ClientboundBossBarStyle msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> StyledBossBarOverlay.accept(msg)));
        context.get().setPacketHandled(true);
    }
}
