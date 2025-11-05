package com.pp.app.array.strategy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.factory.IntArrayFactory;
import com.pp.app.array.provider.SortStrategyArgumentsProvider;
import com.pp.app.array.provider.SortTestDataProvider;
import com.pp.app.array.service.ArraySortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;

class SortStrategyTest {

    private ArraySortService sortService;
    private IntArrayFactory factory;

    @BeforeEach
    void setUp() {
        sortService = new ArraySortService();
        factory = new IntArrayFactory();
    }

    @ParameterizedTest(name = "{0} should sort array correctly")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Each sort strategy produces correct results")
    void eachSortStrategyProducesCorrectResults(SortStrategy strategy) {
        IntArrayEntity unsorted = factory.createArray(new Integer[] {5, 2, 8, 1, 9});
        List<IntArrayEntity> arrays = List.of(unsorted);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertNotNull(sorted);
        assertEquals(1, sorted.size());
        IntArrayEntity sortedEntity = sorted.getFirst();
        Integer[] expectedSorted = {1, 2, 5, 8, 9};
        assertArrayEquals(expectedSorted, sortedEntity.getArray());
    }

    @ParameterizedTest(name = "{0} with test data {1}")
    @ArgumentsSource(SortTestDataProvider.class)
    @DisplayName("All strategies produce consistent results for various data")
    void allStrategiesProduceConsistentResultsForVariousData(String testName, Integer[] originalData,
            Integer[] expectedSorted) {
        IntArrayEntity testArray = factory.createArray(originalData.clone());
        List<IntArrayEntity> arrays = List.of(testArray);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

            assertNotNull(sorted);
            assertEquals(1, sorted.size());
            IntArrayEntity sortedEntity = sorted.getFirst();
            assertArrayEquals(expectedSorted, sortedEntity.getArray(),
                    "Strategy " + strategy + " failed for " + testName);
        }
    }

    @ParameterizedTest(name = "{0} should preserve original arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies preserve original arrays")
    void sortStrategiesPreserveOriginalArrays(SortStrategy strategy) {
        Integer[] originalData = {9, 2, 7, 4, 1, 8};
        IntArrayEntity original = factory.createArray(originalData.clone());
        List<IntArrayEntity> arrays = List.of(original);

        sortService.sort(arrays, strategy);

        assertArrayEquals(originalData, original.getArray());
    }

    @ParameterizedTest(name = "{0} should create new IntArrayEntity instances")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies create new IntArrayEntity instances")
    void sortStrategiesCreateNewIntArrayEntityInstances(SortStrategy strategy) {
        IntArrayEntity original = factory.createArray(new Integer[] {3, 1, 4, 1, 5});
        List<IntArrayEntity> arrays = List.of(original);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        IntArrayEntity sortedEntity = sorted.getFirst();
        assertNotSame(original, sortedEntity);
        assertNotEquals(original.getId(), sortedEntity.getId());
    }

    @ParameterizedTest(name = "{0} should handle empty arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle empty arrays")
    void sortStrategiesHandleEmptyArrays(SortStrategy strategy) {
        IntArrayEntity emptyArray = factory.createArray(new Integer[] {});
        List<IntArrayEntity> arrays = List.of(emptyArray);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertNotNull(sorted);
        assertEquals(1, sorted.size());
        assertEquals(0, sorted.getFirst().getLength());
        assertTrue(sorted.getFirst().isEmpty());
    }

    @ParameterizedTest(name = "{0} should handle single element arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle single element arrays")
    void sortStrategiesHandleSingleElementArrays(SortStrategy strategy) {
        IntArrayEntity singleElement = factory.createArray(new Integer[] {42});
        List<IntArrayEntity> arrays = List.of(singleElement);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        IntArrayEntity sortedEntity = sorted.getFirst();
        assertEquals(1, sortedEntity.getLength());
        assertEquals(42, sortedEntity.getFirst());
        assertArrayEquals(new Integer[] {42}, sortedEntity.getArray());
    }

    @ParameterizedTest(name = "{0} should handle already sorted arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle already sorted arrays")
    void sortStrategiesHandleAlreadySortedArrays(SortStrategy strategy) {
        IntArrayEntity alreadySorted = factory.createArray(new Integer[] {1, 2, 3, 4, 5});
        List<IntArrayEntity> arrays = List.of(alreadySorted);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, sorted.getFirst().getArray());
    }

    @ParameterizedTest(name = "{0} should handle reverse sorted arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle reverse sorted arrays")
    void sortStrategiesHandleReverseSortedArrays(SortStrategy strategy) {
        IntArrayEntity reverseSorted = factory.createArray(new Integer[] {5, 4, 3, 2, 1});
        List<IntArrayEntity> arrays = List.of(reverseSorted);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, sorted.getFirst().getArray());
    }

    @ParameterizedTest(name = "{0} should handle arrays with negative numbers")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle arrays with negative numbers")
    void sortStrategiesHandleArraysWithNegativeNumbers(SortStrategy strategy) {
        IntArrayEntity withNegatives = factory.createArray(new Integer[] {-5, 3, -1, 0, 2, -3});
        List<IntArrayEntity> arrays = List.of(withNegatives);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        Integer[] expectedSorted = {-5, -3, -1, 0, 2, 3};
        assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
    }

    @ParameterizedTest(name = "{0} should handle arrays with extreme values")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort strategies handle arrays with extreme values")
    void sortStrategiesHandleArraysWithExtremeValues(SortStrategy strategy) {
        IntArrayEntity extremeValues = factory.createArray(new Integer[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0});
        List<IntArrayEntity> arrays = List.of(extremeValues);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(1, sorted.size());
        Integer[] expectedSorted = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
    }

    @Test
    @DisplayName("All strategies produce identical results for same input")
    void allStrategiesProduceIdenticalResultsForSameInput() {
        Integer[] testData = {15, 3, 9, 1, 12, 6};
        IntArrayEntity testArray = factory.createArray(testData.clone());
        List<IntArrayEntity> arrays = List.of(testArray);

        List<IntArrayEntity> mergeResult = sortService.sort(arrays, SortStrategy.MERGE);
        List<IntArrayEntity> heapResult = sortService.sort(arrays, SortStrategy.HEAP);
        List<IntArrayEntity> insertionResult = sortService.sort(arrays, SortStrategy.INSERTION);
        List<IntArrayEntity> selectionResult = sortService.sort(arrays, SortStrategy.SELECTION);
        List<IntArrayEntity> bubbleResult = sortService.sort(arrays, SortStrategy.BUBBLE);

        Integer[] mergeArray = mergeResult.getFirst().getArray();
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
            List<IntArrayEntity> emptyResult = sortService.sort(List.of(), strategy);
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
        IntArrayEntity sequential = factory.createSequentialArray("SeqTest", 5, 10);
        List<IntArrayEntity> arrays = List.of(sequential);
        Integer[] expectedSorted = {5, 6, 7, 8, 9, 10};

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
            assertEquals(1, sorted.size());
            assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
        }
    }

    @Test
    @DisplayName("Pattern arrays work with different strategies")
    void patternArraysWorkWithDifferentStrategies() {
        IntArrayEntity pattern = factory.createArrayWithPattern("PatternTest", 5, 10, -2);
        List<IntArrayEntity> arrays = List.of(pattern);
        Integer[] expectedSorted = {2, 4, 6, 8, 10};

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
            assertEquals(1, sorted.size());
            assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
        }
    }

    @Test
    @DisplayName("Range arrays work with different strategies")
    void rangeArraysWorkWithDifferentStrategies() {
        IntArrayEntity range = factory.createArrayFromRange("RangeTest", 20, 10, -3);
        List<IntArrayEntity> arrays = List.of(range);
        Integer[] expectedSorted = {11, 14, 17, 20};

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
            assertEquals(1, sorted.size());
            assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
        }
    }

    @Test
    @DisplayName("Large arrays are sorted correctly")
    void largeArraysAreSortedCorrectly() {
        IntArrayEntity largeArray = factory.createRandomArray("LargeTest", 100, 1, 1000);
        List<IntArrayEntity> arrays = List.of(largeArray);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

            assertEquals(1, sorted.size());
            IntArrayEntity sortedEntity = sorted.getFirst();
            assertEquals(100, sortedEntity.getLength());
            assertTrue(isSorted(sortedEntity.getArray()));
        }
    }

    private boolean isSorted(Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
}