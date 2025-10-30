package com.java.app.array.builder;

import com.java.app.array.validator.ArrayValidator;

import java.util.concurrent.atomic.AtomicInteger;

public final class ArrayBuilder<T> {

    private String name;
    private int[] values;
    private final TriFunction<Integer, String, int[], T> creator;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public ArrayBuilder(TriFunction<Integer, String, int[], T> creator) {
        this.creator = ArrayValidator.getOrThrow(creator);
    }


    public ArrayBuilder<T> setArray(int[] array) {
        this.values = ArrayValidator.getIfNull(array.clone(), new int[0]);
        return this;
    }

    public ArrayBuilder<T> setName(String name) {
        this.name = ArrayValidator.getOrThrow(name);
        return this;
    }

    public T build() {
        return creator.apply(COUNTER.incrementAndGet(), name, ArrayValidator.getIfNull(values.clone(), new int[0]));
    }
}
