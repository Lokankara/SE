package com.java.app.array.specification;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.IntArrayStatistics;
import com.java.app.array.entity.Warehouse;

public class AverageGreaterThanSpecification implements Specification<ArrayEntity> {
    private final double threshold;

    public AverageGreaterThanSpecification(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean specify(ArrayEntity entity) {
        IntArrayStatistics statistics = Warehouse.getInstance().getStatistics(entity.getId());
        return statistics != null && statistics.getAverage() > threshold;
    }
}