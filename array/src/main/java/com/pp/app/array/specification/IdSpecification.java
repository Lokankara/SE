package com.pp.app.array.specification;

import com.pp.app.array.entity.integer.IntArrayEntity;

public class IdSpecification implements Specification<IntArrayEntity> {
    private final int targetId;

    public IdSpecification(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean specify(IntArrayEntity entity) {
        return entity.getId() == targetId;
    }
}
