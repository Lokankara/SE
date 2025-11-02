package com.java.app.array.observer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.integer.IntArrayStatistics;
import com.java.app.array.entity.integer.IntWarehouse;
import com.java.app.array.factory.IntArrayFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObservableTest {

    private IntArrayFactory factory;
    private IntWarehouse intWarehouse;

    @BeforeEach
    void setUp() {
        factory = new IntArrayFactory();
        intWarehouse = IntWarehouse.getInstance();
    }

    @Test
    void testIntArrayEntityNotifiesWarehouseOnElementChange() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 2, 3, 4, 5});
        array.attach(intWarehouse);

        array.setArray(0, 100);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertNotEquals(15, stats.getSum());
    }

    @Test
    void testWarehouseReceivesInitialNotification() {
        IntArrayEntity array = factory.createArray(new Integer[] {10, 20, 30});

        assertNull(intWarehouse.getStatistics(array.getId()));

        array.attach(intWarehouse);
        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
    }

    @Test
    void testMultipleArraysWithWarehouseListener() {
        IntArrayEntity array1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity array2 = factory.createArray(new Integer[] {4, 5, 6});

        array1.attach(intWarehouse);
        array2.attach(intWarehouse);

        intWarehouse.onChanged(array1);
        intWarehouse.onChanged(array2);

        IntArrayStatistics stats1 = intWarehouse.getStatistics(array1.getId());
        IntArrayStatistics stats2 = intWarehouse.getStatistics(array2.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertEquals(3, stats1.getCount());
        assertEquals(3, stats2.getCount());
    }

    @Test
    void testWarehouseUpdatesAfterArrayModification() {
        IntArrayEntity array = factory.createArray(new Integer[] {5, 10, 15});
        array.attach(intWarehouse);
        intWarehouse.onChanged(array);

        IntArrayStatistics initialStats = intWarehouse.getStatistics(array.getId());
        long initialSum = initialStats.getSum();

        array.setArray(1, 50);

        IntArrayStatistics updatedStats = intWarehouse.getStatistics(array.getId());
        assertNotEquals(initialSum, updatedStats.getSum());
        assertEquals(3, updatedStats.getCount());
    }

    @Test
    void testRemoveListenerStopsWarehouseUpdates() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 2, 3});
        array.attach(intWarehouse);
        intWarehouse.onChanged(array);

        IntArrayStatistics initialStats = intWarehouse.getStatistics(array.getId());
        assertNotNull(initialStats);

        array.removeListener(intWarehouse);
        array.setArray(0, 999);

        IntArrayStatistics statsAfterRemoval = intWarehouse.getStatistics(array.getId());
        assertEquals(initialStats.getSum(), statsAfterRemoval.getSum());
    }

    @Test
    void testRandomArrayNotifiesWarehouse() {
        IntArrayEntity randomArray = factory.createRandomArray("RandomTest", 4, 1, 10);
        randomArray.attach(intWarehouse);
        intWarehouse.onChanged(randomArray);

        IntArrayStatistics initialStats = intWarehouse.getStatistics(randomArray.getId());
        assertNotNull(initialStats);
        assertEquals(4, initialStats.getCount());

        randomArray.setArray(0, 100);

        IntArrayStatistics updatedStats = intWarehouse.getStatistics(randomArray.getId());
        assertNotEquals(initialStats.getSum(), updatedStats.getSum());
    }

    @Test
    void testSequentialArrayNotifiesWarehouse() {
        IntArrayEntity sequentialArray = factory.createSequentialArray("SeqTest", 1, 5);
        sequentialArray.attach(intWarehouse);
        intWarehouse.onChanged(sequentialArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(sequentialArray.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());

        Integer[] expectedArray = {1, 2, 3, 4, 5};
        assertArrayEquals(expectedArray, sequentialArray.getArray());
    }

    @Test
    void testPatternArrayNotifiesWarehouse() {
        IntArrayEntity patternArray = factory.createArrayWithPattern("PatternTest", 3, 2, 3);
        patternArray.attach(intWarehouse);
        intWarehouse.onChanged(patternArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(patternArray.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());

        Integer[] expectedArray = {2, 5, 8};
        assertArrayEquals(expectedArray, patternArray.getArray());
    }

    @Test
    void testRangeArrayNotifiesWarehouse() {
        IntArrayEntity rangeArray = factory.createArrayFromRange("RangeTest", 0, 10, 5);
        rangeArray.attach(intWarehouse);
        intWarehouse.onChanged(rangeArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(rangeArray.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());

        Integer[] expectedArray = {0, 5, 10};
        assertArrayEquals(expectedArray, rangeArray.getArray());
    }

    @Test
    void testEmptyArrayNotifiesWarehouse() {
        IntArrayEntity emptyArray = factory.createArray(new Integer[] {});
        emptyArray.attach(intWarehouse);
        intWarehouse.onChanged(emptyArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(emptyArray.getId());
        assertNotNull(stats);
        assertEquals(0, stats.getCount());
        assertEquals(0, stats.getSum());
        assertEquals(0.0, stats.getAverage());
    }

    @Test
    void testSingleElementArrayNotifiesWarehouse() {
        IntArrayEntity singleArray = factory.createArray(new Integer[] {42});
        singleArray.attach(intWarehouse);
        intWarehouse.onChanged(singleArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(singleArray.getId());
        assertNotNull(stats);
        assertEquals(1, stats.getCount());

        singleArray.setArray(0, 84);

        IntArrayStatistics updatedStats = intWarehouse.getStatistics(singleArray.getId());
        assertNotEquals(42, updatedStats.getSum());
        assertEquals(84, singleArray.getArray()[0]);
    }

    @Test
    void testMultipleChangesUpdateWarehouse() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 1, 1, 1});
        array.attach(intWarehouse);
        intWarehouse.onChanged(array);

        IntArrayStatistics initialStats = intWarehouse.getStatistics(array.getId());
        assertEquals(4, initialStats.getSum());

        array.setArray(0, 10);
        IntArrayStatistics stats1 = intWarehouse.getStatistics(array.getId());

        array.setArray(1, 20);
        IntArrayStatistics stats2 = intWarehouse.getStatistics(array.getId());

        array.setArray(2, 30);
        IntArrayStatistics stats3 = intWarehouse.getStatistics(array.getId());

        assertTrue(stats1.getSum() > initialStats.getSum());
        assertTrue(stats2.getSum() > stats1.getSum());
        assertTrue(stats3.getSum() > stats2.getSum());
    }

    @Test
    void testWarehouseStatisticsReflectCurrentState() {
        IntArrayEntity array = factory.createArray(new Integer[] {10, 20, 30});
        array.attach(intWarehouse);
        intWarehouse.onChanged(array);

        array.setArray(0, 100);
        array.setArray(1, 200);
        array.setArray(2, 300);

        IntArrayStatistics finalStats = intWarehouse.getStatistics(array.getId());
        assertEquals(3, finalStats.getCount());

        Integer[] currentArray = array.getArray();
        assertEquals(100, currentArray[0]);
        assertEquals(200, currentArray[1]);
        assertEquals(300, currentArray[2]);
    }

    @Test
    void testWarehouseHandlesLargeArrays() {
        IntArrayEntity largeArray = factory.createRandomArray("LargeArray", 100, 1, 1000);
        largeArray.attach(intWarehouse);
        intWarehouse.onChanged(largeArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(largeArray.getId());
        assertNotNull(stats);
        assertEquals(100, stats.getCount());
        assertTrue(stats.getSum() > 0);
        assertTrue(stats.getAverage() > 0);
    }

    @Test
    void testWarehouseRemoveStatistics() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 2, 3});
        array.attach(intWarehouse);
        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);

        intWarehouse.removeStatistics(array.getId());

        IntArrayStatistics removedStats = intWarehouse.getStatistics(array.getId());
        assertNull(removedStats);
    }

    @Test
    void testObserverPatternWithBoundaryValues() {
        IntArrayEntity array = factory.createArray(new Integer[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE});
        array.attach(intWarehouse);
        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
        assertEquals(Integer.MIN_VALUE, stats.getMin());
        assertEquals(Integer.MAX_VALUE, stats.getMax());
    }

    @Test
    void testArrayModificationNotifiesCorrectly() {
        IntArrayEntity array1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity array2 = factory.createArray(new Integer[] {4, 5, 6});

        array1.attach(intWarehouse);
        array2.attach(intWarehouse);
        intWarehouse.onChanged(array1);
        intWarehouse.onChanged(array2);

        array1.setArray(0, 999);

        IntArrayStatistics stats1 = intWarehouse.getStatistics(array1.getId());
        IntArrayStatistics stats2 = intWarehouse.getStatistics(array2.getId());

        assertTrue(stats1.getSum() > stats2.getSum());
        assertEquals(999, array1.getArray()[0]);
        assertEquals(4, array2.getArray()[0]);
    }
}
