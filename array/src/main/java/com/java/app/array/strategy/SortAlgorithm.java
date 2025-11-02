package com.java.app.array.strategy;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.factory.IntArrayFactory;

public abstract class SortAlgorithm {

    private final IntArrayFactory factory;

    protected SortAlgorithm() {
        this.factory = new IntArrayFactory();
    }

    public abstract IntArrayEntity sort(Integer[] array);

    public IntArrayFactory getFactory() {
        return factory;
    }
}
