package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.item.BedrockWeaponItem;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.skill.BedrockWeaponSkill;
import com.pla.annoyingvillagers.skill.EnderAegisSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.TakeDamageEvent;

import java.util.Objects;

@Mixin(value = {GuardSkill.class}, remap = false)
public abstract class GuardSkillMixin {
    @Inject(method = {"canExecute"}, at = {@At("HEAD")}, cancellable = true)
    private void annoyingVillagers$skipGuardForOffhandUtilityItem(SkillContainer container, CallbackInfoReturnable<Boolean> cir) {
        Player player = container.getExecutor().getOriginal();
        if (FishingRodGrappleUtil.shouldOffhandFishingRodTakeRightClick(player)
                || HookGunItem.shouldOffhandHookGunTakeRightClick(player)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = {"dealEvent"}, at = {@At("HEAD")}, cancellable = true)
    private void playerOnGuard(PlayerPatch<?> playerpatch, TakeDamageEvent.Attack event, boolean advanced, CallbackInfo ci) {
        Player player = playerpatch.getOriginal();
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        ItemStack main = serverPlayer.getMainHandItem();
        if (main.getItem() instanceof EnderAegisItem) {
            if (main.hasTag() && Objects.requireNonNull(main.getTag()).getBoolean("SecondForm")) {
                EnderAegisItem.shieldShoot(serverPlayer.level(), serverPlayer);
            } else {
                EnderAegisSkill.onParry((ServerPlayerPatch) playerpatch);
            }
        }
        if (main.getItem() instanceof BedrockWeaponItem) {
            BedrockWeaponSkill.onParry((ServerPlayerPatch) playerpatch);
        }
    }
}
