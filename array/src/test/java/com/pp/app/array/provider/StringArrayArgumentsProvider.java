package com.pp.app.array.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class StringArrayArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("TestArray1", new String[]{"A", "B", "C"}),
                Arguments.of("TestArray2", new String[]{"X", "Y", "Z"}),
                Arguments.of("EmptyArray", new String[]{}),
                Arguments.of("SingleElementArray", new String[]{"Single"}),
                Arguments.of("DuplicateElementsArray", new String[]{"A", "A", "B", "B", "C"}),
                Arguments.of("MixedCaseArray", new String[]{"a", "B", "c", "A", "b", "C"})
        );
    }
}