package com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo;

import com.pla.annoyingvillagers.entity.BlueDemonThunderBeamEntity;
import mod.chloeprime.aaaparticles.api.client.EffectDefinition;
import mod.chloeprime.aaaparticles.api.client.EffectHolder;
import mod.chloeprime.aaaparticles.api.client.EffectRegistry;
import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.DynamicParameter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BlueDemonThunderBeamParticleEmitterInfo extends ParticleEmitterInfo {
    public enum ForwardAxis { PLUS_Z, PLUS_Y }

    private WeakReference<BlueDemonThunderBeamEntity> beamRef = new WeakReference<>(null);
    private int durationTicks = 0;
    private ForwardAxis axis = ForwardAxis.PLUS_Z;
    private float roll = 0f;
    private Vec3 lastStartPos = null;
    private Vec3 lastEndPos = null;

    public BlueDemonThunderBeamParticleEmitterInfo(Identifier effek) {
        super(effek);
    }

    public BlueDemonThunderBeamParticleEmitterInfo fromTo(Vec3 from, Vec3 to, ForwardAxis axis, float extraRollRad) {
        this.position(from.x, from.y, from.z);
        this.axis = axis;
        this.roll = extraRollRad;
        this.lastStartPos = from;
        this.lastEndPos = to;
        applyRotation(this, from, to, axis, extraRollRad);
        return this;
    }

    public BlueDemonThunderBeamParticleEmitterInfo followBeam(BlueDemonThunderBeamEntity beam,
                                                              int durationTicks,
                                                              ForwardAxis axis,
                                                              float extraRollRad) {
        this.beamRef = new WeakReference<>(beam);
        this.durationTicks = durationTicks;
        this.axis = axis;
        this.roll = extraRollRad;
        return this;
    }

    private static void applyRotation(ParticleEmitterInfo info, Vec3 from, Vec3 to, ForwardAxis axis, float roll) {
        Vec3 d = to.subtract(from);
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);

        switch (axis) {
            case PLUS_Z -> {
                float yaw = (float) Math.atan2(d.x, d.z);
                float pitch = (float) -Math.atan2(d.y, xz);
                info.rotation(pitch, yaw, roll);
            }
            case PLUS_Y -> {
                float yaw = (float) Math.atan2(d.z, d.x) + (float) Math.PI / 2f;
                float pitch = (float) Math.atan2(xz, d.y) - (float) Math.PI / 2f;
                info.rotation(pitch, yaw, roll);
            }
        }
    }

    private static void aim(ParticleEmitter em, Vec3 from, Vec3 to, ForwardAxis axis, float roll) {
        Vec3 d = to.subtract(from);
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);

        switch (axis) {
            case PLUS_Z -> {
                float yaw = (float) Math.atan2(d.x, d.z);
                float pitch = (float) -Math.atan2(d.y, xz);
                em.setRotation(pitch, yaw, roll);
            }
            case PLUS_Y -> {
                float yaw = (float) Math.atan2(d.z, d.x) + (float) Math.PI / 2f;
                float pitch = (float) Math.atan2(xz, d.y) - (float) Math.PI / 2f;
                em.setRotation(pitch, yaw, roll);
            }
        }
    }

    @Override
    public void spawnInWorld(Level level, Player player) {
        if (NativePlatform.isRunningOnUnsupportedPlatform()) return;

        EffectRegistry.load(this.effek).thenAccept((effek) -> {
            ParticleEmitter em = this.hasEmitter() ? effek.play(this.emitter) : effek.play();

            if (this.hasParameters()) {
                for (DynamicParameter p : this.parameters) {
                    em.setDynamicInput(p.index(), p.value());
                }
            }

            if (this.hasTriggers()) {
                this.triggers.forEach(em::sendTrigger);
            }

            BlueDemonThunderBeamEntity beam = beamRef.get();
            if (beam == null || !beam.isAlive()) {
                em.stop();
                return;
            }

            final int startTick = beam.tickCount;

            Vec3 from0 = beam.getStartPos();
            Vec3 to0 = beam.getEndPos();

            this.lastStartPos = from0;
            this.lastEndPos = to0;

            em.setPosition((float) from0.x, (float) from0.y, (float) from0.z);
            aim(em, from0, to0, axis, roll);

            em.addPreDrawCallback((Emitter, partial) -> {
                BlueDemonThunderBeamEntity b = beamRef.get();
                if (b == null || !b.isAlive() || b.isRemoved()) {
                    Emitter.stop();
                    return;
                }

                if (durationTicks > 0 && (b.tickCount - startTick) >= durationTicks) {
                    Emitter.stop();
                    return;
                }

                Vec3 from = b.getStartPos();
                Vec3 to = b.getEndPos();

                if (lastStartPos == null) lastStartPos = from;
                if (lastEndPos == null) lastEndPos = to;

                lastStartPos = lastStartPos.lerp(from, 0.35D);
                lastEndPos = lastEndPos.lerp(to, 0.35D);

                Emitter.setPosition((float) lastStartPos.x, (float) lastStartPos.y, (float) lastStartPos.z);
                aim(Emitter, lastStartPos, lastEndPos, axis, roll);
            });
        });
    }
}