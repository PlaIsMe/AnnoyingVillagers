package com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo;

import mod.chloeprime.aaaparticles.api.client.EffectRegistry;
import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.DynamicParameter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TeleportPortalParticleEmitterInfo extends ParticleEmitterInfo {
    public enum ForwardAxis { PLUS_Z, PLUS_Y }

    private Vec3 pos = null;
    private Vec3 normal = new Vec3(0.0D, 0.0D, 1.0D);
    private ForwardAxis axis = ForwardAxis.PLUS_Z;
    private float roll = 0.0F;

    public TeleportPortalParticleEmitterInfo(ResourceLocation effek) {
        super(effek);
    }

    public TeleportPortalParticleEmitterInfo atPortal(Vec3 pos, Vec3 normal, ForwardAxis axis, float roll) {
        this.pos = pos;
        this.normal = normalizeOrDefault(normal);
        this.axis = axis == null ? ForwardAxis.PLUS_Z : axis;
        this.roll = roll;
        return this;
    }

    private static Vec3 normalizeOrDefault(Vec3 direction) {
        if (direction == null || direction.lengthSqr() < 1.0E-7D) {
            return new Vec3(0.0D, 0.0D, 1.0D);
        }
        return direction.normalize();
    }

    private static void aim(ParticleEmitter emitter, Vec3 direction, ForwardAxis axis, float roll) {
        Vec3 d = normalizeOrDefault(direction);
        double xz = Math.sqrt(d.x * d.x + d.z * d.z);

        switch (axis) {
            case PLUS_Z -> {
                float yaw = (float) Math.atan2(d.x, d.z);
                float pitch = (float) -Math.atan2(d.y, xz);
                emitter.setRotation(pitch, yaw, roll);
            }
            case PLUS_Y -> {
                float yaw = (float) Math.atan2(d.z, d.x) + (float) Math.PI / 2.0F;
                float pitch = (float) Math.atan2(xz, d.y) - (float) Math.PI / 2.0F;
                emitter.setRotation(pitch, yaw, roll);
            }
        }
    }

    private void applyCommonSettings(ParticleEmitter emitter) {
        if (this.isScaleSet()) {
            emitter.setScale(this.scaleX, this.scaleY, this.scaleZ);
        }

        if (this.hasParameters()) {
            for (DynamicParameter parameter : this.parameters) {
                emitter.setDynamicInput(parameter.index(), parameter.value());
            }
        }

        if (this.hasTriggers()) {
            for (int i = 0; i < this.triggers.size(); i++) {
                emitter.sendTrigger(this.triggers.getInt(i));
            }
        }
    }

    @Override
    public void spawnInWorld(Level level, Player player) {
        if (NativePlatform.isRunningOnUnsupportedPlatform() || this.pos == null) {
            return;
        }

        EffectRegistry.load(this.effek).thenAccept((effek) -> {
            ParticleEmitter emitter = this.hasEmitter() ? effek.play(this.emitter) : effek.play();

            this.applyCommonSettings(emitter);
            emitter.setPosition((float) this.pos.x, (float) this.pos.y, (float) this.pos.z);
            aim(emitter, this.normal, this.axis, this.roll);
        });
    }
}
