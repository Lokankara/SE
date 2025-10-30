package com.java.app.array.dao;

import com.java.app.array.entity.ArrayEntity;
import com.java.app.array.entity.Warehouse;
import com.java.app.array.specification.Specification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryArrayRepository implements ArrayRepository<ArrayEntity> {

    private final Warehouse warehouse;
    private final List<ArrayEntity> entities;

    public InMemoryArrayRepository() {
        this.warehouse = Warehouse.getInstance();
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(ArrayEntity entity) {
        entities.add(entity);
        entity.addListener(warehouse);
        warehouse.onChanged(entity);
    }

    @Override
    public void remove(ArrayEntity entity) {
        entities.remove(entity);
        entity.removeListener(warehouse);
        warehouse.removeStatistics(entity.getId());
    }

    @Override
    public void removeById(int id) {
        entities.stream()
                .filter(entity -> entity.getId() == id)
                .findFirst().ifPresent(this::remove);
    }

    @Override
    public List<ArrayEntity> findBySpecification(Specification<ArrayEntity> specification) {
        return entities.stream()
                .filter(specification::isSatisfiedBy)
                .toList();
    }

    @Override
    public List<ArrayEntity> findAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public List<ArrayEntity> sortBy(Comparator<ArrayEntity> comparator) {
        return entities.stream()
                .sorted(comparator)
                .toList();
    }

    @Override
    public void clear() {
        entities.clear();
    }
}
