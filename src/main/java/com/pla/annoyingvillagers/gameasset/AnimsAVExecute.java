package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.fml.common.Mod;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.collider.MultiCollider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsAVExecute {
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> STRANGLE_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> STRANGLE_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> WRESTLING_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> WRESTLING_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> WRESTLING_BACK_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> WRESTLING_BACK_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> STAB_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> DUAL_STAB_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> STAB_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> SHIELD_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> SHIELD_EXECUTE_HIT;

    private static final ExtraDamageInstance.ExtraDamage TARGET_MAX_HEALTH = new ExtraDamageInstance.ExtraDamage((attacker, itemstack, target, baseDamage, params) -> params[0] + target.getMaxHealth() * params[1], (itemstack, tooltips, baseDamage, params) -> {
    });

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        MultiCollider<OBBCollider> executionCollider = new MultiOBBCollider(3, 1.25F, 1.5F, 1.5F, 0.0F, 1.5F, -1.5F);
        MultiCollider<OBBCollider> executionColliderBack = new MultiOBBCollider(3, 1.25F, 1.5F, 1.5F, 0.0F, 1.5F, 1.5F);

        STRANGLE_EXECUTE = builder.nextAccessor("biped/av_execute/strangle_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.01F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.1F, 0.29F, 1.0F, 1.2F, 1.2F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.2F, 0.0F, 3.36F, 1.9F, 1.9F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get())))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        STRANGLE_EXECUTE_HIT = builder.nextAccessor("biped/av_execute/strangle_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.01F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 0.8333333F));

        WRESTLING_EXECUTE = builder.nextAccessor("biped/av_execute/wrestling_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.0F, 0.05F, 1.85F, 2.0F, 2.0F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 2.0F, 0.0F, 3.36F, 2.5F, 2.5F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        WRESTLING_EXECUTE_HIT = builder.nextAccessor("biped/av_execute/wrestling_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.01F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 0.8333333F));

        WRESTLING_BACK_EXECUTE = builder.nextAccessor("biped/av_execute/wrestling_back_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.0F, 0.05F, 1.85F, 2.0F, 2.0F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 2.0F, 0.0F, 3.36F, 2.5F, 2.5F, Armatures.BIPED.get().rootJoint, executionColliderBack))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        WRESTLING_BACK_EXECUTE_HIT = builder.nextAccessor("biped/av_execute/wrestling_back_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.01F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 0.8333333F));

        STAB_EXECUTE = builder.nextAccessor("biped/av_execute/stab_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.05F, 0.05F, 1.85F, 2.0F, 0.4F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.4F,  0.4F, 0.6F, 0.6F, 1.1F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.0F, 1.2F, 1.4F, Float.MAX_VALUE, Float.MAX_VALUE, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));

        DUAL_STAB_EXECUTE = builder.nextAccessor("biped/av_execute/dual_stab_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.05F, 0.05F, 1.85F, 2.0F, 0.4F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.4F,  0.4F, 0.6F, 0.6F, 1.1F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.0F, 1.2F, 1.4F, Float.MAX_VALUE, Float.MAX_VALUE, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        STAB_EXECUTE_HIT = builder.nextAccessor("biped/av_execute/stab_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.1F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));

        SHIELD_EXECUTE = builder.nextAccessor("biped/av_execute/shield_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.1F, 0.65F, 0.8F, 1.2F, 1.2F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 1.2F, 1.45F, 1.6F, 1.6F, 1.6F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.6F, 2.05F, 2.3F, 2.3F, 2.3F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        SHIELD_EXECUTE_HIT = builder.nextAccessor("biped/av_execute/shield_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.1F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
    }
}
