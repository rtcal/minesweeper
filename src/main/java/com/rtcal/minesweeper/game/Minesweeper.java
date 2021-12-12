package com.rtcal.minesweeper.game;

import com.rtcal.arenahandler.base.Arena;
import com.rtcal.arenahandler.base.Team;
import com.rtcal.arenahandler.exceptions.ArenaAlreadyExistsException;
import com.rtcal.minesweeper.Main;
import com.rtcal.minesweeper.Messages;
import com.rtcal.playerhandler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Minesweeper extends Arena {

    public static final String TYPE = "minesweeper";

    private static int arenas = 1;

    private final Game game;
    private final Team team;
    private final Location spawnLocation, boardLocation;
    private final int width, length, bombs;

    public Minesweeper(Location boardLocation, int width, int length, int bombs) throws ArenaAlreadyExistsException {
        super(TYPE, arenas);
        arenas++;

        this.width = width;
        this.length = length;
        this.bombs = bombs;

        this.boardLocation = boardLocation;
        this.spawnLocation = boardLocation.clone().add(width / 2.0, 1, length / 2.0);

        this.game = new Game(boardLocation, width, length, bombs);
        this.team = new Team(5);
    }

    public Game getGame() {
        return game;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public void join(Team team, Player player) {
        if (!isEnabled() || isRunning()) {
            player.sendMessage(Messages.ARENA_BUSY);
            return;
        }

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
        if (!team.containsPlayer(player.getUniqueId())) return;

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
        setRunning(true);
        game.spawnBoard();

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

        reset();
    }

    @Override
    public void reset() {
        game.reset();
    }

    @Override
    public void broadcast(String s) {
        for (UUID id : team.getPlayers()) {
            Player player = Bukkit.getPlayer(id);

            if (player != null) player.sendMessage(s);
        }
    }

    public JSONObject save() {
        JSONObject object = new JSONObject();
        object.put("board", boardLocation.toString());
        object.put("spawn", spawnLocation.toString());
        object.put("width", width);
        object.put("length", length);
        object.put("bombs", bombs);

        return object;
    }

    public static void saveAll() {
        JSONArray arenas = new JSONArray();

        for (Arena genericArena : Arena.getArenas(TYPE)) {
            Minesweeper arena = (Minesweeper) genericArena;
            arenas.put(arena.save());
        }

        try (FileWriter file = new FileWriter(Main.getInstance().getDataFolder() + "/arenas.json")) {
            file.write(arenas.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
