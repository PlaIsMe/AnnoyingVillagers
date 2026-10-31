package com.pla.annoyinglib.client.compat;

import com.pla.annoyinglib.network.ClientboundBetterCombatAnimation;
import net.bettercombat.client.animation.AnimationRegistry;
import net.bettercombat.client.animation.PlayerAttackAnimatable;
import net.bettercombat.logic.AnimatedHand;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class BetterCombatClientCompat {
    private BetterCombatClientCompat() {
    }

    public static void playAnimation(ClientboundBetterCombatAnimation message) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || !AnimationRegistry.animations.containsKey(message.animation())) return;

        Entity entity = minecraft.level.getEntity(message.playerId());
        if (!(entity instanceof PlayerAttackAnimatable animatable)) return;

        AnimatedHand hand = switch (message.animatedHand()) {
            case MAIN_HAND -> AnimatedHand.MAIN_HAND;
            case OFF_HAND -> AnimatedHand.OFF_HAND;
            case TWO_HANDED -> AnimatedHand.TWO_HANDED;
        };
        animatable.playAttackAnimation(message.animation(), hand, message.swingDurationTicks(), message.upswing());
    }

}
