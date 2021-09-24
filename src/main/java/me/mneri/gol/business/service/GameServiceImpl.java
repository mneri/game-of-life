package me.mneri.gol.business.service;

import com.google.inject.Inject;
import me.mneri.gol.business.concurrent.ComputeTask;
import me.mneri.gol.data.config.Configuration;
import me.mneri.gol.data.model.World;

import java.util.concurrent.ForkJoinPool;

public class GameServiceImpl implements GameService {
    private final Configuration config;

    private final ForkJoinPool pool = new ForkJoinPool();

    @Inject
    public GameServiceImpl(Configuration config) {
        this.config = config;
    }

    @Override
    public void evolve(World world) {
        pool.invoke(new ComputeTask(world, config.getDefaultTaskThreshold()));
        world.step();
    }
}
