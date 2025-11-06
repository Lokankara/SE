package com.pp.app.text.provider;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.entity.Leaf;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class IntegrationTextArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        String text = Files.readString(Path.of("src/test/resources/test.txt")).replaceAll("\\s+", " ").trim();

        Composite paragraph = new Composite(ComponentType.PARAGRAPH);
        for (String sentenceStr : text.split("(?<=[.!?])\\s+")) {
            Composite sentence = new Composite(ComponentType.SENTENCE);
            for (String lex : sentenceStr.split("\\s+")) {
                Composite lexeme = new Composite(ComponentType.LEXEME);
                for (char c : lex.toCharArray()) {
                    lexeme.add(new Leaf(c));
                }
                sentence.add(lexeme);
            }
            paragraph.add(sentence);
        }

        Composite textComposite = new Composite(ComponentType.PARAGRAPH);
        textComposite.add(paragraph);

        List<String> sortedSentences = List.of(
                "It is a established fact that a reader will be of a page when looking at its layout.",
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
                "It was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
                "The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here', making it look like readable English."
        );

        List<String> swappedSentences = List.of(
                "layout. is a established fact that a reader will be of a page when looking at its It",
                "unchanged. has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially It",
                "Ipsum. was popularised in the with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem It",
                "layout. is a long established fact that a reader will be distracted by the readable content of a page when looking at its It",
                "English. The point of using Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using Content here, content here', making it look like readable English."
        );

        return Stream.of(
                Arguments.of(textComposite, 2, sortedSentences, swappedSentences)
        );
    }
}
