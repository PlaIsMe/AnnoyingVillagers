//package com.pla.annoyingvillagers.gameasset;
//
//import com.hm.efn.gameasset.EFNAnimations;
//import com.hm.efn.gameasset.animations.EFNGreatSwordAnimations;
//import com.hm.efn.registries.EFNMobEffectRegistry;
//import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
//import com.merlin204.avalon.util.AvalonAnimationUtils;
//import com.merlin204.avalon.util.AvalonEventUtils;
//import com.pla.annoyingvillagers.AnnoyingVillagers;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.particles.ParticleTypes;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.BushBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.fml.common.Mod;
//import org.joml.Math;
//import reascer.wom.animation.WomAnimationProperty;
//import reascer.wom.gameasset.WOMAnimations;
//import reascer.wom.gameasset.WOMSkills;
//import reascer.wom.gameasset.WOMSounds;
//import reascer.wom.gameasset.animations.weapons.AnimsSolar;
//import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
//import reascer.wom.animation.attacks.SpecialAttackAnimation;
//import reascer.wom.animation.attacks.UltimateAttackAnimation;
//import reascer.wom.gameasset.colliders.WOMWeaponColliders;
//import reascer.wom.particle.WOMParticles;
//import reascer.wom.skill.WOMSkillDataKeys;
//import yesman.epicfight.api.animation.AnimationManager;
//import yesman.epicfight.api.animation.property.AnimationEvent;
//import yesman.epicfight.api.animation.property.AnimationProperty;
//import yesman.epicfight.api.animation.types.BasicAttackAnimation;
//import yesman.epicfight.api.animation.types.AttackAnimation;
//import yesman.epicfight.api.animation.types.EntityState;
//import yesman.epicfight.api.collider.Collider;
//import yesman.epicfight.api.utils.TimePairList;
//import yesman.epicfight.api.utils.math.OpenMatrix4f;
//import yesman.epicfight.api.utils.math.Vec3f;
//import yesman.epicfight.gameasset.Armatures;
//import yesman.epicfight.gameasset.EpicFightSounds;
//import yesman.epicfight.model.armature.HumanoidArmature;
//import yesman.epicfight.particle.EpicFightParticles;
//import yesman.epicfight.skill.SkillDataKey;
//import yesman.epicfight.skill.SkillSlots;
//import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
//import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
//import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
//import yesman.epicfight.world.damagesource.ExtraDamageInstance;
//import yesman.epicfight.world.damagesource.StunType;
//import yesman.epicfight.api.utils.math.ValueModifier;
//
//import java.util.Random;
//import java.util.Set;
//
//@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class AnimsLegendarySword {
//    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> NG_GREATSWORD_AUTO1;
//    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> NG_GREATSWORD_AUTO2;
//    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> NG_GREATSWORD_AUTO3;
//    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> TORMENT_CHARGED_ATTACK_2;
//    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> NG_GREATSWORD_CHARG1MAX_GP;
//    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> NAPOLEON_AUSTERLITZ_SHOOT;
//    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> NAPOLEON_WATERLOW_SHOOT;
//
////    replace touch the sword
//    public static AnimationManager.AnimationAccessor<UltimateAttackAnimation> SOLAR_BRASERO_OBSCURIDAD;
//
//    public static void build(AnimationManager.AnimationBuilder builder) {
//        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
//        NG_GREATSWORD_AUTO1 = builder.nextAccessor("biped/ng_greatsword/ng_great_auto1", (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.1F, 0.6F, 0.8F, 0.9F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(5.0F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_RUIN));
//        NG_GREATSWORD_AUTO2 = builder.nextAccessor("biped/ng_greatsword/ng_great_auto2", (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.1F, 0.5F, 0.83F, 0.93F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(5.0F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_RUIN));
//        LEGENDARY_SWORD_AUTO_4 = builder.nextAccessor("biped/pugilist_steve/legendary_sword_auto_4",
//                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
//                        new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F), 1)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F), 1)
//                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
//                        .addEvents(
//                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
//                                    if (!livingEntityPatch.isLogicalClient()) {
//                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
//                                    }
//                                }, AnimationEvent.Side.SERVER)
//                        ));
//        TORMENT_CHARGED_ATTACK_2 = builder.nextAccessor("biped/combat/torment_charged_attack_2", (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 1.0F, (Collider)null, ((HumanoidArmature)biped.get()).toolR, accessor, biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, (Object)null).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.15F, 0.65F})).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));
//        NG_GREATSWORD_CHARG1MAX_GP = builder.nextAccessor("biped/ng_greatsword/ng_great_charge1max_gp", (accessor) -> (AvalonAttackAnimation)(new AvalonAttackAnimation(0.1F, accessor, Armatures.BIPED, 0.5F, 2.0F, new AvalonAttackAnimation.AvalonPhase[]{AvalonAnimationUtils.createSimplePhase(7, 15, 40, InteractionHand.MAIN_HAND, 2.0F, 2.0F, ((HumanoidArmature)Armatures.BIPED.get()).toolR, GREATSWORD_AIRSLASH_SECOND)})).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLADE_RUSH_FINISHER.get()).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create(new float[0]))).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_RUIN).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, self, params) -> ((LivingEntity)entitypatch.getOriginal()).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2, false, false, false)), AnimationEvent.Side.BOTH), AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, self, params) -> ((LivingEntity)entitypatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect) EFNMobEffectRegistry.SIN_STUN_IMMUNITY.get(), 30, 10, false, false, false)), AnimationEvent.Side.BOTH), AvalonEventUtils.simpleCameraShake(7, 20, 2.0F, 2.0F, 2.0F), AvalonEventUtils.simpleGroundSplit(7, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F, 2.5F, true)}));
//        NAPOLEON_AUSTERLITZ_SHOOT = builder.nextAccessor("biped/wom_clone/yellow_napoleon_austerlitz_shoot",
//                accessor -> new SpecialAttackAnimation(0.05F, accessor, humanoidArmature,
//                        new AttackAnimation.Phase(0.0F, 0.15F, 0.4F, 0.41F, 0.41F, humanoidArmature.get().toolR, null),
//                        new AttackAnimation.Phase(0.41F, 0.85F, 1.05F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
//                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
//                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
//                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
//                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
//                        .addEvents(new AnimationEvent[]{
//                                AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, self, params) -> {
//                                    Level level = entityPatch.getOriginal().level();
//                                    LivingEntity entity = entityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.1F, (entityPatch, self, params) -> {
//                                    Level level = entityPatch.getOriginal().level();
//                                    LivingEntity entity = entityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.2F, (entityPatch, self, params) -> {
//                                    Level level = entityPatch.getOriginal().level();
//                                    LivingEntity entity = entityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.3F, (entityPatch, self, params) -> {
//                                    Level level = entityPatch.getOriginal().level();
//                                    LivingEntity entity = entityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.4F, (entityPatch, self, params) -> {
//                                    Level level = entityPatch.getOriginal().level();
//                                    LivingEntity entity = entityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT)
//                        })
//        );
//        NAPOLEON_WATERLOW_SHOOT = builder.nextAccessor("biped/wom_clone/clone_napoleon_waterlow_shoot",
//                accessor -> new SpecialAttackAnimation(0.1F, accessor, humanoidArmature,
//                        new AttackAnimation.Phase(0.0F, 0.1F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().toolR, null),
//                        new AttackAnimation.Phase(0.35F, 0.8F, 0.9F, 0.94F, 0.94F, humanoidArmature.get().toolR, null),
//                        new AttackAnimation.Phase(0.94F, 0.95F, 1.1F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.NAPOLEON_WATERLOW_SHOOT))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
//                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F), 1)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.58F), 2)
//                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
//                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
//                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
//                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
//                        .addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL, true)
//                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.8F))
//                        .addProperty(WomAnimationProperty.CAN_SPAM, true)
//                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
//                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,
//                                (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
//                                    if (elapsedTime > 0.8F && elapsedTime < 0.9F) {
//                                        float dpx = (float) livingEntityPatch.getOriginal().getX();
//                                        float dpy = (float) livingEntityPatch.getOriginal().getY() - 1.0F;
//                                        float dpz = (float) livingEntityPatch.getOriginal().getZ();
//                                        BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz));
//                                        livingEntityPatch.getOriginal().setDeltaMovement(0.0F, -2.0F, 0.0F);
//                                        LivingEntity entity = livingEntityPatch.getOriginal();
//                                        if ((block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR) && dpy > -64.0F && !block.is(Blocks.WATER) && !entity.onGround()) {
//                                            return (elapsedTime - 0.8F) / 0.1F;
//                                        }
//                                        return 2.0F;
//                                    }
//
//                                    return 1.0F;
//                                })
//                        .addEvents(
//                                AnimationEvent.InTimeEvent.create(0.15F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.25F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.75F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.85F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(0.95F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InTimeEvent.create(1.05F, (livingEntityPatch, self, params) -> {
//                                    Level level = livingEntityPatch.getOriginal().level();
//                                    LivingEntity entity = livingEntityPatch.getOriginal();
//                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
//                                }, AnimationEvent.Side.CLIENT),
//                                AnimationEvent.InPeriodEvent.create(0.0F, 1.05F, (livingEntityPatch, self, params) -> {
//                                    livingEntityPatch.getOriginal().resetFallDistance();
//                                    Entity livingEntity = livingEntityPatch.getOriginal();
//                                    if (livingEntity instanceof Player player) {
//                                        player.yCloak = 0.0F;
//                                        player.yCloakO = 0.0F;
//                                    }
//                                }, AnimationEvent.Side.BOTH))
//                        .addEvents(
//                                AnimationEvent.InTimeEvent.create(0.9F, reascer.wom.gameasset.ReuseableEvents.BODY_BIG_GROUNDSLAM, AnimationEvent.Side.CLIENT))
//                        .addEvents(
//                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
//                                    if (!livingEntityPatch.isLogicalClient()) {
//                                        livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F);
//                                    }
//                                }, AnimationEvent.Side.SERVER)
//                        )
//                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true)
//                        .newTimePair(0.0F, 0.35F).addState(EntityState.CAN_SKILL_EXECUTION, false)
//                        .newTimePair(0.55F, 1.1F).addState(EntityState.CAN_SKILL_EXECUTION, false));
//        SOLAR_BRASERO_OBSCURIDAD = builder.nextAccessor("biped/skill/solar_brasero_obscuridad", (accessor) -> (UltimateAttackAnimation)(new UltimateAttackAnimation(0.2F, accessor, biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.55F, 0.65F, 0.75F, Float.MAX_VALUE, ((HumanoidArmature)biped.get()).rootJoint, WOMWeaponColliders.SOLAR_INFIERNO)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.SOLAR_POLVORA_HIT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) WOMSounds.SOLAR_HIT.get()).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.55F, (entitypatch, self, params) -> {
//            if (entitypatch instanceof ServerPlayerPatch spp) {
//                if (((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getSkill() == WOMSkills.SOLAR_ARCANO) {
//                    ((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setDataSync((SkillDataKey) WOMSkillDataKeys.TIMED_ATTACK.get(), 0);
//                    ((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().setDataSync((SkillDataKey)WOMSkillDataKeys.SOLAR_OBSCURIDAD.get(), true);
//                }
//
//                if (((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_PASSIVE).getSkill() == WOMSkills.SOLAR_PASSIVE) {
//                    ((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync((SkillDataKey)WOMSkillDataKeys.STORED_HEAT_LEVEL.get(), 0.0F);
//                    ((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync((SkillDataKey)WOMSkillDataKeys.CYCLE.get(), 0);
//                    ((PlayerPatch)entitypatch).getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync((SkillDataKey)WOMSkillDataKeys.TIMER.get(), 0);
//                }
//
//                ((ServerPlayerPatch)entitypatch).modifyLivingMotionByCurrentItem(false);
//                ((LivingEntity)entitypatch.getOriginal()).level().playSound((Player)null, entitypatch.getOriginal(), (SoundEvent)WOMSounds.SOLAR_HIT.get(), SoundSource.MASTER, 0.7F, 0.5F);
//            }
//
//        }, AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.55F, (entitypatch, self, params) -> {
//            OpenMatrix4f transformMatrix = entitypatch.getArmature().getBoundTransformFor(entitypatch.getAnimator().getPose(0.0F), ((HumanoidArmature)Armatures.BIPED.get()).toolR);
//            transformMatrix.translate(new Vec3f(-0.2F, 0.0F, 0.4F));
//            OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-org.joml.Math.toRadians(((LivingEntity)entitypatch.getOriginal()).yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
//            int n = 70;
//            double r = 0.1;
//
//            for(int i = 0; i < n; ++i) {
//                double theta = (java.lang.Math.PI * 2D) * (new Random()).nextDouble();
//                double phi = org.joml.Math.acos((double)2.0F * (new Random()).nextDouble() - (double)1.0F);
//                double x = r * org.joml.Math.sin(phi) * org.joml.Math.cos(theta);
//                double y = r * org.joml.Math.sin(phi) * org.joml.Math.sin(theta);
//                double z = r * org.joml.Math.cos(phi);
//                ((LivingEntity)entitypatch.getOriginal()).level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, (double)transformMatrix.m30 + ((LivingEntity)entitypatch.getOriginal()).getX(), (double)transformMatrix.m31 + ((LivingEntity)entitypatch.getOriginal()).getY() + (double)((new Random()).nextFloat() * 2.9F), (double)transformMatrix.m32 + ((LivingEntity)entitypatch.getOriginal()).getZ(), (double)((float)x), (double)((float)y), (double)((float)z));
//                if (i % 2 == 0) {
//                    ((LivingEntity)entitypatch.getOriginal()).level().addParticle(ParticleTypes.LAVA, (double)transformMatrix.m30 + ((LivingEntity)entitypatch.getOriginal()).getX(), (double)transformMatrix.m31 + ((LivingEntity)entitypatch.getOriginal()).getY() + (double)((new Random()).nextFloat() * 2.9F), (double)transformMatrix.m32 + ((LivingEntity)entitypatch.getOriginal()).getZ(), (double)((float)x), (double)((float)y), (double)((float)z));
//                }
//            }
//
//            transformMatrix = entitypatch.getArmature().getBoundTransformFor(entitypatch.getAnimator().getPose(0.0F), ((HumanoidArmature)Armatures.BIPED.get()).toolR);
//            OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-org.joml.Math.toRadians(((LivingEntity)entitypatch.getOriginal()).yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
//            transformMatrix.translate(new Vec3f(0.0F, 0.0F, -0.3F));
//            n = 80;
//            r = 0.4;
//            double t = 0.01;
//
//            for(int i = 0; i < n; ++i) {
//                double theta = (java.lang.Math.PI * 2D) * (new Random()).nextDouble();
//                double phi = ((new Random()).nextDouble() - (double)0.5F) * java.lang.Math.PI * t / r;
//                double x = r * org.joml.Math.cos(phi) * org.joml.Math.cos(theta);
//                double y = r * org.joml.Math.cos(phi) * org.joml.Math.sin(theta);
//                double z = r * org.joml.Math.sin(phi);
//                Vec3f direction = new Vec3f((float)x, (float)y, (float)z);
//                OpenMatrix4f rotation = (new OpenMatrix4f()).rotate(org.joml.Math.toRadians(-((LivingEntity)entitypatch.getOriginal()).yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
//                rotation.rotate(org.joml.Math.toRadians(110.0F), new Vec3f(1.0F, 0.0F, 0.0F));
//                OpenMatrix4f.transform3v(rotation, direction, direction);
//                ((LivingEntity)entitypatch.getOriginal()).level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, (double)transformMatrix.m30 + ((LivingEntity)entitypatch.getOriginal()).getX(), (double)transformMatrix.m31 + ((LivingEntity)entitypatch.getOriginal()).getY(), (double)transformMatrix.m32 + ((LivingEntity)entitypatch.getOriginal()).getZ(), (double)direction.x, (double)direction.y, (double)direction.z);
//            }
//
//            for(int i = 0; i < n; ++i) {
//                double theta = (java.lang.Math.PI * 2D) * (new Random()).nextDouble();
//                double phi = ((new Random()).nextDouble() - (double)0.5F) * java.lang.Math.PI * t / r;
//                double x = r * org.joml.Math.cos(phi) * org.joml.Math.cos(theta);
//                double y = r * org.joml.Math.cos(phi) * org.joml.Math.sin(theta);
//                double z = r * org.joml.Math.sin(phi);
//                Vec3f direction = new Vec3f((float)x, (float)y, (float)z);
//                OpenMatrix4f rotation = (new OpenMatrix4f()).rotate(org.joml.Math.toRadians(-((LivingEntity)entitypatch.getOriginal()).yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
//                rotation.rotate(Math.toRadians(70.0F), new Vec3f(1.0F, 0.0F, 0.0F));
//                OpenMatrix4f.transform3v(rotation, direction, direction);
//                ((LivingEntity)entitypatch.getOriginal()).level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, (double)transformMatrix.m30 + ((LivingEntity)entitypatch.getOriginal()).getX(), (double)transformMatrix.m31 + ((LivingEntity)entitypatch.getOriginal()).getY(), (double)transformMatrix.m32 + ((LivingEntity)entitypatch.getOriginal()).getZ(), (double)direction.x, (double)direction.y, (double)direction.z);
//            }
//
//        }, AnimationEvent.Side.CLIENT)}));
//    }
//}
