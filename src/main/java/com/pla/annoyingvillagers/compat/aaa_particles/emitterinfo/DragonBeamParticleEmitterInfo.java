package com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import mod.chloeprime.aaaparticles.api.client.EffectDefinition;
import mod.chloeprime.aaaparticles.api.client.EffectHolder;
import mod.chloeprime.aaaparticles.api.client.EffectRegistry;
import mod.chloeprime.aaaparticles.api.common.DynamicParameter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.Identifier;

import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DragonBeamParticleEmitterInfo extends ParticleEmitterInfo {
    public enum ForwardAxis { PLUS_Z, PLUS_Y }

    private WeakReference<HerobrineDragonEntity> casterRef = new WeakReference<>(null);
    private WeakReference<LivingEntity> targetRef = new WeakReference<>(null);
    private int durationTicks = 0;
    private ForwardAxis axis = ForwardAxis.PLUS_Z;
    private float roll = 0f;
    private Vec3 lastTargetPos = null;

    public DragonBeamParticleEmitterInfo(Identifier effek) {
        super(effek);
    }

    public DragonBeamParticleEmitterInfo fromTo(Vec3 from, Vec3 to, ForwardAxis axis, float extraRollRad) {
        this.position(from.x, from.y, from.z);
        this.axis = axis;
        this.roll = extraRollRad;
        this.lastTargetPos = to;

        Vec3 d = to.subtract(from);
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);
        switch (axis) {
            case PLUS_Z -> {
                float yaw   = (float) Math.atan2(d.x, d.z);
                float pitch = (float)-Math.atan2(d.y, xz);
                this.rotation(pitch, yaw, extraRollRad);
            }
            case PLUS_Y -> {
                float yaw   = (float) Math.atan2(d.z, d.x) + (float)Math.PI/2f;
                float pitch = (float) Math.atan2(xz, d.y) - (float)Math.PI/2f;
                this.rotation(pitch, yaw, extraRollRad);
            }
        }
        return this;
    }

    public DragonBeamParticleEmitterInfo follow(HerobrineDragonEntity caster, LivingEntity target, int durationTicks,
                                                ForwardAxis axis, float extraRollRad) {
        this.casterRef = new WeakReference<>(caster);
        this.targetRef = new WeakReference<>(target);
        this.durationTicks = durationTicks;
        this.axis = axis;
        this.roll = extraRollRad;
        return this;
    }

    private static Vec3 eyeLerped(EnderDragonPart e, float partial) {
        double x = Mth.lerp(partial, e.xOld, e.getX());
        double y = Mth.lerp(partial, e.yOld, e.getY()) + e.getEyeHeight();
        double z = Mth.lerp(partial, e.zOld, e.getZ());
        return new Vec3(x, y, z);
    }

    private static Vec3 eyeLerped(LivingEntity e, float partial) {
        double x = Mth.lerp(partial, e.xOld, e.getX());
        double y = Mth.lerp(partial, e.yOld, e.getY()) + e.getEyeHeight();
        double z = Mth.lerp(partial, e.zOld, e.getZ());
        return new Vec3(x, y, z);
    }

    private static void aim(ParticleEmitter em, Vec3 from, Vec3 to, ForwardAxis axis, float roll) {
        Vec3 d = to.subtract(from);
        double len = d.length();
        if (len < 1e-6) len = 1e-6;
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

    private static Vec3 mouthLerped(HerobrineDragonEntity herobrineDragonEntity, float partial) {
        return herobrineDragonEntity.beamMouthPos(partial);
    }

    @Override
    public void spawnInWorld(Level level, Player player) {
        if (NativePlatform.isRunningOnUnsupportedPlatform()) return;

        EffectRegistry.load(this.effek).thenAccept((effek) -> {
            ParticleEmitter em = this.hasEmitter() ? effek.play(this.emitter) : effek.play();

            if (this.hasParameters()) for (DynamicParameter p : this.parameters) em.setDynamicInput(p.index(), p.value());
            if (this.hasTriggers())    this.triggers.forEach(em::sendTrigger);

            final HerobrineDragonEntity caster0 = casterRef.get();
            if (caster0 == null || !caster0.isAlive()) { em.stop(); return; }
            final int startTick = caster0.tickCount;

            // first frame
            Vec3 from0 = mouthLerped(caster0, 1.0F);
            LivingEntity t0 = targetRef.get();
            Vec3 to0 = (t0 != null && t0.isAlive())
                    ? new Vec3(t0.getX(), t0.getEyeY(), t0.getZ())
                    : (lastTargetPos != null ? lastTargetPos : from0.add(caster0.getLookAngle()));
            lastTargetPos = to0;

            em.setPosition((float) from0.x, (float) from0.y, (float) from0.z);
            aim(em, from0, to0, axis, roll);

            em.addPreDrawCallback((Emitter, partial) -> {
                HerobrineDragonEntity c = casterRef.get();
                if (c == null || !c.isAlive()) { Emitter.stop(); return; }
                if (durationTicks > 0 && (c.tickCount - startTick) >= durationTicks) {
                    Emitter.stop(); return;
                }

                Vec3 from = mouthLerped(c, partial);
                LivingEntity t = targetRef.get();
                Vec3 to;
                if (t != null && t.isAlive()) {
                    to = eyeLerped(t, partial);
                    lastTargetPos = to;
                } else {
                    to = (lastTargetPos != null) ? lastTargetPos : from.add(c.getLookAngle());
                }

                Emitter.setPosition((float) from.x, (float) from.y, (float) from.z);
                aim(Emitter, from, to, axis, roll);
            });
        });
    }
}