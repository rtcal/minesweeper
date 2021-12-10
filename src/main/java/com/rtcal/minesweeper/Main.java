package com.rtcal.minesweeper;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }
}
