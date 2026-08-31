package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Mirrors Epic Fight's useful distinction: a fast airborne pose while falling, then
 * LANDING only when vanilla actually applies meaningful fall damage. */
@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class RigFallAnimationEvent {
    private static final double FALLING_Y_VELOCITY = -0.55D;
    private static final float MIN_FALL_DISTANCE = 3.0F;

    private RigFallAnimationEvent() {}

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob mob)
                || mob.level().isClientSide
                || !RigStunController.supports(mob)
                || !mob.isAlive()
                || mob.isRemoved()
                || mob.isDeadOrDying()
                || mob.isPassenger()
                || mob.onGround()
                || mob.fallDistance < MIN_FALL_DISTANCE
                || mob.getDeltaMovement().y >= FALLING_Y_VELOCITY
                || RigStunController.isStunned(mob)) {
            return;
        }

        RigAnimationId active = RigAnimationController.getActiveAnimationId(mob);
        if (active == RigAnimationId.FALL) return;
        // The rig controller has one active animation layer. Do not cancel an authored
        // attack/ultimate merely because the mob briefly becomes airborne. Start FALL
        // once that authored animation has finished and the mob is still dropping.
        if (active != null) return;

        RigAnimationController.play(mob, RigAnimationId.FALL);
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Mob mob) || mob.level().isClientSide) return;
        RigAnimationController.stop(mob, RigAnimationId.FALL);
    }
}
