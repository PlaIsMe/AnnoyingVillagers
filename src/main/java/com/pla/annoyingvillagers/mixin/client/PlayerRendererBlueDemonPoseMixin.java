package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class PlayerRendererBlueDemonPoseMixin {
    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void annoyingVillagers$aimOffhandBlueDemonTrident(Avatar player, AvatarRenderState state,
                                                              float partialTick, CallbackInfo ci) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled() || !player.isUsingItem() || !player.isShiftKeyDown()) return;
        if (!BlueDemonTridentItem.isBlueDemonTrident(player.getMainHandItem())
                || !BlueDemonTridentItem.isBlueDemonTrident(player.getOffhandItem())) return;
        state.rightArmPose = HumanoidModel.ArmPose.ITEM;
        state.leftArmPose = HumanoidModel.ArmPose.THROW_TRIDENT;
    }
}
