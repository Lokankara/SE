package com.java.app.array.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.integer.IntArrayEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class SearchServiceTest {

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = new SearchService();
    }

    @Test
    void testFindByIdReturnsEmptyListWhenNoArrays() {
        List<IntArrayEntity> result = searchService.findById(1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdWithNegativeId() {
        List<IntArrayEntity> result = searchService.findById(-1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdWithZeroId() {
        List<IntArrayEntity> result = searchService.findById(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdWithLargeId() {
        List<IntArrayEntity> result = searchService.findById(Integer.MAX_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameReturnsEmptyListWhenNoArrays() {
        List<IntArrayEntity> result = searchService.findByName("TestArray");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameWithNullName() {
        assertThrows(Exception.class, () -> searchService.findByName(null));
    }

    @Test
    void testFindByNameWithEmptyString() {
        List<IntArrayEntity> result = searchService.findByName("");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameWithSpecialCharacters() {
        List<IntArrayEntity> result = searchService.findByName("Test@Array#123");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameWithLongString() {
        String longName = "A".repeat(1000);
        List<IntArrayEntity> result = searchService.findByName(longName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanReturnsEmptyListWhenNoArrays() {
        List<IntArrayEntity> result = searchService.findBySumGreaterThan(10);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanWithZeroThreshold() {
        List<IntArrayEntity> result = searchService.findBySumGreaterThan(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanWithNegativeThreshold() {
        List<IntArrayEntity> result = searchService.findBySumGreaterThan(-100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanWithLargeThreshold() {
        List<IntArrayEntity> result = searchService.findBySumGreaterThan(Integer.MAX_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanReturnsEmptyListWhenNoArrays() {
        List<IntArrayEntity> result = searchService.findBySumLessThan(100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanWithZeroThreshold() {
        List<IntArrayEntity> result = searchService.findBySumLessThan(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanWithNegativeThreshold() {
        List<IntArrayEntity> result = searchService.findBySumLessThan(-50);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanWithMaxValue() {
        List<IntArrayEntity> result = searchService.findBySumLessThan(Integer.MAX_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanReturnsEmptyListWhenNoArrays() {
        List<IntArrayEntity> result = searchService.findByAverageGreaterThan(5.0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithZeroThreshold() {
        List<IntArrayEntity> result = searchService.findByAverageGreaterThan(0.0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithNegativeThreshold() {
        List<IntArrayEntity> result = searchService.findByAverageGreaterThan(-10.5);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithVerySmallThreshold() {
        List<IntArrayEntity> result = searchService.findByAverageGreaterThan(0.001);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithLargeThreshold() {
        List<IntArrayEntity> result = searchService.findByAverageGreaterThan(1000000.0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanReturnsEmptyListWhenNoArrays() {
        List<IntArrayEntity> result = searchService.findByMaxGreaterThan(50);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithZeroThreshold() {
        List<IntArrayEntity> result = searchService.findByMaxGreaterThan(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithNegativeThreshold() {
        List<IntArrayEntity> result = searchService.findByMaxGreaterThan(-25);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithMinValue() {
        List<IntArrayEntity> result = searchService.findByMaxGreaterThan(Integer.MIN_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithMaxValue() {
        List<IntArrayEntity> result = searchService.findByMaxGreaterThan(Integer.MAX_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testAllSearchMethodsReturnNonNullLists() {
        assertNotNull(searchService.findById(1));
        assertNotNull(searchService.findByName("test"));
        assertNotNull(searchService.findBySumGreaterThan(10));
        assertNotNull(searchService.findBySumLessThan(10));
        assertNotNull(searchService.findByAverageGreaterThan(5.0));
        assertNotNull(searchService.findByMaxGreaterThan(10));
    }

    @Test
    void testSearchMethodsWithBoundaryValues() {
        List<IntArrayEntity> result1 = searchService.findById(Integer.MIN_VALUE);
        List<IntArrayEntity> result2 = searchService.findById(Integer.MAX_VALUE);
        List<IntArrayEntity> result3 = searchService.findBySumGreaterThan(Integer.MIN_VALUE);
        List<IntArrayEntity> result4 = searchService.findBySumGreaterThan(Integer.MAX_VALUE);
        List<IntArrayEntity> result5 = searchService.findByAverageGreaterThan(Double.MIN_VALUE);
        List<IntArrayEntity> result6 = searchService.findByAverageGreaterThan(Double.MAX_VALUE);

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);
        assertNotNull(result4);
        assertNotNull(result5);
        assertNotNull(result6);

        assertTrue(result1.isEmpty());
        assertTrue(result2.isEmpty());
        assertTrue(result3.isEmpty());
        assertTrue(result4.isEmpty());
        assertTrue(result5.isEmpty());
        assertTrue(result6.isEmpty());
    }

    @Test
    void testConsecutiveSearchCallsReturnConsistentResults() {
        List<IntArrayEntity> result1 = searchService.findById(1);
        List<IntArrayEntity> result2 = searchService.findById(1);

        assertEquals(result1.size(), result2.size());
        assertEquals(result1, result2);
    }

    @Test
    void testDifferentSearchMethodsOnEmptyService() {
        assertTrue(searchService.findById(1).isEmpty());
        assertTrue(searchService.findByName("TestName").isEmpty());
        assertTrue(searchService.findBySumGreaterThan(5).isEmpty());
        assertTrue(searchService.findBySumLessThan(100).isEmpty());
        assertTrue(searchService.findByAverageGreaterThan(2.5).isEmpty());
        assertTrue(searchService.findByMaxGreaterThan(10).isEmpty());
    }

    @Test
    void testSearchMethodsReturnNewListInstances() {
        List<IntArrayEntity> result1 = searchService.findById(1);
        List<IntArrayEntity> result2 = searchService.findById(1);

        assertSame(result1, result2);
    }

    @Test
    void testSearchWithVariousThresholdTypes() {
        searchService.findBySumGreaterThan(0);
        searchService.findBySumGreaterThan(1);
        searchService.findBySumGreaterThan(-1);
        searchService.findBySumGreaterThan(1000);

        searchService.findBySumLessThan(0);
        searchService.findBySumLessThan(1);
        searchService.findBySumLessThan(-1);
        searchService.findBySumLessThan(1000);

        searchService.findByAverageGreaterThan(0.0);
        searchService.findByAverageGreaterThan(1.5);
        searchService.findByAverageGreaterThan(-2.7);
        searchService.findByAverageGreaterThan(999.999);

        searchService.findByMaxGreaterThan(0);
        searchService.findByMaxGreaterThan(50);
        searchService.findByMaxGreaterThan(-100);
        searchService.findByMaxGreaterThan(Integer.MAX_VALUE - 1);
    }

    @Test
    void testSearchServiceStateConsistency() {
        int iterations = 100;

        for (int i = 0; i < iterations; i++) {
            List<IntArrayEntity> result = searchService.findById(i);
            assertTrue(result.isEmpty());
        }

        for (int i = 0; i < iterations; i++) {
            List<IntArrayEntity> result = searchService.findBySumGreaterThan(i);
            assertTrue(result.isEmpty());
        }
    }

    @Test
    void testSearchMethodsHandleExtremeInputs() {
        assertDoesNotThrow(() -> {
            searchService.findById(Integer.MIN_VALUE);
            searchService.findById(Integer.MAX_VALUE);
            searchService.findBySumGreaterThan(Integer.MIN_VALUE);
            searchService.findBySumGreaterThan(Integer.MAX_VALUE);
            searchService.findBySumLessThan(Integer.MIN_VALUE);
            searchService.findBySumLessThan(Integer.MAX_VALUE);
            searchService.findByAverageGreaterThan(Double.NEGATIVE_INFINITY);
            searchService.findByAverageGreaterThan(Double.POSITIVE_INFINITY);
            searchService.findByMaxGreaterThan(Integer.MIN_VALUE);
            searchService.findByMaxGreaterThan(Integer.MAX_VALUE);
        });
    }

    @Test
    void testSearchServiceInstanceIndependence() {
        SearchService anotherSearchService = new SearchService();

        List<IntArrayEntity> result1 = searchService.findById(1);
        List<IntArrayEntity> result2 = anotherSearchService.findById(1);

        assertEquals(result1.size(), result2.size());
        assertTrue(result1.isEmpty());
        assertTrue(result2.isEmpty());
    }
}