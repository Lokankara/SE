package com.java.app.array.strategy;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.factory.ArrayFactory;

public abstract class SortAlgorithm {

    private final ArrayFactory factory;

    public SortAlgorithm() {
        this.factory = new ArrayFactory();
    }

    public abstract ArrayEntity sort(int[] array);

    public ArrayFactory getFactory() {
        return factory;
    }
}
