package com.pla.annoyingvillagers.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.util.ProgressionUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AnnoyingVillagersCommandEvent {
    private AnnoyingVillagersCommandEvent() {
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("annoyingvillagers")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("spawn_player")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(context -> spawnPlayer(
                                        context.getSource(),
                                        StringArgumentType.getString(context, "name")
                                ))))
                .then(Commands.literal("difficulty")
                        .then(Commands.literal("get")
                                .executes(context -> getDifficulty(context.getSource())))
                        .then(Commands.literal("set")
                                .then(Commands.argument("difficulty", StringArgumentType.word())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(new String[]{"easy", "medium", "hard"}, builder))
                                        .executes(context -> setDifficulty(
                                                context.getSource(),
                                                StringArgumentType.getString(context, "difficulty")
                                        ))))));
    }

    private static int spawnPlayer(CommandSourceStack source, String name) {
        ServerLevel level = source.getLevel();
        PlayerNpcEntity entity = AnnoyingVillagersModEntities.PLAYER_NPC.get().create(level);
        if (entity == null) {
            source.sendFailure(Component.literal("Failed to create player NPC"));
            return 0;
        }

        Vec3 position = source.getPosition();
        Vec2 rotation = source.getRotation();
        entity.moveTo(position.x, position.y, position.z, rotation.y, rotation.x);
        entity.setUsername(name);
        DifficultyInstance difficulty = level.getCurrentDifficultyAt(entity.blockPosition());
        entity.finalizeSpawn(level, difficulty, MobSpawnType.COMMAND, null, null);
        level.addFreshEntity(entity);
        source.sendSuccess(() -> Component.literal("Spawned player NPC " + entity.getName().getString()), true);
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
