package me.mneri.gol.presentation.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Model the speed of the game in frames per second.
 *
 * @author Massimo Neri
 */
@AllArgsConstructor
public enum FPS {
    SLOWEST("Slowest", 1000 / 2),
    SLOWER("Slower", 1000 / 15),
    SLOW("Slow", 1000 / 30),
    NORMAL("Normal", 1000 / 60),
    FAST("Fast", 1000 / 120),
    FASTER("Faster", 1000 / 240),
    FASTEST("Fastest", 1000 / 480);

    /**
     * The label presented to the user.
     */
    @Getter
    private String label;

    /**
     * The sleep period in milliseconds between one state of the game and the next.
     */
    @Getter
    private int period;

    @Override
    public String toString() {
        return label;
    }
}
