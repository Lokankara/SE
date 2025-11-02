package com.java.app.array.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SortTestDataProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("BasicUnsorted", new Integer[] {5, 2, 8, 1, 9}, new Integer[] {1, 2, 5, 8, 9}),
                Arguments.of("ReverseSorted", new Integer[] {9, 7, 5, 3, 1}, new Integer[] {1, 3, 5, 7, 9}),
                Arguments.of("AlreadySorted", new Integer[] {1, 2, 3, 4, 5}, new Integer[] {1, 2, 3, 4, 5}),
                Arguments.of("WithDuplicates", new Integer[] {3, 1, 4, 1, 5, 9, 2, 6, 5},
                        new Integer[] {1, 1, 2, 3, 4, 5, 5, 6, 9}),
                Arguments.of("AllSameElements", new Integer[] {7, 7, 7, 7, 7}, new Integer[] {7, 7, 7, 7, 7}),
                Arguments.of("WithNegatives", new Integer[] {-5, 3, -1, 0, 2, -3}, new Integer[] {-5, -3, -1, 0, 2, 3}),
                Arguments.of("TwoElements", new Integer[] {2, 1}, new Integer[] {1, 2}),
                Arguments.of("SingleElement", new Integer[] {42}, new Integer[] {42}),
                Arguments.of("EmptyArray", new Integer[] {}, new Integer[] {}),
                Arguments.of("ExtremeValues", new Integer[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0},
                        new Integer[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
        );
    }
}