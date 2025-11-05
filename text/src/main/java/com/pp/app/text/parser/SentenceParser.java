package com.pp.app.text.parser;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;

public class SentenceParser extends AbstractParser {
    public String getDelimiter() {
        return "(?<=[.!?])\\s+";
    }

    public Composite getComposite() {
        return new Composite(ComponentType.SENTENCE);
    }
}