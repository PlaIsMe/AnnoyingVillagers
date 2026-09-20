package com.pla.annoyingvillagers.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class JevBookItem extends Item {

    public JevBookItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    public boolean isCorrectToolForDrops(BlockState blockstate) {
        return true;
    }

    public void appendHoverText(ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
    }

    public InteractionResult use(Level level, Player player, InteractionHand interactionhand) {
        InteractionResult interactionresultholder = super.use(level, player, interactionhand);
        return interactionresultholder;
    }

    public void inventoryTick(net.minecraft.world.item.ItemStack itemstack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int i = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, itemstack);
        boolean flag = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        super.inventoryTick(itemstack, level, entity, equipmentSlot);
    }
}

