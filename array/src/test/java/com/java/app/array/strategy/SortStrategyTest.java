package com.java.app.array.strategy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.factory.ArrayFactory;
import com.java.app.array.provider.SortStrategyArgumentsProvider;
import com.java.app.array.provider.SortTestDataProvider;
import com.java.app.array.service.ArraySortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

class SortStrategyTest {

    private ArraySortService sortService;
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        sortService = new ArraySortService();
        factory = new ArrayFactory();
    }

    @ParameterizedTest(name = "{0} should sort array correctly")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Each sort strategy produces correct results")
    void eachSortStrategyProducesCorrectResults(SortStrategy strategy) {
        ArrayEntity unsorted = factory.createArray(new int[] {5, 2, 8, 1, 9});
        List<ArrayEntity> arrays = List.of(unsorted);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertNotNull(sorted);
        assertEquals(1, sorted.size());
        ArrayEntity sortedEntity = sorted.getFirst();
        int[] expectedSorted = {1, 2, 5, 8, 9};
        assertArrayEquals(expectedSorted, sortedEntity.getArray());
    }

    @ParameterizedTest(name = "{0} with test data {1}")
    @ArgumentsSource(SortTestDataProvider.class)
    @DisplayName("All strategies produce consistent results for various data")
    void allStrategiesProduceConsistentResultsForVariousData(String testName, int[] originalData,
            int[] expectedSorted) {
        ArrayEntity testArray = factory.createArray(originalData.clone());
        List<ArrayEntity> arrays = List.of(testArray);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

            assertNotNull(sorted);
            assertEquals(1, sorted.size());
            ArrayEntity sortedEntity = sorted.getFirst();
            assertArrayEquals(expectedSorted, sortedEntity.getArray(),
                    "Strategy " + strategy + " failed for " + testName);
        }
    }

    @ParameterizedTest(name = "{0} should preserve original arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies preserve original arrays")
    void sortStrategiesPreserveOriginalArrays(SortStrategy strategy) {
        int[] originalData = {9, 2, 7, 4, 1, 8};
        ArrayEntity original = factory.createArray(originalData.clone());
        List<ArrayEntity> arrays = List.of(original);

        sortService.sort(arrays, strategy);

        assertArrayEquals(originalData, original.getArray());
    }

    @ParameterizedTest(name = "{0} should create new ArrayEntity instances")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies create new ArrayEntity instances")
    void sortStrategiesCreateNewArrayEntityInstances(SortStrategy strategy) {
        ArrayEntity original = factory.createArray(new int[] {3, 1, 4, 1, 5});
        List<ArrayEntity> arrays = List.of(original);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        ArrayEntity sortedEntity = sorted.getFirst();
        assertNotSame(original, sortedEntity);
        assertNotEquals(original.getId(), sortedEntity.getId());
    }

    @ParameterizedTest(name = "{0} should handle empty arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle empty arrays")
    void sortStrategiesHandleEmptyArrays(SortStrategy strategy) {
        ArrayEntity emptyArray = factory.createArray(new int[] {});
        List<ArrayEntity> arrays = List.of(emptyArray);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertNotNull(sorted);
        assertEquals(1, sorted.size());
        assertEquals(0, sorted.getFirst().getLength());
        assertTrue(sorted.getFirst().isEmpty());
    }

    @ParameterizedTest(name = "{0} should handle single element arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle single element arrays")
    void sortStrategiesHandleSingleElementArrays(SortStrategy strategy) {
        ArrayEntity singleElement = factory.createArray(new int[] {42});
        List<ArrayEntity> arrays = List.of(singleElement);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        ArrayEntity sortedEntity = sorted.getFirst();
        assertEquals(1, sortedEntity.getLength());
        assertEquals(42, sortedEntity.getFirst());
        assertArrayEquals(new int[] {42}, sortedEntity.getArray());
    }

    @ParameterizedTest(name = "{0} should handle already sorted arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle already sorted arrays")
    void sortStrategiesHandleAlreadySortedArrays(SortStrategy strategy) {
        ArrayEntity alreadySorted = factory.createArray(new int[] {1, 2, 3, 4, 5});
        List<ArrayEntity> arrays = List.of(alreadySorted);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, sorted.getFirst().getArray());
    }

    @ParameterizedTest(name = "{0} should handle reverse sorted arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle reverse sorted arrays")
    void sortStrategiesHandleReverseSortedArrays(SortStrategy strategy) {
        ArrayEntity reverseSorted = factory.createArray(new int[] {5, 4, 3, 2, 1});
        List<ArrayEntity> arrays = List.of(reverseSorted);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, sorted.getFirst().getArray());
    }

    @ParameterizedTest(name = "{0} should handle arrays with negative numbers")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle arrays with negative numbers")
    void sortStrategiesHandleArraysWithNegativeNumbers(SortStrategy strategy) {
        ArrayEntity withNegatives = factory.createArray(new int[] {-5, 3, -1, 0, 2, -3});
        List<ArrayEntity> arrays = List.of(withNegatives);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        int[] expectedSorted = {-5, -3, -1, 0, 2, 3};
        assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
    }

    @ParameterizedTest(name = "{0} should handle arrays with extreme values")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle arrays with extreme values")
    void sortStrategiesHandleArraysWithExtremeValues(SortStrategy strategy) {
        ArrayEntity extremeValues = factory.createArray(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0});
        List<ArrayEntity> arrays = List.of(extremeValues);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        int[] expectedSorted = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
    }

    @Test
    @DisplayName("All strategies produce identical results for same input")
    void allStrategiesProduceIdenticalResultsForSameInput() {
        int[] testData = {15, 3, 9, 1, 12, 6};
        ArrayEntity testArray = factory.createArray(testData.clone());
        List<ArrayEntity> arrays = List.of(testArray);

        List<ArrayEntity> mergeResult = sortService.sort(arrays, SortStrategy.MERGE);
        List<ArrayEntity> heapResult = sortService.sort(arrays, SortStrategy.HEAP);
        List<ArrayEntity> insertionResult = sortService.sort(arrays, SortStrategy.INSERTION);
        List<ArrayEntity> selectionResult = sortService.sort(arrays, SortStrategy.SELECTION);
        List<ArrayEntity> bubbleResult = sortService.sort(arrays, SortStrategy.BUBBLE);

        int[] mergeArray = mergeResult.getFirst().getArray();
        assertArrayEquals(mergeArray, heapResult.getFirst().getArray());
        assertArrayEquals(mergeArray, insertionResult.getFirst().getArray());
        assertArrayEquals(mergeArray, selectionResult.getFirst().getArray());
        assertArrayEquals(mergeArray, bubbleResult.getFirst().getArray());
    }

    @Test
    @DisplayName("Sort service handles null and empty lists")
    void sortServiceHandlesNullAndEmptyLists() {
        for (SortStrategy strategy : SortStrategy.values()) {
            assertNull(sortService.sort(null, strategy));
            List<ArrayEntity> emptyResult = sortService.sort(List.of(), strategy);
            assertNotNull(emptyResult);
            assertTrue(emptyResult.isEmpty());
        }
    }

    @Test
    @DisplayName("Sort strategy enum has correct values")
    void sortStrategyEnumHasCorrectValues() {
        SortStrategy[] strategies = SortStrategy.values();
        assertEquals(5, strategies.length);

        assertEquals(SortStrategy.MERGE, strategies[0]);
        assertEquals(SortStrategy.HEAP, strategies[1]);
        assertEquals(SortStrategy.INSERTION, strategies[2]);
        assertEquals(SortStrategy.SELECTION, strategies[3]);
        assertEquals(SortStrategy.BUBBLE, strategies[4]);
    }

    @Test
    @DisplayName("Sort strategy getAlgorithm returns non-null algorithms")
    void sortStrategyGetAlgorithmReturnsNonNullAlgorithms() {
        for (SortStrategy strategy : SortStrategy.values()) {
            assertNotNull(strategy.getAlgorithm());
        }
    }

    @Test
    @DisplayName("Sequential arrays work with different strategies")
    void sequentialArraysWorkWithDifferentStrategies() {
        ArrayEntity sequential = factory.createSequentialArray("SeqTest", 5, 10);
        List<ArrayEntity> arrays = List.of(sequential);
        int[] expectedSorted = {5, 6, 7, 8, 9, 10};

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
            assertEquals(1, sorted.size());
            assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
        }
    }

    @Test
    @DisplayName("Pattern arrays work with different strategies")
    void patternArraysWorkWithDifferentStrategies() {
        ArrayEntity pattern = factory.createArrayWithPattern("PatternTest", 5, 10, -2);
        List<ArrayEntity> arrays = List.of(pattern);
        int[] expectedSorted = {2, 4, 6, 8, 10};

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
            assertEquals(1, sorted.size());
            assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
        }
    }

    @Test
    @DisplayName("Range arrays work with different strategies")
    void rangeArraysWorkWithDifferentStrategies() {
        ArrayEntity range = factory.createArrayFromRange("RangeTest", 20, 10, -3);
        List<ArrayEntity> arrays = List.of(range);
        int[] expectedSorted = {11, 14, 17, 20};

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
            assertEquals(1, sorted.size());
            assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
        }
    }

    @Test
    @DisplayName("Large arrays are sorted correctly")
    void largeArraysAreSortedCorrectly() {
        ArrayEntity largeArray = factory.createRandomArray("LargeTest", 100, 1, 1000);
        List<ArrayEntity> arrays = List.of(largeArray);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

            assertEquals(1, sorted.size());
            ArrayEntity sortedEntity = sorted.getFirst();
            assertEquals(100, sortedEntity.getLength());
            assertTrue(isSorted(sortedEntity.getArray()));
        }
    }

    private boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
}