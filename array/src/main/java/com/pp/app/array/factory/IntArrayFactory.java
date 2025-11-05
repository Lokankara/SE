package com.pp.app.array.factory;

import com.pp.app.array.builder.ArrayBuilder;
import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.validator.ArrayValidator;

import java.security.SecureRandom;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class IntArrayFactory {

    public IntArrayEntity createArray(Integer[] array) {
        return createArrayEntity("array", array);
    }

    public IntArrayEntity createRandomArray(String name, int size, int min, int max) {
        ArrayValidator.validate(size <= 0, "Size should be more than 0");
        ArrayValidator.validate(min > max, "Min bigger than max");

        IntUnaryOperator operator = i -> new SecureRandom().nextInt(max - min + 1) + min;
        Integer[] array = getIntegers(size, operator);

        return createArrayEntity(name, array);
    }

    public IntArrayEntity createSequentialArray(String name, int start, int end) {
        ArrayValidator.validate(start > end, "Min bigger than max");

        int size = Math.abs(end - start) + 1;
        IntUnaryOperator operator = i -> start + i;
        Integer[] array = getIntegers(size, operator);

        return createArrayEntity(name, array);
    }

    public IntArrayEntity createArrayWithPattern(String name, int size, int initialValue, int step) {
        ArrayValidator.validate(size <= 0, "Size should be more than 0");

        IntUnaryOperator operator = i -> initialValue + (i * step);
        Integer[] array = getIntegers(size, operator);

        return createArrayEntity(name, array);
    }

    public IntArrayEntity createArrayFromRange(String name, int start, int end, int step) {
        ArrayValidator.validate(step == 0, "Step cannot be zero");
        ArrayValidator.validate((end - start) * step < 0, "Step direction does not match range direction");

        int size = (Math.abs(end - start) / Math.abs(step)) + 1;
        Integer[] array = new Integer[size];
        int current = start;

        for (int i = 0; i < size && ((step > 0 && current <= end) || (step < 0 && current >= end)); i++) {
            array[i] = current;
            current += step;
        }

        return createArrayEntity(name, array);
    }

    private Integer[] getIntegers(int size, IntUnaryOperator operator) {
        return IntStream.range(0, size)
                .map(operator)
                .boxed()
                .toArray(Integer[]::new);
    }

    private IntArrayEntity createArrayEntity(String name, Integer[] array) {
        return new ArrayBuilder<>(IntArrayEntity::new)
                .setName(name)
                .setArray(array)
                .build();
    }
}
