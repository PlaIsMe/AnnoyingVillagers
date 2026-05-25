package com.pla.annoyingvillagers.capabilities;

import com.pla.annoyingvillagers.clazz.ThrowableSpearItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.UseAnim;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class AVThrowableSpearCapability extends WeaponCapability {
    public AVThrowableSpearCapability(CapabilityItem.Builder builder) {
        super(builder);
    }

    @Override
    public UseAnim getUseAnimation(LivingEntityPatch<?> livingEntityPatch) {
        return UseAnim.SPEAR;
    }

    @Override
    public LivingMotion getLivingMotion(LivingEntityPatch<?> livingEntityPatch, InteractionHand hand) {
        LivingEntity livingEntity = livingEntityPatch.getOriginal();

        if (livingEntity.isUsingItem()
                && livingEntity.getUsedItemHand() == hand
                && livingEntity.getItemInHand(hand).getItem() instanceof ThrowableSpearItem) {
            return LivingMotions.AIM;
        }

        return super.getLivingMotion(livingEntityPatch, hand);
    }
}
