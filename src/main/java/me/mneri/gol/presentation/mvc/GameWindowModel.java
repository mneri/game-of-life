package me.mneri.gol.presentation.mvc;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import me.mneri.gol.data.config.Configuration;
import me.mneri.gol.data.model.World;

@Getter
@Setter
public class GameWindowModel {
    private int lastDragX;

    private int lastDragY;

    private int periodMillis;

    private boolean running;

    private World world;

    @Inject
    public GameWindowModel(Configuration config) {
        world = new World(config.getDefaultWorldWidth(), config.getDefaultWorldHeight());
    }
}
