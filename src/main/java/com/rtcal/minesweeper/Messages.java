package com.rtcal.minesweeper;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public final class Messages {

    private static final FileConfiguration config = Main.getInstance().getConfig();

    private static final String PREFIX = ChatColor.translateAlternateColorCodes('&', config.getString("prefix") + " ");

    public static final String BROADCAST_LEAVE = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.broadcast_leave"));
    public static final String BROADCAST_JOIN = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.broadcast_join"));
    public static final String BROADCAST_START = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.broadcast_start"));
    public static final String BROADCAST_OVER = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.broadcast_over"));

    public static final String PLAYER_BUSY = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.player_busy"));
    public static final String PLAYER_ONLY_COMMAND = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.player_only_command"));

    public static final String ARENA_FULL = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.arena_full"));
    public static final String ARENA_WRONG_WORLD = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.arena_wrong_world"));

    public static final String ARENA_CREATION_SUCCESS = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.arena_creation_success"));
    public static final String ARENA_CREATION_FAILED = PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("messages.arena_creation_failed"));
}
