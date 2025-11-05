package com.pp.app.array.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ArrayArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(new Integer[] {5, 2, 8, 1, 9}, new Integer[] {1, 2, 5, 8, 9}),
                Arguments.of(new Integer[] {9, 7, 5, 3, 1}, new Integer[] {1, 3, 5, 7, 9}),
                Arguments.of(new Integer[] {1, 2, 3, 4, 5}, new Integer[] {1, 2, 3, 4, 5}),
                Arguments.of(new Integer[] {3, 1, 4, 1, 5}, new Integer[] {1, 1, 3, 4, 5}),
                Arguments.of(new Integer[] {7, 7, 7, 7}, new Integer[] {7, 7, 7, 7}),
                Arguments.of(new Integer[] {-5, 3, -1, 0, 2}, new Integer[] {-5, -1, 0, 2, 3}),
                Arguments.of(new Integer[] {42}, new Integer[] {42}),
                Arguments.of(new Integer[] {2, 1}, new Integer[] {1, 2}),
                Arguments.of(new Integer[] {}, new Integer[] {}),
                Arguments.of(new Integer[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0},
                        new Integer[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
        );
    }
}
