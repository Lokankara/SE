package com.pp.app.text.strategy;

import com.pp.app.text.entity.TextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SameWordsSentencesStrategy implements TextProcessingStrategy {

    public String process(TextComponent text) {
        List<String> sentences = new ArrayList<>();
        for (TextComponent paragraph : text.getChildren()) {
            for (TextComponent sentence : paragraph.getChildren()) {
                sentences.add(sentence.collect());
            }
        }
        Map<String, Integer> map = new HashMap<>();
        for (String s : sentences) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        int max = 0;
        for (int v : map.values()) {
            if (v > max)
                max = v;
        }
        return String.valueOf(max);
    }
}