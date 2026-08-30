package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigCriticalUtil;
import com.pla.annoyingvillagers.rig.RigDamageContext;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class RigStunCombatEvent {
    private static final float PLAYER_CRITICAL_STUN_CHANCE = 0.30F;

    private RigStunCombatEvent() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCriticalHit(CriticalHitEvent event) {
        if (event.getEntity().level().isClientSide || !event.isVanillaCritical() || event.getResult() == Event.Result.DENY || !(event.getTarget() instanceof LivingEntity target)) return;
        RigCriticalUtil.markVanillaPlayerCritical(event.getEntity(), target);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.isCanceled()) return;

        DamageSource source = event.getSource();
        LivingEntity victim = event.getEntity();
        if (victim instanceof Mob mobVictim && RigAnimationController.isInvulnerable(mobVictim)) {
            event.setCanceled(true);
            if (victim.level() instanceof ServerLevel serverLevel && (source.getEntity() != null || source.getDirectEntity() != null)) CommonUtil.damageBlocked(source, victim, serverLevel);
            return;
        }
        if (RigStunController.isKnockdown(victim) && blocksAttackDuringKnockdown(source)) {
            event.setCanceled(true);
            return;
        }

        Entity attacker = source.getEntity();
        if (attacker instanceof Mob mobAttacker && source.getDirectEntity() == attacker && RigStunController.isStunned(mobAttacker)) event.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHurt(LivingHurtEvent event) {
        float multiplier = RigDamageContext.finalIncomingMultiplier(event.getEntity(), event.getSource());
        if (multiplier != 1.0F) event.setAmount(event.getAmount() * multiplier);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity victim = event.getEntity();
        DamageSource source = event.getSource();
        if (event.getAmount() <= 0.0F || !(victim instanceof Mob mobVictim) || !RigStunController.supports(mobVictim)) return;

        if (RigStunController.isStunned(mobVictim)) {
            RigStunController.applyStun(mobVictim);
            return;
        }

        if (RigDamageContext.isCritical(victim, source)) {
            RigStunController.applyStun(mobVictim);
            return;
        }

        if (RigCriticalUtil.isVanillaPlayerCritical(victim, source) && mobVictim.getRandom().nextFloat() < PLAYER_CRITICAL_STUN_CHANCE) RigStunController.applyStun(mobVictim);
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof Mob mob) RigStunController.restoreStaleState(mob);
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        RigStunController.clearAll();
        RigCriticalUtil.clearAll();
    }

    private static boolean blocksAttackDuringKnockdown(DamageSource source) {
        return source.getEntity() != null && !source.is(DamageTypeTags.IS_EXPLOSION) && !source.is(DamageTypes.MAGIC) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }
}
