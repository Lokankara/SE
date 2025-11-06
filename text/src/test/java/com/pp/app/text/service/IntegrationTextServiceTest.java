package com.pp.app.text.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pp.app.text.entity.TextComponent;
import com.pp.app.text.provider.IntegrationTextArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;


public class IntegrationTextServiceTest {
    TextService service = new TextService();

    @ParameterizedTest(name = "Max equal sentences: {1}")
    @ArgumentsSource(IntegrationTextArgumentsProvider.class)
    @DisplayName("Integration: maxEqualSentencesCount")
    void maxEqualSentencesCount(TextComponent text, int expected, List<String> unused1, List<String> unused2) {
        assertEquals(expected, service.maxEqualSentencesCount(text));
    }

    @ParameterizedTest(name = "Sort sentences by lexeme count")
    @ArgumentsSource(IntegrationTextArgumentsProvider.class)
    @DisplayName("Integration: sortSentencesByLexemeCount")
    void sortSentencesByLexemeCount(TextComponent text, int unused, List<String> expected, List<String> unused2) {
        List<String> actual = service.sortSentencesByLexemeCount(text).stream().map(TextComponent::collect).toList();
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "Swap first and last lexeme")
    @ArgumentsSource(IntegrationTextArgumentsProvider.class)
    @DisplayName("Integration: swapFirstLastLexeme")
    void swapFirstLastLexeme(TextComponent text, int unused, List<String> unused2, List<String> expected) {
        service.swapFirstLastLexeme(text);
        List<String> actual = service.getAllSentences(text).stream().map(TextComponent::collect).toList();
        assertEquals(expected, actual);
    }
}
