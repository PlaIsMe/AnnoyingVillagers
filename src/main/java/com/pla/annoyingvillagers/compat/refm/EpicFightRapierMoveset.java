package com.pla.annoyingvillagers.compat.refm;

import com.pla.annoyingvillagers.combatbehaviour.AvNpcSpear;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.yonchi.refm.world.capabilities.item.RapierWeaponCategories;
import net.yonchi.refm.world.item.RapierAddonItems;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class EpicFightRapierMoveset {
    public static CECombatBehaviors.Builder<MobPatch<?>> overideRapierMovesetWeapon(CapabilityItem mainHandCap, CapabilityItem offHandCap, Style style) {
        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(RapierAddonItems.IRON_RAPIER.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(RapierAddonItems.GOLD_RAPIER.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(RapierAddonItems.DIAMOND_RAPIER.get().getDefaultInstance())
                || mainHandCap == EpicFightCapabilities.getItemStackCapability(RapierAddonItems.NETHERITE_RAPIER.get().getDefaultInstance())) {
            return AvNpcRapier.RAPIER;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(RapierAddonItems.ENDERITE_RAPIER.get().getDefaultInstance())) {
            return AvNpcRapier.ENDER_RAPIER;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(RapierAddonItems.WITHERITE_RAPIER.get().getDefaultInstance())) {
            return AvNpcRapier.WITHER_RAPIER;
        }

        if (mainHandCap == EpicFightCapabilities.getItemStackCapability(RapierAddonItems.OCEANITE_RAPIER.get().getDefaultInstance())) {
            return AvNpcRapier.OCEAN_RAPIER;
        }

        return null;
    }

    public static boolean addRefmSpecialAttack(PlayerPatch<?> playerpatch, Entity entity, LivingEntityPatch<?> livingEntityPatch) {
        WeaponCategory mainHandCategory = playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();

        if (isRapier(mainHandCategory) && entity.level() instanceof ServerLevel) {
            livingEntityPatch.playAnimationSynchronized(AnimsEpicFightBattleArts.SABRE_QUAD_STING, 0.0F);
            return true;
        }

        return false;
    }

    private static boolean isRapier(WeaponCategory category) {
        return category == RapierWeaponCategories.RAPIER
                || category == RapierWeaponCategories.ENDER_RAPIER
                || category == RapierWeaponCategories.OCEAN_RAPIER
                || category == RapierWeaponCategories.WITHER_RAPIER
                || category == RapierWeaponCategories.AMETHYST_RAPIER;
    }
}
