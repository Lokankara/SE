package com.java.app.array.comparator;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.integer.IntArrayEntity;

import java.util.Comparator;

public enum ArrayComparator implements Comparator<IntArrayEntity> {
    ID(Comparator.comparingInt(ArrayEntity::getId)),
    NAME(Comparator.comparing(ArrayEntity::getName)),
    FIRST(Comparator.comparingInt(ArrayEntity::getFirst)),
    LENGTH(Comparator.comparingInt(ArrayEntity::getLength));

    private final Comparator<IntArrayEntity> comparator;

    ArrayComparator(Comparator<IntArrayEntity> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(IntArrayEntity a, IntArrayEntity b) {
        return comparator.compare(a, b);
    }
}
