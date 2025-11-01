package com.java.app.array.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.java.app.array.comparator.ArrayComparators;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.IntArrayStatistics;
import com.java.app.array.entity.Warehouse;
import com.java.app.array.factory.ArrayFactory;
import com.java.app.array.specification.IdSpecification;
import com.java.app.array.specification.NameSpecification;
import com.java.app.array.specification.SumGreaterThanSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryArrayRepositoryTest {

    private InMemoryArrayRepository repository;
    private ArrayFactory factory;
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        repository = new InMemoryArrayRepository();
        factory = new ArrayFactory();
        warehouse = Warehouse.getInstance();
    }

    @Test
    void testAddEntityRegistersListener() {
        ArrayEntity entity = factory.createArray(new int[] {1, 2, 3});

        repository.add(entity);

        List<ArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertTrue(allEntities.contains(entity));

        IntArrayStatistics stats = warehouse.getStatistics(entity.getId());
        assertNotNull(stats);
    }

    @Test
    void testAddMultipleEntities() {
        ArrayEntity entity1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity entity2 = factory.createArray(new int[] {4, 5, 6});
        ArrayEntity entity3 = factory.createArray(new int[] {7, 8, 9});

        repository.add(entity1);
        repository.add(entity2);
        repository.add(entity3);

        List<ArrayEntity> allEntities = repository.findAll();
        assertEquals(3, allEntities.size());
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
        assertTrue(allEntities.contains(entity3));
    }

    @Test
    void testRemoveEntityUnregistersListener() {
        ArrayEntity entity = factory.createArray(new int[] {1, 2, 3});
        repository.add(entity);

        IntArrayStatistics initialStats = warehouse.getStatistics(entity.getId());
        assertNotNull(initialStats);

        repository.remove(entity);

        List<ArrayEntity> allEntities = repository.findAll();
        assertEquals(0, allEntities.size());
        assertFalse(allEntities.contains(entity));

        IntArrayStatistics removedStats = warehouse.getStatistics(entity.getId());
        assertNull(removedStats);
    }

    @Test
    void testRemoveNonExistentEntity() {
        ArrayEntity entity1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity entity2 = factory.createArray(new int[] {4, 5, 6});

        repository.add(entity1);
        repository.remove(entity2);

        List<ArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertTrue(allEntities.contains(entity1));
    }

    @Test
    void testRemoveById() {
        ArrayEntity entity1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity entity2 = factory.createArray(new int[] {4, 5, 6});

        repository.add(entity1);
        repository.add(entity2);

        repository.removeById(entity1.getId());

        List<ArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertFalse(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));

        IntArrayStatistics stats = warehouse.getStatistics(entity1.getId());
        assertNull(stats);
    }

    @Test
    void testRemoveByNonExistentId() {
        ArrayEntity entity = factory.createArray(new int[] {1, 2, 3});
        repository.add(entity);

        repository.removeById(99999);

        List<ArrayEntity> allEntities = repository.findAll();
        assertEquals(1, allEntities.size());
        assertTrue(allEntities.contains(entity));
    }

    @Test
    void testFindBySpecificationId() {
        ArrayEntity entity1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity entity2 = factory.createArray(new int[] {4, 5, 6});

        repository.add(entity1);
        repository.add(entity2);

        List<ArrayEntity> found = repository.findBySpecification(new IdSpecification(entity1.getId()));

        assertEquals(1, found.size());
        assertEquals(entity1, found.getFirst());
    }

    @Test
    void testFindBySpecificationName() {
        ArrayEntity entity1 = factory.createRandomArray("TestArray1", 3, 1, 10);
        ArrayEntity entity2 = factory.createRandomArray("TestArray2", 3, 1, 10);

        repository.add(entity1);
        repository.add(entity2);

        List<ArrayEntity> found = repository.findBySpecification(new NameSpecification("TestArray1"));

        assertEquals(1, found.size());
        assertEquals(entity1, found.getFirst());
    }

    @Test
    void testFindBySpecificationSum() {
        ArrayEntity smallSum = factory.createArray(new int[] {1, 1, 1});
        ArrayEntity largeSum = factory.createArray(new int[] {10, 20, 30});

        repository.add(smallSum);
        repository.add(largeSum);

        List<ArrayEntity> found = repository.findBySpecification(new SumGreaterThanSpecification(10));

        assertEquals(1, found.size());
        assertEquals(largeSum, found.getFirst());
    }

    @Test
    void testFindBySpecificationNoMatch() {
        ArrayEntity entity = factory.createArray(new int[] {1, 2, 3});
        repository.add(entity);

        List<ArrayEntity> found = repository.findBySpecification(new IdSpecification(99999));

        assertEquals(0, found.size());
    }

    @Test
    void testFindAllEmptyRepository() {
        List<ArrayEntity> allEntities = repository.findAll();

        assertEquals(0, allEntities.size());
        assertTrue(allEntities.isEmpty());
    }

    @Test
    void testFindAllWithEntities() {
        ArrayEntity entity1 = factory.createArray(new int[] {1, 2});
        ArrayEntity entity2 = factory.createArray(new int[] {3, 4});
        ArrayEntity entity3 = factory.createArray(new int[] {5, 6});

        repository.add(entity1);
        repository.add(entity2);
        repository.add(entity3);

        List<ArrayEntity> allEntities = repository.findAll();

        assertEquals(3, allEntities.size());
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
        assertTrue(allEntities.contains(entity3));
    }

    @Test
    void testSortById() {
        ArrayEntity entity3 = factory.createArray(new int[] {7, 8, 9});
        ArrayEntity entity1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity entity2 = factory.createArray(new int[] {4, 5, 6});

        repository.add(entity3);
        repository.add(entity1);
        repository.add(entity2);

        List<ArrayEntity> sorted = repository.sortBy(ArrayComparators.ID);

        assertEquals(3, sorted.size());
        assertTrue(sorted.getFirst().getId() < sorted.get(1).getId());
        assertTrue(sorted.get(1).getId() < sorted.get(2).getId());
    }

    @Test
    void testSortByName() {
        ArrayEntity entityC = factory.createRandomArray("Charlie", 3, 1, 10);
        ArrayEntity entityA = factory.createRandomArray("Alice", 3, 1, 10);
        ArrayEntity entityB = factory.createRandomArray("Bob", 3, 1, 10);

        repository.add(entityC);
        repository.add(entityA);
        repository.add(entityB);

        List<ArrayEntity> sorted = repository.sortBy(ArrayComparators.NAME);

        assertEquals(3, sorted.size());
        assertEquals("Alice", sorted.getFirst().getName());
        assertEquals("Bob", sorted.get(1).getName());
        assertEquals("Charlie", sorted.get(2).getName());
    }

    @Test
    void testSortByFirstElement() {
        ArrayEntity entity1 = factory.createArray(new int[] {5, 1, 1});
        ArrayEntity entity2 = factory.createArray(new int[] {2, 2, 2});
        ArrayEntity entity3 = factory.createArray(new int[] {8, 3, 3});

        repository.add(entity1);
        repository.add(entity2);
        repository.add(entity3);

        List<ArrayEntity> sorted = repository.sortBy(ArrayComparators.FIRST);

        assertEquals(3, sorted.size());
        assertEquals(2, sorted.getFirst().getFirst());
        assertEquals(5, sorted.get(1).getFirst());
        assertEquals(8, sorted.get(2).getFirst());
    }

    @Test
    void testSortByLength() {
        ArrayEntity shortArray = factory.createArray(new int[] {1});
        ArrayEntity mediumArray = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity longArray = factory.createArray(new int[] {1, 2, 3, 4, 5});

        repository.add(longArray);
        repository.add(shortArray);
        repository.add(mediumArray);

        List<ArrayEntity> sorted = repository.sortBy(ArrayComparators.LENGTH);

        assertEquals(3, sorted.size());
        assertEquals(1, sorted.getFirst().getLength());
        assertEquals(3, sorted.get(1).getLength());
        assertEquals(5, sorted.get(2).getLength());
    }

    @Test
    void testSortEmptyRepository() {
        List<ArrayEntity> sorted = repository.sortBy(ArrayComparators.ID);

        assertEquals(0, sorted.size());
        assertTrue(sorted.isEmpty());
    }

    @Test
    void testClearRepository() {
        ArrayEntity entity1 = factory.createArray(new int[] {1, 2, 3});
        ArrayEntity entity2 = factory.createArray(new int[] {4, 5, 6});

        repository.add(entity1);
        repository.add(entity2);

        assertEquals(2, repository.findAll().size());

        repository.clear();

        assertEquals(0, repository.findAll().size());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void testRepositoryObserverIntegration() {
        ArrayEntity entity = factory.createArray(new int[] {1, 2, 3});
        repository.add(entity);
        System.out.println(entity);
        IntArrayStatistics initialStats = warehouse.getStatistics(entity.getId());
        System.out.println(initialStats);
        assertEquals(6, initialStats.getSum());

        entity.setArray(0, 10);

        IntArrayStatistics updatedStats = warehouse.getStatistics(entity.getId());
        assertEquals(15, updatedStats.getSum());
    }

    @Test
    void testFindAllReturnsNewList() {
        ArrayEntity entity = factory.createArray(new int[] {1, 2, 3});
        repository.add(entity);

        List<ArrayEntity> list1 = repository.findAll();
        List<ArrayEntity> list2 = repository.findAll();

        assertNotSame(list1, list2);
        assertEquals(list1, list2);
    }
}