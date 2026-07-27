package com.pla.annoyingvillagers.animations;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.EntityState.StateFactor;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer.Priority;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.gamerule.EpicFightGameRules;

import javax.annotation.Nullable;

public class HeavyAttackAnimation extends AttackAnimation {
    void init() {
        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float mfloat = Float.parseFloat(String.format(Locale.US, "%.2f", 1.0F / this.getTotalTime()));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, mfloat);
        }
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Set<JointMask> jointMaskSet = new HashSet<>();
            jointMaskSet.add(JointMask.of("Root", JointMask.KEEP_CHILD_LOCROT));
            jointMaskSet.add(JointMask.of("Torso"));
            jointMaskSet.add(JointMask.of("Chest"));
            jointMaskSet.add(JointMask.of("Head"));
            jointMaskSet.add(JointMask.of("Shoulder_R"));
            jointMaskSet.add(JointMask.of("Arm_R"));
            jointMaskSet.add(JointMask.of("Hand_R"));
            jointMaskSet.add(JointMask.of("Elbow_R"));
            jointMaskSet.add(JointMask.of("Tool_R"));
            jointMaskSet.add(JointMask.of("Shoulder_L"));
            jointMaskSet.add(JointMask.of("Arm_L"));
            jointMaskSet.add(JointMask.of("Hand_L"));
            jointMaskSet.add(JointMask.of("Elbow_L"));
            jointMaskSet.add(JointMask.of("Tool_L"));
            jointMaskSet.add(JointMask.of("Thigh_R"));
            jointMaskSet.add(JointMask.of("Leg_R"));
            jointMaskSet.add(JointMask.of("Knee_R"));
            jointMaskSet.add(JointMask.of("Thigh_L"));
            jointMaskSet.add(JointMask.of("Leg_L"));
            jointMaskSet.add(JointMask.of("Knee_L"));
            JointMaskEntry jointMaskEntry = JointMaskEntry.builder()
                    .defaultMask(JointMask.JointMaskSet.of(jointMaskSet))
                    .create();

            this.addProperty(ClientAnimationProperties.JOINT_MASK, jointMaskEntry);
        }
    }

    public HeavyAttackAnimation(float convertTime, float antic, float preDelay,
                                float contact, float recovery,
                                @Nullable Collider collider, Joint colliderJoint,
                                AnimationManager.AnimationAccessor<? extends HeavyAttackAnimation> accessor,
                                AssetAccessor<? extends Armature> armature) {
        this(convertTime, accessor, armature,
                new Phase(0.0F, antic, preDelay, contact, recovery, Float.MAX_VALUE, colliderJoint, collider));
    }

    public HeavyAttackAnimation(float convertTime,
                                AnimationManager.AnimationAccessor<? extends HeavyAttackAnimation> accessor,
                                AssetAccessor<? extends Armature> armature,
                                Phase... phases) {
        super(convertTime, accessor, armature, phases);
        init();
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER,
                ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    protected void bindPhaseState(Phase phase) {
        float f = phase.preDelay;

        if (f == 0.0F) {
            f += 0.01F;
        }

        this.stateSpectrumBlueprint.newTimePair(phase.start, f).addState(EntityState.PHASE_LEVEL, 1).newTimePair(phase.start, phase.contact + 0.01F).addState(EntityState.CAN_SKILL_EXECUTION, false).newTimePair(phase.start, phase.recovery).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.UPDATE_LIVING_MOTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).newTimePair(phase.start, phase.end).addState(EntityState.INACTION, true).newTimePair(f, phase.contact + 0.01F).addState(EntityState.ATTACKING, true).addState(EntityState.PHASE_LEVEL, 2).newTimePair(phase.contact + 0.01F, phase.end).addState(EntityState.PHASE_LEVEL, 3).addState(EntityState.TURNING_LOCKED, true);
    }

    public void end(LivingEntityPatch<?> livingentitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean flag) {
        super.end(livingentitypatch, nextAnimation, flag);
        boolean flag1 = ((BooleanValue) ((LivingEntity) livingentitypatch.getOriginal()).level().getGameRules().getRule(EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleKey())).get();

        if (!flag && !nextAnimation.get().isMainFrameAnimation() && livingentitypatch.isLogicalClient() && !flag1) {
            float f = 0.05F * this.getPlaySpeed(livingentitypatch, nextAnimation.get());

            livingentitypatch.getClientAnimator().baseLayer.copyLayerTo(livingentitypatch.getClientAnimator().baseLayer.getLayer(Priority.HIGHEST), f);
        }

    }

    public TypeFlexibleHashMap<StateFactor<?>> getStatesMap(LivingEntityPatch<?> livingentitypatch, float f) {
        TypeFlexibleHashMap<StateFactor<?>> typeflexiblehashmap = super.getStatesMap(livingentitypatch, f);

        if (!livingentitypatch.getOriginal().level().getGameRules().getRule(EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleKey()).get()) {
            typeflexiblehashmap.put((EntityState.StateFactor<?>) EntityState.MOVEMENT_LOCKED, Boolean.FALSE);
            typeflexiblehashmap.put((EntityState.StateFactor<?>) EntityState.UPDATE_LIVING_MOTION, Boolean.TRUE);
        }

        return typeflexiblehashmap;
    }

    protected Vec3 getCoordVector(LivingEntityPatch<?> livingentitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation) {
        Vec3 vec3 = super.getCoordVector(livingentitypatch, nextAnimation);

        if (livingentitypatch.shouldBlockMoving() && (Boolean) this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0D);
        }

        return vec3;
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }

    public boolean shouldPlayerMove(LocalPlayerPatch localplayerpatch) {
        return !localplayerpatch.isLogicalClient() || ((BooleanValue) ((LocalPlayer) localplayerpatch.getOriginal()).level().getGameRules().getRule(EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleKey())).get() || ((LocalPlayer) localplayerpatch.getOriginal()).input.forwardImpulse == 0.0F && ((LocalPlayer) localplayerpatch.getOriginal()).input.leftImpulse == 0.0F;
    }
}
