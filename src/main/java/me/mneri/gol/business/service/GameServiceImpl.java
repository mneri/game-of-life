package me.mneri.gol.business.service;

import me.mneri.gol.business.concurrent.ComputeTask;
import me.mneri.gol.data.model.World;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ForkJoinPool;

public class GameServiceImpl implements GameService {
    @Inject
    @Named("me.mneri.gol.task-threshold")
    @SuppressWarnings("unused")
    private int defaultTaskThreshold;

    private final ForkJoinPool pool = new ForkJoinPool();

    @Override
    public void evolve(final World world) {
        pool.invoke(new ComputeTask(world, defaultTaskThreshold));
        world.step();
    }
}
