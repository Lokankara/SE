package com.pp.app.array.dao;

import com.pp.app.array.converter.ArrayConverter;
import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.exception.FileReadException;
import com.pp.app.array.exception.InvalidArrayDataException;
import com.pp.app.array.reader.FileArrayReader;

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

    public List<IntArrayEntity> readArrays(String resourcePath) throws FileReadException {
        List<IntArrayEntity> arrays = new ArrayList<>();
        List<String> lines = reader.readLines(resourcePath);
        for (int i = 0; i < lines.size(); i++) {
            try {
                Integer[] array = converter.parseToArray(lines.get(i));
                arrays.add(new IntArrayEntity(array));
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

