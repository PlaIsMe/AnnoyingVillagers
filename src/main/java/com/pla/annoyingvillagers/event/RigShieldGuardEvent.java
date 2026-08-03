package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.RigShieldGuardController;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class RigShieldGuardEvent {
    private static final double FRONT_DOT_THRESHOLD = 0.0D;

    private RigShieldGuardEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getEntity() instanceof Mob defender) || !(defender.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        DamageSource damageSource = event.getSource();
        Entity sourceEntity = blockingSourceEntity(damageSource);
        if (sourceEntity == null
                || sourceEntity == defender
                || !RigShieldGuardController.canBlock(defender)
                || !canShieldBlock(damageSource)
                || !isSourceInFront(defender, sourceEntity)) {
            return;
        }

        event.setCanceled(true);
        CommonUtil.damageBlockedForce(defender, sourceEntity, serverLevel);
        applyBlockedByShieldKnockback(defender, damageSource);
        RigShieldGuardController.handleBlockedHit(defender, event.getAmount(), sourceEntity);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        RigShieldGuardController.clear();
    }

    private static Entity blockingSourceEntity(DamageSource damageSource) {
        Entity directEntity = damageSource.getDirectEntity();
        return directEntity == null ? damageSource.getEntity() : directEntity;
    }

    private static boolean canShieldBlock(DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD)) {
            return false;
        }

        Entity directEntity = damageSource.getDirectEntity();
        return !(directEntity instanceof AbstractArrow arrow) || arrow.getPierceLevel() <= 0;
    }

    private static boolean isSourceInFront(Mob defender, Entity sourceEntity) {
        Vec3 defenderLook = horizontal(defender.getViewVector(1.0F));
        Vec3 toSource = horizontal(sourceEntity.position().subtract(defender.position()));

        return defenderLook.lengthSqr() > 1.0E-7D
                && toSource.lengthSqr() > 1.0E-7D
                && defenderLook.normalize().dot(toSource.normalize()) > FRONT_DOT_THRESHOLD;
    }

    private static Vec3 horizontal(Vec3 vector) {
        return new Vec3(vector.x, 0.0D, vector.z);
    }

    private static void applyBlockedByShieldKnockback(Mob defender, DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        Entity directEntity = damageSource.getDirectEntity();
        if (directEntity != null && directEntity != attacker) {
            return;
        }
        if (attacker instanceof LivingEntity livingAttacker) {
            livingAttacker.knockback(0.5D, defender.getX() - livingAttacker.getX(), defender.getZ() - livingAttacker.getZ());
        }
    }
}
