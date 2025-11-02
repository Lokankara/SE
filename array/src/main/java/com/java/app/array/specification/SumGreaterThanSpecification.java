package com.java.app.array.specification;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.integer.IntArrayStatistics;
import com.java.app.array.entity.integer.IntWarehouse;

public class SumGreaterThanSpecification implements Specification<IntArrayEntity> {
    private final int threshold;

    public SumGreaterThanSpecification(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean specify(IntArrayEntity entity) {
        IntArrayStatistics statistics = IntWarehouse.getInstance().getStatistics(entity.getId());
        return statistics != null && statistics.getSum() > threshold;
    }
}
