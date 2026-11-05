package com.pla.annoyingvillagers.client.compat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.network.ClientboundBetterCombatAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import punchy.client.animation.PunchyAnimationManager;
import punchy.client.animation.data.AnimationClip;
import punchy.config.PunchyConfig;
import punchy.client.state.AttackActionTracker;

import java.io.IOException;
import java.io.Reader;

/** Loaded only when Punchy is installed; ability packets bypass its ordinary BC attack events. */
public final class PunchyClientCompat {
    private static final Identifier ABILITY_ANIMATIONS = Identifier.fromNamespaceAndPath(
            "minecraft", "punchy/annoyingvillagers/bettercombat_abilities.json");
    private static PendingAbility pending;
    private static boolean reportedFailure;

    private PunchyClientCompat() {}

    public static void queueAbility(ClientboundBetterCombatAnimation message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.getId() == message.playerId()) queueAbility(player, message);
    }

    public static void queueAbility(LocalPlayer player, ClientboundBetterCombatAnimation message) {
        InteractionHand hand = message.animatedHand() == ClientboundBetterCombatAnimation.AnimatedHand.OFF_HAND
                ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        // Some AV weapons deliberately animate the opposite (empty) hand,
        // e.g. the active Ender Slayer Scythe. Scope by the ability's weapon,
        // not necessarily by the hand performing the animation.
        InteractionHand sourceHand = PunchyItemRenderContext.isAvItem(player.getMainHandItem())
                ? InteractionHand.MAIN_HAND : hand;
        ItemStack stack = player.getItemInHand(sourceHand);
        if (PunchyItemRenderContext.isAvItem(stack)) {
            pending = new PendingAbility(player, message, sourceHand, stack.copy());
        }
    }

    public static void flushAbility() {
        PendingAbility ability = pending;
        pending = null;
        Minecraft mc = Minecraft.getInstance();
        if (ability == null || mc.player != ability.player || mc.level == null
                || !mc.options.getCameraType().isFirstPerson()) return;

        try {
            if (!PunchyConfig.isModEnabled() || (net.neoforged.fml.ModList.get().isLoaded("bettercombat")
                    && !PunchyConfig.isBetterCombatCompatEnabled())) return;
            ClientboundBetterCombatAnimation message = ability.message;
            InteractionHand hand = message.animatedHand() == ClientboundBetterCombatAnimation.AnimatedHand.OFF_HAND
                    ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            boolean bothHands = message.animatedHand() == ClientboundBetterCombatAnimation.AnimatedHand.TWO_HANDED;
            // Durability / form components may synchronize between packet receipt
            // and playback; those updates must not discard the ability animation.
            if (!ItemStack.isSameItem(ability.stack, mc.player.getItemInHand(ability.sourceHand))) return;
            if (PunchyConfig.isHandBlacklisted(mc.player, hand)
                    || (bothHands && PunchyConfig.isHandBlacklisted(mc.player, InteractionHand.OFF_HAND))) return;

            // Resolve through the resource manager on each ability so F3+T and pack overrides work.
            var resource = mc.getResourceManager().getResource(ABILITY_ANIMATIONS);
            if (resource.isEmpty()) return;
            String clipName;
            try (Reader reader = resource.get().openAsReader()) {
                JsonObject mappings = JsonParser.parseReader(reader).getAsJsonObject();
                if (!mappings.has(message.animation())) return;
                clipName = mappings.get(message.animation()).getAsString();
            }
            AnimationClip clip = PunchyAnimationManager.resolveNamedClip(clipName, mc, hand);
            if (clip == null) return;

            // A vanilla swing / BC hit can leave an ordinary axe combo queued
            // while Punchy is throttling attacks. It must not replace the
            // server-confirmed Reaver punch on the following tick. Only consume
            // this AV ability's hand; leave unrelated offhand attacks alone.
            if (AttackActionTracker.peekHand() == hand) {
                AttackActionTracker.consumeHand();
                AttackActionTracker.clearPendingCritical();
            }
            HumanoidArm arm = hand == InteractionHand.OFF_HAND
                    ? mc.player.getMainArm().getOpposite() : mc.player.getMainArm();
            PunchyAnimationManager.setSourceHand(mc, hand);
            PunchyAnimationManager.POSE_HANDLER.setActiveArm(bothHands ? null : arm);
            PunchyAnimationManager.POSE_HANDLER.setLooping(false);
            PunchyAnimationManager.POSE_HANDLER.play(clip,
                    PunchyAnimationManager.clipSpeedMultiplier(clip), false, false);
        } catch (IOException | RuntimeException | LinkageError error) {
            if (!reportedFailure) {
                reportedFailure = true;
                AnnoyingVillagers.LOGGER.warn("Could not play Punchy weapon ability; keeping Better Combat playback", error);
            }
        }
    }

    private record PendingAbility(LocalPlayer player, ClientboundBetterCombatAnimation message,
                                  InteractionHand sourceHand, ItemStack stack) {}
}
