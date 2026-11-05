package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.entity.ai.RecoveryAi;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/** Explicit, temporary per-NPC diagnostics. Never invoke goal eligibility/pathfinding from tracing. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class RecoveryTrace {
    private static final Map<UUID, Session> SESSIONS = new LinkedHashMap<>();
    private static final class Session {
        final AVNpc npc;
        final long expires;
        final Map<String, String> decisions = new LinkedHashMap<>();
        Session(AVNpc npc, long expires) { this.npc = npc; this.expires = expires; }
    }

    public static void note(AVNpc npc, String stage, String reason) {
        Session session = SESSIONS.get(npc.getUUID());
        if (session != null) {
            session.decisions.put(stage, reason + "@tick=" + npc.tickCount);
            // Keep short-lived jump/landing events visible between the one-second samples.
            if (reason.startsWith("jump_started")) session.decisions.put("lastJump", reason + "@tick=" + npc.tickCount);
            if (reason.startsWith("landed")) session.decisions.put("lastLanding", reason + "@tick=" + npc.tickCount);
        }
    }

    @SubscribeEvent
    public static void tick(ServerTickEvent.Post event) {
        if (false || SESSIONS.isEmpty()) return;
        long now = event.getServer().overworld().getGameTime();
        if (now % 20 != 0) return;
        var iterator = SESSIONS.values().iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (now >= session.expires || session.npc.isRemoved()) {
                AnnoyingVillagers.LOGGER.info("AV recovery trace ended: npc={} uuid={} reason={}",
                        session.npc.getName().getString(), session.npc.getUUID(),
                        session.npc.isRemoved() ? "entity_removed" : "trace_expired");
                iterator.remove();
            } else snapshot(session);
        }
    }

    @SubscribeEvent
    public static void stopped(ServerStoppedEvent event) { SESSIONS.clear(); }

    private static void snapshot(Session session) {
        AVNpc npc = session.npc;
        var target = npc.getTarget();
        StringBuilder blocks = new StringBuilder();
        for (int slot = 0; slot < npc.getInventory().getContainerSize(); slot++) {
            var stack = npc.getInventory().getItem(slot);
            if (stack.getItem() instanceof BlockItem) blocks.append(slot).append(':')
                    .append(BuiltInRegistries.ITEM.getKey(stack.getItem())).append('x').append(stack.getCount()).append(' ');
        }
        String goals = npc.goalSelector.getAvailableGoals().stream().filter(g -> g.isRunning())
                .map(g -> g.getPriority() + ":" + g.getGoal().getClass().getSimpleName()
                        + ":interruptible=" + g.isInterruptable() + ":flags=" + g.getFlags())
                .collect(Collectors.joining(","));
        AnnoyingVillagers.LOGGER.info("AV recovery trace: npc={}#{} uuid={} tick={} dim={} pos={} velocity={} ground={} collision={} noAI={} passenger={} healing={} rigLocked={} stunned={} recovery={} rig={} target={} hand={} blocks=[{}] eligibleBlockSlot={} navDone={} running=[{}] decisions={}",
                npc.getName().getString(), npc.getId(), npc.getUUID(), npc.tickCount, npc.level().dimension().identifier(),
                npc.position(), npc.getDeltaMovement(), npc.onGround(), npc.horizontalCollision, npc.isNoAi(), npc.isPassenger(),
                npc.isHealing(), npc.isLocked(), RigStunController.isStunned(npc), npc.isRecoveryActionActive(),
                RigAnimationController.getActiveAnimationId(npc),
                target == null ? "none" : target.getName().getString() + "#" + target.getId() + " pos=" + target.position()
                        + " dy=" + (target.getY() - npc.getY()) + " valid=" + RecoveryAi.validTarget(npc, target),
                npc.getMainHandItem(), blocks, new RecoveryAi(npc).blockSlot(npc.blockPosition()), npc.getNavigation().isDone(), goals,
                session.decisions.isEmpty() ? "not_checked_since_enabled" : session.decisions);
    }
}
