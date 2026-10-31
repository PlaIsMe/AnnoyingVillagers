package com.pla.annoyinglib.mixin;

import com.pla.annoyinglib.entity.TridentLightningBolt;
import com.pla.annoyinglib.init.AnnoyingLibModDamageTypes;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "getAttackStrengthScale", at = @At("HEAD"), cancellable = true)
    private void av$fullStrengthHackerCombo(float partialTicks,
            org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Float> cir) {
        if (com.pla.annoyinglib.item.HackerSwordItem.isComboAttack((Player)(Object)this)) {
            cir.setReturnValue(1.0F);
        }
    }
    @Redirect(
            method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/damagesource/DamageSource;is(Lnet/minecraft/tags/TagKey;)Z",
                    ordinal = 0
            )
    )
    private boolean customBypassInvulnerability(DamageSource source, TagKey<DamageType> tag) {
        Player self = (Player) (Object) this;
        boolean original = source.is(tag);

        if (DamageTypeTags.BYPASSES_INVULNERABILITY.equals(tag)
                && self.getAbilities().invulnerable
                && source.is(AnnoyingLibModDamageTypes.IMPACT_EXPLOSION)
                && source.getDirectEntity() instanceof TridentLightningBolt) {
            return false;
        }

        return original;
    }

    // add this in herobrine_vessels
    // @Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
    // private void annoyingLib$keepEnderAegisUsable(boolean guaranteedDisable, CallbackInfo ci) {
    //     Player self = (Player)(Object)this;
    //     if (VanillaWeaponAbilityUtil.abilitiesEnabled() && self.getUseItem().getItem() instanceof EnderAegisItem) ci.cancel();
    // }

}
