package com.pla.annoyingvillagers.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.pla.annoyingvillagers.entity.FloatingLookBlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = AttackAnimation.class, remap = false)
public abstract class AttackAnimationMixin {
    @Shadow
    public abstract EpicFightDamageSource getEpicFightDamageSource(
            LivingEntityPatch<?> entitypatch,
            Entity target,
            AttackAnimation.Phase phase
    );

    @ModifyExpressionValue(
            method = "hurtCollidingEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lyesman/epicfight/api/animation/types/AttackAnimation$Phase;getCollidingEntities(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lyesman/epicfight/api/animation/types/AttackAnimation;FFF)Ljava/util/List;"
            )
    )
    private List<Entity> annoyingvillagers$hurtFloatingLookBlocks(
            List<Entity> collidingEntities,
            @Local(argsOnly = true) LivingEntityPatch<?> attackerPatch,
            @Local(argsOnly = true) AttackAnimation.Phase phase
    ) {
        List<Entity> remainingEntities = new ArrayList<>(collidingEntities.size());

        for (Entity entity : collidingEntities) {
            if (entity instanceof FloatingLookBlockEntity floatingLookBlock) {
                annoyingvillagers$hurtFloatingLookBlock(attackerPatch, phase, floatingLookBlock);
            } else {
                remainingEntities.add(entity);
            }
        }

        return remainingEntities;
    }

    private void annoyingvillagers$hurtFloatingLookBlock(
            LivingEntityPatch<?> attackerPatch,
            AttackAnimation.Phase phase,
            FloatingLookBlockEntity target
    ) {
        if (target.level().isClientSide()
                || !target.isAlive()
                || attackerPatch.getCurrentlyAttackTriedEntities().contains(target)
                || attackerPatch.isTargetInvulnerable(target)) {
            return;
        }

        LivingEntity attacker = attackerPatch.getOriginal();
        if (!annoyingvillagers$canBeSeen(attacker, target)) {
            return;
        }

        EpicFightDamageSource damageSource = this.getEpicFightDamageSource(attackerPatch, target, phase);
        target.hurt(damageSource, 1.0F);
        attackerPatch.getCurrentlyAttackTriedEntities().add(target);
    }

    private static boolean annoyingvillagers$canBeSeen(LivingEntity attacker, Entity target) {
        AABB targetBox = target.getBoundingBox();
        double distance = target.position().distanceTo(attacker.getEyePosition())
                + targetBox.getCenter().distanceTo(new Vec3(targetBox.maxX, targetBox.maxY, targetBox.maxZ));

        return MathUtils.canBeSeen(target, attacker, distance);
    }
}
