package com.pp.app.text.entity;

import java.util.List;

public interface TextComponent {
    List<TextComponent> getChildren();

    String collect();

    ComponentType getType();

    void add(TextComponent component);

    void swapChildrenIfPossible();
}
