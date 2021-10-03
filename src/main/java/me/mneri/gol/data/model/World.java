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

public interface World {
    /**
     * Return the state of the cell at the given coordinates.
     *
     * @param i The horizontal coordinate.
     * @param j The vertical coordinate.
     * @return {@code true} if the cell is alive, {@code false} otherwise.
     */
    int getState(int i, int j);

    /**
     * Return the number of live cells.
     *
     * @return The number of live cells.
     */
    int getLiveCellsCount();

    /**
     * Return the number of live neighbours for the cell at the given coordinates.
     *
     * @param i The horizontal coordinate of the cell.
     * @param j The vertical coordinate of the cell.
     * @return The number of live neighbours.
     */
    int getLiveNeighboursCount(int i, int j);

    /**
     * Return the world height in number of cells.
     *
     * @return The world height.
     */
    int getHeight();

    /**
     * Return the world width in number of cells.
     *
     * @return The world width.
     */
    int getWidth();

    /**
     * Set the state of the cell at the given coordinates.
     *
     * @param i        The horizontal coordinate of the cell.
     * @param j        The vertical coordinate of the cell.
     * @param newState The new state of the cell.
     */
    void setState(int i, int j, int newState);
}
