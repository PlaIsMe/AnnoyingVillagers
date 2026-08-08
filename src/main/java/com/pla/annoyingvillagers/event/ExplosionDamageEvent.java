package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.entity.TridentLightningBolt;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import com.pla.annoyingvillagers.skill.WoopieTheSwordSkill;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.world.entity.mob.EnderHand;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Objects;

@Mod.EventBusSubscriber
public class ExplosionDamageEvent {
    @SubscribeEvent
    public static void onExplode(ExplosionEvent.Detonate detonate) {
        if (detonate.getExplosion().getDirectSourceEntity() instanceof TridentLightningBolt) {
            detonate.getAffectedEntities().removeIf(entity -> entity instanceof ItemEntity);
        }

        LivingEntity livingEntity = detonate.getExplosion().getIndirectSourceEntity();
        final Vec3 center = detonate.getExplosion().getPosition();

        if (livingEntity != null && livingEntity.isAlive() && livingEntity.level() instanceof ServerLevel serverLevel) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
            if (livingEntityPatch == null) return;
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (livingEntity.getMainHandItem().getItem() instanceof WoopieTheSwordItem && dynamicAnimation == AnimsHerrscher.HERRSCHER_AUTO_2) {
                SkillContainer skillContainer = null;
                if (livingEntityPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                    skillContainer = serverPlayerPatch.getSkill(AVSkills.WOOPIE_THE_SWORD);
                }
                for (Entity entity : detonate.getAffectedEntities()) {
                    if (entity.isAlive() && entity != detonate.getExplosion().getIndirectSourceEntity()
                            && entity instanceof LivingEntity livingExploded && !(entity instanceof EnderHand)
                            && !(entity instanceof Player player && player.isCreative())) {
                        LivingEntityPatch<?> explodedPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (explodedPatch != null) {
                            AssetAccessor<? extends StaticAnimation> explodedDynamicAnimation = Objects.requireNonNull(explodedPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                            if (!EpicfightUtil.isLongHitAnimation(explodedDynamicAnimation, explodedPatch)) {
                                explodedPatch.playAnimationSynchronized(AnimsPugilistSteve.LONGEST_HIT, 0.0F);
                            }
                        }
                        if (!entity.isAlive()) continue;
                        double dx = center.x - entity.getX();
                        double dz = center.z - entity.getZ();
                        double dist = entity.position().distanceTo(center);
                        double falloff = Mth.clamp(1.0D - (dist / 8.0D), 0.0D, 1.0D);

                        double horizontal = 6.0D * falloff;
                        double up = 2.6D * falloff;

                        livingExploded.knockback(horizontal, dx, dz);
                        livingExploded.push(0.0D, up, 0.0D);
                        livingExploded.hurtMarked = true;

                        if (skillContainer != null && skillContainer.getStack() < 1) {
                            WoopieTheSwordSkill woopieTheSwordSkill = (WoopieTheSwordSkill) skillContainer.getSkill();
                            float currentResource = skillContainer.getResource();
                            float neededResource = skillContainer.getNeededResource();
                            float addResource = Math.min(50.0F, neededResource);
                            woopieTheSwordSkill.setConsumptionSynchronize(skillContainer, currentResource + addResource);
                        }
                    }
                }
            }

            if (livingEntity.getOffhandItem().getItem() instanceof WoopieTheSwordItem && dynamicAnimation == AVAnimations.LEGENDARYSWORD_WOOPIE_FLY) {
                for (Entity entity : detonate.getAffectedEntities()) {
                    if (entity.isAlive() && entity != detonate.getExplosion().getIndirectSourceEntity()
                            && entity instanceof LivingEntity livingExploded && !(entity instanceof EnderHand)
                            && !(entity instanceof Player player && player.isCreative())) {
                        LivingEntityPatch<?> explodedPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (explodedPatch != null) {
                            AssetAccessor<? extends StaticAnimation> explodedDynamicAnimation = Objects.requireNonNull(explodedPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                            if (!EpicfightUtil.isLongHitAnimation(explodedDynamicAnimation, explodedPatch)) {
                                explodedPatch.playAnimationSynchronized(AnimsPugilistSteve.LONGEST_HIT, 0.0F);
                            }
                        }
                        if (!entity.isAlive()) continue;
                        double dx = center.x - entity.getX();
                        double dz = center.z - entity.getZ();
                        double dist = entity.position().distanceTo(center);
                        double falloff = Mth.clamp(1.0D - (dist / 8.0D), 0.0D, 1.0D);

                        double horizontal = 6.0D * falloff;
                        double up = 2.6D * falloff;

                        livingExploded.knockback(horizontal, dx, dz);
                        livingExploded.push(0.0D, up, 0.0D);
                        livingExploded.hurtMarked = true;
                    }
                }
            }
        }
    }
}
