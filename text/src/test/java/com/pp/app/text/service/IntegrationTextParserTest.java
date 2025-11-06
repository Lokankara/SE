package com.pp.app.text.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;
import com.pp.app.text.parser.LexemeParser;
import com.pp.app.text.parser.ParagraphParser;
import com.pp.app.text.parser.PunctuationParser;
import com.pp.app.text.parser.SentenceParser;
import com.pp.app.text.parser.SymbolParser;
import com.pp.app.text.parser.WordParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class IntegrationTextParserTest {

    @Test
    @DisplayName("Integration: Full parse and collect equals file content (normalized)")
    void testFullParseAndCollect() throws Exception {
        String fileName = "test.txt";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        String text = new String(Objects.requireNonNull(inputStream).readAllBytes(), StandardCharsets.UTF_8).replaceAll(
                "\\s+", " ").trim();
        System.out.println(text);
        PunctuationParser punctuationParser = new PunctuationParser();
        WordParser wordParser = new WordParser();
        LexemeParser lexemeParser = new LexemeParser();
        SentenceParser sentenceParser = new SentenceParser();
        ParagraphParser paragraphParser = new ParagraphParser();

        punctuationParser.setNextParser(new SymbolParser());
        wordParser.setNextParser(punctuationParser);
        lexemeParser.setNextParser(wordParser);
        sentenceParser.setNextParser(lexemeParser);
        paragraphParser.setNextParser(sentenceParser);

        Composite root = new Composite(ComponentType.PARAGRAPH);
        paragraphParser.parse(text, root);

        String collected = root.collect().replaceAll("\\s+", " ").trim();
        assertEquals(text, collected);
    }
}