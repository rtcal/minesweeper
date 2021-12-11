package com.rtcal.minesweeper.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        World world = event.getBlock().getWorld();

        if (world.getName().equalsIgnoreCase("minesweeper"))
            event.setCancelled(true);
    }

}