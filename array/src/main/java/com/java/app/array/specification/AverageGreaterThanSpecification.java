package com.java.app.array.specification;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.integer.IntArrayStatistics;
import com.java.app.array.entity.integer.IntWarehouse;

public class AverageGreaterThanSpecification implements Specification<IntArrayEntity> {
    private final double threshold;

    public AverageGreaterThanSpecification(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean specify(IntArrayEntity entity) {
        IntArrayStatistics statistics = IntWarehouse.getInstance().getStatistics(entity.getId());
        return statistics != null && statistics.getAverage() > threshold;
    }
}
