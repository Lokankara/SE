package com.pp.app.text.provider;


import com.pp.app.text.entity.Leaf;
import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

public class SortSentencesProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Composite text1 = new Composite(ComponentType.LEXEME);
        Composite paragraph1 = new Composite(ComponentType.PARAGRAPH);

        Composite sentence1 = new Composite(ComponentType.SENTENCE);
        Composite lexeme1_1 = new Composite(ComponentType.LEXEME);
        lexeme1_1.add(new Leaf('A'));
        sentence1.add(lexeme1_1);

        Composite sentence2 = new Composite(ComponentType.SENTENCE);
        Composite lexeme2_1 = new Composite(ComponentType.LEXEME);
        lexeme2_1.add(new Leaf('B'));
        Composite lexeme2_2 = new Composite(ComponentType.LEXEME);
        lexeme2_2.add(new Leaf('C'));
        sentence2.add(lexeme2_1);
        sentence2.add(lexeme2_2);

        Composite sentence3 = new Composite(ComponentType.SENTENCE);
        Composite lexeme3_1 = new Composite(ComponentType.LEXEME);
        lexeme3_1.add(new Leaf('D'));
        Composite lexeme3_2 = new Composite(ComponentType.LEXEME);
        lexeme3_2.add(new Leaf('E'));
        Composite lexeme3_3 = new Composite(ComponentType.LEXEME);
        lexeme3_3.add(new Leaf('F'));
        sentence3.add(lexeme3_1);
        sentence3.add(lexeme3_2);
        sentence3.add(lexeme3_3);

        paragraph1.add(sentence3);
        paragraph1.add(sentence1);
        paragraph1.add(sentence2);
        text1.add(paragraph1);

        List<String> expected1 = List.of("A", "B C", "D E F");

        Composite text2 = new Composite(ComponentType.LEXEME);
        Composite paragraph2 = new Composite(ComponentType.PARAGRAPH);

        Composite sentence4 = new Composite(ComponentType.SENTENCE);
        Composite lexeme4_1 = new Composite(ComponentType.LEXEME);
        lexeme4_1.add(new Leaf('X'));
        Composite lexeme4_2 = new Composite(ComponentType.LEXEME);
        lexeme4_2.add(new Leaf('Y'));
        sentence4.add(lexeme4_1);
        sentence4.add(lexeme4_2);

        Composite sentence5 = new Composite(ComponentType.SENTENCE);
        Composite lexeme5_1 = new Composite(ComponentType.LEXEME);
        lexeme5_1.add(new Leaf('Z'));
        sentence5.add(lexeme5_1);

        paragraph2.add(sentence4);
        paragraph2.add(sentence5);
        text2.add(paragraph2);

        List<String> expected2 = List.of("Z", "X Y");

        Composite text3 = new Composite(ComponentType.LEXEME);
        Composite paragraph3 = new Composite(ComponentType.PARAGRAPH);

        Composite sentence6 = new Composite(ComponentType.SENTENCE);
        Composite lexeme6_1 = new Composite(ComponentType.LEXEME);
        lexeme6_1.add(new Leaf('M'));
        Composite lexeme6_2 = new Composite(ComponentType.LEXEME);
        lexeme6_2.add(new Leaf('N'));
        Composite lexeme6_3 = new Composite(ComponentType.LEXEME);
        lexeme6_3.add(new Leaf('O'));
        Composite lexeme6_4 = new Composite(ComponentType.LEXEME);
        lexeme6_4.add(new Leaf('P'));
        sentence6.add(lexeme6_1);
        sentence6.add(lexeme6_2);
        sentence6.add(lexeme6_3);
        sentence6.add(lexeme6_4);

        Composite sentence7 = new Composite(ComponentType.SENTENCE);
        Composite lexeme7_1 = new Composite(ComponentType.LEXEME);
        lexeme7_1.add(new Leaf('Q'));
        sentence7.add(lexeme7_1);

        paragraph3.add(sentence6);
        paragraph3.add(sentence7);
        text3.add(paragraph3);

        List<String> expected3 = List.of("Q", "M N O P");

        Composite text4 = new Composite(ComponentType.LEXEME);
        Composite paragraph4 = new Composite(ComponentType.PARAGRAPH);

        Composite sentence8 = new Composite(ComponentType.SENTENCE);
        Composite lexeme8_1 = new Composite(ComponentType.LEXEME);
        lexeme8_1.add(new Leaf('A'));
        Composite lexeme8_2 = new Composite(ComponentType.LEXEME);
        lexeme8_2.add(new Leaf('B'));
        sentence8.add(lexeme8_1);
        sentence8.add(lexeme8_2);

        Composite sentence9 = new Composite(ComponentType.SENTENCE);
        Composite lexeme9_1 = new Composite(ComponentType.LEXEME);
        lexeme9_1.add(new Leaf('C'));
        Composite lexeme9_2 = new Composite(ComponentType.LEXEME);
        lexeme9_2.add(new Leaf('D'));
        Composite lexeme9_3 = new Composite(ComponentType.LEXEME);
        lexeme9_3.add(new Leaf('E'));
        sentence9.add(lexeme9_1);
        sentence9.add(lexeme9_2);
        sentence9.add(lexeme9_3);

        paragraph4.add(sentence9);
        paragraph4.add(sentence8);
        text4.add(paragraph4);

        List<String> expected4 = List.of("A B", "C D E");

        Composite text5 = new Composite(ComponentType.LEXEME);
        Composite paragraph5 = new Composite(ComponentType.PARAGRAPH);

        Composite sentence10 = new Composite(ComponentType.SENTENCE);
        Composite lexeme10_1 = new Composite(ComponentType.LEXEME);
        lexeme10_1.add(new Leaf('W'));
        sentence10.add(lexeme10_1);

        Composite sentence11 = new Composite(ComponentType.SENTENCE);
        Composite lexeme11_1 = new Composite(ComponentType.LEXEME);
        lexeme11_1.add(new Leaf('X'));
        Composite lexeme11_2 = new Composite(ComponentType.LEXEME);
        lexeme11_2.add(new Leaf('Y'));
        Composite lexeme11_3 = new Composite(ComponentType.LEXEME);
        lexeme11_3.add(new Leaf('Z'));
        sentence11.add(lexeme11_1);
        sentence11.add(lexeme11_2);
        sentence11.add(lexeme11_3);

        paragraph5.add(sentence10);
        paragraph5.add(sentence11);
        text5.add(paragraph5);

        List<String> expected5 = List.of("W", "X Y Z");

        return Stream.of(
                Arguments.of(text1, expected1),
                Arguments.of(text2, expected2),
                Arguments.of(text3, expected3),
                Arguments.of(text4, expected4),
                Arguments.of(text5, expected5)
        );
    }
}