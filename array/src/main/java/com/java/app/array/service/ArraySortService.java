package com.java.app.array.service;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.factory.ArrayFactory;
import com.java.app.array.strategy.SortAlgorithm;
import com.java.app.array.strategy.SortStrategy;

public class ArraySortService implements SortService {
    @Override
    public ArrayEntity sort(ArrayEntity arrayEntity, SortStrategy strategy) {
        int[] array = arrayEntity.getArray();
        SortAlgorithm algorithm = strategy.getAlgorithm();
        ArrayEntity sorted = algorithm.sort(array);
        return new ArrayFactory().createArray(sorted.getArray());
    }
}
