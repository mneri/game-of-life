package me.mneri.gol.presentation.mvc;

import com.google.inject.Provider;
import lombok.Getter;
import lombok.Setter;
import me.mneri.gol.business.service.GameService;
import me.mneri.gol.data.model.World;

import javax.inject.Inject;
import javax.inject.Named;

public class GameWindowModel {
    public interface WorldUpdateListener {
        void onWorldUpdate();
    }

    /**
     * The game service, in charge of the business logic.
     */
    private final GameService gameService;

    @Getter
    @Setter
    private int lastDragX;

    @Getter
    @Setter
    private int lastDragY;

    @Getter
    @Setter
    private int periodMillis;

    @Getter
    @Setter
    private boolean running;

    @Getter
    @Setter
    private World world;

    @Setter
    private WorldUpdateListener worldUpdateListener;

    @Inject
    protected GameWindowModel(final Provider<GameService> serviceProvider) {
        this.gameService = serviceProvider.get();
    }

    @Inject
    @SuppressWarnings("unused")
    private void postConstruct(
            @Named("me.mneri.gol.world-height") final int defaultWorldHeight,
            @Named("me.mneri.gol.world-width") final int defaultWorldWidth) {
        world = new World(defaultWorldWidth, defaultWorldHeight);
    }

    /**
     * Stop the game.
     */
    public void pause() {
        running = false;
    }

    /**
     * Start the game.
     * <p>
     * The game is run on a background thread
     */
    @SuppressWarnings("BusyWait") // Thread.sleep(long) is not a busy-wait
    public void play() {
        if (running) {
            return;
        }

        running = true;

        (new Thread(() -> {
            try {
                while (running) {
                    gameService.evolve(world);
                    worldUpdateListener.onWorldUpdate();
                    Thread.sleep(periodMillis);
                }
            } catch (InterruptedException ignored) {
            }
        })).start();
    }
}
