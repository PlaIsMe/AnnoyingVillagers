package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class EnchantBedItem extends Item {

    public EnchantBedItem() {
        super((new Properties()).stacksTo(1).fireResistant().rarity(Rarity.COMMON));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return true;
    }

    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.enchanted_bed"));
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext) {
        super.useOn(useOnContext);
        useOnContext.getLevel().setBlock(new BlockPos(useOnContext.getClickedPos().getX(), useOnContext.getClickedPos().getY() + 1, useOnContext.getClickedPos().getZ()), AnnoyingVillagersModBlocks.ENCHANT_BED.get().defaultBlockState(), 3);
        if (useOnContext.getPlayer() != null) {
            ItemStack itemstack = new ItemStack(AnnoyingVillagersModItems.ENCHANT_BED_ITEM.get());
            useOnContext.getPlayer().getInventory().clearOrCountMatchingItems((itemstack1) -> {
                return itemstack.getItem() == itemstack1.getItem();
            }, 1, useOnContext.getPlayer().inventoryMenu.getCraftSlots());
        }
        return InteractionResult.SUCCESS;
    }

    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level level, @NotNull Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, level, entity, i, flag);
    }
}
