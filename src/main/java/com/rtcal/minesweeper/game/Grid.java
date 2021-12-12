package com.rtcal.minesweeper.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Grid {

    private static final int[][] NEIGHBOUR_OFFSETS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private final Tile[][] grid;
    private final int width, length, bombs;

    private int remaining;
    private boolean populated = false;

    public Grid(int width, int length, int bombs) {
        this.width = width;
        this.length = length;
        this.bombs = bombs;

        grid = new Tile[width][length];

        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                grid[x][z] = new Tile(x, z);
            }
        }

        reset();
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getBombs() {
        return bombs;
    }

    public int getRemaining() {
        return remaining;
    }

    public boolean isPopulated() {
        return populated;
    }

    public void decrementRemaining() {
        remaining--;
    }

    public Tile getTile(int x, int z) {
        return grid[x][z];
    }

    public List<Tile> getNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>();

        for (int[] offsets : NEIGHBOUR_OFFSETS) {
            int checkX = tile.getX() + offsets[0];
            int checkZ = tile.getZ() + offsets[1];

            if (validCoordinates(checkX, checkZ)) neighbours.add(getTile(checkX, checkZ));
        }

        return neighbours;
    }

    public void populate(Tile initial) {
        List<Tile> tiles = Arrays.stream(grid).flatMap(Arrays::stream).filter(tile -> tile != initial).collect(Collectors.toList());

        Collections.shuffle(tiles);

        for (int i = 0; i < bombs; i++) {
            Tile tile = tiles.get(i);
            tile.setBomb();

            for (Tile neighbour : getNeighbours(tile)) {
                neighbour.increment();
            }
        }

        populated = true;
    }

    private boolean validCoordinates(int x, int z) {
        return x >= 0 && z >= 0 && x < width && z < length;
    }

    public void reset() {
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                getTile(x, z).reset();
            }
        }
        this.populated = false;
        this.remaining = width * length - bombs;
    }
}
