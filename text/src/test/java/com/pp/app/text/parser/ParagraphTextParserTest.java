package com.pp.app.text.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.provider.ParagraphParserArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class ParagraphTextParserTest {
    @ParameterizedTest(name = "ParagraphParser splits into {1} paragraphs")
    @ArgumentsSource(ParagraphParserArgumentsProvider.class)
    @DisplayName("ParagraphParser splits text into paragraphs")
    void testParagraphParser(String text, int expectedParagraphs) {
        SymbolParser symbolParser = new SymbolParser();
        LexemeParser lexemeParser = new LexemeParser();
        SentenceParser sentenceParser = new SentenceParser();
        ParagraphParser paragraphParser = new ParagraphParser();

        lexemeParser.setNextParser(symbolParser);
        sentenceParser.setNextParser(lexemeParser);
        paragraphParser.setNextParser(sentenceParser);

        Composite root = new Composite(ComponentType.LEXEME);
        paragraphParser.parse(text, root);

        assertEquals(expectedParagraphs, root.getChildren().size());
    }
}