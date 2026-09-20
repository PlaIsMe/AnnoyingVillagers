package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import java.util.List;

public class TonyTheFishingRod extends FishingRodItem {
    public TonyTheFishingRod() {
        super(com.pla.annoyingvillagers.util.LegacyItemProperties.create().stacksTo(1).durability(1561));
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, display, tooltip, flag);
        tooltip.accept(Component.translatable("tooltip.annoyingvillagers.tony_the_fishing_rod"));
    }

    @Override
    public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slot = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        super.inventoryTick(stack, level, entity, equipmentSlot);
        FishingRodGrappleUtil.inventoryTick(stack, level, entity);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return FishingRodGrappleUtil.use(this, level, player, hand);
    }
}
