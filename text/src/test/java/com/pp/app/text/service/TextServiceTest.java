package com.pp.app.text.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pp.app.text.entity.TextComponent;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.provider.GetAllSentencesProvider;
import com.pp.app.text.provider.MaxEqualSentencesProvider;
import com.pp.app.text.provider.SortSentencesProvider;
import com.pp.app.text.provider.SwapLexemesProvider;
import com.pp.app.text.provider.TextServiceArgumentsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Collectors;

class TextServiceTest {

    private TextService service;

    @BeforeEach
    void setUp() {
        service = new TextService();
    }

    @ParameterizedTest(name = "maxEqualSentencesCount should be {1}")
    @ArgumentsSource(TextServiceArgumentsProvider.class)
    @DisplayName("TextService.maxEqualSentencesCount returns correct value")
    void testMaxEqualSentencesCount(Composite text, int expected, List<String> unused1, List<String> unused2) {
        TextService service = new TextService();
        assertEquals(expected, service.maxEqualSentencesCount(text));
    }

    @ParameterizedTest(name = "sortSentencesByLexemeCount should be {2}")
    @ArgumentsSource(TextServiceArgumentsProvider.class)
    @DisplayName("TextService.sortSentencesByLexemeCount sorts correctly")
    void testSortSentencesByLexemeCount(Composite text, int unused, List<String> expected, List<String> unused2) {
        List<String> actual = service.sortSentencesByLexemeCount(text).stream().map(c -> c.collect()).toList();
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "swapFirstLastLexeme should be {3}")
    @ArgumentsSource(TextServiceArgumentsProvider.class)
    @DisplayName("TextService.swapFirstLastLexeme swaps lexemes")
    void testSwapFirstLastLexeme(Composite text, int unused, List<String> unused2, List<String> expected) {
        service.swapFirstLastLexeme(text);
        List<String> actual = service.getAllSentences(text).stream().map(TextComponent::collect).toList();
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "Max equal sentences: {1}")
    @ArgumentsSource(MaxEqualSentencesProvider.class)
    @DisplayName("Calculate maximum equal sentences count")
    void maxEqualSentencesCount_ShouldReturnCorrectCount(TextComponent text, int expectedCount) {
        int actualCount = service.maxEqualSentencesCount(text);
        assertEquals(expectedCount, actualCount);
    }

    @ParameterizedTest(name = "Sort sentences by lexeme count")
    @ArgumentsSource(SortSentencesProvider.class)
    @DisplayName("Sort sentences by lexeme count in ascending order")
    void sortSentencesByLexemeCount_ShouldSortInAscendingOrder(TextComponent text, List<String> expectedOrder) {
        List<TextComponent> sortedSentences = service.sortSentencesByLexemeCount(text);
        List<String> actualOrder = sortedSentences.stream()
                .map(TextComponent::collect)
                .collect(Collectors.toList());
        assertEquals(expectedOrder, actualOrder);
    }

    @ParameterizedTest(name = "Swap first and last lexeme")
    @ArgumentsSource(SwapLexemesProvider.class)
    @DisplayName("Swap first and last lexeme in sentences")
    void swapFirstLastLexeme_ShouldSwapLexemesCorrectly(TextComponent text, String expectedResult) {
        service.swapFirstLastLexeme(text);
        assertEquals(expectedResult, text.collect());
    }

    @ParameterizedTest(name = "Get all sentences: {1} sentences expected")
    @ArgumentsSource(GetAllSentencesProvider.class)
    @DisplayName("Get all sentences from text")
    void getAllSentences_ShouldReturnAllSentences(TextComponent text, int expectedSentenceCount) {
        List<TextComponent> sentences = service.getAllSentences(text);
        assertEquals(expectedSentenceCount, sentences.size());
    }
}