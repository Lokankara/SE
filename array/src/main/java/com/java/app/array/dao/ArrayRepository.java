package com.java.app.array.dao;

import com.java.app.array.comparator.ArrayComparators;
import com.java.app.array.specification.Specification;

import java.util.List;

public interface ArrayRepository<T> {

    void add(T array);

    void remove(T array);

    void removeById(int id);

    List<T> findBySpecification(Specification<T> spec);

    List<T> findAll();

    List<T> sortBy(ArrayComparators comparator);

    void clear();
}
