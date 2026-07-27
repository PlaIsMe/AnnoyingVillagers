/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: CC-BY-NC-SA-3.0
 *
 * Upstream: Epic-Fight-Impactful
 * Source: https://github.com/Cyber2049/Epic-Fight-Impactful
 *
 * This file contains code adapted from the upstream project (Screen Shake algorithm).
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/LGPL-2.1.md
 *
 * Modifications and integration:
 *   Copyright (c) 2025 pla_is_me
 */

package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.PriorityQueue;

@OnlyIn(Dist.CLIENT)
public class CameraEngine {
    public CameraEngine(){
        instance = this;
    }
    private static CameraEngine instance;
    public static CameraEngine getInstance(){
        return instance;
    }
    public PriorityQueue<ShakeEntry> getQueue(){
        return this.queue;
    }
    private final PriorityQueue<ShakeEntry> queue =
            new PriorityQueue<>(Comparator.comparingDouble(e -> -e.strength));
    private final ShakeEntry default_entry = new ShakeEntry(1d,3,0.3d, 0);
    public void tick(ViewportEvent.ComputeCameraAngles event, Player player) {
        if (Minecraft.getInstance().isPaused() || queue.isEmpty()) return;

        queue.removeIf(entry -> {
            entry.remainingTicks--;
            if(entry.remainingTicks < entry.decay_time){
                entry.strength *= 0.97;
                entry.frequency *= 0.97;
            }
            return entry.remainingTicks <= 0;
        });

        if (!queue.isEmpty()) {
            ShakeEntry top = queue.peek();
            double ticksExistedDelta = player.tickCount + event.getPartialTick();
            double k = top.strength / 4F;
            double f = top.frequency;
            event.setPitch((float) (event.getPitch() + k * Math.cos(ticksExistedDelta * f + 2)));
            event.setYaw((float) (event.getYaw() + k * Math.cos(ticksExistedDelta * f + 1)));
            event.setRoll((float) (event.getRoll() + k * Math.cos(ticksExistedDelta * f)));
        }
    }

    public void shakeCamera(ShakeEntry entry){
        ShakeEntry entry1 = entry.copy();
        queue.add(entry1);
    }
    public void shakeCamera(float strength, int time, float frequency, int decay_time){
        this.shakeCamera(new ShakeEntry(strength, time, frequency, decay_time));
    }
    public void shakeCamera(int time, float strength, int decay_time){
        this.shakeCamera(new ShakeEntry(strength, time, 0.3, decay_time));
    }

    @Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
    public static class Events {
        @SubscribeEvent(priority = EventPriority.LOW)
        public static void cameraSetupEvent(ViewportEvent.ComputeCameraAngles event) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            if (instance == null) return;

            instance.tick(event, player);
        }
    }

    public static class ShakeEntry {
        double strength;
        int remainingTicks;
        int decay_time;
        double frequency;
        public ShakeEntry(double strength, int tick, double frequency, int decay_time) {
            this.strength = strength;
            this.remainingTicks = tick;
            this.frequency = frequency;
            this.decay_time = decay_time;
        }
        public ShakeEntry(double strength, int tick, int decay_time){
            this(strength, tick, 0.3f, decay_time);
        }

        public ShakeEntry copy(){
            return new ShakeEntry(this.strength, this.remainingTicks, this.frequency, this.decay_time);
        }
    }
}