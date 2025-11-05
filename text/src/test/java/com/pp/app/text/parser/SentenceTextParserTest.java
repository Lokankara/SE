package com.pp.app.text.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.provider.SentenceParserArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class SentenceTextParserTest {
    @ParameterizedTest(name = "SentenceParser splits into {1} sentences")
    @ArgumentsSource(SentenceParserArgumentsProvider.class)
    @DisplayName("SentenceParser splits paragraph into sentences")
    void testSentenceParser(String paragraph, int expectedSentences) {
        SymbolParser symbolParser = new SymbolParser();
        LexemeParser lexemeParser = new LexemeParser();
        SentenceParser sentenceParser = new SentenceParser();

        lexemeParser.setNextParser(symbolParser);
        sentenceParser.setNextParser(lexemeParser);

        Composite paragraphComposite = new Composite(ComponentType.PARAGRAPH);
        sentenceParser.parse(paragraph, paragraphComposite);

        assertEquals(expectedSentences, paragraphComposite.getChildren().size());
    }
}