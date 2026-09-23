package com.pla.annoyingvillagers.mixin.compat.bettercombat;

import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.item.EnderSlayerScytheItem;
import com.pla.annoyingvillagers.network.VanillaAttackKeyMessage;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.bettercombat.api.WeaponAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Intercepts Better Combat before it selects and broadcasts an ordinary combo attack. */
@Pseudo
@Mixin(targets = "net.bettercombat.client.AttackInteractor", remap = false)
public abstract class BetterCombatAttackInteractorMixin {
    @Inject(method = "startUpswing", at = @At("HEAD"), cancellable = true, remap = false)
    private void annoyingVillagers$startWeaponAbilityInstead(WeaponAttributes attributes, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;

        boolean activeScythe = player.getMainHandItem().getItem() instanceof EnderSlayerScytheItem
                && EnderSlayerScytheItem.isDragonActive(player.getMainHandItem());
        boolean awakenedReaver = player.getMainHandItem().getItem() instanceof DemoniacVoltageReaverItem
                && DemoniacVoltageReaverItem.isVanillaAwakened(player.getMainHandItem(), player.level());
        if (!activeScythe && !awakenedReaver) return;

        ClientPacketDistributor.sendToServer(new VanillaAttackKeyMessage());
        ci.cancel();
    }
}
