package com.pp.app.array.specification;

import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.entity.integer.IntArrayStatistics;
import com.pp.app.array.entity.integer.IntWarehouse;

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
