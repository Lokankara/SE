package com.java.app.array.comparator;

import com.java.app.array.entity.ArrayEntity;

import java.util.Comparator;

public enum ArrayComparator {
    ID(Comparator.comparingInt(ArrayEntity::getId)),
    NAME(Comparator.comparing(ArrayEntity::getName)),
    FIRST(Comparator.comparingInt(ArrayEntity::getFirst)),
    LENGTH(Comparator.comparingInt(ArrayEntity::getLength));

    private final Comparator<ArrayEntity> comparator;

    ArrayComparator(Comparator<ArrayEntity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<ArrayEntity> getComparator() {
        return comparator;
    }
}
