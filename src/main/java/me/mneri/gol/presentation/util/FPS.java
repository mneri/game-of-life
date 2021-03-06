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
    /**
     * Slowest game execution speed.
     */
    SLOWEST("Slowest", 1000 / 2),

    /**
     * Slower game execution speed.
     */
    SLOWER("Slower", 1000 / 15),

    /**
     * Slow game execution speed.
     */
    SLOW("Slow", 1000 / 30),

    /**
     * Normal game execution speed.
     */
    NORMAL("Normal", 1000 / 60),

    /**
     * Fast game execution speed.
     */
    FAST("Fast", 1000 / 120),

    /**
     * Faster game execution speed.
     */
    FASTER("Faster", 1000 / 240),

    /**
     * Fastest game execution speed.
     */
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
