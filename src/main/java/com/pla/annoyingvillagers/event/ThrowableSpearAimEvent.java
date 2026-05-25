package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.ThrowableSpearItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.forgeevent.UpdatePlayerMotionEvent;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ThrowableSpearAimEvent {
    private ThrowableSpearAimEvent() {
    }

    @SubscribeEvent
    public static void onCompositeMotion(UpdatePlayerMotionEvent.CompositeLayer event) {
        LivingEntity livingEntity = event.getPlayerPatch().getOriginal();

        if (livingEntity.isUsingItem() && livingEntity.getUseItem().getItem() instanceof ThrowableSpearItem) {
            event.setMotion(LivingMotions.AIM);
        }
    }
}
