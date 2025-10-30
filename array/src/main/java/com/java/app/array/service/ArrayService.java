package com.java.app.array.service;

import com.java.app.array.comparator.ArrayComparators;
import com.java.app.array.dao.ArrayRepository;
import com.java.app.array.dao.InMemoryArrayRepository;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.ArrayStatistics;
import com.java.app.array.entity.Warehouse;
import com.java.app.array.specification.Specification;

import java.util.Comparator;
import java.util.List;

public class ArrayService {
    private final ArrayRepository<ArrayEntity> repository;
    private final Warehouse warehouse;

    public ArrayService() {
        this.repository = new InMemoryArrayRepository();
        this.warehouse = Warehouse.getInstance();
    }

    public List<ArrayEntity> sortById() {
        return repository.sortBy(ArrayComparators.BY_ID);
    }

    public List<ArrayEntity> sortByName() {
        return repository.sortBy(ArrayComparators.BY_NAME);
    }

    public List<ArrayEntity> sortByFirstElement() {
        return repository.sortBy(ArrayComparators.BY_FIRST_ELEMENT);
    }

    public List<ArrayEntity> sortByLength() {
        return repository.sortBy(ArrayComparators.BY_LENGTH);
    }

    public void createArray(int id, String name, int[] array) {
        ArrayEntity entity = new ArrayEntity(id, name, array);
        repository.add(entity);
    }

    public void deleteArray(int id) {
        repository.removeById(id);
    }

    public List<ArrayEntity> searchArrays(Specification<ArrayEntity> specification) {
        return repository.findBySpecification(specification);
    }

    public List<ArrayEntity> sortArrays(Comparator<ArrayEntity> comparator) {
        return repository.sortBy(comparator);
    }

    public ArrayStatistics getArrayStatistics(int id) {
        return warehouse.getStatistics(id);
    }

    public void updateArrayElement(int arrayId, int elementIndex, int newValue) {
        List<ArrayEntity> entities = repository.findAll();
        for (ArrayEntity entity : entities) {
            if (entity.getId() == arrayId) {
                entity.setArray(elementIndex, newValue);
                break;
            }
        }
    }
}
