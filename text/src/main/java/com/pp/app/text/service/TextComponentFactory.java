package com.pp.app.text.service;

import com.pp.app.text.entity.Leaf;
import com.pp.app.text.entity.ComponentType;
import com.pp.app.text.entity.Composite;

public class TextComponentFactory {
    public static Composite create(ComponentType type) {
        return new Composite(type);
    }
    public static Leaf createSymbol(char c) {
        return new Leaf(c);
    }
}