package com.java.app.array.service;

import com.java.app.array.builder.ArrayBuilder;
import com.java.app.array.comparator.ArrayComparators;
import com.java.app.array.dao.ArrayRepository;
import com.java.app.array.dao.InMemoryArrayRepository;
import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.IntArrayStatistics;
import com.java.app.array.entity.Warehouse;
import com.java.app.array.specification.Specification;

import java.util.List;

public class ArrayService {
    private final ArrayRepository<ArrayEntity> repository;
    private final Warehouse warehouse;

    public ArrayService() {
        this.repository = new InMemoryArrayRepository();
        this.warehouse = Warehouse.getInstance();
    }

    public List<ArrayEntity> sort(ArrayComparators comparator) {
        return repository.sortBy(comparator);
    }

    public void createArray(String name, int[] array) {
        ArrayEntity entity = new ArrayBuilder<>(ArrayEntity::new)
                .setName(name)
                .setArray(array)
                .build();

        repository.add(entity);
    }

    public void deleteArray(int id) {
        repository.removeById(id);
    }

    public List<ArrayEntity> searchArrays(Specification<ArrayEntity> specification) {
        return repository.findBySpecification(specification);
    }

    public List<ArrayEntity> sortArrays(ArrayComparators comparator) {
        return repository.sortBy(comparator);
    }

    public IntArrayStatistics getArrayStatistics(int id) {
        return warehouse.getStatistics(id);
    }

    public void updateArrayElement(int arrayId, int elementIndex, int newValue) {
        repository.findAll().stream()
                .filter(entity -> entity.getId() == arrayId)
                .findFirst()
                .ifPresent(entity -> entity.setArray(elementIndex, newValue));
    }
}
