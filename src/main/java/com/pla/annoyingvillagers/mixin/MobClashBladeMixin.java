package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.compat.EfKick;
import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import com.pla.annoyingvillagers.item.FlankerHookedSwordItem;
import com.pla.annoyingvillagers.item.HookedDiamondSwordItem;
import com.pla.annoyingvillagers.item.HookedGoldenSwordItem;
import com.pla.annoyingvillagers.item.HookedIronSwordItem;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.EscapeUtil;
import com.pla.efclash_blade.event.MobClashBladeEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsMoonless;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.Random;

@Mixin(value = MobClashBladeEvent.class, remap = false)
public class MobClashBladeMixin {
    @Inject(method = "customAdditionClashBladeLogic", at = @At("HEAD"), cancellable = true)
    private static void addMoreClashBladeCondition(LivingAttackEvent livingAttackEvent,
                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
                                                   AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (EpicfightUtil.isLongHitAnimation(defenderDynamicAnimation, defenderLivingEntityPatch)) {
            cir.setReturnValue(false);
            return;
        }

        // Auto clash while playing animation
        if (defender instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AnimsEpicFight.AEGIS_SHIELD_SHOOT_MAINHAND
                && defenderEntityState.getLevel() == 3) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AnimsWom.ENDER_AEGIS_NAPOLEON_RELOAD_1) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof SwordsmanHerobrineEntity
                && defenderDynamicAnimation == WOMAnimations.TORMENT_BERSERK_CONVERT) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof GlaiveHerobrineEntity
                && (defenderDynamicAnimation == AnimsWom.AGONY_GUARD_HIT_1)) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof SledgehammerHerobrineEntity
                && (defenderDynamicAnimation == WOMAnimations.TORMENT_BERSERK_CONVERT)) {
            cir.setReturnValue(true);
            return;
        }

        if (defender instanceof HerobrineChrisEntity
                && (defenderDynamicAnimation == AnimsMoonless.MOONLESS_GUARD_HIT_1)) {
            cir.setReturnValue(true);
            return;
        }

    }

    @Inject(method = "customPreAdditionClashBlade", at = @At("HEAD"), cancellable = true)
    private static void customLogicBeforeClashing(LivingAttackEvent livingAttackEvent,
                                                  LivingEntityPatch<?> defenderLivingEntityPatch,
                                                  AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                  EntityState defenderEntityState, Entity attacker,
                                                  Entity defender, int clashBy,
                                                  CallbackInfo ci) {
        if (defender instanceof LivingEntity livingEntity
                && defender.level() instanceof ServerLevel serverLevel) {
            // Herobrine playing animation
            if (clashBy != 0) {
                if (ModList.get().isLoaded("efn")) {
                    if (defender instanceof AegisHerobrineEntity || defender instanceof GlaiveHerobrineEntity
                            || defender instanceof SledgehammerHerobrineEntity || defender instanceof ReaperHerobrineEntity
                            || defender instanceof SwordsmanHerobrineEntity || defender instanceof ShadowHerobrineEntity) {
                        HerobrineMob herobrineMob = (HerobrineMob) defender;
                        if (herobrineMob.getLivingEntityPatch() != null) {
                            EpicFightNightFall.playEfnGuardHit(herobrineMob.getLivingEntityPatch(), herobrineMob.getEfnGuardHitState(), livingAttackEvent.getSource());
                            herobrineMob.postPlayEfnGuardHit();
                        }
                    }
                } else {
                    if (defender instanceof AegisHerobrineEntity || defender instanceof GlaiveHerobrineEntity
                            || defender instanceof SledgehammerHerobrineEntity || defender instanceof ReaperHerobrineEntity) {
                        defenderLivingEntityPatch.playAnimationSynchronized(AnimsAgony.AGONY_GUARD_HIT_1, 0.0F);
                    }
                    if (defender instanceof SwordsmanHerobrineEntity) {
                        defenderLivingEntityPatch.playAnimationSynchronized(AnimsSolar.SOLAR_GUARD_HIT, 0.0F);
                    }
                }
            }
        }
    }

    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(LivingAttackEvent livingAttackEvent,
                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
                                                   AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (defenderDynamicAnimation.get() instanceof BowAttackAnimation) {
            cir.setReturnValue(false);
            return;
        }

        if (defender instanceof LivingEntity livingDefender
                && CommonUtil.isHookSword(livingDefender.getMainHandItem())
                && CommonUtil.isHookSwordClashAnimation(defenderDynamicAnimation)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "customPostAdditionClashBlade", at = @At("HEAD"))
    private static void moreLogicAfterClashing(LivingAttackEvent livingAttackEvent,
                                                  LivingEntityPatch<?> defenderLivingEntityPatch,
                                                  AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                  EntityState defenderEntityState, Entity attacker,
                                                  Entity defender, int clashBy,
                                                  CallbackInfo ci) {
        if (!(defender.level() instanceof ServerLevel serverLevel)) return;

        // Hook Sword
        if (attacker instanceof LivingEntity livingAttacker && defender instanceof LivingEntity defenderLivingDefender) {
            if (defenderLivingDefender.getMainHandItem().getItem() instanceof HookedIronSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof HookedGoldenSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof HookedDiamondSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof FlankerHookedSwordItem) {
                if (defenderDynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO1) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AnimsPugilistSteve.KNOCKDOWN_RIGHT,
                            HookDisarmLaunch.RIGHT
                    );
                }

                if (defenderDynamicAnimation == AnimsEpicFight.HOOK_AXE_AUTO2) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AnimsPugilistSteve.KNOCKDOWN_LEFT,
                            HookDisarmLaunch.LEFT
                    );
                }

                if (defenderDynamicAnimation == AnimsEpicFight.HOOK_DANCING_EDGE || defenderDynamicAnimation == AnimsWom.HOOK_HERRSCHER_UP) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AnimsPugilistSteve.GUARD_BREAK_ATTACK,
                            HookDisarmLaunch.BACKWARD
                    );
                }
            }
        }

        LivingEntityPatch<?> attackerLivingEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);

        // Clash kick post
        if (clashBy == 0) {
            if (attackerLivingEntityPatch != null) {
                AssetAccessor<? extends StaticAnimation> attackerDynamicAnimation = Objects.requireNonNull(attackerLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (attackerDynamicAnimation != null) {
                    if (defender instanceof ServerPlayer serverPlayer && EscapeUtil.isAnimationDangerous(attackerDynamicAnimation) && CommonUtil.isAvDamageableEfnWeaponsMob(attacker)) {
                        boolean damaged = false;
                        int breakValue = AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get();
                        if (ModList.get().isLoaded("efn")) {
                            if (EpicFightNightFall.isEfnWeapons(serverPlayer.getMainHandItem())) {
                                breakValue = AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get() * EpicFightNightFall.MULTIPLIER_DAMAGE_VALUE;
                            }
                        }
                        if ((serverPlayer.getOffhandItem().getItem() instanceof SwordItem || serverPlayer.getOffhandItem().getItem() instanceof AxeItem) && (new Random()).nextBoolean()) {
                            damaged = true;
                            serverPlayer.getOffhandItem().hurtAndBreak(breakValue, serverPlayer, (player) -> player.broadcastBreakEvent(InteractionHand.OFF_HAND));
                        }

                        if (!damaged) {
                            serverPlayer.getMainHandItem().hurtAndBreak(breakValue, serverPlayer, (player) -> player.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        }
                    }
                    if (ModList.get().isLoaded("efkick")) {
                        EfKick.tryDealKickStaminaDamage(
                                livingAttackEvent.getSource(),
                                attackerLivingEntityPatch,
                                attackerDynamicAnimation
                        );
                    }
                }
            }
        }

    }
}
