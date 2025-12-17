package com.pla.annoyingvillagers.procedures;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class AddStarterSkillProcedure {
    private static final String KEY = AnnoyingVillagers.MODID + ":has_joined_before";
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent playerloggedinevent) {
        execute(playerloggedinevent, playerloggedinevent.getEntity().level(), playerloggedinevent.getEntity().getX(), playerloggedinevent.getEntity().getY(), playerloggedinevent.getEntity().getZ(), playerloggedinevent.getEntity());
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag oldData = oldPlayer.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag newRoot = newPlayer.getPersistentData();
        CompoundTag newData = newRoot.getCompound(Player.PERSISTED_NBT_TAG);

        newData.merge(oldData);
        newRoot.put(Player.PERSISTED_NBT_TAG, newData);
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute((Event) null, levelaccessor, d0, d1, d2, entity);
    }

    private static CompoundTag persisted(Player p) {
        CompoundTag root = p.getPersistentData();
        CompoundTag data = root.getCompound(Player.PERSISTED_NBT_TAG);
        root.put(Player.PERSISTED_NBT_TAG, data);
        return data;
    }
    public static boolean hasJoinedBefore(Player p) {
        return persisted(p).getBoolean(KEY);
    }
    public static void markJoined(Player p) {
        persisted(p).putBoolean(KEY, true);
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity != null) {
            if (entity instanceof Player player && !hasJoinedBefore(player)) {
                if (!entity.level().isClientSide() && entity.getServer() != null) {
                    CommandSourceStack source = entity.createCommandSourceStack()
                            .withSuppressedOutput()
                            .withPermission(4);
                    try {
                        entity.getServer().getCommands().getDispatcher().execute(
                                "epicfight skill add @s guard epicfight:guard",
                                source
                        );
                        entity.getServer().getCommands().getDispatcher().execute(
                                "epicfight skill add @s dodge epicfight:roll",
                                source
                        );
                    } catch (CommandSyntaxException e) {
                    }
                    markJoined((Player) entity);
                }
            }

            if (entity instanceof LivingEntity livingEntity) {
                ItemStack stack = livingEntity.getMainHandItem();
                if (stack.equals(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER)){
                    stack.removeTagKey("SnakeAnimation");
                }
            }

            if (entity.getPersistentData().getBoolean("b_d_aim")) {
                entity.getPersistentData().putBoolean("b_d_aim", false);
            }

            entity.getPersistentData().putBoolean("kick_x", false);
            entity.getPersistentData().putBoolean("ender_pearl_used", false);
            entity.getPersistentData().putDouble("air_kick", 0.0D);
            entity.getPersistentData().putDouble("kick", 0.0D);
            entity.getPersistentData().putDouble("axe_a", 0.0D);
            entity.getPersistentData().putDouble("sword_a", 0.0D);
            entity.getPersistentData().putDouble("fist_a", 0.0D);
            entity.getPersistentData().putDouble("dash_auto", 0.0D);
        }
    }
}
