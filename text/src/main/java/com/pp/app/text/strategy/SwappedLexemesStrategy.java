package com.pp.app.text.strategy;

import com.pp.app.text.entity.TextComponent;

import java.util.Collections;

public class SwappedLexemesStrategy implements TextProcessingStrategy {

    @Override
    public String process(TextComponent text) {
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                java.util.List<TextComponent> lexemes = sentence.getChildren();
                if (lexemes.size() > 1) {
                    Collections.swap(lexemes, 0, lexemes.size() - 1);
                }
            }
        }
        return text.collect();
    }
}
