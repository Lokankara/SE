package com.pp.app.text.parser;

import com.pp.app.text.entity.Composite;
import com.pp.app.text.entity.Leaf;

public abstract class AbstractParser {
    protected AbstractParser nextParser;

    public abstract String getDelimiter();

    public abstract Composite getComposite();

    public void setNextParser(AbstractParser parser) {
        this.nextParser = parser;
    }

    public void parse(String text, Composite parentComposite) {
        String delimiter = getDelimiter();
        if (delimiter.isEmpty() && nextParser == null) {
            for (char token : text.toCharArray()) {
                parentComposite.add(new Leaf(token));
            }
            return;
        }

        for (String part : delimiter.isEmpty() ? new String[] {text} : text.split(delimiter)) {
            if (!part.isEmpty()) {
                parentComposite.add(getComposite());
                if (nextParser != null) {
                    nextParser.parse(part, getComposite());
                }
            }
        }
    }
}
