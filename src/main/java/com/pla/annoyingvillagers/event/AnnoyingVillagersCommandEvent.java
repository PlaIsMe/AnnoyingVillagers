package com.pla.annoyingvillagers.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.clazz.PersistentPlayerNpc;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.ProgressionUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class AnnoyingVillagersCommandEvent {
    private AnnoyingVillagersCommandEvent() {
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("annoyingvillagers")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("difficulty")
                        .then(Commands.literal("get")
                                .executes(context -> getDifficulty(context.getSource())))
                        .then(Commands.literal("set")
                                .then(Commands.argument("difficulty", StringArgumentType.word())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(new String[]{"easy", "medium", "hard"}, builder))
                                        .executes(context -> setDifficulty(
                                                context.getSource(),
                                                StringArgumentType.getString(context, "difficulty")
                                        )))))
                .then(Commands.literal("tp")
                        .then(Commands.argument("npc", StringArgumentType.word())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                        availableNpcNames(context.getSource()), builder))
                                .executes(context -> teleportSourceToNpc(
                                        context.getSource(), StringArgumentType.getString(context, "npc")))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> teleportNpcToPlayer(
                                                context.getSource(),
                                                StringArgumentType.getString(context, "npc"),
                                                EntityArgument.getPlayer(context, "player")))))));
    }

    private static Iterable<String> availableNpcNames(CommandSourceStack source) {
        return availableNpcs(source).stream().map(AnnoyingVillagersCommandEvent::commandName).distinct().sorted().toList();
    }

    private static List<AVNpc> availableNpcs(CommandSourceStack source) {
        List<AVNpc> npcs = new ArrayList<>();
        for (ServerLevel level : source.getServer().getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
                if (entity instanceof AVNpc npc && npc.isAlive() && !npc.isRemoved()) npcs.add(npc);
            }
        }
        return npcs;
    }

    private static String commandName(AVNpc npc) {
        if (npc instanceof PersistentPlayerNpc identity) return identity.persistentPlayerIdentity();
        String name = npc.getName().getString().trim();
        return name.isEmpty() ? npc.getType().getDescriptionId() : name.replace(' ', '_');
    }

    private static AVNpc findNpc(CommandSourceStack source, String requestedName) {
        Vec3 origin = source.getPosition();
        ServerLevel sourceLevel = source.getLevel();
        return availableNpcs(source).stream()
                .filter(npc -> commandName(npc).equalsIgnoreCase(requestedName))
                .min(Comparator.comparingDouble(npc -> npc.level() == sourceLevel
                        ? npc.distanceToSqr(origin) : Double.MAX_VALUE))
                .orElse(null);
    }

    private static int teleportSourceToNpc(CommandSourceStack source, String npcName) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        AVNpc npc = findNpc(source, npcName);
        if (npc == null) {
            source.sendFailure(Component.literal("No spawned AvNpc named " + npcName + " is currently available."));
            return 0;
        }
        ServerPlayer player = source.getPlayerOrException();
        player.teleportTo((ServerLevel) npc.level(), npc.getX(), npc.getY(), npc.getZ(), player.getYRot(), player.getXRot());
        source.sendSuccess(() -> Component.literal("Teleported to " + commandName(npc) + "."), false);
        return 1;
    }

    private static int teleportNpcToPlayer(CommandSourceStack source, String npcName, ServerPlayer player) {
        AVNpc npc = findNpc(source, npcName);
        if (npc == null) {
            source.sendFailure(Component.literal("No spawned AvNpc named " + npcName + " is currently available."));
            return 0;
        }
        npc.getNavigation().stop();
        npc.setDeltaMovement(Vec3.ZERO);
        npc.teleportTo(player.serverLevel(), player.getX(), player.getY(), player.getZ(),
                Set.of(), npc.getYRot(), npc.getXRot());
        source.sendSuccess(() -> Component.literal("Teleported " + commandName(npc) + " to " + player.getGameProfile().getName() + "."), true);
        return 1;
    }

    private static int getDifficulty(CommandSourceStack source) {
        Difficulty difficulty = ProgressionUtil.getDifficulty(source.getServer());
        source.sendSuccess(() -> Component.literal("Current Annoying Villagers difficulty is " + difficulty.id()), false);
        return 1;
    }

    private static int setDifficulty(CommandSourceStack source, String name) {
        Difficulty difficulty = Difficulty.findByName(name);
        if (difficulty == null) {
            source.sendFailure(Component.literal("Unknown Annoying Villagers difficulty: " + name));
            return 0;
        }

        boolean changed = ProgressionUtil.setDifficulty(source.getServer(), difficulty);
        source.sendSuccess(() -> Component.literal("Annoying Villagers difficulty "
                + (changed ? "changed to " : "is already ")
                + difficulty.id()), true);
        return changed ? 1 : 0;
    }
}
