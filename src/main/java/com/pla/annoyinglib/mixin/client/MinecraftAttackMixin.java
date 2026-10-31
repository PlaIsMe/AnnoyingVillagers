package com.pla.annoyinglib.mixin.client;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.item.BlueDemonTridentItem;
import com.pla.annoyinglib.item.DestructionEyeItem;
import com.pla.annoyinglib.network.ServerboundDestructionEyeAttack;
import com.pla.annoyinglib.network.VanillaAttackKeyMessage;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftAttackMixin {
    private static boolean annoyingLib$blueDemonOffhandNext;

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void annoyingLib$handleVanillaAbilityAttack(CallbackInfoReturnable<Boolean> cir) {
        Minecraft minecraft = (Minecraft)(Object)this;
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        if (player.getMainHandItem().getItem() instanceof DestructionEyeItem) {
            AnnoyingLib.PACKET_HANDLER.sendToServer(new ServerboundDestructionEyeAttack());
            player.swing(InteractionHand.MAIN_HAND);
            cir.setReturnValue(false);
            return;
        }
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled()) return;

         // add this in herobrine_vessels
        // if (player.getMainHandItem().getItem() instanceof EnderSlayerScytheItem && EnderSlayerScytheItem.isDragonActive(player.getMainHandItem())) {
        //     AnnoyingLib.PACKET_HANDLER.sendToServer(new VanillaAttackKeyMessage());
        //     VanillaWeaponAbilityUtil.swingOffHand(player);
        //     cir.setReturnValue(false);
        //     return;
        // }

        // if (player.getMainHandItem().getItem() instanceof DemoniacVoltageReaverItem && DemoniacVoltageReaverItem.isVanillaAwakened(player.getMainHandItem(), player.level())) {
        //     AnnoyingLib.PACKET_HANDLER.sendToServer(new VanillaAttackKeyMessage());
        //     VanillaWeaponAbilityUtil.swingMainHand(player);
        //     cir.setReturnValue(false);
        // }
    }

    @Redirect(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void annoyingLib$alternateBlueDemonTridentSwing(LocalPlayer player, InteractionHand originalHand) {
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && BlueDemonTridentItem.isBlueDemonTrident(player.getMainHandItem()) && BlueDemonTridentItem.isBlueDemonTrident(player.getOffhandItem())) {
            InteractionHand hand = annoyingLib$blueDemonOffhandNext ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            annoyingLib$blueDemonOffhandNext = !annoyingLib$blueDemonOffhandNext;
            player.swing(hand);
            return;
        }
        annoyingLib$blueDemonOffhandNext = false;
        player.swing(originalHand);
    }
}
