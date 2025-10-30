package com.java.app.array.strategy.algorithm;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.strategy.SortAlgorithm;

public class HeapSort extends SortAlgorithm {

    @Override
    public ArrayEntity sort(int[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heap(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heap(array, i, 0);
        }
        return getFactory().createArray(array);
    }

    private void heap(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            heap(array, n, largest);
        }
    }
}
