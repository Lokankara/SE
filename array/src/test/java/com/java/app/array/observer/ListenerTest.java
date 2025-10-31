package com.java.app.array.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.ArrayStatistics;
import com.java.app.array.entity.Warehouse;
import com.java.app.array.factory.ArrayFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListenerTest {

    private Warehouse warehouse;
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.getInstance();
        factory = new ArrayFactory();
    }

    @Test
    void testOnChangedCalculatesStatistics() {
        ArrayEntity array = factory.createArray(new int[] {2, 4, 6, 8, 10});

        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertEquals(30, stats.getSum());
        assertEquals(6.0, stats.getAverage());
        assertEquals(10, stats.getMax());
        assertEquals(2, stats.getMin());
    }

    @Test
    void testOnChangedUpdatesExistingStatistics() {
        ArrayEntity array = factory.createArray(new int[] {1, 2, 3});
        warehouse.onChanged(array);

        ArrayStatistics initialStats = warehouse.getStatistics(array.getId());
        assertEquals(6, initialStats.getSum());

        array.setArray(2, 30);
        warehouse.onChanged(array);

        ArrayStatistics updatedStats = warehouse.getStatistics(array.getId());
        assertEquals(33, updatedStats.getSum());
        assertEquals(3, updatedStats.getCount());
    }

    @Test
    void testOnChangedHandlesEmptyArray() {
        ArrayEntity emptyArray = factory.createArray(new int[] {});

        warehouse.onChanged(emptyArray);

        ArrayStatistics stats = warehouse.getStatistics(emptyArray.getId());
        assertNotNull(stats);
        assertEquals(0, stats.getCount());
        assertEquals(0, stats.getSum());
        assertEquals(0.0, stats.getAverage());
        assertEquals(0, stats.getMax());
        assertEquals(0, stats.getMin());
    }

    @Test
    void testOnChangedHandlesSingleElement() {
        ArrayEntity singleArray = factory.createArray(new int[] {55});

        warehouse.onChanged(singleArray);

        ArrayStatistics stats = warehouse.getStatistics(singleArray.getId());
        assertNotNull(stats);
        assertEquals(1, stats.getCount());
        assertEquals(55, stats.getSum());
        assertEquals(55.0, stats.getAverage());
        assertEquals(55, stats.getMax());
        assertEquals(55, stats.getMin());
    }

    @Test
    void testOnChangedWithNegativeNumbers() {
        ArrayEntity array = factory.createArray(new int[] {-10, -5, 0, 5, 10});

        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertEquals(0, stats.getSum());
        assertEquals(0.0, stats.getAverage());
        assertEquals(10, stats.getMax());
        assertEquals(-10, stats.getMin());
    }

    @Test
    void testOnChangedWithIdenticalValues() {
        ArrayEntity array = factory.createArray(new int[] {7, 7, 7, 7, 7});

        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
        assertEquals(35, stats.getSum());
        assertEquals(7.0, stats.getAverage());
        assertEquals(7, stats.getMax());
        assertEquals(7, stats.getMin());
    }

    @Test
    void testOnChangedPreservesIndependentStatistics() {
        ArrayEntity array1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity array2 = factory.createArray(new int[] {10, 20, 30});

        warehouse.onChanged(array1);
        warehouse.onChanged(array2);

        ArrayStatistics stats1 = warehouse.getStatistics(array1.getId());
        ArrayStatistics stats2 = warehouse.getStatistics(array2.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertEquals(6, stats1.getSum());
        assertEquals(60, stats2.getSum());
        assertNotEquals(stats1.getSum(), stats2.getSum());
    }

    @Test
    void testOnChangedWithLargeNumbers() {
        ArrayEntity array = factory.createArray(new int[] {1000000, 2000000, 3000000});

        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
        assertEquals(6000000, stats.getSum());
        assertEquals(2000000.0, stats.getAverage());
        assertEquals(3000000, stats.getMax());
        assertEquals(1000000, stats.getMin());
    }

    @Test
    void testOnChangedReplacesOldStatistics() {
        ArrayEntity array = factory.createArray(new int[] {1, 1, 1});
        warehouse.onChanged(array);

        ArrayStatistics oldStats = warehouse.getStatistics(array.getId());
        assertEquals(3, oldStats.getSum());

        array.setArray(0, 100);
        array.setArray(1, 200);
        array.setArray(2, 300);
        warehouse.onChanged(array);

        ArrayStatistics newStats = warehouse.getStatistics(array.getId());
        assertEquals(600, newStats.getSum());
        assertNotEquals(oldStats.getSum(), newStats.getSum());
    }

    @Test
    void testGetStatisticsReturnsNull() {
        ArrayStatistics nonExistentStats = warehouse.getStatistics(99999);
        assertNull(nonExistentStats);
    }

    @Test
    void testRemoveStatisticsDeletesEntry() {
        ArrayEntity array = factory.createArray(new int[] {1, 2, 3});
        warehouse.onChanged(array);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);

        warehouse.removeStatistics(array.getId());

        ArrayStatistics removedStats = warehouse.getStatistics(array.getId());
        assertNull(removedStats);
    }

    @Test
    void testRemoveNonExistentStatistics() {
        warehouse.removeStatistics(12345);

        ArrayStatistics stats = warehouse.getStatistics(12345);
        assertNull(stats);
    }
}