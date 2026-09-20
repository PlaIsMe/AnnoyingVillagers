package com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo;

import mod.chloeprime.aaaparticles.api.client.EffectDefinition;
import mod.chloeprime.aaaparticles.api.client.EffectHolder;
import mod.chloeprime.aaaparticles.api.client.EffectRegistry;
import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EnderGlaiveExplosionParticleEmitterInfo extends ParticleEmitterInfo {
    public enum ForwardAxis { PLUS_Z, PLUS_Y }

    private ForwardAxis axis = ForwardAxis.PLUS_Z;
    private float roll = 0f;
    private Vec3 from = null;
    private Vec3 to   = null;

    public EnderGlaiveExplosionParticleEmitterInfo(Identifier resourceLocation) {
        super(resourceLocation);
    }

    public EnderGlaiveExplosionParticleEmitterInfo fromTo(Vec3 from, Vec3 to, ForwardAxis axis, float extraRollRad, boolean flip) {
        this.from = from;
        this.to = to;
        this.axis = axis;
        this.roll = extraRollRad;
        return this;
    }

    private static void aim(ParticleEmitter em, Vec3 from, Vec3 to, ForwardAxis axis, float roll, boolean flip) {
        Vec3 d = to.subtract(from);
        if (flip) d = d.scale(-1); // flip forward/backward
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);

        switch (axis) {
            case PLUS_Z -> {
                float yaw   = (float) Math.atan2(d.x, d.z);
                float pitch = (float)-Math.atan2(d.y, xz);
                em.setRotation(pitch, yaw, roll);
            }
            case PLUS_Y -> {
                float yaw   = (float) Math.atan2(d.z, d.x) + (float)Math.PI/2f;
                float pitch = (float) Math.atan2(xz, d.y) - (float)Math.PI/2f;
                em.setRotation(pitch, yaw, roll);
            }
        }
    }

    @Override
    public void spawnInWorld(Level level, Player player) {
        if (NativePlatform.isRunningOnUnsupportedPlatform()) return;
        if (from == null || to == null) return;

        EffectRegistry.load(this.effek).thenAccept((effek) -> {
            ParticleEmitter em = this.hasEmitter() ? effek.play(this.emitter) : effek.play();

            if (this.hasParameters()) for (var p : this.parameters) em.setDynamicInput(p.index(), p.value());
            if (this.hasTriggers())    this.triggers.forEach(em::sendTrigger);

            em.setPosition((float) from.x, (float) from.y, (float) from.z);
            aim(em, from, to, axis, roll);
        });
    }

    private static void aim(ParticleEmitter em, Vec3 from, Vec3 to, ForwardAxis axis, float roll) {
        Vec3 d = to.subtract(from);
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);

        switch (axis) {
            case PLUS_Z -> {
                float yaw   = (float) Math.atan2(d.x, d.z);
                float pitch = (float)-Math.atan2(d.y, xz);
                em.setRotation(pitch, yaw, roll);
            }
            case PLUS_Y -> {
                float yaw   = (float) Math.atan2(d.z, d.x) + (float)Math.PI/2f;
                float pitch = (float) Math.atan2(xz, d.y) - (float)Math.PI/2f;
                em.setRotation(pitch, yaw, roll);
            }
        }
    }
}
