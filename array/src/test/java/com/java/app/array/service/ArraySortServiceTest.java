package com.java.app.array.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.factory.ArrayFactory;
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
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        sortService = new ArraySortService();
        factory = new ArrayFactory();
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should preserve original state")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort preserves original array state")
    void sortPreservesOriginalArrayState(int[] originalData, int[] expectedSorted) {
        ArrayEntity original = factory.createArray(originalData == null ? null : originalData.clone());
        List<ArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> result = sortService.sort(originalList, strategy);
            ArrayEntity firstResult = result.stream().findFirst().orElseThrow();
            assertArrayEquals(originalData == null ? new int[0] : originalData, original.getArray());
            assertArrayEquals(expectedSorted == null ? new int[0] : expectedSorted, firstResult.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should preserve array length")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort preserves array length")
    void sortPreservesArrayLength(int[] originalData, int[] expectedSorted) {
        ArrayEntity original = factory.createArray(originalData == null ? null : originalData.clone());
        List<ArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(originalList, strategy);
            ArrayEntity firstSorted = sorted.stream().findFirst().orElseThrow();
            assertEquals(original.getLength(), firstSorted.getLength());
            assertArrayEquals(expectedSorted == null ? new int[0] : expectedSorted, firstSorted.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should create new ArrayEntity instances")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort creates new ArrayEntity instances")
    void sortCreatesNewArrayEntityInstances(int[] originalData, int[] expectedSorted) {
        ArrayEntity original = factory.createArray(originalData == null ? null : originalData.clone());
        List<ArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(originalList, strategy);
            assertEquals(1, sorted.size());
            ArrayEntity sortedEntity = sorted.stream().findFirst().orElseThrow();
            assertNotSame(original, sortedEntity);
            assertNotEquals(original.getId(), sortedEntity.getId());
            assertArrayEquals(expectedSorted == null ? new int[0] : expectedSorted, sortedEntity.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle empty arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles empty arrays")
    void sortHandlesEmptyArrays(int[] originalData, int[] expectedSorted) {
        if (originalData == null || originalData.length == 0) {
            ArrayEntity emptyArray = factory.createArray(originalData);
            List<ArrayEntity> arrays = List.of(emptyArray);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertNotNull(sorted);
                assertEquals(1, sorted.size());
                ArrayEntity first = sorted.stream().findFirst().orElseThrow();
                assertEquals(0, first.getLength());
                assertTrue(first.isEmpty());
                assertArrayEquals(expectedSorted == null ? new int[0] : expectedSorted, first.getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle single element arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles single element arrays")
    void sortHandlesSingleElementArrays(int[] originalData, int[] expectedSorted) {
        if (originalData != null && originalData.length == 1) {
            ArrayEntity singleElement = factory.createArray(originalData);
            List<ArrayEntity> arrays = List.of(singleElement);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                ArrayEntity sortedEntity = sorted.stream().findFirst().orElseThrow();
                assertEquals(1, sortedEntity.getLength());
                assertEquals(originalData[0], sortedEntity.getFirst());
                assertArrayEquals(expectedSorted == null ? new int[] {originalData[0]} : expectedSorted,
                        sortedEntity.getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle already sorted arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles already sorted arrays")
    void sortHandlesAlreadySortedArrays(int[] originalData, int[] expectedSorted) {
        if (Arrays.equals(originalData, expectedSorted)) {
            ArrayEntity alreadySorted = factory.createArray(originalData);
            List<ArrayEntity> arrays = List.of(alreadySorted);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle reverse sorted arrays")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles reverse sorted arrays")
    void sortHandlesReverseSortedArrays(int[] originalData, int[] expectedSorted) {
        if (Arrays.equals(originalData, Arrays.stream(expectedSorted).sorted().toArray())) {
            ArrayEntity reverseSorted = factory.createArray(originalData);
            List<ArrayEntity> arrays = List.of(reverseSorted);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle arrays with duplicates")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles arrays with duplicates")
    void sortHandlesArraysWithDuplicates(int[] originalData, int[] expectedSorted) {
        if (Arrays.stream(originalData).distinct().count() < originalData.length) {
            ArrayEntity withDuplicates = factory.createArray(originalData);
            List<ArrayEntity> arrays = List.of(withDuplicates);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle arrays with negative numbers")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles arrays with negative numbers")
    void sortHandlesArraysWithNegativeNumbers(int[] originalData, int[] expectedSorted) {
        if (Arrays.stream(originalData).anyMatch(n -> n < 0)) {
            ArrayEntity withNegatives = factory.createArray(originalData);
            List<ArrayEntity> arrays = List.of(withNegatives);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should handle arrays with extreme values")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort handles arrays with extreme values")
    void sortHandlesArraysWithExtremeValues(int[] originalData, int[] expectedSorted) {
        if (Arrays.stream(originalData).anyMatch(n -> n == Integer.MAX_VALUE || n == Integer.MIN_VALUE)) {
            ArrayEntity extremeValues = factory.createArray(originalData);
            List<ArrayEntity> arrays = List.of(extremeValues);

            for (SortStrategy strategy : SortStrategy.values()) {
                List<ArrayEntity> sorted = sortService.sort(arrays, strategy);
                assertEquals(1, sorted.size());
                assertArrayEquals(expectedSorted, sorted.getFirst().getArray());
            }
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} should create new ArrayEntity instances")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort returns new ArrayEntity instances")
    void sortReturnsNewArrayEntities(SortStrategy strategy) {
        ArrayEntity original = factory.createArray(new int[] {3, 1, 4, 1, 5});
        List<ArrayEntity> originalList = List.of(original);

        List<ArrayEntity> sorted = sortService.sort(originalList, strategy);

        assertNotNull(sorted);
        assertEquals(1, sorted.size());
        ArrayEntity sortedEntity = sorted.getFirst();
        assertNotSame(original, sortedEntity);
        assertNotEquals(original.getId(), sortedEntity.getId());
    }

    @ParameterizedTest(name = "Sort strategy {0} with array {1} should sort correctly")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("Sort produces correct results")
    void sortProducesCorrectResults(int[] originalData, int[] expectedSorted) {
        ArrayEntity original = factory.createArray(originalData.clone());
        List<ArrayEntity> originalList = List.of(original);

        for (SortStrategy strategy : SortStrategy.values()) {
            List<ArrayEntity> sorted = sortService.sort(originalList, strategy);

            assertNotNull(sorted);
            assertEquals(1, sorted.size());
            ArrayEntity sortedEntity = sorted.getFirst();
            assertArrayEquals(expectedSorted, sortedEntity.getArray());
        }
    }

    @ParameterizedTest(name = "Sort strategy {0} should handle empty lists")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort handles empty lists")
    void sortHandlesEmptyLists(SortStrategy strategy) {
        List<ArrayEntity> emptyList = List.of();

        List<ArrayEntity> result = sortService.sort(emptyList, strategy);

        assertSame(emptyList, result);
    }

    @ParameterizedTest(name = "Sort strategy {0} should preserve array length")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort preserves array length")
    void sortPreservesArrayLength(SortStrategy strategy) {
        ArrayEntity array1 = factory.createArray(new int[] {5, 2, 8});
        ArrayEntity array2 = factory.createArray(new int[] {1, 9, 3, 7});
        List<ArrayEntity> arrays = List.of(array1, array2);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(2, sorted.size());
        assertEquals(3, sorted.getFirst().getLength());
        assertEquals(4, sorted.stream().skip(1).findFirst().orElseThrow().getLength());
    }

    @ParameterizedTest(name = "Sort strategy {0} should maintain consistency across multiple arrays")
    @ArgumentsSource(SortStrategyArgumentsProvider.class)
    @DisplayName("Sort maintains consistency across multiple arrays")
    void sortMaintainsConsistencyAcrossMultipleArrays(SortStrategy strategy) {
        ArrayEntity array1 = factory.createArray(new int[] {15, 3, 9, 1, 12});
        ArrayEntity array2 = factory.createArray(new int[] {8, 2, 6, 4});
        List<ArrayEntity> arrays = List.of(array1, array2);

        List<ArrayEntity> sorted = sortService.sort(arrays, strategy);

        assertEquals(2, sorted.size());
        assertTrue(isSorted(sorted.getFirst().getArray()));
        assertTrue(isSorted(sorted.stream().skip(1).findFirst().orElseThrow().getArray()));
    }

    @ParameterizedTest(name = "All strategies should produce identical results for array {0}")
    @ArgumentsSource(ArrayArgumentsProvider.class)
    @DisplayName("All strategies produce identical results")
    void allStrategiesProduceIdenticalResults(int[] originalData, int[] expectedSorted) {
        ArrayEntity testArray = factory.createArray(originalData == null ? new int[0] : originalData.clone());
        List<ArrayEntity> testList = List.of(testArray);

        List<ArrayEntity> mergeResult = sortService.sort(testList, SortStrategy.MERGE);
        List<ArrayEntity> heapResult = sortService.sort(testList, SortStrategy.HEAP);
        List<ArrayEntity> insertionResult = sortService.sort(testList, SortStrategy.INSERTION);
        List<ArrayEntity> selectionResult = sortService.sort(testList, SortStrategy.SELECTION);
        List<ArrayEntity> bubbleResult = sortService.sort(testList, SortStrategy.BUBBLE);

        int[] mergeArr = mergeResult.stream().findFirst().orElseThrow().getArray();
        int[] heapArr = heapResult.stream().findFirst().orElseThrow().getArray();
        int[] insertionArr = insertionResult.stream().findFirst().orElseThrow().getArray();
        int[] selectionArr = selectionResult.stream().findFirst().orElseThrow().getArray();
        int[] bubbleArr = bubbleResult.stream().findFirst().orElseThrow().getArray();

        assertArrayEquals(mergeArr, heapArr);
        assertArrayEquals(heapArr, insertionArr);
        assertArrayEquals(insertionArr, selectionArr);
        assertArrayEquals(selectionArr, bubbleArr);

        if (expectedSorted != null) {
            assertArrayEquals(expectedSorted, mergeArr);
        }
    }

    private boolean isSorted(int[] array) {
        return IntStream.range(1, array.length).noneMatch(i -> array[i] < array[i - 1]);
    }
}
