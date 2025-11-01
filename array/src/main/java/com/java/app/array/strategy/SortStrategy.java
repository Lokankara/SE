package com.java.app.array.strategy;

import com.java.app.array.builder.ArrayBuilder;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.strategy.algorithm.BubbleSort;
import com.java.app.array.strategy.algorithm.HeapSort;
import com.java.app.array.strategy.algorithm.InsertionSort;
import com.java.app.array.strategy.algorithm.MergeSort;
import com.java.app.array.strategy.algorithm.SelectionSort;

import java.util.List;


public enum SortStrategy {
    MERGE(new MergeSort()),
    HEAP(new HeapSort()),
    INSERTION(new InsertionSort()),
    SELECTION(new SelectionSort()),
    BUBBLE(new BubbleSort());

    private final SortAlgorithm algorithm;

    SortStrategy(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public SortAlgorithm getAlgorithm() {
        return algorithm;
    }

    public List<ArrayEntity> sort(List<ArrayEntity> arrays) {
        return arrays == null || arrays.isEmpty()
                ? arrays
                : arrays.stream()
                .map(e -> new ArrayBuilder<>(
                        ArrayEntity::new)
                        .setName(e.getName())
                        .setArray(algorithm.sort(e.getArray()).getArray())
                        .build())
                .toList();
    }
}