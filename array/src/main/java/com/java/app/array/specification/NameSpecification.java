package com.java.app.array.specification;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.validator.ArrayValidator;

public class NameSpecification implements Specification<ArrayEntity> {
    private final String targetName;

    public NameSpecification(String targetName) {
        ArrayValidator.getOrThrow(targetName);
        this.targetName = targetName;
    }

    @Override
    public boolean specify(ArrayEntity entity) {
        return entity.getName().equals(targetName);
    }
}