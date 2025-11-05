package com.pp.app.text.strategy;

import com.pp.app.text.entity.TextComponent;

public interface TextProcessingStrategy {
    String process(TextComponent text);
}
