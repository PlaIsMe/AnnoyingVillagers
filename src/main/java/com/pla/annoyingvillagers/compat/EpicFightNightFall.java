package com.pla.annoyingvillagers.compat;

import com.hm.efn.animations.types.stun.EFNStunAnimation;
import com.hm.efn.client.sound.EFNSounds;
import com.hm.efn.entity.geoEntity.*;
import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.gameasset.animations.*;
import com.hm.efn.item.custom.*;
import com.hm.efn.item.geo.ExcaliburItem;
import com.hm.efn.particle.EFNParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import org.joml.Vector3d;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.HashSet;
import java.util.Set;

public class EpicFightNightFall {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();
    public static final int MULTIPLIER_DAMAGE_VALUE = 5;

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARGING.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARG1MAX_FIRST.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARG1MAX_SECOND.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARGING_MOB.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_AIRSLASH.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_DASH.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGING.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGING_MOB.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE1.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE2.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE3.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_FINISHER.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT_ALL.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT_CHARGE.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT_JUST.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_VOLCANOL_ALL.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_VOLCANOL.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_VOLCANOL_CHARGE.get().getRegistryName().toString(),
                EFNAnimations.DMC5_V_JC.get().getRegistryName().toString(),
                EFNSkillAnimations.EXECUTION.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_ZANDATSU.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_ZANDATSU_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_DASH_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_DASH_Y_SP.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_KICK_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE_THROUGH.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XY_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXY_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXX.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXXY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXXY_CHARGE.get().getRegistryName().toString(),
                EFNSekiroAnimations.DRAGON_FLASH.get().getRegistryName().toString(),
                EFNSekiroAnimations.MORTAL_BLADE_1.get().getRegistryName().toString(),
                EFNSekiroAnimations.MORTAL_BLADE_2.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_START.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_LOOP.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_START_N.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_LOOP_N.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_HARVEST.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_AIR_SLASH.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_SCARLET_END.get().getRegistryName().toString()
        ));
    }

    public static Set<String> getDangerousAnimations() {
        return DANGEROUS_ANIMATIONS;
    }

    public static boolean isEFNStun(AssetAccessor<? extends StaticAnimation> assetAccessor) {
        return assetAccessor.get() instanceof EFNStunAnimation;
    }

    private static Vector3d getParticleArgumentsForAnimation(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation) {
        if (animation.get() == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT1) {
            return new Vector3d(1.0F, -0.6, 0.0F);
        } else if (animation.get() == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT2) {
            return new Vector3d(1.0F, 0.6, 0.0F);
        } else {
            return animation.get() == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT3 ? new Vector3d(1.2, 0.7, 0.0F) : new Vector3d(1.0F, 0.0F, 0.0F);
        }
    }

    private static Vector3d getParticlePositionForAnimation(Entity owner, Entity target, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation) {
        Vec3 lookVec = owner.getLookAngle();
        Vec3 playerPos = owner.position().add(0.0F, (double)owner.getBbHeight() * 0.6, 0.0F);
        Vec3 targetPos = target.position().add(0.0F, (double)target.getBbHeight() * 0.6, 0.0F);
        Vec3 middlePos = playerPos.add(targetPos.subtract(playerPos).scale(0.5F));
        Vec3 toMiddle = middlePos.subtract(playerPos);
        double distanceToMiddle = toMiddle.length();
        double maxDistance = 1.0F;
        Vec3 limitedMiddlePos;
        if (distanceToMiddle > maxDistance) {
            Vec3 direction = toMiddle.normalize();
            limitedMiddlePos = playerPos.add(direction.scale(maxDistance));
        } else {
            limitedMiddlePos = middlePos;
        }

        Vector3d basePosition = new Vector3d(limitedMiddlePos.x, limitedMiddlePos.y, limitedMiddlePos.z);
        if (animation.get() == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT1) {
            Vec3 leftOffset = lookVec.yRot((float)Math.toRadians(-90.0F)).scale(0.2);
            Vector3d finalPos = new Vector3d(basePosition.x + leftOffset.x, basePosition.y, basePosition.z + leftOffset.z);
            return limitDistanceFromOwner(playerPos, finalPos, maxDistance);
        } else if (animation.get() == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT2) {
            Vec3 rightOffset = lookVec.yRot((float)Math.toRadians(90.0F)).scale(0.2);
            Vector3d finalPos = new Vector3d(basePosition.x + rightOffset.x, basePosition.y, basePosition.z + rightOffset.z);
            return limitDistanceFromOwner(playerPos, finalPos, maxDistance);
        } else {
            return basePosition;
        }
    }

    private static Vector3d limitDistanceFromOwner(Vec3 playerPos, Vector3d particlePos, double maxDistance) {
        Vec3 toParticle = (new Vec3(particlePos.x, particlePos.y, particlePos.z)).subtract(playerPos);
        double distance = toParticle.length();
        if (distance > maxDistance) {
            Vec3 direction = toParticle.normalize();
            Vec3 limitedPos = playerPos.add(direction.scale(maxDistance));
            return new Vector3d(limitedPos.x, limitedPos.y, limitedPos.z);
        } else {
            return particlePos;
        }
    }

    private static void spawnParryFlashParticle(Entity owner, Entity target, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, ServerLevel serverLevel) {
        Vector3d particleArgs = getParticleArgumentsForAnimation(animation);
        EFNParticles.EFN_PARRY_FLASH_MAIN.get().spawnParticleWithArgument(serverLevel, (player, entity) -> getParticlePositionForAnimation(player, entity, animation), (player, entity) -> particleArgs, owner, target);
        EFNParticles.ALL_SPARK.get().spawnParticleWithArgument(serverLevel, (player, entity) -> getParticlePositionForAnimation(player, entity, animation), HitParticleType.ZERO, owner, target);
    }

    public static void playEfnGuardHit(LivingEntityPatch<?> livingEntityPatch, int index, DamageSource damageSource) {
        if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation;
            if (index == 0) {
                animation = EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT1;
            } else if (index == 1) {
                animation = EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT2;
            } else {
                animation = EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT3;
            }
            livingEntityPatch.playAnimationSynchronized(animation, 0.0F);
            spawnParryFlashParticle(livingEntityPatch.getOriginal(), damageSource.getDirectEntity(), animation, serverLevel);
            livingEntityPatch.playSound(EFNSounds.PARRY.get(), 0.5F, 0.0F, 0.0F);
        }
    }

    public static boolean isPlayingEfnGuardHit(CEHumanoidPatch<?> ceHumanoidPatch) {
        AnimationPlayer animationPlayer = ceHumanoidPatch.getAnimator().getPlayerFor(null);
        if (animationPlayer != null) {
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
            return dynamicAnimation == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT1 || dynamicAnimation == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT2 || dynamicAnimation == EFNSkillAnimations.EFN_GUARD_ACTIVE_HIT3;
        }
        return false;
    }

    public static boolean isEfnWeapons(ItemStack itemStack) {
        return itemStack.getItem() instanceof RuinsgreatswordItem
                || itemStack.getItem() instanceof ThornWheelItem
                || itemStack.getItem() instanceof AetherialDuskDualSword_MainHandItem
                || itemStack.getItem() instanceof Meen_SpearItem
                || itemStack.getItem() instanceof PioneerItem
                || itemStack.getItem() instanceof NFShortSwordItem
                || itemStack.getItem() instanceof NFShortSwordTwoItem
                || itemStack.getItem() instanceof ExsiliumgladiusItem
                || itemStack.getItem() instanceof FireExsiliumgladiusItem
                || itemStack.getItem() instanceof AirTachiItem
                || itemStack.getItem() instanceof CoTachiItem
                || itemStack.getItem() instanceof KusabimaruItem
                || itemStack.getItem() instanceof BroadBladeItem
                || itemStack.getItem() instanceof ScytheItem
                || itemStack.getItem() instanceof NfClawItem
                || itemStack.getItem() instanceof YamatoItem
                || itemStack.getItem() instanceof HfMurasamaItem
                || itemStack.getItem() instanceof CrescentMoonItem
                || itemStack.getItem() instanceof ExcaliburItem;
    }
}
