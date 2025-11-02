package com.java.app.array.service;

import com.java.app.array.builder.StringArrayBuilder;
import com.java.app.array.comparator.StringArrayComparator;
import com.java.app.array.dao.StringArrayRepository;
import com.java.app.array.entity.string.StringArrayEntity;

import java.util.List;

public class StringArrayService {

    private final StringArrayRepository repository;

    public StringArrayService(StringArrayRepository repository) {
        this.repository = repository;
    }

    public Integer createArray(String name, String[] array) {
        StringArrayEntity entity = new StringArrayBuilder<>(StringArrayEntity::new)
                .setName(name)
                .setArray(array)
                .build();
        repository.add(entity);
        return entity.getId();
    }

    public void deleteArray(int id) {
        repository.removeById(id);
    }

    public List<StringArrayEntity> findByName(String name) {
        return repository.findByName(name);
    }

    public List<StringArrayEntity> sortArrays(StringArrayComparator comparator) {
        return repository.sortBy(comparator);
    }

    public List<StringArrayEntity> findAll() {
        return repository.findAll();
    }
}