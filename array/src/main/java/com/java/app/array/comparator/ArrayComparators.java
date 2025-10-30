package com.java.app.array.comparator;

import com.java.app.array.entity.ArrayEntity;

import java.util.Comparator;

public class ArrayComparators {

    public static final Comparator<ArrayEntity> BY_ID =
            Comparator.comparingInt(ArrayEntity::getId);

    public static final Comparator<ArrayEntity> BY_NAME =
            Comparator.comparing(ArrayEntity::getName);

    public static final Comparator<ArrayEntity> BY_FIRST_ELEMENT =
            Comparator.comparingInt(ArrayEntity::getFirst);

    public static final Comparator<ArrayEntity> BY_LENGTH =
            Comparator.comparingInt(ArrayEntity::getLength);

    private ArrayComparators() {}
}