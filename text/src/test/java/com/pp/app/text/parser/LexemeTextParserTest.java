package com.pp.app.text.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.provider.LexemeParserArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class LexemeTextParserTest {
    @ParameterizedTest(name = "LexemeParser splits into {1} lexemes")
    @ArgumentsSource(LexemeParserArgumentsProvider.class)
    @DisplayName("LexemeParser splits sentence into lexemes")
    void testLexemeParser(String sentence, int expectedLexemes) {
        SymbolParser symbolParser = new SymbolParser();
        LexemeParser lexemeParser = new LexemeParser();

        lexemeParser.setNextParser(symbolParser);

        Composite sentenceComposite = new Composite(ComponentType.SENTENCE);
        lexemeParser.parse(sentence, sentenceComposite);

        assertEquals(expectedLexemes, sentenceComposite.getChildren().size());
    }
}