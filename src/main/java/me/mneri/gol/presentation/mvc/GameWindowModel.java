package me.mneri.gol.presentation.mvc;

import lombok.Getter;
import lombok.Setter;
import me.mneri.gol.data.model.World;

import javax.inject.Inject;
import javax.inject.Named;

@Getter
@Setter
public class GameWindowModel {
    private int lastDragX;

    private int lastDragY;

    private int periodMillis;

    private boolean running;

    private World world;

    @Inject
    private void postConstruct(
            @Named("me.mneri.gol.world-height") int defaultWorldHeight,
            @Named("me.mneri.gol.world-width") int defaultWorldWidth) {
        world = new World(defaultWorldWidth, defaultWorldHeight);
    }
}
