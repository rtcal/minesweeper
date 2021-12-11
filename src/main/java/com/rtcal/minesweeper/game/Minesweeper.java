package com.rtcal.minesweeper.game;

import com.rtcal.arenahandler.base.Arena;
import com.rtcal.arenahandler.base.Team;
import com.rtcal.arenahandler.exceptions.ArenaAlreadyExistsException;
import com.rtcal.minesweeper.Messages;
import com.rtcal.playerhandler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Minesweeper extends Arena {

    private static int arenas = 0;

    private final Game game;
    private final Team team;
    private final Location spawnLocation;

    public Minesweeper(Location boardLocation, int width, int length, int bombs) throws ArenaAlreadyExistsException {
        super("minesweeper", arenas);
        arenas++;

        this.spawnLocation = boardLocation.clone().add(width / 2.0, 1, length / 2.0);

        this.game = new Game(boardLocation, width, length, bombs);
        this.team = new Team(5);
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void join(Team team, Player player) {
        if (PlayerHandler.isBusy(player.getUniqueId())) {
            player.sendMessage(Messages.PLAYER_BUSY);
            return;
        }

        if (team.getPlayers().size() >= team.getCapacity()) {
            player.sendMessage(Messages.ARENA_FULL);
            return;
        }

        team.addPlayer(player.getUniqueId());
        PlayerHandler.makeBusy(player.getUniqueId(), this);
        broadcast(Messages.BROADCAST_JOIN.replace("<player>", player.getDisplayName()));
    }

    @Override
    public void leave(Player player) {
        if (team.containsPlayer(player.getUniqueId())) return;

        team.removePlayer(player.getUniqueId());
        PlayerHandler.makeAvailable(player.getUniqueId());

        World world = Bukkit.getWorld("world");
        if (world != null) player.teleport(world.getSpawnLocation());

        if (isRunning()) {
            broadcast(Messages.BROADCAST_LEAVE.replace("<player>", player.getDisplayName()));
        }
    }

    @Override
    public void start() {
        Set<UUID> failed = new HashSet<>();

        for (UUID id : team.getPlayers()) {
            Player player = Bukkit.getPlayer(id);

            if (player == null) failed.add(id);
            else player.teleport(spawnLocation);
        }

        for (UUID id : failed) {
            team.removePlayer(id);
        }

        broadcast(Messages.BROADCAST_START);
    }

    @Override
    public void stop() {
        setRunning(false);

        broadcast(Messages.BROADCAST_OVER);

        for (UUID id : team.getPlayers()) {
            Player player = Bukkit.getPlayer(id);

            if (player != null) leave(player);
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void broadcast(String s) {
        for (UUID id : team.getPlayers()) {
            Player player = Bukkit.getPlayer(id);

            if (player != null) player.sendMessage(s);
        }
    }
}
