package com.java.app.array.entity;

import com.java.app.array.service.ArrayCalculateService;
import com.java.app.array.validator.ArrayValidator;

public class ArrayStatistics {
    private final int sum;
    private final double average;
    private final int max;
    private final int min;
    private final int count;


    public ArrayStatistics(ArrayEntity entity) {
        ArrayCalculateService service = new ArrayCalculateService(new ArrayValidator());
        this.count = entity.getArray().length;
        if (count == 0) {
            this.sum = 0;
            this.average = 0.0;
            this.max = 0;
            this.min = 0;
        } else {
            this.sum = service.max(entity);
            this.average = service.average(entity);
            this.max = service.max(entity);
            this.min = service.min(entity);
        }
    }

    public int getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getCount() {
        return count;
    }
}
