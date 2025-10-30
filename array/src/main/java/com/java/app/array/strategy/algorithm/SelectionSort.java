package com.java.app.array.strategy.algorithm;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.strategy.SortAlgorithm;

public class SelectionSort extends SortAlgorithm {

    @Override
    public ArrayEntity sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
        }
        return getFactory().createArray(array);
    }
}
