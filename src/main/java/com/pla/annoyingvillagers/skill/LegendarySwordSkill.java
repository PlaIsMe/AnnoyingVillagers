package com.pla.annoyingvillagers.skill;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class LegendarySwordSkill extends WeaponInnateSkill {
    public LegendarySwordSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, FriendlyByteBuf friendlyByteBuf) {
        LivingEntity entity = skillContainer.getExecutor().getOriginal();
        ServerLevel serverLevel = (ServerLevel) entity.level();

        skillContainer.getExecutor().playAnimationSynchronized(AnimsAgony.AGONY_RISING_EAGLE, 0.0F);
        entity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));
        new DelayedTask(10) {
            @Override
            public void run() {
                serverLevel.playSound(
                        null,
                        entity.getX(), entity.getY(), entity.getZ(),
                        AnnoyingVillagersModSounds.HEAVY_ATTACK_START.get(),
                        SoundSource.NEUTRAL,
                        1.0F, 1.0F
                );

                serverLevel.playSound(
                        null,
                        entity.getX(), entity.getY(), entity.getZ(),
                        AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD.get(),
                        SoundSource.NEUTRAL,
                        1.0F, 1.0F
                );

                serverLevel.playSound(
                        null,
                        entity.getX(), entity.getY(), entity.getZ(),
                        AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD_2.get(),
                        SoundSource.NEUTRAL,
                        1.0F, 1.0F
                );

                serverLevel.sendParticles(
                        ParticleTypes.TOTEM_OF_UNDYING,
                        entity.getX(), entity.getY(), entity.getZ(),
                        15,
                        0.0D, 0.0D, 0.0D,
                        0.2D);

                serverLevel.sendParticles(
                        ParticleTypes.TOTEM_OF_UNDYING,
                        entity.getX(), entity.getEyeY(), entity.getZ(),
                        100,
                        0.0D, 0.0D, 0.0D,
                        0.5D
                );
                skillContainer.getExecutor().playAnimationSynchronized(AnimsPugilistSteve.LEGENDARY_SWORD_HEAVY_ATTACK, 0.0F);
            }
        };
        super.executeOnServer(skillContainer, friendlyByteBuf);
    }
}
