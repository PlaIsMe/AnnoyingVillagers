package com.pla.annoyingvillagers.gameasset;

import com.hm.efn.gameasset.EFNAnimations;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.util.AvalonAnimationUtils;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.ObsidianSledgehammerItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.ReuseableEvents;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.EpicFightSounds;
import reascer.wom.particle.WOMParticles;

import java.util.Random;
import java.util.Set;

import static com.hm.efn.gameasset.animations.EFNGreatSwordAnimations.AIRSLASH;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsObsidianSledgehammer {
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_SLEDGEHAMMER_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_SLEDGEHAMMER_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_SLEDGEHAMMER_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_SLEDGEHAMMER_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_SLEDGEHAMMER_AUTO5;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> OBSIDIAN_SLEDGEHAMMER_DASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_SLEDGEHAMMER_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_SLEDGEHAMMER_SPECIAL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_SLEDGEHAMMER_INNATE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> OBSIDIAN_SLEDGEHAMMER_INNATE_SPECIAL;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        OBSIDIAN_SLEDGEHAMMER_AUTO1 = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, java.util.Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F));

        OBSIDIAN_SLEDGEHAMMER_AUTO2 = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_auto2",
                accessor -> new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, java.util.Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F));

        OBSIDIAN_SLEDGEHAMMER_AUTO3 = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_auto3",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.7F, 0.8F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.SOLAR_OBSCURIDAD_DINAMITA)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.4F))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.75F, (entitypatch, self, params) -> {
                                    Vec3 position = entitypatch.getOriginal().position();
                                    OpenMatrix4f modelTransform = entitypatch.getArmature().getBoundTransformFor(entitypatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().rootJoint).mulFront(OpenMatrix4f.createTranslation((float)position.x, (float)position.y, (float)position.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(entitypatch.getModelMatrix(1.0F))));
                                    Vec3 weaponEdge = OpenMatrix4f.transform(modelTransform, (new Vec3f(-1.0F, 0.0F, -1.0F)).toDoubleVector());
                                    Level level = entitypatch.getOriginal().level();
                                    Vec3 floorPos = ReuseableEvents.getfloor(entitypatch, self.get(), new Vec3f(0.0F, 0.0F, 0.0F), Armatures.BIPED.get().rootJoint);
                                    BlockState blockState = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(floorPos.x, floorPos.y, floorPos.z));
                                    if (entitypatch instanceof PlayerPatch) {
                                        entitypatch.getOriginal().level().playSound((Player)entitypatch.getOriginal(), entitypatch.getOriginal(), blockState.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : EpicFightSounds.SLAM_HEAVY.get(), SoundSource.PLAYERS, 1.5F, 1.5F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
                                    }

                                    weaponEdge = new Vec3(weaponEdge.x, floorPos.y, weaponEdge.z);
                                    entitypatch.getOriginal().level().addParticle(ParticleTypes.END_ROD, weaponEdge.x, (int)weaponEdge.y, weaponEdge.z, 0.0F, 0.0F, 0.0F);
                                    entitypatch.getOriginal().level().addParticle(WOMParticles.WOM_GROUND_SLAM.get(), weaponEdge.x, (int)floorPos.y + 1, weaponEdge.z, 0.7, 35.0F, 0.7);
                                    LevelUtil.circleSlamFracture(entitypatch.getOriginal(), level, weaponEdge, 1.2F, true, true);
                                }, AnimationEvent.Side.CLIENT)
                        }));

        OBSIDIAN_SLEDGEHAMMER_AUTO4 = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_auto4",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.25F, 0.15F, 0.3F, 0.3F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));

        OBSIDIAN_SLEDGEHAMMER_AUTO5 = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_auto5",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.15F, 0.3F, 0.4F, 0.4F, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.4F, 0.4F, 0.55F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.55F, ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        }));

        OBSIDIAN_SLEDGEHAMMER_DASH = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_dash",
                (accessor) -> (AvalonAttackAnimation)(new AvalonAttackAnimation(0.1F, accessor, Armatures.BIPED, 1.0F, 1.0F, AvalonAnimationUtils.createSimplePhase(33, 44, 70, InteractionHand.MAIN_HAND, 1.0F, 1.0F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_RUIN));

        OBSIDIAN_SLEDGEHAMMER_AIRSLASH = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_airslash",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED, new AttackAnimation.Phase(0.0F, 0.8F, 1.16F, 1.16F, 1.8F, Armatures.BIPED.get().toolR, AIRSLASH)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(30.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_RUIN)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.9F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, 0.0F, 0.0F), Armatures.BIPED.get().rootJoint, (double)3.0F, 0.0F),
                                AnimationEvent.InTimeEvent.create(0.1F, (entitypatch, self, params) -> entitypatch.playSound(EpicFightSounds.ENTITY_MOVE.get(), 0.0F, 0.0F), AnimationEvent.Side.CLIENT)
                        }));

        OBSIDIAN_SLEDGEHAMMER_SPECIAL = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_special",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.45F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().rootJoint, WOMWeaponColliders.TORMENT_BERSERK_DASHSLAM), new AttackAnimation.Phase(0.55F, 0.8F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().rootJoint, WOMWeaponColliders.TORMENT_BERSERK_DASHSLAM), new AttackAnimation.Phase(0.9F, 1.35F, 1.4F, 1.4F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.TORMENT_BERSERK_DASHSLAM)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get(), 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.85F, ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.4F, ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        }));

        OBSIDIAN_SLEDGEHAMMER_INNATE = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_innate",
                animationaccessor -> (AttackAnimation) new AttackAnimation(0.15F, 0.0F, 0.54F, 0.70F, 1.75F, null, Armatures.BIPED.get().toolR, animationaccessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicanimation, livingentitypatch, f, f1, f2) -> 1.0F)
                        .newTimePair(0.0F, 0.4F).addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.4F, Float.MAX_VALUE).addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 1.85F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, 1.85F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                EpicfightUtil.cameraZoomInEvent(0.05F, -0.35F, 30),
                                EpicfightUtil.cameraZoomOutBlurEvent(0.5F, 10.0F, 20),
                                AnimationEvent.InTimeEvent.create(0.6F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT)
                                        .params(new Vec3f(0.0F, -0.24F, -2.0F),
                                                Armatures.BIPED.get().toolR, 2.5D, 0.6F)));

        OBSIDIAN_SLEDGEHAMMER_INNATE_SPECIAL = builder.nextAccessor("biped/obsidian_sledgehammer/obsidian_sledgehammer_innate_special", animationaccessor -> (ActionAnimation) new ActionAnimation(0.1F, animationaccessor, Armatures.BIPED)
                .newTimePair(0.0F, 4.5F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                .newTimePair(0.0F, 4.5F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .addEvents(
                        EpicfightUtil.cameraZoomInEvent(0.05F, -0.35F, 30),
                        EpicfightUtil.cameraZoomOutBlurEvent(1.5F, 10.0F, 20),
                        AnimationEvent.InTimeEvent.create(1.5F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(AnnoyingVillagersModSounds.SLEDGE_HAMMER.get()),
                        AnimationEvent.InTimeEvent.create(1.7F, (livingEntityPatch, self, params) -> ObsidianSledgehammerItem.spawnWave(livingEntityPatch.getOriginal(), livingEntityPatch.getYRot(), 0.0F, 4.0F, 18), AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent.create(1.8F, (livingEntityPatch, self, params) -> ObsidianSledgehammerItem.spawnWave(livingEntityPatch.getOriginal(), livingEntityPatch.getYRot(), 4.0F, 8.0F, 24), AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent.create(1.9F, (livingEntityPatch, self, params) -> ObsidianSledgehammerItem.spawnWave(livingEntityPatch.getOriginal(), livingEntityPatch.getYRot(), 8.0F, 12.0F, 30), AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, params) -> ObsidianSledgehammerItem.spawnWave(livingEntityPatch.getOriginal(), livingEntityPatch.getYRot(), 12.0F, 16.0F, 36), AnimationEvent.Side.SERVER)
                ));
    }
}