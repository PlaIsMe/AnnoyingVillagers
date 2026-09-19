package com.pla.annoyingvillagers.mixin.client;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraAccessor {
    @Invoker("move")
    void annoyingVillagers$move(float distanceOffset, float verticalOffset, float horizontalOffset);

    @Invoker("getMaxZoom")
    float annoyingVillagers$getMaxZoom(float startingDistance);
}
