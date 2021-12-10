package com.rtcal.minesweeper.game;

public final class Tile {

    private final int x, z;

    private int value = 0;
    private boolean bomb = false;
    private boolean revealed = false;
    private boolean flagged = false;

    public Tile(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getValue() {
        return value;
    }

    public boolean isBomb() {
        return bomb;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setBomb() {
        this.bomb = true;
    }

    public void reveal() {
        this.revealed = true;
    }

    public void toggle() {
        flagged = !flagged;
    }

    public void increment() {
        value++;
    }

    public void reset() {
        value = 0;
        bomb = false;
        revealed = false;
        flagged = false;
    }
}
