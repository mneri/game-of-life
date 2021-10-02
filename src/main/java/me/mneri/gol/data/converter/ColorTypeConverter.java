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
import com.google.inject.spi.TypeConverter;

import java.awt.Color;

/**
 * Convert Java strings into {@link Color}.
 *
 * @author Massimo Neri
 */
public class ColorTypeConverter implements TypeConverter {
    /**
     * Convert the provided string value to the type specified by the provided type literal.
     *
     * @param value  The string value to convert.
     * @param toType A type literal defining the target type.
     * @return The converted value.
     */
    @Override
    public Object convert(final String value, final TypeLiteral<?> toType) {
        if (!toType.getRawType().isAssignableFrom(Color.class)) {
            throw new IllegalArgumentException("Cannot convert type " + toType.getType().getTypeName());
        }

        if (value == null || value.isBlank()) {
            return null;
        }

        return Color.decode(value);
    }
}
