package com.pp.app.text.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.TextComponent;
import org.junit.jupiter.api.Test;

public class IntegrationTextServiceTest {

    private TextService service;
    private TextComponent text;

    @Test
    void findMaxSentencesWithSameWords() {
        int result = service.maxEqualSentencesCount(text);
        assertTrue(result >= 1);
    }

    @Test
    void sortSentencesByLexemeCount() {
        var sorted = service.sortSentencesByLexemeCount(text);
        assertEquals(sorted.size(), getSentenceCount(text));
        for (int i = 1; i < sorted.size(); i++) {
            assertTrue(sorted.get(i).getChildren().size() >=
                    sorted.get(i - 1).getChildren().size());
        }
    }

    @Test
    void swapFirstAndLastLexemeInSentences() {
        service.swapFirstLastLexeme(this.text);

        var text = ComponentType.LEXEME;
        assertEquals(text, this.text.getType());
    }

    private int getSentenceCount(TextComponent text) {
        int count = 0;
        for (TextComponent p : text.getChildren()) {
            count += p.getChildren().size();
        }
        return count;
    }
}
