package com.pla.annoyingvillagers.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AdvancedFishingRod extends FishingRodItem {
    public AdvancedFishingRod() {
        super(new Item.Properties().stacksTo(1).durability(250));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        FishingRodGrappleUtil.inventoryTick(stack, level, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return FishingRodGrappleUtil.use(this, level, player, hand);
    }
}
