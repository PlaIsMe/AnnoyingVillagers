package com.pla.annoyingvillagers.potion;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class HerobrineMobEffect extends MobEffect {

    public HerobrineMobEffect() {
        super(MobEffectCategory.HARMFUL, -6710887);
    }

    public @NotNull String getDescriptionId() {
        return "effect.annoyingvillagers.herobrine";
    }

    public boolean isInstantenous() {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel effectLevel, @NotNull LivingEntity livingEntity, int i) {
        if (livingEntity instanceof Player player) {
            player.causeFoodExhaustion(0.1F);
        }

        if (Math.random() <= 0.05D) {
            float damage = Math.min(livingEntity.getHealth(), new Random().nextFloat(0.5F, 1.5F));
            if (damage == livingEntity.getHealth()) {
                com.pla.annoyingvillagers.util.LegacyEntityOps.kill(livingEntity);
            } else {
                livingEntity.hurtOrSimulate(livingEntity.level().damageSources().generic(), damage);
            }
        }

        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            Random random = new Random();

            double dx = random.nextInt(-3, 3);
            double dy = random.nextInt(-3, 3);
            double dz = random.nextInt(-3, 3);
            int count = random.nextInt(50, 200);
            serverLevel.sendParticles(
                    AnnoyingVillagersModParticleTypes.GLOWINGEYES.get(),
                    livingEntity.getX() + dx,
                    livingEntity.getY() + dy,
                    livingEntity.getZ() + dz,
                    count,
                    0.0, 0.0, 0.0,
                    0.1
            );
        }
        return true;
    }

    public boolean shouldApplyEffectTickThisTick(int i, int j) {
        return true;
    }

}
