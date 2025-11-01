package com.java.app.array.specification;

import com.java.app.array.entity.ArrayEntity;

public class IdSpecification implements Specification<ArrayEntity> {
    private final int targetId;

    public IdSpecification(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean specify(ArrayEntity entity) {
        return entity.getId() == targetId;
    }
}
