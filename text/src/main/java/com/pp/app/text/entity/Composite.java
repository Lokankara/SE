package com.pp.app.text.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Composite implements TextComponent {

    private final List<TextComponent> children = new ArrayList<>();
    private final ComponentType type;

    public Composite(ComponentType type) {
        this.type = type;
    }

    public ComponentType getType() {
        return type;
    }

    public void swapChildrenIfPossible() {
        if (children.size() > 1) {
            children.set(0, children.getLast());
            children.set(children.size() - 1, children.getFirst());
        }
    }

    public void add(TextComponent component) {
        children.add(component);
    }

    public List<TextComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public String collect() {
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case PARAGRAPH -> buildComponent(sb, "\n");
            case SENTENCE -> buildComponent(sb, " ");
            case LEXEME, WORD, PUNCTUATION, SYMBOL -> buildComponent(sb, "");
        }
        return sb.toString();
    }

    private void buildComponent(StringBuilder sb, String delimiter) {
        for (int i = 0; i < children.size(); i++) {
            sb.append(children.get(i).collect());
            if (i < children.size() - 1) {
                sb.append(delimiter);
            }
        }
    }
}
