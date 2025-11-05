package com.pp.app.text.parser;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;

public class WordParser extends AbstractParser {

    public String getDelimiter() {
        return "(?=\\p{Punct})|\\s+";
    }

    public Composite getComposite() {
        return new Composite(ComponentType.WORD);
    }
}