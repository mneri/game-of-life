/*
 * Copyright 2020 Massimo Neri <hello@mneri.me>
 *
 * This file is part of mneri/gol.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.mneri.gol.data.model;

import lombok.Getter;

/**
 * The Game of Life world.
 *
 * @author Massimo Neri
 */
public class ToroidalWorld implements World {
    /**
     * The number of live cells.
     */
    @Getter
    private int liveCellsCount;

    /**
     * The current state of the world.
     */
    private int[][] cells;

    /**
     * The height of the world expressed in number of cells.
     */
    @Getter
    private int height;

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
    public ToroidalWorld(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.cells = new int[height][width];
    }

    private int ensureXCoord(final int x) {
        return (x % width) + (x >= 0 ? 0 : width);
    }

    private int ensureYCoord(final int y) {
        return (y % height) + (y >= 0 ? 0 : height);
    }

    /**
     * Return the state of the cell at the given coordinates.
     *
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     * @return {@code true} if the cell is alive, {@code false} otherwise.
     */
    @Override
    public int getState(final int x, final int y) {
        return cells[ensureYCoord(y)][ensureXCoord(x)];
    }

    @Override
    public int getLiveNeighboursCount(final int x, final int y) {
        int count = 0;

        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                if (j != 0 || i != 0) {
                    if (getState(x + i, y + j) == Cell.ALIVE) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Set the state of the cell at the given coordinates.
     *
     * @param x        The horizontal coordinate.
     * @param y        The vertical coordinate.
     * @param newState {@code true} if the cell is alive, {@code false} otherwise.
     */
    @Override
    public void setState(final int x, final int y, final int newState) {
        int x2 = ensureXCoord(x);
        int y2 = ensureYCoord(y);
        int oldState = cells[y2][x2];

        if (oldState != newState) {
            liveCellsCount += newState == Cell.ALIVE ? 1 : -1;
            cells[y2][x2] = newState;
        }
    }
}
