package com.java.app.array.dao;

import com.java.app.array.converter.ArrayConverter;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.exception.FileReadException;
import com.java.app.array.exception.InvalidArrayDataException;
import com.java.app.array.reader.FileArrayReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileArrayRepository {

    private final FileArrayReader reader;
    private final ArrayConverter converter;
    private final Map<Integer, String> errorMap;

    public FileArrayRepository(FileArrayReader reader, ArrayConverter converter) {
        errorMap = new HashMap<>();
        this.reader = reader;
        this.converter = converter;
    }

    public List<ArrayEntity> readArrays(String resourcePath) throws FileReadException {
        List<ArrayEntity> arrays = new ArrayList<>();
        List<String> lines = reader.readLines(resourcePath);
        for (int i = 0; i < lines.size(); i++) {
            try {
                int[] array = converter.parseToArray(lines.get(i));
                arrays.add(new ArrayEntity(array));
            } catch (InvalidArrayDataException e) {
                errorMap.put(i + 1, lines.get(i));
            }
        }
        return arrays;
    }

    public Map<Integer, String> getErrorMap() {
        return new HashMap<>(errorMap);
    }
}

