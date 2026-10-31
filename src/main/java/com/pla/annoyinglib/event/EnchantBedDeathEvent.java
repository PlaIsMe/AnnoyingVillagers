package com.pla.annoyinglib.event;

import javax.annotation.Nullable;

import com.pla.annoyinglib.init.AnnoyingLibModMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

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
        if (entity != null && entity1 != null) {
            LivingEntity livingentity;
            LivingEntity livingentity1;
            boolean flag;
            Player player;

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (livingentity.hasEffect((MobEffect) AnnoyingLibModMobEffects.ENCHANT_BED_EFFECT.get())) {
                    LivingEntity livingentity2;
                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        livingentity2.setHealth(20.0F);
                    }

                    if (event != null && event.isCancelable()) {
                        event.setCanceled(true);
                    }

                    if (entity instanceof Player) {
                        player = (Player)entity;
                        player.closeContainer();
                    }

                    ServerPlayer serverplayer;
                    double d0;
                    label179: {
                        if (entity instanceof ServerPlayer) {
                            serverplayer = (ServerPlayer)entity;
                            if (!serverplayer.level().isClientSide()) {
                                d0 = (double)(serverplayer.getRespawnDimension().equals(serverplayer.level().dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getX() : serverplayer.level().getLevelData().getXSpawn());
                                break label179;
                            }
                        }

                        d0 = 0.0D;
                    }

                    int i;
                    label169: {
                        if (entity instanceof ServerPlayer) {
                            serverplayer = (ServerPlayer)entity;
                            if (!serverplayer.level().isClientSide()) {
                                i = serverplayer.getRespawnDimension().equals(serverplayer.level().dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getY() : serverplayer.level().getLevelData().getYSpawn();
                                break label169;
                            }
                        }

                        i = 0;
                    }

                    double d1;
                    double d2;
                    label160: {
                        d1 = (double)(i + 1);
                        if (entity instanceof ServerPlayer) {
                            serverplayer = (ServerPlayer)entity;
                            if (!serverplayer.level().isClientSide()) {
                                d2 = (double)(serverplayer.getRespawnDimension().equals(serverplayer.level().dimension()) && serverplayer.getRespawnPosition() != null ? serverplayer.getRespawnPosition().getZ() : serverplayer.level().getLevelData().getZSpawn());
                                break label160;
                            }
                        }

                        d2 = 0.0D;
                    }

                    entity.teleportTo(d0, d1, d2);
                    if (entity instanceof ServerPlayer) {
                        ServerGamePacketListenerImpl servergamepacketlistenerimpl;
                        label148: {
                            serverplayer = (ServerPlayer)entity;
                            servergamepacketlistenerimpl = serverplayer.connection;
                            if (entity instanceof ServerPlayer) {
                                ServerPlayer serverplayer1 = (ServerPlayer)entity;

                                if (!serverplayer1.level().isClientSide()) {
                                    d0 = (double)(serverplayer1.getRespawnDimension().equals(serverplayer1.level().dimension()) && serverplayer1.getRespawnPosition() != null ? serverplayer1.getRespawnPosition().getX() : serverplayer1.level().getLevelData().getXSpawn());
                                    break label148;
                                }
                            }

                            d0 = 0.0D;
                        }

                        label138: {
                            if (entity instanceof ServerPlayer) {
                                ServerPlayer serverplayer2 = (ServerPlayer)entity;

                                if (!serverplayer2.level().isClientSide()) {
                                    i = serverplayer2.getRespawnDimension().equals(serverplayer2.level().dimension()) && serverplayer2.getRespawnPosition() != null ? serverplayer2.getRespawnPosition().getY() : serverplayer2.level().getLevelData().getYSpawn();
                                    break label138;
                                }
                            }

                            i = 0;
                        }

                        label129: {
                            d1 = (double)(i + 1);
                            if (entity instanceof ServerPlayer) {
                                ServerPlayer serverplayer3 = (ServerPlayer)entity;

                                if (!serverplayer3.level().isClientSide()) {
                                    d2 = (double)(serverplayer3.getRespawnDimension().equals(serverplayer3.level().dimension()) && serverplayer3.getRespawnPosition() != null ? serverplayer3.getRespawnPosition().getZ() : serverplayer3.level().getLevelData().getZSpawn());
                                    break label129;
                                }
                            }

                            d2 = 0.0D;
                        }

                        servergamepacketlistenerimpl.teleport(d0, d1, d2, entity.getYRot(), entity.getXRot());
                    }

                    if (entity instanceof LivingEntity) {
                        livingentity2 = (LivingEntity)entity;
                        livingentity2.removeEffect((MobEffect)AnnoyingLibModMobEffects.ENCHANT_BED_EFFECT.get());
                    }

                    if (entity instanceof Player) {
                        player = (Player)entity;
                        if (!player.level().isClientSide()) {
                            player.displayClientMessage(Component.literal("Your enchanted bed has saved you once. Right-click again to use it again!"), true);
                        }
                    }
                }
            }

            if (entity instanceof LivingEntity) {
                livingentity = (LivingEntity)entity;
                if (livingentity.isHolding(Items.TOTEM_OF_UNDYING)) {
                    entity.kill();
                    if (entity instanceof Player) {
                        player = (Player)entity;
                        ItemStack itemstack = new ItemStack(Items.TOTEM_OF_UNDYING);

                        player.getInventory().clearOrCountMatchingItems((itemstack1) -> {
                            return itemstack.getItem() == itemstack1.getItem();
                        }, 1, player.inventoryMenu.getCraftSlots());
                    }
                }
            }
        }
    }
}
