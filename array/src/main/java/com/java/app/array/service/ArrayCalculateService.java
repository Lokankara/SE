package com.java.app.array.service;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.validator.ArrayValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class ArrayCalculateService implements CalculateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayCalculateService.class);

    private final ArrayValidator validator;

    public ArrayCalculateService(ArrayValidator validator) {
        this.validator = validator;
    }

    @Override
    public long count(IntArrayEntity arrayEntity, IntPredicate predicate) {
        return getCheckedStreamArray(arrayEntity)
                .filter(predicate)
                .count();
    }

    @Override
    public int min(IntArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity)
                .min()
                .orElse(Integer.MIN_VALUE);
    }

    @Override
    public int max(IntArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity)
                .max()
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public double average(IntArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity)
                .average()
                .orElse(0.0);
    }

    @Override
    public long sum(IntArrayEntity arrayEntity) {
        return getCheckedStreamArray(arrayEntity).sum();
    }

    public IntStream getCheckedStreamArray(IntArrayEntity arrayEntity) {
        LOGGER.info("Validating IntArrayEntity: {}", arrayEntity);
        return validator.checkAndGet(arrayEntity)
                .stream()
                .flatMapToInt(array -> Arrays.stream(array).mapToInt(Integer::intValue));
    }
}
