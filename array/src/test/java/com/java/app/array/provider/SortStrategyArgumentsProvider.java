package com.java.app.array.provider;

import com.java.app.array.strategy.SortStrategy;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SortStrategyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(SortStrategy.MERGE),
                Arguments.of(SortStrategy.HEAP),
                Arguments.of(SortStrategy.INSERTION),
                Arguments.of(SortStrategy.SELECTION),
                Arguments.of(SortStrategy.BUBBLE)
        );
    }
}
