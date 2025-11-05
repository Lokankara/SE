package com.pp.app.text.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ParagraphParserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(
                        "    It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n" +
                                "    It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "    It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.\n" +
                                "    The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here', making it look like readable English.\n" +
                                "    It is a established fact that a reader will be of a page when looking at its layout.",
                        5
                ),
                Arguments.of(
                        "    It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n" +
                                "    It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages.",
                        2
                ),
                Arguments.of(
                        "    It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
                        1
                ),
                Arguments.of(
                        "    The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here', making it look like readable English.\n" +
                                "    It is a established fact that a reader will be of a page when looking at its layout.",
                        2
                ),
                Arguments.of(
                        "    It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
                        1
                )
        );
    }
}