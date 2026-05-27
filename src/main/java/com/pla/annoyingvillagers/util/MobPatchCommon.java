package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.combatbehaviour.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.item.Items;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

public class MobPatchCommon {
    public static CECombatBehaviors.Builder<MobPatch<?>> overideCustomWeaponMotionBuilderForPlayerNpc(CapabilityItem mainHandCap, Style style) {
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.DIAMOND_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcStaff.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.GOLDEN_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcStaff.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.IRON_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcStaff.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.STONE_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcStaff.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.WOODEN_STAFF.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcStaff.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLACK_FIRE_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLUE_FLAME_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CENTRANOS_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CLOW_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_ATTRACTOR_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BLASTER_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_WARBLADE.get().getDefaultInstance())) {
//            return PlayerNpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_FALCHION.get().getDefaultInstance())) {
//            return PlayerNpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREAT_FALCHION.get().getDefaultInstance())) {
//            return PlayerNpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SABRE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_IRON_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_GOLDEN_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_IRON_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_CLEAVER.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LAEVATEINN.get().getDefaultInstance())) {
//            return PlayerNpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_CHIPPED_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREATSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DNAX_HOOKED_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.FLANKER_HOOKED_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_TWIN_BLADE_KATANA.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcStaff.STAFF;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.PALADIN_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_GREATSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcLongsword.AV_LONGSWORD;
//            }
//        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_KNIGHT_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.JADE_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RED_DIAMOND_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_SABRE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcSword.AV_SWORD;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcSword.AV_DUAL_SWORD;
            }
        }

//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_FALCHION.get().getDefaultInstance())) {
//            return PlayerNpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_HALBERD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREATAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BATTLEAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.EARTH_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GIANT_NETHERITE_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RED_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_DOUBLE_BLADED_HALBERD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_GREATAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_HALBERD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_GREATAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SAMANTHA_THE_KILLER_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SPEAR_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_ARMBLADE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcDagger.DAGGER;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcDagger.DUAL_DAGGER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_KNIFE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcDagger.DAGGER;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcDagger.DUAL_DAGGER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.KNIFE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcDagger.DAGGER;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcDagger.DUAL_DAGGER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_MOON_BLADE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcDagger.DAGGER;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcDagger.DUAL_DAGGER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_MOON_BLADE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcDagger.DAGGER;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcDagger.DUAL_DAGGER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_KNIFE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return PlayerNpcDagger.DAGGER;
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcDagger.DUAL_DAGGER;
            }
        }

//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLACKSCRATCHER.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BOLT.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SICKLE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SPEAR.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DOUBLE_DIAMOND_GLAIVE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_SICKLE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_SPEAR.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.TWIN_DIAMOND_SPEAR.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return PlayerNpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_MACE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_MACE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return PlayerNpcAxe.AV_AXE;
//            }
//        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.WOODEN_DOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcWoodenDoor.WOODEN_DOOR;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CRAFTING_TABLE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcCraftingTable.CRAFTING_TABLE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.LADDER.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcLadder.LADDER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.TRAPDOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return PlayerNpcTrapdoor.TRAPDOOR;
            }
        }

        return null;
    }

    public static CECombatBehaviors.Builder<MobPatch<?>> overideCustomWeaponMotionBuilderForNpc(CapabilityItem mainHandCap, Style style) {
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.DIAMOND_STAFF.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcStaff.STAFF;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.GOLDEN_STAFF.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcStaff.STAFF;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.IRON_STAFF.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcStaff.STAFF;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.STONE_STAFF.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcStaff.STAFF;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(WOMItems.WOODEN_STAFF.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcStaff.STAFF;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLACK_FIRE_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLUE_FLAME_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CENTRANOS_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CLOW_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_ATTRACTOR_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BLASTER_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_WARBLADE.get().getDefaultInstance())) {
//            return NpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_FALCHION.get().getDefaultInstance())) {
//            return NpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREAT_FALCHION.get().getDefaultInstance())) {
//            return NpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SABRE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_IRON_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_GOLDEN_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.HOOKED_IRON_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_CLEAVER.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LAEVATEINN.get().getDefaultInstance())) {
//            return NpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_CHIPPED_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREATSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DNAX_HOOKED_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.FLANKER_HOOKED_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_TWIN_BLADE_KATANA.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcStaff.STAFF;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.PALADIN_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_GREATSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_LONGSWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcLongsword.AV_LONGSWORD_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcLongsword.AV_LONGSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_KNIGHT_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RUBY_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.JADE_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RED_DIAMOND_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_SABRE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSword.AV_SWORD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSword.AV_DUAL_SWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_FALCHION.get().getDefaultInstance())) {
//            return NpcTachi.AV_TACHI;
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_HALBERD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_GREATAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BATTLEAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.EARTH_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.EXTERMINATOR_BATTLEAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GIANT_NETHERITE_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.RED_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_DOUBLE_BLADED_HALBERD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_GREATAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_HALBERD.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_GREATAXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcGreatsword.AV_GREATSWORD;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SAMANTHA_THE_KILLER_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcAxe.AV_AXE;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.SPEAR_AXE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_ARMBLADE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_KNIFE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.KNIFE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_MOON_BLADE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_MOON_BLADE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_KNIFE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcDagger.DAGGER;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcDagger.DUAL_DAGGER;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.BLACKSCRATCHER.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_BOLT.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SICKLE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_SPEAR.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DOUBLE_DIAMOND_GLAIVE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.IRON_SICKLE.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.NETHERITE_SPEAR.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }
//
//        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.TWIN_DIAMOND_SPEAR.get().getDefaultInstance())) {
//            if (style == CapabilityItem.Styles.ONE_HAND) {
//                return NpcSpear.AV_SPEAR_SHIELD;
//            } else if (style == CapabilityItem.Styles.TWO_HAND) {
//                return NpcSpear.AV_SPEAR;
//            }
//        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.GOLDEN_MACE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return NpcAxe.AV_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.DIAMOND_MACE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.ONE_HAND) {
                return NpcAxe.AV_AXE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.WOODEN_DOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return NpcWoodenDoor.WOODEN_DOOR;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.CRAFTING_TABLE.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return NpcCraftingTable.CRAFTING_TABLE;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.LADDER.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return NpcLadder.LADDER;
            }
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(AnnoyingVillagersModItems.TRAPDOOR.get().getDefaultInstance())) {
            if (style == CapabilityItem.Styles.TWO_HAND) {
                return NpcTrapdoor.TRAPDOOR;
            }
        }

        return null;
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
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(Items.BOW.getDefaultInstance())) {
            return NpcBow.BOW;
        }

        return null;
    }

    public static CECombatBehaviors.Builder<MobPatch<?>> overideBowMotionBuilderForPlayerNpc(CapabilityItem mainHandCap, Style style) {
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(Items.BOW.getDefaultInstance())) {
            return PlayerNpcBow.BOW;
        }

        return null;
    }
}
