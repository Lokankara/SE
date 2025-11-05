package com.pp.app.array.dao;

import com.pp.app.array.entity.integer.IntArrayEntity;
import com.pp.app.array.entity.integer.IntWarehouse;
import com.pp.app.array.specification.Specification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class InMemoryArrayRepository implements ArrayRepository<IntArrayEntity> {

    private final IntWarehouse intWarehouse;
    private final List<IntArrayEntity> entities;

    public InMemoryArrayRepository() {
        this.intWarehouse = IntWarehouse.getInstance();
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(IntArrayEntity entity) {
        entities.add(entity);
        entity.attach(intWarehouse);
        intWarehouse.onChanged(entity);
    }

    @Override
    public void remove(IntArrayEntity entity) {
        entities.remove(entity);
        entity.removeListener(intWarehouse);
        intWarehouse.removeStatistics(entity.getId());
    }

    @Override
    public void removeById(int id) {
        entities.stream()
                .filter(entity -> entity.getId() == id)
                .findFirst().ifPresent(this::remove);
    }

    @Override
    public List<IntArrayEntity> findBySpecification(
            Specification<IntArrayEntity> specification) {

        return entities.stream()
                .filter(specification::specify)
                .toList();
    }

    @Override
    public List<IntArrayEntity> findAll() {
        return new ArrayList<>(entities);
    }

    public List<IntArrayEntity> sortBy(Comparator<IntArrayEntity> comparator) {
        return entities.stream()
                .sorted(comparator)
                .toList();
    }

    @Override
    public List<IntArrayEntity> queryStream(Specification<IntArrayEntity> specification) {
        return entities.stream()
                .filter(specification::specify)
                .toList();
    }

    @Override
    public List<IntArrayEntity> queryPredicate(Predicate<IntArrayEntity> predicate) {
        return entities.stream()
                .filter(predicate)
                .toList();
    }

    @Override
    public void clear() {
        entities.clear();
    }
}
