package com.pla.annoyingvillagers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.List;
import java.util.Map;

@Mixin(value = WeaponCapability.class, remap = false)
public interface WeaponCapabilityAccessor {
    @Accessor("autoAttackMotions")
    Map<Style, List<AnimationAccessor<? extends AttackAnimation>>> annoyingvillagers$getAutoAttackMotions();
}
