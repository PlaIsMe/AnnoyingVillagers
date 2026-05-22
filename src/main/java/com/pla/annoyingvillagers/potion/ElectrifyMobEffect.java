package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.gameasset.AnimsTacticalImbuements;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

public class ElectrifyMobEffect extends MobEffect {

    public ElectrifyMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -16711681);
    }

    public @NotNull String getDescriptionId() {
        return "effect.annoyingvillagers.electrify";
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        double d0 = pLivingEntity.getX();
        double d1 = pLivingEntity.getY();
        double d2 = pLivingEntity.getZ();

        if (pLivingEntity.tickCount % 20 == 0) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(pLivingEntity, LivingEntityPatch.class);

            if (livingEntityPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (dynamicAnimation != null
                        && !livingEntityPatch.isStunned()
                        && !ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch)
                        && !(dynamicAnimation.get() instanceof ExecutionAttackAnimation)
                        && !(dynamicAnimation.get() instanceof ExecutionHitAnimation)) {
                    if (pAmplifier > 1) {
                        livingEntityPatch.playAnimationSynchronized(AnimsTacticalImbuements.ZAP_LONG, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsTacticalImbuements.ZAP, 0.0F);
                    }
                }
            }
        }

        if (Math.random() <= 0.1D) {
            if (pLivingEntity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                        pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(),
                        1,
                        0.3D, 1.2D, 0.3D,
                        0.0D
                );

                if (serverLevel.random.nextDouble() <= 0.8D) {
                    float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                    float pitch  = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                    serverLevel.playSound(
                            null,
                            BlockPos.containing(d0, d1, d2),
                            AnnoyingVillagersModSounds.ELECTRIFY.get(),
                            SoundSource.NEUTRAL,
                            volume,
                            pitch
                    );
                }
            }
        }

        if (Math.random() <= (pAmplifier > 1 ? 1.0D : 0.1D)) {
            pLivingEntity.hurt(pLivingEntity.level().damageSources().generic(),  (pAmplifier > 1 ? 5.0F : 0.2F));
        }
    }

    public boolean isDurationEffectTick(int i, int j) {
        return true;
    }
}
