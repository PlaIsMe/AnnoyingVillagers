package com.pla.annoyingvillagers.procedures;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MobTargetHealingHerobrineProcedure {
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Mob mob) {
            if (mob.getTarget() instanceof HerobrineMob herobrineMob
                    && herobrineMob.isHealing()) {
                if (herobrineMob.getFirstPossessedHerobrine() != null
                        && herobrineMob.getFirstPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                } else if (herobrineMob.getSecondPossessedHerobrine() != null
                        && herobrineMob.getSecondPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                } else if (herobrineMob.getThirdPossessedHerobrine() != null
                        && herobrineMob.getThirdPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                } else if (herobrineMob.getFourthPossessedHerobrine() != null
                        && herobrineMob.getFourthPossessedHerobrine() instanceof LivingEntity living) {
                    mob.setTarget(living);
                }
            }

            if (mob.getTarget() instanceof LowHerobrineCloneEntity lowHerobrineCloneEntity
                    && lowHerobrineCloneEntity.isSacrificing()
                    && lowHerobrineCloneEntity.getPossessedByEntity() != null) {
                if (!lowHerobrineCloneEntity.isAlive()) {
                    mob.setTarget(lowHerobrineCloneEntity.getPossessedByEntity());
                }
            }

            if (mob.getTarget() instanceof LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity
                    && lowShadowHerobrineCloneEntity.isSacrificing()
                    && lowShadowHerobrineCloneEntity.getPossessedByEntity() != null) {
                if (!lowShadowHerobrineCloneEntity.isAlive()) {
                    mob.setTarget(lowShadowHerobrineCloneEntity.getPossessedByEntity());
                }
            }
        }
    }
}
