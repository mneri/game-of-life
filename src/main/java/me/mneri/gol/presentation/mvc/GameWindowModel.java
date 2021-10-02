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

package me.mneri.gol.presentation.mvc;

import com.google.inject.Provider;
import lombok.Getter;
import lombok.Setter;
import me.mneri.gol.business.service.GameService;
import me.mneri.gol.data.model.World;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Model class for the game window.
 *
 * @author Massimo Neri
 */
public class GameWindowModel {
    /**
     * The listener interface for receiving world update events. The class that is interested in processing an update
     * event implements this interface. When an update happens the method {@code onWorldUpdate()} is called.
     */
    public interface WorldUpdateListener {
        void onWorldUpdate();
    }

    /**
     * The game service, in charge of the business logic.
     */
    private final GameService gameService;

    /**
     * The x-coordinate of the last mouse drag motion.
     */
    @Getter
    @Setter
    private int lastDragX;

    /**
     * The y-coordinate of the last mouse drag motion.
     */
    @Getter
    @Setter
    private int lastDragY;

    /**
     * The sleep period in milliseconds between game updates.
     */
    @Getter
    @Setter
    private int periodMillis;

    /**
     * {@code true} if the game is running, {@code false} otherwise.
     */
    @Getter
    @Setter
    private boolean running;

    /**
     * The game world.
     */
    @Getter
    @Setter
    private World world;

    /**
     * The listener to notify after each game update.
     */
    @Setter
    private WorldUpdateListener worldUpdateListener;

    /**
     * Create a new {@code GameWindowModel} instance.
     *
     * @param serviceProvider A provider of {@link GameService} instances.
     */
    @Inject
    protected GameWindowModel(final Provider<GameService> serviceProvider) {
        this.gameService = serviceProvider.get();
    }

    /**
     * Initialise the model.
     *
     * @param defaultWorldHeight The default world height.
     * @param defaultWorldWidth  The default world width.
     */
    @Inject
    @SuppressWarnings("unused") // Invoked after construction by the IoC framework.
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
