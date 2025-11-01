package com.java.app.array.validator;

import com.java.app.array.entity.ArrayEntity;

import java.util.Optional;
import java.util.regex.Pattern;

public class ArrayValidator {
    private static final String VALID_ARRAY_REGEX = "^[\\d\\s,;-]+$";

    public static <T> T getIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static <T> T getOrThrow(T value) {
        if (value == null) {
            throw new IllegalStateException("Value should be not null");
        }
        return value;
    }

    public static void validate(boolean condition, String message) {
        if (condition) {
            throw new IllegalStateException(message);
        }
    }

    public static int[] checkSize(int size) {
        return new int[Math.max(size, 0)];
    }

    public boolean isValid(String line) {
        return Pattern.matches(VALID_ARRAY_REGEX, line);
    }

    public Optional<int[]> checkAndGet(ArrayEntity arrayEntity) {
        return arrayEntity == null || arrayEntity.isEmpty()
                ? Optional.empty()
                : Optional.of(arrayEntity.getArray());
    }
}
