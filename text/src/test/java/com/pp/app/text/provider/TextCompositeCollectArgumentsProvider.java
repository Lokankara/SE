package com.pp.app.text.provider;

import com.pp.app.text.entity.Leaf;
import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class TextCompositeCollectArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Composite text = new Composite(ComponentType.LEXEME);
        Composite paragraph = new Composite(ComponentType.PARAGRAPH);
        Composite sentence = new Composite(ComponentType.SENTENCE);
        Composite lexeme1 = new Composite(ComponentType.LEXEME);
        Composite lexeme2 = new Composite(ComponentType.LEXEME);
        lexeme1.add(new Leaf('I'));
        lexeme1.add(new Leaf('t'));
        lexeme2.add(new Leaf('h'));
        lexeme2.add(new Leaf('a'));
        lexeme2.add(new Leaf('s'));
        sentence.add(lexeme1);
        sentence.add(lexeme2);
        paragraph.add(sentence);
        text.add(paragraph);

        Composite paragraph2 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence2 = new Composite(ComponentType.SENTENCE);
        Composite lexeme3 = new Composite(ComponentType.LEXEME);
        lexeme3.add(new Leaf('s'));
        lexeme3.add(new Leaf('u'));
        lexeme3.add(new Leaf('r'));
        lexeme3.add(new Leaf('v'));
        lexeme3.add(new Leaf('i'));
        lexeme3.add(new Leaf('v'));
        lexeme3.add(new Leaf('e'));
        lexeme3.add(new Leaf('d'));
        sentence2.add(lexeme3);
        paragraph2.add(sentence2);

        Composite sentence3 = new Composite(ComponentType.SENTENCE);
        Composite lexeme4 = new Composite(ComponentType.LEXEME);
        lexeme4.add(new Leaf('n'));
        lexeme4.add(new Leaf('o'));
        lexeme4.add(new Leaf('t'));
        sentence3.add(lexeme4);

        Composite lexeme5 = new Composite(ComponentType.LEXEME);
        lexeme5.add(new Leaf('o'));
        lexeme5.add(new Leaf('n'));
        lexeme5.add(new Leaf('l'));
        lexeme5.add(new Leaf('y'));

        Leaf symbol = new Leaf('f');

        return Stream.of(
                Arguments.of(text, "It has"),
                Arguments.of(paragraph2, "survived"),
                Arguments.of(sentence3, "not"),
                Arguments.of(lexeme5, "only"),
                Arguments.of(symbol, "f")
        );
    }
}
