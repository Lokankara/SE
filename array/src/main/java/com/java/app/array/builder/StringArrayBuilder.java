package com.java.app.array.builder;

import com.java.app.array.validator.ArrayValidator;

import java.util.concurrent.atomic.AtomicInteger;

public class StringArrayBuilder<T> {

    private String name;
    private String[] values;
    private final TriFunction<Integer, String, String[], T> creator;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public StringArrayBuilder(TriFunction<Integer, String, String[], T> creator) {
        this.creator = ArrayValidator.getOrThrow(creator);
    }

    public StringArrayBuilder<T> setArray(String[] array) {
        this.values = ArrayValidator.getIfNull(array.clone(), new String[0]);
        return this;
    }

    public StringArrayBuilder<T> setName(String name) {
        this.name = ArrayValidator.getOrThrow(name);
        return this;
    }

    public T build() {
        return creator.apply(COUNTER.incrementAndGet(), name, ArrayValidator.getIfNull(values.clone(), new String[0]));
    }
}
