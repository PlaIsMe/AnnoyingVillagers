package com.pla.annoyingvillagers.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.Set;

@Mixin(Brain.class)
public interface BrainAccessor<E extends LivingEntity> {
    @Accessor("availableBehaviorsByPriority")
    Map<Integer, Map<Activity, Set<BehaviorControl<? super E>>>> annoyingVillagers$getAvailableBehaviorsByPriority();
}
