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
