package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RigCriticalUtil {
    private static final Map<UUID, VanillaCriticalMark> VANILLA_PLAYER_CRITICALS = new HashMap<>();

    private RigCriticalUtil() {}

    public static void markVanillaPlayerCritical(Player player, LivingEntity target) {
        VanillaCriticalMark mark = new VanillaCriticalMark(player.getUUID(), player.tickCount);
        VANILLA_PLAYER_CRITICALS.put(target.getUUID(), mark);
        new DelayedTask(1) {
            @Override
            public void run() {
                VANILLA_PLAYER_CRITICALS.remove(target.getUUID(), mark);
            }
        };
    }

    public static boolean isCriticalDamage(LivingEntity target, DamageSource source) {
        return RigDamageContext.isCritical(target, source) || isVanillaPlayerCritical(target, source);
    }

    public static boolean isVanillaPlayerCritical(LivingEntity target, DamageSource source) {
        if (!(source.getEntity() instanceof Player player) || source.getDirectEntity() != player) return false;
        VanillaCriticalMark mark = VANILLA_PLAYER_CRITICALS.get(target.getUUID());
        if (mark == null || !mark.playerUuid.equals(player.getUUID()) || mark.playerTick != player.tickCount) return false;
        if (mark.primarySource == null) mark.primarySource = source;
        return mark.primarySource == source;
    }

    public static void clearAll() {
        VANILLA_PLAYER_CRITICALS.clear();
    }

    private static final class VanillaCriticalMark {
        private final UUID playerUuid;
        private final int playerTick;
        private DamageSource primarySource;

        private VanillaCriticalMark(UUID playerUuid, int playerTick) {
            this.playerUuid = playerUuid;
            this.playerTick = playerTick;
        }
    }
}
