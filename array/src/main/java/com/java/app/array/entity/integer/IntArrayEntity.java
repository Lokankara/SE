package com.java.app.array.entity.integer;

import com.java.app.array.entity.ArrayEntity;

public class IntArrayEntity extends ArrayEntity<Integer> {

    public IntArrayEntity() {
    }

    public IntArrayEntity(Integer[] array) {
        super(array);
    }

    public IntArrayEntity(int id, String name, Integer[] array) {
        super(id, name, array);
    }

    private static Integer[] getDefaultValue() {
        return new Integer[0];
    }

    @Override
    protected Integer[] getEmptyArray() {
        return getDefaultValue();
    }
}
