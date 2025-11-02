package com.java.app.array.factory;

import static org.junit.jupiter.api.Assertions.*;

import com.java.app.array.entity.string.StringArrayEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


class StringArrayFactoryTest {

    private final StringArrayFactory factory = new StringArrayFactory();

    @Test
    @DisplayName("createRandomArray produces array of requested size with elements from pool")
    void createRandomArrayProducesCorrectSizeAndPoolElements() {
        String[] pool = new String[] {"a", "b", "c"};
        StringArrayEntity entity = factory.createRandomArray("rnd", 10, pool);
        assertNotNull(entity);
        assertEquals("rnd", entity.getName());
        String[] arr = entity.getArray();
        assertNotNull(arr);
        assertEquals(10, arr.length);
        Set<String> poolSet = new HashSet<>(Arrays.asList(pool));
        for (String s : arr) {
            assertTrue(poolSet.contains(s), "element must be from pool");
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    @DisplayName("createRandomArray throws when size is not positive")
    void createRandomArrayThrowsOnInvalidSize(int badSize) {
        String[] pool = new String[] {"x"};
        assertThrows(IllegalArgumentException.class, () -> factory.createRandomArray("n", badSize, pool));
    }

    @Test
    @DisplayName("createRandomArray throws when pool is null")
    void createRandomArrayThrowsWhenPoolNull() {
        assertThrows(IllegalArgumentException.class, () -> factory.createRandomArray("n", 1, null));
    }

    @Test
    @DisplayName("createRandomArray throws when pool is empty")
    void createRandomArrayThrowsWhenPoolEmpty() {
        assertThrows(IllegalArgumentException.class, () -> factory.createRandomArray("n", 1, new String[0]));
    }

    @Test
    @DisplayName("createSequentialArray returns array with start and end")
    void createSequentialArrayReturnsStartAndEnd() {
        StringArrayEntity entity = factory.createSequentialArray("seq", "start", "end");
        assertNotNull(entity);
        assertEquals("seq", entity.getName());
        String[] arr = entity.getArray();
        assertNotNull(arr);
        assertEquals(2, arr.length);
        assertEquals("start", arr[0]);
        assertEquals("end", arr[1]);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "valid" })
    @DisplayName("createSequentialArray throws when start or end is null")
    void createSequentialArrayThrowsWhenStartOrEndNull(String nonNull) {
        assertThrows(IllegalArgumentException.class, () -> factory.createSequentialArray("n", null, "end"));
        assertThrows(IllegalArgumentException.class, () -> factory.createSequentialArray("n", "start", null));
    }
}
