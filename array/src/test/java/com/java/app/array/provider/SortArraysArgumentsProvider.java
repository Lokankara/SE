package com.java.app.array.provider;

import com.java.app.array.comparator.StringArrayComparator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.stream.Stream;

public class SortArraysArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                new AbstractMap.SimpleEntry<>("A", new String[] {"apple", "banana"}),
                                new AbstractMap.SimpleEntry<>("B", new String[] {"kiwi"}),
                                new AbstractMap.SimpleEntry<>("C", new String[] {"pear", "peach", "plum"})
                        ),
                        StringArrayComparator.LENGTH_ASC,
                        Arrays.asList("B", "A", "C")
                ),
                Arguments.of(
                        Arrays.asList(
                                new AbstractMap.SimpleEntry<>("A", new String[] {"apple", "banana"}),
                                new AbstractMap.SimpleEntry<>("B", new String[] {"kiwi"}),
                                new AbstractMap.SimpleEntry<>("C", new String[] {"pear", "peach", "plum"})
                        ),
                        StringArrayComparator.LENGTH_DESC,
                        Arrays.asList("C", "A", "B")
                ),
                Arguments.of(
                        Arrays.asList(
                                new AbstractMap.SimpleEntry<>("A", new String[] {"banana", "apple"}),
                                new AbstractMap.SimpleEntry<>("B", new String[] {"apple"}),
                                new AbstractMap.SimpleEntry<>("C", new String[] {"pear", "peach"})
                        ),
                        StringArrayComparator.FIRST,
                        Arrays.asList("B", "A", "C")
                ),
                Arguments.of(
                        Arrays.asList(
                                new AbstractMap.SimpleEntry<>("A", new String[] {"b", "a"}),
                                new AbstractMap.SimpleEntry<>("B", new String[] {"a"}),
                                new AbstractMap.SimpleEntry<>("C", new String[] {"p", "p"})
                        ),
                        StringArrayComparator.CONCATENATED,
                        Arrays.asList("B", "A", "C")
                )
        );
    }
}