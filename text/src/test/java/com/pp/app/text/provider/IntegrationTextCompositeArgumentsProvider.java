package com.pp.app.text.provider;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.entity.Leaf;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class IntegrationTextCompositeArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
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

        return Stream.of(
                Arguments.of(paragraph, "It has")
        );
    }
}
