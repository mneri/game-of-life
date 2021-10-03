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
     * @param currentWorld The current state of the world.
     * @param futureWorld The future state of the world, this is an outbound variable.
     */
    void evolve(World currentWorld, World futureWorld);
}
