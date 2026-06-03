package com.pla.annoyingvillagers.util;

import com.p1nero.epicfightbow.item.EFBowItems;
import com.pla.annoyingvillagers.combatbehaviour.*;
import com.pla.annoyingvillagers.compat.p1nero_bow.NpcP1neroBow;
import com.pla.annoyingvillagers.compat.p1nero_bow.NpcP1neroMortisBow;
import com.pla.annoyingvillagers.compat.p1nero_bow.PlayerNpcP1neroBow;
import com.pla.annoyingvillagers.compat.p1nero_bow.PlayerNpcP1neroMortisBow;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

public class MobPatchCommon {
    public static CECombatBehaviors.Builder<MobPatch<?>> overideCustomWeaponMotionBuilderForAvNpc(CapabilityItem mainHandCap, Style style) {
        CECombatBehaviors.Builder<MobPatch<?>> avNpcWeaponOverride = overideRequestedAvNpcWeaponMotionBuilder(mainHandCap, style);
        if (avNpcWeaponOverride != null) {
            return avNpcWeaponOverride;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.DIAMOND_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.GOLDEN_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.IRON_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.STONE_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.WOODEN_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLACK_FIRE_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.BLACK_FIRE_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_BLACK_FIRE_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLUE_FLAME_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.BLUE_FLAME_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_BLUE_FLAME_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CENTRANOS_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_CLEAVER.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.CLEAVER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CLOW_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.CLOW_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_CLOW_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_ATTRACTOR_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.DIAMOND_ATTRACTOR_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_DIAMOND_ATTRACTOR_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BLASTER_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.DIAMOND_BLASTER_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_DIAMOND_BLASTER_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HACKER_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.HACKER_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_HACKER_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_WARBLADE.get().getDefaultInstance())) {
            return AvNpcTachi.DIAMOND_WARBLADE;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LAEVATEINN.get().getDefaultInstance())) {
            return AvNpcTachi.DIAMOND_LAEVATEINN;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_FALCHION.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREAT_FALCHION.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_FALCHION.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcTachi.FALCHION;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcTachi.DUAL_FALCHION;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SABRE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_SABRE.get().getDefaultInstance())) {
            return AvNpcLongsword.DIAMOND_SABRE;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_IRON_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_GOLDEN_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.HOOK_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_HOOK_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.FLANKER_HOOKED_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.FLANKER_HOOK_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_HOOK_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DNAX_HOOKED_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.DNAX_HOOK_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_DNAX_HOOK_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_LONGSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_LONGSWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcLongsword.AV_LONGSWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcLongsword.DUAL_AV_LONGSWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_CHIPPED_LONGSWORD.get().getDefaultInstance())) {
            return AvNpcLongsword.CHIPPED_LONGSWORD;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREATSWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_GREATSWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.AV_GREATSWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.JADE_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RED_DIAMOND_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_KNIGHT_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_KNIGHT_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.PALADIN_SWORD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GREAT_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.EARTH_AXE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcAxe.EARTH_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RED_AXE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcAxe.RED_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BATTLEAXE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.BATTLE_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GIANT_NETHERITE_AXE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.GIANT_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE_GREEN.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_MACE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_MACE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcAxe.EXTERMINATOR_BATTLE_AXE;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcAxe.DUAL_EXTERMINATOR_BATTLE_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_GREATAXE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREATAXE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_GREATAXE.get().getDefaultInstance())) {
            return AvNpcGreatsword.GREATAXE;
        }


        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_HALBERD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_DOUBLE_BLADED_HALBERD.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_HALBERD.get().getDefaultInstance())) {
            return AvNpcAxe.HALBERD;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SAMANTHA_THE_KILLER_AXE.get().getDefaultInstance())) {
            return AvNpcAxe.KILLER_AXE;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.KNIFE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_KNIFE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_KNIFE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcDagger.KNIFE;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcDagger.DUAL_KNIFE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_ARMBLADE.get().getDefaultInstance())) {
            return AvNpcDagger.ARM_BLADE;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_MOON_BLADE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_MOON_BLADE.get().getDefaultInstance())) {
            return AvNpcDagger.MOON_BLADE;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_CLAW.get().getDefaultInstance())) {
            return AvNpcDagger.CLAW;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SPEAR_AXE.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BOLT.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SPEAR.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_SPEAR.get().getDefaultInstance())) {
            return AvNpcSpear.GUANDAO;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.TWIN_DIAMOND_SPEAR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSpear.GUANDAO;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.SPEAR_STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_TWIN_BLADE_KATANA.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DOUBLE_DIAMOND_GLAIVE.get().getDefaultInstance())) {
            return AvNpcSpear.SPEAR_STAFF;
        }


        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLACKSCRATCHER.get().getDefaultInstance())) {
            return AvNpcSpear.BLACK_SCRATCHER;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SICKLE.get().getDefaultInstance()) || mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_SICKLE.get().getDefaultInstance())) {
            return AvNpcSpear.SICKLE;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.WOODEN_DOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcBlockWeapon.WOODEN_DOOR;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CRAFTING_TABLE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcBlockWeapon.CRAFTING_TABLE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.LADDER.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcBlockWeapon.LADDER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.TRAPDOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcBlockWeapon.TRAPDOOR;
            }
        }

        return null;
    }
    private static CECombatBehaviors.Builder<MobPatch<?>> overideRequestedAvNpcWeaponMotionBuilder(CapabilityItem mainHandCap, Style style) {
        if (matches(mainHandCap,
                AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD,
                AnnoyingVillagersModItems.HOOKED_IRON_SWORD,
                AnnoyingVillagersModItems.HOOKED_GOLDEN_SWORD)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.HOOK_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_HOOK_SWORD;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.FLANKER_HOOKED_SWORD)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.FLANKER_HOOK_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_HOOK_SWORD;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.DNAX_HOOKED_SWORD)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSword.DNAX_HOOK_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSword.DUAL_DNAX_HOOK_SWORD;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_SABRE,
                AnnoyingVillagersModItems.NETHERITE_SABRE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcLongsword.DIAMOND_SABRE;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_HALBERD,
                AnnoyingVillagersModItems.IRON_HALBERD)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcAxe.HALBERD;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.IRON_DOUBLE_BLADED_HALBERD)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcAxe.DOUBLE_HALBERD;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.SAMANTHA_THE_KILLER_AXE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcAxe.KILLER_AXE;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.EARTH_AXE)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcAxe.EARTH_AXE;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.RED_AXE)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcAxe.RED_AXE;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE,
                AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE_GREEN,
                AnnoyingVillagersModItems.GOLDEN_MACE,
                AnnoyingVillagersModItems.DIAMOND_MACE)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcAxe.EXTERMINATOR_BATTLE_AXE;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcAxe.DUAL_EXTERMINATOR_BATTLE_AXE;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_SPEAR,
                AnnoyingVillagersModItems.NETHERITE_SPEAR,
                AnnoyingVillagersModItems.SPEAR_AXE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.GUANDAO;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DOUBLE_DIAMOND_GLAIVE,
                AnnoyingVillagersModItems.IRON_TWIN_BLADE_KATANA)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.SPEAR_STAFF;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.TWIN_DIAMOND_SPEAR)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcSpear.GUANDAO;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.SPEAR_STAFF;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_SICKLE,
                AnnoyingVillagersModItems.IRON_SICKLE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.SICKLE;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.DIAMOND_BOLT)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.BOLT;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.BLACKSCRATCHER)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcSpear.BLACK_SCRATCHER;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.DIAMOND_WARBLADE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcTachi.DIAMOND_WARBLADE;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.DIAMOND_LAEVATEINN)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcTachi.DIAMOND_LAEVATEINN;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_FALCHION,
                AnnoyingVillagersModItems.DIAMOND_GREAT_FALCHION,
                AnnoyingVillagersModItems.NETHERITE_FALCHION)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcTachi.FALCHION;
            } else if (style == CapabilityItem.Styles.OCHS) {
                return AvNpcTachi.DUAL_FALCHION;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_LONGSWORD,
                AnnoyingVillagersModItems.GOLDEN_LONGSWORD,
                AnnoyingVillagersModItems.IRON_LONGSWORD,
                AnnoyingVillagersModItems.RUBY_LONGSWORD)) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return AvNpcLongsword.AV_LONGSWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcLongsword.DUAL_AV_LONGSWORD;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.DIAMOND_CHIPPED_LONGSWORD)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcLongsword.CHIPPED_LONGSWORD;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_GREATSWORD,
                AnnoyingVillagersModItems.RUBY_GREATSWORD)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.AV_GREATSWORD;
            }
        }

        if (matches(mainHandCap,
                AnnoyingVillagersModItems.DIAMOND_GREATAXE,
                AnnoyingVillagersModItems.IRON_GREATAXE,
                AnnoyingVillagersModItems.NETHERITE_GREATAXE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.GREATAXE;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.GIANT_NETHERITE_AXE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.GIANT_AXE;
            }
        }

        if (matches(mainHandCap, AnnoyingVillagersModItems.DIAMOND_BATTLEAXE)) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return AvNpcGreatsword.BATTLE_AXE;
            }
        }

        return null;
    }

    @SafeVarargs
    private static boolean matches(CapabilityItem mainHandCap, RegistryObject<Item>... items) {
        for (RegistryObject<Item> item : items) {
            if (mainHandCap == EpicFightCapabilities.getItemStackCapability(item.get().getDefaultInstance())) {
                return true;
            }
        }
        return false;
    }

    public static CECombatBehaviors.Builder<MobPatch<?>> overideCustomWeaponMotionBuilderForShadowHerobrine(CapabilityItem mainHandCap, Style style) {
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return HerobrineObsidianWeapon.OBSIDIAN_WEAPON;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return HerobrineShadowObsidianPillar.SHADOW_OBSIDIAN_PILLAR_WEAPON;
            } else if (style == CapabilityItem.Styles.OCHS) {
                return HerobrineShadowObsidianPillar.SHADOW_OBSIDIAN_PILLAR_SWORD_WEAPON;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return HerobrineShadowObsidianSword.SHADOW_OBSIDIAN_DUAL_SWORD;
            } else if (style == CapabilityItem.Styles.ONE_HAND) {
                return HerobrineShadowObsidianSword.SHADOW_OBSIDIAN_SWORD;
            }
        }

        return null;
    }

    public static CECombatBehaviors.Builder<MobPatch<?>> overideBowMotionBuilderForNpc(CapabilityItem mainHandCap, Style style) {
        if (ModList.get().isLoaded("p1nero_bow")) {
            if (mainHandCap == EpicFightCapabilities.getItemStackCapability(EFBowItems.MORTIS.get().getDefaultInstance())) {
                return NpcP1neroMortisBow.MORTIS_BOW;
            }
            if (mainHandCap == EpicFightCapabilities.getItemStackCapability(Items.BOW.getDefaultInstance())) {
                return NpcP1neroBow.BOW;
            }
        } else {
            if (mainHandCap == EpicFightCapabilities.getItemStackCapability(Items.BOW.getDefaultInstance())) {
                return NpcBow.BOW;
            }
        }

        return null;
    }

    public static CECombatBehaviors.Builder<MobPatch<?>> overideBowMotionBuilderForPlayerNpc(CapabilityItem mainHandCap, Style style) {
        if (ModList.get().isLoaded("p1nero_bow")) {
            if (mainHandCap == EpicFightCapabilities.getItemStackCapability(EFBowItems.MORTIS.get().getDefaultInstance())) {
                return PlayerNpcP1neroMortisBow.MORTIS_BOW;
            }
            if (mainHandCap == EpicFightCapabilities.getItemStackCapability(Items.BOW.getDefaultInstance())) {
                return PlayerNpcP1neroBow.BOW;
            }
        } else {
            if (mainHandCap == EpicFightCapabilities.getItemStackCapability(Items.BOW.getDefaultInstance())) {
                return PlayerNpcBow.BOW;
            }
        }
        return null;
    }
}
