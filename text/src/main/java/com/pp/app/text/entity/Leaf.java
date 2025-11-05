package com.pp.app.text.entity;

import java.util.Collections;
import java.util.List;

public class Leaf implements TextComponent {
    private final char value;
    private final ComponentType type = ComponentType.SYMBOL;

    public Leaf(char value) {
        this.value = value;
    }

    public void swapChildrenIfPossible() {
        throw new UnsupportedOperationException("Cannot swap to leaf");
    }

    public ComponentType getType() {
        return type;
    }

    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Cannot add to leaf");
    }

    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    public String collect() {
        return String.valueOf(value);
    }
}
