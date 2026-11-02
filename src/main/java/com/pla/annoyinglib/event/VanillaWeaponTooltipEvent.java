package com.pla.annoyinglib.event;
import com.pla.annoyinglib.util.SharedWeaponHooks;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class VanillaWeaponTooltipEvent {
    private static final int METER_STEPS = 18;

    private VanillaWeaponTooltipEvent() {
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        List<Component> tooltip = event.getToolTip();

        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.Client.addChargeTooltips(stack, item, tooltip);
        }

        if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isRedAxeItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.red_axe.right_click");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isHackerSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.hacker_sword.right_click");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isEarthAxeItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.earth_axe.right_click", "tooltip.annoyinglib.vanilla_combat.earth_axe.special");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isWoopieTheSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.woopie.right_click", "tooltip.annoyinglib.vanilla_combat.woopie.special");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isThunderDiamondBladeItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.thunder_diamond_blade.right_click");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isLegendarySwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.legendary_sword.right_click", "tooltip.annoyinglib.vanilla_combat.legendary_sword.special");
        else if ((ModList.get().isLoaded("mob_supremacy") && MobSupremacy.isBlueDemonTridentItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.blue_demon_trident.right_click", "tooltip.annoyinglib.vanilla_combat.blue_demon_trident.dual_throw", "tooltip.annoyinglib.vanilla_combat.blue_demon_trident.special", "tooltip.annoyinglib.vanilla_combat.blue_demon_trident.shift_special", "tooltip.annoyinglib.vanilla_combat.blue_demon_trident.festival");
        else if ((SharedWeaponHooks.isBlackFireSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.black_fire_sword.right_click");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isBlueFlameSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.blue_flame_sword.melee");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isClowSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.clow_sword.melee");
        else if ((SharedWeaponHooks.isDiamondAttractorSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.diamond_attractor_sword.right_click");
        else if ((SharedWeaponHooks.isDiamondBlasterSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.diamond_blaster_sword.right_click");
        else if ((SharedWeaponHooks.isHookedDiamondSwordItem(item)) || (SharedWeaponHooks.isHookedGoldenSwordItem(item)) || (SharedWeaponHooks.isHookedIronSwordItem(item)) || (ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isFlankerHookedSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.hook_sword.right_click");
        else if ((ModList.get().isLoaded("legendary_npcs") && LegendaryNpcs.isDNAxHookedSwordItem(item))) addCombatTooltip(tooltip, "tooltip.annoyinglib.vanilla_combat.dnax_hooked_sword.right_click");

        if (ModList.get().isLoaded("herobrine_vessels")) HerobrineVessels.Client.addWeaponTooltips(item, tooltip);
    }

    public static void addCombatTooltip(List<Component> tooltip, String... translationKeys) {
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.annoyinglib.vanilla_combat.title").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD));
        for (String key : translationKeys) tooltip.add(Component.literal("  ").append(Component.translatable(key)).withStyle(ChatFormatting.GRAY));
    }

    public static void addChargeTooltip(List<Component> tooltip, String labelKey, int charge, int maxCharge) {
        int clamped = Mth.clamp(charge, 0, maxCharge);
        tooltip.add(Component.translatable(labelKey).withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD));
        tooltip.add(Component.translatable("tooltip.annoyinglib.vanilla_combat.charge_value", clamped, maxCharge).withStyle(ChatFormatting.WHITE));
        int filled = Mth.clamp(Math.round((clamped / (float)maxCharge) * METER_STEPS), 0, METER_STEPS);
        MutableComponent meter = Component.literal("âš¡ ").withStyle(ChatFormatting.AQUA);
        for (int i = 0; i < METER_STEPS; i++) meter.append(Component.literal(i < filled ? "â–°" : "â–±").withStyle(i < filled ? ChatFormatting.AQUA : ChatFormatting.DARK_GRAY));
        tooltip.add(meter);
        if (clamped >= maxCharge) tooltip.add(Component.translatable("tooltip.annoyinglib.vanilla_combat.charged").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD));
    }
}
