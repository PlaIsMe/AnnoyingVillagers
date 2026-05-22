package com.pla.annoyingvillagers.capabilities;

import java.util.function.Function;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AVSounds;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.*;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Builder;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class AVWeaponCapabilityPresets {

    public static final Function<Item, Builder> ENDER_AEGIS = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .canBePlacedOffhand(false)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsHerrscher.HERRSCHER_AUTO_1,
                            AnimsHerrscher.HERRSCHER_AUTO_2,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_1,
                            AVAnimations.ENDER_AEGIS_MOONLESS_AUTO_2,
                            AnimsSolar.SOLAR_QUEMADURA,
                            AnimsSolar.SOLAR_OBSCURIDAD_IMPACTO,
                            AnimsSolar.SOLAR_HORNO)
                    .innateSkill(Styles.TWO_HAND, (stack) -> AVSkills.ENDER_AEGIS)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                    .weaponCombinationPredicator((patch) -> true);

    public static final Function<Item, CapabilityItem.Builder> ENDER_GLAIVE = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.AGONY)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_1,
                            AVAnimations.ENDER_GLAIVE_NAPOLEON_AUTO_2,
                            AnimsAgony.AGONY_AUTO_4,
                            AnimsAgony.AGONY_AUTO_2,
                            AnimsAgony.AGONY_AUTO_3,
                            AVAnimations.ENDER_GLAIVE_NAPOLEON_AUSTERLITZ,
                            AnimsAgony.AGONY_AIR_ATTACK_4)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SPEAR_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.ENDER_GLAIVE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.VALOUR_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.VALOUR_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> DEMONIAC_VOLTAGE_REAVER = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.DEMONIAC_RUINE_AUTO_1,
                            WOMAnimations.TORMENT_BERSERK_AUTO_2,
                            WOMAnimations.TORMENT_BERSERK_AUTO_1,
                            AVAnimations.DEMONIAC_RUINE_AUTO_2,
                            AVAnimations.DEMONIAC_RUINE_AUTO_4,
                            AVAnimations.DEMONIAC_TORMENT_CHARGED_ATTACK_2,
                            AVAnimations.DEMONIAC_RUINE_COMET
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.DEMONIAC_VOLTAGE_REAVER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.VALOUR_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.VALOUR_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, Builder> OBSIDIAN_SLEDGEHAMMER = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.DEMONIAC_RUINE_AUTO_1,
                            AVAnimations.DEMONIAC_RUINE_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_4,
                            AnimsSolar.SOLAR_AUTO_4,
                            AnimsSolar.SOLAR_AUTO_2,
                            AnimsSolar.SOLAR_OBSCURIDAD_AUTO_4,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_TISHNAW
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.OBSIDIAN_SLEDGEHAMMER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.VALOUR_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.VALOUR_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.VALOUR_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER)));

    public static final Function<Item, CapabilityItem.Builder> ENDER_SLAYER_SCYTHE = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
                    .styleProvider((entityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.ANTITHEUS)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_3,
                            WOMAnimations.KICK_AUTO_3,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_2,
                            WOMAnimations.KICK_AUTO_1,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_4,
                            AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_1,
                            AVAnimations.CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.ENDER_SLAYER_SCYTHE)
                    .innateSkill(Styles.MOUNT, (itemstack) -> AVSkills.ENDER_SLAYER_SCYTHE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.MOUNT, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.MOUNT, LivingMotions.MOUNT, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.MOUNT, LivingMotions.IDLE, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, CapabilityItem.Builder> NULL_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsAgony.AGONY_AIR_ATTACK_1,
                            WOMAnimations.ANTITHEUS_ASCENDED_AUTO_1,
                            WOMAnimations.ANTITHEUS_ASCENDED_AUTO_2,
                            WOMAnimations.ANTITHEUS_ASCENDED_AUTO_3,
                            AVAnimations.CLONE_ANTITHEUS_ASCENDED_BLACKHOLE,
                            AnimsAgony.AGONY_AIR_ATTACK_4,
                            AnimsAgony.AGONY_AIR_ATTACK_3,
                            AVAnimations.CLONE_ANTITHEUS_ASCENDED_DEATHFALL)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.NULL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.CLONE_ANTITHEUS_ASCENDED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.ANTITHEUS_ASCENDED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.ANTITHEUS_ASCENDED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.ANTITHEUS_ASCENDED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD);

    public static final Function<Item, CapabilityItem.Builder> OBSIDIAN_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.OBSIDIAN_FIST_AUTO1,
                            AVAnimations.OBSIDIAN_FIST_AUTO2,
                            AVAnimations.OBSIDIAN_FIST_AUTO3,
                            AVAnimations.OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.OBSIDIAN_BIPED_LANDING,
                            AVAnimations.OBSIDIAN_STRONG_PUNCH,
                            AVAnimations.OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.OBSIDIAN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD);

    public static final Function<Item, CapabilityItem.Builder> BEDROCK_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.ONE_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.FIST_AUTO1,
                            Animations.FIST_AUTO2,
                            Animations.FIST_AUTO3,
                            Animations.FIST_AIR_SLASH,
                            WOMAnimations.STRONG_PUNCH,
                            Animations.FIST_DASH,
                            WOMAnimations.STRONG_KICK)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.BEDROCK_WEAPON)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AnimsMoonless.MOONLESS_GUARD);

    public static final Function<Item, CapabilityItem.Builder> SHADOW_OBSIDIAN_PILLAR = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()) ? Styles.OCHS : Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.OBSIDIAN_FIST_AUTO1,
                            AVAnimations.OBSIDIAN_FIST_AUTO2,
                            AVAnimations.OBSIDIAN_FIST_AUTO3,
                            AVAnimations.OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.OBSIDIAN_ZOMBIE_ATTACK3,
                            AVAnimations.OBSIDIAN_STRONG_PUNCH,
                            AVAnimations.OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW)
                    .newStyleCombo(Styles.OCHS,
                            AVAnimations.SHADOW_OBSIDIAN_FIST_AUTO1,
                            AVAnimations.OBSIDIAN_FIST_AUTO2,
                            AVAnimations.SHADOW_OBSIDIAN_FIST_AUTO3,
                            AVAnimations.OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.OBSIDIAN_INFERNAL_AUTO_1,
                            AVAnimations.OBSIDIAN_STRONG_PUNCH,
                            AVAnimations.OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_PILLAR)
                    .innateSkill(Styles.OCHS,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_PILLAR_SWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AVAnimations.FIST_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));;

    public static final Function<Item, Builder> SHADOW_OBSIDIAN_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SWORD)
                    .canBePlacedOffhand(true)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(AnnoyingVillagersModSounds.OB_PLACE.get())
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()) ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .collider(ColliderPreset.SWORD)
                    .newStyleCombo(Styles.ONE_HAND,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_1,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_2,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG,
                            AVAnimations.SHADOW_OBSIDIAN_FIST_AIR_SLASH,
                            AVAnimations.SWORD_HEAVY_AUTO_1,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_2,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO4,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO5,
                            AVAnimations.GREATSWORD_DUAL_AUTO_2,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AUTO_3,
                            WOMAnimations.TORMENT_DASH,
                            AVAnimations.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AIRSLASH)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_SWORD_DUAL)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AnimsMoonless.MOONLESS_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.OLD_MOONLESS_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));

    public static final Function<Item, Builder> LEGENDARY_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingentitypatch) -> Styles.TWO_HAND
                    ).collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            WOMAnimations.TORMENT_AUTO_1,
                            WOMAnimations.TORMENT_AUTO_2,
                            AnimsSolar.SOLAR_AUTO_1,
                            AVAnimations.LEGENDARY_SWORD_AUTO_4,
                            AVAnimations.LEGENDARY_SWORD_WAKE_UP_ATTACK,
                            AVAnimations.YELLOW_SOLAR_AUTO_2,
                            AVAnimations.YELLOW_NAPOLEON_AUTO_3,
                            AVAnimations.DEMONIAC_TORMENT_CHARGED_ATTACK_2,
                            AVAnimations.YELLOW_NAPOLEON_AUSTERLITZ_SHOOT,
                            WOMAnimations.TORMENT_BERSERK_DASH
                    ).newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.LEGENDARY_SWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.LEGENDARY_SWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.TORMENT_BERSERK_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.LEGENDARY_SWORD_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof BlueDemonTridentItem);

    public static final Function<Item, Builder> BLUE_DEMON_TRIDENT = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SPEAR)
                    .canBePlacedOffhand(true)
                    .collider(ColliderPreset.SPEAR)
                    .swingSound(SoundEvents.TRIDENT_THROW)
                    .hitSound(SoundEvents.TRIDENT_RETURN)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()) ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .collider(ColliderPreset.SPEAR)
                    .newStyleCombo(Styles.ONE_HAND,
                            AVAnimations.ADVANCED_LANCER_AUTO1,
                            AVAnimations.NERF_TSUNAMI_REINFORCED,
                            AVAnimations.ADVANCED_DUELIST_SHOOTING_STAR)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.ADVANCED_LANCER_AUTO1,
                            AVAnimations.TRIDENT_DUAL_AUTO2,
                            AVAnimations.ADVANCED_DUELIST_SHOOTING_STAR,
                            AVAnimations.CUT_DP_AIR_ATTACK,
                            AVAnimations.ADVANCED_LANCER_AUTO3,
                            AVAnimations.ADVANCED_DUELIST_WHIRLEDGE,
                            AVAnimations.NERF_TSUNAMI_REINFORCED,
                            AVAnimations.CUT_HOOK_SPIN_SLASH_AIR)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> EpicFightSkills.WRATHFUL_LIGHTING)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.TRIDENT_FESTIVAL)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.TRIDENT_TWO_HAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.TRIDENT_TWO_HAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()));

    public static final Function<Item, Builder> WOOPIE_THE_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO2,
                            Animations.SWORD_AUTO3,
                            AnimsSatsujin.SATSUJIN_AUTO_1,
                            AnimsSatsujin.SATSUJIN_AUTO_2,
                            AnimsHerrscher.HERRSCHER_VERDAMMNIS,
                            AnimsSatsujin.SATSUJIN_TSUKUYOMI)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_DUAL_AUTO1,
                            Animations.SWORD_DUAL_AUTO2,
                            Animations.SWORD_DUAL_AUTO3,
                            AnimsSatsujin.SATSUJIN_AUTO_1,
                            AnimsSatsujin.SATSUJIN_AUTO_2,
                            AnimsHerrscher.HERRSCHER_VERDAMMNIS,
                            AnimsSatsujin.SATSUJIN_TSUKUYOMI)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.WOOPIE_THE_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.WOOPIE_THE_SWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> GREAT_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                                    && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO2,
                            Animations.SWORD_AUTO3,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AnimsSatsujin.SATSUJIN_AUTO_3,
                            AnimsSatsujin.SATSUJIN_HARUSAKI,
                            AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_DUAL_AUTO1,
                            Animations.SWORD_DUAL_AUTO2,
                            Animations.SWORD_DUAL_AUTO3,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AnimsSatsujin.SATSUJIN_AUTO_3,
                            AnimsSatsujin.SATSUJIN_HARUSAKI,
                            AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.GREAT_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.GREAT_SWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                                    || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> AV_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> THUNDER_DIAMOND_BLADE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.THUNDER_DIAMOND_BLADE)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_THUNDER_DIAMOND_BLADE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> BLACK_FIRE_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.BLACK_FIRE_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.BLACK_FIRE_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> BLUE_FLAME_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.BLUE_FLAME_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.BLUE_FLAME_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> KNIFE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.DAGGER)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER ? Styles.TWO_HAND : Styles.ONE_HAND)
            .collider(ColliderPreset.DAGGER)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.THIEF_AUTO1,
                    AVAnimations.DAGGER_AUTO1,
                    AVAnimations.DAGGER_AUTO2,
                    AVAnimations.DAGGER_AUTO3,
                    AVAnimations.THIEF_AUTO3,
                    AVAnimations.THIEF_DASH_ATTACK,
                    AVAnimations.THIEF_AIRSLASH)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DAGGER_DUAL_AUTO1,
                    AVAnimations.DAGGER_DUAL_AUTO2,
                    AVAnimations.DAGGER_DUAL_AUTO3,
                    AVAnimations.DAGGER_DUAL_AUTO4,
                    AVAnimations.DUAL_BLADES_AUTO3,
                    AVAnimations.SWORD_DASH_ATTACK,
                    AVAnimations.DUAL_BLADES_AIRSLAM)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.KNIFE)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_KNIFE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory()
                            == WeaponCategories.DAGGER);

    public static final Function<Item, Builder> MOON_BLADE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.DAGGER)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .canBePlacedOffhand(true)
            .collider(ColliderPreset.DAGGER)
            .newStyleCombo(Styles.COMMON,
                    AVAnimations.IRON_LOTUS_AUTO1,
                    AVAnimations.IRON_LOTUS_AUTO2,
                    AVAnimations.FIST_UP,
                    AVAnimations.IRON_LOTUS_AUTO3,
                    Animations.REVELATION_TWOHAND,
                    AVAnimations.IRON_LOTUS_DASH_ATTACK,
                    Animations.REVELATION_ONEHAND)
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.MOON_BLADE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory()
                            == WeaponCategories.DAGGER);

    public static final Function<Item, Builder> ARM_BLADE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.DAGGER)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .collider(ColliderPreset.DAGGER)
            .canBePlacedOffhand(true)
            .newStyleCombo(Styles.COMMON,
                    AVAnimations.IRON_LOTUS_AUTO1,
                    AVAnimations.IRON_LOTUS_AUTO2,
                    AVAnimations.FIST_UP,
                    AVAnimations.IRON_LOTUS_AUTO3,
                    Animations.REVELATION_TWOHAND,
                    AVAnimations.IRON_LOTUS_DASH_ATTACK,
                    Animations.REVELATION_ONEHAND)
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.ARM_BLADE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory()
                            == WeaponCategories.DAGGER);

    public static final Function<Item, Builder> CLAW = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.DAGGER)
            .canBePlacedOffhand(false)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .collider(ColliderPreset.DAGGER)
            .newStyleCombo(Styles.COMMON,
                    AVAnimations.IRON_LOTUS_AUTO1,
                    AVAnimations.IRON_LOTUS_AUTO2,
                    AVAnimations.FIST_UP,
                    AVAnimations.IRON_LOTUS_AUTO3,
                    Animations.REVELATION_TWOHAND,
                    AVAnimations.IRON_LOTUS_DASH_ATTACK,
                    Animations.REVELATION_ONEHAND)
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.CLAW)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK);

    public static final Function<Item, Builder> CLOW_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.CLOW_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.CLOW_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> CLEAVER = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.SQUIRE_SWORD_AUTO_1,
                            AVAnimations.SQUIRE_SWORD_AUTO_2,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                            Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                            AVAnimations.SQUIRE_SWORD_AUTO_3,
                            AVAnimations.SQUIRE_SWORD_DASH_ATTACK,
                            AVAnimations.SQUIRE_SWORD_HOP_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.CLEAVER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.SQUIRE_SWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.SQUIRE_SWORD_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.SQUIRE_SWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.SQUIRE_SWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> DIAMOND_ATTRACTOR_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.DIAMOND_ATTRACTOR_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DIAMOND_ATTRACTOR_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> DIAMOND_BLASTER_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.DIAMOND_BLASTER_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DIAMOND_BLASTER_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> HACKER_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.HACKER_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.HACKER_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> DIAMOND_SABRE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.TWO_HAND,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                    AnimsRuine.RUINE_AUTO_1,
                    AnimsRuine.RUINE_AUTO_2,
                    AVAnimations.SABRE_AUTO3,
                    AVAnimations.SABRE_DASH_ATTACK,
                    AVAnimations.SABRE_AIR_ATTACK)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DIAMOND_SABRE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE,  Animations.BIPED_HOLD_LIECHTENAUER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN,  Animations.BIPED_HOLD_LIECHTENAUER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER);

    public static final Function<Item, Builder> HOOK_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.HOOK_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_HOOK_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> FLANKER_HOOK_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.FLANKER_HOOK_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_HOOK_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> DNAX_HOOK_SWORD = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AVAnimations.CUT_LEFT_DP_AUTO_3,
                    AVAnimations.SWORD_DASH,
                    AVAnimations.DAGGER_AUTO1,
                    AnimsHerrscher.HERRSCHER_AUTO_2,
                    AnimsHerrscher.HERRSCHER_AUTO_1,
                    AVAnimations.CUT_LEFT_DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_AUTO_1,
                    AVAnimations.DP_AUTO_2,
                    AVAnimations.DP_AUTO_3,
                    AVAnimations.DP_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO2,
                    AVAnimations.DP_DASH,
                    AVAnimations.DP_NIGHT_FALL)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.DNAX_HOOK_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_DNAX_HOOK_SWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));

    public static final Function<Item, Builder> HALBERD = (item) ->
            WeaponCapability.builder()
                .category(WeaponCategories.AXE)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                .collider(ColliderPreset.SPEAR).canBePlacedOffhand(false)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .newStyleCombo(Styles.TWO_HAND,
                        AVAnimations.BAXE_AUTO_1,
                        AVAnimations.BAXE_AUTO_2,
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO2,
                        Animations.SWORD_AUTO3,
                        AVAnimations.BAXE_DASH_ATTACK,
                        AVAnimations.BAXE_AIR_ATTACK
                ).innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.HALBERD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.BAXE_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.BAXE_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.BAXE_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.BAXE_WALK)
                    .collider(ColliderPreset.SPEAR);

    public static final Function<Item, Builder> DOUBLE_HALBERD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .styleProvider((livingentitypatch) ->  Styles.TWO_HAND)
                    .collider(ColliderPreset.SPEAR).canBePlacedOffhand(false)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            WOMAnimations.STAFF_AUTO_1,
                            AnimsOrbit.ORBIT_ATTACK_1,
                            AnimsOrbit.ORBIT_ATTACK_3,
                            AnimsOrbit.ORBIT_ATTACK_4,
                            Animations.SPEAR_DASH,
                            AnimsOrbit.ORBIT_SATELITE,
                            WOMAnimations.STAFF_KINKONG
                    ).innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.HALBERD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.STAFF_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.STAFF_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, WOMAnimations.STAFF_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.STAFF_IDLE)
                    .collider(ColliderPreset.SPEAR);

    public static final Function<Item, Builder> KILLER_AXE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD).canBePlacedOffhand(false)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.AXE_AUTO1,
                            Animations.SWORD_AUTO2,
                            Animations.SWORD_AUTO1,
                            AVAnimations.AXE_AUTO2,
                            AVAnimations.AXE_AUTO3,
                            AVAnimations.AXE_DASH,
                            AVAnimations.AXE_AIRSLASH
                    ).innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.KILLER_AXE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.BAXE_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.BAXE_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.BAXE_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.BAXE_WALK)
                    .collider(ColliderPreset.SWORD);

    public static final Function<Item, Builder> EARTH_AXE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .collider(ColliderPreset.TOOLS)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.AXE_AUTO1,
                            Animations.AXE_AUTO2,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO2,
                            Animations.SWORD_AUTO3,
                            Animations.AXE_DASH,
                            Animations.AXE_AIRSLASH
                    ).innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.EARTH_AXE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                    .collider(ColliderPreset.TOOLS);

    public static final Function<Item, Builder> RED_AXE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .collider(ColliderPreset.TOOLS)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            Animations.AXE_AUTO1,
                            Animations.AXE_AUTO2,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO2,
                            Animations.SWORD_AUTO3,
                            Animations.AXE_DASH,
                            Animations.AXE_AIRSLASH
                    ).innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.RED_AXE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                    .collider(ColliderPreset.TOOLS);

    public static final Function<Item, Builder> EXTERMINATOR_BATTLE_AXE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.AXE)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    Animations.AXE_AUTO1,
                    Animations.AXE_AUTO2,
                    Animations.SWORD_AUTO1,
                    Animations.SWORD_AUTO2,
                    Animations.SWORD_AUTO3,
                    Animations.AXE_DASH,
                    Animations.AXE_AIRSLASH)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.DP_HEAVY_AUTO_1,
                    AVAnimations.DP_HEAVY_AUTO_2,
                    AVAnimations.DP_HEAVY_AUTO_3,
                    AVAnimations.DP_HEAVY_AUTO_4,
                    AVAnimations.DUAL_SWORD_AUTO4,
                    AVAnimations.DUAL_SWORD_AUTO5,
                    AVAnimations.DP_DASH,
                    AVAnimations.HOOK_SLASH_AIR)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> EpicFightSkills.GUILLOTINE_AXE)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_AXE_SPIN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE);

    public static final Function<Item, Builder> AV_SPEAR = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SPEAR)
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory()
                            == WeaponCategories.SHIELD ? Styles.ONE_HAND : Styles.TWO_HAND).collider(ColliderPreset.SPEAR)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.ONE_HAND,
                    Animations.SPEAR_ONEHAND_AUTO,
                    AnimsAgony.AGONY_AUTO_4,
                    Animations.SPEAR_DASH,
                    WOMAnimations.STAFF_KINKONG)
            .newStyleCombo(Styles.TWO_HAND,
                    Animations.SPEAR_TWOHAND_AUTO1,
                    Animations.SPEAR_TWOHAND_AUTO2,
                    AnimsAgony.AGONY_AUTO_4,
                    WOMAnimations.STAFF_AUTO_2,
                    WOMAnimations.STAFF_AUTO_3,
                    Animations.SPEAR_DASH,
                    Animations.SPEAR_TWOHAND_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SPEAR_MOUNT_ATTACK,
                    Animations.SPEAR_TWOHAND_AUTO1,
                    Animations.SPEAR_TWOHAND_AUTO2,
                    WOMAnimations.STAFF_AUTO_2)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> EpicFightSkills.HEARTPIERCER)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> EpicFightSkills.GRASPING_SPIRE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.GLOWING_AGONY_GUARD);

    public static final Function<Item, Builder> DIAMOND_WARBLADE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_2,
                    AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_1,
                    AnimsRuine.RUINE_AUTO_1,
                    AnimsRuine.RUINE_CHATIMENT,
                    AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_3,
                    AVAnimations.DP_HEAVY_AUTO_4_SPC,
                    AnimsRuine.RUINE_COMET)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DIAMOND_WARBLADE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.SQUIRE_SWORD_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.SQUIRE_SWORD_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.SQUIRE_SWORD_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.SQUIRE_SWORD_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

    public static final Function<Item, Builder> DIAMOND_LAEVATEINN = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.TWO_HAND,
                    AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_2,
                    AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_1,
                    AnimsRuine.RUINE_AUTO_1,
                    AnimsRuine.RUINE_CHATIMENT,
                    AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_3,
                    AVAnimations.DP_HEAVY_AUTO_4_SPC,
                    AnimsRuine.RUINE_COMET)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DIAMOND_LAEVATEINN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.SQUIRE_SWORD_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.SQUIRE_SWORD_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.SQUIRE_SWORD_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.SQUIRE_SWORD_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

    public static final Function<Item, Builder> FALCHION = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.TACHI)
                    .collider(ColliderPreset.TACHI)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI ? Styles.OCHS : Styles.TWO_HAND)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_2,
                            AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_1,
                            AnimsRuine.RUINE_AUTO_1,
                            AnimsRuine.RUINE_CHATIMENT,
                            AVAnimations.CUT_LEFT_DP_SHADOW_LUNGE_3,
                            AVAnimations.DP_HEAVY_AUTO_4_SPC,
                            AnimsRuine.RUINE_COMET)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.FALCHION)
                    .newStyleCombo(Styles.OCHS,
                            AVAnimations.DP_HEAVY_AUTO_1,
                            AVAnimations.DP_HEAVY_AUTO_2,
                            AVAnimations.DP_SHADOW_LUNGE_1,
                            AVAnimations.DP_SHADOW_LUNGE_2,
                            AVAnimations.DP_SHADOW_LUNGE_3,
                            AVAnimations.DP_HEAVY_AUTO_4_SPC,
                            AVAnimations.DP_NIGHT_FALL)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.OCHS,
                            (itemstack) -> AVSkills.DUAL_FALCHION)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, AVAnimations.DUAL_TACHI_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI);

    public static final Function<Item, Builder> AV_LONGSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.LONGSWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .collider(ColliderPreset.LONGSWORD)
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            AnimsRuine.RUINE_AUTO_1,
                            AnimsRuine.RUINE_AUTO_2,
                            AnimsRuine.RUINE_AUTO_3,
                            AnimsRuine.RUINE_CHATIMENT,
                            AVAnimations.HOOK_GROUND,
                            AnimsRuine.RUINE_COMET)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.LONGSWORD)
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.DP_HEAVY_AUTO_3,
                            AVAnimations.DUAL_SWORD_AUTO2,
                            AVAnimations.DUAL_SWORD_AUTO3,
                            AVAnimations.DUAL_SWORD_AUTO4,
                            AVAnimations.DUAL_SWORD_AUTO5,
                            AVAnimations.HOOK_GROUND,
                            AVAnimations.DP_NIGHT_FALL)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.LONGSWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, AnimsRuine.RUINE_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, AnimsRuine.RUINE_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AnimsRuine.RUINE_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.RUN_DUAL_BIG)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.DUAL_TACHI_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD);

    public static final Function<Item, Builder> CHIPPED_LONGSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.LONGSWORD)
                    .styleProvider(
                            (livingentitypatch) -> Styles.TWO_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .collider(ColliderPreset.LONGSWORD)
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsRuine.RUINE_AUTO_1,
                            AnimsRuine.RUINE_AUTO_2,
                            AnimsRuine.RUINE_AUTO_3,
                            AnimsRuine.RUINE_EXPIATION_1,
                            AnimsRuine.RUINE_EXPIATION_2,
                            AVAnimations.CUT_LEFT_DP_DASH,
                            AnimsRuine.RUINE_EXPIATION)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.CHIPPED_LONGSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.SQUIRE_SWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.SQUIRE_SWORD_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.SQUIRE_SWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.SQUIRE_SWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> AV_GREATSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.GREATSWORD_AUTO1,
                            Animations.GREATSWORD_AUTO2,
                            WOMAnimations.TORMENT_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_3,
                            AnimsSolar.SOLAR_HORNO,
                            Animations.GREATSWORD_DASH,
                            Animations.GREATSWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.HELICOPTER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> GREATAXE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.GREATAXE_SLASH,
                            WOMAnimations.TORMENT_AUTO_3,
                            AVAnimations.GREATSWORD_DASH_ATTACK,
                            WOMAnimations.TORMENT_BERSERK_AUTO_1,
                            WOMAnimations.TORMENT_BERSERK_AUTO_2,
                            AVAnimations.GREATSWORD_POWER_GEYSER,
                            AVAnimations.GREATSWORD_AIRSLAM)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.GREATAXE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.GREATAXE_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.GREATAXE_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> GIANT_AXE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.GREATAXE_SLASH,
                            WOMAnimations.TORMENT_AUTO_3,
                            AVAnimations.GREATSWORD_DASH_ATTACK,
                            WOMAnimations.TORMENT_BERSERK_AUTO_1,
                            WOMAnimations.TORMENT_BERSERK_AUTO_2,
                            AVAnimations.GREATSWORD_POWER_GEYSER,
                            AVAnimations.GREATSWORD_AIRSLAM)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.GIANT_AXE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.GREATAXE_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.GREATAXE_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> BATTLE_AXE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            AVAnimations.GREATAXE_SLASH,
                            WOMAnimations.TORMENT_AUTO_3,
                            AVAnimations.GREATSWORD_DASH_ATTACK,
                            WOMAnimations.TORMENT_BERSERK_AUTO_1,
                            WOMAnimations.TORMENT_BERSERK_AUTO_2,
                            AVAnimations.GREATSWORD_POWER_GEYSER,
                            AVAnimations.GREATSWORD_AIRSLAM)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.BATTLE_AXE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.GREATAXE_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.GREATAXE_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> WOODEN_DOOR = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(SoundEvents.WOODEN_DOOR_OPEN)
                    .hitSound(SoundEvents.WOODEN_DOOR_CLOSE)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.GREATSWORD_AUTO1,
                            Animations.GREATSWORD_AUTO2,
                            WOMAnimations.TORMENT_AUTO_2,
                            WOMAnimations.TORMENT_AUTO_3,
                            Animations.GREATSWORD_DASH,
                            WOMAnimations.TORMENT_DASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.WOODEN_DOOR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_GREATSWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> TRAPDOOR = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(SoundEvents.WOODEN_TRAPDOOR_OPEN)
                    .hitSound(SoundEvents.WOODEN_TRAPDOOR_CLOSE)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO3,
                            Animations.SWORD_AUTO2,
                            AnimsHerrscher.HERRSCHER_AUTO_3,
                            AnimsHerrscher.HERRSCHER_VERDAMMNIS,
                            Animations.SWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.TRAPDOOR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.BIPED_RUN_ESWORD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.SHIELD_MAINHAND);

    public static final Function<Item, Builder> CRAFTING_TABLE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.TACHI_AUTO2,
                            Animations.TACHI_AUTO3,
                            AnimsRuine.RUINE_AUTO_1,
                            AnimsRuine.RUINE_AUTO_2,
                            AnimsRuine.RUINE_CHATIMENT,
                            Animations.LONGSWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.CRAFTING_TABLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.CARRY);

    public static final Function<Item, Builder> LADDER = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.AXE)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .swingSound(SoundEvents.LADDER_STEP)
                    .hitSound(SoundEvents.LADDER_HIT)
                    .newStyleCombo(Styles.TWO_HAND,
                            Animations.SWORD_AUTO1,
                            Animations.SWORD_AUTO3,
                            AVAnimations.SWORD_HEAVY_AUTO_1,
                            Animations.TACHI_AUTO3,
                            Animations.SWORD_DASH,
                            Animations.SWORD_AIR_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.LADDER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsMoonless.MOONLESS_GUARD);

    public static final Function<Item, CapabilityItem.Builder> BOW = (item) ->
            WeaponCapability.builder()
                    .category(CapabilityItem.WeaponCategories.RANGED)
                    .styleProvider((patch) -> CapabilityItem.Styles.ONE_HAND)
                    .zoomInType(CapabilityItem.ZoomInType.USE_TICK)
                    .swingSound(SoundEvents.ARROW_SHOOT)
                    .hitSound(SoundEvents.ARROW_HIT)
                    .canBePlacedOffhand(false)
                    .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                            AVAnimations.BOW_AUTO_1,
                            AVAnimations.BOW_AUTO_3,
                            AVAnimations.BOW_AUTO_5)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN,  Animations.BIPED_RUN)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.AIM,  Animations.BIPED_BOW_AIM)
                    .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
                    .constructor(AVBowCapability::new);

    public static void register(WeaponCapabilityPresetRegistryEvent weaponcapabilitypresetregistryevent) {
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_aegis"), AVWeaponCapabilityPresets.ENDER_AEGIS);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_glaive"), AVWeaponCapabilityPresets.ENDER_GLAIVE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "demoniac_voltage_reaver"), AVWeaponCapabilityPresets.DEMONIAC_VOLTAGE_REAVER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_sledgehammer"), AVWeaponCapabilityPresets.OBSIDIAN_SLEDGEHAMMER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_slayer_scythe"), AVWeaponCapabilityPresets.ENDER_SLAYER_SCYTHE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "null_weapon"), AVWeaponCapabilityPresets.NULL_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_weapon"), AVWeaponCapabilityPresets.OBSIDIAN_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "bedrock_weapon"), AVWeaponCapabilityPresets.BEDROCK_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shadow_obsidian_pillar"), AVWeaponCapabilityPresets.SHADOW_OBSIDIAN_PILLAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shadow_obsidian_sword"), AVWeaponCapabilityPresets.SHADOW_OBSIDIAN_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "legendary_sword"), AVWeaponCapabilityPresets.LEGENDARY_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blue_demon_trident"), AVWeaponCapabilityPresets.BLUE_DEMON_TRIDENT);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "woopie_the_sword"), AVWeaponCapabilityPresets.WOOPIE_THE_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "great_sword"), AVWeaponCapabilityPresets.GREAT_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_sword"), AVWeaponCapabilityPresets.AV_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "halberd"), AVWeaponCapabilityPresets.HALBERD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "double_halberd"), AVWeaponCapabilityPresets.DOUBLE_HALBERD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "killer_axe"), AVWeaponCapabilityPresets.KILLER_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "earth_axe"), AVWeaponCapabilityPresets.EARTH_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "red_axe"), AVWeaponCapabilityPresets.RED_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "exterminator_battleaxe"), AVWeaponCapabilityPresets.EXTERMINATOR_BATTLE_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_spear"), AVWeaponCapabilityPresets.AV_SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "falchion"), AVWeaponCapabilityPresets.FALCHION);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_warblade"), AVWeaponCapabilityPresets.DIAMOND_WARBLADE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_laevateinn"), AVWeaponCapabilityPresets.DIAMOND_LAEVATEINN);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_longsword"), AVWeaponCapabilityPresets.AV_LONGSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "chipped_longsword"), AVWeaponCapabilityPresets.CHIPPED_LONGSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_greatsword"), AVWeaponCapabilityPresets.AV_GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "greataxe"), AVWeaponCapabilityPresets.GREATAXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "giant_axe"), AVWeaponCapabilityPresets.GIANT_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "battle_axe"), AVWeaponCapabilityPresets.BATTLE_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_bow"), AVWeaponCapabilityPresets.BOW);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "wooden_door"), AVWeaponCapabilityPresets.WOODEN_DOOR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "crafting_table"), AVWeaponCapabilityPresets.CRAFTING_TABLE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "trapdoor"), AVWeaponCapabilityPresets.TRAPDOOR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ladder"), AVWeaponCapabilityPresets.LADDER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "black_fire_sword"), AVWeaponCapabilityPresets.BLACK_FIRE_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "thunder_diamond_blade"), AVWeaponCapabilityPresets.THUNDER_DIAMOND_BLADE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blue_flame_sword"), AVWeaponCapabilityPresets.BLUE_FLAME_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "clow_sword"), AVWeaponCapabilityPresets.CLOW_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "cleaver"), AVWeaponCapabilityPresets.CLEAVER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_attractor_sword"), AVWeaponCapabilityPresets.DIAMOND_ATTRACTOR_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_blaster_sword"), AVWeaponCapabilityPresets.DIAMOND_BLASTER_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hacker_sword"), AVWeaponCapabilityPresets.HACKER_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_sabre"), AVWeaponCapabilityPresets.DIAMOND_SABRE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hook_sword"), AVWeaponCapabilityPresets.HOOK_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "flanker_hook_sword"), AVWeaponCapabilityPresets.FLANKER_HOOK_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dnax_hook_sword"), AVWeaponCapabilityPresets.DNAX_HOOK_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "knife"), AVWeaponCapabilityPresets.KNIFE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "arm_blade"), AVWeaponCapabilityPresets.ARM_BLADE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "moon_blade"), AVWeaponCapabilityPresets.MOON_BLADE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "claw"), AVWeaponCapabilityPresets.CLAW);
    }
}
