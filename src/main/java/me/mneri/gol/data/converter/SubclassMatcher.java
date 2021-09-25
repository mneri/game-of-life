package me.mneri.gol.data.converter;

import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubclassMatcher extends AbstractMatcher<TypeLiteral<?>> {
    private final Class<?> type;

    @Override
    public boolean matches(TypeLiteral<?> toType) {
        return toType.getRawType().isAssignableFrom(type);
    }
}
