package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.compat.p1nero_bow.EpicFightBow;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.item.FlankerHookedSwordItem;
import com.pla.annoyingvillagers.item.HookedDiamondSwordItem;
import com.pla.annoyingvillagers.item.HookedGoldenSwordItem;
import com.pla.annoyingvillagers.item.HookedIronSwordItem;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.EscapeUtil;
import com.pla.efclash_blade.skill.ClashBladeSkill;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = ClashBladeSkill.class, remap = false)
public class ClashBladeMixin {
    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                                EntityState entityState,
                                                                ServerPlayer serverPlayer,
                                                                CallbackInfoReturnable<Boolean> cir) {
        if (dynamicAnimation.get() instanceof BowAttackAnimation
                || (ModList.get().isLoaded("p1nero_bow") && EpicFightBow.isP1neroBowAnimation(dynamicAnimation))) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "getWeaponDestroyValueOnClash", at = @At("HEAD"), cancellable = true)
    private static void forceDestroyWeaponValueOnClash(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                       DamageSource damageSource,
                                                       PlayerPatch<?> playerPatch,
                                                       ServerLevel serverLevel,
                                                       CallbackInfoReturnable<Integer> cir) {
        if (ModList.get().isLoaded("efn")) {
            if (EpicFightNightFall.isEfnWeapons(playerPatch.getOriginal().getMainHandItem())) {
                if (CommonUtil.isAvDamageableEfnWeaponsMob(damageSource.getEntity())) {
                    if (EscapeUtil.isAnimationDangerous(dynamicAnimation)) {
                        cir.setReturnValue(AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get() * EpicFightNightFall.MULTIPLIER_DAMAGE_VALUE);
                    } else {
                        cir.setReturnValue(AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get());
                    }
                }
                cir.setReturnValue(0);
            }
        }
    }

    @Inject(method = "moreLogicAfterClashing", at = @At("HEAD"))
    private static void forceMoreLogicAfterClash(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                 DamageSource damageSource,
                                                 PlayerPatch<?> playerPatch,
                                                 ServerLevel serverLevel,
                                                 CallbackInfo ci) {
        Player player = playerPatch.getOriginal();
        if (player.getMainHandItem().getItem() instanceof HookedIronSwordItem
                || player.getMainHandItem().getItem() instanceof HookedGoldenSwordItem
                || player.getMainHandItem().getItem() instanceof HookedDiamondSwordItem
                || player.getMainHandItem().getItem() instanceof FlankerHookedSwordItem) {
            if (dynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO1) {
                CommonUtil.applyHookClashDisarmLogic(
                        player,
                        damageSource,
                        serverLevel,
                        AnimsPugilistSteve.KNOCKDOWN_RIGHT,
                        HookDisarmLaunch.RIGHT
                );
                return;
            }

            if (dynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO2) {
                CommonUtil.applyHookClashDisarmLogic(
                        player,
                        damageSource,
                        serverLevel,
                        AnimsPugilistSteve.KNOCKDOWN_LEFT,
                        HookDisarmLaunch.LEFT
                );
                return;
            }

            if (dynamicAnimation == AnimsEpicFight.HOOK_DANCING_EDGE || dynamicAnimation == AnimsWom.HOOK_HERRSCHER_UP) {
                CommonUtil.applyHookClashDisarmLogic(
                        player,
                        damageSource,
                        serverLevel,
                        AnimsPugilistSteve.GUARD_BREAK_ATTACK,
                        HookDisarmLaunch.BACKWARD
                );
            }
        }
    }
}
