package com.pp.app.array.service;

import com.pp.app.array.entity.string.StringArrayEntity;
import com.pp.app.array.dao.StringArrayRepository;
import com.pp.app.array.comparator.StringArrayComparator;
import com.pp.app.array.provider.SortArraysArgumentsProvider;
import com.pp.app.array.provider.StringArrayArgumentsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class StringArrayServiceTest {

    private StringArrayService service;

    @BeforeEach
    void setUp() {
        service = new StringArrayService(new StringArrayRepository());
    }

    @ParameterizedTest(name = "Create array {0} should store array correctly")
    @ArgumentsSource(StringArrayArgumentsProvider.class)
    @DisplayName("Create array stores array in repository")
    void createArrayStoresArrayInRepository(String name, String[] array) {
        service.createArray(name, array);

        List<StringArrayEntity> found = service.findByName(name);

        assertFalse(found.isEmpty());
        StringArrayEntity entity = found.stream().findFirst().orElseThrow();
        assertEquals(name, entity.getName());
        assertArrayEquals(array, entity.getArray());
    }

    @ParameterizedTest(name = "Delete array {0} should remove from repository")
    @ArgumentsSource(StringArrayArgumentsProvider.class)
    @DisplayName("Delete array removes from repository")
    void deleteArrayRemovesFromRepository(String name, String[] array) {
        service.createArray(name, array);

        List<StringArrayEntity> found = service.findByName(name);
        assertFalse(found.isEmpty());
        StringArrayEntity entity = found.stream().findFirst().orElseThrow();

        service.deleteArray(entity.getId());

        List<StringArrayEntity> afterDelete = service.findByName(name);
        assertTrue(afterDelete.isEmpty());
    }

    @ParameterizedTest(name = "Sort arrays by name works correctly for {0}")
    @ArgumentsSource(StringArrayArgumentsProvider.class)
    @DisplayName("Sort arrays by name works correctly")
    void sortArraysByNameWorksCorrectly(String name, String[] array) {
        service.createArray(name, array);
        service.createArray("Charlie", new String[] {"C"});
        service.createArray("Alice", new String[] {"A"});
        service.createArray("Bob", new String[] {"B"});

        List<StringArrayEntity> sorted = service.sortArrays(StringArrayComparator.NAME);
        assertEquals("Alice", sorted.stream().findFirst().orElseThrow().getName());
    }

    @ParameterizedTest(name = "Search arrays by first element works correctly for {0}")
    @ArgumentsSource(StringArrayArgumentsProvider.class)
    @DisplayName("Search arrays by first element works correctly")
    void searchArraysByFirstElementWorksCorrectly(String name, String[] array) {
        service.createArray(name, array);
        service.createArray("other", new String[] {"x", "y"});

        java.util.List<StringArrayEntity> matches = service.findAll().stream()
                .filter(e -> {
                    String[] a = e.getArray();
                    if (array == null || array.length == 0) {
                        return a == null || a.length == 0;
                    }
                    return a != null && a.length > 0 && java.util.Objects.equals(a[0], array[0]);
                })
                .toList();

        assertEquals(1, matches.size());
        assertEquals(name, matches.getFirst().getName());
    }

    @ParameterizedTest(name = "Create array {0} registers with repository and findAll contains it")
    @ArgumentsSource(StringArrayArgumentsProvider.class)
    @DisplayName("Create array registers in repository and findAll contains it")
    void createArrayRegistersInRepository(String name, String[] array) {
        service.createArray(name, array);
        var all = service.findAll();
        assertFalse(all.isEmpty());
        assertTrue(all.stream().anyMatch(a -> Objects.equals(a.getName(), name)));
    }

    @ParameterizedTest(name = "Create array {0} with duplicate names stores multiple entries")
    @ArgumentsSource(StringArrayArgumentsProvider.class)
    @DisplayName("Create with duplicate names stores multiple entries")
    void createDuplicateNamesStoresMultiple(String name, String[] array) {
        service.createArray(name, array);
        service.createArray(name, new String[] {"other"});
        var found = service.findByName(name);
        assertEquals(2, found.size());
    }


    @ParameterizedTest(name = "Sort arrays with comparator, expect order {2}")
    @ArgumentsSource(SortArraysArgumentsProvider.class)
    @DisplayName("Sort arrays by comparator works correctly")
    void sortArraysByComparatorWorksCorrectly(
            List<Map.Entry<String, String[]>> arraysToCreate, StringArrayComparator comparator,
            List<String> expectedOrder
    ) {
        arraysToCreate.forEach(entry -> service.createArray(entry.getKey(), entry.getValue()));

        List<StringArrayEntity> sorted = service.sortArrays(comparator);

        List<String> actualOrder = sorted.stream()
                .map(StringArrayEntity::getName)
                .toList();

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    @DisplayName("deleteArray removes entity and subsequent find returns empty")
    void deleteArrayRemovesEntity() {
        service.createArray("toDelete", new String[] {"a", "b"});
        var found = service.findByName("toDelete");
        assertEquals(1, found.size());
        int id = found.getFirst().getId();
        service.deleteArray(id);
        var after = service.findByName("toDelete");
        assertTrue(after.isEmpty());
    }

    @Test
    @DisplayName("sortArrays uses provided comparator and preserves lengths")
    void sortArraysWithComparator() {
        service.createArray("Z", new String[] {"z"});
        service.createArray("A", new String[] {"a", "aa"});
        var sorted = service.sortArrays(StringArrayComparator.NAME);
        assertEquals(2, sorted.size());
        assertEquals("A", sorted.getFirst().getName());
        assertEquals(2, sorted.getFirst().getArray().length);
        assertEquals(2, service.findAll().size());
    }

    @Test
    @DisplayName("delete non-existent id does nothing")
    void deleteNonExistentDoesNothing() {
        service.createArray("keep", new String[] {"v"});
        assertDoesNotThrow(() -> service.deleteArray(Integer.MAX_VALUE));
        var all = service.findAll();
        assertEquals(1, all.size());
    }
}