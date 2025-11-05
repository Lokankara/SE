package com.pp.app.array.entity;

public class ArrayStatistics<T> {
    private final int id;
    private final String name;
    private final int length;
    private final T[] array;

    public ArrayStatistics(ArrayEntity<T> entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.length = entity.getArray().length;
        this.array = entity.getArray().clone();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public T[] getArray() {
        return array.clone();
    }
}
