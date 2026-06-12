package com.pla.annoyingvillagers.mixin;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FishingHook.class)
public interface FishingHookAccessor {
    @Invoker("setHookedEntity")
    void annoyingVillagers$invokeSetHookedEntity(@Nullable Entity entity);
}
