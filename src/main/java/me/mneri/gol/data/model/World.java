package me.mneri.gol.data.model;

import lombok.Getter;

public class World {
    private boolean[][] curr;

    @Getter
    private int height;

    private boolean[][] next;

    @Getter
    private int width;

    public World(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.curr = new boolean[width][height];
        this.next = new boolean[width][height];
    }

    public boolean get(final int i, final int j) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return false;
        }

        return curr[i][j];
    }

    public void set(final int i, final int j, final boolean val) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return;
        }

        curr[i][j] = val;
    }

    public void setNext(final int i, final int j, final boolean val) {
        next[i][j] = val;
    }

    public void step() {
        boolean[][] hold = curr;
        curr = next;
        next = hold;
    }
}
