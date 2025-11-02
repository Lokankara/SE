package com.java.app.array.specification;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.validator.ArrayValidator;

public class NameSpecification implements Specification<IntArrayEntity> {
    private final String targetName;

    public NameSpecification(String targetName) {
        ArrayValidator.getOrThrow(targetName);
        this.targetName = targetName;
    }

    @Override
    public boolean specify(IntArrayEntity entity) {
        return entity.getName().equals(targetName);
    }
}
