package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Random;

@EventBusSubscriber
public class MonsterHealingEvent {
    @SubscribeEvent
    public static void onMonsterAttacked(LivingHurtEvent livingHurtEvent) {
        if (livingHurtEvent != null && livingHurtEvent.getEntity() != null) {
            LivingEntity entity = livingHurtEvent.getEntity();
            if (!entity.level().isClientSide() && AnnoyingVillagersConfig.VANILLA_MOB_CAN_DRINK_HEALING_POTION.get()) {
                if (entity instanceof Zombie || entity instanceof AbstractSkeleton) {
                    if (entity.getHealth() <= 10 && entity.getPersistentData().getInt("AvHealingCooldown") == 0 && entity.isAlive()) {
                        CombatBehaviour.drinkingHealingPotion(entity, entity.level(), true, livingHurtEvent.getAmount());
                        entity.getPersistentData().putInt("AvHealingCooldown", new Random().nextInt(60, 200));
                    };
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMonsterTick(LivingEvent.LivingTickEvent livingTickEvent) {
        if (livingTickEvent != null && livingTickEvent.getEntity() != null) {
            LivingEntity entity = livingTickEvent.getEntity();
            if (!entity.level().isClientSide() && AnnoyingVillagersConfig.VANILLA_MOB_CAN_DRINK_HEALING_POTION.get()) {
                if (entity instanceof Zombie || entity instanceof AbstractSkeleton) {
                    if (!entity.getPersistentData().contains("AvHealingCooldown")) {
                        entity.getPersistentData().putInt("AvHealingCooldown", 0);
                    } else {
                        int healingCooldown = entity.getPersistentData().getInt("AvHealingCooldown");
                        if (healingCooldown > 0) {
                            entity.getPersistentData().putInt("AvHealingCooldown", healingCooldown - 1);
                        }
                    }
                }
            }
        }
    }
}

