package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HookSwordEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!ModList.get().isLoaded("efclash_blade")) {
            if (event.isCanceled() || event.getAmount() <= 0.0F) {
                return;
            }

            LivingEntity defender = event.getEntity();
            if (!(defender.level() instanceof ServerLevel serverLevel)) {
                return;
            }

            if (!CommonUtil.isHookSword(defender.getMainHandItem())) {
                return;
            }

            DamageSource damageSource = event.getSource();
            if (isIgnoredDamageSource(damageSource)) {
                return;
            }

            LivingEntity attacker = getLivingAttacker(damageSource, defender);
            if (attacker == null || !attacker.isAlive() || !isAttackerInFront(defender, attacker)) {
                return;
            }

            LivingEntityPatch<?> defenderPatch =
                    EpicFightCapabilities.getEntityPatch(defender, LivingEntityPatch.class);
            if (defenderPatch == null) {
                return;
            }

            AnimationPlayer animationPlayer = defenderPatch.getAnimator().getPlayerFor(null);
            if (animationPlayer == null) {
                return;
            }

            AssetAccessor<? extends StaticAnimation> defenderAnimation = animationPlayer.getRealAnimation();
            if (!isValidHookClash(defenderPatch, animationPlayer, defenderAnimation)) {
                return;
            }

            event.setAmount(0.0F);
            event.setCanceled(true);
            EpicfightUtil.damageBlockedForce(defender, attacker, serverLevel);
            CommonUtil.applyHookClashDisarmLogic(
                    defender,
                    attacker,
                    serverLevel,
                    getKnockdownAnimation(defenderAnimation),
                    getLaunchDirection(defenderAnimation)
            );
        }
    }

    private static boolean isValidHookClash(
            LivingEntityPatch<?> defenderPatch,
            AnimationPlayer animationPlayer,
            AssetAccessor<? extends StaticAnimation> defenderAnimation
    ) {
        if (!CommonUtil.isHookSwordClashAnimation(defenderAnimation)) {
            return false;
        }

        if (!(defenderAnimation.get() instanceof AttackAnimation)) {
            return false;
        }

        EntityState entityState = defenderAnimation.get().getState(defenderPatch, animationPlayer.getElapsedTime());
        return entityState.getLevel() < 3;
    }

    private static LivingEntity getLivingAttacker(DamageSource damageSource, LivingEntity defender) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity livingAttacker && livingAttacker != defender) {
            return livingAttacker;
        }

        Entity directAttacker = damageSource.getDirectEntity();
        if (directAttacker instanceof LivingEntity livingAttacker && livingAttacker != defender) {
            return livingAttacker;
        }

        return null;
    }

    private static boolean isAttackerInFront(LivingEntity defender, LivingEntity attacker) {
        Vec3 toAttacker = attacker.position().subtract(defender.getEyePosition());
        if (toAttacker.lengthSqr() < 1.0E-7D) {
            return false;
        }

        return toAttacker.normalize().dot(defender.getViewVector(1.0F)) > 0.0D;
    }

    private static boolean isIgnoredDamageSource(DamageSource damageSource) {
        return damageSource.is(DamageTypes.MAGIC)
                || damageSource.is(DamageTypeTags.IS_EXPLOSION)
                || damageSource.is(DamageTypes.ON_FIRE)
                || damageSource.is(DamageTypes.IN_FIRE)
                || damageSource.is(DamageTypes.FALL);
    }

    private static AssetAccessor<? extends StaticAnimation> getKnockdownAnimation(
            AssetAccessor<? extends StaticAnimation> defenderAnimation
    ) {
        if (defenderAnimation == AnimsAVSword.HOOK_SWORD_INNATE1) {
            return AVAnimations.KNOCKDOWN_RIGHT;
        }

        if (defenderAnimation == AnimsAVSword.HOOK_SWORD_INNATE2) {
            return AVAnimations.KNOCKDOWN_LEFT;
        }

        return AVAnimations.STUN_BACK;
    }

    private static HookDisarmLaunch getLaunchDirection(
            AssetAccessor<? extends StaticAnimation> defenderAnimation
    ) {
        if (defenderAnimation == AnimsAVSword.HOOK_SWORD_INNATE1) {
            return HookDisarmLaunch.RIGHT;
        }

        if (defenderAnimation == AnimsAVSword.HOOK_SWORD_INNATE2) {
            return HookDisarmLaunch.LEFT;
        }

        return HookDisarmLaunch.BACKWARD;
    }
}
