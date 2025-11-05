package com.pp.app.text.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SymbolParserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("It", 2),
                Arguments.of("centuries,", 10),
                Arguments.of("typesetting,", 12),
                Arguments.of("PageMaker", 9),
                Arguments.of("Ipsum.", 6)
        );
    }
}