package com.rtcal.minesweeper.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        World world = Bukkit.getWorld("world");
        World minesweeperWorld = Bukkit.getWorld("minesweeper");

        if (world == null || minesweeperWorld == null) return;

        if (event.getPlayer().getWorld().getName().equalsIgnoreCase("minesweeper")) {
            event.getPlayer().teleport(world.getSpawnLocation());
        }
    }

}