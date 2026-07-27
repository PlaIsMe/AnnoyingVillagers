package com.pla.annoyingvillagers.animations;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.util.BowFunction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class BowAttackAnimation extends AttackAnimation {
    public BowAttackAnimation(float convertTime,
                              float antic,
                              float preDelay,
                              float contact,
                              float recovery,
                              InteractionHand hand,
                              @Nullable Collider collider,
                              Joint colliderJoint,
                              AnimationManager.AnimationAccessor<? extends BowAttackAnimation> accessor,
                              AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        this.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);
    }

    @Override
    protected void bindPhaseState(Phase phase) {
        float start = phase.start;
        float end = phase.end;

        this.stateSpectrumBlueprint
                .newTimePair(start, end)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.TURNING_LOCKED, true)
                .addState(EntityState.LOCKON_ROTATE, true)
                .addState(EntityState.INACTION, true);
    }

    @Override
    public void begin(LivingEntityPatch<?> livingEntityPatch) {
        super.begin(livingEntityPatch);
        LivingEntity livingEntity = livingEntityPatch.getOriginal();
        ItemStack stack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (!livingEntity.level().isClientSide() &&
                !stack.isEmpty() && stack.getTag() != null && stack.getItem() instanceof BowItem
                && BowFunction.hasArrowOrInfinity(livingEntity, stack)) {
            stack.getTag().putFloat("Pulling", 0.1F);
        }
    }

    @Override
    public void end(LivingEntityPatch<?> livingEntityPatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        LivingEntity livingEntity = livingEntityPatch.getOriginal();
        ItemStack stack = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (!livingEntity.level().isClientSide() &&
                !stack.isEmpty() && stack.getTag() != null && stack.getItem() instanceof BowItem) {
            stack.getTag().remove("Pulling");
        }
        super.end(livingEntityPatch, nextAnimation, isEnd);
    }
}
