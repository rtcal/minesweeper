package com.rtcal.minesweeper.game;

import org.bukkit.Material;

public final class TileMaterial {

    private static final Material[] MATERIALS = {
            Material.WHITE_WOOL, // 0 bombs
            Material.LIGHT_BLUE_WOOL, // 1 bombs
            Material.LIME_WOOL, // 2 bombs
            Material.RED_WOOL, // 3 bombs
            Material.BLUE_WOOL, // 4 bombs
            Material.BROWN_WOOL, // 5 bombs
            Material.CYAN_WOOL, // 6 bombs
            Material.BLACK_WOOL, // 7 bombs
            Material.GRAY_WOOL // 8 bombs
    };

    public static Material getFlagged() {
        return Material.STONE;
    }

    public static Material getCover() {
        return Material.YELLOW_WOOL;
    }

    public static Material getBomb() {
        return Material.TNT;
    }

    public static Material getTile(int value) {
        return MATERIALS[value];
    }
}
