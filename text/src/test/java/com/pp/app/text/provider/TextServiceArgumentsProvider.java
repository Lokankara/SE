package com.pp.app.text.provider;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.entity.Leaf;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class TextServiceArgumentsProvider implements ArgumentsProvider {
    static final String SAMPLE_TEXT =
            "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
                    "It was popularised in the with the release of Let raset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. " +
                    "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. " +
                    "The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here', making it look like readable English. " +
                    "It is a established fact that a reader will be of a page when looking at its layout.";

    static Composite buildText() {
        Composite text = new Composite(ComponentType.LEXEME);
        Composite paragraph = new Composite(ComponentType.PARAGRAPH);

        String[] sentences = SAMPLE_TEXT.split("(?<=[.!?])\\s+");
        for (String s : sentences) {
            Composite sentence = new Composite(ComponentType.SENTENCE);
            for (String lex : s.split("\\s+")) {
                Composite lexeme = new Composite(ComponentType.LEXEME);
                for (char c : lex.toCharArray()) {
                    lexeme.add(new Leaf(c));
                }
                sentence.add(lexeme);
            }
            paragraph.add(sentence);
        }
        text.add(paragraph);
        return text;
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

        List<String> sentences = List.of(
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
                "It was popularised in the with the release of Let raset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
                "The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here', making it look like readable English.",
                "It is a established fact that a reader will be of a page when looking at its layout."
        );

        List<String> expectedSorted = sentences.stream()
                .sorted(Comparator.comparingInt(s -> s.split("\\s+").length))
                .toList();

        List<String> swappedSentences = List.of(
                "layout. is a established fact that a reader will be of a page when looking at its It",
                "unchanged. has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially It",
                "Ipsum. was popularised in the with the release of Let raset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem It",
                "layout. is a long established fact that a reader will be distracted by the readable content of a page when looking at its It",
                "English. The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here', making it look like readable English."
        );

        return Stream.of(
                Arguments.of(buildText(), 2, expectedSorted, swappedSentences)
        );
    }
}