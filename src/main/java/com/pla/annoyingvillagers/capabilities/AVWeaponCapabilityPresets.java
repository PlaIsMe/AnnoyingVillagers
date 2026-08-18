package com.pla.annoyingvillagers.capabilities;

import java.util.function.Function;

import com.hm.efn.gameasset.animations.*;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.item.WoopieTheSwordItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.shelmarow.ef_awaken.efassets.animations.DarkNightPursuitersAnimations;
import net.shelmarow.ef_awaken.efassets.animations.StraightSwordAnimations;
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

@SuppressWarnings({"deprecation", "removal"})
public class AVWeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> ENDER_AEGIS = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.SWORD)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsEnderAegis.ENDER_AEGIS_AUTO1,
                            AnimsEnderAegis.ENDER_AEGIS_AUTO2,
                            AnimsEnderAegis.ENDER_AEGIS_AUTO3,
                            AnimsEnderAegis.ENDER_AEGIS_AUTO4,
                            AnimsEnderAegis.ENDER_AEGIS_AUTO5,
                            AnimsEnderAegis.ENDER_AEGIS_DASH,
                            AnimsEnderAegis.ENDER_AEGIS_AIRSLASH)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SPEAR_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.ENDER_AEGIS)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsEnderAegis.ENDER_AEGIS_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EFNDualSwordAnimations.NF_DUAL_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.HOLD_ONEHAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.HOLD_ONEHAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsEnderAegis.ENDER_AEGIS_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK_SHIELD, AnimsEnderAegis.ENDER_AEGIS_GUARD);

    public static final Function<Item, CapabilityItem.Builder> ENDER_GLAIVE = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.AGONY)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsEnderGlaive.ENDER_GLAIVE_AUTO1,
                            AnimsEnderGlaive.ENDER_GLAIVE_AUTO2,
                            AnimsEnderGlaive.ENDER_GLAIVE_AUTO3,
                            AnimsEnderGlaive.ENDER_GLAIVE_AUTO4,
                            AnimsEnderGlaive.ENDER_GLAIVE_AUTO5,
                            AnimsEnderGlaive.ENDER_GLAIVE_DASH,
                            AnimsEnderGlaive.ENDER_GLAIVE_AIRSLASH)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SPEAR_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.ENDER_GLAIVE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.ELITE_HOLD_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.ELITE_WALK_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.ELITE_RUN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.ELITE_RUN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsAVSpear.AV_SPEAR_GUARD);

    public static final Function<Item, CapabilityItem.Builder> ENDER_SLAYER_SCYTHE = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SPEAR)
                    .styleProvider((entityPatch) -> Styles.TWO_HAND)
                    .collider(WOMWeaponColliders.ANTITHEUS)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_AUTO1,
                            AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_AUTO2,
                            AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_AUTO3,
                            AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_AUTO4,
                            AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_AUTO5,
                            AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_DASH,
                            AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.ENDER_SLAYER_SCYTHE)
                    .innateSkill(Styles.MOUNT, (itemstack) -> AVSkills.ENDER_SLAYER_SCYTHE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsEnderblaster.ENDERBLASTER_TWOHAND_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.MOUNT, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.MOUNT, LivingMotions.MOUNT, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.MOUNT, LivingMotions.IDLE, Animations.BIPED_MOUNT)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsAVSpear.AV_SPEAR_GUARD);

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
                            AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_AUTO1,
                            AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_AUTO2,
                            AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_AUTO3,
                            AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_AUTO4,
                            AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_AUTO5,
                            AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_DASH,
                            AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_AIRSLASH
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.DEMONIAC_VOLTAGE_REAVER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.ELITE_HOLD_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.ELITE_WALK_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.ELITE_RUN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.ELITE_RUN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

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
                            AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_AUTO1,
                            AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_AUTO2,
                            AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_AUTO3,
                            AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_AUTO4,
                            AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_AUTO5,
                            AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_DASH,
                            AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_AIRSLASH
                    ).newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.OBSIDIAN_SLEDGEHAMMER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AVAnimations.ELITE_HOLD_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AVAnimations.ELITE_WALK_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.ELITE_RUN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.ELITE_RUN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD);

    public static final Function<Item, CapabilityItem.Builder> NULL_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsNullWeapon.NULL_WEAPON_AUTO1,
                            AnimsNullWeapon.NULL_WEAPON_AUTO2,
                            AnimsNullWeapon.NULL_WEAPON_AUTO3,
                            AnimsNullWeapon.NULL_WEAPON_AUTO4,
                            AnimsNullWeapon.NULL_WEAPON_AUTO5,
                            AnimsNullWeapon.NULL_WEAPON_DASH,
                            AnimsNullWeapon.NULL_WEAPON_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.NULL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsNullWeapon.NULL_WEAPON_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsNullWeapon.NULL_WEAPON_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsNullWeapon.NULL_WEAPON_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsNullWeapon.NULL_WEAPON_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD);

    public static final Function<Item, CapabilityItem.Builder> OBSIDIAN_WEAPON = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_1,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_2,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_3,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_4,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_TWOHAND_1,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_DASH,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.OBSIDIAN_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EFNDualSwordAnimations.NF_DUAL_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EFNDualSwordAnimations.NF_DUAL_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AVAnimations.HEROBRINE_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AVAnimations.HEROBRINE_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD);
    
    public static final Function<Item, CapabilityItem.Builder> SHADOW_OBSIDIAN_PILLAR = (item) ->
            WeaponCapability.builder().category(WeaponCategories.SWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()) ? Styles.OCHS : Styles.TWO_HAND)
                    .collider(ColliderPreset.FIST)
                    .hitSound(EpicFightSounds.BLUNT_HIT_HARD.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_1,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_2,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_3,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_4,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_TWOHAND_2,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_DASH,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_AIRSLASH)
                    .newStyleCombo(Styles.OCHS,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_LEFT_1,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_2,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_LEFT_2,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_4,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_LEFT_3,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_DASH,
                            AnimsObsidianWeapon.OBSIDIAN_WEAPON_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_PILLAR)
                    .innateSkill(Styles.OCHS,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_PILLAR_SWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, EFNDualSwordAnimations.NF_DUAL_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, EFNDualSwordAnimations.NF_DUAL_WALK)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.HEROBRINE_RUN)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, AVAnimations.HEROBRINE_RUN)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, AVAnimations.FIST_GUARD)
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
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_AUTO1,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_AUTO2,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_AUTO3,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_AUTO4,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DASH,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_AIRSLASH)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_AUTO1,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_AUTO2,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_AUTO3,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_AUTO4,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_DASH,
                            AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_AIRSLASH)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_SWORD)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.SHADOW_OBSIDIAN_SWORD_DUAL)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, EFNDualSwordAnimations.NF_DUAL_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, EFNDualSwordAnimations.NF_DUAL_WALK)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, AVAnimations.HEROBRINE_RUN)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, AVAnimations.HEROBRINE_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AnimsMoonless.MOONLESS_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AVAnimations.FIST_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()));

    public static final Function<Item, Builder> LEGENDARY_SWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()) || livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()) ? Styles.OCHS : Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsLegendarySword.LEGENDARY_SWORD_AUTO1,
                            AnimsLegendarySword.LEGENDARY_SWORD_AUTO2,
                            AnimsLegendarySword.LEGENDARY_SWORD_AUTO3,
                            AnimsLegendarySword.LEGENDARY_SWORD_AUTO4,
                            AnimsLegendarySword.LEGENDARY_SWORD_AUTO5,
                            AnimsLegendarySword.LEGENDARY_SWORD_DASH,
                            AnimsLegendarySword.LEGENDARY_SWORD_AIRSLASH
                    ).newStyleCombo(Styles.OCHS,
                            AnimsLegendarySword.LEGENDARY_SWORD_WOOPIE_AUTO1,
                            StraightSwordAnimations.STRAIGHTSWORD_DUAL_AUTO1,
                            StraightSwordAnimations.STRAIGHTSWORD_DUAL_DASH_LIGHT,
                            AnimsLegendarySword.LEGENDARY_SWORD_AUTO4,
                            AnimsLegendarySword.LEGENDARY_SWORD_AUTO5,
                            AnimsLegendarySword.LEGENDARY_SWORD_DASH,
                            AnimsLegendarySword.LEGENDARY_SWORD_AIRSLASH
                    ).newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.COMMON,
                            (itemstack) -> AVSkills.LEGENDARY_SWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, AnimsLegendarySword.LEGENDARY_SWORD_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, WOMAnimations.TORMENT_BERSERK_WALK)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, WOMAnimations.TORMENT_RUN)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, WOMAnimations.TORMENT_RUN)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, AnimsLegendarySword.LEGENDARY_SWORD_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof BlueDemonTridentItem || livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof WoopieTheSwordItem);

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
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO1,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_DASH,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO3)
                    .newStyleCombo(Styles.TWO_HAND,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO1,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO2,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO3,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO4,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO5,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AUTO6,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_DASH,
                            AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_AIRSLASH)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> EpicFightSkills.WRATHFUL_LIGHTING)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.TRIDENT_FESTIVAL)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, EFNDualSwordAnimations.NF_DUAL_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, EFNDualSwordAnimations.NF_DUAL_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.HOLD_ONEHAND_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.HOLD_ONEHAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_TWOHAND_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_TWOHAND_RUN)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()));

    private static WeaponCapability.Builder avSwordTemplate() {
        return WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.SWORD
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.AXE
                            && livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != WeaponCategories.TACHI ? Styles.ONE_HAND : Styles.TWO_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    EFNSwordAnimations.NF_SWORD_AUTO1,
                    EFNSwordAnimations.NF_SWORD_AUTO2,
                    StraightSwordAnimations.STRAIGHTSWORD_AUTO4,
                    StraightSwordAnimations.STRAIGHTSWORD_AUTO3,
                    StraightSwordAnimations.STRAIGHTSWORD_AUTO5,
                    StraightSwordAnimations.STRAIGHTSWORD_DODGE_PURSUIT,
                    EFNSwordAnimations.NF_SWORD_AIRSLASH)
            .newStyleCombo(Styles.TWO_HAND,
                    StraightSwordAnimations.STRAIGHTSWORD_DUAL_AUTO1,
                    StraightSwordAnimations.STRAIGHTSWORD_DUAL_AUTO2,
                    StraightSwordAnimations.STRAIGHTSWORD_DUAL_AUTO3,
                    DarkNightPursuitersAnimations.DP_AUTO_1,
                    DarkNightPursuitersAnimations.DP_AUTO_2,
                    EFNDualSwordAnimations.NF_DUAL_DASH,
                    EFNDualSwordAnimations.NF_DUAL_AIRSLASH)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, EFNSwordAnimations.NF_SWORD_IDLE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, EFNSwordAnimations.NF_SWORD_WALK)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.HOLD_ONEHAND_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.HOLD_ONEHAND_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EFNSwordAnimations.NF_SWORD_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, EFNSwordAnimations.NF_SWORD_RUN)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD
                            || (livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.TACHI)));
    }

    public static final Function<Item, Builder> AV_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_SWORD);

    public static final Function<Item, Builder> WOOPIE_THE_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.WOOPIE_THE_SWORD);

    public static final Function<Item, Builder> GREAT_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.GREAT_SWORD);

    public static final Function<Item, Builder> THUNDER_DIAMOND_BLADE = (item) -> avSwordTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.THUNDER_DIAMOND_BLADE)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_THUNDER_DIAMOND_BLADE);

    public static final Function<Item, Builder> BLACK_FIRE_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.BLACK_FIRE_SWORD);

    public static final Function<Item, Builder> DIAMOND_ATTRACTOR_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.DIAMOND_ATTRACTOR_SWORD);

    public static final Function<Item, Builder> DIAMOND_BLASTER_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.DIAMOND_BLASTER_SWORD);

    public static final Function<Item, Builder> HACKER_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.COMMON,
                    (itemstack) -> AVSkills.HACKER_SWORD);

    public static final Function<Item, Builder> HOOK_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.HOOK_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_HOOK_SWORD);

    public static final Function<Item, Builder> FLANKER_HOOK_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.FLANKER_HOOK_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_HOOK_SWORD);

    public static final Function<Item, Builder> DNAX_HOOK_SWORD = (item) -> avSwordTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.DNAX_HOOK_SWORD)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_DNAX_HOOK_SWORD);

    public static final Function<Item, Builder> AV_TACHI = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.TACHI)
                    .collider(ColliderPreset.TACHI)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .newStyleCombo(Styles.TWO_HAND,
                            EFNTachiAnimations.NF_TACHI_AUTO1,
                            EFNTachiAnimations.NF_TACHI_AUTO2,
                            EFNTachiAnimations.NF_TACHI_AUTO3,
                            AnimsAVTachi.AV_TACHI_AUTO4,
                            AnimsAVTachi.AV_TACHI_AUTO5,
                            EFNTachiAnimations.NF_TACHI_DASH,
                            AnimsAVTachi.AV_TACHI_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.TACHI)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EFNTachiAnimations.NF_TACHI_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EFNTachiAnimations.NF_TACHI_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EFNTachiAnimations.NF_TACHI_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, EFNTachiAnimations.NF_TACHI_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

    public static final Function<Item, Builder> AV_LONGSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.LONGSWORD)
                    .styleProvider(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .collider(ColliderPreset.LONGSWORD)
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .newStyleCombo(Styles.ONE_HAND,
                            StraightSwordAnimations.STRAIGHTSWORD_HEAVY_AUTO1,
                            StraightSwordAnimations.STRAIGHTSWORD_HEAVY_AUTO3,
                            StraightSwordAnimations.STRAIGHTSWORD_HEAVY_AUTO4,
                            EFNSwordAnimations.NF_SWORD_AUTO3,
                            EFNSwordAnimations.NF_SWORD_AUTO4,
                            StraightSwordAnimations.STRAIGHTSWORD_DASH_HEAVY,
                            StraightSwordAnimations.STRAIGHTSWORD_AIR_SLASH_LIGHT)
                    .innateSkill(Styles.ONE_HAND,
                            (itemstack) -> AVSkills.LONGSWORD)
                    .newStyleCombo(Styles.TWO_HAND,
                            DarkNightPursuitersAnimations.DP_HEAVY_AUTO_3,
                            AnimsAVLongsword.AV_LONGSWORD_DUAL_AUTO2,
                            AnimsAVLongsword.AV_LONGSWORD_DUAL_AUTO3,
                            AnimsAVLongsword.AV_LONGSWORD_DUAL_AUTO4,
                            AnimsAVLongsword.AV_LONGSWORD_DUAL_AUTO5,
                            AnimsAVLongsword.AV_LONGSWORD_DUAL_DASH,
                            DarkNightPursuitersAnimations.DP_NIGHT_FALL)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.DUAL_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, EFNSwordAnimations.NF_SWORD_IDLE)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, EFNSwordAnimations.NF_SWORD_WALK)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, EFNSwordAnimations.NF_SWORD_RUN)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, EFNSwordAnimations.NF_SWORD_RUN)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, AnimsRuine.RUINE_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .weaponCombinationPredicator(
                            (livingentitypatch) -> livingentitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.LONGSWORD);

    public static final Function<Item, Builder> AV_GREATSWORD = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            EFNGreatSwordAnimations.NG_GREATSWORD_AUTO1,
                            EFNGreatSwordAnimations.NG_GREATSWORD_AUTO2,
                            EFNGreatSwordAnimations.NG_GREATSWORD_AUTO3,
                            AnimsAVGreatsword.AV_GREATSWORD_AUTO4,
                            AnimsAVGreatsword.AV_GREATSWORD_AUTO5,
                            EFNGreatSwordAnimations.NG_GREATSWORD_DASH,
                            AnimsAVGreatsword.AV_GREATSWORD_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.HELICOPTER)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EFNGreatSwordAnimations.NG_GREATSWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EFNGreatSwordAnimations.NG_GREATSWOED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EFNGreatSwordAnimations.NG_GREATSWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, EFNGreatSwordAnimations.NG_GREATSWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> AV_GREATAXE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                    .collider(ColliderPreset.GREATSWORD)
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .newStyleCombo(Styles.TWO_HAND,
                            EFNGreatSwordAnimations.NG_GREATSWORD_AUTO1,
                            EFNGreatSwordAnimations.NG_GREATSWORD_AUTO2,
                            EFNGreatSwordAnimations.NG_GREATSWORD_AUTO3,
                            AnimsAVGreatsword.AV_GREATAXE_AUTO4,
                            AnimsAVGreatsword.AV_GREATAXE_AUTO5,
                            AnimsAVGreatsword.AV_GREATAXE_DASH,
                            EFNGreatSwordAnimations.NG_GREATSWORD_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> AVSkills.GREATAXE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EFNGreatSwordAnimations.NG_GREATSWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EFNGreatSwordAnimations.NG_GREATSWOED_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EFNGreatSwordAnimations.NG_GREATSWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, EFNGreatSwordAnimations.NG_GREATSWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsSolar.SOLAR_GUARD);

    public static final Function<Item, Builder> CRAFTING_TABLE = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.GREATSWORD)
                    .collider(ColliderPreset.TACHI)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(AVSounds.SWORD_WHOOSH.get())
                    .styleProvider((livingEntityPatch) -> Styles.TWO_HAND)
                    .newStyleCombo(Styles.TWO_HAND,
                            EFNTachiAnimations.NF_TACHI_AUTO1,
                            EFNTachiAnimations.NF_TACHI_AUTO2,
                            EFNTachiAnimations.NF_TACHI_AUTO3,
                            AnimsAVTachi.AV_TACHI_AUTO4,
                            AnimsAVTachi.AV_TACHI_AUTO5,
                            EFNGreatSwordAnimations.NG_GREATSWORD_DASH,
                            AnimsAVGreatsword.AV_GREATSWORD_AIRSLASH)
                    .innateSkill(Styles.TWO_HAND,
                            (itemstack) -> AVSkills.HELICOPTER)
                    .newStyleCombo(Styles.MOUNT,
                            Animations.SWORD_MOUNT_ATTACK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsAVGreatsword.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsAVGreatsword.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsAVGreatsword.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsAVGreatsword.CARRY)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsAVGreatsword.CARRY);

    private static WeaponCapability.Builder avAxeTemplate() {
        return WeaponCapability.builder()
                .category(WeaponCategories.AXE)
                .styleProvider((livingentitypatch) -> Styles.ONE_HAND)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.LONGSWORD)
                .swingSound(AVSounds.SWORD_WHOOSH.get())
                .newStyleCombo(Styles.ONE_HAND,
                        AnimsAVAxe.AV_AXE_AUTO1,
                        AnimsAVAxe.AV_AXE_AUTO2,
                        AnimsAVAxe.AV_AXE_AUTO3,
                        AnimsAVAxe.AV_AXE_AUTO4,
                        AnimsAVAxe.AV_AXE_AUTO5,
                        AnimsAVAxe.AV_AXE_DASH,
                        AnimsAVAxe.AV_AXE_AIRSLASH)
                .newStyleCombo(Styles.MOUNT,
                        Animations.SWORD_MOUNT_ATTACK)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, AnimsAVAxe.AV_AXE_IDLE)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AnimsAVAxe.AV_AXE_RUN)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AnimsAVAxe.AV_AXE_RUN)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, AnimsAVAxe.AV_AXE_WALK);
    }

    public static final Function<Item, Builder> AV_AXE = (item) -> avAxeTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.AXE);

    public static final Function<Item, Builder> EARTH_AXE = (item) -> avAxeTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.EARTH_AXE);

    public static final Function<Item, Builder> RED_AXE = (item) -> avAxeTemplate()
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.GREATAXE);

    public static final Function<Item, Builder> AV_DUAL_AXE = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.AXE)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider(
                    (livingentitypatch) -> ((livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE_GREEN.get()))
                            && (livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE_GREEN.get())))
                            || ((livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.GOLDEN_MACE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.DIAMOND_MACE.get()))
                            && (livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.GOLDEN_MACE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.DIAMOND_MACE.get()))) ? Styles.TWO_HAND : Styles.ONE_HAND)
            .collider(ColliderPreset.SWORD)
            .newStyleCombo(Styles.ONE_HAND,
                    AnimsAVAxe.AV_AXE_AUTO1,
                    AnimsAVAxe.AV_AXE_AUTO2,
                    AnimsAVAxe.AV_AXE_AUTO3,
                    AnimsAVAxe.AV_AXE_AUTO4,
                    AnimsAVAxe.AV_AXE_AUTO5,
                    AnimsAVAxe.AV_AXE_DASH,
                    AnimsAVAxe.AV_AXE_AIRSLASH)
            .newStyleCombo(Styles.TWO_HAND,
                    StraightSwordAnimations.STRAIGHTSWORD_DUAL_AUTO1,
                    StraightSwordAnimations.STRAIGHTSWORD_DUAL_AUTO2,
                    StraightSwordAnimations.STRAIGHTSWORD_DUAL_AUTO3,
                    DarkNightPursuitersAnimations.DP_AUTO_1,
                    DarkNightPursuitersAnimations.DP_AUTO_2,
                    EFNDualSwordAnimations.NF_DUAL_DASH,
                    EFNDualSwordAnimations.NF_DUAL_AIRSLASH)
            .newStyleCombo(Styles.MOUNT,
                    Animations.SWORD_DUAL_AUTO1,
                    Animations.SWORD_DUAL_AUTO2,
                    Animations.SWORD_DUAL_AUTO3,
                    Animations.SWORD_MOUNT_ATTACK)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.AXE)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.DUAL_AXE_SPIN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, AnimsAVAxe.AV_AXE_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AnimsAVAxe.AV_AXE_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AnimsAVAxe.AV_AXE_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, AnimsAVAxe.AV_AXE_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, EFNSwordAnimations.NF_SWORD_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, EFNSwordAnimations.NF_SWORD_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, EFNSwordAnimations.NF_SWORD_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, EFNSwordAnimations.NF_SWORD_RUN)
            .weaponCombinationPredicator(
                    (livingentitypatch) -> ((livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE_GREEN.get()))
                            && (livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE_GREEN.get())))
                            || ((livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.GOLDEN_MACE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(AnnoyingVillagersModItems.DIAMOND_MACE.get()))
                            && (livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.GOLDEN_MACE.get())
                            || livingentitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).getItem().equals(AnnoyingVillagersModItems.DIAMOND_MACE.get()))));

    public static final Function<Item, Builder> AV_DAGGER = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.DAGGER)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .styleProvider((livingentitypatch) -> Styles.ONE_HAND)
            .collider(ColliderPreset.DAGGER)
            .newStyleCombo(Styles.ONE_HAND,
                    AnimsAVSword.AV_DAGGER_AUTO1,
                    AnimsAVSword.AV_DAGGER_AUTO2,
                    EFNShortSwordAnimations.NF_SHORTSWORD_AUTO1,
                    EFNShortSwordAnimations.NF_SHORTSWORD_AUTO2,
                    EFNShortSwordAnimations.NF_SHORTSWORD_AUTO3,
                    EFNShortSwordAnimations.NF_SHORTSWORD_DASH,
                    EFNShortSwordAnimations.NF_SHORTSWORD_AIRSLASH)
            .innateSkill(Styles.ONE_HAND,
                    (itemstack) -> AVSkills.DAGGER)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, EFNSwordAnimations.NF_SWORD_IDLE)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, AVAnimations.HOLD_ONEHAND_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, AVAnimations.HOLD_ONEHAND_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, EFNSwordAnimations.NF_SWORD_WALK);

    private static WeaponCapability.Builder avSpearTemplate() {
        return WeaponCapability.builder()
                .category(WeaponCategories.SPEAR)
                .styleProvider((livingentitypatch) -> Styles.TWO_HAND)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.SPEAR)
                .swingSound(AVSounds.SWORD_WHOOSH.get())
                .newStyleCombo(Styles.TWO_HAND,
                        AnimsAVSpear.AV_SPEAR_AUTO1,
                        AnimsAVSpear.AV_SPEAR_AUTO2,
                        AnimsAVSpear.AV_SPEAR_AUTO3,
                        AnimsAVSpear.AV_SPEAR_AUTO4,
                        AnimsAVSpear.AV_SPEAR_AUTO5,
                        AnimsAVSpear.AV_SPEAR_DASH,
                        AnimsAVSpear.AV_SPEAR_AIRSLASH)
                .newStyleCombo(Styles.MOUNT,
                        Animations.SWORD_MOUNT_ATTACK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsAVSpear.AV_SPEAR_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, AnimsAVSpear.AV_SPEAR_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsAVSpear.AV_SPEAR_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsAVSpear.AV_SPEAR_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsAVSpear.AV_SPEAR_WALK);
    }

    public static final Function<Item, Builder> AV_SPEAR = (item) -> avSpearTemplate()
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.SPEAR);

    public static final Function<Item, Builder> STAFF = (item) -> avSpearTemplate()
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.STAFF);

    public static final Function<Item, Builder> SICKLE = (item) -> avSpearTemplate()
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.SICKLE);

    public static final Function<Item, CapabilityItem.Builder> BOLT = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SPEAR)
            .styleProvider(
                    (livingentitypatch) -> Styles.TWO_HAND)
            .zoomInType(CapabilityItem.ZoomInType.USE_TICK)
            .collider(ColliderPreset.SPEAR)
            .swingSound(AVSounds.SWORD_WHOOSH.get())
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.TWO_HAND,
                    AnimsAVSpear.AV_SPEAR_AUTO1,
                    AnimsAVSpear.AV_SPEAR_AUTO2,
                    AnimsAVSpear.AV_SPEAR_AUTO3,
                    AnimsAVSpear.AV_SPEAR_AUTO4,
                    AnimsAVSpear.AV_SPEAR_AUTO5,
                    AnimsAVSpear.AV_SPEAR_DASH,
                    AnimsAVSpear.AV_SPEAR_AIRSLASH)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.SPEAR)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsAVSpear.AV_SPEAR_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, AnimsAVSpear.AV_SPEAR_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, AnimsAVSpear.AV_SPEAR_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimsAVSpear.AV_SPEAR_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.AIM, Animations.BIPED_JAVELIN_AIM)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SHOT, Animations.BIPED_JAVELIN_THROW)
            .constructor(AVThrowableSpearCapability::new);

    public static final Function<Item, CapabilityItem.Builder> BLACK_SCRATCHER = (item) -> WeaponCapability.builder()
            .category(WeaponCategories.SWORD)
            .styleProvider(
                    (livingentitypatch) -> Styles.TWO_HAND)
            .collider(ColliderPreset.SPEAR)
            .swingSound(SoundEvents.SHEEP_SHEAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .canBePlacedOffhand(false)
            .newStyleCombo(Styles.TWO_HAND,
                    AnimsAVSpear.BLACKSCRATCHER_ATTACK,
                    AnimsAVSpear.BLACKSCRATCHER_ATTACK,
                    AnimsAVSpear.BLACKSCRATCHER_ATTACK)
            .innateSkill(Styles.TWO_HAND,
                    (itemstack) -> AVSkills.BLACKSCRATCHER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimsAVSpear.BLACKSCRATCHER_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK);

    public static void register(WeaponCapabilityPresetRegistryEvent weaponcapabilitypresetregistryevent) {
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_aegis"), AVWeaponCapabilityPresets.ENDER_AEGIS);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_glaive"), AVWeaponCapabilityPresets.ENDER_GLAIVE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "demoniac_voltage_reaver"), AVWeaponCapabilityPresets.DEMONIAC_VOLTAGE_REAVER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_sledgehammer"), AVWeaponCapabilityPresets.OBSIDIAN_SLEDGEHAMMER);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "ender_slayer_scythe"), AVWeaponCapabilityPresets.ENDER_SLAYER_SCYTHE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "null_weapon"), AVWeaponCapabilityPresets.NULL_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "obsidian_weapon"), AVWeaponCapabilityPresets.OBSIDIAN_WEAPON);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shadow_obsidian_pillar"), AVWeaponCapabilityPresets.SHADOW_OBSIDIAN_PILLAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "shadow_obsidian_sword"), AVWeaponCapabilityPresets.SHADOW_OBSIDIAN_SWORD);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "legendary_sword"), AVWeaponCapabilityPresets.LEGENDARY_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blue_demon_trident"), AVWeaponCapabilityPresets.BLUE_DEMON_TRIDENT);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_sword"), AVWeaponCapabilityPresets.AV_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "woopie_the_sword"), AVWeaponCapabilityPresets.WOOPIE_THE_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "great_sword"), AVWeaponCapabilityPresets.GREAT_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "black_fire_sword"), AVWeaponCapabilityPresets.BLACK_FIRE_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "thunder_diamond_blade"), AVWeaponCapabilityPresets.THUNDER_DIAMOND_BLADE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_attractor_sword"), AVWeaponCapabilityPresets.DIAMOND_ATTRACTOR_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "diamond_blaster_sword"), AVWeaponCapabilityPresets.DIAMOND_BLASTER_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hacker_sword"), AVWeaponCapabilityPresets.HACKER_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hook_sword"), AVWeaponCapabilityPresets.HOOK_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "flanker_hook_sword"), AVWeaponCapabilityPresets.FLANKER_HOOK_SWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "dnax_hook_sword"), AVWeaponCapabilityPresets.DNAX_HOOK_SWORD);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_tachi"), AVWeaponCapabilityPresets.AV_TACHI);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_longsword"), AVWeaponCapabilityPresets.AV_LONGSWORD);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_greatsword"), AVWeaponCapabilityPresets.AV_GREATSWORD);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_greataxe"), AVWeaponCapabilityPresets.AV_GREATAXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "crafting_table"), AVWeaponCapabilityPresets.CRAFTING_TABLE);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_axe"), AVWeaponCapabilityPresets.AV_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "earth_axe"), AVWeaponCapabilityPresets.EARTH_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "red_axe"), AVWeaponCapabilityPresets.RED_AXE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_dual_axe"), AVWeaponCapabilityPresets.AV_DUAL_AXE);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_dagger"), AVWeaponCapabilityPresets.AV_DAGGER);

        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "av_spear"), AVWeaponCapabilityPresets.AV_SPEAR);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "staff"), AVWeaponCapabilityPresets.STAFF);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "sickle"), AVWeaponCapabilityPresets.SICKLE);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "bolt"), AVWeaponCapabilityPresets.BOLT);
        weaponcapabilitypresetregistryevent.getTypeEntry().put(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blackscratcher"), AVWeaponCapabilityPresets.BLACK_SCRATCHER);
    }
}
