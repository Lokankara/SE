package com.java.app.array.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.factory.ArrayFactory;
import com.java.app.array.strategy.SortStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArraySortServiceTest {

    private ArraySortService sortService;
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        sortService = new ArraySortService();
        factory = new ArrayFactory();
    }

    @Test
    void testSortReturnsNewArrayEntity() {
        ArrayEntity original = factory.createArray(new int[] {3, 1, 4, 1, 5});

        ArrayEntity sorted = sortService.sort(original, SortStrategy.Merge);

        assertNotSame(original, sorted);
        assertNotEquals(original.getId(), sorted.getId());
    }

    @Test
    void testSortPreservesOriginalArrayState() {
        int[] originalData = {9, 2, 7, 4, 1, 8};
        ArrayEntity original = factory.createArray(originalData.clone());

        sortService.sort(original, SortStrategy.Bubble);

        assertArrayEquals(originalData, original.getArray());
    }

    @Test
    void testSortWithRandomArrays() {
        ArrayEntity randomArray = factory.createRandomArray("RandomSort", 20, 1, 100);

        for (SortStrategy strategy : SortStrategy.values()) {
            ArrayEntity sorted = sortService.sort(randomArray, strategy);

            assertTrue(isSortedAscending(sorted.getArray()));
            assertEquals(randomArray.getLength(), sorted.getLength());
        }
    }

    @Test
    void testSortConsistencyAcrossStrategies() {
        ArrayEntity testArray = factory.createArray(new int[] {15, 3, 9, 1, 12, 6});

        ArrayEntity mergeSorted = sortService.sort(testArray, SortStrategy.Merge);
        ArrayEntity heapSorted = sortService.sort(testArray, SortStrategy.Heap);
        ArrayEntity insertionSorted = sortService.sort(testArray, SortStrategy.Insertion);
        ArrayEntity selectionSorted = sortService.sort(testArray, SortStrategy.Selection);
        ArrayEntity bubbleSorted = sortService.sort(testArray, SortStrategy.Bubble);

        assertArrayEquals(mergeSorted.getArray(), heapSorted.getArray());
        assertArrayEquals(heapSorted.getArray(), insertionSorted.getArray());
        assertArrayEquals(insertionSorted.getArray(), selectionSorted.getArray());
        assertArrayEquals(selectionSorted.getArray(), bubbleSorted.getArray());
    }

    @Test
    void testSortEmptyArrayReturnsEmpty() {
        ArrayEntity emptyArray = factory.createArray(new int[] {});

        ArrayEntity sorted = sortService.sort(emptyArray, SortStrategy.Merge);

        assertEquals(0, sorted.getLength());
        assertTrue(sorted.isEmpty());
    }

    @Test
    void testSortSingleElementArrayReturnsSame() {
        ArrayEntity singleElement = factory.createArray(new int[] {99});

        ArrayEntity sorted = sortService.sort(singleElement, SortStrategy.Selection);

        assertEquals(1, sorted.getLength());
        assertEquals(99, sorted.getFirst());
        assertArrayEquals(new int[] {99}, sorted.getArray());
    }

    @Test
    void testSortArrayWithNegativeAndPositiveNumbers() {
        ArrayEntity mixedArray = factory.createArray(new int[] {-10, 5, -3, 0, 8, -1});

        ArrayEntity sorted = sortService.sort(mixedArray, SortStrategy.Insertion);

        int[] expectedSorted = {-10, -3, -1, 0, 5, 8};
        assertArrayEquals(expectedSorted, sorted.getArray());
    }

    @Test
    void testSortLargeSequentialArray() {
        ArrayEntity largeSeq = factory.createSequentialArray("LargeSeq", 1, 1000);

        ArrayEntity sorted = sortService.sort(largeSeq, SortStrategy.Heap);

        assertTrue(isSortedAscending(sorted.getArray()));
        assertEquals(1000, sorted.getLength());
        assertEquals(1, sorted.getFirst());
    }

    @Test
    void testSortPatternArrayWithNegativeStep() {
        ArrayEntity pattern = factory.createArrayWithPattern("NegPattern", 10, 50, -5);

        ArrayEntity sorted = sortService.sort(pattern, SortStrategy.Bubble);

        assertTrue(isSortedAscending(sorted.getArray()));
        assertEquals(10, sorted.getLength());
    }

    @Test
    void testSortRangeArrayWithCustomStep() {
        ArrayEntity range = factory.createArrayFromRange("CustomRange", 5, 25, 3);

        ArrayEntity sorted = sortService.sort(range, SortStrategy.Selection);

        assertTrue(isSortedAscending(sorted.getArray()));
        int[] expectedOriginal = {5, 8, 11, 14, 17, 20, 23};
        assertEquals(expectedOriginal.length, sorted.getLength());
    }

    @Test
    void testSortPreservesArrayLength() {
        int[] sizes = {0, 1, 5, 10, 50, 100};

        for (int size : sizes) {
            if (size == 0) {
                ArrayEntity array = factory.createArray(new int[] {});
                ArrayEntity sorted = sortService.sort(array, SortStrategy.Merge);
                assertEquals(0, sorted.getLength());
            } else {
                ArrayEntity array = factory.createRandomArray("SizeTest", size, 1, 100);
                ArrayEntity sorted = sortService.sort(array, SortStrategy.Merge);
                assertEquals(size, sorted.getLength());
            }
        }
    }

    @Test
    void testSortWithExtremeValues() {
        ArrayEntity extremes = factory.createArray(new int[] {
                Integer.MAX_VALUE, Integer.MIN_VALUE, 0, -1, 1
        });

        ArrayEntity sorted = sortService.sort(extremes, SortStrategy.Heap);

        assertEquals(Integer.MIN_VALUE, sorted.getArray()[0]);
        assertEquals(Integer.MAX_VALUE, sorted.getArray()[sorted.getLength() - 1]);
        assertTrue(isSortedAscending(sorted.getArray()));
    }

    @Test
    void testSortStability() {
        ArrayEntity withDuplicates = factory.createArray(new int[] {5, 2, 8, 2, 1, 5, 3});

        ArrayEntity sorted = sortService.sort(withDuplicates, SortStrategy.Merge);

        int[] expectedSorted = {1, 2, 2, 3, 5, 5, 8};
        assertArrayEquals(expectedSorted, sorted.getArray());
        assertTrue(isSortedAscending(sorted.getArray()));
    }

    private boolean isSortedAscending(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
}