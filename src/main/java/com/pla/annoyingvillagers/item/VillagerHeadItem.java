package com.pla.annoyingvillagers.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class VillagerHeadItem extends Item {

    public VillagerHeadItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(1).rarity(Rarity.COMMON).equippable(EquipmentSlot.HEAD));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, display, list, tooltipFlag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.villager_head"));
    }

    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
