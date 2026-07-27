package com.pla.annoyingvillagers.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.util.CEPatchUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import reascer.wom.world.entity.mob.EnderHand;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

@Mixin(value = EnderHand.class, remap = true)
public abstract class EnderHandMixin {
    @Inject(method = "customServerAiStep", at = @At("TAIL"), cancellable = true)
    private void makeEnderHandCanDamagePlayer(CallbackInfo ci) {
        EnderHand self = (EnderHand) (Object)this;
        if (self.tickCount == 20 && self.getOwnerUUID() != null
                && self.level() instanceof ServerLevel serverLevel
                && serverLevel.getEntity(self.getOwnerUUID()) instanceof LivingEntity owner
                && !(owner instanceof Player)
                && self.getTarget() != null && self.getTarget().isAlive()) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(owner, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                LivingEntity entity = self.getTarget();
                EpicFightDamageSource damageSource = new EpicFightDamageSource(livingEntityPatch.getDamageSource(AnimsRuine.RUINE_PLUNDER, InteractionHand.MAIN_HAND));
                damageSource.setBaseImpact(4.0F);
                damageSource.setStunType(StunType.HOLD);
                damageSource.attachDamageModifier(ValueModifier.multiplier(2.6F));
                damageSource.addExtraDamage(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(0.8F));
                entity.hurt(damageSource, (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE));
                if (entity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                    entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (12 + 4 * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, livingEntity)) * 20, 0, false, true));
                } else {
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (9 + 3 * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, livingEntity)) * 20, 0, false, true));
                }

                if (entity.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                    entity.removeEffect(MobEffects.DIG_SLOWDOWN);
                    entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, (12 + 4 * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, livingEntity)) * 20, 0, false, true));
                } else {
                    entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, (9 + 3 * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, livingEntity)) * 20, 0, false, true));
                }

                if (livingEntityPatch instanceof CEHumanoidPatch<?> ceHumanoidPatch) {
                    CEPatchUtils.setStamina(ceHumanoidPatch, CEPatchUtils.getStamina(ceHumanoidPatch) + CEPatchUtils.getMaxStamina(ceHumanoidPatch) * 0.05F);
                }
                (livingEntityPatch.getOriginal()).heal((float)(1 + EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, livingEntity)));
                serverLevel.sendParticles(WOMParticles.ENDERBLASTER_BULLET.get(), entity.getX(), entity.getY() + (double)1.2F, entity.getZ(), 1, (double)0.0F, 0.0F, 0.0F, 0.0F);
                serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_EYE_DEATH, livingEntity.getSoundSource(), 1.0F, 0.5F);
            }
        }
    }
}