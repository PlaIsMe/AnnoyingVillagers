package com.pla.annoyingvillagers.event;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.util.ChatUtil;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class PlayerNpcDeadEvent {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent livingdeathevent) {
        if (livingdeathevent != null && livingdeathevent.getEntity() != null) {
            execute(livingdeathevent, livingdeathevent.getEntity().level(), livingdeathevent.getEntity().getX(), livingdeathevent.getEntity().getY(), livingdeathevent.getEntity().getZ(), livingdeathevent.getEntity(), livingdeathevent.getSource().getEntity());
        }

    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity, Entity entity1) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity, entity1);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, final double d0, final double d1, final double d2, final Entity entity, final Entity entity1) {
        if (entity != null && entity1 != null) {
            if (entity1 instanceof PlayerNpcEntity) {
                new DelayedTask(Mth.nextInt(RandomSource.create(), 70, 100)) {
                    @Override
                    public void run() {
                        if (AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get()) {
                            if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> fw"), false);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> Is that all ?"), false);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> LLLLLLLLLLLLL"), false);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> Hey, what happened to you?"), false);
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> Poet's grasp"), false);
                                }
                            } else {
                                PlayerList playerlist;
                                String s;

                                if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + ", is that all the strength you've got?"), false);
                                    }
                                } else if (Math.random() <= 0.1D) {
                                    if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:player") && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + ", that's all the Little Hajiki can do?"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString().equals("minecraft:player") && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + ", you're godlike too"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> Hahaha \uD83D\uDE02 so funny, a " + entity.getDisplayName().getString() + " is lying in bed, rhythmically chanting “Garen~ fafah”"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + ", don't act tough if you're weak"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + " plays like a bot"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + ", I'm dying of laughter, a total \uD83E\uDD21"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> Even giving it your all, you still can't defeat me? Haki " + entity.getDisplayName().getString() + ", you bastard!"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> Joke " + entity.getDisplayName().getString()), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + ", weren't you acting all tough, bro?"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> lol"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + "\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95 So weak!"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist = levelaccessor.getServer().getPlayerList();
                                        s = entity1.getDisplayName().getString();
                                        playerlist.broadcastSystemMessage(Component.literal("<" + s + "> " + entity.getDisplayName().getString() + ", if you're bad, just practice more, little bro"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> It's over"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> Haha, you're getting mad"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> Oh really?"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> \ud83e\udd13\ud83e\udd13\ud83e\udd13"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> I pinned you to the ground and beat you up, haha"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> So bad? Could it be that you've played too much Genshin Impact?\ud83d\ude05"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + "> Desperate now?"), false);
                                    }
                                } else if (Math.random() <= 0.05D && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity1.getDisplayName().getString() + ">, all you can do is spam left-click to death\ud83e\udd13"), false);
                                }
                            }
                        }
                    }
                };
            }

            if (entity instanceof PlayerNpcEntity) {
                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                    PlayerList playerlist = levelaccessor.getServer().getPlayerList();
                    String s = entity.getDisplayName().getString();

                    if (AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get()) {
                        playerlist.broadcastSystemMessage(Component.literal(s + " was killed by " + entity1.getDisplayName().getString()), false);
                    }
                }

                new DelayedTask(5) {
                    @Override
                    public void run() {
                        // PlayerNpc death loot is inventory-backed in PlayerNpcEntity.dropCustomDeathLoot.
                        if (entity instanceof PlayerNpcEntity) {
                            return;
                        }

                        LevelAccessor levelaccessor1;
                        Level level;
                        ItemEntity itementity;

                        if (!entity.getPersistentData().getBoolean("die_by_possess")) {
                            levelaccessor1 = levelaccessor;
                            LivingEntity livingentity = (LivingEntity)entity;
                            ItemStack itemstack;

                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getMainHandItem();
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }

                            levelaccessor1 = levelaccessor;
                            if (levelaccessor1 instanceof Level) {
                                level = (Level)levelaccessor1;
                                if (!level.isClientSide()) {
                                    if (entity instanceof LivingEntity) {
                                        itemstack = livingentity.getOffhandItem();
                                    } else {
                                        itemstack = ItemStack.EMPTY;
                                    }

                                    itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, itemstack);
                                    itementity.setPickUpDelay(10);
                                    level.addFreshEntity(itementity);
                                }
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Blocks.OAK_PLANKS));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.BOW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ARROW));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.ENDER_PEARL));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }

                        levelaccessor1 = levelaccessor;
                        if (levelaccessor1 instanceof Level) {
                            level = (Level)levelaccessor1;
                            if (!level.isClientSide()) {
                                itementity = new ItemEntity(level, d0, d1 + 1.0D, d2, new ItemStack(Items.GOLDEN_APPLE));
                                itementity.setPickUpDelay(10);
                                level.addFreshEntity(itementity);
                            }
                        }
                    }
                };
                if (AnnoyingVillagersConfig.TURN_ON_NPC_CHAT.get()) {
                    new DelayedTask(Mth.nextInt(RandomSource.create(), 40, 80)) {
                        @Override
                        public void run() {
                            Entity entity2;

                            if (Math.random() <= 0.05D) {
                                entity2 = entity;
                                if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                    try {
                                        entity2.getServer().getCommands().getDispatcher().execute(
                                                "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> " + entity1.getDisplayName().getString() + " Bro, I'll remember you for this\ud83d\ude21\"}]",
                                                entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                    } catch (CommandSyntaxException e) {

                                    }
                                }
                            } else if (Math.random() <= 0.05D) {
                                if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> I'm breaking down\ud83d\ude2d"), false);
                                }
                            } else {
                                PlayerList playerlist1;
                                String s1;

                                if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", f** you"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> So speechless\ud83d\ude05"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> 666 this guy is a boss"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> That was tough\ud83d\ude05"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> I really give up\ud83d\ude2d"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + " , your should die. F**"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + " bro, be honest, are you hacking?\ud83d\ude05"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Played as Bull Demon, lost all my gear\ud83d\ude05"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", I'll get you later\ud83d\ude21"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", I will take revenge on you soon\ud83d\ude21"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", f*** you! Dare to fight fair with proper gear?\ud83d\ude21"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", you're dead for sure"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", you just killed me like that. Are you happy now?\ud83d\ude05"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> Please, " + entity1.getDisplayName().getString() + ", don't burnt my items !!!!"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> ????????????????????????????????"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> I haven't even gotten serious yet"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", I'll get you next time\ud83d\ude21"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", you are really a \ud83d\udc36"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        playerlist1 = levelaccessor.getServer().getPlayerList();
                                        s1 = entity.getDisplayName().getString();
                                        playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + " , ambushing an ordinary player like me, is this okay? No, it's not\ud83d\udc4e"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> ......"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        levelaccessor.getServer().getPlayerList().broadcastSystemMessage(Component.literal("<" + entity.getDisplayName().getString() + "> Idiot, f** you \ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95\ud83d\udd95"), false);
                                    }
                                } else if (Math.random() <= 0.05D) {
                                    entity2 = entity;
                                    if (!entity2.level().isClientSide() && entity2.getServer() != null) {
                                        try {
                                            entity2.getServer().getCommands().getDispatcher().execute(
                                                    "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> Don't leave, I'm calling some people\ud83d\ude21\ud83d\ude21\ud83d\ude21\"}]",
                                                    entity2.createCommandSourceStack().withSuppressedOutput().withPermission(4));
                                        } catch (CommandSyntaxException e) {

                                        }
                                    }

                                    new DelayedTask(50) {
                                        @Override
                                        public void run() {
                                            try {
                                                entity.getServer().getCommands().getDispatcher().execute(
                                                        "summon annoyingvillagers:player_npc",
                                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                                );
                                            } catch (CommandSyntaxException e) {

                                            }
                                        }
                                    };

                                    new DelayedTask(20) {
                                        @Override
                                        public void run() {
                                            try {
                                                entity.getServer().getCommands().getDispatcher().execute(
                                                        "summon annoyingvillagers:player_npc",
                                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                                );
                                            } catch (CommandSyntaxException e) {

                                            }
                                        }
                                    };

                                    new DelayedTask(20) {
                                        @Override
                                        public void run() {
                                            try {
                                                entity.getServer().getCommands().getDispatcher().execute(
                                                        "summon annoyingvillagers:player_npc",
                                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                                );
                                            } catch (CommandSyntaxException e) {

                                            }
                                        }
                                    };

                                    new DelayedTask(20) {
                                        @Override
                                        public void run() {
                                            try {
                                                entity.getServer().getCommands().getDispatcher().execute(
                                                        "tellraw @a [{\"text\":\"<\"},{\"selector\":\"@s\"},{\"text\":\"> Hmmm, you're here… bro.🤓\"}]",
                                                        entity.createCommandSourceStack().withSuppressedOutput().withPermission(4)
                                                );
                                            } catch (CommandSyntaxException e) {

                                            }
                                        }
                                    };
                                } else if (Math.random() <= 0.05D && !levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                    playerlist1 = levelaccessor.getServer().getPlayerList();
                                    s1 = entity.getDisplayName().getString();
                                    playerlist1.broadcastSystemMessage(Component.literal("<" + s1 + "> " + entity1.getDisplayName().getString() + ", using your OP weapon, is that fun for you?\ud83d\ude05"), false);
                                }
                            }

                            new DelayedTask(Mth.nextInt(RandomSource.create(), 25, 100)) {
                                @Override
                                public void run() {
                                    if (!levelaccessor.isClientSide() && levelaccessor.getServer() != null) {
                                        ChatUtil.leaveGame(entity);
                                    }
                                }
                            };
                        }
                    };
                }
            }
        }
    }
}
