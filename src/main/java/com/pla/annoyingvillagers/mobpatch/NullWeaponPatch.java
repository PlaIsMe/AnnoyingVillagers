package com.pla.annoyingvillagers.mobpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.pla.annoyingvillagers.combatbehaviour.NullWeaponSword;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.util.EpicfightUtil;
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

public class NullWeaponPatch extends CEHumanoidPatch<PathfinderMob> implements CustomExecuteEntity {
    public NullWeaponPatch() {
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
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsWom.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.CHASE, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.SWORD,
                        ImmutableMap.of(
                                Styles.ONE_HAND, NullWeaponSword.SWORD
                        ));
        
        this.guardHitMotions.put(WeaponCategories.SWORD,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        )
                )
        );

        this.weaponLivingMotions
                .put(WeaponCategories.AXE,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsWom.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.CHASE, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.AXE,
                        ImmutableMap.of(
                                Styles.ONE_HAND, NullWeaponSword.SWORD
                        ));

        this.guardHitMotions.put(WeaponCategories.AXE,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        )
                )
        );

        this.weaponLivingMotions
                .put(WeaponCategories.PICKAXE,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsWom.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.CHASE, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.PICKAXE,
                        ImmutableMap.of(
                                Styles.ONE_HAND, NullWeaponSword.SWORD
                        ));

        this.guardHitMotions.put(WeaponCategories.PICKAXE,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        )
                )
        );

        this.weaponLivingMotions
                .put(WeaponCategories.SHOVEL,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsWom.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.CHASE, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.SHOVEL,
                        ImmutableMap.of(
                                Styles.ONE_HAND, NullWeaponSword.SWORD
                        ));

        this.guardHitMotions.put(WeaponCategories.SHOVEL,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        )
                )
        );

        this.weaponLivingMotions
                .put(WeaponCategories.HOE,
                        ImmutableMap.of(
                                Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsWom.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.CHASE, AnimsPugilistSteve.BIPED_RUN_ESWORD),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )
                        ));
        this.weaponAttackMotions
                .put(WeaponCategories.HOE,
                        ImmutableMap.of(
                                Styles.ONE_HAND, NullWeaponSword.SWORD
                        ));

        this.guardHitMotions.put(WeaponCategories.HOE,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
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

    @Override
    public boolean isBlockableSource(DamageSource damageSource) {
        return true;
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
