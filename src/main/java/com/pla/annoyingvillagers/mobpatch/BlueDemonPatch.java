package com.pla.annoyingvillagers.mobpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.pla.annoyingvillagers.combatbehaviour.*;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.EscapeUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.iml.CustomExecuteEntity;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Set;

public class BlueDemonPatch extends CEHumanoidPatch implements CustomExecuteEntity {
    public BlueDemonPatch() {
        super(Factions.NEUTRAL);
    }

    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.BLOCK, Animations.BIPED_BLOCK);
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
    }

    protected void setWeaponMotions() {
        this.weaponLivingMotions
                .put(WeaponCategories.SPEAR,
                        ImmutableMap.of(
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON),
                                        Pair.of(LivingMotions.RUN, AnimsWom.TRIDENT_TWO_HAND_RUN),
                                        Pair.of(LivingMotions.CHASE, AnimsWom.TRIDENT_TWO_HAND_RUN),
                                        Pair.of(LivingMotions.DEATH, AnimsPugilistSteve.BLUE_DEMON_STATE_TRANSFORM)
                                )
                        ));

        this.weaponAttackMotions
                .put(WeaponCategories.SPEAR,
                        ImmutableMap.of(
                                Styles.TWO_HAND, BlueDemonTrident.TRIDENT
                        ));
        
        this.guardHitMotions.put(WeaponCategories.SPEAR,
                ImmutableMap.of(
                        Styles.TWO_HAND, List.of(
                                Animations.SWORD_DUAL_GUARD_HIT
                        )
                )
        );

        this.weaponLivingMotions
                .put(WeaponCategories.GREATSWORD,
                        ImmutableMap.of(
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsPugilistSteve.LEGENDARY_SWORD_GUARD),
                                        Pair.of(LivingMotions.IDLE, AnimsSculkSteve.LEGENDARY_SWORD_IDLE),
                                        Pair.of(LivingMotions.WALK, AnimsWom.TORMENT_BERSERK_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsPugilistSteve.RUN_DUAL_BIG),
                                        Pair.of(LivingMotions.CHASE, AnimsPugilistSteve.RUN_DUAL_BIG),
                                        Pair.of(LivingMotions.DEATH, AnimsEpicFight.BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.GREATSWORD,
                        ImmutableMap.of(
                                Styles.TWO_HAND, BlueDemonLegendarySword.LEGENDARY_SWORD
                        ));

        this.guardHitMotions.put(WeaponCategories.GREATSWORD,
                ImmutableMap.of(
                        Styles.TWO_HAND, List.of(
                                Animations.GREATSWORD_GUARD_HIT
                        )
                )
        );
    }

    public void playGuardBreakSound() {
        this.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 0.0F, 0.0F);
    }

    public void tick(LivingTickEvent livingTickEvent) {
        super.tick(livingTickEvent);
    }

    public void onDeath(LivingDeathEvent livingDeathEvent) {
        super.onDeath(livingDeathEvent);
    }

    @Override
    public void onGuardHit(DamageSource damageSource) {
        super.onGuardHit(damageSource);
        if (this.getOriginal().level() instanceof ServerLevel serverLevel) {
            EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO, this.getOriginal(), damageSource.getEntity());
        }
        EpicfightUtil.breakWeaponOnParryOpAttack(damageSource);
    }

    @Override
    public boolean isBlockableSource(DamageSource damageSource) {
        return true;
    }

    @Override
    public AttackResult tryHurt(DamageSource damageSource, float amount) {
        EscapeUtil.stepLeftRightOnHurtByDangerousAnimation(damageSource, this);
        return super.tryHurt(damageSource, amount);
    }

    @Override
    public void playGuardHitAnimation(DamageSource damageSource, boolean canCounter) {
        if (ModList.get().isLoaded("efn") && this.getOriginal() instanceof BlueDemonEntity blueDemon && blueDemon.getLivingEntityPatch() != null) {
            EpicFightNightFall.playEfnGuardHit(blueDemon.getLivingEntityPatch(), blueDemon.getEfnGuardHitState(), damageSource);
            blueDemon.postPlayEfnGuardHit();
        } else {
            super.playGuardHitAnimation(damageSource, canCounter);
        }
    }

    public AnimationAccessor<? extends StaticAnimation> getHitAnimation(StunType stuntype) {
        return switch (stuntype) {
            case LONG -> Animations.BIPED_HIT_LONG;
            case SHORT, HOLD -> Animations.BIPED_HIT_SHORT;
            case KNOCKDOWN -> Animations.BIPED_KNOCKDOWN;
            case NEUTRALIZE -> (
                    this.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == WeaponCategories.GREATSWORD ?
                            Animations.GREATSWORD_GUARD_BREAK : Animations.BIPED_COMMON_NEUTRALIZED);
            case FALL -> Animations.BIPED_LANDING;
            default -> null;
        };
    }

    @Override
    public boolean canBeExecuted(LivingEntityPatch<?> livingEntityPatch) {
        return AnnoyingVillagersConfig.CAN_EXECUTE_AV_MOB.get();
    }

    @Override
    public boolean canUseCustomType(LivingEntityPatch<?> livingEntityPatch, ExecutionTypeManager.Type type) {
        return true;
    }

    @Override
    public ExecutionTypeManager.Type getExecutionType(LivingEntityPatch<?> livingEntityPatch, ExecutionTypeManager.Type type) {
        return type;
    }
}
