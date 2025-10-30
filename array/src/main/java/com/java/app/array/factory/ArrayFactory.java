package com.java.app.array.factory;

import com.java.app.array.builder.ArrayBuilder;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.validator.ArrayValidator;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public class ArrayFactory {

    public ArrayEntity createArray(int[] array) {
        return getArrayEntity("array", array);
    }

    public ArrayEntity createRandomArray(String name, int size, int min, int max) {
        ArrayValidator.validate(size <= 0, "Size should be more than 0");
        ArrayValidator.validate(min > max, "Min bigger than max");

        int[] array = IntStream.range(0, size).map(i -> new SecureRandom().nextInt(max - min + 1) + min).toArray();

        return getArrayEntity(name, array);
    }

    public ArrayEntity createSequentialArray(String name, int start, int end) {
        ArrayValidator.validate(start > end, "Min bigger than max");
        int size = Math.abs(end - start) + 1;
        int[] array = IntStream.range(0, size).map(i -> start + i).toArray();

        return getArrayEntity(name, array);
    }

    public ArrayEntity createArrayWithPattern(String name, int size, int initialValue, int step) {
        ArrayValidator.validate(size <= 0, "Size should be more than 0");
        int[] array = IntStream.range(0, size).map(i -> initialValue + (i * step)).toArray();

        return getArrayEntity(name, array);
    }

    public ArrayEntity createArrayFromRange(String name, int start, int end, int step) {
        ArrayValidator.validate(step == 0, "Step cannot be zero");
        ArrayValidator.validate((end - start) * step < 0, "Step direction does not match range direction");
        int size = (Math.abs(end - start) / Math.abs(step)) + 1;
        int[] array = new int[size];

        int current = start;
        for (int i = 0; i < size && ((step > 0 && current <= end) || (step < 0 && current >= end)); i++) {
            array[i] = current;
            current += step;
        }

        return getArrayEntity(name, array);
    }

    private ArrayEntity getArrayEntity(String name, int[] array) {
        return new ArrayBuilder<>(ArrayEntity::new)
                .setName(name)
                .setArray(array)
                .build();
    }
}
