package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.Mob;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

/** Mirrors Epic Fight's useful distinction: a fast airborne pose while falling, then
 * LANDING only when vanilla actually applies meaningful fall damage. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class RigFallAnimationEvent {
    private static final double FALLING_Y_VELOCITY = -0.55D;
    private static final float MIN_FALL_DISTANCE = 3.0F;

    private RigFallAnimationEvent() {}

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Mob mob)
                || mob.level().isClientSide
                || !RigStunController.supports(mob)
                || !mob.isAlive()
                || mob.isRemoved()
                || mob.isDeadOrDying()) {
            return;
        }

        RigAnimationId active = RigAnimationController.getActiveAnimationId(mob);

        // LivingFallEvent is raised through the block fall/causeFallDamage path; it is
        // not a universal ground-contact callback. End FALL from the actual movement
        // state so non-damaging landing paths cannot block combat/navigation until the
        // deliberately long airborne clip times out.
        if (active == RigAnimationId.FALL && (mob.onGround() || mob.isPassenger())) {
            RigAnimationController.stop(mob, RigAnimationId.FALL);
            return;
        }

        if (mob.isPassenger()
                || mob.onGround()
                || mob.fallDistance < MIN_FALL_DISTANCE
                || mob.getDeltaMovement().y >= FALLING_Y_VELOCITY
                || RigStunController.isStunned(mob)) {
            return;
        }

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
