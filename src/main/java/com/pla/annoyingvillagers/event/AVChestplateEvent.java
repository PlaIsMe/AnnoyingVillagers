package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.item.HerobrineObsidianArmorCharge;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorController;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class AVChestplateEvent {
    private AVChestplateEvent() {}

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        // Forge also reports NBT updates (charge consumption and durability) as
        // equipment changes. Only replacing the armor item should cancel its ability.
        if (event.getFrom().getItem() == event.getTo().getItem()) return;
        if (event.getSlot() == EquipmentSlot.CHEST && BlueDemonChestplateItem.isBlueDemonChestplate(event.getFrom())) BlueDemonChestplateItem.stopBuff(event.getFrom());
        if ((event.getSlot() == EquipmentSlot.HEAD || event.getSlot() == EquipmentSlot.CHEST) && HerobrineObsidianArmorCharge.isObsidianArmor(event.getFrom())) ObsidianArmorController.clear(event.getEntity());
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity living) ObsidianArmorController.tick(living);
    }

    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof LivingEntity living) ObsidianArmorController.clear(living);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity wearer = event.getEntity();
        if (!(wearer instanceof Player) || !wearer.isAlive()) return;
        float finalDamage = event.getNewDamage();
        if (finalDamage <= 0.0F) return;

        ItemStack blueChest = wearer.getItemBySlot(EquipmentSlot.CHEST);
        handleBlueDemonDamage(wearer, blueChest, finalDamage, event.getSource().getEntity());

        if (ObsidianArmorController.isActive(wearer)) return;
        int gainedCharge = Math.max(1, Mth.ceil(finalDamage));
        ItemStack helmet = wearer.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = wearer.getItemBySlot(EquipmentSlot.CHEST);
        if (HerobrineObsidianArmorCharge.isHelmet(helmet) && !HerobrineObsidianArmorCharge.isFullyCharged(helmet)) HerobrineObsidianArmorCharge.addCharge(helmet, gainedCharge);
        if (HerobrineObsidianArmorCharge.isChestplate(chest) && !HerobrineObsidianArmorCharge.isFullyCharged(chest)) HerobrineObsidianArmorCharge.addCharge(chest, gainedCharge);
    }

    private static void handleBlueDemonDamage(LivingEntity wearer, ItemStack chest, float finalDamage, Entity sourceEntity) {
        if (!BlueDemonChestplateItem.isBlueDemonChestplate(chest)) return;
        if (BlueDemonChestplateItem.isBuffActive(chest)) {
            if (sourceEntity instanceof LivingEntity attacker && attacker != wearer) {
                float chance = wearer.getRandom().nextFloat();
                if (chance <= 0.2F) attacker.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.ELECTRIFY, 20, 2));
                else if (chance <= 0.6F) attacker.addEffect(new MobEffectInstance(AnnoyingVillagersModMobEffects.ELECTRIFY, 20, 1));
            }
            return;
        }
        if (!BlueDemonChestplateItem.isFullyCharged(chest)) BlueDemonChestplateItem.addStoredCharge(chest, Math.max(1, Mth.ceil(finalDamage)));
    }
}
