package com.pp.app.text.parser;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;

public class PunctuationParser extends AbstractParser {
    public String getDelimiter() {
        return "";
    }

    public Composite getComposite() {
        return new Composite(ComponentType.PUNCTUATION);
    }
}