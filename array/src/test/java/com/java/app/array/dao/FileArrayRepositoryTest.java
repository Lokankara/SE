package com.java.app.array.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.converter.ArrayConverter;
import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.exception.FileReadException;
import com.java.app.array.reader.FileArrayReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class FileArrayRepositoryTest {

    private static final String VALID_FILE_PATH = "data/arrays.txt";
    private static final String INVALID_FILE_PATH = "data/nonexistent.txt";
    private final FileArrayReader reader = new FileArrayReader();
    private final ArrayConverter converter = new ArrayConverter();

    @Test
    @DisplayName("readArrays with nonexistent file throws FileReadException containing path")
    void testReadArraysWithInvalidFile() {
        FileArrayRepository repository = new FileArrayRepository(reader, converter);

        FileReadException ex = assertThrows(FileReadException.class, () -> repository.readArrays(INVALID_FILE_PATH));
        assertTrue(ex.getMessage().contains(INVALID_FILE_PATH));
        assertNotNull(ex.getCause());
    }

    @Test
    @DisplayName("readLines throws FileReadException when path is null or blank")
    void testReadArraysWithNullOrBlankPath() {
        FileArrayRepository repository = new FileArrayRepository(reader, converter);

        FileReadException exNull = assertThrows(FileReadException.class, () -> repository.readArrays(null));
        assertTrue(exNull.getMessage().toLowerCase().contains("null"));

        FileReadException exBlank = assertThrows(FileReadException.class, () -> repository.readArrays("  "));
        assertTrue(exBlank.getMessage().toLowerCase().contains("null") || exBlank.getMessage().toLowerCase().contains("blank"));
    }

    @Test
    void testReadArraysWithValidFile() throws FileReadException {
        FileArrayRepository repository = new FileArrayRepository(reader, converter);
        List<IntArrayEntity> arrays = repository.readArrays(VALID_FILE_PATH);

        assertNotNull(arrays);
        assertFalse(arrays.isEmpty());
        assertEquals(10, arrays.size());
    }

    @Test
    void testReadArraysWithInvalidData() {
        FileArrayRepository repository = new FileArrayRepository(reader, converter);
        Exception exception = assertThrows(FileReadException.class, () -> repository.readArrays(INVALID_FILE_PATH));
        assertEquals( "Error reading file: " + INVALID_FILE_PATH, exception.getMessage());
    }
}