package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.item.DNAxHookedSwordItem;
import com.pla.annoyingvillagers.item.DiamondBlasterSwordItem;
import com.pla.annoyingvillagers.item.DiamondClawItem;
import com.pla.annoyingvillagers.potion.ObedienceMobEffect;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@Mod.EventBusSubscriber
public class AnimationDamageEvent {
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent livingHurtEvent) {
        Entity attacker = livingHurtEvent.getSource().getEntity();
        Entity victim = livingHurtEvent.getEntity();

        if (attacker != null && attacker.isAlive() && attacker instanceof LivingEntity livingAttacker && attacker.level() instanceof ServerLevel serverLevel) {
            LivingEntityPatch<?> livingAttackerPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);
            LivingEntityPatch<?> livingVictimPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
            if (livingAttackerPatch != null) {
                AssetAccessor<? extends StaticAnimation> attackerDynamicAnimation = Objects.requireNonNull(livingAttackerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (livingAttacker.getMainHandItem().getItem() instanceof DiamondBlasterSwordItem
                        && attackerDynamicAnimation == AVAnimations.DIAMOND_BLASTER_SKILL
                        && victim instanceof LivingEntity livingVictim) {
                    if (livingVictimPatch != null) {
                        AssetAccessor<? extends StaticAnimation> victimDynamicAnimation = Objects.requireNonNull(livingVictimPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                        if (!EpicfightUtil.isLongHitAnimation(victimDynamicAnimation, livingVictimPatch)) {
                            livingVictimPatch.playAnimationSynchronized(AnimsPugilistSteve.LONGEST_HIT, 0.0F);
                        }
                    }
                    CommonUtil.pushEntityFromCaster(livingVictim, livingAttacker);
                }

                if (livingAttacker.getMainHandItem().getItem() instanceof DNAxHookedSwordItem
                        && victim instanceof Mob mob) {
                    if (attackerDynamicAnimation == AnimsEpicFight.DNAX_HOOK_SWEEPING_EDGE) {
                        ObedienceMobEffect.applyObedience(mob, livingAttacker, 20 * 5);
                    } else if (attackerDynamicAnimation == AnimsEpicFight.DNAX_HOOK_DANCING_EDGE) {
                        if (livingAttacker.getOffhandItem().getItem() instanceof DNAxHookedSwordItem) {
                            ObedienceMobEffect.applyObedience(mob, livingAttacker, 20 * 10);
                        } else {
                            ObedienceMobEffect.applyObedience(mob, livingAttacker, 20 * 5);
                        }
                    }
                }

                if (livingAttacker.getMainHandItem().getItem() instanceof DiamondClawItem) {
                    if (attackerDynamicAnimation == Animations.FIST_AIR_SLASH && victim instanceof LivingEntity livingVictim) {
                        if (!livingVictim.level().isClientSide() && !livingVictim.getActiveEffects().isEmpty()) {
                            livingVictim.removeAllEffects();
                        }
                    }
                }
            }
        }
    }
}
