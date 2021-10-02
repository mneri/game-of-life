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
