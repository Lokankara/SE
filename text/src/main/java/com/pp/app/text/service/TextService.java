package com.pp.app.text.service;

import com.pp.app.text.entity.TextComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextService {

    public List<TextComponent> getAllSentences(TextComponent text) {
        return text.getChildren()
                .stream()
                .flatMap(paragraph -> paragraph.getChildren().stream())
                .toList();
    }

    public int maxEqualSentencesCount(TextComponent text) {
        List<String> sentences = getAllSentences(text).stream()
                .map(s -> s.collect().trim())
                .toList();
        Map<String, Integer> map = new HashMap<>();
        sentences.forEach(s -> map.put(s, map.getOrDefault(s, 0) + 1));
        return map.values().stream().mapToInt(v -> v).filter(v -> v >= 0).max().orElse(0);
    }


    public List<TextComponent> sortSentencesByLexemeCount(TextComponent text) {
        List<TextComponent> sentences = new ArrayList<>(getAllSentences(text));
        sentences.sort(Comparator.comparingInt(s -> s.getChildren().size()));
        return sentences;
    }

    public void swapFirstLastLexeme(TextComponent text) {
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                sentence.swapChildrenIfPossible();
            }
        }
    }
}
