package com.pp.app.array.strategy;

import com.pp.app.array.builder.ArrayBuilder;
import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.strategy.algorithm.BubbleSort;
import com.pp.app.array.strategy.algorithm.HeapSort;
import com.pp.app.array.strategy.algorithm.InsertionSort;
import com.pp.app.array.strategy.algorithm.MergeSort;
import com.pp.app.array.strategy.algorithm.SelectionSort;

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

    public List<IntArrayEntity> sort(List<IntArrayEntity> arrays) {
        return arrays == null || arrays.isEmpty()
                ? arrays
                : arrays.stream()
                .map(e -> new ArrayBuilder<>(
                        IntArrayEntity::new)
                        .setName(e.getName())
                        .setArray(algorithm.sort(e.getArray()).getArray())
                        .build())
                .toList();
    }
}
