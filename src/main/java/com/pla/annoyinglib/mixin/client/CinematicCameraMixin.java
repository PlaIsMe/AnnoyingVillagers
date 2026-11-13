package com.pla.annoyinglib.mixin.client;

import com.pla.annoyinglib.client.camera.CinematicCameraController;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CinematicCameraMixin {
    @Shadow private boolean detached;
    @Shadow public abstract Vec3 getPosition();
    @Shadow public abstract float getYRot();
    @Shadow public abstract float getXRot();
    @Shadow protected abstract void setPosition(Vec3 position);
    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "setup", at = @At("TAIL"))
    private void annoyinglib$cinematic(BlockGetter level, Entity entity, boolean detached, boolean mirrored,
                                       float partialTick, CallbackInfo callback) {
        var pose = CinematicCameraController.camera(getPosition(), getYRot(), getXRot());
        if (pose == null) return;
        this.detached = true;
        setPosition(pose.position());
        setRotation(pose.yaw(), pose.pitch());
    }
}
