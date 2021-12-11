package com.rtcal.minesweeper;

import org.bukkit.configuration.file.FileConfiguration;

public final class Messages {

    private static final FileConfiguration config = Main.getInstance().getConfig();

    private static final String PREFIX = config.getString("prefix") + " ";

    public static final String BROADCAST_LEAVE = PREFIX + config.getString("messages.broadcast_leave");
    public static final String BROADCAST_JOIN = PREFIX + config.getString("messages.broadcast_join");
    public static final String BROADCAST_START = PREFIX + config.get("messages.broadcast_start");
    public static final String BROADCAST_OVER = PREFIX + config.get("messages.broadcast_over");

    public static final String PLAYER_BUSY = PREFIX + config.getString("messages.player_busy");
    public static final String PLAYER_ONLY_COMMAND = PREFIX + config.getString("messages.player_only_command");

    public static final String ARENA_FULL = PREFIX + config.getString("messages.arena_full");
    public static final String ARENA_WRONG_WORLD = PREFIX + config.getString("messages.arena_wrong_world");

    public static final String ARENA_CREATION_SUCCESS = PREFIX + config.getString("messages.arena_creation_success");
    public static final String ARENA_CREATION_FAILED = PREFIX + config.getString("messages.arena_creation_failed");
}
