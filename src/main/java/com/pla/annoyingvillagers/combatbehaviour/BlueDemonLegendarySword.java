package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.LegendarySwordItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsEnderblaster;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

public class BlueDemonLegendarySword {
    public static final Builder<MobPatch<?>> LEGENDARY_SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
            )
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    WOMAnimations.TORMENT_AUTO_1,
                                    WOMAnimations.TORMENT_AUTO_2,
                                    AnimsSolar.SOLAR_AUTO_1,
                                    AnimsPugilistSteve.LEGENDARY_SWORD_AUTO_4,
                                    AnimsPugilistSteve.LEGENDARY_SWORD_WAKE_UP_ATTACK,
                                    AnimsWom.YELLOW_SOLAR_AUTO_2,
                                    AnimsWom.YELLOW_NAPOLEON_AUTO_3,
                                    AnimsWom.DEMONIAC_TORMENT_CHARGED_ATTACK_2
                            ),
                            CombatCommon.animations(
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_TISHNAW
                            ),
                            CombatCommon.animations(
                                    AnimsSolar.SOLAR_AUTO_4,
                                    WOMAnimations.TORMENT_AIRSLAM,
                                    WOMAnimations.TORMENT_AUTO_3,
                                    WOMAnimations.TORMENT_AUTO_4,
                                    WOMAnimations.TORMENT_DASH,
                                    AnimsWom.DEMONIAC_RUINE_AUTO_1,
                                    AnimsWom.DEMONIAC_RUINE_AUTO_4,
                                    AnimsWom.DEMONIAC_RUINE_AUTO_2,
                                    AnimsRuine.RUINE_CHATIMENT,
                                    WOMAnimations.TORMENT_BERSERK_DASH,
                                    WOMAnimations.TORMENT_BERSERK_AUTO_1,
                                    WOMAnimations.TORMENT_BERSERK_AUTO_2,
                                    WOMAnimations.TORMENT_BERSERK_AIRSLAM,
                                    Animations.GREATSWORD_AUTO1,
                                    Animations.GREATSWORD_AUTO2,
                                    Animations.THE_GUILLOTINE,
                                    WOMAnimations.TORMENT_CHARGED_ATTACK_1,
                                    WOMAnimations.TORMENT_CHARGED_ATTACK_3,
                                    AnimsSolar.SOLAR_AUTO_2,
                                    AnimsAgony.AGONY_RIPPING_FANGS,
                                    AnimsAgony.AGONY_AUTO_3,
                                    AVAnimations.ELECTRIC_FIELD,
                                    AnimsWom.DEMONIAC_RUINE_COMET,
                                    AnimsWom.CLONE_ENDERBLASTER_ONEHAND_DASH,
                                    AnimsWom.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT,
                                    AnimsSolar.SOLAR_OBSCURIDAD_AUTO_4,
                                    AnimsWom.CLONE_NAPOLEON_WATERLOW_SHOOT
                            ),
                            CombatCommon.rollStepAnimations())
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_DASH, 0.0F)
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(WOMAnimations.TORMENT_DASH, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(WOMAnimations.TORMENT_DASH, 0.0F)
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsWom.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(AnimsWom.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT, 0.0F)
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsWom.CLONE_NAPOLEON_WATERLOW_SHOOT, 0.0F)
                                            .addExBehavior(BlueDemonLegendarySword::legendarySwordSpecialAttack)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsAgony.AGONY_RISING_EAGLE, 0.0F)
                                            .addExBehavior(BlueDemonLegendarySword::legendarySwordHeavyAttack)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown(400)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(5.0D, 10.0D)
                                            .animationBehavior(AVAnimations.ELECTRIC_FIELD, 0.0F)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());

    static void legendarySwordHeavyAttack(MobPatch<?> mobpatch) {
        BlueDemonEntity blueDemonEntity = (BlueDemonEntity) mobpatch.getOriginal();
        ItemStack itemStack = blueDemonEntity.getMainHandItem();
        if (itemStack.getItem() instanceof LegendarySwordItem && blueDemonEntity.level() instanceof ServerLevel serverLevel) {
            blueDemonEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));
            new DelayedTask(10) {
                @Override
                public void run() {
                    serverLevel.playSound(
                            null,
                            blueDemonEntity.getX(), blueDemonEntity.getY(), blueDemonEntity.getZ(),
                            AnnoyingVillagersModSounds.HEAVY_ATTACK_START.get(),
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F
                    );

                    serverLevel.playSound(
                            null,
                            blueDemonEntity.getX(), blueDemonEntity.getY(), blueDemonEntity.getZ(),
                            AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD.get(),
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F
                    );

                    serverLevel.playSound(
                            null,
                            blueDemonEntity.getX(), blueDemonEntity.getY(), blueDemonEntity.getZ(),
                            AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD_2.get(),
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F
                    );

                    serverLevel.sendParticles(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            blueDemonEntity.getX(), blueDemonEntity.getY(), blueDemonEntity.getZ(),
                            15,
                            0.0D, 0.0D, 0.0D,
                            0.2D);

                    serverLevel.sendParticles(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            blueDemonEntity.getX(), blueDemonEntity.getEyeY(), blueDemonEntity.getZ(),
                            100,
                            0.0D, 0.0D, 0.0D,
                            0.5D
                    );
                    mobpatch.playAnimationSynchronized(AnimsPugilistSteve.LEGENDARY_SWORD_HEAVY_ATTACK, 0.0F);
                }
            };
        }
    }

    static void legendarySwordSpecialAttack(MobPatch<?> mobpatch) {
        BlueDemonEntity blueDemonEntity = (BlueDemonEntity) mobpatch.getOriginal();
        ItemStack itemStack = blueDemonEntity.getMainHandItem();
        if (itemStack.getItem() instanceof LegendarySwordItem && blueDemonEntity.level() instanceof ServerLevel) {
            blueDemonEntity.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 2));
            new DelayedTask(20) {
                @Override
                public void run() {
                    mobpatch.playAnimationSynchronized(AnimsPugilistSteve.LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F);
                }
            };
        }
    }
}
