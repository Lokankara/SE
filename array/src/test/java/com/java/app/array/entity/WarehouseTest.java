package com.java.app.array.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.java.app.array.factory.ArrayFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarehouseTest {

    private Warehouse warehouse;
    private ArrayFactory factory;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.getInstance();
        factory = new ArrayFactory();
    }

    @Test
    void testWarehouseUpdatesOnArrayEntityChange() {
        ArrayEntity array = factory.createArray(new int[] {1, 2, 3, 4, 5});
        array.attach(warehouse);

        array.setArray(0, 10);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(5, stats.getCount());
    }

    @Test
    void testWarehouseTracksMultipleArrays() {
        ArrayEntity array1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity array2 = factory.createArray(new int[] {4, 5, 6});

        array1.attach(warehouse);
        array2.attach(warehouse);

        array1.setArray(0, 100);
        array2.setArray(0, 200);

        ArrayStatistics stats1 = warehouse.getStatistics(array1.getId());
        ArrayStatistics stats2 = warehouse.getStatistics(array2.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertNotEquals(stats1.getSum(), stats2.getSum());
    }

    @Test
    void testWarehouseRemovesStatistics() {
        ArrayEntity array = factory.createArray(new int[] {1, 2, 3});
        array.attach(warehouse);
        array.setArray(0, 10);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);

        warehouse.removeStatistics(array.getId());

        ArrayStatistics removedStats = warehouse.getStatistics(array.getId());
        assertNull(removedStats);
    }

    @Test
    void testWarehouseCalculatesCorrectStatistics() {
        ArrayEntity array = factory.createArray(new int[] {10, 20, 30});
        array.attach(warehouse);

        array.setArray(1, 25);

        ArrayStatistics stats = warehouse.getStatistics(array.getId());
        assertNotNull(stats);
        assertEquals(3, stats.getCount());
    }

    @Test
    void testWarehouseHandlesEmptyArray() {
        ArrayEntity emptyArray = factory.createArray(new int[] {});
        emptyArray.attach(warehouse);

        warehouse.onChanged(emptyArray);

        ArrayStatistics stats = warehouse.getStatistics(emptyArray.getId());
        assertNotNull(stats);
        assertEquals(0, stats.getCount());
        assertEquals(0, stats.getSum());
        assertEquals(0.0, stats.getAverage());
    }

    @Test
    void testWarehouseUpdatesOnMultipleChanges() {
        ArrayEntity array = factory.createArray(new int[] {5, 10, 15});
        array.attach(warehouse);

        array.setArray(0, 50);
        ArrayStatistics stats1 = warehouse.getStatistics(array.getId());

        array.setArray(1, 100);
        ArrayStatistics stats2 = warehouse.getStatistics(array.getId());

        array.setArray(2, 150);
        ArrayStatistics stats3 = warehouse.getStatistics(array.getId());

        assertNotNull(stats1);
        assertNotNull(stats2);
        assertNotNull(stats3);
        assertNotEquals(stats1.getSum(), stats2.getSum());
        assertNotEquals(stats2.getSum(), stats3.getSum());
    }

    @Test
    void testWarehouseReflectsCurrentArrayState() {
        ArrayEntity array = factory.createArray(new int[] {1, 1, 1});
        array.attach(warehouse);

        array.setArray(0, 100);
        array.setArray(1, 200);
        array.setArray(2, 300);

        ArrayStatistics finalStats = warehouse.getStatistics(array.getId());
        assertNotNull(finalStats);
        assertEquals(3, finalStats.getCount());

        int[] currentArray = array.getArray();
        assertEquals(100, currentArray[0]);
        assertEquals(200, currentArray[1]);
        assertEquals(300, currentArray[2]);
    }
}