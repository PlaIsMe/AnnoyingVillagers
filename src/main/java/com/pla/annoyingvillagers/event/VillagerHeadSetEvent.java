package com.pla.annoyingvillagers.event;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.util.TeamUtil;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VillagerHeadSetEvent {
    private static final String VILLAGER_HEAD_MODE_KEY = "villager_head";
    private static final String VILLAGER_HEAD_COOLDOWN_KEY = "villager_head_used";

    @SubscribeEvent
    public static void onRightClickBlock(RightClickBlock rightclickblock) {
        if (rightclickblock.getHand() == rightclickblock.getEntity().getUsedItemHand()) {
            execute(rightclickblock, rightclickblock.getLevel(), rightclickblock.getEntity());
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() != EquipmentSlot.HEAD) {
            return;
        }

        boolean hadVillagerHead = event.getFrom().getItem() == AnnoyingVillagersModItems.VILLAGER_HEAD.get();
        boolean hasVillagerHead = event.getTo().getItem() == AnnoyingVillagersModItems.VILLAGER_HEAD.get();
        if (hadVillagerHead == hasVillagerHead) {
            return;
        }

        Entity entity = event.getEntity();
        entity.getPersistentData().putBoolean(VILLAGER_HEAD_MODE_KEY, false);
        entity.getPersistentData().putBoolean(VILLAGER_HEAD_COOLDOWN_KEY, false);

        if (hasVillagerHead) {
            TeamUtil.addOrJoinTeam(entity, "villagers");
            if (entity instanceof Player player && !player.level().isClientSide()) {
                player.displayClientMessage(Component.literal("You have put on the villager helmet. Villager soldiers will no longer attack you."), false);
            }
            return;
        }

        TeamUtil.leaveTeam(entity, "villagers");
        if (entity instanceof Player player && !player.level().isClientSide()) {
            player.displayClientMessage(Component.literal("You have removed your helmet. Villager soldiers will now attack you."), false);
        }
    }

    public static void execute(LevelAccessor levelaccessor, Entity entity) {
        execute((Event) null, levelaccessor, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final Entity entity) {
        if (entity != null) {
            if (entity.isShiftKeyDown()) {
                ItemStack itemstack;

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity)entity;

                    itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                } else {
                    itemstack = ItemStack.EMPTY;
                }

                if (itemstack.getItem() == AnnoyingVillagersModItems.VILLAGER_HEAD.get()) {
                    Player player;

                    if (!entity.getPersistentData().getBoolean(VILLAGER_HEAD_MODE_KEY)) {
                        if (!entity.getPersistentData().getBoolean(VILLAGER_HEAD_COOLDOWN_KEY)) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "team leave @s[team=villagers]",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                    
                                }
                            }

                            if (entity instanceof Player) {
                                player = (Player)entity;
                                if (!player.level().isClientSide()) {
                                    player.displayClientMessage(Component.literal("Switched to Attack Mode"), false);
                                }
                            }

                            entity.getPersistentData().putBoolean(VILLAGER_HEAD_COOLDOWN_KEY, true);
                            new DelayedTask(200) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean(VILLAGER_HEAD_MODE_KEY, true);
                                    entity.getPersistentData().putBoolean(VILLAGER_HEAD_COOLDOWN_KEY, false);
                                }
                            };
                        } else if (entity instanceof Player) {
                            player = (Player)entity;
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("On Cooldown"), true);
                            }
                        }
                    } else if (entity.getPersistentData().getBoolean(VILLAGER_HEAD_MODE_KEY)) {
                        if (!entity.getPersistentData().getBoolean(VILLAGER_HEAD_COOLDOWN_KEY)) {
                            if (!entity.level().isClientSide() && entity.getServer() != null) {
                                try {
                                    entity.getServer().getCommands().getDispatcher().execute(
                                            "team join villagers @s",
                                            entity.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                } catch (CommandSyntaxException e) {
                                    
                                }
                            }

                            if (entity instanceof Player) {
                                player = (Player)entity;
                                if (!player.level().isClientSide()) {
                                    player.displayClientMessage(Component.literal("Switched to Disguise Mode"), false);
                                }
                            }

                            entity.getPersistentData().putBoolean(VILLAGER_HEAD_COOLDOWN_KEY, true);
                            new DelayedTask(200) {
                                @Override
                                public void run() {
                                    entity.getPersistentData().putBoolean(VILLAGER_HEAD_MODE_KEY, false);
                                    entity.getPersistentData().putBoolean(VILLAGER_HEAD_COOLDOWN_KEY, false);
                                }
                            };

                        } else if (entity instanceof Player) {
                            player = (Player)entity;
                            if (!player.level().isClientSide()) {
                                player.displayClientMessage(Component.literal("On Cooldown"), true);
                            }
                        }
                    }
                }
            }

        }
    }
}
