package com.pla.annoyinglib.event;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.rig.armor.ObsidianArmorController;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AVChestplateEvent {
    private AVChestplateEvent() {}

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        // Forge also reports NBT updates (charge consumption and durability) as
        // equipment changes. Only replacing the armor item should cancel its ability.
        if (event.getFrom().getItem() == event.getTo().getItem()) return;
        if (event.getSlot() == EquipmentSlot.CHEST && (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.blueDemonChestplateItem_isBlueDemonChestplate(event.getFrom()) : false)) if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.blueDemonChestplateItem_stopBuff(event.getFrom());
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.onArmorChanged(event);
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        ObsidianArmorController.tick(event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof LivingEntity living) ObsidianArmorController.clear(living);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingHurtEvent event) {
        LivingEntity wearer = event.getEntity();
        if (!(wearer instanceof Player) || !wearer.isAlive()) return;
        float finalDamage = event.getAmount();
        if (finalDamage <= 0.0F) return;

        ItemStack blueChest = wearer.getItemBySlot(EquipmentSlot.CHEST);
        handleBlueDemonDamage(wearer, blueChest, finalDamage, event.getSource().getEntity());

        if (ObsidianArmorController.isActive(wearer)) return;
        int gainedCharge = Math.max(1, Mth.ceil(finalDamage));
        ItemStack helmet = wearer.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = wearer.getItemBySlot(EquipmentSlot.CHEST);
        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.chargeArmor(helmet, chest, gainedCharge);
        }
    }

    private static void handleBlueDemonDamage(LivingEntity wearer, ItemStack chest, float finalDamage, Entity sourceEntity) {
        if (ModList.get().isLoaded("mob_supremacy")) MobSupremacy.handleBlueDemonDamage(wearer, chest, finalDamage, sourceEntity);
    }
}
