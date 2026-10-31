package com.pla.annoyinglib.task;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber
public class TaskScheduler {
    private static final List<ScheduledTask> tasks = new LinkedList<>();
    private static final List<ScheduledTask> pendingTasks = new LinkedList<>();

    public static void schedule(Runnable task, int delayTicks) {
        synchronized (pendingTasks) {
            pendingTasks.add(new ScheduledTask(task, delayTicks));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        synchronized (pendingTasks) {
            tasks.addAll(pendingTasks);
            pendingTasks.clear();
        }

        Iterator<ScheduledTask> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            ScheduledTask task = iterator.next();
            task.ticksRemaining--;
            if (task.ticksRemaining <= 0) {
                try {
                    task.task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                iterator.remove();
            }
        }
    }

    private static class ScheduledTask {
        Runnable task;
        int ticksRemaining;

        ScheduledTask(Runnable task, int ticks) {
            this.task = task;
            this.ticksRemaining = ticks;
        }
    }
}
