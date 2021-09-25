package me.mneri.gol.data.converter;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeConverter;

import java.awt.*;

public class ColorTypeConverter implements TypeConverter {
    @Override
    public Object convert(String value, TypeLiteral<?> toType) {
        if (!toType.getRawType().isAssignableFrom(Color.class)) {
            throw new IllegalArgumentException("Cannot convert type " + toType.getType().getTypeName());
        }

        if (value == null || value.isBlank()) {
            return null;
        }

        return Color.decode(value);
    }
}
