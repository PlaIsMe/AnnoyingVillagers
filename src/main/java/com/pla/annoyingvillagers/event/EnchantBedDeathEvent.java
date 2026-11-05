package com.pla.annoyingvillagers.event;

import javax.annotation.Nullable;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class EnchantBedDeathEvent {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
            execute(livingdeathevent, livingdeathevent.getEntity().level(), livingdeathevent.getEntity(), livingdeathevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity, final Entity entity1) {
        if (!(entity instanceof LivingEntity livingEntity) || entity1 == null) return;

        if (livingEntity.hasEffect(AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT)) {
            livingEntity.setHealth(20.0F);
            if (event instanceof net.neoforged.bus.api.ICancellableEvent cancellable) {
                cancellable.setCanceled(true);
            }
            if (livingEntity instanceof Player player) {
                player.closeContainer();
            }

            if (livingEntity instanceof ServerPlayer serverPlayer) {
                BlockPos destination = serverPlayer.level().getRespawnData().pos();
                ServerPlayer.RespawnConfig config = serverPlayer.getRespawnConfig();
                if (config != null && config.respawnData().dimension().equals(serverPlayer.level().dimension())) {
                    destination = config.respawnData().pos();
                }
                double x = destination.getX();
                double y = destination.getY() + 1.0D;
                double z = destination.getZ();
                serverPlayer.connection.teleport(x, y, z, livingEntity.getYRot(), livingEntity.getXRot());
                com.pla.annoyingvillagers.util.LegacyPlayerMessages.display(
                        serverPlayer,
                        Component.literal("Your enchanted bed has saved you once. Right-click again to use it again!"),
                        true
                );
            }
            livingEntity.removeEffect(AnnoyingVillagersModMobEffects.ENCHANT_BED_EFFECT);
        }

        if (livingEntity.isHolding(Items.TOTEM_OF_UNDYING)) {
            com.pla.annoyingvillagers.util.LegacyEntityOps.kill(livingEntity);
            if (livingEntity instanceof Player player) {
                ItemStack itemstack = new ItemStack(Items.TOTEM_OF_UNDYING);
                player.getInventory().clearOrCountMatchingItems(
                        stack -> itemstack.getItem() == stack.getItem(),
                        1,
                        player.inventoryMenu.getCraftSlots()
                );
            }
        }
    }
}
