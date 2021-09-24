package me.mneri.gol.data.model;

import lombok.Getter;

public class World {
    private boolean[][] curr;

    @Getter
    public int height;

    private boolean[][] next;

    @Getter
    public int width;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.curr = new boolean[width][height];
        this.next = new boolean[width][height];
    }

    public boolean get(int i, int j) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return false;
        }

        return curr[i][j];
    }

    public void set(int i, int j, boolean val) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return;
        }

        curr[i][j] = val;
    }

    public void setNext(int i, int j, boolean val) {
        next[i][j] = val;
    }

    public void step() {
        boolean[][] hold = curr;
        curr = next;
        next = hold;
    }
}
