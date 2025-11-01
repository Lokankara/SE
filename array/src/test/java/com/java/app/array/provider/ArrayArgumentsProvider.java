package com.java.app.array.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ArrayArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new int[] {5, 2, 8, 1, 9}, new int[] {1, 2, 5, 8, 9}),
                Arguments.of(new int[] {9, 7, 5, 3, 1}, new int[] {1, 3, 5, 7, 9}),
                Arguments.of(new int[] {1, 2, 3, 4, 5}, new int[] {1, 2, 3, 4, 5}),
                Arguments.of(new int[] {3, 1, 4, 1, 5}, new int[] {1, 1, 3, 4, 5}),
                Arguments.of(new int[] {7, 7, 7, 7}, new int[] {7, 7, 7, 7}),
                Arguments.of(new int[] {-5, 3, -1, 0, 2}, new int[] {-5, -1, 0, 2, 3}),
                Arguments.of(new int[] {42}, new int[] {42}),
                Arguments.of(new int[] {2, 1}, new int[] {1, 2}),
                Arguments.of(new int[] {}, new int[] {}),
                Arguments.of(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0},
                        new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
        );
    }
}
