package com.java.app.array.service;

import com.java.app.array.builder.ArrayBuilder;
import com.java.app.array.comparator.ArrayComparator;
import com.java.app.array.dao.ArrayRepository;
import com.java.app.array.dao.InMemoryArrayRepository;
import com.java.app.array.entity.integer.IntArrayEntity;
import com.java.app.array.entity.integer.IntArrayStatistics;
import com.java.app.array.entity.integer.IntWarehouse;
import com.java.app.array.specification.Specification;

import java.util.Comparator;
import java.util.List;

public class ArrayService {
    private final ArrayRepository<IntArrayEntity> repository;
    private final IntWarehouse intWarehouse;

    public ArrayService() {
        this.repository = new InMemoryArrayRepository();
        this.intWarehouse = IntWarehouse.getInstance();
    }

    public List<IntArrayEntity> sort(Comparator<IntArrayEntity> comparator) {
        return repository.sortBy(comparator);
    }

    public void createArray(String name, Integer[] array) {
        IntArrayEntity entity = new ArrayBuilder<>(IntArrayEntity::new)
                .setName(name)
                .setArray(array)
                .build();

        repository.add(entity);
    }

    public void deleteArray(int id) {
        repository.removeById(id);
    }

    public List<IntArrayEntity> searchArrays(Specification<IntArrayEntity> specification) {
        return repository.findBySpecification(specification);
    }

    public List<IntArrayEntity> sortArrays(ArrayComparator comparator) {
        return repository.sortBy(comparator);
    }

    public IntArrayStatistics getArrayStatistics(int id) {
        return intWarehouse.getStatistics(id);
    }

    public void updateArrayElement(int arrayId, int elementIndex, int newValue) {
        repository.findAll().stream()
                .filter(entity -> entity.getId() == arrayId)
                .findFirst()
                .ifPresent(entity -> entity.setArray(elementIndex, newValue));
    }
}
