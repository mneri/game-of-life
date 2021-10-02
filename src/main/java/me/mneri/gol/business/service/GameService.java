package me.mneri.gol.business.service;

import com.google.inject.ImplementedBy;
import me.mneri.gol.data.model.World;

/**
 * The game service, implementing the business logic of world's evolution.
 *
 * @author Massimo Neri
 */
@ImplementedBy(GameServiceImpl.class)
public interface GameService {
    /**
     * Evolve the world.
     *
     * @param world The world.
     */
    void evolve(World world);
}
