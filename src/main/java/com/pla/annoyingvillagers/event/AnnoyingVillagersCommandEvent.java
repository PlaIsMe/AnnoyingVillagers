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
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AnnoyingVillagersCommandEvent {
    private static final double PLAY_ANIMATION_RADIUS = 20.0D;
    private static final String[] RIG_ANIMATION_SUGGESTIONS = createRigAnimationSuggestions();

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
                .then(Commands.literal("play")
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .then(Commands.argument("animation", StringArgumentType.word())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(RIG_ANIMATION_SUGGESTIONS, builder))
                                        .executes(context -> playAnimation(
                                                context.getSource(),
                                                EntityArgument.getEntities(context, "targets"),
                                                StringArgumentType.getString(context, "animation")
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

    private static int playAnimation(CommandSourceStack source, Collection<? extends Entity> targets, String animationName) {
        RigAnimationId animationId = parseRigAnimationId(animationName);
        if (animationId == null) {
            source.sendFailure(Component.literal("Unknown rig animation: " + animationName));
            return 0;
        }

        Set<Integer> playedEntityIds = new HashSet<>();
        Vec3 sourcePosition = source.getPosition();
        ServerLevel sourceLevel = source.getLevel();

        for (Entity target : targets) {
            if (target instanceof ServerPlayer player) {
                playNearbyRigMobs(player.serverLevel(), player.position(), animationId, playedEntityIds);
            } else if (target.level() == sourceLevel && target instanceof Mob mob && isRigModelEntity(mob)
                    && target.distanceToSqr(sourcePosition) <= PLAY_ANIMATION_RADIUS * PLAY_ANIMATION_RADIUS) {
                playRigMob(mob, animationId, playedEntityIds);
            }
        }

        int playedCount = playedEntityIds.size();
        if (playedCount == 0) {
            source.sendFailure(Component.literal("No rig model entities found within 5 blocks."));
            return 0;
        }

        source.sendSuccess(() -> Component.literal("Played " + animationId.name().toLowerCase(Locale.ROOT)
                + " on " + playedCount + " rig model " + (playedCount == 1 ? "entity." : "entities.")), true);
        return playedCount;
    }

    private static void playNearbyRigMobs(ServerLevel level, Vec3 center, RigAnimationId animationId, Set<Integer> playedEntityIds) {
        AABB bounds = new AABB(center, center).inflate(PLAY_ANIMATION_RADIUS);
        double radiusSqr = PLAY_ANIMATION_RADIUS * PLAY_ANIMATION_RADIUS;
        for (Mob mob : level.getEntitiesOfClass(Mob.class, bounds, mob -> isRigModelEntity(mob) && mob.distanceToSqr(center) <= radiusSqr)) {
            playRigMob(mob, animationId, playedEntityIds);
        }
    }

    private static void playRigMob(Mob mob, RigAnimationId animationId, Set<Integer> playedEntityIds) {
        if (playedEntityIds.add(mob.getId())) {
            RigAnimationController.play(mob, animationId);
        }
    }

    private static boolean isRigModelEntity(Mob mob) {
        EntityType<?> type = mob.getType();
        return type == AnnoyingVillagersModEntities.BLUE_DEMON.get()
                || type == AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get()
                || type == AnnoyingVillagersModEntities.VILLAGER_SCOUT.get()
                || type == AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get()
                || type == AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get()
                || type == AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get()
                || type == AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get()
                || type == AnnoyingVillagersModEntities.ALEX.get()
                || type == AnnoyingVillagersModEntities.JEV.get()
                || type == AnnoyingVillagersModEntities.CHRIS.get()
                || type == AnnoyingVillagersModEntities.STEVE.get()
                || type == AnnoyingVillagersModEntities.ANGRY_STEVE.get()
                || type == AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get()
                || type == AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get()
                || type == AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get()
                || type == AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get()
                || type == AnnoyingVillagersModEntities.REAPER_HEROBRINE.get()
                || type == AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get()
                || type == AnnoyingVillagersModEntities.INFECTED_CHRIS.get()
                || type == AnnoyingVillagersModEntities.HEROBRINE_CLONE.get()
                || type == AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get()
                || type == AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get()
                || type == AnnoyingVillagersModEntities.HEROBRINE_GREG.get()
                || type == AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get()
                || type == AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get()
                || type == AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get()
                || type == AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get()
                || type == AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get()
                || type == AnnoyingVillagersModEntities.NULL.get()
                || type == AnnoyingVillagersModEntities.NULL_SWORD.get()
                || type == AnnoyingVillagersModEntities.NULL_AXE.get()
                || type == AnnoyingVillagersModEntities.NULL_PICKAXE.get()
                || type == AnnoyingVillagersModEntities.NULL_SHOVEL.get()
                || type == AnnoyingVillagersModEntities.NULL_HOE.get();
    }

    private static RigAnimationId parseRigAnimationId(String animationName) {
        try {
            return RigAnimationId.valueOf(animationName.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private static String[] createRigAnimationSuggestions() {
        RigAnimationId[] animationIds = RigAnimationId.values();
        String[] suggestions = new String[animationIds.length];
        for (int i = 0; i < animationIds.length; i++) {
            suggestions[i] = animationIds[i].name().toLowerCase(Locale.ROOT);
        }
        return suggestions;
    }
}
