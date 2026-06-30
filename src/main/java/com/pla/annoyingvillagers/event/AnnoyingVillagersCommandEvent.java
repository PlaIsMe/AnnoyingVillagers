package com.pla.annoyingvillagers.event;

import com.mojang.brigadier.CommandDispatcher;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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
                        .executes(context -> spawnPlayer(context.getSource()))));
    }

    private static int spawnPlayer(CommandSourceStack source) {
        ServerLevel level = source.getLevel();
        PlayerNpcEntity entity = AnnoyingVillagersModEntities.PLAYER_NPC.get().create(level);
        if (entity == null) {
            source.sendFailure(Component.literal("Failed to create player NPC"));
            return 0;
        }

        Vec3 position = source.getPosition();
        Vec2 rotation = source.getRotation();
        entity.moveTo(position.x, position.y, position.z, rotation.y, rotation.x);
        DifficultyInstance difficulty = level.getCurrentDifficultyAt(entity.blockPosition());
        entity.finalizeSpawn(level, difficulty, MobSpawnType.COMMAND, null, null);
        level.addFreshEntity(entity);
        source.sendSuccess(() -> Component.literal("Spawned player NPC " + entity.getName().getString()), true);
        return 1;
    }
}
