package com.pla.annoyinglib.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

import javax.annotation.Nullable;

public class TeamUtil {
    public static boolean isInTeam(Entity entity, String teamName) {
        if (entity == null) return false;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return false;

        Scoreboard scoreboard = serverLevel.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(teamName);
        if (team == null) return false;

        String entry = entity.getScoreboardName();
        PlayerTeam current = scoreboard.getPlayersTeam(entry);
        return current != null && current == team;
    }

    public static void addOrJoinTeam(Entity entity, String teamName) {
        if (entity == null) return;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;

        Scoreboard scoreboard = serverLevel.getScoreboard();

        PlayerTeam team = scoreboard.getPlayerTeam(teamName);
        if (team == null) {
            team = scoreboard.addPlayerTeam(teamName);
        }
        team.setAllowFriendlyFire(false);

        String entry = entity.getScoreboardName();

        PlayerTeam current = scoreboard.getPlayersTeam(entry);
        if (current == team) return;

        if (current != null) {
            scoreboard.removePlayerFromTeam(entry, current);
        }

        scoreboard.addPlayerToTeam(entry, team);
    }

    public static void leaveTeam(Entity entity, String teamName) {
        if (entity == null) return;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;

        Scoreboard scoreboard = serverLevel.getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(teamName);
        if (team == null) return;

        String entry = entity.getScoreboardName();

        PlayerTeam current = scoreboard.getPlayersTeam(entry);
        if (current == team) {
            scoreboard.removePlayerFromTeam(entry, team);
        }
    }

    @Nullable
    public static String getTeamName(Entity entity) {
        if (entity == null) return null;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return null;

        Scoreboard scoreboard = serverLevel.getScoreboard();
        PlayerTeam current = scoreboard.getPlayersTeam(entity.getScoreboardName());

        return current != null ? current.getName() : null;
    }

    public static void leaveCurrentTeam(Entity entity) {
        String teamName = getTeamName(entity);
        if (teamName != null) {
            leaveTeam(entity, teamName);
        }
    }
}
