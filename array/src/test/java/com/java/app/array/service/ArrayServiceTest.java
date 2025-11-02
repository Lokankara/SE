package com.java.app.array.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.comparator.ArrayComparator;
import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.integer.IntArrayStatistics;
import com.java.app.array.provider.ArrayServiceArgumentsProvider;
import com.java.app.array.specification.IdSpecification;
import com.java.app.array.specification.NameSpecification;
import com.java.app.array.specification.SumGreaterThanSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

class ArrayServiceTest {

    private ArrayService arrayService;

    @BeforeEach
    void setUp() {
        arrayService = new ArrayService();
    }

    @ParameterizedTest(name = "Search arrays by name finds correct array {0}")
    @ArgumentsSource(ArrayServiceArgumentsProvider.class)
    @DisplayName("Search arrays by name specification works correctly")
    void searchArraysByNameSpecificationWorksCorrectly(String name, Integer[] array) {
        arrayService.createArray(name, array);
        arrayService.createArray("OtherArray", new Integer[] {99, 99});

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification(name));

        assertEquals(1, found.size());
        assertEquals(name, found.getFirst().getName());
        assertArrayEquals(array, found.getFirst().getArray());
    }

    @Test
    @DisplayName("Array entity maintains correct properties after operations")
    void arrayEntityMaintainsCorrectPropertiesAfterOperations() {
        arrayService.createArray("PropertyTest", new Integer[] {10, 20, 30, 40});

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification("PropertyTest"));
        IntArrayEntity entity = found.getFirst();

        assertEquals("PropertyTest", entity.getName());
        assertEquals(4, entity.getLength());
        assertEquals(10, entity.getFirst());
        assertFalse(entity.isEmpty());

        arrayService.updateArrayElement(entity.getId(), 0, 100);

        List<IntArrayEntity> updated = arrayService.searchArrays(new NameSpecification("PropertyTest"));
        IntArrayEntity updatedEntity = updated.getFirst();

        assertEquals("PropertyTest", updatedEntity.getName());
        assertEquals(4, updatedEntity.getLength());
        assertEquals(100, updatedEntity.getFirst());
        assertFalse(updatedEntity.isEmpty());
    }

    @Test
    @DisplayName("Search and sort operations work together")
    void searchAndSortOperationsWorkTogether() {
        arrayService.createArray("Zebra", new Integer[] {3});
        arrayService.createArray("Alpha", new Integer[] {1});
        arrayService.createArray("Beta", new Integer[] {2});

        List<IntArrayEntity> allSorted = arrayService.sort(ArrayComparator.NAME);
        assertEquals(3, allSorted.size());
        assertEquals("Alpha", allSorted.getFirst().getName());

        List<IntArrayEntity> allSortedByFirst = arrayService.sort(ArrayComparator.FIRST);
        assertEquals(1, allSortedByFirst.getFirst().getFirst());
    }

    @Test
    @DisplayName("Repository operations maintain data integrity")
    void repositoryOperationsMaintainDataIntegrity() {
        arrayService.createArray("IntegrityTest1", new Integer[] {1, 2, 3});
        arrayService.createArray("IntegrityTest2", new Integer[] {4, 5, 6});

        List<IntArrayEntity> found1 = arrayService.searchArrays(new NameSpecification("IntegrityTest1"));
        List<IntArrayEntity> found2 = arrayService.searchArrays(new NameSpecification("IntegrityTest2"));

        assertFalse(found1.isEmpty());
        assertFalse(found2.isEmpty());
        assertNotEquals(found1.getFirst().getId(), found2.getFirst().getId());
        assertArrayEquals(new Integer[] {1, 2, 3}, found1.getFirst().getArray());
        assertArrayEquals(new Integer[] {4, 5, 6}, found2.getFirst().getArray());

        arrayService.deleteArray(found1.getFirst().getId());

        List<IntArrayEntity> afterDelete1 = arrayService.searchArrays(new NameSpecification("IntegrityTest1"));
        List<IntArrayEntity> afterDelete2 = arrayService.searchArrays(new NameSpecification("IntegrityTest2"));

        assertTrue(afterDelete1.isEmpty());
        assertFalse(afterDelete2.isEmpty());
    }

    @ParameterizedTest(name = "Create array {0} should store array correctly")
    @ArgumentsSource(ArrayServiceArgumentsProvider.class)
    @DisplayName("Create array stores array in repository")
    void createArrayStoresArrayInRepository(String name, Integer[] array) {
        arrayService.createArray(name, array);

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification(name));

        assertFalse(found.isEmpty());
        IntArrayEntity entity = found.getFirst();
        assertEquals(name, entity.getName());
        assertArrayEquals(array, entity.getArray());
    }

    @ParameterizedTest(name = "Create array {0} should register with statistics")
    @ArgumentsSource(ArrayServiceArgumentsProvider.class)
    @DisplayName("Create array registers with statistics system")
    void createArrayRegistersWithStatisticsSystem(String name, Integer[] array) {
        arrayService.createArray(name, array);

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification(name));
        IntArrayEntity entity = found.getFirst();

        IntArrayStatistics stats = arrayService.getArrayStatistics(entity.getId());

        if (array.length > 0) {
            assertNotNull(stats);
            assertEquals(array.length, stats.getCount());
        } else {
            if (stats != null) {
                assertEquals(0, stats.getCount());
            }
        }
    }

    @ParameterizedTest(name = "Update array {0} element should modify array")
    @ArgumentsSource(ArrayServiceArgumentsProvider.class)
    @DisplayName("Update array element modifies array correctly")
    void updateArrayElementModifiesArrayCorrectly(String name, Integer[] array) {
        arrayService.createArray(name, array);

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification(name));
        IntArrayEntity entity = found.getFirst();

        if (array.length > 0) {
            int newValue = 1000;
            arrayService.updateArrayElement(entity.getId(), 0, newValue);

            List<IntArrayEntity> updatedFound = arrayService.searchArrays(new NameSpecification(name));
            IntArrayEntity updatedEntity = updatedFound.getFirst();
            assertEquals(newValue, updatedEntity.getArray()[0]);
        }
    }

    @ParameterizedTest(name = "Update array {0} element should update statistics")
    @ArgumentsSource(ArrayServiceArgumentsProvider.class)
    @DisplayName("Update array element updates statistics")
    void updateArrayElementUpdatesStatistics(String name, Integer[] array) {
        arrayService.createArray(name, array);

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification(name));
        IntArrayEntity entity = found.getFirst();

        IntArrayStatistics originalStats = arrayService.getArrayStatistics(entity.getId());

        if (array.length > 0) {
            int newValue = 1000;
            arrayService.updateArrayElement(entity.getId(), 0, newValue);

            IntArrayStatistics updatedStats = arrayService.getArrayStatistics(entity.getId());

            if (originalStats != null && updatedStats != null) {
                assertNotEquals(originalStats.getSum(), updatedStats.getSum());
            }
        }
    }

    @ParameterizedTest(name = "Delete array {0} should remove from repository")
    @ArgumentsSource(ArrayServiceArgumentsProvider.class)
    @DisplayName("Delete array removes from repository")
    void deleteArrayRemovesFromRepository(String name, Integer[] array) {
        arrayService.createArray(name, array);

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification(name));
        IntArrayEntity entity = found.getFirst();
        int entityId = entity.getId();

        arrayService.deleteArray(entityId);

        List<IntArrayEntity> afterDelete = arrayService.searchArrays(new IdSpecification(entityId));
        assertTrue(afterDelete.isEmpty());
    }

    @Test
    @DisplayName("Create multiple arrays stores all correctly")
    void createMultipleArraysStoresAllCorrectly() {
        arrayService.createArray("Array1", new Integer[] {1, 2, 3});
        arrayService.createArray("Array2", new Integer[] {4, 5, 6});
        arrayService.createArray("Array3", new Integer[] {7, 8, 9});

        List<IntArrayEntity> array1 = arrayService.searchArrays(new NameSpecification("Array1"));
        List<IntArrayEntity> array2 = arrayService.searchArrays(new NameSpecification("Array2"));
        List<IntArrayEntity> array3 = arrayService.searchArrays(new NameSpecification("Array3"));

        assertFalse(array1.isEmpty());
        assertFalse(array2.isEmpty());
        assertFalse(array3.isEmpty());
        assertEquals("Array1", array1.getFirst().getName());
        assertEquals("Array2", array2.getFirst().getName());
        assertEquals("Array3", array3.getFirst().getName());
    }

    @Test
    @DisplayName("Search arrays by sum threshold works correctly")
    void searchArraysBySumThresholdWorksCorrectly() {
        arrayService.createArray("SmallSum", new Integer[] {1, 1, 1});
        arrayService.createArray("LargeSum", new Integer[] {10, 20, 30});

        List<IntArrayEntity> highSumArrays = arrayService.searchArrays(new SumGreaterThanSpecification(10));

        assertEquals(1, highSumArrays.size());
        assertEquals("LargeSum", highSumArrays.getFirst().getName());
    }

    @Test
    @DisplayName("Sort arrays by different comparators works correctly")
    void sortArraysByDifferentComparatorsWorksCorrectly() {
        arrayService.createArray("Charlie", new Integer[] {5, 10});
        arrayService.createArray("Alice", new Integer[] {2, 20});
        arrayService.createArray("Bob", new Integer[] {8, 15});

        List<IntArrayEntity> sortedByName = arrayService.sort(ArrayComparator.NAME);
        assertEquals("Alice", sortedByName.getFirst().getName());

        List<IntArrayEntity> sortedByFirst = arrayService.sort(ArrayComparator.FIRST);
        assertEquals(2, sortedByFirst.getFirst().getFirst());
    }

    @Test
    @DisplayName("Update multiple array elements modifies correctly")
    void updateMultipleArrayElementsModifiesCorrectly() {
        arrayService.createArray("MultiUpdate", new Integer[] {1, 1, 1, 1, 1});

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification("MultiUpdate"));
        IntArrayEntity entity = found.getFirst();

        arrayService.updateArrayElement(entity.getId(), 0, 10);
        arrayService.updateArrayElement(entity.getId(), 1, 20);
        arrayService.updateArrayElement(entity.getId(), 2, 30);

        List<IntArrayEntity> updatedFound = arrayService.searchArrays(new NameSpecification("MultiUpdate"));
        IntArrayEntity updatedEntity = updatedFound.getFirst();
        Integer[] expectedArray = {10, 20, 30, 1, 1};
        assertArrayEquals(expectedArray, updatedEntity.getArray());
    }

    @Test
    @DisplayName("Update non-existent array element does nothing")
    void updateNonExistentArrayElementDoesNothing() {
        arrayService.createArray("TestArray", new Integer[] {1, 2, 3});

        assertDoesNotThrow(() -> arrayService.updateArrayElement(99999, 0, 100));

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification("TestArray"));
        IntArrayEntity entity = found.getFirst();
        assertArrayEquals(new Integer[] {1, 2, 3}, entity.getArray());
    }

    @Test
    @DisplayName("Delete non-existent array does nothing")
    void deleteNonExistentArrayDoesNothing() {
        arrayService.createArray("TestArray", new Integer[] {1, 2, 3});

        assertDoesNotThrow(() -> arrayService.deleteArray(99999));

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification("TestArray"));
        assertFalse(found.isEmpty());
    }

    @Test
    @DisplayName("Get statistics for non-existent array returns null")
    void getStatisticsForNonExistentArrayReturnsNull() {
        IntArrayStatistics stats = arrayService.getArrayStatistics(99999);
        assertNull(stats);
    }

    @Test
    @DisplayName("Empty service operations return empty results")
    void emptyServiceOperationsReturnEmptyResults() {
        List<IntArrayEntity> sortedById = arrayService.sort(ArrayComparator.ID);
        List<IntArrayEntity> sortedByName = arrayService.sort(ArrayComparator.NAME);
        List<IntArrayEntity> searchResults = arrayService.searchArrays(new NameSpecification("NonExistent"));

        assertTrue(sortedById.isEmpty());
        assertTrue(sortedByName.isEmpty());
        assertTrue(searchResults.isEmpty());
    }

    @Test
    @DisplayName("Observer pattern updates statistics on element change")
    void observerPatternUpdatesStatisticsOnElementChange() {
        arrayService.createArray("ObserverTest", new Integer[] {5, 3, 8, 1, 9});

        List<IntArrayEntity> found = arrayService.searchArrays(new NameSpecification("ObserverTest"));
        IntArrayEntity entity = found.getFirst();

        IntArrayStatistics stats1 = arrayService.getArrayStatistics(entity.getId());

        arrayService.updateArrayElement(entity.getId(), 0, 50);
        IntArrayStatistics stats2 = arrayService.getArrayStatistics(entity.getId());

        if (stats1 != null && stats2 != null) {
            assertNotEquals(stats1.getSum(), stats2.getSum());
            assertTrue(stats2.getSum() > stats1.getSum());
        }
    }
}
