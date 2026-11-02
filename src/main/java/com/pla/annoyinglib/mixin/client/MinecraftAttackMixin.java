package com.pla.annoyinglib.mixin.client;
import com.pla.annoyinglib.compat.MobSupremacy;
import com.pla.annoyinglib.compat.VillagerKingdom;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
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
        if ((ModList.get().isLoaded("villager_kingdom") && VillagerKingdom.isDestructionEyeItem(player.getMainHandItem().getItem()))) {
            VillagerKingdom.sendDestructionEyeAttack();
            player.swing(InteractionHand.MAIN_HAND);
            cir.setReturnValue(false);
            return;
        }
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled()) return;

        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.Client.onClientAttack(player, cir);
        }
    }

    @Redirect(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void annoyingLib$alternateBlueDemonTridentSwing(LocalPlayer player, InteractionHand originalHand) {
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonTridentItem_isBlueDemonTrident(player.getMainHandItem()) : false) && (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonTridentItem_isBlueDemonTrident(player.getOffhandItem()) : false)) {
            InteractionHand hand = annoyingLib$blueDemonOffhandNext ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            annoyingLib$blueDemonOffhandNext = !annoyingLib$blueDemonOffhandNext;
            player.swing(hand);
            return;
        }
        annoyingLib$blueDemonOffhandNext = false;
        player.swing(originalHand);
    }
}
