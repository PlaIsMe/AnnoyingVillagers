package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class HookSwordEvent {
    private static final double FRONT_DOT_THRESHOLD = 0.0D;

    private HookSwordEvent() {}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.isCanceled()) return;
        if (!(event.getEntity() instanceof Mob defender) || !(defender.level() instanceof ServerLevel serverLevel)) return;

        RigAnimationId animationId = RigAnimationController.getActiveAnimationId(defender);
        if (!isHookClashAnimation(animationId) || !RigAnimationController.isInActiveAttackWindow(defender)) return;

        DamageSource damageSource = event.getSource();
        if (isIgnoredDamageSource(damageSource)) return;

        LivingEntity attacker = getLivingAttacker(damageSource, defender);
        if (attacker == null || !attacker.isAlive() || !isAttackerInFront(defender, attacker)) return;

        event.setCanceled(true);
        CommonUtil.damageBlockedForce(defender, attacker, serverLevel);
        CommonUtil.applyHookClashDisarmLogic(defender, attacker, serverLevel, getLaunchDirection(animationId));

        if (attacker instanceof Mob attackerMob && RigStunController.supports(attackerMob)) applyRigKnockdown(attackerMob, animationId);
    }

    private static boolean isHookClashAnimation(RigAnimationId animationId) {
        return animationId == RigAnimationId.HOOK_SWORD_ULT1 || animationId == RigAnimationId.HOOK_SWORD_ULT2 || animationId == RigAnimationId.FLANKER_HOOK_SWORD_ULT || animationId == RigAnimationId.HOOK_SWORD_DUAL_ULT;
    }

    private static void applyRigKnockdown(Mob attacker, RigAnimationId defenderAnimation) {
        if (defenderAnimation == RigAnimationId.HOOK_SWORD_ULT1) {
            RigStunController.applyKnockdown(attacker, RigAnimationId.KNOCKDOWN_RIGHT);
            return;
        }
        if (defenderAnimation == RigAnimationId.HOOK_SWORD_ULT2) {
            RigStunController.applyKnockdown(attacker, RigAnimationId.KNOCKDOWN_LEFT);
            return;
        }
        RigStunController.applyKnockdown(attacker);
    }

    private static HookDisarmLaunch getLaunchDirection(RigAnimationId animationId) {
        if (animationId == RigAnimationId.HOOK_SWORD_ULT1) return HookDisarmLaunch.RIGHT;
        if (animationId == RigAnimationId.HOOK_SWORD_ULT2) return HookDisarmLaunch.LEFT;
        return HookDisarmLaunch.BACKWARD;
    }

    private static LivingEntity getLivingAttacker(DamageSource damageSource, LivingEntity defender) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity livingAttacker && livingAttacker != defender) return livingAttacker;

        Entity directAttacker = damageSource.getDirectEntity();
        if (directAttacker instanceof LivingEntity livingAttacker && livingAttacker != defender) return livingAttacker;
        return null;
    }

    private static boolean isAttackerInFront(LivingEntity defender, LivingEntity attacker) {
        Vec3 defenderLook = horizontal(defender.getViewVector(1.0F));
        Vec3 toAttacker = horizontal(attacker.position().subtract(defender.position()));
        return defenderLook.lengthSqr() > 1.0E-7D && toAttacker.lengthSqr() > 1.0E-7D && defenderLook.normalize().dot(toAttacker.normalize()) > FRONT_DOT_THRESHOLD;
    }

    private static Vec3 horizontal(Vec3 vector) {
        return new Vec3(vector.x, 0.0D, vector.z);
    }

    private static boolean isIgnoredDamageSource(DamageSource damageSource) {
        return damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypeTags.IS_EXPLOSION) || damageSource.is(DamageTypes.ON_FIRE) || damageSource.is(DamageTypes.IN_FIRE) || damageSource.is(DamageTypes.FALL);
    }
}
