package com.pp.app.text.provider;

import com.pp.app.text.entity.Leaf;
import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class GetAllSentencesProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Composite text1 = new Composite(ComponentType.LEXEME);
        Composite paragraph1 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence1 = new Composite(ComponentType.SENTENCE);
        Composite sentence2 = new Composite(ComponentType.SENTENCE);
        sentence1.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('A'));
        }});
        sentence2.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('B'));
        }});
        paragraph1.add(sentence1);
        paragraph1.add(sentence2);
        text1.add(paragraph1);

        Composite text2 = new Composite(ComponentType.LEXEME);
        Composite paragraph2 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence3 = new Composite(ComponentType.SENTENCE);
        sentence3.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('C'));
        }});
        paragraph2.add(sentence3);
        text2.add(paragraph2);

        Composite text3 = new Composite(ComponentType.LEXEME);
        Composite paragraph3 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence4 = new Composite(ComponentType.SENTENCE);
        Composite sentence5 = new Composite(ComponentType.SENTENCE);
        Composite sentence6 = new Composite(ComponentType.SENTENCE);
        sentence4.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('D'));
        }});
        sentence5.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('E'));
        }});
        sentence6.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('F'));
        }});
        paragraph3.add(sentence4);
        paragraph3.add(sentence5);
        paragraph3.add(sentence6);
        text3.add(paragraph3);

        Composite text4 = new Composite(ComponentType.LEXEME);
        Composite paragraph4 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence7 = new Composite(ComponentType.SENTENCE);
        sentence7.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('G'));
        }});
        paragraph4.add(sentence7);
        text4.add(paragraph4);

        Composite text5 = new Composite(ComponentType.LEXEME);
        Composite paragraph5 = new Composite(ComponentType.PARAGRAPH);
        Composite sentence8 = new Composite(ComponentType.SENTENCE);
        Composite sentence9 = new Composite(ComponentType.SENTENCE);
        sentence8.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('H'));
        }});
        sentence9.add(new Composite(ComponentType.LEXEME) {{
            add(new Leaf('I'));
        }});
        paragraph5.add(sentence8);
        paragraph5.add(sentence9);
        text5.add(paragraph5);

        return Stream.of(
                Arguments.of(text1, 2),
                Arguments.of(text2, 1),
                Arguments.of(text3, 3),
                Arguments.of(text4, 1),
                Arguments.of(text5, 2)
        );
    }
}