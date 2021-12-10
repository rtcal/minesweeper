package com.rtcal.minesweeper.game;

import org.bukkit.Location;
import org.bukkit.Material;

public final class Game {

    private final Location boardLocation;
    private final int width, length;
    private final Grid grid;

    public Game(Location boardLocation, int width, int length, int bombs) {
        this.width = width;
        this.length = length;
        this.boardLocation = boardLocation;

        this.grid = new Grid(width, length, bombs);
    }

    public Tile getTile(Location location) {
        return null;
    }

    public void flagTile(Tile tile) {

    }

    public void revealTile(Tile tile) {

    }

    public void revealBoard() {

    }

    public void spawnBoard() {

    }

    public Material getTileMaterial(Tile tile) {
        return tile.isBomb() ? TileMaterial.getBomb() : TileMaterial.getTile(tile.getValue());
    }
}
