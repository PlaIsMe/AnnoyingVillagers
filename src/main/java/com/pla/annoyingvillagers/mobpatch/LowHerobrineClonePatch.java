package com.pla.annoyingvillagers.mobpatch;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.pla.annoyingvillagers.combatbehaviour.*;
import com.pla.annoyingvillagers.compat.dualaxes.AvNpcDualAxe;
import com.pla.annoyingvillagers.compat.dualgreatsword.AvNpcDualGreatsword;
import com.pla.annoyingvillagers.compat.epicfightx.*;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.MobPatchCommon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.iml.CustomExecuteEntity;
import net.shelmarow.combat_evolution.execution.ExecutionTypeManager;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.world.capabilities.item.WOMWeaponCategories;
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
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Set;

public class LowHerobrineClonePatch extends CEHumanoidPatch<PathfinderMob> implements CustomExecuteEntity {
    public LowHerobrineClonePatch() {
        super(Factions.UNDEAD);
    }

    public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.BLOCK, Animations.BIPED_BLOCK);
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.ZOMBIE_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.ZOMBIE_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, Animations.ZOMBIE_CHASE);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.ZOMBIE_CHASE);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
    }

    protected void setWeaponMotions() {
        this.weaponLivingMotions
                .put(WeaponCategories.NOT_WEAPON,
                        ImmutableMap.of(Styles.COMMON,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WeaponCategories.NOT_WEAPON,
                        ImmutableMap.of(Styles.COMMON, AvNpcFist.FIST));

        this.weaponLivingMotions
                .put(WeaponCategories.FIST,
                        ImmutableMap.of(Styles.COMMON,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        if (!ModList.get().isLoaded("epicfightx")) {
            this.weaponAttackMotions
                    .put(WeaponCategories.FIST,
                            ImmutableMap.of(Styles.COMMON, AvNpcFist.FIST));
            this.weaponAttackMotions
                    .put(WeaponCategories.SWORD,
                            ImmutableMap.of(
                                    Styles.ONE_HAND, AvNpcSword.SWORD,
                                    Styles.TWO_HAND, AvNpcSword.DUAL_SWORD
                            ));

            this.weaponAttackMotions
                    .put(WeaponCategories.DAGGER,
                            ImmutableMap.of(
                                    Styles.ONE_HAND, AvNpcDagger.DAGGER,
                                    Styles.TWO_HAND, AvNpcDagger.DUAL_DAGGER
                            ));

            this.weaponAttackMotions
                    .put(WeaponCategories.UCHIGATANA,
                            ImmutableMap.of(
                                    Styles.TWO_HAND, AvNpcUchigatana.UCHIGATANA
                            ));
            this.weaponAttackMotions
                    .put(WeaponCategories.SPEAR,
                            ImmutableMap.of(
                                    Styles.ONE_HAND, AvNpcSpear.SPEAR_SHIELD,
                                    Styles.TWO_HAND, AvNpcSpear.SPEAR
                            ));

            this.weaponAttackMotions
                    .put(WeaponCategories.LONGSWORD,
                            ImmutableMap.of(
                                    Styles.ONE_HAND, AvNpcLongsword.LONGSWORD_SHIELD,
                                    Styles.TWO_HAND, AvNpcLongsword.LONGSWORD
                            ));

            this.weaponAttackMotions
                    .put(WeaponCategories.TACHI,
                            ImmutableMap.of(
                                    Styles.TWO_HAND, AvNpcTachi.TACHI
                            ));
        } else {
            this.weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.FIST,
                            ImmutableMap.of(CapabilityItem.Styles.COMMON, AvNpcXFist.X_FIST));
            this.weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.SWORD,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.ONE_HAND, AvNpcXSword.X_SWORD,
                                    CapabilityItem.Styles.TWO_HAND, AvNpcXSword.X_DUAL_SWORD
                            ));

            this.weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.DAGGER,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.ONE_HAND, AvNpcXDagger.X_DAGGER,
                                    CapabilityItem.Styles.TWO_HAND, AvNpcXDagger.X_DUAL_DAGGER
                            ));

            this.weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.UCHIGATANA,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.TWO_HAND, AvNpcXUchigatana.X_UCHIGATANA
                            ));
            this.weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.SPEAR,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.ONE_HAND, AvNpcXSpear.X_SPEAR_SHIELD,
                                    CapabilityItem.Styles.TWO_HAND, AvNpcXSpear.X_SPEAR
                            ));

            this.weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.LONGSWORD,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.ONE_HAND, AvNpcXLongsword.X_LONGSWORD_SHIELD,
                                    CapabilityItem.Styles.TWO_HAND, AvNpcXLongsword.X_LONGSWORD
                            ));

            this.weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.TACHI,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.TWO_HAND, AvNpcXTachi.X_TACHI
                            ));
        }

        if (ModList.get().isLoaded("epicfightx")) {
            weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.AXE,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.ONE_HAND, AvNpcXAxe.X_AXE)
                    );
        } else if (ModList.get().isLoaded("dualaxes")) {
            weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.AXE,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.ONE_HAND, AvNpcDualAxe.AXE,
                                    CapabilityItem.Styles.TWO_HAND, AvNpcDualAxe.DUAL_AXE)
                    );
        } else {
            this.weaponAttackMotions
                    .put(WeaponCategories.AXE,
                            ImmutableMap.of(Styles.ONE_HAND, AvNpcAxe.AXE));
        }

        if (ModList.get().isLoaded("epicfightx")) {
            weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.GREATSWORD,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.TWO_HAND, AvNpcXGreatsword.X_GREATSWORD));
        } else if (ModList.get().isLoaded("dualgreatswords")) {
            weaponAttackMotions
                    .put(CapabilityItem.WeaponCategories.GREATSWORD,
                            ImmutableMap.of(
                                    CapabilityItem.Styles.TWO_HAND, AvNpcGreatsword.GREATSWORD,
                                    CapabilityItem.Styles.OCHS, AvNpcDualGreatsword.DUAL_GREATSWORD));
        } else {
            this.weaponAttackMotions
                    .put(WeaponCategories.GREATSWORD,
                            ImmutableMap.of(Styles.TWO_HAND, AvNpcGreatsword.GREATSWORD));
        }

        this.weaponLivingMotions
                .put(WOMWeaponCategories.ENDERBLASTER,
                        ImmutableMap.of(Styles.ONE_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                ), Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.IDLE, Animations.BIPED_IDLE),
                                        Pair.of(LivingMotions.WALK, Animations.BIPED_WALK),
                                        Pair.of(LivingMotions.RUN, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.CHASE, Animations.BIPED_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));
        this.weaponAttackMotions
                .put(WOMWeaponCategories.ENDERBLASTER,
                        ImmutableMap.of(Styles.ONE_HAND, AvNpcFist.FIST, Styles.TWO_HAND, AvNpcFist.FIST));

        this.weaponAttackMotions
                .put(WOMWeaponCategories.TORMENT,
                        ImmutableMap.of(Styles.TWO_HAND, AvNpcGreatsword.GREATSWORD));
        this.weaponLivingMotions
                .put(WOMWeaponCategories.TORMENT,
                        ImmutableMap.of(Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, Animations.BIPED_BLOCK),
                                        Pair.of(LivingMotions.IDLE, WOMAnimations.TORMENT_IDLE),
                                        Pair.of(LivingMotions.WALK, WOMAnimations.TORMENT_WALK),
                                        Pair.of(LivingMotions.RUN, WOMAnimations.TORMENT_RUN),
                                        Pair.of(LivingMotions.CHASE, WOMAnimations.TORMENT_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));

        this.weaponAttackMotions
                .put(WOMWeaponCategories.ANTITHEUS,
                        ImmutableMap.of(
                                Styles.TWO_HAND, AvNpcSpear.SPEAR
                        ));
        this.weaponLivingMotions
                .put(WOMWeaponCategories.ANTITHEUS,
                        ImmutableMap.of(Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsWom.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, AnimsAgony.AGONY_IDLE),
                                        Pair.of(LivingMotions.WALK, AnimsAgony.AGONY_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsAgony.AGONY_RUN),
                                        Pair.of(LivingMotions.CHASE, AnimsAgony.AGONY_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));

        this.weaponAttackMotions
                .put(WOMWeaponCategories.NAPOLEON,
                        ImmutableMap.of(
                                Styles.TWO_HAND, AvNpcSpear.SPEAR
                        ));

        this.weaponLivingMotions
                .put(WOMWeaponCategories.NAPOLEON,
                        ImmutableMap.of(Styles.TWO_HAND,
                                Set.of(
                                        Pair.of(LivingMotions.BLOCK, AnimsWom.GLOWING_AGONY_GUARD),
                                        Pair.of(LivingMotions.IDLE, AnimsAgony.AGONY_IDLE),
                                        Pair.of(LivingMotions.WALK, AnimsAgony.AGONY_WALK),
                                        Pair.of(LivingMotions.RUN, AnimsAgony.AGONY_RUN),
                                        Pair.of(LivingMotions.CHASE, AnimsAgony.AGONY_RUN),
                                        Pair.of(LivingMotions.DEATH, Animations.BIPED_DEATH)
                                )));


        this.guardHitMotions.put(WeaponCategories.SWORD,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        ),
                        Styles.TWO_HAND, List.of(
                                Animations.SWORD_DUAL_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WeaponCategories.LONGSWORD,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.LONGSWORD_GUARD_ACTIVE_HIT1,
                                Animations.LONGSWORD_GUARD_ACTIVE_HIT2
                        ),
                        Styles.TWO_HAND, List.of(
                                Animations.LONGSWORD_GUARD_HIT
                        )
                )
        );
        if (ModList.get().isLoaded("dualaxes")) {
            this.guardHitMotions.put(CapabilityItem.WeaponCategories.AXE,
                    ImmutableMap.of(
                            CapabilityItem.Styles.ONE_HAND, List.of(
                                    Animations.SWORD_GUARD_ACTIVE_HIT1,
                                    Animations.SWORD_GUARD_ACTIVE_HIT2,
                                    Animations.SWORD_GUARD_ACTIVE_HIT3
                            ),
                            CapabilityItem.Styles.TWO_HAND, List.of(
                                    Animations.SWORD_DUAL_GUARD_HIT
                            )
                    )
            );
        } else {
            this.guardHitMotions.put(WeaponCategories.AXE,
                    ImmutableMap.of(
                            Styles.ONE_HAND, List.of(
                                    Animations.SWORD_GUARD_ACTIVE_HIT1,
                                    Animations.SWORD_GUARD_ACTIVE_HIT2,
                                    Animations.SWORD_GUARD_ACTIVE_HIT3
                            )
                    )
            );
        }
        if (ModList.get().isLoaded("dualgreatswords")) {
            guardHitMotions.put(CapabilityItem.WeaponCategories.GREATSWORD,
                    ImmutableMap.of(
                            CapabilityItem.Styles.TWO_HAND, List.of(
                                    Animations.GREATSWORD_GUARD_HIT
                            ),
                            CapabilityItem.Styles.OCHS, List.of(
                                    Animations.SWORD_DUAL_GUARD_HIT
                            )
                    )
            );
        } else {
            this.guardHitMotions.put(WeaponCategories.GREATSWORD,
                    ImmutableMap.of(
                            Styles.TWO_HAND, List.of(
                                    Animations.GREATSWORD_GUARD_HIT
                            )
                    )
            );
        }
        this.guardHitMotions.put(WOMWeaponCategories.TORMENT,
                ImmutableMap.of(
                        Styles.TWO_HAND, List.of(
                                Animations.GREATSWORD_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WOMWeaponCategories.ANTITHEUS,
                ImmutableMap.of(
                        Styles.TWO_HAND, List.of(
                                Animations.SPEAR_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WOMWeaponCategories.NAPOLEON,
                ImmutableMap.of(
                        Styles.TWO_HAND, List.of(
                                Animations.SPEAR_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WeaponCategories.SPEAR,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SPEAR_GUARD_HIT
                        ),
                        Styles.TWO_HAND, List.of(
                                Animations.SPEAR_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WeaponCategories.TRIDENT,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SPEAR_GUARD_HIT
                        ),
                        Styles.TWO_HAND, List.of(
                                Animations.SPEAR_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WeaponCategories.UCHIGATANA,
                ImmutableMap.of(
                        Styles.OCHS, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        ),
                        Styles.TWO_HAND, List.of(
                                Animations.SWORD_DUAL_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WeaponCategories.DAGGER,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        ),
                        Styles.TWO_HAND, List.of(
                                Animations.SWORD_DUAL_GUARD_HIT
                        )
                )
        );
        this.guardHitMotions.put(WeaponCategories.TACHI,
                ImmutableMap.of(
                        Styles.ONE_HAND, List.of(
                                Animations.SWORD_GUARD_ACTIVE_HIT1,
                                Animations.SWORD_GUARD_ACTIVE_HIT2,
                                Animations.SWORD_GUARD_ACTIVE_HIT3
                        ),
                        Styles.TWO_HAND, List.of(
                                Animations.SWORD_DUAL_GUARD_HIT
                        )
                )
        );
    }

    @Override
    protected CECombatBehaviors.Builder<MobPatch<?>> getCustomWeaponMotionBuilder() {
        CapabilityItem mainHandCap = this.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        CapabilityItem offHandCap = this.getHoldingItemCapability(InteractionHand.OFF_HAND);
        CECombatBehaviors.Builder<MobPatch<?>> customOverride = MobPatchCommon.overideCustomWeaponMotionBuilderForAvNpc(mainHandCap, offHandCap, mainHandCap.getStyle(this));
        if (customOverride == null) customOverride = MobPatchCommon.overideBowMotionBuilderForNpc(mainHandCap, mainHandCap.getStyle(this));
        return customOverride != null ? customOverride : super.getCustomWeaponMotionBuilder();
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
