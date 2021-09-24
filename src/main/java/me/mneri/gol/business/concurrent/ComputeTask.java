package me.mneri.gol.business.concurrent;

import me.mneri.gol.data.model.World;

import java.util.concurrent.RecursiveTask;

public class ComputeTask extends RecursiveTask<Integer> {
    private int end;

    private int start;

    private final int threshold;

    private World world;

    public ComputeTask(World world, int threshold) {
        this(world, threshold, 0, world.getWidth());
    }

    private ComputeTask(World world, int threshold, int start, int end) {
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
                    if (count < 2) {
                        world.setNext(i, j, false);
                    } else if (count <= 3) {
                        world.setNext(i, j, true);
                        result++;
                    } else {
                        world.setNext(i, j, false);
                    }
                } else {
                    if (count == 3) {
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

    private int getAliveNeighboursCount(int i, int j) {
        int count = 0;

        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (k != 0 || l != 0) {
                    int x = (i + k + world.getWidth()) % world.getWidth();
                    int y = (j + l + world.getHeight()) % world.getHeight();

                    if (world.get(x, y))
                        count++;
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
