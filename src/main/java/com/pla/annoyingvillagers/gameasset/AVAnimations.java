package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.clazz.TridentMode;
import com.pla.annoyingvillagers.compat.p1nero_bow.AnimsP1neroEpicBow;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.utils.HitEntityList.Priority;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AVAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> ELITE_HOLD_WEAPON;
    public static AnimationManager.AnimationAccessor<MovementAnimation> ELITE_WALK_WEAPON;
    public static AnimationManager.AnimationAccessor<MovementAnimation> ELITE_RUN_WEAPON;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SPINNING_SPEAR_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FIST_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> POINT_LEFT_HAND_TOWARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> POINT_LEFT_HAND_MIDDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> POINT_LEFT_HAND_UP;

    public static AnimationManager.AnimationAccessor<ActionAnimation> TRIDENT_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNOCKED_ELITE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ANIMATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_HEALING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LOW_CLONE_ESCAPE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> IDLE_BREAK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLACE_BLOCK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLACK_FIRE_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BLUE_FLAME_SWORD;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DIAMOND_BLASTER_SKILL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> EARTH_AXE_SHOOT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> RED_AXE_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLACKSCRATCHER_IDLE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLACKSCRATCHER_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_LEFT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_LEFT_TOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_RIGHT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_RIGHT_TOP;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_PUSH;
    public static AnimationManager.AnimationAccessor<ActionAnimation> ELECTRIC_FIELD;
    public static AnimationManager.AnimationAccessor<AttackAnimation> EARTH_AXE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> HEROBRINE_RUN;
    public static AnimationManager.AnimationAccessor<MovementAnimation> TRIDENT_TWO_HAND_RUN;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HACKER_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> LEGENDARYSWORD_WOOPIE_FLY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> TOUCH_THE_SWORD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TRIDENT_SPIN;


    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(AnnoyingVillagers.MODID, AVAnimations::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {
        AnimsEnderGlaive.build(builder);
        AnimsEnderSlayerScythe.build(builder);
        AnimsDemoniacVoltageReaver.build(builder);
        AnimsObsidianSledgehammer.build(builder);
        AnimsNullWeapon.build(builder);
        AnimsObsidianWeapon.build(builder);

        AnimsEpicFight.build(builder);
        AnimsEpicFightACG.build(builder);
        AnimsEpicFightAwaken.build(builder);
        AnimsEpicFightBattleArts.build(builder);
        AnimsEpicFightDualGreatsword.build(builder);
        AnimsEpicFightInfernalGainer.build(builder);
        AnimsEpicFightIronSpell.build(builder);
        AnimsEpicFightSanji.build(builder);
        AnimsEpicFightValourGuard.build(builder);
        AnimsPugilistSteve.build(builder);
        AnimsSculkSteve.build(builder);
        AnimsWom.build(builder);
        AnimsYonchiChikito.build(builder);
        AnimsEpicFightGuandao.build(builder);
        AnimsTacticalImbuements.build(builder);
        if (ModList.get().isLoaded("p1nero_bow")) {
            AnimsP1neroEpicBow.build(builder);
        }

        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        ELITE_HOLD_WEAPON = builder.nextAccessor("biped/living/elite_hold_weapon",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        ELITE_RUN_WEAPON = builder.nextAccessor("biped/living/elite_run_weapon",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        ELITE_WALK_WEAPON = builder.nextAccessor("biped/living/elite_walk_weapon",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        SPINNING_SPEAR_GUARD = builder.nextAccessor("biped/living/spinning_spear_guard",
                accessor -> new StaticAnimation(0.05F, true, accessor, humanoidArmature)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.0F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.6F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.7F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT)));
        FIST_GUARD = builder.nextAccessor("biped/living/fist_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        POINT_LEFT_HAND_TOWARD = builder.nextAccessor("biped/living/point_left_hand_toward",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        POINT_LEFT_HAND_UP = builder.nextAccessor("biped/living/point_left_hand_up",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        POINT_LEFT_HAND_MIDDLE = builder.nextAccessor("biped/epicfight_ironspell/point_left_hand_middle",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));


        TRIDENT_ATTACK = builder.nextAccessor("biped/pla/trident_attack",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(4.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.summonLightningAtGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER)
                        ));
        HEROBRINE_ANIMATE = builder.nextAccessor("biped/pla/herobrine_animate",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        HEROBRINE_HEALING = builder.nextAccessor("biped/pla/herobrine_healing",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        LOW_CLONE_ESCAPE = builder.nextAccessor("biped/pla/low_clone_escape",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        KNOCKED_ELITE = builder.nextAccessor("biped/pla/knocked_elite",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_1 = builder.nextAccessor("biped/pla/eating_elite_1",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_2 = builder.nextAccessor("biped/pla/eating_elite_2",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_3 = builder.nextAccessor("biped/pla/eating_elite_3",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_4 = builder.nextAccessor("biped/pla/eating_elite_4",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        SNAKE_BLADE = builder.nextAccessor("biped/pla/snake_blade",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    ItemStack stack = livingEntityPatch.getOriginal().getMainHandItem();
                                    DemoniacVoltageReaverItem.tryStartSnakeAnimation(stack, livingEntityPatch.getOriginal(), false);
                                }, Side.SERVER)
                        ));
        SNAKE_BLADE_GUARD = builder.nextAccessor("biped/pla/snake_blade_guard",
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
                                }, Side.SERVER)
                        ));
        IDLE_BREAK = builder.nextAccessor("biped/pla/idle_break",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        PLACE_BLOCK = builder.nextAccessor("biped/pla/place_block",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature));
        BLACK_FIRE_SWORD_SKILL = builder.nextAccessor("biped/pla/black_fire_sword_skill",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.8F, (livingEntityPatch, self, p) -> BlackFireEntity.shootFromOwnerLook(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal()), Side.SERVER)
                        ));
        BLUE_FLAME_SWORD = builder.nextAccessor("biped/pla/blue_flame_sword",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false));
        DIAMOND_BLASTER_SKILL = builder.nextAccessor("biped/pla/diamond_blaster_skill",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)));
        EARTH_AXE_SHOOT = builder.nextAccessor("biped/pla/earth_axe_shoot",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                        new Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, staticAnimation, object) -> {
                                    Vec3 bladePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                            Armatures.BIPED.get().toolR, 0.5F, 0.0F);
                                    if (bladePos == null) return;
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        BlockPos liftPos = EarthAxeItem.findLiftableBlockUnderPoint(serverLevel, bladePos, 6, 1);
                                        if (liftPos != null) {
                                            EarthAxeItem.liftBlockAt(serverLevel, liftPos, livingEntity);
                                        }
                                    }
                                }, Side.SERVER)
                        )
        );
        RED_AXE_ATTACK = builder.nextAccessor("biped/pla/red_axe_attack",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.2F, 2.5F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, (humanoidArmature.get()).rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.BYPASS_DODGE))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.35F, 0.9F))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                        if (elapsedTime >= 0.9F && elapsedTime < 1.15F) {
                            float dpx = (float)(entitypatch.getOriginal()).getX();
                            float dpy = (float)(entitypatch.getOriginal()).getY();
                            float dpz = (float)(entitypatch.getOriginal()).getZ();

                            for(BlockState block = (entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                --dpy;
                            }

                            float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                            return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                        } else {
                            return speed;
                        }
                    }).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.35F, reascer.wom.gameasset.ReuseableEvents.AIRBURST_JUMP, Side.CLIENT), AnimationEvent.InTimeEvent.create(1.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, Side.CLIENT)
                    }));

        BLACKSCRATCHER_IDLE = builder.nextAccessor("biped/pla/blackscratcher_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        BLACKSCRATCHER_ATTACK = builder.nextAccessor("biped/pla/blackscratcher_attack.",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)));

        HOOK_HAND_LEFT = builder.nextAccessor("biped/pla/left_hand_hook",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        HOOK_HAND_LEFT_TOP = builder.nextAccessor("biped/pla/left_hand_hook_top",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        HOOK_HAND_RIGHT = builder.nextAccessor("biped/pla/right_hand_hook",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        HOOK_HAND_RIGHT_TOP = builder.nextAccessor("biped/pla/right_hand_hook_top",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        ENDER_AEGIS_PUSH = builder.nextAccessor("biped/pla/ender_aegis_push",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.25F, 0.29F, 0.29F,
                        humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.29F, 0.3F, 0.35F, 0.39F, 0.39F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.39F, 0.4F, 0.45F, 0.49F, 0.49F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.79F, 0.8F, 0.85F, 0.89F, 0.89F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.89F, 1.0F, 1.1F, 1.3F, Float.MAX_VALUE,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 7)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null));

        ELECTRIC_FIELD = builder.nextAccessor("biped/pla/electric_field",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.spawnDamageZones(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));
        EARTH_AXE = builder.nextAccessor("biped/pla/earth_axe",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        EarthAxeItem.summonEarthWall(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ));
        HEROBRINE_RUN = builder.nextAccessor("biped/pla/herobrine_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        TRIDENT_TWO_HAND_RUN = builder.nextAccessor("biped/pla/trident_two_hand_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        HACKER_SWORD_SKILL = builder.nextAccessor("biped/pla/hacker_sword_skill", accessor -> (BasicMultipleAttackAnimation) new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.45F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(0.55F, 0.8F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(0.9F, 1.35F, 1.4F, 1.4F, 1.4F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(1.55F, 1.8F, 1.85F, 1.9F, 1.9F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(1.9F, 2.35F, 2.4F, 2.4F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.SWORD))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.35F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.85F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.35F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.85F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT)
                })
        );

        LEGENDARYSWORD_WOOPIE_FLY = builder.nextAccessor("biped/pla/legendarysword_woopie_fly",
                accessor -> new ActionAnimation(0.05F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (animation, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .newTimePair(0.0F, 0.3F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.3F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, params) -> {
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    if (entity.level().isClientSide() || !entity.getOffhandItem().is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) return;

                                    Vec3 offHandPos = EpicfightUtil.getJointWithTranslation(entity, new Vec3f(0.0F, 0.0F, 0.0F), Armatures.BIPED.get().toolL, 0.0F, 0.0F);
                                    Vec3 windPos = offHandPos == null ? entity.position().add(0.0D, 0.05D, 0.0D) : new Vec3(offHandPos.x, entity.getY() + 0.05D, offHandPos.z);

                                    AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
                                    entity.level().explode(entity, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
                                    AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClientboundWoopieSwordWindFx(windPos));
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, self, params) -> livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.LEGENDARY_SWORD_HEAVY_ATTACK, 0.0F), AnimationEvent.Side.SERVER)
                        ));
        TOUCH_THE_SWORD = builder.nextAccessor("biped/pla/touch_the_sword",
                accessor -> new ActionAnimation(0.05F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (animation, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .newTimePair(0.0F, 0.3F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.3F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));

        TRIDENT_SPIN = builder.nextAccessor("biped/pla/trident_spin",
                accessor -> new StaticAnimation(0.1F, false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.25F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT)));
    }

    static class ReuseableEvents {
        public static final AnimationEvent.E0 FAST_SPINNING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 1.1F - (new Random().nextFloat() - 0.5F) * 0.2F);
        public static final AnimationEvent.E0 TRIDENT_SPINNING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), SoundEvents.TRIDENT_RETURN, SoundSource.NEUTRAL, 0.5F, 1.1F - (new Random().nextFloat() - 0.5F) * 0.2F);
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handL, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                            );
                            if (jointVec == null) return;

                            BlueDemonUtil.spawnBlueDemonEffect(serverLevel, livingEntity, jointVec, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                            float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                            float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                            serverLevel.playSound(
                                    null,
                                    BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                    AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                    SoundSource.NEUTRAL,
                                    volume,
                                    pitch
                            );
                        }
                    }
                };
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_WEAPON_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().toolR, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                            );
                            if (jointVec == null) return;

                            BlueDemonUtil.spawnBlueDemonEffect(serverLevel, livingEntity, jointVec, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                            float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                            float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                            serverLevel.playSound(
                                    null,
                                    BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                    AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                    SoundSource.NEUTRAL,
                                    volume,
                                    pitch
                            );
                        }
                    }
                };
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        Item weapon = livingEntity.getMainHandItem().getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                            );
                            if (jointVec == null) return;

                            BlueDemonUtil.spawnBlueDemonEffect(serverLevel, livingEntity, jointVec, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                            float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                            float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                            serverLevel.playSound(
                                    null,
                                    BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                    AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                    SoundSource.NEUTRAL,
                                    volume,
                                    pitch
                            );
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack =  livingEntity.getOffhandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handL, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack = livingEntity.getMainHandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT_LIGHTNING =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack =  livingEntity.getOffhandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handL, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setMode(TridentMode.LIGHTNING);
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_LIGHTNING =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack = livingEntity.getMainHandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setMode(TridentMode.LIGHTNING);
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_EXPLODE =
                (livingEntityPatch, staticAnimation, object) -> {
                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        ItemStack stack = livingEntity.getMainHandItem();
                        Item weapon = stack.getItem();
                        if (weapon instanceof BlueDemonTridentItem) {
                            if (livingEntity instanceof Player player) {
                                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                            }
                            Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                    livingEntity, new Vec3f(0, 0, 0),
                                    Armatures.BIPED.get().handR, 0.0F, 0.0F
                            );
                            if (jointVec == null) return;

                            Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                            if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                            BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                            trident.assignSpawnSequence(livingEntity);
                            trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                            trident.setMode(TridentMode.EXPLOSION);
                            trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                            trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                            trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                            float speed = 2.5F;
                            float inaccuracy = 1.0F;

                            trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                            trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                            serverLevel.addFreshEntity(trident);
                        }
                    }
                };

        public static final AnimationEvent.E0 SHOCK_WAVE =
                (livingEntityPatch, staticAnimation, object) -> {

                    Vec3 legendarySwordPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                            Armatures.BIPED.get().toolR, 1.5F, 0.0F);
                    final int MAX_SHOCKWAVE_RADIUS = 6;
                    final int TICKS_BETWEEN_LAYERS = 2;
                    for (int radius = 1; radius <= MAX_SHOCKWAVE_RADIUS; radius++) {
                        int delayTicks = (radius - 1) * TICKS_BETWEEN_LAYERS;
                        int ringRadius = radius;
                        if (legendarySwordPos == null) return;
                        BlockPos finalVec = BlockPos.containing(legendarySwordPos);
                        new DelayedTask(delayTicks) {
                            @Override
                            public void run() {
                                LegendarySwordItem.spawnCircleRing((ServerLevel) livingEntityPatch.getOriginal().level(), finalVec, ringRadius, livingEntityPatch.getOriginal());
                            }
                        };
                    }
                };

        public static final AnimationEvent.E0 END_ATTACK =
                (livingentitypatch, staticAnimation, object) -> {
                    if (livingentitypatch instanceof PlayerPatch) {
                        livingentitypatch.playAnimationSynchronized(AnimsPugilistSteve.DUAL_END, 0.1F);
                    }
                };
    }
}
