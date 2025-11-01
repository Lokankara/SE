package com.java.app.array.specification;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.IntArrayStatistics;
import com.java.app.array.entity.Warehouse;

public class SumGreaterThanSpecification implements Specification<ArrayEntity> {
    private final int threshold;

    public SumGreaterThanSpecification(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean isSatisfiedBy(ArrayEntity entity) {
        IntArrayStatistics statistics = Warehouse.getInstance().getStatistics(entity.getId());
        return statistics != null && statistics.getSum() > threshold;
    }
}