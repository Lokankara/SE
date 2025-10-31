package com.java.app.array.strategy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.factory.ArrayFactory;
import com.java.app.array.service.ArraySortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class SortStrategyTest {

    private ArraySortService sortService;
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        sortService = new ArraySortService();
        factory = new ArrayFactory();
    }

    @Test
    void testMergeSortStrategy() {
        ArrayEntity unsorted = factory.createArray(new int[] {5, 2, 8, 1, 9});

        ArrayEntity sorted = sortService.sort(unsorted, SortStrategy.Merge);

        int[] expectedSorted = {1, 2, 5, 8, 9};
        assertArrayEquals(expectedSorted, sorted.getArray());
        assertArrayEquals(new int[] {5, 2, 8, 1, 9}, unsorted.getArray());
    }

    @Test
    void testHeapSortStrategy() {
        ArrayEntity unsorted = factory.createArray(new int[] {3, 7, 1, 4, 6});

        ArrayEntity sorted = sortService.sort(unsorted, SortStrategy.Heap);

        int[] expectedSorted = {1, 3, 4, 6, 7};
        assertArrayEquals(expectedSorted, sorted.getArray());
        assertArrayEquals(new int[] {3, 7, 1, 4, 6}, unsorted.getArray());
    }

    @Test
    void testInsertionSortStrategy() {
        ArrayEntity unsorted = factory.createArray(new int[] {9, 5, 1, 4, 3});

        ArrayEntity sorted = sortService.sort(unsorted, SortStrategy.Insertion);

        int[] expectedSorted = {1, 3, 4, 5, 9};
        assertArrayEquals(expectedSorted, sorted.getArray());
        assertArrayEquals(new int[] {9, 5, 1, 4, 3}, unsorted.getArray());
    }

    @Test
    void testSelectionSortStrategy() {
        ArrayEntity unsorted = factory.createArray(new int[] {6, 2, 9, 1, 5});

        ArrayEntity sorted = sortService.sort(unsorted, SortStrategy.Selection);

        int[] expectedSorted = {1, 2, 5, 6, 9};
        assertArrayEquals(expectedSorted, sorted.getArray());
        assertArrayEquals(new int[] {6, 2, 9, 1, 5}, unsorted.getArray());
    }

    @Test
    void testBubbleSortStrategy() {
        ArrayEntity unsorted = factory.createArray(new int[] {4, 1, 7, 2, 8});

        ArrayEntity sorted = sortService.sort(unsorted, SortStrategy.Bubble);

        int[] expectedSorted = {1, 2, 4, 7, 8};
        assertArrayEquals(expectedSorted, sorted.getArray());
        assertArrayEquals(new int[] {4, 1, 7, 2, 8}, unsorted.getArray());
    }

    @Test
    void testAllStrategiesWithSameArray() {
        int[] originalArray = {10, 3, 7, 1, 9, 4};
        int[] expectedSorted = {1, 3, 4, 7, 9, 10};

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity unsorted = factory.createArray(originalArray.clone());
            ArrayEntity sorted = sortService.sort(unsorted, strategy);

            assertArrayEquals(expectedSorted, sorted.getArray());
            assertArrayEquals(originalArray, unsorted.getArray());
        }
    }

    @Test
    void testSortEmptyArray() {
        ArrayEntity emptyArray = factory.createArray(new int[] {});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(emptyArray, strategy);
            assertEquals(0, sorted.getLength());
        }
    }

    @Test
    void testSortSingleElementArray() {
        ArrayEntity singleElement = factory.createArray(new int[] {42});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(singleElement, strategy);
            assertArrayEquals(new int[] {42}, sorted.getArray());
        }
    }

    @Test
    void testSortAlreadySortedArray() {
        ArrayEntity alreadySorted = factory.createArray(new int[] {1, 2, 3, 4, 5});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(alreadySorted, strategy);
            assertArrayEquals(new int[] {1, 2, 3, 4, 5}, sorted.getArray());
        }
    }

    @Test
    void testSortReverseSortedArray() {
        ArrayEntity reverseSorted = factory.createArray(new int[] {5, 4, 3, 2, 1});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(reverseSorted, strategy);
            assertArrayEquals(new int[] {1, 2, 3, 4, 5}, sorted.getArray());
        }
    }

    @Test
    void testSortArrayWithDuplicates() {
        ArrayEntity withDuplicates = factory.createArray(new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(withDuplicates, strategy);
            int[] expectedSorted = {1, 1, 2, 3, 4, 5, 5, 6, 9};
            assertArrayEquals(expectedSorted, sorted.getArray());
        }
    }

    @Test
    void testSortArrayWithAllSameElements() {
        ArrayEntity allSame = factory.createArray(new int[] {7, 7, 7, 7, 7});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(allSame, strategy);
            assertArrayEquals(new int[] {7, 7, 7, 7, 7}, sorted.getArray());
        }
    }

    @Test
    void testSortArrayWithNegativeNumbers() {
        ArrayEntity withNegatives = factory.createArray(new int[] {-5, 3, -1, 0, 2, -3});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(withNegatives, strategy);
            int[] expectedSorted = {-5, -3, -1, 0, 2, 3};
            assertArrayEquals(expectedSorted, sorted.getArray());
        }
    }

    @Test
    void testSortLargeArray() {
        ArrayEntity largeArray = factory.createRandomArray("LargeTest", 100, 1, 1000);

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(largeArray, strategy);

            int[] sortedArrayCopy = sorted.getArray();
            int[] javaSorted = largeArray.getArray();
            Arrays.sort(javaSorted);

            assertArrayEquals(javaSorted, sortedArrayCopy);
            assertEquals(100, sorted.getLength());
        }
    }

    @Test
    void testSortTwoElementArray() {
        ArrayEntity twoElements = factory.createArray(new int[] {2, 1});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(twoElements, strategy);
            assertArrayEquals(new int[] {1, 2}, sorted.getArray());
        }
    }

    @Test
    void testSortArrayWithExtremeValues() {
        ArrayEntity extremeValues = factory.createArray(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0});

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(extremeValues, strategy);
            int[] expectedSorted = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
            assertArrayEquals(expectedSorted, sorted.getArray());
        }
    }

    @Test
    void testOriginalArrayRemainsUnmodified() {
        int[] originalData = {8, 3, 5, 4, 7, 6, 1, 2};
        ArrayEntity original = factory.createArray(originalData.clone());

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(original, strategy);

            assertArrayEquals(originalData, original.getArray());
            assertNotSame(original.getArray(), sorted.getArray());

            int[] sortedData = sorted.getArray();
            Arrays.sort(originalData.clone());
            assertTrue(isSorted(sortedData));
        }
    }

    @Test
    void testSortStrategyEnumValues() {
        SortStrategy[] strategies = SortStrategy.values();
        assertEquals(5, strategies.length);

        assertEquals(SortStrategy.Merge, strategies[0]);
        assertEquals(SortStrategy.Heap, strategies[1]);
        assertEquals(SortStrategy.Insertion, strategies[2]);
        assertEquals(SortStrategy.Selection, strategies[3]);
        assertEquals(SortStrategy.Bubble, strategies[4]);
    }

    @Test
    void testSortStrategyGetAlgorithm() {
        for (SortStrategy strategy : SortStrategy.values()) {
            assertNotNull(strategy.getAlgorithm());
        }
    }

    @Test
    void testSequentialArrayWithDifferentStrategies() {
        ArrayEntity sequential = factory.createSequentialArray("SeqTest", 5, 10);
        int[] expectedSorted = {5, 6, 7, 8, 9, 10};

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(sequential, strategy);
            assertArrayEquals(expectedSorted, sorted.getArray());
        }
    }

    @Test
    void testPatternArrayWithDifferentStrategies() {
        ArrayEntity pattern = factory.createArrayWithPattern("PatternTest", 5, 10, -2);
        int[] originalPattern = {10, 8, 6, 4, 2};
        int[] expectedSorted = {2, 4, 6, 8, 10};

        assertArrayEquals(originalPattern, pattern.getArray());

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(pattern, strategy);
            assertArrayEquals(expectedSorted, sorted.getArray());
            assertArrayEquals(originalPattern, pattern.getArray());
        }
    }

    @Test
    void testRangeArrayWithDifferentStrategies() {
        ArrayEntity range = factory.createArrayFromRange("RangeTest", 20, 10, -3);
        int[] expectedOriginal = {20, 17, 14, 11};
        int[] expectedSorted = {11, 14, 17, 20};

        assertArrayEquals(expectedOriginal, range.getArray());

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(range, strategy);
            assertArrayEquals(expectedSorted, sorted.getArray());
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