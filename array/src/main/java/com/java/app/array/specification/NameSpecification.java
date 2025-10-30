package com.java.app.array.specification;

import com.java.app.array.entity.ArrayEntity;

public class NameSpecification implements Specification<ArrayEntity> {
    private final String targetName;

    public NameSpecification(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public boolean isSatisfiedBy(ArrayEntity entity) {
        return entity.getName().equals(targetName);
    }
}