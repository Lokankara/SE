package com.java.app.array.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.comparator.ArrayComparators;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.ArrayStatistics;
import com.java.app.array.specification.IdSpecification;
import com.java.app.array.specification.NameSpecification;
import com.java.app.array.specification.SumGreaterThanSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ArrayServiceTest {

    private ArrayService arrayService;

    @BeforeEach
    void setUp() {
        arrayService = new ArrayService();
    }

    @Test
    void testCreateArray() {
        arrayService.createArray(1, "TestArray", new int[] {1, 2, 3, 4, 5});

        List<ArrayEntity> found = arrayService.searchArrays(new IdSpecification(1));

        assertEquals(1, found.size());
        ArrayEntity entity = found.getFirst();
        assertEquals(1, entity.getId());
        assertEquals("TestArray", entity.getName());
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, entity.getArray());
    }

    @Test
    void testCreateMultipleArrays() {
        arrayService.createArray(1, "Array1", new int[] {1, 2, 3});
        arrayService.createArray(2, "Array2", new int[] {4, 5, 6});
        arrayService.createArray(3, "Array3", new int[] {7, 8, 9});

        List<ArrayEntity> found1 = arrayService.searchArrays(new IdSpecification(1));
        List<ArrayEntity> found2 = arrayService.searchArrays(new IdSpecification(2));
        List<ArrayEntity> found3 = arrayService.searchArrays(new IdSpecification(3));

        assertEquals(1, found1.size());
        assertEquals(1, found2.size());
        assertEquals(1, found3.size());
        assertEquals("Array1", found1.getFirst().getName());
        assertEquals("Array2", found2.getFirst().getName());
        assertEquals("Array3", found3.getFirst().getName());
    }

    @Test
    void testDeleteArray() {
        arrayService.createArray(1, "ToDelete", new int[] {1, 2, 3});

        List<ArrayEntity> beforeDelete = arrayService.searchArrays(new IdSpecification(1));
        assertEquals(1, beforeDelete.size());

        arrayService.deleteArray(1);

        List<ArrayEntity> afterDelete = arrayService.searchArrays(new IdSpecification(1));
        assertEquals(0, afterDelete.size());
    }

    @Test
    void testDeleteNonExistentArray() {
        arrayService.createArray(1, "Existing", new int[] {1, 2, 3});

        arrayService.deleteArray(99999);

        List<ArrayEntity> existing = arrayService.searchArrays(new IdSpecification(1));
        assertEquals(1, existing.size());
    }

    @Test
    void testSearchArraysById() {
        arrayService.createArray(1, "Array1", new int[] {1, 2, 3});
        arrayService.createArray(2, "Array2", new int[] {4, 5, 6});

        List<ArrayEntity> found = arrayService.searchArrays(new IdSpecification(1));

        assertEquals(1, found.size());
        assertEquals(1, found.getFirst().getId());
        assertEquals("Array1", found.getFirst().getName());
    }

    @Test
    void testSearchArraysByName() {
        arrayService.createArray(1, "SearchMe", new int[] {1, 2, 3});
        arrayService.createArray(2, "DontFind", new int[] {4, 5, 6});

        List<ArrayEntity> found = arrayService.searchArrays(new NameSpecification("SearchMe"));

        assertEquals(1, found.size());
        assertEquals("SearchMe", found.getFirst().getName());
    }

    @Test
    void testSearchArraysBySum() {
        arrayService.createArray(1, "SmallSum", new int[] {1, 1, 1});
        arrayService.createArray(2, "LargeSum", new int[] {10, 20, 30});

        List<ArrayEntity> found = arrayService.searchArrays(new SumGreaterThanSpecification(10));

        assertEquals(1, found.size());
        assertEquals("LargeSum", found.getFirst().getName());
    }

    @Test
    void testSortById() {
        arrayService.createArray(3, "Third", new int[] {7, 8, 9});
        arrayService.createArray(1, "First", new int[] {1, 2, 3});
        arrayService.createArray(2, "Second", new int[] {4, 5, 6});

        List<ArrayEntity> sorted = arrayService.sortById();

        assertEquals(3, sorted.size());
        assertEquals(1, sorted.getFirst().getId());
        assertEquals(2, sorted.get(1).getId());
        assertEquals(3, sorted.get(2).getId());
    }

    @Test
    void testSortByName() {
        arrayService.createArray(1, "Charlie", new int[] {1, 2, 3});
        arrayService.createArray(2, "Alice", new int[] {4, 5, 6});
        arrayService.createArray(3, "Bob", new int[] {7, 8, 9});

        List<ArrayEntity> sorted = arrayService.sortByName();

        assertEquals(3, sorted.size());
        assertEquals("Alice", sorted.getFirst().getName());
        assertEquals("Bob", sorted.get(1).getName());
        assertEquals("Charlie", sorted.get(2).getName());
    }

    @Test
    void testSortByFirstElement() {
        arrayService.createArray(1, "Array1", new int[] {5, 10, 15});
        arrayService.createArray(2, "Array2", new int[] {2, 20, 30});
        arrayService.createArray(3, "Array3", new int[] {8, 40, 50});

        List<ArrayEntity> sorted = arrayService.sortByFirstElement();

        assertEquals(3, sorted.size());
        assertEquals(2, sorted.getFirst().getFirst());
        assertEquals(5, sorted.get(1).getFirst());
        assertEquals(8, sorted.get(2).getFirst());
    }

    @Test
    void testSortByLength() {
        arrayService.createArray(1, "Short", new int[] {1});
        arrayService.createArray(2, "Long", new int[] {1, 2, 3, 4, 5});
        arrayService.createArray(3, "Medium", new int[] {1, 2, 3});

        List<ArrayEntity> sorted = arrayService.sortByLength();

        assertEquals(3, sorted.size());
        assertEquals(1, sorted.getFirst().getLength());
        assertEquals(3, sorted.get(1).getLength());
        assertEquals(5, sorted.get(2).getLength());
    }

    @Test
    void testSortArraysWithCustomComparator() {
        arrayService.createArray(1, "Array1", new int[] {1, 2, 3});
        arrayService.createArray(2, "Array2", new int[] {4, 5, 6});

        List<ArrayEntity> sorted = arrayService.sortArrays(ArrayComparators.BY_NAME.reversed());

        assertEquals(2, sorted.size());
        assertEquals("Array2", sorted.getFirst().getName());
        assertEquals("Array1", sorted.get(1).getName());
    }

    @Test
    void testGetArrayStatistics() {
        arrayService.createArray(1, "StatsTest", new int[] {2, 4, 6, 8, 10});

        ArrayStatistics stats = arrayService.getArrayStatistics(1);

        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertEquals(30, stats.getSum());
        assertEquals(6.0, stats.getAverage());
        assertEquals(10, stats.getMax());
        assertEquals(2, stats.getMin());
    }

    @Test
    void testGetNonExistentArrayStatistics() {
        ArrayStatistics stats = arrayService.getArrayStatistics(99999);

        assertNull(stats);
    }

    @Test
    void testUpdateArrayElement() {
        arrayService.createArray(1, "UpdateTest", new int[] {1, 2, 3, 4, 5});

        ArrayStatistics initialStats = arrayService.getArrayStatistics(1);
        assertEquals(15, initialStats.getSum());

        arrayService.updateArrayElement(1, 0, 100);

        ArrayStatistics updatedStats = arrayService.getArrayStatistics(1);
        assertEquals(114, updatedStats.getSum());

        List<ArrayEntity> found = arrayService.searchArrays(new IdSpecification(1));
        assertEquals(100, found.getFirst().getArray()[0]);
    }

    @Test
    void testUpdateNonExistentArrayElement() {
        arrayService.createArray(1, "Test", new int[] {1, 2, 3});

        ArrayStatistics initialStats = arrayService.getArrayStatistics(1);
        int initialSum = initialStats.getSum();

        arrayService.updateArrayElement(99999, 0, 100);

        ArrayStatistics unchangedStats = arrayService.getArrayStatistics(1);
        assertEquals(initialSum, unchangedStats.getSum());
    }

    @Test
    void testUpdateArrayElementMultipleTimes() {
        arrayService.createArray(1, "MultiUpdate", new int[] {1, 1, 1, 1, 1});

        arrayService.updateArrayElement(1, 0, 10);
        arrayService.updateArrayElement(1, 1, 20);
        arrayService.updateArrayElement(1, 2, 30);

        ArrayStatistics finalStats = arrayService.getArrayStatistics(1);
        assertEquals(63, finalStats.getSum());

        List<ArrayEntity> found = arrayService.searchArrays(new IdSpecification(1));
        int[] expectedArray = {10, 20, 30, 1, 1};
        assertArrayEquals(expectedArray, found.getFirst().getArray());
    }

    @Test
    void testServiceIntegrationFlow() {
        arrayService.createArray(1, "Integration", new int[] {5, 3, 8, 1, 9});

        List<ArrayEntity> found = arrayService.searchArrays(new IdSpecification(1));
        assertEquals(1, found.size());

        ArrayStatistics initialStats = arrayService.getArrayStatistics(1);
        assertEquals(26, initialStats.getSum());

        arrayService.updateArrayElement(1, 0, 50);

        ArrayStatistics updatedStats = arrayService.getArrayStatistics(1);
        assertEquals(71, updatedStats.getSum());

        List<ArrayEntity> sortedByFirst = arrayService.sortByFirstElement();
        assertEquals(50, sortedByFirst.getFirst().getFirst());
    }

    @Test
    void testEmptyServiceOperations() {
        List<ArrayEntity> sortedById = arrayService.sortById();
        List<ArrayEntity> sortedByName = arrayService.sortByName();
        List<ArrayEntity> sortedByFirst = arrayService.sortByFirstElement();
        List<ArrayEntity> sortedByLength = arrayService.sortByLength();

        assertTrue(sortedById.isEmpty());
        assertTrue(sortedByName.isEmpty());
        assertTrue(sortedByFirst.isEmpty());
        assertTrue(sortedByLength.isEmpty());
    }

    @Test
    void testLargeDataSetOperations() {
        for (int i = 1; i <= 100; i++) {
            arrayService.createArray(i, "Array" + i, new int[] {i, i + 1, i + 2});
        }

        List<ArrayEntity> allSorted = arrayService.sortById();
        assertEquals(100, allSorted.size());

        for (int i = 0; i < 100; i++) {
            assertEquals(i + 1, allSorted.get(i).getId());
        }

        List<ArrayEntity> highSumArrays = arrayService.searchArrays(new SumGreaterThanSpecification(250));
        assertFalse(highSumArrays.isEmpty());
    }
}