package com.pla.annoyingvillagers.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.util.ProgressionUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
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
