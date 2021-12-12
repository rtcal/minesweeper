package com.rtcal.minesweeper.listeners;

import com.rtcal.minesweeper.Main;
import com.rtcal.minesweeper.game.Game;
import com.rtcal.minesweeper.game.Minesweeper;
import com.rtcal.minesweeper.game.Tile;
import com.rtcal.playerhandler.DelayHandler;
import com.rtcal.playerhandler.PlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private static final DelayHandler DELAYS = new DelayHandler();
    private static final long DELAY_LENGTH = 150;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (!event.getPlayer().getWorld().getName().equalsIgnoreCase(Main.getMinesweeperWorld().getName())) return;

        if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK)
                || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;

        event.setCancelled(true);

        Player player = event.getPlayer();

        if (DELAYS.has(player.getUniqueId())) return;

        Minesweeper arena = (Minesweeper) PlayerHandler.getArena(player.getUniqueId());

        if (arena == null || !event.hasBlock() || !arena.isRunning() || !arena.isEnabled()) return;

        Game game = arena.getGame();
        Tile tile = game.getTile(event.getClickedBlock().getLocation());

        if (tile == null) return;

        switch (event.getAction()) {
            case RIGHT_CLICK_BLOCK -> game.flagTile(tile);
            case LEFT_CLICK_BLOCK -> {
                if (game.revealTile(tile)) arena.stop();
            }
        }

        DELAYS.set(player.getUniqueId(), DELAY_LENGTH);
    }

}
