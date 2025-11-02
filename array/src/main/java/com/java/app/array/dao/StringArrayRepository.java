package com.java.app.array.dao;

import com.java.app.array.entity.string.StringArrayEntity;
import com.java.app.array.specification.Specification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StringArrayRepository implements ArrayRepository<StringArrayEntity> {

    private final List<StringArrayEntity> entities;

    public StringArrayRepository() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(StringArrayEntity entity) {
        entities.add(entity);
    }

    @Override
    public void remove(StringArrayEntity entity) {
        entities.remove(entity);
    }

    @Override
    public void removeById(int id) {
        entities.stream()
                .filter(entity -> entity.getId() == id)
                .findFirst().ifPresent(this::remove);
    }

    @Override
    public List<StringArrayEntity> findBySpecification(
            Specification<StringArrayEntity> specification) {

        return entities.stream()
                .filter(specification::specify)
                .toList();
    }

    @Override
    public List<StringArrayEntity> findAll() {
        return new ArrayList<>(entities);
    }

    public List<StringArrayEntity> sortBy(Comparator<StringArrayEntity> comparator) {
        return entities.stream()
                .sorted(comparator)
                .toList();
    }

    @Override
    public List<StringArrayEntity> queryStream(Specification<StringArrayEntity> specification) {
        return entities.stream()
                .filter(specification::specify)
                .toList();
    }

    @Override
    public List<StringArrayEntity> queryPredicate(Predicate<StringArrayEntity> predicate) {
        return entities.stream()
                .filter(predicate)
                .toList();
    }

    @Override
    public void clear() {
        entities.clear();
    }

    public List<StringArrayEntity> findByName(String name) {
        return entities.stream()
                .filter(entity -> entity.getName().equals(name))
                .toList();
    }

}