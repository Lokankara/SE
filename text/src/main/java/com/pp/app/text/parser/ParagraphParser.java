package com.pp.app.text.parser;

import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;

public class ParagraphParser extends AbstractParser {
    @Override
    public String getDelimiter() {
        return "(?m)(?=^\\s{4,})";
    }

    @Override
    public Composite getComposite() {
        return new Composite(ComponentType.PARAGRAPH);
    }

}