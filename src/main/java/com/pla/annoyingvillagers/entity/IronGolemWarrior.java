package com.pla.annoyingvillagers.entity;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class IronGolemWarrior extends AvGolem {
    public IronGolemWarrior(EntityType<? extends IronGolem> type, Level level) {
        super(type, level);
        float chance = new Random().nextFloat();
        if (chance < 0.25) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.RED_VILLAGER_KNIGHT_HELMET_FIX.get()));
        } else if (chance < 0.5) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.BLUE_VILLAGER_KNIGHT_HELMET_FIX.get()));
        } else if (chance < 0.75) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.GREEN_VILLAGER_KNIGHT_HELMET_FIX.get()));
        } else {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(AnnoyingVillagersModItems.PURPLE_VILLAGER_KNIGHT_HELMET_FIX.get()));
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AnnoyingVillagersModItems.DIAMOND_HALBERD.get()));
        if (new Random().nextBoolean()) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(AnnoyingVillagersModItems.DIAMOND_HALBERD.get()));
        }
    }

    }
