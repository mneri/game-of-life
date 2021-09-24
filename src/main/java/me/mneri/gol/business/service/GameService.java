package me.mneri.gol.business.service;

import com.google.inject.ImplementedBy;
import me.mneri.gol.data.model.World;

@ImplementedBy(GameServiceImpl.class)
public interface GameService {
    void evolve(World world);
}
