package com.java.app.array.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.integer.IntArrayStatistics;
import com.java.app.array.entity.integer.IntWarehouse;
import com.java.app.array.factory.IntArrayFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListenerTest {

    private IntWarehouse intWarehouse;
    private IntArrayFactory factory;

    @BeforeEach
    void setUp() {
        intWarehouse = IntWarehouse.getInstance();
        factory = new IntArrayFactory();
    }

    @Test
    void testOnChangedCalculatesStatistics() {
        IntArrayEntity array = factory.createArray(new Integer[] {2, 4, 6, 8, 10});

        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertEquals(30, stats.getSum());
        assertEquals(6.0, stats.getAverage());
        assertEquals(10, stats.getMax());
        assertEquals(2, stats.getMin());
    }

    @Test
    void testOnChangedUpdatesExistingStatistics() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 2, 3});
        intWarehouse.onChanged(array);

        IntArrayStatistics initialStats = intWarehouse.getStatistics(array.getId());
        assertEquals(6, initialStats.getSum());

        array.setArray(2, 30);
        intWarehouse.onChanged(array);

        IntArrayStatistics updatedStats = intWarehouse.getStatistics(array.getId());
        assertEquals(33, updatedStats.getSum());
        assertEquals(3, updatedStats.getCount());
    }

    @Test
    void testOnChangedHandlesEmptyArray() {
        IntArrayEntity emptyArray = factory.createArray(new Integer[] {});

        intWarehouse.onChanged(emptyArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(emptyArray.getId());
        assertNotNull(stats);
        assertEquals(0, stats.getCount());
        assertEquals(0, stats.getSum());
        assertEquals(0.0, stats.getAverage());
        assertEquals(0, stats.getMax());
        assertEquals(0, stats.getMin());
    }

    @Test
    void testOnChangedHandlesSingleElement() {
        IntArrayEntity singleArray = factory.createArray(new Integer[] {55});

        intWarehouse.onChanged(singleArray);

        IntArrayStatistics stats = intWarehouse.getStatistics(singleArray.getId());
        assertNotNull(stats);
        assertEquals(1, stats.getCount());
        assertEquals(55, stats.getSum());
        assertEquals(55.0, stats.getAverage());
        assertEquals(55, stats.getMax());
        assertEquals(55, stats.getMin());
    }

    @Test
    void testOnChangedWithNegativeNumbers() {
        IntArrayEntity array = factory.createArray(new Integer[] {-10, -5, 0, 5, 10});

        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertEquals(0, stats.getSum());
        assertEquals(0.0, stats.getAverage());
        assertEquals(10, stats.getMax());
        assertEquals(-10, stats.getMin());
    }

    @Test
    void testOnChangedWithIdenticalValues() {
        IntArrayEntity array = factory.createArray(new Integer[] {7, 7, 7, 7, 7});

        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertEquals(35, stats.getSum());
        assertEquals(7.0, stats.getAverage());
        assertEquals(7, stats.getMax());
        assertEquals(7, stats.getMin());
    }

    @Test
    void testOnChangedPreservesIndependentStatistics() {
        IntArrayEntity array1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity array2 = factory.createArray(new Integer[] {10, 20, 30});

        intWarehouse.onChanged(array1);
        intWarehouse.onChanged(array2);

        IntArrayStatistics stats1 = intWarehouse.getStatistics(array1.getId());
        IntArrayStatistics stats2 = intWarehouse.getStatistics(array2.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertEquals(6, stats1.getSum());
        assertEquals(60, stats2.getSum());
        assertNotEquals(stats1.getSum(), stats2.getSum());
    }

    @Test
    void testOnChangedWithLargeNumbers() {
        IntArrayEntity array = factory.createArray(new Integer[] {1000000, 2000000, 3000000});

        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
        assertEquals(6000000, stats.getSum());
        assertEquals(2000000.0, stats.getAverage());
        assertEquals(3000000, stats.getMax());
        assertEquals(1000000, stats.getMin());
    }

    @Test
    void testOnChangedReplacesOldStatistics() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 1, 1});
        intWarehouse.onChanged(array);

        IntArrayStatistics oldStats = intWarehouse.getStatistics(array.getId());
        assertEquals(3, oldStats.getSum());

        array.setArray(0, 100);
        array.setArray(1, 200);
        array.setArray(2, 300);
        intWarehouse.onChanged(array);

        IntArrayStatistics newStats = intWarehouse.getStatistics(array.getId());
        assertEquals(600, newStats.getSum());
        assertNotEquals(oldStats.getSum(), newStats.getSum());
    }

    @Test
    void testGetStatisticsReturnsNull() {
        IntArrayStatistics nonExistentStats = intWarehouse.getStatistics(99999);
        assertNull(nonExistentStats);
    }

    @Test
    void testRemoveStatisticsDeletesEntry() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 2, 3});
        intWarehouse.onChanged(array);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);

        intWarehouse.removeStatistics(array.getId());

        IntArrayStatistics removedStats = intWarehouse.getStatistics(array.getId());
        assertNull(removedStats);
    }

    @Test
    void testRemoveNonExistentStatistics() {
        intWarehouse.removeStatistics(12345);

        IntArrayStatistics stats = intWarehouse.getStatistics(12345);
        assertNull(stats);
    }
}