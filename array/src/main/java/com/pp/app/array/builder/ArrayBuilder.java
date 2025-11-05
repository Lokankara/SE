package com.pp.app.array.builder;

import com.pp.app.array.validator.ArrayValidator;

import java.util.concurrent.atomic.AtomicInteger;

public final class ArrayBuilder<T> {

    private String name;
    private Integer[] values;
    private final TriFunction<Integer, String, Integer[], T> creator;
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public ArrayBuilder(TriFunction<Integer, String, Integer[], T> creator) {
        this.creator = creator;
    }


    public ArrayBuilder<T> setArray(Integer[] array) {
        this.values = ArrayValidator.getIfNull(array.clone(), new Integer[0]);
        return this;
    }

    public ArrayBuilder<T> setName(String name) {
        this.name = ArrayValidator.getOrThrow(name);
        return this;
    }

    public T build() {
        return creator.apply(COUNTER.incrementAndGet(), name, ArrayValidator.getIfNull(values.clone(), new Integer[0]));
    }
}
