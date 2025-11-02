package com.java.app.array.dao;

import com.java.app.array.specification.Specification;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface ArrayRepository<T> {

    void add(T array);

    void remove(T array);

    void removeById(int id);

    List<T> findBySpecification(Specification<T> spec);

    List<T> findAll();

    void clear();

    List<T> queryStream(Specification<T> specification);

    List<T> queryPredicate(Predicate<T> predicate);

    List<T> sortBy(Comparator<T> comparator);
}
