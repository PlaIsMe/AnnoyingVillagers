package com.pla.annoyingvillagers.client.compat;

import com.pla.annoyingvillagers.network.ClientboundBetterCombatAnimation;
import net.bettercombat.api.MinecraftClient_BetterCombat;
import net.bettercombat.client.animation.PlayerAttackAnimatable;
import net.bettercombat.logic.AnimatedHand;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public final class BetterCombatClientCompat {
    private BetterCombatClientCompat() {
    }

    public static void playAnimation(ClientboundBetterCombatAnimation message) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        Entity entity = minecraft.level.getEntity(message.playerId());
        if (!(entity instanceof PlayerAttackAnimatable animatable)) return;

        // A locally-started Better Combat combo may still be in its upswing while the
        // authoritative AV ability animation is travelling back from the server. If it
        // is left active, Better Combat advances that combo and replaces the requested
        // animation (most visibly on the awakened Demoniac Voltage Reaver).
        if (entity == minecraft.player) {
            ((MinecraftClient_BetterCombat)(Object)minecraft).cancelUpswing();
        }

        AnimatedHand hand = switch (message.animatedHand()) {
            case MAIN_HAND -> AnimatedHand.MAIN_HAND;
            case OFF_HAND -> AnimatedHand.OFF_HAND;
            case TWO_HANDED -> AnimatedHand.TWO_HANDED;
        };
        animatable.playAttackAnimation(message.animation(), hand, message.swingDurationTicks(), message.upswing());
    }

}
