package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;
import java.util.Random;

@EventBusSubscriber
public class KnockdownEvent {
    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent livingHurtEvent) {
        if (livingHurtEvent != null && livingHurtEvent.getEntity() != null) {
            if (livingHurtEvent.getEntity().hasEffect(AnnoyingVillagersModMobEffects.GROUND_STUCK.get())) return;
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(livingHurtEvent.getEntity(), LivingEntityPatch.class);
            if (livingEntityPatch != null && livingHurtEvent.getEntity().level() instanceof ServerLevel) {
                AnimationPlayer animationPlayer = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null));
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
                if (dynamicAnimation == Animations.BIPED_HIT_LONG) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.HIT_BACKWARD, 0.0F);
                } else if (dynamicAnimation == AnimsPugilistSteve.HIT_BACKWARD) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.HIT_LEFT, 0.0F);
                } else if (dynamicAnimation == AnimsPugilistSteve.HIT_LEFT) {
                    livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.HIT_RIGHT, 0.0F);
                } else if (dynamicAnimation == AnimsPugilistSteve.HIT_RIGHT) {
                    float chance = new Random().nextFloat();
                    if (chance <= 0.25F) {
                        livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.KNOCKDOWN_LEFT, 0.0F);
                    } else if (chance <= 0.5F) {
                        livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.KNOCKDOWN_RIGHT, 0.0F);
                    } else if (chance <= 0.75F) {
                        livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.KNOCKDOWN_FORWARD, 0.0F);
                    } else {
                        livingEntityPatch.applyStun(StunType.KNOCKDOWN, 40.0F);
                    }
                }
            }
        }
    }
}
