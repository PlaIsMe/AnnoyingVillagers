package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.item.BedrockWeaponItem;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.skill.BedrockWeaponSkill;
import com.pla.annoyingvillagers.skill.EnderAegisSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.guard.ImpactGuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.TakeDamageEvent;

import java.util.Objects;

@Mixin(value = {ImpactGuardSkill.class}, remap = false)
public abstract class ImpactGuardSkillMixin {
    private static boolean isAdvancedBlockableDamageSource(DamageSource damageSource) {
        return damageSource.is(DamageTypeTags.IS_EXPLOSION) || damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypeTags.IS_FIRE) || damageSource.is(DamageTypeTags.IS_PROJECTILE) || damageSource.is(DamageTypeTags.BYPASSES_ARMOR);
    }

    @Inject(method = {"dealEvent"}, at = {@At("HEAD")}, cancellable = true)
    private void playerOnGuard(PlayerPatch<?> playerpatch, TakeDamageEvent.Attack event, boolean advanced, CallbackInfo ci) {
        boolean isSpecialSource = isAdvancedBlockableDamageSource(event.getDamageSource());
        if (!isSpecialSource) {
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
}
