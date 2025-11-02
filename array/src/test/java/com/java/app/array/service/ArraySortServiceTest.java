package com.java.app.array.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.factory.IntArrayFactory;
import com.java.app.array.provider.ArrayArgumentsProvider;
import com.java.app.array.provider.SortStrategyArgumentsProvider;
import com.java.app.array.strategy.SortStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class ArraySortServiceTest {

    private ArraySortService sortService;
    private IntArrayFactory factory;

    @BeforeEach
    void setUp() {
        sortService = new ArraySortService();
        factory = new IntArrayFactory();
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should preserve original state")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort preserves original array state")
    void sortPreservesOriginalArrayState(Integer[] originalData, Integer[] expectedSorted) {
        IntArrayEntity original = factory.createArray(originalData == null ? null : originalData.clone());
        List<IntArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> result = sortService.sort(originalList, strategy);
            IntArrayEntity firstResult = result.stream().findFirst().orElseThrow();
            assertArrayEquals(originalData == null ? new Integer[0] : originalData, original.getArray());
            assertArrayEquals(expectedSorted == null ? new Integer[0] : expectedSorted, firstResult.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should preserve array length")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort preserves array length")
    void sortPreservesArrayLength(Integer[] originalData, Integer[] expectedSorted) {
        IntArrayEntity original = factory.createArray(originalData == null ? null : originalData.clone());
        List<IntArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(originalList, strategy);
            IntArrayEntity firstSorted = sorted.stream().findFirst().orElseThrow();
            assertEquals(original.getLength(), firstSorted.getLength());
            assertArrayEquals(expectedSorted == null ? new Integer[0] : expectedSorted, firstSorted.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should create new IntArrayEntity instances")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort creates new IntArrayEntity instances")
    void sortCreatesNewIntArrayEntityInstances(Integer[] originalData, Integer[] expectedSorted) {
        IntArrayEntity original = factory.createArray(originalData == null ? null : originalData.clone());
        List<IntArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(originalList, strategy);
            assertEquals(1, sorted.size());
            IntArrayEntity sortedEntity = sorted.stream().findFirst().orElseThrow();
            assertNotSame(original, sortedEntity);
            assertNotEquals(original.getId(), sortedEntity.getId());
            assertArrayEquals(expectedSorted == null ? new Integer[0] : expectedSorted, sortedEntity.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle empty arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles empty arrays")
    void sortHandlesEmptyArrays(Integer[] originalData, Integer[] expectedSorted) {
        if (originalData == null || originalData.length == 0) {
            IntArrayEntity emptyArray = factory.createArray(originalData);
            List<IntArrayEntity> arrays = List.of(emptyArray);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertNotNull(sorted);
                assertEquals(1, sorted.size());
                IntArrayEntity first = sorted.stream().findFirst().orElseThrow();
                assertEquals(0, first.getLength());
                assertTrue(first.isEmpty());
                assertArrayEquals(expectedSorted == null ? new Integer[0] : expectedSorted, first.getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle single element arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles single element arrays")
    void sortHandlesSingleElementArrays(Integer[] originalData, Integer[] expectedSorted) {
        if (originalData != null && originalData.length == 1) {
            IntArrayEntity singleElement = factory.createArray(originalData);
            List<IntArrayEntity> arrays = List.of(singleElement);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                IntArrayEntity sortedEntity = sorted.stream().findFirst().orElseThrow();
                assertEquals(1, sortedEntity.getLength());
                assertEquals(originalData[0], sortedEntity.getFirst());
                assertArrayEquals(expectedSorted == null ? new Integer[] {originalData[0]} : expectedSorted,
                        sortedEntity.getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle already sorted arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles already sorted arrays")
    void sortHandlesAlreadySortedArrays(Integer[] originalData, Integer[] expectedSorted) {
        if (Arrays.equals(originalData, expectedSorted)) {
            IntArrayEntity alreadySorted = factory.createArray(originalData);
            List<IntArrayEntity> arrays = List.of(alreadySorted);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle reverse sorted arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles reverse sorted arrays")
    void sortHandlesReverseSortedArrays(Integer[] originalData, Integer[] expectedSorted) {
        if (Arrays.equals(originalData, Arrays.stream(expectedSorted).sorted().toArray())) {
            IntArrayEntity reverseSorted = factory.createArray(originalData);
            List<IntArrayEntity> arrays = List.of(reverseSorted);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle arrays with duplicates")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles arrays with duplicates")
    void sortHandlesArraysWithDuplicates(Integer[] originalData, Integer[] expectedSorted) {
        if (Arrays.stream(originalData).distinct().count() < originalData.length) {
            IntArrayEntity withDuplicates = factory.createArray(originalData);
            List<IntArrayEntity> arrays = List.of(withDuplicates);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle arrays with negative numbers")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles arrays with negative numbers")
    void sortHandlesArraysWithNegativeNumbers(Integer[] originalData, Integer[] expectedSorted) {
        if (Arrays.stream(originalData).anyMatch(n -> n < 0)) {
            IntArrayEntity withNegatives = factory.createArray(originalData);
            List<IntArrayEntity> arrays = List.of(withNegatives);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle arrays with extreme values")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles arrays with extreme values")
    void sortHandlesArraysWithExtremeValues(Integer[] originalData, Integer[] expectedSorted) {
        if (Arrays.stream(originalData).anyMatch(n -> n == Integer.MAX_VALUE || n == Integer.MIN_VALUE)) {
            IntArrayEntity extremeValues = factory.createArray(originalData);
            List<IntArrayEntity> arrays = List.of(extremeValues);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} should create new IntArrayEntity instances")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort returns new IntArrayEntity instances")
    void sortReturnsNewArrayEntities(SortStrategy strategy) {
        IntArrayEntity original = factory.createArray(new Integer[] {3, 1, 4, 1, 5});
        List<IntArrayEntity> originalList = List.of(original);

        List<IntArrayEntity> sorted = sortService.sort(originalList, strategy);

        assertNotNull(sorted);
        assertEquals(1, sorted.size());
        IntArrayEntity sortedEntity = sorted.getFirst();
        assertNotSame(original, sortedEntity);
        assertNotEquals(original.getId(), sortedEntity.getId());
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should sort correctly")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort produces correct results")
    void sortProducesCorrectResults(Integer[] originalData, Integer[] expectedSorted) {
        IntArrayEntity original = factory.createArray(originalData.clone());
        List<IntArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<IntArrayEntity> sorted = sortService.sort(originalList, strategy);

            assertNotNull(sorted);
            assertEquals(1, sorted.size());
            IntArrayEntity sortedEntity = sorted.getFirst();
            assertArrayEquals(expectedSorted, sortedEntity.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} should handle empty lists")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort handles empty lists")
    void sortHandlesEmptyLists(SortStrategy strategy) {
        List<IntArrayEntity> emptyList = List.of();

        List<IntArrayEntity> result = sortService.sort(emptyList, strategy);

        assertSame(emptyList, result);
    }

    @ParameterizedTest(name = "Sort strategy {0} should preserve array length")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort preserves array length")
    void sortPreservesArrayLength(SortStrategy strategy) {
        IntArrayEntity array1 = factory.createArray(new Integer[] {5, 2, 8});
        IntArrayEntity array2 = factory.createArray(new Integer[] {1, 9, 3, 7});
        List<IntArrayEntity> arrays = List.of(array1, array2);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(2, sorted.size());
        assertEquals(3, sorted.getFirst().getLength());
        assertEquals(4, sorted.stream().skip(1).findFirst().orElseThrow().getLength());
    }

    @ParameterizedTest(name = "Sort strategy {0} should maintain consistency across multiple arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort maintains consistency across multiple arrays")
    void sortMaintainsConsistencyAcrossMultipleArrays(SortStrategy strategy) {
        IntArrayEntity array1 = factory.createArray(new Integer[] {15, 3, 9, 1, 12});
        IntArrayEntity array2 = factory.createArray(new Integer[] {8, 2, 6, 4});
        List<IntArrayEntity> arrays = List.of(array1, array2);

        List<IntArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(2, sorted.size());
        assertTrue(isSorted(sorted.getFirst().getArray()));
        assertTrue(isSorted(sorted.stream().skip(1).findFirst().orElseThrow().getArray()));
    }

    @ParameterizedTest(name = "All strategies should produce identical results for array {0}")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("All strategies produce identical results")
    void allStrategiesProduceIdenticalResults(Integer[] originalData, Integer[] expectedSorted) {
        IntArrayEntity testArray = factory.createArray(originalData == null ? new Integer[0] : originalData.clone());
        List<IntArrayEntity> testList = List.of(testArray);

        List<IntArrayEntity> mergeResult = sortService.sort(testList, SortStrategy.MERGE);
        List<IntArrayEntity> heapResult = sortService.sort(testList, SortStrategy.HEAP);
        List<IntArrayEntity> insertionResult = sortService.sort(testList, SortStrategy.INSERTION);
        List<IntArrayEntity> selectionResult = sortService.sort(testList, SortStrategy.SELECTION);
        List<IntArrayEntity> bubbleResult = sortService.sort(testList, SortStrategy.BUBBLE);

        Integer[] mergeArr = mergeResult.stream().findFirst().orElseThrow().getArray();
        Integer[] heapArr = heapResult.stream().findFirst().orElseThrow().getArray();
        Integer[] insertionArr = insertionResult.stream().findFirst().orElseThrow().getArray();
        Integer[] selectionArr = selectionResult.stream().findFirst().orElseThrow().getArray();
        Integer[] bubbleArr = bubbleResult.stream().findFirst().orElseThrow().getArray();

        assertArrayEquals(mergeArr, heapArr);
        assertArrayEquals(heapArr, insertionArr);
        assertArrayEquals(insertionArr, selectionArr);
        assertArrayEquals(selectionArr, bubbleArr);

        if (expectedSorted != null) {
            assertArrayEquals(expectedSorted, mergeArr);
        }
    }

    private boolean isSorted(Integer[] array) {
        return IntStream.range(1, array.length).noneMatch(i -> array[i] < array[i - 1]);
    }
}
