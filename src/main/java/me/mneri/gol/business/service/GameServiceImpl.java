package me.mneri.gol.business.service;

import me.mneri.gol.business.concurrent.ComputeTask;
import me.mneri.gol.data.model.World;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.ForkJoinPool;

/**
 * Default implementation of {@link GameService}.
 *
 * @author Massimo Neri
 */
public class GameServiceImpl implements GameService {
    @Inject
    @Named("me.mneri.gol.task-threshold")
    @SuppressWarnings("unused")
    private int defaultTaskThreshold;

    private final ForkJoinPool pool = new ForkJoinPool();

    /**
     * {@inheritDoc}
     *
     * @param world The world.
     */
    @Override
    public void evolve(final World world) {
        pool.invoke(new ComputeTask(world, defaultTaskThreshold));
        world.step();
    }
}
