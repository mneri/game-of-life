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

package me.mneri.gol.business.service;

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
    /**
     * The maximum number of world's columns a single thread can process.
     */
    @Inject
    @Named("me.mneri.gol.task-threshold")
    @SuppressWarnings("unused") // Assigned by the IoC framework.
    private int defaultTaskThreshold;

    /**
     * The pool of thread processing.
     */
    private final ForkJoinPool pool = ForkJoinPool.commonPool();

    /**
     * {@inheritDoc}
     *
     * @param currentWorld The current state of the world.
     * @param futureWorld  The future state of the world.
     */
    @Override
    public void evolve(final World currentWorld, final World futureWorld) {
        pool.invoke(new EvolveTask(currentWorld, futureWorld, defaultTaskThreshold));
    }
}
