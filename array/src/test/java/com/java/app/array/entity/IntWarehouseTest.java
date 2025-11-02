package com.java.app.array.entity;

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

class IntWarehouseTest {

    private IntWarehouse intWarehouse;
    private IntArrayFactory factory;

    @BeforeEach
    void setUp() {
        intWarehouse = IntWarehouse.getInstance();
        factory = new IntArrayFactory();
    }

    @Test
    void testWarehouseUpdatesOnIntArrayEntityChange() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 2, 3, 4, 5});
        array.attach(intWarehouse);

        array.setArray(0, 10);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
    }

    @Test
    void testWarehouseTracksMultipleArrays() {
        IntArrayEntity array1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity array2 = factory.createArray(new Integer[] {4, 5, 6});

        array1.attach(intWarehouse);
        array2.attach(intWarehouse);

        array1.setArray(0, 100);
        array2.setArray(0, 200);

        IntArrayStatistics stats1 = intWarehouse.getStatistics(array1.getId());
        IntArrayStatistics stats2 = intWarehouse.getStatistics(array2.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertNotEquals(stats1.getSum(), stats2.getSum());
    }

    @Test
    void testWarehouseRemovesStatistics() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 2, 3});
        array.attach(intWarehouse);
        array.setArray(0, 10);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);

        intWarehouse.removeStatistics(array.getId());

        IntArrayStatistics removedStats = intWarehouse.getStatistics(array.getId());
        assertNull(removedStats);
    }

    @Test
    void testWarehouseCalculatesCorrectStatistics() {
        IntArrayEntity array = factory.createArray(new Integer[] {10, 20, 30});
        array.attach(intWarehouse);

        array.setArray(1, 25);

        IntArrayStatistics stats = intWarehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
    }

    @Test
    void testWarehouseHandlesEmptyArray() {
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
    void testWarehouseUpdatesOnMultipleChanges() {
        IntArrayEntity array = factory.createArray(new Integer[] {5, 10, 15});
        array.attach(intWarehouse);

        array.setArray(0, 50);
        IntArrayStatistics stats1 = intWarehouse.getStatistics(array.getId());

        array.setArray(1, 100);
        IntArrayStatistics stats2 = intWarehouse.getStatistics(array.getId());

        array.setArray(2, 150);
        IntArrayStatistics stats3 = intWarehouse.getStatistics(array.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertNotNull(stats3);
        assertNotEquals(stats1.getSum(), stats2.getSum());
        assertNotEquals(stats2.getSum(), stats3.getSum());
    }

    @Test
    void testWarehouseReflectsCurrentArrayState() {
        IntArrayEntity array = factory.createArray(new Integer[] {1, 1, 1});
        array.attach(intWarehouse);

        array.setArray(0, 100);
        array.setArray(1, 200);
        array.setArray(2, 300);

        IntArrayStatistics finalStats = intWarehouse.getStatistics(array.getId());
        assertNotNull(finalStats);
        assertEquals(3, finalStats.getCount());

        Integer[] currentArray = array.getArray();
        assertEquals(100, currentArray[0]);
        assertEquals(200, currentArray[1]);
        assertEquals(300, currentArray[2]);
    }
}