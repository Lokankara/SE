package com.pp.app.text.provider;

import com.pp.app.text.entity.Leaf;
import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class MaxEqualSentencesProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

        Composite text1 = new Composite(ComponentType.LEXEME);
        Composite paragraph1 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence1a = new Composite(ComponentType.SENTENCE);
        Composite sentence1b = new Composite(ComponentType.SENTENCE);
        Composite sentence1c = new Composite(ComponentType.SENTENCE);

        sentence1a.add(createLexeme("It"));
        sentence1a.add(createLexeme("is"));
        sentence1a.add(createLexeme("a"));
        sentence1a.add(createLexeme("fact."));
        sentence1b.add(createLexeme("It"));
        sentence1b.add(createLexeme("is"));
        sentence1b.add(createLexeme("a"));
        sentence1b.add(createLexeme("fact."));
        sentence1c.add(createLexeme("Other."));

        paragraph1.add(sentence1a);
        paragraph1.add(sentence1b);
        paragraph1.add(sentence1c);
        text1.add(paragraph1);

        Composite text2 = new Composite(ComponentType.LEXEME);
        Composite paragraph2 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence2a = new Composite(ComponentType.SENTENCE);
        Composite sentence2b = new Composite(ComponentType.SENTENCE);
        sentence2a.add(createLexeme("First."));
        sentence2b.add(createLexeme("Second."));
        paragraph2.add(sentence2a);
        paragraph2.add(sentence2b);
        text2.add(paragraph2);

        Composite text3 = new Composite(ComponentType.LEXEME);
        Composite paragraph3 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence3a = new Composite(ComponentType.SENTENCE);
        Composite sentence3b = new Composite(ComponentType.SENTENCE);
        Composite sentence3c = new Composite(ComponentType.SENTENCE);
        sentence3a.add(createLexeme("Same."));
        sentence3b.add(createLexeme("Same."));
        sentence3c.add(createLexeme("Same."));
        paragraph3.add(sentence3a);
        paragraph3.add(sentence3b);
        paragraph3.add(sentence3c);
        text3.add(paragraph3);

        Composite text4 = new Composite(ComponentType.LEXEME);
        Composite paragraph4 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence4a = new Composite(ComponentType.SENTENCE);
        Composite sentence4b = new Composite(ComponentType.SENTENCE);
        Composite sentence4c = new Composite(ComponentType.SENTENCE);
        sentence4a.add(createLexeme("Repeat."));
        sentence4b.add(createLexeme("Repeat."));
        sentence4c.add(createLexeme("Unique."));
        paragraph4.add(sentence4a);
        paragraph4.add(sentence4b);
        paragraph4.add(sentence4c);
        text4.add(paragraph4);

        Composite text5 = new Composite(ComponentType.LEXEME);
        Composite paragraph5 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence5a = new Composite(ComponentType.SENTENCE);
        Composite sentence5b = new Composite(ComponentType.SENTENCE);
        sentence5a.add(createLexeme("Lorem."));
        sentence5b.add(createLexeme("Lorem."));
        paragraph5.add(sentence5a);
        paragraph5.add(sentence5b);
        text5.add(paragraph5);

        return Stream.of(
                Arguments.of(text1, 2),
                Arguments.of(text2, 1),
                Arguments.of(text3, 3),
                Arguments.of(text4, 2),
                Arguments.of(text5, 2)
        );
    }

    private static Composite createLexeme(String word) {
        Composite lexeme = new Composite(ComponentType.LEXEME);
        for (char c : word.toCharArray()) {
            lexeme.add(new Leaf(c));
        }
        return lexeme;
    }
}