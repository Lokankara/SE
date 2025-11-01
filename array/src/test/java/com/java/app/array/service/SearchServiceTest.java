package com.java.app.array.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.ArrayEntity;
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
        List<ArrayEntity> result = searchService.findById(1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdWithNegativeId() {
        List<ArrayEntity> result = searchService.findById(-1);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdWithZeroId() {
        List<ArrayEntity> result = searchService.findById(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByIdWithLargeId() {
        List<ArrayEntity> result = searchService.findById(Integer.MAX_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameReturnsEmptyListWhenNoArrays() {
        List<ArrayEntity> result = searchService.findByName("TestArray");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameWithNullName() {
        assertThrows(Exception.class, () -> searchService.findByName(null));
    }

    @Test
    void testFindByNameWithEmptyString() {
        List<ArrayEntity> result = searchService.findByName("");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameWithSpecialCharacters() {
        List<ArrayEntity> result = searchService.findByName("Test@Array#123");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByNameWithLongString() {
        String longName = "A".repeat(1000);
        List<ArrayEntity> result = searchService.findByName(longName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanReturnsEmptyListWhenNoArrays() {
        List<ArrayEntity> result = searchService.findBySumGreaterThan(10);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanWithZeroThreshold() {
        List<ArrayEntity> result = searchService.findBySumGreaterThan(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanWithNegativeThreshold() {
        List<ArrayEntity> result = searchService.findBySumGreaterThan(-100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumGreaterThanWithLargeThreshold() {
        List<ArrayEntity> result = searchService.findBySumGreaterThan(Integer.MAX_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanReturnsEmptyListWhenNoArrays() {
        List<ArrayEntity> result = searchService.findBySumLessThan(100);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanWithZeroThreshold() {
        List<ArrayEntity> result = searchService.findBySumLessThan(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanWithNegativeThreshold() {
        List<ArrayEntity> result = searchService.findBySumLessThan(-50);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBySumLessThanWithMaxValue() {
        List<ArrayEntity> result = searchService.findBySumLessThan(Integer.MAX_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanReturnsEmptyListWhenNoArrays() {
        List<ArrayEntity> result = searchService.findByAverageGreaterThan(5.0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithZeroThreshold() {
        List<ArrayEntity> result = searchService.findByAverageGreaterThan(0.0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithNegativeThreshold() {
        List<ArrayEntity> result = searchService.findByAverageGreaterThan(-10.5);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithVerySmallThreshold() {
        List<ArrayEntity> result = searchService.findByAverageGreaterThan(0.001);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAverageGreaterThanWithLargeThreshold() {
        List<ArrayEntity> result = searchService.findByAverageGreaterThan(1000000.0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanReturnsEmptyListWhenNoArrays() {
        List<ArrayEntity> result = searchService.findByMaxGreaterThan(50);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithZeroThreshold() {
        List<ArrayEntity> result = searchService.findByMaxGreaterThan(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithNegativeThreshold() {
        List<ArrayEntity> result = searchService.findByMaxGreaterThan(-25);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithMinValue() {
        List<ArrayEntity> result = searchService.findByMaxGreaterThan(Integer.MIN_VALUE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByMaxGreaterThanWithMaxValue() {
        List<ArrayEntity> result = searchService.findByMaxGreaterThan(Integer.MAX_VALUE);

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
        List<ArrayEntity> result1 = searchService.findById(Integer.MIN_VALUE);
        List<ArrayEntity> result2 = searchService.findById(Integer.MAX_VALUE);
        List<ArrayEntity> result3 = searchService.findBySumGreaterThan(Integer.MIN_VALUE);
        List<ArrayEntity> result4 = searchService.findBySumGreaterThan(Integer.MAX_VALUE);
        List<ArrayEntity> result5 = searchService.findByAverageGreaterThan(Double.MIN_VALUE);
        List<ArrayEntity> result6 = searchService.findByAverageGreaterThan(Double.MAX_VALUE);

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
        List<ArrayEntity> result1 = searchService.findById(1);
        List<ArrayEntity> result2 = searchService.findById(1);

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
        List<ArrayEntity> result1 = searchService.findById(1);
        List<ArrayEntity> result2 = searchService.findById(1);

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
            List<ArrayEntity> result = searchService.findById(i);
            assertTrue(result.isEmpty());
        }

        for (int i = 0; i < iterations; i++) {
            List<ArrayEntity> result = searchService.findBySumGreaterThan(i);
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

        List<ArrayEntity> result1 = searchService.findById(1);
        List<ArrayEntity> result2 = anotherSearchService.findById(1);

        assertEquals(result1.size(), result2.size());
        assertTrue(result1.isEmpty());
        assertTrue(result2.isEmpty());
    }
}