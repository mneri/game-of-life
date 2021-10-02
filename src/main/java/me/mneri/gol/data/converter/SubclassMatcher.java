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

package me.mneri.gol.data.converter;

import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import lombok.RequiredArgsConstructor;

/**
 * Match a type with the same type or any descendant.
 *
 * @author Massimo Neri
 */
@RequiredArgsConstructor
public class SubclassMatcher extends AbstractMatcher<TypeLiteral<?>> {
    /**
     * The matched type.
     */
    private final Class<?> type;

    /**
     * Return {@code true} it the type passed as parameter is the same or a subclass of the matched type, {@code false}
     * otherwise.
     *
     * @param toType The type to match.
     * @return {@code true} it the type passed as parameter is the same or a subclass of the matched type, {@code false}
     * otherwise.
     */
    @Override
    public boolean matches(final TypeLiteral<?> toType) {
        return toType.getRawType().isAssignableFrom(type);
    }
}
