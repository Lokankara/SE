package com.pp.app.text.parser;

import static org.junit.jupiter.api.Assertions.*;

import com.pp.app.text.entity.Composite;
import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.provider.SymbolParserArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class SymbolTextParserTest {
    @ParameterizedTest(name = "SymbolParser splits into {1} symbols")
    @ArgumentsSource(SymbolParserArgumentsProvider.class)
    @DisplayName("SymbolParser splits lexeme into symbols")
    void testSymbolParser(String lexeme, int expectedSymbols) {
        SymbolParser symbolParser = new SymbolParser();
        Composite lexemeComposite = new Composite(ComponentType.LEXEME);
        symbolParser.parse(lexeme, lexemeComposite);

        assertEquals(expectedSymbols, lexemeComposite.getChildren().size());
    }
}