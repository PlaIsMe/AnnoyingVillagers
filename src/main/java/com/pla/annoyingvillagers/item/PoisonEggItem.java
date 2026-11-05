package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.ThrownPoisonEggEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class PoisonEggItem extends Item {
    public PoisonEggItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(16));
    }
    public void appendHoverText(ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.poison_egg"));
    }
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!pLevel.isClientSide()) {
            ThrownPoisonEggEntity thrownegg = new ThrownPoisonEggEntity(
                    AnnoyingVillagersModEntities.THROWN_POISON_EGG.get(), pPlayer, pLevel
            );
            thrownegg.setItem(itemstack);
            thrownegg.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(thrownegg);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }
}