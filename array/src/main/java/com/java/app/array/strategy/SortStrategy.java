package com.java.app.array.strategy;

import com.java.app.array.strategy.algorithm.BubbleSort;
import com.java.app.array.strategy.algorithm.HeapSort;
import com.java.app.array.strategy.algorithm.InsertionSort;
import com.java.app.array.strategy.algorithm.MergeSort;
import com.java.app.array.strategy.algorithm.SelectionSort;

public enum SortStrategy {

    Merge(new MergeSort()),
    Heap(new HeapSort()),
    Insertion(new InsertionSort()),
    Selection(new SelectionSort()),
    Bubble(new BubbleSort());

    private final SortAlgorithm algorithm;

    SortStrategy(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public SortAlgorithm getAlgorithm() {
        return algorithm;
    }
}