package com.java.app.array.strategy.algorithm;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.strategy.SortAlgorithm;

public class InsertionSort extends SortAlgorithm {

    @Override
    public IntArrayEntity sort(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
        return getFactory().createArray(array);
    }
}
