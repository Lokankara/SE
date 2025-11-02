package com.java.app.array.converter;

import com.java.app.array.exception.InvalidArrayDataException;

import java.util.Arrays;

public class ArrayConverter {

    private static final String DELIMITER = "[,;\\s-]+";

    public Integer[] parseToArray(String line) throws InvalidArrayDataException {
        try {
            return Arrays.stream(line.split(DELIMITER))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .toArray(Integer[]::new);

        } catch (Exception e) {
            throw new InvalidArrayDataException("Invalid data for parsing to integer: " + line, e);
        }
    }
}
