package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.compat.EpicFightResurrection;
import com.pla.annoyingvillagers.compat.EpicFightSwordSoaring;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class EscapeUtil {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                AnimsWom.ENDER_AEGIS_BULL_CHARGE.get().getRegistryName().toString(),
                AnimsWom.YELLOW_TORMENT_CHARGED_ATTACK_3.get().getRegistryName().toString(),
                AnimsWom.ENDER_GLAIVE_NAPOLEON_SHOOT_3.get().getRegistryName().toString(),
                AnimsWom.ENDER_GLAIVE_AGONY_AUTO_1.get().getRegistryName().toString(),
                AnimsEpicFight.AEGIS_SHIELD_SHOOT.get().getRegistryName().toString(),
                AnimsWom.CLONE_NAPOLEON_WATERLOW_SHOOT.get().getRegistryName().toString(),
                AVAnimations.TRIDENT_ATTACK.get().getRegistryName().toString(),
                AnimsPugilistSteve.BLUE_DEMON_STATE_TRANSFORM.get().getRegistryName().toString(),
                AnimsWom.ELECTRIC_FIELD.get().getRegistryName().toString(),
                AVAnimations.SNAKE_BLADE_GUARD.get().getRegistryName().toString(),
                AnimsAgony.AGONY_SKY_DIVE_X.get().getRegistryName().toString(),
                AnimsAgony.AGONY_SKY_DIVE.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_2.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_3.get().getRegistryName().toString(),
                AnimsRuine.RUINE_PLUNDER.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_LAPSE.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENSION.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENDED_BLACKHOLE.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_BERSERK_CONVERT.get().getRegistryName().toString(),
                AnimsSatsujin.SATSUJIN_GESSHOKU.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_AUTO_3.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_SPRENGKOPF.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_WIDERSTAND.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECHO.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECLIPSE.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_FULLMOON.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_OBSCURIDAD.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_CREMATORIO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_INFIERNO.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_AUSTERLITZ_SHOOT.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_WATERLOW_SHOOT.get().getRegistryName().toString(),
                AnimsOrbit.ORBIT_LIGHT_BEAM.get().getRegistryName().toString()
        ));

        if (ModList.get().isLoaded("efn")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightNightFall.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        if (ModList.get().isLoaded("sword_soaring")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightSwordSoaring.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        if (ModList.get().isLoaded("cdmoveset")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightResurrection.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }

    public static boolean isAnimationDangerous(AssetAccessor<? extends StaticAnimation> targetDynamicAnimation) {
        if (targetDynamicAnimation != null && targetDynamicAnimation.get().getRegistryName() != null) {
            String animation = targetDynamicAnimation.get().getRegistryName().toString();
            return DANGEROUS_ANIMATIONS.contains(animation);
        }
        return false;
    }

    public static boolean checkEscape(Mob mob) {
        LivingEntity target = mob.getTarget();
        LivingEntityPatch<?> targetLivingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (target == null || targetLivingEntityPatch == null) return false;
        AssetAccessor<? extends StaticAnimation> targetDynamicAnimation = Objects.requireNonNull(targetLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        return isAnimationDangerous(targetDynamicAnimation) || targetDynamicAnimation.get() instanceof ExecutionAttackAnimation;
    }

    public static void stepLeftRightOnHurtByDangerousAnimation(DamageSource damageSource, MobPatch<?> mobPatch) {
        Entity target = damageSource.getEntity();
        if (!(target instanceof LivingEntity livingEntity)) return;
        LivingEntityPatch<?> targetEntityPatch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
        if (targetEntityPatch != null) {
            AssetAccessor<? extends StaticAnimation> targetDynamicAnimation = Objects.requireNonNull(targetEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(mobPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (isAnimationDangerous(targetDynamicAnimation)
                    && !EpicfightUtil.isLongHitAnimation(dynamicAnimation, mobPatch)) {
                if (mobPatch.getOriginal() instanceof HerobrineMob herobrineMob
                        && herobrineMob.getStunEscapeCooldown() == 0) {
                    herobrineMob.setStunEscapeCooldown(60);
                    if (new Random().nextBoolean()) {
                        mobPatch.playAnimationSynchronized(WOMAnimations.ENDERSTEP_LEFT, 0.0F);
                    } else {
                        mobPatch.playAnimationSynchronized(WOMAnimations.ENDERSTEP_RIGHT, 0.0F);
                    }
                }

                if (mobPatch.getOriginal() instanceof AngrySteveEntity angrySteveEntity
                        && angrySteveEntity.getStunEscapeCooldown() == 0) {
                    angrySteveEntity.setStunEscapeCooldown(60);
                    if (new Random().nextBoolean()) {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_LEFT, 0.0F);
                    } else {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_RIGHT, 0.0F);
                    }
                }

                if (mobPatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity
                        && blueDemonEntity.getStunEscapeCooldown() == 0) {
                    blueDemonEntity.setStunEscapeCooldown(60);
                    if (new Random().nextBoolean()) {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_LEFT, 0.0F);
                    } else {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_RIGHT, 0.0F);
                    }
                }
            }
        }
    }
}
