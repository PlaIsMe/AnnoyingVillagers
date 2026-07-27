package com.pla.annoyingvillagers.mobpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.pla.annoyingvillagers.combatbehaviour.HerobrineNullWeapon;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.EscapeUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.iml.CustomExecuteEntity;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.AttackResult.ResultType;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Set;

public class NullPatch extends CEHumanoidPatch<PathfinderMob> implements CustomExecuteEntity {
    public NullPatch() {
        super(Factions.UNDEAD);
    }

    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.BLOCK, AnimsPugilistSteve.FIST_GUARD);
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_CREATIVE_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_CREATIVE_IDLE);
        animator.addLivingAnimation(LivingMotions.RUN, Animations.BIPED_CREATIVE_IDLE);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_CREATIVE_IDLE);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
    }

    protected void setWeaponMotions() {
        this.weaponLivingMotions
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsPugilistSteve.FIST_GUARD),
                                        Pair.of(LivingMotions.IDLE, AnimsWom.CLONE_ANTITHEUS_ASCENDED_IDLE),
                                        Pair.of(LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_WALK),
                                        Pair.of(LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                        Pair.of(LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.TWO_HAND, HerobrineNullWeapon.NULL_WEAPON
                        ));
        
        this.guardHitMotions.put(WeaponCategories.SWORD,
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

    public AttackResult attack(EpicFightDamageSource epicFightDamageSource, Entity entity, InteractionHand interactionhand) {
        AttackResult attackresult = super.attack(epicFightDamageSource, entity, interactionhand);

        if (attackresult.resultType == ResultType.SUCCESS && entity.isAlive()) {
            // More logic when mob attack success
        }

        return attackresult;
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
