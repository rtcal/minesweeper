package com.rtcal.minesweeper;

import com.rtcal.command.Command;
import com.rtcal.minesweeper.commands.Create;
import com.rtcal.minesweeper.commands.Join;
import com.rtcal.minesweeper.listeners.BlockBreakListener;
import com.rtcal.minesweeper.listeners.BlockPlaceListener;
import com.rtcal.minesweeper.listeners.PlayerInteractListener;
import com.rtcal.minesweeper.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

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

        cmd.register(this);
    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }
}
