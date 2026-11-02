package com.pla.annoyinglib.task;

import com.pla.annoyinglib.AnnoyingLib;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class DelayedTask {
    private static final Scheduler SCHEDULER = new Scheduler();
    private static final Queue<DelayedTask> PENDING_ADD = new ConcurrentLinkedQueue<>();
    private static boolean schedulerRegistered = false;

    private int remainingTicks;
    private boolean cancelled = false;
    private boolean finished = false;

    public DelayedTask(int waitTicks) {
        if (waitTicks < 0) {
            throw new IllegalArgumentException("waitTicks must be >= 0");
        }

        this.remainingTicks = waitTicks;
        ensureSchedulerRegistered();
        PENDING_ADD.add(this);
    }

    public abstract void run();

    protected void onCancel() {
    }

    public final void cancel() {
        this.cancelled = true;
    }

    public final boolean isCancelled() {
        return cancelled;
    }

    public final boolean isFinished() {
        return finished;
    }

    public final int getRemainingTicks() {
        return Math.max(remainingTicks, 0);
    }

    private boolean tickInternal() {
        if (finished) {
            return true;
        }

        if (cancelled) {
            finished = true;
            safeRun(this::onCancel, "DelayedTask onCancel");
            return true;
        }

        if (remainingTicks > 0) {
            remainingTicks--;
            if (remainingTicks > 0) {
                return false;
            }
        }

        finished = true;
        safeRun(this::run, "DelayedTask run");
        return true;
    }

    private static synchronized void ensureSchedulerRegistered() {
        if (!schedulerRegistered) {
            MinecraftForge.EVENT_BUS.register(SCHEDULER);
            schedulerRegistered = true;
        }
    }

    private static void safeRun(Runnable action, String label) {
        try {
            action.run();
        } catch (Exception e) {
            AnnoyingLib.LOGGER.error("[AV MOD DEBUG] {} failed", label, e);
        }
    }

    private static final class Scheduler {
        private final List<DelayedTask> activeTasks = new ArrayList<>();

        @SubscribeEvent
        public void onServerTick(TickEvent.ServerTickEvent event) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }

            DelayedTask pendingTask;
            while ((pendingTask = PENDING_ADD.poll()) != null) {
                activeTasks.add(pendingTask);
            }

            Iterator<DelayedTask> iterator = activeTasks.iterator();
            while (iterator.hasNext()) {
                DelayedTask task = iterator.next();
                if (task.tickInternal()) {
                    iterator.remove();
                }
            }
        }

        @SubscribeEvent
        public void onServerStopped(ServerStoppedEvent event) {
            activeTasks.clear();
            PENDING_ADD.clear();
        }
    }
}
