package com.java.app.array.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SortTestDataProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("BasicUnsorted", new int[] {5, 2, 8, 1, 9}, new int[] {1, 2, 5, 8, 9}),
                Arguments.of("ReverseSorted", new int[] {9, 7, 5, 3, 1}, new int[] {1, 3, 5, 7, 9}),
                Arguments.of("AlreadySorted", new int[] {1, 2, 3, 4, 5}, new int[] {1, 2, 3, 4, 5}),
                Arguments.of("WithDuplicates", new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5},
                        new int[] {1, 1, 2, 3, 4, 5, 5, 6, 9}),
                Arguments.of("AllSameElements", new int[] {7, 7, 7, 7, 7}, new int[] {7, 7, 7, 7, 7}),
                Arguments.of("WithNegatives", new int[] {-5, 3, -1, 0, 2, -3}, new int[] {-5, -3, -1, 0, 2, 3}),
                Arguments.of("TwoElements", new int[] {2, 1}, new int[] {1, 2}),
                Arguments.of("SingleElement", new int[] {42}, new int[] {42}),
                Arguments.of("EmptyArray", new int[] {}, new int[] {}),
                Arguments.of("ExtremeValues", new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0},
                        new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
        );
    }
}