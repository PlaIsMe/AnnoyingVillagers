package com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo;

import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import mod.chloeprime.aaaparticles.api.client.EffectDefinition;
import mod.chloeprime.aaaparticles.api.client.EffectHolder;
import mod.chloeprime.aaaparticles.api.client.EffectRegistry;
import mod.chloeprime.aaaparticles.api.client.effekseer.ParticleEmitter;
import mod.chloeprime.aaaparticles.api.common.DynamicParameter;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import mod.chloeprime.aaaparticles.client.installer.NativePlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BlackFireParticleEmitterInfo extends ParticleEmitterInfo {
    private WeakReference<Entity> followEntityRef = new WeakReference<>(null);
    private Supplier<Vec3> followPositionSupplier = null;

    private boolean followEnabled = false;
    private boolean useEyePosition = false;

    private Vec3 offset = Vec3.ZERO;
    private Vec3 lastPos = null;

    private int durationTicks = 0;

    private double smoothing = 1.0D;

    public BlackFireParticleEmitterInfo(ResourceLocation effek) {
        super(effek);
    }

    public BlackFireParticleEmitterInfo(ResourceLocation effek, ResourceLocation emitter) {
        super(effek, emitter);
    }

    public BlackFireParticleEmitterInfo followEntity(Entity entity) {
        return this.followEntity(entity, 0, Vec3.ZERO);
    }

    public BlackFireParticleEmitterInfo followEntity(Entity entity, int durationTicks) {
        return this.followEntity(entity, durationTicks, Vec3.ZERO);
    }

    public BlackFireParticleEmitterInfo followEntity(Entity entity, int durationTicks, Vec3 offset) {
        this.followEnabled = true;
        this.followEntityRef = new WeakReference<>(entity);
        this.followPositionSupplier = null;
        this.durationTicks = durationTicks;
        this.offset = offset == null ? Vec3.ZERO : offset;
        this.useEyePosition = false;
        return this;
    }

    public BlackFireParticleEmitterInfo followEntityEye(Entity entity, int durationTicks, Vec3 offset) {
        this.followEntity(entity, durationTicks, offset);
        this.useEyePosition = true;
        return this;
    }

    public BlackFireParticleEmitterInfo followPosition(Supplier<Vec3> positionSupplier, int durationTicks) {
        this.followEnabled = true;
        this.followEntityRef = new WeakReference<>(null);
        this.followPositionSupplier = positionSupplier;
        this.durationTicks = durationTicks;
        this.offset = Vec3.ZERO;
        this.useEyePosition = false;
        return this;
    }

    private static Vec3 getInterpolatedEntityPosition(Entity entity, float partialTick) {
        double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());

        return new Vec3(x, y, z);
    }

    private static Vec3 getSwordPosition(Entity entity, float partialTick) {
        try {
            return EpicfightUtil.getJointWithTranslation(
                    entity,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    partialTick,
                    0.0F
            );
        } catch (Exception ignored) {
            return null;
        }
    }

    private Vec3 getCurrentFollowPosition(float partialTick) {
        Entity entity = this.followEntityRef.get();

        if (entity != null) {
            if (!entity.isAlive() || entity.isRemoved()) {
                return null;
            }

            Vec3 pos;

            if (entity instanceof BlackFireEntity blackFire) {
                pos = getBlackFireFollowPosition(blackFire, partialTick);
            } else {
                pos = getSwordPosition(entity, partialTick);

                if (pos == null) {
                    pos = getInterpolatedEntityPosition(entity, partialTick);
                }
            }

            return pos == null ? null : pos.add(this.offset);
        }

        if (this.followPositionSupplier != null) {
            Vec3 pos = this.followPositionSupplier.get();
            return pos == null ? null : pos.add(this.offset);
        }

        return null;
    }

    private static Vec3 getBlackFireFollowPosition(BlackFireEntity blackFire, float partialTick) {
        if (blackFire.isFollowOwnerSwordMode()) {
            Entity owner = blackFire.getOwnerEntity();

            if (owner != null && owner.isAlive() && !owner.isRemoved()) {
                Vec3 swordPos = getSwordPosition(owner, partialTick);

                if (swordPos != null) {
                    return swordPos;
                }
            }
        }

        return getInterpolatedEntityPosition(blackFire, partialTick);
    }

    public BlackFireParticleEmitterInfo smoothing(double value) {
        this.smoothing = Math.max(0.0D, Math.min(1.0D, value));
        return this;
    }

    private void applyCommonSettings(ParticleEmitter emitter) {
        if (this.isRotationSet()) {
            emitter.setRotation(this.rotX, this.rotY, this.rotZ);
        }

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
        if (NativePlatform.isRunningOnUnsupportedPlatform()) {
            return;
        }

        if (!this.followEnabled) {
            super.spawnInWorld(level, player);
            return;
        }

        EffectRegistry.load(this.effek).thenAccept((effek) -> {
            ParticleEmitter emitter = this.hasEmitter() ? effek.play(this.emitter) : effek.play();

            this.applyCommonSettings(emitter);

            final long startGameTime = level.getGameTime();

            Vec3 startPos = this.getCurrentFollowPosition(0.0F);
            if (startPos == null) {
                emitter.stop();
                return;
            }

            this.lastPos = startPos;
            emitter.setPosition((float) startPos.x, (float) startPos.y, (float) startPos.z);

            emitter.addPreDrawCallback((em, partial) -> {
                if (this.durationTicks > 0 && level.getGameTime() - startGameTime >= this.durationTicks) {
                    em.stop();
                    return;
                }

                Vec3 currentPos = this.getCurrentFollowPosition(partial);
                if (currentPos == null) {
                    em.stop();
                    return;
                }

                if (this.lastPos == null || this.smoothing >= 1.0D) {
                    this.lastPos = currentPos;
                } else {
                    this.lastPos = this.lastPos.lerp(currentPos, this.smoothing);
                }

                em.setPosition(
                        (float) this.lastPos.x,
                        (float) this.lastPos.y,
                        (float) this.lastPos.z
                );
            });
        });
    }
}