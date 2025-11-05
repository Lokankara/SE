package com.pp.app.text.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class LexemeParserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("It has survived not only five centuries,", 7),
                Arguments.of("but also the leap into electronic typesetting,", 8),
                Arguments.of("remaining essentially unchanged.", 3),
                Arguments.of(
                        "It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages,",
                        14),
                Arguments.of(
                        "and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                        15)
        );
    }
}