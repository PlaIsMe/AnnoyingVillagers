package com.pla.annoyingvillagers.capabilities;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.UseAnim;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class AVBowCapability extends WeaponCapability {
    public AVBowCapability(CapabilityItem.Builder builder) {
        super(builder);
    }

    @Override
    public UseAnim getUseAnimation(LivingEntityPatch<?> livingEntityPatch) {
        return UseAnim.BOW;
    }

    @Override
    public LivingMotion getLivingMotion(LivingEntityPatch<?> livingEntityPatch, InteractionHand hand) {
        LivingEntity livingEntity = livingEntityPatch.getOriginal();

        if (livingEntity.isUsingItem() && livingEntity.getUseItem().getUseAnimation() == UseAnim.BOW) {
            return LivingMotions.AIM;
        }

        return super.getLivingMotion(livingEntityPatch, hand);
    }
}
