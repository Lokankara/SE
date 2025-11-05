package com.pp.app.text.provider;


import com.pp.app.text.entity.Leaf;
import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class SwapLexemesProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Composite text1 = new Composite(ComponentType.LEXEME);
        Composite paragraph1 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence1 = new Composite(ComponentType.SENTENCE);
        Composite lexeme1 = new Composite(ComponentType.LEXEME);
        lexeme1.add(new Leaf('I'));
        lexeme1.add(new Leaf('t'));
        Composite lexeme2 = new Composite(ComponentType.LEXEME);
        lexeme2.add(new Leaf('h'));
        lexeme2.add(new Leaf('a'));
        lexeme2.add(new Leaf('s'));
        sentence1.add(lexeme1);
        sentence1.add(lexeme2);
        paragraph1.add(sentence1);
        text1.add(paragraph1);

        Composite text2 = new Composite(ComponentType.LEXEME);
        Composite paragraph2 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence2 = new Composite(ComponentType.SENTENCE);
        Composite lexeme3 = new Composite(ComponentType.LEXEME);
        lexeme3.add(new Leaf('O'));
        lexeme3.add(new Leaf('n'));
        lexeme3.add(new Leaf('l'));
        lexeme3.add(new Leaf('y'));
        Composite lexeme4 = new Composite(ComponentType.LEXEME);
        lexeme4.add(new Leaf('f'));
        lexeme4.add(new Leaf('i'));
        lexeme4.add(new Leaf('v'));
        lexeme4.add(new Leaf('e'));
        sentence2.add(lexeme3);
        sentence2.add(lexeme4);
        paragraph2.add(sentence2);
        text2.add(paragraph2);

        Composite text3 = new Composite(ComponentType.LEXEME);
        Composite paragraph3 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence3 = new Composite(ComponentType.SENTENCE);
        Composite lexeme5 = new Composite(ComponentType.LEXEME);
        lexeme5.add(new Leaf('a'));
        lexeme5.add(new Leaf('b'));
        lexeme5.add(new Leaf('c'));
        Composite lexeme6 = new Composite(ComponentType.LEXEME);
        lexeme6.add(new Leaf('d'));
        lexeme6.add(new Leaf('e'));
        lexeme6.add(new Leaf('f'));
        Composite lexeme7 = new Composite(ComponentType.LEXEME);
        lexeme7.add(new Leaf('g'));
        lexeme7.add(new Leaf('h'));
        lexeme7.add(new Leaf('i'));
        sentence3.add(lexeme5);
        sentence3.add(lexeme6);
        sentence3.add(lexeme7);
        paragraph3.add(sentence3);
        text3.add(paragraph3);

        Composite text4 = new Composite(ComponentType.LEXEME);
        Composite paragraph4 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence4 = new Composite(ComponentType.SENTENCE);
        Composite lexeme8 = new Composite(ComponentType.LEXEME);
        lexeme8.add(new Leaf('A'));
        Composite lexeme9 = new Composite(ComponentType.LEXEME);
        lexeme9.add(new Leaf('B'));
        sentence4.add(lexeme8);
        sentence4.add(lexeme9);
        paragraph4.add(sentence4);
        text4.add(paragraph4);

        Composite text5 = new Composite(ComponentType.LEXEME);
        Composite paragraph5 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence5 = new Composite(ComponentType.SENTENCE);
        Composite lexeme10 = new Composite(ComponentType.LEXEME);
        lexeme10.add(new Leaf('X'));
        Composite lexeme11 = new Composite(ComponentType.LEXEME);
        lexeme11.add(new Leaf('Y'));
        Composite lexeme12 = new Composite(ComponentType.LEXEME);
        lexeme12.add(new Leaf('Z'));
        sentence5.add(lexeme10);
        sentence5.add(lexeme11);
        sentence5.add(lexeme12);
        paragraph5.add(sentence5);
        text5.add(paragraph5);

        return Stream.of(
                Arguments.of(text1, "has It"),
                Arguments.of(text2, "five Only"),
                Arguments.of(text3, "g h i d e f a b c"),
                Arguments.of(text4, "B A"),
                Arguments.of(text5, "Z Y X")
        );
    }
}