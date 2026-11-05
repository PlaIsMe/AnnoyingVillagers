package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class HerobrineDragonDismountEvent {

    @SubscribeEvent
    public static void onMount(EntityMountEvent event) {
        if (!event.isDismounting()) return;
        if (!(event.getEntityBeingMounted() instanceof HerobrineDragonEntity dragon)) return;
        if (dragon.level().isClientSide()) return;

        if (!dragon.onGround() && !dragon.isNearGround() && event.getEntityMounting() instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 2));
        }
    }
}
