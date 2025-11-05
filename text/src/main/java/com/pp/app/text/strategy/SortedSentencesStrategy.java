package com.pp.app.text.strategy;

import com.pp.app.text.entity.TextComponent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortedSentencesStrategy implements TextProcessingStrategy {

    public String process(TextComponent text) {
        List<TextComponent> sentences =
                text.getChildren().stream().flatMap(paragraph -> paragraph.getChildren().stream())
                        .sorted(Comparator.comparingInt(s -> s.getChildren().size())).toList();
        return sentences.stream().map(s -> s.collect() + "\n").collect(Collectors.joining());
    }
}