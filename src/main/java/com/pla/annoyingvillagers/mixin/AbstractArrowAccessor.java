package com.pla.annoyingvillagers.mixin;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Invoker("setPierceLevel")
    void annoyingVillagers$setPierceLevel(byte pierceLevel);
}
