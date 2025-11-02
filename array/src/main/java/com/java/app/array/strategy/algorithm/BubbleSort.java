package com.java.app.array.strategy.algorithm;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.strategy.SortAlgorithm;

public class BubbleSort extends SortAlgorithm {

    @Override
    public IntArrayEntity sort(Integer[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return getFactory().createArray(array);
    }
}
