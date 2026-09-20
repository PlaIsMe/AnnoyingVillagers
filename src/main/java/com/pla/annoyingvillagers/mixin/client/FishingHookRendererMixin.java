package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.state.FishingHookRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Lets combat-NPC fishing hooks use the vanilla 26.1 submission renderer. */
@Mixin(FishingHookRenderer.class)
public abstract class FishingHookRendererMixin {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$renderNpcHook(FishingHook hook, Frustum frustum,
                                                  double camX, double camY, double camZ,
                                                  CallbackInfoReturnable<Boolean> cir) {
        Entity owner = hook.getOwner();
        if (owner instanceof LivingEntity && FishingRodGrappleUtil.isNpcCombatFishingHookOwner(owner)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void annoyingVillagers$extractNpcLine(FishingHook hook, FishingHookRenderState state,
                                                   float partialTick, CallbackInfo ci) {
        Entity ownerEntity = hook.getOwner();
        if (!(ownerEntity instanceof LivingEntity owner)
                || !FishingRodGrappleUtil.isNpcCombatFishingHookOwner(ownerEntity)) return;

        Vec3 hand = annoyingVillagers$getNpcRodHandPosition(owner, partialTick);
        Vec3 hookPosition = hook.getPosition(partialTick).add(0.0D, 0.25D, 0.0D);
        state.lineOriginOffset = hand.subtract(hookPosition);
    }

    private static Vec3 annoyingVillagers$getNpcRodHandPosition(LivingEntity owner, float partialTick) {
        int side = annoyingVillagers$getRodHandSide(owner);
        float bodyYaw = Mth.lerp(partialTick, owner.yBodyRotO, owner.yBodyRot) * ((float) Math.PI / 180F);
        double sin = Mth.sin(bodyYaw);
        double cos = Mth.cos(bodyYaw);
        double sideOffset = side * 0.35D * owner.getScale();
        double forwardOffset = 0.8D * owner.getScale();
        double crouchOffset = owner.isCrouching() ? -0.1875D : 0.0D;
        return owner.getEyePosition(partialTick).add(
                -cos * sideOffset - sin * forwardOffset,
                crouchOffset - 0.45D * owner.getScale(),
                -sin * sideOffset + cos * forwardOffset);
    }

    private static int annoyingVillagers$getRodHandSide(LivingEntity owner) {
        int mainHandSide = owner.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
        ItemStack mainHand = owner.getMainHandItem();
        return mainHand.canPerformAction(ItemAbilities.FISHING_ROD_CAST) ? mainHandSide : -mainHandSide;
    }
}
