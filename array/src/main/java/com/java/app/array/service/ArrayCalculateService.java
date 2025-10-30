package com.java.app.array.service;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.validator.ArrayValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class ArrayCalculateService implements CalculateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayCalculateService.class);

    private final ArrayValidator validator;

    public ArrayCalculateService(ArrayValidator validator) {
        this.validator = validator;
    }

    @Override
    public long count(ArrayEntity arrayEntity, IntPredicate predicate) {
        return getCheckedStreamArray(arrayEntity)
                .filter(predicate)
                .count();
    }

    @Override
    public int min(ArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity)
                .min()
                .orElse(Integer.MIN_VALUE);
    }

    @Override
    public int max(ArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity)
                .max()
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public double average(ArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity)
                .average()
                .orElse(0.0);
    }

    @Override
    public long sum(ArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity).sum();
    }

    public IntStream getCheckedStreamArray(ArrayEntity arrayEntity) {
        LOGGER.info("Validating ArrayEntity: {}", arrayEntity);
        return validator.checkAndGet(arrayEntity)
                .stream()
                .flatMapToInt(IntStream::of);
    }
}
