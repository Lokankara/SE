package com.java.app.array.factory;

import com.java.app.array.builder.StringArrayBuilder;
import com.java.app.array.entity.StringArrayEntity;
import com.java.app.array.validator.ArrayValidator;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public class StringArrayFactory {

    private StringArrayEntity createArray(String name, String[] array) {
        return new StringArrayBuilder<>(StringArrayEntity::new)
                .setName(name)
                .setArray(array)
                .build();
    }

    public StringArrayEntity createRandomArray(String name, int size, String[] pool) {
        ArrayValidator.validate(size <= 0, "Size should be more than 0");
        ArrayValidator.validate(pool == null || pool.length == 0, "Pool cannot be empty");
        String[] array = IntStream.range(0, size)
                .mapToObj(i -> pool[(new SecureRandom().nextInt(pool.length))])
                .toArray(String[]::new);

        return createArray(name, array);
    }

    public StringArrayEntity createSequentialArray(String name, String start, String end) {
        ArrayValidator.validate(start == null || end == null, "Start and end cannot be null");
        String[] array = {start, end};
        return createArray(name, array);
    }
}
