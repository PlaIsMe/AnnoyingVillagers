package com.pla.annoyinglib.bossbar;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.network.ClientboundBossBarStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.network.PacketDistributor;
import java.util.List;
import java.util.Objects;

/** Server-thread API. The owner supplies viewers and persists its own health/deadline/phase state. */
public class StyledBossBar extends ServerBossEvent {
    public enum Mode { HEALTH, TIMER }

    private BossBarStyle style;
    private Mode mode;
    private Component subtitle = Component.empty();
    private int phase;

    public StyledBossBar(Component title, Mode mode, BossBarStyle style) {
        super(title, BossBarColor.WHITE, BossBarOverlay.PROGRESS);
        this.mode = Objects.requireNonNull(mode);
        this.style = Objects.requireNonNull(style);
    }

    public BossBarStyle style() { return style; }
    public Mode mode() { return mode; }
    public int phase() { return phase; }
    public Component subtitle() { return subtitle.copy(); }

    public void setStyle(BossBarStyle style) {
        Objects.requireNonNull(style);
        if (this.style.equals(style)) return;
        this.style = style;
        phase = Math.min(phase, style.phases());
        sync();
    }

    public void setSubtitle(Component subtitle) {
        Objects.requireNonNull(subtitle);
        if (this.subtitle.equals(subtitle)) return;
        this.subtitle = subtitle.copy();
        sync();
    }

    /** One-based current phase; zero means no current phase. Default phase count is zero. */
    public void setPhase(int phase) {
        if (phase < 0 || phase > style.phases()) throw new IllegalArgumentException("Phase exceeds configured count");
        if (this.phase == phase) return;
        this.phase = phase;
        sync();
    }

    public void updateHealth(double health, double maximum) {
        setMode(Mode.HEALTH);
        setProgress(ratio(health, maximum));
    }

    /** Both arguments must use the same unit (ticks, seconds, etc.). Does not end the owner's event. */
    public void updateTimer(long remaining, long duration) {
        setMode(Mode.TIMER);
        setProgress(ratio(remaining, duration));
    }

    @Override public void setProgress(float progress) {
        super.setProgress(Float.isFinite(progress) ? Mth.clamp(progress, 0, 1) : 0);
    }

    private static float ratio(double value, double maximum) {
        return !Double.isFinite(value) || !Double.isFinite(maximum) || maximum <= 0
                ? 0 : (float) Math.max(0, Math.min(1, value / maximum));
    }

    private void setMode(Mode mode) {
        if (this.mode != mode) { this.mode = mode; sync(); }
    }

    @Override public void addPlayer(ServerPlayer player) {
        boolean added = !getPlayers().contains(player);
        super.addPlayer(player);
        if (added && isVisible()) send(player, false);
    }

    @Override public void removePlayer(ServerPlayer player) {
        boolean removed = getPlayers().contains(player);
        super.removePlayer(player);
        if (removed) send(player, true);
    }

    @Override public void removeAllPlayers() {
        for (ServerPlayer player : List.copyOf(getPlayers())) removePlayer(player);
    }

    @Override public void setVisible(boolean visible) {
        boolean changed = isVisible() != visible;
        super.setVisible(visible);
        if (changed) for (ServerPlayer player : getPlayers()) send(player, !visible);
    }

    private void sync() {
        if (isVisible()) for (ServerPlayer player : getPlayers()) send(player, false);
    }

    private void send(ServerPlayer player, boolean remove) {
        AnnoyingLib.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player),
                new ClientboundBossBarStyle(getId(), remove ? null : style, mode, subtitle, phase));
    }
}
