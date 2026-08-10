package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.SwordsmanHerobrineEntity;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.util.HerobrinePortalCombatUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.joml.Math;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.ReuseableEvents;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsDemoniacVoltageReaver {
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_VOLTAGE_REAVER_SPECIAL;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DEMONIAC_VOLTAGE_REAVER_INNATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        DEMONIAC_VOLTAGE_REAVER_AUTO1 = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_auto1",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.15F, 0.2F, 0.6F, 0.65F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F));

        DEMONIAC_VOLTAGE_REAVER_AUTO2 = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_auto2",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.2F, 0.2F, 0.5F, 0.6F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F));

        DEMONIAC_VOLTAGE_REAVER_AUTO3 = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_auto3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, AnimationEvent.Side.CLIENT)
                        ));

        DEMONIAC_VOLTAGE_REAVER_AUTO4 = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_auto4",
                (accessor) -> (BasicMultipleAttackAnimation) (new BasicMultipleAttackAnimation(
                        0.15F, 0.25F, 0.4F, 0.5F, null,
                        humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false));

        DEMONIAC_VOLTAGE_REAVER_AUTO5 = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_auto5",
                (accessor) -> (BasicMultipleAttackAnimation) (new BasicMultipleAttackAnimation(
                        0.05F, 0.65F, 0.8F, 1.0F, null,
                        humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(
                                        0.8F,
                                        ReuseableEvents.SOLAR_GROUNDSLAM_SMALL,
                                        AnimationEvent.Side.CLIENT
                                )
                        ));

        DEMONIAC_VOLTAGE_REAVER_DASH = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_dash",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.35F, 0.6F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.45F, ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        }));

        DEMONIAC_VOLTAGE_REAVER_AIRSLASH = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_airslash",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.55F, 0.75F, WOMWeaponColliders.RUINE_COMET, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(1.2F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 20)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                                float dpx = (float) entitypatch.getOriginal().getX();
                                float dpy = (float) entitypatch.getOriginal().getY();
                                float dpz = (float) entitypatch.getOriginal().getZ();

                                for(BlockState block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) Math.max(Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                LivingEntity livingentity = entitypatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -0.25F, 0.0F);
                                OpenMatrix4f rotation = (new OpenMatrix4f()).rotate(-Math.toRadians(entitypatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                AABB box = AABB.ofSize(entitypatch.getOriginal().getPosition(1.0F), 3.0F, 3.0F, 3.0F);
                                List<Entity> list = entitypatch.getOriginal().level().getEntities(entitypatch.getOriginal(), box);
                                if (distanceToGround > 0.5F && list.isEmpty()) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.05F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.25F, ReuseableEvents.RUINE_COMET_AIRBURST, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, ReuseableEvents.RUINE_COMET_GROUNDTHRUST, AnimationEvent.Side.CLIENT)
                        }));

        DEMONIAC_VOLTAGE_REAVER_SPECIAL = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_special",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.35F, 0.4F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.SOLAR_HORNO)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NEUTRALIZE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.15F))
                        .newTimePair(0.0F, 0.9F)
                        .addStateRemoveOld(EntityState.INACTION, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime > 0.15F && elapsedTime < 0.25F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY() - 0.2F;
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();
                                BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz));
                                if ((block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR) && !livingEntityPatch.getOriginal().onGround()) {
                                    Vec3 delta = livingEntityPatch.getOriginal().getDeltaMovement();
                                    livingEntityPatch.getOriginal().setDeltaMovement(delta.x, -2.0F * ((elapsedTime - 0.15F) / 0.1F), delta.z);
                                    return 1.0F - (elapsedTime - 0.15F) / 0.1F;
                                }

                                Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(self.getAccessor())).setElapsedTimeCurrent(0.25F);
                            }
                            return speed;
                        })
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.35F, ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        }));

        DEMONIAC_VOLTAGE_REAVER_INNATE = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_innate",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    ItemStack stack = livingEntityPatch.getOriginal().getMainHandItem();
                                    DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, livingEntityPatch.getOriginal(), false);
                                }, AnimationEvent.Side.SERVER)
                        ));
        
        DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL = builder.nextAccessor("biped/demoniac_voltage_reaver/demoniac_voltage_reaver_innate_special",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal() instanceof SwordsmanHerobrineEntity swordsmanHerobrineEntity
                                            && ((swordsmanHerobrineEntity.getGregUUID() != null
                                            && HerobrinePortalCombatUtil.hasNearbyPortalGroup(swordsmanHerobrineEntity, swordsmanHerobrineEntity.getGregUUID(), 6, 48.0D))
                                            || HerobrinePortalCombatUtil.hasNearbyPortalGroup(swordsmanHerobrineEntity, null, 6, 48.0D))) {
                                        return;
                                    }
                                    ItemStack stack = livingEntityPatch.getOriginal().getMainHandItem();
                                    DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, livingEntityPatch.getOriginal(), true);
                                }, AnimationEvent.Side.SERVER)
                        ));
    }
}