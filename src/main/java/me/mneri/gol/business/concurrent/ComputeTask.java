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

package me.mneri.gol.business.concurrent;

import me.mneri.gol.data.model.World;

import java.util.concurrent.RecursiveTask;

/**
 * Update the state of the world in a multi-threaded fashion.
 *
 * @author Massimo Neri
 */
public class ComputeTask extends RecursiveTask<Integer> {
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
     * End y-index for processing.
     */
    private int end;

    /**
     * Start y-index for processing.
     */
    private int start;

    /**
     * Maximum number of columns for a single thread to process.
     */
    private final int threshold;

    /**
     * The Game of life world.
     */
    private World world;

    /**
     * Create a new {@code ComputeTask} instance.
     *
     * @param world     The Game of Life world.
     * @param threshold The maximum number of columns a single thread can process.
     */
    public ComputeTask(final World world, final int threshold) {
        this(world, threshold, 0, world.getWidth());
    }

    /**
     * Create a new {@code ComputeTask} instance.
     *
     * @param world     The Game of Life world.
     * @param threshold The maximum number of columns a single thread can process.
     * @param start     The start y-index of the columns to process.
     * @param end       The end y-index of the columns to process.
     */
    private ComputeTask(final World world, final int threshold, final int start, final int end) {
        this.world = world;
        this.threshold = threshold;
        this.start = start;
        this.end = end;
    }

    protected int doCompute() {
        int result = 0;

        for (int i = start; i < end; i++) {
            for (int j = 0; j < world.getHeight(); j++) {
                int count = getAliveNeighboursCount(i, j);

                if (world.get(i, j)) {
                    if (count < UNDERPOPULATION_THRESHOLD) {
                        world.setNext(i, j, false);
                    } else if (count <= OVERPOPULATION_THRESHOLD) {
                        world.setNext(i, j, true);
                        result++;
                    } else {
                        world.setNext(i, j, false);
                    }
                } else {
                    if (count == REPRODUCTION_THRESHOLD) {
                        world.setNext(i, j, true);
                        result++;
                    } else {
                        world.setNext(i, j, false);
                    }
                }
            }
        }

        return result;
    }

    private int getAliveNeighboursCount(final int i, final int j) {
        int count = 0;

        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (k != 0 || l != 0) {
                    int x = (i + k + world.getWidth()) % world.getWidth();
                    int y = (j + l + world.getHeight()) % world.getHeight();

                    if (world.get(x, y)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    @Override
    protected Integer compute() {
        if (end - start <= threshold) {
            return doCompute();
        }

        int split = start + threshold;
        ComputeTask t1 = new ComputeTask(world, threshold, start, split);
        t1.fork();
        ComputeTask t2 = new ComputeTask(world, threshold, split, end);
        return t2.compute() + t1.join();
    }
}
