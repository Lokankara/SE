package com.java.app.array.entity.integer;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.ArrayStatistics;

import java.util.Arrays;

public class IntArrayStatistics extends ArrayStatistics<Integer> {
    private final int sum;
    private final double average;
    private final int min;
    private final int max;
    private final int count;

    public IntArrayStatistics(ArrayEntity<Integer> entity) {
        super(entity);
        int[] ints = Arrays.stream(entity.getArray()).mapToInt(Integer::intValue).toArray();
        this.sum = Arrays.stream(ints).sum();
        this.average = ints.length > 0 ? Arrays.stream(ints).average().orElse(0) : 0;
        this.min = ints.length > 0 ? Arrays.stream(ints).min().orElse(0) : 0;
        this.max = ints.length > 0 ? Arrays.stream(ints).max().orElse(0) : 0;
        this.count = entity.getArray().length;
    }

    public int getCount() {
        return count;
    }

    public int getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return String.format(
                "Array Statistics'{'sum=%s, average==%s, max==%s, min==%s, count==%s'}'",
                sum, average, max, min, count);
    }
}
