package com.java.app.array.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ArrayServiceArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("TestArray1", new int[] {1, 2, 3, 4, 5}),
                Arguments.of("TestArray2", new int[] {10, 20, 30}),
                Arguments.of("NegativeArray", new int[] {-5, -2, -8}),
                Arguments.of("MixedArray", new int[] {-10, 0, 10}),
                Arguments.of("SingleElement", new int[] {99}),
                Arguments.of("EmptyArray", new int[] {}),
                Arguments.of("TestArray1", new int[] {1, 2, 3, 4, 5}, 15, 3.0, 5, 1),
                Arguments.of("TestArray2", new int[] {10, 20, 30}, 60, 20.0, 30, 10),
                Arguments.of("NegativeArray", new int[] {-5, -2, -8}, -15, -5.0, -2, -8),
                Arguments.of("MixedArray", new int[] {-10, 0, 10}, 0, 0.0, 10, -10),
                Arguments.of("SingleElement", new int[] {99}, 99, 99.0, 99, 99),
                Arguments.of("AllZeros", new int[] {0, 0, 0, 0}, 0, 0.0, 0, 0)
        );
    }
}
