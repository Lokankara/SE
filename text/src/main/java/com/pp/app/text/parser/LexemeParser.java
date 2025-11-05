package com.pp.app.text.parser;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;

public class LexemeParser extends AbstractParser {

    @Override
    public String getDelimiter() {
        return "\\s+";
    }

    @Override
    public Composite getComposite() {
        return new Composite(ComponentType.LEXEME);
    }
}
