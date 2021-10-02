package me.mneri.gol.data.model;

import lombok.Getter;

/**
 * The Game of Life world.
 *
 * @author Massimo Neri
 */
public class World {
    private boolean[][] curr;

    /**
     * The height of the world expressed in number of cells.
     */
    @Getter
    private int height;

    private boolean[][] next;

    /**
     * The width of the world expressed in number of cells.
     */
    @Getter
    private int width;

    /**
     * Construct a new {@code World} instance.
     *
     * @param width  The width of the world expressed in number of cells.
     * @param height The height of the world expressed in number of cells.
     */
    public World(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.curr = new boolean[width][height];
        this.next = new boolean[width][height];
    }

    /**
     * Return the state of the cell at the given coordinates.
     *
     * @param i The horizontal coordinate.
     * @param j The vertical coordinate.
     * @return {@code true} if the cell is alive, {@code false} otherwise.
     */
    public boolean get(final int i, final int j) {
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return false;
        }

        return curr[i][j];
    }

    /**
     * Set the state of the cell at the given coordinates.
     * @param i The horizontal coordinate.
     * @param j The vertical coordinate.
     * @param val {@code true} if the cell is alive, {@code false} otherwise.
     */
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
