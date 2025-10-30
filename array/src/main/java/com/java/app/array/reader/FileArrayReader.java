package com.java.app.array.reader;

import com.java.app.array.exception.FileReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class FileArrayReader {

    public List<String> readLines(String resourcePath) throws FileReadException {
        if (resourcePath == null || resourcePath.isBlank()) {
            throw new FileReadException("File name is null");
        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            return reader.lines().toList();
        } catch (IOException | NullPointerException e) {
            throw new FileReadException(resourcePath, e);
        }
    }
}
