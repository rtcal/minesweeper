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
        int x = location.getBlockX() - boardLocation.getBlockX();
        int z = location.getBlockZ() - boardLocation.getBlockZ();

        return location.getBlockY() == boardLocation.getBlockY() ? grid.getTile(x, z) : null;
    }

    public void flagTile(Tile tile) {
        tile.toggle();

        Location loc = boardLocation.clone().add(tile.getX(), 0, tile.getZ());
        Material mat = tile.isFlagged() ? TileMaterial.getFlagged() : TileMaterial.getCover();

        loc.getBlock().setType(mat);
    }

    private void reveal(Tile tile) {
        tile.reveal();
        grid.decrementRemaining();
    }

    public boolean revealTile(Tile tile) {
        if (tile.isRevealed() || tile.isFlagged()) return false;

        if (tile.isBomb()) {
            revealBoard();
            return true;
        }

        reveal(tile);

        Location loc = boardLocation.clone().add(tile.getX(), -1, tile.getZ());
        loc.getBlock().setType(TileMaterial.getTile(tile.getValue()));
        loc.add(0, 1, 0).getBlock().setType(Material.AIR);

        if (tile.getValue() == 0) floodReveal(tile);

        return false;
    }

    private void floodReveal(Tile tile) {
        for (Tile neighbour : grid.getNeighbours(tile)) {
            if (neighbour.isRevealed() || neighbour.isFlagged() || neighbour.isBomb() || tile.getValue() > 0) continue;
            revealTile(neighbour);
        }
    }

    public void revealBoard() {
        for (int x = 0; x < width; x++) {

            for (int z = 0; z < length; z++) {
                Tile tile = grid.getTile(x, z);

                if (tile == null) continue;

                Location loc = boardLocation.clone();

                loc.add(x, -1, z).getBlock().setType(getTileMaterial(tile));
                loc.add(0, 1, 0).getBlock().setType(Material.AIR);

            }
        }
    }

    public void spawnBoard() {
        for (int x = 0; x < width; x++) {

            for (int z = 0; z < length; z++) {
                Tile tile = grid.getTile(x, z);

                if (tile == null) continue;

                Location loc = boardLocation.clone();

                loc.add(x, 0, z).getBlock().setType(TileMaterial.getCover());
                loc.subtract(0, 1, 0).getBlock().setType(Material.AIR);

            }
        }
    }

    private Material getTileMaterial(Tile tile) {
        return tile.isBomb() ? TileMaterial.getBomb() : TileMaterial.getTile(tile.getValue());
    }
}
