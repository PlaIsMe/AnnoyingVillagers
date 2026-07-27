package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.gameasset.AVExecutionType;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shelmarow.combat_evolution.api.event.RegisterCustomExecutionEvent;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExecuteEvent {
    private static final List<ResourceLocation> listAvSwords = new ArrayList<>(Arrays.asList(
            AnnoyingVillagersModItems.BLACK_FIRE_SWORD.getId(),
            AnnoyingVillagersModItems.BLUE_FLAME_SWORD.getId(),
            AnnoyingVillagersModItems.IRON_CLEAVER.getId(),
            AnnoyingVillagersModItems.CENTRANOS_SWORD.getId(),
            AnnoyingVillagersModItems.CLOW_SWORD.getId(),
            AnnoyingVillagersModItems.DIAMOND_ATTRACTOR_SWORD.getId(),
            AnnoyingVillagersModItems.DIAMOND_BLASTER_SWORD.getId(),
            AnnoyingVillagersModItems.HACKER_SWORD.getId(),
            AnnoyingVillagersModItems.DIAMOND_WARBLADE.getId(),
            AnnoyingVillagersModItems.DIAMOND_LAEVATEINN.getId(),
            AnnoyingVillagersModItems.DIAMOND_FALCHION.getId(),
            AnnoyingVillagersModItems.DIAMOND_GREAT_FALCHION.getId(),
            AnnoyingVillagersModItems.NETHERITE_FALCHION.getId(),
            AnnoyingVillagersModItems.DIAMOND_SABRE.getId(),
            AnnoyingVillagersModItems.NETHERITE_SABRE.getId(),
            AnnoyingVillagersModItems.HOOKED_IRON_SWORD.getId(),
            AnnoyingVillagersModItems.HOOKED_GOLDEN_SWORD.getId(),
            AnnoyingVillagersModItems.HOOKED_DIAMOND_SWORD.getId(),
            AnnoyingVillagersModItems.DNAX_HOOKED_SWORD.getId(),
            AnnoyingVillagersModItems.FLANKER_HOOKED_SWORD.getId(),
            AnnoyingVillagersModItems.JADE_SWORD.getId(),
            AnnoyingVillagersModItems.RED_DIAMOND_SWORD.getId(),
            AnnoyingVillagersModItems.PALADIN_SWORD.getId(),
            AnnoyingVillagersModItems.DIAMOND_KNIGHT_SWORD.getId(),
            AnnoyingVillagersModItems.RUBY_SWORD.getId(),
            AnnoyingVillagersModItems.RUBY_KNIGHT_SWORD.getId(),
            AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.getId(),
            AnnoyingVillagersModItems.WOOPIE_THE_SWORD.getId(),
            AnnoyingVillagersModItems.GREAT_SWORD.getId()
    ));

    @SubscribeEvent
    public static void registerExecution(RegisterCustomExecutionEvent event){
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.OBSIDIAN_WEAPON.getId(), AVExecutionType.STRANGLE);
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.getId(), AVExecutionType.STRANGLE);
        event.registerExecutionByItem(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.getId(), AVExecutionType.STRANGLE);
        event.registerExecutionByItem(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.getId(), AVExecutionType.STRANGLE);
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.BEDROCK_WEAPON.getId(), AVExecutionType.STRANGLE);
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.NULL_WEAPON.getId(), AVExecutionType.STRANGLE);
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.GOLDEN_MOON_BLADE.getId(), AVExecutionType.WRESTLING);
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.DIAMOND_MOON_BLADE.getId(), AVExecutionType.WRESTLING);
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.DIAMOND_ARMBLADE.getId(), AVExecutionType.WRESTLING_BACK);
        event.RegisterExecutionByItem(AnnoyingVillagersModItems.DIAMOND_CLAW.getId(), AVExecutionType.WRESTLING_BACK);
        listAvSwords.forEach(avSword -> {
            event.registerExecutionByItem(avSword, CapabilityItem.Styles.ONE_HAND, (item, livingEntityPatch) -> {
                WeaponCategory weaponCategory = livingEntityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory();
                if (livingEntityPatch.isOffhandItemValid() && weaponCategory == CapabilityItem.WeaponCategories.SHIELD) {
                    return AVExecutionType.SHIELD;
                }
                return AVExecutionType.STAB;
            });
            event.registerExecutionByItem(avSword, CapabilityItem.Styles.TWO_HAND, AVExecutionType.DUAL_STAB);
        });
    }
}
