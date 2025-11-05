package com.pp.app.array.strategy;

import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.factory.IntArrayFactory;

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
