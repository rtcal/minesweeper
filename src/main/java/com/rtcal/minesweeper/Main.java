package com.rtcal.minesweeper;

import com.rtcal.command.Command;
import com.rtcal.minesweeper.commands.Create;
import com.rtcal.minesweeper.commands.Join;
import com.rtcal.minesweeper.commands.Start;
import com.rtcal.minesweeper.game.Minesweeper;
import com.rtcal.minesweeper.listeners.BlockBreakListener;
import com.rtcal.minesweeper.listeners.BlockPlaceListener;
import com.rtcal.minesweeper.listeners.PlayerInteractListener;
import com.rtcal.minesweeper.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static World minesweeperWorld;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);

        Command cmd = new Command("minesweeper");
        cmd.addChild(new Create());
        cmd.addChild(new Join());
        cmd.addChild(new Start());

        cmd.register(this);

        minesweeperWorld = Bukkit.getWorld("Minesweeper");

        if (minesweeperWorld == null) {
            this.getLogger().info("Minesweeper world not found!");
            this.getLogger().info("Disabling myself...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Minesweeper.saveAll();
    }

    public static Main getInstance() {
        return instance;
    }

    public static World getMinesweeperWorld() {
        return minesweeperWorld;
    }
}
