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

package me.mneri.gol.business.service;

import me.mneri.gol.data.model.Cell;
import me.mneri.gol.data.model.World;

import java.util.concurrent.RecursiveAction;

/**
 * Update the state of the world in a multi-threaded fashion.
 */
public class EvolveTask extends RecursiveAction {
    /**
     * Number of alive neighbours required to make a cell die of loneliness.
     */
    private static final int UNDERPOPULATION_THRESHOLD = 2;

    /**
     * Number of alive neighbours required to make a cell die for overcrowding.
     */
    private static final int OVERPOPULATION_THRESHOLD = 3;

    /**
     * Number of alive neighbours required to make a cell born for reproduction.
     */
    private static final int REPRODUCTION_THRESHOLD = 3;

    /**
     * The current state of the world.
     */
    private final World currentWorld;

    /**
     * The future state of the world.
     */
    private final World futureWorld;

    /**
     * Maximum number of columns that a single thread will process.
     */
    private final int columnsPerThread;

    /**
     * Start y-index for processing.
     */
    private final int startColumn;

    /**
     * End y-index for processing.
     */
    private final int endColumn;

    EvolveTask(final World currentWorld,
               final World futureWorld,
               final int columnsPerThread) {
        this(currentWorld, futureWorld, 0, currentWorld.getWidth(), columnsPerThread);
    }

    /**
     * Create a new {@code ComputeTask} instance.
     *
     * @param currentWorld     The current state of the world.
     * @param futureWorld      The future state of the world, this is an outbound variable.
     * @param startColumn      The start y-index of the columns to process.
     * @param endColumn        The end y-index of the columns to process.
     * @param columnsPerThread The maximum number of columns a single thread can handle.
     */
    private EvolveTask(
            final World currentWorld,
            final World futureWorld,
            final int startColumn,
            final int endColumn,
            final int columnsPerThread) {
        this.currentWorld = currentWorld;
        this.futureWorld = futureWorld;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
        this.columnsPerThread = columnsPerThread;
    }

    @Override
    protected void compute() {
        if ((endColumn - startColumn) <= columnsPerThread) {
            doCompute();
        } else {
            int middleColumn = startColumn + columnsPerThread;
            invokeAll(
                    new EvolveTask(currentWorld, futureWorld, startColumn, middleColumn, columnsPerThread),
                    new EvolveTask(currentWorld, futureWorld, middleColumn, endColumn, columnsPerThread));
        }
    }

    protected void doCompute() {
        for (int j = 0; j < currentWorld.getHeight(); j++) {
            for (int i = startColumn; i < currentWorld.getWidth(); i++) {
                int count = currentWorld.getLiveNeighboursCount(i, j);

                if (currentWorld.getState(i, j) == Cell.ALIVE) {
                    if (count < UNDERPOPULATION_THRESHOLD) {
                        futureWorld.setState(i, j, Cell.DEAD);
                    } else if (count <= OVERPOPULATION_THRESHOLD) {
                        futureWorld.setState(i, j, Cell.ALIVE);
                    } else {
                        futureWorld.setState(i, j, Cell.DEAD);
                    }
                } else {
                    if (count == REPRODUCTION_THRESHOLD) {
                        futureWorld.setState(i, j, Cell.ALIVE);
                    } else {
                        futureWorld.setState(i, j, Cell.DEAD);
                    }
                }
            }
        }
    }
}
