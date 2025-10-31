package com.java.app.array.observer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.ArrayStatistics;
import com.java.app.array.entity.Warehouse;
import com.java.app.array.factory.ArrayFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObservableTest {

    private ArrayFactory factory;
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        factory = new ArrayFactory();
        warehouse = Warehouse.getInstance();
    }

    @Test
    void testArrayEntityNotifiesWarehouseOnElementChange() {
        ArrayEntity array = factory.createArray(new int[] {1, 2, 3, 4, 5});
        array.addListener(warehouse);

        array.setArray(0, 100);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertNotEquals(15, stats.getSum());
    }

    @Test
    void testWarehouseReceivesInitialNotification() {
        ArrayEntity array = factory.createArray(new int[] {10, 20, 30});

        assertNull(warehouse.getStatistics(array.getId()));

        array.addListener(warehouse);
        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
    }

    @Test
    void testMultipleArraysWithWarehouseListener() {
        ArrayEntity array1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity array2 = factory.createArray(new int[] {4, 5, 6});

        array1.addListener(warehouse);
        array2.addListener(warehouse);

        warehouse.onChanged(array1);
        warehouse.onChanged(array2);

        ArrayStatistics stats1 = warehouse.getStatistics(array1.getId());
        ArrayStatistics stats2 = warehouse.getStatistics(array2.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertEquals(3, stats1.getCount());
        assertEquals(3, stats2.getCount());
    }

    @Test
    void testWarehouseUpdatesAfterArrayModification() {
        ArrayEntity array = factory.createArray(new int[] {5, 10, 15});
        array.addListener(warehouse);
        warehouse.onChanged(array);

        ArrayStatistics initialStats = warehouse.getStatistics(array.getId());
        int initialSum = initialStats.getSum();

        array.setArray(1, 50);

        ArrayStatistics updatedStats = warehouse.getStatistics(array.getId());
        assertNotEquals(initialSum, updatedStats.getSum());
        assertEquals(3, updatedStats.getCount());
    }

    @Test
    void testRemoveListenerStopsWarehouseUpdates() {
        ArrayEntity array = factory.createArray(new int[] {1, 2, 3});
        array.addListener(warehouse);
        warehouse.onChanged(array);

        ArrayStatistics initialStats = warehouse.getStatistics(array.getId());
        assertNotNull(initialStats);

        array.removeListener(warehouse);
        array.setArray(0, 999);

        ArrayStatistics statsAfterRemoval = warehouse.getStatistics(array.getId());
        assertEquals(initialStats.getSum(), statsAfterRemoval.getSum());
    }

    @Test
    void testRandomArrayNotifiesWarehouse() {
        ArrayEntity randomArray = factory.createRandomArray("RandomTest", 4, 1, 10);
        randomArray.addListener(warehouse);
        warehouse.onChanged(randomArray);

        ArrayStatistics initialStats = warehouse.getStatistics(randomArray.getId());
        assertNotNull(initialStats);
        assertEquals(4, initialStats.getCount());

        randomArray.setArray(0, 100);

        ArrayStatistics updatedStats = warehouse.getStatistics(randomArray.getId());
        assertNotEquals(initialStats.getSum(), updatedStats.getSum());
    }

    @Test
    void testSequentialArrayNotifiesWarehouse() {
        ArrayEntity sequentialArray = factory.createSequentialArray("SeqTest", 1, 5);
        sequentialArray.addListener(warehouse);
        warehouse.onChanged(sequentialArray);

        ArrayStatistics stats = warehouse.getStatistics(sequentialArray.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());

        int[] expectedArray = {1, 2, 3, 4, 5};
        assertArrayEquals(expectedArray, sequentialArray.getArray());
    }

    @Test
    void testPatternArrayNotifiesWarehouse() {
        ArrayEntity patternArray = factory.createArrayWithPattern("PatternTest", 3, 2, 3);
        patternArray.addListener(warehouse);
        warehouse.onChanged(patternArray);

        ArrayStatistics stats = warehouse.getStatistics(patternArray.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());

        int[] expectedArray = {2, 5, 8};
        assertArrayEquals(expectedArray, patternArray.getArray());
    }

    @Test
    void testRangeArrayNotifiesWarehouse() {
        ArrayEntity rangeArray = factory.createArrayFromRange("RangeTest", 0, 10, 5);
        rangeArray.addListener(warehouse);
        warehouse.onChanged(rangeArray);

        ArrayStatistics stats = warehouse.getStatistics(rangeArray.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());

        int[] expectedArray = {0, 5, 10};
        assertArrayEquals(expectedArray, rangeArray.getArray());
    }

    @Test
    void testEmptyArrayNotifiesWarehouse() {
        ArrayEntity emptyArray = factory.createArray(new int[] {});
        emptyArray.addListener(warehouse);
        warehouse.onChanged(emptyArray);

        ArrayStatistics stats = warehouse.getStatistics(emptyArray.getId());
        assertNotNull(stats);
        assertEquals(0, stats.getCount());
        assertEquals(0, stats.getSum());
        assertEquals(0.0, stats.getAverage());
    }

    @Test
    void testSingleElementArrayNotifiesWarehouse() {
        ArrayEntity singleArray = factory.createArray(new int[] {42});
        singleArray.addListener(warehouse);
        warehouse.onChanged(singleArray);

        ArrayStatistics stats = warehouse.getStatistics(singleArray.getId());
        assertNotNull(stats);
        assertEquals(1, stats.getCount());

        singleArray.setArray(0, 84);

        ArrayStatistics updatedStats = warehouse.getStatistics(singleArray.getId());
        assertNotEquals(42, updatedStats.getSum());
        assertEquals(84, singleArray.getArray()[0]);
    }

    @Test
    void testMultipleChangesUpdateWarehouse() {
        ArrayEntity array = factory.createArray(new int[] {1, 1, 1, 1});
        array.addListener(warehouse);
        warehouse.onChanged(array);

        ArrayStatistics initialStats = warehouse.getStatistics(array.getId());
//        assertEquals(4, initialStats.getSum());

        array.setArray(0, 10);
        ArrayStatistics stats1 = warehouse.getStatistics(array.getId());

        array.setArray(1, 20);
        ArrayStatistics stats2 = warehouse.getStatistics(array.getId());

        array.setArray(2, 30);
        ArrayStatistics stats3 = warehouse.getStatistics(array.getId());

        assertTrue(stats1.getSum() > initialStats.getSum());
        assertTrue(stats2.getSum() > stats1.getSum());
        assertTrue(stats3.getSum() > stats2.getSum());
    }

    @Test
    void testWarehouseStatisticsReflectCurrentState() {
        ArrayEntity array = factory.createArray(new int[] {10, 20, 30});
        array.addListener(warehouse);
        warehouse.onChanged(array);

        array.setArray(0, 100);
        array.setArray(1, 200);
        array.setArray(2, 300);

        ArrayStatistics finalStats = warehouse.getStatistics(array.getId());
        assertEquals(3, finalStats.getCount());

        int[] currentArray = array.getArray();
        assertEquals(100, currentArray[0]);
        assertEquals(200, currentArray[1]);
        assertEquals(300, currentArray[2]);
    }

    @Test
    void testWarehouseHandlesLargeArrays() {
        ArrayEntity largeArray = factory.createRandomArray("LargeArray", 100, 1, 1000);
        largeArray.addListener(warehouse);
        warehouse.onChanged(largeArray);

        ArrayStatistics stats = warehouse.getStatistics(largeArray.getId());
        assertNotNull(stats);
        assertEquals(100, stats.getCount());
        assertTrue(stats.getSum() > 0);
        assertTrue(stats.getAverage() > 0);
    }

    @Test
    void testWarehouseRemoveStatistics() {
        ArrayEntity array = factory.createArray(new int[] {1, 2, 3});
        array.addListener(warehouse);
        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);

        warehouse.removeStatistics(array.getId());

        ArrayStatistics removedStats = warehouse.getStatistics(array.getId());
        assertNull(removedStats);
    }

    @Test
    void testObserverPatternWithBoundaryValues() {
        ArrayEntity array = factory.createArray(new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE});
        array.addListener(warehouse);
        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
        assertEquals(Integer.MIN_VALUE, stats.getMin());
        assertEquals(Integer.MAX_VALUE, stats.getMax());
    }

    @Test
    void testArrayModificationNotifiesCorrectly() {
        ArrayEntity array1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity array2 = factory.createArray(new int[] {4, 5, 6});

        array1.addListener(warehouse);
        array2.addListener(warehouse);
        warehouse.onChanged(array1);
        warehouse.onChanged(array2);

        array1.setArray(0, 999);

        ArrayStatistics stats1 = warehouse.getStatistics(array1.getId());
        ArrayStatistics stats2 = warehouse.getStatistics(array2.getId());

        assertTrue(stats1.getSum() > stats2.getSum());
        assertEquals(999, array1.getArray()[0]);
        assertEquals(4, array2.getArray()[0]);
    }
}
