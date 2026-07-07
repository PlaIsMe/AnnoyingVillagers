package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.inventory.InventoryViewerMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InventoryViewerItem extends Item {
    public InventoryViewerItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(
            @NotNull ItemStack stack,
            @NotNull Player player,
            @NotNull LivingEntity target,
            @NotNull InteractionHand hand
    ) {
        SimpleContainer inventory = getNpcInventory(target);
        if (inventory == null) {
            if (!player.level().isClientSide()) {
                player.displayClientMessage(Component.translatable("message.annoyingvillagers.inventory_viewer.unsupported")
                        .withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide());
        }

        if (player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(
                    serverPlayer,
                    new SimpleMenuProvider(
                            (containerId, playerInventory, menuPlayer) -> new InventoryViewerMenu(containerId, playerInventory, target, inventory),
                            target.getDisplayName()
                    ),
                    data -> data.writeInt(target.getId())
            );
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }

    @Override
    public void appendHoverText(
            @NotNull ItemStack stack,
            Level level,
            @NotNull List<Component> tooltip,
            @NotNull TooltipFlag flag
    ) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.annoyingvillagers.inventory_viewer").withStyle(ChatFormatting.GRAY));
    }

    private static SimpleContainer getNpcInventory(LivingEntity target) {
        if (target instanceof AVNpc avNpc) {
            return avNpc.getInventory();
        }
        return null;
    }
}
