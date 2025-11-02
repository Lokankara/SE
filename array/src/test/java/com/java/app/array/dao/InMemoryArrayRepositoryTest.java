package com.java.app.array.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.comparator.ArrayComparator;
import com.java.app.array.entity.ArrayStatistics;
import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.integer.IntArrayStatistics;
import com.java.app.array.entity.integer.IntWarehouse;
import com.java.app.array.factory.IntArrayFactory;
import com.java.app.array.specification.IdSpecification;
import com.java.app.array.specification.NameSpecification;
import com.java.app.array.specification.SumGreaterThanSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryArrayRepositoryTest {

    private InMemoryArrayRepository repository;
    private IntArrayFactory factory;
    private IntWarehouse intWarehouse;

    @BeforeEach
    void setUp() {
        repository = new InMemoryArrayRepository();
        factory = new IntArrayFactory();
        intWarehouse = IntWarehouse.getInstance();
    }

    @Test
    void testAddEntityRegistersListener() {
        IntArrayEntity entity = factory.createArray(new Integer[] {1, 2, 3});

        repository.add(entity);

        List<IntArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertTrue(allEntities.contains(entity));

        ArrayStatistics<?> stats = intWarehouse.getStatistics(entity.getId());
        assertNotNull(stats);
    }

    @Test
    void testAddMultipleEntities() {
        IntArrayEntity entity1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {4, 5, 6});
        IntArrayEntity entity3 = factory.createArray(new Integer[] {7, 8, 9});

        repository.add(entity1);
        repository.add(entity2);
        repository.add(entity3);

        List<IntArrayEntity> allEntities = repository.findAll();
        assertEquals(3, allEntities.size());
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
        assertTrue(allEntities.contains(entity3));
    }

    @Test
    void testRemoveEntityUnregistersListener() {
        IntArrayEntity entity = factory.createArray(new Integer[] {1, 2, 3});
        repository.add(entity);

        ArrayStatistics<?> initialStats = intWarehouse.getStatistics(entity.getId());
        assertNotNull(initialStats);

        repository.remove(entity);

        List<IntArrayEntity> allEntities = repository.findAll();
        assertEquals(0, allEntities.size());
        assertFalse(allEntities.contains(entity));

        ArrayStatistics<?> removedStats = intWarehouse.getStatistics(entity.getId());
        assertNull(removedStats);
    }

    @Test
    void testRemoveNonExistentEntity() {
        IntArrayEntity entity1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {4, 5, 6});

        repository.add(entity1);
        repository.remove(entity2);

        List<IntArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertTrue(allEntities.contains(entity1));
    }

    @Test
    void testRemoveById() {
        IntArrayEntity entity1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {4, 5, 6});

        repository.add(entity1);
        repository.add(entity2);

        repository.removeById(entity1.getId());

        List<IntArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertFalse(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));

        ArrayStatistics<?> stats = intWarehouse.getStatistics(entity1.getId());
        assertNull(stats);
    }

    @Test
    void testRemoveByNonExistentId() {
        IntArrayEntity entity = factory.createArray(new Integer[] {1, 2, 3});
        repository.add(entity);

        repository.removeById(99999);

        List<IntArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertTrue(allEntities.contains(entity));
    }

    @Test
    void testFindBySpecificationId() {
        IntArrayEntity entity1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {4, 5, 6});

        repository.add(entity1);
        repository.add(entity2);

        List<IntArrayEntity> found = repository.findBySpecification(new IdSpecification(entity1.getId()));

        assertEquals(1, found.size());
        assertEquals(entity1, found.getFirst());
    }

    @Test
    void testFindBySpecificationName() {
        IntArrayEntity entity1 = factory.createRandomArray("TestArray1", 3, 1, 10);
        IntArrayEntity entity2 = factory.createRandomArray("TestArray2", 3, 1, 10);

        repository.add(entity1);
        repository.add(entity2);

        List<IntArrayEntity> found = repository.findBySpecification(new NameSpecification("TestArray1"));

        assertEquals(1, found.size());
        assertEquals(entity1, found.getFirst());
    }

    @Test
    void testFindBySpecificationSum() {
        IntArrayEntity smallSum = factory.createArray(new Integer[] {1, 1, 1});
        IntArrayEntity largeSum = factory.createArray(new Integer[] {10, 20, 30});

        repository.add(smallSum);
        repository.add(largeSum);

        List<IntArrayEntity> found = repository.findBySpecification(new SumGreaterThanSpecification(10));

        assertEquals(1, found.size());
        assertEquals(largeSum, found.getFirst());
    }

    @Test
    void testFindBySpecificationNoMatch() {
        IntArrayEntity entity = factory.createArray(new Integer[] {1, 2, 3});
        repository.add(entity);

        List<IntArrayEntity> found = repository.findBySpecification(new IdSpecification(99999));

        assertEquals(0, found.size());
    }

    @Test
    void testFindAllEmptyRepository() {
        List<IntArrayEntity> allEntities = repository.findAll();

        assertEquals(0, allEntities.size());
        assertTrue(allEntities.isEmpty());
    }

    @Test
    void testFindAllWithEntities() {
        IntArrayEntity entity1 = factory.createArray(new Integer[] {1, 2});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {3, 4});
        IntArrayEntity entity3 = factory.createArray(new Integer[] {5, 6});

        repository.add(entity1);
        repository.add(entity2);
        repository.add(entity3);

        List<IntArrayEntity> allEntities = repository.findAll();

        assertEquals(3, allEntities.size());
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
        assertTrue(allEntities.contains(entity3));
    }

    @Test
    void testSortById() {
        IntArrayEntity entity3 = factory.createArray(new Integer[] {7, 8, 9});
        IntArrayEntity entity1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {4, 5, 6});

        repository.add(entity3);
        repository.add(entity1);
        repository.add(entity2);

        List<IntArrayEntity> sorted = repository.sortBy(ArrayComparator.ID);

        assertEquals(3, sorted.size());
        assertTrue(sorted.getFirst().getId() < sorted.get(1).getId());
        assertTrue(sorted.get(1).getId() < sorted.get(2).getId());
    }

    @Test
    void testSortByName() {
        IntArrayEntity entityC = factory.createRandomArray("Charlie", 3, 1, 10);
        IntArrayEntity entityA = factory.createRandomArray("Alice", 3, 1, 10);
        IntArrayEntity entityB = factory.createRandomArray("Bob", 3, 1, 10);

        repository.add(entityC);
        repository.add(entityA);
        repository.add(entityB);

        List<IntArrayEntity> sorted = repository.sortBy(ArrayComparator.NAME);

        assertEquals(3, sorted.size());
        assertEquals("Alice", sorted.getFirst().getName());
        assertEquals("Bob", sorted.get(1).getName());
        assertEquals("Charlie", sorted.get(2).getName());
    }

    @Test
    void testSortByFirstElement() {
        IntArrayEntity entity1 = factory.createArray(new Integer[] {5, 1, 1});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {2, 2, 2});
        IntArrayEntity entity3 = factory.createArray(new Integer[] {8, 3, 3});

        repository.add(entity1);
        repository.add(entity2);
        repository.add(entity3);

        List<IntArrayEntity> sorted = repository.sortBy(ArrayComparator.FIRST);

        assertEquals(3, sorted.size());
        assertEquals(2, sorted.getFirst().getFirst());
        assertEquals(5, sorted.get(1).getFirst());
        assertEquals(8, sorted.get(2).getFirst());
    }

    @Test
    void testSortByLength() {
        IntArrayEntity shortArray = factory.createArray(new Integer[] {1});
        IntArrayEntity mediumArray = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity longArray = factory.createArray(new Integer[] {1, 2, 3, 4, 5});

        repository.add(longArray);
        repository.add(shortArray);
        repository.add(mediumArray);

        List<IntArrayEntity> sorted = repository.sortBy(ArrayComparator.LENGTH);

        assertEquals(3, sorted.size());
        assertEquals(1, sorted.getFirst().getLength());
        assertEquals(3, sorted.get(1).getLength());
        assertEquals(5, sorted.get(2).getLength());
    }

    @Test
    void testSortEmptyRepository() {
        List<IntArrayEntity> sorted = repository.sortBy(ArrayComparator.ID);

        assertEquals(0, sorted.size());
        assertTrue(sorted.isEmpty());
    }

    @Test
    void testClearRepository() {
        IntArrayEntity entity1 = factory.createArray(new Integer[] {1, 2, 3});
        IntArrayEntity entity2 = factory.createArray(new Integer[] {4, 5, 6});

        repository.add(entity1);
        repository.add(entity2);

        assertEquals(2, repository.findAll().size());

        repository.clear();

        assertEquals(0, repository.findAll().size());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void testRepositoryObserverIntegration() {
        IntArrayEntity entity = factory.createArray(new Integer[] {1, 2, 3});
        repository.add(entity);
        IntArrayStatistics initialStats = intWarehouse.getStatistics(entity.getId());
        assertEquals(6, initialStats.getSum());

        entity.setArray(0, 10);

        IntArrayStatistics updatedStats = intWarehouse.getStatistics(entity.getId());
        assertEquals(15, updatedStats.getSum());
    }

    @Test
    void testFindAllReturnsNewList() {
        IntArrayEntity entity = factory.createArray(new Integer[] {1, 2, 3});
        repository.add(entity);

        List<IntArrayEntity> list1 = repository.findAll();
        List<IntArrayEntity> list2 = repository.findAll();

        assertNotSame(list1, list2);
        assertEquals(list1, list2);
    }
}