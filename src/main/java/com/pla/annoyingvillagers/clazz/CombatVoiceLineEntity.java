package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;

public interface CombatVoiceLineEntity {
    int getVoiceCooldown();
    void setVoiceCooldown(int cooldown);

    default int getMinVoiceCooldown() {
        return 300;
    }

    default int getMaxVoiceCooldown() {
        return 600;
    }

    default void tickVoiceCooldown() {
        if (getVoiceCooldown() > 0) {
            setVoiceCooldown(getVoiceCooldown() - 1);
        }
    }

    default void resetVoiceCooldown(Mob self) {
        int min = Math.min(getMinVoiceCooldown(), getMaxVoiceCooldown());
        int max = Math.max(getMinVoiceCooldown(), getMaxVoiceCooldown());
        setVoiceCooldown(Mth.nextInt(self.getRandom(), min, max));
    }

    default boolean hasValidVoiceTarget(Mob self) {
        LivingEntity target = self.getTarget();
        return target != null && target.isAlive();
    }

    default boolean canPlayVoiceLine(Mob self) {
        return !self.level().isClientSide
                && self.isAlive()
                && getVoiceCooldown() <= 0
                && hasValidVoiceTarget(self);
    }

    @Nullable
    default SoundEvent getHurtVoiceSound() {
        return null;
    }

    @Nullable
    default SoundEvent getAttackVoiceSound() {
        return null;
    }

    default boolean canSayHurtSound(Mob self, DamageSource source) {
        return canPlayVoiceLine(self);
    }

    default boolean canSayAttackSound(Mob self, Entity target) {
        return canPlayVoiceLine(self);
    }

    default void sayHurtSound(Mob self, DamageSource source) {
        if (!(self.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!AnnoyingVillagersConfig.TURN_ON_NPC_VOICE.get()) {
            return;
        }

        SoundEvent sound = getHurtVoiceSound();
        if (sound == null) {
            return;
        }

        if (!canSayHurtSound(self, source)) {
            return;
        }

        serverLevel.playSound(
                null,
                self.getX(),
                self.getY(),
                self.getZ(),
                sound,
                SoundSource.HOSTILE,
                0.5F,
                1.0F
        );

        resetVoiceCooldown(self);
    }

    default void sayAttackSound(Mob self, Entity target) {
        if (!(self.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!AnnoyingVillagersConfig.TURN_ON_NPC_VOICE.get()) {
            return;
        }

        SoundEvent sound = getAttackVoiceSound();
        if (sound == null) {
            return;
        }

        if (!canSayAttackSound(self, target)) {
            return;
        }

        serverLevel.playSound(
                null,
                self.getX(),
                self.getY(),
                self.getZ(),
                sound,
                SoundSource.HOSTILE,
                0.5F,
                1.0F
        );

        resetVoiceCooldown(self);
    }
}