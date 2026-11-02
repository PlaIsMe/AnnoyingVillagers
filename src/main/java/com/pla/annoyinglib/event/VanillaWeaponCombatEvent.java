package com.pla.annoyinglib.event;
import com.pla.annoyinglib.compat.MobSupremacy;
import com.pla.annoyinglib.compat.LegendaryNpcs;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class VanillaWeaponCombatEvent {
    private VanillaWeaponCombatEvent() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        if ((ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.hackerSwordItem_isComboAttack(player) : false)) {
            event.setResult(net.minecraftforge.eventbus.api.Event.Result.ALLOW);
            event.setDamageModifier(1.5F);
        }
        if (!event.isVanillaCritical() || player.level().isClientSide() || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
        ItemStack stack = player.getMainHandItem();
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.onCriticalHit(stack, player);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.onAttackEntity(event, player);
        }
    }


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END || player.level().isClientSide() || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.tickSavedTeleport(player);
        }
        if (!(ModList.get().isLoaded("legendary_npcs") ? LegendaryNpcs.legendarySwordItem_hasActiveVanillaAwakening(player) : false)) if (ModList.get().isLoaded("legendary_npcs")) LegendaryNpcs.legendarySwordItem_clearVanillaAttackSpeed(player);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onShieldBlock(ShieldBlockEvent event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide() || !VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
        ItemStack stack = player.getUseItem();
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.onShieldBlock(event, player, stack);
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.onLivingHurt(event);
        }
        if (!(event.getEntity() instanceof Player player)) return;
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.cancelOwnedTridentDamage(event, player);
    }

}
